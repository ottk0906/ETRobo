package log;

import java.util.ArrayList;
import java.util.List;

import body.Body;
import fileIO.CsvWrite;
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

	/** CSVファイル出力クラス */
	private CsvWrite csvWrite;

	/** ログデータリスト */
	private List<LogData> logList;
	private List<LogData> logRGBList;
	private List<LogData> logHSVList;
	private List<LogData> logHSLList;

	/**
	 * コンストラクタ
	 * @param	game	競技クラス
	 */
	public Log(Game game) {
		this.game = game;
		logList = new ArrayList<LogData>();
		logRGBList = new ArrayList<LogData>();
        logHSVList = new ArrayList<LogData>();
        logHSLList = new ArrayList<LogData>();
        //CSVファイル出力クラスのインスタンスを生成する
		csvWrite = new CsvWrite();
		//ログアファイルを生成し、ヘッダー情報を書き込む
        write(true);
	}

	/**
	 * 実行する
	 */
	public void run() {
		draw();
		add();
        write(false);
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
		LogData data = new LogDataCourse(game.getCount(), game.toString(),
                Body.measure.getHue(), Body.measure.getSaturation(), Body.measure.getValue(),
                Body.measure.getLeftRotationSpeed(), Body.measure.getRightRotationSpeed(),
                Body.stopwatch.elapsed());
        logList.add(data);
	}

	/**
	 * RGB情報を追加する
	 */
	private void addRGB() {
		float[] rgb = Body.measure.getRGB();
		LogData data = new LogRGBData(game.getCount(), rgb[0], rgb[1], rgb[2],Body.stopwatch.elapsed());
		logRGBList.add(data);
	}

	/**
	 * HSV情報を追加する
	 */
	private void addHSV() {
		LogData data = new LogHSVData(game.getCount(),
				Body.measure.getHueHSV(), Body.measure.getSaturationHSV(),
				Body.measure.getValueHSV(), Body.measure.getColorHSV(),
				Body.stopwatch.elapsed());
		logHSVList.add(data);
	}

	/**
	 * HSL情報を追加する
	 */
	private void addHSL() {
		LogData data = new LogHSLData(game.getCount(),
				Body.measure.getHueHSV(), Body.measure.getSaturationHSV(),
				Body.measure.getValueHSV(), Body.measure.getColorHSL(),
				Body.stopwatch.elapsed());
		logHSLList.add(data);
	}

	/**
	 * LCDに描画する
	 */
	private void draw() {
		//LCD.clear();
		LCD.drawString(game.toString(), 0, 0);
		/*
		LCD.drawString("White", 0, 1);
		LCD.drawString(Float.toString(Body.measure.getWhite()), 11, 1);
		LCD.drawString("Black", 0, 2);
		LCD.drawString(Float.toString(Body.measure.getBlack()), 11, 2);
		LCD.drawString("Target", 0, 3);
		LCD.drawString(Float.toString(Body.measure.getTarget()), 11, 3);
		LCD.drawString("Value", 0, 4);
		LCD.drawString(Float.toString(Body.measure.getValue()), 11, 4);
		*/
	}

	/**
	 * ファイルに出力する
	 * @param	init	初期化フラグ
	 */
	public void write(boolean init) {
		writeCourse(init);
		writeRGB(init);
		writeHSV(init);
		writeHSL(init);
	}

	/**
	 * コース情報を出力する
	 * @param	init	初期化フラグ
	 */
	public void writeCourse(boolean init) {
		if(init) {
			//ヘッダー文字列を設定する
			String headerString = "count,status,hue,saturation,value,leftRotationSpeed,rightRotationSpeed,elapsedTime";
			csvWrite.setHeaderString(headerString);
		}
		//ファイル名を生成する
		String fileName = "log" + Body.logFileSuffix + ".csv";
		//CSVファイルに出力する
		csvWrite.writeCsvFile(fileName, logList, true, init);
		logList.clear();
	}

	/**
	 * RGB情報を出力する
	 * @param	init	初期化フラグ
	 */
	public void writeRGB(boolean init) {
		if(init) {
			//ヘッダー文字列を設定する
			String headerString = "Count,Red,Green,Blue,elapsedTime";
			csvWrite.setHeaderString(headerString);
		}
		//ファイル名を生成する
		String fileName = "RGBLog" + Body.logFileSuffix + ".csv";
		//CSVファイルに出力する
		csvWrite.writeCsvFile(fileName, logRGBList, true, init);
		logRGBList.clear();
	}

	/**
	 * HSV情報を出力する
	 * @param	init	初期化フラグ
	 */
	public void writeHSV(boolean init) {
		if(init) {
			//ヘッダー文字列を設定する
			String headerString = "Count,Hue,Saturation,Value,Color,elapsedTime";
			csvWrite.setHeaderString(headerString);
		}
		//ファイル名を生成する
		String fileName = "logHSV" + Body.logFileSuffix + ".csv";
		//CSVファイルに出力する
		csvWrite.writeCsvFile(fileName, logHSVList, true, init);
		logHSVList.clear();
	}

	/**
	 * HSL情報を出力する
	 * @param	init	初期化フラグ
	 */
	public void writeHSL(boolean init) {
		if(init) {
			//ヘッダー文字列を設定する
			String headerString = "Count,Hue,Saturation,Lightness,Color,elapsedTime";
			csvWrite.setHeaderString(headerString);
		}
		//ファイル名を生成する
		String fileName = "logHSL" + Body.logFileSuffix + ".csv";
		//CSVファイルに出力する
		csvWrite.writeCsvFile(fileName, logHSLList, true, init);
		logHSLList.clear();
	}

}
