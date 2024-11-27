package android.window;

/* loaded from: classes4.dex */
public final class DisplayAreaInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.window.DisplayAreaInfo> CREATOR = new android.os.Parcelable.Creator<android.window.DisplayAreaInfo>() { // from class: android.window.DisplayAreaInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.DisplayAreaInfo createFromParcel(android.os.Parcel parcel) {
            return new android.window.DisplayAreaInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.DisplayAreaInfo[] newArray(int i) {
            return new android.window.DisplayAreaInfo[i];
        }
    };
    public final android.content.res.Configuration configuration;
    public final int displayId;
    public final int featureId;
    public int rootDisplayAreaId;
    public final android.window.WindowContainerToken token;

    public DisplayAreaInfo(android.window.WindowContainerToken windowContainerToken, int i, int i2) {
        this.configuration = new android.content.res.Configuration();
        this.rootDisplayAreaId = -1;
        this.token = windowContainerToken;
        this.displayId = i;
        this.featureId = i2;
    }

    private DisplayAreaInfo(android.os.Parcel parcel) {
        this.configuration = new android.content.res.Configuration();
        this.rootDisplayAreaId = -1;
        this.token = android.window.WindowContainerToken.CREATOR.createFromParcel(parcel);
        this.configuration.readFromParcel(parcel);
        this.displayId = parcel.readInt();
        this.featureId = parcel.readInt();
        this.rootDisplayAreaId = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        this.token.writeToParcel(parcel, i);
        this.configuration.writeToParcel(parcel, i);
        parcel.writeInt(this.displayId);
        parcel.writeInt(this.featureId);
        parcel.writeInt(this.rootDisplayAreaId);
    }

    public java.lang.String toString() {
        return "DisplayAreaInfo{token=" + this.token + " config=" + this.configuration + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
