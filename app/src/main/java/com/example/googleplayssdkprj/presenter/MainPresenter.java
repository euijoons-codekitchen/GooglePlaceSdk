package com.example.googleplayssdkprj.presenter;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.googleplayssdkprj.GlobalApplication;
import com.example.googleplayssdkprj.dto.MainItem;
import com.example.googleplayssdkprj.dto.MainItemViewModel;
import com.example.googleplayssdkprj.view.currentposition.CurrentPositionActivity;
import com.example.googleplayssdkprj.view.mainview.MainActivity;
import com.example.googleplayssdkprj.view.placepicker.PlacePickerActivity;
import com.example.googleplayssdkprj.view.mainview.MainView;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter {

    String TAG = MainPresenter.class.getName();
    private MainView mainView;
    private List<MainItem> items;

    MainItemViewModel model;


    public MainPresenter(MainView mainView){
        items = new ArrayList<>();
        this.mainView = mainView;

    }

    public void updateSpecificItem(String address, int index){

        String[] originalStr = items.get(index).getTitle().split(" ");
        String newAddress = originalStr[0]+" "+address;
        items.get(index).setTitle(newAddress);
        mainView.updateList(items);
    }

    public void setMainItems(){
        items.add(new MainItem("Current Position"));
        items.add(new MainItem("Place Picker"));
        mainView.updateList(items);
    }

    public void showMapsOnNextActivity(int index){


        switch (index){
            case 0 :
                Log.d(TAG, "showMapsOnNextActivity: " + "Current Position");
                GlobalApplication.getGlobalApplicationContext().startActivity(new Intent(GlobalApplication.getGlobalApplicationContext(),CurrentPositionActivity.class));
                break;
            case 1:
                Log.d(TAG, "showMapsOnNextActivity: " + "Place Picker");
                GlobalApplication.getGlobalApplicationContext().startActivity(new Intent(GlobalApplication.getGlobalApplicationContext(), PlacePickerActivity.class));
                break;
            case 2:

                break;
            case 3:

                break;

        }
    }



}
