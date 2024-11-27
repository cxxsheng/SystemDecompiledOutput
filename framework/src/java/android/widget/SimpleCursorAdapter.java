package android.widget;

/* loaded from: classes4.dex */
public class SimpleCursorAdapter extends android.widget.ResourceCursorAdapter {
    private android.widget.SimpleCursorAdapter.CursorToStringConverter mCursorToStringConverter;
    protected int[] mFrom;
    java.lang.String[] mOriginalFrom;
    private int mStringConversionColumn;
    protected int[] mTo;
    private android.widget.SimpleCursorAdapter.ViewBinder mViewBinder;

    public interface CursorToStringConverter {
        java.lang.CharSequence convertToString(android.database.Cursor cursor);
    }

    public interface ViewBinder {
        boolean setViewValue(android.view.View view, android.database.Cursor cursor, int i);
    }

    @java.lang.Deprecated
    public SimpleCursorAdapter(android.content.Context context, int i, android.database.Cursor cursor, java.lang.String[] strArr, int[] iArr) {
        super(context, i, cursor);
        this.mStringConversionColumn = -1;
        this.mTo = iArr;
        this.mOriginalFrom = strArr;
        findColumns(cursor, strArr);
    }

    public SimpleCursorAdapter(android.content.Context context, int i, android.database.Cursor cursor, java.lang.String[] strArr, int[] iArr, int i2) {
        super(context, i, cursor, i2);
        this.mStringConversionColumn = -1;
        this.mTo = iArr;
        this.mOriginalFrom = strArr;
        findColumns(cursor, strArr);
    }

    @Override // android.widget.CursorAdapter
    public void bindView(android.view.View view, android.content.Context context, android.database.Cursor cursor) {
        boolean z;
        android.widget.SimpleCursorAdapter.ViewBinder viewBinder = this.mViewBinder;
        int length = this.mTo.length;
        int[] iArr = this.mFrom;
        int[] iArr2 = this.mTo;
        for (int i = 0; i < length; i++) {
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
                        throw new java.lang.IllegalStateException(findViewById.getClass().getName() + " is not a  view that can be bounds by this SimpleCursorAdapter");
                    }
                }
            }
        }
    }

    public android.widget.SimpleCursorAdapter.ViewBinder getViewBinder() {
        return this.mViewBinder;
    }

    public void setViewBinder(android.widget.SimpleCursorAdapter.ViewBinder viewBinder) {
        this.mViewBinder = viewBinder;
    }

    public void setViewImage(android.widget.ImageView imageView, java.lang.String str) {
        try {
            imageView.setImageResource(java.lang.Integer.parseInt(str));
        } catch (java.lang.NumberFormatException e) {
            imageView.setImageURI(android.net.Uri.parse(str));
        }
    }

    public void setViewText(android.widget.TextView textView, java.lang.String str) {
        textView.lambda$setTextAsync$0(str);
    }

    public int getStringConversionColumn() {
        return this.mStringConversionColumn;
    }

    public void setStringConversionColumn(int i) {
        this.mStringConversionColumn = i;
    }

    public android.widget.SimpleCursorAdapter.CursorToStringConverter getCursorToStringConverter() {
        return this.mCursorToStringConverter;
    }

    public void setCursorToStringConverter(android.widget.SimpleCursorAdapter.CursorToStringConverter cursorToStringConverter) {
        this.mCursorToStringConverter = cursorToStringConverter;
    }

    @Override // android.widget.CursorAdapter, android.widget.CursorFilter.CursorFilterClient
    public java.lang.CharSequence convertToString(android.database.Cursor cursor) {
        if (this.mCursorToStringConverter != null) {
            return this.mCursorToStringConverter.convertToString(cursor);
        }
        if (this.mStringConversionColumn > -1) {
            return cursor.getString(this.mStringConversionColumn);
        }
        return super.convertToString(cursor);
    }

    private void findColumns(android.database.Cursor cursor, java.lang.String[] strArr) {
        if (cursor != null) {
            int length = strArr.length;
            if (this.mFrom == null || this.mFrom.length != length) {
                this.mFrom = new int[length];
            }
            for (int i = 0; i < length; i++) {
                this.mFrom[i] = cursor.getColumnIndexOrThrow(strArr[i]);
            }
            return;
        }
        this.mFrom = null;
    }

    @Override // android.widget.CursorAdapter
    public android.database.Cursor swapCursor(android.database.Cursor cursor) {
        findColumns(cursor, this.mOriginalFrom);
        return super.swapCursor(cursor);
    }

    public void changeCursorAndColumns(android.database.Cursor cursor, java.lang.String[] strArr, int[] iArr) {
        this.mOriginalFrom = strArr;
        this.mTo = iArr;
        findColumns(cursor, this.mOriginalFrom);
        super.changeCursor(cursor);
    }
}
