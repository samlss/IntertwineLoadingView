package com.iigo.library;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com / 729717222@qq.com
 * @github https://github.com/samlss
 * @description A two balls intertwined loading view.
 */
public class IntertwineLoadingView extends View{
    private final static int DEFAULT_SIZE = 250; //200 px

    private final static int DEFAULT_DURATION = 500;
    private final static int DEFAULT_1ST_BALL_COLOR = Color.parseColor("#FF7C81");
    private final static int DEFAULT_2ND_BALL_COLOR = Color.parseColor("#A9F5C2");

    private int mFirstBallColor  = DEFAULT_1ST_BALL_COLOR;
    private int mSecondBallColor = DEFAULT_2ND_BALL_COLOR;

    private float mBallRadius;

    private Paint mFirstBallPaint;
    private Paint mSecondBallPaint;

    private Path mFirstBallPath;
    private Path mSecondBallPath;

    private Path mRotatePath;
    private PathMeasure mRotatePathMeasure;
    private Path mCalculatePath;
    private Matrix mCalculateMatrix;
    private float[] mCalculatePos = new float[2];

    private float mCenterX;
    private float mCenterY;

    private ValueAnimator mValueAnimator;
    private float mAnimatedValue;

    private int mDuration;

    //Whether the first ball is in front
    private boolean isFirstBallFront = true;
    private long mAnimatorPlayTime;

    public IntertwineLoadingView(Context context) {
        this(context, null);
    }

    public IntertwineLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IntertwineLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        parseAttr(attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public IntertwineLoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        parseAttr(attrs);
        init();
    }

