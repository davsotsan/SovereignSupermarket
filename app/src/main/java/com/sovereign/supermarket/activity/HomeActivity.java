package com.sovereign.supermarket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sovereign.supermarket.R;

import org.jetbrains.annotations.NotNull;

/**
 * Created by JoseV on 7/7/17.
 */

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    Button buttonRegister, buttonSignIn;
    EditText editTextEmail, editTextPass;

    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);

        buttonRegister=(Button) findViewById(R.id.register_button);
        buttonSignIn=(Button) findViewById(R.id.signin_button);
        editTextEmail=(EditText) findViewById(R.id.login_email);
        editTextPass=(EditText) findViewById(R.id.login_password);

        buttonRegister.setOnClickListener(this);
        buttonSignIn.setOnClickListener(this);

        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NotNull FirebaseAuth firebaseAuth){
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    Log.i("SESION", "sesion iniciada con email"+ user.getEmail());

                }else{
                    Log.i("SESION", "sesion cerrada");
                }
            }

        };


    }


    private void registrar(String email, String pass){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.i("SESION", "usuario creado correctamente");
                }else{
                    Log.e("SESION", task.getException().getMessage()+"");
                }
            }
        });
    }

    private void iniciarSesion(String email, String pass){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,pass);

    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.signin_button:
                String emailInicio =  editTextEmail.getText().toString();
                String passInicio =  editTextPass.getText().toString();
                iniciarSesion(emailInicio,passInicio);
                Intent intent = new Intent(HomeActivity.this, SignIn.class);
                break;
            case R.id.register_button:
                String emailReg =  editTextEmail.getText().toString();
                String passReg =  editTextPass.getText().toString();
                registrar(emailReg,passReg);
                break;
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(mAuthListener!=null){
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }


}


