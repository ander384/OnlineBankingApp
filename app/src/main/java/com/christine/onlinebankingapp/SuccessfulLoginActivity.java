package com.christine.onlinebankingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
import static android.view.View.VISIBLE;

public class SuccessfulLoginActivity extends AppCompatActivity {

    MediaPlayer successSound;
    MediaPlayer failureSound;
    MediaPlayer nextScreenSound;

    EditText entry1EditText;
    EditText entry2EditText;
    EditText entry3EditText;
    EditText entry4EditText;

    TextView conversion1TextView;
    TextView conversion2TextView;
    TextView conversion3TextView;
    TextView conversion4TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_login);

        entry1EditText = findViewById(R.id.et_number1);
        entry2EditText = findViewById(R.id.et_number2);
        entry3EditText = findViewById(R.id.et_number3);
        entry4EditText = findViewById(R.id.et_number4);

        conversion1TextView = findViewById(R.id.tv1);
        conversion2TextView = findViewById(R.id.tv2);
        conversion3TextView = findViewById(R.id.tv3);
        conversion4TextView = findViewById(R.id.tv4);

        successSound = MediaPlayer.create(this,R.raw.success_sound);
        failureSound = MediaPlayer.create(this, R.raw.failure_sound);
        nextScreenSound = MediaPlayer.create(this, R.raw.level_up_sound_effect);
    }

    @Override
    protected void onStart() {
        final View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(SYSTEM_UI_FLAG_IMMERSIVE_STICKY|SYSTEM_UI_FLAG_FULLSCREEN|SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onStart();
    }

    public void onClickImage(View view) {
        hideKeyboard(this);
        final View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(SYSTEM_UI_FLAG_IMMERSIVE_STICKY|SYSTEM_UI_FLAG_FULLSCREEN|SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void onClickLogOut(View view) {
        failureSound.release();
        successSound.release();
        nextScreenSound.release();
        this.finish();
    }

    public void onClickCheckBalance(View view) {
        vibrate(50);
        compareNumbers(entry1EditText, conversion1TextView, 1.817);
    }

    public void onClickCheckBalance2(View view) {
        vibrate(50);
        compareNumbers(entry2EditText, conversion2TextView, 1.035);
    }

    public void onClickCheckBalance3(View view) {
        vibrate(50);
        compareNumbers(entry3EditText, conversion3TextView, 1.256);
    }

    public void onClickCheckBalance4(View view) {
        vibrate(50);
        compareNumbers(entry4EditText, conversion4TextView, 0.822);
    }

    void compareNumbers(EditText userInput, TextView conversionTextView, double rightAnswer){
        String enteredNumberText = userInput.getText().toString().trim();
        try {
            double enteredNumberDouble = Double.parseDouble(enteredNumberText);
            if(rightAnswer ==enteredNumberDouble){
                successSound.start();
                conversionTextView.setVisibility(VISIBLE);
                checkForSuccessState();
            }
            else{
                failureSound.start();
                userInput.setText("");
            }
        }
        catch(NumberFormatException e){ 
            failureSound.start();
            userInput.setText("");
        }
    }

    void checkForSuccessState() {
        if(conversion1TextView.getVisibility()==VISIBLE&&conversion2TextView.getVisibility()==VISIBLE&&conversion3TextView.getVisibility()==VISIBLE&&conversion4TextView.getVisibility()==VISIBLE){

            Intent i = new Intent(SuccessfulLoginActivity.this, FinalCodeActivity.class);
            nextScreenSound.start();
            SuccessfulLoginActivity.this.startActivity(i);
        }
    }

    private void vibrate(int lengthInMil) {
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(lengthInMil, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(lengthInMil);
        }
    }
}
