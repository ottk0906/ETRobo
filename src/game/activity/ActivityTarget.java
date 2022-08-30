package game.activity;

import body.Body;

/**
 * 目標明度設定動作クラス
 * @author 尾角 武俊
 *
 */
public class ActivityTarget extends Activity  {

	private float target;	//目標明度

	/**
     * コンストラクタ
     * @param	target	目標明度(割合)	※0～1の値を設定
     */
    public ActivityTarget(float target) {
    	//処理なし
    	super.name = "ActivityTarget";
    	this.target = target;
    }

	/**
	 * 継続動作を実行する
	 * 状態にいる間、継続して実行される動作
	 * UMLステートマシン図のdoアクティビティ
	 */
	@Override
	public void doActivity() {
		//目標明度を設定する
		Body.measure.setTarget(target);
	}

}
