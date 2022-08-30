package log;

/**
 * ログデータクラス
 * @author 尾角 武俊
 *
 */
public class LogDataCourse extends LogData {
    /** タスク呼出回数 */
    private int count;
    /** 競技状態名 */
    private String statusName;
    /** 路面色相　*/
    private float hue;
    /** 路面彩度　*/
    private float saturation;
    /** 路面明度　*/
    private float value;
    /** 目標明度　*/
    private float target;
    /** 左モータの角速度(度/秒) */
    private float leftRotationSpeed;
    /** 右モータの角速度(度/秒) */
    private float rightRotationSpeed;
	private int elapsedTime;			//経過時間(走行体起動時からの経過時間(ms))

    /**
     * コンストラクタ
     * @param count タスクの呼び出し回数
     * @param status 競技状態名
     * @param hue 路面色相
     * @param saturation 路面彩度
     * @param brightness 路面明度
     * @param turget 目標明度
     * @param leftRotationSpeed 左モータの角速度(度/秒)
     * @param rightRotationSpeed 右モータの角速度(度/秒)
     * @param elapsedTime	経過時間(走行体起動時からの経過時間(ms))
     */
    public LogDataCourse(int count, String statusName, float hue, float saturation, float value, float target, float leftRotationSpeed, float rightRotationSpeed, int elapsedTime){
        this.count = count;
        this.statusName = statusName;
        this.hue = hue;
        this.saturation = saturation;
        this.value = value;
        this.target = target;
        this.leftRotationSpeed = leftRotationSpeed;
        this.rightRotationSpeed = rightRotationSpeed;
		this.elapsedTime = elapsedTime;
    }

	/**
     * オブジェクトの文字列表現を取得する
     */
	@Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(count);
        sb.append(",");
        sb.append(statusName);
        sb.append(",");
        sb.append(hue);
        sb.append(",");
        sb.append(saturation);
        sb.append(",");
        sb.append(value);
        sb.append(",");
        sb.append(target);
        sb.append(",");
        sb.append(leftRotationSpeed);
        sb.append(",");
        sb.append(rightRotationSpeed);
        sb.append(",");
        sb.append(elapsedTime);
        sb.append("\r\n");

		return sb.toString();
	}

}
