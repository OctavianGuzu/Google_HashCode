import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * Created by octavian.guzu on 2/23/2017.
 */
public class MainData {
    Vector<Endpoint> endpoints = new Vector<>();
    DataCenter dataCenter = new DataCenter();
    Vector<CacheServer> cacheServers = new Vector<>();

    public void readFromFile(String fileName) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        String line = in.readLine();
        StringTokenizer aux = new StringTokenizer(line);
        int nrOfVideos = Integer.valueOf(aux.nextToken());
        int nrOfEndpoints = Integer.valueOf(aux.nextToken());
        int nrOfRequests = Integer.valueOf(aux.nextToken());
        int nrOfCaches = Integer.valueOf(aux.nextToken());
        int cachesSize = Integer.valueOf(aux.nextToken());
        //System.out.println(cachesSize);
        // Adding Cache Servers
        for(int i = 0; i < nrOfCaches; i++)
            cacheServers.add(new CacheServer(cachesSize));

        line = in.readLine();
        aux = new StringTokenizer(line);
        for(int i=0;i<nrOfVideos;i++) {
            int videoSize = Integer.valueOf(aux.nextToken());
            dataCenter.addVideo(new Video(videoSize, i));
        }
        for(int i=0;i<nrOfEndpoints;i++) {
            line = in.readLine();
            aux = new StringTokenizer(line);
            int latentyToDataCenter = Integer.valueOf(aux.nextToken());
            endpoints.add(new Endpoint(latentyToDataCenter));
            int nrOfCaches2 = Integer.valueOf(aux.nextToken());
            for(int j=0;j<nrOfCaches2;j++) {
                line = in.readLine();
                aux = new StringTokenizer(line);
                int cacheNumber = Integer.valueOf(aux.nextToken());
                int latenty = Integer.valueOf(aux.nextToken());
                endpoints.get(i).connectCacheServer(cacheServers.get(cacheNumber), latenty);
            }
        }
        for(int i=0;i<nrOfRequests;i++) {
            line = in.readLine();
            aux = new StringTokenizer(line);
            int videoNr = Integer.valueOf(aux.nextToken());
            int endpointNr = Integer.valueOf(aux.nextToken());
            int nrRequests = Integer.valueOf(aux.nextToken());
            endpoints.get(endpointNr).requests.add(new Request(nrRequests, dataCenter.videos.get(videoNr)));
        }
    }

    public static void main(String args[]) throws IOException {
        MainData obj = new MainData();
        obj.readFromFile("me_at_the_zoo.in");


        // Start

        for(Endpoint e: obj.endpoints) {
            e.sortRequests();
            e.sortCacheServers();
            e.numberOfRequests();
        }

        Collections.sort(obj.endpoints, (o1, o2) -> o2.nrOfRequests - o1.nrOfRequests);

        for(Endpoint e: obj.endpoints) {
            for(Request r: e.requests) {
                for(Pair p:e.connectCacheServers) {
                    if(!p.cacheServer.videos.contains(r.requestedVideo))
                        if(p.cacheServer.addVideo(r.requestedVideo))
                            break;
                }
            }
        }

        for(int i=0;i<obj.cacheServers.size();i++)
            System.out.println("" + i + " " + obj.cacheServers.get(i));


    }
}
