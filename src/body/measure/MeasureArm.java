package body.measure;

import hardware.KamogawaRegulatedMotor;

/**
 * アーム計測クラス
 * @author 駒井
 *
 */
public class MeasureArm {
    /**  アームモータ */
    private KamogawaRegulatedMotor armMotor;
    /**  アームモータの角度 */
    private float armDegrees;

    /**
     * コンストラクタ
     * @param armMotor アームモータ
     */
    public MeasureArm(KamogawaRegulatedMotor armMotor){
        this.armMotor = armMotor;
    }

    /**
     * アームモータの角度を更新する
     */
    public void update() {
        armDegrees = armMotor.getPosition();
    }

    /**
     *アームモータの角度を渡す
     * armDegrees アームモータの角度
     */
    public float getDegrees() {
        return armDegrees;
    }

}