    private void parseAttr(AttributeSet attrs) {
        if (attrs == null){
            return;
        }

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.IntertwineLoadingView);
        mFirstBallColor = typedArray.getColor(R.styleable.IntertwineLoadingView_firstBallColor, DEFAULT_1ST_BALL_COLOR);
        mSecondBallColor = typedArray.getColor(R.styleable.IntertwineLoadingView_secondBallColor, DEFAULT_2ND_BALL_COLOR);
        mDuration = typedArray.getInteger(R.styleable.IntertwineLoadingView_animDuration, DEFAULT_DURATION);
        typedArray.recycle();
    }

    private void init(){
        mFirstBallPaint  = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSecondBallPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mFirstBallPaint.setStyle(Paint.Style.FILL);
        mSecondBallPaint.setStyle(Paint.Style.FILL);

        mFirstBallPaint.setColor(mFirstBallColor);
        mSecondBallPaint.setColor(mSecondBallColor);

        mFirstBallPath  = new Path();
        mSecondBallPath = new Path();

        mCalculatePath = new Path();
        mRotatePath = new Path();
        mCalculateMatrix = new Matrix();
        mRotatePathMeasure = new PathMeasure();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        int w = widthSpecSize;
        int h = heightSpecSize;

        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            w = DEFAULT_SIZE;
            h = DEFAULT_SIZE;
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            w = DEFAULT_SIZE;
            h = heightSpecSize;
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            w = widthSpecSize;
            h = DEFAULT_SIZE;
        }

        setMeasuredDimension(w, h);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mFirstBallPath.reset();
        mSecondBallPath.reset();
        mRotatePath.reset();

        int minSize = Math.min(w, h);

        mCenterX = w / 2;
        mCenterY = h / 2;

        mBallRadius = minSize / 4f;

        float rotatePathCircleRadius = 1.1f * mBallRadius / 2;

        mRotatePath.addCircle(mCenterX, mCenterY - rotatePathCircleRadius, rotatePathCircleRadius, Path.Direction.CW);
        mRotatePathMeasure.setPath(mRotatePath, false);

        setupAnimator();
    }

    private void setupAnimator(){
        stop();

        mValueAnimator = ValueAnimator.ofFloat(0, 1);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                if (animatedValue < mAnimatedValue){
                    isFirstBallFront  = !isFirstBallFront;
                }

                mAnimatedValue = animatedValue;
                invalidate();
            }
        });

        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setDuration(mDuration);
        mValueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mCalculateMatrix.reset();
        mCalculatePath.reset();

        //the ball hiding distance
        float showDistance = (mRotatePathMeasure.getLength() * 3 / 4f + mRotatePathMeasure.getLength() / 2 * mAnimatedValue) % mRotatePathMeasure.getLength();
        //the ball showing distance
        float hideDistance = mRotatePathMeasure.getLength() / 4f + mRotatePathMeasure.getLength() / 2 * mAnimatedValue;

        float hideAnimatedValue = 1 - mAnimatedValue;
        float showAnimatedValue = mAnimatedValue;

        mRotatePathMeasure.getPosTan(isFirstBallFront ? hideDistance : showDistance,
                mCalculatePos, null);

        mFirstBallPath.reset();
        mFirstBallPath.addCircle(mCalculatePos[0], mCalculatePos[1], mBallRadius, Path.Direction.CW);

        mCalculateMatrix.postScale(isFirstBallFront ? hideAnimatedValue : showAnimatedValue,
                isFirstBallFront ? hideAnimatedValue : showAnimatedValue, mCalculatePos[0], mCalculatePos[1]);

        mFirstBallPath.transform(mCalculateMatrix, mCalculatePath);
        canvas.drawPath(mCalculatePath, mFirstBallPaint);

        mCalculateMatrix.reset();
        mCalculatePath.reset();

        mRotatePathMeasure.getPosTan(isFirstBallFront ? showDistance : hideDistance, mCalculatePos, null);

        mSecondBallPath.reset();
        mSecondBallPath.addCircle(mCalculatePos[0], mCalculatePos[1], mBallRadius, Path.Direction.CW);

        mCalculateMatrix.postScale(isFirstBallFront ? showAnimatedValue : hideAnimatedValue,
                isFirstBallFront ? showAnimatedValue : hideAnimatedValue, mCalculatePos[0], mCalculatePos[1]);

        mSecondBallPath.transform(mCalculateMatrix, mCalculatePath);
        canvas.drawPath(mCalculatePath, mSecondBallPaint);
    }

    /**
     * Set animation duration.
     * */
    public void setDuration(int duration) {
        this.mDuration = duration;
        setupAnimator();
    }

    /**
     * Get animation duration.
     * */
    public int getDuration() {
        return mDuration;
    }

    /**
     * Get the color of the first ball.
     * */
    public int getFirstBallColor() {
        return mFirstBallColor;
    }

    /**
     * Set the color of the first ball.
     * */
    public void setFirstBallColor(int firstBallColor) {
        this.mFirstBallColor = firstBallColor;
        mFirstBallPaint.setColor(mFirstBallColor);
        postInvalidate();
    }

    /**
     * Get the color of the second ball.
     * */
    public int getSecondBallColor() {
        return mSecondBallColor;
    }

    /**
     * Set the color of the second ball.
     * */
    public void setSecondBallColor(int secondBallColor) {
        this.mSecondBallColor = secondBallColor;
        mSecondBallPaint.setColor(mSecondBallColor);
        postInvalidate();
    }

    /**
     * Pause the animation.
     * */
    public void pause(){
        if (mValueAnimator != null && mValueAnimator.isRunning()){
            mAnimatorPlayTime = mValueAnimator.getCurrentPlayTime();
            mValueAnimator.cancel();
        }
    }

    /**
     * Resume the animation.
     * */
    public void resume(){
        if (mValueAnimator != null && !mValueAnimator.isRunning()){
            mValueAnimator.setCurrentPlayTime(mAnimatorPlayTime);
            mValueAnimator.start();
        }
    }

    /**
     * Start the animation.
     * */
    public void start(){
        mAnimatorPlayTime = 0;
        if (mValueAnimator != null){
            mValueAnimator.start();
        }
    }

    /**
     * Cancel the animation.
     * */
    public void stop(){
        if (mValueAnimator != null){
            mValueAnimator.cancel();
        }
    }

    /**
     * Release this view when you do not need it.
     * */
    public void release(){
        stop();
        if (mValueAnimator != null){
            mValueAnimator.removeAllUpdateListeners();
        }
    }
}
