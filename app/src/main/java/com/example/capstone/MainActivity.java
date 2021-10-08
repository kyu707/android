package com.example.capstone;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button button, button1, button2;
    TextView textView, textView1;
    Intent intent, serviceIntent;
    SpeechRecognizer mRecognizer;
    final int PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 안드로이드 6.0버전 이상인지 체크해서 퍼미션 체크

        //결합
        button = findViewById(R.id.button);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        textView = findViewById(R.id.textView);
        textView1 = findViewById(R.id.textView1);

        // RecognizerIntent 생성
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName()); // 여분의 키
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR"); // 언어 설정

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecognizer = SpeechRecognizer.createSpeechRecognizer(MainActivity.this); // 새 SpeechRecognizer 를 만드는 팩토리 메서드
                mRecognizer.setRecognitionListener(listener); // 리스너 설정
                mRecognizer.startListening(intent); // 듣기 시작
                startService();
                Toast.makeText(getApplicationContext(), "시작합니다.", Toast.LENGTH_SHORT).show();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MainActivity.this).setTitle("종료")
                        .setMessage("종료하시겠습니까?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        stopService();
                        Toast.makeText(MainActivity.this,"종료되었습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).setNegativeButton("No", null).show();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Word_setting.class);
                startActivity(intent);
            }
        });
        intentSet();
    }


    /**
     * 알림서비스 실행
     */
    public void startService()
    {
        serviceIntent = new Intent(this, MyService.class);
        startService(serviceIntent);
    }

    /**
     * 알림서비스 중지
     */
    public void stopService()
    {
        serviceIntent = new Intent(this, MyService.class);
        stopService(serviceIntent);
    }

    private RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle bundle) {
            //시작준비  되면 호출
        }

        @Override
        public void onBeginningOfSpeech() {
            //시작할 때 호출
        }

        @Override
        public void onRmsChanged(float v) {
            //입력받는 소리 크기 알려줌
        }

        @Override
        public void onBufferReceived(byte[] bytes) {
            //말을 시작하고 인식이 된 단어를 buffer에 담음
        }

        @Override
        public void onEndOfSpeech() {
            //말하기를 중지하면 호출
        }

        @Override
        public void onError(int error) {
            //네트워크 또는 인식 오류 발생시 호출
            String message;

            switch (error) {
                case SpeechRecognizer.ERROR_AUDIO:
                    message = "오디오 에러";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "클라이언트 에러";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "퍼미션 없음";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "네트워크 에러";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "네트웍 타임아웃";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "RECOGNIZER 가 바쁨";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "서버가 이상함";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "말하는 시간초과";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            mRecognizer.startListening(intent);
        }


        @Override
        public void onResults(Bundle results) {
                Intent intent1 = getIntent();
                String word = intent1.getStringExtra("editText");
                String word1 = intent1.getStringExtra("editText1");
                String word2 = intent1.getStringExtra("editText2");
                String word3 = "지정된 단어: " + word + ", " + word1 + ", " + word2;
                textView1.setText(word3);

            // 인식 결과가 준비되면 호출
            // 말을 하면 ArrayList에 단어를 넣고 textView에 단어를 이어줌
            String key = SpeechRecognizer.RESULTS_RECOGNITION;
            ArrayList<String> mResult = results.getStringArrayList(key);
            String[] rs = new String[mResult.size()];
            mResult.toArray(rs);
            textView.setText("" + rs[0]);
            String a =textView.getText().toString();
            if(a.equals(word) ==true || a.equals(word1) ==true || a.equals(word2) == true) {
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(2000);
                Toast.makeText(getApplicationContext(), "지정한 단어가 인식되었습니다.",Toast.LENGTH_SHORT).show();
                //화면 다시 불러오기
            }
            mRecognizer.startListening(intent);
        }

        @Override
        public void onPartialResults(Bundle bundle) {
            //부분 인식 결과를 사용할 수 있을 때 호출
        }

        @Override
        public void onEvent(int i, Bundle bundle) {
            //향후 이벤트를 추가하기 위해 예약
        }
    };
    public void intentSet(){
        Intent intent1 = getIntent();
        if(TextUtils.isEmpty(intent1.getStringExtra("editText"))){
            textView1.setText("단어를 지정하세요");
        }else {
            String word = intent1.getStringExtra("editText");
            String word1 = intent1.getStringExtra("editText1");
            String word2 = intent1.getStringExtra("editText2");
            String word3 = "지정된 단어: " + word + ", " + word1 + ", " + word2;
            textView1.setText(word3);
        }
    }
}


