package ssd.sale;

import java.util.Calendar;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2014/11/22.
 */
public class _ {

    public static String now(pattern="yyyy/MM/dd HH:mm:ss") {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(Calendar.getInstance().getTime());
    }

}
