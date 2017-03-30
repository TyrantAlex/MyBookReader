package com.example.administrator.mybookreader.api;

/**
 * Created by Administrator on 2017/3/11 0011.
 */

public class BookApi {

    private static BookApi sInstance;

    public BookApi(){

    }

    public static BookApi getsInstance(){
        if(sInstance == null){
            sInstance = new BookApi();
        }
        return sInstance;
    }
}
