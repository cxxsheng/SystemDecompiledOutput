package android.database;

/* loaded from: classes.dex */
public interface Cursor extends java.io.Closeable {
    public static final int FIELD_TYPE_BLOB = 4;
    public static final int FIELD_TYPE_FLOAT = 2;
    public static final int FIELD_TYPE_INTEGER = 1;
    public static final int FIELD_TYPE_NULL = 0;
    public static final int FIELD_TYPE_STRING = 3;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FieldType {
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    void close();

    void copyStringToBuffer(int i, android.database.CharArrayBuffer charArrayBuffer);

    @java.lang.Deprecated
    void deactivate();

    byte[] getBlob(int i);

    int getColumnCount();

    int getColumnIndex(java.lang.String str);

    int getColumnIndexOrThrow(java.lang.String str) throws java.lang.IllegalArgumentException;

    java.lang.String getColumnName(int i);

    java.lang.String[] getColumnNames();

    int getCount();

    double getDouble(int i);

    android.os.Bundle getExtras();

    float getFloat(int i);

    int getInt(int i);

    long getLong(int i);

    android.net.Uri getNotificationUri();

    int getPosition();

    short getShort(int i);

    java.lang.String getString(int i);

    int getType(int i);

    boolean getWantsAllOnMoveCalls();

    boolean isAfterLast();

    boolean isBeforeFirst();

    boolean isClosed();

    boolean isFirst();

    boolean isLast();

    boolean isNull(int i);

    boolean move(int i);

    boolean moveToFirst();

    boolean moveToLast();

    boolean moveToNext();

    boolean moveToPosition(int i);

    boolean moveToPrevious();

    void registerContentObserver(android.database.ContentObserver contentObserver);

    void registerDataSetObserver(android.database.DataSetObserver dataSetObserver);

    @java.lang.Deprecated
    boolean requery();

    android.os.Bundle respond(android.os.Bundle bundle);

    void setExtras(android.os.Bundle bundle);

    void setNotificationUri(android.content.ContentResolver contentResolver, android.net.Uri uri);

    void unregisterContentObserver(android.database.ContentObserver contentObserver);

    void unregisterDataSetObserver(android.database.DataSetObserver dataSetObserver);

    default void setNotificationUris(android.content.ContentResolver contentResolver, java.util.List<android.net.Uri> list) {
        setNotificationUri(contentResolver, list.get(0));
    }

    default java.util.List<android.net.Uri> getNotificationUris() {
        android.net.Uri notificationUri = getNotificationUri();
        if (notificationUri == null) {
            return null;
        }
        return java.util.Arrays.asList(notificationUri);
    }
}
