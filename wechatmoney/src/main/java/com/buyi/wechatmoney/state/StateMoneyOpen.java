package com.buyi.wechatmoney.state;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * Created by buyi on 15/12/9.
 */
public class StateMoneyOpen implements State {

    @Override
    public void click(AccessibilityService context, AccessibilityEvent event, WeChatLuckMoney money) {
        System.out.println("StateMoneyOpen");
        money.sleep();
        AccessibilityNodeInfo info = context.getRootInActiveWindow();
        if (info == null) {
            System.out.println("StateMoneyOpen info is null");
            back(context, money);
            return;
        }

        List<AccessibilityNodeInfo> node = info.findAccessibilityNodeInfosByText("拆红包");
        final int size = node.size();
        System.out.println("StateMoneyOpen size:" + size);
        if (size > 0) {
            System.out.println("StateMoneyOpen open money");
            AccessibilityNodeInfo cellNode = node.get(size - 1);
            money.sleep();
            cellNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);

            cellNode.recycle();
            money.setState(money.moneyReceived);
        } else {
            System.out.println("StateMoneyOpen no open money");
            List<AccessibilityNodeInfo> node3 = info.findAccessibilityNodeInfosByText("红包详情");
            List<AccessibilityNodeInfo> node4 = info.findAccessibilityNodeInfosByText("手慢了");
            if (!node3.isEmpty() || !node4.isEmpty()) {
                System.out.println("StateMoneyOpen has 红包详情");
                back(context, money);
            } else {
                System.out.println("StateMoneyOpen not has 红包详情");
                money.setState(money.messageComing);
            }
//            back (context, money);
        }

    }

    @Override
    public void back(Context context, WeChatLuckMoney money) {
        ((AccessibilityService) context).performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
        money.sleep();
        money.setState(money.messageComing);
    }
}
