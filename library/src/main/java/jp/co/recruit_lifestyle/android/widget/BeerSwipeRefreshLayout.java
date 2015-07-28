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

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;

/**
 * @author amyu_san
 */
public class BeerSwipeRefreshLayout extends ViewGroup implements SensorEventListener {

  private static final int INVALID_POINTER = -1;

  private static final int ROLL_LIMIT = 30;

  private float mOldRoll;

  private boolean mIsRefreshing = false;

  private SensorManager mSensorManager;

  private Sensor mRotationVectorSensor;

  private int mActivePointerId = INVALID_POINTER;

  private float mOldDiffY;

  private final static float MOVE_LIMIT = 500.f;

  private float mFirstTouchDownPointY;

  private View mTarget;

  private BeerView mBeerView;

  private OnRefreshListener mListener;

  public BeerSwipeRefreshLayout(Context context) {
    this(context, null, 0);
  }

  public BeerSwipeRefreshLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public BeerSwipeRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    setWillNotDraw(false);
    ViewCompat.setChildrenDrawingOrderEnabled(this, true);
    addView(mBeerView = new BeerView(context));
    mBeerView.setVisibility(GONE);
    mBeerView.setAlpha(0.f);

    mSensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
    mRotationVectorSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
  }

  @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
    if (getChildCount() == 0) {
      return;
    }

    ensureTarget();

    final int thisWidth = getMeasuredWidth();
    final int thisHeight = getMeasuredHeight();

    final int childRight = thisWidth - getPaddingRight();
    final int childBottom = thisHeight - getPaddingBottom();
    mTarget.layout(getPaddingLeft(), getPaddingTop(), childRight, childBottom);
    mBeerView.layout(l, t, r, b);
    mBeerView.bringToFront();
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    ensureTarget();

    mTarget.measure(
        makeMeasureSpecExactly(getMeasuredWidth() - (getPaddingLeft() + getPaddingRight())),
        makeMeasureSpecExactly(getMeasuredHeight() - (getPaddingTop() + getPaddingBottom())));
  }

  @Override protected void onAttachedToWindow() {
    mSensorManager.registerListener(this, mRotationVectorSensor, 30000);
    super.onAttachedToWindow();
  }

  @Override protected void onDetachedFromWindow() {
    mSensorManager.unregisterListener(this);
    super.onDetachedFromWindow();
  }

  @Override public boolean onInterceptTouchEvent(MotionEvent event) {
    ensureTarget();

    if (!isEnabled() || canChildScrollUp() || isRefreshing()) {
      return false;
    }
    if (mBeerView.isReverseAnimationRunning() || mBeerView.isMax()) {
      return false;
    }

    final int action = MotionEventCompat.getActionMasked(event);

    switch (action) {
      case MotionEvent.ACTION_DOWN:
        mActivePointerId = MotionEventCompat.getPointerId(event, 0);
        mFirstTouchDownPointY = getMotionEventY(event, mActivePointerId);
        if (isRefreshing()) {
          mOldDiffY = MOVE_LIMIT;
        } else {
          mOldDiffY = 0.f;
        }
        break;

      case MotionEvent.ACTION_MOVE:
        if (mActivePointerId == INVALID_POINTER) {
          return false;
        }

        final float currentY = getMotionEventY(event, mActivePointerId);
        if (currentY == -1.f) {
          return false;
        }

        if (mFirstTouchDownPointY == -1.f) {
          mFirstTouchDownPointY = currentY;
        }

        final float yDiff = currentY - mFirstTouchDownPointY;

        // State is changed to drag if over slop
        if (yDiff > ViewConfiguration.get(getContext())
            .getScaledTouchSlop() && !isRefreshing()) {
          return true;
        }

        break;

      case MotionEvent.ACTION_UP:
      case MotionEvent.ACTION_CANCEL:
        mActivePointerId = INVALID_POINTER;
        break;
    }
    return false;
  }

  @Override public boolean onTouchEvent(@NonNull MotionEvent event) {
    if (!isEnabled() || canChildScrollUp() /*|| isRefreshing()*/) {
      return false;
    }
    if (mBeerView.isReverseAnimationRunning() || mBeerView.isMax()) {
      return false;
    }

    final int action = MotionEventCompat.getActionMasked(event);
    switch (action) {
      case MotionEvent.ACTION_DOWN:
        // Here is not called from anywhere
        break;

      case MotionEvent.ACTION_MOVE:
        if (mBeerView.getVisibility() == GONE) {
          mBeerView.setVisibility(VISIBLE);
        }

        final int pointerIndex = MotionEventCompat.findPointerIndex(event, mActivePointerId);
        float currentY = getMotionEventY(event, pointerIndex);
        float diffY = mOldDiffY + currentY - mFirstTouchDownPointY;
        if (isRefreshing()) {
          diffY = Math.max(diffY, MOVE_LIMIT);
        }

        if (diffY < MOVE_LIMIT) {
          mBeerView.setAlpha(diffY / MOVE_LIMIT);
          mBeerView.drawBottle(0);
          mBeerView.drawGlass(0);
          return false;
        } else {
          setRefreshing(true, true);
        }
        float touchMoveDegree = Math.max(-(diffY - mFirstTouchDownPointY) / 3, -110);

        if (touchMoveDegree > 0) {
          return false;
        }
        mBeerView.drawBottle(touchMoveDegree);
        mBeerView.drawGlass(0);

        if (touchMoveDegree < -90) {
          mBeerView.startPour();
        } else {
          mBeerView.stopPour();
        }

        break;

      case MotionEvent.ACTION_UP:
        mBeerView.stopPour();

        currentY = getMotionEventY(event, mActivePointerId);
        diffY = mOldDiffY + currentY - mFirstTouchDownPointY;
        touchMoveDegree = Math.max(-(diffY - mFirstTouchDownPointY) / 3, -110);
        if (!isRefreshing()) {
          mBeerView.startDisappearAnimator();
        }
        if (touchMoveDegree > 0) {
          return false;
        }

        mBeerView.startReverseBottleAnimator(touchMoveDegree);
        break;

      case MotionEvent.ACTION_CANCEL:
        if (mActivePointerId == INVALID_POINTER) {
          return false;
        }
        mActivePointerId = INVALID_POINTER;
        return false;
    }
    return true;
  }

  @Override public void onSensorChanged(SensorEvent event) {
    if (event.sensor.getType() != Sensor.TYPE_ROTATION_VECTOR || !mBeerView.isMax()) {
      return;
    }

    float[] rotationMatrix = new float[9];
    SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);

    float[] adjustedRotationMatrix = new float[9];
    SensorManager.remapCoordinateSystem(rotationMatrix, SensorManager.AXIS_X, SensorManager.AXIS_Z,
        adjustedRotationMatrix);

    float[] orientation = new float[3];
    SensorManager.getOrientation(adjustedRotationMatrix, orientation);

    float roll = orientation[2] * -57;

    if (roll < ROLL_LIMIT && roll > -ROLL_LIMIT) {
      mBeerView.drawGlass(-roll);
      mBeerView.drawGlassFroth(-roll, 1);
      mOldRoll = -roll;
    } else {
      mBeerView.drawGlass(mOldRoll);
      mBeerView.drawGlassFroth(mOldRoll, 1);
    }
  }

  @Override public void onAccuracyChanged(Sensor sensor, int accuracy) {

  }

  public void setRefreshing(boolean refreshing) {
    if (refreshing && !isRefreshing()) {
      // scale and show
      mIsRefreshing = true;
      mBeerView.setVisibility(VISIBLE);
      mBeerView.setAlpha(1.f);
    } else {
      setRefreshing(refreshing, false /* notify */);
    }
  }

  private void setRefreshing(boolean refreshing, final boolean notify) {
    if (isRefreshing() != refreshing) {
      ensureTarget();
      mIsRefreshing = refreshing;
      if (isRefreshing()) {
        if (notify) {
          if (mListener != null) {
            mListener.onRefresh();
          }
        }
      } else {
        mBeerView.startDisappearAnimator();
      }
    }
  }

  private boolean isRefreshing() {
    return mIsRefreshing;
  }

  private float getMotionEventY(@NonNull MotionEvent ev, int activePointerId) {
    final int index = MotionEventCompat.findPointerIndex(ev, activePointerId);
    if (index < 0) {
      return -1;
    }
    return MotionEventCompat.getY(ev, index);
  }

  private void ensureTarget() {
    if (mTarget == null) {
      for (int i = 0; i < getChildCount(); i++) {
        View child = getChildAt(i);
        if (!child.equals(mBeerView)) {
          mTarget = child;
          break;
        }
      }
    }

    if (mTarget == null) {
      throw new IllegalStateException("This view must have at least one AbsListView");
    }
  }

  public boolean canChildScrollUp() {
    if (mTarget == null) {
      return false;
    }

    if (android.os.Build.VERSION.SDK_INT < 14) {
      if (mTarget instanceof AbsListView) {
        final AbsListView absListView = (AbsListView) mTarget;
        return absListView.getChildCount() > 0 && (absListView.getFirstVisiblePosition() > 0
            || absListView.getChildAt(0).getTop() < absListView.getPaddingTop());
      } else {
        return mTarget.getScrollY() > 0;
      }
    } else {
      return ViewCompat.canScrollVertically(mTarget, -1);
    }
  }

  public void setOnRefreshListener(OnRefreshListener listener) {
    mListener = listener;
  }

  private static int makeMeasureSpecExactly(int length) {
    return MeasureSpec.makeMeasureSpec(length, MeasureSpec.EXACTLY);
  }

  public interface OnRefreshListener {
    void onRefresh();
  }
}
