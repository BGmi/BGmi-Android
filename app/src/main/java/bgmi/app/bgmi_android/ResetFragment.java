package bgmi.app.bgmi_android;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bgmi.app.bgmi_android.utils.BGmiProperties;


public class ResetFragment extends Fragment {
    public ResetFragment() {
        // Required empty public constructor
    }

    public static ResetFragment newInstance() {
        return new ResetFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset, container, false);

        SharedPreferences sp = getActivity().getSharedPreferences("bgmi_config", 0);
        String url = sp.getString("bgmi_url", "");
        EditText editText = view.findViewById(R.id.reset_backend_url);
        editText.setText(url);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        EditText editText = getView().findViewById(R.id.reset_backend_url);
        String url = editText.getText().toString();

        if (!url.endsWith("/")) {
            url += "/";
        }

        BGmiProperties.getInstance().bgmiBackendURL = url;
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("bgmi_config", 0).edit();
        editor.putString("bgmi_url", url);
        editor.apply();

        Toast.makeText(getActivity(), "Config saved successfully", Toast.LENGTH_SHORT).show();
        return true;
    }


}
