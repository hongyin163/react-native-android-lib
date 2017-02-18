package cn.mandata.react_native_android_lib.umeng;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.views.view.ReactViewGroup;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Administrator on 2016/4/17.
 */
public class UmengLogin {
    private static UMShareAPI mShareAPI;

    public static void init(Activity comp, ReadableMap platform) {

        ReadableArray appData = platform.getArray("weixin");
        String appKey=appData.getString(0);
        String appSecret= appData.getString(1);
        PlatformConfig.setWeixin(appKey, appSecret);
        //微信 appid appsecret

        appData = platform.getArray("sina");
        PlatformConfig.setSinaWeibo(appData.getString(0), appData.getString(1));
        //新浪微博 appkey appsecret

        appData = platform.getArray("qq");
        PlatformConfig.setQQZone(appData.getString(0), appData.getString(1));
        // QQ和Qzone appid appkey

        //PlatformConfig.setAlipay("2015111700822536");
        //支付宝 appid
        mShareAPI = UMShareAPI.get(comp);
    }

    public static void Login(Activity comp, String target, final Callback callback) {

        target = target.toUpperCase();
        SHARE_MEDIA targetMedia = SHARE_MEDIA.valueOf(target);
        final Activity activity = comp;
        final Context appContext = activity.getApplicationContext();

        mShareAPI.doOauthVerify(activity, targetMedia, new UMAuthListener() {
            @Override
            public void onComplete(SHARE_MEDIA platform, int i, Map<String, String> value) {

                if (value == null) return;

                Toast.makeText(appContext, "授权成功", Toast.LENGTH_SHORT).show();
                String userId="";
                if(value.containsKey("uid")){
                    userId=value.get("uid");
                }else if(value.containsKey("unionid")){
                    userId=value.get("unionid");
                }

                final String uid =userId;

                final String password = value.get("access_token");
                android.util.Log.i("TestData", "uid" + uid);
                android.util.Log.i("TestData", "access_token" + password);
                SharedPreferences preferences = appContext.getSharedPreferences(
                        "userlogin", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("uid", uid);
                editor.putString("password", password);
                editor.putString("source", platform.toString());
                editor.commit();

                mShareAPI.getPlatformInfo(activity, platform,
                        new UMAuthListener() {
                            @Override
                            public void onComplete(SHARE_MEDIA platform, int i, Map<String, String> info) {

                                String screen_name = "", profile_image_url = "", gender = "";
                                switch (platform) {
                                    case SINA:
                                    case QQ:
                                        screen_name = info.get("screen_name") + "";
                                        profile_image_url = info.get("profile_image_url") + "";
                                        gender = info.get("gender") + "";
                                        break;
                                    case WEIXIN:
                                        screen_name = info.get("nickname") + "";
                                        profile_image_url = info.get("headimgurl") + "";
                                        gender = info.get("sex") + "";
                                        break;
                                    default:
                                        break;
                                }

                                SharedPreferences preferences0 = appContext.getSharedPreferences("userlogin", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor0 = preferences0.edit();
                                editor0.putString("username", screen_name);
                                editor0.putString("profile_image_url", profile_image_url);
                                String genderText="";
                                if (gender.equals("1") || gender.equals("男")) {
                                    genderText="男";
                                } else {
                                    genderText="女";
                                }
                                editor0.putString("gender", genderText);
                                editor0.commit();

                                WritableMap data= Arguments.createMap();
                                try {
                                    data.putString("id", uid);
                                    data.putString("username", screen_name);
                                    data.putString("password", password);
                                    data.putString("sso", platform.toString());
                                    data.putString("gender", genderText);
                                    data.putString("image_url",profile_image_url);
                                    callback.invoke(data);

                                } catch(Exception ex) {
                                    // TODO Auto-generated catch
                                    // block
                                    ex.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                                Toast.makeText(appContext, "获取信息失败", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA share_media, int i) {

                            }

                        });

            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Toast.makeText(appContext, "授权失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });
    }



    public static void Verify(Activity comp,Callback callback){
        SharedPreferences refer=comp.getApplicationContext().getSharedPreferences("userlogin",Context.MODE_PRIVATE);
        if(refer==null){
            callback.invoke(false);
        }else {
            String id=refer.getString("id","");
            String username = refer.getString("username", "");
            String pwd=refer.getString("password","");
            String image_url=refer.getString("profile_image_url","");
            String gender=refer.getString("gender","");

            WritableMap v= Arguments.createMap();
            v.putString("id",id);
            v.putString("username",username);
            v.putString("password",pwd);
            v.putString("image_url",image_url);
            v.putString("gender",gender);
            callback.invoke(true,v);
        }
    }
    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }

}
