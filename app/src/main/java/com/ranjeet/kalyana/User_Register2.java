package com.ranjeet.kalyana;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class User_Register2 extends Activity {
    TextView textView1,textView2;
    Button button1;

//jhghg
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__register2);
        textView1= (TextView) findViewById(R.id.name);
        textView2= (TextView) findViewById(R.id.last);
        Bundle bundle=getIntent().getExtras();
        String name=bundle.getString("Name");
        String last=bundle.getString("Last");
        textView1.setText(name);
        textView2.setText(last);
        addListenerOnButton();

    }
    public void addListenerOnButton() {

        final Context context = this;

        button1 = (Button) findViewById(R.id.button_continue);

        button1.setOnClickListener(
                new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, User_Register3.class);
                startActivity(intent);

            }

        });

    }

    }

