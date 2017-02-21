package co.crossroadsapp.leagueoflegends;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GamertagErrorScreen extends BaseActivity {

    String user=null;
    ImageView back;
    private TextView contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamertag_error_screen);

        TextView subtext = (TextView) findViewById(R.id.error_message);
        TextView userText = (TextView) findViewById(R.id.message_title);
        back = (ImageView) findViewById(R.id.back_btn);
        contact = (TextView) findViewById(R.id.contact_us);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        CrashReport.class);
                startActivity(intent);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Bundle b = getIntent().getExtras();
        int value = -1; // or other values
        if(b != null) {
            value = b.getInt("key");
            user = b.getString("user");
        }

        String textMsg = null;

        switch (value) {
            case 1:
                textMsg = "An account already exists for that summoner name in . Please check for any typos.\n\n If you believe someone is using your summoner name, let us know using the contact form below!";
                break;
            case 2:
                textMsg = "We couldn’t find that " + user +". Please check for any typos.\n\nIf this issue persists, use the contact form below and we’ll get back to you!";
                break;
            default:
                textMsg = getString(R.string.not_found_message);
                break;
        }

        subtext.setText(textMsg);
        userText.setText(user);
    }
}
