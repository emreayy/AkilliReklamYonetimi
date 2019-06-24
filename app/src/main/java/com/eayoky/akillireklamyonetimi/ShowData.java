package com.eayoky.akillireklamyonetimi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class ShowData extends AppCompatActivity {

    String TAG="Firelog";
    DatabaseReference ref;
    ArrayList<Firma>list;
    RecyclerView recyclerView;
    SearchView searchVie;
    EditText editText,edtLat,edtLon;
    private RecyclerView.LayoutManager mLayoutManager;
    private LocationManager locationManager;
    private LocationListener listener;
    private double lat12,lon12;
    private NotificationCompat.Builder notBuilder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.view_database_layout);

        ref= FirebaseDatabase.getInstance ().getReference ().child ("users");
        recyclerView=findViewById (R.id.rv);
        searchVie=findViewById (R.id.searhView);
        editText = findViewById (R.id.howMeter);
        edtLat = findViewById (R.id.lat);
        edtLon = findViewById (R.id.lon);

        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager (this);
        recyclerView.setLayoutManager(mLayoutManager);

        //System.out.println("distance:"+mesafeHesapla(40.762402, 29.932949, 40.762841, 29.940864)+" metre");

        locationManager = (LocationManager) getSystemService (LOCATION_SERVICE);
        listener = new LocationListener ( ) {
            @Override
            public void onLocationChanged(Location location) {

                double te = location.getLatitude ();
                double ter = location.getLongitude ();


                /*DecimalFormat precision = new DecimalFormat("#.###");
                String formattedString = precision.format(te);

                Log.d ("TATAA",formattedString);*/

                String numberStr = Double.toString(te);
                String numberStr2 = Double.toString(ter);

                edtLat.setText (numberStr);
                edtLon.setText (numberStr2);
                //edtLat.setText (String.format("%.3f", location.getLatitude ()));
                //edtLon.setText (String.format("%.3f", location.getLongitude ()));

            }
            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }
            @Override
            public void onProviderEnabled(String s) {

            }
            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent (Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity (i);
            }
        };

        configure_button ( );

    }
        //// DOUBLE CHANGE STRİNG AND RETURN DOUBLE.PARSDOUBLE ADD
    public static String mesafeHesapla(double lat1, double lon1, double lat2, double lon2) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return "0";
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344; //convert to kilometer
            dist = dist * 1000; //convert to meter

            String numberStr = Double.toString(dist);
            return numberStr;
            //DecimalFormat formater = new DecimalFormat ("#0.00");
            //return Double.parseDouble (((formater.format(dist))));
        }
    }
    @Override
    protected void onStart() {
        super.onStart ( );
        if(ref!=null){
            ref.addValueEventListener (new ValueEventListener ( ) {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists ()){
                        list=new ArrayList<> ();
                        for (DataSnapshot da:dataSnapshot.getChildren ()){
                            list.add (da.getValue (Firma.class));

                        }
                        AdapterClass adapterClass=new AdapterClass (list);
                        recyclerView.setAdapter (adapterClass);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText (ShowData.this,databaseError.getMessage (),Toast.LENGTH_SHORT).show ();
                }
            });
        }
        if(searchVie!=null){
            searchVie.setOnQueryTextListener (new SearchView.OnQueryTextListener ( ) {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return false;
                }

            });
        }
    }

    private void search(String str) {
        ArrayList<Firma> myList=new ArrayList <> ();
        ArrayList<Firma> myList2=new ArrayList <> ();
        double max=0;
        for(Firma object :list){

            if (object.getKategori ().toLowerCase ().contains (str.toLowerCase ()) || object.getFirmaAdi ().toLowerCase ().contains (str.toLowerCase ())){
                myList.add (object);
                myList2.add (object);
            }

            String a = object.getFirmaLat ();
            String b = object.getFirmaLon ();

            double lat22=Double.parseDouble (a);
            double lon22=Double.parseDouble (b);

            if(editText.getText ().toString ().equals ("") || editText.getText ()==null ){
            max = 99999999;}
            else {
            String strMax = String.valueOf (editText.getText ());
            max = Double.parseDouble (strMax);}

            int c = control (lat22,lon22,max);

            if(c==0){
            myList.remove (object);
            myList2.remove (object);
            }

            for (int i=0; i<myList2.size (); i++){
            noti (myList2.get (i).getFirmaAdi (),myList2.get (i).getKategori ());

            }

            /*
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace ( );
            }*/

        }

        AdapterClass adapterClass = new AdapterClass (myList);
        recyclerView.setAdapter (adapterClass);


    }

    public void noti(String ad,String kategori){

        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(getApplicationContext(), ShowData.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,0);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "101";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification", NotificationManager.IMPORTANCE_MAX);

            //Configure Notification Channel
            notificationChannel.setDescription("Game Notifications");
            notificationChannel.enableLights(true);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);

            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(ad)
                .setAutoCancel(true)
                .setSound(defaultSound)
                .setContentText(kategori)
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_MAX);


        notificationManager.notify(1, notificationBuilder.build());
    }
    public int control(Double lat22, Double lon22,double limit){

        //lat12 = 40.762402;
        //lon12 = 29.932949;


        double aa = Double.parseDouble (edtLat.getText ().toString ());
        double bb = Double.parseDouble (edtLon.getText ().toString ());

        lat12 = aa;
        lon12 = bb;

        Log.d ("TAG ",lat12 + " TAG2 "+ lon12 ) ;

        String strMetre = mesafeHesapla (lat12,lon12,lat22,lon22);

        double distance2 = Double.parseDouble (strMetre);

        //String numberStr = Double.toString(distance2);

        //double dis = Integer.parseInt (numberStr);
        if(limit <= distance2)
            return 0; /// edittext = 100, limit = 600 /// objecti kaldır
                else
                    return 1;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                configure_button ( );
                break;
            default:
                break;
        }
    }
    void configure_button() {
        // first check for permissions
        if (ActivityCompat.checkSelfPermission (this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission (this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions (new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
            return;
        }
                if (ActivityCompat.checkSelfPermission (ShowData.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission (ShowData.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates ("gps", 60000, 0, listener);
    }

}
