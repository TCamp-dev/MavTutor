package people;

import session.Course;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.PrintStream;

public class Student extends Person
{

    public Student(String name, String email)
    {
        super(name, email);
        
        studentID = nextStudentID;
        nextStudentID++;
        this.courses = new ArrayList<>();
    }

    public Student(Scanner in)
    {
        super(in);
        this.studentID = in.nextInt(); in.nextLine();
        this.nextStudentID = in.nextInt(); in.nextLine();
        int size = in.nextInt(); in.nextLine();
        this.courses = new ArrayList<>();
        for (int i = 0; i < size; i++)
        {
            this.courses.add(new Course(in));
        }
    }

    public void save(PrintStream out)
    {
        super.save(out);
        out.println(studentID);
        out.println(nextStudentID);
        out.println(courses.size());
        for (int i = 0; i < courses.size(); i++)
        {
            courses.get(i).save(out);
        }
    }

    public void addCourse(Course course)
    {
        courses.add(course);
    }

    public Course[] getCourses()
    {
        return courses.toArray(new Course[0]);
    }

    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder(super.toString());
        int idx = s.indexOf(")");
        s.replace(idx, idx +1," #" + studentID + ")");
        return s.toString();
    }
    private static int nextStudentID = 0;
    private int studentID;
    private List<Course> courses;

}