package channelpopularity.context;

import channelpopularity.driver.Driver;
import channelpopularity.state.factory.SimpleStateFactory;
import channelpopularity.state.factory.SimpleStateFactoryI;
import channelpopularity.state.StateName;
import channelpopularity.state.StateI;
import channelpopularity.context.ChannelContext;
import channelpopularity.state.AbstractState;
import java.util.List;
import java.util.ArrayList;
import channelpopularity.operation.Operation;


/***
 * Helper class validate the input line by line.
 */
public class HelperClass {

    private SimpleStateFactoryI stateFactory;
    private List<StateName> stateNames;
    private ChannelContext channelContext;
    private StateI curState;

    public HelperClass() {
        stateFactory = new SimpleStateFactory();
        stateNames = new ArrayList() {{
            add(StateName.UNPOPULAR);
            add(StateName.MILDLY_POPULAR);
            add(StateName.HIGHLY_POPULAR);
            add(StateName.ULTRA_POPULAR);
        }};
        channelContext = new ChannelContext(stateFactory, stateNames);
    }

    /***
     * helper method takes the input and then check the format as per the actions (ADD_VIDEO, REMOVE_VIDEO, METRICS, AD_REQUEST)
     * @param InputLine : input line is obtained by using the poll method in file processor.
     */

    public void helper(String InputLine){
        String Video_Name;
        String likes;
        String dislike;
        String views;
        String Length;

        /**
         * Validating Add Video formatting
         */
        if((InputLine.contains((Operation.valueOf("ADD_VIDEO")).toString())) && InputLine.contains("::")){
            Video_Name = InputLine.substring(11, InputLine.length());
            if(!Driver.resultmap.containsKey(Video_Name)) {
                if (((InputLine.indexOf(":", 11)) != -1) || (InputLine.substring(11, InputLine.length()).isEmpty())) {
                    System.out.println("Invalid Add Video Input Format");
                    System.exit(0);
                } else {
                    Driver.likemap.put(Video_Name,0);
                    Driver.dislikemap.put(Video_Name,0);
                    curState = channelContext.getCurrentState();
                    curState.addVideo(Video_Name);
                    AbstractState.myArray.add(channelContext.returnStateName(curState) + "__VIDEO_ADDED::" + Video_Name);
                    channelContext.update();
                }
            }else{
                System.out.println(Video_Name + " already exist in the channel.");
                System.exit(0);
            }
        }
        /**
         * Validating Remove Video formatting
         */
        else if((InputLine.contains((Operation.valueOf("REMOVE_VIDEO")).toString())) && InputLine.contains("::")){
            if(((InputLine.indexOf(":",14)) != -1) || (InputLine.substring(14,InputLine.length()).isEmpty())){
                System.out.println("Invalid Remove Video Input Format");
                System.exit(0);
            }else{
                Video_Name = InputLine.substring(14,InputLine.length());
                if(Driver.resultmap.containsKey(Video_Name)) {
                    curState = channelContext.getCurrentState();
                    curState.removeVideo(Video_Name);
                    AbstractState.myArray.add(channelContext.returnStateName(curState) + "__VIDEO_REMOVED::" + Video_Name);
                    channelContext.update();
                }
                else{
                    System.out.println(Video_Name +" you are trying to remove, does not exist in the channel");
                    System.exit(0);
                }
            }
        }
        /**
         * Validating Metric formatting
         */
        else if ((InputLine.contains((Operation.valueOf("METRICS")).toString()))  && InputLine.contains("::") && InputLine.contains("__")) {
            Video_Name = InputLine.substring((InputLine.indexOf("_")+2),(InputLine.indexOf(":")));
            if(Driver.resultmap.containsKey(Video_Name)) {
                if (
                        ((InputLine.indexOf(":", InputLine.indexOf(":") + 2)) != -1) ||
                                (InputLine.indexOf(" ", InputLine.indexOf(",") - 1)) != -1 ||
                                (InputLine.indexOf(" ", InputLine.indexOf(",") + 1)) != -1 ||
                                (InputLine.indexOf(" ", InputLine.indexOf(",", InputLine.indexOf(",") + 1) - 1)) != -1 ||
                                (InputLine.indexOf(" ", InputLine.indexOf(",", InputLine.indexOf(",") + 1) + 1)) != -1

                ) {
                    System.out.println("Invalid Metrics Format");
                    System.exit(0);
                } else {
                    views = InputLine.substring((InputLine.indexOf("=") + 1), (InputLine.indexOf(",")));
                    likes = InputLine.substring((InputLine.indexOf("=", (InputLine.indexOf("=") + 1)) + 1), (InputLine.indexOf(",", (InputLine.indexOf(",")) + 1)));
                    dislike = InputLine.substring((InputLine.indexOf("=", (InputLine.indexOf("=", (InputLine.indexOf("=") + 1)) + 1) + 1) + 1), (InputLine.indexOf("]")));

                    try {
                        Integer.parseInt(views);
                        Integer.parseInt(likes);
                        Integer.parseInt(dislike);
                    } catch (NumberFormatException e) {
                        System.out.println("Views/Likes/Dislikes Only accept Integer values");
                        System.exit(0);
                    }if((Integer.parseInt(views))>=0){
                        int oldlike = Driver.likemap.get(Video_Name);
                        int olddislike = Driver.dislikemap.get(Video_Name);
                        if(((oldlike + Integer.parseInt(likes))<0) || ((olddislike + Integer.parseInt(dislike))<0)){
                            System.out.println("Decrease in likes or dislikes cannot be more than total number of likes or dislikes");
                            System.exit(0);
                        }else{
                             Driver.likemap.put(Video_Name,(oldlike + Integer.parseInt(likes)));
                             Driver.dislikemap.put(Video_Name,(olddislike + Integer.parseInt(dislike)));
                        }
                    }else{
                        System.out.println("Views cannot be negative.");
                        System.exit(0);
                    }

                    curState = channelContext.getCurrentState();
                    curState.metrics(Video_Name, views, likes, dislike);
                    AbstractState.myArray.add(channelContext.returnStateName(curState) + "__POPULARITY_SCORE_UPDATE::" + Driver.CurrentMetrics);
                    channelContext.update();
                }
            }else{
                System.out.println("You are trying to update" +Video_Name+", Kindly add this video first.");
                System.exit(0);
            }
        }
        /**
         * Validating Add Request formatting
         */
        else if ((InputLine.contains((Operation.valueOf("AD_REQUEST")).toString())) && (InputLine.contains("::")) && InputLine.contains("__")) {
            Video_Name = InputLine.substring((InputLine.indexOf("__") + 2), (InputLine.indexOf(":")));
                if (Driver.resultmap.containsKey(Video_Name)) {
                    if (
                        ((InputLine.indexOf(":", InputLine.indexOf(":") + 2)) != -1)
                )   {
                    System.out.println("Invalid Request Format");
                    System.exit(0);
                    }   else {
                    Length = InputLine.substring(InputLine.indexOf("=") + 1);
                    try {
                        Integer.parseInt(Length);

                    } catch (NumberFormatException e) {
                        System.out.println("Length Only accept Integer values");
                        System.exit(0);
                    }
                    curState = channelContext.getCurrentState();
                    curState.adRequest(Video_Name, Length);
                    }
                }else{
                    System.out.println("This Request cannot be processed for "+ Video_Name +", Kindly add the video first.");
                    System.exit(0);
                }
            }
        else
        {
            System.out.println("Invalid Input Format");
            System.exit(0);
        }


    }

}