package nobugs.team.shopping.ui.activity;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.CBPageAdapter;
import com.bigkoo.convenientbanner.CBViewHolderCreator;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import nobugs.team.shopping.R;
import nobugs.team.shopping.app.base.BaseActivity;
import nobugs.team.shopping.mvp.presenter.MainPagePresenterImpl;
import nobugs.team.shopping.mvp.view.MainPageView;

/**
 * 选择商家店铺页面
 */
public class MainPageActivity extends BaseActivity implements MainPageView {

    @Bind(R.id.banner_main)
    ConvenientBanner bannerMain;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main_page);
    }

    @Override
    protected void initData() {
        setPresenter(new MainPagePresenterImpl(this));
    }

    @Override
    public MainPagePresenterImpl getPresenter() {
        return (MainPagePresenterImpl) super.getPresenter();
    }

    @Override
    public void showAndRunAdsBanner(List<Object> imgRes, int period) {
        if (bannerMain != null) {
            bannerMain.setPages(
                    new CBViewHolderCreator<ImageViewHolder>() {
                        @Override
                        public ImageViewHolder createHolder() {
                            return new ImageViewHolder();
                        }
                    }, imgRes)
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

    public class ImageViewHolder implements CBPageAdapter.Holder<Object> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, Object urlOrId) {
            if (urlOrId != null) {
                if (urlOrId instanceof Integer) {
                    imageView.setImageResource((Integer)urlOrId);
                } else if (urlOrId instanceof String) {
                    imageView.setImageResource(android.R.color.white);
                    Picasso.with(context).load((String) urlOrId).into(imageView);
                }
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
