package nobugs.team.shopping.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nobugs.team.shopping.R;
import nobugs.team.shopping.app.base.BaseActivity;
import nobugs.team.shopping.mvp.presenter.MainPagePresenter;
import nobugs.team.shopping.mvp.presenter.MainPagePresenterImpl;
import nobugs.team.shopping.mvp.view.MainPageView;
import nobugs.team.shopping.ui.fragment.MainOrderListFragment;
import nobugs.team.shopping.ui.fragment.MainShopFragment;

/**
 * 选择商家店铺页面
 */
public class MainPageActivity extends BaseActivity<MainPagePresenter> implements MainPageView {


    @Bind(R.id.btn_main_shop)
    Button mBtnMainShop;
    @Bind(R.id.btn_main_order)
    Button mBtnMainOrder;
    @Bind(R.id.frame_fragment_content)
    FrameLayout frameFragmentContent;

    private MainShopFragment mFragmentShop;
    private MainOrderListFragment mFragmentOrder;


    @Override
    protected MainPagePresenter initPresenter() {
        return new MainPagePresenterImpl(this);
    }

    @Override
    protected void initView() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mFragmentShop = new MainShopFragment();
        mFragmentOrder = new MainOrderListFragment();
        fragmentTransaction.replace(R.id.frame_fragment_content,mFragmentShop);
        fragmentTransaction.commit();

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main_page;
    }


    @OnClick({R.id.btn_main_shop, R.id.btn_main_order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_main_shop:
                getPresenter().navigateToShop();
                break;
            case R.id.btn_main_order:
                getPresenter().navigateToOrderList();
                break;
        }
    }

    @Override
    public void showShop() {
        FragmentManager fm = getSupportFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();
        if(mFragmentShop == null){
            mFragmentShop = new MainShopFragment();
        }
        transaction.replace(R.id.frame_fragment_content, mFragmentShop).commit();
    }

    @Override
    public void showOrderList() {
        FragmentManager fm = getSupportFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();
        if(mFragmentOrder == null){
            mFragmentOrder = new MainOrderListFragment();
        }
        transaction.replace(R.id.frame_fragment_content, mFragmentOrder).commit();    }
}
