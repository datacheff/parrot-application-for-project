package kr.study.firstapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView)findViewById(R.id.text);
        String s = calc(100);
        s += calc(1000);
        s += calc(-10000);
        textView.setText(s);
    }

    private String calc(int n) {
        int a = 0;
        if (n > 0) {
            for (int i = 1; i <= n; i++) {
                a += i;
            }
        } else {
            for (int i = 1; i >= n; i--) {
                a += i;
            }
        }
        String s = "1부터 " + String.valueOf(n) + "을 더한 값은 " + String.valueOf(a) + "\n";
        return s;
    }

}
