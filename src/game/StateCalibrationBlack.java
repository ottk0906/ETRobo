package game;

/**
 * 黒明度キャリブレーション状態クラス
 * デザインパターンのSingletonパターンを採用
 * @author 後藤　聡文
 *
 */
public class StateCalibrationBlack extends State {
    /** インスタンス */
    private static StateCalibrationBlack instance = new StateCalibrationBlack();

    /** コンストラクタ */
    private StateCalibrationBlack() {
        name = "CalibrationBlack";
    }

    /**
     * 競技状態を遷移する
     * @param game 競技
     */
    @Override
    public void changeState(Game game) {
        game.changeState(this, StateWaitStart.getInstance());
    }

    /**
     * インスタンスを取得する
     * @return インスタンス
     */
    public static StateCalibrationBlack getInstance() {
        return instance;
    }

}
