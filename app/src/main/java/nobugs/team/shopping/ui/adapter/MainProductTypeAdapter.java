package nobugs.team.shopping.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nobugs.team.shopping.R;
import nobugs.team.shopping.repo.db.entity.ProductTypePo;

/**
 * Created by Administrator on 2015/8/16 0016.
 */
public class MainProductTypeAdapter extends RecyclerView.Adapter<MainProductTypeAdapter.ViewHolder> {

    private List<ProductTypePo> mProductTypePoList;

    private int mCurSelectIndex;

    private Context mContex;

    private OnItemClickListener mItemClickListener;

    public MainProductTypeAdapter(List<ProductTypePo> productTypePoList) {
        this.mProductTypePoList = productTypePoList;
    }


    public List<ProductTypePo> getProductTypeList() {
        return mProductTypePoList;
    }

    public void setProductTypeList(List<ProductTypePo> productTypePos) {
        this.mProductTypePoList = productTypePos;
    }

    public void addProductTypeToList(List<ProductTypePo> collectionToAdd) {
        mProductTypePoList.addAll(collectionToAdd);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContex = parent.getContext();
        return new ViewHolder(LayoutInflater.from(mContex).inflate(R.layout.item_main_product_type, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.update(position);
    }

    @Override
    public int getItemCount() {
        return mProductTypePoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.lyt_main_type_item)
        RelativeLayout lytMainTypeItem;

        @Bind(R.id.iv_main_type)
        ImageView ivMainType;

        @Bind(R.id.tv_main_type_name)
        TextView tvMainTypeName;

        ProductTypePo mProductTypePo;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.lyt_main_type_item)
        public void onItemClick(View v) {
            int position = getLayoutPosition();

            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, position, mProductTypePo);
            }

            if (position != mCurSelectIndex) {
                notifyItemChanged(position);
                notifyItemChanged(mCurSelectIndex);

                mCurSelectIndex = position;
            }
        }

        public void update(int position) {
            mProductTypePo = mProductTypePoList.get(position);

            tvMainTypeName.setText(mProductTypePo.getName());

            if (mProductTypePo.getImgurl() != null && !mProductTypePo.getImgurl().equals("")) {
                Picasso.with(mContex).load(mProductTypePo.getImgurl()).into(ivMainType);
            }

            if (position == mCurSelectIndex) {
                tvMainTypeName.setTextColor(mContex.getResources().getColor(android.R.color.white));
                tvMainTypeName.setBackgroundResource(R.drawable.bg_tv_main_type_item);
            } else {
                tvMainTypeName.setTextColor(mContex.getResources().getColor(android.R.color.black));
                tvMainTypeName.setBackgroundResource(0);
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, ProductTypePo productTypePo);
    }
}
