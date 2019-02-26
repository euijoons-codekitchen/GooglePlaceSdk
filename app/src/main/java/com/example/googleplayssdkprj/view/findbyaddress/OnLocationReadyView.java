package com.example.googleplayssdkprj.view.findbyaddress;

import com.example.googleplayssdkprj.dto.KTLocation;
import com.example.googleplayssdkprj.dto.KTStore;

import java.util.List;

public interface OnLocationReadyView {
    public void drawMarker(KTLocation ktLocation);

    public void drawMarkers(List<KTStore> stores);

}
