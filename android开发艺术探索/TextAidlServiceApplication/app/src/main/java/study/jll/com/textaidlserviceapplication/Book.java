package study.jll.com.textaidlserviceapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {

    private String bookName;

    public Book(String bookName) {
        this.bookName = bookName;
    }

    protected Book(Parcel in) {
        bookName = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bookName);
    }

    public void readFromParcel(Parcel dest){
        bookName = dest.readString();
    }
}
