package game.activity;

import body.Body;

/**
 * ゲーム攻略開始待ち動作クラス
 * @author 尾角 武俊
 *
 */
public class ActivityGameStartWait extends Activity{

    /**
     * コンストラクタ
     */
	public ActivityGameStartWait() {
		super.name = "ActivityGameStartWait";
	}

	/**
	 * 継続動作を実行する
	 * 状態にいる間、継続して実行される動作
	 * UMLステートマシン図のdoアクティビティ
	 */
	@Override
	public void doActivity() {
		//ゲーム攻略準備完了判定フラグがfalseの場合（ゲーム攻略用のシナリオが未作成の場合）
		if(!Body.paraFileMgt.getGameScenarioReady()) {
			//走行体を停止する
			Body.control.setForward(0.0f);
			Body.control.setTurn(0.0f);
		}
	}

}
