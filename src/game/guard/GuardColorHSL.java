package game.guard;

import java.util.ArrayList;

import body.Body;

/**
 * 色指定による走行動作停止条件(HSLで判定)
 * @author 尾角 武俊
 */
public class GuardColorHSL extends Guard {

	private int stopColor;					//停止判定色
	private int consecutivelyJudgeNum;		//連続判定回数
	private float judgeRate;				//判定割合(指定した色が指定した割合以上検知できればtrueとする)
	private int judgeNum;					//判定回数
	private ArrayList<Integer> ArrayJudgeResult = new ArrayList<Integer>();	//判定結果保存用配列

    /**
     * コンストラクタ
     * @param color 停止判定色
     * @param color 連続判定回数
     * @param color 判定割合
     */
	public GuardColorHSL(int stopColor, int consecutivelyJudgeNum, float judgeRate) {
		this.stopColor = stopColor;
		this.consecutivelyJudgeNum = consecutivelyJudgeNum;
		this.judgeRate = judgeRate;
		judgeNum = 0;
		name = "GuardColorHSL";
	}

    /**
     * 路面の色が指定色かどうかを判定する
     * @return	True : 検知 / False ： 未検知
     */
	@Override
	public boolean judge() {

		int tmpJudge;

		//指定した色を検知した場合
		if(Body.measure.getColorHSL().getInt() == stopColor) {
			tmpJudge = 1;
		} else {
			tmpJudge = 0;
		}

		//判定回数が「連続判定回数」未満の場合
		if (judgeNum < consecutivelyJudgeNum) {
			//判定結果を追加する
			ArrayJudgeResult.add(tmpJudge);
			//判定回数をカウントアップする
			judgeNum++;

			//色未検知と判定する
			return false;

		//判定回数が「連続判定回数」以上の場合
		} else {

			//一番古い判定結果（先頭のデータ）を削除する
			ArrayJudgeResult.remove(0);
			//判定結果を追加する
			ArrayJudgeResult.add(tmpJudge);

			int tmpVal = 0;
			for(int val : ArrayJudgeResult) {
				tmpVal = tmpVal + val;
			}
/*
		    //---> Debug用
			boolean ret;
			if((tmpVal / (float) consecutivelyJudgeNum) >= judgeRate ) {
				ret = true;
			} else {
				ret = false;
			}
			write(ArrayJudgeResult,consecutivelyJudgeNum,judgeRate,tmpVal, (tmpVal / (float) consecutivelyJudgeNum) ,ret);
			//<--- Debug用
*/
			//判定結果保存用配列内の色検知の割合が、指定した判定割合以上の場合、色検知と判定する
			if((tmpVal / (float) consecutivelyJudgeNum) >= judgeRate ) {
				return true;
			} else {
				return false;
			}
		}
	}

/*
    //---> Add 2022/09/07 T.Okado Debug用
	public void write(ArrayList<Integer> arrJudge, int consecutivelyJudgeNum,
			float judgeRate, int val1 , float val2, boolean ret) {
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(consecutivelyJudgeNum).append(",");
            sb.append(judgeRate).append(",");
			for(int val : ArrayJudgeResult) {
	            sb.append(val).append(",");
			}
            sb.append(val1).append(",");
            sb.append(val2).append(",");
            sb.append(ret).append("\r\n");

        	File file = new File("GuardColorHSL.csv");
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
