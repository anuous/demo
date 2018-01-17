package com.example.demo.test;

import java.sql.Connection;
import java.util.LinkedList;

public class ConnectionPool {
    LinkedList<Connection> pool = new LinkedList<Connection>();

    public ConnectionPool(int initialSize){
        if(initialSize>0){
            for (int i = 0; i <initialSize ; i++) {
                pool.add(ConnectionDriver.createConnection());
            }
        }
    }

    public  Connection fetchConnection(long mills) throws InterruptedException {
        synchronized (pool){
            if(mills<=0){
                while(pool.isEmpty()){
                    pool.wait();
                }
                return pool.removeFirst();
            }else{
                long feature=System.currentTimeMillis()+mills;
                long remaining=mills;
                while(pool.isEmpty()||remaining>0){
                    pool.wait(remaining);
                    remaining=feature-System.currentTimeMillis();
                }
                Connection result=null;
                if(!pool.isEmpty()){
                    result=pool.removeFirst();
                }
                return result;
            }
        }
    }
    public void releaseConnection(Connection connection){
        if(connection!=null){
            synchronized (pool){
                pool.add(connection);
                pool.notifyAll();
            }
        }
    }
}
