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
	 * @param	fileName			ファイル名
	 * @param init					命令セット呼出フラグ
	 * @return List<ScenarioData>	シナリオデータリスト
	 */
	public List<ScenarioData> readScenarioFile(String fileName, boolean instructionSetFlg) {

		List<ScenarioData> ScenarioList = new ArrayList<ScenarioData>();
		List<String> csvDataArr = new ArrayList<String>();

		//CSVファイルを読み込む
		csvDataArr = readCsvFile(fileName);

		final int SCENE_NO_ROW = 0;					//シーン番号の位置
		final int STATE_NO_ROW = 1;					//状態番号の位置
		final int ACT_NO_ROW = 2;					//動作番号の位置
		final int ACT_DETAIL_NUM_ROW = 3;			//動作パラメータ数の位置
		final int GUARD_NO_ROW = 14;					//遷移条件番号の位置
		final int GUARD_LOGIC_OPE_ROW = 15;			//遷移条件論理演算子の位置
		final int GUARD_DETAIL_NUM_ROW = 16;		//遷移条件パラメータ数の位置

		final String ACT_NO_INSTRUCTION_SET = "7";	//命令セットの動作番号
		final int SCENE_NO_ADD_VALUE = 1000;		//命令セットでのシーン番号にインクリメントする値

		List<String> actValue;		//動作のパラメータ
		List<Double> gdValue;		//遷移条件のパラメータ

		//CSVデータを1行毎に処理して、シナリオデータを生成する
		for(String tmpLine : csvDataArr) {
			//1行分のデータをカンマで区切って、各項目のデータを配列にセットする
			String[] tmpData = tmpLine.split(",");

			//命令セットの場合
			if(tmpData[ACT_NO_ROW].equals(ACT_NO_INSTRUCTION_SET)) {

				//シナリオリストを初期化する
				List<ScenarioData> tmpSnList = new ArrayList<ScenarioData>();
				//動作パラメータ1の値（命令セットの対象ファイル名）を取得し、自分自信を再帰的に呼び出す
				tmpSnList = readScenarioFile(tmpData[ACT_DETAIL_NUM_ROW + 1] , true);

				//シナリオリストにシナリオデータを追加する
				for(ScenarioData snData : tmpSnList) {
					ScenarioList.add(snData);
				}

			} else {

				//シナリオデータのインスタンスを生成する
				ScenarioData tmpSnData = new ScenarioData();

				//シーン番号
				//通常のシナリオファイル読み込みの場合
				if(!instructionSetFlg) {
					tmpSnData.setSceneNo(Integer.parseInt(tmpData[SCENE_NO_ROW]));
				//命令セットでのファイル読み込みの場合
				} else {
					//シーン番号に定数値を付加して、シーン番号の競合を防止する
					tmpSnData.setSceneNo(Integer.parseInt(tmpData[SCENE_NO_ROW]) + SCENE_NO_ADD_VALUE);
				}

				//状態番号
				tmpSnData.setStateNo(Integer.parseInt(tmpData[STATE_NO_ROW]));
				//動作番号
				tmpSnData.setActNo(Integer.parseInt(tmpData[ACT_NO_ROW]));
				//動作のパラメータ
				actValue = new ArrayList<String>();
				for(int i = 1; i <= Integer.parseInt(tmpData[ACT_DETAIL_NUM_ROW]); i++ ) {
					actValue.add(tmpData[ACT_DETAIL_NUM_ROW + i]);
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
		}

	    //---> Add 2022/08/10 T.Okado Debug用
		//write(ScenarioList);
	    //<--- Add 2022/08/10 T.Okado

		return ScenarioList;

	}

	/**
	 * CSVファイル入力
	 * @param	fileName		ファイル名
	 * @return	List<String> 	CSVデータリスト（1行分のデータを格納）
	 */
	public List<String> readCsvFile(String fileName) {

		File file = null;
		FileReader fr = null;
		BufferedReader br = null;
		String txtLine;
		List<String> retList = new ArrayList<String>();

		try {
			file = new File(fileName);
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

	/**
	 * CSVファイル存在チェック
	 * @param	fileName	ファイル名
	 * @return	boolean 	ファイル存在チェック結果(True:存在する / False：存在しない)
	 */
	public boolean isFileExists(String fileName){
		File file = new File(fileName);
		return file.exists();
	}

	/**
	 * CSVファイルをリネームする
	 * @param	src		リネーム前のファイル名
	 * @param	des		リネーム後のファイル名
	 * @return	boolean リネーム結果(True:リネーム成功 / False：リネーム失敗)
	 */
	public boolean renameCsvFile(String src, String des) {
		File file1 = new File(src);
        File file2 = new File(des);
        //リネーム後のファイルが既に存在する場合は削除する
        if (file2.exists()) file2.delete();
        //ファイルをリネームする
        boolean success = file1.renameTo(file2);
        return success;
	}

/*
    //---> Add 2022/08/10 T.Okado Debug用
	public void write(List<ScenarioData> snData) {
        try {

        	File file = new File("CsvRead.csv");
            FileWriter fw = new FileWriter(file,true);
            BufferedWriter bw = new BufferedWriter(fw);

            StringBuilder sb = new StringBuilder();
            for(ScenarioData data : snData) {
	            sb.append(data.getSceneNo()).append(",");
	            sb.append(data.getStateNo()).append(",");
	            sb.append(data.getActNo()).append(",");
	            for(Double tmp1 : data.getActValue()) {
		            sb.append(tmp1).append(",");
	            }
	            sb.append(data.getGdNo()).append(",");
	            sb.append(data.getGdLogicalOperator());
	            for(Double tmp2 : data.getGdValue()) {
		            sb.append(",").append(tmp2);
	            }
	            sb.append("\r\n");
        	}
            bw.write(sb.toString());
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //<--- Add 2022/08/10 T.Okado
*/

}
