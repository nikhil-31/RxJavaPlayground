package com.example.nikhilb.rxjavaplayground;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class Example2Activity extends AppCompatActivity {
    private static final String TAG = "Example2Activity";

    Integer[] integers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example2);

        compositeDisposable.add(Observable.fromArray(integers)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Integer>() {
                    @Override
                    public void onNext( Integer integer ) {
                        Log.d(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError( Throwable e ) {
                        Log.d(TAG, "onError: ", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: All items are removed");
                    }
                }));


        compositeDisposable.add(Observable.range(1, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test( Integer integer ) throws Exception {
                        return integer % 2 == 0;
                    }
                })
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return integer + " is even number";
                    }
                })
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext( String s ) {

                    }

                    @Override
                    public void onError( Throwable e ) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
