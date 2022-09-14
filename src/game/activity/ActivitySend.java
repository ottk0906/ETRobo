package game.activity;

import communication.CommCtrl;

/**
 * メッセージ送信動作クラス
 *
 * ★☆★☆ 本クラスは未使用                                                                                                    ★☆★☆
 * ★☆★☆ メッセージ送信での無線通信デバイス動作開始を廃止し、無線通信デバイスの開始ファイル監視での動作開始の方式に変更した。★☆★☆
 *
 * @author 尾角 武俊
 *
 */
public class ActivitySend extends Activity {

	private String ipAddress;	//接続先PCにIPアドレス
	private int port;			//ポート番号
	private int timeout;		//コネクション確立時のタイムアウト
	private int msgCode;		//メッセージコード

	/**
     * コンストラクタ
	 * @param ipAddress	IPアドレス
	 * @param port 		ポート番号
	 * @param timeout		ソケット接続時のタイムアウト(コネクション確立時、サーバ側が無反応の場合のタイムアウト時間(ms))
	 * @param msgCode		メッセージコード(サーバ（無線通信デバイス）に送信するメッセージコード)
     */
    public ActivitySend(String ipAddress, int port, int timeout, int msgCode) {
    	super.name = "ActivitySend";
		this.ipAddress = ipAddress;
		this.port = port;
		this.timeout = timeout;
		this.msgCode = msgCode;
    }

	/**
	 * 継続動作を実行する
	 * 状態にいる間、継続して実行される動作
	 * UMLステートマシン図のdoアクティビティ
	 */
    @Override
	public void doActivity() {
    	//無線通信デバイスへメッセージを送信する
    	CommCtrl commCtrl = new CommCtrl(ipAddress, port, timeout, msgCode);
    	commCtrl.sendMessage();
	}

}
