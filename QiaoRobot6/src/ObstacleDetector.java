import lejos.hardware.sensor.EV3UltrasonicSensor;
//import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class ObstacleDetector implements Runnable {

	private DataExchange DE;
	private EV3UltrasonicSensor ultrasonicSensor;
	private SampleProvider distanceProvider;
	private float[] distanceSample;

	final float OBSTACLE_DISTANCE_THRESHOLD = 20.0f;
	// private boolean obstacleDetected = false;

	public ObstacleDetector(DataExchange dataExchange) {
		this.DE = dataExchange;
		ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S2);
		distanceProvider = ultrasonicSensor.getDistanceMode();
		distanceSample = new float[distanceProvider.sampleSize()];

	}

	@Override
	public void run() {
		while (!DE.isStopThreads()) {
			distanceProvider.fetchSample(distanceSample, 0);
			float distance = distanceSample[0] * 100; // Convert to centimeters

			if (distance < OBSTACLE_DISTANCE_THRESHOLD) { // Adjust the threshold according needs
	//			DE.setAvoidObstacle(true);
				LCD.drawString("Find Obstacle!", 1, 6);
				// Sound.buzz();

				if (DE.getCurrent_CMD() == DataExchange.followLine && !DE.isAvoidObstacle()) {
					// DE.recordObstacleDetection();
//					DE.setFirstTime(System.currentTimeMillis());
					DE.setAvoidObstacle(true);
					DE.setCurrent_CMD(DataExchange.avoid);
					System.out.println("avoiding now!");
				} else if(DE.getCurrent_CMD() == DataExchange.followLine && DE.isAvoidObstacle()) {
//					DE.setSecondTime(System.currentTimeMillis());
//					long time = DE.getSecondTime() - DE.getFirstTime();
//					int timeSeconds = (int) (time / 1000);
//					LCD.clear();
//					LCD.drawString("Time between:", 0, 0);
//					LCD.drawString(timeSeconds + " seconds", 0, 1);
					DE.setCurrent_CMD(DataExchange.end);
					// DE.setObstacleDetected(true);

				}

			}

//            else 
//            {
//  //              obstacleDetected = false;  
//            	DE.setCurrent_CMD(DataExchange.followLine);
//            }
//            
			try {
				Thread.sleep(500); // Control the frequency of checks
			} catch (InterruptedException e) {
				e.printStackTrace(); // Print error trace and exit the loop
				// break;

			}
			Delay.msDelay(100);
		}
		ultrasonicSensor.close();
	}

}
