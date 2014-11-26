package ssd.sale;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/11/15.
 */
public class FZXD001 extends ListFragment implements Button.OnClickListener {
    private Button mButton_XinZ;
    private FZXD002 fzxd002;
    private FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fzxd001, container, false);
        initView(v);

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initView(View view){
        mButton_XinZ = (Button) view.findViewById(R.id.myButton_add);
        fzxd002 = new FZXD002();
        fragmentManager = getFragmentManager();
        mButton_XinZ.setOnClickListener(this);

        // Sql.parseCursor 範例
        Db db = new Db(getActivity());
        SQLiteDatabase database = db.getWritableDatabase();
        Cursor cursor = database.query("fuZXD003", null, null, null, null, null, null, null);
        List list = Sql.parseCursor(cursor);
        // for 1
        for (Object o: list) {
            Map m = (Map) o;
            System.out.println(m);
            System.out.println(m.get("gongYSMC"));
            System.out.println("-----");
        }

        // for 2
//        for (int i = 0; i < list.size(); i++) {
//            Map m = (Map) list.get(i);
//            System.out.println(m);
//        }

    }

    @Override
    public void onClick(View view) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (view == mButton_XinZ) {
            transaction.replace(R.id.content, fzxd002).commit();
        }
    }
}
