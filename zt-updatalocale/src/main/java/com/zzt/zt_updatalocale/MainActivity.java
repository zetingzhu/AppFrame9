package com.zzt.zt_updatalocale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.ConfigurationCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;

import com.blankj.utilcode.util.LanguageUtils;
import com.zzt.adapter.StartActivityRecyclerAdapter;
import com.zzt.entity.StartActivityDao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    String setLocale = "";

    RecyclerView rv_list;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);

        Resources resources = newBase.getResources();
        Configuration configuration = resources.getConfiguration();
        String languageB = configuration.getLocales().get(0).toString();
        Log.d(TAG, "语言 newBase ：" + languageB);

        String languageS = LanguageSp.getLanguage(newBase);
        Log.d(TAG, "语言 储存：" + languageS);

//        Locale locale = new Locale("zh", "CN");
//        configuration.setLocale(locale);
//        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
//        resources.updateConfiguration(configuration, displayMetrics);

    }

    @Override
    public void recreate() {
        super.recreate();
        Log.d(TAG, "语言 > recreate");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setLocale = getResources().getConfiguration().getLocales().get(0).toString();
        Log.d(TAG, "语言 获取当前应用语言：" + setLocale);
        initView();
    }

    private void initView() {
        rv_list = findViewById(R.id.rv_list);

        List<StartActivityDao> mListData = new ArrayList<>();
        mListData.add(new StartActivityDao("切换语言", "各种参数", "0"));
        mListData.add(new StartActivityDao("切换语言", "中文", "1"));
        mListData.add(new StartActivityDao("切换语言", "英文", "2"));
        mListData.add(new StartActivityDao("切换语言", "德语", "3"));
        mListData.add(new StartActivityDao("切换语言", "法语", "4"));

        StartActivityRecyclerAdapter.setAdapterData(
                rv_list,
                RecyclerView.VERTICAL,
                mListData,
                new StartActivityRecyclerAdapter.OnItemClickListener<StartActivityDao>() {
                    @Override
                    public void onItemClick(View itemView, int position, StartActivityDao data) {
                        switch (data.getArouter()) {
                            case "0":
                                StringBuffer sb = new StringBuffer();
                                sb.append("\nal:").append(LanguageUtils.isAppliedLanguage());
                                sb.append("\nalz:").append(LanguageUtils.isAppliedLanguage(Locale.SIMPLIFIED_CHINESE));
                                sb.append("\ngal:").append(LanguageUtils.getAppliedLanguage());
                                sb.append("\ngcl:").append(LanguageUtils.getContextLanguage(MainActivity.this));
                                sb.append("\ngacl:").append(LanguageUtils.getAppContextLanguage());
                                sb.append("\ngsl:").append(LanguageUtils.getSystemLanguage());
                                Log.d(TAG, "语言 参数：" + sb);
                                break;
                            case "1":
                                LanguageUtils.applyLanguage(new Locale("zh", "", ""), true);
                                break;
                            case "2":
                                LanguageUtils.applyLanguage(new Locale("en", "", ""), false);
                                break;
                            case "3":
                                LanguageUtils.applyLanguage(Locale.GERMAN, false);
                                break;
                            case "4":
                                LanguageUtils.applyLanguage(Locale.FRANCE, false);
                                break;
                        }
                    }
                }
        );
    }

    /**
     * 获取系统语言
     *
     * @return
     */
    public String getSystemLocal() {
        // 第一种方式
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            return Resources.getSystem().getConfiguration().getLocales().get(0).getLanguage();//解决了获取系统默认错误的问题
//        } else {
//            return Locale.getDefault().getLanguage();
//        }
// 第二种方式(推荐)
        return ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0).getLanguage();
    }

}