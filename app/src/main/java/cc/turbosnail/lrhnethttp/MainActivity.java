package cc.turbosnail.lrhnethttp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import cc.turbosnail.LXBind;
import cc.turbosnail.lrhlibrary.BaseObserver;
import cc.turbosnail.lrhnethttp.mvp.contract.BingContract;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}