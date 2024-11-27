package android.widget;

/* loaded from: classes4.dex */
public abstract class CursorAdapter extends android.widget.BaseAdapter implements android.widget.Filterable, android.widget.CursorFilter.CursorFilterClient, android.widget.ThemedSpinnerAdapter {

    @java.lang.Deprecated
    public static final int FLAG_AUTO_REQUERY = 1;
    public static final int FLAG_REGISTER_CONTENT_OBSERVER = 2;
    protected boolean mAutoRequery;
    protected android.widget.CursorAdapter.ChangeObserver mChangeObserver;
    protected android.content.Context mContext;
    protected android.database.Cursor mCursor;
    protected android.widget.CursorFilter mCursorFilter;
    protected android.database.DataSetObserver mDataSetObserver;
    protected boolean mDataValid;
    protected android.content.Context mDropDownContext;
    protected android.widget.FilterQueryProvider mFilterQueryProvider;
    protected int mRowIDColumn;

    public abstract void bindView(android.view.View view, android.content.Context context, android.database.Cursor cursor);

    public abstract android.view.View newView(android.content.Context context, android.database.Cursor cursor, android.view.ViewGroup viewGroup);

    @java.lang.Deprecated
    public CursorAdapter(android.content.Context context, android.database.Cursor cursor) {
        init(context, cursor, 1);
    }

    public CursorAdapter(android.content.Context context, android.database.Cursor cursor, boolean z) {
        init(context, cursor, z ? 1 : 2);
    }

    public CursorAdapter(android.content.Context context, android.database.Cursor cursor, int i) {
        init(context, cursor, i);
    }

    @java.lang.Deprecated
    protected void init(android.content.Context context, android.database.Cursor cursor, boolean z) {
        init(context, cursor, z ? 1 : 2);
    }

    void init(android.content.Context context, android.database.Cursor cursor, int i) {
        if ((i & 1) == 1) {
            i |= 2;
            this.mAutoRequery = true;
        } else {
            this.mAutoRequery = false;
        }
        boolean z = cursor != null;
        this.mCursor = cursor;
        this.mDataValid = z;
        this.mContext = context;
        this.mRowIDColumn = z ? cursor.getColumnIndexOrThrow("_id") : -1;
        if ((i & 2) == 2) {
            this.mChangeObserver = new android.widget.CursorAdapter.ChangeObserver();
            this.mDataSetObserver = new android.widget.CursorAdapter.MyDataSetObserver();
        } else {
            this.mChangeObserver = null;
            this.mDataSetObserver = null;
        }
        if (z) {
            if (this.mChangeObserver != null) {
                cursor.registerContentObserver(this.mChangeObserver);
            }
            if (this.mDataSetObserver != null) {
                cursor.registerDataSetObserver(this.mDataSetObserver);
            }
        }
    }

    @Override // android.widget.ThemedSpinnerAdapter
    public void setDropDownViewTheme(android.content.res.Resources.Theme theme) {
        if (theme == null) {
            this.mDropDownContext = null;
        } else if (theme == this.mContext.getTheme()) {
            this.mDropDownContext = this.mContext;
        } else {
            this.mDropDownContext = new android.view.ContextThemeWrapper(this.mContext, theme);
        }
    }

    @Override // android.widget.ThemedSpinnerAdapter
    public android.content.res.Resources.Theme getDropDownViewTheme() {
        if (this.mDropDownContext == null) {
            return null;
        }
        return this.mDropDownContext.getTheme();
    }

    @Override // android.widget.CursorFilter.CursorFilterClient
    public android.database.Cursor getCursor() {
        return this.mCursor;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        if (this.mDataValid && this.mCursor != null) {
            return this.mCursor.getCount();
        }
        return 0;
    }

