package young.tze.appstat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by Yangzhi on 2017-06-27.
 */

public class LoaderTask extends AsyncTask<Void, Void, ArrayList<ItemInfo>> {
    private WeakReference<Context> mContext;
    private RecyclerView mRecyclerView;
    private View mProgressBarContainer;
    private View mIndexBar;

    public LoaderTask(Context context) {
        mContext = new WeakReference<>(context);

        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            mRecyclerView = (RecyclerView) activity.findViewById(R.id.recycler_view);
            mProgressBarContainer = activity.findViewById(R.id.progress_bar_container);
            mIndexBar = activity.findViewById(R.id.index_bar);
        }

    }

    @Override
    protected ArrayList<ItemInfo> doInBackground(Void... params) {
        Context context = mContext.get();
        if (context == null) {
            return null;
        }
        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent,
                PackageManager.MATCH_UNINSTALLED_PACKAGES);
        PriorityQueue<ItemInfo> itemInfos = new PriorityQueue<>();
        for (int i = 0; i < resolveInfos.size(); i++) {
            String packageName = resolveInfos.get(i).activityInfo.packageName;
            String appName = resolveInfos.get(i).activityInfo.loadLabel(pm).toString();
            appName = Utilities.trimLeading(appName.trim());
            Drawable appIcon = resolveInfos.get(i).activityInfo.loadIcon(pm);
            try {
                String appNameInPinyin = PinyinHelper.convertToPinyinString(appName, "", PinyinFormat.WITHOUT_TONE);
                appNameInPinyin = appNameInPinyin.toLowerCase();
                itemInfos.add(new ItemInfo(packageName, appName, appNameInPinyin, appIcon));
            } catch (PinyinException pe) {

            }
        }
        ArrayList<ItemInfo> arrayList = new ArrayList<>();
        ItemInfo itemInfo;
        while ((itemInfo = itemInfos.poll()) != null) {
            arrayList.add(itemInfo);
        }
        return arrayList;
    }


    @Override
    protected void onPostExecute(final ArrayList<ItemInfo> itemInfos) {
        Context context = mContext.get();
        mProgressBarContainer.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mIndexBar.setVisibility(View.VISIBLE);
        mRecyclerView.addItemDecoration(new RecyclerViewDecoration(mContext.get(), itemInfos));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(new RecyclerViewAdapter(context, itemInfos));
    }
}
