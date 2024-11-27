package android.media;

/* loaded from: classes2.dex */
public class ExternalRingtonesCursorWrapper extends android.database.CursorWrapper {
    private android.net.Uri mUri;

    public ExternalRingtonesCursorWrapper(android.database.Cursor cursor, android.net.Uri uri) {
        super(cursor);
        this.mUri = uri;
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public java.lang.String getString(int i) {
        if (i == 2) {
            return this.mUri.toString();
        }
        return super.getString(i);
    }
}
