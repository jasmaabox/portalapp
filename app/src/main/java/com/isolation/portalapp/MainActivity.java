package com.isolation.portalapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.isolation.portalhelper.Credentials;
import com.isolation.portalhelper.Portal;
import com.isolation.portalhelper.SchoolClass;

import java.io.IOException;

public class MainActivity extends AppCompatActivity{

    String userStr;
    String passStr;
    Portal p = new Portal();

    Button loginBut;
    TextView user;
    TextView pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = ((TextView)findViewById(R.id.user));
        pass = ((TextView)findViewById(R.id.pass));

        loginBut = (Button) findViewById(R.id.login);
        loginBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userStr = user.getText().toString();
                passStr = pass.getText().toString();

                new PortalTask().execute();
            }
        });
    }



    class PortalTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                p.login(userStr, passStr);
                p.getGrades();

                for(SchoolClass c : p.classes){
                    System.out.println(c);
                }
            }
            catch(Exception e) {
                return null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            // Clear and click out
            user.setText("");
            pass.setText("");
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

            if(p == null){
                return;
            }

            Intent intent = new Intent(getBaseContext(), GradeViewActivity.class);
            intent.putExtra("myPortal", p);
            startActivity(intent);
        }
    }
}