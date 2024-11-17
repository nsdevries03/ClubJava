public class DateTime {
    public int hours;
    public int minutes;
    public char day;

    public DateTime(int hours, int minutes, char day) {
        this.hours = hours;
        this.minutes = minutes;
        this.day = day;
    }

    public DateTime(String str) {
        day = str.charAt(0);
        hours = Integer.parseInt(str.substring(2, 4));
        minutes = Integer.parseInt(str.substring(5, 7));
    }

    @Override
    public String toString() {
        String time = day + " ";
        if (hours < 10) {
            time += "0" + hours;
        } else {
            time += hours;
        }

        if (minutes < 10) {
            time += ":0" + minutes;
        } else {
            time += ":" + minutes;
        }

        return time;
    }
}
