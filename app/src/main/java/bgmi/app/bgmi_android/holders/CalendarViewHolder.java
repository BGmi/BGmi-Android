package bgmi.app.bgmi_android.holders;

import android.os.Handler;
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
import bgmi.app.bgmi_android.models.Bangumi;
import bgmi.app.bgmi_android.utils.BGmiProperties;
import bgmi.app.bgmi_android.utils.ImageWorker;

public class CalendarViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "BangumiViewHolder";

    private TextView calTitle;
    private LinearLayout calList;

    public CalendarViewHolder(View itemView) {
        super(itemView);
        this.calTitle = itemView.findViewById(R.id.title_cal);
        this.calList = itemView.findViewById(R.id.cal_list);
        calList.setClickable(true);
    }

    public void updateUI(String weekDay, ArrayList<String> listData) {
        calTitle.setText(weekDay);

        LayoutInflater inflater = LayoutInflater.from(calList.getContext());
        for (String name: listData) {
            TextView view = (TextView) inflater.inflate(R.layout.content_calendar_item, calList, false);
            view.setText(name);
            calList.addView(view);
        }
    }
}
