import java.util.Vector;

/**
 * Created by octavian.guzu on 2/23/2017.
 */
public class CacheServer {
    int capacity;
    Vector<Video> videos = new Vector<>();

    public CacheServer(int capacity) {
        this.capacity  = capacity;
    }

    public int totalVideosSize() {
        int total = 0;
        for(int i=0; i<videos.size(); i++)
            total += videos.get(i).size;
        return total;
    }

    public boolean addVideo(Video v) {
        if(totalVideosSize() + v.size <= capacity) {
            videos.add(v);
            return true;
        }
        else {
            return false;
        }
    }

    public String toString() {
        String result = new String();
        for(int i=0;i<videos.size()-1;i++)
            result += videos.get(i).ID + " ";
        result += videos.get(videos.size()-1).ID;
        return result;
    }
}
