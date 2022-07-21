package game.SelfPosition;

import body.Body;

/**
 * 自己位置推定計算処理クラス
 * 自己位置推定する際の計算処理を記載する
 * <P> 参考サイト：https://qiita.com/ryosuke1212/items/591213808ce9047460b7
 * @author 尾角 武俊
 */
public class CalcSelfPosition {

	private double beforeX;			//移動前のX座標
	private double beforeY;			//移動前のY座標
	private double beforeRadian;		//移動前の回転角度（ラジアン）
	private double afterX;			//移動後のX座標
	private double afterY;			//移動後のY座標
	private double afterRadian;		//移動後の回転角度（ラジアン）
	private double accumDistance;		//積算移動距離
	private double distanceL;			//左車輪の移動距離
	private double distanceR;			//右車輪の移動距離
	private double distance;			//走行体の移動距離
	private double beforeAngleL;		//左車輪の回転角度過去値
	private double beforeAngleR;		//右車輪の回転角度過去値
	private double odometryX;			//オドメトリ計算により算出したX座標の変位値
	private double odometryY;			//オドメトリ計算により算出したY座標の変位値
	private double odometryRadian;	//オドメトリ計算により算出した回転角度の変位値（ラジアン）

    /**
     * コンストラクタ
     */
	public CalcSelfPosition() {
		//各変数値を初期化する
		//自己位置推定を開始する位置（走行体が走行を開始する位置）を「原点（0，0）」として走行体の座標を積算する
		//自己位置推定を開始する位置（走行体が走行を開始する方向）を「90°」として走行体の回転角度（向き）を積算する
		//右旋回を正転とする
		beforeX = 0.0;
		beforeY = 0.0;
		beforeRadian = Math.PI / 2;		//90°をラジアン値で設定
		afterX = 0.0;
		afterY = 0.0;
		afterRadian = Math.PI / 2;		//90°をラジアン値で設定
		distanceL = 0.0;
		distanceR = 0.0;
		distance = 0.0;
		accumDistance = 0.0;
		odometryX = 0.0;
		odometryY = 0.0;
		odometryRadian = 0.0;
		beforeAngleL = Body.measure.getLeftAnglePosition();	//左車輪の回転角度現在値を取得する
		beforeAngleR = Body.measure.getRightAnglePosition();	//右車輪の回転角度現在値を取得する
	}

    /**
     * 更新する
     */
	public void update() {
		calcOdometry();					//オドメトリ計算処理を呼び出す
		updateWorldPositionCoord();		//ワールド座標系を更新する
	}

    /**
     * オドメトリ計算処理にてEV3の変位を算出する
     */
	private void calcOdometry() {

		double tmpOdometryX;
		double tmpOdometryY;
		double tmpOdometryRadian;
		double tmpDistance;

		//左車輪の回転角度現在値を取得する
		double afterAngleL = Body.measure.getLeftAnglePosition();
		//右車輪の回転角度現在値を取得する
		double afterAngleR = Body.measure.getRightAnglePosition();

		//左車輪の移動距離を算出する（車輪の円周 / 360 * (回転角度現在値 - 回転角度過去値)）
		distanceL = (Body.CIRCLE / 360) * (afterAngleL - beforeAngleL);
		//右車輪の移動距離を算出する（車輪の円周 / 360 * (回転角度現在値 - 回転角度過去値)）
		distanceR = (Body.CIRCLE / 360) * (afterAngleR - beforeAngleR);

		//両方の車輪の移動量が同じ場合（直進している場合）
		if(distanceL == distanceR) {
			tmpOdometryX = distanceL;
			tmpOdometryY = 0;
			tmpOdometryRadian = 0;
			tmpDistance = distanceL;
		} else {

			//回転角度(ラジアン)を算出する
			tmpOdometryRadian = (distanceR - distanceL) / Body.TREAD;
			//走行体の移動距離を算出する
			tmpDistance = ((distanceR + distanceL) / (distanceR - distanceL)) * (Body.TREAD / 2);	//円弧の半径
			tmpDistance = tmpDistance * tmpOdometryRadian;											//円弧の半径 * 回転角度(ラジアン)

			//各座標の移動量を算出する
			tmpOdometryX = tmpDistance * Math.cos(0.5 * tmpOdometryRadian);
			tmpOdometryY = tmpDistance * Math.sin(0.5 * tmpOdometryRadian);
		}

		//オドメトリの変位値を設定する
		odometryX =tmpOdometryX;
		odometryY = tmpOdometryY;
		odometryRadian = tmpOdometryRadian;

		//走行体の移動距離値を設定する
		distance = tmpDistance;

		//左右車輪の回転角度を更新する
		beforeAngleL = afterAngleL;
		beforeAngleR = afterAngleR;

		//積算距離を更新する(バック走行により移動距離がマイナスになる場合でも、加算すること)
		accumDistance = accumDistance + Math.abs(tmpDistance);
	}

