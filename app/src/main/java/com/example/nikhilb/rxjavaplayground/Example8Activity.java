package com.example.nikhilb.rxjavaplayground;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Buffer
 */
public class Example8Activity extends AppCompatActivity {
    private static final String TAG = "Example8Activity";

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example8);

        Observable<Integer> integerObservable = Observable.just(1, 2, 3, 4,
                5, 6, 7, 8, 9);

        integerObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .buffer(3)
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe( Disposable d ) {

                    }

                    @Override
                    public void onNext( List<Integer> integers ) {
                        Log.d(TAG, "onNext: ");
                        for ( int i : integers ) {
                            Log.d(TAG, " " + i);
                        }
                    }

                    @Override
                    public void onError( Throwable e ) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
