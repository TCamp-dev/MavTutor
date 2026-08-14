package mdi;

import session.Course;
import session.Session;
import people.Person;
import people.Student;
import people.Tutor;
import menu.Menu;
import menu.MenuItem;
import rating.Rateable;
import rating.Rating;
import rating.Comment;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;




public class MavTutor
{

    public MavTutor()
    {
        this.file = null;
        this.scanner = new Scanner(System.in);
        this.courses = new ArrayList<>();
        this.students = new ArrayList<>();
        this.tutors = new ArrayList<>();
        this.sessions = new ArrayList<>();

        System.out.println("\n");
        this.menu = new Menu("\t\t\tMavTutor", "Make a selection");
        menu.addMenuItem(new MenuItem("exit", () -> quit()));
        menu.addMenuItem(new MenuItem("add a course",() -> newCourse()));
        menu.addMenuItem(new MenuItem("add a Tutor",() -> newTutor()));
        menu.addMenuItem(new MenuItem("add a student",() -> newStudent()));
        menu.addMenuItem(new MenuItem("add a session",() -> newSession()));
        menu.addMenuItem(new MenuItem("view added selections",() -> selectView()));
        menu.addMenuItem(new MenuItem("review a student", () -> review(students)));
        menu.addMenuItem(new MenuItem("review a tutor", () -> review(tutors)));
        menu.addMenuItem(new MenuItem("review a session", () -> review(sessions)));
        menu.addMenuItem(new MenuItem("newz",() -> newz()));
        menu.addMenuItem(new MenuItem("save",() -> save()));
        menu.addMenuItem(new MenuItem("save as",() -> saveAs()));
        menu.addMenuItem(new MenuItem("open",() -> open()));
        menu.run(); 
    }

    private void newz()
    {
        courses.clear();
        students.clear();
        tutors.clear();
        sessions.clear();
        file = null;
        menu.result.append("cleared data and started new MavTutor");
    }

    private void save()
    {
        if (file == null)
        {
            file = menu.selectFile("select or create a .MavTutor file (-1 to abort): ", file, null);   
        }
        
        try(PrintStream out = new PrintStream(file))
        {
            out.println(courses.size());
            for (int i = 0; i < courses.size(); i++)
            {
                courses.get(i).save(out);
            }
            out.println(tutors.size());
            for (int i = 0; i < tutors.size(); i++)
            {
                tutors.get(i).save(out);
            }
            out.println(students.size());
            for (int i = 0; i < students.size(); i++)
            {
                students.get(i).save(out);
            }
            out.println(sessions.size());
            for (int i = 0; i < sessions.size(); i++)
            {
                sessions.get(i).save(out);
            }

            menu.result.append("Congrats, your file was saved.");
        }
        catch(Exception e)
        {
            menu.result.append("there was an error saving the file: " + e);
            newz();
        }
    }
    private void saveAs()
    {
        file = null;
        save();
    }

    private void open()
    {
        file = menu.selectFile("Choose a .MavTutor file to load (-1 to abort): ", null, null);
        if (file != null)
        {
            try(Scanner in = new Scanner(file))
            {
                int courseSize = in.nextInt(); in.nextLine();
                for (int i = 0; i < courseSize; i++)
                {
                    courses.add(new Course(in));
                }
                int tutorSize = in.nextInt(); in.nextLine();
                for(int i = 0; i < tutorSize; i++)
                {
                    tutors.add(new Tutor(in));
                }
                int studentSize = in.nextInt(); in.nextLine();
                for(int i = 0; i < studentSize; i++)
                {
                    students.add(new Student(in));
                }
                int sessionSize = in.nextInt(); in.nextLine();
                for(int i = 0; i < sessionSize; i++)
                {
                    sessions.add(new Session(in));
                }

                menu.result.append("file was successfully opened");
            } catch(Exception e)
            {
                menu.result.append("there was an error opening the file: " + e);
            }
        }
    }

    private void review(List<? extends Rateable> list)
    {
        //average rating
        Integer selection = menu.selectItemFromArray("choose who to Review: ", list.toArray());
        if (Double.isNaN(list.get(selection).getAverageRating()))
            System.out.printf("its average Rating is 0\n");
        else
            System.out.printf("its average Rating is " + list.get(selection).getAverageRating() + "\n");

        //choose user and make comment
        System.out.println("you must login for first comment");
        Person user = login();
      
        if (user != null)
        {
            int stars = menu.getInt("how would you rate it out of 5: ");
            String text = menu.getString("any comments about them? ");
            Comment comment = new Comment(text, user, null);
            Rating rating = new Rating(stars, comment);
            list.get(selection).addRating(rating);
        }


        Rating[] rootRating = list.get(selection).getRatings();
        
        int ratingSelect = menu.selectItemFromArray("choose a comment to add to: ", rootRating);
        Comment root = rootRating[ratingSelect].getReview();
        printExpandedComments(root, 0);

        String[] commentChoices = {"reply", "Up", "Down", "Main Menu"};
        int commentChoose = menu.selectItemFromArray("Make a selection: ", commentChoices);
        
        while(commentChoose != 3)
        {
            if (commentChoose == 0)
            {
                if (user == null)
                {
                    System.out.println("you must login to reply");
                    user = login();
                }
                if (user == null) break;
                String reply = menu.getString("write your reply: ");
                root.addReply(reply, user);
            }
            else if(commentChoose == 1)
            {
                if (root.getInReplyTo() != null) root = root.getInReplyTo();
                else System.out.println("this is the top comment");

            }
            else if(commentChoose == 2)
            {
                if(root.numReplies() == 0) menu.result.append("this is last comment");
                else
                {
                    int replyNum = menu.getInt("which comment to go to? ");
                    root = root.getReply(replyNum);
                }
            }

            printExpandedComments(root, 0);
            commentChoose = menu.selectItemFromArray("Make a selection: ", commentChoices);
        }
        if(commentChoose == 3) menu.result.append("returned to main menu");
       
    }

