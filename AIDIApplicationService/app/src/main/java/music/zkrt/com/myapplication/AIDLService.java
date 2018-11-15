package music.zkrt.com.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jll on 2018/11/14.
 */

public class AIDLService extends Service {

    private final String TAG = "Service";

    private List<Book> bookList;

    public AIDLService(List<Book> bookList) {
        this.bookList = bookList;
    }

    public AIDLService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        bookList = new ArrayList<>();
        initData();
    }

    private void initData() {
        Book book1 = new Book("java");
        Book book2 = new Book("Android");
        Book book3 = new Book("kotlin");
        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);
    }

    private final BookController.Stub stub = new BookController.Stub() {
        @Override
        public void addBookInOut(Book book) throws RemoteException {
            if (book != null) {
                book.setName("服务器修改了新书名字 inout");
                bookList.add(book);
            } else {
                Log.i(TAG, "接受到了一空对象 inout");
            }
        }

        @Override
        public List<Book> getBookList() throws RemoteException {

            return bookList;
        }
        @Override
        public void addBookIn(Book book) throws RemoteException {
            if (book != null) {
                book.setName("服务器改了新书的名字 In");
                bookList.add(book);
            } else {
                Log.e(TAG, "接收到了一个空对象 In");
            }
        }

        @Override
        public void addBookOut(Book book) throws RemoteException {
            if (book != null) {
                Log.e(TAG, "客户端传来的书的名字：" + book.getName());
                book.setName("服务器改了新书的名字 Out");
                bookList.add(book);
            } else {
                Log.e(TAG, "接收到了一个空对象 Out");
            }
        }

    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }
}
