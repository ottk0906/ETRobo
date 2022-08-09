package fileIO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import log.LogData;

/**
 * CSVファイル出力クラス
 * @author 尾角 武俊
 */
public class CsvWrite {

	private String headerString;

	/**
	 * ヘッダー文字列を設定する
	 * @param	headerString	ヘッダー文字列
	 */
	public void setHeaderString(String headerString) {
		this.headerString = headerString;
	}

	/**
	 * CSVファイル出力
	 * @param	fileName	ファイル名
	 * @param	LogData		出力データリスト
	 * @param	appendMode	アペンドモード(True：追記 / False：上書)
	 * @param	outHeader	ヘッダー情報出力可否(True：出力 / False：未出力)
	 * @throws IOException
	 */
	public void writeCsvFile(String fileNme, List<LogData> logList, boolean appendMode, boolean outHeader) {

		File file = null;
		FileWriter fw = null;
		BufferedWriter bw = null;

		try {
			file = new File(fileNme);
			fw = new FileWriter(file, appendMode);
			bw = new BufferedWriter(fw);

			StringBuilder sb = new StringBuilder();
			//ヘッダ部を出力する
			if(outHeader) {
				sb.append(headerString).append("\r\n");
			}
			//データ部を出力する
			for (LogData data : logList) {
				sb.append(data.toString());
			}
			bw.write(sb.toString());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * CSVファイル削除
	 * @param	fileName	ファイル名
	 */
	public void deleteCsvFile(String fileNme) {
        try {
	        //CSVファイルが既に存在する場合は削除する
	        File file = new File(fileNme);
	        if (file.exists()) file.delete();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	/**
	 * ログファイルのサフィックス値を取得する
	 * @return	String サフィックス値
	 */
	public String getLogFileSuffix() {

		//フィルタを作成する
		FilenameFilter filter = new FilenameFilter() {

			public boolean accept(File file, String str){
				//指定文字列でフィルタする
				//indexOfは指定した文字列が見つからなかったら-1を返す
				if (str.indexOf("logSelfPos")  != -1){
					return true;
				}else{
					return false;
				}
			}
		};

		//カレントディレクトリのパスを取得する
		Path tmpPath1 = Paths.get("");
	    Path tmpPath2 = tmpPath1.toAbsolutePath();
	    String curPath = tmpPath2.toString();
	    //カレントディレクトリにある「logSelfPosXX.csv」の一覧を取得する
	    File[] files = new File(curPath).listFiles(filter);

	    //サフィックス値を初期化する
	    int tmpSuffix = 0;

	    //ファイル一覧のサフィックス（「logSelfPosXX.csv」のXXの部分）を比較し、最大値を取得する
	    for(File file : files) {
	    	//ファイル名の「logSelfPos」の後ろに追加したサフィックス値を取得する
	    	String tmp = file.getName().substring(10, file.getName().length() - 4);
	    	//サフィックス値の最大値を算出する
	    	if(tmpSuffix < Integer.parseInt(tmp)) {
	    		tmpSuffix = Integer.parseInt(tmp);
	    	}
	    }

	    //現在ログファイルに付与さえれているサフィックス値の最大値に1を加えた値を新しいサフィックス値とする
	    tmpSuffix = tmpSuffix +1;

		return String.valueOf(tmpSuffix);
	}

}
