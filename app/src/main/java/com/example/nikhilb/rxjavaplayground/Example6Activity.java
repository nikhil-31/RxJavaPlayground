package com.example.nikhilb.rxjavaplayground;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class Example6Activity extends AppCompatActivity {
    private static final String TAG = "Example6Activity";

    private Disposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example6);

        getUsersObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<User, Observable<User>>() {
                    @Override
                    public Observable<User> apply( User user ) throws Exception {
                        return userObservable(user);
                    }
                })
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe( Disposable d ) {
                        Log.d(TAG, "onSubscribe");
                        disposable = d;
                    }

                    @Override
                    public void onNext( User user ) {
                        Log.d(TAG, "onNext: " + user.getName() + ", " + user.getGender() + ", " + user.getAddress().getAddress());

                    }

                    @Override
                    public void onError( Throwable e ) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }


    private Observable<User> userObservable( final User user ) {
        final String[] addresses = new String[]{
                "1600 Amphitheatre Parkway, Mountain View, CA 94043",
                "2300 Traverwood Dr. Ann Arbor, MI 48105",
                "500 W 2nd St Suite 2900 Austin, TX 78701",
                "355 Main Street Cambridge, MA 02142"
        };

        return Observable
                .create(new ObservableOnSubscribe<User>() {
                    @Override
                    public void subscribe( ObservableEmitter<User> emitter ) throws Exception {
                        Address address = new Address();
                        address.setAddress(addresses[new Random().nextInt(2)]);
                        if ( !emitter.isDisposed() ) {
                            user.setAddress(address);


                            // Generate network latency of random duration
                            int sleepTime = new Random().nextInt(1000) + 500;

                            Thread.sleep(sleepTime);
                            emitter.onNext(user);
                            emitter.onComplete();
                        }
                    }
                }).subscribeOn(Schedulers.io());
    }

    /**
     * Assume this is a network call to fetch users
     * returns Users with name and gender but missing address
     */
    private Observable<User> getUsersObservable() {
        String[] maleUsers = new String[]{"Mark", "John", "Trump", "Obama"};

        final List<User> users = new ArrayList<>();

        for ( String name : maleUsers ) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        disposable.dispose();
    }
}
