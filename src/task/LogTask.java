package task;

import log.Log;

/**
 * ログタスククラス
 * @author 後藤　聡文
 *
 */
public class LogTask extends Thread {

    /** ログ */
    private Log log;
    
    /**
     * コンストラクタ
     * @param log   ログ
     */
    public LogTask(Log log){
        this.log = log;
    }
    
    /**
     * 実行する
     */
    @Override
    public void run() {
        log.run();
    }

}
