package com.android.internal.widget;

/* loaded from: classes5.dex */
public abstract class PagerAdapter {
    public static final int POSITION_NONE = -2;
    public static final int POSITION_UNCHANGED = -1;
    private android.database.DataSetObservable mObservable = new android.database.DataSetObservable();

    public abstract int getCount();

    public abstract boolean isViewFromObject(android.view.View view, java.lang.Object obj);

    public void startUpdate(android.view.ViewGroup viewGroup) {
        startUpdate((android.view.View) viewGroup);
    }

    public java.lang.Object instantiateItem(android.view.ViewGroup viewGroup, int i) {
        return instantiateItem((android.view.View) viewGroup, i);
    }

    public void destroyItem(android.view.ViewGroup viewGroup, int i, java.lang.Object obj) {
        destroyItem((android.view.View) viewGroup, i, obj);
    }

    public void setPrimaryItem(android.view.ViewGroup viewGroup, int i, java.lang.Object obj) {
        setPrimaryItem((android.view.View) viewGroup, i, obj);
    }

    public void finishUpdate(android.view.ViewGroup viewGroup) {
        finishUpdate((android.view.View) viewGroup);
    }

    public void startUpdate(android.view.View view) {
    }

    public java.lang.Object instantiateItem(android.view.View view, int i) {
        throw new java.lang.UnsupportedOperationException("Required method instantiateItem was not overridden");
    }

    public void destroyItem(android.view.View view, int i, java.lang.Object obj) {
        throw new java.lang.UnsupportedOperationException("Required method destroyItem was not overridden");
    }

    public void setPrimaryItem(android.view.View view, int i, java.lang.Object obj) {
    }

    public void finishUpdate(android.view.View view) {
    }

    public android.os.Parcelable saveState() {
        return null;
    }

    public void restoreState(android.os.Parcelable parcelable, java.lang.ClassLoader classLoader) {
    }

    public int getItemPosition(java.lang.Object obj) {
        return -1;
    }

    public void notifyDataSetChanged() {
        this.mObservable.notifyChanged();
    }

    public void registerDataSetObserver(android.database.DataSetObserver dataSetObserver) {
        this.mObservable.registerObserver(dataSetObserver);
    }

    public void unregisterDataSetObserver(android.database.DataSetObserver dataSetObserver) {
        this.mObservable.unregisterObserver(dataSetObserver);
    }

    public java.lang.CharSequence getPageTitle(int i) {
        return null;
    }

    public float getPageWidth(int i) {
        return 1.0f;
    }
}
