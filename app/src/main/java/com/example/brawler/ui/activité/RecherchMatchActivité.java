package com.example.brawler.ui.activité;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.brawler.DAO.SourceLikeApi;
import com.example.brawler.DAO.SourceUtilisateursApi;
import com.example.brawler.R;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.présenteur.PrésenteurRechercheMatch;
import com.example.brawler.présentation.vue.VueRechercheMatch;
import com.example.brawler.ui.activité.Services.ServiceNotificationMessage;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class RecherchMatchActivité extends AppCompatActivity {

    private PrésenteurRechercheMatch présenteur;
    private String clé;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1001;
    private boolean locationPermissionGranted;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ServiceNotificationMessage.démarerJob(getApplicationContext());

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        clé = sharedPref.getString("token", "");
        if(clé.trim().isEmpty()){
            startActivity(new Intent(this, ConnexionActivité.class));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.navigation_app);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        Modèle modèle = new Modèle();
        VueRechercheMatch vue = new VueRechercheMatch();
        présenteur = new PrésenteurRechercheMatch(vue, modèle);
        présenteur.setSourceUtilisateurs(new SourceUtilisateursApi(clé));
        présenteur.setSourceLike(new SourceLikeApi(clé));
        vue.setPrésenteur(présenteur);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLocationPermission();

        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layoutPrincipal, vue);
        ft.commit();

    }

    @Override
    public void onStart() {
        super.onStart();
        présenteur.lancerChargerUtilisateur();
        getDeviceLocation();
    }

    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        clé = sharedPref.getString("token", "");
        présenteur.setSourceUtilisateurs(new SourceUtilisateursApi(clé));
        présenteur.setSourceLike(new SourceLikeApi(clé));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_navigation_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_profil:
                Intent profil = new Intent(this, ConsulterProfilActivité.class);
                startActivity(profil);
                break;
            case R.id.menu_match:
                break;
            case R.id.menu_contact:
                Intent contact = new Intent(this,CommunicationUtilisateurs.class);
                startActivity(contact);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Prompts the user for permission to use the device location.
     */
    private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                locationPermissionGranted = true;
                getDeviceLocation();
            }else{
                getLocationPermission();
            }
        }
    }

    private void getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
                locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null){
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            Locale currentLocale = getResources().getConfiguration().locale;
                            Geocoder geocoder = new Geocoder(getApplicationContext(), currentLocale);
                            try {
                                List<Address> adressesConnues =
                                        geocoder.getFromLocation(latitude, longitude, 1);
                                Address adresseConnue = adressesConnues.get(0);
                                String localité = adresseConnue.getLocality();
                                présenteur.lancerFilEsclaveMettreLocalisationAJour(clé, localité);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

                locationTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Location Failure: ", e.getLocalizedMessage());
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }


}
