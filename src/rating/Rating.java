package rating;

public class Rating
{
    private int stars;
    private Comment review;

    public Rating(int stars, Comment review)
    {
        if (stars < 1 || stars > 5) throw new IllegalArgumentException("rating has to be between 1 and 5");
        this.stars = stars;
        
        this.review = review;
        
    }

    public int getStars()
    {
        return stars;
    }
    public Comment getReview()
    {
        return review;
    }

    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        int full = 0x2605;
        int empty =  0x2606;
    
        for (int i = 1; i <= 5; i++)
        {
            if (i <= stars) 
            {
                s.append((char) full);
            }
            else 
            {
                s.append((char) empty);
            }
            
        } 
       
        
        return s.toString();
    }
}