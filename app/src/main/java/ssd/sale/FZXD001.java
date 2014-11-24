package ssd.sale;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

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

    }

    @Override
    public void onClick(View view) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (view == mButton_XinZ) {
            transaction.replace(R.id.content, fzxd002).commit();
        }
    }
}
