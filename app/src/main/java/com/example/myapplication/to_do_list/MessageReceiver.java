package com.example.myapplication.to_do_list;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.myapplication.MainHomeActivity.MainContainActivity;
import com.example.myapplication.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessageReceiver extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        if (message.getNotification() != null) {
            showNotification(message.getNotification().getTitle(), message.getNotification().getBody());
        }
    }

    @Override
    public void onNewToken(@NonNull String token) {
        Log.e("FireToken", token);
        super.onNewToken(token);
    }

    private RemoteViews getNotificationDesign(String notificationTitle, String notificationBody) {
        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.to_do_list_notification);
        remoteViews.setTextViewText(R.id.notification_title, notificationTitle);
        remoteViews.setTextViewText(R.id.notification_body, notificationBody);
        //remoteViews.setImageViewResource(R.id.notification_icon, R.drawable.my_notes);
        return remoteViews;
    }

    private void showNotification(String notificationTitle, String notificationBody) {
        String channelID = "notification_channel";

        Intent intent = new Intent(this, MainContainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainContainActivity.class);
        stackBuilder.addNextIntent(intent);

        Intent intentEmailView = new Intent(this, ToDoListHomeActivity.class);
        intentEmailView.putExtra("EmailId", "you can Pass emailId here");
        stackBuilder.addNextIntent(intentEmailView);

//        Intent intent = new Intent(this, ToDoListHomeActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addNextIntentWithParentStack(intent);
        //stackBuilder.addParentStack(MainContainActivity.class);
        //stackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);
        //PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setSmallIcon(R.drawable.my_notes)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            notification = notification.setContent(getNotificationDesign(notificationTitle, notificationBody));
//        } else {}

        notification = notification.setContentTitle(notificationTitle)
                .setContentText(notificationBody)
                .setSmallIcon(R.drawable.my_notes);


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelID, "note app",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(0, notification.build());
    }
}
