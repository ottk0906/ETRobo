package game.activity;

import body.Body;

/**
 * PID走行クラス
 *@author 原田　寛大
 */
public class ActivityRunPID extends ActivityRun {
	/** 処理周期 */
	private float DELTA_T = 0.010f;
	/** 偏差(前回の偏差、今回の偏差) */
	private float kago[] = new float[2];
	/** 平均偏差/秒 */
	private float integral;

	/** 比例係数 */
	private final float KP;
	/** 積分係数 */
	private final float KI;
	/** 微分係数 */
	private final float KD;
	/** ライン走行のエッジ */
	private int edge;
	/** ライン走行のエッジ指定 */
	private final int RIGHT_EDGE = 0;
	private final int LEFT_EDGE = 1;

	public ActivityRunPID(float forward, float turn, float KP, float KI, float KD, int edge) {
		super(forward, turn, "ActivityRunPID");

		this.KP = KP;
		this.KI = KI;
		this.KD = KD;
		this.edge = edge;
	}

	/**
	 * 継続動作を実行する
	 * 路面明度と目標明度を比較して目標回転角速度を計算し、
	 * 目標速度と目標回転角速度を設定する
	 */
	@Override
	public void doActivity() {
		float p, i, d;
		// 路面明度を取得する
		float brightness = Body.measure.getValue();

		// 目標明度を取得する
		float target = Body.measure.getTarget();

		//操作量を計算する
		kago[0] = kago[1];
		kago[1] = target - brightness;
		integral += (kago[1] + kago[0]) / 2.0f * DELTA_T;

		p = KP * kago[1];
		i = KI * integral;
		d = KD * (kago[1] - kago[0]) / DELTA_T;

		turn = p + i + d;

		//デフォルトは右エッジの走行のため、左エッジの場合は、turn値を反転する
		if(edge == LEFT_EDGE) {
			turn = turn * -1.0f;
		}

		// 速度を設定する
		Body.control.setForward(forward);
		Body.control.setTurn(turn);
	}
}