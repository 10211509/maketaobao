package nobugs.team.shopping.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nobugs.team.shopping.R;
import nobugs.team.shopping.repo.db.entity.ProductTypePo;

/**
 * Created by Administrator on 2015/8/16 0016.
 */
public class SubProductTypeAdapter extends RecyclerView.Adapter<SubProductTypeAdapter.ViewHolder> {

    private List<ProductTypePo> mProductTypePoList;

    private int mCurSelectIndex;

    private Context mContex;

    private OnItemClickListener mItemClickListener;

    public SubProductTypeAdapter(List<ProductTypePo> productTypePoList) {
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
        return new ViewHolder(LayoutInflater.from(mContex).inflate(R.layout.item_sub_product_type, parent, false));
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

        @Bind(R.id.tv_sub_type_name)
        TextView tvSubTypeName;

        @Bind(R.id.iv_sub_type_choose)
        ImageView ivSubTypeChoose;

        @Bind(R.id.lyt_sub_type_item)
        RelativeLayout lytSubTypeItem;

        private ProductTypePo mProductTypePo;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.lyt_sub_type_item)
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

            tvSubTypeName.setText(mProductTypePo.getName());

            if (position == mCurSelectIndex) {
                tvSubTypeName.setTextColor(mContex.getResources().getColor(R.color.tv_sub_type_pressed));
                ivSubTypeChoose.setVisibility(View.VISIBLE);
            } else {
                tvSubTypeName.setTextColor(mContex.getResources().getColor(R.color.tv_sub_type_normal));
                ivSubTypeChoose.setVisibility(View.GONE);
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, ProductTypePo productTypePo);
    }
}
