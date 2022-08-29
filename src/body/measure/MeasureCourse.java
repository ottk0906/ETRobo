package body.measure;

import java.util.Arrays;

import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;

/**
 * 路面計測クラス
 *
 */
public class MeasureCourse {

	//赤色上限値,黄色下限値
	private float borderRedToYellow;
	//黄色上限値,緑色下限値
	private float borderYellowToGreen;
	//緑色上限値,青色下限値
	private float borderGreenToBlue;
	//青色上限値,赤色下限値
	private float borderBlueToRed;
	// judgeColorHSLで使用 saturation = 0.5f以下の時
	private float sat50BlackJudgeValueToHSL;
	private float sat50WhiteJudgeValueToHSL;
	//judgeColorHSLで使用 saturation = 1.0f以下の時
	private float sat100BlackJudgeValueToHSL;
	private float sat100WhiteJudgeValueToHSL;
	// judgeColorHSVで使用 白色の上限値
	private float limitSatWhiteHSV;
	// judgeColorHSVで使用 黒色の上限値
	private float limitSatBlackHSV;
	// RGBの係数
	private float kr;
	private float kg;
	private float kb;
	// 白RGB(赤、緑、青)
	private float whiteRGB[];
	// 黒RGB(赤、緑、青)
	private float blackRGB[];

	/** カラーセンサ */
	private EV3ColorSensor colorSensor;
	private SensorMode sensorMode;

	private MeasureCourseHSL measureCourseHSL = new MeasureCourseHSL();
	private MeasureCourseHSV measureCourseHSV = new MeasureCourseHSV();

	/** 白明度 */
	private float white;
	/** 黒明度 */
	private float black;
	/** 目標明度 */
	private float target;
	/** 路面RGB（割合） */
	private float rgb[];

	/** 路面RGB（生値） */
	private float rgbOrigin[];

	/**
	 * コンストラクタ
	 * @param colorSensor カラーセンサ
	 */
	public MeasureCourse(EV3ColorSensor colorSensor) {
		this.colorSensor = colorSensor;
		sensorMode = colorSensor.getRGBMode();
		rgb = new float[sensorMode.sampleSize()];
		rgbOrigin = new float[sensorMode.sampleSize()];

		whiteRGB = new float[3];
		blackRGB = new float[3];
		blackRGB[0] = 0.0f;
		blackRGB[1] = 0.0f;
		blackRGB[2] = 0.0f;
		target = 0.5f;
		kr = 1;
		kg = 1;
		kb = 1;
	}

	/**
	 * 更新する
	 */
	public void update() {
		//RGBを取得する
		sensorMode.fetchSample(rgb, 0);

		//RGBの生値をコピーする
		rgbOrigin = Arrays.copyOf(rgb, 3);

		//RGBに補正をかける
		ajustRGB();

		//RGBをHSV、HSLに変換し、それぞれで色を判定する
		measureCourseHSL.update();
		measureCourseHSV.update();
	}

	public MeasureCourseHSV getMeasureCourseHSV() {
		return measureCourseHSV;
	}

	public MeasureCourseHSL getMeasureCourseHSL() {
		return measureCourseHSL;
	}

	/**
	 * 白明度を取得する
	 * @return　白明度
	 */
	public float getWhite() {
		return white;
	}

	/**
	 * 白明度を設定する
	 * @param white　白明度
	 */
	public void setWhite(float white) {
		this.white = white;
	}

	/**
	 * 黒明度を取得する
	 * @return　黒明度
	 */
	public float getBlack() {
		return black;
	}

	/**
	 * 黒明度を設定する
	 * @param black　黒明度
	 */
	public void setBlack(float black) {
		this.black = black;
	}

	/**
	 * 目標明度を取得する
	 * @return　目標明度
	 */
	public float getTarget() {
		return target;
	}

	/**
	 * 目標明度を設定する
	 * @param target　目標明度
	 */
	public void setTarget(float target) {
		this.target = target;
	}

