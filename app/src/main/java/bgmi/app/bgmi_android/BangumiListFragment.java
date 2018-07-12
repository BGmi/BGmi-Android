package bgmi.app.bgmi_android;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
import bgmi.app.bgmi_android.utils.BGmiManager;
import bgmi.app.bgmi_android.utils.BGmiProperties;
import bgmi.app.bgmi_android.utils.CallBack;


public class BangumiListFragment extends Fragment implements CallBack<ArrayList<Bangumi>> {
    private static final String TAG = "BangumiListFragment";
    private ArrayList<Bangumi> bangumiList;
    private SwipeRefreshLayout swipeRefreshLayout;

    public void loadData() {
        BGmiManager.getInstance().load(getContext(), this, BGmiProperties.getInstance().pageIndexURL);
    }

    public BangumiListFragment() {
    }

    public static BangumiListFragment newInstance() {
        return new BangumiListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getActivity().getSharedPreferences("bgmi_config", 0);
        String bgmi_data = sp.getString("bgmi_data", "");

        if (bgmi_data.equals("") || BGmiProperties.getInstance().refresh) {
            loadData();
            BGmiProperties.getInstance().refresh = false;
        } else {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Bangumi>>() {}.getType();
            bangumiList = gson.fromJson(bgmi_data, type);
            BGmiProperties.getInstance().followedBangumi = bangumiList;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bangumi_list, container, false);
        if (bangumiList != null) {
            callback(view);
        }
        return view;
    }

    public void callback(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(bangumiList.size());

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        BangumiAdapter bangumiAdapter = new BangumiAdapter(this.bangumiList);
        bangumiAdapter.setHasStableIds(true);
        recyclerView.setAdapter(bangumiAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }


    public void callback(ArrayList<Bangumi> bangumiArrayList, String errorString) {

        if (!errorString.equals("")) {
            Toast.makeText(getContext(), errorString, Toast.LENGTH_LONG).show();
            return;
        }

        bangumiList = bangumiArrayList;
        BGmiProperties.getInstance().followedBangumi = bangumiArrayList;

        Gson gson = new Gson();
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("bgmi_config", 0).edit();
        editor.putString("bgmi_data", gson.toJson(bangumiArrayList));
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
