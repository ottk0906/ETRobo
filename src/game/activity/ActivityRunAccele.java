package game.activity;

import java.util.HashMap;

import body.Body;

/**
 * 通常走行(加減速)動作クラス
 * @author 尾角 武俊
 *
 */
public class ActivityRunAccele extends Activity  {

	protected float targetForward;								//目標速度(mm/秒)
	protected float turn;											//目標回転角速度(度/秒)
	protected float second;										//加減速範囲(ms)
	private float beforeForward;									//動作開始時の送応対の速度
	private boolean initFlg;										//初回動作フラグ
	private int maxCnt;											//実行回数の最大値
	private int nowCnt;											//現在の実行回数
	private HashMap<Integer,Float> mapCngVal = new HashMap<>();	//増分値(減分値)格納用マップ
	private float setForwardVal;									//現在設定中の速度
	private float adjustSpeed;									//調整加速量(減速量)

	/**
	 * コンストラクタ
	 * @param forward		目標速度(mm/秒)
	 * @param turn			目標回転角速度(度/秒)
	 * @param second		加減速範囲(ms)
	 * @param adjustSpeed	調整加速量(減速量)
	 */
	public ActivityRunAccele(float targetForward, float turn, float second, float adjustSpeed) {
		super.name = "ActivityRunAccele";
		this.targetForward = targetForward;
		this.turn = turn;
		this.second = second;
		this.adjustSpeed = adjustSpeed;
		this.maxCnt = 0;
		this.nowCnt = 0;
		this.setForwardVal = 0;
		this.initFlg = true;
	}

	/**
	 * コンストラクタ
	 * @param forward		目標速度(mm/秒)
	 * @param turn			目標回転角速度(度/秒)
	 * @param second		加減速範囲(ms)
	 * @param adjustSpeed	調整加速量(減速量)
	 * @param activityName	アクティビティ名
	 */
	public ActivityRunAccele(float targetForward, float turn, float second, float adjustSpeed, String activityName) {
		super.name = activityName;
		this.targetForward = targetForward;
		this.turn = turn;
		this.second = second;
		this.adjustSpeed = adjustSpeed;
		this.maxCnt = 0;
		this.nowCnt = 0;
		this.setForwardVal = 0;
		this.initFlg = true;
	}

	/**
	 * 継続動作を実行する
	 * 目標速度と目標回転角速度を設定する
	 */
	@Override
	public void doActivity() {

		//加減速後の目標速度を算出する
		float setForwardVal = makeForwardValue();

		//速度を設定する
		Body.control.setForward(setForwardVal);
		Body.control.setTurn(turn);

	}

