package vn.com.payment.config;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TaskManager {

	//private static Timer timer = new Timer();
	

	/**
	 * @param task
	 * @param time_to_run
	 */
	public static void addTask(TimerTask task, Date time_to_run) {
		// Timer timer = new Timer();
		getTimer().schedule(task, time_to_run);
	}

	/**
	 * @param task
	 * @param time_to_run
	 * @param period in miliseconds
	 */
	public static void addTask(TimerTask task, Date time_to_run, long period) {
		// Timer timer = new Timer();
		getTimer().schedule(task, time_to_run, period);
	}

	/**
	 * @param task
	 * @param delay
	 *            in miliseconds
	 * @param period
	 *            in miliseconds
	 */
	public static void addTask(TimerTask task, long delay, long period) {
		// Timer timer = new Timer();
		getTimer().schedule(task, delay, period);
	}

	public static void shutdown() {
		// Timer timer = new Timer();
		getTimer().cancel();
	}

	static Timer getTimer() {
		return new Timer();
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			// Timer timer = new Timer();
			// timer.

			//TaskManager.addTask(new TestTask("Task" + i), 1000, 15000);
		}
	}
}
