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

import android.graphics.Path;
import jp.co.recruit_lifestyle.android.widget.BeerView.Point;

/**
 * @author amyu_san
 */
public class BottlePath {

  private Path mBottlePath;

  private Path mLabelOuterCirclePath;

  private Path mLabelInnerCirclePath;

  private Path mLabelRibbonPath;

  private Path mBeerPath;

  private Path mEmptyPath;

  private float mCircleWidth;

  private float mObjectWidth;

  private Point mBottlePoints[] = new Point[Bottle.BOTTLE_POINTS.length * 2];

  private Point mBottleBeerPoints[] = new Point[Bottle.BEER_POINTS.length * 2];

  private Point mBottleLabelOuterCirclePoints[] =
      new Point[Bottle.LABEL_OUTER_CIRCLE_POINTS.length * 2];

  private Point mBottleLabelInnerCirclePoints[] =
      new Point[Bottle.LABEL_INNER_CIRCLE_POINTS.length * 2];

  private Point mBottleLabelRibbonPoints[] = new Point[Bottle.LABEL_RIBBON_POINTS.length * 2];

  public BottlePath() {
    mBottlePath = new Path();
    mBeerPath = new Path();
    mLabelOuterCirclePath = new Path();
    mLabelInnerCirclePath = new Path();
    mLabelRibbonPath = new Path();
    mEmptyPath = new Path();
  }

  public Path getBottlePath() {
    return mBottlePath;
  }

  public Path getLabelOuterCirclePath() {
    return mLabelOuterCirclePath;
  }

  public Path getLabelInnerCirclePath() {
    return mLabelInnerCirclePath;
  }

  public Path getLabelRibbonPath() {
    return mLabelRibbonPath;
  }

  public Path getBeerPath() {
    return mBeerPath;
  }

  public Path getEmptyPath() {
    return mEmptyPath;
  }

  public void drawBottle(BeerView.Point[] points, float degree) {
    mBottlePath.reset();
    //left
    mBottlePath.moveTo(points[0].getRotateX(degree), points[0].getRotateY(degree));
    mBottlePath.lineTo(points[1].getRotateX(degree), points[1].getRotateY(degree));
    mBottlePath.lineTo(points[2].getRotateX(degree), points[2].getRotateY(degree));
    mBottlePath.cubicTo(points[3].getRotateX(degree), points[3].getRotateY(degree),

        points[4].getRotateX(degree), points[4].getRotateY(degree),

        points[5].getRotateX(degree), points[5].getRotateY(degree));
    mBottlePath.cubicTo(points[6].getRotateX(degree), points[6].getRotateY(degree),

        points[7].getRotateX(degree), points[7].getRotateY(degree),

        points[8].getRotateX(degree), points[8].getRotateY(degree));
    mBottlePath.cubicTo(points[9].getRotateX(degree), points[9].getRotateY(degree),

        points[10].getRotateX(degree), points[10].getRotateY(degree),

        points[11].getRotateX(degree), points[11].getRotateY(degree));

    //right
    mBottlePath.moveTo(points[12].getRotateX(degree), points[12].getRotateY(degree));
    mBottlePath.lineTo(points[13].getRotateX(degree), points[13].getRotateY(degree));
    mBottlePath.lineTo(points[14].getRotateX(degree), points[14].getRotateY(degree));
    mBottlePath.cubicTo(points[15].getRotateX(degree), points[15].getRotateY(degree),

        points[16].getRotateX(degree), points[16].getRotateY(degree),

        points[17].getRotateX(degree), points[17].getRotateY(degree));
    mBottlePath.cubicTo(points[18].getRotateX(degree), points[18].getRotateY(degree),

        points[19].getRotateX(degree), points[19].getRotateY(degree),

        points[20].getRotateX(degree), points[20].getRotateY(degree));
    mBottlePath.cubicTo(points[21].getRotateX(degree), points[21].getRotateY(degree),

        points[22].getRotateX(degree), points[22].getRotateY(degree),

        points[23].getRotateX(degree), points[23].getRotateY(degree));
  }

  public void drawLabelOuterCircle(BeerView.Point[] points, float degree) {
    mLabelOuterCirclePath.reset();
    mLabelOuterCirclePath.moveTo(points[0].getRotateX(degree), points[0].getRotateY(degree));

    mLabelOuterCirclePath.cubicTo(points[1].getRotateX(degree), points[1].getRotateY(degree),

        points[2].getRotateX(degree), points[2].getRotateY(degree),

        points[3].getRotateX(degree), points[3].getRotateY(degree));

    mLabelOuterCirclePath.cubicTo(points[4].getRotateX(degree), points[4].getRotateY(degree),

        points[5].getRotateX(degree), points[5].getRotateY(degree),

        points[6].getRotateX(degree), points[6].getRotateY(degree));

    mLabelOuterCirclePath.moveTo(points[7].getRotateX(degree), points[7].getRotateY(degree));

    mLabelOuterCirclePath.cubicTo(points[8].getRotateX(degree), points[8].getRotateY(degree),

        points[9].getRotateX(degree), points[9].getRotateY(degree),

        points[10].getRotateX(degree), points[10].getRotateY(degree));

    mLabelOuterCirclePath.cubicTo(points[11].getRotateX(degree), points[11].getRotateY(degree),

        points[12].getRotateX(degree), points[12].getRotateY(degree),

        points[13].getRotateX(degree), points[13].getRotateY(degree));
  }

