package bgmi.app.bgmi_android.utils;

import java.util.ArrayList;

import bgmi.app.bgmi_android.models.Bangumi;

public interface CallBack {
    void callback(ArrayList<Bangumi> bangumiArrayList, String errorString);
}
