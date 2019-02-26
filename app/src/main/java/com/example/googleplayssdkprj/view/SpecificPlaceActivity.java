package com.example.googleplayssdkprj.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.googleplayssdkprj.GlobalApplication;
import com.example.googleplayssdkprj.R;
import com.example.googleplayssdkprj.dto.KTStore;
import com.example.googleplayssdkprj.dto.MainItem;
import com.example.googleplayssdkprj.dto.MainItemViewModel;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpecificPlaceActivity extends AppCompatActivity {

    @BindView(R.id.tv_specific_storename)
    TextView mName;

    @BindView(R.id.img_specific_storephoto)
    ImageView mImageview;

    @BindView(R.id.tv_specific_middle_rating_result)
    TextView mRatingResult;

    @BindView(R.id.tv_specific_middle_openresult)
    TextView mOpenResult;

    @BindView(R.id.tv_specific_middle_addressresult)
    TextView mAddressResult;

    KTStore ktStore;
    private String TAG = SpecificPlaceActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_place);
        ButterKnife.bind(this);

        ktStore = MainItemViewModel.getKTStoreLiveData().getKtStore();
        initLayout();

    }

    private void initLayout() {
        mName.setText(ktStore.getName());
        mRatingResult.setText(ktStore.getRating()+"");
        Log.d(TAG, "initLayout: "+ktStore.getIconUrl());
        Log.d(TAG, "initLayout: "+ktStore.getPhotoUrl());


        if(ktStore.getPhotoUrl()!=null){
            String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="
            +ktStore.getPhotoUrl()+"&key="+ GlobalApplication.getApiKey();
            Glide.with(this).load(url).into(mImageview);
        }
        else
            Glide.with(this).load(ktStore.getIconUrl()).into(mImageview);


        if(ktStore.isOpened())
            mOpenResult.setText("OPENED");
        else
            mOpenResult.setText("CLOSED");
        //mImageview.setImageURI(ktStore.getIconUrl());
        mAddressResult.setText(ktStore.getLocation().getFormatted_address());

    }
}
