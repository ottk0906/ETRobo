package log;

/**
 * RGBログデータクラス
 * @author 原田　寛大
 */
public class LogRGBData {
	/** タスク呼び出し回数 */
	private int count;
	/** red:赤,green:緑,blue:青 */
	private float red,green,blue;

	public LogRGBData(int count,float red,float green,float blue) {
		this.count = count;
		this.red = red;
		this.green = green;
		this.blue = blue;
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
		sb.append("\r\n");

		return sb.toString();
	}

}
