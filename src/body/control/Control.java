package body.control;

import body.Body;

/**
 * 制御クラス
 * デザインパターンのFacadeパターンを採用
 *
 */
public class Control {
	/***/
	//Body body;

	/** 車輪制御 */
	ControlWheel controlWheel;
	/** アーム制御 */
	ControlArm controlArm;
	/** 目標速度(mm/秒) */
	private float forward;
	/** 目標回転角速度(度/秒) */
	private float turn;
	/** 目標角度 */
	private float targetDegrees;

	/**
	 * コンストラクタ
	 * @param controlWheel 車輪制御
	 */
	public Control(ControlWheel controlWheel) {
		this.controlWheel = controlWheel;
	}

	/**
	 * コンストラクタ
	 * @param controlWheel 車輪制御
	 * @param controlArm アーム制御
	 */
	public Control(ControlWheel controlWheel, ControlArm controlArm) {
		this.controlWheel = controlWheel;
		this.controlArm = controlArm;
	}

	/**
	 * 制御する
	 * 目標速度(mm/秒)と目標回転角速度(度/秒)から
	 * 左右モータの角速度(度/秒)を計算して設定する
	 */
	public void run() {
		turn = (float) Math.toRadians(turn);

		float leftRotationSpeed = (float) Math.toDegrees((2.0f * forward - Body.TREAD * turn) / Body.DIAMETER);
		float rightRotationSpeed = (float) Math.toDegrees((2.0f * forward + Body.TREAD * turn) / Body.DIAMETER);

		controlWheel.setLeftRotationSpeed(leftRotationSpeed);
		controlWheel.setRightRotationSpeed(rightRotationSpeed);
		controlArm.setDegrees(this.targetDegrees);
		controlWheel.run();
		controlArm.run();
	}

	/**
	 * 目標速度(mm/秒)を設定する
	 * @param forward 目標速度(mm/秒)
	 */
	public void setForward(float forward) {
		this.forward = forward;
	}

	/**
	 * 目標回転角速度(度/秒)を設定する
	 * @param turn 目標回転角速度(度/秒)
	 */
	public void setTurn(float turn) {
		this.turn = turn;
	}

	/**
	 * 目標角度を設定する
	 * @param targetDegrees 目標角度
	 */
	public void setDegrees(float targetDegrees) {
		this.targetDegrees = targetDegrees;
	}
}
