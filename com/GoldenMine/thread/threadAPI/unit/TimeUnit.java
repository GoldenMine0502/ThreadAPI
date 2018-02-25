package GoldenMine.thread.threadAPI.unit;

public enum TimeUnit {
    MS(n -> 1F/(n/1000F)),
    FPS(n -> n),
    NS(n -> Math.round(1/(n/1000000000F))),
    SECOND(n -> 1F/n),
    MINUTE(n -> 1F/(n*60F)),
    HOUR(n -> 1F/(n*60F*60F)),
    DAY(n -> 1F/(n*60F*60F*24F));

    private ITimeUnit unit;

    TimeUnit(ITimeUnit unit) {
        this.unit = unit;
    }

    public double convert(double n) {
        return unit.calculate(n);
    }

    //public abstract double calculate(double n);

    private interface ITimeUnit {
        double calculate(double n); // n의 단위를 FPS로 변환하기
    }
}
