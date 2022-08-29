package game.activity;

import body.Body;

/**
 * 黒明度キャリブレーション動作クラス
 * @author 後藤　聡文
 *
 */
public class ActivityCalibrationBlack extends Activity {

	/** RGBの各最小値 */
	private float[] minRGB;

	/**
	 * コンストラクタ
	 */
	public ActivityCalibrationBlack() {
		super.name = "ActivityCalibrationBlack";
		this.minRGB = new float[3];
		//最大値を設定
		minRGB[0] = 1;
		minRGB[1] = 1;
		minRGB[2] = 1;
	}

	/**
	 * 継続動作を実行する
	 */
	@Override
	public void doActivity() {
		float[] rgb = Body.measure.getRGB();
		if (rgb[0] < minRGB[0]) {
			minRGB[0] = rgb[0];
		}
		if (rgb[1] < minRGB[1]) {
			minRGB[1] = rgb[1];
		}
		if (rgb[2] < minRGB[2]) {
			minRGB[2] = rgb[2];
		}
	}

	/**
	 * 後動作を実行する
	 * 黒明度と目標明度を設定する
	 */
	@Override
	public void exitAction() {
		//黒RGB値とRGBの割合の係数を設定する
		Body.measure.setBlackRGB(minRGB);
		Body.measure.setKRKGKB();

		//黒RGB値の中で最大の数値を設定する
		float tmp = minRGB[0];
		for (int i = 1; i < minRGB.length; i++) {
			if (tmp < minRGB[i]) {
				tmp = minRGB[i];
			}
		}
		Body.measure.setBlack(tmp);

		//色判定用の閾値を設定する
		Body.measure.calcColorBorder();
	}
}
