package kampus.bulletinboard;

import java.util.Date;

/**
 * Created by Павел on 10.12.2015.
 */
public class Bulletin {
    String title;
    String text;
    int id;

    public Bulletin(String title,  String text, int id) {
        this.title = title;
        this.text = text;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public int getId() {
        return id;
    }
}
