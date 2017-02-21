package co.crossroadsapp.leagueoflegends;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;

import java.util.Observable;
import java.util.Observer;

import co.crossroadsapp.leagueoflegends.data.UserData;

public class AddNewActivity extends BaseActivity implements Observer {

    private ControlManager mCntrlMngr;
    private UserData user;
    private ImageView back;
    private ImageView exoticAct;
    private ImageView questAct;
    private ImageView storyAct;
    private TextView patrolAct;
    private TextView strikeAct;
    private TextView crucibleAct;
    private TextView arenaAct;
    private TextView raiddAct;
    private TextView featuredAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        setTRansparentStatusBar();

        mCntrlMngr = ControlManager.getmInstance();
        mCntrlMngr.setCurrentActivity(this);
        //mCntrlMngr.getAllActivities(this);
        boolean ads= false;
        String adP=null;

        Bundle b = getIntent().getExtras();
        //user = b.getParcelable("userdata");
        if(b!=null) {
            if (b.containsKey("adcard")) {
                ads = b.getBoolean("adcard");
            }
            if (b.containsKey("adCardId")) {
                adP = b.getString("adCardId");
            }
        }

        user = mCntrlMngr.getUserData();

        checkIfAdFlow(ads, adP);

        back = (ImageView) findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Raid
        featuredAct = (TextView) findViewById(R.id.first_act);
        featuredAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //featuredAct.setImageResource(R.drawable.img_featured_selected);
                callPostGetActivity("PvP, Summoner's Rift");
            }
        });

        raiddAct = (TextView) findViewById(R.id.second_act);
        raiddAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //raiddAct.setImageResource(R.drawable.img_raids_selected);
                callPostGetActivity("PvP, Twisted Treeline");
            }
        });

        arenaAct = (TextView) findViewById(R.id.third_act);
        arenaAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //arenaAct.setImageResource(R.drawable.img_arena_selected);
                callPostGetActivity("PvP, ARAM");
            }
        });

        crucibleAct = (TextView) findViewById(R.id.fourth_act);
        crucibleAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //crucibleAct.setImageResource(R.drawable.img_cruicible_selected);
                callPostGetActivity("PvP, Rotating");
            }
        });

        strikeAct = (TextView) findViewById(R.id.fifth_act);
        strikeAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //strikeAct.setImageResource(R.drawable.img_strikes_selected);
                callPostGetActivity("AI, Summoner's Rift");
            }
        });

        patrolAct = (TextView) findViewById(R.id.sixth_act);
        patrolAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //patrolAct.setImageResource(R.drawable.img_patrol_selected);
                callPostGetActivity("AI, Twisted Treeline");
            }
        });

//        storyAct = (ImageView) findViewById(R.id.seventh_act);
//        storyAct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                storyAct.setImageResource(R.drawable.img_story_selected);
//                callPostGetActivity("Story");
//            }
//        });
//
//        questAct = (ImageView) findViewById(R.id.eigth_act);
//        questAct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                questAct.setImageResource(R.drawable.img_quest_selected);
//                callPostGetActivity("Quest");
//            }
//        });
//
//        exoticAct = (ImageView) findViewById(R.id.ninth_act);
//        exoticAct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                exoticAct.setImageResource(R.drawable.img_exotic_selected);
//                callPostGetActivity("Exotic");
//            }
//        });
        //todo handle adscard here
//        if(b.containsKey("adsCardId")) {
//            adcardEventId = b.getString("adsCardId");
//        }
    }

    private void setButtonDefaultStates() {
//        exoticAct.setImageResource(R.drawable.img_exotic);
//        questAct.setImageResource(R.drawable.img_quest);
//        storyAct.setImageResource(R.drawable.img_story);
//        patrolAct.setImageResource(R.drawable.img_patrol);
//        strikeAct.setImageResource(R.drawable.img_strikes);
//        crucibleAct.setImageResource(R.drawable.img_cruicible);
//        arenaAct.setImageResource(R.drawable.img_arena);
//        raiddAct.setImageResource(R.drawable.img_raids);
//        featuredAct.setImageResource(R.drawable.img_featured);
    }

    private void callPostGetActivity(String type) {
        showProgressBar();
        RequestParams rp = new RequestParams();
        rp.add("aType", type);
        rp.add("includeTags", "true");
        mCntrlMngr.postGetActivityList(rp);
    }

    private void checkIfAdFlow(boolean ads, String adP) {
        if(ads) {
            if(adP!=null) {
                //start new activity for add event creation
                Intent regIntent = new Intent(AddNewActivity.this,
                        AddFinalActivity.class);
                //regIntent.putExtra("userdata", user);
                regIntent.putExtra("adcard", ads);
                regIntent.putExtra("adCardId", adP);
                startActivity(regIntent);
            }
        }
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

    public void showError(String err) {
        hideProgressBar();
        setErrText(err);
        setButtonDefaultStates();
    }


    @Override
    public void update(Observable observable, Object data) {
        hideProgressBar();
        if(mCntrlMngr.getCurrentActivityList()!=null && !mCntrlMngr.getCurrentActivityList().isEmpty()) {
            //start new activity for add event creation
            Intent regIntent = new Intent(AddNewActivity.this,
                    AddFinalActivity.class);
            //regIntent.putExtra("userdata", user);
            startActivity(regIntent);
        }
    }

    private void launchListActivityAndFinish() {
        Intent i=new Intent (this, ListActivityFragment.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mCntrlMngr.setCurrentActivity(this);
        setButtonDefaultStates();
    }

    @Override
    public void onStop() {
        if(mCntrlMngr!=null && mCntrlMngr.getCurrentActivity()!=null && mCntrlMngr.getCurrentActivity() instanceof AddNewActivity) {
            mCntrlMngr.setCurrentActivity(null);
        }
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        launchListActivityAndFinish();
    }
}
