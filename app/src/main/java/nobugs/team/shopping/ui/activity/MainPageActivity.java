package nobugs.team.shopping.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.OnClick;
import nobugs.team.shopping.R;
import nobugs.team.shopping.app.base.BaseActivity;
import nobugs.team.shopping.mvp.model.User;
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
    private long mExitTime;
    @Override
    protected MainPagePresenter initPresenter() {
        return new MainPagePresenterImpl(this);
    }

    @Override
    protected void initView() {
        mFragmentShop = (MainShopFragment) getSupportFragmentManager().findFragmentById(R.id.id_fragment_shop);
        mFragmentOrder = (MainOrderListFragment) getSupportFragmentManager().findFragmentById(R.id.id_fragment_order);
        getPresenter().initView();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

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
        transaction.hide(mFragmentOrder).show(mFragmentShop).commit();
       /* if (mFragmentShop == null) {
            mFragmentShop = new MainShopFragment();
        }
        transaction.replace(R.id.frame_fragment_content, mFragmentShop).commit();*/
    }

    @Override
    public void showOrderList() {
        FragmentManager fm = getSupportFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.hide(mFragmentShop).show(mFragmentOrder).commit();
        /*if (mFragmentOrder == null) {
            mFragmentOrder = new MainOrderListFragment();
        }
        transaction.replace(R.id.frame_fragment_content, mFragmentOrder).commit();*/
    }

    @Override
    public void initView(User user) {

        if (user.isSeller()) {
            showOrderList();
            mBtnMainShop.setVisibility(View.GONE);
            mBtnMainOrder.setVisibility(View.GONE);
        } else {
            showShop();
            mBtnMainShop.setVisibility(View.VISIBLE);
            mBtnMainOrder.setVisibility(View.VISIBLE);
        }


        /*FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mFragmentShop = new MainShopFragment();
        fragmentTransaction.replace(R.id.frame_fragment_content,mFragmentShop);
        fragmentTransaction.commit();*/
    }

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - mExitTime >1500){
            Toast.makeText(this,"再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        }else{
            super.onBackPressed();
        }
    }
}
