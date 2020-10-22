package com.example.videochatbasic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
     EditText emailBox,passwordBox;
     Button loginBtn,signUpBtn;
     FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailBox =findViewById(R.id.inputEmail);
        passwordBox=findViewById(R.id.inputPassword);
        loginBtn =findViewById(R.id.buttonLogin);
        signUpBtn=findViewById(R.id.buttonCreateAnAccount);
        auth =FirebaseAuth.getInstance();
        loginBtn.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            String email, password;

                                            email = emailBox.getText().toString();
                                            password = passwordBox.getText().toString();

                                            auth.signInWithEmailAndPassword(email, password)
                                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                            if (task.isSuccessful())
                                                                Toast.makeText(LoginActivity.this, "welcome log in successful ", Toast.LENGTH_SHORT).show();
                                                            else
                                                                Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }
                                    });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class) );
            }
        });
    }
}