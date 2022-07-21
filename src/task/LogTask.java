package task;

import log.Log;
import log.LogSelfPosition;

/**
 * ログタスククラス
 * @author
 *
 */
public class LogTask extends Thread {

    /** ログ */
    private Log log;

    /** 自己位置推定ログ */
    private LogSelfPosition logSelfPos;

    /**
     * コンストラクタ
     * @param log   ログ
     */
    public LogTask(Log log,LogSelfPosition logSelfPos){
        this.log = log;
        this.logSelfPos = logSelfPos;
    }

    /**
     * 実行する
     */
    @Override
    public void run() {
        log.run();
        //自己位置推定ログ出力処理を実行する
        logSelfPos.run();
    }

}
