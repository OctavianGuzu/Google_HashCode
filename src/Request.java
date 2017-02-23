/**
 * Created by octavian.guzu on 2/23/2017.
 */
public class Request {
    int numbersOfRequest;
    Video requestedVideo;

    public Request(int numbersOfRequest, Video requestedVideo) {
        this.numbersOfRequest = numbersOfRequest;
        this.requestedVideo = requestedVideo;
        requestedVideo.requests += numbersOfRequest;
    }
}
