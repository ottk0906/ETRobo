package log;

import body.measure.Measure.Color;

/**
 * HSVログデータクラス
 * @author 原田　寛大
 */
public class LogHSLData extends LogData {
	private int count;
	private float hue,saturation,lightness;
	private Color color;

	public LogHSLData(int count,float hue,float saturation,float lightness,Color color) {
		this.count = count;
		this.hue = hue;
		this.saturation = saturation;
		this.lightness = lightness;
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
		sb.append(lightness);
		sb.append(",");
		sb.append(color);
		sb.append("\r\n");

		return sb.toString();
	}

}
