package ssd.sale;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


/**
 * Created by Administrator on 2014/12/4.
 */
public class FZXD004 extends Fragment implements Button.OnClickListener{
    private Button mBtn_fanH;               // 按鈕 返回
    private Button mBtn_shaiX;              // 按鈕 篩選
    private CheckBox mCb_zhiKT;             // CheckBox 只看圖
    private TextView mTv_sum;               // 上方的合計數據顯示
    private FZXD003 fzxd003;                // 貨品編輯畫面
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fzxd004, container, false);
        initView(v);

        return v;
    }

    private void initView(View view) {
        mBtn_fanH = (Button) view.findViewById(R.id.myBtn_fanH);
        mBtn_shaiX = (Button) view.findViewById(R.id.myBtn_shaiX);
        mCb_zhiKT = (CheckBox) view.findViewById(R.id.myCb_zhiKT);
        mTv_sum = (TextView) view.findViewById(R.id.myTv_sum);
        fzxd003 = new FZXD003();
        fragmentManager = getFragmentManager();

        mBtn_fanH.setOnClickListener(this);
        mBtn_shaiX.setOnClickListener(this);
        mCb_zhiKT.setOnCheckedChangeListener(checkBoxChange);
    }

    @Override
    public void onClick(View view) {
        transaction = fragmentManager.beginTransaction();
        switch (view.getId()) {
            case R.id.myBtn_fanH:
                transaction.replace(R.id.content, fzxd003).commit();

                break;

            case R.id.myBtn_shaiX:

                break;
        }
    }

    CompoundButton.OnCheckedChangeListener checkBoxChange = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (mCb_zhiKT.isChecked()) {

            } else {

            }
        }
    };
}
