package android.content.pm;

/* loaded from: classes.dex */
public class FeatureInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.FeatureInfo> CREATOR = new android.os.Parcelable.Creator<android.content.pm.FeatureInfo>() { // from class: android.content.pm.FeatureInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.FeatureInfo createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.FeatureInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.FeatureInfo[] newArray(int i) {
            return new android.content.pm.FeatureInfo[i];
        }
    };
    public static final int FLAG_REQUIRED = 1;
    public static final int GL_ES_VERSION_UNDEFINED = 0;
    public int flags;
    public java.lang.String name;
    public int reqGlEsVersion;
    public int version;

    public FeatureInfo() {
    }

    public FeatureInfo(android.content.pm.FeatureInfo featureInfo) {
        this.name = featureInfo.name;
        this.version = featureInfo.version;
        this.reqGlEsVersion = featureInfo.reqGlEsVersion;
        this.flags = featureInfo.flags;
    }

    public java.lang.String toString() {
        if (this.name != null) {
            return "FeatureInfo{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " " + this.name + " v=" + this.version + " fl=0x" + java.lang.Integer.toHexString(this.flags) + "}";
        }
        return "FeatureInfo{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " glEsVers=" + getGlEsVersion() + " fl=0x" + java.lang.Integer.toHexString(this.flags) + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString8(this.name);
        parcel.writeInt(this.version);
        parcel.writeInt(this.reqGlEsVersion);
        parcel.writeInt(this.flags);
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        if (this.name != null) {
            protoOutputStream.write(1138166333441L, this.name);
        }
        protoOutputStream.write(1120986464258L, this.version);
        protoOutputStream.write(1138166333443L, getGlEsVersion());
        protoOutputStream.write(1120986464260L, this.flags);
        protoOutputStream.end(start);
    }

    private FeatureInfo(android.os.Parcel parcel) {
        this.name = parcel.readString8();
        this.version = parcel.readInt();
        this.reqGlEsVersion = parcel.readInt();
        this.flags = parcel.readInt();
    }

    public java.lang.String getGlEsVersion() {
        return java.lang.String.valueOf((this.reqGlEsVersion & (-65536)) >> 16) + android.media.MediaMetrics.SEPARATOR + java.lang.String.valueOf(this.reqGlEsVersion & 65535);
    }
}
