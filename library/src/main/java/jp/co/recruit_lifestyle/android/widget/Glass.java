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
public class Glass {

  public final static float[][] GLASS_POINTS = {
      //{x, y}

      { 0.5f, 0.1321f },
      //lineTo
      { 0.2904f, 0.1321f },

      { 0.2904f, 0.3845f },
      //cubicTo
      { 0.3384f, 0.4613f },
      //cubicTo
      { 0.3621f, 0.5025f },

      { 0.4129f, 0.6295f },
      //cubicTo
      { 0.4102f, 0.7528f },
      //cubicTo
      { 0.3616f, 0.9303f },

      { 0.5f, 0.9303f }
      //lineTo
  };
  public final static float[][] GLASS_BEER_POINTS = {
      //{x, y}

      { 0.5f, 0.2417f },
      //lineTo
      { 0.3065f, 0.2417f },

      { 0.315f, 0.3809f },
      //cubicTo
      { 0.3423f, 0.4159f },
      //cubicTo
      { 0.366f, 0.4754f },

      { 0.4168f, 0.6024f },
      //cubicTo
      { 0.4354f, 0.7175f },
      //cubicTo
      { 0.3868f, 0.895f },

      { 0.5f, 0.895f }
      //lineTo
  };
  public final static float[][] GLASS_BEER_FROTH_POINTS = {
      //{x, y, radius}
      { 0.6648f, 0.2284f, 0.054f }, { 0.624f, 0.2284f, 0.054f }, { 0.5633f, 0.2284f, 0.054f },
      { 0.5097f, 0.202f, 0.054f }, { 0.4414f, 0.2185f, 0.054f }, { 0.3686f, 0.2284f, 0.054f },
      { 0.3256f, 0.1917f, 0.054f }, { 0.3201f, 0.143f, 0.054f }, { 0.3686f, 0.1259f, 0.054f },
      { 0.4488f, 0.1377f, 0.054f }, { 0.5024f, 0.143f, 0.054f }, { 0.5633f, 0.1645f, 0.054f },
      { 0.6169f, 0.143f, 0.054f }, { 0.6704f, 0.148f, 0.054f }, { 0.3792f, 0.2825f, 0.0314f },
      { 0.3952f, 0.3139f, 0.0165f },
  };

  private Glass() {
  }
}
