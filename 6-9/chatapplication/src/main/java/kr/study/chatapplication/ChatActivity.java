package kr.study.chatapplication;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener  {
    private EditText mInputMessage;
    private Button mSendMessage;
    private LinearLayout mMessageLog;
    private TextView mCpuMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
      /*
        Locale locale = Locale.US; Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, null);
      */
       // XML의 View를 가져온다
        mInputMessage = (EditText) findViewById(R.id.input_message); // 사용자 입력 필드
        mSendMessage = (Button) findViewById(R.id.send_message); // SEND 버튼
        mMessageLog = (LinearLayout) findViewById(R.id.message_log); // 입력 이력을 표시할 레이아웃
        mCpuMessage = (TextView) findViewById(R.id.cpu_message); // 컴퓨터의 응답
        mSendMessage.setOnClickListener(this);  // 버튼이 눌렸을 때 메소드를 실행한다

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        if(getPackageManager().queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0).size()==0){
            menu.removeItem(R.id.action_voice_input);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_voice_input) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            startActivityForResult(intent, 0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK && data.hasExtra(RecognizerIntent.
                EXTRA_RESULTS)) {
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.
                    EXTRA_RESULTS);
            if (results.size() > 0) {
                mInputMessage.setText(results.get(0));
                send();
            }
        }
    }


    private void send() {
        // SEND 버튼이 눌렸을 때의 처리
        String inputText = mInputMessage.getText().toString();
        String lowerInputText = inputText.toLowerCase();
        String answer;
        TextView userMessage = new TextView(this);		// 새 TextView를 생성한다.
        int messageColor = getResources().getColor(R.color.message_color);
        userMessage.setTextColor(messageColor);
        userMessage.setBackgroundResource(R.drawable.user_message);
        userMessage.setText(inputText); 			// TextView에 입력한 텍스트를 설정한다.

        // 메시지 크기에 맞춘다
        LinearLayout.LayoutParams userMessageLayout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        userMessageLayout.gravity = Gravity.END;
        final int marginSize = getResources().getDimensionPixelSize(R.dimen.message_margin);
        // 간격을 설정한다.
        userMessageLayout.setMargins(0, marginSize, 0, marginSize);
        mMessageLog.addView(userMessage, 0, userMessageLayout);

        // TextView를 View의 맨 위에 설정한다.
        if (lowerInputText.contains(getString(R.string.how_are_you))) {
            answer = getString(R.string.fine);
        } else if (lowerInputText.contains(getString(R.string.tire))) {
            answer = getString(R.string.bless_you);
        } else if (lowerInputText.contains(getString(R.string.fortune))) {
            double random = Math.random() * 5.1d;
            if (random < 1d) {
                answer = getString(R.string.worst_luck);
            } else if (random < 2d) {
                answer = getString(R.string.bad_luck);
            } else if (random < 3d) {
                answer = getString(R.string.good_luck);
            } else if (random < 4d) {
                answer = getString(R.string.nice_luck);
            } else if (random < 5d) {
                answer = getString(R.string.best_luck);
            } else {
                answer = getString(R.string.amazing_best_luck);
            }
        } else if (lowerInputText.contains(getString(R.string.time))) {
            Calendar cal = Calendar.getInstance();
            int hour = cal.get(Calendar.HOUR); // 시
            int minute = cal.get(Calendar.MINUTE); // 분
            int second = cal.get(Calendar.SECOND); // 초
            answer = getString(R.string.time_format, hour, minute, second);
        } else {
            answer = getString(R.string.good);
        }
        final TextView cpuMessage = new TextView(this); // 내부 클래스에서 참조하기 위해 final 선언
        cpuMessage.setTextColor(messageColor);
        cpuMessage.setBackgroundResource(R.drawable.cpu_message);
        cpuMessage.setText(answer);
        cpuMessage.setGravity(Gravity.START);
        mInputMessage.setText("");
        TranslateAnimation userMessageAnimation = new TranslateAnimation(mMessageLog.
                getWidth(), 0, 0, 0);
        userMessageAnimation.setDuration(1000);
        userMessageAnimation.setAnimationListener(new Animation.AnimationListener() {
            // 클래스 이름이 선언되지 않은 내부 클래스를 익명 내부 클래스라고 한다.
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                LinearLayout.LayoutParams cpuMessageLayout = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT); cpuMessageLayout.gravity = Gravity.START;
                // 간격을 설정한다.
                cpuMessageLayout.setMargins(marginSize, marginSize, marginSize, marginSize);
                mMessageLog.addView(cpuMessage, 0, cpuMessageLayout);

                TranslateAnimation cpuAnimation = new TranslateAnimation(-mMessageLog.getWidth(), 0, 0, 0);
                cpuAnimation.setDuration(1000);
                cpuMessage.setAnimation(cpuAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        userMessage.startAnimation(userMessageAnimation);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(mSendMessage)) {
            send();
        }
    }
}

