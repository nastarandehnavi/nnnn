
public class RunClass1 {

	public static void main(String[] args) {

		DataExchange DE = new DataExchange();

		ColorDetector colorDetector = new ColorDetector(DE);
		MotorDrive motorDriver = new MotorDrive(DE);
		ObstacleDetector obstacleDetector = new ObstacleDetector(DE);
		// SingSongs SingSongs = new SingSongs();
//	ParameterAdjuster parameterAdjuster = new ParameterAdjuster(DE);

		Thread colorDetectJob = new Thread(colorDetector);
		Thread motorDriveJob = new Thread(motorDriver);
		Thread obstacleDetectJob = new Thread(obstacleDetector);
		// Thread SingSongsJob = new Thread(SingSongs);
//	Thread parameterAdjustJob = new Thread(parameterAdjuster);

//	colorDetectJob.setDaemon(true);
//    motorDriveJob.setDaemon(true);
//    obstacleDetectJob.setDaemon(true);

		colorDetectJob.start();
		motorDriveJob.start();
		obstacleDetectJob.start();
		// SingSongsJob.start();
		// parameterAdjustJob.start();

		try {
			colorDetectJob.join();
			motorDriveJob.join();
			obstacleDetectJob.join();
//	        parameterAdjustJob.join(); 
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
