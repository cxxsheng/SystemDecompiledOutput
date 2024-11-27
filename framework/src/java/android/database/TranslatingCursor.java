package android.database;

/* loaded from: classes.dex */
public class TranslatingCursor extends android.database.CrossProcessCursorWrapper {
    private final int mAuxiliaryColumnIndex;
    private final android.database.TranslatingCursor.Config mConfig;
    private final boolean mDropLast;
    private final android.util.ArraySet<java.lang.Integer> mTranslateColumnIndices;
    private final android.database.TranslatingCursor.Translator mTranslator;

    public interface Translator {
        java.lang.String translate(java.lang.String str, int i, java.lang.String str2, android.database.Cursor cursor);
    }

    public static class Config {
        public final java.lang.String auxiliaryColumn;
        public final android.net.Uri baseUri;
        public final java.lang.String[] translateColumns;

        public Config(android.net.Uri uri, java.lang.String str, java.lang.String... strArr) {
            this.baseUri = uri;
            this.auxiliaryColumn = str;
            this.translateColumns = strArr;
        }
    }

    public TranslatingCursor(android.database.Cursor cursor, android.database.TranslatingCursor.Config config, android.database.TranslatingCursor.Translator translator, boolean z) {
        super(cursor);
        this.mConfig = (android.database.TranslatingCursor.Config) java.util.Objects.requireNonNull(config);
        this.mTranslator = (android.database.TranslatingCursor.Translator) java.util.Objects.requireNonNull(translator);
        this.mDropLast = z;
        this.mAuxiliaryColumnIndex = cursor.getColumnIndexOrThrow(config.auxiliaryColumn);
        this.mTranslateColumnIndices = new android.util.ArraySet<>();
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            if (com.android.internal.util.ArrayUtils.contains(config.translateColumns, cursor.getColumnName(i))) {
                this.mTranslateColumnIndices.add(java.lang.Integer.valueOf(i));
            }
        }
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public int getColumnCount() {
        if (this.mDropLast) {
            return super.getColumnCount() - 1;
        }
        return super.getColumnCount();
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public java.lang.String[] getColumnNames() {
        if (this.mDropLast) {
            return (java.lang.String[]) java.util.Arrays.copyOfRange(super.getColumnNames(), 0, super.getColumnCount() - 1);
        }
        return super.getColumnNames();
    }

    public static android.database.Cursor query(android.database.TranslatingCursor.Config config, android.database.TranslatingCursor.Translator translator, android.database.sqlite.SQLiteQueryBuilder sQLiteQueryBuilder, android.database.sqlite.SQLiteDatabase sQLiteDatabase, java.lang.String[] strArr, java.lang.String str, java.lang.String[] strArr2, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, android.os.CancellationSignal cancellationSignal) {
        java.lang.String[] strArr3;
        boolean z = com.android.internal.util.ArrayUtils.isEmpty(strArr) || com.android.internal.util.ArrayUtils.contains(strArr, config.auxiliaryColumn);
        if (!(com.android.internal.util.ArrayUtils.isEmpty(strArr) || com.android.internal.util.ArrayUtils.containsAny(strArr, config.translateColumns))) {
            return sQLiteQueryBuilder.query(sQLiteDatabase, strArr, str, strArr2, str2, str3, str4, str5, cancellationSignal);
        }
        if (z) {
            strArr3 = strArr;
        } else {
            strArr3 = (java.lang.String[]) com.android.internal.util.ArrayUtils.appendElement(java.lang.String.class, strArr, config.auxiliaryColumn);
        }
        return new android.database.TranslatingCursor(sQLiteQueryBuilder.query(sQLiteDatabase, strArr3, str, strArr2, str2, str3, str4), config, translator, !z);
    }

    @Override // android.database.CrossProcessCursorWrapper, android.database.CrossProcessCursor
    public void fillWindow(int i, android.database.CursorWindow cursorWindow) {
        android.database.DatabaseUtils.cursorFillWindow(this, i, cursorWindow);
    }

    @Override // android.database.CrossProcessCursorWrapper, android.database.CrossProcessCursor
    public android.database.CursorWindow getWindow() {
        return null;
    }

    @Override // android.database.CursorWrapper
    public android.database.Cursor getWrappedCursor() {
        throw new java.lang.UnsupportedOperationException("Returning underlying cursor risks leaking data");
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public double getDouble(int i) {
        if (com.android.internal.util.ArrayUtils.contains(this.mTranslateColumnIndices, java.lang.Integer.valueOf(i))) {
            throw new java.lang.IllegalArgumentException();
        }
        return super.getDouble(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public float getFloat(int i) {
        if (com.android.internal.util.ArrayUtils.contains(this.mTranslateColumnIndices, java.lang.Integer.valueOf(i))) {
            throw new java.lang.IllegalArgumentException();
        }
        return super.getFloat(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public int getInt(int i) {
        if (com.android.internal.util.ArrayUtils.contains(this.mTranslateColumnIndices, java.lang.Integer.valueOf(i))) {
            throw new java.lang.IllegalArgumentException();
        }
        return super.getInt(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public long getLong(int i) {
        if (com.android.internal.util.ArrayUtils.contains(this.mTranslateColumnIndices, java.lang.Integer.valueOf(i))) {
            throw new java.lang.IllegalArgumentException();
        }
        return super.getLong(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public short getShort(int i) {
        if (com.android.internal.util.ArrayUtils.contains(this.mTranslateColumnIndices, java.lang.Integer.valueOf(i))) {
            throw new java.lang.IllegalArgumentException();
        }
        return super.getShort(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public java.lang.String getString(int i) {
        if (com.android.internal.util.ArrayUtils.contains(this.mTranslateColumnIndices, java.lang.Integer.valueOf(i))) {
            return this.mTranslator.translate(super.getString(i), this.mAuxiliaryColumnIndex, getColumnName(i), this);
        }
        return super.getString(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public void copyStringToBuffer(int i, android.database.CharArrayBuffer charArrayBuffer) {
        if (com.android.internal.util.ArrayUtils.contains(this.mTranslateColumnIndices, java.lang.Integer.valueOf(i))) {
            throw new java.lang.IllegalArgumentException();
        }
        super.copyStringToBuffer(i, charArrayBuffer);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public byte[] getBlob(int i) {
        if (com.android.internal.util.ArrayUtils.contains(this.mTranslateColumnIndices, java.lang.Integer.valueOf(i))) {
            throw new java.lang.IllegalArgumentException();
        }
        return super.getBlob(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public int getType(int i) {
        if (com.android.internal.util.ArrayUtils.contains(this.mTranslateColumnIndices, java.lang.Integer.valueOf(i))) {
            return 3;
        }
        return super.getType(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public boolean isNull(int i) {
        if (com.android.internal.util.ArrayUtils.contains(this.mTranslateColumnIndices, java.lang.Integer.valueOf(i))) {
            return getString(i) == null;
        }
        return super.isNull(i);
    }
}
