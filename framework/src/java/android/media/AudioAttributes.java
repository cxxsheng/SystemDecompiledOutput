package android.media;

/* loaded from: classes2.dex */
public final class AudioAttributes implements android.os.Parcelable {
    public static final int ALLOW_CAPTURE_BY_ALL = 1;
    public static final int ALLOW_CAPTURE_BY_NONE = 3;
    public static final int ALLOW_CAPTURE_BY_SYSTEM = 2;
    private static final int ALL_PARCEL_FLAGS = 1;
    private static final int ATTR_PARCEL_IS_NULL_BUNDLE = -1977;
    private static final int ATTR_PARCEL_IS_VALID_BUNDLE = 1980;
    private static final android.util.IntArray CONTENT_TYPES;
    public static final int CONTENT_TYPE_MOVIE = 3;
    public static final int CONTENT_TYPE_MUSIC = 2;
    public static final int CONTENT_TYPE_SONIFICATION = 4;
    public static final int CONTENT_TYPE_SPEECH = 1;

    @android.annotation.SystemApi
    public static final int CONTENT_TYPE_ULTRASOUND = 1997;
    public static final int CONTENT_TYPE_UNKNOWN = 0;
    public static final android.os.Parcelable.Creator<android.media.AudioAttributes> CREATOR;
    private static final int FLAG_ALL = 129023;
    private static final int FLAG_ALL_API_SET = 465;
    private static final int FLAG_ALL_PUBLIC = 273;
    public static final int FLAG_AUDIBILITY_ENFORCED = 1;

    @android.annotation.SystemApi
    public static final int FLAG_BEACON = 8;

    @android.annotation.SystemApi
    public static final int FLAG_BYPASS_INTERRUPTION_POLICY = 64;

    @android.annotation.SystemApi
    public static final int FLAG_BYPASS_MUTE = 128;
    public static final int FLAG_CALL_REDIRECTION = 65536;
    public static final int FLAG_CAPTURE_PRIVATE = 8192;
    public static final int FLAG_CONTENT_SPATIALIZED = 16384;
    public static final int FLAG_DEEP_BUFFER = 512;
    public static final int FLAG_HW_AV_SYNC = 16;

    @android.annotation.SystemApi
    public static final int FLAG_HW_HOTWORD = 32;
    public static final int FLAG_LOW_LATENCY = 256;
    public static final int FLAG_MUTE_HAPTIC = 2048;
    public static final int FLAG_NEVER_SPATIALIZE = 32768;
    public static final int FLAG_NO_MEDIA_PROJECTION = 1024;
    public static final int FLAG_NO_SYSTEM_CAPTURE = 4096;
    public static final int FLAG_SCO = 4;
    public static final int FLAG_SECURE = 2;
    public static final int FLATTEN_TAGS = 1;
    public static final android.util.IntArray SDK_USAGES;
    public static final int SPATIALIZATION_BEHAVIOR_AUTO = 0;
    public static final int SPATIALIZATION_BEHAVIOR_NEVER = 1;
    public static final int SUPPRESSIBLE_ALARM = 4;
    public static final int SUPPRESSIBLE_CALL = 2;
    public static final int SUPPRESSIBLE_MEDIA = 5;
    public static final int SUPPRESSIBLE_NEVER = 3;
    public static final int SUPPRESSIBLE_NOTIFICATION = 1;
    public static final int SUPPRESSIBLE_SYSTEM = 6;
    public static final android.util.SparseIntArray SUPPRESSIBLE_USAGES = new android.util.SparseIntArray();
    private static final int SYSTEM_USAGE_OFFSET = 1000;
    private static final java.lang.String TAG = "AudioAttributes";
    public static final int USAGE_ALARM = 4;

    @android.annotation.SystemApi
    public static final int USAGE_ANNOUNCEMENT = 1003;
    public static final int USAGE_ASSISTANCE_ACCESSIBILITY = 11;
    public static final int USAGE_ASSISTANCE_NAVIGATION_GUIDANCE = 12;
    public static final int USAGE_ASSISTANCE_SONIFICATION = 13;
    public static final int USAGE_ASSISTANT = 16;

