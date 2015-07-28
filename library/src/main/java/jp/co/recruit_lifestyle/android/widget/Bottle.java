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

/**
 * @author amyu_san
 */
public class Bottle {

  public final static float[][] BOTTLE_POINTS = {
      //{x, y}

      { 0.5f, 0.0452f },
      //lineTo
      { 0.4647f, 0.0452f },
      //lineTo
      { 0.4252f, 0.387f },

      { 0.4252f, 0.387f },
      //cubicTo
      { 0.353f, 0.4172f },
      //cubicTo
      { 0.3523f, 0.4774f },

      { 0.3523f, 0.4774f },
      //cubicTo
      { 0.3406f, 0.9018f },
      //cubicTo
      { 0.3575f, 0.9492f },

      { 0.3702f, 0.969f },
      //cubicTo
      { 0.5f, 0.9669f },
      //cubicTo
      { 0.5f, 0.9669f }
  };
  public final static float[][] BEER_POINTS = {
      //{x, y}

      { 0.5f, 0.14f },
      //lineTo
      { 0.4696f, 0.1395f },
      //lineTo
      { 0.4426f, 0.3985f },

      { 0.4426f, 0.3985f },
      //cubicTo
      { 0.37f, 0.4232f },
      //cubicTo
      { 0.3693f, 0.4834f },

      { 0.3693f, 0.4834f },
      //cubicTo
      { 0.3583f, 0.8538f },
      //cubicTo
      { 0.3753f, 0.9012f },

      { 0.388f, 0.9211f },
      //cubicTo
      { 0.5f, 0.9215f },
      //cubicTo
      { 0.5f, 0.9215f }
  };
  public final static float[][] LABEL_OUTER_CIRCLE_POINTS = {
      //{x, y}

      { 0.5f, 0.5148f }, { 0.4306f, 0.5148f }, { 0.3743f, 0.5926f }, { 0.3743f, 0.6885f },
      { 0.3743f, 0.7884f }, { 0.4306f, 0.8621f }, { 0.5f, 0.8621f }
  };
  public final static float[][] LABEL_INNER_CIRCLE_POINTS = {
      //{x, y}

      { 0.5f, 0.5292f }, { 0.4497f, 0.5292f }, { 0.4089f, 0.6005f }, { 0.4089f, 0.6885f },
      { 0.4089f, 0.7764f }, { 0.4306f, 0.8478f }, { 0.5f, 0.8478f }
  };
  public final static float[][] LABEL_RIBBON_POINTS = {
      //{x, y}

      { 0.5f, 0.7772f }, { 0.5f, 0.7772f }, { 0.4329f, 0.7828f }, { 0.4037f, 0.7543f },
      { 0.4189f, 0.7782f }, { 0.4037f, 0.7806f }, { 0.4487f, 0.8097f }, { 0.5f, 0.8011f },
      { 0.5f, 0.8011f }
  };

  private Bottle() {
  }
}
