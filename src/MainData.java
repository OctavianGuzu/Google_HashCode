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
        obj.readFromFile("trending_today.in");


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
                        else { // Iau toate videoclipurile din cache-ul respectiv, in ordinea crescatoare a nr de requesturi
                                // Iau o variabila sum = 0
                                // Parcurg videourile
                                // Adun la suma dim videoului si adaug Video intr-un vector de Video
                                // If Cache.size - sum >= Video.size
                                    // Parcurg videoclipurile din Vectorul de Video si iau cache cel mai apropiat ca latenta
                                        // While(1)
                                            // if(cache cel mai apropiat.add(Vector videoclipuri.element))
                                                 // sum -= Vector videoclipuri.element.size();
                                                // break
                                            //else
                                                // trec la cache urmator ca si latenta
                            r.requestedVideo.requests = r.numbersOfRequest;
                            Collections.sort(p.cacheServer.videos, new Comparator<Video>() {
                                @Override
                                public int compare(Video o1, Video o2) {
                                    return o1.requests - o2.requests;
                                }
                            });

                            int sum = 0;
                            int sum_r = 0;
                            Vector<Video> videos = new Vector<>();
                            for(Video v:p.cacheServer.videos) {
                                sum += v.size;
                                videos.add(v);
                            }
                            if((p.cacheServer.capacity - p.cacheServer.totalVideosSize()) + sum >= r.requestedVideo.size) {
                                if(sum_r < r.requestedVideo.requests) {
                                    for(Video v: videos) {
                                        if(obj.cacheServers.indexOf(p.cacheServer) == obj.cacheServers.size() - 1)
                                            break;
                                        CacheServer closest = obj.cacheServers.get(obj.cacheServers.indexOf(p.cacheServer) + 1);
                                        CacheServer aux = closest;
                                        System.out.println("HERE");
                                        if( closest == null) {
                                            continue;
                                        }

                                        while (true) {
                                            if(closest == null) {
                                                break;
                                            }
                                            int ok = 0;
                                            if(closest.totalVideosSize() - v.size >= 0) {
                                                if(!closest.videos.contains(v)) {
                                                    closest.addVideo(v);
                                                    break;
                                                }
                                            }
                                            if(obj.cacheServers.indexOf(closest) == obj.cacheServers.size() - 1)
                                                break;
                                            closest = obj.cacheServers.get(obj.cacheServers.indexOf(closest) + 1);
                                        }
                                    }
                                    p.cacheServer.videos.removeAll(videos);
                                    p.cacheServer.videos.add(r.requestedVideo);
                                }
                            }

                        }
                }
            }
        }

        for(int i=0;i<obj.cacheServers.size();i++)
            System.out.println("" + i + " " + obj.cacheServers.get(i));


    }
}
