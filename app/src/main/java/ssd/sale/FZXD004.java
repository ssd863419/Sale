package ssd.sale;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import ssd.util.DataHelper;
import ssd.util.Sql;
import ssd.util.SqlList;
import ssd.util.SqlMap;
import ssd.util.ViewMap;
import ssd.util._;
import ssd.util.__;


/**
 * Created by Administrator on 2014/12/4.
 */
public class FZXD004 extends Fragment implements Button.OnClickListener{
    private Button mBtn_fanH;               // 按鈕 返回
    private Button mBtn_shaiX;              // 按鈕 篩選
    private CheckBox mCb_zhiKT;             // CheckBox 只看圖
    private TextView mTv_sum;               // 上方的合計數據顯示
    private FZXD003 fzxd003;                // 貨品編輯畫面
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private GridView mGridView;             // 用GridView來顯示
    private ListView mListView;             // 用ListView來顯示
    private SqlList list;
    private DataHelper db;
    private SQLiteDatabase database;
    private Cursor cursor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fzxd004, container, false);
        initView(v);        // 初始化畫面的view
        getData();          // 取得資料

        return v;
    }

    private void initView(View view) {
        mBtn_fanH = (Button) view.findViewById(R.id.myBtn_fanH);
        mBtn_shaiX = (Button) view.findViewById(R.id.myBtn_shaiX);
        mCb_zhiKT = (CheckBox) view.findViewById(R.id.myCb_zhiKT);
        mTv_sum = (TextView) view.findViewById(R.id.myTv_sum);
        mGridView = (GridView) view.findViewById(R.id.myGridView);
        mListView = (ListView) view.findViewById(R.id.myListView);

        fzxd003 = new FZXD003();
        fragmentManager = getFragmentManager();
        db = new DataHelper(getActivity());
        database = db.getWritableDatabase();

        mBtn_fanH.setOnClickListener(this);
        mBtn_shaiX.setOnClickListener(this);
        mCb_zhiKT.setOnCheckedChangeListener(checkBoxChange);


    }

    @Override
    public void onClick(View view) {
        transaction = fragmentManager.beginTransaction();
        switch (view.getId()) {
            case R.id.myBtn_fanH:
                transaction.replace(R.id.content, fzxd003).commit();

                break;

            case R.id.myBtn_shaiX:

                break;
        }
    }

    CompoundButton.OnCheckedChangeListener checkBoxChange = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (mCb_zhiKT.isChecked()) {
                mListView.setVisibility(View.GONE);
                mGridView.setVisibility(View.VISIBLE);
            } else {
                mListView.setVisibility(View.VISIBLE);
                mGridView.setVisibility(View.GONE);
            }
        }
    };

    public class Fzxd004Adapter extends BaseAdapter {
        private LayoutInflater mInflater = null;

        public Fzxd004Adapter(Context context, SqlList list1) {
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
            ViewMap holder;

            if (convertView != null) {                      // 有資料
                holder = (ViewMap) convertView.getTag();
            } else if (mCb_zhiKT.isChecked()){              // 沒資料 且 勾選"只看圖",GridView
                convertView = mInflater.inflate(R.layout.fzxd004_item_grid, null);
                holder = (ViewMap) convertView.getTag();
            } else {                                        // 沒資料 且 未勾選"只看圖", ListView
                convertView = mInflater.inflate(R.layout.fzxd004_item_list, null);
                holder = _.getViews(convertView);
                convertView.setTag(holder);
            }

            if (!mCb_zhiKT.isChecked()) {
                final SqlMap map = list.getMyMap(position);
                holder.getImageView(R.id.myImage).setImageBitmap(__.bytesToBimap(map.getBlob("huoPTP")));
                holder.getTextView(R.id.myTv_jinHR).setText(map.getString("jinHR"));
                holder.getTextView(R.id.myTv_gongYSMC).setText(map.getString("fuZXD003_id"));
                holder.getTextView(R.id.myTv_huoPBZ).setText(map.getString("huoPBZ"));
                holder.getTextView(R.id.myTv_jinHJ).setText(map.getString("jinHJ"));
                holder.getTextView(R.id.myTv_biaoZSJ).setText(map.getString("biaoZSJ"));
                holder.getTextView(R.id.myTv_jinHJS).setText(map.getString("jianS"));
                // TODO 銷售件數, 剩餘件數, 要等到銷售登陸畫面完成, 再回頭來寫
                holder.getTextView(R.id.myTv_xiaoSJS).setText("0");
                holder.getTextView(R.id.myTv_shengYJS).setText("0");
            }

            return convertView;
        }
    };

    private void getData() {
        cursor = database.query("fuZXD002", null, null, null,
                null, null, null, null);

        list = Sql.parseCursor(cursor);

        Fzxd004Adapter adapter = new Fzxd004Adapter(getActivity(), list);
        mListView.setAdapter(adapter);
    }
}
