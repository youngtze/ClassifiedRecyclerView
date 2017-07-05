package young.tze.appstat;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.ArrayList;

/**
 * Created by Yangzhi on 2017-06-30.
 */

public class ClassifiedRecyclerView extends RecyclerView implements IndexBar.IndexChangedListener {
    private IndexBar mIndexBar;

    public ClassifiedRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getContext() instanceof Activity) {
            Activity activity = (Activity) getContext();
            mIndexBar = (IndexBar) activity.findViewById(R.id.index_bar);
        }
        if (mIndexBar != null) {
            mIndexBar.addListener(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mIndexBar != null) {
            mIndexBar.removeListener(this);
        }
    }

    private int getScrollPosition(String index) {
        RecyclerViewAdapter rva = (RecyclerViewAdapter) getAdapter();
        ArrayList<ItemInfo> itemInfos = rva.getDataList();
        for (int i = 0; i < itemInfos.size(); i++) {
            if (("" + itemInfos.get(i).getAppNameInPinyin().charAt(0)).equals(index.toLowerCase())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean indexChanged(String index) {
        if (index != null) {
            int position = getScrollPosition(index);
            if (position != -1) {
                LinearLayoutManager llm = (LinearLayoutManager) getLayoutManager();
                llm.scrollToPositionWithOffset(getScrollPosition(index), 0);
                return true;
            }
        }
        return false;
    }
}
