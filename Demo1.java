package xianchengtongxin;
/*
 * 线程间通信的简单Demo
 * demo1:两个线程同时进行
 * demo2:线程A执行完再执行线程B（join()）
 * demo3:线程B执行完一次等待，线程A开始执行并且完成(synchronized  notify )
 * references:http://www.importnew.com/26850.html
 * 
 */
public class Demo1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		System.out.println("第一次测试");
//		demo1();
//		System.out.println("第二次测试");
//		demo2();
		System.out.println("第三次测试");
		demo3();

		
	}
	private static void demo1(){
		Thread A= new Thread(new Runnable(){
			public void run(){
				printNumber("A");
			}
		});
		Thread B=new Thread(new Runnable(){
			public void run(){
				printNumber("B");
			}
		});
		A.start();
		B.start();
	}
	private static void printNumber(String threadName){
		int i=0;
		while(i++<3){
			try{
				Thread.sleep(100);
			} catch(InterruptedException e){
				e.printStackTrace();
			}
			System.out.println(threadName+"print:"+i);
		}
	}
	private static void demo2(){
		 final Thread A=new Thread(new Runnable(){
			public void run(){
				printNumber("A");
			}
		});
		Thread B=new Thread(new Runnable(){
			public void run(){
			System.out.println("B 开始等待 A");
			try{
				A.join();
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			printNumber("B");
			}
		});
		A.start();
		B.start();
	}
	
	private static void demo3(){
		final Object lock = new Object();
		Thread A = new Thread(new Runnable(){
			public void run(){
				synchronized (lock) {
					System.out.println("A 1");
				try{
					lock.wait();
				}	
				catch(InterruptedException e){
					e.printStackTrace();
				}
				System.out.println("A 2");
				System.out.println("A 3");
				}
				
			}
		});
		Thread B= new Thread(new Runnable(){
			public void run(){
				System.out.println("B 1");
				System.out.println("B 2");
				System.out.println("B 3");
				synchronized (lock) {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}			
				lock.notify();
				}
			}
		});
		A.start();
		B.start();
	}
	

}
