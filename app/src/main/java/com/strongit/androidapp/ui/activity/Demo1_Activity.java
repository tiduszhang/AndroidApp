package com.strongit.androidapp.ui.activity;


import android.databinding.Observable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.daimajia.swipe.util.Attributes;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.BallPulseView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.strongit.androidapp.BR;
import com.strongit.androidapp.R;
import com.strongit.androidapp.databinding.ActivityDemo1Binding;
import com.strongit.androidapp.entity.DemoEntity;
import com.strongit.androidapp.ui.vm.DemoViewModel;
import com.strongit.androidapp.utils.adapter.RecyclerViewSwipeAdapter;
import com.strongit.androidapp.utils.listener.AppBarStateChangeListener;
import com.strongit.androidapp.utils.ui.StatusBarUtil;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.ToastUtils;

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

        //binding.toolbarLayout.setTitle("这是一个系统标题");

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
        TwinklingRefreshLayout.setDefaultHeader(SinaRefreshView.class.getName());
        TwinklingRefreshLayout.setDefaultFooter(BallPulseView.class.getName());

//        binding.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (verticalOffset >= 0) {
//                    binding.twinklingRefreshLayout.setEnableRefresh(true);
//                    binding.twinklingRefreshLayout.setEnableOverScroll(false);
//                } else {
//                    binding.twinklingRefreshLayout.setEnableRefresh(false);
//                    binding.twinklingRefreshLayout.setEnableOverScroll(false);
//                }
//            }
//        });

        binding.appBar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, AppBarStateChangeListener.State state) {
                Log.d("STATE", state.name());
                if( state == State.EXPANDED ) {

                    //展开状态
                    binding.twinklingRefreshLayout.setEnableRefresh(true);
                    binding.twinklingRefreshLayout.setEnableOverScroll(false);

                }else if(state == State.COLLAPSED){

                    //折叠状态
                    binding.twinklingRefreshLayout.setEnableRefresh(false);
                    binding.twinklingRefreshLayout.setEnableOverScroll(false);
                }else {

                    //中间状态

                }
            }
        });

        setToolbar();

        setDataItemView();

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            CollapsingToolbarLayout.LayoutParams titleRelativeLayoutLayoutParams = (CollapsingToolbarLayout.LayoutParams) binding.titleRelativeLayout.getLayoutParams();
            // 标题栏在上方留出一段距离，看起来仍在状态栏下方
            titleRelativeLayoutLayoutParams.topMargin = StatusBarUtil.getStatusBarHeight(this);
            // 标题栏在上方留出一段距离，看起来仍在状态栏下方
            binding.titleRelativeLayout.setLayoutParams(titleRelativeLayoutLayoutParams);


            CollapsingToolbarLayout.LayoutParams toobarLayoutParams = (CollapsingToolbarLayout.LayoutParams) binding.toolbar.getLayoutParams();
            // 标题栏在上方留出一段距离，看起来仍在状态栏下方
            toobarLayoutParams.topMargin = StatusBarUtil.getStatusBarHeight(this);
            // 标题栏在上方留出一段距离，看起来仍在状态栏下方
            binding.toolbar.setLayoutParams(toobarLayoutParams);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu); //解析menu布局文件到menu
        return super.onCreateOptionsMenu(menu);
    }


    //设置标题栏Toolbar
    private void setToolbar() {
        Toolbar toolbar  = binding.toolbar;
//        toolbar.setSubtitleTextColor(Color.WHITE);  //设置副标题字体颜色
        setSupportActionBar(toolbar);   //必须使用
        //添加左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("顶部导航按钮点击被点击了一下！");
            }
        });

        //添加menu项点击事件
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.toolbar_r_img:
                        Log.e("Test---->","点击了右边图标");
                        break;
                    case R.id.toolbar_r_1:
                        Log.e("Test---->","点击了弹出菜单1");
                        break;
                    case R.id.toolbar_r_2:
                        Log.e("Test---->","点击了弹出菜单2");
                        break;
                    case R.id.toolbar_r_3:
                        Log.e("Test---->","点击了弹出菜单3");
                        break;
                }
                return true;    //返回为true
            }
        });
    }


    private RecyclerViewSwipeAdapter<DemoEntity.ItemsEntity> mNewsAdapter;

    private void setDataItemView(){

        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.mainContent.setLayoutManager(linearLayoutManager);


        //设置适配器
        if(mNewsAdapter == null){
            //设置适配器
            mNewsAdapter = new RecyclerViewSwipeAdapter(this, this.viewModel.observableList, R.id.swipeLayout, R.layout.activity_demo_item, R.id.bottom_wrapper);
            mNewsAdapter.setMode(Attributes.Mode.Single);//设置只有一个拖拽打开的时候，其他的关闭
            binding.mainContent.setAdapter(mNewsAdapter);
            //添加分割线
            //设置添加删除动画
            //调用ListView的setSelected(!ListView.isSelected())方法，这样就能及时刷新布局
            binding.mainContent.setSelected(true);
        }else{
            mNewsAdapter.notifyDataSetChanged();
        }

    }

}