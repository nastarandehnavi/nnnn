
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;

public class ColorDetector implements Runnable {
	// SHIFT+CMD+f
	private DataExchange DE;
	private EV3ColorSensor colorSensor;
	private SampleProvider colorProvider;
	private float[] colorProviderSample;

	final float EDGE_LOWER_THRESHOLD = 15.0f;
	final float EDGE_UPPER_THRESHOLD = 22.0f;

	public ColorDetector(DataExchange dataExchange) {
		this.DE = dataExchange;
		colorSensor = new EV3ColorSensor(SensorPort.S3);
		colorProvider = colorSensor.getRedMode(); // Identify the color of the object.
		colorProviderSample = new float[colorProvider.sampleSize()];

	}

	@Override
	public void run() {
		// Activate the color sensor's light.
		colorSensor.setFloodlight(true);
		DE.setFirstTime(System.currentTimeMillis());

		while (!DE.isStopThreads()) {
			// Fetch the color sample and store it.
			colorProvider.fetchSample(colorProviderSample, 0);
			float intensity = colorProviderSample[0] * 100;
			DE.setCurrentThreshold(intensity);
			DE.setCurrent_CMD(DataExchange.followLine);

//      if  (intensity >= EDGE_LOWER_THRESHOLD && intensity <= EDGE_UPPER_THRESHOLD) {
//    	  DE.setCurrentThreshold(intensity);
//      }
//      else if (intensity < EDGE_LOWER_THRESHOLD) {
//
//      }
//      else {
//
//      }

		}

		// Clean up resources and close the sensor upon thread interruption.
		colorSensor.close();
	}
}
