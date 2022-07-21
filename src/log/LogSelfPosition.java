package log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import body.Body;
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
    private List<LogSelfPositionData> logList;

    /** ログファイル名 */
    private final String LOG_FILE_NAME = "logSelfPosition.csv";


    /**
     * コンストラクタ
     */
    public LogSelfPosition(Game game, SelfPosition selfPos) {
    	this.game = game;
    	this.selfPos = selfPos;
        logList = new ArrayList<LogSelfPositionData>();

        try {
	        //ログファイルが既に存在する場合は削除する
	        File file = new File(LOG_FILE_NAME);
	        if (file.exists()) file.delete();
	        //ログファイルにヘッダ項目を出力する
	        write(true);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
    }

    /**
     * 実行する
     */
    public void run() {
		//走行中の場合のみ、自己位置推定ログ出力処理を実行する
        if (game.getStatus() instanceof StateRun) {
	    	draw();
	    	add();
	    	write(false);
        }
    }

    /**
     * 追加する
     */
    private void add() {
    	//自己位置推定ログデータクラスのインスタンスを生成し、ログリストに追加する
        logList.add(
        	new LogSelfPositionData(
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
        	)
    	);
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
     */
    public void write(boolean init) {
        try {
            StringBuilder sb = new StringBuilder();

            //ヘッダー項目を出力する
            if(init) {
	            sb.append("X-Coord").append(",");
	        	sb.append("Y-Coord").append(",");
	        	sb.append("Accumulated-Angle").append(",");
	        	sb.append("Accumulated-Distance").append(",");
	        	sb.append("Odometry-X").append(",");
	        	sb.append("Odometry-Y").append(",");
	        	sb.append("Odometry-Angle").append(",");
	        	sb.append("LeftWheel-Distance").append(",");
	        	sb.append("RigjtWheel-Distance").append(",");
	        	sb.append("Distance").append(",");
	        	sb.append("LeftWheel-Angle-Past").append(",");
	        	sb.append("RightWheel-Angle-Past").append(",");
	        	sb.append("elapsedTime").append("\r\n");
        	//データ部分を出力する
            } else {
	        	for (LogSelfPositionData data : logList) {
	                sb.append(data.toString());
	            }
            }

        	File file = new File(LOG_FILE_NAME);
            FileWriter fw = new FileWriter(file, true);	//アペンドモードで書き込む
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(sb.toString());
            bw.close();

            logList.clear();	//ログリストをクリアする

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
