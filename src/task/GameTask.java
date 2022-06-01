package task;

import java.util.concurrent.CountDownLatch;

import body.measure.Measure;
import game.Game;
import body.control.Control;
import lejos.hardware.Button;

/**
 * 競技タスククラス
 * @author 後藤　聡文
 *
 */
public class GameTask extends Thread {
    
    /** ほかのスレッドで実行中の操作セットが完了するまで、1つ以上のスレッドを待機可能にする同期化支援機能 */
    private CountDownLatch countDownLatch;
    
    /** 計測　*/
    Measure measure;
    /** 競技 */
    Game game;
    /** 制御 */
    Control control;

    /**
     * コンストラクタ
     * @param countDownLatch 同期化支援機能
     * @param measure 計測
     * @param game 競技
     * @param control 制御
     */
    public GameTask(CountDownLatch countDownLatch, Measure measure, Game game, Control control){
        this.countDownLatch = countDownLatch;
        this.measure = measure;
        this.game = game;
        this.control = control;
    }
    
    /**
     * 実行する
     */
    @Override
    public void run() {
        if(Button.ESCAPE.isDown() || game.isOver()){
            countDownLatch.countDown();
        }
        measure.update();
        game.run();
        control.run();
    }

}
