public class Timeslot {
    public char day;
    public int hours1;
    public int hours2;
    public int minutes1;
    public int minutes2;

    public Timeslot(char day, int hours1, int minutes1, int hours2, int minutes2) {
        this.day = day;
        this.hours1 = hours1;
        this.minutes1 = minutes1;
        this.hours2 = hours2;
        this.minutes2 = minutes2;
    }

    public Timeslot(String str) {
        day = str.charAt(0);
        hours1 = Integer.parseInt(str.substring(2, 4));
        minutes1 = Integer.parseInt(str.substring(5, 7));
        hours2 = Integer.parseInt(str.substring(8, 10));
        minutes2 = Integer.parseInt(str.substring(11, 13));
        if (str.substring(13).equals("PM")) {
            if (hours1 != 12) hours1 += 12;
            if (hours2 != 12) hours2 += 12;
        }
    }

    public boolean isin(DateTime dt) { 
        if (dt.day != day) return false;
        if (dt.hours < hours1 || dt.hours > hours2) return false;

        if (dt.hours == hours1) {
            return dt.minutes < minutes1;
        }
        if (dt.hours == hours2) {
            return dt.minutes > minutes2;
        }
        return true;
    }

    @Override
    public String toString() {
        String time = day + " ";

        if (hours1 < 10) time = "0" + hours1;
        else time = time + hours1;

        if (minutes1 < 10) time = time + ":0" + minutes1;
        else time = time + ":" + minutes1;

        return time;
    }
}
