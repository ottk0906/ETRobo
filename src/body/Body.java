package body;

import body.control.Control;
import body.control.ControlArm;
import body.control.ControlWheel;
import body.measure.Measure;
import body.measure.MeasureArm;
import body.measure.MeasureCourse;
import body.measure.MeasureTouch;
import body.measure.MeasureWheel;
import game.SelfPosition.SelfPosition;
import hardware.KamogawaRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.utility.Stopwatch;

/**
 * 走行体クラス
 *
 */
public final class Body {
	/** 計測 */
	public static final Measure measure;
	/** 制御 */
	public static final Control control;
	// 自己位置推定クラス
	public static final SelfPosition selfPos;
    /** ストップウォッチクラス	*/
	public static final Stopwatch stopwatch;

	/** 車輪の半径(mm) */
	public static final float RADIUS = 50.0f;
	/** 車輪の直径(mm) */
	public static final float DIAMETER = 2.0f * RADIUS;
	/** 左右動輪の間隔(mm) */
	public static final float TREAD = 147.0f;
	/** 車輪の円周(mm) */
	public static final float CIRCLE = 320.0f;
	/** ログファイルのサフィックス値 */
	public static String logFileSuffix = "0";

	static {

		// ハードウェアの初期化
		EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S1);
		EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S2);
		KamogawaRegulatedMotor leftMotor = new KamogawaRegulatedMotor(MotorPort.C);
		KamogawaRegulatedMotor rightMotor = new KamogawaRegulatedMotor(MotorPort.B);
		KamogawaRegulatedMotor armMotor = new KamogawaRegulatedMotor(MotorPort.A);

		// 計測の初期化
		MeasureTouch measureTouch = new MeasureTouch(touchSensor);
		MeasureCourse measureCourse = new MeasureCourse(colorSensor);
		MeasureWheel measureWheel = new MeasureWheel(leftMotor, rightMotor);
		MeasureArm measureArm = new MeasureArm(armMotor);
		measure = new Measure(measureTouch, measureCourse, measureWheel, measureArm);
		// 制御の初期化
		ControlWheel controlWheel = new ControlWheel(leftMotor, rightMotor);
		ControlArm controlArm = new ControlArm(armMotor);
		control = new Control(controlWheel, controlArm);
		// 自己位置推定クラス
		selfPos = new SelfPosition();
	    //ストップウォッチクラスを生成する
	    stopwatch = new Stopwatch();
	    //ストップウォッチのカウンタをリセットする
	    stopwatch.reset();

	}

	/**
	 * コンストラクタ
	 */
	private Body() {

	}

}
