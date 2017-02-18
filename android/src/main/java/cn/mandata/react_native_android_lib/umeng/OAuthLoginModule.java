package cn.mandata.react_native_android_lib.umeng;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.RootViewUtil;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.view.ReactViewGroup;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareAPI;

import java.util.Map;

import javax.annotation.Nullable;

/**
 * Created by Administrator on 2016/4/24.
 */
public class OAuthLoginModule extends ReactContextBaseJavaModule implements Runnable {
    private String MODULE_NAME="OAuthLogin";
    private int  COMMAND_LOGIN=0;
    private ReactContext reactContext=null;
    private String target;
    private Context activity;
    private Callback successCallback;
    private Callback errorCallback;
    private UMShareAPI mShareAPI;
    private  final Activity currentActivity;
    public OAuthLoginModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext=reactContext;
        currentActivity=this.getCurrentActivity();

        reactContext.addActivityEventListener(new ActivityEventListener() {
            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                UmengLogin.onActivityResult(requestCode, resultCode, data);
            }
        });
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    private ReadableMap platform;
    @ReactMethod
    public void setConfig(ReadableMap platform){

        this.platform=platform;

        UmengLogin.init(this.getCurrentActivity(),this.platform);
    }

    @ReactMethod
    public void verify(Callback callback){
        UmengLogin.Verify(this.getCurrentActivity(),callback);
    }
    @ReactMethod
    public void login(String target,Callback call) {
        this.successCallback=call;
        this.target=target;
        this.getReactApplicationContext()
                .getCatalystInstance()
                .getReactQueueConfiguration()
                .getUIQueueThread()
                .runOnQueue(this);
    }

    @Override
    public void run() {
        final Activity activity=this.getCurrentActivity();
        //UmengLogin.init(activity,this.platform);
        UmengLogin.Login(activity,target,this.successCallback);
    }
}
