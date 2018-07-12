package bgmi.app.bgmi_android.adapters;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import bgmi.app.bgmi_android.R;
import bgmi.app.bgmi_android.holders.CalendarViewHolder;


public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {
    private HashMap<String, ArrayList<String>> calData;
    private ArrayList<String> weekOrder;
    private Fragment fragment;

    public CalendarAdapter(Fragment fragment, HashMap<String, ArrayList<String>> calData) {
        this.fragment = fragment;
        this.calData = calData;

        Integer i;
        Integer day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
        weekOrder = new ArrayList<>();

        String[] weekDays = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
        for (i = day; i < 7; i++) {
            weekOrder.add(weekDays[i]);
        }

        for (i = 0; i < day; i++) {
            weekOrder.add(weekDays[i]);
        }
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View calCard = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_calendar, parent, false);
        return new CalendarViewHolder(fragment, calCard);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        holder.updateUI(weekOrder.get(position), calData.get(weekOrder.get(position).toLowerCase()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return calData.keySet().size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
