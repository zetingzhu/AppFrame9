package com.zzt.zt_fragmentscreenact;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zzt.zt_fragmentscreenact.frag.AFragment;
import com.zzt.zt_fragmentscreenact.frag.BFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private FrameLayout fl_content, fl_content_land;

    private AFragment aFragment;
    private BFragment bFragment;
    private TextView tv_to_lan, tv_to_por;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        fl_content = findViewById(R.id.fl_content);
        fl_content_land = findViewById(R.id.fl_content_land);
        tv_to_lan = findViewById(R.id.tv_to_lan);
        tv_to_por = findViewById(R.id.tv_to_por);

        aFragment = AFragment.newInstance("", "");
        bFragment = BFragment.newInstance();

        startFragment(getContextViewIdPor(), aFragment);

        tv_to_lan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
            }
        });

        tv_to_por.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
            }
        });
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Configuration cf = this.getResources().getConfiguration(); //获取设置的配置信息
        int ori = cf.orientation; //获取屏幕方向
        if (ori == Configuration.ORIENTATION_LANDSCAPE) {
            //横屏
            int lanIndex = startFragment(getContextViewIdPor(), bFragment);
            Log.i(TAG, "landscape  :" + lanIndex);

//            Fragment hasFrag =  getSupportFragmentManager().findFragmentByTag(AFragment.class.getSimpleName());
//            if (hasFrag instanceof AFragment) {
//                moveContentFragment(getContextViewIdLand(), hasFrag);
//            }
        } else if (ori == Configuration.ORIENTATION_PORTRAIT) {
            //竖屏
            int porIndex = startFragment(getContextViewIdPor(), aFragment);
            Log.i(TAG, "portrait     :" + porIndex);
        }
    }

    public int startFragment(@IdRes int containerViewId, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.isDestroyed()) {
            return -1;
        }
        if (fragmentManager.isStateSaved()) {
            return -1;
        }
        String tagName = fragment.getClass().getSimpleName();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .setPrimaryNavigationFragment(null)
                .addToBackStack(tagName)
                .replace(containerViewId, fragment, tagName);
        return transaction.commit();
    }

    public void moveContentFragment(@IdRes int contNext, Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.isDestroyed()) {
            return;
        }
        if (manager.isStateSaved()) {
            return;
        }
        String tagName = fragment.getClass().getSimpleName();
        FragmentTransaction t1 = manager.beginTransaction();
        t1.remove(fragment)
                .commit();
        manager.executePendingTransactions();

        FragmentTransaction t2 = manager.beginTransaction();
        t2.addToBackStack(tagName)
                .replace(contNext, fragment, tagName)
                .commit();
        manager.executePendingTransactions();
    }

    private int getContextViewIdPor() {
        return R.id.fl_content;
    }

    private int getContextViewIdLand() {
        return R.id.fl_content_land;
    }


}