package ssd.sale;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import ssd.util.Dao;
import ssd.util.Sql;
import ssd.util.SqlList;
import ssd.util._;
import ssd.util.__;

/**
 * Created by Administrator on 2014/11/29.
 */
public class FZXD003 extends Fragment implements Button.OnClickListener {
    // TODO 停用的情況, 需判斷是否有售出資料
    // TODO 標準售價旁, 增加計算鈕, 跳出AlertFragment, 顯示自動計算的方式(幾筆選項: 進價*? +? = ?, 取整數?)
    // TODO 進入輸入框之後, 預設全選資料
    // TODO 沒有圖片的情況, 在顯示的圖片畫面, 顯示其供應商+供應商型號
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
    private Dao dao;                    // Dao
    private ssd.util.SpinnerAdapter adapter_gongYS; // 供應商的adapter
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private Bitmap bitmap;              // 取得的圖片
    private int _id = -1;               // 貨品資料檔的_id, -1 代表新增的情況

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fzxd003, container, false);
        initView(v);
        initImage();
        queryGongYS();

        __.toast(getActivity(), R.string.app_name, Toast.LENGTH_SHORT);

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

        mBtn_paiZ.setOnClickListener(this);
        mBtn_benDT.setOnClickListener(this);
        mBtn_liST.setOnClickListener(this);
        mBtn_chuC.setOnClickListener(this);
        mBtn_xiaYB.setOnClickListener(this);
        mBtn_chaX.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();

        switch (view.getId()) {
            case R.id.myBtn_paiZ:
                // TODO 要確認使用者的手機的開發者選項, 是否有勾選"不要保留活動"
                // 確認有攝像頭, 在進行拍照取圖
                if (hasCamera()) {
                    useCamera();
                }

                break;

            case R.id.myBtn_chuC:
                // TODO 新增與修改情況的分別處理
                save(_id);      // 儲存

                break;

            case R.id.myBtn_xiaYB:
                // TODO 下一筆的處理

                break;

            case R.id.myBtn_chaX:
                // TODO 點擊查詢, 跳出AlertFragment, 顯示貨品查詢畫面, 返回時, 畫面不更新

                break;

            case R.id.myBtn_liST:
                // TODO 歷史圖, 顯示曾經入系統的圖片, 點擊後, 帶出歷史資料

                break;

            case R.id.myBtn_benDT:
                // TODO 本地圖, 點選本地圖庫
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
        // TODO 新增的情況, 下拉框的首筆資料, 預設空值, 儲存時, 判斷提示是否有選供應商
        // TODO 根據供應商+供應商型號, 帶出最近的歷史資料

        adapter_gongYS = new ssd.util.SpinnerAdapter(
                getActivity(), android.R.layout.simple_spinner_item,
                dao.getGongYSMC().toSpinnerArray("_id", "gongYSMC"));

        mSpinner.setAdapter(adapter_gongYS);
    }

    /* 檢測是否有攝像頭 */
    private boolean hasCamera() {
        Context context = getActivity();
        PackageManager packageManager = context.getPackageManager();

        if(!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Toast.makeText(getActivity(),
                    "This device does not have a camera.",
                    Toast.LENGTH_SHORT).show();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            int edgeLength;     //用來做為照片的長寬

            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd卡是否可用
                return;
            }

            String name = _.now("yyyyMMdd_HHmmss") + ".jpg";

            Toast.makeText(getActivity(), name, Toast.LENGTH_LONG).show();
            Bundle bundle = data.getExtras();

            // 获取相机返回的数据，并转换为Bitmap图片格式
            bitmap = (Bitmap) bundle.get("data");
            FileOutputStream b = null;
            File file = new File("/sdcard/myImage/");
            file.mkdirs();// 创建文件夹
            String fileName = "/sdcard/myImage/" + name;

            try {
                edgeLength = (bitmap.getHeight() <= bitmap.getWidth() ?
                        bitmap.getHeight() : bitmap.getWidth());

                bitmap = centerSquareScaleBitmap(bitmap, edgeLength);
                b = new FileOutputStream(fileName);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    b.flush();
                    b.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            mImage.setImageBitmap(bitmap);
            initImage();
        }
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

    private void save(int _id) {
        // TODO 進價, 售價, 件數, 需另寫method, 控制大於等於0, 到小數2位
        // TODO 判斷圖片 或 供應商+供應商型號, 至少其中一個條件須滿足


    }

    private float chkPrice(String price) {
        float result;

        if (price == null) {
            result = 0;
        } else {
            result = price.charAt(price.indexOf('.') + 2);
        }
        return result;
    }

}


