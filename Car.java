package ru.geekbrains.Lesson5;

public class Car implements Runnable {
    protected boolean started;
    protected boolean finished;
    private Race race;
    private int speed;
    private String name;
    public String getName () {
        return name;
    }
    public int getSpeed () {
        return speed;
    }

    public Car (int iCarNumber, Race race, int speed) {
        this.started = false;
        this.finished = false;
        this.race = race;
        this.speed = speed;
        this .name = "Участник #" + iCarNumber;
    }

    public void prepare () {
        try {
            System.out.println( this .name + " готовится" );
            Thread.sleep( 500 + ( int )(Math.random() * 800 ));
            System.out.println( this .name + " готов" );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run () {
        for ( int i = 0 ; i < race.getStages().size(); i++) {
            race.getStages().get(i).go( this );
        }
    }
}
