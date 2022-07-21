package body.measure;

import body.Body;
import body.measure.Measure.Color;

/**
 * 路面計測HSVクラス
 * @author 原田　寛大
 */
public class MeasureCourseHSV extends MeasureCourseHue {
	/** sv（彩度:Saturation、明度:Value）*/
	private float saturation, value;

	/**
	 * 彩度を取得する
	 * @return saturation 彩度
	 */
	float getSaturation() {
		return saturation;
	}

	/**
	 * 明度を取得する
	 * @return value 輝度
	 */
	float getValue() {
		return value;
	}

	public void update() {
		convertRGBTo(Body.measure.getRGB());
		judgeColor();
	}

	/**
	 * 色判定結果を設定する
	 */
	public void judgeColor() {
		if (value <= Body.measure.measureCourse.getLimitSatBlackHSV()) {
			setColor(Color.Black);
		} else if (saturation < Body.measure.measureCourse.getLimitSatWhiteHSV()) {
			setColor(Color.White);
		} else {
			setColor(judgeColorHue(getHue()));
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

		//色相設定用変数
		float hue;

		// rgbからhsvへ変換
		if (max == min) {
			hue = -1.0f;
			if (max == 0) {
				saturation = -1.0f;
			}
		} else {
			/* h設定部分 */
			if (max == r) {
				hue = (g - b) / (max - min) * 60.0f;
			} else if (max == g) {
				hue = (b - r) / (max - min) * 60.0f + 120.0f;
			} else {
				hue = (r - g) / (max - min) * 60.0f + 240.0f;
			}
			if (hue < 0.0f) {
				hue = hue + 360.0f;
			} else if (hue > 360.0f) {
				hue = hue - 360.0f;
			}
			setHue(hue);

			/* s設定部分 */
			saturation = (max - min) / max;
		}
		value = max;
	}
}
