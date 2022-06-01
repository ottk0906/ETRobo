package game;

/**
 * 白明度キャリブレーション状態クラス
 * デザインパターンのSingletonパターンを採用
 * @author 後藤　聡文
 *
 */
public class StateCalibrationWhite extends State {
    /** インスタンス */
    private static StateCalibrationWhite instance = new StateCalibrationWhite();

    /**
     * コンストラクタ
     */
    private StateCalibrationWhite() {
        name = "CalibrationWhite";
    }

    /**
     * 競技状態を遷移する
     * @param game 競技
     */
    @Override
    public void changeState(Game game) {
        game.changeState(this, StateCalibrationBlack.getInstance());
    }

    /**
     * インスタンスを取得する
     * @return インスタンス
     */
    public static StateCalibrationWhite getInstance() {
        return instance;
    }

}
