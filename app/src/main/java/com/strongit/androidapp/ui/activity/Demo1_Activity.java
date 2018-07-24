package com.strongit.androidapp.ui.activity;


import android.databinding.Observable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import com.lcodecore.tkrefreshlayout.footer.BallPulseView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.strongit.androidapp.BR;
import com.strongit.androidapp.R;
import com.strongit.androidapp.databinding.ActivityDemo1Binding;
import com.strongit.androidapp.ui.vm.DemoViewModel;
import com.strongit.androidapp.utils.StatusBarUtil;
import me.goldze.mvvmhabit.base.BaseActivity;

public class Demo1_Activity extends BaseActivity<ActivityDemo1Binding, DemoViewModel> {

    @Override
    public int initContentView(Bundle bundle) {
        return R.layout.activity_demo1_;
    }

    @Override
    public int initVariableId() {
        return BR.demoViewModel;
    }

    @Override
    public DemoViewModel initViewModel() {
        //View持有ViewModel的引用 (考虑到框架适用性，这里暂时没有用Dagger2解耦)
        return new DemoViewModel(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        floatStatusBar();

        //设置联动对象
        binding.twinklingRefreshLayout.setTargetView(binding.mainContent);

        //运行自动加载更多
        binding.twinklingRefreshLayout.setAutoLoadMore(true);

        //是否保持显示View
        binding.twinklingRefreshLayout.setEnableKeepIView(false);

        //支持切换到像SwipeRefreshLayout一样的悬浮刷新模式了。
        binding.twinklingRefreshLayout.setFloatRefresh(true);

        //现在已经提供了设置默认的Header、Footer的static方法，可在Application或者一个Activity中这样设置
        binding.twinklingRefreshLayout.setDefaultHeader(SinaRefreshView.class.getName());
        binding.twinklingRefreshLayout.setDefaultFooter(BallPulseView.class.getName());

        binding.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    binding.twinklingRefreshLayout.setEnableRefresh(true);
                    binding.twinklingRefreshLayout.setEnableOverScroll(false);
                } else {
                    binding.twinklingRefreshLayout.setEnableRefresh(false);
                    binding.twinklingRefreshLayout.setEnableOverScroll(false);
                }
            }
        });
    }
    @Override
    public void initViewObservable() {
        //监听下拉刷新完成
        viewModel.uc.isFinishRefreshing.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                //结束刷新
                binding.twinklingRefreshLayout.finishRefreshing();
            }
        });
        //监听上拉加载完成
        viewModel.uc.isFinishLoadmore.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                //结束刷新
                binding.twinklingRefreshLayout.finishLoadmore();
            }
        });
    }


    private void floatStatusBar() {
        StatusBarUtil.fullScreen(this);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            RelativeLayout.LayoutParams titleParams = (RelativeLayout.LayoutParams) ll_title.getLayoutParams();
//            // 标题栏在上方留出一段距离，看起来仍在状态栏下方
//            titleParams.topMargin = StatusBarUtil.getStatusBarHeight(this);
//            ll_title.setLayoutParams(titleParams);
//        }
    }

}