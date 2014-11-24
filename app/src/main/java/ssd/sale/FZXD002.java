package ssd.sale;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Administrator on 2014/11/18.
 */
public class FZXD002 extends Fragment implements Button.OnClickListener {
    private Button mButton_save;
    private Button mButton_next;
    private Button mButton_back;
    private ImageButton mButton_addphone;
    private EditText mEditText_gongYSMC;    // 供應商名稱
    private EditText mEditText_gongYSDZ;    // 供應商地址
    private EditText mEditText_lianXRXM;    // 聯繫人姓名
    private EditText mEditText_lianXRDH;    // 聯繫人電話
    private EditText mEditText_lianXRDH2;    // 聯繫人電話2
    private EditText mEditText_lianXRDH3;    // 聯繫人電話3
    private EditText mEditText_beiZ;        // 備註
    private TableRow mTableRow1;            //用來隱藏顯示聯繫人電話欄2
    private TableRow mTableRow2;            //用來隱藏顯示聯繫人電話欄3
    private FragmentManager fragmentManager;
    private FZXD001 fzxd001;
    private Db db;
    SQLiteDatabase database;
    long _id = -1;                          // 用來判斷目前是處於修改資料, 或新增資料(-1)

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
        mButton_addphone = (ImageButton) view.findViewById(R.id.myButton_addphone);
        mEditText_gongYSMC = (EditText) view.findViewById(R.id.myEditText_gongYSMC);
        mEditText_gongYSDZ = (EditText) view.findViewById(R.id.myEditText_gongYSDZ);
        mEditText_lianXRXM = (EditText) view.findViewById(R.id.myEditText_lianXRXM);
        mEditText_lianXRDH = (EditText) view.findViewById(R.id.myEditText_lianXRDH);
        mEditText_lianXRDH2 = (EditText) view.findViewById(R.id.myEditText_lianXRDH2);
        mEditText_lianXRDH3 = (EditText) view.findViewById(R.id.myEditText_lianXRDH3);
        mEditText_beiZ = (EditText) view.findViewById(R.id.myEditText_beiZ);
        mTableRow1 = (TableRow) view.findViewById(R.id.myTableRow1);
        mTableRow2 = (TableRow) view.findViewById(R.id.myTableRow2);
        fragmentManager = getFragmentManager();
        fzxd001 = new FZXD001();
        db = new Db(getActivity());
        database = db.getWritableDatabase();

        mButton_save.setOnClickListener(this);
        mButton_next.setOnClickListener(this);
        mButton_back.setOnClickListener(this);
        mButton_addphone.setOnClickListener(this);

