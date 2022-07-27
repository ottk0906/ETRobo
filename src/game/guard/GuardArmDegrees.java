package game.guard;

import body.Body;
/**
 * 角度遷移条件
 * @author 駒井
 */
public class GuardArmDegrees extends Guard {

	private float targetDegrees; 			//目標角度
	private float targetDegreesMargin;	//目標角度に対してのマージン

	/**
	 * コンストラクタ
	 * @param targetDegrees	目標角度
	 */
	public GuardArmDegrees(float targetDegrees, float targetDegreesMargin) {
		//アームモータの目標角度を設定する
		this.targetDegrees = targetDegrees;
		//目標角度に対してのマージンを設定する
		this.targetDegreesMargin = targetDegreesMargin;
		name = "GuardDegrees";
	}

	/**
	 * 回転停止を判定する
	 * @return	true : 回転停止 / false ： 回転中
	 */
	@Override
	public boolean judge() {
		//現在の走行体の角度を取得する
		float tmpDegrees = Body.measure.getDegrees();
		//目標角度に足してのマージンの設定
		return (tmpDegrees >= targetDegrees - targetDegreesMargin && tmpDegrees <= targetDegrees + targetDegreesMargin);
	}
}
