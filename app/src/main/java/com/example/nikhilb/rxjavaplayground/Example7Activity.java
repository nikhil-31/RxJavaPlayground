package com.example.nikhilb.rxjavaplayground;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class Example7Activity extends AppCompatActivity {
    private static final String TAG = "ExampleActivity7";

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example7);

        Observable<User> observable = getUsersObservable();

        compositeDisposable.add(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<User, User>() {
                    @Override
                    public User apply( User user ) {
                        user.setEmail(String.format("%s.gmail.com", user.getName()));
                        user.setName(user.getName().toUpperCase());
                        return user;
                    }
                })
                .subscribeWith(new DisposableObserver<User>() {
                    @Override
                    public void onNext( User user ) {
                        Log.d(TAG, "onNext: " + user.toString());
                    }

                    @Override
                    public void onError( Throwable e ) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                }));
    }


    private Observable<User> getUsersObservable() {
        String[] names = new String[]{"mark", "john", "trump", "obama"};

        final List<User> users = new ArrayList<>();
        for ( String name : names ) {
            User user = new User();
            user.setName(name);
            user.setGender("male");

            users.add(user);
        }
        return Observable.create(new ObservableOnSubscribe<User>() {
            @Override
            public void subscribe( ObservableEmitter<User> emitter ) throws Exception {
                for ( User user : users ) {
                    if ( !emitter.isDisposed() ) {
                        emitter.onNext(user);
                    }
                }

                if ( !emitter.isDisposed() ) {
                    emitter.onComplete();
                }
            }
        }).subscribeOn(Schedulers.io());
    }
}
