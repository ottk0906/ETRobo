package game.activity;

import task.Beep;

/**
 * 動作クラス
 * @author 後藤 聡文
 *
 */
public abstract class Activity {
    
    /**
     * 前動作を実行する
     * 状態に遷移したときに1度だけ実行される動作
     * UMLステートマシン図のentryアクション
     */
    public void entryAction() {
        Beep.ring();
    }
    
    /**
     * 継続動作を実行する
     * 状態にいる間、継続して実行される動作
     * UMLステートマシン図のdoアクティビティ
     */
    public abstract void doActivity();
    
    /**
     * 後動作を実行する
     * 状態から離れる直前に1度だけ実行される動作
     * UMLステートマシン図のexitアクション
     */
    public void exitAction() {
        
    }
}
