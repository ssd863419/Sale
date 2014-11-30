package ssd.sale;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import ssd.util.Db;
import ssd.util.Sql;
import ssd.util.SqlList;
import ssd.util.SqlMap;

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
    private EditText mET_gongYSXH;      // 供應商型號
    private EditText mET_jingHJ;        // 進貨價
    private EditText mET_biaoZSJ;       // 標準售價
    private EditText mET_jianS;         // 件數
    private EditText mET_huoPBZ;        // 貨品備註
    private Db db;
    private SQLiteDatabase database_gongYS;         // 供應商的database
    private Cursor cursor_gongYS;                   // 供應商的cursor
    private SqlList list_gongYS;                    // 供應商的list
    private ssd.util.SpinnerAdapter adapter_gongYS; // 供應商的adapter

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fzxd003, container, false);
        initView(v);
        initImage();
        queryGongYS();

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
        mET_gongYSXH = (EditText) view.findViewById(R.id.myET_gongYSXH);
        mET_biaoZSJ = (EditText) view.findViewById(R.id.myET_biaoZSJ);
        mET_jingHJ = (EditText) view.findViewById(R.id.myET_jingHJ);
        mET_biaoZSJ = (EditText) view.findViewById(R.id.myET_biaoZSJ);
        mET_jianS = (EditText) view.findViewById(R.id.myET_jianS);
        mET_huoPBZ = (EditText) view.findViewById(R.id.myET_huoPBZ);
        db = new Db(getActivity());
        database_gongYS = db.getReadableDatabase();

        mBtn_paiZ.setOnClickListener(this);
        mBtn_benDT.setOnClickListener(this);
        mBtn_liST.setOnClickListener(this);
        mBtn_chuC.setOnClickListener(this);
        mBtn_xiaYB.setOnClickListener(this);
        mBtn_chaX.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myBtn_paiZ:

        }
    }

    /* 沒有顯示圖片之前的文字說明 */
    private void initImage() {
        if (mImage.getDrawable() == null) {
            mTv_img.setVisibility(View.VISIBLE);
        } else {
            mTv_img.setVisibility(View.GONE);
        }
    }

    /* 供應商的資料 */
    private void queryGongYS() {
        cursor_gongYS = database_gongYS.query(
                "fuZXD003", new String[] {"_id", "gongYSMC"}, "shiFQY = ?", new String[] {"1"},
                null, null, "gongYSMC", null);

        list_gongYS = Sql.parseCursor(cursor_gongYS);

        adapter_gongYS = new ssd.util.SpinnerAdapter(
                getActivity(), android.R.layout.simple_spinner_item, list_gongYS.toSpinnerArray("_id", "gongYSMC"));

        mSpinner.setAdapter(adapter_gongYS);
    }


}


