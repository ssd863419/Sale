package ssd.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import ssd.sale.R;

import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2014/11/22.
 */
public class _ {

    /* 取得當前時間 */
    public static String now(pattern="yyyy/MM/dd HH:mm:ss") {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(Calendar.getInstance().getTime());
    }

    /* 用於ViewHolder */
    public static ViewMap getViews(v) {
        def result = new ViewMap()
        result[v.getId()] = v
        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v
            for (int i = 0; i < vg.getChildCount(); i++) {
                View child = vg.getChildAt(i)
                result[child.getId()] = child
                result.putAll(getViews(child))
            }
        }
        result[-1] = null; // force -1 = null
        return result
    }


}
