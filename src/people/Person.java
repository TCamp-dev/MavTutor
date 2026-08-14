package people;

import rating.Rateable;
import rating.Rating;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.io.PrintStream;

public class Person implements Rateable
{


    public Person(String name, String email)
    {
        if (name == null || name.length() == 0 || email == null|| email.length() == 0)
            throw new IllegalArgumentException("name or email is empty");
        this.name = name;
        this.email = email;
    }

    public Person(Scanner in)
    {
        this.name = in.nextLine();
        this.email = in.nextLine();
    }

    public void save(PrintStream out)
    {
        out.println(name);
        out.println(email);
    }

    public String getName()
    {
        return name;
    }
    public String getEmail()
    {
        return email;
    }
    @Override
    public void addRating(Rating rating)
    {   
        ratings.add(rating);
    }

    @Override 
    public double getAverageRating()
    {   
        double average = 0;
        for (Rating r : ratings)
        {
            average += (double)r.getStars();
        }

        return average/(double)ratings.size();
    }

    @Override 
    public String toString()
    {
        return name + " (" + email + ")"; 
    }

    @Override
    public Rating[] getRatings()
    {
        return ratings.toArray(new Rating[ratings.size()]);
    }

    @Override 
    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (o == null || o.getClass() != getClass()) return false;
        Person p = (Person) o;
        return (p.getName().equals(name) && p.getEmail().equals(email));

    }

    @Override 
    public int hashCode()
    {
        return Objects.hash(name, email);
    }

    protected String name;
    protected String email;
    private ArrayList<Rating> ratings = new ArrayList<>();
}