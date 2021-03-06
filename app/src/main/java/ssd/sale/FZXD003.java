package ssd.sale;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;

import java.io.FileNotFoundException;
import java.util.Calendar;

import ssd.util.Dao;
import ssd.util.DataHelper;
import ssd.util.Sql;
import ssd.util.SqlList;
import ssd.util.SqlMap;
import ssd.util._;
import ssd.util.__;

/**
 * Created by Administrator on 2014/11/29.
 */
public class FZXD003 extends Fragment implements Button.OnClickListener {
    // TODO 停用的情況, 需判斷是否有售出資料
    // TODO 標準售價旁, 增加計算鈕, 跳出AlertFragment, 顯示自動計算的方式(幾筆選項: 進價*? +? = ?, 取整數?)
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
    private RadioButton mRB_check;      // 啟用
    private RadioButton mRB_uncheck;    // 停用
    private FZXD004 fzxd004;            // 貨品查詢畫面
    private Dao dao;                    // Dao
    private DataHelper db;
    private SQLiteDatabase database;
    private ssd.util.SpinnerAdapter adapter_gongYS; // 供應商的adapter
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private Bitmap bitmap;              // 取得的圖片資料
    private long _id = -1;              // 貨品資料檔的_id, -1 代表新增的情況
    private int fuZXD003_id = 0;       // 用於供應商spinner的顯示項目

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fzxd003, container, false);
        initView(v);
        initText();
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
        mRB_check = (RadioButton) view.findViewById(R.id.myRB_check);
        mRB_uncheck = (RadioButton) view.findViewById(R.id.myRB_uncheck);

        dao = new Dao(this);
        db = new DataHelper(getActivity());
        database = db.getWritableDatabase();
        fzxd004 = new FZXD004();

        mBtn_paiZ.setOnClickListener(this);
        mBtn_benDT.setOnClickListener(this);
        mBtn_liST.setOnClickListener(this);
        mBtn_chuC.setOnClickListener(this);
        mBtn_xiaYB.setOnClickListener(this);
        mBtn_chaX.setOnClickListener(this);

        /* 檢查進貨價, 標準售價 的輸入資料, 改成規範的 x.xx */
        mET_jingHJ.setOnFocusChangeListener(chkNumber);
        mET_biaoZSJ.setOnFocusChangeListener(chkNumber);

        /* 有_id, 代表處於編輯狀態 */
        Bundle bundle = getArguments();
        if (bundle != null) {
            _id = Long.valueOf(bundle.getString("_id"));
            query(new String[]{String.valueOf(_id)});
        }
    }

    @Override
    public void onClick(View view) {
        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();

        switch (view.getId()) {
            // 拍照按鈕
            case R.id.myBtn_paiZ:
                // TODO 要確認使用者的手機的開發者選項, 是否有勾選"不要保留活動"
                // 確認有攝像頭, 在進行拍照取圖
                if (hasCamera()) {
                    useCamera();
                }

                break;

            // 儲存按鈕
            case R.id.myBtn_chuC:
                // TODO 新增與修改情況的分別處理
                save();      // 儲存

                break;

            // 下一筆
            case R.id.myBtn_xiaYB:
                _id = -1;
                mImage.setImageDrawable(null);
                bitmap = null;
                mET_gongYSXH.setText("");
                mET_jingHJ.setText("0.0");
                mET_biaoZSJ.setText("0.0");
                mET_jianS.setText("1");
                mET_huoPBZ.setText("");
                mRB_check.setChecked(true);
                initText();

                break;

            // 查詢
            case R.id.myBtn_chaX:
                transaction.replace(R.id.content, fzxd004).commit();

                break;

            // 歷史圖按鈕
            case R.id.myBtn_liST:
                // TODO 歷史圖, 顯示曾經入系統的圖片, 點擊後, 帶出歷史資料

                break;

            // 本地圖按鈕
            case R.id.myBtn_benDT:
                getLocalPicture();

                break;
        }
    }

    /* 沒有顯示圖片之前的文字說明 */
    private void initText() {
        if (mImage.getDrawable() == null) {
            mTv_img.setVisibility(View.VISIBLE);
            mImage.setVisibility(View.GONE);
        } else {
            mTv_img.setVisibility(View.GONE);
            mImage.setVisibility(View.VISIBLE);
        }
    }

    /* 供應商的資料 */
    private void queryGongYS() {
        // TODO 新增的情況, 下拉框的首筆資料, 預設空值, 儲存時, 判斷提示是否有選供應商
        // TODO 根據供應商+供應商型號, 帶出最近的歷史資料

        adapter_gongYS = new ssd.util.SpinnerAdapter(
                getActivity(), android.R.layout.simple_spinner_item,
                dao.getGongYSMC().toSpinnerArray("_id", "gongYSMC"));

        mSpinner.setAdapter(adapter_gongYS);

        // 非新增的情況下, spinner預設顯示值(對應table紀錄值)
        if (_id != -1) {
            mSpinner.setSelection(getSpinnerPosition(fuZXD003_id));
        }
    }

    /* 檢測是否有攝像頭 */
    private boolean hasCamera() {
        Context context = getActivity();
        PackageManager packageManager = context.getPackageManager();

        if(!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            __.toast(getActivity(), "This device does not have a camera.", Toast.LENGTH_SHORT);
            return false;
        } else {
            return true;
        }
    }

    /* 拍照取照片 */
    private void useCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(intent, 1);
    }

    /* 本地圖庫取照片 */
    private void getLocalPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int edgeLength;         //用來做為照片的長寬

        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            bitmap = (Bitmap) data.getExtras().get("data");
        }

        if (requestCode == 2 && data != null) {
            Uri uri = data.getData();
            ContentResolver cr = getActivity().getContentResolver();
            try {
                bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        if (bitmap != null) {
            try {
                edgeLength = (bitmap.getHeight() <= bitmap.getWidth() ?
                        bitmap.getHeight() : bitmap.getWidth());

                bitmap = centerSquareScaleBitmap(bitmap, edgeLength);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        mImage.setImageBitmap(bitmap);
        initText();
    }

    /**

     * @param bitmap      原图
     * @param edgeLength  希望得到的正方形部分的边长
     * @return  缩放截取正中部分后的位图。
     */
    public static Bitmap centerSquareScaleBitmap(Bitmap bitmap, int edgeLength)
    {
        if(null == bitmap || edgeLength <= 0)
        {
            return  null;
        }

        Bitmap result = bitmap;
        int widthOrg = bitmap.getWidth();
        int heightOrg = bitmap.getHeight();

        if(widthOrg >= edgeLength && heightOrg >= edgeLength)
        {
            //压缩到一个最小长度是edgeLength的bitmap
            int longerEdge = (int)(edgeLength * Math.max(widthOrg, heightOrg) / Math.min(widthOrg, heightOrg));
            int scaledWidth = widthOrg > heightOrg ? longerEdge : edgeLength;
            int scaledHeight = widthOrg > heightOrg ? edgeLength : longerEdge;
            Bitmap scaledBitmap;

            try{
                scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
            }
            catch(Exception e){
                return null;
            }

            //从图中截取正中间的正方形部分。
            int xTopLeft = (scaledWidth - edgeLength) / 2;
            int yTopLeft = (scaledHeight - edgeLength) / 2;

            try{
                result = Bitmap.createBitmap(scaledBitmap, xTopLeft, yTopLeft, edgeLength, edgeLength);
                scaledBitmap.recycle();
            }
            catch(Exception e){
                return null;
            }
        }

        return result;
    }

    private void save() {
        int flag = 0;       // 用來判斷資料是否可儲存, 0代表可以

        // 取得供應商spinner對應的fuZXD003_id
        fuZXD003_id = Integer.valueOf(
                adapter_gongYS.getID(mSpinner.getSelectedItemPosition()));

        // 如果沒有選擇供應商
        if (fuZXD003_id < 0) {
            __.toast(getActivity(), R.string.qingXZGYS, Toast.LENGTH_SHORT);
            flag = -1;
        }

        // 判斷圖片或供應商型號, 至少其中一個條件需滿足
        if (bitmap == null && mET_gongYSXH.getText().toString().trim().equals("")) {
            __.toast(getActivity(), R.string.tuPHGYSXHXYZL, Toast.LENGTH_SHORT);
            flag = -1;
        }

        /* 新增的處理 */
        if (flag == 0 && _id == -1) {
            final ProgressDialog dialog = ProgressDialog.show(
                    this.getActivity(), null, null, true);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        long startTime = Calendar.getInstance().getTimeInMillis();

                        _id = insert(
                                Double.valueOf(mET_jingHJ.getText().toString()),
                                Double.valueOf(mET_biaoZSJ.getText().toString()),
                                _.now(),
                                Integer.valueOf(mET_jianS.getText().toString()),
                                mET_huoPBZ.getText().toString(),
                                bitmap,
                                fuZXD003_id,
                                mET_gongYSXH.getText().toString(),
                                (mRB_check.isChecked() ? 1 : 0)
                        );

                        long endTime = Calendar.getInstance().getTimeInMillis();
                        Thread.sleep((endTime - startTime < 500) ? 500 : 0);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                }
            }).start();

            __.toast(getActivity(), R.string.chuCCG, Toast.LENGTH_SHORT);
        }

        /* 修改的處理 */
        if (flag == 0 && _id != -1) {
            final ProgressDialog dialog = ProgressDialog.show(
                    this.getActivity(), null, null, true);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        long startTime = Calendar.getInstance().getTimeInMillis();

                        update(
                                _id,
                                Double.valueOf(mET_jingHJ.getText().toString()),
                                Double.valueOf(mET_biaoZSJ.getText().toString()),
                                Integer.valueOf(mET_jianS.getText().toString()),
                                mET_huoPBZ.getText().toString(),
                                bitmap,
                                fuZXD003_id,
                                mET_gongYSXH.getText().toString(),
                                (mRB_check.isChecked() ? 1 : 0)
                        );

                        long endTime = Calendar.getInstance().getTimeInMillis();
                        Thread.sleep((endTime - startTime < 500) ? 500 : 0);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                }
            }).start();

            __.toast(getActivity(), R.string.chuCCG, Toast.LENGTH_SHORT);
        }

    }

    private String chkPrice(String price) {
        String result = "0";
        if (price != null) {
            try {
                double d = Double.parseDouble(price); // 轉成 dobule
                double r = (double) Math.round(d * 100) / 100d; // 四捨五入, 但是要轉型回 double 去做除法
                result = String.valueOf(r);
            } catch (NumberFormatException e) {
                // DO NOTHING. 因為有問題就返回 0, 所以出錯就不要做任何事
            }
        }
        return result;
    }

    EditText.OnFocusChangeListener chkNumber = new EditText.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if (!mET_jingHJ.hasFocus()) {
                mET_jingHJ.setText(chkPrice(mET_jingHJ.getText().toString()));
            }

            if (!mET_biaoZSJ.hasFocus()) {
                mET_biaoZSJ.setText(chkPrice(mET_biaoZSJ.getText().toString()));
            }
        }
    };

    private long insert(double jinHJ, double biaoZSJ, String jinHR, int jianS, String huoPBZ,
                       Bitmap huoPTP, int fuZXD003_id, String gongYSXH, int shiFQY) {

        ContentValues values = new ContentValues();
        values.put("jinHJ", jinHJ);
        values.put("biaoZSJ", biaoZSJ);
        values.put("jinHR", jinHR);
        values.put("jianS", jianS);
        values.put("huoPBZ", huoPBZ);
        values.put("huoPTP", __.bitmapToBytes(huoPTP));
        values.put("fuZXD003_id", fuZXD003_id);
        values.put("gongYSXH", gongYSXH);
        values.put("shiFQY", shiFQY);
        values.put("prgName", "FZXD003");
        values.put("crtDay", _.now());
        values.put("updDay", _.now());

        return database.insert("fuZXD002", null, values);
    }

    private long update(long key_id, double jinHJ, double biaoZSJ, int jianS, String huoPBZ,
                        Bitmap huoPTP, int fuZXD003_id, String gongYSXH, int shiFQY) {

        ContentValues values = new ContentValues();
        values.put("jinHJ", jinHJ);
        values.put("biaoZSJ", biaoZSJ);
        values.put("jianS", jianS);
        values.put("huoPBZ", huoPBZ);
        values.put("huoPTP", __.bitmapToBytes(huoPTP));
        values.put("fuZXD003_id", fuZXD003_id);
        values.put("gongYSXH", gongYSXH);
        values.put("shiFQY",shiFQY);
        values.put("prgName", "FZXD003");
        values.put("updDay", _.now());

        return database.update("fuZXD002", values, "_id = ?", new String[]{String.valueOf(key_id)});
    }

    private void query(String[] str) {
        Cursor cursor = database.query("fuZXD002", null, "_id = ?", str, null, null, null, null);
        SqlList list = Sql.parseCursor(cursor);
        SqlMap map = list.getMyMap(0);
        fuZXD003_id = map.getInt("fuZXD003_id");

        // 供應商spinner的顯示值, 放在queryGongYS()處理
        mET_gongYSXH.setText(map.getString("gongYSXH"));                // 供應商型號
        mET_jingHJ.setText(String.valueOf(map.getDouble("jinHJ")));    // 進貨價
        mET_biaoZSJ.setText(String.valueOf(map.getDouble("biaoZSJ")));  // 標準售價
        mET_jianS.setText(String.valueOf(map.getInt("jianS")));         // 件數
        mET_huoPBZ.setText(map.getString("huoPBZ"));                    // 貨品備註
        mImage.setImageBitmap(__.bytesToBimap(map.getBlob("huoPTP")));  // 貨品圖片
        bitmap = __.bytesToBimap(map.getBlob("huoPTP"));

        // 是否啟用
        mRB_check.setChecked(map.getInt("shiFQY") == 1);
        mRB_uncheck.setChecked(map.getInt("shiFQY") == 0);
    }

    private int getSpinnerPosition(int fuZXD003_id) {
        int i;
        for (i = 0 ; i <= fuZXD003_id ; i++) {
            if (Integer.valueOf(adapter_gongYS.getID(i)) == fuZXD003_id) {
                break;
            }
        }

        return i;
    }
}


