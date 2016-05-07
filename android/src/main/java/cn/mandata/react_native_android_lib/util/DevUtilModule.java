package cn.mandata.react_native_android_lib.util;

import android.view.KeyEvent;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

/**
 * Created by Administrator on 2016/4/10.
 */
public class DevUtilModule extends ReactContextBaseJavaModule {
    public DevUtilModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "DevUtil";
    }

    @ReactMethod
    public void ShowDevMenu(){
        this.getCurrentActivity().onKeyUp(KeyEvent.KEYCODE_MENU,new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_MENU));
    }
}
