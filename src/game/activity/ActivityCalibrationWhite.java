package game.activity;

import body.Body;

/**
 * 白明度キャリブレーション動作クラス
 * @author 後藤　聡文
 *
 */
public class ActivityCalibrationWhite extends Activity{
    
    /**
     * コンストラクタ
     */
    public ActivityCalibrationWhite(){

    }
    
    /**
     * 継続動作を実行する
     */
    @Override
    public void doActivity(){
        
    }
    
    /**
     * 後動作を実行する
     * 白明度を設定する
     */
    @Override
    public void exitAction() {
        Body.measure.setWhite(Body.measure.getValue());
    }

}