    /**
     * ワールド座標系及び回転角度を更新する
     */
	private void updateWorldPositionCoord() {

		//移動後の座標系及び回転角度を更新する
		afterX = beforeX + Math.cos(beforeRadian) * odometryX - Math.sin(beforeRadian) * odometryY;
		afterY = beforeY + Math.sin(beforeRadian) * odometryX + Math.cos(beforeRadian) * odometryY;
		afterRadian = beforeRadian + odometryRadian;

		//移動前の座標系及び回転角度を更新する
		beforeX = afterX;
		beforeY = afterY;
		beforeRadian = afterRadian;

	}

    /**
     * ラジアンを角度（°）に変換する
     * @param radian	ラジアン値
     * @return 変換後の角度（°）
     */
	private double changeRadianToAngle(double radian) {
		//ラジアンを角度に変換する
		double angle = radian * (180 / Math.PI);
		//360°単位に補正する
		angle = angle % 360;
		//マイナスの角度(逆回転)の場合、正回転の値に補正する
		if(angle < 0) angle = angle + 360;

		return angle;
	}


	//************* タスク周期間の移動距離のgetter() *************

    /**
     * 左車輪の移動距離を取得する(タスク実行周期(10m)単位の差分値)
     * @return 左車輪の移動距離（mm）
     */
	public double getLeftDistnce() {
		return distanceL;
	}

	/**
     * 右車輪の移動距離を取得する(タスク実行周期(10m)単位の差分値)
     * @return 右車輪の移動距離（mm）
     */
	public double getRightDistnce() {
		return distanceR;
	}

	/**
     * 走行体の移動距離を取得する(タスク実行周期(10m)単位の差分値)
     * @return 走行体の移動距離（mm）
     */
	public double getDistnce() {
		return distance;
	}

	//************* タスク周期間の回転角度のgetter() *************

    /**
     * 左車輪の回転角度過去値を取得する(タスク実行周期(10m)単位の差分値)
     * @return 左車輪の回転角度過去値
     */
	public double getLeftBeforeAngle() {
		return beforeAngleL;
	}

    /**
     * 右車輪の回転角度過去値を取得する(タスク実行周期(10m)単位の差分値)
     * @return 右車輪の回転角度過去値
     */
	public double getRightBeforeAngle() {
		return beforeAngleR;
	}

	//************* オドメトリ変位のgetter() *************

    /**
     * オドメトリ計算により算出した回転角度の変位値
     * @return 回転角度の変位値(ラジアン)
     */
	public double getOdometryRadian() {
		return odometryRadian;
	}

	/**
     * オドメトリ計算により算出した回転角度の変位値
     * @return 回転角度の変位値(ラジアンを角度（360°単位）に変換)
     */
	public double getOdometryRadianToAngle() {
		return changeRadianToAngle(odometryRadian);
	}

    /**
     * オドメトリ計算により算出したX座標の変位値
     * @return X座標の変位値
     */
	public double getOdometryX() {
		return odometryX;
	}

    /**
     * オドメトリ計算により算出したY座標の変位値
     * @return Y座標の変位値
     */
	public double getOdometryY() {
		return odometryY;
	}

	//************* ワールド座標系のgetter() *************

	/**
     * 走行体の現在位置のX座標を取得する
     * @return 走行体の現在位置のX座標
     */
	public double getXCoord() {
		return afterX;
	}

	/**
     * 走行体の現在位置のY座標を取得する
     * @return 走行体の現在位置のY座標
     */
	public double getYCoord() {
		return afterY;
	}

	/**
     * 走行体の積算移動距離を取得する
     * @return 走行体の積算移動距離（mm）
     */
	public double getAccumDistance() {
		return accumDistance;
	}

	/**
     * 走行体の移動前の回転角度を取得する
     * @return 走行体の移動前の回転角度(ラジアン)
     */
	public double getBeforeRadian() {
		return beforeRadian;

	}

	/**
     * 走行体の移動前の回転角度を取得する
     * @return 走行体の移動前の回転角度(ラジアンを角度（360°単位）に変換)
     */
	public double getBeforeRadianToAngle() {
		return changeRadianToAngle(beforeRadian);

	}

	/**
     * 走行体の移動後の回転角度を取得する
     * @return 走行体の移動後の回転角度(ラジアン)
     */
	public double getAfterRadian() {
		return afterRadian;
	}

	/**
     * 走行体の移動後の回転角度を取得する
     * @return 走行体の移動後の回転角度(ラジアンを角度（360°単位）に変換)
     */
	public double getAfterRadianToAngle() {
		return changeRadianToAngle(afterRadian);
	}

}
