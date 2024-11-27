package android.credentials.selection;

/* loaded from: classes.dex */
public abstract class ProviderData implements android.os.Parcelable {
    public static final java.lang.String EXTRA_DISABLED_PROVIDER_DATA_LIST = "android.credentials.selection.extra.DISABLED_PROVIDER_DATA_LIST";
    public static final java.lang.String EXTRA_ENABLED_PROVIDER_DATA_LIST = "android.credentials.selection.extra.ENABLED_PROVIDER_DATA_LIST";
    private final java.lang.String mProviderFlattenedComponentName;

    public ProviderData(java.lang.String str) {
        this.mProviderFlattenedComponentName = str;
    }

    public java.lang.String getProviderFlattenedComponentName() {
        return this.mProviderFlattenedComponentName;
    }

    protected ProviderData(android.os.Parcel parcel) {
        this.mProviderFlattenedComponentName = parcel.readString8();
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mProviderFlattenedComponentName);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString8(this.mProviderFlattenedComponentName);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
