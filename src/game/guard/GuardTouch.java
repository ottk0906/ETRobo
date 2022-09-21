package game.guard;

import body.Body;
import task.Beep;

/**
 * タッチ遷移条件
 * @author 後藤　聡文
 *
 */
public class GuardTouch extends Guard{

	public GuardTouch() {
		//クラス名を設定する
		name = "GuardTouch";
	}

    /**
     * 判定する
     * @return タッチセンサが押された場合はtrue、押されていない場合はfalse
     */
    @Override
    public boolean judge(){
		//---> Modify 2022/09/21 T.Okado ログ出力停止
        //return Body.measure.isUpped();
    	if(Body.measure.isUpped()) {
    		Beep.ring();
    		return true;
    	} else {
    		return false;
    	}
        //<--- Modify 2022/09/21 T.Okado ログ出力停止
    }
}
