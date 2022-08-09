package game;

import body.control.Control;
import body.measure.Measure;
import game.state.State;
import game.state.StateCalibrationWhite;
import game.state.StateEnd;
import scenario.ScenarioMake;

/**
 * 競技クラス
 *
 */
public class Game {

	private Measure measure;
	private Game game;
	private Control control;

	/** タスク呼出回数 */
	private int count = 0;

	/** 競技状態 */
	private State state;

	/** 競技が終了したか */
	private boolean isOver = false;

	/** シナリオリストファイル名(初期動作シナリオ) */
	private final String INIT_SCENARIO_LIST_FILE = "EV3_Init_ScenarioList.csv";

	/**
	 * コンストラクタ
	 */
	public Game() {

		ScenarioMake Scenario = new ScenarioMake();
		Scenario.makeScenarioList(INIT_SCENARIO_LIST_FILE);
		Scenario.makeMoveStatus();

		changeState(null, StateCalibrationWhite.getInstance());
	}

	/**
	 * 実施する
	 */
	public void run() {
		if (isOver == false) {
			count++;
			if (state instanceof StateEnd) {
				isOver = true;
			} else {
				state.doActivity(this);
			}
		}
	}

	/**
	 * タスク呼出回数を取得する
	 * @return タスク呼出回数
	 */
	public int getCount() {
		return count;
	}

	/**
	 * 競技状態を遷移する
	 * @param oldState 前状態
	 * @param newState　後状態
	 */
	public void changeState(State oldState, State newState) {
		this.state = newState;

		if (oldState != null) {
			oldState.exitAction();
		}
		if (newState != null) {
			newState.entryAction();
		}
	}

	/**
	 * 競技が終了したか
	 * @return 競技が終了した場合はtrue
	 */
	public boolean isOver() {
		return isOver;
	}

	/**
	 * 現在の競技状態を取得する
	 * @return 現在の競技状態
	 */
	public State getStatus() {
		return state;
	}

	/**
	 * オブジェクトの文字列表現を取得する
	 */
	@Override
	public String toString() {
		return state.toString();
	}

}
