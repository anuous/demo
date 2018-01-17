package com.example.demo.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Test {

    public static void main(String[] args){
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        long start=System.currentTimeMillis();
        System.out.println(start);
        try {
            System.out.println(1 << 16);
            System.out.println(System.currentTimeMillis());
            lock.newCondition().await(1000l, TimeUnit.MILLISECONDS);
            System.out.println(System.currentTimeMillis()-start);
            System.out.println(System.currentTimeMillis());
        }catch(InterruptedException e){

        }finally {
            try {
                Thread.sleep(1000l);
                System.out.println("sleep--------");
            }catch (InterruptedException e){
            }
            lock.unlock();
        }
        lock.lock();
        try {
            System.out.println(Math.pow(2,16.2));
        }finally {

            lock.unlock();
        }

    }
}
