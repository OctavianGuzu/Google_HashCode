import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

/**
 * Created by octavian.guzu on 2/23/2017.
 */
public class Endpoint {
    int latencyToCenter;
    Vector<Pair> connectCacheServers = new Vector<>();
    Vector<Request> requests = new Vector<>();
    int nrOfRequests = 0;

    public Endpoint(int latencyToCenter) {
        this.latencyToCenter = latencyToCenter;
    }

    public void connectCacheServer(CacheServer cs, int latencyToCacheServer) {
        connectCacheServers.add(new Pair(cs, latencyToCacheServer));
    }

    public void addRequest(int numbersOfRequest, Video v) {
        requests.add(new Request(numbersOfRequest, v));
    }

    public void sortRequests() {
        Collections.sort(requests, (o1, o2) -> o2.numbersOfRequest - o1.numbersOfRequest);
    }

    public void sortCacheServers() {
        Collections.sort(connectCacheServers, new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return o1.latency - o2.latency;
            }
        });
    }

    public void numberOfRequests() {
        for(Request r:requests)
            this.nrOfRequests += r.numbersOfRequest;
    }
}
