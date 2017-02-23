/**
 * Created by octavian.guzu on 2/23/2017.
 */
public class Video {
    int size;
    int ID;
    int requests = 0;

    public Video(int size, int ID) {
        this.size = size;
        this.ID = ID;
    }

    public String toString() {
        return "Video: "+size+" MB";
    }
}
