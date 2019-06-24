package com.eayoky.akillireklamyonetimi;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {

    private EditText kadi;
    private Button sifreDegistir,cikis;
    private String str;
    private NavigationView navbar;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.change_password);

        kadi=findViewById (R.id.userNameTxt);
        sifreDegistir=findViewById (R.id.changePasswordBtn);
        cikis=findViewById (R.id.signOutBtn);

        auth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser(); // get current user

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) { // when auth state change
                    finish();
                }
            }
        };

        kadi.setText("Kullanıcı Adı:" + " " + auth.getCurrentUser().getEmail());
        sifreDegistir.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                str = "Lütfen yeni şifreyi giriniz.";
                 changeEmailOrPasswordFunc(str,false);
            }
        });

        cikis.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent (getBaseContext ( ), MainActivity.class);
                startActivity (intent);
                finish ();
            }
        });
    }

    private void changeEmailOrPasswordFunc(String title, final boolean option) {

        AlertDialog.Builder builder = new AlertDialog.Builder(
                ChangePassword.this);
        final EditText edit = new EditText(ChangePassword.this);
        builder.setPositiveButton(getString(R.string.Email), null);
        builder.setNegativeButton(getString(R.string.Parola), null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        edit.setLayoutParams(lp);
        if(!option){  // password type
            edit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        builder.setTitle(title);
        builder.setView(edit);

        final AlertDialog mAlertDialog = builder.create();
        mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if(edit.getText().toString().isEmpty()){
                            edit.setError("Lütfen ilgili alanı doldurunuz!");

                        }else{
                                changePassword();
                            }
                    }
                });
            }
            private void changePassword() {

                firebaseUser.updatePassword(edit.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ChangePassword.this, "Şifre değiştirildi.", Toast.LENGTH_LONG).show();
                                    signOutFunc();
                                    auth.signOut();
                                    Intent intent = new Intent (getBaseContext ( ), Login.class);
                                    startActivity (intent);
                                    finish ();
                                } else {
                                    edit.setText("");
                                    Toast.makeText(ChangePassword.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
        mAlertDialog.show();

    }
    private void signOutFunc() {
        auth.signOut();
    }
}