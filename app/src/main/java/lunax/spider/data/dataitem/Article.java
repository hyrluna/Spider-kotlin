package lunax.spider.data.dataitem;

/**
 * Created by G1494458 on 2017/6/1.
 */

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Generated;

/**
 * var avatar: String,
 var title: String,
 var subtitle: String,
 var author: String,
 var type: String,
 var rating: String,
 var description: String,
 var articleUrl: String
 */

@Entity
public class Article implements Parcelable {

    @Id(autoincrement = true)
    private long id;

    private String kind;

    private String avatar;
    private String title;
    private String subtitle;
    private String author;
    private String type;
    private String rating;
    private String description;
    private String articleUrl;

    @Keep
    public Article() {
    }

    @Keep
    public Article(long id,
                   String avatar,
                   String title,
                   String subtitle,
                   String author,
                   String type,
                   String rating,
                   String description,
                   String articleUrl) {
        this.id = id;
        this.avatar = avatar;
        this.title = title;
        this.subtitle = subtitle;
        this.author = author;
        this.type = type;
        this.rating = rating;
        this.description = description;
        this.articleUrl = articleUrl;
    }

    public Article(String avatar, String title, String subtitle, String author, String type, String rating, String description, String articleUrl) {
        this.avatar = avatar;
        this.title = title;
        this.subtitle = subtitle;
        this.author = author;
        this.type = type;
        this.rating = rating;
        this.description = description;
        this.articleUrl = articleUrl;
    }

    @Generated(hash = 1041464052)
    public Article(long id, String kind, String avatar, String title, String subtitle, String author, String type, String rating, String description,
            String articleUrl) {
        this.id = id;
        this.kind = kind;
        this.avatar = avatar;
        this.title = title;
        this.subtitle = subtitle;
        this.author = author;
        this.type = type;
        this.rating = rating;
        this.description = description;
        this.articleUrl = articleUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(avatar);
        dest.writeString(title);
        dest.writeString(subtitle);
        dest.writeString(author);
        dest.writeString(type);
        dest.writeString(rating);
        dest.writeString(description);
        dest.writeString(articleUrl);
    }

    public static final Parcelable.Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel source) {
            Article article = new Article();
            article.avatar = source.readString();
            article.title = source.readString();
            article.subtitle = source.readString();
            article.author = source.readString();
            article.type = source.readString();
            article.rating = source.readString();
            article.description = source.readString();
            article.articleUrl = source.readString();
            return article;
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }
}
