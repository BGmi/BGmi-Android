package bgmi.app.bgmi_android.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Handler;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bgmi.app.bgmi_android.R;
import bgmi.app.bgmi_android.models.Bangumi;
import bgmi.app.bgmi_android.utils.BGmiProperties;
import bgmi.app.bgmi_android.utils.ImageWorker;

public class BangumiViewHolder extends RecyclerView.ViewHolder {
    private Handler handler = new Handler();
    private ImageView bangumiImageView;
    private TextView bangumiNameView;
    private TextView bangumiLatestView;
    private LinearLayout bangumiEpisodeLayout;

    public BangumiViewHolder(View itemView) {
        super(itemView);

        this.bangumiImageView = itemView.findViewById(R.id.card_imageview);
        this.bangumiNameView = itemView.findViewById(R.id.card_textview);
        this.bangumiLatestView = itemView.findViewById(R.id.card_episode);
        this.bangumiEpisodeLayout = itemView.findViewById(R.id.card_button_layout);
    }

    public void updateUI(Bangumi bangumi) {
        if (bangumiEpisodeLayout.getChildCount() > 0) {
            return;
        }

        String url = BGmiProperties.getInstance().bgmiFrontEndURL + bangumi.getCover();
        ImageWorker.loadImage(bangumiImageView, url, handler);
        bangumiNameView.setText(bangumi.getBangumi_name());
        bangumiLatestView.setText("latest: " + bangumi.getEpisode());

        List<Integer> list = new ArrayList<>();

        for (String key: new ArrayList<>(bangumi.getPlayer().keySet())) {
            list.add(Integer.parseInt(key));
        }
        Collections.sort(list, Collections.reverseOrder());

        for (Integer key: list) {
            String path = bangumi.getPlayer().get(key.toString()).get("path");
            System.out.println(path);

            Button episodeButton = new Button(itemView.getContext(), null);
            episodeButton.setText(key.toString());

            bangumiEpisodeLayout.addView(episodeButton);
        }
    }

}
