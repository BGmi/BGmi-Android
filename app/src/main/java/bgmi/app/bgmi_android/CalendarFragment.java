package bgmi.app.bgmi_android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import bgmi.app.bgmi_android.models.Bangumi;
import bgmi.app.bgmi_android.utils.CallBack;
import bgmi.app.bgmi_android.utils.LoadBangumi;


public class CalendarFragment extends Fragment implements CallBack {

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
    public void callback(ArrayList<Bangumi> bangumiArrayList, String errorString) {

    }
}

