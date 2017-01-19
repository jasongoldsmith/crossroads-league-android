package co.crossroadsapp.leagueoflegends;

import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import co.crossroadsapp.leagueoflegends.utils.Constants;
import co.crossroadsapp.leagueoflegends.utils.Util;

public class SelectRegionActivity extends Activity {

    private Spinner dropdown;
    private ControlManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_region);

        mManager = ControlManager.getmInstance();

        dropdown = (Spinner) findViewById(R.id.console_spinner);

        //dropdown.setOnItemSelectedListener(this);
        // Set adapter for console selector
        updateConsoleListUserDrawer();
    }

    private void updateConsoleListUserDrawer() {
//        //final ArrayList<String> consoleItems = new ArrayList<String>();
//        consoleItems = Util.getCorrectConsoleName(mManager.getConsoleList());
//        //final ArrayList<String> consoleItems = mManager.getConsoleList();
//        if(needToAdd(consoleItems)) {
//            //consoleItems.add("Add Console");
//        }
//        if (consoleItems.size()>1) {
//            down_arw_img.setVisibility(View.VISIBLE);
//            adapterConsole = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, consoleItems) {
//
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
//                    ((TextView) v).setPadding(Util.dpToPx(0, ListActivityFragment.this), 0, 0, 0);
//                    ((TextView) v).setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
//                    ((TextView) v).setText(((TextView) v).getText());
//
//                    if (((TextView) v).getText().toString().equalsIgnoreCase(Constants.CONSOLEXBOXONESTRG) || ((TextView) v).getText().toString().equalsIgnoreCase(Constants.CONSOLEXBOX360STRG)) {
//                        imgConsole.setImageResource(R.drawable.icon_xboxone_consolex);
//                    } else {
//                        imgConsole.setImageResource(R.drawable.icon_psn_consolex);
//                    }
//
//                    return v;
//                }
//
//                public View getDropDownView(int position, View convertView, ViewGroup parent) {
//                    return getCustomView(position, convertView, parent, consoleItems);
//                }
//            };
//            adapterConsole.setDropDownViewResource(R.layout.empty_layout);
//            dropdown.setAdapter(adapterConsole);
//            adapterConsole.notifyDataSetChanged();
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
