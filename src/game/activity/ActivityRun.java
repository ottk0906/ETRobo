package game.activity;

import body.Body;

/**
 * 通常走行動作クラス
 * @author 後藤　聡文
 *
 */
public class ActivityRun extends Activity {

	/** 目標速度(mm/秒) */
	protected float forward;
	/** 目標回転角速度(度/秒) */
	protected float turn;

	/**
	 * コンストラクタ
	 * @param forward 目標速度(mm/秒)
	 * @param turn 目標回転角速度(度/秒)
	 */
	public ActivityRun(float forward, float turn) {
		super.name = "ActivityRun";
		this.forward = forward;
		this.turn = turn;
	}

	/**
	 * コンストラクタ
	 * @param forward		目標速度(mm/秒)
	 * @param turn			目標回転角速度(度/秒)
	 * @param activityName	アクティビティ名
	 */
	public ActivityRun(float forward, float turn, String activityName) {
		super.name = activityName;
		this.forward = forward;
		this.turn = turn;
	}


	/**
	 * 継続動作を実行する
	 * 目標速度と目標回転角速度を設定する
	 */
	@Override
	public void doActivity() {
		Body.control.setForward(forward);
		Body.control.setTurn(turn);
	}

}
