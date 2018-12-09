package com.example.android.sudoku;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.android.sudoku.MainActivity;
/**
 * Created by Asus on 30-04-2018.
 */

public class TbActivity extends AppCompatActivity {

    public int bcr=0;
    public int slice_dice=0;
    public int ghost=0;
    public int brute=0;
    public String[][] Arr=new String[9][9];




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tb);

        Bundle b=getIntent().getExtras();
        String[][] Ar=null;
        Object[] oa=(Object[])getIntent().getExtras().getSerializable("Arr");
        if(oa!=null){
            Ar=new String[oa.length][];
            for(int i=0;i<oa.length;i++){
                Ar[i]=(String[])oa[i];
            }
        }

        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                Arr[i][j]=Ar[i][j];
            }
        }


       final ToggleButton tb=(ToggleButton)findViewById(R.id.bcr);

        tb.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(tb.isChecked()){
                   bcr=1;
                }
                else{
                    bcr=0;
                }

            }

        });

        final ToggleButton tb1=(ToggleButton)findViewById(R.id.slice_dice);
        tb1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(tb1.isChecked()){
                    slice_dice=1;
                }
                else{
                    slice_dice=0;
                }

            }

        });

        final ToggleButton tb2=(ToggleButton)findViewById(R.id.ghost);
        tb2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(tb.isChecked()){
                    ghost=1;
                }
                else{
                    ghost=0;
                }

            }

        });

        final ToggleButton tb3=(ToggleButton)findViewById(R.id.brute);
        tb3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(tb.isChecked()){
                    brute=1;
                }
                else{
                    brute=0;
                }

            }

        });
        final Button bt2=(Button)findViewById(R.id.nextbt);
        bt2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent it2=new Intent(TbActivity.this,solver.class);
                Bundle b=new Bundle();
                b.putSerializable("arr",Arr);
                it2.putExtras(b);
                it2.putExtra("bcr",bcr);
                it2.putExtra("slice_dice",slice_dice);
                it2.putExtra("ghost",ghost);
                it2.putExtra("brute",brute);
                startActivity(it2);
            }

        });
}}
