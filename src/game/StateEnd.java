package game;

/**
 * 競技終了状態クラス
 * デザインパターンのSingletonパターンを採用
 * @author 後藤　聡文
 *
 */
public class StateEnd extends State {
    /** インスタンス */
    private static StateEnd instance = new StateEnd();
    
    /**
     * コンストラクタ
     */
    private StateEnd() {
        name = "End";
    }

    /**
     * 競技状態を遷移する
     * @param game 競技
     */
    @Override
    public void changeState(Game game) {
        
    }

    /**
     * インスタンスを取得する
     * @return インスタンス
     */
    public static StateEnd getInstance() {
        return instance;
    }

}
