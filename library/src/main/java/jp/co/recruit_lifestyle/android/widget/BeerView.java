/*
 * Copyright (C) 2015 RECRUIT LIFESTYLE CO., LTD.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.co.recruit_lifestyle.android.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import java.util.Random;

/**
 * @author amyu_san
 */
public class BeerView extends View {

  private final static int BOTTLE_COLOR = 0xff623B30;
  private final static int BEER_COLOR = 0xffEEBC54;
  private final static int GLASS_COLOR = 0xffffffff;
  private final static int BOTTLE_LABEL_INNER_CIRCLE_COLOR = 0xffDDCF87;
  private final static int BOTTLE_LABEL_OUTER_CIRCLE_COLOR = 0xffC49E34;
  private final static int BOTTLE_LABEL_RIBBON_COLOR = 0xff621819;
  private final static int BOTTLE_EMPTY_COLOR = 0xff703126;
  private final static int BOTTLE_BACKGROUND_COLOR = 0xffD6737B;
  private final static int GLASS_BACKGROUND_COLOR = 0xffA8D4DC;
  private final static float BOTTLE_POUR_HEIGHT_DX = 1.7f;
  private final static float GLASS_POUR_HEIGHT_DX = 3.43f;
  private final static int FROTH_NUM = 5;

  private Paint mBottlePaint;
  private Paint mBeerPaint;
  private Paint mBottleLabelOuterCirclePaint;
  private Paint mBottleLabelInnerCirclePaint;
  private Paint mBottleLabelRibbonPaint;
  private Paint mBottleBackgroundPaint;
  private Paint mBottleEmptyPaint;
  private Paint mGlassPaint;
  private Paint mGlassBackgroundPaint;
  private BottlePath mBottlePath;
  private Path mBottlePourPath;
  private GlassPath mGlassPath;
  private Path mGlassPourPath;
  private Path mFrothPath;
  private Handler mPourHandler;
  private float mBottlePourHeight;
  private float mGlassPourHeight;
  private int mViewWidth;
  private float mObjectWidth;
  private int mGlassHeight;
  private boolean mIsMax = false;
  private boolean mIsReverseAnimationRunning = false;
  private boolean mIsStartedPour = false;
  private float mBeerFrothRadius = 0.f;
  private float mCurrentDegree;
  private float mBottleCenterX;
  private float mBottleCenterY;
  private float mGlassCenterX;
  private float mGlassCenterY;
  private float mCircleWidth;
  private Region mGlassBeerRegion;
  private Region mClipRegion = new Region();
  private RectF mRectF = new RectF();
  private Random mRandom;
  private float[][] mFrothPoints;
  private long mLastInvalidate = SystemClock.uptimeMillis();
  private ValueAnimator mAlphaAnimator;
  private ValueAnimator[] mFrothAnimators = new ValueAnimator[FROTH_NUM];
  private Point mBottlePoints[] = new Point[Bottle.BOTTLE_POINTS.length * 2];
  private Point mBottleBeerPoints[] = new Point[Bottle.BEER_POINTS.length * 2];
  private Point mGlassPoints[] = new Point[Glass.GLASS_POINTS.length * 2];
  private Point mGlassBeerPoints[] = new Point[Glass.GLASS_BEER_POINTS.length * 2];
  private Point mGlassFrothPoints[] = new Point[Glass.GLASS_BEER_FROTH_POINTS.length];
  private Point mBottleLabelOuterCirclePoints[] =
      new Point[Bottle.LABEL_OUTER_CIRCLE_POINTS.length * 2];
  private Point mBottleLabelInnerCirclePoints[] =
      new Point[Bottle.LABEL_INNER_CIRCLE_POINTS.length * 2];
  private Point mBottleLabelRibbonPoints[] = new Point[Bottle.LABEL_RIBBON_POINTS.length * 2];
  private float mBottlePourHeightLimit;

