/**
 * Copyright 2008 - 2012
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * @project loon
 * @author cping
 * @email：javachenpeng@yahoo.com
 * @version 0.3.3
 */
package loon.utils;

public class Easing {

	private static final int TYPE_IN = 0;

	private static final int TYPE_OUT = 1;

	private static final int TYPE_IN_OUT = 2;

	private static final int TYPE_NONE = 3;

	private static final int FUNCTION_LINEAR = 0;

	private static final int FUNCTION_QUADRADIC = 1;

	private static final int FUNCTION_IN = 2;

	private static final int FUNCTION_QUARTIC = 3;

	private static final int FUNCTION_QUINTIC = 4;

	private static final int FUNCTION_BACK = 5;

	private static final int FUNCTION_ELASTIC = 6;

	private static final int FUNCTION_NONE = 7;

	private static final int FUNCTION_OUT = 8;

	private static final int FUNCTION_INOUT = 9;

	private static final int FUNCTION_IN_BACK = 10;

	private static final int FUNCTION_OUT_BACK = 11;

	private static final int FUNCTION_BOUNCE_OUT = 12;

	private static final int FUNCTION_OUT_ELASTIC = 13;

	public static final Easing NONE = new Easing("NONE", TYPE_IN,
			FUNCTION_LINEAR);

	public static final Easing REGULAR_IN = new Easing("REGULAR_IN", TYPE_IN,
			FUNCTION_QUADRADIC);

	public static final Easing REGULAR_OUT = new Easing("REGULAR_OUT",
			TYPE_OUT, FUNCTION_QUADRADIC);

	public static final Easing REGULAR_IN_OUT = new Easing("REGULAR_IN_OUT",
			TYPE_IN_OUT, FUNCTION_QUADRADIC);

	public static final Easing STRONG_IN = new Easing("STRONG_IN", TYPE_IN,
			FUNCTION_QUINTIC);

	public static final Easing STRONG_OUT = new Easing("STRONG_OUT", TYPE_OUT,
			FUNCTION_QUINTIC);

	public static final Easing STRONG_IN_OUT = new Easing("STRONG_IN_OUT",
			TYPE_IN_OUT, FUNCTION_QUINTIC);

	public static final Easing BACK_IN = new Easing("BACK_IN", TYPE_IN,
			FUNCTION_BACK);

	public static final Easing BACK_OUT = new Easing("BACK_OUT", TYPE_OUT,
			FUNCTION_BACK);

	public static final Easing BACK_IN_OUT = new Easing("BACK_IN_OUT",
			TYPE_IN_OUT, FUNCTION_BACK);

	public static final Easing ELASTIC_IN = new Easing("ELASTIC_IN", TYPE_IN,
			FUNCTION_ELASTIC);

	public static final Easing ELASTIC_OUT = new Easing("ELASTIC_OUT",
			TYPE_OUT, FUNCTION_ELASTIC);

	public static final Easing ELASTIC_IN_OUT = new Easing("ELASTIC_IN_OUT",
			TYPE_IN_OUT, FUNCTION_ELASTIC);

	public static final Easing JUST_NONE = new Easing("JUST_NONE", TYPE_NONE,
			FUNCTION_NONE);

	public static final Easing JUST_LINEAR = new Easing("JUST_LINEAR",
			TYPE_NONE, FUNCTION_LINEAR);

	public static final Easing JUST_EASE_IN = new Easing("JUST_EASE_IN",
			TYPE_NONE, FUNCTION_IN);

	public static final Easing JUST_EASE_OUT = new Easing("JUST_EASE_OUT",
			TYPE_NONE, FUNCTION_OUT);

	public static final Easing JUST_EASE_INOUT = new Easing("JUST_EASE_INOUT",
			TYPE_NONE, FUNCTION_INOUT);

	public static final Easing JUST_EASE_IN_BACK = new Easing(
			"JUST_EASE_IN_BACK", TYPE_NONE, FUNCTION_IN_BACK);

	public static final Easing JUST_EASE_OUT_BACK = new Easing(
			"JUST_EASE_OUT_BACK", TYPE_NONE, FUNCTION_OUT_BACK);

	public static final Easing JUST_BOUNCE_OUT = new Easing("JUST_BOUNCE_OUT",
			TYPE_NONE, FUNCTION_BOUNCE_OUT);

	public static final Easing JUST_EASE_OUT_ELASTIC = new Easing(
			"JUST_EASE_OUT_ELASTIC", TYPE_NONE, FUNCTION_OUT_ELASTIC);

