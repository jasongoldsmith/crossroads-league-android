package co.crossroadsapp.leagueoflegends;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import co.crossroadsapp.leagueoflegends.data.UserData;
import co.crossroadsapp.leagueoflegends.utils.Util;

import com.loopj.android.http.RequestParams;

import java.util.Observable;
import java.util.Observer;

public class ChangePassword extends BaseActivity implements Observer {

    private ImageView backBtn;
    private EditText oldPswrd;
    private CardView setPswrd;
    private EditText newPswrd;
    private ControlManager mManager;
    private ProgressDialog dialog;
    String userId;
    String newP;
    String newE;
    private EditText oldEmail;
    private EditText newEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        mManager = ControlManager.getmInstance();
        mManager.setCurrentActivity(this);

        backBtn = (ImageView) findViewById(R.id.back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setPswrd = (CardView) findViewById(R.id.send_change_pswrd);

        oldPswrd = (EditText) findViewById(R.id.pswrd_edit);
        newPswrd = (EditText) findViewById(R.id.pswrd_edit_new);
        oldEmail = (EditText) findViewById(R.id.email_edit);
        newEmail = (EditText) findViewById(R.id.email_edit_new);

        //hint configuration
        oldPswrd.setTypeface(Typeface.DEFAULT);
        oldPswrd.setTransformationMethod(new PasswordTransformationMethod());
        newPswrd.setTypeface(Typeface.DEFAULT);
        newPswrd.setTransformationMethod(new PasswordTransformationMethod());

//        newPswrd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
//                    // the user is done typing.
//                    setPswrd.requestFocus();
//                    setPswrd.performClick();
//                }
//                return false;
//            }
//        });

        newEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                    // the user is done typing.
                    setPswrd.requestFocus();
                    setPswrd.performClick();
                }
                return false;
            }
        });

        dialog = new ProgressDialog(this);

        if(mManager!=null && mManager.getUserData()!=null) {
            UserData ud = mManager.getUserData();
            userId = ud.getUserId();
        }

        setPswrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userId != null) {
                    v.setEnabled(false);
                    String oldP = oldPswrd.getText().toString();
                    String oldE = oldEmail.getText().toString();
                    newP = newPswrd.getText().toString();
                    newE = newEmail.getText().toString();
                    if ((oldP != null && newP != null) || (oldE != null && newE != null)) {
                        if ((!newP.isEmpty() && !oldP.isEmpty()) || (!newE.isEmpty() && !oldE.isEmpty())) {
                            RequestParams params = new RequestParams();
                            params.put("oldPassWord", oldP);
                            params.put("newPassWord", newP);
                            params.put("oldEmail", oldE);
                            params.put("newEmail", newE);
                            showProgressBar();
//                            dialog.show();
//                            dialog.setCancelable(false);
//                            dialog.setCanceledOnTouchOutside(false);
                            mManager.postChangePassword(ChangePassword.this, params);
                        } else {
                            showError(getResources().getString(R.string.password_short));
                        }
                    }
                }
            }
        });
    }

    public void showError(String err) {
        //dialog.dismiss();
        hideProgressBar();
        setPswrd.setEnabled(true);
        setErrText(err);
    }

    @Override
    public void update(Observable observable, Object data) {
        //dialog.dismiss();
        hideProgressBar();
        if(newP!=null && (!newP.isEmpty())) {
            Util.setDefaults("password", newP, getApplicationContext());
        }
        if(newE!=null && (!newE.isEmpty())) {
            Util.setDefaults("user", newE, getApplicationContext());
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
