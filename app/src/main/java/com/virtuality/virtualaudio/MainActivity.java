package com.virtuality.virtualaudio;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.io.IOException;


public class MainActivity extends Activity {

    Button playVirtualBarberAudio;
    SeekBar controlVirtualBarberAudio;
    boolean virtualBarberStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playVirtualBarberAudio = (Button) findViewById(R.id.play_audio001);
        controlVirtualBarberAudio = (SeekBar) findViewById(R.id.seekbar_audio001);
        final MediaPlayer virtualBarberAudioPlayer = MediaPlayer.create(MainActivity.this, R.raw.audio001);
        controlVirtualBarberAudio.setMax(virtualBarberAudioPlayer.getDuration());
        playVirtualBarberAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(virtualBarberStarted) {
                    virtualBarberAudioPlayer.stop();
                    virtualBarberStarted = false;
                } else{
                    try {
                        virtualBarberAudioPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    virtualBarberAudioPlayer.start();
                    virtualBarberStarted = true;
                    final Handler handlerVirtualBarberAudio = new Handler();
                    Runnable runnableVirtualBarberAudio = new Runnable() {
                        @Override
                        public void run() {
                            if(virtualBarberAudioPlayer != null) {
                                int currentAudioPosition = virtualBarberAudioPlayer.getCurrentPosition();
                                controlVirtualBarberAudio.setProgress(currentAudioPosition);
                            }
                            handlerVirtualBarberAudio.postDelayed(this, 1000);
                        }
                    };
                    runnableVirtualBarberAudio.run();
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
