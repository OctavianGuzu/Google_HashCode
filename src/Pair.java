/**
 * Created by octavian.guzu on 2/23/2017.
 */
public class Pair {
    CacheServer cacheServer;
    int latency;

    public Pair(CacheServer cacheServer, int latency) {
        this.cacheServer = cacheServer;
        this.latency = latency;
    }
}
