package study.jll.com.textaidlclientapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import study.jll.com.textaidlserviceapplication.Book;
import study.jll.com.textaidlserviceapplication.BookAidlInterface;
import study.jll.com.textaidlserviceapplication.onNewBookArrivedListener;

public class MainActivity extends AppCompatActivity {

    private BookAidlInterface bookAidlInterface;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.i("jll", "receive new Book" + msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindData();
//        String fhTs = "1553138384576";
//        String fhAppId = "zkrt2019";
//        String fhKey = "zkrt2019";
//        try {
//            byte[] SignKey1 = HMACSHA1.getHMACSHA1(fhTs.getBytes("UTF-8"), fhKey.getBytes("UTF-8"));
//            Log.i("jll", " Key1 ==  " + DigitalTrans.byte2hex(SignKey1));
//            byte[] MasterSignKey = HMACSHA1.getHMACSHA1(fhAppId.getBytes("UTF-8"), SignKey1);
//            Log.i("jll", " Key2 ==MasterSignKey  " + DigitalTrans.byte2hex(MasterSignKey));
//            byte[] hash = HMACSHA1.getHMACSHA1("".getBytes("UTF-8"), MasterSignKey);
//            String strBase64 = Base64.encodeToString(hash, Base64.DEFAULT);
//            Log.i("jll", " Key64 strBase64 ==  " + strBase64);
//            boolean  a =true;
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

    }


    onNewBookArrivedListener onNewBookArrivedListener = new onNewBookArrivedListener.Stub() {

        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            Log.i("jll", " newBook" + newBook.toString());
        }
    };

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bookAidlInterface = BookAidlInterface.Stub.asInterface(service);
            try {
                bookAidlInterface.addBookInOut(new Book("jll"));
                bookAidlInterface.getBookList();
                bookAidlInterface.registerListener(onNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            try {
                Log.i("jll", " bookAidlInterface " + bookAidlInterface.getBookList().toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    private void bindData() {
        Intent it = new Intent();
        it.setPackage("study.jll.com.textaidlserviceapplication");
        it.setAction("study.jll.com.textaidlserviceapplication.action");
        bindService(it, serviceConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onDestroy() {
        if (bookAidlInterface != null && bookAidlInterface.asBinder().isBinderAlive()) {
            try {
                bookAidlInterface.unregisterListener(onNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
                Log.i("jll", " RemoteException ");
            }

            unbindService(serviceConnection);
        }
        super.onDestroy();
    }
}
