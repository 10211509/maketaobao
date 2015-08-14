package nobugs.team.shopping.ui.activity;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.CBPageAdapter;
import com.bigkoo.convenientbanner.CBViewHolderCreator;
import com.bigkoo.convenientbanner.ConvenientBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import nobugs.team.shopping.R;
import nobugs.team.shopping.app.base.BaseActivity;

/**
 * 选择商家店铺页面
 */
public class MainPageActivity extends BaseActivity {

    public static final long BANNER_TURN_PERIOD = 3000L;

    @Bind(R.id.banner_main)
    ConvenientBanner bannerMain;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main_page);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void updateData() {
        initBannerPager();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bannerMain.startTurning(BANNER_TURN_PERIOD);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bannerMain.stopTurning();
    }

    private void initBannerPager() {
        bannerMain.setPages(
            new CBViewHolderCreator<LocalImageHolderView>() {
                @Override
                public LocalImageHolderView createHolder() {
                    return new LocalImageHolderView();
                }
            }, getTestImage())
            .setPageIndicator(new int[]{R.drawable.ic_banner_indicator, R.drawable.ic_banner_indicator_focus})
            .setPageTransformer(ConvenientBanner.Transformer.DefaultTransformer);
    }

    private List<Integer> getTestImage() {
        List<Integer> localImages = new ArrayList<>();
        localImages.add(R.drawable.iv_banner_1);
        localImages.add(R.drawable.iv_banner_1);
        localImages.add(R.drawable.iv_banner_1);
        return localImages;
    }

    public class LocalImageHolderView implements CBPageAdapter.Holder<Integer> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, Integer resId) {
            imageView.setImageResource(resId);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //点击事件
                    Toast.makeText(view.getContext(), "点击了第" + (position + 1) + "图片", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
