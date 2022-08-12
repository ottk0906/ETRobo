package scenario;

import java.util.ArrayList;
import java.util.List;

import fileIO.CsvRead;
import game.activity.Activity;
import game.activity.ActivityArm;
import game.activity.ActivityArmThrow;
import game.activity.ActivityCalibrationBlack;
import game.activity.ActivityCalibrationWhite;
import game.activity.ActivityRun;
import game.activity.ActivityRunOnOff;
import game.activity.ActivityRunPID;
import game.guard.GuardMain;
import game.state.State;
import game.state.StateCalibrationBlack;
import game.state.StateCalibrationWhite;
import game.state.StateConquest;
import game.state.StateEnd;
import game.state.StateRun;
import game.state.StateWaitStart;

/**
 * 競技状態作成クラス
 * <p>シナリオ情報を読み込んで競技状態を生成する
 */
public class ScenarioMake {

	List<ScenarioData> scenarioList;

	/**
	 * コンストラクタ
	 */
	public ScenarioMake() {

/*
		//---> Add 2022/07/13 T.Okado Debug用
        try {
	        //ログファイルが既に存在する場合は削除する
	        File file = new File("ScenarioMake.csv");
	        if (file.exists()) file.delete();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
        //<--- Add 2022/07/13 T.Okado
*/
	}

	/**
	 * シナリオリストを作成する
	 * @param file
	 */
	public void makeScenarioList(String file) {
		//シナリオリストを初期化する
		scenarioList = new ArrayList<ScenarioData>();

		//シナリオリストファイルを読み込む
		CsvRead CsvRead = new CsvRead();
		scenarioList = CsvRead.readScenarioFile(file, false);
	}

	/**
	 * 競技状態を生成する
	 */
	public void makeMoveStatus() {

		//tmpシナリオリストを生成する（シーン単位で1まとめにしたシナリオデータのリスト）
		List<ScenarioData> tmpSnList = new ArrayList<ScenarioData>();

		//tmpシナリオリストに最初のシナリオデータを登録する
		tmpSnList.add(scenarioList.get(0));

		//2つ目のシナリオデータからループを開始する
		for(int iLoop = 1 ;iLoop < scenarioList.size(); iLoop++){
			//write(1,snData.getSceneNo(),snData.getStateNo(),snData.getActNo(),snData.getGdNo());

			//シーン番号が変わった場合に、tmpシナリオリストの情報を競技状態を追加する
			if(tmpSnList.get(0).getSceneNo() !=  scenarioList.get(iLoop).getSceneNo()) {
				getState(tmpSnList.get(0).getStateNo()).add(
						new GuardMain(tmpSnList),
						getActivity(tmpSnList.get(0).getActNo(),tmpSnList.get(0).getActValue())
				);
/*
				for(int j = 0; j < tmpSnList.size(); j++ ) {
					write(2,tmpSnList.get(j).getSceneNo(),
							tmpSnList.get(j).getStateNo(),
							tmpSnList.get(j).getActNo() ,
							tmpSnList.get(j).getGdNo() );

				}
*/
				//tmpシナリオリストを初期化する
				tmpSnList = new ArrayList<ScenarioData>();
			}
			//tmpシナリオリストにシナリオデータを追加する
			tmpSnList.add(scenarioList.get(iLoop));
/*
			write(1,tmpSnList.get(tmpSnList.size() - 1).getSceneNo(),
					tmpSnList.get(tmpSnList.size() - 1).getStateNo(),
					tmpSnList.get(tmpSnList.size() - 1).getActNo() ,
					tmpSnList.get(tmpSnList.size() - 1).getGdNo() );
*/
		}

		//最後のtmpシナリオデータの情報を競技状態を追加する
		getState(tmpSnList.get(0).getStateNo()).add(
				new GuardMain(tmpSnList),
				getActivity(tmpSnList.get(0).getActNo(),tmpSnList.get(0).getActValue())
		);
/*
		for(int j = 0; j < tmpSnList.size(); j++ ) {
			write(2,tmpSnList.get(j).getSceneNo(),
					tmpSnList.get(j).getStateNo(),
					tmpSnList.get(j).getActNo() ,
					tmpSnList.get(j).getGdNo() );

		}
*/
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
		//ベーシックコース走行状態
		case 3:
			tmpState = StateRun.getInstance();
			break;
		//ゲーム攻略状態
		case 4:
			tmpState = StateConquest.getInstance();
			break;
		//終了状態
		case 5:
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
					actValue.get(4).floatValue(),
					actValue.get(5).intValue()
				);
				break;
			//OnOff走行
			case 4:
				tmpActivity = new ActivityRunOnOff(
						actValue.get(0).floatValue(),
						actValue.get(1).floatValue()
					);
					break;
			//アーム操作(位置固定)
			case 5:
				tmpActivity =new ActivityArm(actValue.get(0).floatValue());
				break;
			//アーム操作(スロー)
			case 6:
				tmpActivity =new ActivityArmThrow(
					actValue.get(0).floatValue(),
					actValue.get(1).floatValue()
				);
				break;
		}

		return tmpActivity;

	}


/*

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
*/

}