  private Runnable mPourRunnable = new Runnable() {
    @Override public void run() {

      if (mBottlePourHeightLimit < mBottlePourHeight) {
        mIsMax = true;
        startReverseBottleAnimator(mCurrentDegree);
        stopPour();
        return;
      }

      float width = mViewWidth / 2.f + mObjectWidth / 2.f + mObjectWidth;

      mIsStartedPour = true;
      mBottlePourHeight += BOTTLE_POUR_HEIGHT_DX;
      mBottlePourPath.reset();
      mBottlePourPath.addRect(0, 0, width, mBottlePourHeight, Path.Direction.CCW);

      mGlassPourHeight -= GLASS_POUR_HEIGHT_DX;
      mGlassPourPath.reset();
      mGlassPourPath.addRect(0, 0, width, mGlassPourHeight, Path.Direction.CCW);
      drawGlass(0);

      if (mBottlePourHeight > mBottlePourHeightLimit - mBottlePourHeightLimit * 0.05f) {
        mBeerFrothRadius += 0.15;
        drawGlassFroth(0, mBeerFrothRadius);
      }
      mPourHandler.postDelayed(this, 30);
    }
  };

  public BeerView(Context context) {
    super(context);
    init();
  }

  private void init() {
    //setLayerType(LAYER_TYPE_HARDWARE, null);
    mPourHandler = new Handler();
    mRandom = new Random();
    mFrothPoints = new float[FROTH_NUM][2];
    mGlassBeerRegion = new Region();

    setUpPaint();
    setUpPath();
  }

  private void setUpPaint() {
    mBottlePaint = new Paint();
    mBottlePaint.setColor(BOTTLE_COLOR);
    mBottlePaint.setStyle(Paint.Style.FILL);
    mBottlePaint.setAntiAlias(true);

    mBeerPaint = new Paint();
    mBeerPaint.setColor(BEER_COLOR);
    mBeerPaint.setStyle(Paint.Style.FILL);
    mBeerPaint.setAntiAlias(true);

    mGlassPaint = new Paint();
    mGlassPaint.setColor(GLASS_COLOR);
    mGlassPaint.setStyle(Paint.Style.FILL);
    mGlassPaint.setAntiAlias(true);

    mBottleLabelInnerCirclePaint = new Paint();
    mBottleLabelInnerCirclePaint.setColor(BOTTLE_LABEL_INNER_CIRCLE_COLOR);
    mBottleLabelInnerCirclePaint.setStyle(Paint.Style.FILL);
    mBottleLabelInnerCirclePaint.setAntiAlias(true);

    mBottleLabelOuterCirclePaint = new Paint();
    mBottleLabelOuterCirclePaint.setColor(BOTTLE_LABEL_OUTER_CIRCLE_COLOR);
    mBottleLabelOuterCirclePaint.setStyle(Paint.Style.FILL);
    mBottleLabelOuterCirclePaint.setAntiAlias(true);

    mBottleLabelRibbonPaint = new Paint();
    mBottleLabelRibbonPaint.setColor(BOTTLE_LABEL_RIBBON_COLOR);
    mBottleLabelRibbonPaint.setStyle(Paint.Style.FILL);
    mBottleLabelRibbonPaint.setAntiAlias(true);

    mBottleEmptyPaint = new Paint();
    mBottleEmptyPaint.setColor(BOTTLE_EMPTY_COLOR);
    mBottleEmptyPaint.setStyle(Paint.Style.FILL);
    mBottleEmptyPaint.setAntiAlias(true);

    mBottleBackgroundPaint = new Paint();
    mBottleBackgroundPaint.setColor(BOTTLE_BACKGROUND_COLOR);
    mBottleBackgroundPaint.setStyle(Paint.Style.FILL);
    mBottleBackgroundPaint.setAntiAlias(true);

    mGlassBackgroundPaint = new Paint();
    mGlassBackgroundPaint.setColor(GLASS_BACKGROUND_COLOR);
    mGlassBackgroundPaint.setStyle(Paint.Style.FILL);
    mGlassBackgroundPaint.setAntiAlias(true);
  }

  private void setUpPath() {
    mBottlePath = new BottlePath();
    mGlassPath = new GlassPath();

    mBottlePourPath = new Path();
    mGlassPourPath = new Path();
    mFrothPath = new Path();
  }

  @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    mViewWidth = w;

    mCircleWidth = w / 3;
    mObjectWidth = mCircleWidth - mViewWidth / 15.f;
    mGlassHeight = h / 2;
    mBottlePourHeight = 0.3f * mCircleWidth;
    mGlassPourHeight = 0.865f * mCircleWidth + mGlassHeight;
    mBottleCenterX = mViewWidth / 2.f;
    mBottleCenterY = mCircleWidth / 2.f;
    mGlassCenterX = mViewWidth / 2.f;
    mGlassCenterY = mGlassHeight + mCircleWidth / 2.f;

