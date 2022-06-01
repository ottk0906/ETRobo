package game;

/**
 * スタート待ち状態クラス
 * デザインパターンのSingletonパターンを採用
 * @author 後藤　聡文
 *
 */
public class StateWaitStart extends State {
    /** インスタンス */
    private static StateWaitStart instance = new StateWaitStart();

    /**
     * コンストラクタ
     */
    private StateWaitStart() {
        name = "WaitStart";
    }

    /**
     * 競技状態を遷移する
     * @param game 競技
     */
    @Override
    public void changeState(Game game) {
        game.changeState(this, StateRun.getInstance());
    }

    /**
     * インスタンスを取得する
     * @return インスタンス
     */
    public static StateWaitStart getInstance() {
        return instance;
    }

}
