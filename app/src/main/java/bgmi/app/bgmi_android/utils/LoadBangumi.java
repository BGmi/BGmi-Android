package bgmi.app.bgmi_android.utils;

import android.content.Context;
import android.util.Log;

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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
        Log.i(TAG, "load: " + url);
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

    public void calendar(Context context, final CallBack<HashMap<String, ArrayList<String>>> clazz) {
        String url = BGmiProperties.getInstance().bgmiBackendURL + BGmiProperties.getInstance().pageCalURL;
        Log.i(TAG, "calendar: " + url);

        RequestQueue queue = Volley.newRequestQueue(context);
        final JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject("data");
                            HashMap<String, ArrayList<String>> calendarMap = new HashMap<>();
                            for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
                                String key = it.next();
                                ArrayList<String> nameList = new ArrayList<String>();

                                JSONArray jsonArray = jsonObject.getJSONArray(key);
                                for (Integer i = 0; i<jsonArray.length(); i++) {
                                    nameList.add(jsonArray.getJSONObject(i).getString("name"));
                                }
                                calendarMap.put(key, nameList);
                            }
                            clazz.callback(calendarMap, "");

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
