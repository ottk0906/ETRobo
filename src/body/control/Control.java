package body.control;

import body.Body;

/**
 * 制御クラス
 * デザインパターンのFacadeパターンを採用
 * @author 後藤　聡文
 *
 */
public class Control {
    /** 車輪制御 */
    ControlWheel controlWheel;
    
    /** 目標速度(mm/秒) */
    private float forward;
    /** 目標回転角速度(度/秒) */
    private float turn;
    
    /**
     * コンストラクタ
     * @param controlWheel 車輪制御
     */
    public Control(ControlWheel controlWheel) {
        this.controlWheel = controlWheel;
    }
    
    /**
     * 制御する
     * 目標速度(mm/秒)と目標回転角速度(度/秒)から
     * 左右モータの角速度(度/秒)を計算して設定する
     */
    public void run(){
        turn = (float)Math.toRadians(turn);
        
        float leftRotationSpeed = (float) Math.toDegrees((2.0f * forward - Body.TREAD * turn) / Body.DIAMETER);
        float rightRotationSpeed = (float) Math.toDegrees((2.0f * forward + Body.TREAD * turn) / Body.DIAMETER);
        
        controlWheel.setLeftRotationSpeed(leftRotationSpeed);
        controlWheel.setRightRotationSpeed(rightRotationSpeed);
        controlWheel.run();
    }

    /**
     * 目標速度(mm/秒)を設定する
     * @param forward 目標速度(mm/秒)
     */
    public void setForward(float forward) {
        this.forward = forward;
    }

    /**
     * 目標回転角速度(度/秒)を設定する
     * @param turn 目標回転角速度(度/秒)
     */
    public void setTurn(float turn) {
        this.turn = turn;
    }
}
