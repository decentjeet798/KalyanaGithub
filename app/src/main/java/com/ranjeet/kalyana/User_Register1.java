package com.ranjeet.kalyana;



import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class User_Register1 extends Activity {
    String Storage_Path = "All_Image_Uploads/";
    String Database_Path = "User_Database";
    Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog ;
    Spinner sprofile,ssis,sbro,sgender,sreligion,stongue,scountry;
    Snackbar  mSnackbar;


    ImageView viewImage;
    Button b;
    private EditText edt_email,edt_password;
    private  EditText etName,etLastName;
    private CheckBox mShowPwd;
    private Button button;
    private TextView textView;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__register1);
        edt_password = (EditText) findViewById(R.id.edt_password);
        mShowPwd = (CheckBox) findViewById(R.id.chbox_showpassword);

        initViews();
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);
        progressDialog = new ProgressDialog(User_Register1.this);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);
            }
        });


        mShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    edt_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {

                    edt_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });



        addListenerOnNextButton();
        addListenerOnText();

    }
   public void initViews(){
       edt_email= (EditText) findViewById(R.id.edt_email);
       button = (Button) findViewById(R.id.button_next);
       etName = (EditText) findViewById(R.id.edit_first);
       etLastName= (EditText) findViewById(R.id.edit_second);
        sprofile= (Spinner) findViewById(R.id.spinner_profile);
        ssis= (Spinner) findViewById(R.id.spinner_sis);
        sbro= (Spinner) findViewById(R.id.spinner_bro);
        sgender= (Spinner) findViewById(R.id.spinner_gender);
        sreligion= (Spinner) findViewById(R.id.spinner_religion);
        stongue= (Spinner) findViewById(R.id.spinner_tongue);
        scountry= (Spinner) findViewById(R.id.spinner_country);
        b=(Button)findViewById(R.id.btnSelectPhoto);
        viewImage=(ImageView)findViewById(R.id.viewImage);
    }

    public void addListenerOnNextButton() {
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                final String email = edt_email.getText().toString().trim();
                String password = edt_password.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    mSnackbar = Snackbar.make(arg0, "Please Enter email address!", Snackbar.LENGTH_LONG);
                    TextView mainTextView = (TextView) (mSnackbar.getView()).findViewById(android.support.design.R.id.snackbar_text);
                    TextView actionTextView = (TextView) (mSnackbar.getView()).findViewById(android.support.design.R.id.snackbar_action);
                    mainTextView.setTextColor(Color.WHITE);
                    actionTextView.setTextColor(Color.WHITE);
                    mSnackbar.show();
                    //Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String Expn =
                        "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                                +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                                +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                                +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

                if (!email.matches(Expn) && email.length() > 0)
                {
                    mSnackbar = Snackbar.make(arg0, "Please Enter Valid EmailId!", Snackbar.LENGTH_LONG);
                    TextView mainTextView = (TextView) (mSnackbar.getView()).findViewById(android.support.design.R.id.snackbar_text);
                    TextView actionTextView = (TextView) (mSnackbar.getView()).findViewById(android.support.design.R.id.snackbar_action);
                    mainTextView.setTextColor(Color.WHITE);
                    actionTextView.setTextColor(Color.WHITE);
                    mSnackbar.show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mSnackbar = Snackbar.make(arg0, "Please Enter password!", Snackbar.LENGTH_LONG);
                    TextView mainTextView = (TextView) (mSnackbar.getView()).findViewById(android.support.design.R.id.snackbar_text);
                    TextView actionTextView = (TextView) (mSnackbar.getView()).findViewById(android.support.design.R.id.snackbar_action);
                    mainTextView.setTextColor(Color.WHITE);
                    actionTextView.setTextColor(Color.WHITE);
                    mSnackbar.show();

                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(User_Register1.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        UploadImageFileToFirebaseStorage();
                        Toast.makeText(User_Register1.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                        if (!task.isSuccessful()) {
                            Toast.makeText(User_Register1.this, "Authentication failed." + task.getException(), Toast.LENGTH_SHORT).show();
                        } else {

                            Intent intent=new Intent(User_Register1.this, User_Register2.class);
                            //intent.putExtra("Name",name);
                            //intent.putExtra("Last",last);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
                UploadImageFileToFirebaseStorage();


            }

        });
    }






    public void addListenerOnText() {
        final Context context = this;

        textView = (TextView) findViewById(R.id.text_login);

        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);

            }

        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {
            FilePathUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                viewImage.setImageBitmap(bitmap);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String GetFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;
    }

    public void UploadImageFileToFirebaseStorage() {

            progressDialog.setTitle("User Details is Uploading...");
            progressDialog.show();
                  StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(), "uploading  Successfully ", Toast.LENGTH_SHORT).show();
                            String name = etName.getText().toString().trim();
                            String last = etLastName.getText().toString().trim();
                            String profile= sprofile.getSelectedItem().toString();
                            String sister= ssis.getSelectedItem().toString();
                            String brother= sbro.getSelectedItem().toString();
                            String gender= sgender.getSelectedItem().toString();
                            String religion= sreligion.getSelectedItem().toString();
                            String tong= stongue.getSelectedItem().toString();
                            String country= scountry.getSelectedItem().toString();

                            if(TextUtils.isEmpty(name)){
                                etName.setError("First name not entered");
                                etName.requestFocus();
                                return;
                            }
                            if(TextUtils.isEmpty(last)){
                                etName.setError("Last name not entered");
                                etName.requestFocus();
                                return;
                            }


                            progressDialog.dismiss();

                            @SuppressWarnings("VisibleForTests")
                            User user = new User(profile,name,last,sister,brother,gender,religion,tong,country,taskSnapshot.getDownloadUrl().toString());
                            String ImageUploadId = databaseReference.push().getKey();
                            databaseReference.child(ImageUploadId).setValue(user);
                            progressDialog.setTitle("data is added...");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(User_Register1.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.setTitle("data is Uploading...");
                        }
                    });


    }


    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_action_alert)
                .setTitle("Kalyana Vaibhavam")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();


    }

}