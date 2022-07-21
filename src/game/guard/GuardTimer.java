package game.guard;

import body.Body;

/**
 * 指定時間での動作停止条件
 * @author 尾角 武俊
 */
public class GuardTimer extends Guard {

	private int startTime;		//計測開始時のタイマー値(ms単位)
	private int targetTime;	//目標時間(ms単位)

    /**
     * コンストラクタ
     * @param 目標時間(ms単位)
     */
	public GuardTimer(int targetTime) {
		//目標時間を設定する(ms単位)
		this.targetTime = targetTime;
		//計測開始時のタイマー値を初期化する
		this.startTime = 0;
		//クラス名を設定する
		name = "GuardTimer";
	}

    /**
     * 指定時間による走行動作停止を判定する
     * @return	True : 走行停止 / False ： 走行中
     */
	@Override
	public boolean judge() {
		//経過時間を取得する
		int tmpTime = Body.stopwatch.elapsed();

		//初回の判定処理開始時点から計測を開始する
		if(startTime == 0) {
			//計測開始時のタイマー値を設定する
			this.startTime = tmpTime;
			return false;
		} else {
			//「現在のタイマー値」と「計測開始時のタイマー値」の差分値が目標時間以上になった場合に走行停止と判定する
			if (tmpTime - startTime >= targetTime) {
				return true;
			} else {
				return false;
			}
		}
	}

}
