package log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import body.Body;
import game.Game;
import lejos.hardware.lcd.LCD;

/**
 * ログクラス
 * @author
 *
 */
public class Log {
	/** 競技 */
	Game game;

	/** ログデータリスト */
	private List<LogData> logList;
	private List<LogRGBData> logRGBList;
	private List<LogHSVData> logHSVList;
	private List<LogHSLData> logHSLList;

	/**
	 * コンストラクタ
	 */
	public Log(Game game) {
		this.game = game;
		logList = new ArrayList<LogData>();
		logRGBList = new ArrayList<LogRGBData>();
        logHSVList = new ArrayList<LogHSVData>();
        logHSLList = new ArrayList<LogHSLData>();
	}

	/**
	 * 実行する
	 */
	public void run() {
		draw();
		add();
	}

	/**
	 * 追加する
	 */
	private void add() {
        addCourse();
		addRGB();
		addHSV();
		addHSL();
	}

	/**
	 * コース情報を追加する
	 */
	private void addCourse() {
        logList.add(
                new LogData(game.getCount(), game.toString(),
                        Body.measure.getHue(), Body.measure.getSaturation(), Body.measure.getValue(),
                        Body.measure.getLeftRotationSpeed(), Body.measure.getRightRotationSpeed()));
	}

	/**
	 * RGB情報を追加する
	 */
	private void addRGB() {
		float[] rgb = Body.measure.getRGB();
		logRGBList.add(new LogRGBData(game.getCount(), rgb[0], rgb[1], rgb[2]));
	}

	/**
	 * HSV情報を追加する
	 */
	private void addHSV() {
		logHSVList.add(new LogHSVData(game.getCount(),
				Body.measure.getHueHSV(), Body.measure.getSaturationHSV(),
				Body.measure.getValueHSV(), Body.measure.getColorHSV()));
	}

	/**
	 * HSL情報を追加する
	 */
	private void addHSL() {
		logHSLList.add(new LogHSLData(game.getCount(),
				Body.measure.getHueHSV(), Body.measure.getSaturationHSV(),
				Body.measure.getValueHSV(), Body.measure.getColorHSL()));
	}

	/**
	 * LCDに描画する
	 */
	private void draw() {
		LCD.clear();
		LCD.drawString(game.toString(), 0, 0);
		LCD.drawString("White", 0, 1);
		LCD.drawString(Float.toString(Body.measure.getWhite()), 11, 1);
		LCD.drawString("Black", 0, 2);
		LCD.drawString(Float.toString(Body.measure.getBlack()), 11, 2);
		LCD.drawString("Target", 0, 3);
		LCD.drawString(Float.toString(Body.measure.getTarget()), 11, 3);
		LCD.drawString("Value", 0, 4);
		LCD.drawString(Float.toString(Body.measure.getValue()), 11, 4);
	}

	/**
	 * ファイルに保存する
	 */
	public void write() {
		writeCourse();
		writeRGB();
		writeHSV();
		writeHSL();
	}

	/**
	 * コース情報を出力する
	 */
	public void writeCourse() {

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File("log.csv")));) {
			StringBuilder sb = new StringBuilder();

			sb.append("count,status,hue,saturation,value,leftRotationSpeed,rightRotationSpeed\r\n");
			for (LogData data : logList) {
				sb.append(data.toString());
			}
			bw.write(sb.toString());
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * RGB情報を出力する
	 */
	public void writeRGB() {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File("RGBLog.csv")));) {
			StringBuilder sb = new StringBuilder();

			sb.append("Count,Red,Green,Blue\r\n");
			for (LogRGBData data : logRGBList) {
				sb.append(data.toString());
			}
			bw.write(sb.toString());
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * HSV情報を出力する
	 */
	public void writeHSV() {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File("logHSV.csv")));) {
			StringBuilder sb = new StringBuilder();

			sb.append("Count,Hue,Saturation,Value,Color\r\n");
			for (LogHSVData data : logHSVList) {
				sb.append(data.toString());
			}
			bw.write(sb.toString());
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * HSL情報を出力する
	 */
	public void writeHSL() {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File("logHSL.csv")));) {
			StringBuilder sb = new StringBuilder();

			sb.append("Count,Hue,Saturation,Lightness,Color\r\n");
			for (LogHSLData data : logHSLList) {
				sb.append(data.toString());
			}
			bw.write(sb.toString());
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
