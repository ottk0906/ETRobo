package game.activity;

import body.Body;

/**
 * アームブロック投擲動作クラス
 * @author 駒井
 */
public class ActivityArmThrow extends ActivityRun {
	/** アーム回転角速度 */
	private float armRotationSpeed;
	/** 目標角度 */
	private float targetDegrees;
	/** アームモード */
	private int armMode = 1;

	/**
	 * コンストラクタ
	 *
	 */
	public ActivityArmThrow(float targetDegrees, float armRotationSpeed) {
		super(0, 0);
		this.targetDegrees = targetDegrees;
		this.armRotationSpeed = armRotationSpeed;
	}

	/**
	 * 継続動作を実行する
	 * controlクラスに速度と目標角度とアームモードを渡す
	 */
	@Override
	public void doActivity() {
		Body.control.setArmRotationSpeed(armRotationSpeed);
		Body.control.setDegrees(targetDegrees);
		Body.control.setArmMode(armMode);
	}

}
