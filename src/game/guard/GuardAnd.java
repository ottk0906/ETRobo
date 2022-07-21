package game.guard;

import java.util.List;

/**
 * 状態遷移And条件
 * @author 尾角 武俊
 */
public class GuardAnd extends Guard{

	private List<Guard>  guardList;

	/**
	 * コンストラクタ
	 * @param guardList	状態遷移条件リスト
	 *
	 */
	public GuardAnd(List<Guard> guardList) {
		this.guardList = guardList;
		name = "GuardAnd";
	}

    /**
     * 状態遷移条件1、状態遷移条件2の判定結果をAND演算した結果を返却する
     * @return	True : OK / False ： NG
     */
	@Override
	public boolean judge() {
		//1つ目の条件の判定結果を設定する
		boolean tmpRet = guardList.get(0).judge();

		//2つ目以降の条件の判定結果を反映させる
		for(int iLoop = 1; iLoop < guardList.size(); iLoop++) {

			tmpRet = tmpRet & guardList.get(iLoop).judge();
		}
		return tmpRet;
	}

}
