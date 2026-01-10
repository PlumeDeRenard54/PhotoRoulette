package com.example.photoroulette;


import android.app.Service;
import android.content.Intent;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by rd on 14-9-5.
 */
public class ListeningService extends Service {

    private final String INTENT_NEW_MSG = "INTENT_NEW_MSG";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("service", "listeningService started");
        new serviceSocketThread().start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.v("service", "listeningService binded");
        return null;
    }

    private final NsdManager.RegistrationListener registrationListener = new NsdManager.RegistrationListener() {
        @Override
        public void onRegistrationFailed(NsdServiceInfo nsdServiceInfo, int i) {
            Log.v("NSD", "register failed :" + nsdServiceInfo.toString());
        }

        @Override
        public void onUnregistrationFailed(NsdServiceInfo nsdServiceInfo, int i) {
            Log.v("NSD", "unRegister failed :" + nsdServiceInfo.toString());
        }

        @Override
        public void onServiceRegistered(NsdServiceInfo nsdServiceInfo) {
            Log.v("NSD", "Service Registered: " + nsdServiceInfo.toString());
        }

        @Override
        public void onServiceUnregistered(NsdServiceInfo nsdServiceInfo) {
            Log.v("NSD", "Service unRegistered: " + nsdServiceInfo.toString());
        }
    };

    private class serviceSocketThread extends Thread {
        ServerSocket mServerSocket = null;

        @Override
        public void run() {
            super.run();
            try {
                mServerSocket = new ServerSocket(0);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            int localPort = mServerSocket.getLocalPort();
            Log.v("socket", "server socket started at: " + mServerSocket.getLocalPort());
            CommonHelper.registerNSDService(ListeningService.this, localPort, registrationListener);
            while (!Thread.currentThread().isInterrupted()) {
                Socket socket = null;
                try {
                    socket = mServerSocket.accept();
                    new clientSocketThread(socket).start();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            CommonHelper.unregisterNSDService(ListeningService.this, registrationListener);
        }
    }

    private static class clientSocketThread extends Thread {
        final Socket mSocket;
        final BufferedReader mInputReader;
        final OutputStream mOutputStream;

        public clientSocketThread(Socket socket) throws IOException {
            Log.v("socket", "new client socket received: " + socket.toString());
            this.mSocket = socket;
            this.mInputReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
            this.mOutputStream = mSocket.getOutputStream();
        }

        @Override
        public void run() {
            super.run();
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    String data = mInputReader.readLine();
                    if (data == null) throw new IOException("Connection closed");
                    Log.v("input", "data received: " + data);
                } catch (IOException e) {
                    break;
                }
            }
            Log.v("socket", "client socket closed: " + mSocket.toString());
        }
    }
}