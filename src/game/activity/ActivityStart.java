package game.activity;

import fileIO.CsvWrite;

/**
 * 競技開始ファイル作成クラス
 * ※競技開始時に「start.dat」ファイル(中身無しのファイル)を作成し、無線通信デバイスがファイル存在を検知して処理を開始する方式に変更
 * @author 尾角 武俊
 *
 */
public class ActivityStart extends Activity {

	private final String START_FILE_NAME = "start.dat";

	@Override
	public void doActivity() {
		//CSVファイル出力クラスのインスタンスを生成する
		CsvWrite csvWrite = new CsvWrite();
		//競技開始ファイルを作成する（空ファイル）
		csvWrite.writeFile(START_FILE_NAME, "", false);
	}

}
