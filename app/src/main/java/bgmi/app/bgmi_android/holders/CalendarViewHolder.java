package bgmi.app.bgmi_android.holders;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import bgmi.app.bgmi_android.R;
import bgmi.app.bgmi_android.adapters.CalendarItemAdapter;


public class CalendarViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "CalendarViewHolder";

    private Fragment fragment;
    private TextView calTitle;
    private RecyclerView calList;

    public CalendarViewHolder(Fragment fragment, View itemView) {
        super(itemView);
        this.fragment = fragment;
        this.calTitle = itemView.findViewById(R.id.title_cal);
        this.calList = itemView.findViewById(R.id.cal_list);
        calList.setClickable(true);
    }

    public void updateUI(String weekDay, ArrayList<String> listData) {
        calTitle.setText(weekDay);

        calList.setHasFixedSize(true);
        calList.setItemViewCacheSize(listData.size());

        CalendarItemAdapter calendarItemAdapter = new CalendarItemAdapter(fragment, listData);
        calendarItemAdapter.setHasStableIds(true);
        calList.setAdapter(calendarItemAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(calList.getContext());
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        calList.setLayoutManager(layoutManager);
    }
}
