package test;

import rating.Rating;
import rating.Comment;
import people.Person;

public class TestRating
{
    public static void main(String[] args) 
    {
        Person person = new Person("Terry", "tic8675@mavs.uta.edu");
        Comment review = new Comment("they're great", person, null); 


        //test 1        
        for(int i = 1; i <= 5; i++)
        {


            Rating rating = new Rating(i, review);

            StringBuilder s = new StringBuilder();
            int full = 0x2605;
            int empty =  0x2606;
        
            for (int j = 1; j <= 5; j++)
            {
                if (j <= i) 
                {
                    s.append((char) full);
                }
                else 
                {
                    s.append((char) empty);
                }
                
            } 

            if(rating.getStars() != i)
            {
                System.err.println("Fail, stars are not correct: i = " + i + " getStars = " + rating.getStars());
                
            } 
            if(!s.toString().equals(rating.toString()))
            {
                System.err.println("Fail, rating toString is not correct");
            }
        }

        //test 2
        Rating r2 = new Rating(2, review);
        if (!r2.getReview().equals(review))
        System.err.println("Fail, review not correct");
        
    }
    
}