package game.guard;

import body.Body;

/**
 * 指定距離による走行動作停止条件
 * @author 尾角 武俊
 */
public class GuardDistance extends Guard {

	private double startDistance;		//計測開始時の積算移動距離(mm単位)
	private double moveDistance;		//目標移動距離(mm単位)

    /**
     * コンストラクタ
     * @param 目標移動距離(mm単位)
     */
	public GuardDistance(double moveDistance) {
		//走行体の目標移動距離を設定する(mm単位)
		this.moveDistance = moveDistance;
		//走行体の積算目標移動距離を初期化する
		this.startDistance = 0.0;
		//クラス名を設定する
		name = "GuardDistance";
	}

    /**
     * 指定距離による走行動作停止を判定する
     * @return	True : 走行停止 / False ： 走行中
     */
	@Override
	public boolean judge() {
		//現在の走行体の積算移動距離を取得する
		double tmpDistance = Body.selfPos.getAccumDistance();

		//初回の判定処理開始時点から計測を開始する
		if(startDistance == 0.0) {
			//計測開始時の積算移動距離を設定する
			this.startDistance = tmpDistance;
			return false;
		} else {
			//「現在の積算移動距離」と「計測開始時の積算移動距離」の差分値が目標移動距離以上になった場合に走行停止と判定する
			if (Math.abs(tmpDistance - startDistance) >= moveDistance) {
				return true;
			} else {
				return false;
			}
		}
	}
}
