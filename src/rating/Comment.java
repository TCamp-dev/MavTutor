package rating;

import people.Person;
import java.util.ArrayList;
public class Comment 
{
    private String text;
    private Person author;
    private Comment inReplyTo;
    private ArrayList<Comment> replies;

    public Comment(String text, Person author, Comment inReplyTo)
    {
        if (text == null || text.length() == 0 || author == null)
        throw new IllegalArgumentException("author or text is null or empty");
        this.text = text;
        this.author = author;
        this.inReplyTo = inReplyTo;
        this.replies = new ArrayList<>();
    }

    public void addReply(String text, Person author)
    {
        if (text == null || text.length() == 0 || author == null)
        throw new IllegalArgumentException("author or text is null or empty");
        Comment c = new Comment(text, author, this);
        replies.add(c);
    }
    public int numReplies()
    {
        return replies.size();
    }

    public String getText()
    {
        return text;
    }

    public Comment getReply(int index)
    {
        return replies.get(index);
    }

    public Person getAuthor()
    {
        return author;
    }

    public Comment getInReplyTo()
    {
        return inReplyTo;
    }

    @Override 
    public String toString()
    {
        
        StringBuilder sb = new StringBuilder("Comment by " + getAuthor() + " ");
        if (getInReplyTo() != null) sb.append(" in reply to " + getInReplyTo().getAuthor());
    
        if (numReplies() > 0)
        {
            sb.append("\nReplies:   ");
            for (int i = 0;i < numReplies(); i++)
            {
                sb.append("(" + i + ") " + getReply(i).getAuthor() + " ");
            }
        }
        sb.append("\n" + getText());
        
        return sb.toString();   
    }
}