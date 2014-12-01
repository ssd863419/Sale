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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import ssd.util.Db;
import ssd.util.Sql;
import ssd.util.SqlList;

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
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

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
                getActivity(), android.R.layout.simple_spinner_item,
                list_gongYS.toSpinnerArray("_id", "gongYSMC"));

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

            String name = new DateFormat().format(
                    "yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";

            Toast.makeText(getActivity(), name, Toast.LENGTH_LONG).show();
            Bundle bundle = data.getExtras();

            // 获取相机返回的数据，并转换为Bitmap图片格式
            Bitmap bitmap = (Bitmap) bundle.get("data");
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
}


