package com.administrateur.speak;

import android.support.v7.app.AppCompatActivity;

import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements OnInitListener {

    private final String error = "You haven't typed anything";
    private final int TTS_REQUEST_CODE = 1;
    private TextToSpeech myTTS;
    private EditText ed;
    private String phrase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, TTS_REQUEST_CODE);

        ed = findViewById(R.id.editText);

        Button startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                final String phrase = ed.getText().toString();
                if (!phrase.isEmpty()) {
                    myTTS.speak(phrase, TextToSpeech.QUEUE_FLUSH, null);
                } else
                    Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();

            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TTS_REQUEST_CODE) {

            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                myTTS = new TextToSpeech(this, this);
                myTTS.setLanguage(Locale.getDefault());
            } else {
                // TTS data not yet loaded, try to install it
               /* Intent ttsLoadIntent = new Intent();
                ttsLoadIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(ttsLoadIntent);*/
            }
        }
    }

    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            //myTTS.speak(phrase, TextToSpeech.QUEUE_FLUSH, null);

        } else if (status == TextToSpeech.ERROR) {
            myTTS.shutdown();
        }
    }

}

