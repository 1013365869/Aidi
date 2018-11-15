package music.zkrt.com.aidiapplication2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import music.zkrt.com.myapplication.Book;
import music.zkrt.com.myapplication.BookController;

public class MainActivity extends AppCompatActivity {

    private Button bt_getBookList;
    private Button bt_addBook;
    private Button btn_addBook_in;
    private Button btn_addBook_out;

    private List<Book> bookList;
    private boolean connected;

    private BookController bookController;
    private ServiceConnection serviceConnection =new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bookController = BookController.Stub.asInterface(service);
            connected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            connected = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_addBook = findViewById(R.id.bt_addBook);
        bt_getBookList = findViewById(R.id.bt_getBookList);
        btn_addBook_in = findViewById(R.id.btn_addBook_in);
        btn_addBook_out = findViewById(R.id.btn_addBook_out);

        btn_addBook_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connected) {
                    Book book = new Book("这是一本新书 out");
                    try {
                        bookController.addBookOut(book);
                        Log.e("jll", "向服务器以out方式添加了一本新书");
                        Log.e("jll", "新书名：" + book.getName());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btn_addBook_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connected) {
                    Book book = new Book("这是一本新书 In");
                    try {
                        bookController.addBookIn(book);
                        Log.e("jll", "向服务器以In方式添加了一本新书");
                        Log.e("jll", "新书名：" + book.getName());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }


            }
        });

        btn_addBook_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bt_getBookList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connected){
                    try {
                        bookList = bookController.getBookList();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    log();
                }
            }
        });

        bt_addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connected) {
                    Book book = new Book("这是一本新书 InOut");
                    try {
                        bookController.addBookInOut(book);
                        Log.e("jll", "向服务器以InOut方式添加了一本新书");
                        Log.e("jll", "新书名：" + book.getName());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }


            }
        });

        bindService();

    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setPackage("music.zkrt.com.myapplication");
        intent.setAction("music.zkrt.com.myapplication.action");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void log() {
        for (Book book : bookList) {
            Log.e("jll", book.toString());
        }
    }
}