    private Person login()
    {
        String[] selections= {"student", "tutor"};

        Integer selection = menu.selectItemFromArray("are you a student or a tutor(q to skip login): ", selections, "q");
        if (selection == null) return null;
        if(selection == 0)
        {
            int student =  menu.selectItemFromArray("choose a user: ", students.toArray());
            return students.get(student);
        }
        else if (selection == 1)
        {
            int tutor =  menu.selectItemFromArray("choose a user: ", tutors.toArray());
            return tutors.get(tutor);
        }
        else return null;

    }
    
    private void quit()
    {
        menu.result = null;
    }

    private void selectView()
    {
        String [] selections = {"Courses", "Tutors", "Students", "Sessions"};
        int selection = menu.selectItemFromArray("choose which view you want (or press q to exit): ", selections, "q");
        if (selection == 0)
            System.out.println(menu.listToString("Course view\n\n", courses, '-'));
        else if (selection == 1)
            System.out.println(menu.listToString("Tutor view\n\n", tutors, '-'));
        else if (selection == 2)
            System.out.println(menu.listToString("Student view\n\n", students, '-'));
        else 
            System.out.println(menu.listToString("Session view\n\n", sessions, '-'));
    }

    private static void printIndented(String multiline, int level) 
    {
        String[] strings = multiline.split("\n");
        for(String s : strings) 
            System.out.println("  ".repeat(level) + s);
    }

    private static void printExpandedComments(Comment c, int level) 
    {
        printIndented(c.toString(), level);
        System.out.println("\n");
        for(int i=0; i<c.numReplies(); ++i)
            printExpandedComments(c.getReply(i), level+1);
    }

    private void newCourse()
    {
        
        
        // String dept = menu.getString("enter dept: ");
        // int numb = menu.getInt("enter dept number: ");
        System.out.print("enter course (ex: cse 1325): ");
        Course addedCourse = new Course(scanner.next(), scanner.nextInt());
        if (courses.indexOf(addedCourse) == -1)
        {
            courses.add(addedCourse);
        }
    }

    private void newTutor()
    {
        String name = menu.getString("enter Tutor's name: ");
        String email = menu.getString("enter tutor's email: ");
        int ssn = menu.getInt("enter tutor's ssn: ");
        String bio = menu.getString("enter tutor's bio (or enter q to leave blank): ", "q");
        
        int selection = menu.selectItemFromArray("select the course they tutor: ", courses.toArray());
        Course course = courses.get(selection);
        Tutor newTutor = new Tutor(name, email, ssn, bio, course);
        tutors.add(newTutor);
    }

    private void newStudent()
    {
        String name = menu.getString("enter student's name: ");
        String email = menu.getString("enter student's email: ");
        Student newStudent = new Student(name, email);
        boolean exit = false;
        while(!exit && courses.size() != 1)
        {
            Integer studentCourse = menu.selectItemFromArray("select all classes they need tutoring in (or enter q to exit): ", courses.toArray(), "q");
            if(studentCourse == null)
            {
                exit = true;
            } 
            else 
                newStudent.addCourse(courses.get((int)studentCourse));
        }
        if (courses.size() == 1)
        {
            newStudent.addCourse(courses.get(0));
        }

        students.add(newStudent);
    }

    private void newSession()
    {
        int courseSelect = menu.selectItemFromArray("choose the course for the session: ", courses.toArray());
        System.out.println();
        int tutorSelect =  menu.selectItemFromArray("choose the tutor for the session: ", tutors.toArray());
        System.out.println();
        Session session = new Session(courses.get(courseSelect), tutors.get(tutorSelect));
        String date = menu.getString("enter the date of the session (yyyy/mm/dd): ");
        String startTime = menu.getString("what time will the session start (hh:mm): ");
        long duration = menu.getInt("how long will the session be in minutes: ");
        session.setSchedule(date, startTime, duration);
       
        boolean exit = false;
        while(!exit && students.size() != 1)
        {
            System.out.println();
            Integer studentSession = menu.selectItemFromArray("select all students attending this session (or enter q to exit): ", students.toArray(), "q");
            if(studentSession == null)
            {
                exit = true;
            } 
            else 
                session.addStudent(students.get((int)studentSession));
        }
        if (students.size() == 1)
        {
            session.addStudent(students.get(0));
        }

        sessions.add(session);

    }

    public static void main(String[] args)
    {
        try
        {
            MavTutor run = new MavTutor();
        } catch (Exception e)
        {
            System.err.print(e);
        }
       

    }

    @Override
    public String toString()
    {
        return courses.toString() + students.toString() + sessions.toString() + tutors.toString();
    }


    private Menu menu;
    private List view;
    private ArrayList<Course> courses;
    private ArrayList<Student> students;
    private ArrayList<Tutor> tutors;
    private ArrayList<Session> sessions;
    private Scanner scanner;
    private File file;



}