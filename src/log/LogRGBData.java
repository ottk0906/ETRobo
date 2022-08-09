package log;

/**
 * RGBログデータクラス
 * @author 原田　寛大
 */
public class LogRGBData extends LogData {
	/** タスク呼び出し回数 */
	private int count;
	/** red:赤,green:緑,blue:青 */
	private float red,green,blue;
	private int elapsedTime;			//経過時間(走行体起動時からの経過時間(ms))

    /**
     * コンストラクタ
     * @param count		連番
     * @param red			赤の値
     * @param green		緑の値
     * @param blue			青の値
     * @param elapsedTime	経過時間(走行体起動時からの経過時間(ms))
     */
	public LogRGBData(int count, float red, float green, float blue, int elapsedTime) {
		this.count = count;
		this.red = red;
		this.green = green;
		this.blue = blue;
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
		sb.append(red);
		sb.append(",");
		sb.append(green);
		sb.append(",");
		sb.append(blue);
        sb.append(",");
        sb.append(elapsedTime);
		sb.append("\r\n");

		return sb.toString();
	}

}
