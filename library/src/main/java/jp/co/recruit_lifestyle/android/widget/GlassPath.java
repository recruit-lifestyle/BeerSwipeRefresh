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

/**
 * @author amyu_san
 */
public class GlassPath {

  private Path mGlassPath;

  private Path mBeerPath;

  private Path mBeerFrothPath;

  public GlassPath() {
    mGlassPath = new Path();
    mBeerPath = new Path();
    mBeerFrothPath = new Path();
  }

  public Path getGlassPath() {
    return mGlassPath;
  }

  public Path getBeerPath() {
    return mBeerPath;
  }

  public Path getBeerFrothPath() {
    return mBeerFrothPath;
  }

  public void drawGlass(BeerView.Point[] points, float degree) {
    mGlassPath.reset();
    mGlassPath.moveTo(points[0].getRotateX(degree), points[0].getRotateY(degree));
    mGlassPath.lineTo(points[1].getRotateX(degree), points[1].getRotateY(degree));
    mGlassPath.cubicTo(points[2].getRotateX(degree), points[2].getRotateY(degree),

        points[3].getRotateX(degree), points[3].getRotateY(degree),

        points[4].getRotateX(degree), points[4].getRotateY(degree));
    mGlassPath.cubicTo(points[5].getRotateX(degree), points[5].getRotateY(degree),

        points[6].getRotateX(degree), points[6].getRotateY(degree),

        points[7].getRotateX(degree), points[7].getRotateY(degree));
    mGlassPath.lineTo(points[8].getRotateX(degree), points[8].getRotateY(degree));

    mGlassPath.moveTo(points[9].getRotateX(degree), points[9].getRotateY(degree));
    mGlassPath.lineTo(points[10].getRotateX(degree), points[10].getRotateY(degree));
    mGlassPath.cubicTo(points[11].getRotateX(degree), points[11].getRotateY(degree),

        points[12].getRotateX(degree), points[12].getRotateY(degree),

        points[13].getRotateX(degree), points[13].getRotateY(degree));
    mGlassPath.cubicTo(points[14].getRotateX(degree), points[14].getRotateY(degree),

        points[15].getRotateX(degree), points[15].getRotateY(degree),

        points[16].getRotateX(degree), points[16].getRotateY(degree));
    mGlassPath.lineTo(points[17].getRotateX(degree), points[17].getRotateY(degree));
  }

  public void drawBeer(BeerView.Point[] points, float degree) {
    mBeerPath.reset();
    mBeerPath.moveTo(points[0].getRotateX(degree), points[0].getRotateY(degree));
    mBeerPath.lineTo(points[1].getRotateX(degree), points[1].getRotateY(degree));
    mBeerPath.cubicTo(points[2].getRotateX(degree), points[2].getRotateY(degree),

        points[3].getRotateX(degree), points[3].getRotateY(degree),

        points[4].getRotateX(degree), points[4].getRotateY(degree));
    mBeerPath.cubicTo(points[5].getRotateX(degree), points[5].getRotateY(degree),

        points[6].getRotateX(degree), points[6].getRotateY(degree),

        points[7].getRotateX(degree), points[7].getRotateY(degree));
    mBeerPath.lineTo(points[8].getRotateX(degree), points[8].getRotateY(degree));

    mBeerPath.moveTo(points[9].getRotateX(degree), points[9].getRotateY(degree));
    mBeerPath.lineTo(points[10].getRotateX(degree), points[10].getRotateY(degree));
    mBeerPath.cubicTo(points[11].getRotateX(degree), points[11].getRotateY(degree),

        points[12].getRotateX(degree), points[12].getRotateY(degree),

        points[13].getRotateX(degree), points[13].getRotateY(degree));
    mBeerPath.cubicTo(points[14].getRotateX(degree), points[14].getRotateY(degree),

        points[15].getRotateX(degree), points[15].getRotateY(degree),

        points[16].getRotateX(degree), points[16].getRotateY(degree));
    mBeerPath.lineTo(points[17].getRotateX(degree), points[17].getRotateY(degree));
  }

  public void drawGlassFroth(float degree, BeerView.Point[] points, float radiusPercent) {
    radiusPercent = Math.min(radiusPercent, 1);
    mBeerFrothPath.reset();
    for (BeerView.Point point : points) {
      mBeerFrothPath.addCircle(point.getRotateX(degree), point.getRotateY(degree),
          point.getRadius() * radiusPercent, Path.Direction.CCW);
    }
  }
}