        mButton_save.setEnabled(false);     // 剛進入畫面時, 儲存鈕無效
        mButton_next.setEnabled(false);     // 剛進入畫面時, 下一筆鈕無效
        mTableRow1.setVisibility(View.GONE);    // 剛進入畫面時, 聯繫人電話2的欄位隱藏
        mTableRow2.setVisibility(View.GONE);    // 剛進入畫面時, 聯繫人電話3的欄位隱藏
        mEditText_gongYSMC.addTextChangedListener(buttonState);
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        switch (view.getId()) {
            case R.id.myButton_save:
                // TODO 後面需要存入店面檔的sn, 那是在有帳號密碼登錄畫面+上傳雲端硬盤的操作之後

                String[] temp= new String[]{mEditText_gongYSMC.getText().toString()};

                /* 供應商重名 */
                if (checkGongYSMCisUsed(temp)) {

                    new AlertDialog.Builder(this.getActivity())
                        .setMessage(R.string.gongYSMCCF)
                        .setPositiveButton(R.string.queR, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //return;
                            }
                        }).create().show();

                } else {
                    final ProgressDialog dialog = ProgressDialog.show(
                            this.getActivity(), null, null, true);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                long startTime = Calendar.getInstance().getTimeInMillis();

                                /* _id == -1 代表新增資料 */
                                if (_id == -1) {
                                    _id = insert(
                                            mEditText_gongYSMC.getText().toString(),
                                            mEditText_gongYSDZ.getText().toString(),
                                            mEditText_lianXRXM.getText().toString(),
                                            mEditText_lianXRDH.getText().toString(),
                                            mEditText_lianXRDH2.getText().toString(),
                                            mEditText_lianXRDH3.getText().toString(),
                                            mEditText_beiZ.getText().toString()
                                    );

                                /* _id != -1 代表更新資料 */
                                } else {
                                    update(
                                            _id,
                                            mEditText_gongYSMC.getText().toString(),
                                            mEditText_gongYSDZ.getText().toString(),
                                            mEditText_lianXRXM.getText().toString(),
                                            mEditText_lianXRDH.getText().toString(),
                                            mEditText_lianXRDH2.getText().toString(),
                                            mEditText_lianXRDH3.getText().toString(),
                                            mEditText_beiZ.getText().toString()
                                    );
                                }

                                long endTime = Calendar.getInstance().getTimeInMillis();
                                Thread.sleep((endTime - startTime < 500) ? 500 : 0);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            dialog.dismiss();
                        }
                    }).start();

                    Toast.makeText(getActivity(), R.string.chuCCG, Toast.LENGTH_SHORT).show();
                    mButton_next.setEnabled(true);      // 下一筆鈕enable
                    mButton_save.setEnabled(false);     // 儲存鈕disable
                }
                break;

            case R.id.myButton_next:
                mEditText_gongYSMC.setText("");
                mEditText_gongYSDZ.setText("");
                mEditText_lianXRXM.setText("");
                mEditText_lianXRDH.setText("");
                mEditText_beiZ.setText("");
                _id = -1;

                break;

            case R.id.myButton_back:
                transaction.replace(R.id.content, fzxd001).commit();
                break;

            case R.id.myButton_addphone:
                mTableRow1.setVisibility(View.VISIBLE);
                mTableRow2.setVisibility(View.VISIBLE);
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

    public long insert(String gongYSMC, String gongYSDZ, String lianXRXM,
                       String lianXRDH, String lianXRDH2, String lianXRDH3, String beiZ) {

        ContentValues values = new ContentValues();
        values.put("gongYSMC", gongYSMC);
        values.put("gongYSDZ", gongYSDZ);
        values.put("lianLRXM", lianXRXM);
        values.put("lianLRDH", lianXRDH);
        values.put("lianLRDH2", lianXRDH2);
        values.put("lianLRDH3", lianXRDH3);
        values.put("beiZ", beiZ);
        values.put("shiFQY", 1);
        values.put("prgName", "FZXD002");
        values.put("crtDay", _.now());
        values.put("updDay", _.now());

        _id = database.insert("fuZXD003", null, values);
        return _id;
    }

    public long update(long key_id, String gongYSMC, String gongYSDZ, String lianXRXM,
                       String lianXRDH, String lianXRDH2, String lianXRDH3, String beiZ) {

        ContentValues values = new ContentValues();
        values.put("gongYSMC", gongYSMC);
        values.put("gongYSDZ", gongYSDZ);
        values.put("lianLRXM", lianXRXM);
        values.put("lianLRDH", lianXRDH);
        values.put("lianLRDH2", lianXRDH2);
        values.put("lianLRDH3", lianXRDH3);
        values.put("beiZ", beiZ);
        values.put("shiFQY", 1);
        values.put("prgName", "FZXD002");
        values.put("updDay", _.now());
        return database.update("fuZXD003", values, "_ID = ?", new String[]{String.valueOf(key_id)});
    }

    public boolean checkGongYSMCisUsed(String[] str) {
        boolean result = false;
        Cursor cursor = database.query(
                "fuZXD003", null, "gongYSMC = ?", str, null, null, null, null);

        /* 如果供應商重名 */
        if (cursor.getCount() > 0) {
            result = true;
        }
        cursor.close();

        return result;
    }
}
