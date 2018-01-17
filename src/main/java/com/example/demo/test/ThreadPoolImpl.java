package com.example.demo.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ThreadPoolImpl<Job extends Runnable> implements ThreadPool<Job> {

    private static final int MAX_WORKER_NUMS=10;
    private static final int DEFAULT_WORKER_NUMS=5;
    private static final int MIN_WORKER_NUMS=1;

    private final LinkedList<Job> jobs=new LinkedList<Job>();

    private final List<Worker> workers= Collections.synchronizedList(new ArrayList<Worker>());
    // 工作者线程的数量
    private int workerNum = DEFAULT_WORKER_NUMS;
    // 线程编号生成
    private AtomicLong threadNum = new AtomicLong();

    public ThreadPoolImpl(){
        initalizeWorkers(workerNum);
    }
    public ThreadPoolImpl( int nums){
        workerNum= nums>MAX_WORKER_NUMS?MAX_WORKER_NUMS:nums<MIN_WORKER_NUMS?MIN_WORKER_NUMS:nums;
        initalizeWorkers(workerNum);
    }
    @Override
    public void execute(Job job) {
        if(job!=null){
            synchronized (jobs){
                jobs.add(job);
                jobs.notify();
            }
        }
    }

    @Override
    public void shutdown() {
        for(Worker worker:workers){
            worker.shutdown();
        }
    }

    @Override
    public void addWorkers(int num) {
        if(this.workerNum+num>MAX_WORKER_NUMS){
            num=MAX_WORKER_NUMS-this.workerNum;
        }
        initalizeWorkers(num);
        this.workerNum+=num;
    }

    @Override
    public void removeWorker(int num) {
        synchronized (jobs){
            if(num>this.workerNum){
                throw new IllegalArgumentException();
            }
            int count=0;
            while(count<num){
                Worker worker=workers.get(count);
                if(workers.remove(worker)){
                    worker.shutdown();
                    count++;
                }
            }
            this.workerNum-=count;
        }
    }

    @Override
    public int getJobSize() {
        return jobs.size();
    }

    private void initalizeWorkers(int num){
        for(int i=0;i<num;i++){
            Worker worker=new Worker();
            workers.add(worker);
            Thread thread=new Thread(worker,"thread-worker-"+threadNum.incrementAndGet());
            thread.start();
        }
    }

    class Worker implements Runnable{
        private volatile boolean running = true;
        @Override
        public void run() {
            Job job =null;
            synchronized (jobs){
                while(jobs.isEmpty()){
                    try {
                        jobs.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                    job=jobs.removeFirst();
                    if(job!=null){
                        try {
                            job.run();
                        } catch (Exception e) {
                            //ignore
                        }
                    }
                }
            }
        }
        public void shutdown(){
            running=false;
        }
    }
}

