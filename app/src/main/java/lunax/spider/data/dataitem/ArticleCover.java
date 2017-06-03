package lunax.spider.data.dataitem;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Bamboo on 6/1/2017.
 */

@Entity
public class ArticleCover {

    @Id(autoincrement = true)
    private long id;

    private String title;

    private String wordCount;
    private String intro;
    private String hotMark;


    @Keep
    public ArticleCover() {

    }

    @Keep
    public ArticleCover(long id, String wordCount, String intro, String hotMark) {
        this.id = id;
        this.wordCount = wordCount;
        this.intro = intro;
        this.hotMark = hotMark;
    }

    public ArticleCover(String wordCount, String intro, String hotMark) {
        this.wordCount = wordCount;
        this.intro = intro;
        this.hotMark = hotMark;
    }

    @Generated(hash = 569491223)
    public ArticleCover(long id, String title, String wordCount, String intro,
            String hotMark) {
        this.id = id;
        this.title = title;
        this.wordCount = wordCount;
        this.intro = intro;
        this.hotMark = hotMark;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWordCount() {
        return wordCount;
    }

    public void setWordCount(String wordCount) {
        this.wordCount = wordCount;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getHotMark() {
        return hotMark;
    }

    public void setHotMark(String hotMark) {
        this.hotMark = hotMark;
    }
}