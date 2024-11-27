package android.app;

@java.lang.Deprecated
/* loaded from: classes.dex */
public class ListActivity extends android.app.Activity {
    protected android.widget.ListAdapter mAdapter;
    protected android.widget.ListView mList;
    private android.os.Handler mHandler = new android.os.Handler();
    private boolean mFinishedStart = false;
    private java.lang.Runnable mRequestFocus = new java.lang.Runnable() { // from class: android.app.ListActivity.1
        @Override // java.lang.Runnable
        public void run() {
            android.app.ListActivity.this.mList.focusableViewAvailable(android.app.ListActivity.this.mList);
        }
    };
    private android.widget.AdapterView.OnItemClickListener mOnClickListener = new android.widget.AdapterView.OnItemClickListener() { // from class: android.app.ListActivity.2
        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(android.widget.AdapterView<?> adapterView, android.view.View view, int i, long j) {
            android.app.ListActivity.this.onListItemClick((android.widget.ListView) adapterView, view, i, j);
        }
    };

    protected void onListItemClick(android.widget.ListView listView, android.view.View view, int i, long j) {
    }

    @Override // android.app.Activity
    protected void onRestoreInstanceState(android.os.Bundle bundle) {
        ensureList();
        super.onRestoreInstanceState(bundle);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        this.mHandler.removeCallbacks(this.mRequestFocus);
        super.onDestroy();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onContentChanged() {
        super.onContentChanged();
        android.view.View findViewById = findViewById(16908292);
        this.mList = (android.widget.ListView) findViewById(16908298);
        if (this.mList == null) {
            throw new java.lang.RuntimeException("Your content must have a ListView whose id attribute is 'android.R.id.list'");
        }
        if (findViewById != null) {
            this.mList.setEmptyView(findViewById);
        }
        this.mList.setOnItemClickListener(this.mOnClickListener);
        if (this.mFinishedStart) {
            setListAdapter(this.mAdapter);
        }
        this.mHandler.post(this.mRequestFocus);
        this.mFinishedStart = true;
    }

    public void setListAdapter(android.widget.ListAdapter listAdapter) {
        synchronized (this) {
            ensureList();
            this.mAdapter = listAdapter;
            this.mList.setAdapter(listAdapter);
        }
    }

    public void setSelection(int i) {
        this.mList.setSelection(i);
    }

    public int getSelectedItemPosition() {
        return this.mList.getSelectedItemPosition();
    }

    public long getSelectedItemId() {
        return this.mList.getSelectedItemId();
    }

    public android.widget.ListView getListView() {
        ensureList();
        return this.mList;
    }

    public android.widget.ListAdapter getListAdapter() {
        return this.mAdapter;
    }

    private void ensureList() {
        if (this.mList != null) {
            return;
        }
        setContentView(com.android.internal.R.layout.list_content_simple);
    }
}
