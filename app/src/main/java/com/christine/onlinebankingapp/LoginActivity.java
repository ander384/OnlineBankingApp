package com.christine.onlinebankingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.opengl.Visibility;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_IMMERSIVE;
import static android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
import static android.view.View.VISIBLE;

public class LoginActivity extends AppCompatActivity {

    String correctUsername = "username";
    String correctPassword = "password";

    View LoginActivityLayout;
    EditText UsernameEditText;
    EditText PasswordEditText;
    Button LoginButton;
    TextView IncorrectUsernameTextView;
    TextView IncorrectPasswordTextView;

    MediaPlayer incorrectLoginMediaPlayer;
    MediaPlayer correctLoginMediaPlayer;

    boolean usernameIsCorrect = false;
    boolean passwordIsCorrect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.LoginActivityLayout = findViewById(R.id.layout_login_activity);
        this.UsernameEditText = findViewById(R.id.et_username);
        this.PasswordEditText = findViewById(R.id.et_password);
        this.LoginButton = findViewById(R.id.btn_login);
        this.incorrectLoginMediaPlayer = MediaPlayer.create(this, R.raw.incorrect_login);
        this.correctLoginMediaPlayer = MediaPlayer.create(this, R.raw.correct_login);
        this.IncorrectPasswordTextView = findViewById(R.id.tv_incorrect_password);
        this.IncorrectUsernameTextView = findViewById(R.id.tv_incorrect_username);

    }

    @Override
    protected void onStart() {
        final View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(SYSTEM_UI_FLAG_IMMERSIVE_STICKY|SYSTEM_UI_FLAG_FULLSCREEN|SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        super.onStart();
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

    public void onClickImage(View view) {
        hideKeyboard(this);
        final View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(SYSTEM_UI_FLAG_IMMERSIVE_STICKY|SYSTEM_UI_FLAG_FULLSCREEN|SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    public void onPressLoginButton(View view) {

        String usernameEntered = UsernameEditText.getText().toString().toLowerCase();

        String passwordEntered = PasswordEditText.getText().toString().toLowerCase();


        if(!usernameEntered.equals(this.correctUsername)){
            usernameIsCorrect = false;
        }
        else{
            usernameIsCorrect = true;
        }
        if(!passwordEntered.equals(this.correctPassword)){
            passwordIsCorrect = false;
        }
        else{
            passwordIsCorrect = true;
        }

        if(usernameIsCorrect&&passwordIsCorrect){
            clearEditTexts();
            correctLoginMediaPlayer.start();
            Intent i = new Intent(LoginActivity.this, SuccessfulLoginActivity.class);
            LoginActivity.this.startActivity(i);
        }
        else{
            incorrectLoginMediaPlayer.start();


            Animation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(150); //You can manage the time of the blink with this parameter
            anim.setStartOffset(20);
            anim.setRepeatCount(17);
            final boolean finalPasswordIsCorrect = passwordIsCorrect;
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if(!passwordIsCorrect){
                        PasswordEditText.setText("");
                    }
                    if (!usernameIsCorrect) {
                        UsernameEditText.setText("");
                    }
                    PasswordEditText.setTextColor(Color.parseColor("#ffffff"));
                    UsernameEditText.setTextColor(Color.parseColor("#ffffff"));
                    setRedMessageVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            if(!passwordIsCorrect){
                PasswordEditText.setTextColor(Color.parseColor("#ff0000"));
                IncorrectPasswordTextView.setVisibility(VISIBLE);
                IncorrectPasswordTextView.startAnimation(anim);
                PasswordEditText.startAnimation(anim);
            }
            if(!usernameIsCorrect) {
                UsernameEditText.setTextColor(Color.parseColor("#ff0000"));
                IncorrectUsernameTextView.setVisibility(View.INVISIBLE);
                UsernameEditText.startAnimation(anim);
                IncorrectUsernameTextView.startAnimation(anim);
            }
        }
    }

    void clearEditTexts(){
        PasswordEditText.setText("");
        UsernameEditText.setText("");
    }

    //pass in VISIBLE or INVISIBLE to make them visible or not
    void setRedMessageVisibility(int visib){
        IncorrectUsernameTextView.setVisibility(visib);
        IncorrectPasswordTextView.setVisibility(visib);
    }

}
