package body.measure;

import body.Body;
import body.measure.Measure.Color;

/**
 * 路面計測色相クラス
 * @author 原田　寛大
 */
public class MeasureCourseHue {

	/** 色判定結果 黒,白,赤,黄,緑,青,その他 のいずれか */
	private Color color;

	/**
	 * 色判定結果を設定する
	 * @param color :白、黒、赤、黄、緑、青のいずれか
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * 色判定結果を取得する
	 * @return color :白、黒、赤、黄、緑、青のいずれか
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * 色相：Hueの数値から色を判定する
	 * @return 赤、黄、緑、青のいずれか
	 */
	Color judgeColorHue(float hue) {
		if (hue > Body.measure.measureCourse.getBorderBlueToRed()|| hue <= Body.measure.measureCourse.getBorderRedToYellow()) {
			return Color.Red;
		} else if (hue <= Body.measure.measureCourse.getBorderYellowToGreen()) {
			return Color.Yellow;
		} else if (hue <= Body.measure.measureCourse.getBorderGreenToBlue()) {
			return Color.Green;
		} else {
			return Color.Blue;
		}
	}

}
