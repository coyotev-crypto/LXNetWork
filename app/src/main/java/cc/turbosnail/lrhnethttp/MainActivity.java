package cc.turbosnail.lrhnethttp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import cc.turbosnail.LXBind;
import cc.turbosnail.lrhlibrary.BaseObserver;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    MVP.Model testModel;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testModel = LXBind.bind(MVP.Model.class);

        testModel.driverStart(new BaseObserver() {
            @Override
            public void onSuccess(Object o) {

            }

            @Override
            public void onFailure(Throwable e) {

            }
        });
    }
}