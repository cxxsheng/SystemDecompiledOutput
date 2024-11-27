package android.media.audiopolicy;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class AudioMix implements android.os.Parcelable {
    private static final int CALLBACK_FLAGS_ALL = 1;
    public static final int CALLBACK_FLAG_NOTIFY_ACTIVITY = 1;
    public static final android.os.Parcelable.Creator<android.media.audiopolicy.AudioMix> CREATOR = new android.os.Parcelable.Creator<android.media.audiopolicy.AudioMix>() { // from class: android.media.audiopolicy.AudioMix.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audiopolicy.AudioMix createFromParcel(android.os.Parcel parcel) {
            android.media.audiopolicy.AudioMix.Builder builder = new android.media.audiopolicy.AudioMix.Builder();
            builder.setRouteFlags(parcel.readInt());
            builder.setCallbackFlags(parcel.readInt());
            builder.setDevice(parcel.readInt(), parcel.readString8());
            builder.setFormat(android.media.AudioFormat.CREATOR.createFromParcel(parcel));
            builder.setMixingRule(android.media.audiopolicy.AudioMixingRule.CREATOR.createFromParcel(parcel));
            builder.setToken(parcel.readStrongBinder());
            return builder.build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audiopolicy.AudioMix[] newArray(int i) {
            return new android.media.audiopolicy.AudioMix[i];
        }
    };
    public static final int MIX_STATE_DISABLED = -1;
    public static final int MIX_STATE_IDLE = 0;
    public static final int MIX_STATE_MIXING = 1;
    public static final int MIX_TYPE_INVALID = -1;
    public static final int MIX_TYPE_PLAYERS = 0;
    public static final int MIX_TYPE_RECORDERS = 1;
    private static final int PRIVILEDGED_CAPTURE_MAX_BYTES_PER_SAMPLE = 2;
    private static final int PRIVILEDGED_CAPTURE_MAX_CHANNEL_NUMBER = 1;
    private static final int PRIVILEDGED_CAPTURE_MAX_SAMPLE_RATE = 16000;
    public static final int ROUTE_FLAG_LOOP_BACK = 2;
    public static final int ROUTE_FLAG_LOOP_BACK_RENDER = 3;
    public static final int ROUTE_FLAG_RENDER = 1;
    private static final int ROUTE_FLAG_SUPPORTED = 3;
    int mCallbackFlags;
    java.lang.String mDeviceAddress;
    final int mDeviceSystemType;
    private android.media.AudioFormat mFormat;
    int mMixState;
    private int mMixType;
    private int mRouteFlags;
    private android.media.audiopolicy.AudioMixingRule mRule;
    private final android.os.IBinder mToken;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RouteFlags {
    }

    private AudioMix(android.media.audiopolicy.AudioMixingRule audioMixingRule, android.media.AudioFormat audioFormat, int i, int i2, int i3, java.lang.String str, android.os.IBinder iBinder) {
        this.mMixType = -1;
        this.mMixState = -1;
        this.mRule = (android.media.audiopolicy.AudioMixingRule) java.util.Objects.requireNonNull(audioMixingRule);
        this.mFormat = (android.media.AudioFormat) java.util.Objects.requireNonNull(audioFormat);
        this.mRouteFlags = i;
        this.mMixType = audioMixingRule.getTargetMixType();
        this.mCallbackFlags = i2;
        this.mDeviceSystemType = i3;
        this.mDeviceAddress = str == null ? new java.lang.String("") : str;
        this.mToken = iBinder;
    }

    public int getMixState() {
        return this.mMixState;
    }

    public int getRouteFlags() {
        return this.mRouteFlags;
    }

    public android.media.AudioFormat getFormat() {
        return this.mFormat;
    }

    public android.media.audiopolicy.AudioMixingRule getRule() {
        return this.mRule;
    }

    public int getMixType() {
        return this.mMixType;
    }

    void setRegistration(java.lang.String str) {
        this.mDeviceAddress = str;
    }

    public void setAudioMixingRule(android.media.audiopolicy.AudioMixingRule audioMixingRule) {
        if (this.mRule.getTargetMixType() != audioMixingRule.getTargetMixType()) {
            throw new java.lang.UnsupportedOperationException("Target mix role of updated rule doesn't match the mix role of the AudioMix");
        }
        this.mRule = (android.media.audiopolicy.AudioMixingRule) java.util.Objects.requireNonNull(audioMixingRule);
    }

    public java.lang.String getRegistration() {
        return this.mDeviceAddress;
    }

    public boolean isAffectingUsage(int i) {
        return this.mRule.isAffectingUsage(i);
    }

    public boolean containsMatchAttributeRuleForUsage(int i) {
        return this.mRule.containsMatchAttributeRuleForUsage(i);
    }

    public boolean isRoutedToDevice(int i, java.lang.String str) {
        return (this.mRouteFlags & 1) == 1 && i == this.mDeviceSystemType && str.equals(this.mDeviceAddress);
    }

    public static java.lang.String canBeUsedForPrivilegedMediaCapture(android.media.AudioFormat audioFormat) {
        int sampleRate = audioFormat.getSampleRate();
        if (sampleRate > PRIVILEDGED_CAPTURE_MAX_SAMPLE_RATE || sampleRate <= 0) {
            return "Privileged audio capture sample rate " + sampleRate + " can not be over " + PRIVILEDGED_CAPTURE_MAX_SAMPLE_RATE + "kHz";
        }
        int channelCount = audioFormat.getChannelCount();
        if (channelCount > 1 || channelCount <= 0) {
            return "Privileged audio capture channel count " + channelCount + " can not be over 1";
        }
        int encoding = audioFormat.getEncoding();
        if (!android.media.AudioFormat.isPublicEncoding(encoding) || !android.media.AudioFormat.isEncodingLinearPcm(encoding)) {
            return "Privileged audio capture encoding " + encoding + "is not linear";
        }
        if (android.media.AudioFormat.getBytesPerSample(encoding) > 2) {
            return "Privileged audio capture encoding " + encoding + " can not be over 2 bytes per sample";
        }
        return null;
    }

    public boolean isForCallRedirection() {
        return this.mRule.isForCallRedirection();
    }

    public boolean equals(java.lang.Object obj) {
        boolean z;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.media.audiopolicy.AudioMix audioMix = (android.media.audiopolicy.AudioMix) obj;
        if (android.media.audiopolicy.Flags.audioMixOwnership()) {
            z = java.util.Objects.equals(this.mToken, audioMix.mToken);
        } else {
            z = true;
        }
        if (java.util.Objects.equals(java.lang.Integer.valueOf(this.mRouteFlags), java.lang.Integer.valueOf(audioMix.mRouteFlags)) && java.util.Objects.equals(this.mRule, audioMix.mRule) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mMixType), java.lang.Integer.valueOf(audioMix.mMixType)) && java.util.Objects.equals(this.mFormat, audioMix.mFormat) && z) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (android.media.audiopolicy.Flags.audioMixOwnership()) {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.mRouteFlags), this.mRule, java.lang.Integer.valueOf(this.mMixType), this.mFormat, this.mToken);
        }
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mRouteFlags), this.mRule, java.lang.Integer.valueOf(this.mMixType), this.mFormat);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mRouteFlags);
        parcel.writeInt(this.mCallbackFlags);
        parcel.writeInt(this.mDeviceSystemType);
        parcel.writeString8(this.mDeviceAddress);
        this.mFormat.writeToParcel(parcel, i);
        this.mRule.writeToParcel(parcel, i);
        parcel.writeStrongBinder(this.mToken);
    }

    public static class Builder {
        private int mCallbackFlags;
        private java.lang.String mDeviceAddress;
        private int mDeviceSystemType;
        private android.media.AudioFormat mFormat;
        private int mRouteFlags;
        private android.media.audiopolicy.AudioMixingRule mRule;
        private android.os.IBinder mToken;

        Builder() {
            this.mRule = null;
            this.mFormat = null;
            this.mRouteFlags = 0;
            this.mCallbackFlags = 0;
            this.mToken = null;
            this.mDeviceSystemType = 0;
            this.mDeviceAddress = null;
        }

        public Builder(android.media.audiopolicy.AudioMixingRule audioMixingRule) throws java.lang.IllegalArgumentException {
            this.mRule = null;
            this.mFormat = null;
            this.mRouteFlags = 0;
            this.mCallbackFlags = 0;
            this.mToken = null;
            this.mDeviceSystemType = 0;
            this.mDeviceAddress = null;
            if (audioMixingRule == null) {
                throw new java.lang.IllegalArgumentException("Illegal null AudioMixingRule argument");
            }
            this.mRule = audioMixingRule;
        }

        android.media.audiopolicy.AudioMix.Builder setMixingRule(android.media.audiopolicy.AudioMixingRule audioMixingRule) throws java.lang.IllegalArgumentException {
            if (audioMixingRule == null) {
                throw new java.lang.IllegalArgumentException("Illegal null AudioMixingRule argument");
            }
            this.mRule = audioMixingRule;
            return this;
        }

        android.media.audiopolicy.AudioMix.Builder setToken(android.os.IBinder iBinder) {
            this.mToken = iBinder;
            return this;
        }

        android.media.audiopolicy.AudioMix.Builder setCallbackFlags(int i) throws java.lang.IllegalArgumentException {
            if (i != 0 && (i & 1) == 0) {
                throw new java.lang.IllegalArgumentException("Illegal callback flags 0x" + java.lang.Integer.toHexString(i).toUpperCase());
            }
            this.mCallbackFlags = i;
            return this;
        }

        public android.media.audiopolicy.AudioMix.Builder setDevice(int i, java.lang.String str) {
            this.mDeviceSystemType = i;
            this.mDeviceAddress = str;
            return this;
        }

        public android.media.audiopolicy.AudioMix.Builder setFormat(android.media.AudioFormat audioFormat) throws java.lang.IllegalArgumentException {
            if (audioFormat == null) {
                throw new java.lang.IllegalArgumentException("Illegal null AudioFormat argument");
            }
            this.mFormat = audioFormat;
            return this;
        }

        public android.media.audiopolicy.AudioMix.Builder setRouteFlags(int i) throws java.lang.IllegalArgumentException {
            if (i == 0) {
                throw new java.lang.IllegalArgumentException("Illegal empty route flags");
            }
            if ((i & 3) == 0) {
                throw new java.lang.IllegalArgumentException("Invalid route flags 0x" + java.lang.Integer.toHexString(i) + "when configuring an AudioMix");
            }
            if ((i & (-4)) != 0) {
                throw new java.lang.IllegalArgumentException("Unknown route flags 0x" + java.lang.Integer.toHexString(i) + "when configuring an AudioMix");
            }
            this.mRouteFlags = i;
            return this;
        }

        public android.media.audiopolicy.AudioMix.Builder setDevice(android.media.AudioDeviceInfo audioDeviceInfo) throws java.lang.IllegalArgumentException {
            if (audioDeviceInfo == null) {
                throw new java.lang.IllegalArgumentException("Illegal null AudioDeviceInfo argument");
            }
            if (!audioDeviceInfo.isSink()) {
                throw new java.lang.IllegalArgumentException("Unsupported device type on mix, not a sink");
            }
            this.mDeviceSystemType = android.media.AudioDeviceInfo.convertDeviceTypeToInternalDevice(audioDeviceInfo.getType());
            this.mDeviceAddress = audioDeviceInfo.getAddress();
            return this;
        }

        public android.media.audiopolicy.AudioMix build() throws java.lang.IllegalArgumentException {
            java.lang.String canBeUsedForPrivilegedMediaCapture;
            if (this.mRule == null) {
                throw new java.lang.IllegalArgumentException("Illegal null AudioMixingRule");
            }
            if (this.mRouteFlags == 0) {
                this.mRouteFlags = 2;
            }
            if (this.mFormat == null) {
                int primaryOutputSamplingRate = android.media.AudioSystem.getPrimaryOutputSamplingRate();
                if (primaryOutputSamplingRate <= 0) {
                    primaryOutputSamplingRate = 44100;
                }
                this.mFormat = new android.media.AudioFormat.Builder().setSampleRate(primaryOutputSamplingRate).build();
            } else if ((this.mFormat.getPropertySetMask() & 4) != 0 && this.mFormat.getChannelCount() == 1 && this.mFormat.getChannelMask() == 16) {
                this.mFormat = new android.media.AudioFormat.Builder(this.mFormat).setChannelMask(4).build();
            }
            if ((this.mRouteFlags & 2) == 2) {
                if (this.mDeviceSystemType == 0) {
                    this.mDeviceSystemType = getLoopbackDeviceSystemTypeForAudioMixingRule(this.mRule);
                } else if (!android.media.AudioSystem.isRemoteSubmixDevice(this.mDeviceSystemType)) {
                    throw new java.lang.IllegalArgumentException("Device " + android.media.AudioSystem.getDeviceName(this.mDeviceSystemType) + "is not supported for loopback mix.");
                }
            }
            if ((this.mRouteFlags & 1) == 1) {
                if (this.mDeviceSystemType == 0) {
                    throw new java.lang.IllegalArgumentException("Can't have flag ROUTE_FLAG_RENDER without an audio device");
                }
                if (android.media.AudioSystem.DEVICE_IN_ALL_SET.contains(java.lang.Integer.valueOf(this.mDeviceSystemType))) {
                    throw new java.lang.IllegalArgumentException("Input device is not supported with ROUTE_FLAG_RENDER");
                }
                if (this.mRule.getTargetMixType() == 1) {
                    throw new java.lang.IllegalArgumentException("ROUTE_FLAG_RENDER/ROUTE_FLAG_LOOP_BACK_RENDER is not supported for non-playback mix rule");
                }
            }
            if (this.mRule.allowPrivilegedMediaPlaybackCapture() && (canBeUsedForPrivilegedMediaCapture = android.media.audiopolicy.AudioMix.canBeUsedForPrivilegedMediaCapture(this.mFormat)) != null) {
                throw new java.lang.IllegalArgumentException(canBeUsedForPrivilegedMediaCapture);
            }
            if (this.mToken == null) {
                this.mToken = new android.os.Binder();
            }
            return new android.media.audiopolicy.AudioMix(this.mRule, this.mFormat, this.mRouteFlags, this.mCallbackFlags, this.mDeviceSystemType, this.mDeviceAddress, this.mToken);
        }

        private int getLoopbackDeviceSystemTypeForAudioMixingRule(android.media.audiopolicy.AudioMixingRule audioMixingRule) {
            switch (this.mRule.getTargetMixType()) {
                case 0:
                    return 32768;
                case 1:
                    return android.media.AudioSystem.DEVICE_IN_REMOTE_SUBMIX;
                default:
                    throw new java.lang.IllegalArgumentException("Unknown mixing rule type - 0x" + java.lang.Integer.toHexString(audioMixingRule.getTargetMixType()));
            }
        }
    }
}
