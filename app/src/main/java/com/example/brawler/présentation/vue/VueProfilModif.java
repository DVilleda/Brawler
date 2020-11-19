package com.example.brawler.présentation.vue;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.brawler.R;
import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.présentation.présenteur.PrésenteurProfil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VueProfilModif extends Fragment {

    private PrésenteurProfil _presenteur;
    //Codes pour les requêtes et résultats des Intent
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int STORAGE_PERMISSION_WRITE_CODE = 102;
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;
    private String _PhotoCurrentPath;

    private EditText nomProfil;
    private EditText locationProfil;
    private EditText ageProfil;
    private EditText distanceProfil;
    private EditText emailProfil;
    private TextView niveauAdversaire;
    private EditText descriptionProfil;
    private Button confirmerModif;
    private Button annulerModif;
    private Utilisateur utilisateurActuel;
    private Button chargerPhoto;

    public void setPresenteur(PrésenteurProfil présenteurProfil){
        _presenteur = présenteurProfil;
        utilisateurActuel = présenteurProfil.getUtilisateur();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View vue = inflater.inflate(R.layout.fragment_profil_modif,container,false);
        nomProfil = vue.findViewById(R.id.Nom);
        emailProfil = vue.findViewById(R.id.email_edit);
        locationProfil = vue.findViewById(R.id.Location);
        ageProfil = vue.findViewById(R.id.Age);
        distanceProfil = vue.findViewById(R.id.Distance);
        niveauAdversaire = vue.findViewById(R.id.Niveau);
        descriptionProfil = vue.findViewById(R.id.Description);
        confirmerModif = vue.findViewById(R.id.ModifierProfil);
        annulerModif = vue.findViewById(R.id.AnnulerModif);
        chargerPhoto = vue.findViewById(R.id.modifier_photo_btn);
        chargerPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ChoisirPhotoProfil();
            }
        });

        registerForContextMenu(niveauAdversaire);

        chargerInfosActuel(utilisateurActuel);

        confirmerModif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                montrerAlerteModif();
            }
        });

        annulerModif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        return vue;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v,
                                    @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.niveau_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.option_1:
                niveauAdversaire.setText("Débutant");
                return true;
            case R.id.option_2:
                niveauAdversaire.setText("Intermédiaire");
                return true;
            case R.id.option_3:
                niveauAdversaire.setText("Expert");
                return true;
            case R.id.option_4:
                niveauAdversaire.setText("Légendaire");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);
        if(resultCode== Activity.RESULT_OK){
            if(requestCode==REQUEST_IMAGE_CAPTURE){
                File fichierPhoto = new File(_PhotoCurrentPath);
                try {
                    //On verifie l'orientation de la photo
                    ExifInterface exifInterface = new ExifInterface(_PhotoCurrentPath);
                    int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_UNDEFINED);
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                            this.getContext().getContentResolver(),Uri.fromFile(fichierPhoto));
                    Bitmap bitmapPhoto = null;
                    //Le switch case va verifier le parametre d'orientation de la photo et ajuster l'angle selon le cas
                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            bitmapPhoto = changerOrientationImage(bitmap, 90);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_180:
                            bitmapPhoto = changerOrientationImage(bitmap, 180);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_270:
                            bitmapPhoto = changerOrientationImage(bitmap, 270);
                            break;

                        case ExifInterface.ORIENTATION_NORMAL:
                        default:
                            bitmapPhoto = bitmap;
                    }
                    if(bitmapPhoto != null)
                    {
                        //Conversion de la photo prise par la caméra en byte[] et ajouter au profil
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmapPhoto.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] photoProfil = stream.toByteArray();
                        utilisateurActuel.setPhoto(photoProfil);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if(requestCode==RESULT_LOAD_IMAGE){
                //Obtenir le URI de la photo dans le téléphone
                Uri uriImageChoisi = data.getData();
                /**Obtention de la photo et conversion de la photo en bitmap puis ajouter au profil
                 * Utiliser le try catch pour erreurs de IO
                 */
                try {
                    final Bitmap bmpPhotoChoisi = MediaStore.Images.Media.getBitmap(
                            this.getContext().getContentResolver(), uriImageChoisi);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmpPhotoChoisi.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    utilisateurActuel.setPhoto(byteArray);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this.getContext(),"Permission à la caméra accordée",
                        Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this.getContext(),"Permission à la caméra pas accordée",
                        Toast.LENGTH_LONG).show();
            }
        }
        else if(requestCode == STORAGE_PERMISSION_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this.getContext(),"Permission au stockage accordé",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this.getContext(),"Permission au stockage pas accordé",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Cette ce charge de verifier l'orientation de la photo puis changer cette orientation selon un angle
     * @param bitmap la photo
     * @param angle l'angle de rotation
     * @return la photo avec une nouvelle rotation
     */
    public static Bitmap changerOrientationImage(Bitmap bitmap, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                matrix, true);
    }

    public void chargerInfosActuel(Utilisateur utilisateur){
        if(utilisateur != null) {
            nomProfil.setText(utilisateur.getNom());
            emailProfil.setText(utilisateur.getEmail());
            locationProfil.setText(utilisateur.getLocation());
            niveauAdversaire.setText(utilisateur.getNiveau().toString().toLowerCase());
            descriptionProfil.setText(utilisateur.getDescription());
        }else{
            Toast.makeText(getContext(),"Erreur de chargement du profil...",Toast.LENGTH_SHORT).show();
        }
    }

    public void VerificationPermissions(String permission, int requestCode){
        if(ContextCompat.checkSelfPermission(this.getContext(),permission) == PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(this.getActivity(),new String[]{ permission },requestCode);
        }
    }

    private File SauvegarderPhotoCamera() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "PNG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );
        _PhotoCurrentPath = image.getAbsolutePath();
        return image;
    }

    public void ChoisirPhotoProfil(){
        //Les choix pour ce AlertDialog
        final CharSequence[] items={"Caméra","Galerie", "Annuler"};

        AlertDialog.Builder dialogueChoisirPhoto = new AlertDialog.Builder(this.getContext());
        dialogueChoisirPhoto.setTitle("Changer la photo profil?");
        dialogueChoisirPhoto.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Caméra")) {
                    VerificationPermissions(Manifest.permission.CAMERA,CAMERA_PERMISSION_CODE);
                    VerificationPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,STORAGE_PERMISSION_WRITE_CODE);
                    if(ContextCompat.checkSelfPermission(
                            getContext(),Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(
                            getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                        //Creation du Intent et fichier pour la photo
                        Intent prendrePhotoCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File profilePhoto = null;
                        try {
                            profilePhoto = SauvegarderPhotoCamera();
                        }catch (IOException e)
                        {
                            Log.e("Errreur IO","Erreur de creation de fichier");
                        }
                        if(profilePhoto != null)
                        {
                            Uri photoURI = FileProvider.getUriForFile(getContext(),"com.example.brawler.fileprovider",profilePhoto);
                            prendrePhotoCamera.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                            startActivityForResult(prendrePhotoCamera, REQUEST_IMAGE_CAPTURE);
                        }
                    }
                } else if (items[i].equals("Galerie")) {
                    VerificationPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,STORAGE_PERMISSION_CODE);
                    if(ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                        Intent obtenirPhotoGalerie = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        obtenirPhotoGalerie.setType("image/*");
                        startActivityForResult(obtenirPhotoGalerie, RESULT_LOAD_IMAGE);
                    }
                } else if (items[i].equals("Annuler")) {
                    dialogInterface.cancel();
                }
            }
        });
        dialogueChoisirPhoto.show();
    }

    public void montrerAlerteModif()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Appliquer les changements au profil?");
        builder.setCancelable(true);

        builder.setPositiveButton("Oui",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        utilisateurActuel.setNom(nomProfil.getText().toString());
                        utilisateurActuel.setEmail(emailProfil.getText().toString());
                        utilisateurActuel.setLocation(locationProfil.getText().toString());
                        utilisateurActuel.setNiveau(Niveau.valueOf(niveauAdversaire.getText().toString().toUpperCase()));
                        utilisateurActuel.setDescription(descriptionProfil.getText().toString());
                        _presenteur.modifierUtilisateur(utilisateurActuel);
                        Toast.makeText(getContext(),"Modifications apportées",Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                });
        builder.setNegativeButton("Non",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog avertissement = builder.create();
        avertissement.show();
    }
}
