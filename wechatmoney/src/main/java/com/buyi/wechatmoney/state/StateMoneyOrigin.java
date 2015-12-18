package com.buyi.wechatmoney.state;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by buyi on 15/12/9.
 */
public class StateMoneyOrigin implements State {

    /**
     * 已获取的红包队列
     */
    public List<String> fetchedIdentifiers = new ArrayList<>();
    /**
     * 待抢的红包队列
     */
    public List<AccessibilityNodeInfo> nodesToFetch = new ArrayList<>();


    @Override
    public void click(AccessibilityService context, AccessibilityEvent event, WeChatLuckMoney money) {
        System.out.println("StateMoneyOrigin");

        // 已经收到红包队列 初始化空列表
        List<AccessibilityNodeInfo> mReceiveNode = new ArrayList<AccessibilityNodeInfo>();

        // 未拆红包队列
        List<AccessibilityNodeInfo> mUnpackNode = null;

        money.sleep();
         // 获得当前视窗内容的节点对象 内部含有tree of accessibility node
        AccessibilityNodeInfo nodeInfo = null;

            nodeInfo = context.getRootInActiveWindow();

         int count = event.getRecordCount();
        for (int i =0; i< count; i++) {
            System.out.println("cout:" + event.getRecord(i));
        }



        if (nodeInfo == null) {
            System.out.println("StateMoneyOrigin nodeInfo is null");
            money.setState(money.messageComing);
            return;
        } else {
            if (!"com.tencent.mm".equals(nodeInfo.getPackageName()) || !"ndroid.widget.TextView".equals(nodeInfo.getClassName())) {
                money.setState(money.messageComing);
                return;
            }
        }

        /* 聊天会话窗口，遍历节点匹配“领取红包”和"查看红包" */
        List<AccessibilityNodeInfo> node0 = nodeInfo.findAccessibilityNodeInfosByText("查看红包");
        List<AccessibilityNodeInfo> node1 = nodeInfo.findAccessibilityNodeInfosByText("领取红包");

        // 组合所有可能含有钱的红包
        mReceiveNode.addAll(node0);
        mReceiveNode.addAll(node1);



//        for (int i = 0; i < mReceiveNode.size(); i++) {
//            String id = getHongbaoHash(mReceiveNode.get(i));
//            System.out.println("nodeId2:" + id.substring(id.indexOf('@')+1));
//        }

        if (mReceiveNode.size() == 0) {
            System.out.println("size is zero:" + nodeInfo);
        }

        for (AccessibilityNodeInfo node : mReceiveNode) {
            String id = getHongbaoHash(node);
            System.out.println("id:" +  id.substring(0,id.indexOf('@')+1) + fetchedIdentifiers.contains(id));
            /* 如果节点没有被回收且该红包没有抢过 */
            if (id != null && !fetchedIdentifiers.contains(id)) {
                nodesToFetch.add(node);
            } else {
//                fetchedIdentifiers.remove(id);

            }
        }
        System.out.println("nodesToFetch1:" + Arrays.toString(nodesToFetch.toArray()));
//        System.out.println("fetchedIdentifiers1:" + Arrays.toString(fetchedIdentifiers.toArray()));

           /* 先消灭待抢红包队列中的红包 */
        if (nodesToFetch.size() > 0) {
                    /* 从最下面的红包开始戳 */
            AccessibilityNodeInfo node = nodesToFetch.remove(nodesToFetch.size() - 1);
                String id = getHongbaoHash(node);


                if (id == null) return;

                // 调试信息，在每次打开红包后打印出已经获取的红包
                // Log.d("fetched", Arrays.toString(fetchedIdentifiers.toArray()));

            AccessibilityNodeInfo parent = node.getParent();
            System.out.println("parent:" + parent);
            if (parent != null) {
                money.sleep();
                parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);

                fetchedIdentifiers.add(id);
            }
            node.recycle();




//            System.out.println("nodesToFetch2:" + Arrays.toString(nodesToFetch.toArray()));
//            System.out.println("fetchedIdentifiers2:" + Arrays.toString(fetchedIdentifiers.toArray()));
            money.setState(money.moneyOpen);
        } else {
            System.out.println("StateMoneyOpen nodesToFetch is null");
            money.setState(money.messageComing);
        }
    }

    @Override
    public void back(Context context, WeChatLuckMoney money) {

    }

    static int index =0;


    /**
     * 将节点对象的id和红包上的内容合并
     * 用于表示一个唯一的红包
     *
     * @param node 任意对象
     * @return 红包标识字符串
     */
    String getHongbaoHash(AccessibilityNodeInfo node) {
        /* 获取红包上的文本 */
        String content = "";
        try {
            AccessibilityNodeInfo i = node.getParent().getChild(0);
            content = i.getText().toString();
        } catch (NullPointerException npr) {
            return content + "@" + (index++);
        }

        return content + "@" + getNodeId(node);
    }

    /**
     * 获取节点对象唯一的id，通过正则表达式匹配
     * AccessibilityNodeInfo@后的十六进制数字
     *
     * @param node AccessibilityNodeInfo对象
     * @return id字符串
     */
    private String getNodeId(AccessibilityNodeInfo node) {
        /* 用正则表达式匹配节点Object */
        Pattern objHashPattern = Pattern.compile("(?<=@)[0-9|a-z]+(?=;)");
        Matcher objHashMatcher = objHashPattern.matcher(node.toString());

        // AccessibilityNodeInfo必然有且只有一次匹配，因此不再作判断
        objHashMatcher.find();
//        System.out.println("getNodeId" + node);
//        System.out.println("getNodeId1" + objHashMatcher.group(0));

        return objHashMatcher.group(0);
    }

}
