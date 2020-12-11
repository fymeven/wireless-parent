package cn._51even.wireless.web.test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {

    private Integer ticket = 5;

    public void buyTicket(){
//        synchronized (this){
            if (ticket >0){
                System.out.println(String.format(Thread.currentThread().getName()+"卖出了第%s张票", ticket));
                ticket--;
            }
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//        }
    }

    public static void main(String[] args) {
        LockTest lockTest = new LockTest();
        Lock lock = new ReentrantLock();
        for (int i = 1; i <= 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    lock.lock();
                    lockTest.buyTicket();
                    lock.unlock();
                }
            },String.format("%s号窗口",i)).start();
        }
    }
}
