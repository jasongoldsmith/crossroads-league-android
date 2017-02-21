package co.crossroadsapp.leagueoflegends;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import co.crossroadsapp.leagueoflegends.core.BattletagAlreadyTakenException;
import co.crossroadsapp.leagueoflegends.core.InvalidEmailProvided;
import co.crossroadsapp.leagueoflegends.core.LeagueLoginException;
import co.crossroadsapp.leagueoflegends.core.NoUserFoundException;
import co.crossroadsapp.leagueoflegends.data.UserData;
import co.crossroadsapp.leagueoflegends.utils.Constants;
import co.crossroadsapp.leagueoflegends.utils.Util;


public class SelectRegionActivity extends BaseActivity implements Observer, AdapterView.OnItemSelectedListener {

    private Spinner dropdown;
    private ControlManager mManager;
    private ArrayList<String> regionList;
    private ArrayAdapter<String> adapterConsole;
    private EditText summonerName;
    private TextView registerBtn;
    private Map<String, Object> hashMAp;
    private ImageView back;
    private int firstTimeKeyboardOpens=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_region);
        mManager = ControlManager.getmInstance();
        mManager.setCurrentActivity(this);

        dropdown = (Spinner) findViewById(R.id.summoner_region);
        regionList = new ArrayList<>();

        summonerName = (EditText) findViewById(R.id.signup_name);

        registerBtn = (TextView) findViewById(R.id.register_btn);

        back = (ImageView) findViewById(R.id.region_backbtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                gotoMainActivity();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(summonerName.getText()!=null && summonerName.getText().toString().length()>2) {
                    String selectedRegion = String.valueOf(dropdown.getSelectedItem());
                    if(!selectedRegion.isEmpty()) {
                        firstTimeKeyboardOpens=0;
                        showProgressBar();
                        // TODO: 2/13/17 check the cause of control manager current activity getting null
                        if(mManager==null || mManager.getCurrentActivity()==null) {
                            mManager = ControlManager.getmInstance();
                            mManager.setCurrentActivity(SelectRegionActivity.this);
                        }
                        RequestParams params = new RequestParams();
                        params.put("consoleId", summonerName.getText().toString());
                        params.put("region", hashMAp.get(selectedRegion).toString());
                        mManager.addOtherConsole(params, summonerName.getText().toString());
                    }
                } else {
                    showError(getString(R.string.summoername_short_error));
                }
            }
        });

        final View contentView = this.findViewById(android.R.id.content);
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                contentView.getWindowVisibleDisplayFrame(r);
                int screenHeight = contentView.getRootView().getHeight();

                // r.bottom is the position above soft keypad or device button.
                // if keypad is shown, the r.bottom is smaller than that before.
                int keypadHeight = screenHeight - r.bottom;

                if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                    // keyboard is opened
                    //heroImg.setVisibility(View.GONE);
                }
                else {
                    // keyboard is closed
                    //heroImg.setVisibility(View.VISIBLE);
                    if(firstTimeKeyboardOpens>1) {
                        onBackPressed();
                    }else {
                        firstTimeKeyboardOpens++;
                    }
                }
            }
        });

        dropdown.setOnItemSelectedListener(this);
        // Set adapter for console selector
        updateConsoleListUserDrawer();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setTRansparentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onStop() {
        if(mManager!=null && mManager.getCurrentActivity()!=null && mManager.getCurrentActivity() instanceof SelectRegionActivity) {
            mManager.setCurrentActivity(null);
        }
        hideProgressBar();
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mManager==null) {
            mManager = ControlManager.getmInstance();
        }
        mManager.setCurrentActivity(this);
    }

    public void showError(String err) {
        //firstTimeKeyboardOpens=0;
        hideProgressBar();
        setErrText(err);
        showKeyboard();
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                showKeyboard();
//            }
//        }, 500);
    }

    private void showKeyboard() {
        summonerName.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(SelectRegionActivity.this.getCurrentFocus().getWindowToken(),0);
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
            adapterConsole = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, regionList){

                int FONT_STYLE = Typeface.BOLD;

                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);

                    ((TextView) v).setTypeface(Typeface.SANS_SERIF, FONT_STYLE);
                    ((TextView) v).setTextColor(
                            getResources().getColorStateList(R.color.trimbe_white)
                    );
                    ((TextView) v).setGravity(Gravity.CENTER);

                    ((TextView) v).setPadding(Util.dpToPx(0, SelectRegionActivity.this), 0, 0, 0);
                    ((TextView) v).setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                    ((TextView) v).setText(((TextView) v).getText());

//                    if (((TextView) v).getText().toString().equalsIgnoreCase(Constants.CONSOLEXBOXONESTRG) || ((TextView) v).getText().toString().equalsIgnoreCase(Constants.CONSOLEXBOX360STRG)) {
//                        imgConsole.setImageResource(R.drawable.icon_xboxone_consolex);
//                    } else {
//                        imgConsole.setImageResource(R.drawable.icon_psn_consolex);
//                    }

                    return v;
                }

                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    return getCustomView(position, convertView, parent, regionList);
                }
            };
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

    private View getCustomView(int position, View convertView, ViewGroup parent, ArrayList<String> regionList) {
        LayoutInflater inflater=getLayoutInflater();
        View row=inflater.inflate(R.layout.fragment_checkpoint, parent, false);
        CardView card = (CardView) row.findViewById(R.id.activity_checkpoint_card);

        card.setVisibility(View.VISIBLE);
        RelativeLayout cardLayout = (RelativeLayout) row.findViewById(R.id.activity_checkpoint_card_frag);
        cardLayout.setBackgroundColor(getResources().getColor(R.color.consoleAddColor));
        card.setCardBackgroundColor(getResources().getColor(R.color.freelancer_background));
        TextView label = (TextView) row.findViewById(R.id.activity_checkpoint_text);
        if (regionList != null) {
            label.setText(regionList.get(position));
        }

        return row;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void update(Observable observable, Object data) {
        if(data!=null) {
            if (data instanceof BattletagAlreadyTakenException) {
                String userTag = ((LeagueLoginException) data).getUserTag();
                Intent intent = new Intent(this, GamertagErrorScreen.class);
                Bundle b = new Bundle();
                b.putInt("key", 1);//Your id
                b.putString("user",userTag);
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
            } else if (data instanceof NoUserFoundException) {
                String userTag = ((LeagueLoginException) data).getUserTag();
                Intent intent = new Intent(this, GamertagErrorScreen.class);
                Bundle b = new Bundle();
                b.putInt("key", 2);//Your id
                b.putString("user",userTag);
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
            } else {
                hideProgressBar();
                hideKeyboard();
                if (data != null) {
                    UserData ud = (UserData) data;
                    if (ud != null && ud.getUserId() != null) {
                        if (ud.getConsoleType() != null || !ud.getConsoleType().isEmpty()) {
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
    }
}
