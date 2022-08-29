package game.activity;

import body.Body;

/**
 * 白明度キャリブレーション動作クラス
 * @author 後藤　聡文
 *
 */
public class ActivityCalibrationWhite extends Activity {

	/** RGBの各最大値 */
	private float[] maxRGB;

	/**
	 * コンストラクタ
	 */
	public ActivityCalibrationWhite() {
		super.name = "ActivityCalibrationWhite";
		this.maxRGB = new float[3];
	}

	/**
	 * 継続動作を実行する
	 */
	@Override
	public void doActivity() {
		float[] rgb = Body.measure.getRGB();
		if (rgb[0] > maxRGB[0]) {
			maxRGB[0] = rgb[0];
		}
		if (rgb[1] > maxRGB[1]) {
			maxRGB[1] = rgb[1];
		}
		if (rgb[2] > maxRGB[2]) {
			maxRGB[2] = rgb[2];
		}
	}

	/**
	 * 後動作を実行する
	 * 白明度を設定する
	 */
	@Override
	public void exitAction() {
		//白RGBを設定する
		Body.measure.setWhiteRGB(maxRGB);

		//白RGB値の中で最小の数値を設定する
		float tmp = maxRGB[0];
		for (int i = 1; i < maxRGB.length; i++) {
			if (tmp > maxRGB[i]) {
				tmp = maxRGB[i];
			}
		}
		Body.measure.setWhite(tmp);
	}

}
