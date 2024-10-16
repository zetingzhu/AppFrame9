package com.zzt.flow;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.zzt.adapter.StartActivityRecyclerAdapter;
import com.zzt.entity.StartActivityDao;
import com.zzt.flow.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.functions.Function0;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private ZFlowUtil zFlowUtil = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        zFlowUtil = new ZFlowUtil();

        initView();
    }

    private void initView() {
        List<StartActivityDao> mlist = new ArrayList<>();
        mlist.add(new StartActivityDao("flow 测试", "测试内容", "1"));
        StartActivityRecyclerAdapter.setAdapterData(binding.rvList, RecyclerView.VERTICAL, mlist,
                new StartActivityRecyclerAdapter.OnItemClickListener<StartActivityDao>() {
                    @Override
                    public void onItemClick(View itemView, int position, StartActivityDao data) {
                        switch (data.getArouter()) {
                            case "1":
                                zFlowUtil.runBlocking(new ZFlowUtil.FlowCallback() {
                                    @Nullable
                                    @Override
                                    public Object doBack() {
                                        try {
                                            Thread.sleep(2000L);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }

                                        return new StartActivityDao("发送的内容", "", "2");
                                    }

                                    @Override
                                    public void uiView(@Nullable Object any) {
                                        Log.d("TAG", "接收信息 1 ：" + any);
                                        if (any instanceof StartActivityDao) {
                                            Log.d("TAG", "接收信息 2 ：" + ((StartActivityDao) any).getTitle());
                                            Toast.makeText(MainActivity.this, ((StartActivityDao) any).getTitle(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                break;
                        }
                    }
                });
    }
}