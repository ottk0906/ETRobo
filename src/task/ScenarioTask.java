package task;

import scenario.ParaFileMgt;

/**
 * 走行パラメータファイル取得タスククラス
 * @author
 *
 */
public class ScenarioTask extends Thread{

	private ParaFileMgt scenarioFileMgt;

    /**
     * コンストラクタ
     * @param scenarioFileMgt	走行パラメータファイル追加管理
     */
	public ScenarioTask(ParaFileMgt scenarioFileMgt) {
		this.scenarioFileMgt = scenarioFileMgt;
	}

    /**
     * 実行する
     */
    @Override
    public void run() {
    	//走行パラメータファイル追加管理処理を実行する
    	scenarioFileMgt.run();
    }

}
