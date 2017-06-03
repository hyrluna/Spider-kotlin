package lunax.spider.data.dataitem;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by G1494458 on 2017/6/2.
 */

@Entity(indexes = {@Index(value = "text, date desc", unique = true)})
public class Note {
    @Id
    private Long id;

    @NotNull
    private String text;
    private String comment;
    private java.util.Date date;

//    @Convert(converter = NoteTypeConverter.class, columnType = String.class)
//    private NoteType type;

    @Generated(hash = 1272611929)
    public Note() {
    }

    public Note(Long id) {
        this.id = id;
    }

    @Generated(hash = 1028896452)
    public Note(Long id, @NotNull String text, String comment, java.util.Date date) {
        this.id = id;
        this.text = text;
        this.comment = comment;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public String getText() {
        return text;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setText(@NotNull String text) {
        this.text = text;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }
//
//    public NoteType getType() {
//        return type;
//    }
//
//    public void setType(NoteType type) {
//        this.type = type;
//    }
}
