package android.content;

/* loaded from: classes.dex */
public final class Entity {
    private final java.util.ArrayList<android.content.Entity.NamedContentValues> mSubValues = new java.util.ArrayList<>();
    private final android.content.ContentValues mValues;

    public Entity(android.content.ContentValues contentValues) {
        this.mValues = contentValues;
    }

    public android.content.ContentValues getEntityValues() {
        return this.mValues;
    }

    public java.util.ArrayList<android.content.Entity.NamedContentValues> getSubValues() {
        return this.mSubValues;
    }

    public void addSubValue(android.net.Uri uri, android.content.ContentValues contentValues) {
        this.mSubValues.add(new android.content.Entity.NamedContentValues(uri, contentValues));
    }

    public static class NamedContentValues {
        public final android.net.Uri uri;
        public final android.content.ContentValues values;

        public NamedContentValues(android.net.Uri uri, android.content.ContentValues contentValues) {
            this.uri = uri;
            this.values = contentValues;
        }
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Entity: ").append(getEntityValues());
        java.util.Iterator<android.content.Entity.NamedContentValues> it = getSubValues().iterator();
        while (it.hasNext()) {
            android.content.Entity.NamedContentValues next = it.next();
            sb.append("\n  ").append(next.uri);
            sb.append("\n  -> ").append(next.values);
        }
        return sb.toString();
    }
}
