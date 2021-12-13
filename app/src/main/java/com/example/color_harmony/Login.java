package com.example.color_harmony;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



//        Amplify.Auth.fetchAuthSession(
//                result -> Log.i("AmplifyQuickstart", result.toString()),
//                error -> Log.e("AmplifyQuickstart", error.toString())
//        );

        EditText userName=findViewById(R.id.userName);
        EditText password=findViewById(R.id.password);


        Button loginButton=findViewById(R.id.loginButton);


        TextView signUpText=findViewById(R.id.signUpText);


        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goSignUp=new Intent(Login.this,SignUp.class);
                startActivity(goSignUp);


            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goMain=new Intent(Login.this,MainActivity.class);
                Amplify.Auth.signIn(
                        userName.getText().toString(),
                        password.getText().toString(),
                        result -> Log.i("AuthQuickstart", result.isSignInComplete() ? "Sign in succeeded" : "Sign in not complete"),
                        error -> Log.e("AuthQuickstart", error.toString())
                );
                startActivity(goMain);
            }
        });



    }
}