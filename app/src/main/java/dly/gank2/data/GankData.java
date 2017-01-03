package dly.gank2.data;

import java.util.Date;
import java.util.Objects;

/**
 * Created by 19229 on 2016/12/14.
 */
public class GankData {
    public String _id;
    public Date createdAt;
    public String desc;
    public Date publishedAt;
    public String source;
    public String type;
    public String url;
    public boolean used;
    public String who;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GankData gank = (GankData) o;
        return Objects.equals(_id, gank._id) &&
                Objects.equals(type, gank.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, type);
    }

}
