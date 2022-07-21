package body.measure;

import body.Body;

/**
 * 計測クラス
 * デザインパターンのFacadeパターンを採用
 *
 */
public class Measure {
	/**
	 * 列挙型色クラス
	 * 白、黒、赤、黄色、緑、青、その他
	 * @author 原田　寛大
	 *
	 */

	/**
	 * カラー定数
	 */
	public enum Color{
		White,
		Black,
		Red,
		Yellow,
		Green,
		Blue,
		Other;
	}

	/** タッチ計測 */
	MeasureTouch measureTouch;
	/** 路面計測 */
	MeasureCourse measureCourse;
	/** 車輪計測 */
	MeasureWheel measureWheel;
	/** アーム制御 */
	MeasureArm measureArm;

	/**
	 * コンストラクタ
	 * @param measureCourse 路面計測
	 */
	public Measure(MeasureCourse measureCourse) {
		this.measureCourse = measureCourse;
	}

	/**
	 * コンストラクタ
	 * @param measureTouch
	 * @param measureCourse
	 */
	public Measure(MeasureTouch measureTouch, MeasureCourse measureCourse) {
		this.measureTouch = measureTouch;
		this.measureCourse = measureCourse;
	}

	/**
	 * コンストラクタ
	 * @param measureTouch タッチ計測
	 * @param measureCourse 路面計測
	 * @param measureWheel 車輪計測
	 */
	public Measure(MeasureTouch measureTouch, MeasureCourse measureCourse, MeasureWheel measureWheel) {
		this.measureTouch = measureTouch;
		this.measureCourse = measureCourse;
		this.measureWheel = measureWheel;
	}

	/**
	 * コンストラクタ
	 * @param measureTouch タッチ計測
	 * @param measureCourse 路面計測
	 * @param measureWheel 車輪計測
	 * @param measureArm アーム計測
	 */
	public Measure(MeasureTouch measureTouch, MeasureCourse measureCourse, MeasureWheel measureWheel,
			MeasureArm measureArm) {
		this.measureTouch = measureTouch;
		this.measureCourse = measureCourse;
		this.measureWheel = measureWheel;
		this.measureArm = measureArm;
	}

	/**
	 * 更新する
	 */
	public void update() {
		measureTouch.update();
		measureCourse.update();
		measureWheel.update();
		measureArm.update();
	}

	/**
	 * タッチセンサが離されたか
	 * @return タッチセンサが離された場合はtrue
	 */
	public boolean isUpped() {
		return measureTouch.isUpped();
	}

	/**
	 * 白明度を取得する
	 * @return　白明度
	 */
	public float getWhite() {
		return measureCourse.getWhite();
	}

	/**
	 * 白明度を設定する
	 * @param white　白明度
	 */
	public void setWhite(float white) {
		measureCourse.setWhite(white);
	}

	/**
	 * 黒明度を取得する
	 * @return　黒明度
	 */
	public float getBlack() {
		return measureCourse.getBlack();
	}

	/**
	 * 黒明度を設定する
	 * @param black　黒明度
	 */
	public void setBlack(float black) {
		measureCourse.setBlack(black);
	}

	/**
	 * 目標明度を取得する
	 * @return　目標明度
	 */
	public float getTarget() {
		return measureCourse.getTarget();
	}

	/**
	 * 目標明度を設定する
	 * @param target　目標明度
	 */
	public void setTarget(float target) {
		measureCourse.setTarget(target);
	}

	/**
	 * 路面色相を取得する
	 * @return　色相
	 */
	public float getHue() {
		return measureCourse.getHue();
	}

	/**
	 * 路面彩度を取得する
	 * @return　彩度
	 */
	public float getSaturation() {
		return measureCourse.getSaturation();
	}

	/**
	 * 路面明度を取得する
	 * @return　路面明度
	 */
	public float getValue() {
		return measureCourse.getValue();
	}

	/**
	 * 左モータの角速度(度/秒)を計測する
	 * @return leftRotationSpeed　左モータの角速度(度/秒)
	 */
	public float getLeftRotationSpeed() {
		return measureWheel.getLeftRotationSpeed();
	}

	/**
	 * 右モータの角速度(度/秒)を計測する
	 * @return rightRotationSpeed　右モータの角速度(度/秒)
	 */
	public float getRightRotationSpeed() {
		return measureWheel.getRightRotationSpeed();
	}

	/**
	 * 左モータの現在位置の角度を取得する
	 * @return 左モータの現在位置の角度
	 */
	public float getLeftAnglePosition() {
		return measureWheel.getLeftAnglePosition();
	}

