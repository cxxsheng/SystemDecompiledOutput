package android.widget;

/* loaded from: classes4.dex */
public abstract class ResourceCursorAdapter extends android.widget.CursorAdapter {
    private android.view.LayoutInflater mDropDownInflater;
    private int mDropDownLayout;
    private android.view.LayoutInflater mInflater;
    private int mLayout;

    @java.lang.Deprecated
    public ResourceCursorAdapter(android.content.Context context, int i, android.database.Cursor cursor) {
        super(context, cursor);
        this.mDropDownLayout = i;
        this.mLayout = i;
        this.mInflater = (android.view.LayoutInflater) context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
        this.mDropDownInflater = this.mInflater;
    }

    public ResourceCursorAdapter(android.content.Context context, int i, android.database.Cursor cursor, boolean z) {
        super(context, cursor, z);
        this.mDropDownLayout = i;
        this.mLayout = i;
        this.mInflater = (android.view.LayoutInflater) context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
        this.mDropDownInflater = this.mInflater;
    }

    public ResourceCursorAdapter(android.content.Context context, int i, android.database.Cursor cursor, int i2) {
        super(context, cursor, i2);
        this.mDropDownLayout = i;
        this.mLayout = i;
        this.mInflater = (android.view.LayoutInflater) context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
        this.mDropDownInflater = this.mInflater;
    }

    @Override // android.widget.CursorAdapter, android.widget.ThemedSpinnerAdapter
    public void setDropDownViewTheme(android.content.res.Resources.Theme theme) {
        super.setDropDownViewTheme(theme);
        if (theme == null) {
            this.mDropDownInflater = null;
        } else if (theme == this.mInflater.getContext().getTheme()) {
            this.mDropDownInflater = this.mInflater;
        } else {
            this.mDropDownInflater = android.view.LayoutInflater.from(new android.view.ContextThemeWrapper(this.mContext, theme));
        }
    }

    @Override // android.widget.CursorAdapter
    public android.view.View newView(android.content.Context context, android.database.Cursor cursor, android.view.ViewGroup viewGroup) {
        return this.mInflater.inflate(this.mLayout, viewGroup, false);
    }

    @Override // android.widget.CursorAdapter
    public android.view.View newDropDownView(android.content.Context context, android.database.Cursor cursor, android.view.ViewGroup viewGroup) {
        return this.mDropDownInflater.inflate(this.mDropDownLayout, viewGroup, false);
    }

    public void setViewResource(int i) {
        this.mLayout = i;
    }

    public void setDropDownViewResource(int i) {
        this.mDropDownLayout = i;
    }
}
