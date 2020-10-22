package com.example.videochatbasic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
     private EditText emailBox,passwordBox,nameBox;
    private Button loginBtn,signUpBtn;
    private ImageView profilePhoto;
    private TextView addPhoto;
    private Uri imageUri;
    FirebaseAuth auth;
    FirebaseFirestore database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        database=FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        emailBox =findViewById(R.id.inputEmail);
        passwordBox=findViewById(R.id.inputPassword);
        loginBtn =findViewById(R.id.buttonLogin);
        signUpBtn=findViewById(R.id.buttonCreateAnAccount);
        nameBox=findViewById(R.id.personName);
        profilePhoto=findViewById(R.id.image_added);
        addPhoto=findViewById(R.id.addPhoto);
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().start(SignUpActivity.this);
            }
        });



        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password,name;
                email=emailBox.getText().toString();
                password=passwordBox.getText().toString();
                name=nameBox.getText().toString();
             final User user =new User();
             user.setEmail(email);
             user.setName(name);
             user.setPassword(password);
             user.setImageUri(imageUri);
                final HashMap<String ,Object> map =new HashMap<>();
                map.put("Name",name);
                map.put("Email",email);
                map.put("Passwors",password);
                map.put("imageurl",profilePhoto);
                auth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    database.collection("Users")
                                            .document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                       startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                                        }
                                    });
                                    Toast.makeText(SignUpActivity.this,"account created",Toast.LENGTH_SHORT).show();

                                }else {
                                    Toast.makeText(SignUpActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });



            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK){
            CropImage.ActivityResult result =CropImage.getActivityResult(data);
            imageUri =result.getUri();
            profilePhoto.setImageURI(imageUri);
        }
        else {
            Toast.makeText(SignUpActivity.this,"try again",Toast.LENGTH_SHORT).show();
        }
    }
}