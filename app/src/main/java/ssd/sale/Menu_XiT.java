package ssd.sale;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Administrator on 2014/11/15.
 */
public class Menu_XiT extends Fragment {
    private Button mButton_xiuGMM;      // 修改密碼
    private Button mButton_gongYSSD;    // 供應商設定
    private Button mButton_shiYSM;      // 使用說明
    private Button mButton_chongZ;      // 充值
    private Button mButton_tuiJSYZ;     // 推薦使用者
    private Button mButton_dengC;       // 登出
    private FragmentManager fragmentManager;        //用於對Fragment進行管理
    private FZXD001 fzxd001;

    /*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initView();

        return inflater.inflate(R.layout.menu_xit, container, false);
    }

    private void initView() {
        mButton_xiuGMM = (Button) getActivity().findViewById(R.id.myButton_xiuGMM);
        mButton_gongYSSD = (Button) getActivity().findViewById(R.id.myButton_gongYSSD);
        mButton_shiYSM = (Button) getActivity().findViewById(R.id.myButton_shiYSM);
        mButton_chongZ = (Button) getActivity().findViewById(R.id.myButton_chongZ);
        mButton_tuiJSYZ = (Button) getActivity().findViewById(R.id.myButton_tuiJSYZ);
        mButton_dengC = (Button) getActivity().findViewById(R.id.myButton_dengC);
        fzxd001 = new FZXD001();
        fragmentManager = getFragmentManager();

        mButton_xiuGMM.setOnClickListener(myButton);
        mButton_gongYSSD.setOnClickListener(myButton);
        mButton_shiYSM.setOnClickListener(myButton);
        mButton_chongZ.setOnClickListener(myButton);
        mButton_tuiJSYZ.setOnClickListener(myButton);
        mButton_dengC.setOnClickListener(myButton);

    }

    Button.OnClickListener myButton = new Button.OnClickListener() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        @Override
        public void onClick(View view) {
            if (view == mButton_xiuGMM) {
                transaction.replace(R.id.content, fzxd001).commit();


            } else if (view == mButton_gongYSSD) {

            } else if (view == mButton_shiYSM) {

            } else if (view == mButton_chongZ) {

            } else if (view == mButton_tuiJSYZ) {

            } else if (view == mButton_dengC) {

            }
        }
    };
}
