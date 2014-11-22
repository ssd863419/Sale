package ssd.sale;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Administrator on 2014/11/18.
 */
public class FZXD002 extends Fragment implements Button.OnClickListener {
    private Button mButton_save;
    private Button mButton_next;
    private Button mButton_back;
    private EditText mEditText_gongYSMC;    // 供應商名稱
    private EditText mEditText_gongYSDZ;    // 供應商地址
    private EditText mEditText_lianXRXM;    // 聯繫人姓名
    private EditText mEditText_lianXRDH;    // 聯繫人電話
    private EditText mEditText_beiZ;        // 備註
    private FragmentManager fragmentManager;
    private FZXD001 fzxd001;
    private FZXD002DBHelper fzxd002DBHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fzxd002, container, false);
        initView(v);

        return v;
    }

    private void initView(View view) {
        mButton_save = (Button) view.findViewById(R.id.myButton_save);
        mButton_next = (Button) view.findViewById(R.id.myButton_next);
        mButton_back = (Button) view.findViewById(R.id.myButton_back);
        mEditText_gongYSMC = (EditText) view.findViewById(R.id.myEditText_gongYSMC);
        mEditText_gongYSDZ = (EditText) view.findViewById(R.id.myEditText_gongYSDZ);
        mEditText_lianXRXM = (EditText) view.findViewById(R.id.myEditText_lianXRXM);
        mEditText_lianXRDH = (EditText) view.findViewById(R.id.myEditText_lianXRDH);
        mEditText_beiZ = (EditText) view.findViewById(R.id.myEditText_beiZ);
        fragmentManager = getFragmentManager();
        fzxd001 = new FZXD001();
        fzxd002DBHelper = new FZXD002DBHelper(this.getActivity());

        mButton_save.setOnClickListener(this);
        mButton_next.setOnClickListener(this);
        mButton_back.setOnClickListener(this);

        mButton_save.setEnabled(false);     // 剛進入畫面時, 儲存鈕無效
        mButton_next.setEnabled(false);     // 剛進入畫面時, 下一筆鈕無效
        mEditText_gongYSMC.addTextChangedListener(buttonState);
    }

    @Override
    public void onClick(View view) {
        // TODO 沒有儲存, 下一筆無效, 輸入框沒資料, 儲存無效, 沒有儲存, 返回須提示
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        switch (view.getId()) {
            case R.id.myButton_save:
                // TODO 後面需要存入店面檔的sn, 那是在有帳號密碼登錄畫面+上傳雲端硬盤的操作之後
                // TODO 後面須考量資料庫版本更新的操作
                // TODO 判斷供應商是否重複

                fzxd002DBHelper.insert(
                        mEditText_gongYSMC.getText().toString(),
                        mEditText_gongYSDZ.getText().toString(),
                        mEditText_lianXRXM.getText().toString(),
                        mEditText_lianXRDH.getText().toString(),
                        mEditText_beiZ.getText().toString()
                        );

                mButton_next.setEnabled(true);      // 下一筆鈕enable
                mButton_save.setEnabled(false);     // 儲存鈕disable

                break;
            case R.id.myButton_next:
                // TODO 點擊下一筆時, 要確認使用者有儲存過該資料
                mEditText_gongYSMC.setText("");
                mEditText_gongYSDZ.setText("");
                mEditText_lianXRXM.setText("");
                mEditText_lianXRDH.setText("");
                mEditText_beiZ.setText("");

                break;
            case R.id.myButton_back:
                transaction.replace(R.id.content, fzxd001).commit();
                break;
        }
    }

    /* 控制儲存Button的使用 */
    private TextWatcher buttonState = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            /* 供應商名稱無資料, 則儲存鈕disable */
            if (s.toString().equals("")) {
                mButton_save.setEnabled(false);
            } else {
                mButton_save.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    public class FZXD002DBHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "fuZXD";
        private static final int DATABASE_VERSION = 1;
        private static final String TABLE_NAME = "fuZXD003";
        private static final String _ID = "_id";
        private static final String FIELD_gongYSMC = "gongYSMC";
        private static final String FIELD_gongYSDZ = "gongYSDZ";
        private static final String FIELD_lianLRXM = "lianLRXM";
        private static final String FIELD_lianLRDH  = "lianLRDH";
        private static final String FIELD_beiZ  = "beiZ";
        private static final String FIELD_shiFQY  = "shiFQY";
        private static final String FIELD_prgName  = "prgName";
        private static final String FIELD_crtDay  = "crtDay";
        private static final String FIELD_updDay  = "updDay";

        private String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FIELD_gongYSMC + " TEXT" +
                FIELD_gongYSDZ + " TEXT" +
                FIELD_lianLRXM + " TEXT" +
                FIELD_lianLRDH + " TEXT" +
                FIELD_beiZ + " TEXT" +
                FIELD_shiFQY + " INTEGER" +
                FIELD_prgName + " TEXT" +
                FIELD_crtDay + " TEXT" +
                FIELD_updDay + " TEXT" +
                ")";

        private SQLiteDatabase database;

        public FZXD002DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            database = this.getWritableDatabase();
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onCreate(db);
        }

        public void insert(String gongYSMC, String gongYSDZ, String lianXRXM,
                           String lianXRDH, String beiZ) {

            ContentValues values = new ContentValues();
            values.put(FIELD_gongYSMC, gongYSMC);
            values.put(FIELD_gongYSDZ, gongYSDZ);
            values.put(FIELD_lianLRXM, lianXRXM);
            values.put(FIELD_lianLRDH, lianXRDH);
            values.put(FIELD_beiZ, beiZ);
            values.put(FIELD_shiFQY, "1");
            values.put(FIELD_prgName, "FZXD002");
            //values.put(FIELD_crtDay, );
            //values.put(FIELD_updDay, );

        }
    }
}
