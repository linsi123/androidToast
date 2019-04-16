package com.androidtoast;

import android.widget.Toast;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.LifecycleEventListener;


import java.util.Map;
import java.util.HashMap;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

import com.facebook.react.uimanager.PixelUtil;

import javax.annotation.Nullable;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import com.facebook.react.ReactInstanceManager;


public class ToastModule extends ReactContextBaseJavaModule {

    private final static String MODULE_NAME = "ToastExample";
    private static final String DURATION_SHORT_KEY = "SHORT";
    private static final String DURATION_LONG_KEY = "LONG";


    public ToastModule(ReactApplicationContext reactContext) {
        super(reactContext);
//        reactContext.addLifecycleEventListener(this);
    }

//    @Override
//    public void onHostResume() {
        //
//        Log.i(TAG, "onHostResume");
//    }


    @Override
    public String getName() {
        return MODULE_NAME;
    }

    public Boolean flag = true;

    public int count = 1;


    private static final String E_LAYOUT_ERROR = "E_LAYOUT_ERROR";

    /**
     * 方法可以返回一些常量用于react-native中调用
     * @return Map
     */
    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put(DURATION_SHORT_KEY, Toast.LENGTH_SHORT);
        constants.put(DURATION_LONG_KEY, Toast.LENGTH_LONG);
        return constants;
    }


    private @javax.annotation.Nullable ReactInstanceManager mReactInstanceManager;


    /**
     * 1.@ReactMethod 用于java返回一个react-native中可调用的方法
     * 2.java中提供了一个Callback的数据类型对应了react-native中的function
     * 3.发送事件
     *
     *
     *
     * @param message
     * @param duration
     */
    @ReactMethod
    public void show(String message,int duration,Callback successCallCallback,Callback errorCallback){
        Toast.makeText(getReactApplicationContext(),message,duration).show();

        // 2.cb
        if(flag) successCallCallback.invoke("success",++count);
        else errorCallback.invoke("error",++count);

        flag = !flag;

        // 3.event
        WritableMap params = Arguments.createMap();
        params.putBoolean("boolean",true);
        params.putDouble("double",0.003);
        params.putString("string","string");
        sendEvent(getReactApplicationContext(), "onRefreshMessage",params);
    }
    private final int[] mMeasureBuffer = new int[4];

    /**
     * promises
     * @param tag
     * @param ancestorTag
     * @param promise
     */
    @ReactMethod
    public void measureLayout(int tag, int ancestorTag, Promise promise) {
       //measureLayout(tag, ancestorTag, mMeasureBuffer);

        WritableMap map = Arguments.createMap();

        map.putDouble("relativeX", PixelUtil.toDIPFromPixel(mMeasureBuffer[0]));
        map.putDouble("relativeY", PixelUtil.toDIPFromPixel(mMeasureBuffer[1]));
        map.putDouble("width", PixelUtil.toDIPFromPixel(mMeasureBuffer[2]));
        map.putDouble("height", PixelUtil.toDIPFromPixel(mMeasureBuffer[3]));

        promise.resolve(map);
    }


    /**
     * 发送事件到js
     */
    private void sendEvent(ReactContext reactContext,String eventName,@Nullable WritableMap params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }

}