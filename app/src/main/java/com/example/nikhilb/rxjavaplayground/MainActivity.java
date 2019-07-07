package com.example.nikhilb.rxjavaplayground;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    // this is used to end the subscription of the observer with the observable
    private Disposable disposable;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Observable<String> stringObservable = getAnimalsObservable();
//
//        Observer<String> animalsObserver = getObserver();
//
//        stringObservable
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .filter(new Predicate<String>() {
//                    @Override
//                    public boolean test( String s ) throws Exception {
//                        return s.toLowerCase().startsWith("b");
//                    }
//                })
//                .map(new Function<String, String>() {
//                    @Override
//                    public String apply( String s ) throws Exception {
//                        return null;
//                    }
//                }).subscribe(animalsObserver);

        Intent intent = new Intent(MainActivity.this, Example8Activity.class);
        startActivity(intent);

    }

    private Observer<String> getObserver() {
        return new Observer<String>() {
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
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        };
    }

    private Observable<String> getAnimalsObservable() {
        return Observable.fromArray(
                "Ant", "Ape",
                "Bat", "Bee", "Bear", "Butterfly",
                "Cat", "Crab", "Cod",
                "Dog", "Dove",
                "Fox", "Frog");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
