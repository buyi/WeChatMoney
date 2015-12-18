package com.buyi.wechatmoney.state;

/**
 * Created by buyi on 15/12/9.
 */

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.view.accessibility.AccessibilityEvent;

/**
 * 对应状态迁移的方法 对于微信红包而言基本只有点击和回退
 */
public interface State {

    void click(AccessibilityService context, AccessibilityEvent event, WeChatLuckMoney money);

    void back(Context context, WeChatLuckMoney money);
}
