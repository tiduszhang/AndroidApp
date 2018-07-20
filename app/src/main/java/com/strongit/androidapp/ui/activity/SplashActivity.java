package com.strongit.androidapp.ui.activity;

import android.os.Bundle;
import com.strongit.androidapp.BR;
import com.strongit.androidapp.R;
import com.strongit.androidapp.databinding.ActivitySplashBinding;
import com.strongit.androidapp.ui.vm.SplashViewModel;
import me.goldze.mvvmhabit.base.BaseActivity;

public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel> {

    @Override
    public int initContentView(Bundle bundle) {
        return R.layout.activity_splash;
    }

    @Override
    public int initVariableId() {
        return BR.splashViewModel;
    }

    @Override
    public SplashViewModel initViewModel() {
        //View持有ViewModel的引用 (考虑到框架适用性，这里暂时没有用Dagger2解耦)
        return new SplashViewModel(this);
    }

}