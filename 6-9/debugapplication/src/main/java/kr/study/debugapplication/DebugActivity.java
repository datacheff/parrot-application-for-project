package kr.study.debugapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DebugActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        TextView textView = (TextView) findViewById(R.id.debugtext);
        String result;
        try {
            result = exceptionTest(50, 0);
        } catch (java.lang.ArithmeticException ex) {
            result = "onCreate()에서 오류를 캐치했습니다." + ex.getMessage();
        } catch (java.lang.IllegalArgumentException ex) {
            result = "올바르지 않은 인수입니다." + ex.getMessage();
        }
        textView.setText(result);
    }

    private String exceptionTest(int x, int y) {
        if (x >= 100) {
            throw new IllegalArgumentException("100 이상인 값을 나눌 수 없습니다.");
        }
        try {
            return String.valueOf(x / y);
        } catch (java.lang.ArithmeticException ex) {
            throw new java.lang.IllegalArgumentException("IllegalArgumentException으로 치환");
        } finally {
            Log.d("exceptionTest", "x = " + String.valueOf(x) + ", y = " + String.valueOf(y));
        }
    }

    private int calc(int x) {
        int result = 0;
        for (int i = 1; i <= x; i++) {
            result += i;
            Log.d("result", String.valueOf(i) + ":" + String.valueOf(result));
        }
        return result;

    }
}
