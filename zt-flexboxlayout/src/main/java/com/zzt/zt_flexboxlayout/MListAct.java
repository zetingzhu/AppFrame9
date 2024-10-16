package com.zzt.zt_flexboxlayout;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.MainActivity;
import com.zzt.adapter.StartActivityRecyclerAdapter;
import com.zzt.entity.StartActivityDao;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zeting
 * @date: 2024/10/12
 */
public class MListAct extends AppCompatActivity {
    private RecyclerView rv_list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_m_list);

        initView();

    }


    private void initView() {
        rv_list = findViewById(R.id.rv_list);

        List<StartActivityDao> mlist = new ArrayList<>();
        mlist.add(new StartActivityDao("FlexboxLayout 小试", "看看怎么个排序", "1"));
        mlist.add(new StartActivityDao("DrawerLayout 小试", "看看现在的 DrawerLayout 好不好用", "2"));
        mlist.add(new StartActivityDao("SlidingMenu 自定义测滑", "自定义的，推出左边测滑", "3"));
        mlist.add(new StartActivityDao("FlexboxLayout And RecycleView", "RecycleView 设置 FlexboxLayoutManager 使用", "4"));


        StartActivityRecyclerAdapter.setAdapterData(rv_list, RecyclerView.VERTICAL, mlist,
                new StartActivityRecyclerAdapter.OnItemClickListener<StartActivityDao>() {
                    @Override
                    public void onItemClick(View itemView, int position, StartActivityDao data) {
                        switch (data.getArouter()) {
                            case "1":
                                MainActivity.start(MListAct.this);
                                break;
                            case "2":
                                MDrawerLayoutAct.start(MListAct.this);
                                break;
                            case "3":
                                MSlidingLayoutAct.start(MListAct.this);
                                break;
                            case "4":
                                MRVFlexBoxAct.start(MListAct.this);
                                break;
                        }
                    }
                });
    }
}
