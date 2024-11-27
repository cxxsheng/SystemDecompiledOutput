package android.widget;

/* loaded from: classes4.dex */
public abstract class BaseAdapter implements android.widget.ListAdapter, android.widget.SpinnerAdapter {
    private java.lang.CharSequence[] mAutofillOptions;
    private final android.database.DataSetObservable mDataSetObservable = new android.database.DataSetObservable();

    @Override // android.widget.Adapter
    public boolean hasStableIds() {
        return false;
    }

    @Override // android.widget.Adapter
    public void registerDataSetObserver(android.database.DataSetObserver dataSetObserver) {
        this.mDataSetObservable.registerObserver(dataSetObserver);
    }

    @Override // android.widget.Adapter
    public void unregisterDataSetObserver(android.database.DataSetObserver dataSetObserver) {
        this.mDataSetObservable.unregisterObserver(dataSetObserver);
    }

    public void notifyDataSetChanged() {
        this.mDataSetObservable.notifyChanged();
    }

    public void notifyDataSetInvalidated() {
        this.mDataSetObservable.notifyInvalidated();
    }

    @Override // android.widget.ListAdapter
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override // android.widget.ListAdapter
    public boolean isEnabled(int i) {
        return true;
    }

    public android.view.View getDropDownView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
        return getView(i, view, viewGroup);
    }

    @Override // android.widget.Adapter
    public int getItemViewType(int i) {
        return 0;
    }

    @Override // android.widget.Adapter
    public int getViewTypeCount() {
        return 1;
    }

    @Override // android.widget.Adapter
    public boolean isEmpty() {
        return getCount() == 0;
    }

    @Override // android.widget.Adapter
    public java.lang.CharSequence[] getAutofillOptions() {
        return this.mAutofillOptions;
    }

    public void setAutofillOptions(java.lang.CharSequence... charSequenceArr) {
        this.mAutofillOptions = charSequenceArr;
    }
}
