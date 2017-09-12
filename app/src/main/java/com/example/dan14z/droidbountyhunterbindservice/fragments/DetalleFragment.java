package com.example.dan14z.droidbountyhunterbindservice.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dan14z.droidbountyhunterbindservice.R;
import com.example.dan14z.droidbountyhunterbindservice.interfaces.OnFugitivoListener;

/**
 * Created by Dan14z on 11/09/2017.
 */

public class DetalleFragment extends Fragment implements OnFugitivoListener{

    private boolean mIsTablet = false;
    private TextView mNombreTextView;
    private TextView mStatusTextView;
    private TextView mPhotoTextView;
    private String mNombre;
    private String mStatus;
    private String mPhoto;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent intent = getActivity().getIntent();
        mIsTablet = intent.getBooleanExtra("mIsTablet",false);
        if(!mIsTablet){
            mNombre = intent.getStringExtra("name");
            mStatus = intent.getStringExtra("status");
            mPhoto = intent.getStringExtra("photo");
            updateData();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle, container, false);

        mNombreTextView = (TextView) view.findViewById(R.id.nombre_text_view);
        mStatusTextView = (TextView) view.findViewById(R.id.status_text_view);
        mPhotoTextView = (TextView) view.findViewById(R.id.photo_text_view);

        if(!mIsTablet){
            updateData();
         }
        return view;
    }

    private void updateData(){
        if(mNombre != null || mStatus != null){
            mNombreTextView.setText(mNombre);
            mStatusTextView.setText(mStatus.equals("0") ? "Fugitivo" : "Atrapado");
            mPhotoTextView.setText(TextUtils.isEmpty(mPhoto) ? "No hay foto" : "Hay foto");
        }
    }

    @Override
    public void OnLogItemList(String nombre, String status, String photo) {
        mNombre = nombre;
        mStatus = status;
        mPhoto = photo;
        updateData();
    }
}
