package channelpopularity.state;

import java.util.logging.Level;
import java.util.logging.Logger;
import channelpopularity.context.ChannelContext;
import channelpopularity.driver.Driver;
import java.util.ArrayList;
import java.util.List;

/***
 * unpopular Popular class works only when unpopular popular instance invoked.
 */
public class Unpopular extends AbstractState{
    /**
     * validating the logger
     */
    private final static Logger LOGGER = Logger.getLogger(Unpopular.class.getName());

    /***
     * this ad request method invokes only when channel state is unpopular popular
     * @param VideoName : for which video the request is
     * @param length : length of the request.
     */

    public void adRequest(String VideoName, String length){
        if(Integer.parseInt(length) > 1 && Integer.parseInt(length) <= 10){
            myArray.add("UNPOPULAR__AD_REQUEST::APPROVED");
        }
        else
            myArray.add("UNPOPULAR__AD_REQUEST::REJECTED");
    }
}