package ssd.sale;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import ssd.util.Db;

/**
 * Created by Administrator on 2014/11/29.
 */
public class FZXD003 extends Fragment implements Button.OnClickListener {
    private Button mBtn_paiZ;           // 拍照 按鈕
    private Button mBtn_benDT;          // 本地圖 按鈕
    private Button mBtn_liST;           // 歷史圖 按鈕
    private Button mBtn_chuC;           // 儲存 按鈕
    private Button mBtn_xiaYB;          // 下一筆 按鈕
    private Button mBtn_chaX;           // 查詢 按鈕
    private ImageView mImage;           // 要顯示的圖片
    private TextView mTv_img;           // 未選圖之前的文字提示
    private Spinner mSpinner;           // 選供應商
    private Db db;
    private SQLiteDatabase database;
    private Cursor cursor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fzxd003, container, false);
        initView(v);
        initImage();

        return v;
    }

    public void initView(View view) {
        mBtn_paiZ = (Button) view.findViewById(R.id.myBtn_paiZ);
        mBtn_benDT = (Button) view.findViewById(R.id.myBtn_benDT);
        mBtn_liST = (Button) view.findViewById(R.id.myBtn_liST);
        mBtn_chuC = (Button) view.findViewById(R.id.myBtn_chuC);
        mBtn_xiaYB = (Button) view.findViewById(R.id.myBtn_xiaYB);
        mBtn_chaX = (Button) view.findViewById(R.id.myBtn_chaX);
        mImage = (ImageView) view.findViewById(R.id.myImg);
        mTv_img = (TextView) view.findViewById(R.id.myTv_img);
        mSpinner = (Spinner) view.findViewById(R.id.mySpinner);

        mBtn_paiZ.setOnClickListener(this);
        mBtn_benDT.setOnClickListener(this);
        mBtn_liST.setOnClickListener(this);
        mBtn_chuC.setOnClickListener(this);
        mBtn_xiaYB.setOnClickListener(this);
        mBtn_chaX.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }

    /* 沒有顯示圖片之前的文字說明 */
    private void initImage() {
        if (mImage.getDrawable() == null) {
            mTv_img.setVisibility(View.VISIBLE);
        } else {
            mTv_img.setVisibility(View.GONE);
        }
    }
}
