package nobugs.team.shopping.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nobugs.team.shopping.R;
import nobugs.team.shopping.repo.db.entity.ShopPo;

/**
 * Created by Administrator on 2015/8/16 0016.
 */
public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    private List<ShopPo> mShopPoList;

    private Context mContex;

    private OnItemClickListener mItemClickListener;

    public ShopAdapter(List<ShopPo> shopPoList) {
        this.mShopPoList = shopPoList;
    }

    public List<ShopPo> getShopList() {
        return mShopPoList;
    }

    public void setShopList(List<ShopPo> shopPos) {
        this.mShopPoList = shopPos;
    }

    public void addShopToList(List<ShopPo> collectionToAdd) {
        mShopPoList.addAll(collectionToAdd);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContex = parent.getContext();
        return new ViewHolder(LayoutInflater.from(mContex).inflate(R.layout.item_main_shop, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.update(position);
    }

    @Override
    public int getItemCount() {
        return mShopPoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_avatar_shop)
        ImageView ivAvatarShop;

        @Bind(R.id.tv_shop_name)
        TextView tvShopName;

        @Bind(R.id.tv_shop_intro)
        TextView tvShopIntro;

        @Bind(R.id.btn_shop_open)
        Button btnShopOpen;

        ShopPo mShopPo;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.btn_shop_open)
        public void onButtonClick(View v) {
            int position = getLayoutPosition();

            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, position, mShopPo);
            }
        }

        public void update(int position) {
            mShopPo = mShopPoList.get(position);

            tvShopName.setText(mShopPo.getName());
            tvShopIntro.setText(mShopPo.getIntroduction());

//            if (mShopPo.getImgurl() != null) {
//                Picasso.with(mContex).load(mShopPo.getImgurl()).into(ivAvatarShop);
//            }

        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, ShopPo ShopPo);
    }
}
