package nobugs.team.shopping.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.CBPageAdapter;
import com.bigkoo.convenientbanner.CBViewHolderCreator;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import nobugs.team.shopping.R;
import nobugs.team.shopping.app.base.BaseFragment;
import nobugs.team.shopping.mvp.model.ProductType;
import nobugs.team.shopping.mvp.model.Shop;
import nobugs.team.shopping.mvp.model.User;
import nobugs.team.shopping.mvp.presenter.MainShopPresenter;
import nobugs.team.shopping.mvp.presenter.MainShopPresenterImpl;
import nobugs.team.shopping.mvp.presenter.VoipCallPresenterImpl;
import nobugs.team.shopping.mvp.view.MainShopView;
import nobugs.team.shopping.ui.activity.VoipCallActivity;
import nobugs.team.shopping.ui.adapter.MainProductTypeAdapter;
import nobugs.team.shopping.ui.adapter.ShopAdapter;
import nobugs.team.shopping.ui.adapter.SubProductTypeAdapter;

/**
 * ShopPo
 */
public class MainShopFragment extends BaseFragment<MainShopPresenter> implements MainShopView {


    @Bind(R.id.banner_main)
    ConvenientBanner bannerMain;

    @Bind(R.id.edt_search)
    EditText edtSearch;

    @Bind(R.id.rv_main_types)
    RecyclerView rvMainTypes;

    @Bind(R.id.rv_sub_types)
    RecyclerView rvSubTypes;

    @Bind(R.id.rv_shops)
    RecyclerView rvShops;

    @Bind(R.id.iv_search)
    ImageView ivSearch;

    private MainProductTypeAdapter mMainProductTypeAdapter;
    private SubProductTypeAdapter mSubProductTypeAdapter;
    private ShopAdapter mShopAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainShopFragment.
     */
    public static MainShopFragment newInstance(String param1, String param2) {
        MainShopFragment fragment = new MainShopFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    public MainShopFragment() {
        // Required empty public constructor
    }


    @Override
    protected MainShopPresenter initPresenter() {
        return new MainShopPresenterImpl(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_main_shop;
    }

    @Override
    protected void initView() {
        initMainTypeRecyclerView();
        initSubTypeRecyclerView();
        initShopRecyclerView();
    }

    private void initMainTypeRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvMainTypes.setLayoutManager(layoutManager);

        mMainProductTypeAdapter = new MainProductTypeAdapter(new ArrayList<ProductType>());
        mMainProductTypeAdapter.setOnItemClickListener(new MainProductTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ProductType productType) {
                getPresenter().onSelectMainProductType(productType);
            }
        });

        rvMainTypes.setAdapter(mMainProductTypeAdapter);
        rvMainTypes.setHasFixedSize(true);
    }

    private void initSubTypeRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        rvSubTypes.setLayoutManager(layoutManager);

        mSubProductTypeAdapter = new SubProductTypeAdapter(new ArrayList<ProductType>());
        mSubProductTypeAdapter.setOnItemClickListener(new SubProductTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ProductType productTypePo) {
                getPresenter().onSelectSubProductType(productTypePo);
            }
        });

        rvSubTypes.setAdapter(mSubProductTypeAdapter);
        rvSubTypes.setHasFixedSize(true);
    }

    private void initShopRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        rvShops.setLayoutManager(layoutManager);

        mShopAdapter = new ShopAdapter(new ArrayList<Shop>());
        mShopAdapter.setOnItemClickListener(new ShopAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Shop shop) {
                getPresenter().onSelectShop(shop);
            }
        });

        rvShops.setAdapter(mShopAdapter);
        rvShops.setHasFixedSize(true);
    }

    @OnClick(R.id.iv_search)
    public void onSearchClick() {
        String keyword = edtSearch.getText().toString();
        getPresenter().onSearchShop(keyword);
        edtSearch.clearFocus();
        InputMethodManager ime = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        ime.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @OnEditorAction(R.id.edt_search)
    public boolean onSearchPress(TextView v, int actionId, KeyEvent event) {
        if (actionId == KeyEvent.ACTION_DOWN || actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH) {
            String keyword = v.getText().toString();
            getPresenter().onSearchShop(keyword);
            v.clearFocus();
        }
        return true;
    }

    @Override
    public void showAndRunAdsBanner(List<String> imgUrls, int period) {
        if (bannerMain != null) {
            bannerMain.setPages(
                    new CBViewHolderCreator<ImageViewHolder>() {
                        @Override
                        public ImageViewHolder createHolder() {
                            return new ImageViewHolder();
                        }
                    }, imgUrls)
                    .setPageIndicator(new int[]{R.drawable.ic_banner_indicator, R.drawable.ic_banner_indicator_focus})
                    .setPageTransformer(ConvenientBanner.Transformer.DefaultTransformer);
            bannerMain.startTurning(period);
        }
    }

    @Override
    public void showEmptyBanner() {

    }

    @Override
    public void showErrorBanner() {

    }

    @Override
    public void stopRunAdsBanner() {
        if (bannerMain != null) {
            bannerMain.stopTurning();
        }
    }

    @Override
    public void showMainProductTypes(List<ProductType> productTypes) {
        mMainProductTypeAdapter.setProductTypeList(productTypes);
        mMainProductTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyMainProductType() {

    }

    @Override
    public void showSubProductTypes(List<ProductType> productTypes) {
        mSubProductTypeAdapter.setProductTypeList(productTypes);
        mSubProductTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptySubProductType() {

    }

    @Override
    public void showShops(List<Shop> shops) {
        mShopAdapter.setShopList(shops);
        mShopAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyShop() {

    }

    @Override
    public void navigateCallOut(@NonNull User user) {
        //navigate to VideoCallActivity to make a call with the SELLER
        Intent intent = new Intent(this.getActivity(), VoipCallActivity.class);
        intent.putExtra(VoipCallPresenterImpl.EXTRA_OUTGOING_CALL, true);
        startActivity(intent);
    }


    public class ImageViewHolder implements CBPageAdapter.Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, String url) {
            if (url != null) {
                Picasso.with(context).load(url).into(imageView);
            }

        }
    }
}
