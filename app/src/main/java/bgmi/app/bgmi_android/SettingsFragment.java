package bgmi.app.bgmi_android;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import bgmi.app.bgmi_android.utils.BGmiProperties;


public class SettingsFragment extends Fragment {
    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
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
        EditText urlText = view.findViewById(R.id.reset_backend_url);
        urlText.setText(url);

        EditText tokenText = view.findViewById(R.id.admin_token);
        tokenText.setText(sp.getString("bgmi_token", ""));

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        EditText urlText = getView().findViewById(R.id.reset_backend_url);
        String url = urlText.getText().toString();

        EditText tokenText = getView().findViewById(R.id.admin_token);
        String token = tokenText.getText().toString();

        if (!url.endsWith("/")) {
            url += "/";
        }

        BGmiProperties.getInstance().bgmiBackendURL = url;
        BGmiProperties.getInstance().adminToken = token;

        SharedPreferences.Editor editor = getActivity().getSharedPreferences("bgmi_config", 0).edit();
        editor.putString("bgmi_url", url);
        editor.putString("bgmi_token", token);
        editor.apply();

        Toast.makeText(getActivity(), "Config saved successfully", Toast.LENGTH_SHORT).show();
        return true;
    }


}
