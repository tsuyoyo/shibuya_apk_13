/**
 * Copyright (c) 2017 Tsuyoyo. All Rights Reserved.
 */
package tsuyoyo.prev.progress;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {

    @Inject
    MyViewModel myViewModel;

    private CompositeSubscription subscriptions = new CompositeSubscription();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((MyApplication) getApplication()).getMyComponent().inject(this);
        bindViewModel();
    }

    private void bindViewModel() {
        Button button = (Button) findViewById(R.id.start_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myViewModel.inputs().kickApi();
            }
        });

        subscriptions.add(myViewModel.outputs().isLoading()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean isLoading) {
                        if (isLoading && progressDialog == null) {
                            progressDialog = new ProgressDialog(MainActivity.this);
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            progressDialog.setTitle("loading...");
                            progressDialog.setMessage("loading...");
                            progressDialog.show();
                        } else if (!isLoading && progressDialog != null) {
                            progressDialog.dismiss();
                            progressDialog = null;
                        }
                    }
                }));

        subscriptions.add(myViewModel.outputs().message()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    @Override
    public void finish() {
        super.finish();
        ((MyApplication) getApplication()).releaseMyComponent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        subscriptions.unsubscribe();
    }

}
