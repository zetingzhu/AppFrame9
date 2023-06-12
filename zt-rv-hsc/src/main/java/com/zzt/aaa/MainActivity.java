package com.zzt.aaa;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.zzt.zt_rv_hsc.MAdapter;
import com.zzt.zt_rv_hsc.MData;
import com.zzt.zt_rv_hsc.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    RecycleViewNestHSv zt_rv;
    MAdapter rvAdapter;
    LinearLayout mHeaderView;
    RVNestHorizontalScrollView hs_right_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        zt_rv = findViewById(R.id.zt_rv);
        mHeaderView = findViewById(R.id.ll_header_view);
        hs_right_title = findViewById(R.id.hs_right_title);

        addHeaderView();
        rvAdapter = new MAdapter(initDataTest(), zt_rv);
        zt_rv.setAdapter(rvAdapter);

        try {
            int sss = Integer.parseInt(String.valueOf(55.0));
            Log.d("", ">>>>>>" + sss);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    public List<MData> initDataTest() {
        List<MData> mDataList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            MData mData = new MData("标题" + i);
            int randomNum = random.nextInt(1000);
            mData.setDay1("D1-" + randomNum);
            mData.setDay2("D2-" + randomNum);
            mData.setDay3("D3-" + randomNum);
            mData.setDay4("D4-" + randomNum);
            mData.setDay5("D5-" + randomNum);
            mData.setDay6("D6-" + randomNum);
            mDataList.add(mData);
        }
        return mDataList;
    }

    private void addHeaderView() {
        hs_right_title.setRecycleView(zt_rv);
    }
}