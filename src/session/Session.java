package session;

import people.Tutor;
import people.Student;
import rating.Rateable;
import rating.Rating;

import java.util.ArrayList;
import java.util.Scanner;

import java.io.PrintStream;

/**
 * models a tutoring session
 * 
 * @author      Terry Camp
 * @version     1.0 
 * @since       1.0
 */
public class Session implements Rateable
{
    /**
     * Creates a tutoring session.
     * 
     * @param course    the course that the session is about
     * @param tutor     the tutor for the course
     */
    public Session (Course course, Tutor tutor)
    {
        this.course = course;
        this.tutor = tutor;
        this.students = new ArrayList<>();
    }

    public Session(Scanner in)
    {
        this.ratings = new ArrayList<>();
        this.course = new Course(in);
        this.dates = new DateRange(in);
        this.tutor = new Tutor(in);
        this.students = new ArrayList<>();
        int size  = in.nextInt(); in.nextLine();
        for (int i = 0; i < size; i++)
        {
            this.students.add(new Student(in));
        }

    }

    public void save(PrintStream out)
    {
        course.save(out);
        dates.save(out);
        tutor.save(out);
        out.println(students.size());
        for (int i = 0; i < students.size(); i++)
        {
            students.get(i).save(out);
        }
    }

    /**
     * sets the date and how long a tutoring session is
     * 
     * @param date      the date of session yyyy/mm/dd
     * @param startTime the start time hh:mm
     * @param duration  the duration of session in minutes
     * 
     */
    public void setSchedule(String date, String startTime, long duration)
    {
        this.dates = new DateRange(date, startTime, duration);
    }

    /**
     * adds a student to the tutoring session 
     * 
     * @param student   the student to add 
     */
    public void addStudent(Student student)
    {
        students.add(student);
    }

    @Override
    public void addRating(Rating rating)
    {
        ratings.add(rating);
    }
    @Override
    public double getAverageRating()
    {
        double stars = 0;
        for(int i = 0; i < ratings.size(); i++)
        {
            stars += ratings.get(i).getStars();
        }

        return stars /= ratings.size();
    }

    @Override
    public Rating[] getRatings()
    {
        return ratings.toArray(new Rating[ratings.size()]);
    }

    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append("session on " + course + " at " + dates + "\n");
        s.append("Tutor: " + tutor + "\n Students: ");

        for (int i = 0; i < students.size(); i++)
        {
            s.append(students.get(i) + "\n\t   ");
        }

        return s.toString();
    }
    private Course course;
    private DateRange dates;
    private Tutor tutor;
    private ArrayList<Student> students;
    private ArrayList<Rating> ratings;

}
