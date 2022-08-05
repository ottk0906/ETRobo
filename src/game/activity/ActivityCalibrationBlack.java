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
		this.minRGB = new float[3];
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

		//黒明度と目標明度を設定する
		//Body.measure.setBlack(Body.measure.getValue());
		//Body.measure.setTarget((Body.measure.getWhite() + Body.measure.getBlack()) / 2.0f);
		//色判定用の閾値を設定する
		Body.measure.calcColorBorder();
	}
}
