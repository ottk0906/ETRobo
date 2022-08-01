package body.control;

import body.Body;
import game.Game;
import game.state.StateRun;

/**
 * 制御クラス
 * デザインパターンのFacadeパターンを採用
 *
 */
public class Control {
	/** 車輪制御 */
	ControlWheel controlWheel;
	/** アーム制御 */
	ControlArm controlArm;
	/** カラー制御 */
	ControlColor controlColor;

    /** 競技 */
    private Game game;
    /** 目標速度(mm/秒) */
	private float forward;
	/** 目標回転角速度(度/秒) */
	private float turn;
	/** 目標角度 */
	private float targetDegrees;
	/** アーム回転角速度 */
	private float armRotationSpeed;
	/** アームモード */
	private int armMode;

	/**
	 * コンストラクタ
	 * @param controlWheel 車輪制御
	 * @param controlArm 	アーム制御
	 * @param controlColor カラー制御
	 */
	public Control(ControlWheel controlWheel, ControlArm controlArm, ControlColor controlColor) {
		this.controlWheel = controlWheel;
		this.controlArm = controlArm;
		this.controlColor = controlColor;
	}

	/**
	 * 制御する
	 * 目標速度(mm/秒)と目標回転角速度(度/秒)から
	 * 左右モータの角速度(度/秒)を計算して設定する
	 */
	public void run() {
		turn = (float) Math.toRadians(turn);

		float leftRotationSpeed = (float) Math.toDegrees((2.0f * forward + Body.TREAD * turn) / Body.DIAMETER);
		float rightRotationSpeed = (float) Math.toDegrees((2.0f * forward - Body.TREAD * turn) / Body.DIAMETER);

		controlWheel.setLeftRotationSpeed(leftRotationSpeed);
		controlWheel.setRightRotationSpeed(rightRotationSpeed);
		controlArm.setArmRotationSpeed(this.armRotationSpeed);
		controlArm.setDegrees(this.targetDegrees);
		controlArm.setArmMode(this.armMode);

		controlWheel.run();
		//走行中の場合のみ、
        if (game.getStatus() instanceof StateRun) {
			controlArm.run();
			controlColor.run();
        }
	}

    /**
     * gameクラスオブジェクトを設定する
     * @param	game	gameクラスのインスタンス
     */
	public void setGameInstance(Game game) {
		this.game = game;
	}

	/**
	 * 目標速度(mm/秒)を設定する
	 * @param forward 目標速度(mm/秒)
	 */
	public void setForward(float forward) {
		this.forward = forward;
	}

	/**
	 * 目標回転角速度(度/秒)を設定する
	 * @param turn 目標回転角速度(度/秒)
	 */
	public void setTurn(float turn) {
		this.turn = turn;
	}

	/**
	 * 目標角度を設定する
	 * @param targetDegrees 目標角度
	 */
	public void setDegrees(float targetDegrees) {
		this.targetDegrees = targetDegrees;
	}

	/**
	 * アーム回転角速度を設定する
	 * @param armRotationSpeed アーム回転角速度
	 */
	public void setArmRotationSpeed(float armRotationSpeed) {
		this.armRotationSpeed = armRotationSpeed;
	}

	/**
	 * アームのモードを設定する。0ならPIDでの制御、1なら単純な速度での制御
	 * @param armMode アームのモード
	 */
	public void setArmMode(int armMode) {
		this.armMode = armMode;
	}

}
