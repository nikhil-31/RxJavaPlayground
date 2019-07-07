package com.example.nikhilb.rxjavaplayground;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class ExampleActivity extends AppCompatActivity {
    private static final String TAG = "ExampleActivity";

    private Disposable disposable;

    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        // Just operator is used to emit some data
        Observable<String> observable = Observable.just("Ant", "Bee", "Cat");

        // this is the observer
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe( Disposable d ) {
                Log.d(TAG, "onSubscribe: ");
                disposable = d;
            }

            @Override
            public void onNext( String s ) {
                Log.d(TAG, "onNext: " + s);
            }

            @Override
            public void onError( Throwable e ) {
                Log.d(TAG, "onError: ", e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        };

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);




        // Disposable is used to dispose a subscription when an observer no longer wants to listen
        // to an observable

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ( !disposable.isDisposed() ){
            disposable.dispose();
        }
    }
}
