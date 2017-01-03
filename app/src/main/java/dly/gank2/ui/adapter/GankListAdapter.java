package dly.gank2.ui.adapter;


import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import dly.gank2.R;
import dly.gank2.data.ui.GankTopImageItem;
import dly.gank2.data.ui.GankHeaderItem;
import dly.gank2.data.ui.GankItem;
import dly.gank2.data.ui.GankNormalItem;
import dly.gank2.ui.widget.RatioImageView;
import dly.gank2.utils.AppUtil;

/**
 * description
 *
 *
 */
public class GankListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_HEADER = 2;
    private static final int VIEW_TYPE_GIRL_IMAGE = 3;

    private Fragment mFragment;
    private List<GankItem> mItems;

    private OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onClickNormalItem(View view, GankNormalItem normalItem);
        void onClickGirlItem(View view, GankTopImageItem girlItem);
    }

    public GankListAdapter(Fragment fragment) {
        mFragment = fragment;
    }

    public void refreshData(List<GankItem> list) {
        mItems = list;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        mItemClickListener = clickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                return new CategoryHeaderViewHolder(parent);
            case VIEW_TYPE_NORMAL:
                return new NormalViewHolder(parent);
            case VIEW_TYPE_GIRL_IMAGE:
                return new GirlImageViewHolder(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof CategoryHeaderViewHolder) {
            CategoryHeaderViewHolder headerHolder = (CategoryHeaderViewHolder) holder;
            headerHolder.title.setText(((GankHeaderItem)mItems.get(position)).name);
            return;
        }
        if(holder instanceof NormalViewHolder) {
            NormalViewHolder normalHolder = (NormalViewHolder) holder;
            final GankNormalItem normalItem = (GankNormalItem) mItems.get(position);
            normalHolder.title.setText(getGankTitleStr(normalItem.desc, normalItem.who));
            normalHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(null != mItemClickListener) {
                        mItemClickListener.onClickNormalItem(v, normalItem);
                    }
                }
            });
            return;
        }
        if(holder instanceof GirlImageViewHolder) {
            GirlImageViewHolder girlHolder = (GirlImageViewHolder) holder;
            final GankTopImageItem girlItem = (GankTopImageItem) mItems.get(position);
            Glide.with(mFragment)
                    .load(girlItem.imgUrl)
                    .placeholder(R.color.imageColorPlaceholder)
                    .centerCrop()
                    .into(girlHolder.girl_image);
            girlHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(null != mItemClickListener) {
                        mItemClickListener.onClickGirlItem(v, girlItem);
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        GankItem gankItem = mItems.get(position);
        if(gankItem instanceof GankHeaderItem) {
            return VIEW_TYPE_HEADER;
        }
        if(gankItem instanceof GankTopImageItem) {
            return VIEW_TYPE_GIRL_IMAGE;
        }
        return VIEW_TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        return null == mItems ? 0 : mItems.size();
    }

    private CharSequence getGankTitleStr(String desc, String who) {
        if(TextUtils.isEmpty(who)) {
            return desc;
        }
        SpannableStringBuilder builder = new SpannableStringBuilder(desc);
        SpannableString spannableString = new SpannableString(" (" + who + ")");
        spannableString.setSpan(new TextAppearanceSpan(AppUtil.getAppContext(), R.style.SummaryTextAppearance), 0, spannableString.length(), 0);
        builder.append(spannableString);
        return builder;
    }

    public static class CategoryHeaderViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.category_title) TextView title;

        public CategoryHeaderViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_category_title, parent, false));
            ButterKnife.bind(this, itemView);
        }
    }

    public static class NormalViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.title) TextView title;

        public NormalViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_gank, parent, false));
            ButterKnife.bind(this, itemView);
        }
    }

    public static class GirlImageViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.girl_image)
        RatioImageView girl_image;

        public GirlImageViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_girl_imge, parent, false));
            ButterKnife.bind(this, itemView);
            girl_image.setRatio(1.618f);
        }
    }
}
