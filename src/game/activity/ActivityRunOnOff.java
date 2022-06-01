package game.activity;

import body.Body;

/**
 * OnOff走行動作クラス
 * @author 後藤　聡文
 *
 */
public class ActivityRunOnOff extends ActivityRun{
    
    /** 回転角速度(度/秒)の初期値 */
    private float initialTurn;
    
    /**
     * コンストラクタ
     * @param forward 目標速度(mm/秒)
     * @param turn 目標回転角速度(度/秒)
     */
    public ActivityRunOnOff(float forward, float turn){
        super(forward, 0.0f);
        initialTurn = turn;
    }
    
    /**
     * 継続動作を実行する
     * 路面明度と目標明度を比較して目標回転角速度を計算し、
     * 目標速度と目標回転角速度を設定する
     */
    @Override
    public void doActivity(){
        // 路面明度を取得する
        float brightness = Body.measure.getValue();

        // 目標明度を取得する
        float target = Body.measure.getTarget(); 
        
        // 目標回転角速度を計算する
        if(brightness < target){
            turn = initialTurn;
        }else{
            turn = -initialTurn;
        }
        
        // 速度を設定する
        Body.control.setForward(forward);
        Body.control.setTurn(turn);
    }

}
