package android.widget;

/* loaded from: classes4.dex */
public abstract class SimpleCursorTreeAdapter extends android.widget.ResourceCursorTreeAdapter {
    private int[] mChildFrom;
    private java.lang.String[] mChildFromNames;
    private int[] mChildTo;
    private int[] mGroupFrom;
    private java.lang.String[] mGroupFromNames;
    private int[] mGroupTo;
    private android.widget.SimpleCursorTreeAdapter.ViewBinder mViewBinder;

    public interface ViewBinder {
        boolean setViewValue(android.view.View view, android.database.Cursor cursor, int i);
    }

    public SimpleCursorTreeAdapter(android.content.Context context, android.database.Cursor cursor, int i, int i2, java.lang.String[] strArr, int[] iArr, int i3, int i4, java.lang.String[] strArr2, int[] iArr2) {
        super(context, cursor, i, i2, i3, i4);
        init(strArr, iArr, strArr2, iArr2);
    }

    public SimpleCursorTreeAdapter(android.content.Context context, android.database.Cursor cursor, int i, int i2, java.lang.String[] strArr, int[] iArr, int i3, java.lang.String[] strArr2, int[] iArr2) {
        super(context, cursor, i, i2, i3);
        init(strArr, iArr, strArr2, iArr2);
    }

    public SimpleCursorTreeAdapter(android.content.Context context, android.database.Cursor cursor, int i, java.lang.String[] strArr, int[] iArr, int i2, java.lang.String[] strArr2, int[] iArr2) {
        super(context, cursor, i, i2);
        init(strArr, iArr, strArr2, iArr2);
    }

    private void init(java.lang.String[] strArr, int[] iArr, java.lang.String[] strArr2, int[] iArr2) {
        this.mGroupFromNames = strArr;
        this.mGroupTo = iArr;
        this.mChildFromNames = strArr2;
        this.mChildTo = iArr2;
    }

    public android.widget.SimpleCursorTreeAdapter.ViewBinder getViewBinder() {
        return this.mViewBinder;
    }

    public void setViewBinder(android.widget.SimpleCursorTreeAdapter.ViewBinder viewBinder) {
        this.mViewBinder = viewBinder;
    }

    private void bindView(android.view.View view, android.content.Context context, android.database.Cursor cursor, int[] iArr, int[] iArr2) {
        boolean z;
        android.widget.SimpleCursorTreeAdapter.ViewBinder viewBinder = this.mViewBinder;
        for (int i = 0; i < iArr2.length; i++) {
            android.view.View findViewById = view.findViewById(iArr2[i]);
            if (findViewById != null) {
                if (viewBinder == null) {
                    z = false;
                } else {
                    z = viewBinder.setViewValue(findViewById, cursor, iArr[i]);
                }
                if (z) {
                    continue;
                } else {
                    java.lang.String string = cursor.getString(iArr[i]);
                    if (string == null) {
                        string = "";
                    }
                    if (findViewById instanceof android.widget.TextView) {
                        setViewText((android.widget.TextView) findViewById, string);
                    } else if (findViewById instanceof android.widget.ImageView) {
                        setViewImage((android.widget.ImageView) findViewById, string);
                    } else {
                        throw new java.lang.IllegalStateException("SimpleCursorTreeAdapter can bind values only to TextView and ImageView!");
                    }
                }
            }
        }
    }

    private void initFromColumns(android.database.Cursor cursor, java.lang.String[] strArr, int[] iArr) {
        for (int length = strArr.length - 1; length >= 0; length--) {
            iArr[length] = cursor.getColumnIndexOrThrow(strArr[length]);
        }
    }

    @Override // android.widget.CursorTreeAdapter
    protected void bindChildView(android.view.View view, android.content.Context context, android.database.Cursor cursor, boolean z) {
        if (this.mChildFrom == null) {
            this.mChildFrom = new int[this.mChildFromNames.length];
            initFromColumns(cursor, this.mChildFromNames, this.mChildFrom);
        }
        bindView(view, context, cursor, this.mChildFrom, this.mChildTo);
    }

    @Override // android.widget.CursorTreeAdapter
    protected void bindGroupView(android.view.View view, android.content.Context context, android.database.Cursor cursor, boolean z) {
        if (this.mGroupFrom == null) {
            this.mGroupFrom = new int[this.mGroupFromNames.length];
            initFromColumns(cursor, this.mGroupFromNames, this.mGroupFrom);
        }
        bindView(view, context, cursor, this.mGroupFrom, this.mGroupTo);
    }

    protected void setViewImage(android.widget.ImageView imageView, java.lang.String str) {
        try {
            imageView.setImageResource(java.lang.Integer.parseInt(str));
        } catch (java.lang.NumberFormatException e) {
            imageView.setImageURI(android.net.Uri.parse(str));
        }
    }

    public void setViewText(android.widget.TextView textView, java.lang.String str) {
        textView.lambda$setTextAsync$0(str);
    }
}
