package body.measure;

/**
 * 計測クラス
 * デザインパターンのFacadeパターンを採用
 * @author 後藤　聡文
 *
 */
public class Measure {
    /** タッチ計測 */
    MeasureTouch measureTouch;
    /** 路面計測 */
    MeasureCourse measureCourse;
    /** 車輪計測 */
    MeasureWheel measureWheel;
    
    /**
     * コンストラクタ
     * @param measureTouch タッチ計測
     * @param measureCourse 路面計測
     * @param measureWheel 車輪計測
     */
    public Measure(MeasureTouch measureTouch, MeasureCourse measureCourse, MeasureWheel measureWheel) {
        this.measureTouch = measureTouch;
        this.measureCourse = measureCourse;
        this.measureWheel = measureWheel;
    }
    
    /**
     * 更新する
     */
    public void update() {
        measureTouch.update();
        measureCourse.update();
        measureWheel.update();
    }
    
    /**
     * タッチセンサが離されたか
     * @return タッチセンサが離された場合はtrue
     */
    public boolean isUpped() {
        return measureTouch.isUpped();
    }
    
    /**
     * 白明度を取得する
     * @return　白明度
     */
    public float getWhite() {
        return measureCourse.getWhite();
    }

    /**
     * 白明度を設定する
     * @param white　白明度
     */
    public void setWhite(float white) {
        measureCourse.setWhite(white);
    }

    /**
     * 黒明度を取得する
     * @return　黒明度
     */
    public float getBlack() {
        return measureCourse.getBlack();
    }

    /**
     * 黒明度を設定する
     * @param black　黒明度
     */
    public void setBlack(float black) {
        measureCourse.setBlack(black);
    }
    
    /**
     * 目標明度を取得する
     * @return　目標明度
     */
    public float getTarget() {
        return measureCourse.getTarget();
    }
    
    /**
     * 目標明度を設定する
     * @param target　目標明度
     */
    public void setTarget(float target) {
        measureCourse.setTarget(target);
    }
    
    /**
     * 路面色相を取得する
     * @return　色相
     */
    public float getHue(){
        return measureCourse.getHue();
    }
    
    /**
     * 路面彩度を取得する
     * @return　彩度
     */
    public float getSaturation(){
        return measureCourse.getSaturation();
    }
    
    /**
     * 路面明度を取得する
     * @return　路面明度
     */
    public float getValue() {
        return measureCourse.getValue();
    }
    
    /**
     * 左モータの角速度(度/秒)を計測する
     * @return leftRotationSpeed　左モータの角速度(度/秒)
     */
    public float getLeftRotationSpeed() {
        return measureWheel.getLeftRotationSpeed();
    }

    /**
     * 右モータの角速度(度/秒)を計測する
     * @return rightRotationSpeed　右モータの角速度(度/秒)
     */
    public float getRightRotationSpeed() {
        return measureWheel.getRightRotationSpeed();
    }

}
