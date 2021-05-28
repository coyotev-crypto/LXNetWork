package cc.turbosnail.lrhnethttp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import cc.turbosnail.LXBind;
import cc.turbosnail.lrhlibrary.BaseObserver;
import cc.turbosnail.lrhnethttp.mvp.contract.BingContract;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private BingContract.Model mModel;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView viewById = findViewById(R.id.tvText);
        mModel = LXBind.bind(BingContract.Model.class);
        mModel.bingIndex(new BaseObserver<String>() {
            @Override
            public void onSuccess(String str) {
//                Log.i(TAG, "onSuccess: " + str);
                viewById.setText(str);
            }

            @Override
            public void onFailure(Throwable e) {
//                Log.i(TAG, "onFailure: " + e.getMessage());
                viewById.setText(e.getMessage());
            }
        });
        startActivity(new Intent(this,Main2Activity.class));
        finish();
    }
}