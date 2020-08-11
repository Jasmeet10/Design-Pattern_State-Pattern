package channelpopularity.state;

import java.util.logging.Level;
import java.util.logging.Logger;
import channelpopularity.context.ChannelContext;
import channelpopularity.driver.Driver;
import java.util.ArrayList;
import java.util.List;

/***
 * Highly Popular class works only when highly popular instance invoked.
 */
public class Highly_Popular extends AbstractState{

    /***
     * this ad request method invokes only when channel state is highly popular
     * @param VideoName : for which video the request is
     * @param length : length of the request.
     */
    public void adRequest(String VideoName, String length){
        if(Integer.parseInt(length) > 1 && Integer.parseInt(length) <= 30){
            myArray.add("HIGHLY_POPULAR__AD_REQUEST::APPROVED");

        }
        else
            myArray.add("HIGHLY_POPULAR__AD_REQUEST::REJECTED");
    }
}