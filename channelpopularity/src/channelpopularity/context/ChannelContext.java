package channelpopularity.context;

import channelpopularity.operation.Operation;
import channelpopularity.state.factory.SimpleStateFactory;
import channelpopularity.driver.Driver;
import channelpopularity.state.AbstractState;
import channelpopularity.state.StateI;
import java.util.Map;
import channelpopularity.state.StateName;
import channelpopularity.state.factory.SimpleStateFactoryI;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

/***
 * ChannelContext is here to maintain the current state of the channel, by using different methods below.
 */
public class ChannelContext implements ContextI {
    private StateI curState;
    private StateName StateNameupdate;
    private Map<StateName, StateI> availableStates;


    public ChannelContext(SimpleStateFactoryI stateFactoryIn, List<StateName> stateNames) {
        // initialize states using factory instance and the provided state names.
        // initialize current state.
        if (stateFactoryIn != null && stateNames != null && stateNames.size() > 1) {
            availableStates = new HashMap<>();
            for (StateName stateName : stateNames)
                availableStates.put(stateName, stateFactoryIn.create(stateName));
            curState = availableStates.get(StateName.UNPOPULAR);
        }

    }

    // Called by the States based on their logic of what the machine state should change to.

    /***
     * setCurrentState method is setter for current state using avaliable state map.
     * @param nextState
     */
    public void setCurrentState(StateName nextState) {
        if (availableStates.containsKey(nextState)) { // for safety.
            curState = availableStates.get(nextState);
        }
    }

    /**
     * getCurrentState method is getter for the current state.
     * @return State
     */

    public StateI getCurrentState() {
        return curState;
    }

    /**
     * Undate method is upadting the current state with the change in the metrics.
     */
    public void update() {
        if (Driver.CurrentMetrics > 0 && Driver.CurrentMetrics < 1000) {
            curState = availableStates.get(StateName.UNPOPULAR);

        }
        if (Driver.CurrentMetrics > 1001 && Driver.CurrentMetrics < 10000) {
            curState = availableStates.get(StateName.MILDLY_POPULAR);

        }
        if (Driver.CurrentMetrics > 10001 && Driver.CurrentMetrics < 100000) {
            curState = availableStates.get(StateName.HIGHLY_POPULAR);

        }
        if (Driver.CurrentMetrics > 100000) {
            curState = availableStates.get(StateName.ULTRA_POPULAR);

        }
    }

    /***
     * ReturnStateName methods checking the instance and returning back the stane name for that state.
     * @param StateIn : current state instance.
     * @return
     */

    public StateName returnStateName(StateI StateIn) {

        for (Map.Entry<StateName, StateI> entry : availableStates.entrySet()) {
            if (entry.getValue().equals(StateIn)) {
                StateNameupdate = entry.getKey();
            }
        }
        return StateNameupdate;
    }
}