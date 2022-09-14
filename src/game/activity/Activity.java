package game.activity;

import body.Body;
import fileIO.CsvWrite;
import task.Beep;


/**
 * 動作クラス
 * @author 後藤 聡文
 *
 */
public abstract class Activity {

    /** Activity名 */
    protected String name;

	/**
	 * 前動作を実行する
	 * 状態に遷移したときに1度だけ実行される動作
	 * UMLステートマシン図のentryアクション
	 */
	public void entryAction() {
		Beep.ring();

		//CSVファイル出力クラスのインスタンスを生成する
		CsvWrite csvWrite = new CsvWrite();
		//ログに出力する文言をセットする
		String outStr = "entry Activity ---> " + name + "\r\n";

		String fileName="";
		for(int iLoop = 0; iLoop < 4; iLoop++ ) {
			//ファイル名を生成する
			switch(iLoop){
			case 0:
				fileName = "log" + Body.logFileSuffix + ".csv";
				break;
			case 1:
				fileName = "RGBLog" + Body.logFileSuffix + ".csv";
				break;
			case 2:
				fileName = "logHSV" + Body.logFileSuffix + ".csv";
				break;
			case 3:
				fileName = "logHSL" + Body.logFileSuffix + ".csv";
				break;
			}
			//CSVファイルに出力する
			csvWrite.writeCsvFile(fileName, outStr, true);
		}
	}

	/**
	 * 継続動作を実行する
	 * 状態にいる間、継続して実行される動作
	 * UMLステートマシン図のdoアクティビティ
	 */
	public abstract void doActivity();

	/**
	 * 後動作を実行する
	 * 状態から離れる直前に1度だけ実行される動作
	 * UMLステートマシン図のexitアクション
	 */
	public void exitAction() {
		//処理なし
	}

    /**
     * Activity名を取得する
     * @return Activity名
     */
	public String getName() {
		return name;
	}

}

