package com.eayoky.akillireklamyonetimi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EntryData extends AppCompatActivity{

    private EditText edt_ad,edt_lat,edt_long,edt_sure,edt_icerik,edt_kategori;
    private Button btn_ekle,btn_cik;
    private FirebaseAuth mAuth;
    private Firma firma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.entry_data);

        edt_ad=findViewById (R.id.edt_ad);
        edt_lat=findViewById (R.id.edt_lat);
        edt_long=findViewById (R.id.edt_long);
        edt_sure=findViewById (R.id.edt_sure);
        edt_icerik=findViewById (R.id.edt_icerik);
        btn_ekle=findViewById (R.id.btn_ekle);
        btn_cik=findViewById (R.id.btn_cik);
        edt_kategori=findViewById (R.id.kategori);
        btn_ekle.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReferenceUsers = FirebaseDatabase.getInstance().getReference("users");
                String id = databaseReferenceUsers.push().getKey();
                firma = new Firma (id,edt_ad.getText().toString(),edt_lat.getText ().toString (),edt_long.getText ().toString (),edt_icerik.getText ().toString (),edt_sure.getText ().toString (),edt_kategori.getText ().toString ());
                databaseReferenceUsers.child(id).setValue(firma);
                mAuth.signInWithEmailAndPassword(id,edt_ad.toString ()); //edt_ad,edt_lat,edt_long,edt_icerik,edt_sure);
                if(firma.getFirmaAdi ().isEmpty() || firma.getFirmaLat ().isEmpty()||firma.getFirmaLon ().isEmpty()||firma.getKampanyaIcerik ().isEmpty()||firma.getKampanyaSuresi ().isEmpty()||firma.getKategori ().isEmpty ()){
                    Toast.makeText(getApplicationContext(),"Lütfen gerekli alanları doldurunuz!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Başarılı Bir Şekilde Eklendi",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(EntryData.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
        mAuth = FirebaseAuth.getInstance();
        FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener ( ) {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser ( );
                if (user != null) {
                    // User is signed in
                    //Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Toast.makeText (getApplicationContext ( ), "Successfully signed in with: " + firma.getFirmaAdi ( ), Toast.LENGTH_LONG).show ( );
                } else {
                    // User is signed out
                    Toast.makeText (getApplicationContext ( ), "Successfully signed out.", Toast.LENGTH_LONG).show ( );

                }
                // ...
            }
        };
        btn_cik.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EntryData.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}
