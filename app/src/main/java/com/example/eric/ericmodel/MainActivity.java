package com.example.eric.ericmodel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eric.ericmodel.widget.PointCanvasView;

import org.xutils.view.annotation.ViewInject;

import java.io.IOException;

public class MainActivity extends BaseActivity {

    @ViewInject(R.id.pv)
    PointCanvasView pointCanvasView;
    private TextView point;
    private ImageView show;
     Bitmap bitmap;
    Canvas canvas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        pointCanvasView= (PointCanvasView) findViewById(R.id.pv);
        show= (ImageView) findViewById(R.id.iv_show);

        bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher).copy(Bitmap.Config.ARGB_8888, true);
//        Bitmap bitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        canvas  = new Canvas(bitmap);
        canvas.drawBitmap(bitmap,0,0,null);
        pointCanvasView.draw(canvas);

        point= (TextView) findViewById(R.id.tv_drow_point);
        point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pointCanvasView.nextLine();
                show.setImageBitmap(pointCanvasView.getBitmap());
            }
        });
        findViewById(R.id.tv_point).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pointCanvasView.setPoint(true);

            }
        });

        tx2 = (TextView) findViewById(R.id.TextView02);
        initSensor(this, Sensor.TYPE_LINEAR_ACCELERATION);
        setOnChangeListener(new OnChangeListener() {
            @Override
            public void onAccuracyChanged2(Sensor sensor, int accuracy) {

            }

            @Override
            public void onSensorChanged2(SensorEvent event) {
                long currentTime = System.currentTimeMillis();
                // 没100毫秒取一次距离
                if (currentTime - lastTime < 100)
                    return;
                lastTime = currentTime;
                // 获取加速度传感器的三个参数
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                // 手机加速度矢量
                angle[0] = x;
                angle[1] = y;
                angle[2] = z;
                content = String.format("X:%8.4f , Y:%8.4f , Z:%8.4f", event.values[0],
                        event.values[1], event.values[2]);
                tx2.setText(content);
            }
        });


    }

    public void saveBitmapToJPG(Bitmap bitmap) throws IOException {
//        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(newBitmap);
//        canvas.drawColor(Color.WHITE);
//        canvas.drawBitmap(bitmap, 0, 0, null);
        show.setImageBitmap(bitmap);
      /*  OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        stream.close();*/
    }
    public Bitmap getBitmap() {
        Bitmap whiteBgBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(whiteBgBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        return whiteBgBitmap;
    }

  /*  public void onSensorChanged2(SensorEvent event) {
        // TODO Auto-generated method stub
        long currentTime = System.currentTimeMillis();
        // 没100毫秒取一次距离
        if (currentTime - lastTime < 100)
            return;
        lastTime = currentTime;
        // 获取加速度传感器的三个参数
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        // 手机加速度矢量
        angle[0] = x;
        angle[1] = y;
        angle[2] = z;
        content = String.format("X:%8.4f , Y:%8.4f , Z:%8.4f", event.values[0],
                event.values[1], event.values[2]);
        tx2.setText(content);
    }*/
}