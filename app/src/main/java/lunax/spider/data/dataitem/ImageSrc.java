package lunax.spider.data.dataitem;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Bamboo on 5/31/2017.
 */

@Entity
public class ImageSrc {

    @Id(autoincrement = true)
    private long id;

    private String size;
    private String url;
    private long wallpaperId;

    @Keep
    public ImageSrc() {
    }

    @Keep
    public ImageSrc(String size, String url, long wallpaperId) {
        this.size = size;
        this.url = url;
        this.wallpaperId = wallpaperId;
    }

    public ImageSrc(String size, String url) {
        this.size = size;
        this.url = url;
    }

    @Generated(hash = 1586207397)
    public ImageSrc(long id, String size, String url, long wallpaperId) {
        this.id = id;
        this.size = size;
        this.url = url;
        this.wallpaperId = wallpaperId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getWallpaperId() {
        return wallpaperId;
    }

    public void setWallpaperId(long wallpaperId) {
        this.wallpaperId = wallpaperId;
    }
}