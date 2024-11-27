package android.hardware.hdmi;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class HdmiPortInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.hdmi.HdmiPortInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.hdmi.HdmiPortInfo>() { // from class: android.hardware.hdmi.HdmiPortInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.hdmi.HdmiPortInfo createFromParcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            boolean z = parcel.readInt() == 1;
            return new android.hardware.hdmi.HdmiPortInfo.Builder(readInt, readInt2, readInt3).setCecSupported(z).setArcSupported(parcel.readInt() == 1).setEarcSupported(parcel.readInt() == 1).setMhlSupported(parcel.readInt() == 1).build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.hdmi.HdmiPortInfo[] newArray(int i) {
            return new android.hardware.hdmi.HdmiPortInfo[i];
        }
    };
    public static final int PORT_INPUT = 0;
    public static final int PORT_OUTPUT = 1;
    private final int mAddress;
    private final boolean mArcSupported;
    private final boolean mCecSupported;
    private final boolean mEarcSupported;
    private final int mId;
    private final boolean mMhlSupported;
    private final int mType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PortType {
    }

    @java.lang.Deprecated
    public HdmiPortInfo(int i, int i2, int i3, boolean z, boolean z2, boolean z3) {
        this.mId = i;
        this.mType = i2;
        this.mAddress = i3;
        this.mCecSupported = z;
        this.mArcSupported = z3;
        this.mEarcSupported = false;
        this.mMhlSupported = z2;
    }

    public android.hardware.hdmi.HdmiPortInfo.Builder toBuilder() {
        return new android.hardware.hdmi.HdmiPortInfo.Builder();
    }

    private HdmiPortInfo(android.hardware.hdmi.HdmiPortInfo.Builder builder) {
        this.mId = builder.mId;
        this.mType = builder.mType;
        this.mAddress = builder.mAddress;
        this.mCecSupported = builder.mCecSupported;
        this.mArcSupported = builder.mArcSupported;
        this.mEarcSupported = builder.mEarcSupported;
        this.mMhlSupported = builder.mMhlSupported;
    }

    public int getId() {
        return this.mId;
    }

    public int getType() {
        return this.mType;
    }

    public int getAddress() {
        return this.mAddress;
    }

    public boolean isCecSupported() {
        return this.mCecSupported;
    }

    public boolean isMhlSupported() {
        return this.mMhlSupported;
    }

    public boolean isArcSupported() {
        return this.mArcSupported;
    }

    public boolean isEarcSupported() {
        return this.mEarcSupported;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    @android.annotation.SystemApi
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mAddress);
        parcel.writeInt(this.mCecSupported ? 1 : 0);
        parcel.writeInt(this.mArcSupported ? 1 : 0);
        parcel.writeInt(this.mMhlSupported ? 1 : 0);
        parcel.writeInt(this.mEarcSupported ? 1 : 0);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("port_id: ").append(this.mId).append(", ");
        sb.append("type: ").append(this.mType == 0 ? "HDMI_IN" : "HDMI_OUT").append(", ");
        sb.append("address: ").append(java.lang.String.format("0x%04x", java.lang.Integer.valueOf(this.mAddress))).append(", ");
        sb.append("cec: ").append(this.mCecSupported).append(", ");
        sb.append("arc: ").append(this.mArcSupported).append(", ");
        sb.append("mhl: ").append(this.mMhlSupported).append(", ");
        sb.append("earc: ").append(this.mEarcSupported);
        return sb.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.hardware.hdmi.HdmiPortInfo)) {
            return false;
        }
        android.hardware.hdmi.HdmiPortInfo hdmiPortInfo = (android.hardware.hdmi.HdmiPortInfo) obj;
        return this.mId == hdmiPortInfo.mId && this.mType == hdmiPortInfo.mType && this.mAddress == hdmiPortInfo.mAddress && this.mCecSupported == hdmiPortInfo.mCecSupported && this.mArcSupported == hdmiPortInfo.mArcSupported && this.mMhlSupported == hdmiPortInfo.mMhlSupported && this.mEarcSupported == hdmiPortInfo.mEarcSupported;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mId), java.lang.Integer.valueOf(this.mType), java.lang.Integer.valueOf(this.mAddress), java.lang.Boolean.valueOf(this.mCecSupported), java.lang.Boolean.valueOf(this.mArcSupported), java.lang.Boolean.valueOf(this.mMhlSupported), java.lang.Boolean.valueOf(this.mEarcSupported));
    }

    public static final class Builder {
        private int mAddress;
        private boolean mArcSupported;
        private boolean mCecSupported;
        private boolean mEarcSupported;
        private int mId;
        private boolean mMhlSupported;
        private int mType;

        public Builder(int i, int i2, int i3) {
            if (i2 != 0 && i2 != 1) {
                throw new java.lang.IllegalArgumentException("type should be 0 or 1.");
            }
            if (i3 < 0) {
                throw new java.lang.IllegalArgumentException("address should be positive.");
            }
            this.mId = i;
            this.mType = i2;
            this.mAddress = i3;
        }

        private Builder(android.hardware.hdmi.HdmiPortInfo hdmiPortInfo) {
            this.mId = hdmiPortInfo.mId;
            this.mType = hdmiPortInfo.mType;
            this.mAddress = hdmiPortInfo.mAddress;
            this.mCecSupported = hdmiPortInfo.mCecSupported;
            this.mArcSupported = hdmiPortInfo.mArcSupported;
            this.mEarcSupported = hdmiPortInfo.mEarcSupported;
            this.mMhlSupported = hdmiPortInfo.mMhlSupported;
        }

        public android.hardware.hdmi.HdmiPortInfo build() {
            return new android.hardware.hdmi.HdmiPortInfo(this);
        }

        public android.hardware.hdmi.HdmiPortInfo.Builder setCecSupported(boolean z) {
            this.mCecSupported = z;
            return this;
        }

        public android.hardware.hdmi.HdmiPortInfo.Builder setArcSupported(boolean z) {
            this.mArcSupported = z;
            return this;
        }

        public android.hardware.hdmi.HdmiPortInfo.Builder setEarcSupported(boolean z) {
            this.mEarcSupported = z;
            return this;
        }

        public android.hardware.hdmi.HdmiPortInfo.Builder setMhlSupported(boolean z) {
            this.mMhlSupported = z;
            return this;
        }
    }
}
