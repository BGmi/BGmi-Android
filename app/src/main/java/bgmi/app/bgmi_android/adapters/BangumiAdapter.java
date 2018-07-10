package bgmi.app.bgmi_android.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import bgmi.app.bgmi_android.R;
import bgmi.app.bgmi_android.holders.BangumiViewHolder;
import bgmi.app.bgmi_android.models.Bangumi;


public class BangumiAdapter extends RecyclerView.Adapter<BangumiViewHolder> {
    private static final String TAG = "BangumiAdapter";
    private ArrayList<Bangumi> bangumiList;

    public BangumiAdapter(ArrayList<Bangumi> bangumiList) {
        this.bangumiList = bangumiList;
    }

    @NonNull
    @Override
    public BangumiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ticketCard = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_bangumi_card, parent, false);
        return new BangumiViewHolder(ticketCard);
    }

    @Override
    public void onBindViewHolder(@NonNull BangumiViewHolder holder, int position) {
        if (position == 0) {
            Log.e(TAG, "position:" + position + " tv:" + holder.itemView.toString());
        }else {
            Log.i(TAG, "position:" + position + " tv:" + holder.itemView.toString());
        }

        final Bangumi bangumi = bangumiList.get(position);
        holder.updateUI(bangumi);

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

    @Override
    public long getItemId(int position) {
        return position;
    }
}