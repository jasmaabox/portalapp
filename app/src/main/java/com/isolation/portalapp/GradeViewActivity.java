package com.isolation.portalapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.isolation.portalhelper.Portal;
import com.isolation.portalhelper.SchoolClass;

import org.w3c.dom.Text;

import java.util.zip.Inflater;

public class GradeViewActivity extends AppCompatActivity {

    Portal p;
    Inflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_view);

        p = (Portal) getIntent().getSerializableExtra("myPortal");

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TableLayout table = (TableLayout)findViewById(R.id.gradeTable);


        for(SchoolClass c : p.classes){

            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

            View custom = inflater.inflate(R.layout.grade_cell, null);
            ((TextView)custom.findViewById(R.id.class_name)).setText(c.getTitle());
            ((TextView)custom.findViewById(R.id.grade)).setText(c.getTotalPercent() + "");

            tr.addView(custom);

            table.addView(tr);
        }

    }
}
