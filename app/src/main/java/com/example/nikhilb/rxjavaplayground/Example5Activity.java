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

public class Example5Activity extends AppCompatActivity {
    private static final String TAG = "Example5Activity";

    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example5);

        disposable.add(getNotesObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Note, Note>() {
                    @Override
                    public Note apply(Note note) throws Exception {
                        // Making the note to all uppercase
                        note.setNote(note.getNote().toUpperCase());
                        return note;
                    }
                })
                .subscribeWith(getNotesObserver()));
    }

    /**
     * This is the observer
     *
     * @return - It returns a disposable observer
     */
    private DisposableObserver<Note> getNotesObserver() {
        return new DisposableObserver<Note>() {

            @Override
            public void onNext( Note note ) {
                Log.d(TAG, "Note: " + note.getNote());
            }

            @Override
            public void onError( Throwable e ) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "All notes are emitted!");
            }
        };
    }

    private Observable<Note> getNotesObservable() {
        final List<Note> notes = prepareNotes();

        return Observable.create(new ObservableOnSubscribe<Note>() {
            @Override
            public void subscribe( ObservableEmitter<Note> emitter ) throws Exception {
                for ( Note note : notes ) {
                    if ( !emitter.isDisposed() ) {
                        emitter.onNext(note);
                    }
                }

                if ( !emitter.isDisposed() ) {
                    emitter.onComplete();
                }
            }
        });
    }

    private List<Note> prepareNotes() {
        List<Note> notes = new ArrayList<>();
        notes.add(new Note(1, "buy tooth paste!"));
        notes.add(new Note(2, "call brother!"));
        notes.add(new Note(3, "watch narcos tonight!"));
        notes.add(new Note(4, "pay power bill!"));

        return notes;
    }

    class Note {
        int id;
        String note;

        public Note( int id, String note ) {
            this.id = id;
            this.note = note;
        }

        public int getId() {
            return id;
        }

        public String getNote() {
            return note;
        }

        public void setId( int id ) {
            this.id = id;
        }

        public void setNote( String note ) {
            this.note = note;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
