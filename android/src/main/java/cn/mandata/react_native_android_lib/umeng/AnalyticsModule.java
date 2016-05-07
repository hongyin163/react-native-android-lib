package cn.mandata.react_native_android_lib.umeng;

import android.app.Activity;
import android.content.Intent;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2016/3/16.
 */
public class AnalyticsModule extends ReactContextBaseJavaModule {
    private String MODULE_NAME="UmengAnalytics";
    private final ReactApplicationContext appContext;
    private  final Activity currentActivity;
    public AnalyticsModule(ReactApplicationContext reactContext) {
        super(reactContext);
        appContext=reactContext;
        currentActivity=this.getCurrentActivity();

//        appContext.addLifecycleEventListener(new LifecycleEventListener() {
//            @Override
//            public void onHostResume() {
//                MobclickAgent.onResume(currentActivity);
//            }
//
//            @Override
//            public void onHostPause() {
//                MobclickAgent.onPause(currentActivity);
//            }
//
//            @Override
//            public void onHostDestroy() {
//
//            }
//        });
    }
    @ReactMethod
    public void setAppkey(String appKey){
        AnalyticsConfig.setAppkey(currentActivity,appKey);
    }
    @ReactMethod
    public void setChannel(String channel){
        AnalyticsConfig.setChannel(channel);
    }

    @ReactMethod
    public  void  onPageStart(String pageName){
        MobclickAgent.onPageStart(pageName);
    }
    @ReactMethod
    public  void  onPageEnd(String pageName){
        MobclickAgent.onPageStart(pageName);
    }
    @Override
    public String getName() {
        return MODULE_NAME;
    }
}
