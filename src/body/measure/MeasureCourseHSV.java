package body.measure;

import body.Body;
import body.measure.Measure.Color;

/**
 * 路面計測HSVクラス
 * @author 原田　寛大
 */
public class MeasureCourseHSV extends MeasureCourseHue {
	/** hsv（彩度:Saturation、明度:Value）*/
	private float[] hsv = new float[3];

	/**
	 * 色相を取得する
	 * @return hsv[0] 色相
	 */
	public float getHue() {
		return hsv[0];
	}

	/**
	 * 彩度を取得する
	 * @return hsv[1] 彩度
	 */
	float getSaturation() {
		return hsv[1];
	}

	/**
	 * 明度を取得する
	 * @return hsv[2] 輝度
	 */
	float getValue() {
		return hsv[2];
	}

	public void update() {
		convertRGBTo(Body.measure.getRGB());
		judgeColor();
	}

	/**
	 * 色判定結果を設定する
	 */
	public void judgeColor() {
		if (hsv[2] <= Body.measure.measureCourse.getLimitSatBlackHSV()) {
			setColor(Color.Black);
		} else if (hsv[1] < Body.measure.measureCourse.getLimitSatWhiteHSV()) {
			setColor(Color.White);
		} else {
			setColor(judgeColorHue(hsv[0]));
		}
	}

	/**
	 * RGBをHSVに変換する
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
			hsv[0] = -1.0f;
			if (max == 0) {
				hsv[1] = -1.0f;
			}
		} else {
			/* h設定部分 */
			if (max == r) {
				hsv[0] = (g - b) / (max - min) * 60.0f;
			} else if (max == g) {
				hsv[0] = (b - r) / (max - min) * 60.0f + 120.0f;
			} else {
				hsv[0] = (r - g) / (max - min) * 60.0f + 240.0f;
			}
			if (hsv[0] < 0.0f) {
				hsv[0] = hsv[0] + 360.0f;
			} else if (hsv[0] > 360.0f) {
				hsv[0] = hsv[0] - 360.0f;
			}

			/* s設定部分 */
			hsv[1] = (max - min) / max;
		}
		hsv[2] = max;
	}
}
