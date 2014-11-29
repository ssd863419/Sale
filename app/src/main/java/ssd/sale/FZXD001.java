package ssd.sale;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import ssd.util.Db;
import ssd.util.MyList;
import ssd.util.MyMap;
import ssd.util.Sql;

/**
 * Created by Administrator on 2014/11/15.
 */
public class FZXD001 extends ListFragment implements Button.OnClickListener {
    private Button mButton_XinZ;
    private CheckBox mCheckBox;
    private FZXD002 fzxd002;
    private FragmentManager fragmentManager;
    private MyList list;
    private Db db;
    private SQLiteDatabase database;
    private Cursor cursor;

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
        mCheckBox = (CheckBox) view.findViewById(R.id.myCheckBox);
        fzxd002 = new FZXD002();
        fragmentManager = getFragmentManager();
        mButton_XinZ.setOnClickListener(this);

        db = new Db(getActivity());
        database = db.getWritableDatabase();
        cursor = database.query("fuZXD003", null, "shiFQY = ?", new String[]{"1"},
                null, null, "gongYSMC", null);
        list = Sql.parseCursor(cursor);
        myBaseAdapter adapter = new myBaseAdapter(getActivity(), list);
        setListAdapter(adapter);

        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mCheckBox.isChecked()) {
                    cursor = database.query("fuZXD003", null, "shiFQY = ?", new String[]{"1"},
                            null, null, "gongYSMC", null);
                } else {
                    cursor = database.query("fuZXD003", null, null, null, null, null, "gongYSMC", null);
                }

                list = Sql.parseCursor(cursor);
                myBaseAdapter adapter = new myBaseAdapter(getActivity(), list);
                setListAdapter(adapter);
            }
        });
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (view == mButton_XinZ) {
            transaction.replace(R.id.content, fzxd002).commit();
        }
    }

    static class ViewHolder {
        public TextView mTextView_gongYSMC;
        public TextView mTextView_lianXRDH;
        public TextView mTextView_lianXRDH2;
        public TextView mTextView_lianXRDH3;
    }

    public class myBaseAdapter extends BaseAdapter {
        private LayoutInflater mInflater = null;

        public myBaseAdapter(Context context, MyList list1) {
            this.mInflater = LayoutInflater.from(context);
            list = list1;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.fzxd001_item, null);
                holder.mTextView_gongYSMC = (TextView) convertView.findViewById(
                        R.id.myTextView_gongYSMC);
                holder.mTextView_lianXRDH = (TextView) convertView.findViewById(
                        R.id.myTextView_lianXRDH);
                holder.mTextView_lianXRDH2 = (TextView) convertView.findViewById(
                        R.id.myTextView_lianXRDH2);
                holder.mTextView_lianXRDH3 = (TextView) convertView.findViewById(
                        R.id.myTextView_lianXRDH3);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            try {
                final MyMap map = list.getMyMap(position);
                holder.mTextView_gongYSMC.setText(map.getString("gongYSMC"));
                holder.mTextView_lianXRDH.setText(map.getString("lianLRDH"));

                // 如果連繫人電話2沒資料, 則不顯示
                if (map.get("lianLRDH2").toString().equals("")) {
                    holder.mTextView_lianXRDH2.setVisibility(View.GONE);
                } else {
                    holder.mTextView_lianXRDH2.setText(map.getString("lianLRDH2"));
                }
                // 如果連繫人電話3沒資料, 則不顯示
                if (map.get("lianLRDH3").toString().equals("")) {
                    holder.mTextView_lianXRDH3.setVisibility(View.GONE);
                } else {
                    holder.mTextView_lianXRDH3.setText(map.getString("lianLRDH3"));
                }

                // 點擊 供應商名稱, 則跳至該供應商的編輯畫面
                holder.mTextView_gongYSMC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.content, fzxd002);
                        Bundle bundle = new Bundle();
                        bundle.putString("_id", map.getString("_ID"));
                        fzxd002.setArguments(bundle);
                        transaction.commit();
                    }
                });

                /* 停用的資料, 顯示紅字 */
                if (map.getInt("shiFQY") == 0) {
                    holder.mTextView_gongYSMC.setTextColor(getResources().getColor(R.color.red));
                } else {
                    holder.mTextView_gongYSMC.setTextColor(getResources().getColor(R.color.black));
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            return convertView;
        }
    };
}
