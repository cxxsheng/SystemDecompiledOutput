package android.widget;

/* loaded from: classes4.dex */
public abstract class ResourceCursorTreeAdapter extends android.widget.CursorTreeAdapter {
    private int mChildLayout;
    private int mCollapsedGroupLayout;
    private int mExpandedGroupLayout;
    private android.view.LayoutInflater mInflater;
    private int mLastChildLayout;

    public ResourceCursorTreeAdapter(android.content.Context context, android.database.Cursor cursor, int i, int i2, int i3, int i4) {
        super(cursor, context);
        this.mCollapsedGroupLayout = i;
        this.mExpandedGroupLayout = i2;
        this.mChildLayout = i3;
        this.mLastChildLayout = i4;
        this.mInflater = (android.view.LayoutInflater) context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
    }

    public ResourceCursorTreeAdapter(android.content.Context context, android.database.Cursor cursor, int i, int i2, int i3) {
        this(context, cursor, i, i2, i3, i3);
    }

    public ResourceCursorTreeAdapter(android.content.Context context, android.database.Cursor cursor, int i, int i2) {
        this(context, cursor, i, i, i2, i2);
    }

    @Override // android.widget.CursorTreeAdapter
    public android.view.View newChildView(android.content.Context context, android.database.Cursor cursor, boolean z, android.view.ViewGroup viewGroup) {
        return this.mInflater.inflate(z ? this.mLastChildLayout : this.mChildLayout, viewGroup, false);
    }

    @Override // android.widget.CursorTreeAdapter
    public android.view.View newGroupView(android.content.Context context, android.database.Cursor cursor, boolean z, android.view.ViewGroup viewGroup) {
        return this.mInflater.inflate(z ? this.mExpandedGroupLayout : this.mCollapsedGroupLayout, viewGroup, false);
    }
}
