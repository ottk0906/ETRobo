package body.control;

import body.Body;
import lejos.hardware.lcd.LCD;

/**
 * カラー制御クラス
 * @author 尾角 武俊
 *
 */
public class ControlColor {

	/**
	 * 制御する
	 */
	public void run() {
        draw();
	}

	/**
	 * LCDに描画する
	 */
	private void draw() {
		//LCD.clear();
		//LCD.drawString("RGB", 0, 0);
		//float[] rgb = Body.measure.getRGB();
		//LCD.drawString(Float.toString(rgb[0]), 11, 0);
		//LCD.drawString(Float.toString(rgb[1]), 11, 1);
		//LCD.drawString(Float.toString(rgb[2]), 11, 2);

		//HSV表示
		LCD.drawString("Color(HSV)", 0, 5);
		LCD.drawString(String.valueOf(Body.measure.getColorHSV()), 11, 5);
		//HSL表示
		LCD.drawString("Color(HSL)", 0, 6);
		LCD.drawString(String.valueOf(Body.measure.getColorHSL()), 11, 6);

		/*
		LCD.drawString("H(HSV)", 0, 7);
		LCD.drawString(Float.toString(Body.measure.getHueHSV()), 11, 7);
		LCD.drawString("S(HSV)", 0, 8);
		LCD.drawString(Float.toString(Body.measure.getSaturationHSV()), 11, 8);
		LCD.drawString("V(HSV)", 0, 9);
		LCD.drawString(Float.toString(Body.measure.getValueHSV()), 11, 9);

		LCD.drawString("H(HSL)", 0, 7);
		LCD.drawString(Float.toString(Body.measure.getHueHSL()), 11, 7);
		LCD.drawString("S(HSL)", 0, 8);
		LCD.drawString(Float.toString(Body.measure.getSaturationHSL()), 11, 8);
		LCD.drawString("L(HSL)", 0, 9);
		LCD.drawString(Float.toString(Body.measure.getLightnessHSL()), 11, 9);
		*/
	}
}
