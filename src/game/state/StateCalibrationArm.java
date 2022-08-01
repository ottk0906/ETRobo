package game.state;

import game.Game;

/**
 * アーム角度キャリブレーション状態クラス
 * @author 駒井
 *
 */
public class StateCalibrationArm extends State {
	/** インスタンス */
	private static StateCalibrationArm instance = new StateCalibrationArm();

	/** コンストラクタ */
	private StateCalibrationArm() {
		name = "CalibrationArm";
	}

	/**
	 * 競技状態を遷移する
	 * @param game 競技
	 */
	@Override
	public void changeState(Game game) {
		game.changeState(this, StateCalibrationWhite.getInstance());
	}

	/**
	 * インスタンスを取得する
	 * @return インスタンス
	 */
	public static StateCalibrationArm getInstance() {
		return instance;
	}

}
