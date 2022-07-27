package fileIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import scenario.ScenarioData;

/**
 * CSVファイル入力クラス
 * @author 尾角 武俊
 */
public class CsvRead {

	/**
	 * シナリオファイルを読み込む
	 * @param	fileNme				ファイル名
	 * @return List<ScenarioData>	シナリオデータリスト
	 */
	public List<ScenarioData> readScenarioFile(String fileNme) {

		List<ScenarioData> ScenarioList = new ArrayList<ScenarioData>();
		List<String> csvDataArr = new ArrayList<String>();

		//CSVファイルを読み込む
		csvDataArr = readCsvFile(fileNme);

		final int SCENE_NO_ROW = 1;					//シーン番号の位置
		final int STATE_NO_ROW = 2;					//状態番号の位置
		final int ACT_NO_ROW = 3;					//動作番号の位置
		final int ACT_DETAIL_NUM_ROW = 4;			//動作パラメータ数の位置
		final int GUARD_NO_ROW = 15;					//遷移条件番号の位置
		final int GUARD_LOGIC_OPE_ROW = 16;			//遷移条件論理演算子の位置
		final int GUARD_DETAIL_NUM_ROW = 17;		//遷移条件パラメータ数の位置

		List<Double> actValue;		//動作のパラメータ
		List<Double> gdValue;		//遷移条件のパラメータ

		//CSVデータを1行毎に処理して、シナリオデータを生成する
		for(String tmpLine : csvDataArr) {
			//1行分のデータをカンマで区切って、各項目のデータを配列にセットする
			String[] tmpData = tmpLine.split(",");

			//シナリオデータのインスタンスを生成する
			ScenarioData tmpSnData = new ScenarioData();

			//シーン番号
			tmpSnData.setSceneNo(Integer.parseInt(tmpData[SCENE_NO_ROW]));
			//状態番号
			tmpSnData.setStateNo(Integer.parseInt(tmpData[STATE_NO_ROW]));
			//動作番号
			tmpSnData.setActNo(Integer.parseInt(tmpData[ACT_NO_ROW]));
			//動作のパラメータ
			actValue = new ArrayList<Double>();
			for(int i = 1; i <= Integer.parseInt(tmpData[ACT_DETAIL_NUM_ROW]); i++ ) {
				actValue.add(Double.parseDouble(tmpData[ACT_DETAIL_NUM_ROW + i]));
			}
			tmpSnData.setActValue(actValue);
			//遷移条件番号
			tmpSnData.setGdNo(Integer.parseInt(tmpData[GUARD_NO_ROW]));
			//遷移条件論理演算子
			tmpSnData.setGdLogicalOperator(Integer.parseInt(tmpData[GUARD_LOGIC_OPE_ROW]));
			//遷移条件パラメータ
			gdValue = new ArrayList<Double>();
			for(int i = 1; i <= Integer.parseInt(tmpData[GUARD_DETAIL_NUM_ROW]); i++ ) {
				gdValue.add(Double.parseDouble(tmpData[GUARD_DETAIL_NUM_ROW + i]));
			}
			tmpSnData.setGdValue(gdValue);

			//シナリオリストにシナリオデータを追加する
			ScenarioList.add(tmpSnData);
		}

		return ScenarioList;

	}

	/**
	 * CSVファイル入力
	 * @param	fileName		ファイル名
	 * @return	List<String> 	CSVデータリスト（1行分のデータを格納）
	 */
	public List<String> readCsvFile(String fileNme) {

		File file = null;
		FileReader fr = null;
		BufferedReader br = null;
		String txtLine;
		List<String> retList = new ArrayList<String>();

		try {
			file = new File(fileNme);
			fr = new FileReader(file);
			br = new BufferedReader(fr);

			//読み込み対象のファイルが存在している場合
			if (file.exists()) {
				//1行分のデータを読み込む
				while ((txtLine = br.readLine()) != null) {
					//1行分のデータを配列にセットする
					retList.add(txtLine);
				}
				br.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return retList;

	}

}
