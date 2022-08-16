package task;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import body.Body;
import fileIO.CsvWrite;
import game.Game;
import lejos.hardware.lcd.LCD;
import log.Log;
import log.LogSelfPosition;

/**
 * タスク管理クラス
 * @author
 *
 */
public class TaskManager {

    //競技タスク
    private GameTask gameTask;

    private Game game;

    //ログタスク
    private LogTask logTask;

    private Log log;

    //自己位置推定ログクラス
    private LogSelfPosition logSelfPos;

    //走行パラメータファイル取得タスク
    private ScenarioTask scenarioTask;

    // スケジューラ
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> futureGame;
    private ScheduledFuture<?> futureLog;
    private ScheduledFuture<?> futureScenarioFileMgt;
    private CountDownLatch countDownLatch;

    /**
     * コンストラクタ
     */
    public TaskManager(){
    	// タスク初期化 開始
        LCD.drawString("Initialize", 0, 0);

        // スケジューラ生成
        scheduler = Executors.newScheduledThreadPool(3);
        countDownLatch = new CountDownLatch(1);

		//ログファイルのサフィックス値を取得する
		CsvWrite csvWrite = new CsvWrite();
		Body.logFileSuffix = csvWrite.getLogFileSuffix();

        // タスク生成
        game = new Game();
        log = new Log(game);

        //制御クラスのインスタンスにgemeクラスのインスタンスを設定する
        Body.control.setGameInstance(game);

		// 自己位置推定インスタンスにgemeクラスのインスタンスを設定する
        Body.selfPos.setGameInstance(game);
		// 自己位置推定ログのインスタンス生成
		logSelfPos = new LogSelfPosition(game, Body.selfPos);

		//走行パラメータファイル追加管理インスタンスにgemeクラスのインスタンスを設定する
        Body.paraFileMgt.setGameInstance(game);

		//競技タスク
        gameTask = new GameTask(countDownLatch, Body.measure, game, Body.control, Body.selfPos);
        gameTask.setPriority(Thread.MAX_PRIORITY);

        //走行パラメータファイル追加管理
        scenarioTask = new ScenarioTask(Body.paraFileMgt);
        scenarioTask.setPriority(Thread.NORM_PRIORITY);

        //ログタスク
        logTask = new LogTask(log, logSelfPos);
        logTask.setPriority(Thread.MIN_PRIORITY);

        //初期化完了
        Beep.ring();
    }

    /**
     * タスクのスケジューリング
     */
    public void schedule(){
        futureGame = scheduler.scheduleAtFixedRate(gameTask, 0, 10, TimeUnit.MILLISECONDS);
        futureScenarioFileMgt = scheduler.scheduleAtFixedRate(scenarioTask, 0, 500, TimeUnit.MILLISECONDS);
        futureLog = scheduler.scheduleAtFixedRate(logTask, 0, 500, TimeUnit.MILLISECONDS);
    }

    /**
     * 競技タスクが終了するまで待つ
     */
    public void await(){
        try{
            countDownLatch.await();
        }catch(InterruptedException e){

        }
    }

    /**
     * タスクの実行の取り消しとスケジューラのシャットダウン
     */
    public void shutdown(){
        if(futureLog != null){
            futureLog.cancel(true);
        }

        if(futureScenarioFileMgt != null){
        	futureScenarioFileMgt.cancel(true);
        }

        if(futureGame != null){
            futureGame.cancel(true);
        }

        scheduler.shutdownNow();
    }
}