    @Override // android.widget.Adapter
    public java.lang.Object getItem(int i) {
        if (this.mDataValid && this.mCursor != null) {
            this.mCursor.moveToPosition(i);
            return this.mCursor;
        }
        return null;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        if (this.mDataValid && this.mCursor != null && this.mCursor.moveToPosition(i)) {
            return this.mCursor.getLong(this.mRowIDColumn);
        }
        return 0L;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public boolean hasStableIds() {
        return true;
    }

    @Override // android.widget.Adapter
    public android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
        if (!this.mDataValid) {
            throw new java.lang.IllegalStateException("this should only be called when the cursor is valid");
        }
        if (!this.mCursor.moveToPosition(i)) {
            throw new java.lang.IllegalStateException("couldn't move cursor to position " + i);
        }
        if (view == null) {
            view = newView(this.mContext, this.mCursor, viewGroup);
        }
        bindView(view, this.mContext, this.mCursor);
        return view;
    }

    @Override // android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public android.view.View getDropDownView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
        if (this.mDataValid) {
            android.content.Context context = this.mDropDownContext == null ? this.mContext : this.mDropDownContext;
            this.mCursor.moveToPosition(i);
            if (view == null) {
                view = newDropDownView(context, this.mCursor, viewGroup);
            }
            bindView(view, context, this.mCursor);
            return view;
        }
        return null;
    }

    public android.view.View newDropDownView(android.content.Context context, android.database.Cursor cursor, android.view.ViewGroup viewGroup) {
        return newView(context, cursor, viewGroup);
    }

    @Override // android.widget.CursorFilter.CursorFilterClient
    public void changeCursor(android.database.Cursor cursor) {
        android.database.Cursor swapCursor = swapCursor(cursor);
        if (swapCursor != null) {
            swapCursor.close();
        }
    }

    public android.database.Cursor swapCursor(android.database.Cursor cursor) {
        if (cursor == this.mCursor) {
            return null;
        }
        android.database.Cursor cursor2 = this.mCursor;
        if (cursor2 != null) {
            if (this.mChangeObserver != null) {
                cursor2.unregisterContentObserver(this.mChangeObserver);
            }
            if (this.mDataSetObserver != null) {
                cursor2.unregisterDataSetObserver(this.mDataSetObserver);
            }
        }
        this.mCursor = cursor;
        if (cursor != null) {
            if (this.mChangeObserver != null) {
                cursor.registerContentObserver(this.mChangeObserver);
            }
            if (this.mDataSetObserver != null) {
                cursor.registerDataSetObserver(this.mDataSetObserver);
            }
            this.mRowIDColumn = cursor.getColumnIndexOrThrow("_id");
            this.mDataValid = true;
            notifyDataSetChanged();
        } else {
            this.mRowIDColumn = -1;
            this.mDataValid = false;
            notifyDataSetInvalidated();
        }
        return cursor2;
    }

    @Override // android.widget.CursorFilter.CursorFilterClient
    public java.lang.CharSequence convertToString(android.database.Cursor cursor) {
        return cursor == null ? "" : cursor.toString();
    }

    @Override // android.widget.CursorFilter.CursorFilterClient
    public android.database.Cursor runQueryOnBackgroundThread(java.lang.CharSequence charSequence) {
        if (this.mFilterQueryProvider != null) {
            return this.mFilterQueryProvider.runQuery(charSequence);
        }
        return this.mCursor;
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

    protected void onContentChanged() {
        if (this.mAutoRequery && this.mCursor != null && !this.mCursor.isClosed()) {
            this.mDataValid = this.mCursor.requery();
        }
    }

    private class ChangeObserver extends android.database.ContentObserver {
        public ChangeObserver() {
            super(new android.os.Handler());
        }

        @Override // android.database.ContentObserver
        public boolean deliverSelfNotifications() {
            return true;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            android.widget.CursorAdapter.this.onContentChanged();
        }
    }

    private class MyDataSetObserver extends android.database.DataSetObserver {
        private MyDataSetObserver() {
        }

        @Override // android.database.DataSetObserver
        public void onChanged() {
            android.widget.CursorAdapter.this.mDataValid = true;
            android.widget.CursorAdapter.this.notifyDataSetChanged();
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() {
            android.widget.CursorAdapter.this.mDataValid = false;
            android.widget.CursorAdapter.this.notifyDataSetInvalidated();
        }
    }
}
