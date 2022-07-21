package game.guard;

import body.Body;

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
        return Body.measure.isUpped();
    }
}
