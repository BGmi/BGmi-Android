package bgmi.app.bgmi_android.holders;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import bgmi.app.bgmi_android.CalendarFragment;
import bgmi.app.bgmi_android.R;
import bgmi.app.bgmi_android.models.Bangumi;
import bgmi.app.bgmi_android.utils.BGmiManager;
import bgmi.app.bgmi_android.utils.BGmiProperties;
import bgmi.app.bgmi_android.utils.CallBack;

public class CalendarItemViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "CalendarItemViewHolder";

    private Context context;
    private TextView bangumiTextView;
    private Switch bangumiFollowedSwitch;
    private Fragment fragment;

    public CalendarItemViewHolder(Fragment fragment, View itemView) {
        super(itemView);
        this.fragment = fragment;
        bangumiTextView = itemView.findViewById(R.id.cal_item);
        bangumiFollowedSwitch = itemView.findViewById(R.id.cal_switch);
    }

    public void updateUI(String name) {
        bangumiTextView.setText(name);
        bangumiFollowedSwitch.setOnCheckedChangeListener(new SubscribeOnCheckedChangeListener(fragment, name));

        for (Bangumi bangumi: BGmiProperties.getInstance().followedBangumi) {
            if (bangumi.getBangumi_name().equals(name)) {
                bangumiFollowedSwitch.setChecked(true);
                break;
            }
        }
    }
}


class SubscribeOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener, CallBack<String> {
    private static final String TAG = "SubscribeOnCheckedChang";
    private String bangumiName;
    private CompoundButton button;
    private Fragment fragment;

    SubscribeOnCheckedChangeListener(Fragment fragment, String name) {
        this.fragment = fragment;
        this.bangumiName = name;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        button = buttonView;

        if (!buttonView.isPressed()) {
            return;
        }

        CalendarFragment calendarFragment = (CalendarFragment) fragment;
        SwipeRefreshLayout swipeRefreshLayout = calendarFragment.getSwipeRefreshLayout();
        swipeRefreshLayout.setRefreshing(true);

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
        CalendarFragment calendarFragment = (CalendarFragment) fragment;
        SwipeRefreshLayout swipeRefreshLayout = calendarFragment.getSwipeRefreshLayout();
        swipeRefreshLayout.setRefreshing(false);

        if (!errorString.equals("")) {
            Toast.makeText(button.getContext(), errorString, Toast.LENGTH_LONG).show();
            button.setChecked(!button.isChecked());
            return;
        }
        BGmiProperties.getInstance().refresh = true;
        Toast.makeText(button.getContext(),"Bangumi " + bangumiName + " has been (un)subscribed", Toast.LENGTH_LONG).show();
    }
}