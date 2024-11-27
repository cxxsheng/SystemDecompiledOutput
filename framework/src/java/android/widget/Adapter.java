package android.widget;

/* loaded from: classes4.dex */
public interface Adapter {
    public static final int IGNORE_ITEM_VIEW_TYPE = -1;
    public static final int NO_SELECTION = Integer.MIN_VALUE;

    int getCount();

    java.lang.Object getItem(int i);

    long getItemId(int i);

    int getItemViewType(int i);

    android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup);

    int getViewTypeCount();

    boolean hasStableIds();

    boolean isEmpty();

    void registerDataSetObserver(android.database.DataSetObserver dataSetObserver);

    void unregisterDataSetObserver(android.database.DataSetObserver dataSetObserver);

    default java.lang.CharSequence[] getAutofillOptions() {
        return null;
    }
}
