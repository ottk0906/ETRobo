package game.activity;

import body.Body;
import lejos.hardware.lcd.LCD;

/**
 * 通常走行(加減速)動作クラス
 * @author 尾角 武俊
 *
 */
public class ActivityRunAccele extends Activity  {

	/** 目標速度(mm/秒) */
	protected float forward;
	/** 目標回転角速度(度/秒) */
	protected float turn;
	/** 加減速範囲(距離) */
	protected float distance;
	/** 動作開始時の送応対の速度 */
	private float beforeForward;

	/**
	 * コンストラクタ
	 * @param forward	目標速度(mm/秒)
	 * @param turn		目標回転角速度(度/秒)
	 * @param distance	加減速範囲(距離)
	 */
	public ActivityRunAccele(float forward, float turn, float distance) {
		super.name = "ActivityRunAccele";
		this.forward = forward;
		this.turn = turn;
		this.distance = distance;
	}

	/**
	 * コンストラクタ
	 * @param forward		目標速度(mm/秒)
	 * @param turn			目標回転角速度(度/秒)
	 * @param distance	加減速範囲(距離)
	 * @param activityName	アクティビティ名
	 */
	public ActivityRunAccele(float forward, float turn, String activityName) {
		super.name = activityName;
		this.forward = forward;
		this.turn = turn;
		this.distance = distance;
	}


	/**
	 * 継続動作を実行する
	 * 目標速度と目標回転角速度を設定する
	 */
	@Override
	public void doActivity() {
		Body.control.setForward(forward);
		Body.control.setTurn(turn);
		beforeForward = Body.control.getForward();

		LCD.drawString(String.valueOf(beforeForward), 0, 2);


	}

}
