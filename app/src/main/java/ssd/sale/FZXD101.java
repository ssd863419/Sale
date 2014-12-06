package ssd.sale;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TableRow;

import ssd.util.DataHelper;

/**
 * Created by 0_o on 2014/12/5.
 */
public class FZXD101 extends Fragment { // implements Button.OnClickListener{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fzxd101, container, false);
        initView(v);
        return v;
    }

    private void initView(View view) {

    }

/*
    @Override
    public void onClick(View view) {

    }
    */
}
