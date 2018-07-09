package bgmi.app.bgmi_android.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import bgmi.app.bgmi_android.R;
import bgmi.app.bgmi_android.holders.BangumiViewHolder;
import bgmi.app.bgmi_android.models.Bangumi;


public class BangumiAdapter extends RecyclerView.Adapter<BangumiViewHolder> {
    private Bangumi[] bangumiList;

    public BangumiAdapter(Bangumi[] bangumiList) {
        this.bangumiList = bangumiList;
    }

    @NonNull
    @Override
    public BangumiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ticketCard = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_bangumi, parent, false);
        return new BangumiViewHolder(ticketCard);
    }

    @Override
    public void onBindViewHolder(@NonNull BangumiViewHolder holder, int position) {
        final Bangumi bangumi = bangumiList[position];
        holder.updateUI(bangumi);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return bangumiList.length;
    }
}