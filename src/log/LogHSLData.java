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
	private int elapsedTime;			//経過時間(走行体起動時からの経過時間(ms))

    /**
     * コンストラクタ
     * @param count		連番
     * @param hue			色相の値
     * @param saturation	彩度の値
     * @param value		明度の値
     * @param elapsedTime	経過時間(走行体起動時からの経過時間(ms))
     */
	public LogHSLData(int count,float hue,float saturation,float lightness,Color color,int elapsedTime) {
		this.count = count;
		this.hue = hue;
		this.saturation = saturation;
		this.lightness = lightness;
		this.color = color;
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
		sb.append(hue);
		sb.append(",");
		sb.append(saturation);
		sb.append(",");
		sb.append(lightness);
		sb.append(",");
		sb.append(color);
        sb.append(",");
        sb.append(elapsedTime);
		sb.append("\r\n");

		return sb.toString();
	}

}
