public class DateTime {
    public int hours;
    public int minutes;
    public char day;

    public DateTime(int hours, int minutes, char day) {
        this.hours = hours;
        this.minutes = minutes;
        this.day = day;
    }

    @Override
    public String toString() {
        String time = day + " ";
        if (hours < 10) {
            time = "0" + hours;
        } else {
            time = time + hours;
        }

        if (minutes < 10) {
            time = time + ":0" + minutes;
        } else {
            time = time + ":" + minutes;
        }

        return time;
    }
}
