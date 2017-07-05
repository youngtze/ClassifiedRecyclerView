package young.tze.appstat;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Yangzhi on 2017-06-20.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private Context mContext;
    private ArrayList<ItemInfo> mItemInfos;
    private int mItemHeight;

    public RecyclerViewAdapter(Context context, ArrayList<ItemInfo> itemInfos) {
        this.mContext = context;
        this.mItemInfos = itemInfos;
        this.mItemHeight = context.getResources().getDimensionPixelSize(R.dimen.item_icon_height);
    }

    public ArrayList<ItemInfo> getDataList() {
        return mItemInfos;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.recycler_item_view, parent, false); //注意parent为null与不为null的区别
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return mItemInfos.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView mAppIcon;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mAppIcon = (TextView) itemView.findViewById(R.id.app_icon);
        }

        public void bindData(final int position) {
            Drawable appIcon = mItemInfos.get(position).getAppIcon();
            appIcon.setBounds(new Rect(0, 0, mItemHeight, mItemHeight));
            mAppIcon.setCompoundDrawables(null, appIcon, null, null);
            mAppIcon.setText(mItemInfos.get(position).getAppNameInDefault());
        }
    }
}
