package nobugs.team.shopping.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import nobugs.team.shopping.R;
import nobugs.team.shopping.app.base.BaseFragment;
import nobugs.team.shopping.repo.db.entity.ProductTypePo;
import nobugs.team.shopping.repo.db.entity.ShopPo;
import nobugs.team.shopping.repo.db.entity.UserPo;
import nobugs.team.shopping.mvp.presenter.MainShopPresenter;
import nobugs.team.shopping.mvp.presenter.MainShopPresenterImpl;
import nobugs.team.shopping.mvp.presenter.VideoCallPresenterImpl;
import nobugs.team.shopping.mvp.view.MainShopView;
import nobugs.team.shopping.ui.activity.VideoCallActivity;
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

   /* @Bind(R.id.btn_main_shop)
    Button btnMainShop;

    @Bind(R.id.btn_main_order)
    Button btnMainOrder;*/

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

        mMainProductTypeAdapter = new MainProductTypeAdapter(new ArrayList<ProductTypePo>());
        mMainProductTypeAdapter.setOnItemClickListener(new MainProductTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ProductTypePo productTypePo) {
                getPresenter().onSelectMainProductType(productTypePo.getId());
            }
        });

        rvMainTypes.setAdapter(mMainProductTypeAdapter);
        rvMainTypes.setHasFixedSize(true);
    }

    private void initSubTypeRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        rvSubTypes.setLayoutManager(layoutManager);

        mSubProductTypeAdapter = new SubProductTypeAdapter(new ArrayList<ProductTypePo>());
        mSubProductTypeAdapter.setOnItemClickListener(new SubProductTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ProductTypePo productTypePo) {
                getPresenter().onSelectSubProductType(productTypePo.getId());
            }
        });

        rvSubTypes.setAdapter(mSubProductTypeAdapter);
        rvSubTypes.setHasFixedSize(true);
    }

    private void initShopRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        rvShops.setLayoutManager(layoutManager);

        mShopAdapter = new ShopAdapter(new ArrayList<ShopPo>());
        mShopAdapter.setOnItemClickListener(new ShopAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ShopPo shopPo) {
                getPresenter().onSelectShop(shopPo);
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
    public void showMainProductTypes(List<ProductTypePo> productTypePos) {
        mMainProductTypeAdapter.setProductTypeList(productTypePos);
        mMainProductTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyMainProductType() {

    }

    @Override
    public void showSubProductTypes(List<ProductTypePo> productTypePos) {
        mSubProductTypeAdapter.setProductTypeList(productTypePos);
        mSubProductTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptySubProductType() {

    }

    @Override
    public void showShops(List<ShopPo> productTypes) {
        mShopAdapter.setShopList(productTypes);
        mShopAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyShop() {

    }

    @Override
    public void navigateCallOut(@NonNull UserPo userPo) {
        //navigate to VideoCallActivity to make a call with the seller
        Intent intent = new Intent(this.getActivity(), VideoCallActivity.class);
        intent.putExtra(VideoCallPresenterImpl.EXTRA_OUTGOING_CALL, true);
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
