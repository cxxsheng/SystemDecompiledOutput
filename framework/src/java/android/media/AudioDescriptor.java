package android.media;

/* loaded from: classes2.dex */
public class AudioDescriptor implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.AudioDescriptor> CREATOR = new android.os.Parcelable.Creator<android.media.AudioDescriptor>() { // from class: android.media.AudioDescriptor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.AudioDescriptor createFromParcel(android.os.Parcel parcel) {
            return new android.media.AudioDescriptor(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.AudioDescriptor[] newArray(int i) {
            return new android.media.AudioDescriptor[i];
        }
    };
    public static final int STANDARD_EDID = 1;
    public static final int STANDARD_NONE = 0;
    public static final int STANDARD_SADB = 2;
    public static final int STANDARD_VSADB = 3;
    private final byte[] mDescriptor;
    private final int mEncapsulationType;
    private final int mStandard;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AudioDescriptorStandard {
    }

    @android.annotation.SystemApi
    public AudioDescriptor(int i, int i2, byte[] bArr) {
        this.mStandard = i;
        this.mEncapsulationType = i2;
        this.mDescriptor = bArr;
    }

    public int getStandard() {
        return this.mStandard;
    }

    public byte[] getDescriptor() {
        return this.mDescriptor;
    }

    public int getEncapsulationType() {
        return this.mEncapsulationType;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mStandard), java.lang.Integer.valueOf(this.mEncapsulationType), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mDescriptor)));
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.media.AudioDescriptor audioDescriptor = (android.media.AudioDescriptor) obj;
        if (this.mStandard == audioDescriptor.mStandard && this.mEncapsulationType == audioDescriptor.mEncapsulationType && java.util.Arrays.equals(this.mDescriptor, audioDescriptor.mDescriptor)) {
            return true;
        }
        return false;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("{");
        sb.append("standard=" + this.mStandard);
        sb.append(", encapsulation type=" + this.mEncapsulationType);
        if (this.mDescriptor != null && this.mDescriptor.length > 0) {
            sb.append(", descriptor=").append(java.util.Arrays.toString(this.mDescriptor));
        }
        sb.append("}");
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mStandard);
        parcel.writeInt(this.mEncapsulationType);
        parcel.writeByteArray(this.mDescriptor);
    }

    private AudioDescriptor(android.os.Parcel parcel) {
        this.mStandard = parcel.readInt();
        this.mEncapsulationType = parcel.readInt();
        this.mDescriptor = parcel.createByteArray();
    }
}
