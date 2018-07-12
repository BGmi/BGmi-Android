package bgmi.app.bgmi_android.holders;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bgmi.app.bgmi_android.R;
import bgmi.app.bgmi_android.adapters.CalendarItemAdapter;
import bgmi.app.bgmi_android.models.Bangumi;
import bgmi.app.bgmi_android.utils.BGmiProperties;
import bgmi.app.bgmi_android.utils.ImageWorker;

public class CalendarViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "CalendarViewHolder";

    private TextView calTitle;
    private RecyclerView calList;

    public CalendarViewHolder(View itemView) {
        super(itemView);
        this.calTitle = itemView.findViewById(R.id.title_cal);
        this.calList = itemView.findViewById(R.id.cal_list);
        calList.setClickable(true);
    }

    public void updateUI(String weekDay, ArrayList<String> listData) {
        calTitle.setText(weekDay);

        calList.setHasFixedSize(true);
        calList.setItemViewCacheSize(listData.size());

        CalendarItemAdapter calendarItemAdapter = new CalendarItemAdapter(listData);
        calendarItemAdapter.setHasStableIds(true);
        calList.setAdapter(calendarItemAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(calList.getContext());
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        calList.setLayoutManager(layoutManager);
    }
}
