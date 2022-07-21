package game.guard;

import body.Body;

/**
 * 旋回動作停止条件
 * @author 尾角 武俊
 */
public class GuardTurn extends Guard {

	private double startRadian;	//計測開始時の積算回転角度（ラジアン）
	private double moveRadian;	//目標回転角度（ラジアン）

    /**
     * コンストラクタ
     * @param moveAngle	目標旋回角度(360°単位)
     */
	public GuardTurn(double moveAngle) {
		//走行体の目標回転角度を設定する（ラジアン）
		this.moveRadian = moveAngle * (Math.PI / 180);
		//計測開始時の積算回転角度を初期化する
		this.startRadian = 0.0;
		//クラス名を設定する
		name = "GuardTurn";
	}

    /**
     * 回転停止を判定する
     * @return	True : 回転停止 / False ： 回転中
     */
	@Override
	public boolean judge() {
		//現在の走行体の積算旋回角度を取得する
		double tmpRadian = Body.selfPos.getAfterRadian();

		//初回の判定処理開始時点から計測を開始する
		if(startRadian == 0.0) {
			//計測開始時の積算回転角度を設定する
			this.startRadian = tmpRadian;
			return false;
		} else {
			//「現在の積算回転角度」と「計測開始時の積算回転角度」の差分値が目標回転角度以上になった場合に回転停止と判定する
			if (Math.abs(tmpRadian - startRadian) >= moveRadian) {
				return true;
			} else {
				return false;
			}
		}
	}

}
