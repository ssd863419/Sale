package ssd.sale;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Method;


public class Main extends Activity {
    //TODO 點擊手機首頁再回來, Fragment會重疊顯示, 後面再設法解決

    /* 用於展示每個Tab Item的Fragment, 也就是該menu對應的畫面 */
    private Menu_JinH menuJinH;
    private Menu_XiaoS menuXiaoS;
    private Menu_PanD menuPanD;
    private Menu_BaoB menuBaoB;
    private Menu_XiT menuXiT;
    private Menu_Major menuMajor;

    /* 每個Menu選單裡的文字部分 */
    private TextView mTextViewJinH;
    private TextView mTextViewXiaoS;
    private TextView mTextViewPanD;
    private TextView mTextViewBaoB;
    private TextView mTextViewXiT;

    /* 每個Menu item的View */
    private View layoutJinH;
    private View layoutXiaoS;
    private View layoutPanD;
    private View layoutBaoB;
    private View layoutXiT;

    private FragmentManager fragmentManager;        //用於對Fragment進行管理

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.menu_tab_layout);

        initViews();
        fragmentManager = getFragmentManager();
        setTabSelection(0);
    }

    /* 初始化每個需要用到的控件, 並且設置必要的點擊事件 */
    private void initViews() {
        layoutJinH = findViewById(R.id.layout_jinH);
        layoutXiaoS = findViewById(R.id.layout_xiaoS);
        layoutPanD = findViewById(R.id.layout_panD);
        layoutBaoB = findViewById(R.id.layout_baoB);
        layoutXiT = findViewById(R.id.layout_xiT);
        mTextViewJinH = (TextView) findViewById(R.id.myText_jinH);
        mTextViewXiaoS = (TextView) findViewById(R.id.myText_xiaoS);
        mTextViewPanD = (TextView) findViewById(R.id.myText_panD);
        mTextViewBaoB = (TextView) findViewById(R.id.myText_baoB);
        mTextViewXiT = (TextView) findViewById(R.id.myText_xiT);
        layoutJinH.setOnClickListener(menuClick);
        layoutXiaoS.setOnClickListener(menuClick);
        layoutPanD.setOnClickListener(menuClick);
        layoutBaoB.setOnClickListener(menuClick);
        layoutXiT.setOnClickListener(menuClick);
    }

    Button.OnClickListener menuClick = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            switchViewId(v.getId());
        }
    };

    private void switchViewId(int ViewId) {
        switch (ViewId) {
            case R.id.layout_jinH:
                setTabSelection(layoutJinH.getId());
                break;
            case R.id.layout_xiaoS:
                setTabSelection(layoutXiaoS.getId());
                break;
            case R.id.layout_panD:
                setTabSelection(layoutPanD.getId());
                break;
            case R.id.layout_baoB:
                setTabSelection(layoutBaoB.getId());
                break;
            case R.id.layout_xiT:
                setTabSelection(layoutXiT.getId());
                break;
            default:
                break;
        }
    }

    /* 根據傳入的index參數, 來設置選中的tab */
    private void setTabSelection(int index) {
        clearSelection();   // 每次選中之前, 先清除上次的選中狀態
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);     // 隱藏所有的Fragment
        switch (index) {
            case R.id.layout_jinH:
                mTextViewJinH.setTextColor(Color.WHITE);
                if (menuJinH == null) {
                    menuJinH = new Menu_JinH();
                    transaction.add(R.id.content, menuJinH);
                } else {
                    transaction.show(menuJinH);
                }
                break;
            case R.id.layout_xiaoS:
                mTextViewXiaoS.setTextColor(Color.WHITE);
                if (menuXiaoS == null) {
                    menuXiaoS = new Menu_XiaoS();
                    transaction.add(R.id.content, menuXiaoS);
                } else {
                    transaction.show(menuXiaoS);
                }
                break;
            case R.id.layout_panD:
                mTextViewPanD.setTextColor(Color.WHITE);
                if (menuPanD == null) {
                    menuPanD = new Menu_PanD();
                    transaction.add(R.id.content, menuPanD);
                } else {
                    transaction.show(menuPanD);
                }
                break;
            case R.id.layout_baoB:
                mTextViewBaoB.setTextColor(Color.WHITE);
                if (menuBaoB == null) {
                    menuBaoB = new Menu_BaoB();
                    transaction.add(R.id.content, menuBaoB);
                } else {
                    transaction.show(menuBaoB);
                }
                break;
            case R.id.layout_xiT:
                mTextViewXiT.setTextColor(Color.WHITE);
                if (menuXiT == null) {
                    menuXiT = new Menu_XiT();
                    transaction.add(R.id.content, menuXiT);
                } else {
                    transaction.show(menuXiT);
                }
                break;
            default:
                if (menuMajor == null) {
                    menuMajor = new Menu_Major();
                    transaction.add(R.id.content, menuMajor);
                } else {
                    transaction.show(menuMajor);
                }
                break;
        }
        transaction.commit();
    }

    /* 清除所有的選中狀態 */
    private void clearSelection() {
        mTextViewJinH.setTextColor(getResources().getColor(R.color.menuItemUnselectedColor));
        mTextViewXiaoS.setTextColor(getResources().getColor(R.color.menuItemUnselectedColor));
        mTextViewPanD.setTextColor(getResources().getColor(R.color.menuItemUnselectedColor));
        mTextViewBaoB.setTextColor(getResources().getColor(R.color.menuItemUnselectedColor));
        mTextViewXiT.setTextColor(getResources().getColor(R.color.menuItemUnselectedColor));
    }

    /* 隱藏所有的Fragment */
    private void hideFragments(FragmentTransaction transaction) {
        if (menuJinH != null) {
            transaction.hide(menuJinH);
        }
        if (menuXiaoS != null) {
            transaction.hide(menuXiaoS);
        }
        if (menuPanD != null) {
            transaction.hide(menuPanD);
        }
        if (menuBaoB != null) {
            transaction.hide(menuBaoB);
        }
        if (menuXiT != null) {
            transaction.hide(menuXiT);
        }
        if (menuMajor != null) {
            transaction.hide(menuMajor);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setIconEnable(menu, true);  //用這個鬼東西來顯示menu的icon
        menu.add(0, layoutJinH.getId(), Menu.NONE, R.string.jinH).setIcon(R.drawable.icon_jinh);
        menu.add(0, layoutXiaoS.getId(), Menu.NONE + 1, R.string.xiaoS).setIcon(R.drawable.icon_xiaos);
        menu.add(0, layoutPanD.getId(), Menu.NONE + 2, R.string.panD).setIcon(R.drawable.icon_pand);
        menu.add(0, layoutBaoB.getId(), Menu.NONE + 3, R.string.baoB).setIcon(R.drawable.icon_baob);
        menu.add(0, layoutXiT.getId(), Menu.NONE + 4, R.string.xiT).setIcon(R.drawable.icon_xit);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switchViewId(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    private void setIconEnable(Menu menu, boolean enable) {
        try {
            Class<?> clazz = Class.forName("com.android.internal.view.menu.MenuBuilder");
            Method m = clazz.getDeclaredMethod("setOptionalIconsVisible", boolean.class);
            m.setAccessible(true);
            m.invoke(menu, enable);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
