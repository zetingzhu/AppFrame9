//package com.zzt.zt_facebooklogin;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.util.Log;
//
//import androidx.fragment.app.Fragment;
//
//import com.facebook.AccessToken;
//import com.facebook.CallbackManager;
//import com.facebook.FacebookCallback;
//import com.facebook.FacebookException;
//import com.facebook.login.LoginManager;
//import com.facebook.login.LoginResult;
//import com.facebook.login.widget.LoginButton;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * @author: zeting
// * @date: 2024/7/30
// */
//public class FacebookSignUtil {
//    private static final String TAG = FacebookSignUtil.class.getSimpleName();
//    private static final String EMAIL = "email";
//    private static final String INSTAGRAM_BASIC = "instagram_basic";
//    private static final String USER_POSTS = "user_posts";
//    private static final String AUTH_TYPE = "rerequest";
//    private static final String PUBLIC_PROFILE = "public_profile";
//    private static final String USER_STATUS = "user_status";
//    private CallbackManager callbackManager;
//
//    public void fbOnCreate() {
//        callbackManager = CallbackManager.Factory.create();
//        LoginManager.getInstance().registerCallback(callbackManager,
//                new FacebookCallback<LoginResult>() {
//                    @Override
//                    public void onSuccess(LoginResult loginResult) {
//                        // App code
//                        Log.d(TAG, "onSuccess 2> " + loginResult);
//                    }
//
//                    @Override
//                    public void onCancel() {
//                        // App code
//                        Log.d(TAG, "onCancel 2> ");
//                    }
//
//                    @Override
//                    public void onError(FacebookException exception) {
//                        // App code
//                        Log.d(TAG, "onError 2> " + exception);
//                    }
//                });
//    }
//
//
//    public void fbBtnLogin(LoginButton loginButton) {
////        loginButton.setReadPermissions(Arrays.asList(USER_STATUS));
//        List<String> ps = new ArrayList<>();
//        ps.add(EMAIL);
//        ps.add(USER_POSTS);
//        loginButton.setPermissions(ps);
//
//        loginButton.setAuthType(AUTH_TYPE);
//        // If you are using in a fragment, call loginButton.setFragment(this);
//
//        // Callback registration
//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                // App code
//                Log.d(TAG, "onSuccess > " + loginResult);
//            }
//
//            @Override
//            public void onCancel() {
//                // App code
//                Log.d(TAG, "onCancel > ");
//            }
//
//            @Override
//            public void onError(FacebookException exception) {
//                // App code
//                Log.d(TAG, "onError > " + exception);
//            }
//        });
//    }
//
//
//    public void fbLoginV2(Fragment fragment) {
//        LoginManager.getInstance().logInWithReadPermissions(
//                fragment,
//                Arrays.asList("email"));
//    }
//
//
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//    }
//
//    public void fbCheckLoginStatus(Activity activity) {
//        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
//        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile"));
//    }
//}
