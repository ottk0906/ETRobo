package game.guard;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import scenario.ScenarioData;

/**
 * 状態遷移条件メイン処理
 * @author 尾角 武俊
 */
public class GuardMain extends Guard {

	private Guard guard;	//状態遷移条件

	public GuardMain(List<ScenarioData> scenarioList) {

		/*
	    //---> Add 2022/07/13 T.Okado Debug用
        try {
	        //ログファイルが既に存在する場合は削除する
	        File file = new File("GuardMain.csv");
	        if (file.exists()) file.delete();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
        //<--- Add 2022/07/13 T.Okado
		*/

        final int LOGICAL_OPERATOR_AND = 0;
		final int LOGICAL_OPERATOR_OR = 1;

		int tmplogicalOpe = 0;
		List<Guard> guardList = new ArrayList<Guard>();

		//クラス名を設定する
		name = "GuardMain";

		//1つ目の条件を状態遷移条件リストに設定する
		guardList.add(getGuard(scenarioList.get(0).getGdNo(),scenarioList.get(0).getGdValue()));

		for(int iLoop = 1; iLoop < scenarioList.size(); iLoop++){

			if(iLoop == 1) {
				tmplogicalOpe = scenarioList.get(iLoop).getGdLogicalOperator();
				//2つ目の条件を状態遷移条件リストに設定する
				guardList.add(getGuard(scenarioList.get(iLoop).getGdNo(),scenarioList.get(iLoop).getGdValue()));
			} else {
				if(tmplogicalOpe == scenarioList.get(iLoop).getGdLogicalOperator()) {
					//2つ目の条件を状態遷移条件リストに設定する
					guardList.add(getGuard(scenarioList.get(iLoop).getGdNo(),scenarioList.get(iLoop).getGdValue()));
				} else {
					//And条件の場合
					if(tmplogicalOpe == LOGICAL_OPERATOR_AND) {
						guard = new GuardAnd(guardList);
					//Or条件の場合
					} else {
						guard = new GuardOr(guardList);
					}


					for(Guard gd:guardList) {
						write(1,gd.name, tmplogicalOpe);
					}

					guardList = new ArrayList<Guard>();
					tmplogicalOpe = scenarioList.get(iLoop).getGdLogicalOperator();
					guardList.add(guard);
					guardList.add(getGuard(scenarioList.get(iLoop).getGdNo(),scenarioList.get(iLoop).getGdValue()));

				}
			}
		}

		//And条件の場合
		if(tmplogicalOpe == LOGICAL_OPERATOR_AND) {
			guard = new GuardAnd(guardList);
		//Or条件の場合
		} else {
			guard = new GuardOr(guardList);
		}

		for(Guard gd:guardList) {
			write(2,gd.name, tmplogicalOpe);
		}

		write(3,guard.name, -1);

	}

    /**
     * 状態遷移条件を判定する
     * @return	True : OK / False ： NG
     */
	@Override
	public boolean judge() {
		//複合条件の判定結果を設定する
		return guard.judge();
	}

    /**
     * 状態遷移条件番号に関連付けられている状態遷移条件を取得する
     * @param 状態遷移条件番号
     */
	private  Guard getGuard(int gdNo, List<Double> gdValue) {

		Guard tmpGuard = null;

		switch(gdNo){
			case 0:
				tmpGuard = new GuardTouch();
				break;
			case 1:
				tmpGuard = new GuardDistance(gdValue.get(0).doubleValue());
				break;
			case 2:
				tmpGuard = new GuardTimer(gdValue.get(0).intValue());
				break;
		}

		return tmpGuard;

	}

    //---> Add 2022/07/13 T.Okado Debug用
	public void write(int no, String name, int logicalOpe) {
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(no).append(",");
            sb.append(name).append(",");
            sb.append(logicalOpe).append("\r\n");

        	File file = new File("GuardMain.csv");
            FileWriter fw = new FileWriter(file, true);	//アペンドモードで書き込む
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(sb.toString());
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //<--- Add 2022/07/13 T.Okado

}
