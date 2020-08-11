package channelpopularity.state.factory;
import channelpopularity.state.StateI;
import channelpopularity.state.StateName;
import channelpopularity.state.Unpopular;
import channelpopularity.state.Mildly_Popular;
import channelpopularity.state.Highly_Popular;
import channelpopularity.state.Ultra_Popular;

/**
 * SimpleStateFactory class checking the current state of the channel and invode the same instance to handle the data.
 */
public class SimpleStateFactory implements SimpleStateFactoryI {
    public StateI create(StateName stateName){
        StateI stateI = null;
        switch(stateName) {
            case UNPOPULAR:
                stateI = new Unpopular();
                break;

            case MILDLY_POPULAR:
                stateI = new Mildly_Popular();
                break;

            case HIGHLY_POPULAR:
                stateI = new Highly_Popular();
                break;

            case ULTRA_POPULAR:
                stateI = new Ultra_Popular();
                break;
        }
        return stateI;
    }
}
