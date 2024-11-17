import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final String FILEPATH = "input.txt";
        ArrayList<String> fileData = getFileData(FILEPATH);
        ArrayList<Timeslot> timeslotData = toTimeslots(fileData);
        ArrayList<ArrayList<Integer>> schedule = createNewSchedule();
        schedule = addTimes(schedule, timeslotData);

        ArrayList<Timeslot> bestTimes = getBestTimes(schedule.get(1), 'M');

        for (Timeslot ts : bestTimes) {
            System.out.println(ts);
        }

//        for (ArrayList<Integer> a : schedule) {
//            System.out.println(a);
//        }
    }

    // Gets schedule times from txt and loads into array
    public static ArrayList<String> getFileData(String filename) {
        File file = new File(filename);
        ArrayList<String> fileData = new ArrayList<>();
        try {
            Scanner s = new Scanner(file);
            while (s.hasNextLine()) {
                String line = s.nextLine();
                int i = 0;
                while (line.charAt(i) != ' ') {
                    fileData.add(removeLetters(line, line.charAt(i)));
                    i++;
                }
            }
        } catch (IOException e) {
            System.err.println("File not found: " + filename);
        }
        return fileData;
    }

    public static String removeLetters(String line, char x) {
        Scanner s = new Scanner(line);
        String token = s.next();
        token = s.next();
        return x + " " + token;
    }

    // String to timeslot object
    public static ArrayList<Timeslot> toTimeslots(ArrayList<String> fileData) {
        ArrayList<Timeslot> timeslotData = new ArrayList<>();
        for (String s : fileData) {
            timeslotData.add(new Timeslot(s));
        }
        return timeslotData;
    }

    // Create schedule [Sunday - Saturday][6:00 - 0:00] (broken up every 15 min)]
    public static ArrayList<ArrayList<Integer>> createNewSchedule() {
        ArrayList<ArrayList<Integer>> schedule = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            ArrayList<Integer> times = new ArrayList<>();
            for (int j = 0; j < 73; j++) {
                times.add(0);
            }
            schedule.add(times);
        }
        return schedule;
    }

    // Add timeslots to schedule
    public static ArrayList<ArrayList<Integer>> addTimes(ArrayList<ArrayList<Integer>> schedule, ArrayList<Timeslot> timeslotData) {
        for (Timeslot ts : timeslotData) {
            for (int i = getIndex(ts.hours1, ts.minutes1); i <= getIndex(ts.hours2, ts.minutes2); i++) {
                switch (ts.day) {
                    case 'M' -> {
                        int temp = schedule.get(1).get(i);
                        schedule.get(1).set(i, temp + 1);
                    }
                    case 'T' -> {
                        int temp = schedule.get(2).get(i);
                        schedule.get(2).set(i, temp + 1);
                    }
                    case 'W' -> {
                        int temp = schedule.get(3).get(i);
                        schedule.get(3).set(i, temp + 1);
                    }
                    case 'R' -> {
                        int temp = schedule.get(4).get(i);
                        schedule.get(4).set(i, temp + 1);
                    }
                    case 'F' -> {
                        int temp = schedule.get(5).get(i);
                        schedule.get(5).set(i, temp + 1);
                    }
                    default -> System.err.println("Invalid day: " + ts.day);
                }
            }
        }
        return schedule;
    }

    public static int getIndex(int hours, int minutes) {
        int result = (hours - 6) * 4;
        result += minutes / 15;
        return result;
    }

    public static ArrayList<Timeslot> getBestTimes(ArrayList<Integer> a, char day) {
        ArrayList<Timeslot> bestTimeslots = new ArrayList<>();

        if (a.size() < 4) {
            return bestTimeslots;
        } else if (a.size() == 4) {
            bestTimeslots.add(new Timeslot(day + " 06:00-07:00"));
            return bestTimeslots;
        }

        int window = a.get(0) + a.get(1) + a.get(2) + a.get(3);
        int min = window;
        for (int i = 1; i < a.size() - 3; i++) {
            window -= a.get(i - 1);
            window += a.get(i + 3);
            if (window < min) {
                min = window;
            }
        }

        window = a.get(0) + a.get(1) + a.get(2) + a.get(3);
        if (window == min) {
            bestTimeslots.add(new Timeslot(day + " 06:00-07:00"));
        }
        for (int i = 1; i < a.size() - 3; i++) {
            window -= a.get(i - 1);
            window += a.get(i + 3);
            if (window == min) {
                String temp = day + " " + getTime(i);
                bestTimeslots.add(new Timeslot(temp));
            }
        }
        bestTimeslots.remove(bestTimeslots.size() - 1);
        return bestTimeslots;
    }

    public static String getTime(int index) {
        String str = "";
        int hours = index / 4 + 6;
        int minutes;
        switch (index % 4) {
            case 1 -> minutes = 15;
            case 2 -> minutes = 30;
            case 3 -> minutes = 45;
            default -> minutes = 0;
        }
        if (hours < 10) {
            str += "0" + hours;
        } else {
            str += hours;
        }
        if (minutes < 10) {
            str += ":0" + minutes;
        } else {
            str += ":" + minutes;
        }
        str += "-";

        hours++;
        if (hours < 10) {
            str += "0" + hours;
        } else {
            str += hours;
        }
        if (minutes < 10) {
            str += ":0" + minutes;
        } else {
            str += ":" + minutes;
        }
        return str;
    }
}