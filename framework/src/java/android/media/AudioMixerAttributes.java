package android.media;

/* loaded from: classes2.dex */
public final class AudioMixerAttributes implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.AudioMixerAttributes> CREATOR = new android.os.Parcelable.Creator<android.media.AudioMixerAttributes>() { // from class: android.media.AudioMixerAttributes.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.AudioMixerAttributes createFromParcel(android.os.Parcel parcel) {
            return new android.media.AudioMixerAttributes(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.AudioMixerAttributes[] newArray(int i) {
            return new android.media.AudioMixerAttributes[i];
        }
    };
    public static final int MIXER_BEHAVIOR_BIT_PERFECT = 1;
    public static final int MIXER_BEHAVIOR_DEFAULT = 0;
    private final android.media.AudioFormat mFormat;
    private final int mMixerBehavior;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MixerBehavior {
    }

    AudioMixerAttributes(android.media.AudioFormat audioFormat, int i) {
        this.mFormat = audioFormat;
        this.mMixerBehavior = i;
    }

    public android.media.AudioFormat getFormat() {
        return this.mFormat;
    }

    public int getMixerBehavior() {
        return this.mMixerBehavior;
    }

    public static final class Builder {
        private final android.media.AudioFormat mFormat;
        private int mMixerBehavior = 0;

        public Builder(android.media.AudioFormat audioFormat) {
            java.util.Objects.requireNonNull(audioFormat);
            this.mFormat = audioFormat;
        }

        public android.media.AudioMixerAttributes build() {
            return new android.media.AudioMixerAttributes(this.mFormat, this.mMixerBehavior);
        }

        public android.media.AudioMixerAttributes.Builder setMixerBehavior(int i) {
            switch (i) {
                case 0:
                case 1:
                    this.mMixerBehavior = i;
                    return this;
                default:
                    throw new java.lang.IllegalArgumentException("Invalid mixer behavior " + i);
            }
        }
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mFormat, java.lang.Integer.valueOf(this.mMixerBehavior));
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.media.AudioMixerAttributes audioMixerAttributes = (android.media.AudioMixerAttributes) obj;
        if (this.mFormat.equals(audioMixerAttributes.mFormat) && this.mMixerBehavior == audioMixerAttributes.mMixerBehavior) {
            return true;
        }
        return false;
    }

    private java.lang.String mixerBehaviorToString(int i) {
        switch (i) {
            case 0:
                return "default";
            case 1:
                return "bit-perfect";
            default:
                return "unknown";
        }
    }

    public java.lang.String toString() {
        return new java.lang.String("AudioMixerAttributes: format:" + this.mFormat.toString() + " mixer behavior:" + mixerBehaviorToString(this.mMixerBehavior));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mFormat, i);
        parcel.writeInt(this.mMixerBehavior);
    }

    private AudioMixerAttributes(android.os.Parcel parcel) {
        this.mFormat = (android.media.AudioFormat) parcel.readParcelable(android.media.AudioFormat.class.getClassLoader(), android.media.AudioFormat.class);
        this.mMixerBehavior = parcel.readInt();
    }
}
