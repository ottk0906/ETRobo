package communication;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import body.Body;
import lejos.hardware.lcd.LCD;

/**
 * メッセージ制御クラス
 * <P> Bluetoothで走行体に接続後、コマンドプロンプトで「ipconfig」コマンドを実行し
 *     「イーサネット アダプター Bluetooth ネットワーク接続」の「IPv4アドレス」を「IPアドレス」とする。
 * <P> ポート番号は5000番を使用している。（ポート開放しておくこと）
 * @author 尾角 武俊
 *
 */
public class CommCtrl {

	private String ipAddress;	//接続先PCのIPアドレス
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
	public CommCtrl(String ipAddress, int port, int timeout, int msgCode) {
		this.ipAddress = ipAddress;
		this.port = port;
		this.timeout = timeout;
		this.msgCode = msgCode;

		//走行パラメータ転送要求の再送用にメッセージ送信用の情報を記憶しておく
		Body.paraFileMgt.setIpAddress(ipAddress);
		Body.paraFileMgt.setPort(port);
		Body.paraFileMgt.setTimeout(timeout);
		Body.paraFileMgt.setMsgCode(msgCode);
	}

	/**
	 * メッセージを送信する
	 * <P> メッセージ送信時に都度、無線通信デバイスとのコネクションを確立、メッセージを送信、コネクション切断する。
	 *     ※走行体からのメッセージは無線通信デバイスの経路探索等の動作開始の契機にするために使用するのみのため
	 *       常時、通信は発生しない。
	 */
    public void sendMessage() {

		Socket socket = null;

		try {
	        //LCD.clear();
	        //LCD.drawString("Connecting...",0,1);

	        //接続先のIPアドレスとポートを指定してSocketAddressオブジェクトを生成する
	        InetSocketAddress endpoint = new InetSocketAddress(ipAddress, port);
	        //接続されていないソケットを作成する
	        socket = new Socket();
	        //指定されたタイムアウト値を使って、サーバーに接続する
	        socket.connect(endpoint,  timeout);

			//LCD.drawString("Connected!!",0,2);

			BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            //LCD.drawString("Sending...",0,3);

            // 入力ストリームの内容を全て送信する
			output.write(msgCode);
            output.flush();

            //LCD.drawString("Send:" + msgCode,0,4);

		} catch (SocketException e) {
			LCD.drawString("SocketException",0,5);
		} catch (SocketTimeoutException e) {
			LCD.drawString("SocketTimeoutException",0,5);
		} catch (Exception e) {
			LCD.drawString("Exception",0,5);
		} finally {
			try {
				//サーバから切断する
				socket.close();

				//LCD.drawString("closed!!",0,6);
			} catch (IOException e) {
				LCD.drawString("IOException",0,5);
			}
		}
	}

}
