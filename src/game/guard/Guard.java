package game.guard;

/**
 * 遷移条件クラス
 * 遷移条件（ガード条件）を判定する
 * @author 後藤　聡文
 *
 */
public abstract class Guard {

	/** 状態遷移条件名 */
	protected String name;

    /**
     * 判定する
     * UMLステートマシン図のガード条件
     * @return 条件が成立する場合はtrue、成立しない場合はfalse
     */
    public abstract boolean judge();

    /**
     * オブジェクトの文字列表現を取得する
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        return sb.toString();
    }

}
