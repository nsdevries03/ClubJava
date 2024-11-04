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
        ArrayList<ArrayList<Integer>> newSchedule = addTimes(schedule, timeslotData);

        for (ArrayList<Integer> a : newSchedule) {
            System.out.println(a);
        }
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

    // Create schedule [Sunday - Saturday][6:00 - 0:00] (broken up every 30 min)]
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

    public static ArrayList<ArrayList<Integer>> addTimes(ArrayList<ArrayList<Integer>> schedule, ArrayList<Timeslot> timeslotData) {
        for (Timeslot ts : timeslotData) {
            for (int i = getVal(ts.hours1, ts.minutes1); i <= getVal(ts.hours2, ts.minutes2); i++) {
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

    public static int getVal(int hours, int minutes) {
        int result = (hours - 6) * 4;
        result += minutes / 15;
        return result;
    }
}