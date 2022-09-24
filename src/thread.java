public class thread extends Thread {
    public void run() {
        int num = 5000 + (int)(Math.random()*5000);
        try {
        	thread.sleep(num);
		} catch (InterruptedException e) {
			//System.out.println("Exception Occurs");
			e.printStackTrace();
		}
    }
}
