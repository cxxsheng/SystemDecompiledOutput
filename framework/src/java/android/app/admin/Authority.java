package android.app.admin;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public abstract class Authority implements android.os.Parcelable {
    protected Authority() {
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass();
    }

    public int hashCode() {
        return 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
