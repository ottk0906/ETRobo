package task;

import lejos.hardware.Sound;

/**
 * Beepクラス
 * @author 後藤 聡文
 *
 */
public class Beep {

    /**
     * Beep音を鳴らす
     */
    public static void ring() {
        Thread beepTask = new Thread(new Runnable() {
            @Override
            public void run() {
                Sound.beep();
            }
        });
        beepTask.setPriority(Thread.MIN_PRIORITY);
        beepTask.start();
    }

}
