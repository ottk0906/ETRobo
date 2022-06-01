package log;

/**
 * ログデータクラス
 * @author 後藤　聡文
 *
 */
public class LogData {
    /** タスク呼出回数 */
    private int count;
    /** 競技状態名 */
    private String statusName;
    /** 路面色相　*/
    private float hue;
    /** 路面彩度　*/
    private float saturation;
    /** 路面明度　*/
    private float value;
    /** 左モータの角速度(度/秒) */
    private float leftRotationSpeed;
    /** 右モータの角速度(度/秒) */
    private float rightRotationSpeed;
    
    /**
     * コンストラクタ
     * @param count タスクの呼び出し回数
     * @param status 競技状態名
     * @param hue 路面色相
     * @param saturation 路面彩度
     * @param brightness 路面明度
     * @param leftRotationSpeed 左モータの角速度(度/秒)
     * @param rightRotationSpeed 右モータの角速度(度/秒)
     */
    public LogData(int count, String statusName, float hue, float saturation, float value, float leftRotationSpeed, float rightRotationSpeed){
        this.count = count;
        this.statusName = statusName;
        this.hue = hue;
        this.saturation = saturation;
        this.value = value;
        this.leftRotationSpeed = leftRotationSpeed;
        this.rightRotationSpeed = rightRotationSpeed;
    }


    /**
     * オブジェクトの文字列表現を取得する
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(count);
        sb.append(",");
        sb.append(statusName);
        sb.append(",");
        sb.append(hue);
        sb.append(",");
        sb.append(saturation);
        sb.append(",");
        sb.append(value);
        sb.append(",");
        sb.append(leftRotationSpeed);
        sb.append(",");
        sb.append(rightRotationSpeed);
        sb.append("\r\n");
        
        return sb.toString();
    }
}
