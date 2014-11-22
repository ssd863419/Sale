package ssd.sale;

import java.util.Calendar;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2014/11/22.
 */
public class _ {

    public _() {
    }

//    public String now() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        return sdf.format(Calendar.getInstance().getTime());
//        //return now("yyyy/MM/dd HH:mm:ss");
//    }

    public static String now() {
        return now("yyyy/MM/dd HH:mm:ss");
    }

    public static String now(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(Calendar.getInstance().getTime());
    }
}
