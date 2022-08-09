package game.state;

import game.Game;

/**
 * ゲーム攻略状態クラス
 * デザインパターンのSingletonパターンを採用
 * @author 尾角　武俊
 *
 */
public class StateConquest extends State {

    /** インスタンス */
    private static StateConquest instance = new StateConquest();

    /**
     * コンストラクタ
     */
    private StateConquest() {
        name = "Conquest";
    }

    /**
     * 競技状態を遷移する
     * @param game 競技
     */
    @Override
    public void changeState(Game game) {
        game.changeState(this, StateEnd.getInstance());
    }

    /**
     * インスタンスを取得する
     * @return インスタンス
     */
    public static StateConquest getInstance() {
        return instance;
    }

}
