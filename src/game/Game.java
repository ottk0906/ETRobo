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

		//StateCalibrationWhite.getInstance().add(new GuardTouch(), new ActivityCalibrationWhite());
		//StateCalibrationBlack.getInstance().add(new GuardTouch(), new ActivityCalibrationBlack());
		//StateWaitStart.getInstance().add(new GuardTouch(), new ActivityRun(0, 0));
		//StateRun.getInstance().add(new GuardTouch(), new ActivityRun(200, 0));

		//StateRun.getInstance().add(new GuardTimer(2000), new ActivityRun(300, 0));
		//StateRun.getInstance().add(new GuardTimer(3000), new ActivityRun(200, 0));
		//StateRun.getInstance().add(new GuardArmThrow(30.0f), new ActivityArmThrow(50,300));
		//StateRun.getInstance().add(new GuardTimer(3000), new ActivityRun(200, 0));
		//StateRun.getInstance().add(new GuardTimer(0), new ActivityArm(0));
		//StateRun.getInstance().add(new GuardTimer(3000), new ActivityRun(100, 0));

		//StateRun.getInstance().add(new GuardColorHSV(2,3), new ActivityRun(100, 0));
		//StateRun.getInstance().add(new GuardTouch(), new ActivityRun(0, 0));

		/*
		StateRun.getInstance().add(new GuardTouch(), new ActivityAcquisitionColor());
		 */
		//StateRun.getInstance().add(new GuardDegrees(30.0f, 1.0f), new ActivityArm(30.0f));
		//StateRun.getInstance().add(new GuardTouch(), new ActivityRun(0, 0));
		//StateRun.getInstance().add(new GuardDegrees(0.0f, 1.0f), new ActivityArm(0.0f));
		//StateRun.getInstance().add(new GuardTouch(), new ActivityRun(0, 0));

		/*
		//---> Modify 2022/07/06 Harada
		StateRun.getInstance().add(new GuardTouch(), new ActivityAcquisitionColor());
		StateRun.getInstance().add(new GuardTouch(), new ActivityRun(0, 0));
		//<--- Modify 2022/07/06 Harada

		//---> Modify 2022/07/06 Harada
		 *
		StateRun.getInstance().add(new GuardTouch(), new ActivityRunPID(200, 0.0f, 1500f, 0.01f, 0.01f));
		StateRun.getInstance().add(new GuardTouch(), new ActivityRun(0, 0));
		//<--- Modify 2022/07/06 Harada

		//---> Modify 2022/07/05 Komai
		StateRun.getInstance().add(new GuardDegrees(30.0f, 1.0f), new ActivityArm(30.0f));
		StateRun.getInstance().add(new GuardTouch(), new ActivityRun(0, 0));
		StateRun.getInstance().add(new GuardDegrees(0.0f, 1.0f), new ActivityArm(0.0f));
		StateRun.getInstance().add(new GuardTouch(), new ActivityRun(0, 0));
		//<--- Modify 2022/07/05 Komai

		//---> Modify 2022/06/22 T.Okado
		//StateRun.getInstance().add(new GuardTouch(), new ActivityRunPID(200, 0.0f , 900f , 0.01f , 0.01f));
		//StateRun.getInstance().add(new GuardTouch(), new ActivityTurnTest(200.0f , 45.0f));
		//StateRun.getInstance().add(new GuardTouch(), new ActivityTurnTest(200.0f , -45.0f));
		//StateRun.getInstance().add(new GuardTouch(), new ActivityTurnTest(200.0f , 0.0f));
		//StateRun.getInstance().add(new GuardTouch(), new ActivityTurnTest(-200.0f , 0.0f));
		//StateRun.getInstance().add(new GuardTouch(), new ActivityRunOnOff(200, 100.0f));
		//<--- Modify 2022/06/22 T.Okado

		//---> Add 2022/06/29 T.Okado
		StateRun.getInstance().add(new GuardTurnStop(180.0), new ActivityTurnTest(0.0f, 200.0f));
		StateRun.getInstance().add(new GuardDistanceStop(1000), new ActivityTurnTest(200.0f, 0.0f));
		StateRun.getInstance().add(new GuardTurnStop(180.0), new ActivityTurnTest(0.0f, -200.0f));
		StateRun.getInstance().add(new GuardDistanceStop(1500), new ActivityTurnTest(200.0f, 0.0f));

		//StateRun.getInstance().add(new GuardDistanceStop(1000), new ActivityRunPID(200, 0.0f , 1500f , 0.01f , 0.01f));
		//StateRun.getInstance().add(new GuardDistanceStop(), new ActivityRunOnOff(200, 100.0f));
		//StateRun.getInstance().add(new GuardDistanceStop(), new ActivityTurnTest(200.0f , 0.0f));
		//<--- Add 2022/06/29 T.Okado

		//StateEnd.getInstance().add(new GuardTouch(), new ActivityRun(0, 0));
*/

		ScenarioMake Scenario = new ScenarioMake();
		Scenario.makeScenarioList(INIT_SCENARIO_LIST_FILE);
		Scenario.makeMoveStatus();

		//StateEnd.getInstance().add(new GuardTouch(), new ActivityRun(0, 0));

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
