package code.anish.sqlitecrudx;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.net.IDN;

public class EditRecordActivity extends AppCompatActivity {
    private ImageView pImageView;
    private EditText pNameEt, pAgeEt, pPhoneEt;
    Button saveInfoBt;
    ActionBar actionBar;

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;

    private static final int IMAGE_PICK_CAMERA_CODE= 102;
    private static final int IMAGE_PICK_GALLERY_CODE= 103;

    private String[] cameraPermissions;
    private String[] storagePermissions;

    private Uri imageUri;

    private String id, name, age, phone, addTimeStamp, updateTimeStamp;
    private boolean editMode = false;
    private DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record);


        actionBar = getSupportActionBar();
        actionBar.setTitle("Add Information");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        pImageView = findViewById(R.id.personImage);
        pNameEt=findViewById(R.id.personName);
        pAgeEt=findViewById(R.id.personAge);
        pPhoneEt=findViewById(R.id.personPhone);

        saveInfoBt=findViewById(R.id.addButton);

        Intent intent = getIntent();
        editMode = intent.getBooleanExtra("editMode", editMode);
        id = intent.getStringExtra("ID");
        name = intent.getStringExtra("NAME");
        age = intent.getStringExtra("AGE");
        phone = intent.getStringExtra("PHONE");
        imageUri = Uri.parse(intent.getStringExtra("IMAGE"));
        addTimeStamp = intent.getStringExtra("ADD_TIMESTAMP");
        updateTimeStamp = intent.getStringExtra("UPDATE_TIMESTAMP");


        if (editMode){
            actionBar.setTitle("Update Information");

            editMode = intent.getBooleanExtra("editMode", editMode);
            id = intent.getStringExtra("ID");
            name = intent.getStringExtra("NAME");
            age = intent.getStringExtra("AGE");
            phone = intent.getStringExtra("PHONE");
            imageUri = Uri.parse(intent.getStringExtra("IMAGE"));
            addTimeStamp = intent.getStringExtra("ADD_TIMESTAMP");
            updateTimeStamp = intent.getStringExtra("UPDATE_TIMESTAMP");

            pNameEt.setText(name);
            pAgeEt.setText(age);
            pPhoneEt.setText(phone);

            if (imageUri.toString().equals("null")) {
                pImageView.setImageResource(R.drawable.add_photo);
            }
            else {
                pImageView.setImageURI(imageUri);
            }
        }

        else {
            actionBar.setTitle("Add Information");
        }


        cameraPermissions = new String[]{
                Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        storagePermissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        //initiate database object in main function
        dbHelper = new DatabaseHelper(this);



        pImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imagePickDialog();

            }
        });

        saveInfoBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //when click save button insert the data to db
                getData();
                startActivity(new Intent(EditRecordActivity.this, MainActivity.class));
                Toast.makeText(EditRecordActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getData()  {

        name = ""+pNameEt.getText().toString().trim();
        age = ""+pAgeEt.getText().toString().trim();
        phone = ""+pPhoneEt.getText().toString().trim();

        if  (editMode) {

            String newUpdateTime = ""+System.currentTimeMillis();

            dbHelper.updateInfo(
                    ""+id,
                    ""+name,
                    ""+age,
                    ""+phone,
                    ""+imageUri,
                    ""+addTimeStamp,
                    ""+newUpdateTime
            );
        }
        else {

            String timeStamp = "" + System.currentTimeMillis();

            dbHelper.insertInfo(
                    "" + name,
                    "" + age,
                    "" + phone,
                    "" + imageUri,
                    "" + timeStamp,
                    "" + timeStamp
            );
        }
    }

    private void imagePickDialog() {

        String[] options = {"Camera", "Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Select for Image");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i==0){
                    //if 0 then open camera and also check permission
                    if (!checkCameraPermission()){
                        requestCameraPermission();
                    }
                    else{
                        pickFromCamera();
                    }

                }
                else if (i==1){

                    if (!checkStoragePermission()){
                        requestStoragePermission();
                    }
                    else{
                        pickFromStorage();

                    }
                }
                builder.create().show();
            }
        });
    }

    private void pickFromStorage() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        super.startActivityForResult(Intent.createChooser(galleryIntent, " PICK IMAGE FROM GALLERY "),IMAGE_PICK_GALLERY_CODE);
    }

    private void pickFromCamera() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Image title");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image description");

        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        super.startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private boolean checkStoragePermission(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);

        return result;
    }

    private void requestStoragePermission(){

        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);

        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);

        return result && result1;

    }

    private void requestCameraPermission(){

        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){

            case CAMERA_REQUEST_CODE: {
                if (grantResults.length>0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && storageAccepted){
                        pickFromCamera();
                    }
                    else{
                        Toast.makeText(this, "Camera Permission Required", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;

            case STORAGE_REQUEST_CODE:{

                if (grantResults.length>0){

                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (storageAccepted){
                        pickFromStorage();
                    }
                    else {
                        Toast.makeText(this, "Storage Permission Required", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if  (resultCode == RESULT_OK){
            if (requestCode == IMAGE_PICK_GALLERY_CODE){

                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            }
            else if (resultCode == IMAGE_PICK_CAMERA_CODE){

                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                CropImage.ActivityResult result = CropImage.getActivityResult(data);

                if (resultCode == RESULT_OK){

                    Uri resultUri = result.getUri();
                    imageUri = resultUri;
                    pImageView.setImageURI(resultUri);
                }
                else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){

                    Exception error = result.getError();
                    Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}