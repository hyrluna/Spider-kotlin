package lunax.spider.data.dataitem;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.DaoException;

/**
 * Created by Bamboo on 3/18/2017.
 */

@Entity
public class Wallpaper {

    @Id(autoincrement = true)
    private long id;

    private String href;
    private String imgUrl;

    private long albumId;

    @ToMany(referencedJoinProperty = "wallpaperId")
    private List<ImageSrc> downloadUrls;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1591992433)
    private transient WallpaperDao myDao;


    @Generated(hash = 1395429522)
    public Wallpaper() {
    }

    @Generated(hash = 1053494452)
    public Wallpaper(long id, String href, String imgUrl, long albumId) {
        this.id = id;
        this.href = href;
        this.imgUrl = imgUrl;
        this.albumId = albumId;
    }

    public Wallpaper(String href, String imgUrl, List<ImageSrc> downloadUrls) {
        this.href = href;
        this.imgUrl = imgUrl;
        this.downloadUrls = downloadUrls;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    @Keep
    public List<ImageSrc> getDownloadUrls() {
        return downloadUrls;
    }

    public void setDownloadUrls(List<ImageSrc> downloadUrls) {
        this.downloadUrls = downloadUrls;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 709538206)
    public synchronized void resetDownloadUrls() {
        downloadUrls = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1695756527)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getWallpaperDao() : null;
    }
}