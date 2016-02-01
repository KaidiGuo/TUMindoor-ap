package com.mycompany.tum_map_2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by kaidi on 1/7/2016.
 */
public class ScaleImage extends ImageView {
    //初始狀態的Matrix
    private Matrix mMatrix = new Matrix();
    //進行變動狀況下的Matrix
    private Matrix mChangeMatrix = new Matrix();
    //圖片的Bitmap
    private Bitmap mBitmap = null;
    //手機畫面尺寸資訊
    private DisplayMetrics mDisplayMetrics;
    //設定縮放最小比例
    private float mMinScale = 0.5f;
    //設定縮放最大比例
    private float mMaxScale = 5.0f;
    //圖片狀態 - 初始狀態
    private  static final int STATE_NONE = 0;
    //圖片狀態 - 拖動狀態
    private static final int STATE_DRAG = 1;
    //圖片狀態 - 縮放狀態
    private static final int STATE_ZOOM = 2;
    //當下的狀態
    private int mState = STATE_NONE;
    //第一點按下的座標
    private PointF mFirstPointF = new PointF();
    //第二點按下的座標
    private PointF mSecondPointF = new PointF();
    //兩點距離
    private float mDistance = 1f;
    //圖片中心座標
    private float mCenterX,mCenterY;
    //ScaleImage類別，xml呼叫運用
    public ScaleImage(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        //取得圖片Bitmap
        BitmapDrawable mBitmapDrawable = (BitmapDrawable) this.getDrawable();
        if(mBitmapDrawable != null)
        {
            mBitmap = mBitmapDrawable.getBitmap();
            build_image();
        }
    }
    //圖片縮放層級設定
    private void Scale()
    {
        //取得圖片縮放的層級
        float level[] = new float[9];
        mMatrix.getValues(level);

        //狀態為縮放時進入
        if (mState == STATE_ZOOM)
        {
            //若層級小於1則縮放至原始大小
            if (level[0] < mMinScale)
            {
                mMatrix.setScale(mMinScale, mMinScale);
                mMatrix.postTranslate(mCenterX,mCenterY);
            }

            //若縮放層級大於最大層級則顯示最大層級
            if (level[0] > mMaxScale)  mMatrix.set(mChangeMatrix);
        }
    }

    //兩點距離
    private float Spacing(MotionEvent event)
    {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float)Math.sqrt(x * x + y * y);
    }

    //兩點中心
    private void MidPoint(PointF point, MotionEvent event)
    {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    //圖片縮放設定
    public void build_image()
    {
        //取得Context
        Context mContext = getContext();
        //取得手機畫面尺寸資訊
        mDisplayMetrics = mContext.getResources().getDisplayMetrics();

        //設置縮放的型態
        this.setScaleType(ScaleType.MATRIX);
        //將Bitmap帶入
        this.setImageBitmap(mBitmap);

        //將圖片放置畫面中央
        mCenterX = (float)((mDisplayMetrics.widthPixels/2)-(mBitmap.getWidth()/2));
        mCenterY = (float)((mDisplayMetrics.heightPixels/3)-(mBitmap.getHeight()/2));
        mMatrix.postTranslate(mCenterX,mCenterY);

        //put mMatrix in
        this.setImageMatrix(mMatrix);

        //set Touch to action Listener
        this.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                //detection of two points touch
                switch(event.getAction() & MotionEvent.ACTION_MASK)
                {
                    //first point touch
                    case MotionEvent.ACTION_DOWN :
                        mChangeMatrix.set(mMatrix);
                        mFirstPointF.set(event.getX(), event.getY());
                        mState = STATE_DRAG;
                        break;

                    //second point touch
                    case MotionEvent.ACTION_POINTER_DOWN :
                        mDistance = Spacing(event);
                        //if the distance is large than 10, consider it as two points touch action
                        if (Spacing(event) > 10f)
                        {
                            mChangeMatrix.set(mMatrix);
                            MidPoint(mSecondPointF, event);
                            mState = STATE_ZOOM;
                        }
                        break;

                    //release
                    case MotionEvent.ACTION_UP :
                        break;

                    //release
                    case MotionEvent.ACTION_POINTER_UP :
                        mState = STATE_NONE;
                        break;

                    //滑動過程進入
                    case MotionEvent.ACTION_MOVE :
                        if (mState == STATE_DRAG)
                        {
                            mMatrix.set(mChangeMatrix);
                            mMatrix.postTranslate(event.getX() - mFirstPointF.x, event.getY() - mFirstPointF.y);
                        }
                        else if (mState == STATE_ZOOM)
                        {
                            float NewDistance = Spacing(event);
                            if (NewDistance > 10f)
                            {
                                mMatrix.set(mChangeMatrix);
                                float NewScale = NewDistance / mDistance;
                                mMatrix.postScale(NewScale, NewScale, mSecondPointF.x, mSecondPointF.y);
                            }
                        }
                        break;
                }

                //get mMatrix scale new bitmap
                ScaleImage.this.setImageMatrix(mMatrix);

                Scale();

                return true;
            }
        });
    }
    //ON DRAW
    @Override
    protected void onDraw(Canvas canvas) {
        try {
            super.onDraw(canvas);
        } catch (Exception e) {
            System.out
                    .println("MyImageView  -> onDraw() Canvas: trying to use a recycled bitmap");
        }
    }

}
