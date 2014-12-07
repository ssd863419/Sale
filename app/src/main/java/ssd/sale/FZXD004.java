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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    private RadioGroup mRg_columnNumber;    // 用來選擇GridView以幾列顯示

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
        mRg_columnNumber = (RadioGroup) view.findViewById(R.id.myRg_columnNumber);

        fzxd003 = new FZXD003();
        fragmentManager = getFragmentManager();
        db = new DataHelper(getActivity());
        database = db.getWritableDatabase();

        mBtn_fanH.setOnClickListener(this);
        mBtn_shaiX.setOnClickListener(this);
        mCb_zhiKT.setOnCheckedChangeListener(checkBoxChange);
        mRg_columnNumber.setOnCheckedChangeListener(radioButtonChange);

        mGridView.setVisibility(View.GONE);             // 一開始不顯示GridView
        mRg_columnNumber.setVisibility(View.GONE);      // 一開始不顯示幾列選項

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
                mRg_columnNumber.setVisibility(View.VISIBLE);
                mGridView.setNumColumns(4);             // 預設以4列顯示
                mGridView.setHorizontalSpacing(1);      // 設置每個Grid之間的水平間距
                mGridView.setVerticalSpacing(1);        // 設置每個Grid之間的垂直間距
                getData();
            } else {
                mListView.setVisibility(View.VISIBLE);
                mGridView.setVisibility(View.GONE);
                mRg_columnNumber.setVisibility(View.GONE);
                getData();
            }
        }
    };

    RadioGroup.OnCheckedChangeListener radioButtonChange = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.myRb_2:
                    mGridView.setNumColumns(2);
                    break;
                case R.id.myRb_3:
                    mGridView.setNumColumns(3);
                    break;
                case R.id.myRb_4:
                    mGridView.setNumColumns(4);
                    break;
                case R.id.myRb_5:
                    mGridView.setNumColumns(5);
                    break;
            }
        }
    };

    // TODO wxy 寫了2個BaseAdapter, 分別用於顯示ListView, GridView, 看看是否該合併在一個BaseAdapter
    public class Fzxd004AdapterList extends BaseAdapter {
        private LayoutInflater mInflater = null;

        public Fzxd004AdapterList(Context context, SqlList list1) {
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
            final SqlMap map = list.getMyMap(position);

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.fzxd004_item_list, null);
                holder = _.getViews(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewMap) convertView.getTag();
            }

            holder.getImageView(R.id.myImage).setImageBitmap(__.bytesToBimap(map.getBlob("huoPTP")));
            holder.getTextView(R.id.myTv_gongYSMC).setText(map.getString("gongYSMC"));
            holder.getTextView(R.id.myTv_jinHR).setText(map.getString("jinHR").substring(0, 10));
            holder.getTextView(R.id.myTv_gongYSXH).setText(map.getString("gongYSXH"));
            holder.getTextView(R.id.myTv_huoPBZ).setText(map.getString("huoPBZ"));
            holder.getTextView(R.id.myTv_jinHJ).setText(map.getString("jinHJ"));
            holder.getTextView(R.id.myTv_biaoZSJ).setText(map.getString("biaoZSJ"));
            holder.getTextView(R.id.myTv_jinHJS).setText(map.getString("jianS"));
            // TODO 銷售件數, 剩餘件數, 要等到銷售登陸畫面完成, 再回頭來寫
            holder.getTextView(R.id.myTv_xiaoSJS).setText("0");
            holder.getTextView(R.id.myTv_shengYJS).setText("0");

            // 點擊 圖片, 則跳至該貨品的編輯畫面
            holder.getImageView(R.id.myImage).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.content, fzxd003);
                    Bundle bundle = new Bundle();
                    bundle.putString("_id", String.valueOf(map.getInt("_id")));
                    fzxd003.setArguments(bundle);
                    transaction.commit();
                }
            });

            return convertView;
        }
    }

    public class Fzxd004AdapterGrid extends BaseAdapter {
        private LayoutInflater mInflater = null;

        public Fzxd004AdapterGrid(Context context, SqlList list1) {
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
            final SqlMap map = list.getMyMap(position);

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.fzxd004_item_grid, null);
                holder = _.getViews(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewMap) convertView.getTag();
            }

            holder.getImageView(R.id.myImage).setImageBitmap(__.bytesToBimap(map.getBlob("huoPTP")));
            holder.getTextView(R.id.myTv_gongYSXH).setText(map.getString("gongYSXH"));

            // 點擊 圖片, 則跳至該貨品的編輯畫面
            holder.getImageView(R.id.myImage).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.content, fzxd003);
                    Bundle bundle = new Bundle();
                    bundle.putString("_id", String.valueOf(map.getInt("_id")));
                    fzxd003.setArguments(bundle);
                    transaction.commit();
                }
            });

            return convertView;
        }
    }

    private void getData() {
        String sql = "select a.*, b.gongYSMC " +
                "from fuZXD002 a " +
                "left join fuZXD003 b on a.fuZXD003_id = b._id " +
                "where a.shiFQY = 1 " +
                "order by a.updDay DESC";

        cursor = database.rawQuery(sql, null);
        list = Sql.parseCursor(cursor);


        if (mCb_zhiKT.isChecked()) {
            Fzxd004AdapterGrid adapter = new Fzxd004AdapterGrid(getActivity(), list);
            mGridView.setAdapter(adapter);
        } else {
            Fzxd004AdapterList adapter = new Fzxd004AdapterList(getActivity(), list);
            mListView.setAdapter(adapter);
        }
    }


}
