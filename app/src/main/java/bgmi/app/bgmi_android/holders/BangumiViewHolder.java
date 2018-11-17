package bgmi.app.bgmi_android.holders;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bgmi.app.bgmi_android.PlayerActivity;
import bgmi.app.bgmi_android.R;
import bgmi.app.bgmi_android.models.Bangumi;
import bgmi.app.bgmi_android.utils.BGmiProperties;
import bgmi.app.bgmi_android.utils.ImageWorker;

public class BangumiViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "BangumiViewHolder";

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
        Log.i(TAG, "updateUI: " + bangumi.getBangumi_name());
        if (bangumiEpisodeLayout.getChildCount() > 0) {
            return;
        }

        String url = BGmiProperties.getInstance().bgmiBackendURL + bangumi.getCover();
        ImageWorker.context = bangumiImageView.getContext();
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
            Button episodeButton = new Button(itemView.getContext(), null);
            episodeButton.setText(key.toString());
            episodeButton.setOnClickListener(new BangumiPlayClickListener(path));

            bangumiEpisodeLayout.addView(episodeButton);
        }
    }

}


class BangumiPlayClickListener implements View.OnClickListener {
    private String path;

    BangumiPlayClickListener(String path) {
        this.path = path;
    }

    @Override
    public void onClick(View v) {
        String url = v.getContext().getSharedPreferences("bgmi_config", 0).getString("bgmi_url", "");
        if (url.equals("")) {
            Toast.makeText(v.getContext(), "No BGmi backend configured", Toast.LENGTH_LONG).show();
            return;
        }
        Uri uri = Uri.parse(url + "bangumi" + path);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
        v.getContext().startActivity(browserIntent);
    }
}