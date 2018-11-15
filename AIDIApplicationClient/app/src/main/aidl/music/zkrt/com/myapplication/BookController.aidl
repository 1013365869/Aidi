// BookController.aidl
package music.zkrt.com.myapplication;
import music.zkrt.com.myapplication.Book;
// Declare any non-default types here with import statements

interface BookController {
   void addBookInOut(inout Book book);
   void addBookIn(in Book book);
   void addBookOut(out Book book);
   List<Book> getBookList();
}
