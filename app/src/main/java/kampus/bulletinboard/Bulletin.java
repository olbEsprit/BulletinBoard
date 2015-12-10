package kampus.bulletinboard;

import java.util.Date;

/**
 * Created by Павел on 10.12.2015.
 */
public class Bulletin {
    String title;
    String text;

    public Bulletin(String title,  String text) {
        this.title = title;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

}
