package com.strongit.androidapp.ui.activity;

import android.os.Bundle;
import com.strongit.androidapp.R;
import com.strongit.androidapp.BR;
import com.strongit.androidapp.databinding.ActivityMainBinding;
import com.strongit.androidapp.ui.vm.MainViewModel;
import me.goldze.mvvmhabit.base.BaseActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    @Override
    public int initContentView(Bundle bundle) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.mainViewModel;
    }

    @Override
    public MainViewModel initViewModel() {
        //View持有ViewModel的引用 (考虑到框架适用性，这里暂时没有用Dagger2解耦)
        return new MainViewModel(this);
    }

}
