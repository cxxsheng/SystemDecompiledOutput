package android.os;

/* loaded from: classes3.dex */
public interface Parcelable {
    public static final int CONTENTS_FILE_DESCRIPTOR = 1;
    public static final int PARCELABLE_ELIDE_DUPLICATES = 2;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public static final int PARCELABLE_STABILITY_LOCAL = 0;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public static final int PARCELABLE_STABILITY_VINTF = 1;
    public static final int PARCELABLE_WRITE_RETURN_VALUE = 1;

    public interface ClassLoaderCreator<T> extends android.os.Parcelable.Creator<T> {
        T createFromParcel(android.os.Parcel parcel, java.lang.ClassLoader classLoader);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ContentsFlags {
    }

    public interface Creator<T> {
        T createFromParcel(android.os.Parcel parcel);

        T[] newArray(int i);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Stability {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface WriteFlags {
    }

    int describeContents();

    void writeToParcel(android.os.Parcel parcel, int i);

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    default int getStability() {
        return 0;
    }
}
