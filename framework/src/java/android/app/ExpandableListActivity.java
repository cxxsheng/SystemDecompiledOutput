package android.app;

@java.lang.Deprecated
/* loaded from: classes.dex */
public class ExpandableListActivity extends android.app.Activity implements android.view.View.OnCreateContextMenuListener, android.widget.ExpandableListView.OnChildClickListener, android.widget.ExpandableListView.OnGroupCollapseListener, android.widget.ExpandableListView.OnGroupExpandListener {
    android.widget.ExpandableListAdapter mAdapter;
    boolean mFinishedStart = false;
    android.widget.ExpandableListView mList;

    @Override // android.app.Activity, android.view.View.OnCreateContextMenuListener
    public void onCreateContextMenu(android.view.ContextMenu contextMenu, android.view.View view, android.view.ContextMenu.ContextMenuInfo contextMenuInfo) {
    }

    @Override // android.widget.ExpandableListView.OnChildClickListener
    public boolean onChildClick(android.widget.ExpandableListView expandableListView, android.view.View view, int i, int i2, long j) {
        return false;
    }

    @Override // android.widget.ExpandableListView.OnGroupCollapseListener
    public void onGroupCollapse(int i) {
    }

    @Override // android.widget.ExpandableListView.OnGroupExpandListener
    public void onGroupExpand(int i) {
    }

    @Override // android.app.Activity
    protected void onRestoreInstanceState(android.os.Bundle bundle) {
        ensureList();
        super.onRestoreInstanceState(bundle);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onContentChanged() {
        super.onContentChanged();
        android.view.View findViewById = findViewById(16908292);
        this.mList = (android.widget.ExpandableListView) findViewById(16908298);
        if (this.mList == null) {
            throw new java.lang.RuntimeException("Your content must have a ExpandableListView whose id attribute is 'android.R.id.list'");
        }
        if (findViewById != null) {
            this.mList.setEmptyView(findViewById);
        }
        this.mList.setOnChildClickListener(this);
        this.mList.setOnGroupExpandListener(this);
        this.mList.setOnGroupCollapseListener(this);
        if (this.mFinishedStart) {
            setListAdapter(this.mAdapter);
        }
        this.mFinishedStart = true;
    }

    public void setListAdapter(android.widget.ExpandableListAdapter expandableListAdapter) {
        synchronized (this) {
            ensureList();
            this.mAdapter = expandableListAdapter;
            this.mList.setAdapter(expandableListAdapter);
        }
    }

    public android.widget.ExpandableListView getExpandableListView() {
        ensureList();
        return this.mList;
    }

    public android.widget.ExpandableListAdapter getExpandableListAdapter() {
        return this.mAdapter;
    }

    private void ensureList() {
        if (this.mList != null) {
            return;
        }
        setContentView(17367041);
    }

    public long getSelectedId() {
        return this.mList.getSelectedId();
    }

    public long getSelectedPosition() {
        return this.mList.getSelectedPosition();
    }

    public boolean setSelectedChild(int i, int i2, boolean z) {
        return this.mList.setSelectedChild(i, i2, z);
    }

    public void setSelectedGroup(int i) {
        this.mList.setSelectedGroup(i);
    }
}
