package android.app;

/* loaded from: classes.dex */
public class ContentProviderHolder implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.ContentProviderHolder> CREATOR = new android.os.Parcelable.Creator<android.app.ContentProviderHolder>() { // from class: android.app.ContentProviderHolder.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.ContentProviderHolder createFromParcel(android.os.Parcel parcel) {
            return new android.app.ContentProviderHolder(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.ContentProviderHolder[] newArray(int i) {
            return new android.app.ContentProviderHolder[i];
        }
    };
    public android.os.IBinder connection;
    public final android.content.pm.ProviderInfo info;
    public boolean mLocal;
    public boolean noReleaseNeeded;
    public android.content.IContentProvider provider;

    public ContentProviderHolder(android.content.pm.ProviderInfo providerInfo) {
        this.info = providerInfo;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        this.info.writeToParcel(parcel, 0);
        if (this.provider != null) {
            parcel.writeStrongBinder(this.provider.asBinder());
        } else {
            parcel.writeStrongBinder(null);
        }
        parcel.writeStrongBinder(this.connection);
        parcel.writeInt(this.noReleaseNeeded ? 1 : 0);
        parcel.writeInt(this.mLocal ? 1 : 0);
    }

    private ContentProviderHolder(android.os.Parcel parcel) {
        this.info = android.content.pm.ProviderInfo.CREATOR.createFromParcel(parcel);
        this.provider = android.content.ContentProviderNative.asInterface(parcel.readStrongBinder());
        this.connection = parcel.readStrongBinder();
        this.noReleaseNeeded = parcel.readInt() != 0;
        this.mLocal = parcel.readInt() != 0;
    }
}
