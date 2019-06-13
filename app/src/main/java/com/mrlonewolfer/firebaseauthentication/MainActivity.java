package com.mrlonewolfer.firebaseauthentication;

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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSignUp,btnSingIn;
    EditText edtName,edtPass;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        edtName=findViewById(R.id.edtName);
        edtPass=findViewById(R.id.edtPassword);
        btnSignUp=findViewById(R.id.btnSIGNUP);
        btnSingIn=findViewById(R.id.btnSIGNIN);

        btnSingIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

    }

    private void updateUI(FirebaseUser currentUser) {
        if(currentUser!=null){
            // user already login
            Intent intent=new Intent(this,HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnSIGNIN){

            signinUser();
        }
        if(v.getId()==R.id.btnSIGNUP){
        
            signupUser();

        }
    }

    private void signupUser() {
        String email=edtName.getText().toString();
        String password=edtPass.getText().toString();
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user=mAuth.getCurrentUser();
                            updateUI(user);
                        }
                    }
                });
    }

    private void signinUser() {
        String email=edtName.getText().toString();
        String password=edtPass.getText().toString();
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user=mAuth.getCurrentUser();
                            updateUI(user);
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        
    }
}
