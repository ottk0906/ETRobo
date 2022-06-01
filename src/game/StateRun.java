package game;

/**
 * 走行状態クラス
 * デザインパターンのSingletonパターンを採用
 * @author 後藤　聡文
 *
 */
public class StateRun extends State {
    /** インスタンス */
    private static StateRun instance = new StateRun();
    
    /**
     * コンストラクタ
     */
    private StateRun() {
        name = "Run";
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
    public static StateRun getInstance() {
        return instance;
    }

}
