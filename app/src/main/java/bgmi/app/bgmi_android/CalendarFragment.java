package bgmi.app.bgmi_android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import bgmi.app.bgmi_android.adapters.BangumiAdapter;
import bgmi.app.bgmi_android.adapters.CalendarAdapter;
import bgmi.app.bgmi_android.utils.CallBack;
import bgmi.app.bgmi_android.utils.LoadBangumi;


public class CalendarFragment extends Fragment implements CallBack<HashMap<String, ArrayList<String>>> {
    ArrayList<String> listItem = new ArrayList<>();
    private String[] weekDays = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};

    public CalendarFragment() {
    }

    public static CalendarFragment newInstance() {
        return new CalendarFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoadBangumi.getInstance().calendar(getContext(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void callback(HashMap<String, ArrayList<String>> callbackData, String errorString) {
        if (getView() == null || getContext() == null) {
            return;
        }

        if (!errorString.equals("")) {
            Toast.makeText(getContext(), errorString, Toast.LENGTH_LONG).show();
            return;
        }

        View view = getView();
        RecyclerView recyclerView = view.findViewById(R.id.calendar_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemViewCacheSize(callbackData.keySet().size());


        CalendarAdapter calendarAdapter = new CalendarAdapter(callbackData);
        calendarAdapter.setHasStableIds(true);
        recyclerView.setAdapter(calendarAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }
}

