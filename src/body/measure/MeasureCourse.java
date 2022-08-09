package body.measure;

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
	/** 路面RGB（赤、緑、青） */
	private float rgb[];
	/** 路面HSV（色相、彩度、明度） */
	private float hsv[];

	/**
	 * コンストラクタ
	 * @param colorSensor カラーセンサ
	 */
	public MeasureCourse(EV3ColorSensor colorSensor) {
		this.colorSensor = colorSensor;
		sensorMode = colorSensor.getRGBMode();
		rgb = new float[sensorMode.sampleSize()];
		hsv = new float[3];

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
		// RGBを取得する
		sensorMode.fetchSample(rgb, 0);

		//RGBに補正をかける
		ajustRGB();

		// RGBをHSVに変換する
		convertRGBtoHSV(rgb);

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
	public float getHue(){
	    return hsv[0];

	    //return measureCourseHSL.getHue();

	}

	/**
	 * 路面彩度を取得する
	 * @return　路面彩度
	 */
	public float getSaturation(){
	    return hsv[1];

	    //return measureCourseHSL.getSaturation();
	}

	/**
	 * 路面明度を取得する
	 * @return　路面明度
	 */
	public float getValue() {
		return hsv[2];
	    //return measureCourseHSL.getValue();
	}

	/**
     * RGB値を取得する
     * @return rgb (Red,Green,Blue)
     */
    float[] getRGB() {
    	return rgb;
    }

	/**
	 * RGBをHSVに変換する
	 */
	private void convertRGBtoHSV(float[] rgb) {
		// rgb（赤:Red、緑:Green、青:Blue）
		float r = rgb[0];
		float g = rgb[1];
		float b = rgb[2];

		// hsv（色相:Hue、彩度:Saturation、明度:Value）
		float h, s, v;

		// rgbの最大値
		float max = (r > g) ? r : g;
		if (b > max)
			max = b;

		// rgbの最小値
		float min = (r < g) ? r : g;
		if (b < min)
			min = b;

		// rgbからhsvへ変換
		if (max == min) {
			h = -1.0f; // 未定義
		} else {
			if (max == r) {
				h = (g - b) / (max - min) * 60.0f;
			} else if (max == g) {
				h = (b - r) / (max - min) * 60.0f + 120.0f;
			} else {
				h = (r - g) / (max - min) * 60.0f + 240.0f;
			}
			if (h < 0.0f)
				h = h + 360.0f;
			if (h > 360.0f)
				h = h - 360.0f;
		}
		if (max != 0.0f) {
			s = (max - min) / max;
		} else {
			s = -1.0f; // 未定義
		}
		v = max;

		hsv[0] = h;
		hsv[1] = s;
		hsv[2] = v;
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
	 * 白RGBを設定する
	 * @param maxRGB
	 */
	public void setWhtieRGB(float[] maxRGB) {
		this.whiteRGB = maxRGB;
	}

	/**
	 * 黒RGBを設定する
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
	 * 0～1外なら前回の0～1内の数値を使用する
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
