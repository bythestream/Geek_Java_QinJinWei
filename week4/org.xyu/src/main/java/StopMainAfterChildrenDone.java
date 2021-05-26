import java.util.concurrent.CountDownLatch;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 一个简单的代码参考：
 */
 public class StopMainAfterChildrenDone  extends Thread{

    
    public static void main(String[] args) {
        
        long start=System.currentTimeMillis();

        // 在这里创建一个线程或线程池，
        int numThread = 10;
        CountDownLatch signal = new CountDownLatch(numThread);
        for(int i=0;i<numThread;i++){
            Thread t = new CountDownLatchThread(signal);
            t.start();
        }

        try{
            signal.await();
        } catch(InterruptedException e){
            e.printStackTrace();
        }

         
        System.out.println("method1 使用时间："+ (System.currentTimeMillis()-start) + " ms");

        Thread thread1 = new Thread(new MyRunnable());
        Thread thread2 = new Thread(new MyRunnable());
        System.out.println("method 2: start two threads with join");

        thread1.start();
        thread2.start();
        try{
            thread2.join();
            thread1.join();
            System.out.println("method 2." + Thread.currentThread().getName() + " child runnable both ends. \n");
        } catch(Exception e){
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + " ended.");

    }
    
    static class MyRunnable implements Runnable{
    	
    	public MyRunnable() {
    		
    	}
    	
        @Override
        public void run()
        {
            System.out.println(Thread.currentThread().getName() + ": I begin to work.\n" );
            
                int result = sum();
                System.out.println(" 异步计算结果为：" + result );
            
            System.out.println(Thread.currentThread().getName() + " My work ended.\n" );

        }
    }
    private static int sum() {
        return fibo(36);
    }
    
    private static int fibo(int a) {
        if ( a < 2) 
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}