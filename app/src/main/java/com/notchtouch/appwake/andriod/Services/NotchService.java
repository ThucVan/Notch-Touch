package com.notchtouch.appwake.andriod.Services;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.notchtouch.appwake.andriod.R;
import com.notchtouch.appwake.andriod.Utils.Functions;

public class NotchService extends Service {

    private static final String CHANNEL_ID = "MyForegroundServiceChannel";
    private static final int NOTIFICATION_ID = 1;

    private View overlay;
    private WindowManager windowManager;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("reveiver_check", "service called - 1");
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("My Foreground Service")
                .setContentText("Running in the foreground")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build();
        startForeground(NOTIFICATION_ID, notification);

        overlay = View.inflate(getApplicationContext(), R.layout.overlay_service_layout, null);
        overlay.findViewById(R.id.button_notch).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("check", "notch area clicked");
                return false;
            }
        });

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |
                        WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR,
                PixelFormat.TRANSLUCENT
        );
        /*layoutParams.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING;
        layoutParams.layoutInDisplayCutoutMode=WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        overlay.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);*/
        overlay.setBackgroundColor(Color.BLUE);
        layoutParams.gravity = Gravity.START | Gravity.TOP;
//        overlay.setVisibility(View.GONE);
        overlay.setFitsSystemWindows(false);
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(overlay, layoutParams);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (overlay != null && windowManager != null) windowManager.removeView(overlay);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "My Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
        );

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(channel);
        }
    }
}