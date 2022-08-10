package game.SelfPosition;

import game.Game;
import game.state.StateConquest;
import game.state.StateRun;

/**
 * 自己位置推定クラス
 * デザインパターンのSingletonパターンを採用
 * @author 尾角 武俊
 */
public final class SelfPosition {

    /** 競技 */
    private Game game;
	/** 自己位置推定計算処理 */
	private CalcSelfPosition calcSelfPos;

    /**
     * コンストラクタ
     */
	public SelfPosition(){
		calcSelfPos = new CalcSelfPosition();	//自己位置推定計算処理クラスのインスタンスを生成する
	}

    /**
     * 実施する
     */
	public void run() {

		//「ベーシックコース走行状態」、「ゲーム攻略状態」の場合、自己位置推定を実行する
        if (game.getStatus() instanceof StateRun | game.getStatus() instanceof StateConquest) {
        	//自己位置更新処理を呼び出す
        	calcSelfPos.update();
		}
	}

    /**
     * gameクラスオブジェクトを設定する
     * @param	game	gameクラスのインスタンス
     */
	public void setGameInstance(Game game) {
		this.game = game;
	}

	//************* タスク周期間の移動距離のgetter() *************

    /**
     * 左車輪の移動距離を取得する(タスク実行周期(10m)単位の差分値)
     * @return 左車輪の移動距離（mm）
     */
	public double getLeftDistnce() {
		return calcSelfPos.getLeftDistnce();
	}

	/**
     * 右車輪の移動距離を取得する(タスク実行周期(10m)単位の差分値)
     * @return 右車輪の移動距離（mm）
     */
	public double getRightDistnce() {
		return calcSelfPos.getRightDistnce();
	}

	/**
     * 走行体の移動距離を取得する(タスク実行周期(10m)単位の差分値)
     * @return 走行体の移動距離（mm）
     */
	public double getDistnce() {
		return calcSelfPos.getDistnce();
	}

	//************* タスク周期間の回転角度のgetter() *************

    /**
     * 左車輪の回転角度過去値を取得する(タスク実行周期(10m)単位の差分値)
     * @return 左車輪の回転角度過去値
     */
	public double getLeftBeforeAngle() {
		return calcSelfPos.getLeftBeforeAngle();
	}

    /**
     * 右車輪の回転角度過去値を取得する(タスク実行周期(10m)単位の差分値)
     * @return 右車輪の回転角度過去値
     */
	public double getRightBeforeAngle() {
		return calcSelfPos.getRightBeforeAngle();
	}

	//************* オドメトリ変位のgetter() *************

    /**
     * オドメトリ計算により算出した回転角度の変位値
     * @return 回転角度の変位値(ラジアン)
     */
	public double getOdometryRadian() {
		return calcSelfPos.getOdometryRadian();
	}

	/**
     * オドメトリ計算により算出した回転角度の変位値
     * @return 回転角度の変位値(ラジアンを角度（360°単位）に変換)
     */
	public double getOdometryRadianToAngle() {
		return calcSelfPos.getOdometryRadianToAngle();
	}

    /**
     * オドメトリ計算により算出したX座標の変位値
     * @return X座標の変位値
     */
	public double getOdometryX() {
		return calcSelfPos.getOdometryX();
	}

    /**
     * オドメトリ計算により算出したY座標の変位値
     * @return Y座標の変位値
     */
	public double getOdometryY() {
		return calcSelfPos.getOdometryY();
	}

	//************* ワールド座標系のgetter() *************

	/**
     * 走行体の現在位置のX座標を取得する
     * @return 走行体の現在位置のX座標
     */
	public double getXCoord() {
		return calcSelfPos.getXCoord();
	}

	/**
     * 走行体の現在位置のY座標を取得する
     * @return 走行体の現在位置のY座標
     */
	public double getYCoord() {
		return calcSelfPos.getYCoord();
	}

	/**
     * 走行体の積算移動距離を取得する
     * @return 走行体の積算移動距離（mm）
     */
	public double getAccumDistance() {
		return calcSelfPos.getAccumDistance();
	}

	/**
     * 走行体の移動前の回転角度を取得する
     * @return 走行体の移動前の回転角度(ラジアン)
     */
	public double getBeforeRadian() {
		return calcSelfPos.getBeforeRadian();

	}

	/**
     * 走行体の移動前の回転角度を取得する
     * @return 走行体の移動前の回転角度(ラジアンを角度（360°単位）に変換)
     */
	public double getBeforeRadianToAngle() {
		return calcSelfPos.getBeforeRadianToAngle();

	}

	/**
     * 走行体の移動後の回転角度を取得する
     * @return 走行体の移動後の回転角度(ラジアン)
     */
	public double getAfterRadian() {
		return calcSelfPos.getAfterRadian();
	}

	/**
     * 走行体の移動後の回転角度を取得する
     * @return 走行体の移動後の回転角度(ラジアンを角度（360°単位）に変換)
     */
	public double getAfterRadianToAngle() {
		return calcSelfPos.getAfterRadianToAngle();
	}

}
