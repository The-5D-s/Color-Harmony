package com.example.color_harmony;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        EditText userName=findViewById(R.id.userName1);
        EditText email=findViewById(R.id.userEmail);

        EditText password=findViewById(R.id.password1);

        Button signUp=findViewById(R.id.loginButton);



        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent confirm=new Intent(SignUp.this,SignUpConfirm.class);
                AuthSignUpOptions options = AuthSignUpOptions.builder()
                        .userAttribute(AuthUserAttributeKey.email(), email.getText().toString())
                        .userAttribute(AuthUserAttributeKey.name(), userName.getText().toString())
                        .build();
                Amplify.Auth.signUp(userName.getText().toString(), password.getText().toString(), options,
                        result -> Log.i("AuthQuickStart", "Result: " + result.toString()),
                        error -> Log.e("AuthQuickStart", "Sign up failed", error)
                );
                startActivity(confirm);
            }
        });



    }
}