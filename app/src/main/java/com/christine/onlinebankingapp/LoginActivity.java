package com.christine.onlinebankingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_IMMERSIVE;
import static android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

public class LoginActivity extends AppCompatActivity {

    String correctUsername = "username";
    String correctPassword = "password";

    View LoginActivityLayout;
    EditText UsernameEditText;
    EditText PasswordEditText;
    Button LoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.LoginActivityLayout = findViewById(R.id.layout_login_activity);
        this.UsernameEditText = findViewById(R.id.et_username);
        this.PasswordEditText = findViewById(R.id.et_password);
        this.LoginButton = findViewById(R.id.btn_login);

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
        boolean usernameIsCorrect = false;
        boolean passwordIsCorrect = false;
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
            Intent i = new Intent(LoginActivity.this, SuccessfulLoginActivity.class);
            LoginActivity.this.startActivity(i);
        }
    }
}
