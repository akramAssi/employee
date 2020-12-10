package com.example.employee;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class myNotification extends Service {

    private static final String Insert_Channel_ID = " EMPLOYEE_CHANNEL_ID";
    private static final String Delete_Channel_ID = " EMPLOYEE_CHANNEL_ID";
    private static final String Modify_Channel_ID = " EMPLOYEE_CHANNEL_ID";
    private static final AtomicInteger c = new AtomicInteger(0);
    private NotificationManager man;
    private PendingIntent pendingIntent;
    private boolean check = true;

    public static int getID() {
        return c.incrementAndGet();
    }

    private void createChannel(String name) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel;
            channel = new NotificationChannel(name, "Employee insert", NotificationManager.IMPORTANCE_HIGH);
            man = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            man.createNotificationChannel(channel);


        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (check) {
            createChannel("insert");
            NotificationCompat.Builder notefor;
            notefor = new NotificationCompat.Builder(getApplicationContext(), Insert_Channel_ID)
                    .setAutoCancel(true)
                    .setOngoing(false)
                    .setSmallIcon(R.drawable.employee)
                    .setColor(Color.BLACK);
            startForeground(10, notefor.build());
            check = false;
        }


        Intent notificationIntent = new Intent(this, MainActivity.class);
        pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        String action = intent.getAction();
        switch (Objects.requireNonNull(action)) {
            case storeService.ACTION_DELETE_EMPLOYEE: {

                pushNotification(Delete_Channel_ID, "Delete Employee",
                        "Delete '" + intent.getStringExtra(storeService.Search_name) + "' Successfully",
                        R.drawable.delete);
                break;
            }
            case storeService.ACTION_INSERT_EMPLOYEE: {
                char g = intent.getCharExtra(storeService.Search_gender, 'm');
                int icon = (g == 'm') ? R.drawable.man : R.drawable.woman;
                pushNotification(Insert_Channel_ID, "insert Employee",
                        "insert '" + intent.getStringExtra(storeService.Search_name) + "' Successfully",
                        icon);


                break;
            }
            case storeService.ACTION_MODIFY_EMPLOYEE: {

                pushNotification(Modify_Channel_ID, "modify Employee",
                        intent.getStringExtra(storeService.Search_name),
                        R.drawable.edit_notifiy);
                break;
            }
        }


        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        //  Return the communication channel to the service.
        return null;
    }


    private void pushNotification(String nameChannel, String title, String text, int icon) {
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.bigText(text);

        createChannel(nameChannel);
        NotificationCompat.Builder notefor;
        notefor = new NotificationCompat.Builder(getApplicationContext(), nameChannel)
                .setAutoCancel(true)
                .setOngoing(false)
                .setContentTitle(title)

                .setContentIntent(pendingIntent)
                .setStyle(bigTextStyle)
                .setSmallIcon(R.drawable.employee).setLargeIcon(BitmapFactory.decodeResource(getResources(), icon))
                .setColor(getColor(R.color.illuminatingEmerald));


        System.out.println("id_is :" + getID());
        man.notify(getID(), notefor.build());


    }

}
