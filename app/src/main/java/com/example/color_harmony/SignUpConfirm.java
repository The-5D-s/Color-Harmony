package com.example.color_harmony;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.core.Amplify;

public class SignUpConfirm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_confirm);


        EditText code=findViewById(R.id.code);

        Button codeConfirm=findViewById(R.id.codeConfirm);

        EditText userName=findViewById(R.id.userName1);




        codeConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent confirm=new Intent(SignUpConfirm.this,MainActivity.class);
                Amplify.Auth.confirmSignUp(
                        userName.getText().toString(),
                        code.getText().toString(),
                        result -> Log.i("AuthQuickstart", result.isSignUpComplete() ? "Confirm signUp succeeded" : "Confirm sign up not complete"),
                        error -> Log.e("AuthQuickstart", error.toString())
                );

                startActivity(confirm);
            }
        });



    }
}