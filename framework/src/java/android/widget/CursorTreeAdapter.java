package android.widget;

/* loaded from: classes4.dex */
public abstract class CursorTreeAdapter extends android.widget.BaseExpandableListAdapter implements android.widget.Filterable, android.widget.CursorFilter.CursorFilterClient {
    private boolean mAutoRequery;
    android.util.SparseArray<android.widget.CursorTreeAdapter.MyCursorHelper> mChildrenCursorHelpers;
    private android.content.Context mContext;
    android.widget.CursorFilter mCursorFilter;
    android.widget.FilterQueryProvider mFilterQueryProvider;
    android.widget.CursorTreeAdapter.MyCursorHelper mGroupCursorHelper;
    private android.os.Handler mHandler;

    protected abstract void bindChildView(android.view.View view, android.content.Context context, android.database.Cursor cursor, boolean z);

    protected abstract void bindGroupView(android.view.View view, android.content.Context context, android.database.Cursor cursor, boolean z);

    protected abstract android.database.Cursor getChildrenCursor(android.database.Cursor cursor);

    protected abstract android.view.View newChildView(android.content.Context context, android.database.Cursor cursor, boolean z, android.view.ViewGroup viewGroup);

    protected abstract android.view.View newGroupView(android.content.Context context, android.database.Cursor cursor, boolean z, android.view.ViewGroup viewGroup);

    public CursorTreeAdapter(android.database.Cursor cursor, android.content.Context context) {
        init(cursor, context, true);
    }

    public CursorTreeAdapter(android.database.Cursor cursor, android.content.Context context, boolean z) {
        init(cursor, context, z);
    }

    private void init(android.database.Cursor cursor, android.content.Context context, boolean z) {
        this.mContext = context;
        this.mHandler = new android.os.Handler();
        this.mAutoRequery = z;
        this.mGroupCursorHelper = new android.widget.CursorTreeAdapter.MyCursorHelper(cursor);
        this.mChildrenCursorHelpers = new android.util.SparseArray<>();
    }

    synchronized android.widget.CursorTreeAdapter.MyCursorHelper getChildrenCursorHelper(int i, boolean z) {
        android.widget.CursorTreeAdapter.MyCursorHelper myCursorHelper = this.mChildrenCursorHelpers.get(i);
        if (myCursorHelper == null) {
            if (this.mGroupCursorHelper.moveTo(i) == null) {
                return null;
            }
            android.widget.CursorTreeAdapter.MyCursorHelper myCursorHelper2 = new android.widget.CursorTreeAdapter.MyCursorHelper(getChildrenCursor(this.mGroupCursorHelper.getCursor()));
            this.mChildrenCursorHelpers.put(i, myCursorHelper2);
            myCursorHelper = myCursorHelper2;
        }
        return myCursorHelper;
    }

    public void setGroupCursor(android.database.Cursor cursor) {
        this.mGroupCursorHelper.changeCursor(cursor, false);
    }

    public void setChildrenCursor(int i, android.database.Cursor cursor) {
        getChildrenCursorHelper(i, false).changeCursor(cursor, false);
    }

    @Override // android.widget.ExpandableListAdapter
    public android.database.Cursor getChild(int i, int i2) {
        return getChildrenCursorHelper(i, true).moveTo(i2);
    }

    @Override // android.widget.ExpandableListAdapter
    public long getChildId(int i, int i2) {
        return getChildrenCursorHelper(i, true).getId(i2);
    }

    @Override // android.widget.ExpandableListAdapter
    public int getChildrenCount(int i) {
        android.widget.CursorTreeAdapter.MyCursorHelper childrenCursorHelper = getChildrenCursorHelper(i, true);
        if (!this.mGroupCursorHelper.isValid() || childrenCursorHelper == null) {
            return 0;
        }
        return childrenCursorHelper.getCount();
    }

    @Override // android.widget.ExpandableListAdapter
    public android.database.Cursor getGroup(int i) {
        return this.mGroupCursorHelper.moveTo(i);
    }

    @Override // android.widget.ExpandableListAdapter
    public int getGroupCount() {
        return this.mGroupCursorHelper.getCount();
    }

