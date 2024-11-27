package android.service.voice;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class HotwordDetectedResult implements android.os.Parcelable {
    public static final int AUDIO_CHANNEL_UNSET = -1;
    public static final int BACKGROUND_AUDIO_POWER_UNSET = -1;
    public static final int CONFIDENCE_LEVEL_HIGH = 5;
    public static final int CONFIDENCE_LEVEL_LOW = 1;
    public static final int CONFIDENCE_LEVEL_LOW_MEDIUM = 2;
    public static final int CONFIDENCE_LEVEL_MEDIUM = 3;
    public static final int CONFIDENCE_LEVEL_MEDIUM_HIGH = 4;
    public static final int CONFIDENCE_LEVEL_NONE = 0;
    public static final int CONFIDENCE_LEVEL_VERY_HIGH = 6;
    private static final java.lang.String EXTRA_PROXIMITY = "android.service.voice.extra.PROXIMITY";
    public static final int HOTWORD_OFFSET_UNSET = -1;
    private static final int LIMIT_AUDIO_CHANNEL_MAX_VALUE = 63;
    private static final int LIMIT_HOTWORD_OFFSET_MAX_VALUE = 3600000;
    public static final int PROXIMITY_FAR = 2;
    public static final int PROXIMITY_NEAR = 1;
    public static final int PROXIMITY_UNKNOWN = -1;
    private int mAudioChannel;
    private final java.util.List<android.service.voice.HotwordAudioStream> mAudioStreams;
    private final int mBackgroundAudioPower;
    private final int mConfidenceLevel;
    private final android.os.PersistableBundle mExtras;
    private boolean mHotwordDetectionPersonalized;
    private int mHotwordDurationMillis;
    private int mHotwordOffsetMillis;
    private final int mHotwordPhraseId;
    private android.media.MediaSyncEvent mMediaSyncEvent;
    private final int mPersonalizedScore;
    private final int mScore;
    private final int mSpeakerId;
    private static int sMaxBundleSize = -1;
    public static final android.os.Parcelable.Creator<android.service.voice.HotwordDetectedResult> CREATOR = new android.os.Parcelable.Creator<android.service.voice.HotwordDetectedResult>() { // from class: android.service.voice.HotwordDetectedResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.voice.HotwordDetectedResult[] newArray(int i) {
            return new android.service.voice.HotwordDetectedResult[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.voice.HotwordDetectedResult createFromParcel(android.os.Parcel parcel) {
            return new android.service.voice.HotwordDetectedResult(parcel);
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ConfidenceLevel {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface HotwordConfidenceLevelValue {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface Limit {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Proximity {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ProximityValue {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultSpeakerId() {
        return 0;
    }

    public static int getMaxSpeakerId() {
        return 15;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultConfidenceLevel() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultScore() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultPersonalizedScore() {
        return 0;
    }

    public static int getMaxScore() {
        return 255;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultHotwordPhraseId() {
        return 0;
    }

    public static int getMaxHotwordPhraseId() {
        return 63;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.util.List<android.service.voice.HotwordAudioStream> defaultAudioStreams() {
        return java.util.Collections.emptyList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.os.PersistableBundle defaultExtras() {
        return new android.os.PersistableBundle();
    }

    public static int getMaxBundleSize() {
        if (sMaxBundleSize < 0) {
            sMaxBundleSize = android.content.res.Resources.getSystem().getInteger(com.android.internal.R.integer.config_hotwordDetectedResultMaxBundleSize);
        }
        return sMaxBundleSize;
    }

    public android.media.MediaSyncEvent getMediaSyncEvent() {
        return this.mMediaSyncEvent;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultBackgroundAudioPower() {
        return -1;
    }

    public static int getMaxBackgroundAudioPower() {
        return 255;
    }

    public static int getParcelableSize(android.os.Parcelable parcelable) {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        parcelable.writeToParcel(obtain, 0);
        obtain.setDataPosition(0);
        int dataSize = obtain.dataSize();
        obtain.recycle();
        return dataSize;
    }

    public static int getUsageSize(android.service.voice.HotwordDetectedResult hotwordDetectedResult) {
        int bitCount = hotwordDetectedResult.getConfidenceLevel() != defaultConfidenceLevel() ? 0 + bitCount(6L) : 0;
        if (hotwordDetectedResult.getHotwordOffsetMillis() != -1) {
            bitCount += bitCount(3600000L);
        }
        if (hotwordDetectedResult.getHotwordDurationMillis() != 0) {
            bitCount += bitCount(android.media.AudioRecord.getMaxSharedAudioHistoryMillis());
        }
        if (hotwordDetectedResult.getAudioChannel() != -1) {
            bitCount += bitCount(63L);
        }
        int i = bitCount + 1;
        if (hotwordDetectedResult.getScore() != defaultScore()) {
            i += bitCount(getMaxScore());
        }
        if (hotwordDetectedResult.getPersonalizedScore() != defaultPersonalizedScore()) {
            i += bitCount(getMaxScore());
        }
        if (hotwordDetectedResult.getHotwordPhraseId() != defaultHotwordPhraseId()) {
            i += bitCount(getMaxHotwordPhraseId());
        }
        android.os.PersistableBundle extras = hotwordDetectedResult.getExtras();
        if (!extras.isEmpty()) {
            i += getParcelableSize(extras) * 8;
        }
        if (hotwordDetectedResult.getBackgroundAudioPower() != defaultBackgroundAudioPower()) {
            return i + bitCount(getMaxBackgroundAudioPower());
        }
        return i;
    }

    private static int bitCount(long j) {
        int i = 0;
        while (j > 0) {
            i++;
            j >>= 1;
        }
        return i;
    }

    private void onConstructed() {
        com.android.internal.util.Preconditions.checkArgumentInRange(this.mSpeakerId, 0, getMaxSpeakerId(), "speakerId");
        com.android.internal.util.Preconditions.checkArgumentInRange(this.mScore, 0, getMaxScore(), "score");
        com.android.internal.util.Preconditions.checkArgumentInRange(this.mPersonalizedScore, 0, getMaxScore(), "personalizedScore");
        com.android.internal.util.Preconditions.checkArgumentInRange(this.mHotwordPhraseId, 0, getMaxHotwordPhraseId(), "hotwordPhraseId");
        if (this.mBackgroundAudioPower != -1) {
            com.android.internal.util.Preconditions.checkArgumentInRange(this.mBackgroundAudioPower, 0, getMaxBackgroundAudioPower(), "backgroundAudioPower");
        }
        com.android.internal.util.Preconditions.checkArgumentInRange(this.mHotwordDurationMillis, 0L, android.media.AudioRecord.getMaxSharedAudioHistoryMillis(), "hotwordDurationMillis");
        if (this.mHotwordOffsetMillis != -1) {
            com.android.internal.util.Preconditions.checkArgumentInRange(this.mHotwordOffsetMillis, 0, LIMIT_HOTWORD_OFFSET_MAX_VALUE, "hotwordOffsetMillis");
        }
        if (this.mAudioChannel != -1) {
            com.android.internal.util.Preconditions.checkArgumentInRange(this.mAudioChannel, 0, 63, "audioChannel");
        }
        if (!this.mExtras.isEmpty()) {
            if (this.mExtras.containsKey(EXTRA_PROXIMITY)) {
                int i = this.mExtras.getInt(EXTRA_PROXIMITY);
                this.mExtras.remove(EXTRA_PROXIMITY);
                if (this.mExtras.size() > 0) {
                    com.android.internal.util.Preconditions.checkArgumentInRange(getParcelableSize(this.mExtras), 0, getMaxBundleSize(), android.content.pm.AppSearchShortcutInfo.KEY_EXTRAS);
                }
                this.mExtras.putInt(EXTRA_PROXIMITY, i);
                return;
            }
            com.android.internal.util.Preconditions.checkArgumentInRange(getParcelableSize(this.mExtras), 0, getMaxBundleSize(), android.content.pm.AppSearchShortcutInfo.KEY_EXTRAS);
        }
    }

    public java.util.List<android.service.voice.HotwordAudioStream> getAudioStreams() {
        return java.util.List.copyOf(this.mAudioStreams);
    }

    public void setProximity(double d) {
        int convertToProximityLevel = convertToProximityLevel(d);
        if (convertToProximityLevel != -1) {
            this.mExtras.putInt(EXTRA_PROXIMITY, convertToProximityLevel);
        }
    }

    public int getProximity() {
        return this.mExtras.getInt(EXTRA_PROXIMITY, -1);
    }

    private int convertToProximityLevel(double d) {
        if (d < 0.0d) {
            return -1;
        }
        if (d <= 3.0d) {
            return 1;
        }
        return 2;
    }

    static abstract class BaseBuilder {
        BaseBuilder() {
        }

        public android.service.voice.HotwordDetectedResult.Builder setAudioStreams(java.util.List<android.service.voice.HotwordAudioStream> list) {
            java.util.Objects.requireNonNull(list, "value should not be null");
            android.service.voice.HotwordDetectedResult.Builder builder = (android.service.voice.HotwordDetectedResult.Builder) this;
            builder.mBuilderFieldsSet |= 1024;
            builder.mAudioStreams = java.util.List.copyOf(list);
            return builder;
        }
    }

    public android.service.voice.HotwordDetectedResult.Builder buildUpon() {
        return new android.service.voice.HotwordDetectedResult.Builder().setConfidenceLevel(this.mConfidenceLevel).setMediaSyncEvent(this.mMediaSyncEvent).setHotwordOffsetMillis(this.mHotwordOffsetMillis).setHotwordDurationMillis(this.mHotwordDurationMillis).setAudioChannel(this.mAudioChannel).setHotwordDetectionPersonalized(this.mHotwordDetectionPersonalized).setScore(this.mScore).setPersonalizedScore(this.mPersonalizedScore).setHotwordPhraseId(this.mHotwordPhraseId).setAudioStreams(this.mAudioStreams).setExtras(this.mExtras).setBackgroundAudioPower(this.mBackgroundAudioPower).setSpeakerId(this.mSpeakerId);
    }

    public static java.lang.String confidenceLevelToString(int i) {
        switch (i) {
            case 0:
                return "CONFIDENCE_LEVEL_NONE";
            case 1:
                return "CONFIDENCE_LEVEL_LOW";
            case 2:
                return "CONFIDENCE_LEVEL_LOW_MEDIUM";
            case 3:
                return "CONFIDENCE_LEVEL_MEDIUM";
            case 4:
                return "CONFIDENCE_LEVEL_MEDIUM_HIGH";
            case 5:
                return "CONFIDENCE_LEVEL_HIGH";
            case 6:
                return "CONFIDENCE_LEVEL_VERY_HIGH";
            default:
                return java.lang.Integer.toHexString(i);
        }
    }

    static java.lang.String limitToString(int i) {
        switch (i) {
            case 63:
                return "LIMIT_AUDIO_CHANNEL_MAX_VALUE";
            case LIMIT_HOTWORD_OFFSET_MAX_VALUE /* 3600000 */:
                return "LIMIT_HOTWORD_OFFSET_MAX_VALUE";
            default:
                return java.lang.Integer.toHexString(i);
        }
    }

    public static java.lang.String proximityToString(int i) {
        switch (i) {
            case -1:
                return "PROXIMITY_UNKNOWN";
            case 0:
            default:
                return java.lang.Integer.toHexString(i);
            case 1:
                return "PROXIMITY_NEAR";
            case 2:
                return "PROXIMITY_FAR";
        }
    }

    HotwordDetectedResult(int i, int i2, android.media.MediaSyncEvent mediaSyncEvent, int i3, int i4, int i5, boolean z, int i6, int i7, int i8, java.util.List<android.service.voice.HotwordAudioStream> list, android.os.PersistableBundle persistableBundle, int i9) {
        this.mMediaSyncEvent = null;
        this.mHotwordOffsetMillis = -1;
        this.mHotwordDurationMillis = 0;
        this.mAudioChannel = -1;
        this.mHotwordDetectionPersonalized = false;
        this.mSpeakerId = i;
        this.mConfidenceLevel = i2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.service.voice.HotwordDetectedResult.HotwordConfidenceLevelValue.class, (java.lang.annotation.Annotation) null, this.mConfidenceLevel);
        this.mMediaSyncEvent = mediaSyncEvent;
        this.mHotwordOffsetMillis = i3;
        this.mHotwordDurationMillis = i4;
        this.mAudioChannel = i5;
        this.mHotwordDetectionPersonalized = z;
        this.mScore = i6;
        this.mPersonalizedScore = i7;
        this.mHotwordPhraseId = i8;
        this.mAudioStreams = list;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mAudioStreams);
        this.mExtras = persistableBundle;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mExtras);
        this.mBackgroundAudioPower = i9;
        onConstructed();
    }

    public int getSpeakerId() {
        return this.mSpeakerId;
    }

    public int getConfidenceLevel() {
        return this.mConfidenceLevel;
    }

    public int getHotwordOffsetMillis() {
        return this.mHotwordOffsetMillis;
    }

    public int getHotwordDurationMillis() {
        return this.mHotwordDurationMillis;
    }

    public int getAudioChannel() {
        return this.mAudioChannel;
    }

    public boolean isHotwordDetectionPersonalized() {
        return this.mHotwordDetectionPersonalized;
    }

    public int getScore() {
        return this.mScore;
    }

    public int getPersonalizedScore() {
        return this.mPersonalizedScore;
    }

    public int getHotwordPhraseId() {
        return this.mHotwordPhraseId;
    }

    public android.os.PersistableBundle getExtras() {
        return this.mExtras;
    }

    public int getBackgroundAudioPower() {
        return this.mBackgroundAudioPower;
    }

    public java.lang.String toString() {
        return "HotwordDetectedResult { speakerId = " + this.mSpeakerId + ", confidenceLevel = " + this.mConfidenceLevel + ", mediaSyncEvent = " + this.mMediaSyncEvent + ", hotwordOffsetMillis = " + this.mHotwordOffsetMillis + ", hotwordDurationMillis = " + this.mHotwordDurationMillis + ", audioChannel = " + this.mAudioChannel + ", hotwordDetectionPersonalized = " + this.mHotwordDetectionPersonalized + ", score = " + this.mScore + ", personalizedScore = " + this.mPersonalizedScore + ", hotwordPhraseId = " + this.mHotwordPhraseId + ", audioStreams = " + this.mAudioStreams + ", extras = " + this.mExtras + ", backgroundAudioPower = " + this.mBackgroundAudioPower + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.service.voice.HotwordDetectedResult hotwordDetectedResult = (android.service.voice.HotwordDetectedResult) obj;
        if (this.mSpeakerId == hotwordDetectedResult.mSpeakerId && this.mConfidenceLevel == hotwordDetectedResult.mConfidenceLevel && java.util.Objects.equals(this.mMediaSyncEvent, hotwordDetectedResult.mMediaSyncEvent) && this.mHotwordOffsetMillis == hotwordDetectedResult.mHotwordOffsetMillis && this.mHotwordDurationMillis == hotwordDetectedResult.mHotwordDurationMillis && this.mAudioChannel == hotwordDetectedResult.mAudioChannel && this.mHotwordDetectionPersonalized == hotwordDetectedResult.mHotwordDetectionPersonalized && this.mScore == hotwordDetectedResult.mScore && this.mPersonalizedScore == hotwordDetectedResult.mPersonalizedScore && this.mHotwordPhraseId == hotwordDetectedResult.mHotwordPhraseId && java.util.Objects.equals(this.mAudioStreams, hotwordDetectedResult.mAudioStreams) && java.util.Objects.equals(this.mExtras, hotwordDetectedResult.mExtras) && this.mBackgroundAudioPower == hotwordDetectedResult.mBackgroundAudioPower) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((((((((((((((((((((this.mSpeakerId + 31) * 31) + this.mConfidenceLevel) * 31) + java.util.Objects.hashCode(this.mMediaSyncEvent)) * 31) + this.mHotwordOffsetMillis) * 31) + this.mHotwordDurationMillis) * 31) + this.mAudioChannel) * 31) + java.lang.Boolean.hashCode(this.mHotwordDetectionPersonalized)) * 31) + this.mScore) * 31) + this.mPersonalizedScore) * 31) + this.mHotwordPhraseId) * 31) + java.util.Objects.hashCode(this.mAudioStreams)) * 31) + java.util.Objects.hashCode(this.mExtras)) * 31) + this.mBackgroundAudioPower;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        int i2 = this.mHotwordDetectionPersonalized ? 64 : 0;
        if (this.mMediaSyncEvent != null) {
            i2 |= 4;
        }
        parcel.writeInt(i2);
        parcel.writeInt(this.mSpeakerId);
        parcel.writeInt(this.mConfidenceLevel);
        if (this.mMediaSyncEvent != null) {
            parcel.writeTypedObject(this.mMediaSyncEvent, i);
        }
        parcel.writeInt(this.mHotwordOffsetMillis);
        parcel.writeInt(this.mHotwordDurationMillis);
        parcel.writeInt(this.mAudioChannel);
        parcel.writeInt(this.mScore);
        parcel.writeInt(this.mPersonalizedScore);
        parcel.writeInt(this.mHotwordPhraseId);
        parcel.writeParcelableList(this.mAudioStreams, i);
        parcel.writeTypedObject(this.mExtras, i);
        parcel.writeInt(this.mBackgroundAudioPower);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    HotwordDetectedResult(android.os.Parcel parcel) {
        this.mMediaSyncEvent = null;
        this.mHotwordOffsetMillis = -1;
        this.mHotwordDurationMillis = 0;
        this.mAudioChannel = -1;
        this.mHotwordDetectionPersonalized = false;
        int readInt = parcel.readInt();
        boolean z = (readInt & 64) != 0;
        int readInt2 = parcel.readInt();
        int readInt3 = parcel.readInt();
        android.media.MediaSyncEvent mediaSyncEvent = (readInt & 4) == 0 ? null : (android.media.MediaSyncEvent) parcel.readTypedObject(android.media.MediaSyncEvent.CREATOR);
        int readInt4 = parcel.readInt();
        int readInt5 = parcel.readInt();
        int readInt6 = parcel.readInt();
        int readInt7 = parcel.readInt();
        int readInt8 = parcel.readInt();
        int readInt9 = parcel.readInt();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readParcelableList(arrayList, android.service.voice.HotwordAudioStream.class.getClassLoader());
        android.os.PersistableBundle persistableBundle = (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR);
        int readInt10 = parcel.readInt();
        this.mSpeakerId = readInt2;
        this.mConfidenceLevel = readInt3;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.service.voice.HotwordDetectedResult.HotwordConfidenceLevelValue.class, (java.lang.annotation.Annotation) null, this.mConfidenceLevel);
        this.mMediaSyncEvent = mediaSyncEvent;
        this.mHotwordOffsetMillis = readInt4;
        this.mHotwordDurationMillis = readInt5;
        this.mAudioChannel = readInt6;
        this.mHotwordDetectionPersonalized = z;
        this.mScore = readInt7;
        this.mPersonalizedScore = readInt8;
        this.mHotwordPhraseId = readInt9;
        this.mAudioStreams = arrayList;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mAudioStreams);
        this.mExtras = persistableBundle;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mExtras);
        this.mBackgroundAudioPower = readInt10;
        onConstructed();
    }

    public static final class Builder extends android.service.voice.HotwordDetectedResult.BaseBuilder {
        private int mAudioChannel;
        private java.util.List<android.service.voice.HotwordAudioStream> mAudioStreams;
        private int mBackgroundAudioPower;
        private long mBuilderFieldsSet = 0;
        private int mConfidenceLevel;
        private android.os.PersistableBundle mExtras;
        private boolean mHotwordDetectionPersonalized;
        private int mHotwordDurationMillis;
        private int mHotwordOffsetMillis;
        private int mHotwordPhraseId;
        private android.media.MediaSyncEvent mMediaSyncEvent;
        private int mPersonalizedScore;
        private int mScore;
        private int mSpeakerId;

        @Override // android.service.voice.HotwordDetectedResult.BaseBuilder
        public /* bridge */ /* synthetic */ android.service.voice.HotwordDetectedResult.Builder setAudioStreams(java.util.List list) {
            return super.setAudioStreams(list);
        }

        public android.service.voice.HotwordDetectedResult.Builder setSpeakerId(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mSpeakerId = i;
            return this;
        }

        public android.service.voice.HotwordDetectedResult.Builder setConfidenceLevel(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mConfidenceLevel = i;
            return this;
        }

        public android.service.voice.HotwordDetectedResult.Builder setMediaSyncEvent(android.media.MediaSyncEvent mediaSyncEvent) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mMediaSyncEvent = mediaSyncEvent;
            return this;
        }

        public android.service.voice.HotwordDetectedResult.Builder setHotwordOffsetMillis(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mHotwordOffsetMillis = i;
            return this;
        }

        public android.service.voice.HotwordDetectedResult.Builder setHotwordDurationMillis(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            this.mHotwordDurationMillis = i;
            return this;
        }

        public android.service.voice.HotwordDetectedResult.Builder setAudioChannel(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 32;
            this.mAudioChannel = i;
            return this;
        }

        public android.service.voice.HotwordDetectedResult.Builder setHotwordDetectionPersonalized(boolean z) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 64;
            this.mHotwordDetectionPersonalized = z;
            return this;
        }

        public android.service.voice.HotwordDetectedResult.Builder setScore(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 128;
            this.mScore = i;
            return this;
        }

        public android.service.voice.HotwordDetectedResult.Builder setPersonalizedScore(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 256;
            this.mPersonalizedScore = i;
            return this;
        }

        public android.service.voice.HotwordDetectedResult.Builder setHotwordPhraseId(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 512;
            this.mHotwordPhraseId = i;
            return this;
        }

        public android.service.voice.HotwordDetectedResult.Builder setExtras(android.os.PersistableBundle persistableBundle) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2048;
            this.mExtras = persistableBundle;
            return this;
        }

        public android.service.voice.HotwordDetectedResult.Builder setBackgroundAudioPower(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4096;
            this.mBackgroundAudioPower = i;
            return this;
        }

        public android.service.voice.HotwordDetectedResult build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8192;
            if ((this.mBuilderFieldsSet & 1) == 0) {
                this.mSpeakerId = android.service.voice.HotwordDetectedResult.defaultSpeakerId();
            }
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mConfidenceLevel = android.service.voice.HotwordDetectedResult.defaultConfidenceLevel();
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mMediaSyncEvent = null;
            }
            if ((this.mBuilderFieldsSet & 8) == 0) {
                this.mHotwordOffsetMillis = -1;
            }
            if ((this.mBuilderFieldsSet & 16) == 0) {
                this.mHotwordDurationMillis = 0;
            }
            if ((this.mBuilderFieldsSet & 32) == 0) {
                this.mAudioChannel = -1;
            }
            if ((this.mBuilderFieldsSet & 64) == 0) {
                this.mHotwordDetectionPersonalized = false;
            }
            if ((this.mBuilderFieldsSet & 128) == 0) {
                this.mScore = android.service.voice.HotwordDetectedResult.defaultScore();
            }
            if ((this.mBuilderFieldsSet & 256) == 0) {
                this.mPersonalizedScore = android.service.voice.HotwordDetectedResult.defaultPersonalizedScore();
            }
            if ((this.mBuilderFieldsSet & 512) == 0) {
                this.mHotwordPhraseId = android.service.voice.HotwordDetectedResult.defaultHotwordPhraseId();
            }
            if ((this.mBuilderFieldsSet & 1024) == 0) {
                this.mAudioStreams = android.service.voice.HotwordDetectedResult.defaultAudioStreams();
            }
            if ((this.mBuilderFieldsSet & 2048) == 0) {
                this.mExtras = android.service.voice.HotwordDetectedResult.defaultExtras();
            }
            if ((this.mBuilderFieldsSet & 4096) == 0) {
                this.mBackgroundAudioPower = android.service.voice.HotwordDetectedResult.defaultBackgroundAudioPower();
            }
            return new android.service.voice.HotwordDetectedResult(this.mSpeakerId, this.mConfidenceLevel, this.mMediaSyncEvent, this.mHotwordOffsetMillis, this.mHotwordDurationMillis, this.mAudioChannel, this.mHotwordDetectionPersonalized, this.mScore, this.mPersonalizedScore, this.mHotwordPhraseId, this.mAudioStreams, this.mExtras, this.mBackgroundAudioPower);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 8192) != 0) {
                throw new java.lang.IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
