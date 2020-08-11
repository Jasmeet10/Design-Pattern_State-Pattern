package channelpopularity.context;

import channelpopularity.state.StateName;
import channelpopularity.state.StateI;

public interface ContextI {
    public void setCurrentState(StateName nextState);
    public StateI getCurrentState();
    public void update();
    public StateName returnStateName(StateI StateIn);
}