    @Override // android.widget.ExpandableListAdapter
    public long getGroupId(int i) {
        return this.mGroupCursorHelper.getId(i);
    }

    @Override // android.widget.ExpandableListAdapter
    public android.view.View getGroupView(int i, boolean z, android.view.View view, android.view.ViewGroup viewGroup) {
        android.database.Cursor moveTo = this.mGroupCursorHelper.moveTo(i);
        if (moveTo == null) {
            throw new java.lang.IllegalStateException("this should only be called when the cursor is valid");
        }
        if (view == null) {
            view = newGroupView(this.mContext, moveTo, z, viewGroup);
        }
        bindGroupView(view, this.mContext, moveTo, z);
        return view;
    }

    @Override // android.widget.ExpandableListAdapter
    public android.view.View getChildView(int i, int i2, boolean z, android.view.View view, android.view.ViewGroup viewGroup) {
        android.database.Cursor moveTo = getChildrenCursorHelper(i, true).moveTo(i2);
        if (moveTo == null) {
            throw new java.lang.IllegalStateException("this should only be called when the cursor is valid");
        }
        if (view == null) {
            view = newChildView(this.mContext, moveTo, z, viewGroup);
        }
        bindChildView(view, this.mContext, moveTo, z);
        return view;
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean isChildSelectable(int i, int i2) {
        return true;
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean hasStableIds() {
        return true;
    }

    private synchronized void releaseCursorHelpers() {
        for (int size = this.mChildrenCursorHelpers.size() - 1; size >= 0; size--) {
            this.mChildrenCursorHelpers.valueAt(size).deactivate();
        }
        this.mChildrenCursorHelpers.clear();
    }

    @Override // android.widget.BaseExpandableListAdapter
    public void notifyDataSetChanged() {
        notifyDataSetChanged(true);
    }

    public void notifyDataSetChanged(boolean z) {
        if (z) {
            releaseCursorHelpers();
        }
        super.notifyDataSetChanged();
    }

    @Override // android.widget.BaseExpandableListAdapter
    public void notifyDataSetInvalidated() {
        releaseCursorHelpers();
        super.notifyDataSetInvalidated();
    }

    @Override // android.widget.BaseExpandableListAdapter, android.widget.ExpandableListAdapter
    public void onGroupCollapsed(int i) {
        deactivateChildrenCursorHelper(i);
    }

    synchronized void deactivateChildrenCursorHelper(int i) {
        android.widget.CursorTreeAdapter.MyCursorHelper childrenCursorHelper = getChildrenCursorHelper(i, true);
        this.mChildrenCursorHelpers.remove(i);
        childrenCursorHelper.deactivate();
    }

    @Override // android.widget.CursorFilter.CursorFilterClient
    public java.lang.String convertToString(android.database.Cursor cursor) {
        return cursor == null ? "" : cursor.toString();
    }

    @Override // android.widget.CursorFilter.CursorFilterClient
    public android.database.Cursor runQueryOnBackgroundThread(java.lang.CharSequence charSequence) {
        if (this.mFilterQueryProvider != null) {
            return this.mFilterQueryProvider.runQuery(charSequence);
        }
        return this.mGroupCursorHelper.getCursor();
    }

    @Override // android.widget.Filterable
    public android.widget.Filter getFilter() {
        if (this.mCursorFilter == null) {
            this.mCursorFilter = new android.widget.CursorFilter(this);
        }
        return this.mCursorFilter;
    }

    public android.widget.FilterQueryProvider getFilterQueryProvider() {
        return this.mFilterQueryProvider;
    }

    public void setFilterQueryProvider(android.widget.FilterQueryProvider filterQueryProvider) {
        this.mFilterQueryProvider = filterQueryProvider;
    }

    @Override // android.widget.CursorFilter.CursorFilterClient
    public void changeCursor(android.database.Cursor cursor) {
        this.mGroupCursorHelper.changeCursor(cursor, true);
    }

    @Override // android.widget.CursorFilter.CursorFilterClient
    public android.database.Cursor getCursor() {
        return this.mGroupCursorHelper.getCursor();
    }

    class MyCursorHelper {
        private android.widget.CursorTreeAdapter.MyCursorHelper.MyContentObserver mContentObserver;
        private android.database.Cursor mCursor;
        private android.widget.CursorTreeAdapter.MyCursorHelper.MyDataSetObserver mDataSetObserver;
        private boolean mDataValid;
        private int mRowIDColumn;

        MyCursorHelper(android.database.Cursor cursor) {
            boolean z = cursor != null;
            this.mCursor = cursor;
            this.mDataValid = z;
            this.mRowIDColumn = z ? cursor.getColumnIndex("_id") : -1;
            this.mContentObserver = new android.widget.CursorTreeAdapter.MyCursorHelper.MyContentObserver();
            this.mDataSetObserver = new android.widget.CursorTreeAdapter.MyCursorHelper.MyDataSetObserver();
            if (z) {
                cursor.registerContentObserver(this.mContentObserver);
                cursor.registerDataSetObserver(this.mDataSetObserver);
            }
        }

        android.database.Cursor getCursor() {
            return this.mCursor;
        }

        int getCount() {
            if (this.mDataValid && this.mCursor != null) {
                return this.mCursor.getCount();
            }
            return 0;
        }

        long getId(int i) {
            if (this.mDataValid && this.mCursor != null && this.mCursor.moveToPosition(i)) {
                return this.mCursor.getLong(this.mRowIDColumn);
            }
            return 0L;
        }

        android.database.Cursor moveTo(int i) {
            if (this.mDataValid && this.mCursor != null && this.mCursor.moveToPosition(i)) {
                return this.mCursor;
            }
            return null;
        }

        void changeCursor(android.database.Cursor cursor, boolean z) {
            if (cursor == this.mCursor) {
                return;
            }
            deactivate();
            this.mCursor = cursor;
            if (cursor != null) {
                cursor.registerContentObserver(this.mContentObserver);
                cursor.registerDataSetObserver(this.mDataSetObserver);
                this.mRowIDColumn = cursor.getColumnIndex("_id");
                this.mDataValid = true;
                android.widget.CursorTreeAdapter.this.notifyDataSetChanged(z);
                return;
            }
            this.mRowIDColumn = -1;
            this.mDataValid = false;
            android.widget.CursorTreeAdapter.this.notifyDataSetInvalidated();
        }

        void deactivate() {
            if (this.mCursor == null) {
                return;
            }
            this.mCursor.unregisterContentObserver(this.mContentObserver);
            this.mCursor.unregisterDataSetObserver(this.mDataSetObserver);
            this.mCursor.close();
            this.mCursor = null;
        }

        boolean isValid() {
            return this.mDataValid && this.mCursor != null;
        }

        private class MyContentObserver extends android.database.ContentObserver {
            public MyContentObserver() {
                super(android.widget.CursorTreeAdapter.this.mHandler);
            }

            @Override // android.database.ContentObserver
            public boolean deliverSelfNotifications() {
                return true;
            }

            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                if (android.widget.CursorTreeAdapter.this.mAutoRequery && android.widget.CursorTreeAdapter.MyCursorHelper.this.mCursor != null && !android.widget.CursorTreeAdapter.MyCursorHelper.this.mCursor.isClosed()) {
                    android.widget.CursorTreeAdapter.MyCursorHelper.this.mDataValid = android.widget.CursorTreeAdapter.MyCursorHelper.this.mCursor.requery();
                }
            }
        }

        private class MyDataSetObserver extends android.database.DataSetObserver {
            private MyDataSetObserver() {
            }

            @Override // android.database.DataSetObserver
            public void onChanged() {
                android.widget.CursorTreeAdapter.MyCursorHelper.this.mDataValid = true;
                android.widget.CursorTreeAdapter.this.notifyDataSetChanged();
            }

            @Override // android.database.DataSetObserver
            public void onInvalidated() {
                android.widget.CursorTreeAdapter.MyCursorHelper.this.mDataValid = false;
                android.widget.CursorTreeAdapter.this.notifyDataSetInvalidated();
            }
        }
    }
}
