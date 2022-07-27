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
	 */
	public ScenarioData() {
	}

	//************* シナリオデータのsetter() *************

	/**
     * シーン番号を設定する
     * @param シーン番号
     */
	public void setSceneNo(int sceneNo) {
		this.sceneNo = sceneNo;
	}

	/**
     * 競技状態番号を設定する
     * @param 競技状態番号
     */
	public void setStateNo(int stateNo) {
		this.stateNo = stateNo;
	}

	/**
     * 動作定義番号を設定する
     * @param 動作定義番号
     */
	public void setActNo(int actNo) {
		this.actNo = actNo;
	}

	/**
     * 動作定義のパラメータを設定する
     * @param 動作定義のパラメータ（Activityクラスを生成する際の引数値）
     */
	public void setActValue(List<Double> actValue) {
		this.actValue = actValue;
	}

	/**
     * 状態遷移条件番号を設定する
     * @param 状態遷移条件番号
     */
	public void setGdNo(int gdNo) {
		this.gdNo = gdNo;
	}

	/**
     * 論理演算子を設定する
     * @param 論理演算子（0:AND / 1:OR）
     */
	public void setGdLogicalOperator(int gdLogicalOperator) {
		this.gdLogicalOperator = gdLogicalOperator;
	}

	/**
     * 状態遷移条件のパラメータを設定する
     * @param 遷状態移条件のパラメータ（Guardクラスを生成する際の引数値）
     */
	public void setGdValue(List<Double> gdValue) {
		this.gdValue = gdValue;
	}

	//************* シナリオデータのgetter() *************

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