	private final int type;

	private final int function;

	private final float strength;

	private final String name;

	protected Easing() {
		this("NONE");
	}

	protected Easing(String name) {
		this(name, TYPE_IN, FUNCTION_LINEAR);
	}

	protected Easing(String name, int type) {
		this(name, type, FUNCTION_LINEAR);
	}

	private Easing(String name, int type, int function) {
		this(name, type, function, 1);
	}

	private Easing(String name, int type, int function, float stength) {
		this.name = name;
		this.type = type;
		this.function = function;
		this.strength = stength;
	}

	public Easing(Easing easing, float strength) {
		this(easing.name, easing.type, easing.function, strength);
	}

	public final float apply(float time, float duration) {
		if (TYPE_NONE == type) {
			return call(function, duration / time);
		}
		if (time <= 0 || duration <= 0) {
			return 0;
		} else if (time >= duration) {
			return duration;
		}

		final float t = time / duration;

		float easedT;

		switch (type) {

		default:
			easedT = t;
			break;

		case TYPE_IN:
			easedT = call(function, t);
			break;

		case TYPE_OUT:
			easedT = 1 - call(function, 1 - t);
			break;

		case TYPE_IN_OUT:
			if (t < 0.5) {
				easedT = call(function, 2 * t) / 2;
			} else {
				easedT = 1 - call(function, 2 - 2 * t) / 2;
			}
			break;
		}
		if (strength != 1) {
			easedT = strength * easedT + (1 - strength) * t;
		}
		return (easedT * duration);
	}

	public float applyClamp(float time, float duration) {
		return apply((time < 0) ? 0 : (time > 1 ? 1 : time), duration);
	}

	public float apply(float start, float range, float time, float duration) {
		float pos = (duration == 0) ? 1 : apply(time, duration);
		return start + range * pos;
	}

	public float applyClamp(float start, float range, float time, float duration) {
		return apply(start, range, duration, MathUtils.clamp(time, 0, duration));
	}

	protected static float call(int fun, float t) {

		float t2;
		float t3;

		switch (fun) {

		default:
		case FUNCTION_LINEAR:
			return t;

		case FUNCTION_QUADRADIC:
			return t * t;

		case FUNCTION_IN:
			return t * t * t;

		case FUNCTION_QUARTIC:
			t2 = t * t;
			return t2 * t2;

		case FUNCTION_QUINTIC:
			t2 = t * t;
			return t2 * t2 * t;

		case FUNCTION_BACK:
			t2 = t * t;
			t3 = t2 * t;
			return t3 + t2 - t;

		case FUNCTION_ELASTIC:
			t2 = t * t;
			t3 = t2 * t;

			float scale = t2 * (2 * t3 + t2 - 4 * t + 2);
			float wave = -MathUtils.sin(t * 3.5f * MathUtils.PI);

			return scale * wave;
		case FUNCTION_NONE:
			return 0;
		case FUNCTION_OUT:
			t2 = t - 1;
			return (1 + t2 * t2 * t2);
		case FUNCTION_INOUT:
			t2 = 2 * t;
			if (t2 < 1) {
				return (t2 * t2 * t2) / 2;
			}
			t3 = t2 - 2;
			return (2 + t3 * t3 * t3) / 2;
		case FUNCTION_IN_BACK:
			t2 = 1.70158f;
			return t * t * ((t2 + 1) * t - t2);
		case FUNCTION_OUT_BACK:
			t2 = 1.70158f;
			t3 = t - 1;
			return (t3 * t3 * ((t2 + 1) * t3 + t2) + 1);
		case FUNCTION_BOUNCE_OUT:
			if (t < (1 / 2.75f)) {
				return 7.5625f * t * t;
			} else if (t < (2 / 2.75f)) {
				t2 = t - (1.5f / 2.75f);
				return 7.5625f * t2 * t2 + 0.75f;
			} else if (t < (2.5 / 2.75)) {
				t2 = t - (2.25f / 2.75f);
				return 7.5625f * t2 * t2 + 0.9375f;
			} else {
				t2 = t - (2.625f / 2.75f);
				return 7.5625f * t2 * t2 + 0.984375f;
			}
		case FUNCTION_OUT_ELASTIC:
			t2 = 0.3f / 4;
			t3 = (float) (2 * MathUtils.PI / 0.3);
			return MathUtils.pow(2, -10 * t)
					* MathUtils.sin((t - t2) * t3) + 1;
		}
	}

}
