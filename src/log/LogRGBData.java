package log;

/**
 * RGBログデータクラス
 * @author 原田　寛大
 */
public class LogRGBData extends LogData {
	/** タスク呼び出し回数 */
	private int count;

	private float rgb[];
	private float originRGB[];
	private float whiteRGB[];
	private float blackRGB[];



	private int elapsedTime;			//経過時間(走行体起動時からの経過時間(ms))

    /**
     * コンストラクタ
     * @param count		連番
     * @param red			赤の値
     * @param green		緑の値
     * @param blue			青の値
     * @param elapsedTime	経過時間(走行体起動時からの経過時間(ms))
     */
	public LogRGBData(int count, float[] rgb, float[] originRGB, float[] whiteRGB, float[] blackRGB, int elapsedTime) {
		this.count = count;
		this.rgb = rgb;
		this.originRGB = originRGB;
		this.whiteRGB = whiteRGB;
		this.blackRGB = blackRGB;
		this.elapsedTime = elapsedTime;
	}

	/**
     * オブジェクトの文字列表現を取得する
     */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(count).append(",");
		for(int iLoop = 0; iLoop < rgb.length; iLoop++ ) {
			sb.append(rgb[iLoop]).append(",");
		}
		for(int iLoop = 0; iLoop < originRGB.length; iLoop++ ) {
			sb.append(originRGB[iLoop]).append(",");
		}
		for(int iLoop = 0; iLoop < whiteRGB.length; iLoop++ ) {
			sb.append(whiteRGB[iLoop]).append(",");
		}
		for(int iLoop = 0; iLoop < blackRGB.length; iLoop++ ) {
			sb.append(blackRGB[iLoop]).append(",");
		}
        sb.append(elapsedTime);
		sb.append("\r\n");

		return sb.toString();
	}

}
