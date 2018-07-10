package bgmi.app.bgmi_android.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import bgmi.app.bgmi_android.R;


public class CalendarAdapter extends ArrayAdapter<String> {
    private ArrayList<String> mList;
    private String[] weekDays = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};

    public CalendarAdapter(@NonNull Context context, int resource, ArrayList<String> mList) {
        super(context, resource, mList);
        this.mList = mList;
    }

    public static <T> boolean contains(final T[] array, final T v) {
        for (final T e : array)
            if (e == v || v != null && v.equals(e))
                return true;

        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.content_calendar, parent, false);
        }
        TextView textView = convertView.findViewById(R.id.calendar_item);
        textView.setText(mList.get(position));
        ViewGroup.LayoutParams params = textView.getLayoutParams();

        if (contains(weekDays, mList.get(position))) {
            params.height = 145;
            textView.setLayoutParams(params);
            textView.setTextColor(Color.WHITE);
            textView.setBackgroundColor(0xFF018786);
            textView.setTextSize(21);
            textView.setPadding(20, 30, 10, 30);
        } else {
            params.height = 100;
            textView.setTextSize(15);
            textView.setLayoutParams(params);
            textView.setTextColor(Color.GRAY);
            textView.setBackgroundColor(Color.WHITE);
            textView.setPadding(40, 18, 20, 18);
        }

        return convertView;
    }
}
