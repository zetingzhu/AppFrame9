package com.zzt.zt_flexboxlayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.zzt.zt_flexboxlayout.adapter.FlexBoxAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author: zeting
 * @date: 2024/10/12
 */
public class MRVFlexBoxAct extends AppCompatActivity {
    public static void start(Context context) {
        Intent starter = new Intent(context, MRVFlexBoxAct.class);
        context.startActivity(starter);
    }

    private RecyclerView rv_box;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_m_rv_flexbox_layout);
        initView();
    }

    private void initView() {
        rv_box = findViewById(R.id.rv_box);

        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(MRVFlexBoxAct.this);

        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);// 设置项目排序方向
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);// 正常换行
        flexboxLayoutManager.setAlignItems(AlignItems.STRETCH);// 上下轴对齐方式
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);// 左右对齐方式

        rv_box.setLayoutManager(flexboxLayoutManager);

        List<String> mList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            mList.add(generateRandomString());
        }
        FlexBoxAdapter flexBoxAdapter = new FlexBoxAdapter(mList);
        rv_box.setAdapter(flexBoxAdapter);
    }

    public static String generateRandomString() {
        String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        int length = Math.max(2, random.nextInt(25));
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(charSet.length());
            char randomChar = charSet.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }


}
