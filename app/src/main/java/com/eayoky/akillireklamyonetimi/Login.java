package com.eayoky.akillireklamyonetimi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.ButterKnife;

public class Login extends Activity {

            private Button girisYap;
            private TextView uyeOl;
            private EditText kadi, sif;
            private FirebaseAuth mAuth;
            private FirebaseUser firebaseUser;
            private User user = new User ( );

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate (savedInstanceState);
                setContentView (R.layout.login);
                ButterKnife.bind (this);

        uyeOl = findViewById (R.id.link_signup);
        girisYap = findViewById (R.id.btn_login);
        kadi = findViewById (R.id.input_email);
        sif = findViewById (R.id.input_password);
        uyeOl.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getBaseContext ( ), SignUp.class);
                startActivity (intent);
            }
        });

        mAuth = FirebaseAuth.getInstance ( );
        firebaseUser = mAuth.getCurrentUser ( );

        girisYap.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                user = new User (kadi.getText ( ).toString ( ), sif.getText ( ).toString ( ));
                //userName = kadi.getText ( ).toString ( );
                // userPassword = sif.getText ( ).toString ( );
                if (user.getUserName ( ).isEmpty ( ) || user.getUserPass ( ).isEmpty ( )) {

                    Toast.makeText (getApplicationContext ( ), "Lütfen gerekli alanları doldurunuz!", Toast.LENGTH_SHORT).show ( );

                } else {

                    loginFunc ( );
                }
            }
        });

    }

    private void loginFunc() {
        mAuth.signInWithEmailAndPassword (user.getUserName ( ), user.getUserPass ( )).addOnCompleteListener (Login.this,
                new OnCompleteListener <AuthResult> ( ) {
                    @Override
                    public void onComplete(@NonNull Task <AuthResult> task) {
                        if (task.isSuccessful ( )) {

                            Intent i = new Intent (Login.this, MainActivity.class);
                            startActivity (i);
                            finish ( );
                        } else {
                            Toast.makeText (getApplicationContext ( ), task.getException ( ).getMessage ( ), Toast.LENGTH_SHORT).show ( );
                        }
                    }
                });
    }
}

