package app.prasun.earnbtctest.web.client;

public class StateHandler {
    public static StateHandler stateHandler;
    public int actualTimerData = 0;
    private StateHandler(){ }

    public static StateHandler getStateInstance() {
        if(stateHandler == null){
            stateHandler = new StateHandler();
        }
        return stateHandler;
    }
}
