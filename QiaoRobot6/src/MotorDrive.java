
//import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
//import lejos.hardware.motor.UnregulatedMotor;
//import lejos.hardware.port.MotorPort;
//import lejos.utility.Delay;
import lejos.utility.Delay;

public class MotorDrive implements Runnable {

	private DataExchange DE;
//    private UnregulatedMotor motorA; 
//    private UnregulatedMotor motorB; 

	final float EDGE_LOWER_THRESHOLD = 15.0f;
	final float EDGE_UPPER_THRESHOLD = 21.0f;
//    final float OBSTACLE_DISTANCE_THRESHOLD = 20.0f; // Adjust as needed

	public MotorDrive(DataExchange dataExchange) {
		this.DE = dataExchange;
//        motorA = new UnregulatedMotor(MotorPort.C);
//	    motorB = new UnregulatedMotor(MotorPort.D);
	}

	@Override
	public void run() {

		while (!DE.isStopThreads()) {
			if (DE.getCurrent_CMD() == DataExchange.followLine) {
//			if (DE.getCurrentThreshold() !=  0 ) {
				LCD.clear();
				LCD.drawString("Following line!", 1, 6);
				followLine();

			} else if (DE.getCurrent_CMD() == DataExchange.avoid) {
				// if(DE.isAvoidObstacle()) {
				// driveAvoid();
				// backToLine();
				LCD.clear();
				LCD.drawString("Avoiding Obstacle!", 1, 6);
				avoidObstacle();
				findLine();
				// DE.setAvoidObstacle(false);

//              }
//			  else if (DE.isObstacleDetected()) {
//				  end();
//	              break;
//	            }

			} else if (DE.getCurrent_CMD() == DataExchange.end) {
				end();

			}

		}

	}

//            private void driveAvoid() {
//            	 Motor.C.stop(); 
//                 Motor.D.stop(); 
//                 
//                 Motor.C.setSpeed(100); 
//                 Motor.D.setSpeed(100); 
//
//                 Motor.C.rotate(-200); 
//                 Motor.D.rotate(200); 
//
//                 
//                 Motor.C.forward(); 
//                 Motor.D.forward();
//            }
//            
	private void followLine() {
		if (DE.getCurrentThreshold() >= EDGE_LOWER_THRESHOLD && DE.getCurrentThreshold() <= EDGE_UPPER_THRESHOLD) {

			Motor.C.setSpeed(200);
			Motor.D.setSpeed(200);
			Motor.C.forward();
			Motor.D.forward();
		} else if (DE.getCurrentThreshold() < EDGE_LOWER_THRESHOLD) {

			Motor.C.setSpeed(150);
			Motor.D.setSpeed(50);
			Motor.C.forward();
			Motor.D.forward();
		} else if (DE.getCurrentThreshold() > EDGE_UPPER_THRESHOLD) {
			Motor.C.setSpeed(50);
			Motor.D.setSpeed(150);
			Motor.C.forward();
			Motor.D.forward();
		}
	}
//            private void backToLine() {
//            	for(int n = 0; n <= 4; n ++) {
//                    int angle = 90; 
//     
//                    Motor.C.rotate(-angle, true); 
//                    Motor.D.rotate(angle); 
//
//                    Motor.C.forward(); 
//                    Motor.D.forward();
//                    if(DE.getCurrent_CMD() == DataExchange.followLine) {
//                    	return;
//                    }
//                    Motor.C.rotate(angle, true); 
//                    Motor.D.rotate(-angle); 
//
//                    Motor.C.forward(); 
//                    Motor.D.forward();
//                    if(DE.getCurrent_CMD() == DataExchange.followLine) {
//                    	return;
//                    }
//                    
////                    else {
////                    	end();
////                    }
//            	}
//            }

	private void end() {

		LCD.clear();
		LCD.drawString("Program ends!", 0, 0);
		LCD.drawString("Thank you!", 1, 1);
		// Delay.msDelay(2000);
		Motor.C.stop();
		Motor.D.stop();
		Motor.C.close();
		Motor.D.close();

		long time = DE.getSecondTime() - DE.getFirstTime();
		int timeSeconds = (int) (time / 1000);
		LCD.clear();
		LCD.drawString("Time between:", 0, 0);
		LCD.drawString(timeSeconds + " seconds", 0, 1);
		
		DE.setStopThreads(true);
	}

	private void avoidObstacle() {

		
		System.out.println("jippii started!!!");
		// Move robot left for 1,8sec
		Motor.C.setSpeed(150);
		Motor.D.setSpeed(300);
		Motor.C.forward();
		Motor.D.forward();

		Delay.msDelay(1800);

		// Move robot right for 2sec
		Motor.C.setSpeed(300);
		Motor.D.setSpeed(90);
		Motor.C.forward();
		Motor.D.forward();

		Delay.msDelay(2000);

		// Move robot straight 1,4sec
		Motor.C.setSpeed(200);
		Motor.D.setSpeed(200);
		Motor.C.forward();
		Motor.D.forward();

		Delay.msDelay(1400);

		// Rotate the robot so it is easier for it to continue to follow a line
		Motor.C.rotate(90);
		Delay.msDelay(200);

		System.out.println("jippii done!!!");
		
		// Robot continues to follow a line
//	  DE.setCurrent_CMD(DataExchange.followLine);
	}

	private void findLine() {
		// Perform a zigzag search
		for (int i = 0; i < 4; i++) { // Execute four times to increase the search range
			Motor.C.rotate(-90 - 45 * i, true); // Turn left
			Motor.D.rotate(90 + 45 * i);
			Motor.C.rotate(360, true); // Move forward
			Motor.D.rotate(360);
			if (DE.getCurrent_CMD() == DataExchange.followLine) { // If the line is found
				return; // End the search
			}
			Motor.C.rotate(90 + 90 * i, true); // Turn right
			Motor.D.rotate(-90 - 90 * i);
			Motor.C.rotate(360, true); // Move forward
			Motor.D.rotate(360);
			if (DE.getCurrent_CMD() == DataExchange.followLine) { // If the line is found again
				return; // End the search
			}
		}
		// If the zigzag search is completed and the line is still not found, it may set
		// to END state or try other strategies
		DE.setCurrent_CMD(DataExchange.followLine);
	}
}
