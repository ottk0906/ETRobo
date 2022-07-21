package log;

import body.measure.Measure.Color;

/**
 * HSVログデータクラス
 * @author 原田　寛大
 */
public class LogHSVData {
	/** タスク呼び出し回数 */
	private int count;
	/** 色相:hue, 彩度:saturation, 明度:value */
	private float hue,saturation,value;
	/** 判定された色 */
	private Color color;

	public LogHSVData(int count,float hue,float saturation,float value,Color color) {
		this.count = count;
		this.hue = hue;
		this.saturation = saturation;
		this.value = value;
		this.color = color;
	}

	/**
     * オブジェクトの文字列表現を取得する
     */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(count);
		sb.append(",");
		sb.append(hue);
		sb.append(",");
		sb.append(saturation);
		sb.append(",");
		sb.append(value);
		sb.append(",");
		sb.append(color);
		sb.append("\r\n");

		return sb.toString();
	}

}