  public void drawLabelInnerCircle(BeerView.Point[] points, float degree) {
    mLabelInnerCirclePath.reset();
    mLabelInnerCirclePath.moveTo(points[0].getRotateX(degree), points[0].getRotateY(degree));

    mLabelInnerCirclePath.cubicTo(points[1].getRotateX(degree), points[1].getRotateY(degree),

        points[2].getRotateX(degree), points[2].getRotateY(degree),

        points[3].getRotateX(degree), points[3].getRotateY(degree));

    mLabelInnerCirclePath.cubicTo(points[4].getRotateX(degree), points[4].getRotateY(degree),

        points[5].getRotateX(degree), points[5].getRotateY(degree),

        points[6].getRotateX(degree), points[6].getRotateY(degree));

    mLabelInnerCirclePath.moveTo(points[7].getRotateX(degree), points[7].getRotateY(degree));

    mLabelInnerCirclePath.cubicTo(points[8].getRotateX(degree), points[8].getRotateY(degree),

        points[9].getRotateX(degree), points[9].getRotateY(degree),

        points[10].getRotateX(degree), points[10].getRotateY(degree));

    mLabelInnerCirclePath.cubicTo(points[11].getRotateX(degree), points[11].getRotateY(degree),

        points[12].getRotateX(degree), points[12].getRotateY(degree),

        points[13].getRotateX(degree), points[13].getRotateY(degree));
  }

  public void drawLabelRibbon(BeerView.Point[] points, float degree) {
    mLabelRibbonPath.reset();
    mLabelRibbonPath.moveTo(points[0].getRotateX(degree), points[0].getRotateY(degree));

    mLabelRibbonPath.cubicTo(points[1].getRotateX(degree), points[1].getRotateY(degree),

        points[2].getRotateX(degree), points[2].getRotateY(degree),

        points[3].getRotateX(degree), points[3].getRotateY(degree));

    mLabelRibbonPath.lineTo(points[4].getRotateX(degree), points[4].getRotateY(degree));

    mLabelRibbonPath.lineTo(points[5].getRotateX(degree), points[5].getRotateY(degree));

    mLabelRibbonPath.cubicTo(points[6].getRotateX(degree), points[6].getRotateY(degree),

        points[7].getRotateX(degree), points[7].getRotateY(degree),

        points[8].getRotateX(degree), points[8].getRotateY(degree));

    mLabelRibbonPath.moveTo(points[9].getRotateX(degree), points[9].getRotateY(degree));

    mLabelRibbonPath.cubicTo(points[10].getRotateX(degree), points[10].getRotateY(degree),

        points[11].getRotateX(degree), points[11].getRotateY(degree),

        points[12].getRotateX(degree), points[12].getRotateY(degree));

    mLabelRibbonPath.lineTo(points[13].getRotateX(degree), points[13].getRotateY(degree));

    mLabelRibbonPath.lineTo(points[14].getRotateX(degree), points[14].getRotateY(degree));

    mLabelRibbonPath.cubicTo(points[15].getRotateX(degree), points[15].getRotateY(degree),

        points[16].getRotateX(degree), points[16].getRotateY(degree),

        points[17].getRotateX(degree), points[17].getRotateY(degree));
  }

  public void drawBeer(BeerView.Point[] points, float degree) {
    mEmptyPath.reset();
    mBeerPath.reset();
    mBeerPath.moveTo(points[0].getRotateX(degree), points[0].getRotateY(degree));
    mBeerPath.lineTo(points[1].getRotateX(degree), points[1].getRotateY(degree));
    mBeerPath.lineTo(points[2].getRotateX(degree), points[2].getRotateY(degree));
    mBeerPath.cubicTo(points[3].getRotateX(degree), points[3].getRotateY(degree),

        points[4].getRotateX(degree), points[4].getRotateY(degree),

        points[5].getRotateX(degree), points[5].getRotateY(degree));
    mBeerPath.cubicTo(points[6].getRotateX(degree), points[6].getRotateY(degree),

        points[7].getRotateX(degree), points[7].getRotateY(degree),

        points[8].getRotateX(degree), points[8].getRotateY(degree));
    mBeerPath.cubicTo(points[9].getRotateX(degree), points[9].getRotateY(degree),

        points[10].getRotateX(degree), points[10].getRotateY(degree),

        points[11].getRotateX(degree), points[11].getRotateY(degree));

    //right
    mBeerPath.moveTo(points[12].getRotateX(degree), points[12].getRotateY(degree));
    mBeerPath.lineTo(points[13].getRotateX(degree), points[13].getRotateY(degree));
    mBeerPath.lineTo(points[14].getRotateX(degree), points[14].getRotateY(degree));
    mBeerPath.cubicTo(points[15].getRotateX(degree), points[15].getRotateY(degree),

        points[16].getRotateX(degree), points[16].getRotateY(degree),

        points[17].getRotateX(degree), points[17].getRotateY(degree));
    mBeerPath.cubicTo(points[18].getRotateX(degree), points[18].getRotateY(degree),

        points[19].getRotateX(degree), points[19].getRotateY(degree),

        points[20].getRotateX(degree), points[20].getRotateY(degree));
    mBeerPath.cubicTo(points[21].getRotateX(degree), points[21].getRotateY(degree),

        points[22].getRotateX(degree), points[22].getRotateY(degree),

        points[23].getRotateX(degree), points[23].getRotateY(degree));

    mEmptyPath.set(mBeerPath);
  }
}
