package com.buyi.wechatmoney.state;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * Created by buyi on 15/12/9.
 */
public class StateMoneyReceived implements State {

    @Override
    public void click(AccessibilityService context, AccessibilityEvent event, WeChatLuckMoney money) {
        System.out.println("StateMoneyReceived");
        money.sleep();
        AccessibilityNodeInfo info = context.getRootInActiveWindow();
        if (info == null) {
            System.out.println("StateMoneyReceived is null");
            back(context, money);
            return;
        }
        List<AccessibilityNodeInfo> node3 = info.findAccessibilityNodeInfosByText("红包详情");
        List<AccessibilityNodeInfo> node4 = info.findAccessibilityNodeInfosByText("手慢了");
        if (!node3.isEmpty() || !node4.isEmpty()) {
            System.out.println("StateMoneyReceived has 红包详情");
            back(context, money);
        }
        money.setState(money.messageComing);
    }

    @Override
    public void back(Context context, WeChatLuckMoney money) {
        ((AccessibilityService) context).performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
        money.sleep();
        money.setState(money.messageComing);
    }
}
