package com.example.dan14z.droidbountyhunterbindservice.fragments;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.dan14z.droidbountyhunterbindservice.R;
import com.example.dan14z.droidbountyhunterbindservice.data.DBHelper;
import com.example.dan14z.droidbountyhunterbindservice.data.FugitivoContract;
import com.example.dan14z.droidbountyhunterbindservice.interfaces.OnFugitivoListener;
import com.example.dan14z.droidbountyhunterbindservice.model.Fugitivo;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Dan14z on 10/09/2017.
 */

public class FugitivosFragment extends Fragment {

    private static final String LOG_TAG = FugitivosFragment.class.getSimpleName();

    private boolean mIsTablet = false;
    private OnFugitivoListener mListener;
    private DBHelper dbHelper;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mIsTablet = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_detalle) != null;
        if(mIsTablet){
            mListener = (OnFugitivoListener) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_detalle);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dbHelper = new DBHelper(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fugitivo,container,false);

        View emptyView = view.findViewById(R.id.empty_view);

        final ArrayList<Fugitivo> fugitivos = dbHelper.getFugitivos();
        Log.d("fugitivos",fugitivos.toString());
        ListView listView = (ListView) view.findViewById(R.id.list);
        listView.setEmptyView(emptyView);
        ArrayList<String> rows = new ArrayList<>();

        for(int i = 0; i < fugitivos.size() ; i++){
            String name = fugitivos.get(i).getNombre() + "";
            rows.add(name);
        }
        Log.d("Rows",rows.toString());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,rows);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fugitivo currFugitivo = fugitivos.get(i);
                if(mIsTablet){
                    mListener.OnLogItemList(currFugitivo.getNombre(),currFugitivo.getStatus(),currFugitivo.getPhoto());
                }else{
                    Intent intent = new Intent(getContext(),com.example.dan14z.droidbountyhunterbindservice.DetalleFugitivoActivity.class);
                    intent.putExtra("isTablet",mIsTablet);
                    intent.putExtra("name",currFugitivo.getNombre());
                    intent.putExtra("status",currFugitivo.getStatus());
                    intent.putExtra("photo",currFugitivo.getPhoto());
                    startActivity(intent);
                }
            }
        });

        return view;
    }

}
