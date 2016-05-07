package cn.mandata.react_native_android_lib.toolbar;

import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.toolbar.ReactToolbar;
import com.facebook.react.views.toolbar.ReactToolbarManager;

/**
 * Created by Administrator on 2015/12/19.
 */
public class ManToolbarManager extends ReactToolbarManager {

    @Override
    public String getName() {
        return "ManToolbar";
    }

    @ReactProp(name = "menuVisible")
    public void setMenuVisible(ReactToolbar view, boolean visible) {
        if(visible){
            view.showOverflowMenu();
        }else{
            view.hideOverflowMenu();
        }
    }
}
