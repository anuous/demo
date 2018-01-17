package com.example.demo.test;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueTest {
    public static void main(String[]args){
        ConcurrentLinkedQueue<Integer> queue=new ConcurrentLinkedQueue<Integer>();
        for (int i = 0; i <10 ; i++) {
            queue.add(i);
            System.out.println(i);
        }
    }
}
