package com.example.eric.ericmodel.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.eric.ericmodel.bean.PointBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 2017/9/26.
 */

public class PointCanvasView extends View {

    private int colorOne;
    private Paint paint;
    private List<PointBean> pointList = new ArrayList<>();
    private List<PointBean> pList = new ArrayList<>();
    private float pointX = 0, pointY = 0;
    private boolean flag;
    private int indexNumber = 10;   //每条线上点的数量
    private int index=1;            //自动打点时的线条
    private boolean isAutomatic;


    public PointCanvasView(Context context) {
        super(context);
    }

    public PointCanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }
    public void draw(Canvas canvas) {
        paint.setStrokeWidth(10.0f);
        for (int i = 0; i < pointList.size(); i++) {
            paint.setColor(Color.GREEN);
            PointBean bean = pointList.get(i);
            canvas.drawCircle(bean.getX(), bean.getY(), 20, paint);// 小圆
            if (i > 0) {
                paint.setColor(Color.BLUE);
                PointBean lastBean = pointList.get(i - 1);
                canvas.drawLine(bean.getX(), bean.getY(), lastBean.getX(), lastBean.getY(), paint);
                if (flag) {
                    setPlist(lastBean, bean);
                }
            }
        }
        for (PointBean bean : pList) {
//                paint.setColor(bean.getColor());
            paint.setColor(Color.BLACK);
            canvas.drawCircle(bean.getX(), bean.getY(), 20, paint);// 小圆
        }
    }

    public void setPointList(List<PointBean> pointList) {
        this.pointList.clear();
        this.pointList.addAll(pointList);
        invalidate();
    }

    public void DrawPointLine(float xx, float yy) {
        pointX = xx;
        pointY = yy;
        pointList.add(new PointBean(xx, yy));
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        DrawPointLine(event.getX(), event.getY());
        invalidate();//更新绘制
        return super.onTouchEvent(event);
    }

    private void setPlist(PointBean bean1, PointBean bean2) {
        float indexY = bean2.getY() - bean1.getY();
        float indexX = bean2.getX() - bean1.getX();
        for (int i = 0; i < indexNumber; i++) {
            pList.add(new PointBean(bean1.getX() + indexX / indexNumber * i, bean1.getY() + indexY / indexNumber * i));
        }
    }

    public void setPoint(boolean flag) {
        this.flag = flag;
        invalidate();
    }

    public void setIndexNumber(int indexNumber) {
        this.indexNumber = indexNumber;
    }
    public void nextLine() {
        if(pointList.size()>1){
            PointBean bean = pointList.get(index);
            PointBean lastBean = pointList.get(index - 1);
            setPlist(lastBean, bean);
            invalidate();
            index++;
        }
    }
    /**
     * 获取画板的bitmap
     *
     * @return
     */
    public Bitmap getBitmap() {
        invalidate();
        setDrawingCacheEnabled(true);
        Bitmap bitmap = getDrawingCache(true);
        return bitmap;
    }
}
