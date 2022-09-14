package scenario;

import communication.CommCtrl;
import fileIO.CsvRead;
import game.Game;
import game.state.StateConquest;
import game.state.StateRun;

/**
 * 走行パラメータファイル追加管理クラス
 * <p>走行パラメータファイルの有無をチェックし、ファイルが存在する場合、走行シナリオを生成する
 */
public class ParaFileMgt {

	private final String  EV3_GAME_SCENARIO_LIST = "EV3_Game_ScenarioList.csv";				//ゲーム攻略用の走行パラメータファイル名
	private final String  EV3_GAME_SCENARIO_LIST_BAK = "EV3_Game_ScenarioList.bak";			//ゲーム攻略用の走行パラメータファイル名のバックアップファイル名
	private final String  EV3_DEGRADATION_SCENARIO_LIST = "EV3_Degradation_ScenarioList.csv";	//縮退運転用の走行パラメータファイル名

	private Game game;							//競技クラス
	private CsvRead csvRead;					// CSVファイル入力クラス
	private boolean baseCourseFinish;			//ベーシックコースゴール到達判定フラグ
	private boolean gameScenarioReady;		//ゲーム攻略準備完了判定フラグ

	//無線通信デバイスへの走行パラメータ転送要求の再送信用（開始時に送信した際の情報）
	private String ipAddress;	//接続先PCのIPアドレス
	private int port;			//ポート番号
	private int timeout;		//コネクション確立時のタイムアウト
	private int msgCode;		//メッセージコード

	private boolean msgResendFlg;	//走行パラメータ転送要求再送フラグ

	/**
	 * コンストラクタ
	 */
	public ParaFileMgt(){
		csvRead = new CsvRead();
		baseCourseFinish = false;
		gameScenarioReady = false;
		msgResendFlg = false;
	}

	/**
	 * 実施する
	 */
	public void run() {

		//LCD.clear();
		//LCD.drawString("ReadyFlg " + getGameScenarioReady(), 0, 3);
		//LCD.drawString("BaseFlg " + getBaseCourseFinish(), 0, 4);
		//LCD.drawString("State " + game.toString() , 0, 5);

		//「ベーシックコース走行状態」、「ゲーム攻略状態」の場合、ゲーム攻略用の走行パラメータファイルのチェックを実行する
        if (game.getStatus() instanceof StateRun | game.getStatus() instanceof StateConquest) {
			//ゲーム攻略開始準備が完了していない場合
			if(!getGameScenarioReady()) {
		    	//ゲーム攻略用の走行パラメータファイルが存在する場合
		    	if(csvRead.isFileExists(EV3_GAME_SCENARIO_LIST)) {

		    		//LCD.drawString("File Exitst " + Body.stopwatch.elapsed(), 0, 2);

		    		//ゲーム攻略用の走行パラメータファイルを読み込み、走行データを生成する
		    		ScenarioMake Scenario = new ScenarioMake();
		    		Scenario.makeScenarioList(EV3_GAME_SCENARIO_LIST);
		    		Scenario.makeMoveStatus();
		    		//読み込んみ済みの走行パラメータファイルをリネームする
		    		//csvRead.renameCsvFile(EV3_GAME_SCENARIO_LIST, EV3_GAME_SCENARIO_LIST_BAK);
		    		//ゲーム攻略準備完了フラグにTrue(準備完了)を設定する
		    		setGameScenarioReady(true);

		    	//ゲーム攻略用の走行パラメータファイルが存在していない場合
		    	} else {

		    		//LCD.drawString("File Not Exitst " + Body.stopwatch.elapsed(), 0, 1);

		    		//ベーシックコースのゴールに到達している場合
		    		if(getBaseCourseFinish()) {

		    			//走行パラメータ転送要求の再送は1回のみとする
		    			if(!msgResendFlg) {
			    			//無線通信デバイスに走行パラメータ転送要求を送信する
			    	    	CommCtrl commCtrl = new CommCtrl(getIpAddress(), getPort(), getTimeout(), getMsgCode());
			    	    	commCtrl.sendMessage();
			    	    	msgResendFlg = true;
		    			}

		    			//現在の競技状態が「ゲーム攻略状態」の場合
		    			if(game.getStatus() instanceof StateConquest) {
		    	    		//縮退運転用の走行パラメータファイルを読み込み、走行データを生成する
		    	    		ScenarioMake Scenario = new ScenarioMake();
		    	    		Scenario.makeScenarioList(EV3_DEGRADATION_SCENARIO_LIST);
		    	    		Scenario.makeMoveStatus();
		    	    		//ゲーム攻略準備完了フラグにTrue(準備完了)を設定する
		    	    		setGameScenarioReady(true);
		    			}
		    		}
		    	}
			}
        }
	}

    /**
     * gameクラスオブジェクトを設定する
     * @param	game	gameクラスのインスタンス
     */
	public void setGameInstance(Game game) {
		this.game = game;
	}

	//************* getter() *************

	/**
	 * ベーシックコースゴール到達判定フラグを取得する
	 * @return 判定結果（True：ゴール到達 / False：走行中）
	 */
	public boolean getBaseCourseFinish() {
		return baseCourseFinish;
	}

	/**
	 * ゲーム攻略準備完了判定フラグを取得する
	 * @return 判定結果（True：準備完了 / False：準備中）
	 */
	public boolean getGameScenarioReady() {
		return gameScenarioReady;
	}

	/**
	 * 走行パラメータ転送要求送信用の接続先PCのIPアドレスを取得する
	 * @return 接続先PCのIPアドレス
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * 走行パラメータ転送要求送信用の接続先PCのポート番号を取得する
	 * @return 接続先PCのポート番号
	 */
	public int getPort() {
		return port;
	}

	/**
	 * 走行パラメータ転送要求送信用のタイムアウト時間を取得する
	 * @return タイムアウト時間
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * 走行パラメータ転送要求送信用のメッセージコードを取得する
	 * @return メッセージコード
	 */
	public int getMsgCode() {
		return msgCode;
	}

	//************* setter() *************

	/**
	 * ベーシックコースゴール到達判定フラグを設定する
	 * @param flg フラグ（True：ゴール到達 / False：走行中）
	 */
	public void setBaseCourseFinish(boolean flg) {
		baseCourseFinish = flg;
	}

	/**
	 * ゲーム攻略準備完了判定フラグを設定する
	 * @param flg フラグ（True：準備完了 / False：準備中）
	 */
	public void setGameScenarioReady(boolean flg) {
		gameScenarioReady = flg;
	}

	/**
	 * 走行パラメータ転送要求送信用の接続先PCのIPアドレスを取得する
	 * @return 接続先PCのIPアドレス
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * 走行パラメータ転送要求送信用の接続先PCのポート番号を取得する
	 * @return 接続先PCのポート番号
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * 走行パラメータ転送要求送信用のタイムアウト時間を取得する
	 * @return タイムアウト時間
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * 走行パラメータ転送要求送信用のメッセージコードを取得する
	 * @return メッセージコード
	 */
	public void setMsgCode(int msgCode) {
		this.msgCode = msgCode;
	}

}
