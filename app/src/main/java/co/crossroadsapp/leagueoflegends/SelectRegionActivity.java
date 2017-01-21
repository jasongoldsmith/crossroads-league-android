package co.crossroadsapp.leagueoflegends;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import co.crossroadsapp.leagueoflegends.data.UserData;
import co.crossroadsapp.leagueoflegends.utils.Constants;
import co.crossroadsapp.leagueoflegends.utils.Util;


public class SelectRegionActivity extends BaseActivity implements Observer, AdapterView.OnItemSelectedListener {

    private Spinner dropdown;
    private ControlManager mManager;
    private ArrayList<String> regionList;
    private ArrayAdapter<String> adapterConsole;
    private EditText summonerName;
    private ImageView registerBtn;
    private Map<String, Object> hashMAp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_region);

        mManager = ControlManager.getmInstance();
        mManager.setCurrentActivity(this);

        dropdown = (Spinner) findViewById(R.id.summoner_region);
        regionList = new ArrayList<>();

        summonerName = (EditText) findViewById(R.id.signup_name);

        registerBtn = (ImageView) findViewById(R.id.register_btn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(summonerName.getText()!=null && !summonerName.getText().toString().isEmpty()) {
                    String selectedRegion = String.valueOf(dropdown.getSelectedItem());
                    if(!selectedRegion.isEmpty()) {
                        RequestParams params = new RequestParams();
                        params.put("consoleId", summonerName.getText().toString());
                        params.put("region", hashMAp.get(selectedRegion).toString());
                        mManager.addOtherConsole(params);
                    }
                }
            }
        });

        dropdown.setOnItemSelectedListener(this);
        // Set adapter for console selector
        updateConsoleListUserDrawer();
    }

    public void showError(String err) {
        setErrText(err);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        gotoMainActivity();
    }

    private void gotoMainActivity() {
        Intent signinIntent = new Intent(getApplicationContext(),
                MainActivity.class);
        startActivity(signinIntent);
        finish();
    }

    private void updateConsoleListUserDrawer() {
        hashMAp = mManager.getRegions();
        for (String key : hashMAp.keySet()) {
            regionList.add(key);
        }
//        //final ArrayList<String> consoleItems = new ArrayList<String>();
//        consoleItems = Util.getCorrectConsoleName(mManager.getConsoleList());
//        //final ArrayList<String> consoleItems = mManager.getConsoleList();
//        if(needToAdd(consoleItems)) {
//            //consoleItems.add("Add Console");
//        }
        if (regionList.size() > 0) {
            //down_arw_img.setVisibility(View.VISIBLE);
            adapterConsole = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, regionList);

//                int FONT_STYLE = Typeface.BOLD;
//
//                public View getView(int position, View convertView, ViewGroup parent) {
//                    View v = super.getView(position, convertView, parent);
//
//                    ((TextView) v).setTypeface(Typeface.SANS_SERIF, FONT_STYLE);
//                    ((TextView) v).setTextColor(
//                            getResources().getColorStateList(R.color.trimbe_white)
//                    );
//                    ((TextView) v).setGravity(Gravity.CENTER);
//
//                    ((TextView) v).setPadding(Util.dpToPx(0, SelectRegionActivity.this), 0, 0, 0);
//                    ((TextView) v).setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
//                    ((TextView) v).setText(((TextView) v).getText());
//
////                    if (((TextView) v).getText().toString().equalsIgnoreCase(Constants.CONSOLEXBOXONESTRG) || ((TextView) v).getText().toString().equalsIgnoreCase(Constants.CONSOLEXBOX360STRG)) {
////                        imgConsole.setImageResource(R.drawable.icon_xboxone_consolex);
////                    } else {
////                        imgConsole.setImageResource(R.drawable.icon_psn_consolex);
////                    }
//
//                    return v;
//                }
//
//                public View getDropDownView(int position, View convertView, ViewGroup parent) {
//                    //return getCustomView(position, convertView, parent, regionList);
//                }
//            };
                adapterConsole.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dropdown.setAdapter(adapterConsole);
                adapterConsole.notifyDataSetChanged();
//        } else {
//            if(consoleItems.get(0).equalsIgnoreCase(Constants.CONSOLEXBOXONESTRG)) {
//                imgConsole.setImageResource(R.drawable.icon_xboxone_consolex);
//            } else if (consoleItems.get(0).equalsIgnoreCase(Constants.CONSOLEPS4STRG)) {
//                imgConsole.setImageResource(R.drawable.icon_psn_consolex);
//            }
//            down_arw_img.setVisibility(View.GONE);
//            consoleText.setText(consoleItems.get(0).toString());
//        }
            }
        }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void update(Observable observable, Object data) {
        //decide activity to open
        //regIntent = mManager.decideToOpenActivity(localPushEvent);

        if (data!=null) {
            UserData ud = (UserData) data;
            if (ud != null && ud.getUserId() != null) {
                if(ud.getConsoleType()!=null || !ud.getConsoleType().isEmpty()) {
                    Util.setDefaults("consoleType", ud.getConsoleType(), getApplicationContext());
                }
            }
            Intent regIntent;
            regIntent = new Intent(getApplicationContext(),
                    ListActivityFragment.class);
            //clear invitation req params
//        if(invitationRp!=null) {
//            invitationRp.clearRp();
//        }
            startActivity(regIntent);
            finish();
        }
    }
}
