package bgmi.app.bgmi_android.utils;

import java.util.ArrayList;

import bgmi.app.bgmi_android.models.Bangumi;

public interface CallBack<T> {
    void callback(T callbackData, String errorString);
}
