package ssd.sale;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;

import ssd.util._;
import ssd.util.__;


public class Main extends Activity {
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

    /* 表頭與表尾 */
    static private FrameLayout frameLayout_title;      // 畫面最上方的"服裝小店管理系統"
    static private FrameLayout frameLayout_bottom;     // 畫面下方的 "Powered by 山海觀工作室"

    private FragmentManager fragmentManager;        //用於對Fragment進行管理

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.menu_tab_layout);

        initViews();
        setTabSelection(0);
    }

    /* 下面這個空的方法必須保留, 否則Fragment會在某些情況下, 發生重疊顯示 */
    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        // empty
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
        menuJinH = new Menu_JinH();
        menuXiaoS = new Menu_XiaoS();
        menuPanD = new Menu_PanD();
        menuBaoB = new Menu_BaoB();
        menuXiT = new Menu_XiT();
        frameLayout_title = (FrameLayout) findViewById(R.id.content_title);
        frameLayout_bottom = (FrameLayout) findViewById(R.id.content_bottom);
        fragmentManager = getFragmentManager();

    }

    Button.OnClickListener menuClick = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            frameLayout_title.setVisibility(View.VISIBLE);
            frameLayout_bottom.setVisibility(View.VISIBLE);
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
        switch (index) {
            case R.id.layout_jinH:
                mTextViewJinH.setTextColor(Color.WHITE);
                transaction.replace(R.id.content, menuJinH);
                break;

            case R.id.layout_xiaoS:
                mTextViewXiaoS.setTextColor(Color.WHITE);
                transaction.replace(R.id.content, menuXiaoS);
                break;

            case R.id.layout_panD:
                mTextViewPanD.setTextColor(Color.WHITE);
                transaction.replace(R.id.content, menuPanD);
                break;

            case R.id.layout_baoB:
                mTextViewBaoB.setTextColor(Color.WHITE);
                transaction.replace(R.id.content, menuBaoB);
                break;

            case R.id.layout_xiT:
                mTextViewXiT.setTextColor(Color.WHITE);
                transaction.replace(R.id.content, menuXiT);
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

    /* 隱藏最上方與最下方的 FrameLayout */
    public void hideTitle() {
        frameLayout_title.setVisibility(View.GONE);
        frameLayout_bottom.setVisibility(View.GONE);
    }

    /* 選單: 系統 的Fragment畫面 */
    public static class Menu_XiT extends Fragment implements Button.OnClickListener {
        private Button mButton_xiuGMM;      // 修改密碼
        private Button mButton_gongYSSD;    // 供應商設定
        private Button mButton_shiYSM;      // 使用說明
        private Button mButton_chongZ;      // 充值
        private Button mButton_tuiJSYZ;     // 推薦使用者
        private Button mButton_dengC;       // 登出
        private FragmentManager fragmentManager;
        private FZXD001 fzxd001;            // 供應商設定畫面

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.menu_xit, container, false);
            initView(v);

            return v;
        }

        private void initView(View v) {
            mButton_xiuGMM = (Button) v.findViewById(R.id.myButton_xiuGMM);
            mButton_gongYSSD = (Button) v.findViewById(R.id.myButton_gongYSSD);
            mButton_shiYSM = (Button) v.findViewById(R.id.myButton_shiYSM);
            mButton_chongZ = (Button) v.findViewById(R.id.myButton_chongZ);
            mButton_tuiJSYZ = (Button) v.findViewById(R.id.myButton_tuiJSYZ);
            mButton_dengC = (Button) v.findViewById(R.id.myButton_dengC);
            fragmentManager = getFragmentManager();

            mButton_xiuGMM.setOnClickListener(this);
            mButton_gongYSSD.setOnClickListener(this);
            mButton_shiYSM.setOnClickListener(this);
            mButton_chongZ.setOnClickListener(this);
            mButton_tuiJSYZ.setOnClickListener(this);
            mButton_dengC.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            new Main().hideTitle();

            switch (view.getId()) {
                case R.id.myButton_gongYSSD :
                    fzxd001 = new FZXD001();
                    transaction.replace(R.id.content, fzxd001).commit();
                    break;
                case R.id.myButton_dengC:

                    // TODO: 很奇怪 寫在 _.groovy 就出錯, 所以先搬到 __.java 裡面
                    __.toast(getActivity(), "Hello, World!!\nI am 0_o", Toast.LENGTH_SHORT);

                    break;


            }
        }
    }

    /* 選單: 報表 的Fragment畫面 */
    public static class Menu_BaoB extends Fragment implements Button.OnClickListener {


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.menu_baob, container, false);

            return v;
        }



        @Override
        public void onClick(View view) {

        }
    }

    /* 選單: 進貨 的Fragment畫面 */
    public static class Menu_JinH extends Fragment implements Button.OnClickListener {
        private Button mBtn_huoPDL;     // 貨品登陸
        private Button mBtn_huoPCX;     // 貨品查詢
        private Button mBtn_tuiCSDL;    // 退廠商登陸
        private Button mBtn_jinHD;      // 進貨單
        private FragmentManager fragmentManager;
        private FZXD003 fzxd003;        // 貨品登陸畫面

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.menu_jinh, container, false);
            initView(v);
            return v;
        }

        private void initView(View view) {
            mBtn_huoPDL = (Button) view.findViewById(R.id.myButton_huoPDL);
            mBtn_huoPCX = (Button) view.findViewById(R.id.myButton_huoPCX);
            mBtn_tuiCSDL = (Button) view.findViewById(R.id.myButton_tuiCSDL);
            mBtn_jinHD = (Button) view.findViewById(R.id.myButton_jinHD);
            fragmentManager = getFragmentManager();

            mBtn_huoPDL.setOnClickListener(this);
            mBtn_huoPCX.setOnClickListener(this);
            mBtn_tuiCSDL.setOnClickListener(this);
            mBtn_jinHD.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            new Main().hideTitle();

            switch (view.getId()) {
                case R.id.myButton_huoPDL:
                    fzxd003 = new FZXD003();
                    transaction.replace(R.id.content, fzxd003).commit();

            }

        }
    }

    /* 選單: 盤點 的Fragment畫面 */
    public static class Menu_PanD extends Fragment implements Button.OnClickListener {


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.menu_pand, container, false);

            return v;
        }



        @Override
        public void onClick(View view) {

        }
    }

    /* 選單: 銷售 的Fragment畫面 */
    public static class Menu_XiaoS extends Fragment implements Button.OnClickListener {


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.menu_xiaos, container, false);

            return v;
        }



        @Override
        public void onClick(View view) {

        }
    }
}
