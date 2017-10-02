package starsoft.litrail_android.Model;

/**
 * Created by Ramu on 02/10/2017.
 */

public class Message {

    public final String title;
    public final String date;
    public final String content;

    public Message(String title, String content, String date) {
        this.title = title;
        this.date = date;
        this.content = content;
    }

}