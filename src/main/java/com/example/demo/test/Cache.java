package com.example.demo.test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Cache {

   private static Map<String,Object> map=new HashMap<String,Object>();

    static ReentrantReadWriteLock rlw=new ReentrantReadWriteLock();
   static ReentrantReadWriteLock.ReadLock r=rlw.readLock();
   static ReentrantReadWriteLock.WriteLock w =rlw.writeLock();

   public static Object put(String key,Object value){
       w.lock();
       try{
           return map.put(key,value);
       }finally {
           w.unlock();
       }
   }
   public static Object get(String key){
       r.lock();
       try{
           return map.get(key);
       }finally {
           r.unlock();
       }
   }
    // 清空所有的内容
    public static final void clear() {
        w.lock();
        try {
            map.clear();
        } finally {
            w.unlock();
        }
    }
}
