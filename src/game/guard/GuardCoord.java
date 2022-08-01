package game.guard;

import body.Body;

/**
 * 座標指定による走行動作停止条件
 * <P>相対座標系の場合、判定開始時の初回の自己位置を原点座標とする
 * @author 尾角 武俊
 */
public class GuardCoord extends Guard {

	private final int COORD_KIND_ABSOLUTE = 0;	//絶対座標系
	private final int COORD_KIND_RELATIVE = 1;	//相対座標系

	private int coordKind;							//座標系種別(0：絶対座標系 / 1:相対座標系)
	private double coordX;						//X座標
	private double coordY;						//Y座標
	private double marginX;						//X座標マージン
	private double marginY;						//Y座標マージン
	private double originCoordX;					//原点X座標（相対座標系で使用）
	private double originCoordY;					//原点Y座標（相対座標系で使用）

    /**
     * コンストラクタ
     * @param 座標系種別(0：絶対座標系 / 1:相対座標系)
     * @param X座標
     * @param Y座標
     * @param X座標マージン
     * @param Y座標マージン
     *
     */
	public GuardCoord(int coordKind, double coordX, double coordY, double marginX, double marginY) {
		this.coordKind	= coordKind;
		this.coordX = coordX;
		this.coordY = coordY;
		this.marginX = marginX;
		this.marginY = marginY;
		this.originCoordX = 0.0;
		this.originCoordY = 0.0;

		//クラス名を設定する
		name = "GuardCoord";
	}

	@Override
	public boolean judge() {

		double tmpUnderX = coordX - marginX;	//X座標範囲の下限値
		double tmpUpperX = coordX + marginX;	//X座標範囲の上限値
		double tmpUnderY = coordY - marginY;	//Y座標範囲の下限値
		double tmpUpperY = coordY + marginY;	//Y座標範囲の上限値
		double tmpX;
		double tmpY;

		//絶対座標系での判定の場合
		if(coordKind == COORD_KIND_ABSOLUTE) {
			tmpX = Body.selfPos.getXCoord();
			tmpY = Body.selfPos.getYCoord();
		//相対座標系での判定の場合
		} else {
			//初回判定の場合、現在位置を原点座標とする
			if(originCoordX == 0.0 & originCoordY == 0.0) {
				originCoordX = Body.selfPos.getXCoord();
				originCoordY = Body.selfPos.getYCoord();
				return false;
			} else {
				//現在の自己位置を原点座標から見た座標に変換する
				tmpX = Body.selfPos.getXCoord() - originCoordX;
				tmpY = Body.selfPos.getYCoord() - originCoordY;
			}
		}

		//自己位置のX座標、Y座標が指定範囲以内の場合
		if((tmpX >= tmpUnderX) & (tmpX <= tmpUpperX) &
		    (tmpY >= tmpUnderY) & (tmpY <= tmpUpperY)) {
			return true;
		} else {
			return false;
		}

	}
}
