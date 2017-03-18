package lunax.spider.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by Bamboo on 3/12/2017.
 */

public class TagCloudLayout extends ViewGroup {
    final Random mRandom = new Random();
    private float mRotateAngle;

    private Handler mHandler = new Handler();
    private float rotateAngleDegree;

    public TagCloudLayout(Context context) {
        super(context);
    }

    public TagCloudLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagCloudLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final float radius = Math.min(getMeasuredWidth(), getMeasuredHeight()) / 2F;
        float halfWidth = getMeasuredWidth() / 2F;
        float halfHeight = getMeasuredHeight() / 2F;
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            float sinTheta = (float) Math.sin(lp.theta);
            float x = (int) (radius * Math.cos(lp.fi + mRotateAngle)
                    * sinTheta);

            if (child instanceof TextView) {
                ((TextView) child)
                        .setTextSize(15 * ((radius - x) / radius) + 10);
            }
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //  http://en.wikipedia.org/wiki/Spherical_coordinates
            lp.x = (int) ((halfWidth + radius * Math.sin(lp.fi + mRotateAngle)
                    * sinTheta) - /* for balancing on x-axis */(child
                    .getMeasuredWidth() / 2F));
            lp.y = (int) (halfHeight + radius * Math.cos(lp.theta)-/* for balancing on y-axis */(child
                    .getMeasuredHeight() / 2F));
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                rotateAngleDegree += 5;
                mRotateAngle = (float) Math.toRadians(rotateAngleDegree);
                requestLayout();
                mHandler.postDelayed(this, 40);
            }
        }, 40);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void addView(View child, int index,
                        android.view.ViewGroup.LayoutParams params) {
        super.addView(child, index, params);

        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        lp.fi = (float) Math.toRadians(mRandom.nextInt(360));
        lp.theta = (float) Math.toRadians(mRandom.nextInt(360));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            child.layout(lp.x, lp.y, lp.x + child.getMeasuredWidth(), lp.y
                    + child.getMeasuredHeight());
        }
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p.width, p.height);
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {
        int x;
        int y;
        float fi, theta;

        public LayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public LayoutParams(int w, int h) {
            super(w, h);
        }
    }
}
