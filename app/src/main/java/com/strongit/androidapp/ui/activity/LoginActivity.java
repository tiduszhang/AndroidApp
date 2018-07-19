package com.strongit.androidapp.ui.activity;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.strongit.androidapp.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    //用户名的绑定
    public ObservableField<String> userName = new ObservableField<>("");

    //密码的绑定
    public ObservableField<String> passWord = new ObservableField<>("");



}
