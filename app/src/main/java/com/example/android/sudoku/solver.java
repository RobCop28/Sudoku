package com.example.android.sudoku;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.sudoku.MainActivity;
import com.example.android.sudoku.TbActivity;
/**
 * Created by Asus on 29-04-2018.
 */

public class solver extends AppCompatActivity {
    public int rowNum=9;
    public int colNum=9;
    MainActivity obj=new MainActivity();
    TbActivity obj2=new TbActivity();
    static int [][][] bord=new int[10][9][9];
    static int flag;
    static int diff;
    static int x,y,z,X,Y;
    static int count;
    static int biginer,dice,boo,boom;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        Bundle b=getIntent().getExtras();
        String[][] Arr=null;
        Object[] oa=(Object[])getIntent().getExtras().getSerializable("arr");
        if(oa!=null){
            Arr=new String[oa.length][];
            for(int i=0;i<oa.length;i++){
                Arr[i]=(String[])oa[i];
            }
        }
        Intent intent=getIntent();
        biginer=intent.getIntExtra("bcr",0);
        dice=intent.getIntExtra("slice_dice",0);
        boo=intent.getIntExtra("ghost",0);
        boom=intent.getIntExtra("brute",0);

        for(y=0;y<9;y++)
        {
            for(x=0;x<9;x++)
            {
                for(z=1;z<10;++z)
                {
                    bord[z][x][y]=1;
                }
            }
        }
        for(y=0;y<9;y++)
        {
            for(x=0;x<9;x++)
            {
                bord[0][x][y]=Integer.parseInt(Arr[x][y]);

            }
        }
        do
        {
            do // <--- go through the bord many times (till we get new things)
            {
                diff++;
                flag=0;       // <--- flag that if we found anything new? (0 = no)
		/*solving process*/
                clear();
                if(biginer==1)bcr();
                solve();
                clear();
                if(dice==1)slice();
                clear();
                if(boo==1)ghost();
                solve();
            }while(flag==1);
            for(y=0;y<9;y++)
            {
                for(x=0;x<9;x++)
                {
                    if(bord[0][x][y]==0)
                    {
                        flag=1;
                    }
                }
            }
            if(flag==1)
            {
                flag=0;
                if(boom==1)force();
            }
        }while(flag==1);



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
        final TextView[][] edit_Texts = new TextView[rowNum][colNum];


