import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class ReadersWriter implements ReadersWriterInterface {

	private Semaphore orderMutex;
	private Semaphore read;
	private Semaphore write;
	private int readCount;
	private Map<String, Integer> schedules;

	public ReadersWriter() {
		readCount = 0;
		orderMutex = new Semaphore(1, true);
		read = new Semaphore(1, true);
		write = new Semaphore(1, true);
		schedules = new HashMap<String, Integer>();
		fillMapWithInitialSchedules();
	}

	public void writer(String flight, Integer time) throws InterruptedException {
		orderMutex.acquire();
		try {
			write.acquire();
			if (orderMutex.hasQueuedThreads()) {
				System.out.println("Status :	Acquired"+ " ||		Writer Thread: "+Thread.currentThread().getName()+" ||		Waiting Threads :	"+orderMutex.getQueueLength());
			}
		} 
		finally {
			orderMutex.release();
		}
		// Writing/Updating new/existing schedules
		try {
			Thread.sleep(1000);
			setMapValue(flight, time);
			// System.out.println("---------------------------------------------");
			System.out.println("Writer :	" +Thread.currentThread().getName()+" ||		Data: "+time);
			System.out.println("---------------------------------------------");

			// System.out.println("Set for " +flight+ " time: "+time+" by Writer Thread: " +Thread.currentThread().getName());

			// Writing done
		} 
		finally {
			write.release();
		}
	}

	public void reader(String flight) throws InterruptedException {
		orderMutex.acquire();
		read.acquire();
		try {
			readCount++;
			if (readCount == 1) {
				write.acquire();
			}

			if (orderMutex.hasQueuedThreads()) {
				System.out.println("Status :	Acquired"+ " ||		Reader Thread: "+Thread.currentThread().getName()+" ||		Waiting Threads :	"+orderMutex.getQueueLength());
			}
		} 
		finally {
			orderMutex.release();
			read.release();
		}
		// Read the schedules...
		Thread.sleep(1000);
		// System.out.println("---------------------------------------------");
		System.out.println("Reader :	" +Thread.currentThread().getName()+" ||	Data :	" +schedules.get(flight));
		System.out.println("---------------------------------------------");


		// System.out.println("Schedule for " + flight + " is :"+schedules.get(flight));
		// Reading done
		read.acquire();
		try {
			readCount--;
			if (readCount == 0) {
				write.release();
			}
		} 
		finally {
			read.release();
		}
	}

	public Integer getMapValue(ReadersWriterInterface r, String s) {
		return schedules.get(s);
	}

	public void setMapValue(String flght, Integer time) {
		this.schedules.put(flght, time);
	}

	public void fillMapWithInitialSchedules() {
		this.schedules.put("Flight-1", 1);
	}

	public static void main(String[] args) 
    {
        ReadersWriter f1 = new ReadersWriter();
        Thread[] users = new Thread[15];
		String flight = "Flight-1"; // Can also take the flight as command line argument..

		users[0] = new Thread(new Writers(f1, flight));
		users[1] = new Thread(new Readers(f1, flight));
		users[2] = new Thread(new Writers(f1, flight));
		users[3] = new Thread(new Readers(f1, flight));
		users[4] = new Thread(new Readers(f1, flight));
		users[5] = new Thread(new Readers(f1, flight));
		users[6] = new Thread(new Readers(f1, flight));
		users[7] = new Thread(new Readers(f1, flight));
		users[8] = new Thread(new Writers(f1, flight));
		users[9] = new Thread(new Readers(f1, flight));
		users[10] = new Thread(new Writers(f1, flight));
		users[11] = new Thread(new Writers(f1, flight));
		users[12] = new Thread(new Readers(f1, flight));
		users[13] = new Thread(new Readers(f1, flight));
		users[14] = new Thread(new Readers(f1, flight));

		for (int i = 0; i < 15; i++) {
			users[i].start();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}

		for (int i = 0; i < 15; i++) {
			try {
				users[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Completed Successfully !");
    }

}