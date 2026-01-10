package com.example.photoroulette;

import android.content.*;
import android.net.nsd.NsdManager;
import android.annotation.*;
import android.net.nsd.*;

public class CommonHelper {
    public static final String SERVICE_NAME = "NsdChat";
    public static final String SERVICE_TYPE = "_http._tcp";
    private Context context;
    private static NsdManager nsdManager;
    private NsdManager.RegistrationListener listener;
    private static NsdServiceInfo service;


    public CommonHelper(Context context) {

        this.context = context;
        nsdManager = (NsdManager) context.getSystemService(Context.NSD_SERVICE);

        service = new NsdServiceInfo();
        service.setServiceName(SERVICE_NAME);
        service.setServiceType(SERVICE_TYPE);


    }

    public static void registerNSDService(Context context, int port, NsdManager.RegistrationListener listener) {

        CommonHelper commonHelper = new CommonHelper(context);
        commonHelper.setupService(port);
        nsdManager.registerService(service, NsdManager.PROTOCOL_DNS_SD, listener);

    }

    public static void unregisterNSDService(Context context, NsdManager.RegistrationListener listener) {

        nsdManager.unregisterService(listener);
    }

    private void setupService(int port) {
        service = new NsdServiceInfo();
        service.setServiceName(SERVICE_NAME);
        service.setServiceType(SERVICE_TYPE);
        service.setPort(port);
    }
}