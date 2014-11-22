package ssd.sale;

import java.util.Calendar;

/**
 * Created by Administrator on 2014/11/22.
 */
public class CommonFunction {

    public CommonFunction() {
    }

    public String getCurrentDateTime() {
        StringBuffer currentDateTime = new StringBuffer();

        Calendar c = Calendar.getInstance();
        String mYear = Integer.toString(c.get(Calendar.YEAR));
        String mMonth = Integer.toString(c.get(Calendar.MONTH) - 1);
        String mDay = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
        String mHour = Integer.toString(c.get(Calendar.HOUR_OF_DAY));
        String mMinute = Integer.toString(c.get(Calendar.MINUTE));
        String mSecond = Integer.toString(c.get(Calendar.SECOND));
        currentDateTime.append(mYear).append("-")
            .append(paddingTwoDigits(mMonth)).append("-")
            .append(paddingTwoDigits(mDay)).append(" ")
            .append(paddingTwoDigits(mHour)).append(":")
            .append(paddingTwoDigits(mMinute)).append(":")
            .append(paddingTwoDigits(mSecond));

        return currentDateTime.toString();
    }

    public String paddingTwoDigits(String str) {
        StringBuffer tmp = new StringBuffer();
        if (str.length() < 2) {
            tmp.append(0).append(str);
        } else {
            tmp.append(str);
        }

        return tmp.toString();
    }
}
