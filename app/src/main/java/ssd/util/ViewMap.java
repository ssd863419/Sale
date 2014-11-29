package ssd.util;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.HashMap;

/**
 * Created by 0_o on 2014/11/29.
 */
public class ViewMap extends HashMap {

    public Button getButton(int id) {
        return (Button) this.get(id);
    }

    public ListView getListView(int id) {
        return (ListView) this.get(id);
    }

    public LinearLayout getLinearLayout(int id) {
        return (LinearLayout) this.get(id);
    }

    public CheckBox getCheckBox(int id) {
        return (CheckBox) this.get(id);
    }


    // TODO: 還有好多沒寫 有用到再寫


}
