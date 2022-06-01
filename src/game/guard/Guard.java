package game.guard;

/**
 * 遷移条件クラス
 * 遷移条件（ガード条件）を判定する
 * @author 後藤　聡文
 *
 */
public abstract class Guard {
    
    /**
     * 判定する
     * UMLステートマシン図のガード条件
     * @return 条件が成立する場合はtrue、成立しない場合はfalse
     */
    public abstract boolean judge();
}
