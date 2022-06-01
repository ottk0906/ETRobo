package game;

import java.util.ArrayList;
import game.activity.Activity;
import game.guard.Guard;
import task.Beep;

/**
 * 競技状態クラス
 * デザインパターンのStateパターンを採用
 * @author 後藤　聡文
 *
 */
public abstract class State {
    /** 遷移条件リスト */
    protected ArrayList<Guard> guardList;
    /** 動作リスト */
    protected ArrayList<Activity> activityList;
    /** リストのインデックス */
    protected int index;
    /** 状態名 */
    protected String name;

    /**
     * コンストラクタ
     */
    public State() {
        guardList = new ArrayList<>();
        activityList = new ArrayList<>();
        index = 0;
        name = "";
    }

    /**
     * 追加する
     * @param guard 遷移条件
     * @param activity　動作
     */
    public void add(Guard guard, Activity activity) {
        guardList.add(guard);
        activityList.add(activity);
    }

    /**
     * 前動作を実行する
     * 状態に遷移したときに1度だけ実行される動作
     * UMLステートマシン図のentryアクション
     */
    public void entryAction() {
        activityList.get(index).entryAction();
    }

    /**
     * 継続動作を実行する
     * 状態にいる間、継続して実行される動作
     * UMLステートマシン図のdoアクティビティ
     * @param game 競技
     */
    public void doActivity(Game game) {
        if (guardList.get(index).judge()) {
            index++;
            if(index >= guardList.size()){
                changeState(game);
            } else {
                Beep.ring();
            }
        } else {
            activityList.get(index).doActivity();
        }
    }

    /**
     * 後動作を実行する
     * 状態から離れる直前に1度だけ実行される動作
     * UMLステートマシン図のexitアクション
     */
    public void exitAction() {
        activityList.get(index-1).exitAction();        
    }

    /**
     * 競技状態を遷移する
     * @param game 競技
     */
    public abstract void changeState(Game game);

    /**
     * リストのインデックスを取得する
     * @return リストのインデックス
     */
    public int getIndex() {
        return index;
    }
    
    /**
     * リストのサイズを取得する
     * @return
     */
    public int getSize() {
        return guardList.size();
    }

    /**
     * オブジェクトの文字列表現を取得する
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(" ");
        sb.append(getIndex() + 1);
        sb.append("/");
        sb.append(getSize());
        
        return sb.toString();
    }
}
