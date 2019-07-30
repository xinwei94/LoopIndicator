package com.xinwei.loopindicator.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.View;

import com.xinwei.loopindicator.R;

/**
 * 支持两张图片组合展示的ImageView
 * Created by xinwei2 on 2019/3/7
 */

public class VaryImageView extends View {

    private static final String TAG = "VaryImageView";

    private static final float MAX_POSITION = 1f;//最大显示位置

    private static final float MIN_POSITION = -1f;//最小显示位置

    private Bitmap mCenterBitmap;

    private Bitmap mEdgeBitmap;

    private int mCenterImageDrawLeft;//居中图片绘制的左边沿
    private int mCenterImageDrawRight;//居中图片绘制的右边沿
    private int mEdgeImageDrawLeft;//侧边图片绘制的左边沿
    private int mEdgeImageDrawRight;//侧边图片绘制的右边沿

    private int mCenterImageShowLeft;//居中图片显示的左边沿
    private int mCenterImageShowRight;//居中图片显示的右边沿
    private int mEdgeImageShowLeft;//侧边图片显示的左边沿
    private int mEdgeImageShowRight;//侧边图片显示的右边沿

    private float mCurrentPosition = 1f;

    public VaryImageView(Context context) {
        this(context, null);
    }

    public VaryImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VaryImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.VaryImageView);
        Drawable centerDrawable = typedArray.getDrawable(R.styleable.VaryImageView_centerSrc);
        Drawable edgeDrawable = typedArray.getDrawable(R.styleable.VaryImageView_edgeSrc);

        setBitmap(centerDrawable, edgeDrawable);
    }

    public void setPosition(float position) {
        if (position > MAX_POSITION) {
            position = MAX_POSITION;
        }

        if (position < MIN_POSITION) {
            position = MIN_POSITION;
        }

        mCurrentPosition = position;
        invalidate();
    }

    public void setImageRes(@DrawableRes int centerRes, @DrawableRes int edgeRes) {
        Drawable centerDrawable = getContext().getResources().getDrawable(centerRes);
        Drawable edgeDrawable = getContext().getResources().getDrawable(edgeRes);

        setBitmap(centerDrawable, edgeDrawable);
        invalidate();
    }

    private void setBitmap(Drawable centerDrawable, Drawable edgeDrawable) {
        if (null != centerDrawable) {
            BitmapDrawable centerBd = (BitmapDrawable) centerDrawable;
            mCenterBitmap = centerBd.getBitmap();
        }

        if (null != edgeDrawable) {
            BitmapDrawable edgeBd = (BitmapDrawable) edgeDrawable;
            mEdgeBitmap = edgeBd.getBitmap();
        }
    }

    private void update(float position) {
        int width = getWidth();
        int centerImageWidth = mCenterBitmap.getWidth();
        int edgeImageWidth = mEdgeBitmap.getWidth();

        if (mCurrentPosition > 0) {
            mCenterImageDrawLeft = 0;
            mCenterImageDrawRight = (int)(centerImageWidth * (1 - position));
            mCenterImageShowLeft = 0;
            mCenterImageShowRight = (int) (width * (1 - position));

            mEdgeImageDrawLeft = (int) (edgeImageWidth * (1 - position));
            mEdgeImageDrawRight = edgeImageWidth;
            mEdgeImageShowLeft = mCenterImageShowRight;
            mEdgeImageShowRight = width;

        } else {
            mCenterImageDrawLeft = (int) (centerImageWidth * (-position));
            mCenterImageDrawRight = centerImageWidth;
            mCenterImageShowLeft = (int) (width * (-position));
            mCenterImageShowRight = width;

            mEdgeImageDrawLeft = 0;
            mEdgeImageDrawRight = (int) (edgeImageWidth * (-position));
            mEdgeImageShowLeft = 0;
            mEdgeImageShowRight = mCenterImageShowLeft;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        update(mCurrentPosition);

        // 将画布坐标系移动到左上角
        canvas.translate(0, 0);
        // 居中图片绘制区域
        Rect centerSrc = new Rect(mCenterImageDrawLeft, 0, mCenterImageDrawRight, mCenterBitmap.getHeight());
        // 居中图片在屏幕上显示的区域
        Rect centerDst = new Rect(mCenterImageShowLeft, 0, mCenterImageShowRight, getHeight());
        // 绘制居中图片
        canvas.drawBitmap(mCenterBitmap, centerSrc, centerDst, null);

        // 侧边图片绘制区域
        Rect edgeSrc = new Rect(mEdgeImageDrawLeft, 0, mEdgeImageDrawRight, mEdgeBitmap.getHeight());
        // 侧边图片在屏幕上显示的区域
        Rect edgeDst = new Rect(mEdgeImageShowLeft, 0, mEdgeImageShowRight, getHeight());
        // 绘制侧边图片
        canvas.drawBitmap(mEdgeBitmap, edgeSrc, edgeDst, null);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (null != mCenterBitmap) {
            //mCenterBitmap.recycle();
        }

        if (null != mEdgeBitmap) {
            //mEdgeBitmap.recycle();
        }
    }
}
