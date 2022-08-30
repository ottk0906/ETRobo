package game.activity;

import body.Body;

/**
 * アーム角度キャリブレーションクラス
 * @author 駒井
 *
 */
public class ActivityCalibrationArm extends Activity {

	/**  アームの目標角度 */
	private float targetDegrees;

	/**
	 * コンストラクタ
	 * @param targetDegrees アームの目標角度
	 */
	public ActivityCalibrationArm(float targetDegrees) {
		super.name = "ActivityCalibrationArm";
		this.targetDegrees = targetDegrees;
	}

	/**
	 * 継続動作を実行する
	 * アームの目標角度を渡す。
	 */
	@Override
	public void doActivity() {
		Body.control.setDegrees(targetDegrees);
	}

	/**
	 * 後動作を実行する
	 * 現在の角度を0°と設定し0°を渡す。
	 */
	public void exitAction() {
		Body.measure.resetArmPosition();
		Body.control.setDegrees(0.0f);
	}
}
