package com.example.photoroulette;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerObsolete {

    public static final String TAG = "NsdPhotoRoulette";
    public static final String SERVICE_TYPE = "_photoroulette._tcp";

    private static ServerObsolete instance;

    private final NsdManager nsdManager;
    private ServerSocket serverSocket;
    private int localPort;
    private String serviceName;

    private NsdManager.RegistrationListener registrationListener;
    private NsdManager.DiscoveryListener discoveryListener;
    private NsdManager.ResolveListener resolveListener;

    private ServerObsolete(Context context) {
        this.nsdManager = (NsdManager) context.getSystemService(Context.NSD_SERVICE);
        try {
            // Initialize a server socket on a free port.
            this.serverSocket = new ServerSocket(0);
            this.localPort = serverSocket.getLocalPort();
        } catch (IOException e) {
            Log.e(TAG, "Error creating ServerSocket", e);
            // In a real app, you might want to handle this more gracefully.
        }

        // Start a thread to listen for incoming connections.
        new Thread(new ServerThread()).start();

        initializeListeners();
    }

    public static synchronized ServerObsolete getInstance(Context context) {
        if (instance == null) {
            instance = new ServerObsolete(context.getApplicationContext());
        }
        return instance;
    }

    public void registerService() {
        NsdServiceInfo serviceInfo = new NsdServiceInfo();
        serviceInfo.setServiceName("PhotoRoulette");
        serviceInfo.setServiceType(SERVICE_TYPE);
        serviceInfo.setPort(localPort);

        nsdManager.registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, registrationListener);
    }

    public void discoverServices() {
        nsdManager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener);
    }

    public void tearDown() {
        nsdManager.unregisterService(registrationListener);
        nsdManager.stopServiceDiscovery(discoveryListener);
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            Log.e(TAG, "Error closing server socket", e);
        }
    }


    private void initializeListeners() {
        registrationListener = new NsdManager.RegistrationListener() {
            @Override
            public void onServiceRegistered(NsdServiceInfo NsdServiceInfo) {
                serviceName = NsdServiceInfo.getServiceName();
                Log.d(TAG, "Service registered: " + serviceName + " on port " + NsdServiceInfo.getPort());
            }

            @Override
            public void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                Log.e(TAG, "Service registration failed. Error code: " + errorCode);
            }

            @Override
            public void onServiceUnregistered(NsdServiceInfo arg0) {
                Log.i(TAG, "Service unregistered: " + arg0.getServiceName());
            }

            @Override
            public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                Log.e(TAG, "Service unregistration failed. Error code: " + errorCode);
            }
        };

        discoveryListener = new NsdManager.DiscoveryListener() {
            @Override
            public void onDiscoveryStarted(String regType) {
                Log.d(TAG, "Service discovery started");
            }

            @Override
            public void onServiceFound(NsdServiceInfo service) {
                Log.d(TAG, "Service found: " + service);
                if (!service.getServiceType().equals(SERVICE_TYPE)) {
                    Log.d(TAG, "Unknown Service Type: " + service.getServiceType());
                } else if (service.getServiceName().equals(serviceName)) {
                    Log.d(TAG, "Same machine: " + serviceName);
                } else if (service.getServiceName().contains("PhotoRoulette")) {
                    nsdManager.resolveService(service, resolveListener);
                }
            }

            @Override
            public void onServiceLost(NsdServiceInfo service) {
                Log.e(TAG, "Service lost: " + service);
            }

            @Override
            public void onDiscoveryStopped(String serviceType) {
                Log.i(TAG, "Discovery stopped: " + serviceType);
            }

            @Override
            public void onStartDiscoveryFailed(String serviceType, int errorCode) {
                Log.e(TAG, "Discovery failed to start. Error code: " + errorCode);
                nsdManager.stopServiceDiscovery(this);
            }

            @Override
            public void onStopDiscoveryFailed(String serviceType, int errorCode) {
                Log.e(TAG, "Discovery failed to stop. Error code: " + errorCode);
            }
        };

        resolveListener = new NsdManager.ResolveListener() {
            @Override
            public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
                Log.e(TAG, "Resolve failed. Error code: " + errorCode);
            }

            @Override
            public void onServiceResolved(NsdServiceInfo serviceInfo) {
                Log.i(TAG, "Resolve Succeeded. " + serviceInfo);
                // Now you have the IP and port of the other device.
                String host = serviceInfo.getHost().getHostAddress();
                int port = serviceInfo.getPort();
                Log.d(TAG, "Connecting to host: " + host + ", port: " + port);
                // TODO: Implement client connection logic (e.g., open a new socket to this host and port)
            }
        };
    }

    private class ServerThread implements Runnable {
        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Log.d(TAG, "ServerSocket waiting for connection.");
                    Socket clientSocket = serverSocket.accept(); // This is a blocking call
                    Log.d(TAG, "Accepted a connection from " + clientSocket.getInetAddress());
                    // TODO: Handle the client connection in a new thread
                }
            } catch (IOException e) {
                if (serverSocket.isClosed()) {
                    Log.i(TAG, "ServerSocket is closed, stopping listener thread.");
                } else {
                    Log.e(TAG, "Error in server thread", e);
                }
            }
        }
    }
}