        //define how many rows and columns to be used in the layout
        gridLayout.setRowCount(rowNum);
        gridLayout.setColumnCount(colNum);
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                Log.v("Array",Arr[i][j]);
            }
        }

        for (int i = 0; i < rowNum; i++) {

            for (int j = 0; j < colNum; j++) {
                edit_Texts[i][j]=new TextView(this);
                edit_Texts[i][j].setText(String.valueOf(bord[0][i][j]));
                edit_Texts[i][j].setTextSize(20);
                edit_Texts[i][j].setPadding(8,5,0,0);
                setPos(edit_Texts[i][j], i, j);

                gridLayout.addView(edit_Texts[i][j]);
                //Log.v("Values",obj.ar[i][j]);

            }

        }



        layoutpr=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);

        gridLayout.setLayoutParams(layoutpr);
        la.addView(gridLayout);


        setContentView(la);


    }






    //putting the edit text according to row and column index
    private void setPos(TextView editText, int row, int column) {
        GridLayout.LayoutParams param =new GridLayout.LayoutParams();
        param.width = 100;
        param.height = 100;
        param.setGravity(Gravity.CENTER);
        param.rowSpec = GridLayout.spec(row);
        param.columnSpec = GridLayout.spec(column);
        editText.setLayoutParams(param);
    }

    public static int clear()
    {
        int X,Y,z;
        for(Y=0;Y<9;Y++)   // <--- one time travel the bord
        {
            for(X=0;X<9;X++)
            {
                if(bord[0][X][Y]!=0)// <--- if the cell is filled
                {
                    for(z=1;z<10;++z)
                    {
                        if(z!=(bord[0][X][Y]))
                        {
                            bord[z][X][Y]=0;
                        }
                    }
                }
            }
        }
        return 0;
    }

    public static int bcr()
    {
        int [] pres=new	int[10];
        for(y=0;y<9;y++)   // <--- one time travel the bord
        {
            for(x=0;x<9;x++)
            {
                if(bord[0][x][y]==0)      // <--- if the cell (display)
                {         //      is empty (to be filled)
                    for(X=0;X<9;X++)
                    {
                        if(bord[0][X][y]!=0 && bord[bord[0][X][y]][x][y]==1)
                        {
                            z=bord[0][X][y];
                            bord[z][x][y]=0;
                            flag=1;
                        }
                    }
                    for(Y=0;Y<9;Y++)
                    {
                        if(bord[0][x][Y]!=0 && bord[bord[0][x][Y]][x][y]==1)
                        {
                            z=bord[0][x][Y];
                            bord[z][x][y]=0;
                            flag=1;
                        }
                    }
                }
            }
        }
        for(x=0;x<9;x+=3)
        {
            for(y=0;y<9;y+=3)
            {
                for(X=0;X<10;X++)
                {
                    pres[X]=0;
                }
                for(X=x;X<x+3;X++)
                {
                    for(Y=y;Y<y+3;Y+=2)
                    {
                        if(bord[0][X][Y]!=0)
                        {
                            pres[bord[0][X][Y]]=1;
                        }
                    }
                }
                for(z=1;z<10;z++)
                {
                    if(pres[z]==1)
                    {
                        for(X=x;X<x+3;X++)
                        {
                            for(Y=y;Y<y+3;Y++)
                            {
                                if(bord[z][X][Y]==1 && bord[0][X][Y]==0)
                                {
                                    bord[z][X][Y]=0;
                                    flag=1;
                                }
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    public static int solve()
    {
        for(y=0;y<9;y++)   // <--- fill with the knowledge of what we cant fill
        {
            for(x=0;x<9;x++)
            {
                if(bord[0][x][y]==0)
                {
                    for(Y=0,z=1;z<10;++z)
                    {
                        if(bord[z][x][y]==1)
                        {
                            Y++; // <--- from here on 'Y' is the no of possible enteries
                        }
                    }
                    if(Y==1)   // <--- if only one possible entery (found one!!!)
                    {
                        for(z=1;z<10;++z)  // <--- now what was the entry?
                        {
                            if(bord[z][x][y]==1)   // <--- this one!!!
                            {
                                bord[0][x][y]=z;
                                flag=1; // <--- set the flag because we found some thing new
                            }               // <--- this means its worth checking again
                        }
                    }
                }
            }
        }
        return 0;
    }

    public static int slice()
    {
        for(y=0;y<9;y++)   // <--- one time travel the bord
        {
            for(z=1;z<10;z++)
            {
                count=0;
                for(x=0;x<9;x++)
                {
                    if(bord[z][x][y]==1)
                    {
                        count++;
                    }
                }
                if(count==1)
                {
                    for(x=0;x<9;x++)
                    {
                        if(bord[z][x][y]==1 && bord[0][x][y]==0)
                        {
                            bord[0][x][y]=z;
                            flag=1;
                            break;
                        }
                    }
                }
            }
        }
        for(x=0;x<9;x++)   // <--- one time travel the bord
        {
            for(z=1;z<10;z++)
            {
                count=0;
                for(y=0;y<9;y++)
                {
                    if(bord[z][x][y]==1)
                    {
                        count++;
                    }
                }
                if(count==1)
                {
                    for(y=0;y<9;y++)
                    {
                        if(bord[z][x][y]==1 && bord[0][x][y]==0)
                        {
                            bord[0][x][y]=z;
                            flag=1;
                            break;
                        }
                    }
                }
            }
        }
        for(x=0;x<9;x+=3)
        {
            for(y=0;y<9;y+=3)
            {
                for(z=1;z<10;z++)
                {
                    count=0;
                    for(X=x;X<x+3;X++)
                    {
                        for(Y=y;Y<y+3;Y++)
                        {
                            if(bord[z][X][Y]==1)
                            {
                                count++;
                            }
                        }
                    }
                    if(count==1)
                    {
                        for(X=x;X<x+3;X++)
                        {
                            for(Y=y;Y<y+3;Y++)
                            {
                                if(bord[z][X][Y]==1 && bord[0][X][Y]==0)
                                {
                                    bord[0][X][Y]=z;
                                    flag=1;
                                }
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    public static int ghost()
    {
        int hori,vert;
        for(x=0;x<9;x+=3)
        {
            for(y=0;y<9;y+=3)
            {
                for(z=1;z<10;z++)
                {
                    hori=0;
                    vert=0;
                    for(X=x;X<x+3;X++)
                    {
                        if(bord[z][X][y]==1 || bord[z][X][y+1]==1 || bord[z][X][y+2]==1)
                        {
                            vert++;
                        }
                    }
                    for(Y=y;Y<y+3;Y++)
                    {
                        if(bord[z][x][Y]==1 || bord[z][x+1][Y]==1 || bord[z][x+2][Y]==1)
                        {
                            hori++;
                        }
                    }
                    if(hori==1)
                    {
                        for(Y=y;Y<y+3;Y++)
                        {
                            if(bord[z][x][Y]==1 || bord[z][x+1][Y]==1 || bord[z][x+2][Y]==1)
                            {
                                for(X=1;X<9;X++)
                                {
                                    if(X!=x && X!=x+1 && X!=x+2 && bord[z][X][Y]==1)
                                    {
                                        bord[z][X][Y]=0;
                                        flag=1;
                                    }
                                }
                            }
                        }
                    }
                    if(vert==1)
                    {
                        for(X=x;X<x+3;X++)
                        {
                            if(bord[z][X][y]==1 || bord[z][X][y+1]==1 || bord[z][X][y+2]==1)
                            {
                                for(Y=0;Y<9;Y++)
                                {
                                    if(Y!=y && Y!=y+1 && Y!=y+2 && bord[z][X][Y]==1)
                                    {
                                        bord[z][X][Y]=0;
                                        flag=1;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    public static int force()
    {
        int c,mflag=0,tot;
        int [][][][] copy=new int [9][10][9][9];
        int [][][] backup=new int[10][9][9];
        for(z=0;z<10;z++)
        {
            for(y=0;y<9;y++)
            {
                for(x=0;x<9;x++)
                {
                    backup[z][x][y]=bord[z][x][y];
                    for(c=0;c<9;c++)
                    {
                        copy[c][z][x][y]=backup[z][x][y];
                    }
                }
            }
        }
        for(y=0;y<9;y++)
        {
            for(x=0;x<9;x++)
            {
                if(backup[0][x][y]==0)
                {
                    tot=0;
                    for(z=1;z<10;z++)
                    {
                        if(backup[z][x][y]==1)
                        {
                            copy[tot][0][x][y]=z;
                            tot++;
                        }
                    }
                    for(c=0;c<tot;++c)
                    {
                        for(z=0;z<10;z++)
                        {
                            for(Y=0;Y<9;Y++)
                            {
                                for(X=0;X<9;X++)
                                {
                                    bord[z][(X*4)+2][(Y*2)+1]=copy[c][z][X][Y];
                                }
                            }
                        }
                        for(flag=1;flag==1;) // <--- go through the bord many times (till we get new things)
                        {
                            flag=0;       // <--- flag that if we found anything new? (0 = no)
                            clear();
                            if(biginer==1)bcr();
                            solve();
                            clear();
                            if(dice==1)slice();
                            clear();
                            if(boo==1)ghost();
                            solve();
                            clear();
                        }
                        for(z=0;z<10;z++)
                        {
                            for(Y=0;Y<9;Y++)
                            {
                                for(X=0;X<9;X++)
                                {
                                    copy[c][z][X][Y]=bord[z][(X*4)+2][(Y*2)+1];
                                }
                            }
                        }
                    }
                    for(Y=0;Y<9;Y++)
                    {
                        for(X=0;X<9;X++)
                        {
                            for(z=1;z<10;z++)
                            {
                                count=0;
                                for(c=0;c<tot;c++)
                                {
                                    if(copy[c][z][X][Y]!=0)count=1;
                                }
                                if(count==0 && backup[z][X][Y]==1)
                                {
                                    backup[z][X][Y]=0;
                                    mflag=1;
                                }
                            }
                        }
                    }
                }
            }
        }
        for(z=0;z<10;z++)
        {
            for(y=0;y<9;y++)
            {
                for(x=0;x<9;x++)
                {
                    bord[z][(x*4)+2][(y*2)+1]=backup[z][x][y];
                }
            }
        }
        flag=mflag;
        return 0;
    }
}









