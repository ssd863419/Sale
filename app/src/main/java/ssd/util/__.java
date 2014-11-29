package ssd.util;

import android.app.Activity;
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
        toast.show();
    }

}
