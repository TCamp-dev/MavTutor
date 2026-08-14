package session;


/**
 * Creates a new IllegalArgumentException for illegal course values
 * 
 * @author      Terry Camp
 * @version     1.0
 * @since       1.0 
 */
public class InvalidCourseException extends IllegalArgumentException
{
    /**
     * creates invalid course exception if department name is not valid
     * 
     * @param dept      the department name
     * @since           1.0
     */
    public InvalidCourseException(String dept)
    {
        super("invalid dept in new course: " + dept);
    }

    /**
     * creates invalid course exception if department number is not valid
     * 
     * @param dept      the department name
     * @param number    the department number
     * @since           1.0
     */
    public InvalidCourseException(String dept, int number)
    {
        super("invalid course number in new course: " + dept + number);
    } 
}