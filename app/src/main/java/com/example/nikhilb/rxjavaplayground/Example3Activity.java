package com.example.nikhilb.rxjavaplayground;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Example3Activity extends AppCompatActivity {
    private static final String TAG = "Example3Activity";

    @BindView(R.id.edit)
    EditText editText;

    @BindView(R.id.text)
    TextView textView;

    private Unbinder unbinder;

    private CompositeDisposable disposable = new CompositeDisposable();

    private Observable<String> observable;

    private Flowable<String> flowable;

    private Maybe<String> maybe;

    private Completable completable;

    /**
     * Single always emits only one value or throws an error.
     */
    private Single<String> single;


    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example3);

        unbinder = ButterKnife.bind(this);

        Single<Note> single = getSingleNote();

        SingleObserver<Note> observer = getSingleObserver();

        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);


        Completable completable = getCompletable();

        CompletableObserver completableObserver = getCompletableObserver();

        completable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(completableObserver);

    }

    private SingleObserver<Note> getSingleObserver() {
        return new SingleObserver<Note>() {

            @Override
            public void onSubscribe( Disposable d ) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onSuccess( Note note ) {
                Log.d(TAG, "onSuccess: " + note.toString());
            }

            @Override
            public void onError( Throwable e ) {

            }
        };
    }

    private Single<Note> getSingleNote() {
        return Single.create(new SingleOnSubscribe<Note>() {
            @Override
            public void subscribe( SingleEmitter<Note> emitter ) throws Exception {
                Note note = new Note(1, "Milk");
                emitter.onSuccess(note);
            }
        });
    }

    private Completable getCompletable() {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe( CompletableEmitter emitter ) throws Exception {
                if ( !emitter.isDisposed() ) {
                    emitter.onComplete();
                }
            }
        });
    }

    private CompletableObserver getCompletableObserver() {
        return new CompletableObserver() {
            @Override
            public void onSubscribe( Disposable d ) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: Task completed");
            }

            @Override
            public void onError( Throwable e ) {
                Log.d(TAG, "onError: ", e);
            }
        };
    }

}
