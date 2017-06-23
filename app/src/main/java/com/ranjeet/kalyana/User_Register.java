package com.ranjeet.kalyana;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class User_Register extends Activity {
    private EditText edt_email,edt_password;
    EditText etName,etLastName;
    private CheckBox mShowPwd;
    private Button button;
    private TextView textView;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__register);
        edt_password = (EditText) findViewById(R.id.edt_password);
        mShowPwd = (CheckBox) findViewById(R.id.chbox_showpassword);
        etName = (EditText) findViewById(R.id.edit_first);
        etLastName= (EditText) findViewById(R.id.edit_second);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

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

    public void addListenerOnNextButton() {
        final Context context = this;
        edt_email= (EditText) findViewById(R.id.edt_email);
        button = (Button) findViewById(R.id.button_next);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                final String email = edt_email.getText().toString().trim();
                String password = edt_password.getText().toString().trim();
                final String name = etName.getText().toString().trim();
                final String last = etLastName.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(User_Register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {

                        Toast.makeText(User_Register.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                        if (!task.isSuccessful()) {
                            Toast.makeText(User_Register.this, "Authentication failed." + task.getException(), Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent=new Intent(User_Register.this, User_Register2.class);
                            intent.putExtra("Name",name);
                            intent.putExtra("Last",last);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
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