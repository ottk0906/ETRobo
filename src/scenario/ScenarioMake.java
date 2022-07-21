package scenario;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import game.activity.Activity;
import game.activity.ActivityCalibrationBlack;
import game.activity.ActivityCalibrationWhite;
import game.activity.ActivityRun;
import game.activity.ActivityRunPID;
import game.guard.GuardMain;
import game.state.State;
import game.state.StateCalibrationBlack;
import game.state.StateCalibrationWhite;
import game.state.StateEnd;
import game.state.StateRun;
import game.state.StateWaitStart;

/**
 * 競技状態作成クラス
 * <p>シナリオ情報を読み込んで競技状態を生成する
 */
public class ScenarioMake {

	List<ScenarioData> ScenarioList;

	/**
	 * コンストラクタ
	 */
	public ScenarioMake() {
	    //---> Add 2022/07/13 T.Okado Debug用
        try {
	        //ログファイルが既に存在する場合は削除する
	        File file = new File("ScenarioMake.csv");
	        if (file.exists()) file.delete();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
        //<--- Add 2022/07/13 T.Okado
	}

	/**
	 * シナリオリストを作成する
	 * @param mode	データ入力モード(0:csvファイル / 1:無線通信デバイス)
	 */
	public void makeScenarioList(int mode) {
		//シナリオリストを初期化する
		ScenarioList = new ArrayList<ScenarioData>();

		switch(mode) {
		//CSVファイルを読み込んでシナリオデータを作成する
		case 0:

			int sceneNo;
			int stateNo;
			int actNo;
			int gdNo;
			int gdLogicalOperator;
			List<Double> actValue;
			List<Double> gdValue;

			//白キャリブレーション
			sceneNo = 0;
			stateNo = 0;
			actNo = 0;
			actValue = new ArrayList<Double>();
			gdNo = 0;
			gdLogicalOperator = 0;
			gdValue = new ArrayList<Double>();

			ScenarioList.add(new ScenarioData(sceneNo, stateNo, actNo, actValue, gdNo, gdLogicalOperator, gdValue));

			//黒キャリブレーション
			sceneNo = 1;
			stateNo = 1;
			actNo = 1;
			actValue = new ArrayList<Double>();
			gdNo = 0;
			gdLogicalOperator = 0;
			gdValue = new ArrayList<Double>();

			ScenarioList.add(new ScenarioData(sceneNo, stateNo, actNo, actValue, gdNo, gdLogicalOperator, gdValue));

			//Wait
			sceneNo = 2;
			stateNo = 2;
			actNo = 2;
			actValue = new ArrayList<Double>();
			actValue.add(0.0);
			actValue.add(0.0);
			gdNo = 0;
			gdLogicalOperator = 0;
			gdValue = new ArrayList<Double>();

			ScenarioList.add(new ScenarioData(sceneNo, stateNo, actNo, actValue, gdNo, gdLogicalOperator, gdValue));

			//走行
			sceneNo = 3;
			stateNo = 3;
			actNo = 2;
			actValue = new ArrayList<Double>();
			actValue.add(200.0);
			actValue.add(0.0);

			//500mmで停止
			gdNo = 1;
			gdLogicalOperator = 0;
			gdValue = new ArrayList<Double>();
			gdValue.add(500.0);
			ScenarioList.add(new ScenarioData(sceneNo, stateNo, actNo, actValue, gdNo, gdLogicalOperator, gdValue));

			//1000mmで停止
			gdNo = 1;
			gdLogicalOperator = 0;
			gdValue = new ArrayList<Double>();
			gdValue.add(1000.0);

			ScenarioList.add(new ScenarioData(sceneNo, stateNo, actNo, actValue, gdNo, gdLogicalOperator, gdValue));

			//2000msで停止
			gdNo = 2;
			gdLogicalOperator = 0;
			gdValue = new ArrayList<Double>();
			gdValue.add(2000.0);

			ScenarioList.add(new ScenarioData(sceneNo, stateNo, actNo, actValue, gdNo, gdLogicalOperator, gdValue));

			//2000msで停止
			gdNo = 2;
			gdLogicalOperator = 1;
			gdValue = new ArrayList<Double>();
			gdValue.add(4000.0);

			ScenarioList.add(new ScenarioData(sceneNo, stateNo, actNo, actValue, gdNo, gdLogicalOperator, gdValue));

			//5000mmで停止
			gdNo = 1;
			gdLogicalOperator = 1;
			gdValue = new ArrayList<Double>();
			gdValue.add(5000.0);

			ScenarioList.add(new ScenarioData(sceneNo, stateNo, actNo, actValue, gdNo, gdLogicalOperator, gdValue));

			//7000msで停止
			gdNo = 2;
			gdLogicalOperator = 0;
			gdValue = new ArrayList<Double>();
			gdValue.add(7000.0);

			ScenarioList.add(new ScenarioData(sceneNo, stateNo, actNo, actValue, gdNo, gdLogicalOperator, gdValue));



			//終了
			sceneNo = 4;
			stateNo = 4;
			actNo = 2;
			actValue = new ArrayList<Double>();
			actValue.add(0.0);
			actValue.add(0.0);
			gdNo = 0;
			gdLogicalOperator = 1;
			gdValue = new ArrayList<Double>();

			ScenarioList.add(new ScenarioData(sceneNo, stateNo, actNo, actValue, gdNo, gdLogicalOperator, gdValue));

			break;
		//無線通信デバイスからデータを受信してシナリオデータを作成する
		case 1:

			break;
		}
	}

	/**
	 * 競技状態を生成する
	 */
	public void makeMoveStatus() {

		//tmpシナリオリストを生成する（シーン単位で1まとめにしたシナリオデータのリスト）
		List<ScenarioData> tmpSnList = new ArrayList<ScenarioData>();

		//tmpシナリオリストに最初のシナリオデータを登録する
		tmpSnList.add(ScenarioList.get(0));

		//2つ目のシナリオデータからループを開始する
		for(int iLoop = 1 ;iLoop < ScenarioList.size(); iLoop++){
			//write(1,snData.getSceneNo(),snData.getStateNo(),snData.getActNo(),snData.getGdNo());

			//シーン番号が変わった場合に、tmpシナリオリストの情報を競技状態を追加する
			if(tmpSnList.get(0).getSceneNo() !=  ScenarioList.get(iLoop).getSceneNo()) {
				getState(tmpSnList.get(0).getStateNo()).add(
						new GuardMain(tmpSnList),
						getActivity(tmpSnList.get(0).getActNo(),tmpSnList.get(0).getActValue())
				);

				for(int j = 0; j < tmpSnList.size(); j++ ) {
					write(2,tmpSnList.get(j).getSceneNo(),
							tmpSnList.get(j).getStateNo(),
							tmpSnList.get(j).getActNo() ,
							tmpSnList.get(j).getGdNo() );

				}

				//tmpシナリオリストを初期化する
				tmpSnList = new ArrayList<ScenarioData>();
			}
			//tmpシナリオリストにシナリオデータを追加する
			tmpSnList.add(ScenarioList.get(iLoop));

			write(1,tmpSnList.get(tmpSnList.size() - 1).getSceneNo(),
					tmpSnList.get(tmpSnList.size() - 1).getStateNo(),
					tmpSnList.get(tmpSnList.size() - 1).getActNo() ,
					tmpSnList.get(tmpSnList.size() - 1).getGdNo() );

		}

		//最後のtmpシナリオデータの情報を競技状態を追加する
		getState(tmpSnList.get(0).getStateNo()).add(
				new GuardMain(tmpSnList),
				getActivity(tmpSnList.get(0).getActNo(),tmpSnList.get(0).getActValue())
		);

		for(int j = 0; j < tmpSnList.size(); j++ ) {
			write(2,tmpSnList.get(j).getSceneNo(),
					tmpSnList.get(j).getStateNo(),
					tmpSnList.get(j).getActNo() ,
					tmpSnList.get(j).getGdNo() );

		}

	}

	/**
	 * 競技状態番号に関連付けられている競技状態を取得する
	 * @param stateNo	競技状態番号
	 */
	private State getState(int stateNo) {

		State tmpState = null;

		switch(stateNo) {
		//白キャリブレーション状態
		case 0:
			tmpState = StateCalibrationWhite.getInstance();
			break;
		//黒キャリブレーション状態
		case 1:
			tmpState = StateCalibrationBlack.getInstance();
			break;
		//待機状態
		case 2:
			tmpState = StateWaitStart.getInstance();
			break;
		//走行状態
		case 3:
			tmpState = StateRun.getInstance();
			break;
		//終了状態
		case 4:
			tmpState = StateEnd.getInstance();
			break;
		}

		return tmpState;

	}

	/**
	 * 動作定義番号に関連付けられている動作定義を取得する
	 * @param actNo	動作定義番号
	 * @param actValue	動作定義のパラメータ
	 */
	private Activity getActivity(int actNo, List<Double> actValue) {

		Activity tmpActivity = null;

		switch(actNo){
			//白キャリブレーション
			case 0:
				tmpActivity = new ActivityCalibrationWhite();
				break;
			//黒キャリブレーション
			case 1:
				tmpActivity = new ActivityCalibrationBlack();
				break;
			//通常走行
			case 2:
				tmpActivity = new ActivityRun(
						actValue.get(0).floatValue(),
						actValue.get(1).floatValue()
					);
				break;
			//PID走行
			case 3:
				tmpActivity = new ActivityRunPID(
						actValue.get(0).floatValue(),
						actValue.get(1).floatValue(),
						actValue.get(2).floatValue(),
						actValue.get(3).floatValue(),
						actValue.get(4).floatValue()
					);
				break;
		}

		return tmpActivity;

	}


    //---> Add 2022/07/13 T.Okado Debug用
	public void write(int no, int sceneNo, int stateNo, int actNo, int gdNo) {
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(no).append(",");
            sb.append(sceneNo).append(",");
            sb.append(stateNo).append(",");
            sb.append(actNo).append(",");
            sb.append(gdNo).append("\r\n");

        	File file = new File("ScenarioMake.csv");
            FileWriter fw = new FileWriter(file, true);	//アペンドモードで書き込む
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(sb.toString());
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //<--- Add 2022/07/13 T.Okado


}
