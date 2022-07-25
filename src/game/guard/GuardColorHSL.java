package game.guard;

import body.Body;

/**
 * 色指定による走行動作停止条件(HSLで判定)
 * @author 尾角 武俊
 */
public class GuardColorHSL extends Guard {

	private int stopColor;					//停止判定色
	private int consecutivelyJudgeNum;		//連続判定回数
	private int judgeNum;					//判定回数

    /**
     * コンストラクタ
     * @param color 停止判定色
     */
	public GuardColorHSL(int stopColor, int consecutivelyJudgeNum) {
		this.stopColor = stopColor;
		this.consecutivelyJudgeNum = consecutivelyJudgeNum;
		judgeNum = 0;
		name = "GuardColorHSL";
	}

    /**
     * 色指定による走行動作停止を判定する
     * @return	True : 走行停止 / False ： 走行中
     */
	@Override
	public boolean judge() {
		//指定した色を検知した場合
		if(Body.measure.getColorHSL().getInt() == stopColor) {
			//判定回数をカウントアップする
			judgeNum++;
		} else {
			//判定回数を初期化する
			judgeNum = 0;
		}

		//連続判定回数分、指定した色を検知したら条件満了と判定する
		if(judgeNum >= consecutivelyJudgeNum) {
			return true;
		} else {
			return false;
		}
	}
}
