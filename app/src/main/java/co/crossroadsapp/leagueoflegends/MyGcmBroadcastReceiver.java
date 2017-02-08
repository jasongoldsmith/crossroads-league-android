package co.crossroadsapp.leagueoflegends;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import co.crossroadsapp.leagueoflegends.utils.Constants;

import co.crossroadsapp.leagueoflegends.utils.TravellerLog;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by sharmha on 3/14/16.
 */
public class MyGcmBroadcastReceiver extends FirebaseMessagingService {

    private static final String GCM_RECEIVE_INTENT = "com.google.android.c2dm.intent.RECEIVE";
    //private final String TAG = this.getClass().getSimpleName();

    private static final String TAG = "Firebase_MSG";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        if(remoteMessage!=null) {
            Map<String, String> data = remoteMessage.getData();
            String alert = data.get("message");
            String payload = data.get("payload"); //remoteMessage.getData().toString();
            ControlManager cm = ControlManager.getmInstance();
            if (isAppRunning(getApplicationContext())) {
                final Intent in = new Intent(cm.getCurrentActivity(), NotificationService.class);
                in.putExtra("payload", payload);
                in.putExtra("message", alert);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                cm.getCurrentActivity().startService(in);
//                Thread t = new Thread(){
//                    public void run(){
//                        context.startService(in);
//                    }
//                };
//                t.start();
            } else {
                sendNotification(alert, payload);
//                Intent notificationIntent = createNotificationIntent(getApplicationContext(), alert, payload);
//                PendingIntent resultIntent = PendingIntent.getActivity(getApplicationContext(), Constants.INTENT_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(cm.getCurrentActivity());
//                if (alert != null) {
//                    mBuilder.setAutoCancel(true).setSmallIcon(R.drawable.cr_lol_app_icon).setContentIntent(resultIntent).setContentTitle(getResources().getString(R.string.app_name)).setStyle(new NotificationCompat.BigTextStyle().bigText(alert)).setContentText(alert);
//                } else {
//                    mBuilder.setSmallIcon(R.drawable.cr_lol_app_icon).setContentIntent(resultIntent).setContentText("New Message Received").setContentTitle(getResources().getString(R.string.app_name));
//                }
//                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                mNotificationManager.notify(UUID.randomUUID().hashCode(), mBuilder.build());
            }
            //sendNotification(remoteMessage.getNotification().getBody());
            Log.d(TAG, "From: " + remoteMessage.getFrom());
            Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        }
    }

//    @Override
//    public void onReceive(final Context context, Intent intent) {
//        TravellerLog.w(TAG, "received from gcm");
//        if (intent.getAction().equals(GCM_RECEIVE_INTENT)) {
//            String pushDataString = intent.getStringExtra("data");
//            Bundle i = intent.getExtras();
//            JSONObject pushData = null;
//            String alert = null;
//            String payload = null;
//            if (i != null) {
//                    //pushData = new JSONObject(pushDataString);
//                    alert = i.getString("message");
//                    payload = i.getString("payload");
//            }
//            if (isAppRunning(context)) {
//                final Intent in = new Intent(context, NotificationService.class);
//                in.putExtra("payload", payload);
//                in.putExtra("message", alert);
//                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startService(in);
////                Thread t = new Thread(){
////                    public void run(){
////                        context.startService(in);
////                    }
////                };
////                t.start();
//            } else {
////                Intent i = new Intent(context, UpdateCacheService.class);
////                i.putExtra("message", payload);
////                i.putExtra("alert", alert);
////                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                context.startService(i);
//                //GCMResponse gcmMessage = Utils.readGCMResponse(payload);
////                if( gcmMessage == null )
////                {
////                    return;
////                }
//                //int notificationCounter = Utils.readNewNotificationCount();
//                Intent notificationIntent = createNotificationIntent(context, alert, payload);
//                PendingIntent resultIntent = PendingIntent.getActivity(context, Constants.INTENT_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
//                if(alert!=null) {
//                    mBuilder.setAutoCancel(true).setSmallIcon(R.drawable.img_traveler_badge_icon).setContentIntent(resultIntent).setContentTitle(context.getResources().getString(R.string.app_name)).setStyle(new NotificationCompat.BigTextStyle().bigText(alert)).setContentText(alert);
//                }else{
//                    mBuilder.setSmallIcon(R.drawable.img_traveler_badge_icon).setContentIntent(resultIntent).setContentText("New Message Received").setContentTitle(context.getResources().getString(R.string.app_name));
//                }
//                NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//                mNotificationManager.notify(UUID.randomUUID().hashCode(), mBuilder.build());
//                //Utils.storeNewNotificationCount(notificationCounter);
//            }
//        }
//    }

    private void sendNotification(String alert, String payload) {
        Intent intent = createNotificationIntent(getApplicationContext(), alert, payload);
        //Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, Constants.INTENT_ID, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icon_notification)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText(alert)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(UUID.randomUUID().hashCode(), notificationBuilder.build());
    }

    private Intent createNotificationIntent(Context ctxt, String alert, String payload) {
        // notificationIntent
        Intent notificationIntent = new Intent(ctxt, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // mark intent as notification
        notificationIntent.putExtra(Constants.TRAVELER_NOTIFICATION_INTENT, Constants.TRAVELER_NOTIFICATION_INTENT);
        // SplashScreen Intent
        Intent launchIntent = new Intent(ctxt, MainActivity.class);
        if (alert != null && alert.length() > 0 && payload != null && payload.length() > 0) {
            Intent contentIntent = new Intent();
            contentIntent.putExtra("message", payload);
            contentIntent.putExtra("alert", alert);
            contentIntent.putExtra("id", Constants.INTENT_ID);
            launchIntent.putExtra(Constants.NOTIFICATION_INTENT_CHANNEL, contentIntent);
        }
        notificationIntent.putExtra(Constants.TRAVELER_NOTIFICATION_INTENT, launchIntent);
        return notificationIntent;
    }

    public boolean isAppRunning(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> services = activityManager.getRunningTasks(Integer.MAX_VALUE);
        if (services != null && services.size() > 0 && services.get(0).topActivity.getPackageName().toString()
                .equalsIgnoreCase(context.getPackageName().toString())) {
            return true;
        }
        return false;
    }
}
