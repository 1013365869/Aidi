package study.jll.com.textaidlserviceapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookService extends Service {
    public BookService() {
    }

    private List<Book> bookList = new ArrayList<>();
    private RemoteCallbackList<onNewBookArrivedListener> copyOnWriteArrayList = new RemoteCallbackList<>();
    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);

    @Override
    public void onCreate() {
        super.onCreate();
        bookList.add(new Book("呵呵"));
        bookList.add(new Book("丹丹"));

        new Thread(new ServiceWorker()).start();
    }

    private BookAidlInterface.Stub stub = new BookAidlInterface.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return bookList;
        }

        @Override
        public void addBookInOut(Book book) throws RemoteException {
            bookList.add(book);
        }

        @Override
        public void registerListener(onNewBookArrivedListener listener) throws RemoteException {
            copyOnWriteArrayList.register(listener);

        }

        @Override
        public void unregisterListener(onNewBookArrivedListener listener) throws RemoteException {
            copyOnWriteArrayList.unregister(listener);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }


    private class ServiceWorker implements Runnable {

        @Override
        public void run() {
            while (!mIsServiceDestoryed.get()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookdId = bookList.size() + 1;
                Book newBook = new Book(bookdId + "");
                onNewBookArrived(newBook);
            }
        }
    }

    private void onNewBookArrived(Book newBook) {
        bookList.add(newBook);
        final int N = copyOnWriteArrayList.beginBroadcast();
        for (int i = 0; i < N; i++) {
            onNewBookArrivedListener listener = copyOnWriteArrayList.getBroadcastItem(i);
            try {
                if (listener != null) {
                    listener.onNewBookArrived(newBook);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
        copyOnWriteArrayList.finishBroadcast();
    }
}