	/**
	 * 加減速後の目標速度算出処理
	 * @return float 加減速後の目標速度(mm/秒)
	 */
	protected float makeForwardValue() {

		//以下の計算で増分値(減分値)を算出する
		//・実行回数：N(回)
		//・変位量：X(mm/秒)
		//・平均増分値（平均減分値）：Y = X / N
		//・基準増分値（基準減分値）：Z = Y / ( N / 2 - 1) = 2 * Y / (N - 2)
		//・各実行回数毎の増分値（減分値）：V[N]
		//  ・V[i] = Z * i
		//  ・V[N - 1 - i] = 2 * Y - V[i]
		//    ※「i」は「0」～「N / 2」の範囲でインクリメント
		//＜考え方＞
		//  ・「1回目とN回目」、「2回目とN-1回目」・・・の合計が平均増分値（平均減分値）の2倍になるように調整する
		//  ・増分値（減分値）と実行回数を比例させて変位させることで、二次曲線的に速度を変化させる

		//初回動作時に走行体の現在の速度を取得する
		if(initFlg) {
			//現在の速度を取得する
			beforeForward = Body.control.getForward();
			//速度を変化させる「doActivity」の実行回数を算出する（指定時間(ms) / 実行周期(10ms)）
			//※実行周期はms単位に変換して使用すること
			maxCnt = (int) Math.floor(second / (Body.DELTA_T * 1000));
			nowCnt = 0;
			initFlg = false;

			float tmpCngVal1 = 0;
			float tmpCngVal2 = 0;
			float tmpAveCngVal = 0;
			float tmpBaseCngVal = 0;
			float tmpAllCngVal = 0;		//総変化量（「現在速度」と「目標速度」の差分に「調整速度」を加味して変化量を算出）

			//加速する際、速度を比例して増加させた場合、立ち上がりが遅いため、ロケットスタートできるように対応する
			//（初回の設定速度をある程度大きな値にして、その後、速度を比例させて増加させるようにする）
			//※減速時は、パラメータに「0」を設定することを推奨（急ブレーキはしない）

			//加速の場合
			if(targetForward  > beforeForward) {
				//総変化量を算出する（調整速度を変化量から除外）
				tmpAllCngVal = targetForward  - beforeForward - adjustSpeed;
				//調整速度が変化量を上回る場合は変化量を0とする
				if(tmpAllCngVal < 0) {
					//変化量を0とする
					tmpAllCngVal = 0;
					//初回の速度を設定する（目標速度）
					setForwardVal = targetForward;
				} else {
					//初回の速度を設定する（現在速度＋調整速度）
					setForwardVal = beforeForward + adjustSpeed;
				}
			//減速の場合
			} else {
				//総変化量を算出する（調整速度を変化量から除外）
				tmpAllCngVal = targetForward  - beforeForward + adjustSpeed;
				//調整速度が変化量を上回る場合は変化量を0とする
				if(tmpAllCngVal > 0) {
					tmpAllCngVal = 0;
					//初回の速度を設定する（目標速度）
					setForwardVal = targetForward;
				} else {
					//初回の速度を設定する（現在速度－調整速度）
					setForwardVal = beforeForward - adjustSpeed;
				}
			}

			//平均増分値(平均減分値)を算出する（変位量の平均値を算出する）
			tmpAveCngVal = tmpAllCngVal / maxCnt;

			//基準増分値（基準減分値）を算出する
			tmpBaseCngVal = 2 * tmpAveCngVal / (maxCnt - 2);

			//---> Add 2022/09/07 T.Okado Debug用
			/*
			LCD.drawString("maxCnt", 0, 3);
			LCD.drawString(String.valueOf(maxCnt) + " " + String.valueOf(maxCnt / 2), 11, 3);
			LCD.drawString("Ave", 0, 4);
			LCD.drawString(String.valueOf(tmpAveCngVal), 11, 4);
			LCD.drawString("BaseCng", 0, 5);
			LCD.drawString(String.valueOf(tmpBaseCngVal), 11, 5);
			*/
		    //<--- Add 2022/09/07 T.Okado

			//ループ数を算出する
			int loopCnt = (int) Math.ceil((double)maxCnt / 2);

		    //---> Add 2022/09/07 T.Okado Debug用
			//write2("1,maxCnt," +  String.valueOf(maxCnt) + ",loopCnt," + String.valueOf(loopCnt) + ",ceil," + Math.ceil((double)maxCnt / 2) );
		    //<--- Add 2022/09/07 T.Okado

			for(int iLoop = 0; iLoop < loopCnt ; iLoop++) {
				//実行回数が奇数回の場合に中央値が1つになるための対応
				if(iLoop == (maxCnt - 1 - iLoop)) {
					//平均増分値(平均減分値)を、増分値(減分値)マップに追加する
					mapCngVal.put(iLoop, tmpAveCngVal);

				    //---> Add 2022/09/07 T.Okado Debug用
					//write2("2," +  String.valueOf(iLoop) + "," + String.valueOf(tmpAveCngVal));
				    //<--- Add 2022/09/07 T.Okado

				} else {
					//増分値（減分値）を算出する
					tmpCngVal1 = tmpBaseCngVal * iLoop;
					//「tmpCngVal1」+「tmpCngVal2」が平均増分値（平均減分値）の2倍になるように調整する
					tmpCngVal2 = 2 * tmpAveCngVal - tmpCngVal1;

					//算出した増分値(減分値)を増分値(減分値)マップに追加する
					mapCngVal.put(iLoop, tmpCngVal1);
					mapCngVal.put(maxCnt - 1 - iLoop, tmpCngVal2);

				    //---> Add 2022/09/07 T.Okado Debug用
					//write2("3," +  String.valueOf(iLoop) + "," + String.valueOf(tmpCngVal1) + "," +  String.valueOf(maxCnt - 1 - iLoop) + "," + String.valueOf(tmpCngVal2) );
				    //<--- Add 2022/09/07 T.Okado

				}
			}

		    //---> Add 2022/09/07 T.Okado Debug用
			//write();
		    //<--- Add 2022/09/07 T.Okado

		} else {
			//目標速度に達するまでは、目標速度リストから値を取得し、速度を設定する
			if(nowCnt < maxCnt) {
				if(mapCngVal.get(nowCnt) != null) {
					setForwardVal = setForwardVal + mapCngVal.get(nowCnt) ;
				}
				//現在の実行回数をカウントアップする
				nowCnt++;
			//目標速度に達した以降は、目標速度を設定する
			} else {
				setForwardVal = targetForward;
			}
		}

		//---> Add 2022/09/07 T.Okado Debug用
		/*
		LCD.drawString("Before", 0, 1);
		LCD.drawString(String.valueOf(beforeForward), 11, 1);
		LCD.drawString("Target", 0, 2);
		LCD.drawString( String.valueOf(targetForward), 11, 2);
		LCD.drawString("setForward", 0, 6);
		LCD.drawString(String.valueOf(setForwardVal), 11, 6);
		*/
	    //<--- Add 2022/09/07 T.Okado

		return setForwardVal;

	}

/*
    //---> Add 2022/09/07 T.Okado Debug用
	public void write() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("size : " + mapCngVal.size()).append("\r\n");
            sb.append(mapCngVal.get(0));
            for(int i = 1; i < mapCngVal.size(); i++) {
                sb.append(",").append(mapCngVal.get(i));
            }
            sb.append("\r\n");

        	File file = new File("ActivityRunAccele.csv");
            FileWriter fw = new FileWriter(file, true);	//アペンドモードで書き込む
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(sb.toString());
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public void write2(String str) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(str).append("\r\n");
        	File file = new File("ActivityRunAccele.csv");
            FileWriter fw = new FileWriter(file, true);	//アペンドモードで書き込む
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(sb.toString());
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	//<--- Add 2022/09/07 T.Okado
*/
}