	/**
	 * 右モータの現在位置の角度を取得する
	 * @return 右モータの現在位置の角度
	 */
	public float getRightAnglePosition() {
		return measureWheel.getRightAnglePosition();
	}

	/**
	 * アームモータの角度を計測する
	 * @return armDegrees アームモータの角度
	 */
	public float getDegrees() {
		return measureArm.getDegrees();
	}

	/**
	 * RGB値を取得する
	 * @return	rgb　RGB値
	 */
	public float[] getRGB() {
		return measureCourse.getRGB();
	}

	/**
	 * HSVクラスの色相を取得する
	 * @return hue
	 */
	public float getHueHSV() {
		return measureCourse.getMeasureCourseHSV().getHue();
	}

	/**
	 * HSVクラスの彩度を取得する
	 * @return saturation
	 */
	public float getSaturationHSV() {
		return measureCourse.getMeasureCourseHSV().getSaturation();
	}

	/**
	 * HSVクラスの明度を取得する
	 * @return value
	 */
	public float getValueHSV() {
		return measureCourse.getMeasureCourseHSV().getValue();
	}

	/**
	 * HSVクラスの色判定結果を取得する
	 * @return
	 */
	public Color getColorHSV() {
		return measureCourse.getMeasureCourseHSV().getColor();
	}

	/**
	 * HSLクラスの色相を取得する
	 * @return hue
	 */
	public float getHueHSL() {
		//		return measureCourseHSL.getHue();
		return measureCourse.getMeasureCourseHSL().getHue();
	}

	/**
	 * HSLクラスの彩度を取得する
	 * @return saturation
	 */
	public float getSaturationHSL() {
		return measureCourse.getMeasureCourseHSL().getSaturation();
	}

	/**
	 * HSLクラスの明度を取得する
	 * @return
	 */
	public float getLightnessHSL() {
		return measureCourse.getMeasureCourseHSL().getLightness();
	}

	/**
	 * HSLクラスの色判定結果を取得する
	 * @return
	 */
	public Color getColorHSL() {
		return measureCourse.getMeasureCourseHSL().getColor();
	}

	/**
	 * 色判定用の閾値を算出する
	 */
	public void calcColorBorder() {

		//★★★ここにキャリブレーション結果から算出した色判定の閾値を設定するプログラムを追加して以下の4変数に値を入れる！！！
		//★★★修正したらこの2行のコメントは削除してね。

		float borderRedToYellow = 30.0f;
		float borderYellowToGreen = 90.0f;
		float borderGreenToBlue = 180.0f;
		float borderBlueToRed = 300.0f;

		float sat50BlackJudgeValueToHSL;
		float sat50WhiteJudgeValueToHSL;
		float sat100BlackJudgeValueToHSL;
		float sat100WhiteJudgeValueToHSL;
		float target = Body.measure.getTarget();
		float white = Body.measure.getWhite();

		sat100BlackJudgeValueToHSL = target - (white - target) * 35.0f / 50f;
		sat100WhiteJudgeValueToHSL = target + (white - target) * 35.0f / 50f;

		/** (limitWhite - averageLimitWhiteBlack)が0.035fになるような係数 */
		float correctionJudgeColorHSL = 0.35f / (sat100WhiteJudgeValueToHSL - target);

		sat50BlackJudgeValueToHSL = target - 0.3f * correctionJudgeColorHSL;
		sat50WhiteJudgeValueToHSL = target + 0.3f * correctionJudgeColorHSL;

		// judgeColorHSVで使用 白色の上限値
		float limitSatWhiteHSV;
		// judgeColorHSVで使用 黒色の上限値
		float limitSatBlackHSV;

		limitSatWhiteHSV = 0.5f;
		limitSatBlackHSV = Body.measure.getTarget() * 3.0f / 5.0f;

		measureCourse.setSat50BlackJudgeValueToHSL(sat50BlackJudgeValueToHSL);
		measureCourse.setSat50WhiteJudgeValueToHSL(sat50WhiteJudgeValueToHSL);
		measureCourse.setSat100BlackJudgeValueToHSL(sat100BlackJudgeValueToHSL);
		measureCourse.setSat100WhiteJudgeValueToHSL(sat100WhiteJudgeValueToHSL);

		measureCourse.setBorderRedToYellow(borderRedToYellow);
		measureCourse.setBorderYellowToGreen(borderYellowToGreen);
		measureCourse.setBorderGreenToBlue(borderGreenToBlue);
		measureCourse.setBorderBlueToRed(borderBlueToRed);

	}

}
