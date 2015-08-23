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
import nobugs.team.shopping.mvp.model.Shop;

/**
 * Created by Administrator on 2015/8/16 0016.
 */
public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    private List<Shop> mShopList;

    private Context mContex;

    private OnItemClickListener mItemClickListener;

    public ShopAdapter(List<Shop> shopList) {
        this.mShopList = shopList;
    }

    public List<Shop> getShopList() {
        return mShopList;
    }

    public void setShopList(List<Shop> shops) {
        this.mShopList = shops;
    }

    public void addShopToList(List<Shop> collectionToAdd) {
        mShopList.addAll(collectionToAdd);
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
        return mShopList.size();
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

        Shop mShop;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.btn_shop_open)
        public void onButtonClick(View v) {
            int position = getLayoutPosition();

            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, position, mShop);
            }
        }

        public void update(int position) {
            mShop = mShopList.get(position);

            tvShopName.setText(mShop.getName());
            tvShopIntro.setText(mShop.getIntroduction());

//            if (mShop.getImgUrl() != null) {
//                Picasso.with(mContex).load(mShop.getImgUrl()).into(ivAvatarShop);
//            }

        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, Shop Shop);
    }
}
