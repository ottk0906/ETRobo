package body.measure;

import body.Body;
import body.measure.Measure.Color;

/**
 * 路面計測HSLクラス
 * @author 原田　寛大
 */
public class MeasureCourseHSL extends MeasureCourseHue {
	/** hsl（色相:Hue、彩度:Saturation、明度:Lightness）*/
	private float[] hsl = new float[3];

	/**
	 * 色相を取得する
	 * @return hsl[0] 色相
	 */
	public float getHue() {
		return hsl[0];
	}

	/**
	 * 彩度を取得する
	 * @return hsl[1] 彩度
	 */
	public float getSaturation() {
		return hsl[1];
	}

	/**
	 * 明度を取得する
	 * @return
	 */
	public float getLightness() {
		return hsl[2];
	}

	/**
	 * 更新する
	 */
	public void update() {
		convertRGBTo(Body.measure.getRGB());
		judgeColor();
	}

	/**
	 * 色をHSLで判定する
	 * 色判定結果を設定する
	 */
	public void judgeColor() {
		if (hsl[1] <= 0.3f) {
			if (hsl[2] < Body.measure.getTarget()) {
				setColor(Color.Black);
			} else {
				setColor(Color.White);
			}
		} else if (hsl[1] <= 0.5f) {
			if (hsl[2] < Body.measure.measureCourse.getSat50BlackJudgeValueToHSL()) {
				setColor(Color.Black);
			} else if (hsl[2] > Body.measure.measureCourse.getSat50WhiteJudgeValueToHSL()) {
				setColor(Color.White);
			} else {
				setColor(judgeColorHue(hsl[0]));
			}
		} else {
			if (hsl[2] < Body.measure.measureCourse.getSat100BlackJudgeValueToHSL()) {
				setColor(Color.Black);
			} else if (hsl[2] > Body.measure.measureCourse.getSat100WhiteJudgeValueToHSL()) {
				setColor(Color.White);
			} else {
				setColor(judgeColorHue(hsl[0]));
			}
		}
	}

	/**
	 * RGBからHSLに変換する
	 * HSLを設定する
	 * @param rgb
	 */
	void convertRGBTo(float[] rgb) {
		// rgb（赤:Red、緑:Green、青:Blue）
		float r = rgb[0];
		float g = rgb[1];
		float b = rgb[2];

		// rgbの最大値
		float max;
		// rgbの最小値
		float min;

		//収束値
		float cnt;

		//maxとminを設定
		if (r > g) {
			if (r > b) {
				max = r;
				if (g > b) {
					min = b;
				} else {
					min = g;
				}
			} else {
				max = b;
				min = g;
			}
		} else {
			if (g > b) {
				max = g;
				if (r > b) {
					min = b;
				} else {
					min = r;
				}
			} else {
				max = b;
				min = r;
			}
		}

		// rgbからhsvへ変換
		if (max == min) {
			hsl[0] = -1.0f;
		} else {
			/* h設定部分 */
			if (max == r) {
				hsl[0] = (g - b) / (max - min) * 60.0f;
			} else if (max == g) {
				hsl[0] = (b - r) / (max - min) * 60.0f + 120.0f;
			} else {
				hsl[0] = (r - g) / (max - min) * 60.0f + 240.0f;
			}
			if (hsl[0] < 0.0f) {
				hsl[0] += 360.0f;
			} else if (hsl[0] > 360.0f) {
				hsl[0] -= 360.0f;
			}
		}

		/* s設定部分*/
		//収束値:cnt
		cnt = (max + min) / 2.0f;
		if (cnt <= (1.0f / 2.0f)) {
			hsl[1] = (cnt - min) / cnt;
		} else {
			hsl[1] = (max - cnt) / (1.0f - cnt);
		}
		/*l設定部分*/
		hsl[2] = (max + min) / 2;
	}
}
