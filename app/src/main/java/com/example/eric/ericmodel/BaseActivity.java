package com.example.eric.ericmodel;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Eric on 2017/10/18.
 */

public class BaseActivity extends Activity implements SensorEventListener {
    public TextView tx1;
    public TextView tx2;
    public float[] angle = new float[3];
    public long lastTime;
    public SensorManager sm;
    public String content;
    private OnChangeListener onChangeListener;
    private Sensor acceleromererSensor;

    public List<Sensor> initSensorS() {
        //获取手机所有的传感器
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        return sm.getSensorList(Sensor.TYPE_ALL);
    }

    public void initSensor(Context ctx, int type) {
        //根据传入的传感器类型初始化传感器
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acceleromererSensor = sm.getDefaultSensor(type);
        // //注册传感器，第一个参数为距离监听器，第二个是传感器类型，第三个是获取数据速度
        sm.registerListener(this, acceleromererSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    public static interface OnChangeListener {

        public void onAccuracyChanged2(Sensor sensor, int accuracy);

        public void onSensorChanged2(SensorEvent event);

    }

    public final void setOnChangeListener(OnChangeListener listener) {
        onChangeListener = listener;
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (sm != null) {
            //理解为取消这个事件监听实现注销传感器
            sm.unregisterListener(this);
        }
    }

    // 向量求模
    public double magnitude(float x, float y, float z) {
        double magnitude = 0;
        magnitude = Math.sqrt(x * x + y * y + z * z);
        return magnitude;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        onChangeListener.onSensorChanged2(event);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
        // 精度发生改变
        // 什么也不干
        onChangeListener.onAccuracyChanged2(sensor, accuracy);
    }
}
