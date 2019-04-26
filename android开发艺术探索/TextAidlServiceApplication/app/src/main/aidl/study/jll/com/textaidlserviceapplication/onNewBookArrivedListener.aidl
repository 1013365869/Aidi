// onNewBookArrivedListener.aidl
package study.jll.com.textaidlserviceapplication;

// Declare any non-default types here with import statements
import study.jll.com.textaidlserviceapplication.Book;

interface onNewBookArrivedListener {
    void onNewBookArrived(in Book newBook);
}
