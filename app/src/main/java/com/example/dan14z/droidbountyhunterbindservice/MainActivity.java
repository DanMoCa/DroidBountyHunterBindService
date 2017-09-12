package com.example.dan14z.droidbountyhunterbindservice;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dan14z.droidbountyhunterbindservice.data.DBHelper;
import com.example.dan14z.droidbountyhunterbindservice.data.FugitivoContract;
import com.example.dan14z.droidbountyhunterbindservice.model.Fugitivo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private Button mButtonFugitivos;
    private Button mButtonConexion;
    private DBHelper dbHelper;

    int mContadorPorcentaje;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonFugitivos = (Button) findViewById(R.id.btn_fugitivos);
        mButtonConexion = (Button) findViewById(R.id.btn_conexion);
        final ProgressBar mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        final TextView mTextProgress = (TextView) findViewById(R.id.progress_text);

        mProgressBar.setVisibility(View.GONE);
        mTextProgress.setVisibility(View.GONE);
        dbHelper = new DBHelper(this);
        mButtonFugitivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ListActivity.class);
                startActivity(intent);
            }
        });

        mButtonConexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFugitivosProvider();
            }
        });
    }


    private ArrayList<Fugitivo> getFugitivosProvider(){
        ContentResolver cr = getContentResolver();

        final ArrayList<Fugitivo> fugitivos = new ArrayList<>();
        final ProgressBar mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        final TextView mTextProgress = (TextView) findViewById(R.id.progress_text);

        String selection = FugitivoContract.COLUMN_NAME_STATUS + "=?";
        String[] selectionArgs = new String[] {"0"};
        Cursor cursor = cr.query(FugitivoContract.CONTENT_URI,null,selection,selectionArgs,null,null);

        if(cursor != null && cursor.getCount() > 0){
            for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex(FugitivoContract.COLUMN_NAME_NAME));
                String status = cursor.getString(cursor.getColumnIndex(FugitivoContract.COLUMN_NAME_STATUS));
                String photo = cursor.getString(cursor.getColumnIndex(FugitivoContract.COLUMN_NAME_PHOTO));

                fugitivos.add(new Fugitivo(name,status,photo));
            }
            mButtonConexion.setVisibility(View.INVISIBLE);
            mButtonFugitivos.setVisibility(View.INVISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
            mTextProgress.setVisibility(View.VISIBLE);
            final int fugSize = fugitivos.size();
            //TODO: INSERT TO DB
            new Thread(){
                @Override
                public void run() {
                    mProgressBar.post(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(0);
                        }
                    });

                    for(int i = 0; i < fugSize; i++){
                        Fugitivo currentFugitivo = fugitivos.get(i);
                        mContadorPorcentaje = Math.round(((i+1.0f)/fugSize)*100);
                        dbHelper.insertFugitivo(currentFugitivo);
                        mProgressBar.post(new Runnable() {
                            @Override
                            public void run() {
                                retardo();
                                mTextProgress.setText(getString(R.string.progress) + " " + mContadorPorcentaje + "%");
                                mProgressBar.incrementProgressBy(Math.round((1.0f/fugSize)*100));
                            }
                        });
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"Importacion finalizada ",Toast.LENGTH_SHORT).show();
                            setResult(0);
                            mButtonConexion.setVisibility(View.VISIBLE);
                            mButtonFugitivos.setVisibility(View.VISIBLE);
                            mProgressBar.setVisibility(View.GONE);
                            mTextProgress.setVisibility(View.GONE);
                        }
                    });
                }
            }.start();
//            dbHelper.insertFugitivos(fugitivos);
        }else{
            Toast.makeText(this,"No hay fugitivos",Toast.LENGTH_SHORT).show();
        }

        return fugitivos;
    }

    public void retardo(){
        try{
            Thread.sleep(500);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
