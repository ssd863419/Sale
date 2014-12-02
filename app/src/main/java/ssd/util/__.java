package ssd.util;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import ssd.sale.R;

/**
 * Created by 0_o on 2014/11/29.
 */
public class __ {
    private static Toast toast;
    private static TextView textView;
    public static void toast(Activity activity, String text, int duration) {
        // TODO 有空來改寫出好看的Toast
        // TODO String text 這邊有問題, 要可以滿足String, 要可以滿足R.string.xxx
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

}
