package com.example.nikhilb.rxjavaplayground;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.time.LocalDate;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Example9Activity extends AppCompatActivity {

    private static final String TAG = "Example9Activity";

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example9);

        Observable<String> dates = Observable.just("2018-11-12", "2019-12-12", "2019-02-12");

        dates.map(new Function<String, LocalDate>() {
            @Override
            public LocalDate apply( String s ) throws Exception {
                return LocalDate.parse(s);
            }
        })
        .map(new Function<LocalDate, Integer>() {
            @Override
            public Integer apply( LocalDate localDate ) throws Exception {
                return localDate.lengthOfMonth();
            }
        })
        .subscribe(result -> Log.d(TAG, "Result " + result));


        dates.map(LocalDate::parse)
                .map(LocalDate::lengthOfMonth)
                .subscribe(result -> Log.d(TAG, "Result " + result));

    }
}