    @android.annotation.SystemApi
    public static final int USAGE_CALL_ASSISTANT = 17;

    @android.annotation.SystemApi
    public static final int USAGE_EMERGENCY = 1000;
    public static final int USAGE_GAME = 14;
    private static final int USAGE_INVALID = -1;
    public static final int USAGE_MEDIA = 1;
    public static final int USAGE_NOTIFICATION = 5;

    @java.lang.Deprecated
    public static final int USAGE_NOTIFICATION_COMMUNICATION_DELAYED = 9;

    @java.lang.Deprecated
    public static final int USAGE_NOTIFICATION_COMMUNICATION_INSTANT = 8;

    @java.lang.Deprecated
    public static final int USAGE_NOTIFICATION_COMMUNICATION_REQUEST = 7;
    public static final int USAGE_NOTIFICATION_EVENT = 10;
    public static final int USAGE_NOTIFICATION_RINGTONE = 6;

    @android.annotation.SystemApi
    public static final int USAGE_SAFETY = 1001;
    public static final int USAGE_UNKNOWN = 0;

    @android.annotation.SystemApi
    public static final int USAGE_VEHICLE_STATUS = 1002;
    public static final int USAGE_VIRTUAL_SOURCE = 15;
    public static final int USAGE_VOICE_COMMUNICATION = 2;
    public static final int USAGE_VOICE_COMMUNICATION_SIGNALLING = 3;
    private static final java.util.Map<java.lang.String, java.lang.Integer> sXsdStringToUsage;
    private android.os.Bundle mBundle;
    private int mContentType;
    private int mFlags;
    private java.lang.String mFormattedTags;
    private int mSource;
    private java.util.HashSet<java.lang.String> mTags;
    private int mUsage;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AttrInternalContentType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AttributeContentType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AttributeSdkUsage {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AttributeSystemUsage {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AttributeUsage {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CapturePolicy {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SpatializationBehavior {
    }

    static {
        SUPPRESSIBLE_USAGES.put(5, 1);
        SUPPRESSIBLE_USAGES.put(6, 2);
        SUPPRESSIBLE_USAGES.put(7, 2);
        SUPPRESSIBLE_USAGES.put(8, 1);
        SUPPRESSIBLE_USAGES.put(9, 1);
        SUPPRESSIBLE_USAGES.put(10, 1);
        SUPPRESSIBLE_USAGES.put(11, 3);
        SUPPRESSIBLE_USAGES.put(2, 3);
        SUPPRESSIBLE_USAGES.put(3, 3);
        SUPPRESSIBLE_USAGES.put(4, 4);
        SUPPRESSIBLE_USAGES.put(1, 5);
        SUPPRESSIBLE_USAGES.put(12, 5);
        SUPPRESSIBLE_USAGES.put(14, 5);
        SUPPRESSIBLE_USAGES.put(16, 5);
        SUPPRESSIBLE_USAGES.put(17, 3);
        SUPPRESSIBLE_USAGES.put(0, 5);
        SUPPRESSIBLE_USAGES.put(13, 6);
        SDK_USAGES = android.util.IntArray.wrap(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 16});
        CONTENT_TYPES = android.util.IntArray.wrap(new int[]{0, 1, 2, 3, 4});
        CREATOR = new android.os.Parcelable.Creator<android.media.AudioAttributes>() { // from class: android.media.AudioAttributes.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.AudioAttributes createFromParcel(android.os.Parcel parcel) {
                return new android.media.AudioAttributes(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.AudioAttributes[] newArray(int i) {
                return new android.media.AudioAttributes[i];
            }
        };
        sXsdStringToUsage = new java.util.HashMap();
        sXsdStringToUsage.put(android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_UNKNOWN.toString(), 0);
        sXsdStringToUsage.put(android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_UNKNOWN.toString(), 0);
        sXsdStringToUsage.put(android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_MEDIA.toString(), 1);
        sXsdStringToUsage.put(android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_VOICE_COMMUNICATION.toString(), 2);
        sXsdStringToUsage.put(android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_VOICE_COMMUNICATION_SIGNALLING.toString(), 3);
        sXsdStringToUsage.put(android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_ALARM.toString(), 4);
        sXsdStringToUsage.put(android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_NOTIFICATION.toString(), 5);
        sXsdStringToUsage.put(android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_NOTIFICATION_TELEPHONY_RINGTONE.toString(), 6);
        sXsdStringToUsage.put(android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_ASSISTANCE_ACCESSIBILITY.toString(), 11);
        sXsdStringToUsage.put(android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_ASSISTANCE_NAVIGATION_GUIDANCE.toString(), 12);
        sXsdStringToUsage.put(android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_ASSISTANCE_SONIFICATION.toString(), 13);
        sXsdStringToUsage.put(android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_GAME.toString(), 14);
        sXsdStringToUsage.put(android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_VIRTUAL_SOURCE.toString(), 15);
        sXsdStringToUsage.put(android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_ASSISTANT.toString(), 16);
        sXsdStringToUsage.put(android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_CALL_ASSISTANT.toString(), 17);
        sXsdStringToUsage.put(android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_EMERGENCY.toString(), 1000);
        sXsdStringToUsage.put(android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_SAFETY.toString(), 1001);
        sXsdStringToUsage.put(android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_VEHICLE_STATUS.toString(), 1002);
        sXsdStringToUsage.put(android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_ANNOUNCEMENT.toString(), 1003);
    }

    public static int[] getSdkUsages() {
        return SDK_USAGES.toArray();
    }

    private AudioAttributes() {
        this.mUsage = 0;
        this.mContentType = 0;
        this.mSource = -1;
        this.mFlags = 0;
    }

    public int getContentType() {
        return this.mContentType;
    }

    public int getUsage() {
        if (isSystemUsage(this.mUsage)) {
            return 0;
        }
        return this.mUsage;
    }

    @android.annotation.SystemApi
    public int getSystemUsage() {
        return this.mUsage;
    }

    @android.annotation.SystemApi
    public int getCapturePreset() {
        return this.mSource;
    }

    public int getFlags() {
        return this.mFlags & 273;
    }

    @android.annotation.SystemApi
    public int getAllFlags() {
        return this.mFlags & FLAG_ALL;
    }

    @android.annotation.SystemApi
    public android.os.Bundle getBundle() {
        if (this.mBundle == null) {
            return this.mBundle;
        }
        return new android.os.Bundle(this.mBundle);
    }

    public java.util.Set<java.lang.String> getTags() {
        return java.util.Collections.unmodifiableSet(this.mTags);
    }

    public boolean areHapticChannelsMuted() {
        return (this.mFlags & 2048) != 0;
    }

    public boolean isContentSpatialized() {
        return (this.mFlags & 16384) != 0;
    }

    public int getSpatializationBehavior() {
        return (this.mFlags & 32768) != 0 ? 1 : 0;
    }

    public int getAllowedCapturePolicy() {
        if ((this.mFlags & 4096) == 4096) {
            return 3;
        }
        if ((this.mFlags & 1024) == 1024) {
            return 2;
        }
        return 1;
    }

    public boolean isForCallRedirection() {
        return (this.mFlags & 65536) == 65536;
    }

    public static class Builder {
        private static final int PRIVACY_SENSITIVE_DEFAULT = -1;
        private static final int PRIVACY_SENSITIVE_DISABLED = 0;
        private static final int PRIVACY_SENSITIVE_ENABLED = 1;
        private android.os.Bundle mBundle;
        private int mContentType;
        private int mFlags;
        private boolean mIsContentSpatialized;
        private boolean mMuteHapticChannels;
        private int mPrivacySensitive;
        private int mSource;
        private int mSpatializationBehavior;
        private int mSystemUsage;
        private java.util.HashSet<java.lang.String> mTags;
        private int mUsage;

        public Builder() {
            this.mUsage = -1;
            this.mSystemUsage = -1;
            this.mContentType = 0;
            this.mSource = -1;
            this.mFlags = 0;
            this.mMuteHapticChannels = true;
            this.mIsContentSpatialized = false;
            this.mSpatializationBehavior = 0;
            this.mTags = new java.util.HashSet<>();
            this.mPrivacySensitive = -1;
        }

        public Builder(android.media.AudioAttributes audioAttributes) {
            this.mUsage = -1;
            this.mSystemUsage = -1;
            this.mContentType = 0;
            this.mSource = -1;
            this.mFlags = 0;
            this.mMuteHapticChannels = true;
            this.mIsContentSpatialized = false;
            this.mSpatializationBehavior = 0;
            this.mTags = new java.util.HashSet<>();
            this.mPrivacySensitive = -1;
            this.mUsage = audioAttributes.mUsage;
            this.mContentType = audioAttributes.mContentType;
            this.mSource = audioAttributes.mSource;
            this.mFlags = audioAttributes.getAllFlags();
            this.mTags = (java.util.HashSet) audioAttributes.mTags.clone();
            this.mMuteHapticChannels = audioAttributes.areHapticChannelsMuted();
            this.mIsContentSpatialized = audioAttributes.isContentSpatialized();
            this.mSpatializationBehavior = audioAttributes.getSpatializationBehavior();
            if ((this.mFlags & 8192) != 0) {
                this.mPrivacySensitive = 1;
            }
        }

        public android.media.AudioAttributes build() {
            android.media.AudioAttributes audioAttributes = new android.media.AudioAttributes();
            audioAttributes.mContentType = this.mContentType;
            if (this.mUsage == -1) {
                if (this.mSystemUsage == -1) {
                    audioAttributes.mUsage = 0;
                } else {
                    audioAttributes.mUsage = this.mSystemUsage;
                }
            } else if (this.mSystemUsage == -1) {
                audioAttributes.mUsage = this.mUsage;
            } else {
                throw new java.lang.IllegalArgumentException("Cannot set both usage and system usage on same builder");
            }
            switch (audioAttributes.mUsage) {
                case 7:
                case 8:
                case 9:
                    audioAttributes.mUsage = 5;
                    break;
            }
            audioAttributes.mSource = this.mSource;
            audioAttributes.mFlags = this.mFlags;
            if (this.mMuteHapticChannels) {
                audioAttributes.mFlags |= 2048;
            }
            if (this.mIsContentSpatialized) {
                audioAttributes.mFlags |= 16384;
            }
            if (this.mSpatializationBehavior == 1) {
                audioAttributes.mFlags |= 32768;
            }
            if (this.mPrivacySensitive == -1) {
                if (this.mSource == 7 || this.mSource == 5) {
                    audioAttributes.mFlags |= 8192;
                } else {
                    audioAttributes.mFlags &= -8193;
                }
            } else if (this.mPrivacySensitive == 1) {
                audioAttributes.mFlags |= 8192;
            } else {
                audioAttributes.mFlags &= -8193;
            }
            audioAttributes.mTags = (java.util.HashSet) this.mTags.clone();
            audioAttributes.mFormattedTags = android.text.TextUtils.join(android.inputmethodservice.navigationbar.NavigationBarInflaterView.GRAVITY_SEPARATOR, this.mTags);
            if (this.mBundle != null) {
                audioAttributes.mBundle = new android.os.Bundle(this.mBundle);
            }
            if (this.mSource != 6 && (this.mFlags & 32) == 32) {
                audioAttributes.mFlags &= -33;
            }
            return audioAttributes;
        }

        public android.media.AudioAttributes.Builder setUsage(int i) {
            switch (i) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                    this.mUsage = i;
                    return this;
                default:
                    throw new java.lang.IllegalArgumentException("Invalid usage " + i);
            }
        }

        @android.annotation.SystemApi
        public android.media.AudioAttributes.Builder setSystemUsage(int i) {
            if (android.media.AudioAttributes.isSystemUsage(i)) {
                this.mSystemUsage = i;
                return this;
            }
            throw new java.lang.IllegalArgumentException("Invalid system usage " + i);
        }

        public android.media.AudioAttributes.Builder setContentType(int i) {
            switch (i) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                    this.mContentType = i;
                    return this;
                default:
                    throw new java.lang.IllegalArgumentException("Invalid content type " + i);
            }
        }

        @android.annotation.SystemApi
        public android.media.AudioAttributes.Builder setInternalContentType(int i) {
            switch (i) {
                case 1997:
                    this.mContentType = i;
                    return this;
                default:
                    setContentType(i);
                    return this;
            }
        }

        public android.media.AudioAttributes.Builder setFlags(int i) {
            this.mFlags = (i & 465) | this.mFlags;
            return this;
        }

        @android.annotation.SystemApi
        public android.media.AudioAttributes.Builder setHotwordModeEnabled(boolean z) {
            if (z) {
                this.mFlags |= 32;
            } else {
                this.mFlags &= -33;
            }
            return this;
        }

        public android.media.AudioAttributes.Builder setAllowedCapturePolicy(int i) {
            this.mFlags = android.media.AudioAttributes.capturePolicyToFlags(i, this.mFlags);
            return this;
        }

        public android.media.AudioAttributes.Builder setIsContentSpatialized(boolean z) {
            this.mIsContentSpatialized = z;
            return this;
        }

        public android.media.AudioAttributes.Builder setSpatializationBehavior(int i) {
            switch (i) {
                case 0:
                case 1:
                    this.mSpatializationBehavior = i;
                    return this;
                default:
                    throw new java.lang.IllegalArgumentException("Invalid spatialization behavior " + i);
            }
        }

        public android.media.AudioAttributes.Builder replaceFlags(int i) {
            this.mFlags = i & android.media.AudioAttributes.FLAG_ALL;
            return this;
        }

        @android.annotation.SystemApi
        public android.media.AudioAttributes.Builder addBundle(android.os.Bundle bundle) {
            if (bundle == null) {
                throw new java.lang.IllegalArgumentException("Illegal null bundle");
            }
            if (this.mBundle == null) {
                this.mBundle = new android.os.Bundle(bundle);
            } else {
                this.mBundle.putAll(bundle);
            }
            return this;
        }

        public android.media.AudioAttributes.Builder addTag(java.lang.String str) {
            this.mTags.add(str);
            return this;
        }

        public android.media.AudioAttributes.Builder replaceTags(java.util.HashSet<java.lang.String> hashSet) {
            this.mTags = (java.util.HashSet) hashSet.clone();
            return this;
        }

        public android.media.AudioAttributes.Builder setLegacyStreamType(int i) {
            if (i == 10) {
                throw new java.lang.IllegalArgumentException("STREAM_ACCESSIBILITY is not a legacy stream type that was used for audio playback");
            }
            setInternalLegacyStreamType(i);
            return this;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public android.media.AudioAttributes.Builder setInternalLegacyStreamType(int i) {
            android.media.AudioAttributes audioAttributesForStrategyWithLegacyStreamType;
            this.mContentType = 0;
            this.mUsage = 0;
            if (android.media.audiopolicy.AudioProductStrategy.getAudioProductStrategies().size() > 0 && (audioAttributesForStrategyWithLegacyStreamType = android.media.audiopolicy.AudioProductStrategy.getAudioAttributesForStrategyWithLegacyStreamType(i)) != null) {
                this.mUsage = audioAttributesForStrategyWithLegacyStreamType.mUsage;
                this.mFlags = audioAttributesForStrategyWithLegacyStreamType.getAllFlags();
                this.mMuteHapticChannels = audioAttributesForStrategyWithLegacyStreamType.areHapticChannelsMuted();
                this.mIsContentSpatialized = audioAttributesForStrategyWithLegacyStreamType.isContentSpatialized();
                this.mSpatializationBehavior = audioAttributesForStrategyWithLegacyStreamType.getSpatializationBehavior();
                this.mTags = audioAttributesForStrategyWithLegacyStreamType.mTags;
                this.mBundle = audioAttributesForStrategyWithLegacyStreamType.mBundle;
                this.mSource = audioAttributesForStrategyWithLegacyStreamType.mSource;
            }
            switch (i) {
                case 0:
                    this.mContentType = 1;
                    break;
                case 1:
                    this.mContentType = 4;
                    break;
                case 2:
                    this.mContentType = 4;
                    break;
                case 3:
                    break;
                case 4:
                    this.mContentType = 4;
                    break;
                case 5:
                    this.mContentType = 4;
                    break;
                case 6:
                    this.mContentType = 1;
                    this.mFlags |= 4;
                    break;
                case 7:
                    this.mFlags = 1 | this.mFlags;
                    this.mContentType = 4;
                    break;
                case 8:
                    this.mContentType = 4;
                    break;
                case 9:
                    this.mContentType = 4;
                    this.mFlags |= 8;
                    break;
                case 10:
                    this.mContentType = 1;
                    break;
                case 11:
                    this.mContentType = 1;
                    break;
                default:
                    android.util.Log.e(android.media.AudioAttributes.TAG, "Invalid stream type " + i + " for AudioAttributes");
                    break;
            }
            if (this.mUsage == 0) {
                this.mUsage = android.media.AudioAttributes.usageForStreamType(i);
            }
            return this;
        }

        @android.annotation.SystemApi
        public android.media.AudioAttributes.Builder setCapturePreset(int i) {
            switch (i) {
                case 0:
                case 1:
                case 5:
                case 6:
                case 7:
                case 9:
                case 10:
                    this.mSource = i;
                    return this;
                case 2:
                case 3:
                case 4:
                case 8:
                default:
                    android.util.Log.e(android.media.AudioAttributes.TAG, "Invalid capture preset " + i + " for AudioAttributes");
                    return this;
            }
        }

        @android.annotation.SystemApi
        public android.media.AudioAttributes.Builder setInternalCapturePreset(int i) {
            if (i == 1999 || i == 8 || i == 1998 || i == 3 || i == 2 || i == 4 || i == 1997 || i == 2000 || i == -1) {
                this.mSource = i;
            } else {
                setCapturePreset(i);
            }
            return this;
        }

        public android.media.AudioAttributes.Builder setHapticChannelsMuted(boolean z) {
            this.mMuteHapticChannels = z;
            return this;
        }

        public android.media.AudioAttributes.Builder setPrivacySensitive(boolean z) {
            this.mPrivacySensitive = z ? 1 : 0;
            return this;
        }

        public android.media.AudioAttributes.Builder setForCallRedirection() {
            this.mFlags |= 65536;
            return this;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mUsage);
        parcel.writeInt(this.mContentType);
        parcel.writeInt(this.mSource);
        parcel.writeInt(this.mFlags);
        int i2 = i & 1;
        parcel.writeInt(i2);
        if (i2 == 0) {
            java.lang.String[] strArr = new java.lang.String[this.mTags.size()];
            this.mTags.toArray(strArr);
            parcel.writeStringArray(strArr);
        } else if (i2 == 1) {
            parcel.writeString(this.mFormattedTags);
        }
        if (this.mBundle == null) {
            parcel.writeInt(ATTR_PARCEL_IS_NULL_BUNDLE);
        } else {
            parcel.writeInt(1980);
            parcel.writeBundle(this.mBundle);
        }
    }

    private AudioAttributes(android.os.Parcel parcel) {
        this.mUsage = 0;
        this.mContentType = 0;
        this.mSource = -1;
        this.mFlags = 0;
        this.mUsage = parcel.readInt();
        this.mContentType = parcel.readInt();
        this.mSource = parcel.readInt();
        this.mFlags = parcel.readInt();
        boolean z = (parcel.readInt() & 1) == 1;
        this.mTags = new java.util.HashSet<>();
        if (z) {
            this.mFormattedTags = new java.lang.String(parcel.readString());
            this.mTags.add(this.mFormattedTags);
        } else {
            java.lang.String[] readStringArray = parcel.readStringArray();
            for (int length = readStringArray.length - 1; length >= 0; length--) {
                this.mTags.add(readStringArray[length]);
            }
            this.mFormattedTags = android.text.TextUtils.join(android.inputmethodservice.navigationbar.NavigationBarInflaterView.GRAVITY_SEPARATOR, this.mTags);
        }
        switch (parcel.readInt()) {
            case ATTR_PARCEL_IS_NULL_BUNDLE /* -1977 */:
                this.mBundle = null;
                break;
            case 1980:
                this.mBundle = new android.os.Bundle(parcel.readBundle());
                break;
            default:
                android.util.Log.e(TAG, "Illegal value unmarshalling AudioAttributes, can't initialize bundle");
                break;
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.media.AudioAttributes audioAttributes = (android.media.AudioAttributes) obj;
        if (this.mContentType == audioAttributes.mContentType && this.mFlags == audioAttributes.mFlags && this.mSource == audioAttributes.mSource && this.mUsage == audioAttributes.mUsage && this.mFormattedTags.equals(audioAttributes.mFormattedTags)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mContentType), java.lang.Integer.valueOf(this.mFlags), java.lang.Integer.valueOf(this.mSource), java.lang.Integer.valueOf(this.mUsage), this.mFormattedTags, this.mBundle);
    }

    public java.lang.String toString() {
        return new java.lang.String("AudioAttributes: usage=" + usageToString() + " content=" + contentTypeToString() + (this.mSource != -1 ? " source=" + android.media.MediaRecorder.toLogFriendlyAudioSource(this.mSource) : "") + " flags=0x" + java.lang.Integer.toHexString(this.mFlags).toUpperCase() + " tags=" + this.mFormattedTags + " bundle=" + (this.mBundle == null ? "null" : this.mBundle.toString()));
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1159641169921L, this.mUsage);
        protoOutputStream.write(1159641169922L, this.mContentType);
        protoOutputStream.write(1120986464259L, this.mFlags);
        for (java.lang.String str : this.mFormattedTags.split(android.inputmethodservice.navigationbar.NavigationBarInflaterView.GRAVITY_SEPARATOR)) {
            java.lang.String trim = str.trim();
            if (trim != "") {
                protoOutputStream.write(2237677961220L, trim);
            }
        }
        protoOutputStream.end(start);
    }

    public java.lang.String usageToString() {
        return usageToString(this.mUsage);
    }

    public static java.lang.String usageToString(int i) {
        switch (i) {
            case 0:
                return "USAGE_UNKNOWN";
            case 1:
                return "USAGE_MEDIA";
            case 2:
                return "USAGE_VOICE_COMMUNICATION";
            case 3:
                return "USAGE_VOICE_COMMUNICATION_SIGNALLING";
            case 4:
                return "USAGE_ALARM";
            case 5:
                return "USAGE_NOTIFICATION";
            case 6:
                return "USAGE_NOTIFICATION_RINGTONE";
            case 7:
                return "USAGE_NOTIFICATION_COMMUNICATION_REQUEST";
            case 8:
                return "USAGE_NOTIFICATION_COMMUNICATION_INSTANT";
            case 9:
                return "USAGE_NOTIFICATION_COMMUNICATION_DELAYED";
            case 10:
                return "USAGE_NOTIFICATION_EVENT";
            case 11:
                return "USAGE_ASSISTANCE_ACCESSIBILITY";
            case 12:
                return "USAGE_ASSISTANCE_NAVIGATION_GUIDANCE";
            case 13:
                return "USAGE_ASSISTANCE_SONIFICATION";
            case 14:
                return "USAGE_GAME";
            case 16:
                return "USAGE_ASSISTANT";
            case 17:
                return "USAGE_CALL_ASSISTANT";
            case 1000:
                return "USAGE_EMERGENCY";
            case 1001:
                return "USAGE_SAFETY";
            case 1002:
                return "USAGE_VEHICLE_STATUS";
            case 1003:
                return "USAGE_ANNOUNCEMENT";
            default:
                return "unknown usage " + i;
        }
    }

    public static java.lang.String usageToXsdString(int i) {
        switch (i) {
            case 0:
                return android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_UNKNOWN.toString();
            case 1:
                return android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_MEDIA.toString();
            case 2:
                return android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_VOICE_COMMUNICATION.toString();
            case 3:
                return android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_VOICE_COMMUNICATION_SIGNALLING.toString();
            case 4:
                return android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_ALARM.toString();
            case 5:
                return android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_NOTIFICATION.toString();
            case 6:
                return android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_NOTIFICATION_TELEPHONY_RINGTONE.toString();
            case 11:
                return android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_ASSISTANCE_ACCESSIBILITY.toString();
            case 12:
                return android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_ASSISTANCE_NAVIGATION_GUIDANCE.toString();
            case 13:
                return android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_ASSISTANCE_SONIFICATION.toString();
            case 14:
                return android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_GAME.toString();
            case 15:
                return android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_VIRTUAL_SOURCE.toString();
            case 16:
                return android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_ASSISTANT.toString();
            case 17:
                return android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_CALL_ASSISTANT.toString();
            case 1000:
                return android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_EMERGENCY.toString();
            case 1001:
                return android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_SAFETY.toString();
            case 1002:
                return android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_VEHICLE_STATUS.toString();
            case 1003:
                return android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_ANNOUNCEMENT.toString();
            default:
                android.util.Log.w(TAG, "Unknown usage value " + i);
                return android.audio.policy.configuration.V7_0.AudioUsage.AUDIO_USAGE_UNKNOWN.toString();
        }
    }

    public static int xsdStringToUsage(java.lang.String str) {
        if (sXsdStringToUsage.containsKey(str)) {
            return sXsdStringToUsage.get(str).intValue();
        }
        android.util.Log.w(TAG, "Usage name not found in AudioUsage enum: " + str);
        return 0;
    }

    public java.lang.String contentTypeToString() {
        switch (this.mContentType) {
            case 0:
                return new java.lang.String("CONTENT_TYPE_UNKNOWN");
            case 1:
                return new java.lang.String("CONTENT_TYPE_SPEECH");
            case 2:
                return new java.lang.String("CONTENT_TYPE_MUSIC");
            case 3:
                return new java.lang.String("CONTENT_TYPE_MOVIE");
            case 4:
                return new java.lang.String("CONTENT_TYPE_SONIFICATION");
            case 1997:
                return new java.lang.String("CONTENT_TYPE_ULTRASOUND");
            default:
                return new java.lang.String("unknown content type " + this.mContentType);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int usageForStreamType(int i) {
        switch (i) {
        }
        return 2;
    }

    @android.annotation.SystemApi
    public static boolean isSystemUsage(int i) {
        return i == 17 || i == 1000 || i == 1001 || i == 1002 || i == 1003;
    }

    public static boolean isSdkUsage(int i) {
        return SDK_USAGES.contains(i);
    }

    public static boolean isSdkContentType(int i) {
        return CONTENT_TYPES.contains(i);
    }

    public int getVolumeControlStream() {
        return toVolumeStreamType(true, this);
    }

    public static int toLegacyStreamType(android.media.AudioAttributes audioAttributes) {
        return toVolumeStreamType(false, audioAttributes);
    }

    private static int toVolumeStreamType(boolean z, android.media.AudioAttributes audioAttributes) {
        if ((audioAttributes.getFlags() & 1) == 1) {
            return z ? 1 : 7;
        }
        if ((audioAttributes.getAllFlags() & 4) == 4) {
            return z ? 0 : 6;
        }
        if ((audioAttributes.getAllFlags() & 8) == 8) {
            return z ? 3 : 9;
        }
        if (android.media.audiopolicy.AudioProductStrategy.getAudioProductStrategies().size() > 0) {
            return android.media.audiopolicy.AudioProductStrategy.getLegacyStreamTypeForStrategyWithAudioAttributes(audioAttributes);
        }
        switch (audioAttributes.getUsage()) {
            case 0:
            case 1000:
            case 1001:
            case 1002:
            case 1003:
                return 3;
            case 1:
            case 12:
            case 14:
            case 16:
                return 3;
            case 2:
            case 17:
                return 0;
            case 3:
                return z ? 0 : 8;
            case 4:
                return 4;
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
                return 5;
            case 6:
                return 2;
            case 11:
                return 10;
            case 13:
                return 1;
            default:
                if (z) {
                    throw new java.lang.IllegalArgumentException("Unknown usage value " + audioAttributes.getUsage() + " in audio attributes");
                }
                return 3;
        }
    }

    public static int capturePolicyToFlags(int i, int i2) {
        switch (i) {
            case 1:
                return i2 & (-5121);
            case 2:
                return (i2 | 1024) & (-4097);
            case 3:
                return i2 | 5120;
            default:
                throw new java.lang.IllegalArgumentException("Unknown allow playback capture policy");
        }
    }
}
