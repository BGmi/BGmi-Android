package bgmi.app.bgmi_android.utils;

public class BGmiProperties {
    private static BGmiProperties mInstance = null;

    private BGmiProperties(){}

    public static synchronized BGmiProperties getInstance() {
        if(null == mInstance){
            mInstance = new BGmiProperties();
        }
        return mInstance;
    }

    public String bgmiBackendURL;
    final public String pageIndexURL = "api/index";
    final public String pageOldURL = "api/old";
}
