package channelpopularity.state;

import channelpopularity.driver.Driver;
import java.lang.Math;
import channelpopularity.context.ChannelContext;
import channelpopularity.state.StateName;
import java.util.ArrayList;
import java.util.List;

/***
 * Abstract class is used for the common methods which can be used by the other classes by extending this class.
 * common methods are defined here which helps to reduce the repetation of code.
 */
public abstract class AbstractState implements StateI {
    public static List<String> myArray = new ArrayList<String>();

    abstract public void adRequest(String VideoName, String length);

    /***
     *
     * If the video in the channel is not present then only add video method can be invoked.
     * @param VideoName : video need to added in the channel
     */
    public void addVideo(String VideoName) {
        Driver.resultmap.put( VideoName , 0);
        Driver.CurrentMetrics = Driver.CurrentMetrics * Driver.NumberOfVideos;
        Driver.NumberOfVideos++;
        Driver.CurrentMetrics = Driver.CurrentMetrics / Driver.NumberOfVideos;

    }

    /***
     * if the video need to removed from the channel, remove video method can be invoked
     * @param VideoName
     */
    public void removeVideo(String VideoName){
        int value = Driver.resultmap.get(VideoName);
        Driver.resultmap.remove(VideoName);
        Driver.CurrentMetrics = Driver.CurrentMetrics * Driver.NumberOfVideos;
        Driver.CurrentMetrics = Driver.CurrentMetrics - value;
        Driver.NumberOfVideos--;
        if(Driver.NumberOfVideos != 0) {
            Driver.CurrentMetrics = Driver.CurrentMetrics / Driver.NumberOfVideos;
        }

    }

    /***
     * calculate the metrics for the video, if any video is added or removed from the channel.
     * @param VideoName : for which video metrics needs to be calculated
     * @param viewsIn : number of views
     * @param likesIn : number of likes
     * @param dislikesIn : number of dislikes
     */

    public void metrics(String VideoName,String viewsIn,String likesIn,String dislikesIn ){
        int value = Driver.resultmap.get(VideoName);
        Driver.resultmap.remove(VideoName);
        Driver.CurrentMetrics = Driver.CurrentMetrics * Driver.NumberOfVideos;
        int Metrics = Integer.parseInt(viewsIn) + 2*(Integer.parseInt(likesIn) - Integer.parseInt(dislikesIn));
        if(Metrics <= 0){
            Metrics = 0;
        }
        Driver.resultmap.put( VideoName , (Metrics + value));
        //System.out.println("Value of result map matrics " + (Driver.CurrentMetrics + Metrics));
        Driver.CurrentMetrics = (int) Math.round(((float) Driver.CurrentMetrics + (float) Metrics) / (float) Driver.NumberOfVideos);


    }
}
