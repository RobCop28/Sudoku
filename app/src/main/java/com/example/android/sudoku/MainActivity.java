package com.example.android.sudoku;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {


    public int rowNum=9;
    public int colNum=9;

    public  static String[][] ar=new String[9][9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        //GridLayout gridlayout=(GridLayout) findViewById(R.id.gridLayout);

        RelativeLayout la=new RelativeLayout(this);
        RelativeLayout.LayoutParams layoutpr=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        la.setLayoutParams(layoutpr);

        ImageView img=new ImageView(this);

        img.setImageResource(R.drawable.sudoku);
        layoutpr=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutpr.width=900;
        layoutpr.height=900;
        img.setLayoutParams(layoutpr);

        la.addView(img);
        Intent passedIntent = getIntent();
        Bundle extras = passedIntent.getExtras();
        GridLayout gridLayout=new GridLayout(this);
        final EditText[][] editTexts = new EditText[rowNum][colNum];


        //define how many rows and columns to be used in the layout
        gridLayout.setRowCount(rowNum);
        gridLayout.setColumnCount(colNum);

        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                int k=0;
                editTexts[i][j] = new EditText(this);

                setPos(editTexts[i][j], i, j);
                //if(editTexts[i][j].getText().toString().length()==0){

                   // ar[i][j]="0";}
                //else{
                   // ar[i][j]=editTexts[i][j].getText().toString();
                //}
                gridLayout.addView(editTexts[i][j]);


            }



        }


        Button bt=new Button(this);
        layoutpr=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutpr.addRule(RelativeLayout.ALIGN_PARENT_LEFT,la.getId());
        gridLayout.setLayoutParams(layoutpr);
        la.addView(gridLayout);

        layoutpr=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutpr.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,la.getId());
        bt.setLayoutParams(layoutpr);
        final char[] arr={'s','O','l','v','e'};
        bt.setText(arr,0,5);
        la.addView(bt);



        setContentView(la);



        bt.setOnClickListener( new View.OnClickListener(){
            public void onClick(View view){
              for(int i=0;i<9;i++) {
                  for (int j = 0; j < 9; j++) {

                      if(editTexts[i][j].getText().toString().length()==0){

                       ar[i][j]="0";}
                      else{
                       ar[i][j]=editTexts[i][j].getText().toString();
                      }
                  }
              }

                        Intent it=new Intent(MainActivity.this,TbActivity.class);
                        Bundle bd=new Bundle();
                        bd.putSerializable("Arr",ar);
                        it.putExtras(bd);

                        startActivity(it);
            }


        });
    }

    //putting the edit text according to row and column index
   private void setPos(EditText editText, int row, int column) {
       GridLayout.LayoutParams param =new GridLayout.LayoutParams();
       param.width = 100;
       param.height = 100;
       param.setGravity(Gravity.CENTER);
       param.rowSpec = GridLayout.spec(row);
       param.columnSpec = GridLayout.spec(column);
       editText.setLayoutParams(param);
    }





}

