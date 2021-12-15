package com.example.color_harmony;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.core.Amplify;

public class EditProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        EditText name = findViewById(R.id.editName);
        Button save = findViewById(R.id.saveChanges);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String modifiedName = name.getText().toString();
                Amplify.Auth.fetchUserAttributes(
                        attributes -> Log.i("AuthDemo", "User attributes = " + attributes.toString()),
                        error -> Log.e("AuthDemo", "Failed to fetch user attributes.", error)
                );

                AuthUserAttribute userName =
                        new AuthUserAttribute(AuthUserAttributeKey.name(), modifiedName);
                Amplify.Auth.updateUserAttribute(userName,
                        result -> Log.i("AuthDemo", "Updated user attribute = " + result.toString()),
                        error -> Log.e("AuthDemo", "Failed to update user attribute.", error)
                );
            }
        });
    }
}