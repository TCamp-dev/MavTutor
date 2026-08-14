package session;

import java.util.Objects;
import java.util.Scanner;

import java.io.PrintStream;

 /**
  * Creates a course with department and number
  *  
  * @author      Terry Camp
  * @version     1.0
  * @since       1.0 
  */
public class Course
{

    /**
     * creates course instance
     * 
     * @param dept    the course department
     * @param number  the department number
     * @since 1.0
     */
    public Course(String dept, int number)
    {
        if (dept.length() < 3 || dept.length() > 4)
            throw new InvalidCourseException(dept);

         this.dept = dept;

        if(number < 1000 || number > 9999)
            throw new InvalidCourseException(dept, number);
    
        this.number = number;

    }

    public Course(Scanner in)
    {
        this.dept = in.nextLine();
        this.number = in.nextInt(); in.nextLine();
    } 

    public void save(PrintStream out)
    {
        out.println(dept);
        out.println(number);
    }

    @Override
    public String toString()
    {
        return dept + " " + number;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (o.getClass() != getClass()) return false;
        Course c = (Course) o;
        return (c.dept.equals(dept) && c.number == number);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(dept, number);
    }
    private String dept;
    private int number;

}