package young.tze.appstat;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

/**
 * Created by Yangzhi on 2017-06-20.
 */

public class ItemInfo implements Comparable {
    private String mPackageName;
    private String mAppNameInDefault;
    private String mAppNameInPinyin;
    private Drawable mAppIcon;
    private int mTotalUsage;
    private int mDailyUsage;

    public ItemInfo() {
        mPackageName = null;
        mAppNameInDefault = null;
        mAppNameInPinyin = null;
        mAppIcon = null;
        mTotalUsage = 0;
        mDailyUsage = 0;
    }

    public ItemInfo(String packageName, String appNameInDefault, String appNameInPinyin, Drawable appIcon) {
        this();
        this.mPackageName = packageName;
        this.mAppNameInDefault = appNameInDefault;
        this.mAppNameInPinyin = appNameInPinyin;
        this.mAppIcon = appIcon;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public void setPackageName(String packageName) {
        this.mPackageName = packageName;
    }

    public int getTotalUsage() {
        return mTotalUsage;
    }

    public void setTotalUsage(int totalUsage) {
        this.mTotalUsage = totalUsage;
    }

    public int getDailyUsage() {
        return mDailyUsage;
    }

    public void setDailyUsage(int dailyUsage) {
        this.mDailyUsage = dailyUsage;
    }

    public String getAppNameInDefault() {
        return mAppNameInDefault;
    }

    public void setAppName(String appNameInDefault) {
        this.mAppNameInDefault = appNameInDefault;
    }

    public String getAppNameInPinyin() {
        return mAppNameInPinyin;
    }

    public void setAppNameInPinyin(String appNameInPinyin) {
        this.mAppNameInPinyin = appNameInPinyin;
    }


    public Drawable getAppIcon() {
        return mAppIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.mAppIcon = appIcon;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        if (o instanceof ItemInfo) {
            ItemInfo itemInfo = (ItemInfo) o;
            if (Character.isLetter(getAppNameInPinyin().charAt(0))
                    && Character.isLetter(itemInfo.getAppNameInPinyin().charAt(0))) {
                return this.mAppNameInPinyin.compareTo(itemInfo.mAppNameInPinyin);
            } else if (Character.isLetter(getAppNameInPinyin().charAt(0))) {
                return -1;
            } else if (Character.isLetter(itemInfo.getAppNameInPinyin().charAt(0))) {
                return 1;
            }
            return 0;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String toString() {
        return "ItemInfo{" +
                "mPackageName='" + mPackageName + '\'' +
                ", mAppNameInDefault='" + mAppNameInDefault + '\'' +
                ", mAppNameInPinyin='" + mAppNameInPinyin + '\'' +
                ", mAppIcon=" + mAppIcon +
                ", mTotalUsage=" + mTotalUsage +
                ", mDailyUsage=" + mDailyUsage +
                '}';
    }
}
