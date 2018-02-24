package com.GoldenMine.thread.threadAPI.pastVersion.Unit;

public enum TimeUnitFactory {
    MS(n -> 1F/(n/1000F)),
    FPS(n -> n),
    NS(n -> Math.round(1/(n/1000000000F))),
    SECOND(n -> 1F/n),
    MINUTE(n -> 1F/(n*60F)),
    HOUR(n -> 1F/(n*60F*60F)),
    DAY(n -> 1F/(n*60F*60F*24F));

    private TimeUnit unit;

    TimeUnitFactory(TimeUnit unit) {
        this.unit = unit;
    }

    public double convert(double n) {
        return unit.calculate(n);
    }

    public interface TimeUnit {
        double calculate(double n); // n의 단위를 FPS로 변환하기
    }
}
