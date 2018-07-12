package bgmi.app.bgmi_android.utils;

import java.util.ArrayList;

import bgmi.app.bgmi_android.models.Bangumi;

public class BGmiProperties {
    private static BGmiProperties mInstance = null;

    private BGmiProperties(){}

    public static synchronized BGmiProperties getInstance() {
        if(null == mInstance){
            mInstance = new BGmiProperties();
        }
        return mInstance;
    }

    public ArrayList<Bangumi> followedBangumi;
    public String bgmiBackendURL;
    public String adminToken;

    final public String pageIndexURL = "api/index";
    final public String pageOldURL = "api/old";
    final public String pageCalURL = "api/cal";
}
