package android.media;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class FadeManagerConfiguration implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.FadeManagerConfiguration> CREATOR = new android.os.Parcelable.Creator<android.media.FadeManagerConfiguration>() { // from class: android.media.FadeManagerConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.FadeManagerConfiguration createFromParcel(android.os.Parcel parcel) {
            return new android.media.FadeManagerConfiguration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.FadeManagerConfiguration[] newArray(int i) {
            return new android.media.FadeManagerConfiguration[i];
        }
    };
    private static final long DEFAULT_FADE_IN_DURATION_MS = 1000;
    private static final long DEFAULT_FADE_OUT_DURATION_MS = 2000;
    public static final long DURATION_NOT_SET = 0;
    public static final int FADE_STATE_DISABLED = 0;
    public static final int FADE_STATE_ENABLED_DEFAULT = 1;
    public static final java.lang.String TAG = "FadeManagerConfiguration";
    public static final int VOLUME_SHAPER_SYSTEM_FADE_ID = 2;
    private final android.util.ArrayMap<android.media.AudioAttributes, android.media.FadeManagerConfiguration.FadeVolumeShaperConfigsWrapper> mAttrToFadeWrapperMap;
    private final long mFadeInDelayForOffendersMillis;
    private final long mFadeInDurationMillis;
    private final long mFadeOutDurationMillis;
    private final int mFadeState;
    private final android.util.IntArray mFadeableUsages;
    private final java.util.List<android.media.AudioAttributes> mUnfadeableAudioAttributes;
    private final android.util.IntArray mUnfadeableContentTypes;
    private final android.util.IntArray mUnfadeablePlayerTypes;
    private final android.util.IntArray mUnfadeableUids;
    private final android.util.SparseArray<android.media.FadeManagerConfiguration.FadeVolumeShaperConfigsWrapper> mUsageToFadeWrapperMap;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FadeStateEnum {
    }

    private FadeManagerConfiguration(int i, long j, long j2, long j3, android.util.SparseArray<android.media.FadeManagerConfiguration.FadeVolumeShaperConfigsWrapper> sparseArray, android.util.ArrayMap<android.media.AudioAttributes, android.media.FadeManagerConfiguration.FadeVolumeShaperConfigsWrapper> arrayMap, android.util.IntArray intArray, android.util.IntArray intArray2, android.util.IntArray intArray3, android.util.IntArray intArray4, java.util.List<android.media.AudioAttributes> list) {
        this.mFadeState = i;
        this.mFadeOutDurationMillis = j;
        this.mFadeInDurationMillis = j2;
        this.mFadeInDelayForOffendersMillis = j3;
        this.mUsageToFadeWrapperMap = (android.util.SparseArray) java.util.Objects.requireNonNull(sparseArray, "Usage to fade wrapper map cannot be null");
        this.mAttrToFadeWrapperMap = (android.util.ArrayMap) java.util.Objects.requireNonNull(arrayMap, "Attribute to fade wrapper map cannot be null");
        this.mFadeableUsages = (android.util.IntArray) java.util.Objects.requireNonNull(intArray, "List of fadeable usages cannot be null");
        this.mUnfadeableContentTypes = (android.util.IntArray) java.util.Objects.requireNonNull(intArray2, "List of unfadeable content types cannot be null");
        this.mUnfadeablePlayerTypes = (android.util.IntArray) java.util.Objects.requireNonNull(intArray3, "List of unfadeable player types cannot be null");
        this.mUnfadeableUids = (android.util.IntArray) java.util.Objects.requireNonNull(intArray4, "List of unfadeable uids cannot be null");
        this.mUnfadeableAudioAttributes = (java.util.List) java.util.Objects.requireNonNull(list, "List of unfadeable audio attributes cannot be null");
    }

    public int getFadeState() {
        return this.mFadeState;
    }

    public java.util.List<java.lang.Integer> getFadeableUsages() {
        ensureFadingIsEnabled();
        return convertIntArrayToIntegerList(this.mFadeableUsages);
    }

    public java.util.List<java.lang.Integer> getUnfadeablePlayerTypes() {
        ensureFadingIsEnabled();
        return convertIntArrayToIntegerList(this.mUnfadeablePlayerTypes);
    }

    public java.util.List<java.lang.Integer> getUnfadeableContentTypes() {
        ensureFadingIsEnabled();
        return convertIntArrayToIntegerList(this.mUnfadeableContentTypes);
    }

    public java.util.List<java.lang.Integer> getUnfadeableUids() {
        ensureFadingIsEnabled();
        return convertIntArrayToIntegerList(this.mUnfadeableUids);
    }

    public java.util.List<android.media.AudioAttributes> getUnfadeableAudioAttributes() {
        ensureFadingIsEnabled();
        return this.mUnfadeableAudioAttributes;
    }

    public long getFadeOutDurationForUsage(int i) {
        ensureFadingIsEnabled();
        validateUsage(i);
        return getDurationForVolumeShaperConfig(getVolumeShaperConfigFromWrapper(this.mUsageToFadeWrapperMap.get(i), false));
    }

    public long getFadeInDurationForUsage(int i) {
        ensureFadingIsEnabled();
        validateUsage(i);
        return getDurationForVolumeShaperConfig(getVolumeShaperConfigFromWrapper(this.mUsageToFadeWrapperMap.get(i), true));
    }

    public android.media.VolumeShaper.Configuration getFadeOutVolumeShaperConfigForUsage(int i) {
        ensureFadingIsEnabled();
        validateUsage(i);
        return getVolumeShaperConfigFromWrapper(this.mUsageToFadeWrapperMap.get(i), false);
    }

    public android.media.VolumeShaper.Configuration getFadeInVolumeShaperConfigForUsage(int i) {
        ensureFadingIsEnabled();
        validateUsage(i);
        return getVolumeShaperConfigFromWrapper(this.mUsageToFadeWrapperMap.get(i), true);
    }

    public long getFadeOutDurationForAudioAttributes(android.media.AudioAttributes audioAttributes) {
        ensureFadingIsEnabled();
        return getDurationForVolumeShaperConfig(getVolumeShaperConfigFromWrapper(this.mAttrToFadeWrapperMap.get(audioAttributes), false));
    }

    public long getFadeInDurationForAudioAttributes(android.media.AudioAttributes audioAttributes) {
        ensureFadingIsEnabled();
        return getDurationForVolumeShaperConfig(getVolumeShaperConfigFromWrapper(this.mAttrToFadeWrapperMap.get(audioAttributes), true));
    }

    public android.media.VolumeShaper.Configuration getFadeOutVolumeShaperConfigForAudioAttributes(android.media.AudioAttributes audioAttributes) {
        java.util.Objects.requireNonNull(audioAttributes, "Audio attributes cannot be null");
        ensureFadingIsEnabled();
        return getVolumeShaperConfigFromWrapper(this.mAttrToFadeWrapperMap.get(audioAttributes), false);
    }

    public android.media.VolumeShaper.Configuration getFadeInVolumeShaperConfigForAudioAttributes(android.media.AudioAttributes audioAttributes) {
        java.util.Objects.requireNonNull(audioAttributes, "Audio attributes cannot be null");
        ensureFadingIsEnabled();
        return getVolumeShaperConfigFromWrapper(this.mAttrToFadeWrapperMap.get(audioAttributes), true);
    }

    public java.util.List<android.media.AudioAttributes> getAudioAttributesWithVolumeShaperConfigs() {
        return getAudioAttributesInternal();
    }

    public long getFadeInDelayForOffenders() {
        return this.mFadeInDelayForOffendersMillis;
    }

    public boolean isFadeEnabled() {
        return this.mFadeState != 0;
    }

    public boolean isUsageFadeable(int i) {
        if (!isFadeEnabled()) {
            return false;
        }
        return this.mFadeableUsages.contains(i);
    }

    public boolean isContentTypeUnfadeable(int i) {
        if (!isFadeEnabled()) {
            return true;
        }
        return this.mUnfadeableContentTypes.contains(i);
    }

    public boolean isPlayerTypeUnfadeable(int i) {
        if (!isFadeEnabled()) {
            return true;
        }
        return this.mUnfadeablePlayerTypes.contains(i);
    }

    public boolean isAudioAttributesUnfadeable(android.media.AudioAttributes audioAttributes) {
        java.util.Objects.requireNonNull(audioAttributes, "Audio attributes cannot be null");
        if (!isFadeEnabled()) {
            return true;
        }
        return this.mUnfadeableAudioAttributes.contains(audioAttributes);
    }

    public boolean isUidUnfadeable(int i) {
        if (!isFadeEnabled()) {
            return true;
        }
        return this.mUnfadeableUids.contains(i);
    }

    public static long getDefaultFadeOutDurationMillis() {
        return DEFAULT_FADE_OUT_DURATION_MS;
    }

    public static long getDefaultFadeInDurationMillis() {
        return 1000L;
    }

    public java.lang.String toString() {
        return "FadeManagerConfiguration { fade state = " + fadeStateToString(this.mFadeState) + ", fade out duration = " + this.mFadeOutDurationMillis + ", fade in duration = " + this.mFadeInDurationMillis + ", offenders fade in delay = " + this.mFadeInDelayForOffendersMillis + ", fade volume shapers for audio attributes = " + this.mAttrToFadeWrapperMap + ", fadeable usages = " + this.mFadeableUsages.toString() + ", unfadeable content types = " + this.mUnfadeableContentTypes.toString() + ", unfadeable player types = " + this.mUnfadeablePlayerTypes.toString() + ", unfadeable uids = " + this.mUnfadeableUids.toString() + ", unfadeable audio attributes = " + this.mUnfadeableAudioAttributes + "}";
    }

    public static java.lang.String fadeStateToString(int i) {
        switch (i) {
            case 0:
                return "FADE_STATE_DISABLED";
            case 1:
                return "FADE_STATE_ENABLED_DEFAULT";
            default:
                return "unknown fade state: " + i;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.media.FadeManagerConfiguration)) {
            return false;
        }
        android.media.FadeManagerConfiguration fadeManagerConfiguration = (android.media.FadeManagerConfiguration) obj;
        return this.mUsageToFadeWrapperMap.contentEquals(fadeManagerConfiguration.mUsageToFadeWrapperMap) && this.mAttrToFadeWrapperMap.equals(fadeManagerConfiguration.mAttrToFadeWrapperMap) && java.util.Arrays.equals(this.mFadeableUsages.toArray(), fadeManagerConfiguration.mFadeableUsages.toArray()) && java.util.Arrays.equals(this.mUnfadeableContentTypes.toArray(), fadeManagerConfiguration.mUnfadeableContentTypes.toArray()) && java.util.Arrays.equals(this.mUnfadeablePlayerTypes.toArray(), fadeManagerConfiguration.mUnfadeablePlayerTypes.toArray()) && java.util.Arrays.equals(this.mUnfadeableUids.toArray(), fadeManagerConfiguration.mUnfadeableUids.toArray()) && this.mUnfadeableAudioAttributes.equals(fadeManagerConfiguration.mUnfadeableAudioAttributes) && this.mFadeState == fadeManagerConfiguration.mFadeState && this.mFadeOutDurationMillis == fadeManagerConfiguration.mFadeOutDurationMillis && this.mFadeInDurationMillis == fadeManagerConfiguration.mFadeInDurationMillis && this.mFadeInDelayForOffendersMillis == fadeManagerConfiguration.mFadeInDelayForOffendersMillis;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mUsageToFadeWrapperMap, this.mAttrToFadeWrapperMap, this.mFadeableUsages, this.mUnfadeableContentTypes, this.mUnfadeablePlayerTypes, this.mUnfadeableAudioAttributes, this.mUnfadeableUids, java.lang.Integer.valueOf(this.mFadeState), java.lang.Long.valueOf(this.mFadeOutDurationMillis), java.lang.Long.valueOf(this.mFadeInDurationMillis), java.lang.Long.valueOf(this.mFadeInDelayForOffendersMillis));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mFadeState);
        parcel.writeLong(this.mFadeOutDurationMillis);
        parcel.writeLong(this.mFadeInDurationMillis);
        parcel.writeLong(this.mFadeInDelayForOffendersMillis);
        parcel.writeTypedSparseArray(this.mUsageToFadeWrapperMap, i);
        parcel.writeMap(this.mAttrToFadeWrapperMap);
        parcel.writeIntArray(this.mFadeableUsages.toArray());
        parcel.writeIntArray(this.mUnfadeableContentTypes.toArray());
        parcel.writeIntArray(this.mUnfadeablePlayerTypes.toArray());
        parcel.writeIntArray(this.mUnfadeableUids.toArray());
        parcel.writeTypedList(this.mUnfadeableAudioAttributes, i);
    }

    FadeManagerConfiguration(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        long readLong = parcel.readLong();
        long readLong2 = parcel.readLong();
        long readLong3 = parcel.readLong();
        android.util.SparseArray<android.media.FadeManagerConfiguration.FadeVolumeShaperConfigsWrapper> createTypedSparseArray = parcel.createTypedSparseArray(android.media.FadeManagerConfiguration.FadeVolumeShaperConfigsWrapper.CREATOR);
        android.util.ArrayMap<android.media.AudioAttributes, android.media.FadeManagerConfiguration.FadeVolumeShaperConfigsWrapper> arrayMap = new android.util.ArrayMap<>();
        parcel.readMap(arrayMap, getClass().getClassLoader(), android.media.AudioAttributes.class, android.media.FadeManagerConfiguration.FadeVolumeShaperConfigsWrapper.class);
        int[] createIntArray = parcel.createIntArray();
        int[] createIntArray2 = parcel.createIntArray();
        int[] createIntArray3 = parcel.createIntArray();
        int[] createIntArray4 = parcel.createIntArray();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readTypedList(arrayList, android.media.AudioAttributes.CREATOR);
        this.mFadeState = readInt;
        this.mFadeOutDurationMillis = readLong;
        this.mFadeInDurationMillis = readLong2;
        this.mFadeInDelayForOffendersMillis = readLong3;
        this.mUsageToFadeWrapperMap = createTypedSparseArray;
        this.mAttrToFadeWrapperMap = arrayMap;
        this.mFadeableUsages = android.util.IntArray.wrap(createIntArray);
        this.mUnfadeableContentTypes = android.util.IntArray.wrap(createIntArray2);
        this.mUnfadeablePlayerTypes = android.util.IntArray.wrap(createIntArray3);
        this.mUnfadeableUids = android.util.IntArray.wrap(createIntArray4);
        this.mUnfadeableAudioAttributes = arrayList;
    }

    private long getDurationForVolumeShaperConfig(android.media.VolumeShaper.Configuration configuration) {
        if (configuration != null) {
            return configuration.getDuration();
        }
        return 0L;
    }

    private android.media.VolumeShaper.Configuration getVolumeShaperConfigFromWrapper(android.media.FadeManagerConfiguration.FadeVolumeShaperConfigsWrapper fadeVolumeShaperConfigsWrapper, boolean z) {
        if (fadeVolumeShaperConfigsWrapper == null) {
            return null;
        }
        if (z) {
            return fadeVolumeShaperConfigsWrapper.getFadeInVolShaperConfig();
        }
        return fadeVolumeShaperConfigsWrapper.getFadeOutVolShaperConfig();
    }

    private java.util.List<android.media.AudioAttributes> getAudioAttributesInternal() {
        java.util.ArrayList arrayList = new java.util.ArrayList(this.mAttrToFadeWrapperMap.size());
        for (int i = 0; i < this.mAttrToFadeWrapperMap.size(); i++) {
            arrayList.add(this.mAttrToFadeWrapperMap.keyAt(i));
        }
        return arrayList;
    }

    private static boolean isUsageValid(int i) {
        return android.media.AudioAttributes.isSdkUsage(i) || android.media.AudioAttributes.isSystemUsage(i);
    }

    private void ensureFadingIsEnabled() {
        if (!isFadeEnabled()) {
            throw new java.lang.IllegalStateException("Method call not allowed when fade is disabled");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void validateUsage(int i) {
        com.android.internal.util.Preconditions.checkArgument(isUsageValid(i), "Invalid usage: %s", java.lang.Integer.valueOf(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.util.IntArray convertIntegerListToIntArray(java.util.List<java.lang.Integer> list) {
        if (list == null) {
            return new android.util.IntArray();
        }
        android.util.IntArray intArray = new android.util.IntArray(list.size());
        for (int i = 0; i < list.size(); i++) {
            intArray.add(list.get(i).intValue());
        }
        return intArray;
    }

    private static java.util.List<java.lang.Integer> convertIntArrayToIntegerList(android.util.IntArray intArray) {
        if (intArray == null) {
            return new java.util.ArrayList();
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(intArray.size());
        for (int i = 0; i < intArray.size(); i++) {
            arrayList.add(java.lang.Integer.valueOf(intArray.get(i)));
        }
        return arrayList;
    }

    public static final class Builder {
        private static final long DEFAULT_DELAY_FADE_IN_OFFENDERS_MS = 2000;
        private static final int INVALID_INDEX = -1;
        private static final long IS_BUILDER_USED_FIELD_SET = 1;
        private static final long IS_FADEABLE_USAGES_FIELD_SET = 2;
        private static final long IS_UNFADEABLE_CONTENT_TYPE_FIELD_SET = 4;
        private android.util.ArrayMap<android.media.AudioAttributes, android.media.FadeManagerConfiguration.FadeVolumeShaperConfigsWrapper> mAttrToFadeWrapperMap;
        private long mBuilderFieldsSet;
        private long mFadeInDelayForOffendersMillis;
        private long mFadeInDurationMillis;
        private long mFadeOutDurationMillis;
        private int mFadeState;
        private android.util.IntArray mFadeableUsages;
        private java.util.List<android.media.AudioAttributes> mUnfadeableAudioAttributes;
        private android.util.IntArray mUnfadeableContentTypes;
        private android.util.IntArray mUnfadeablePlayerTypes;
        private android.util.IntArray mUnfadeableUids;
        private android.util.SparseArray<android.media.FadeManagerConfiguration.FadeVolumeShaperConfigsWrapper> mUsageToFadeWrapperMap;
        private static final android.util.IntArray DEFAULT_UNFADEABLE_PLAYER_TYPES = android.util.IntArray.wrap(new int[]{13, 3});
        private static final android.util.IntArray DEFAULT_UNFADEABLE_CONTENT_TYPES = android.util.IntArray.wrap(new int[]{1});
        private static final android.util.IntArray DEFAULT_FADEABLE_USAGES = android.util.IntArray.wrap(new int[]{14, 1});

        public Builder() {
            this.mFadeState = 1;
            this.mFadeInDelayForOffendersMillis = DEFAULT_DELAY_FADE_IN_OFFENDERS_MS;
            this.mUsageToFadeWrapperMap = new android.util.SparseArray<>();
            this.mAttrToFadeWrapperMap = new android.util.ArrayMap<>();
            this.mFadeableUsages = new android.util.IntArray();
            this.mUnfadeableContentTypes = new android.util.IntArray();
            this.mUnfadeablePlayerTypes = DEFAULT_UNFADEABLE_PLAYER_TYPES;
            this.mUnfadeableUids = new android.util.IntArray();
            this.mUnfadeableAudioAttributes = new java.util.ArrayList();
            this.mFadeOutDurationMillis = DEFAULT_DELAY_FADE_IN_OFFENDERS_MS;
            this.mFadeInDurationMillis = 1000L;
        }

        public Builder(long j, long j2) {
            this.mFadeState = 1;
            this.mFadeInDelayForOffendersMillis = DEFAULT_DELAY_FADE_IN_OFFENDERS_MS;
            this.mUsageToFadeWrapperMap = new android.util.SparseArray<>();
            this.mAttrToFadeWrapperMap = new android.util.ArrayMap<>();
            this.mFadeableUsages = new android.util.IntArray();
            this.mUnfadeableContentTypes = new android.util.IntArray();
            this.mUnfadeablePlayerTypes = DEFAULT_UNFADEABLE_PLAYER_TYPES;
            this.mUnfadeableUids = new android.util.IntArray();
            this.mUnfadeableAudioAttributes = new java.util.ArrayList();
            this.mFadeOutDurationMillis = j;
            this.mFadeInDurationMillis = j2;
        }

        public Builder(android.media.FadeManagerConfiguration fadeManagerConfiguration) {
            this.mFadeState = 1;
            this.mFadeInDelayForOffendersMillis = DEFAULT_DELAY_FADE_IN_OFFENDERS_MS;
            this.mUsageToFadeWrapperMap = new android.util.SparseArray<>();
            this.mAttrToFadeWrapperMap = new android.util.ArrayMap<>();
            this.mFadeableUsages = new android.util.IntArray();
            this.mUnfadeableContentTypes = new android.util.IntArray();
            this.mUnfadeablePlayerTypes = DEFAULT_UNFADEABLE_PLAYER_TYPES;
            this.mUnfadeableUids = new android.util.IntArray();
            this.mUnfadeableAudioAttributes = new java.util.ArrayList();
            this.mFadeState = fadeManagerConfiguration.mFadeState;
            this.mUsageToFadeWrapperMap = fadeManagerConfiguration.mUsageToFadeWrapperMap.m4835clone();
            this.mAttrToFadeWrapperMap = new android.util.ArrayMap<>(fadeManagerConfiguration.mAttrToFadeWrapperMap);
            this.mFadeableUsages = fadeManagerConfiguration.mFadeableUsages.m4809clone();
            setFlag(2L);
            this.mUnfadeableContentTypes = fadeManagerConfiguration.mUnfadeableContentTypes.m4809clone();
            setFlag(4L);
            this.mUnfadeablePlayerTypes = fadeManagerConfiguration.mUnfadeablePlayerTypes.m4809clone();
            this.mUnfadeableUids = fadeManagerConfiguration.mUnfadeableUids.m4809clone();
            this.mUnfadeableAudioAttributes = new java.util.ArrayList(fadeManagerConfiguration.mUnfadeableAudioAttributes);
            this.mFadeOutDurationMillis = fadeManagerConfiguration.mFadeOutDurationMillis;
            this.mFadeInDurationMillis = fadeManagerConfiguration.mFadeInDurationMillis;
        }

        public android.media.FadeManagerConfiguration.Builder setFadeState(int i) {
            validateFadeState(i);
            this.mFadeState = i;
            return this;
        }

        public android.media.FadeManagerConfiguration.Builder setFadeOutVolumeShaperConfigForUsage(int i, android.media.VolumeShaper.Configuration configuration) {
            android.media.FadeManagerConfiguration.validateUsage(i);
            getFadeVolShaperConfigWrapperForUsage(i).setFadeOutVolShaperConfig(configuration);
            cleanupInactiveWrapperEntries(i);
            return this;
        }

        public android.media.FadeManagerConfiguration.Builder setFadeInVolumeShaperConfigForUsage(int i, android.media.VolumeShaper.Configuration configuration) {
            android.media.FadeManagerConfiguration.validateUsage(i);
            getFadeVolShaperConfigWrapperForUsage(i).setFadeInVolShaperConfig(configuration);
            cleanupInactiveWrapperEntries(i);
            return this;
        }

        public android.media.FadeManagerConfiguration.Builder setFadeOutDurationForUsage(int i, long j) {
            android.media.FadeManagerConfiguration.validateUsage(i);
            setFadeOutVolumeShaperConfigForUsage(i, createVolShaperConfigForDuration(j, false));
            return this;
        }

        public android.media.FadeManagerConfiguration.Builder setFadeInDurationForUsage(int i, long j) {
            android.media.FadeManagerConfiguration.validateUsage(i);
            setFadeInVolumeShaperConfigForUsage(i, createVolShaperConfigForDuration(j, true));
            return this;
        }

        public android.media.FadeManagerConfiguration.Builder setFadeOutVolumeShaperConfigForAudioAttributes(android.media.AudioAttributes audioAttributes, android.media.VolumeShaper.Configuration configuration) {
            java.util.Objects.requireNonNull(audioAttributes, "Audio attribute cannot be null");
            getFadeVolShaperConfigWrapperForAttr(audioAttributes).setFadeOutVolShaperConfig(configuration);
            cleanupInactiveWrapperEntries(audioAttributes);
            return this;
        }

        public android.media.FadeManagerConfiguration.Builder setFadeInVolumeShaperConfigForAudioAttributes(android.media.AudioAttributes audioAttributes, android.media.VolumeShaper.Configuration configuration) {
            java.util.Objects.requireNonNull(audioAttributes, "Audio attribute cannot be null");
            getFadeVolShaperConfigWrapperForAttr(audioAttributes).setFadeInVolShaperConfig(configuration);
            cleanupInactiveWrapperEntries(audioAttributes);
            return this;
        }

        public android.media.FadeManagerConfiguration.Builder setFadeOutDurationForAudioAttributes(android.media.AudioAttributes audioAttributes, long j) {
            java.util.Objects.requireNonNull(audioAttributes, "Audio attribute cannot be null");
            setFadeOutVolumeShaperConfigForAudioAttributes(audioAttributes, createVolShaperConfigForDuration(j, false));
            return this;
        }

        public android.media.FadeManagerConfiguration.Builder setFadeInDurationForAudioAttributes(android.media.AudioAttributes audioAttributes, long j) {
            java.util.Objects.requireNonNull(audioAttributes, "Audio attribute cannot be null");
            setFadeInVolumeShaperConfigForAudioAttributes(audioAttributes, createVolShaperConfigForDuration(j, true));
            return this;
        }

        public android.media.FadeManagerConfiguration.Builder setFadeableUsages(java.util.List<java.lang.Integer> list) {
            java.util.Objects.requireNonNull(list, "List of usages cannot be null");
            validateUsages(list);
            setFlag(2L);
            this.mFadeableUsages.clear();
            this.mFadeableUsages.addAll(android.media.FadeManagerConfiguration.convertIntegerListToIntArray(list));
            return this;
        }

        public android.media.FadeManagerConfiguration.Builder addFadeableUsage(int i) {
            android.media.FadeManagerConfiguration.validateUsage(i);
            setFlag(2L);
            if (!this.mFadeableUsages.contains(i)) {
                this.mFadeableUsages.add(i);
            }
            return this;
        }

        public android.media.FadeManagerConfiguration.Builder clearFadeableUsages() {
            setFlag(2L);
            this.mFadeableUsages.clear();
            return this;
        }

        public android.media.FadeManagerConfiguration.Builder setUnfadeableContentTypes(java.util.List<java.lang.Integer> list) {
            java.util.Objects.requireNonNull(list, "List of content types cannot be null");
            validateContentTypes(list);
            setFlag(4L);
            this.mUnfadeableContentTypes.clear();
            this.mUnfadeableContentTypes.addAll(android.media.FadeManagerConfiguration.convertIntegerListToIntArray(list));
            return this;
        }

        public android.media.FadeManagerConfiguration.Builder addUnfadeableContentType(int i) {
            validateContentType(i);
            setFlag(4L);
            if (!this.mUnfadeableContentTypes.contains(i)) {
                this.mUnfadeableContentTypes.add(i);
            }
            return this;
        }

        public android.media.FadeManagerConfiguration.Builder clearUnfadeableContentTypes() {
            setFlag(4L);
            this.mUnfadeableContentTypes.clear();
            return this;
        }

        public android.media.FadeManagerConfiguration.Builder setUnfadeableUids(java.util.List<java.lang.Integer> list) {
            java.util.Objects.requireNonNull(list, "List of uids cannot be null");
            this.mUnfadeableUids.clear();
            this.mUnfadeableUids.addAll(android.media.FadeManagerConfiguration.convertIntegerListToIntArray(list));
            return this;
        }

        public android.media.FadeManagerConfiguration.Builder addUnfadeableUid(int i) {
            if (!this.mUnfadeableUids.contains(i)) {
                this.mUnfadeableUids.add(i);
            }
            return this;
        }

        public android.media.FadeManagerConfiguration.Builder clearUnfadeableUids() {
            this.mUnfadeableUids.clear();
            return this;
        }

        public android.media.FadeManagerConfiguration.Builder setUnfadeableAudioAttributes(java.util.List<android.media.AudioAttributes> list) {
            java.util.Objects.requireNonNull(list, "List of audio attributes cannot be null");
            this.mUnfadeableAudioAttributes.clear();
            this.mUnfadeableAudioAttributes.addAll(list);
            return this;
        }

        public android.media.FadeManagerConfiguration.Builder addUnfadeableAudioAttributes(android.media.AudioAttributes audioAttributes) {
            java.util.Objects.requireNonNull(audioAttributes, "Audio attributes cannot be null");
            if (!this.mUnfadeableAudioAttributes.contains(audioAttributes)) {
                this.mUnfadeableAudioAttributes.add(audioAttributes);
            }
            return this;
        }

        public android.media.FadeManagerConfiguration.Builder clearUnfadeableAudioAttributes() {
            this.mUnfadeableAudioAttributes.clear();
            return this;
        }

        public android.media.FadeManagerConfiguration.Builder setFadeInDelayForOffenders(long j) {
            com.android.internal.util.Preconditions.checkArgument(j >= 0, "Delay cannot be negative");
            this.mFadeInDelayForOffendersMillis = j;
            return this;
        }

        public android.media.FadeManagerConfiguration build() {
            if (!checkNotSet(1L)) {
                throw new java.lang.IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
            setFlag(1L);
            if (checkNotSet(2L)) {
                this.mFadeableUsages = DEFAULT_FADEABLE_USAGES;
                setVolShaperConfigsForUsages(this.mFadeableUsages);
            }
            if (checkNotSet(4L)) {
                this.mUnfadeableContentTypes = DEFAULT_UNFADEABLE_CONTENT_TYPES;
            }
            validateFadeConfigurations();
            return new android.media.FadeManagerConfiguration(this.mFadeState, this.mFadeOutDurationMillis, this.mFadeInDurationMillis, this.mFadeInDelayForOffendersMillis, this.mUsageToFadeWrapperMap, this.mAttrToFadeWrapperMap, this.mFadeableUsages, this.mUnfadeableContentTypes, this.mUnfadeablePlayerTypes, this.mUnfadeableUids, this.mUnfadeableAudioAttributes);
        }

        private void setFlag(long j) {
            this.mBuilderFieldsSet = j | this.mBuilderFieldsSet;
        }

        private boolean checkNotSet(long j) {
            return (j & this.mBuilderFieldsSet) == 0;
        }

        private android.media.FadeManagerConfiguration.FadeVolumeShaperConfigsWrapper getFadeVolShaperConfigWrapperForUsage(int i) {
            if (!this.mUsageToFadeWrapperMap.contains(i)) {
                this.mUsageToFadeWrapperMap.put(i, new android.media.FadeManagerConfiguration.FadeVolumeShaperConfigsWrapper());
            }
            return this.mUsageToFadeWrapperMap.get(i);
        }

        private android.media.FadeManagerConfiguration.FadeVolumeShaperConfigsWrapper getFadeVolShaperConfigWrapperForAttr(android.media.AudioAttributes audioAttributes) {
            if (!this.mAttrToFadeWrapperMap.containsKey(audioAttributes)) {
                this.mAttrToFadeWrapperMap.put(audioAttributes, new android.media.FadeManagerConfiguration.FadeVolumeShaperConfigsWrapper());
            }
            return this.mAttrToFadeWrapperMap.get(audioAttributes);
        }

        private android.media.VolumeShaper.Configuration createVolShaperConfigForDuration(long j, boolean z) {
            if (j == 0) {
                return null;
            }
            android.media.VolumeShaper.Configuration.Builder duration = new android.media.VolumeShaper.Configuration.Builder().setId(2).setOptionFlags(2).setDuration(j);
            if (z) {
                duration.setCurve(new float[]{0.0f, 0.5f, 1.0f}, new float[]{0.0f, 0.3f, 1.0f});
            } else {
                duration.setCurve(new float[]{0.0f, 0.25f, 1.0f}, new float[]{1.0f, 0.65f, 0.0f});
            }
            return duration.build();
        }

        private void cleanupInactiveWrapperEntries(int i) {
            android.media.FadeManagerConfiguration.FadeVolumeShaperConfigsWrapper fadeVolumeShaperConfigsWrapper = this.mUsageToFadeWrapperMap.get(i);
            if (fadeVolumeShaperConfigsWrapper != null && fadeVolumeShaperConfigsWrapper.isInactive()) {
                this.mUsageToFadeWrapperMap.remove(i);
            }
        }

        private void cleanupInactiveWrapperEntries(android.media.AudioAttributes audioAttributes) {
            android.media.FadeManagerConfiguration.FadeVolumeShaperConfigsWrapper fadeVolumeShaperConfigsWrapper = this.mAttrToFadeWrapperMap.get(audioAttributes);
            if (fadeVolumeShaperConfigsWrapper != null && fadeVolumeShaperConfigsWrapper.isInactive()) {
                this.mAttrToFadeWrapperMap.remove(audioAttributes);
            }
        }

        private void setVolShaperConfigsForUsages(android.util.IntArray intArray) {
            for (int i = 0; i < intArray.size(); i++) {
                setMissingVolShaperConfigsForWrapper(getFadeVolShaperConfigWrapperForUsage(intArray.get(i)));
            }
        }

        private void setMissingVolShaperConfigsForWrapper(android.media.FadeManagerConfiguration.FadeVolumeShaperConfigsWrapper fadeVolumeShaperConfigsWrapper) {
            if (!fadeVolumeShaperConfigsWrapper.isFadeOutConfigActive()) {
                fadeVolumeShaperConfigsWrapper.setFadeOutVolShaperConfig(createVolShaperConfigForDuration(this.mFadeOutDurationMillis, false));
            }
            if (!fadeVolumeShaperConfigsWrapper.isFadeInConfigActive()) {
                fadeVolumeShaperConfigsWrapper.setFadeInVolShaperConfig(createVolShaperConfigForDuration(this.mFadeInDurationMillis, true));
            }
        }

        private void validateFadeState(int i) {
            switch (i) {
                case 0:
                case 1:
                    return;
                default:
                    throw new java.lang.IllegalArgumentException("Unknown fade state: " + i);
            }
        }

        private void validateUsages(java.util.List<java.lang.Integer> list) {
            for (int i = 0; i < list.size(); i++) {
                android.media.FadeManagerConfiguration.validateUsage(list.get(i).intValue());
            }
        }

        private void validateContentTypes(java.util.List<java.lang.Integer> list) {
            for (int i = 0; i < list.size(); i++) {
                validateContentType(list.get(i).intValue());
            }
        }

        private void validateContentType(int i) {
            com.android.internal.util.Preconditions.checkArgument(android.media.AudioAttributes.isSdkContentType(i), "Invalid content type: ", java.lang.Integer.valueOf(i));
        }

        private void validateFadeConfigurations() {
            validateFadeableUsages();
            validateFadeVolumeShaperConfigsWrappers();
            validateUnfadeableAudioAttributes();
        }

        private void validateFadeableUsages() {
            com.android.internal.util.Preconditions.checkArgumentPositive(this.mFadeableUsages.size(), "Fadeable usage list cannot be empty when state set to enabled");
            for (int i = 0; i < this.mFadeableUsages.size(); i++) {
                setMissingVolShaperConfigsForWrapper(getFadeVolShaperConfigWrapperForUsage(this.mFadeableUsages.get(i)));
            }
        }

        private void validateFadeVolumeShaperConfigsWrappers() {
            for (int i = 0; i < this.mUsageToFadeWrapperMap.size(); i++) {
                setMissingVolShaperConfigsForWrapper(getFadeVolShaperConfigWrapperForUsage(this.mUsageToFadeWrapperMap.keyAt(i)));
            }
            for (int i2 = 0; i2 < this.mAttrToFadeWrapperMap.size(); i2++) {
                setMissingVolShaperConfigsForWrapper(getFadeVolShaperConfigWrapperForAttr(this.mAttrToFadeWrapperMap.keyAt(i2)));
            }
        }

        private void validateUnfadeableAudioAttributes() {
            for (int i = 0; i < this.mUnfadeableAudioAttributes.size(); i++) {
                android.media.AudioAttributes audioAttributes = this.mUnfadeableAudioAttributes.get(i);
                boolean contains = this.mFadeableUsages.contains(audioAttributes.getSystemUsage());
                com.android.internal.util.Preconditions.checkArgument(!contains || (contains && !isGeneric(audioAttributes)), "Unfadeable audio attributes cannot be generic of the fadeable usage");
            }
        }

        private static boolean isGeneric(android.media.AudioAttributes audioAttributes) {
            return audioAttributes.getContentType() == 0 && audioAttributes.getFlags() == 0 && audioAttributes.getBundle() == null && audioAttributes.getTags().isEmpty();
        }
    }

    private static final class FadeVolumeShaperConfigsWrapper implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.media.FadeManagerConfiguration.FadeVolumeShaperConfigsWrapper> CREATOR = new android.os.Parcelable.Creator<android.media.FadeManagerConfiguration.FadeVolumeShaperConfigsWrapper>() { // from class: android.media.FadeManagerConfiguration.FadeVolumeShaperConfigsWrapper.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.FadeManagerConfiguration.FadeVolumeShaperConfigsWrapper createFromParcel(android.os.Parcel parcel) {
                return new android.media.FadeManagerConfiguration.FadeVolumeShaperConfigsWrapper(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.FadeManagerConfiguration.FadeVolumeShaperConfigsWrapper[] newArray(int i) {
                return new android.media.FadeManagerConfiguration.FadeVolumeShaperConfigsWrapper[i];
            }
        };
        private android.media.VolumeShaper.Configuration mFadeInVolShaperConfig;
        private android.media.VolumeShaper.Configuration mFadeOutVolShaperConfig;

        FadeVolumeShaperConfigsWrapper() {
        }

        public void setFadeOutVolShaperConfig(android.media.VolumeShaper.Configuration configuration) {
            this.mFadeOutVolShaperConfig = configuration;
        }

        public void setFadeInVolShaperConfig(android.media.VolumeShaper.Configuration configuration) {
            this.mFadeInVolShaperConfig = configuration;
        }

        public android.media.VolumeShaper.Configuration getFadeOutVolShaperConfig() {
            return this.mFadeOutVolShaperConfig;
        }

        public android.media.VolumeShaper.Configuration getFadeInVolShaperConfig() {
            return this.mFadeInVolShaperConfig;
        }

        public boolean isInactive() {
            return (isFadeOutConfigActive() || isFadeInConfigActive()) ? false : true;
        }

        boolean isFadeOutConfigActive() {
            return this.mFadeOutVolShaperConfig != null;
        }

        boolean isFadeInConfigActive() {
            return this.mFadeInVolShaperConfig != null;
        }

        public boolean equals(java.lang.Object obj) {
            boolean z;
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof android.media.FadeManagerConfiguration.FadeVolumeShaperConfigsWrapper)) {
                return false;
            }
            android.media.FadeManagerConfiguration.FadeVolumeShaperConfigsWrapper fadeVolumeShaperConfigsWrapper = (android.media.FadeManagerConfiguration.FadeVolumeShaperConfigsWrapper) obj;
            if (this.mFadeInVolShaperConfig == null && fadeVolumeShaperConfigsWrapper.mFadeInVolShaperConfig == null && this.mFadeOutVolShaperConfig == null && fadeVolumeShaperConfigsWrapper.mFadeOutVolShaperConfig == null) {
                return true;
            }
            if (this.mFadeOutVolShaperConfig != null) {
                z = this.mFadeOutVolShaperConfig.equals(fadeVolumeShaperConfigsWrapper.mFadeOutVolShaperConfig);
            } else {
                if (fadeVolumeShaperConfigsWrapper.mFadeOutVolShaperConfig != null) {
                    return false;
                }
                z = true;
            }
            if (this.mFadeInVolShaperConfig != null) {
                return z && this.mFadeInVolShaperConfig.equals(fadeVolumeShaperConfigsWrapper.mFadeInVolShaperConfig);
            }
            if (fadeVolumeShaperConfigsWrapper.mFadeInVolShaperConfig != null) {
                return false;
            }
            return z;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mFadeOutVolShaperConfig, this.mFadeInVolShaperConfig);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            this.mFadeOutVolShaperConfig.writeToParcel(parcel, i);
            this.mFadeInVolShaperConfig.writeToParcel(parcel, i);
        }

        FadeVolumeShaperConfigsWrapper(android.os.Parcel parcel) {
            this.mFadeOutVolShaperConfig = android.media.VolumeShaper.Configuration.CREATOR.createFromParcel(parcel);
            this.mFadeInVolShaperConfig = android.media.VolumeShaper.Configuration.CREATOR.createFromParcel(parcel);
        }
    }
}
