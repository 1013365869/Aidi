package music.zkrt.com.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jll on 2018/11/14.
 */

public class Book implements Parcelable {
    private String name;

    public Book(String name) {
        this.name = name;
    }

    public Book() {
    }
    protected Book(Parcel in) {
        name = in.readString();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    public void readFromParcel(Parcel dest) {
        name = dest.readString();
    }

    @Override
    public String toString() {
        return "book name：" + name;
    }
}
