package com.ranjeet.kalyana;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.content.Context;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends Activity {
    EditText et_email,et_password;
    Button login,joinus,btn_reset_password;
    final Context context = this;
    private FirebaseAuth auth;
    private Snackbar mSnackbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        addListnerOnForget();
        addListenerOnLogin();
        addListenerOnJoinus();
    }

    private void addListnerOnForget() {

        btn_reset_password= (Button) findViewById(R.id.btn_reset_password);
        btn_reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_reset_password, null);
        dialogBuilder.setView(dialogView);

        final EditText editEmail = (EditText) dialogView.findViewById(R.id.email);
        final Button btnReset = (Button) dialogView.findViewById(R.id.btn_reset_password);
        final ProgressBar progressBar1 = (ProgressBar) dialogView.findViewById(R.id.progressBar);
        final Button backbtn= (Button) dialogView.findViewById(R.id.btn_back);

        //dialogBuilder.setTitle("Send Photos");
        final AlertDialog dialog = dialogBuilder.create();
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email = editEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar1.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                }

                                progressBar1.setVisibility(View.GONE);
                                dialog.dismiss();
                            }
                        });

            }
        });
        dialog.show();
    }

    public void addListenerOnJoinus() {
        joinus = (Button) findViewById(R.id.register_btn);
        joinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, User_Register1.class);
                startActivity(intent);
            }
        });

    }

    public void addListenerOnLogin(){
        final String regEx =
                "^(([w-]+.)+[w-]+|([a-zA-Z]{1}|[w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9]).([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9]).([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[w-]+.)+[a-zA-Z]{2,4})$";
        et_email= (EditText) findViewById(R.id.email);
        et_password= (EditText) findViewById(R.id.password);
        login= (Button) findViewById(R.id.login_btn);
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString();
                final String password = et_password.getText().toString();

                if (TextUtils.isEmpty(email)) {
                     mSnackbar = Snackbar.make(v, "Please Enter EmailId!", Snackbar.LENGTH_LONG);
                    TextView mainTextView = (TextView) (mSnackbar.getView()).findViewById(android.support.design.R.id.snackbar_text);
                    TextView actionTextView = (TextView) (mSnackbar.getView()).findViewById(android.support.design.R.id.snackbar_action);
                    mainTextView.setTextColor(Color.WHITE);
                    actionTextView.setTextColor(Color.WHITE);
                    mSnackbar.show();
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
                    mSnackbar = Snackbar.make(v, "Please Enter Valid EmailId!", Snackbar.LENGTH_LONG);
                    TextView mainTextView = (TextView) (mSnackbar.getView()).findViewById(android.support.design.R.id.snackbar_text);
                    TextView actionTextView = (TextView) (mSnackbar.getView()).findViewById(android.support.design.R.id.snackbar_action);
                    mainTextView.setTextColor(Color.WHITE);
                    actionTextView.setTextColor(Color.WHITE);
                    mSnackbar.show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mSnackbar = Snackbar.make(v, "Please Enter Password!", Snackbar.LENGTH_LONG);
                    TextView mainTextView = (TextView) (mSnackbar.getView()).findViewById(android.support.design.R.id.snackbar_text);
                    TextView actionTextView = (TextView) (mSnackbar.getView()).findViewById(android.support.design.R.id.snackbar_action);
                    mainTextView.setTextColor(Color.WHITE);
                    actionTextView.setTextColor(Color.WHITE);
                    mSnackbar.show();
                    return;
                }
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            if (password.length() < 6) {
                                et_password.setError(getString(R.string.minimum_password));
                            }
                            else {
                                Toast.makeText(MainActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Intent intent = new Intent(MainActivity.this, User_Register4.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

            }
        });
    }

}
