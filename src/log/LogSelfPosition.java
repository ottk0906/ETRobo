package log;

import java.util.ArrayList;
import java.util.List;

import body.Body;
import fileIO.CsvWrite;
import game.Game;
import game.SelfPosition.SelfPosition;
import game.state.StateRun;
import lejos.hardware.lcd.LCD;

/**
 * 自己位置推定ログクラス
 * @author 尾角 武俊
 */
public class LogSelfPosition {

	/** 競技 */
	private Game game;

	/** 自己位置推定 */
	private SelfPosition selfPos;

    /** 自己位置推定ログデータリスト */
    private List<LogData> logList;

    /** ログファイル名 */
    private String fileName;

	/** CSVファイル出力クラス */
	private CsvWrite csvWrite;

    /**
     * コンストラクタ
     * @param	game		競技クラス
     * @param	selfPos		自己位置推定クラス
     */
    public LogSelfPosition(Game game, SelfPosition selfPos) {
    	this.game = game;
    	this.selfPos = selfPos;
        logList = new ArrayList<LogData>();
        //CSVファイル出力クラスのインスタンスを生成する
		csvWrite = new CsvWrite();
		//ファイル名を生成する
		fileName = "logSelfPos" + Body.logFileSuffix + ".csv";
		//ログファイルが既に存在する場合は削除する
		csvWrite.deleteCsvFile(fileName);

		//ヘッダー文字列を設定する
		String headerString = "X-Coord,Y-Coord,Accumulated-Angle,Accumulated-Distance,";
		headerString = headerString + "Odometry-X,Odometry-Y,Odometry-Angle,";
		headerString = headerString + "LeftWheel-Distance,RigjtWheel-Distance,Distance,";
		headerString = headerString + "LeftWheel-Angle-Past,RightWheel-Angle-Past,elapsedTime";
		csvWrite.setHeaderString(headerString);
		//ヘッダーを出力する
        write(true);
    }

    /**
     * 実行する
     */
    public void run() {
		//走行中の場合のみ、自己位置推定ログ出力処理を実行する
        if (game.getStatus() instanceof StateRun) {
	    	//draw();
	    	add();
	    	write(false);
        }
    }

    /**
     * 追加する
     */
    private void add() {
    	//自己位置推定ログデータクラスのインスタンスを生成し、ログリストに追加する
		LogData data = new LogSelfPositionData(
    		selfPos.getXCoord(),
    		selfPos.getYCoord(),
    		selfPos.getAfterRadianToAngle(),
    		selfPos.getAccumDistance(),
    		selfPos.getOdometryX(),
    		selfPos.getOdometryY(),
    		selfPos.getOdometryRadianToAngle(),
    		selfPos.getLeftDistnce(),
    		selfPos.getRightDistnce(),
    		selfPos.getDistnce(),
    		selfPos.getLeftBeforeAngle(),
    		selfPos.getRightBeforeAngle(),
    		Body.stopwatch.elapsed()
    	);
		logList.add(data);
    }

    /**
     * 画面出力する
     */
    private void draw() {
		LCD.clear();
        LCD.drawString("X-Coord", 0, 1);
        LCD.drawString(String.valueOf(selfPos.getXCoord()) , 11, 1);
        LCD.drawString("Y-Coord", 0, 2);
        LCD.drawString(String.valueOf(selfPos.getYCoord()), 11, 2);
        LCD.drawString("Angle", 0, 3);
        LCD.drawString(String.valueOf(selfPos.getAfterRadianToAngle()), 11, 3);
        LCD.drawString("Distance", 0, 4);
        LCD.drawString(String.valueOf(selfPos.getAccumDistance()), 11, 4);
    }

    /**
     * ログを出力する
	 * @param	init	初期化フラグ
     */
    public void write(boolean init) {
		//CSVファイルに出力する
		csvWrite.writeCsvFile(fileName, logList, true, init);
		//ログリストをクリアする
		logList.clear();
    }

}