	/**
	 * 路面色相を取得する
	 * @return　路面色相
	 */
	public float getHue() {
		return measureCourseHSL.getHue();
	}

	/**
	 * 路面彩度を取得する
	 * @return　路面彩度
	 */
	public float getSaturation() {
		return measureCourseHSL.getSaturation();
	}

	/**
	 * 路面明度を取得する
	 * @return　路面明度
	 */
	public float getValue() {
		return measureCourseHSL.getLightness();
	}

	/**
	 * RGB値を取得する
	 * @return rgb (割合)
	 */
	public float[] getRGB() {
		return rgb;
	}

	/**
	 * RGB値（生値）を取得する
	 * @return rgb (生値)
	 */
	public float[] getRGBOrigin() {
		return rgbOrigin;
	}

	//************* 色判定閾値設定のsetter() *************

	/**
	 * 閾値を設定する（赤色上限値、黄色下限値）
	 * @param value 閾値
	 */
	public void setBorderRedToYellow(float value) {
		borderRedToYellow = value;
	}

	/**
	 * 閾値を設定する（黄色上限値、緑色下限値）
	 * @param value 閾値
	 */
	public void setBorderYellowToGreen(float value) {
		borderYellowToGreen = value;
	}

	/**
	 * 閾値を設定する（緑色上限値、青色下限値）
	 * @param value 閾値
	 */
	public void setBorderGreenToBlue(float value) {
		borderGreenToBlue = value;
	}

	/**
	 * 閾値を設定する（青色上限値、赤色下限値）
	 * @param value 閾値
	 */
	public void setBorderBlueToRed(float value) {
		borderBlueToRed = value;
	}

	//************* HSLの閾値設定のsetter() *************

	/**
	 * 閾値を設定する（HSLでsaturation = 0.5以下の場合の黒色とその他の色との閾値）
	 * @param value 閾値
	 */
	public void setSat50BlackJudgeValueToHSL(float value) {
		sat50BlackJudgeValueToHSL = value;
	}

	/**
	 * 閾値を設定する（HSLでsaturation = 0.5以下の場合の白色とその他の色との閾値）
	 * @param value 閾値
	 */
	public void setSat50WhiteJudgeValueToHSL(float value) {
		sat50WhiteJudgeValueToHSL = value;
	}

	/**
	 * 閾値を設定する（HSLでsaturation = 1.0以下の場合の黒色とその他の色との閾値）
	 * @param value 閾値
	 */
	public void setSat100BlackJudgeValueToHSL(float value) {
		sat100BlackJudgeValueToHSL = value;
	}

	/**
	 * 閾値を設定する（HSLでsaturation = 1.0以下の場合の白色とその他の色との閾値）
	 * @param value 閾値
	 */
	public void setSat100WhiteJudgeValueToHSL(float value) {
		sat100WhiteJudgeValueToHSL = value;
	}

	//************* HSVの閾値設定のsetter() *************

	/**
	 * 閾値を設定する（HSVでの白色の上限値）
	 * @param value 閾値
	 */
	public void setLimitSatWhiteHSV(float value) {
		limitSatWhiteHSV = value;
	}

	/**
	 * 閾値を設定する（HSVでの黒色の上限値）
	 * @param value 閾値
	 */
	public void setLimitSatBlackHSV(float value) {
		limitSatBlackHSV = value;
	}

	//************* 色判定閾値設定のgetter() *************

	/**
	 * 閾値を取得する（赤色上限値、黄色下限値）
	 * @return  閾値
	 */
	public float getBorderRedToYellow() {
		return borderRedToYellow;
	}

	/**
	 * 閾値を取得する（黄色上限値、緑色下限値）
	 * @return  閾値
	 */
	public float getBorderYellowToGreen() {
		return borderYellowToGreen;
	}

	/**
	 * 閾値を取得する（緑色上限値、青色下限値）
	 * @return  閾値
	 */
	public float getBorderGreenToBlue() {
		return borderGreenToBlue;
	}

