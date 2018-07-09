package bgmi.app.bgmi_android;

import android.content.Context;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import bgmi.app.bgmi_android.adapters.BangumiAdapter;
import bgmi.app.bgmi_android.models.Bangumi;


public class BangumiListFragment extends Fragment {
    private Bangumi[] bangumi;

    public BangumiListFragment() {
    }

    public static BangumiListFragment newInstance() {
        return new BangumiListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bangumi_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);


        BangumiAdapter bangumiAdapter = new BangumiAdapter(this.bangumi);
        recyclerView.setAdapter(bangumiAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    public void setBangumi(Bangumi[] bangumi) {
        this.bangumi = bangumi;
    }

}
