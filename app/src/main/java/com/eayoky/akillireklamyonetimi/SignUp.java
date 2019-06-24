package com.eayoky.akillireklamyonetimi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {
    private Button signUp,loginPage;
    private EditText username,password;
    private FirebaseAuth mAuth;
    private User user = new User ();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        signUp=findViewById (R.id.signUp);
        username=findViewById (R.id.kadi);
        password=findViewById (R.id.sifre);
        mAuth = FirebaseAuth.getInstance();
        loginPage=findViewById (R.id.loginPage);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = new User(username.getText().toString(),password.getText ().toString ());
                if(user.getUserName ().isEmpty() || user.getUserPass ().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Lütfen gerekli alanları doldurunuz!",Toast.LENGTH_SHORT).show();
                }else{
                    registerFunc();
                }
            }
        });

        loginPage.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Login.class);
                startActivity(intent);
            }
        });
    }
    private void registerFunc() {
        mAuth.createUserWithEmailAndPassword(user.getUserName (),user.getUserPass ())
                .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult> () {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent i = new Intent(SignUp.this,Login.class);
                            startActivity(i);
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }});
    }
}

///https://www.mobilhanem.com/android-firebase-authentication-kimlik-dogrulama/ intial edilmiştir.


    /*
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.sign_up);

        signUp=findViewById (R.id.signUp);
        loginPage=findViewById (R.id.loginPage);
        username=findViewById (R.id.kadi);
        password=findViewById (R.id.sifre);
        signUp.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                ///Üye OL
                DatabaseReference databaseReferenceUsers = FirebaseDatabase.getInstance().getReference("users");
                String id = databaseReferenceUsers.push().getKey();
                User user = new User(username.getText().toString(),password.getText ().toString ());
                databaseReferenceUsers.child(id).setValue(user);

                Toast.makeText(getApplicationContext(), "Başarılı Bir Şekilde Üye Olundu..", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getBaseContext(), Login.class);
                startActivity(intent);
            }
        });

        loginPage.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Login.class);
                startActivity(intent);
            }
        });

    }*/

