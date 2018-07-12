package bgmi.app.bgmi_android.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import bgmi.app.bgmi_android.R;
import bgmi.app.bgmi_android.holders.CalendarItemViewHolder;

public class CalendarItemAdapter extends RecyclerView.Adapter<CalendarItemViewHolder> {
    private ArrayList<String> bangumiList;

    public CalendarItemAdapter(ArrayList<String> bangumiList) {
        this.bangumiList = bangumiList;
    }

    @NonNull
    @Override
    public CalendarItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View calItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_calendar_item, parent, false);
        return new CalendarItemViewHolder(calItem);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarItemViewHolder holder, int position) {
        holder.updateUI(bangumiList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return bangumiList.size();
    }
}
