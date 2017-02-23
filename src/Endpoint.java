import java.util.Vector;

/**
 * Created by octavian.guzu on 2/23/2017.
 */
public class Endpoint {
    int latencyToCenter;
    Vector<Pair> connectCacheServers = new Vector<>();
    Vector<Request> requests = new Vector<>();

    public Endpoint(int latencyToCenter) {
        this.latencyToCenter = latencyToCenter;
    }

    public void connectCacheServer(CacheServer cs, int latencyToCacheServer) {
        connectCacheServers.add(new Pair(cs, latencyToCacheServer));
    }

    public void addRequest(int numbersOfRequest, Video v) {
        requests.add(new Request(numbersOfRequest, v));
    }
}
