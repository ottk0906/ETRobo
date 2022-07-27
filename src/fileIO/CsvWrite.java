package fileIO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

}
