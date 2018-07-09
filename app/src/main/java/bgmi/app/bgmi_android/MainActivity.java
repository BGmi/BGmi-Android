package bgmi.app.bgmi_android;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import bgmi.app.bgmi_android.utils.BGmiProperties;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "bgmi.app.bgmi_android.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences config = getSharedPreferences("bgmi_config", 0);
        String url = config.getString("bgmi_url", "");
        if (!url.equals("")) {
            Intent intent = new Intent(this, BangumiListActivity.class);
            startActivity(intent);
        }


    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, BangumiListActivity.class);
        EditText editText = findViewById(R.id.editText);
        BGmiProperties.getInstance().bgmiFrontEndURL = editText.getText().toString();

        SharedPreferences.Editor editor = getSharedPreferences("bgmi_config", 0).edit();
        editor.putString("bgmi_url", editText.getText().toString());
        editor.apply();

        startActivity(intent);
    }

}
