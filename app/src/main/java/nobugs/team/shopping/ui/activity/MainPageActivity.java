package nobugs.team.shopping.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.CBPageAdapter;
import com.bigkoo.convenientbanner.CBViewHolderCreator;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import nobugs.team.shopping.R;
import nobugs.team.shopping.app.base.BaseActivity;
import nobugs.team.shopping.db.entity.ProductType;
import nobugs.team.shopping.db.entity.Shop;
import nobugs.team.shopping.db.entity.User;
import nobugs.team.shopping.mvp.presenter.MainPagePresenter;
import nobugs.team.shopping.mvp.presenter.MainPagePresenterImpl;
import nobugs.team.shopping.mvp.view.MainPageView;
import nobugs.team.shopping.ui.adapter.MainProductTypeAdapter;
import nobugs.team.shopping.ui.adapter.ShopAdapter;
import nobugs.team.shopping.ui.adapter.SubProductTypeAdapter;

/**
 * 选择商家店铺页面
 */
public class MainPageActivity extends BaseActivity<MainPagePresenter> implements MainPageView {

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

    private MainProductTypeAdapter mMainProductTypeAdapter;
    private SubProductTypeAdapter mSubProductTypeAdapter;
    private ShopAdapter mShopAdapter;

    @Override
    protected MainPagePresenter initPresenter() {
        return new MainPagePresenterImpl(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main_page;
    }

    @Override
    protected void initView() {
        initMainTypeRecyclerView();
        initSubTypeRecyclerView();
        initShopRecyclerView();
    }

    private void initMainTypeRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvMainTypes.setLayoutManager(layoutManager);

        mMainProductTypeAdapter = new MainProductTypeAdapter(new ArrayList<ProductType>());
        mMainProductTypeAdapter.setOnItemClickListener(new MainProductTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ProductType productType) {
                getPresenter().onSelectMainProductType(productType.getId());
            }
        });

        rvMainTypes.setAdapter(mMainProductTypeAdapter);
        rvMainTypes.setHasFixedSize(true);
    }

    private void initSubTypeRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvSubTypes.setLayoutManager(layoutManager);

        mSubProductTypeAdapter = new SubProductTypeAdapter(new ArrayList<ProductType>());
        mSubProductTypeAdapter.setOnItemClickListener(new SubProductTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ProductType productType) {
                getPresenter().onSelectSubProductType(productType.getId());
            }
        });

        rvSubTypes.setAdapter(mSubProductTypeAdapter);
        rvSubTypes.setHasFixedSize(true);
    }

    private void initShopRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
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
    public void showShops(List<Shop> productTypes) {
        mShopAdapter.setShopList(productTypes);
        mShopAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyShop() {

    }

    @Override
    public void navigateCallOut(User user) {
        //navigate to CallOutActivity to make a call with the seller
        startActivity(new Intent(this,CallOutActivity.class));

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
//                if (urlOrId instanceof Integer) {
//                    imageView.setImageResource((Integer) urlOrId);
//                } else if (urlOrId instanceof String) {
//                    imageView.setImageResource(android.R.color.white);
                    Picasso.with(context).load(url).into(imageView);
//                }
            }

//            imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //点击事件
//                    Toast.makeText(view.getContext(), "点击了第" + (position + 1) + "图片", Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    }
}
