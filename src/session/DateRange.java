package session;

import java.util.Scanner;
import java.io.PrintStream;

/**
 * models a date range for a session
 * 
 * @author      Terry Camp
 * @version     1.0
 * @since       1.0
 */
public class DateRange
{
    /**
     * creates a dateRange using endTime
     * 
     * @param date      the date in (yyyy/mm,dd)
     * @param startTime the the start time in (hh,mm) using 24 hours
     * @param endTime the end time in (hh,mm) using 24 hours 
     */
    public DateRange(String date, String startTime, String endTime)
    {
        if (date != null && date.length() != 0)
            this.date = date;
        if (startTime != null && startTime.length() != 0)
            this.startTime = startTime;
        if (endTime != null && endTime.length() != 0)
            this.endTime = endTime;
    }

    /**
     * creates a dateRange using the duration in minutes
     * 
     * @param date      the date in (yyyy/mm,dd)
     * @param startTime the the start time in (hh,mm) using 24 hours
     * @param duration the end time in minutes 
     */
    public DateRange(String date, String startTime, long duration)
    {
        String[] time = startTime.split(":");
        int hour = Integer.parseInt(time[0]);
        int min = Integer.parseInt(time[1]);
        long totalMinutes = hour * 60L + min + duration;
        hour = (int)(totalMinutes / 60);
        min = (int)(totalMinutes % 60);
        String endtime = hour + ":" + min;

        this.date = date;
        this.startTime = startTime;
        this.endTime = endtime;
    }

    public DateRange(Scanner in)
    {
        this.date = in.nextLine();
        this.startTime = in.nextLine();
        this.endTime = in.nextLine();
    }

    public void save(PrintStream out)
    {
        out.println(date);
        out.println(startTime);
        out.println(endTime);
    }

    /**
     * calculate the duration 
     * 
     */
    public long duration()
    {
        String[] startSplit = startTime.split(":");
        String[] endSplit = endTime.split(":");

        int hours = Integer.parseInt(endSplit[0]) - Integer.parseInt(startSplit[0]);
        int min =  Integer.parseInt(endSplit[1]) - Integer.parseInt(startSplit[1]);

        return (long)(hours*60) + min;
    }

    @Override
    public String toString()
    {
        return date + " " + startTime + " - " + endTime + " (" + duration() + " minutes)";
    }
    private String startTime;
    private String endTime;
    private String date;
}