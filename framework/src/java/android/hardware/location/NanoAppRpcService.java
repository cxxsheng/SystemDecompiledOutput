package android.hardware.location;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class NanoAppRpcService implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.location.NanoAppRpcService> CREATOR = new android.os.Parcelable.Creator<android.hardware.location.NanoAppRpcService>() { // from class: android.hardware.location.NanoAppRpcService.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.location.NanoAppRpcService createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.location.NanoAppRpcService(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.location.NanoAppRpcService[] newArray(int i) {
            return new android.hardware.location.NanoAppRpcService[i];
        }
    };
    private long mServiceId;
    private int mServiceVersion;

    public NanoAppRpcService(long j, int i) {
        this.mServiceId = j;
        this.mServiceVersion = i;
    }

    public long getId() {
        return this.mServiceId;
    }

    public int getVersion() {
        return this.mServiceVersion;
    }

    private int getMajorVersion() {
        return (this.mServiceVersion & (-16777216)) >>> 24;
    }

    private int getMinorVersion() {
        return (this.mServiceVersion & android.text.Spanned.SPAN_PRIORITY) >>> 16;
    }

    private int getPatchVersion() {
        return this.mServiceVersion & 65535;
    }

    private NanoAppRpcService(android.os.Parcel parcel) {
        this.mServiceId = parcel.readLong();
        this.mServiceVersion = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mServiceId);
        parcel.writeInt(this.mServiceVersion);
    }

    public java.lang.String toString() {
        return "NanoAppRpcService[Id = " + java.lang.Long.toHexString(this.mServiceId) + ", version = v" + getMajorVersion() + android.media.MediaMetrics.SEPARATOR + getMinorVersion() + android.media.MediaMetrics.SEPARATOR + getPatchVersion() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof android.hardware.location.NanoAppRpcService)) {
            return false;
        }
        android.hardware.location.NanoAppRpcService nanoAppRpcService = (android.hardware.location.NanoAppRpcService) obj;
        return nanoAppRpcService.getId() == this.mServiceId && nanoAppRpcService.getVersion() == this.mServiceVersion;
    }

    public int hashCode() {
        return (int) getId();
    }
}
