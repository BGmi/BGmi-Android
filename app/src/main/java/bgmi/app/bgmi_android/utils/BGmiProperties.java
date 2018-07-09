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

    public String bgmiFrontEndURL;
}
