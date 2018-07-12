package bgmi.app.bgmi_android.holders;

import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import bgmi.app.bgmi_android.R;
import bgmi.app.bgmi_android.models.Bangumi;
import bgmi.app.bgmi_android.utils.BGmiManager;
import bgmi.app.bgmi_android.utils.BGmiProperties;
import bgmi.app.bgmi_android.utils.CallBack;

public class CalendarItemViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "CalendarItemViewHolder";

    private TextView bangumiTextView;
    private Switch bangumiFollowedSwitch;

    public CalendarItemViewHolder(View itemView) {
        super(itemView);
        bangumiTextView = itemView.findViewById(R.id.cal_item);
        bangumiFollowedSwitch = itemView.findViewById(R.id.cal_switch);
    }

    public void updateUI(String name) {
        bangumiTextView.setText(name);
        bangumiFollowedSwitch.setOnCheckedChangeListener(new SubscribeOnCheckedChangeListener(name));

        for (Bangumi bangumi: BGmiProperties.getInstance().followedBangumi) {
            if (bangumi.getBangumi_name().equals(name)) {
                bangumiFollowedSwitch.setChecked(true);
                break;
            }
        }
    }
}


class SubscribeOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener, CallBack<String> {
    private String bangumiName;
    private CompoundButton button;

    SubscribeOnCheckedChangeListener(String name) {
        this.bangumiName = name;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        button = buttonView;

        if (!buttonView.isPressed()) {
            return;
        }

        if (BGmiProperties.getInstance().adminToken == null || BGmiProperties.getInstance().adminToken.equals("")) {
            Toast.makeText(button.getContext(),"No ADMIN_TOKEN set", Toast.LENGTH_LONG).show();
            return;
        }

        if (isChecked) {
            BGmiManager.getInstance().subscribe(button.getContext(), this, bangumiName, BGmiProperties.getInstance().pageAddURL);
        } else {
            BGmiManager.getInstance().subscribe(button.getContext(), this, bangumiName, BGmiProperties.getInstance().pageDeleteURL);
        }
    }

    @Override
    public void callback(String callbackData, String errorString) {
        if (!errorString.equals("")) {
            Toast.makeText(button.getContext(), errorString, Toast.LENGTH_LONG).show();
            button.setChecked(!button.isChecked());
            return;
        }
        BGmiProperties.getInstance().refresh = true;
        Toast.makeText(button.getContext(),"Bangumi " + bangumiName + " has been (un)subscribed", Toast.LENGTH_LONG).show();
    }
}