package bgmi.app.bgmi_android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class AboutFragment extends Fragment {

    public AboutFragment() {
    }

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_about, container, false);
        TextView textView = view.findViewById(R.id.about_text);
        textView.setText(Html.fromHtml("<h1>BGmi Android</h1><br><br>Project URL: <a href=\"https://github.com/BGmi/BGmi-Android\">https://github.com/BGmi/BGmi-Android</a>", Html.FROM_HTML_MODE_COMPACT));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        return view;
    }

}
