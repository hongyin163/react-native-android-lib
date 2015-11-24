package cn.mandata.react_native_android_lib;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ReactProp;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.events.RCTEventEmitter;


/**SwipeRefreshLayout
 * Created by Administrator on 2015/11/22.
 */
public class ScrollViewManager extends ViewGroupManager<SwipeRefreshLayout> {
    private static final String REACT_CLASS = "ScrollViewPullToRefresh";
    @Override
    public String getName() {
        return REACT_CLASS;
    }
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected SwipeRefreshLayout createViewInstance(ThemedReactContext reactContext) {

//        LayoutInflater inflater =LayoutInflater.from(reactContext);
//        contentView = (RelativeLayout)inflater.inflate(R.layout.fragment_pull_to_refresh, null);

        swipeRefreshLayout=  new SwipeRefreshLayout(reactContext);

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        final ReactContext context=reactContext;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                WritableMap event = Arguments.createMap();
                event.putString("message", "MyMessage");
                context.getJSModule(RCTEventEmitter.class).receiveEvent(
                        swipeRefreshLayout.getId(),
                        "topLoadingStart",
                        event);
//                // TODO Auto-generated method stub
//                new Handler().postDelayed(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        // TODO Auto-generated method stub
//
//                        swipeRefreshLayout.setRefreshing(false);
//                    }
//                }, 6000);
            }
        });
        return  swipeRefreshLayout;
    }
    @ReactProp(name="refreshing",defaultBoolean = false)
    public  void  setRefreshing(SwipeRefreshLayout view,boolean refreshing){
        view.setRefreshing(refreshing);
    }


}
