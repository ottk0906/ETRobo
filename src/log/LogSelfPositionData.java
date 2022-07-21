package log;

/**
 * 自己位置推定ログデータクラス
 * @author 尾角 武俊
 */
public class LogSelfPositionData {

	private double currentX;			//移動後のX座標（計算後の自己位置）
	private double currentY;			//移動後のY座標（計算後の自己位置）
	private double currentAngle;		//移動後の回転角度
	private double accumDistance;		//積算移動距離
	private double odometryX;			//オドメトリ計算により算出したX座標の変位値
	private double odometryY;			//オドメトリ計算により算出したY座標の変位値
	private double odometryAngle;		//オドメトリ計算により算出した回転角度の変位値
	private double distanceL;			//左車輪の移動距離
	private double distanceR;			//右車輪の移動距離
	private double distance;			//走行体の移動距離
	private double beforeAngleL;		//左車輪の回転角度過去値
	private double beforeAngleR;		//右車輪の回転角度過去値
	private int elapsedTime;			//経過時間(走行体起動時からの経過時間(ms))

    /**
     * コンストラクタ
     * @param currentX			X座標
     * @param currentY			Y座標
     * @param currentAngle		回転角度
     * @param accumDistance	積算移動距離
     * @param odometryX		オドメトリ計算により算出したX座標の変位値
     * @param odometryY		オドメトリ計算により算出したY座標の変位値
     * @param odometryAngle	オドメトリ計算により算出した回転角度の変位値
     * @param distanceL		左車輪の移動距離
     * @param distanceR		右車輪の移動距離
     * @param distance			走行体の移動距離
     * @param beforeAngleL		左車輪の回転角度過去値
     * @param beforeAngleR		右車輪の回転角度過去値
     * @param elapsedTime		経過時間(走行体起動時からの経過時間(ms))
     */
	public LogSelfPositionData(double currentX, double currentY, double currentAngle,
								double accumDistance, double odometryX, double odometryY,
								double odometryAngle, double distanceL, double distanceR,
								double distance, double beforeAngleL, double beforeAngleR, int elapsedTime) {

		this.currentX = currentX;
		this.currentY = currentY;
		this.currentAngle = currentAngle;
		this.accumDistance = accumDistance;
		this.odometryX = odometryX;
		this.odometryY = odometryY;
		this.odometryAngle = odometryAngle;
		this.distanceL = distanceL;
		this.distanceR = distanceR;
		this.distance = distance;
		this.beforeAngleL = beforeAngleL;
		this.beforeAngleR = beforeAngleR;
		this.elapsedTime = elapsedTime;

	}

    /**
     * オブジェクトの文字列表現を取得する
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(currentX).append(",");
        sb.append(currentY).append(",");
        sb.append(currentAngle).append(",");
        sb.append(accumDistance).append(",");
        sb.append(odometryX).append(",");
        sb.append(odometryY).append(",");
        sb.append(odometryAngle).append(",");
        sb.append(distanceL).append(",");
        sb.append(distanceR).append(",");
        sb.append(distance).append(",");
        sb.append(beforeAngleL).append(",");
        sb.append(beforeAngleR).append(",");
        sb.append(elapsedTime).append("\r\n");
        return sb.toString();
    }

}
