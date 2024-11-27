package android.app.compat;

/* loaded from: classes.dex */
final class ChangeIdStateQuery {
    static final int QUERY_BY_PACKAGE_NAME = 0;
    static final int QUERY_BY_UID = 1;
    public long changeId;
    public java.lang.String packageName;
    public int type;
    public int uid;
    public int userId;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface QueryType {
    }

    private ChangeIdStateQuery(int i, long j, java.lang.String str, int i2, int i3) {
        this.type = i;
        this.changeId = j;
        this.packageName = str;
        this.uid = i2;
        this.userId = i3;
    }

    static android.app.compat.ChangeIdStateQuery byPackageName(long j, java.lang.String str, int i) {
        return new android.app.compat.ChangeIdStateQuery(0, j, str, 0, i);
    }

    static android.app.compat.ChangeIdStateQuery byUid(long j, int i) {
        return new android.app.compat.ChangeIdStateQuery(1, j, null, i, 0);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.app.compat.ChangeIdStateQuery)) {
            return false;
        }
        android.app.compat.ChangeIdStateQuery changeIdStateQuery = (android.app.compat.ChangeIdStateQuery) obj;
        if (this.type == changeIdStateQuery.type && this.changeId == changeIdStateQuery.changeId && java.util.Objects.equals(this.packageName, changeIdStateQuery.packageName) && this.uid == changeIdStateQuery.uid && this.userId == changeIdStateQuery.userId) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = ((this.type + 31) * 31) + ((int) (this.changeId ^ (this.changeId >>> 32)));
        if (this.packageName != null) {
            i = (i * 31) + this.packageName.hashCode();
        }
        return (((i * 31) + this.uid) * 31) + this.userId;
    }
}
