package body.measure;

import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;

/**
 * 路面計測クラス
 * @author 後藤　聡文
 *
 */
public class MeasureCourse{
    /** カラーセンサ */
    private EV3ColorSensor colorSensor;
    private SensorMode sensorMode;

    /** 白明度 */
    private float white;
    /** 黒明度 */
    private float black;
    /** 目標明度 */
    private float target;
    /** 路面RGB（赤、緑、青） */
    private float rgb[];
    /** 路面HSV（色相、彩度、明度） */
    private float hsv[];
    
    /**
     * コンストラクタ
     * @param colorSensor カラーセンサ
     */
    public MeasureCourse(EV3ColorSensor colorSensor){
        this.colorSensor = colorSensor;
        sensorMode =  colorSensor.getRGBMode();
        rgb = new float[sensorMode.sampleSize()];
        hsv = new float[3];
        
        // 仮キャリブレーション
        white = 0.2f;
        black = 0.0f;
        target = (white + black)/2.0f;
    }
    
    /**
     * 更新する
     */
    public void update() {
        // RGBを取得する
        sensorMode.fetchSample(rgb, 0);
        
        // RGBをHSVに変換する
        RGBtoHSV();
    }
    
    /**
     * RGBをHSVに変換する
     */
    private void RGBtoHSV() {
        // rgb（赤:Red、緑:Green、青:Blue）
        float r = rgb[0];
        float g = rgb[1];
        float b = rgb[2];
        
        // hsv（色相:Hue、彩度:Saturation、明度:Value）
        float h, s, v;
        
        // rgbの最大値
        float max = (r > g) ? r : g;
        if (b > max) max = b;
        
        // rgbの最小値
        float min = (r < g) ? r : g;
        if (b < min) min = b;

        // rgbからhsvへ変換
        if (max == min) {
            h = -1.0f;       // 未定義
        } else {
            if (max == r) {
                h = (g - b) / (max - min) * 60.0f;
            } else if (max == g) {
                h = (b - r) / (max - min) * 60.0f + 120.0f; 
            } else {
                h = (r - g) / (max - min) * 60.0f + 240.0f;
            }
            if (h < 0.0f) h = h + 360.0f;
            if (h > 360.0f) h = h - 360.0f;
        }
        if (max != 0.0f) {
            s = (max - min) / max; 
        } else {
            s = -1.0f;   // 未定義
        }
        v = max;
        
        hsv[0] = h;
        hsv[1] = s;
        hsv[2] = v;
    }
    
    /**
     * 白明度を取得する
     * @return　白明度
     */
    public float getWhite() {
        return white;
    }

    /**
     * 白明度を設定する
     * @param white　白明度
     */
    public void setWhite(float white) {
        this.white = white;
    }

    /**
     * 黒明度を取得する
     * @return　黒明度
     */
    public float getBlack() {
        return black;
    }

    /**
     * 黒明度を設定する
     * @param black　黒明度
     */
    public void setBlack(float black) {
        this.black = black;
    }

    /**
     * 目標明度を取得する
     * @return　目標明度
     */
    public float getTarget() {
        return target;
    }

    /**
     * 目標明度を設定する
     * @param target　目標明度
     */
    public void setTarget(float target) {
        this.target = target;
    }
    
    /**
     * 路面色相を取得する
     * @return　路面色相
     */
    public float getHue(){
        return hsv[0];
    }
    
    /**
     * 路面彩度を取得する
     * @return　路面彩度
     */
    public float getSaturation(){
        return hsv[1];
    }
    
    /**
     * 路面明度を取得する
     * @return　路面明度
     */
    public float getValue() {
        return hsv[2];
    }
    
}
