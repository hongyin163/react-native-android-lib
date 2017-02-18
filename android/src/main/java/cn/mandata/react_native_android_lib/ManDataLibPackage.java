package cn.mandata.react_native_android_lib;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cn.mandata.react_native_android_lib.toolbar.ManToolbarManager;
import cn.mandata.react_native_android_lib.umeng.AnalyticsModule;
import cn.mandata.react_native_android_lib.umeng.OAuthLoginModule;
import cn.mandata.react_native_android_lib.util.DevUtilModule;

/**
 * Created by Administrator on 2015/11/22.
 */
public class ManDataLibPackage implements ReactPackage {

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
       return Arrays.<NativeModule>asList(
               new OAuthLoginModule(reactContext),
               new AnalyticsModule(reactContext),
               new DevUtilModule(reactContext)
        );
    }

    @Override
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {

        return Arrays.<ViewManager>asList(
                new ScrollViewManager(),
                new ManToolbarManager()
        );
    }
}
