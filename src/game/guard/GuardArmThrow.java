package game.guard;

import body.Body;

/**
 * アーム動作停止条件
 * @author 駒井
 */
public class GuardArmThrow extends Guard {
	/**  目標角度 */
	private float targetDegrees;

	/**
	 * コンストラクタ
	 * @param targetDegrees	目標角度
	 */
	public GuardArmThrow(float targetDegrees) {
		//アームモータの目標角度を設定する
		this.targetDegrees = targetDegrees;
		name = "GuardArmThrow";
	}

	/**
	 * 回転停止を判定する
	 * @return	true : 回転停止 / false ： 回転中
	 */
	@Override
	public boolean judge() {
		//現在の走行体の角度を取得する
		float tmpDegrees = Body.measure.getDegrees();
		//目標角度以上の場合に停止
		if (tmpDegrees >= targetDegrees){
			Body.control.setArmMode(0);
			return true;
		} else {
			return false;
		}
	}
}