package ssd.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import ssd.sale.R;

/**
 * Created by 0_o on 2014/11/29.
 */
public class __ {
    private static Toast toast;
    private static TextView textView;
    private static DisplayMetrics dm;


    // TODO 完成了, 测试后把这个删除掉 String text 這邊有問題, 要可以滿足String, 要可以滿足R.string.xxx
    public static void toast(Activity activity, int resId, int duration) {
        __.toast(activity, activity.getString(resId), duration);
    }

    public static void toast(Activity activity, String text, int duration) {
        // TODO 有空來改寫出好看的Toast
        if (toast == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast, (ViewGroup) activity.findViewById(R.id.llToast));
            textView = (TextView) layout.findViewById(R.id.tvToast);
            textView.setText(text);
            toast = new Toast(activity.getApplicationContext());
            toast.setView(layout);
            toast.setDuration(duration);
        } else {
            textView.setText(text);
            toast.setDuration(duration);
        }

        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);    // 全置中
        //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);      // 置中
        //toast.setGravity(Gravity.TOP, 0, 200);                // 靠上
        //toast.setGravity(Gravity.BOTTOM, 0, 100);             // 靠下
        toast.show();
    }

    // TODO wxy 確認2個bitmap的轉換method 是否ok
    /* 將bitmap 轉換為 可存入資料庫的blob格式 */
    public static byte[] bitmapToBytes(Bitmap bm){
        if (bm != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            return baos.toByteArray();
        } else {
            return new byte[0];
        }
    }

    /* 將資料庫的blob格式 轉換為 bitmap */
    public static Bitmap bytesToBimap(byte[] b){
        if(b.length != 0){
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        }
        else {
            return null;
        }
    }

    /* 取得手機銀幕寬度 */
    public static int getWidth(Activity activity) {
        dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /* 取得手機銀幕長度 */
    public static int getHeight(Activity activity) {
        dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

}
