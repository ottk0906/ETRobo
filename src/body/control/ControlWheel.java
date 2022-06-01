package body.control;

import hardware.KamogawaRegulatedMotor;

/**
 * 車輪制御クラス
 * @author 後藤　聡文
 *
 */
public class ControlWheel {
    /** 左モータ */
    private KamogawaRegulatedMotor leftMotor;
    /** 右モータ */
    private KamogawaRegulatedMotor rightMotor;
    
    /** 左モータの角速度(度/秒) */
    private float leftRotationSpeed;
    /** 右モータの角速度(度/秒) */
    private float rightRotationSpeed;
    
    /**
     * コンストラクタ
     * @param leftMotor 左モータ
     * @param rightMotor　右モータ
     */
    public ControlWheel(KamogawaRegulatedMotor leftMotor, KamogawaRegulatedMotor rightMotor){
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
    }

    /**
     * 制御する
     */
    public void run(){
        leftMotor.setSpeed(leftRotationSpeed);
        rightMotor.setSpeed(rightRotationSpeed);
        if(leftRotationSpeed >= 0){
            leftMotor.forward();
        }else{
            leftMotor.backward();
        }
        if(rightRotationSpeed >= 0){
            rightMotor.forward();
        }else{
            rightMotor.backward();
        }
    }

    /**
     * 左モータの角速度(度/秒)を設定する
     * @param leftRotationSpeed　左モータの角速度(度/秒)
     */
    public void setLeftRotationSpeed(float leftRotationSpeed) {
        this.leftRotationSpeed = leftRotationSpeed;
    }

    /**
     * 右モータの角速度(度/秒)を設定する
     * @param leftRotationSpeed　右モータの角速度(度/秒)
     */
    public void setRightRotationSpeed(float rightRotationSpeed) {
        this.rightRotationSpeed = rightRotationSpeed;
    }

}
