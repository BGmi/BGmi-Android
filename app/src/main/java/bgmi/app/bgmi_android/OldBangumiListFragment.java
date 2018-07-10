package bgmi.app.bgmi_android;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import bgmi.app.bgmi_android.adapters.BangumiAdapter;
import bgmi.app.bgmi_android.models.Bangumi;
import bgmi.app.bgmi_android.utils.BGmiProperties;
import bgmi.app.bgmi_android.utils.CallBack;
import bgmi.app.bgmi_android.utils.LoadBangumi;


public class OldBangumiListFragment extends BangumiListFragment {
    private static final String TAG = "OldBangumiListFragment";
    private ArrayList<Bangumi> bangumiList;

    public void loadData() {
        String url = getActivity().getSharedPreferences("bgmi_config", 0).getString("bgmi_url", "");
        url += BGmiProperties.getInstance().pageOldURL;
        Log.i(TAG, "loadData: " + url);

        LoadBangumi.getInstance().load(url, getContext(), this);
    }
    public OldBangumiListFragment() {
    }

    public static OldBangumiListFragment newInstance() {
        return new OldBangumiListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        SharedPreferences sp = getActivity().getSharedPreferences("bgmi_config", 0);
        String bgmi_data = sp.getString("bgmi_old_data", "");

        if (bgmi_data.equals("")) {
            loadData();
        } else {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Bangumi>>() {}.getType();
            bangumiList = gson.fromJson(bgmi_data, type);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bangumi_list, container, false);
    }

    public void callback(ArrayList<Bangumi> bangumiArrayList, String errorString) {

        if (!errorString.equals("")) {
            Toast.makeText(getContext(), errorString, Toast.LENGTH_LONG).show();
            return;
        }

        bangumiList = bangumiArrayList;

        Gson gson = new Gson();
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("bgmi_config", 0).edit();
        editor.putString("bgmi_old_data", gson.toJson(bangumiArrayList));
        editor.apply();

        View view = getView();
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(bangumiList.size());


        BangumiAdapter bangumiAdapter = new BangumiAdapter(bangumiList);
        bangumiAdapter.setHasStableIds(true);
        recyclerView.setAdapter(bangumiAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

}