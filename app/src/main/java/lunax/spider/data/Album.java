package lunax.spider.data;

/**
 * Created by Bamboo on 3/12/2017.
 */

public class Album {
    private String imgUrl;
    private String title;
    private String typeUrl;

    public Album(String imgUrl, String title, String typeUrl) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.typeUrl = typeUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getTypeUrl() {
        return typeUrl;
    }
}
