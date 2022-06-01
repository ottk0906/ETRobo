package game.activity;

import body.Body;

/**
 * 黒明度キャリブレーション動作クラス
 * @author 後藤　聡文
 *
 */
public class ActivityCalibrationBlack extends Activity{
    
    /**
     * コンストラクタ
     */
    public ActivityCalibrationBlack(){

    }
    
    /**
     * 継続動作を実行する
     */
    @Override
    public void doActivity(){

    }

    /**
     * 後動作を実行する
     * 黒明度と目標明度を設定する
     */
    @Override
    public void exitAction() {
        Body.measure.setBlack(Body.measure.getValue());
        Body.measure.setTarget((Body.measure.getWhite() + Body.measure.getBlack()) / 2.0f);
    }
}
