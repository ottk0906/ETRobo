package game.activity;

import body.Body;

/**
 * ベースコースゴール到達動作クラス
 * @author 尾角 武俊
 *
 */
public class ActivityBaseFinish extends Activity {

    /**
     * コンストラクタ
     */
    public ActivityBaseFinish() {
    	//処理なし
    }

	/**
	 * 継続動作を実行する
	 * 状態にいる間、継続して実行される動作
	 * UMLステートマシン図のdoアクティビティ
	 */
	@Override
	public void doActivity() {
    	//ベーシックコースゴール到達判定フラグに「True:ゴール到達」を設定する
    	Body.paraFileMgt.setBaseCourseFinish(true);
	}
}
