package databinding.android.vogella.com.bikecounter;

import java.io.Serializable;

public class Measurement implements Serializable {
    Integer numberOfRevolutions;
    Integer wheelEventTime;

    public Measurement(Integer numberOfRevolutions, Integer wheelEventTime) {
        this.numberOfRevolutions = numberOfRevolutions;
        this.wheelEventTime = wheelEventTime;
    }

    @Override
    public String toString() {
        return numberOfRevolutions.toString() + " " + wheelEventTime.toString();
    }

    public static Measurement fromString(String s) {
        String numberOfRevString, wheelEventTimeString;
        numberOfRevString = s.split(" ")[0];
        wheelEventTimeString = s.split(" ")[1];
        return new Measurement(Integer.parseInt(numberOfRevString), Integer.parseInt(wheelEventTimeString));
    }

    public double getSpeed() {
        final double speed = numberOfRevolutions*(2*Math.PI*0.3)*1000/wheelEventTime;
        return Math.floor(speed*100)/100;
    }
}