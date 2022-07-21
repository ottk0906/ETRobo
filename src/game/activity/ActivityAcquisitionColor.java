package game.activity;

import body.Body;
import lejos.hardware.lcd.LCD;

/**
 * 色情報取得表示動作クラス
 * @author 原田　寛大
 */
public class ActivityAcquisitionColor extends Activity {

	/**
	 * コンストラクタ
	 */
	public ActivityAcquisitionColor() {

	}

	/**
	 * 仮限界値設定
	 */
	@Override
	public void entryAction() {

		//色判定の閾値を設定する
		//★★★本処理は、後日、黒キャリブレーションのexitAction()に移動する想定
		Body.measure.calcColorBorder();

		/*
		try {
			float borderRtoY = 30.0f;
			float borderYtoG = 90.0f;
			float borderGtoB = 180.0f;
			float borderBtoR = 300.0f;
			Body.measure.setColorBorder(borderRtoY, borderYtoG, borderGtoB, borderBtoR);
		} catch (Exception e) {
			LCD.drawString("ACE", 0, 7);
		}

		*/
	}

	/**
	 * 継続動作を実行する
	 */
	@Override
	public void doActivity() {
		draw();
	}

	/**
	 * LCDに描画する
	 */
	private void draw() {
		LCD.clear();
		LCD.drawString("RGB", 0, 0);
		float[] rgb = Body.measure.getRGB();
		LCD.drawString(Float.toString(rgb[0]), 11, 0);
		LCD.drawString(Float.toString(rgb[1]), 11, 1);
		LCD.drawString(Float.toString(rgb[2]), 11, 2);
		//画面が狭いので片方ずつ表示するようにコメントアウト

		/** HSV表示 */
		/*LCD.drawString("H(HSV)", 0, 3);
		LCD.drawString(Float.toString(Body.measure.getHueHSV()), 11, 3);
		LCD.drawString("S(HSV)", 0, 4);
		LCD.drawString(Float.toString(Body.measure.getSaturationHSV()), 11, 4);
		LCD.drawString("V(HSV)", 0, 5);
		LCD.drawString(Float.toString(Body.measure.getValueHSV()), 11, 5);
		LCD.drawString("C(HSV)", 0, 6);
		LCD.drawString(String.valueOf(Body.measure.getColorHSV()), 11, 6);*/

		/** HSL表示 */
		LCD.drawString("H(HSL)", 0, 3);
		LCD.drawString(Float.toString(Body.measure.getHueHSL()), 11, 3);
		LCD.drawString("S(HSL)", 0, 4);
		LCD.drawString(Float.toString(Body.measure.getSaturationHSL()), 11, 4);
		LCD.drawString("L(HSL)", 0, 5);
		LCD.drawString(Float.toString(Body.measure.getLightnessHSL()), 11, 5);
		LCD.drawString("C(HSL)", 0, 6);
		LCD.drawString(String.valueOf(Body.measure.getColorHSL()), 11, 6);

	}

}