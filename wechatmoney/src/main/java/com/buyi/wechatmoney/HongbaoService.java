package com.buyi.wechatmoney;

/**
 * Created by buyi on 15/12/8.
 */

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.accessibility.AccessibilityEvent;

import com.buyi.wechatmoney.state.WeChatLuckMoney;



public class HongbaoService extends AccessibilityService {

     // 全局抢红包对象
    WeChatLuckMoney money = new WeChatLuckMoney();

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:// 通知栏事件
                System.out.println("notification is coming");
                //represents the event of opening a PopupWindow, Menu, Dialog, etc.
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                System.out.println("something happened (popupwindow,menu,dialog etc)");
                //represents the event of change in the content of a window. This change can be adding/removing view, changing a view size, etc.
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                System.out.println("something happened (view adding/removing or change view's size)");
            default:
                money.click(this, event);
                break;
        }
    }

    @Override
    public void onInterrupt() {

    }
}

