package game.guard;

import body.Body;

/**
 * ゲーム攻略準備完了判定条件
 * @author 尾角 武俊
 */
public class GuardGameReady extends Guard{

    /**
     * ゲーム攻略準備完了を判定する
     * @return	True : 準備完了 / False ： 準備中
     */
	@Override
	public boolean judge() {
		return Body.paraFileMgt.getGameScenarioReady();
	}

}
