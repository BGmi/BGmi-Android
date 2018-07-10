package bgmi.app.bgmi_android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import bgmi.app.bgmi_android.adapters.CalendarAdapter;
import bgmi.app.bgmi_android.models.Bangumi;
import bgmi.app.bgmi_android.utils.CallBack;
import bgmi.app.bgmi_android.utils.LoadBangumi;


public class CalendarFragment extends Fragment implements CallBack<HashMap<String, ArrayList<String>>> {
    ArrayList<String> listItem = new ArrayList<>();

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
        for (String key: callbackData.keySet()) {
            listItem.add(key.toUpperCase());
            listItem.addAll(callbackData.get(key));
        }

        ListView listView = getView().findViewById(R.id.calendar_list);
        ListAdapter listAdapter = new CalendarAdapter(getContext(), R.layout.content_calendar, listItem);

        listView.setAdapter(listAdapter);
    }
}

