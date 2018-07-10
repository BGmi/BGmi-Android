package bgmi.app.bgmi_android.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.security.auth.callback.Callback;

import bgmi.app.bgmi_android.models.Bangumi;

public class LoadBangumi {
    private static final String TAG = "LoadBangumi";
    private static LoadBangumi mInstance = null;

    private LoadBangumi(){}

    public static synchronized LoadBangumi getInstance() {
        if(null == mInstance){
            mInstance = new LoadBangumi();
        }
        return mInstance;
    }

    public void load(String url, Context context, final CallBack clazz) {
        String errorString = "";
        Log.i(TAG, "loadData: " + url);
        RequestQueue queue = Volley.newRequestQueue(context);
        final JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        try {
                            JSONArray bangumiJSONArray = response.getJSONArray("data");
                            ArrayList<Bangumi> bangumiList = new ArrayList<>();
                            for (int i = 0; i<bangumiJSONArray.length(); i++) {
                                Bangumi bangumi = gson.fromJson(bangumiJSONArray.getJSONObject(i).toString(), Bangumi.class);
                                bangumiList.add(bangumi);
                            }

                            clazz.callback(bangumiList, "");

                        } catch (Exception ex) {
                            clazz.callback(null, "Error when parsing response data");
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        clazz.callback(null, "Error when performing HTTP request");
                        Log.e(TAG, error.toString());
                    }
                }
        );
        queue.add(request);
    }
}
