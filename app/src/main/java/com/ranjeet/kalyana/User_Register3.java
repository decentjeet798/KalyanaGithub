package com.ranjeet.kalyana;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class User_Register3 extends Activity {

    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__register3);

        addListenerOnButton();
    }
    public void addListenerOnButton() {

        final Context context = this;

        button2 = (Button) findViewById(R.id.button_continue_2);

        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, User_Register4.class);
                startActivity(intent);

            }

        });


    }

}
