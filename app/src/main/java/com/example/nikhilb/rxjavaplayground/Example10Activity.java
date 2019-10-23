package com.example.nikhilb.rxjavaplayground;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;

public class Example10Activity extends AppCompatActivity {
    private static final String TAG = "Example10Activity";

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example10);

        Observable<String> observable = Observable.just("a", "b", "c", "d", "e");

//        observable.scan(( x, y ) -> x + y).subscribe(s -> Log.d(TAG, " Scan " + s));

        observable.scan("", ( total, y ) -> total + y)
                .skip(1)
                .subscribe(s -> Log.d(TAG, "scan : " + s));

    }
}
