package com.buyi.wechatmoney.state;

import android.accessibilityservice.AccessibilityService;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.os.PowerManager;
import android.view.accessibility.AccessibilityEvent;

import java.util.List;

/**
 * Created by buyi on 15/12/9.
 */
public class StateMessageComing implements State {
    //  点击含有[微信红包]的通知，进入到微信信息流
    @Override
    public void click(AccessibilityService context, AccessibilityEvent event, WeChatLuckMoney money) {
        System.out.println("StateMessageComing");
        List<CharSequence> texts = event.getText();
        for (CharSequence text : texts) {
            String content = text.toString();
            if (content.contains("[微信红包]")) {
                keepScreen(context);
                // 监听到微信红包的notification，打开通知
                if (event.getParcelableData() != null && event.getParcelableData() instanceof Notification) {
                    Notification notification = (Notification) event.getParcelableData();
                    PendingIntent pendingIntent = notification.contentIntent;
                    try {
                        pendingIntent.send();
                    } catch (PendingIntent.CanceledException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        // 到这里，已进入到微信列表界面
        money.setState(money.moneyOrigin);
    }

    @Override
    public void back(Context context, WeChatLuckMoney money) {

    }

    // 解锁屏幕
    private void keepScreen (Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock mWakelock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |PowerManager.SCREEN_DIM_WAKE_LOCK, "SimpleTimer");
        mWakelock.acquire();
        mWakelock.release();

        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("");
        keyguardLock.disableKeyguard();
    }

}
