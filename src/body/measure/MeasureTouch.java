package body.measure;

import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;

/**
 * タッチ計測クラス
 * @author 後藤　聡文
 *
 */
public class MeasureTouch{
    /** タッチセンサ */
    private EV3TouchSensor touchSensor;
    private SensorMode sensorMode;
    private float value[];

    /** 押されたか（今の状態） */
    private boolean isPressed;
    /** 押されたか（前回の状態） */
    private boolean isPressedOld;
    /** 離されたか */
    private boolean isUpped;
    
    /**
     * コンストラクタ
     * @param touchSensor タッチセンサ
     */
    public MeasureTouch(EV3TouchSensor touchSensor){
        this.touchSensor = touchSensor;
        sensorMode =  this.touchSensor.getTouchMode();
        value = new float[sensorMode.sampleSize()];
    }

    /**
     * 更新する
     */
    public void update() {
        // タッチセンサが押されたか（今の状態）
        sensorMode.fetchSample(value, 0);
        isPressed = ((int)value[0] != 0);
        
        // タッチセンサが離されたか（前回と今の状態を比較）
        if(isPressedOld && !isPressed){
            isUpped = true;
        }else{
            isUpped = false;
        }
        isPressedOld = isPressed;
    }
    
    /**
     * 押されたか
     * @return 押された場合はtrue
     */
    public boolean isPressed(){
        return isPressed;
    }
    
    /**
     * 離されたか
     * @return 離された場合はtrue
     */
    public boolean isUpped() {
        return isUpped;
    }
}
