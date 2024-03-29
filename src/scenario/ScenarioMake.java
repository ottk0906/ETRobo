package scenario;

import java.util.ArrayList;
import java.util.List;

import fileIO.CsvRead;
import game.activity.Activity;
import game.activity.ActivityArm;
import game.activity.ActivityArmThrow;
import game.activity.ActivityBaseFinish;
import game.activity.ActivityCalibrationArm;
import game.activity.ActivityCalibrationBlack;
import game.activity.ActivityCalibrationWhite;
import game.activity.ActivityGameStartWait;
import game.activity.ActivityRun;
import game.activity.ActivityRunAccele;
import game.activity.ActivityRunOnOff;
import game.activity.ActivityRunPID;
import game.activity.ActivityRunPIDAccele;
import game.activity.ActivitySend;
import game.activity.ActivityStart;
import game.activity.ActivityTarget;
import game.guard.GuardMain;
import game.state.State;
import game.state.StateCalibrationArm;
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
		CsvRead csvRead = new CsvRead();
		scenarioList = csvRead.readScenarioFile(file, false);
	}

	/**
	 * 競技状態を生成する
	 */
	public void makeMoveStatus() {

		//tmpシナリオリストを生成する（シーン単位で1まとめにしたシナリオデータのリスト）
		List<ScenarioData> tmpSnList = new ArrayList<ScenarioData>();

		//tmpシナリオリストに最初のシナリオデータを登録する
		tmpSnList.add(scenarioList.get(0));
/*
	    //---> Add 2022/07/13 T.Okado Debug用
		int debugCnt = 1;
	    //<--- Add 2022/07/13 T.Okado
*/
		//2つ目のシナリオデータからループを開始する
		for(int iLoop = 1 ;iLoop < scenarioList.size(); iLoop++){

			//シーン番号が変わった場合に、tmpシナリオリストの情報を競技状態を追加する
			if(tmpSnList.get(0).getSceneNo() !=  scenarioList.get(iLoop).getSceneNo()) {
				getState(tmpSnList.get(0).getStateNo()).add(
						new GuardMain(tmpSnList),
						getActivity(tmpSnList.get(0).getActNo(),tmpSnList.get(0).getActValue())
				);
/*
			    //---> Add 2022/07/13 T.Okado Debug用
				for(int j = 0; j < tmpSnList.size(); j++ ) {
					write(debugCnt,tmpSnList.get(j).getSceneNo(),
							tmpSnList.get(j).getStateNo(),
							tmpSnList.get(j).getActNo() ,
							tmpSnList.get(j).getGdNo() );

				}
				debugCnt = debugCnt + 1;
			    //<--- Add 2022/07/13 T.Okado
*/
				//tmpシナリオリストを初期化する
				tmpSnList = new ArrayList<ScenarioData>();
			}
			//tmpシナリオリストにシナリオデータを追加する
			tmpSnList.add(scenarioList.get(iLoop));
		}

		//最後のtmpシナリオデータの情報を競技状態を追加する
		getState(tmpSnList.get(0).getStateNo()).add(
				new GuardMain(tmpSnList),
				getActivity(tmpSnList.get(0).getActNo(),tmpSnList.get(0).getActValue())
		);
/*
	    //---> Add 2022/07/13 T.Okado Debug用
		for(int j = 0; j < tmpSnList.size(); j++ ) {
			write(debugCnt,tmpSnList.get(j).getSceneNo(),
					tmpSnList.get(j).getStateNo(),
					tmpSnList.get(j).getActNo() ,
					tmpSnList.get(j).getGdNo() );
		}
	    //<--- Add 2022/07/13 T.Okado
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
		//アームキャリブレーション状態
		case 6:
			tmpState = StateCalibrationArm.getInstance();
			break;
		}

		return tmpState;

	}

	/**
	 * 動作定義番号に関連付けられている動作定義を取得する
	 * @param actNo	動作定義番号
	 * @param actValue	動作定義のパラメータ
	 */
	private Activity getActivity(int actNo, List<String> actValue) {

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
						Float.parseFloat(actValue.get(0)),
						Float.parseFloat(actValue.get(1))
				);
				break;
			//PID走行
			case 3:
				tmpActivity = new ActivityRunPID(
						Float.parseFloat(actValue.get(0)),
						Float.parseFloat(actValue.get(1)),
						Float.parseFloat(actValue.get(2)),
						Float.parseFloat(actValue.get(3)),
						Float.parseFloat(actValue.get(4)),
						Integer.parseInt(actValue.get(5))
				);
				break;
			//OnOff走行
			case 4:
				tmpActivity = new ActivityRunOnOff(
						Float.parseFloat(actValue.get(0)),
						Float.parseFloat(actValue.get(1))
				);
				break;
			//アーム操作(位置固定)
			case 5:
				tmpActivity =new ActivityArm(Float.parseFloat(actValue.get(0)));
				break;
			//アーム操作(スロー)
			case 6:
				tmpActivity = new ActivityArmThrow(
						Float.parseFloat(actValue.get(0)),
						Float.parseFloat(actValue.get(1))
				);
				break;
			//ベースコースゴール到達
			case 8:
				tmpActivity =new ActivityBaseFinish();
				break;
			//ゲーム攻略開始待ち
			case 9:
				tmpActivity =new ActivityGameStartWait();
				break;
			//アームキャリブレーション
			case 10:
				tmpActivity =new ActivityCalibrationArm(Float.parseFloat(actValue.get(0)));
				break;
			//目標明度設定
			case 11:
				tmpActivity =new ActivityTarget(Float.parseFloat(actValue.get(0)));
				break;
			//通常走行(加減速)
			case 12:
				tmpActivity = new ActivityRunAccele(
						Float.parseFloat(actValue.get(0)),
						Float.parseFloat(actValue.get(1)),
						Float.parseFloat(actValue.get(2)),
						Float.parseFloat(actValue.get(3))
				);
				break;
			//PID走行(加減速)
			case 13:
				tmpActivity = new ActivityRunPIDAccele(
						Float.parseFloat(actValue.get(0)),
						Float.parseFloat(actValue.get(1)),
						Float.parseFloat(actValue.get(2)),
						Float.parseFloat(actValue.get(3)),
						Float.parseFloat(actValue.get(4)),
						Integer.parseInt(actValue.get(5)),
						Float.parseFloat(actValue.get(6)),
						Float.parseFloat(actValue.get(7))
				);
				break;
			//メッセージ送信
			case 14:
				tmpActivity =new ActivitySend(
						actValue.get(0),
						Integer.parseInt(actValue.get(1)),
						Integer.parseInt(actValue.get(2)),
						Integer.parseInt(actValue.get(3))
				);
				break;
			//競技開始ファイル作成
			case 15:
				tmpActivity = new ActivityStart();
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