	/**
	 * 閾値を取得する（青色上限値、赤色下限値）
	 * @return  閾値
	 */
	public float getBorderBlueToRed() {
		return borderBlueToRed;
	}

	//************* HSLの閾値設定のgetter() *************

	/**
	 * 閾値を取得する（saturation = 0.5以下の場合の黒色とその他の色との閾値）
	 * @return 閾値
	 */
	public float getSat50BlackJudgeValueToHSL() {
		return sat50BlackJudgeValueToHSL;
	}

	/**
	 * 閾値を取得する（saturation = 0.5以下の場合の白色とその他の色との閾値）
	 * @return 閾値
	 */
	public float getSat50WhiteJudgeValueToHSL() {
		return sat50WhiteJudgeValueToHSL;
	}

	/**
	 * 閾値を取得する（saturation = 1.0以下の場合の黒色とその他の色との閾値）
	 * @return 閾値
	 */
	public float getSat100BlackJudgeValueToHSL() {
		return sat100BlackJudgeValueToHSL;
	}

	/**
	 * 閾値を取得する（saturation = 1.0以下の場合の白色とその他の色との閾値）
	 * @return 閾値
	 */
	public float getSat100WhiteJudgeValueToHSL() {
		return sat100WhiteJudgeValueToHSL;
	}

	//************* HSVの閾値設定のgetter() *************

	/**
	 * 閾値を取得する（HSVでの白色の上限値）
	 * @return 閾値
	 */
	public float getLimitSatWhiteHSV() {
		return limitSatWhiteHSV;
	}

	/**
	 * 閾値を取得する（HSVでの黒色の上限値）
	 * @return 閾値
	 */
	public float getLimitSatBlackHSV() {
		return limitSatBlackHSV;
	}

	//************* RGBキャリブレーション *************

	/**
	 * 白キャリブレーションのRGB値を取得する
	 * @return 白キャリブレーションのRGB値
	 */
	public float[] getWhiteRGB() {
		return this.whiteRGB;
	}

	/**
	 * 黒キャリブレーションのRGB値を取得する
	 * @return 黒キャリブレーションのRGB値
	 */
	public float[] getBlackRGB() {
		return this.blackRGB;
	}

	/**
	 * 白キャリブレーションのRGB値を設定する
	 * @param maxRGB
	 */
	public void setWhiteRGB(float[] maxRGB) {
		this.whiteRGB = maxRGB;
	}

	/**
	 * 黒キャリブレーションのRGB値を設定する
	 * @param minRGB
	 */
	public void setBlackRGB(float[] minRGB) {
		this.blackRGB = minRGB;
	}

	/**
	 * RGBの係数をキャリブレーション結果から設定する
	 */
	public void setKRKGKB() {
		kr = 1.0f / (whiteRGB[0] - blackRGB[0]);
		kg = 1.0f / (whiteRGB[1] - blackRGB[1]);
		kb = 1.0f / (whiteRGB[2] - blackRGB[2]);
	}

	/**
	 * RGBの数値に補正をかける
	 * その数値が0～1内なら今回の数値を、
	 * 0より小さいなら0を、1より大きいなら1を設定する
	 */
	public void ajustRGB() {
		//赤を0～1の数値に変換する
		float tmp = (rgb[0] - blackRGB[0]) * kr;

		if (tmp < 0) {
			tmp = 0;
		} else if (tmp > 1) {
			tmp = 1;
		}
		rgb[0] = tmp;

		//緑を0～1の数値に変換する
		tmp = (rgb[1] - blackRGB[1]) * kg;
		if (tmp < 0) {
			tmp = 0;
		} else if (tmp > 1) {
			tmp = 1;
		}
		rgb[1] = tmp;

		//青を0～1の数値に変換する
		tmp = (rgb[2] - blackRGB[2]) * kb;
		if (tmp < 0) {
			tmp = 0;
		} else if (tmp > 1) {
			tmp = 1;
		}
		rgb[2] = tmp;

	}

}
