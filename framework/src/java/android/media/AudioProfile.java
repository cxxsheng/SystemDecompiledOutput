package android.media;

/* loaded from: classes2.dex */
public class AudioProfile implements android.os.Parcelable {
    public static final int AUDIO_ENCAPSULATION_TYPE_IEC61937 = 1;
    public static final int AUDIO_ENCAPSULATION_TYPE_NONE = 0;
    public static final int AUDIO_ENCAPSULATION_TYPE_PCM = 2;
    public static final android.os.Parcelable.Creator<android.media.AudioProfile> CREATOR = new android.os.Parcelable.Creator<android.media.AudioProfile>() { // from class: android.media.AudioProfile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.AudioProfile createFromParcel(android.os.Parcel parcel) {
            return new android.media.AudioProfile(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.AudioProfile[] newArray(int i) {
            return new android.media.AudioProfile[i];
        }
    };
    private final int[] mChannelIndexMasks;
    private final int[] mChannelMasks;
    private final int mEncapsulationType;
    private final int mFormat;
    private final int[] mSamplingRates;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EncapsulationType {
    }

    @android.annotation.SystemApi
    public AudioProfile(int i, int[] iArr, int[] iArr2, int[] iArr3, int i2) {
        this.mFormat = i;
        this.mSamplingRates = iArr;
        this.mChannelMasks = iArr2;
        this.mChannelIndexMasks = iArr3;
        this.mEncapsulationType = i2;
    }

    public int getFormat() {
        return this.mFormat;
    }

    public int[] getChannelMasks() {
        return this.mChannelMasks;
    }

    public int[] getChannelIndexMasks() {
        return this.mChannelIndexMasks;
    }

    public int[] getSampleRates() {
        return this.mSamplingRates;
    }

    public int getEncapsulationType() {
        return this.mEncapsulationType;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mFormat), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mSamplingRates)), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mChannelMasks)), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mChannelIndexMasks)), java.lang.Integer.valueOf(this.mEncapsulationType));
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.media.AudioProfile audioProfile = (android.media.AudioProfile) obj;
        if (this.mFormat == audioProfile.mFormat && hasIdenticalElements(this.mSamplingRates, audioProfile.mSamplingRates) && hasIdenticalElements(this.mChannelMasks, audioProfile.mChannelMasks) && hasIdenticalElements(this.mChannelIndexMasks, audioProfile.mChannelIndexMasks) && this.mEncapsulationType == audioProfile.mEncapsulationType) {
            return true;
        }
        return false;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("{");
        sb.append(android.media.AudioFormat.toLogFriendlyEncoding(this.mFormat));
        if (this.mSamplingRates != null && this.mSamplingRates.length > 0) {
            sb.append(", sampling rates=").append(java.util.Arrays.toString(this.mSamplingRates));
        }
        if (this.mChannelMasks != null && this.mChannelMasks.length > 0) {
            sb.append(", channel masks=").append(toHexString(this.mChannelMasks));
        }
        if (this.mChannelIndexMasks != null && this.mChannelIndexMasks.length > 0) {
            sb.append(", channel index masks=").append(java.util.Arrays.toString(this.mChannelIndexMasks));
        }
        sb.append(", encapsulation type=" + this.mEncapsulationType);
        sb.append("}");
        return sb.toString();
    }

    private static java.lang.String toHexString(int[] iArr) {
        if (iArr == null || iArr.length == 0) {
            return "";
        }
        return (java.lang.String) java.util.Arrays.stream(iArr).mapToObj(new java.util.function.IntFunction() { // from class: android.media.AudioProfile$$ExternalSyntheticLambda0
            @Override // java.util.function.IntFunction
            public final java.lang.Object apply(int i) {
                java.lang.String format;
                format = java.lang.String.format("0x%02X", java.lang.Integer.valueOf(i));
                return format;
            }
        }).collect(java.util.stream.Collectors.joining(", "));
    }

    private static boolean hasIdenticalElements(int[] iArr, int[] iArr2) {
        int[] copyOf = java.util.Arrays.copyOf(iArr, iArr.length);
        java.util.Arrays.sort(copyOf);
        int[] copyOf2 = java.util.Arrays.copyOf(iArr2, iArr2.length);
        java.util.Arrays.sort(copyOf2);
        return java.util.Arrays.equals(copyOf, copyOf2);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mFormat);
        parcel.writeIntArray(this.mSamplingRates);
        parcel.writeIntArray(this.mChannelMasks);
        parcel.writeIntArray(this.mChannelIndexMasks);
        parcel.writeInt(this.mEncapsulationType);
    }

    private AudioProfile(android.os.Parcel parcel) {
        this.mFormat = parcel.readInt();
        this.mSamplingRates = parcel.createIntArray();
        this.mChannelMasks = parcel.createIntArray();
        this.mChannelIndexMasks = parcel.createIntArray();
        this.mEncapsulationType = parcel.readInt();
    }
}
