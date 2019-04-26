// BookAidlInterface.aidl
package study.jll.com.textaidlserviceapplication;

// Declare any non-default types here with import statements
import study.jll.com.textaidlserviceapplication.Book;
import study.jll.com.textaidlserviceapplication.onNewBookArrivedListener;

interface BookAidlInterface {
   List<Book> getBookList();
   void addBookInOut(inout Book book);
   void registerListener(onNewBookArrivedListener listener);
   void unregisterListener(onNewBookArrivedListener listener);
}