    mBottlePourPath.reset();
    mBottlePourPath.addRect(0, 0, w, mBottlePourHeight, Path.Direction.CCW);

    mGlassPourPath.reset();
    mGlassPourPath.addRect(0, 0, w, mGlassPourHeight, Path.Direction.CCW);

    initPoint();

    mBottlePourHeightLimit = mBottleBeerPoints[6].getRotateY(-90);
    super.onSizeChanged(w, h, oldw, oldh);
  }

  private void initPoint() {
    int bottleIndex = Bottle.BOTTLE_POINTS.length;
    for (int index = 0; index < bottleIndex; index++) {
      mBottlePoints[index] = new Point(mObjectWidth * Bottle.BOTTLE_POINTS[index][0],
          mObjectWidth * Bottle.BOTTLE_POINTS[index][1] + mCircleWidth * 0.5f - mObjectWidth * 0.5f,
          Point.BOTTLE);
    }
    for (int index = bottleIndex; index < bottleIndex * 2; index++) {
      mBottlePoints[index] =
          new Point(mObjectWidth * (1 - Bottle.BOTTLE_POINTS[index - bottleIndex][0]),
              mObjectWidth * Bottle.BOTTLE_POINTS[index - bottleIndex][1] + mCircleWidth * 0.5f
                  - mObjectWidth * 0.5f, Point.BOTTLE);
    }

    int circleIndex = Bottle.LABEL_OUTER_CIRCLE_POINTS.length;
    for (int index = 0; index < circleIndex; index++) {
      mBottleLabelOuterCirclePoints[index] =
          new Point(mObjectWidth * Bottle.LABEL_OUTER_CIRCLE_POINTS[index][0],
              mObjectWidth * Bottle.LABEL_OUTER_CIRCLE_POINTS[index][1] + mCircleWidth * 0.5f
                  - mObjectWidth * 0.5f, Point.BOTTLE);
    }
    for (int index = circleIndex; index < circleIndex * 2; index++) {
      mBottleLabelOuterCirclePoints[index] =
          new Point(mObjectWidth * (1 - Bottle.LABEL_OUTER_CIRCLE_POINTS[index - circleIndex][0]),
              mObjectWidth * Bottle.LABEL_OUTER_CIRCLE_POINTS[index - circleIndex][1]
                  + mCircleWidth * 0.5f - mObjectWidth * 0.5f, Point.BOTTLE);
    }

    circleIndex = Bottle.LABEL_INNER_CIRCLE_POINTS.length;
    for (int index = 0; index < Bottle.LABEL_INNER_CIRCLE_POINTS.length; index++) {
      mBottleLabelInnerCirclePoints[index] =
          new Point(mObjectWidth * Bottle.LABEL_INNER_CIRCLE_POINTS[index][0],
              mObjectWidth * Bottle.LABEL_INNER_CIRCLE_POINTS[index][1] + mCircleWidth * 0.5f
                  - mObjectWidth * 0.5f, Point.BOTTLE);
    }
    for (int index = circleIndex; index < circleIndex * 2; index++) {
      mBottleLabelInnerCirclePoints[index] =
          new Point(mObjectWidth * (1 - Bottle.LABEL_INNER_CIRCLE_POINTS[index - circleIndex][0]),
              mObjectWidth * Bottle.LABEL_INNER_CIRCLE_POINTS[index - circleIndex][1]
                  + mCircleWidth * 0.5f - mObjectWidth * 0.5f, Point.BOTTLE);
    }

    int ribbonIndex = Bottle.LABEL_RIBBON_POINTS.length;
    for (int index = 0; index < ribbonIndex; index++) {
      mBottleLabelRibbonPoints[index] =
          new Point(mObjectWidth * Bottle.LABEL_RIBBON_POINTS[index][0],
              mObjectWidth * Bottle.LABEL_RIBBON_POINTS[index][1] + mCircleWidth * 0.5f
                  - mObjectWidth * 0.5f, Point.BOTTLE);
    }
    for (int index = ribbonIndex; index < ribbonIndex * 2; index++) {
      mBottleLabelRibbonPoints[index] =
          new Point(mObjectWidth * (1 - Bottle.LABEL_RIBBON_POINTS[index - ribbonIndex][0]),
              mObjectWidth * Bottle.LABEL_RIBBON_POINTS[index - ribbonIndex][1]
                  + mCircleWidth * 0.5f - mObjectWidth * 0.5f, Point.BOTTLE);
    }

    int bottleBeerIndex = Bottle.BEER_POINTS.length;
    for (int index = 0; index < bottleBeerIndex; index++) {
      mBottleBeerPoints[index] = new Point(mObjectWidth * Bottle.BEER_POINTS[index][0],
          mObjectWidth * Bottle.BEER_POINTS[index][1] + mCircleWidth * 0.5f - mObjectWidth * 0.5f,
          Point.BOTTLE);
    }
    for (int index = bottleBeerIndex; index < bottleBeerIndex * 2; index++) {
      mBottleBeerPoints[index] =
          new Point(mObjectWidth * (1 - Bottle.BEER_POINTS[index - bottleBeerIndex][0]),
              mObjectWidth * Bottle.BEER_POINTS[index - bottleBeerIndex][1] + mCircleWidth * 0.5f
                  - mObjectWidth * 0.5f, Point.BOTTLE);
    }

    int glassIndex = Glass.GLASS_POINTS.length;
    for (int index = 0; index < glassIndex; index++) {
      mGlassPoints[index] = new Point(mObjectWidth * Glass.GLASS_POINTS[index][0],
          mGlassHeight + mObjectWidth * Glass.GLASS_POINTS[index][1] + mCircleWidth * 0.5f
              - mObjectWidth * 0.5f, Point.GLASS);
    }
    for (int index = glassIndex; index < glassIndex * 2; index++) {
      mGlassPoints[index] =
          new Point(mObjectWidth * (1 - Glass.GLASS_POINTS[index - glassIndex][0]),
              mGlassHeight + mObjectWidth * Glass.GLASS_POINTS[index - glassIndex][1]
                  + mCircleWidth * 0.5f - mObjectWidth * 0.5f, Point.GLASS);
    }

    int glassBeerIndex = Glass.GLASS_BEER_POINTS.length;
    for (int index = 0; index < glassBeerIndex; index++) {
      mGlassBeerPoints[index] = new Point(mObjectWidth * Glass.GLASS_BEER_POINTS[index][0],
          mGlassHeight + mObjectWidth * Glass.GLASS_BEER_POINTS[index][1] + mCircleWidth * 0.5f
              - mObjectWidth * 0.5f, Point.GLASS);
    }
    for (int index = glassIndex; index < glassBeerIndex * 2; index++) {
      mGlassBeerPoints[index] =
          new Point(mObjectWidth * (1 - Glass.GLASS_BEER_POINTS[index - glassBeerIndex][0]),
              mGlassHeight + mObjectWidth * Glass.GLASS_BEER_POINTS[index - glassBeerIndex][1]
                  + mCircleWidth * 0.5f - mObjectWidth * 0.5f, Point.GLASS);
    }

    for (int index = 0; index < Glass.GLASS_BEER_FROTH_POINTS.length; index++) {
      mGlassFrothPoints[index] = new Point(mObjectWidth * Glass.GLASS_BEER_FROTH_POINTS[index][0],
          mGlassHeight + mObjectWidth * Glass.GLASS_BEER_FROTH_POINTS[index][1]
              + mCircleWidth * 0.5f - mObjectWidth * 0.5f, Glass.GLASS_BEER_FROTH_POINTS[index][2],
          Point.GLASS);
    }

    drawGlass(0);
    drawBottle(0);
  }

  public boolean isMax() {
    return mIsMax;
  }

  public boolean isReverseAnimationRunning() {
    return mIsReverseAnimationRunning;
  }

  @Override protected void onDraw(Canvas canvas) {
    mBottlePath.getBeerPath().op(mBottlePourPath, Path.Op.DIFFERENCE);
    mFrothPath.reset();
    int radius = 8;
    for (int i = 0; i < FROTH_NUM; i++) {
      ValueAnimator animator = mFrothAnimators[i];
      if (animator == null) {
        continue;
      }
      float value = (float) animator.getAnimatedValue();
      float centerX = mFrothPoints[i][0];
      float centerY = mFrothPoints[i][1] * (1.f - value);
      if (mGlassBeerRegion.contains((int) centerX + radius, (int) centerY)
          && mGlassBeerRegion.contains((int) centerX - radius, (int) centerY)) {
        mFrothPath.addCircle(centerX, centerY, radius * ((value + 0.1f) * 5.f), Path.Direction.CCW);
      }
    }
    mGlassPath.getBeerPath().op(mGlassPourPath, Path.Op.DIFFERENCE);
    canvas.drawCircle(mBottleCenterX, mBottleCenterY, mCircleWidth / 2, mBottleBackgroundPaint);
    canvas.drawCircle(mGlassCenterX, mGlassCenterY, mCircleWidth / 2, mGlassBackgroundPaint);
    canvas.drawPath(mGlassPath.getGlassPath(), mGlassPaint);
    canvas.drawPath(mGlassPath.getBeerPath(), mBeerPaint);
    canvas.drawPath(mBottlePath.getBottlePath(), mBottlePaint);
    canvas.drawPath(mBottlePath.getEmptyPath(), mBottleEmptyPaint);
    canvas.drawPath(mBottlePath.getBeerPath(), mBeerPaint);
    canvas.drawPath(mBottlePath.getLabelOuterCirclePath(), mBottleLabelOuterCirclePaint);
    canvas.drawPath(mBottlePath.getLabelInnerCirclePath(), mBottleLabelInnerCirclePaint);
    canvas.drawPath(mBottlePath.getLabelRibbonPath(), mBottleLabelRibbonPaint);
    canvas.drawPath(mGlassPath.getBeerFrothPath(), mGlassPaint);
    canvas.drawPath(mFrothPath, mGlassPaint);
  }

  @Override protected void onDetachedFromWindow() {
    if (mAlphaAnimator != null) {
      mAlphaAnimator.end();
      mAlphaAnimator.removeAllUpdateListeners();
    }
    for (ValueAnimator animator : mFrothAnimators) {
      if (animator != null) {
        animator.end();
        animator.removeAllUpdateListeners();
      }
    }
    super.onDetachedFromWindow();
  }

  public void startPour() {
    if (mIsStartedPour) {
      return;
    }
    startFrothAnimation();
    mPourHandler.post(mPourRunnable);
  }

  public void stopPour() {
    mIsStartedPour = false;
    mPourHandler.removeCallbacks(mPourRunnable);
    if (mBottlePourHeightLimit < mBottlePourHeight) {
      mBottlePourPath.reset();
      mBottlePourPath.addRect(0, 0, mViewWidth, 1000, Path.Direction.CCW);
      invalidate();
    }
  }

  public void startReverseBottleAnimator(float degree) {
    ValueAnimator returnAnimator = ValueAnimator.ofFloat(degree, 0.f);
    returnAnimator.setDuration(500);
    returnAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        float value = (float) animation.getAnimatedValue();
        drawBottle(value);
      }
    });
    returnAnimator.addListener(new Animator.AnimatorListener() {
      @Override public void onAnimationStart(Animator animation) {
        mIsReverseAnimationRunning = true;
      }

      @Override public void onAnimationEnd(Animator animation) {
        mIsReverseAnimationRunning = false;
      }

      @Override public void onAnimationCancel(Animator animation) {

      }

      @Override public void onAnimationRepeat(Animator animation) {

      }
    });
    returnAnimator.start();
  }

  public void startDisappearAnimator() {
    mAlphaAnimator = ValueAnimator.ofFloat(getAlpha(), 0.f);
    mAlphaAnimator.setDuration(500);
    mAlphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        setAlpha((float) animation.getAnimatedValue());
      }
    });
    mAlphaAnimator.addListener(new Animator.AnimatorListener() {
      @Override public void onAnimationStart(Animator animation) {

      }

      @Override public void onAnimationEnd(Animator animation) {
        stopFrothAnimation();
        stopPour();
        setVisibility(GONE);

        setUpPath();
        mBottlePourHeight = 0.3f * mCircleWidth;
        mGlassPourHeight = 0.865f * mCircleWidth + mGlassHeight;
        mBeerFrothRadius = 0.f;

        mBottlePourPath.reset();
        mBottlePourPath.addRect(0, 0, getWidth(), mBottlePourHeight, Path.Direction.CCW);

        mGlassPourPath.reset();
        mGlassPourPath.addRect(0, 0, getWidth(), mGlassPourHeight, Path.Direction.CCW);

        mIsMax = false;
      }

      @Override public void onAnimationCancel(Animator animation) {

      }

      @Override public void onAnimationRepeat(Animator animation) {

      }
    });
    mAlphaAnimator.start();
  }


  public void startFrothAnimation() {
    if (mFrothAnimators[0] != null && mFrothAnimators[0].isRunning()) {
      return;
    }
    for (int i = 0; i < FROTH_NUM; i++) {
      ValueAnimator animator = ValueAnimator.ofFloat(0.f, 0.15f);
      animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        @Override public void onAnimationUpdate(ValueAnimator valueAnimator) {
          if (!mIsMax) {
            drawGlass(0);
          }
        }
      });
      animator.setRepeatCount(ValueAnimator.INFINITE);
      animator.setRepeatMode(ValueAnimator.RESTART);
      final int finalI = i;
      animator.addListener(new Animator.AnimatorListener() {
        @Override public void onAnimationStart(Animator animation) {

        }

        @Override public void onAnimationEnd(Animator animation) {

        }

        @Override public void onAnimationCancel(Animator animation) {

        }

        @Override public void onAnimationRepeat(Animator animation) {
          mFrothPoints[finalI][0] =
              (mRandom.nextFloat() * 0.2264f + 0.3868f) * mObjectWidth + mViewWidth / 2
                  - mObjectWidth / 2;
          mFrothPoints[finalI][1] =
              (mRandom.nextFloat() * 0.365f + 0.5f) * mObjectWidth + mGlassHeight;
        }
      });
      animator.setDuration(2000);
      animator.setStartDelay(i * 2000 / FROTH_NUM);
      animator.start();
      mFrothAnimators[i] = animator;
    }
  }

  private void stopFrothAnimation() {
    for (ValueAnimator animator : mFrothAnimators) {
      if (animator != null) {
        animator.end();
      }
    }
    mFrothAnimators = new ValueAnimator[FROTH_NUM];
  }

  @Override public void invalidate() {
    if (SystemClock.uptimeMillis() - mLastInvalidate < 30) {
      return;
    }
    mLastInvalidate = SystemClock.uptimeMillis();
    super.invalidate();
  }

  public void drawBottle(float degree) {
    mCurrentDegree = degree;
    mBottlePath.drawBottle(mBottlePoints, degree);
    mBottlePath.drawLabelOuterCircle(mBottleLabelOuterCirclePoints, degree);
    mBottlePath.drawLabelOuterCircle(mBottleLabelOuterCirclePoints, degree);
    mBottlePath.drawLabelInnerCircle(mBottleLabelInnerCirclePoints, degree);
    mBottlePath.drawLabelRibbon(mBottleLabelRibbonPoints, degree);
    mBottlePath.drawBeer(mBottleBeerPoints, degree);
    invalidate();
  }

  public void drawGlass(float degree) {
    mGlassPath.drawGlass(mGlassPoints, degree);
    mGlassPath.drawBeer(mGlassBeerPoints, degree);
    mRectF.setEmpty();
    mGlassPath.getBeerPath().computeBounds(mRectF, true);
    mGlassBeerRegion.setEmpty();
    mClipRegion.setEmpty();
    mClipRegion.set((int) mRectF.left, (int) mRectF.top, (int) mRectF.right, (int) mRectF.bottom);
    mGlassBeerRegion.setPath(mGlassPath.getBeerPath(), mClipRegion);
    invalidate();
  }

  public void drawGlassFroth(float degree, float radiusPercent) {
    mGlassPath.drawGlassFroth(degree, mGlassFrothPoints, Math.min(radiusPercent, 1));
    invalidate();
  }

  public class Point {

    public final static int BOTTLE = 0;
    public final static int GLASS = 1;
    private float x;
    private float y;
    private float radius;
    private float centerX = mObjectWidth * 0.5f;
    private float centerY;

    public Point(float x, float y, float radius, int mode) {
      this(x, y, mode);
      this.radius = radius;
    }

    public Point(float x, float y, int mode) {
      switch (mode) {
        case BOTTLE:
          centerY = mBottleCenterY;
          break;
        case GLASS:
          centerY = mGlassCenterY;
          break;
      }
      this.x = x;
      this.y = y;
    }

    public float getRotateX(float degree) {
      return (float) (mBottleCenterX + (x - centerX) * Math.cos(Math.toRadians(degree))
          - (y - centerY) * Math.sin(Math.toRadians(degree)));
    }

    public float getRotateY(float degree) {
      return (float) (centerY + (x - centerX) * Math.sin(Math.toRadians(degree))
          + (y - centerY) * Math.cos(Math.toRadians(degree)));
    }

    public float getRadius() {
      return radius * mObjectWidth;
    }
  }
}
