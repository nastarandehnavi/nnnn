
public class DataExchange {

	private float currentThreshold = 0;
	private long firstTime = 0;
	private long secondTime = 0;
	private int current_CMD = 0;
	private boolean ObstacleDetected = false;
	private boolean avoidObstacle = false;
//	 private int obstacleCount = 0;

	private boolean stopThreads = false;

	public final static int followLine = 1;
	public final static int end = 2;
	public final static int avoid = 3;

	public synchronized int getCurrent_CMD() {
		return current_CMD;

	}

	public synchronized void setCurrent_CMD(int current_CMD) {
		this.current_CMD = current_CMD;
		notifyAll();
	}

	public synchronized long getFirstTime() {
		return firstTime;

	}

	public synchronized void setFirstTime(long firstTime) {
		this.firstTime = firstTime;
		notifyAll();
	}

	public synchronized long getSecondTime() {
		return secondTime;

	}

	public synchronized void setSecondTime(long secondTime) {
		this.secondTime = secondTime;
		notifyAll();
	}

	public synchronized float getCurrentThreshold() {
		return currentThreshold;

	}

	public synchronized void setCurrentThreshold(float currentThreshold) {
		this.currentThreshold = currentThreshold;
		notifyAll();
	}

	public synchronized boolean isObstacleDetected() {
		return ObstacleDetected;

	}

	public synchronized void setObstacleDetected(boolean obstacleDetected) {
		ObstacleDetected = obstacleDetected;
		notifyAll();
	}

	public synchronized boolean isAvoidObstacle() {
		return avoidObstacle;

	}

	public synchronized void setAvoidObstacle(boolean avoidObstacle) {
		this.avoidObstacle = avoidObstacle;
		notifyAll();

	}

	public synchronized boolean isStopThreads() {
		return stopThreads;

	}

	public synchronized void setStopThreads(boolean stopThreads) {
		this.stopThreads = stopThreads;
		notifyAll();
	}

//		public synchronized void recordObstacleDetection() {
//    	    
//	        if (!isAvoidObstacle()) {
//	            obstacleCount++;
//	            if (obstacleCount == 1) {
//	            	firstTime = System.currentTimeMillis();
//	               
//	            } else if (obstacleCount == 2) {
//	                secondTime = System.currentTimeMillis();
//	            }
//	        }
//	      }
}
