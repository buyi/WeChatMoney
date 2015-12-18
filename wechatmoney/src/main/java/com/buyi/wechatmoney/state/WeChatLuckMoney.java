package com.buyi.wechatmoney.state;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

/**
 * Created by buyi on 15/12/9.
 */
public class WeChatLuckMoney {

    final State messageComing;
    final State moneyOrigin;
    final State moneyOpen;
    final State moneyReceived;
    final State moneyDone;

    State current;








    public WeChatLuckMoney () {
        messageComing = new StateMessageComing();
        moneyOrigin = new StateMoneyOrigin();
        moneyOpen = new StateMoneyOpen();
        moneyReceived = new StateMoneyReceived();
        moneyDone = new StateMoneyDone();
        current = messageComing;
    }

    void setState (State state) {
        current = state;
    }


    public void click (AccessibilityService context, AccessibilityEvent event) {
        current.click(context, event, this);
    }

    void back () {

    }

    public void sleep () {
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();;

        }
    }
}
