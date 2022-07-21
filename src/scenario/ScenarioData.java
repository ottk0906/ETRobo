package scenario;

import java.util.List;

/**
 * シナリオデータクラス
 */
public class ScenarioData {

	private int sceneNo;				//シーン番号
	private int stateNo;				//競技状態番号
	private int actNo;					//動作定義番号(Activity)
	private List<Double> actValue;		//動作定義のパラメータ（Activityクラスを生成する際の引数値）
	private int gdNo;					//状態遷移条件番号
	private int gdLogicalOperator;		//論理演算子（0:AND / 1:OR）
	private List<Double> gdValue;		//遷移条件のパラメータ（Guardクラスを生成する際の引数値）

	/**
	 * コンストラクタ
	 * @param sceneNo			シーン番号
	 * @param stateNo			競技状態番号
	 * @param actNo			動作定義番号(Activity)
	 * @param actValue			動作定義のパラメータ（Activityクラスを生成する際の引数値）
	 * @param gdNo				状態遷移条件番号
	 * @param gdLogicaOperator	論理演算子（0:AND / 1:OR）
	 * @param gdValue			状態遷移条件のパラメータ（Guardクラスを生成する際の引数値）
	 */
	public ScenarioData(int sceneNo,int stateNo, int actNo, List<Double> actValue,
						 int gdNo,int gdLogicalOperator,List<Double> gdValue ) {
		this.sceneNo = sceneNo;
		this.stateNo = stateNo;
		this.actNo = actNo;
		this.actValue = actValue;
		this.gdNo = gdNo;
		this.gdLogicalOperator = gdLogicalOperator;
		this.gdValue = gdValue;
	}

	/**
     * シーン番号を取得する
     * @return シーン番号
     */
	public int getSceneNo() {
		return sceneNo;
	}

	/**
     * 競技状態番号を取得する
     * @return 競技状態番号
     */
	public int getStateNo() {
		return stateNo;
	}

	/**
     * 動作定義番号を取得する
     * @return 動作定義番号
     */
	public int getActNo() {
		return actNo;
	}

	/**
     * 動作定義のパラメータを取得する
     * @return 動作定義のパラメータ（Activityクラスを生成する際の引数値）
     */
	public List<Double> getActValue() {
		return actValue;
	}

	/**
     * 状態遷移条件番号を取得する
     * @return 状態遷移条件番号
     */
	public int getGdNo() {
		return gdNo;
	}

	/**
     * 論理演算子を取得する
     * @return 論理演算子（0:AND / 1:OR）
     */
	public int getGdLogicalOperator() {
		return gdLogicalOperator;
	}

	/**
     * 状態遷移条件のパラメータを取得する
     * @return 遷状態移条件のパラメータ（Guardクラスを生成する際の引数値）
     */
	public List<Double> getGdValue() {
		return gdValue;
	}

}
