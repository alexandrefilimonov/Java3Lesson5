package ru.geekbrains.Lesson5;
import java.lang.String;
import java.util.concurrent.*;

public class Main {
	public static final int CARS_COUNT = 4 ;
    public static boolean bRaceStarted = false;
    public static boolean bRaceFinished = false;
    public static Semaphore smp = null;

    public static void main(String[] args) {
        smp = new Semaphore( (int)(CARS_COUNT/2) );
        bRaceStarted = false;
	    System.out.println( "ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!" );
		Race race = new Race( new Road( 60 ), new Tunnel(), new Road( 40 ));

		CyclicBarrier cb = new CyclicBarrier( CARS_COUNT );
		Car[] cars = new Car[CARS_COUNT];
		for ( int i = 0 ; i < CARS_COUNT ; i++) {
			final int w = i;
			new Thread (() -> {
				try {
					cars[w] = new Car((w+1), race, 20 + ( int ) (Math.random() * 10 ));
                    cars[w].prepare();
                    cars[w].started = true;
                    try {
                        boolean bAllStarted = true;
                        for ( int j = 0 ; j < CARS_COUNT ; j++) {
                            if (!cars[j].started) bAllStarted = false;
                        }
                        if (!bRaceStarted && bAllStarted) {
                            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
                            bRaceStarted = true;
                        }
                    }
                    catch(Exception ex) {
                    }
                    cb.await();
                    cars[w].run();
                    cars[w].finished = true;
                    boolean bAllFinished = true;
                    for ( int j = 0 ; j < CARS_COUNT ; j++) {
                        if (!cars[j].finished) bAllFinished = false;
                    }
                    try {
                        if (!bRaceFinished && bAllFinished) {
                            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
                            bRaceFinished = true;
                        }
                    }
                    catch(Exception ex) {
                    }
                } catch (Exception e) {
					e.printStackTrace();
				}
			}).start();
			//new Thread(cars[i]).start();
		}
        //System.out.println("Array");
        //Array1and4 aa = new Array1and4(3);
    }
}
