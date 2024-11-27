package android.media.audiopolicy;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class AudioProductStrategy implements android.os.Parcelable {
    private static final int AUDIO_FLAGS_AFFECT_STRATEGY_SELECTION = 13;
    public static final int DEFAULT_GROUP = -1;
    private static final java.lang.String TAG = "AudioProductStrategy";
    private static java.util.List<android.media.audiopolicy.AudioProductStrategy> sAudioProductStrategies;
    private final android.media.audiopolicy.AudioProductStrategy.AudioAttributesGroup[] mAudioAttributesGroups;
    private int mId;
    private final java.lang.String mName;
    private static final java.lang.Object sLock = new java.lang.Object();
    public static final android.os.Parcelable.Creator<android.media.audiopolicy.AudioProductStrategy> CREATOR = new android.os.Parcelable.Creator<android.media.audiopolicy.AudioProductStrategy>() { // from class: android.media.audiopolicy.AudioProductStrategy.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audiopolicy.AudioProductStrategy createFromParcel(android.os.Parcel parcel) {
            java.lang.String readString = parcel.readString();
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            android.media.audiopolicy.AudioProductStrategy.AudioAttributesGroup[] audioAttributesGroupArr = new android.media.audiopolicy.AudioProductStrategy.AudioAttributesGroup[readInt2];
            for (int i = 0; i < readInt2; i++) {
                audioAttributesGroupArr[i] = android.media.audiopolicy.AudioProductStrategy.AudioAttributesGroup.CREATOR.createFromParcel(parcel);
            }
            return new android.media.audiopolicy.AudioProductStrategy(readString, readInt, audioAttributesGroupArr);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audiopolicy.AudioProductStrategy[] newArray(int i) {
            return new android.media.audiopolicy.AudioProductStrategy[i];
        }
    };
    private static final android.media.AudioAttributes DEFAULT_ATTRIBUTES = new android.media.AudioAttributes.Builder().build();

    private static native int native_list_audio_product_strategies(java.util.ArrayList<android.media.audiopolicy.AudioProductStrategy> arrayList);

    public static java.util.List<android.media.audiopolicy.AudioProductStrategy> getAudioProductStrategies() {
        if (sAudioProductStrategies == null) {
            synchronized (sLock) {
                if (sAudioProductStrategies == null) {
                    sAudioProductStrategies = initializeAudioProductStrategies();
                }
            }
        }
        return sAudioProductStrategies;
    }

    public static android.media.audiopolicy.AudioProductStrategy getAudioProductStrategyWithId(int i) {
        synchronized (sLock) {
            if (sAudioProductStrategies == null) {
                sAudioProductStrategies = initializeAudioProductStrategies();
            }
            for (android.media.audiopolicy.AudioProductStrategy audioProductStrategy : sAudioProductStrategies) {
                if (audioProductStrategy.getId() == i) {
                    return audioProductStrategy;
                }
            }
            return null;
        }
    }

    @android.annotation.SystemApi
    public static android.media.audiopolicy.AudioProductStrategy createInvalidAudioProductStrategy(int i) {
        return new android.media.audiopolicy.AudioProductStrategy("dummy strategy", i, new android.media.audiopolicy.AudioProductStrategy.AudioAttributesGroup[0]);
    }

    public static android.media.AudioAttributes getAudioAttributesForStrategyWithLegacyStreamType(int i) {
        java.util.Iterator<android.media.audiopolicy.AudioProductStrategy> it = getAudioProductStrategies().iterator();
        while (it.hasNext()) {
            android.media.AudioAttributes audioAttributesForLegacyStreamType = it.next().getAudioAttributesForLegacyStreamType(i);
            if (audioAttributesForLegacyStreamType != null) {
                return audioAttributesForLegacyStreamType;
            }
        }
        return DEFAULT_ATTRIBUTES;
    }

    public static int getLegacyStreamTypeForStrategyWithAudioAttributes(android.media.AudioAttributes audioAttributes) {
        java.util.Objects.requireNonNull(audioAttributes, "AudioAttributes must not be null");
        for (android.media.audiopolicy.AudioProductStrategy audioProductStrategy : getAudioProductStrategies()) {
            if (audioProductStrategy.supportsAudioAttributes(audioAttributes)) {
                int legacyStreamTypeForAudioAttributes = audioProductStrategy.getLegacyStreamTypeForAudioAttributes(audioAttributes);
                if (legacyStreamTypeForAudioAttributes == -1) {
                    android.util.Log.w(TAG, "Attributes " + audioAttributes + " supported by strategy " + audioProductStrategy.getId() + " have no associated stream type, therefore falling back to STREAM_MUSIC");
                    return 3;
                }
                if (legacyStreamTypeForAudioAttributes < android.media.AudioSystem.getNumStreamTypes()) {
                    return legacyStreamTypeForAudioAttributes;
                }
            }
        }
        return 3;
    }

    public static int getVolumeGroupIdForAudioAttributes(android.media.AudioAttributes audioAttributes, boolean z) {
        java.util.Objects.requireNonNull(audioAttributes, "attributes must not be null");
        int volumeGroupIdForAudioAttributesInt = getVolumeGroupIdForAudioAttributesInt(audioAttributes);
        if (volumeGroupIdForAudioAttributesInt != -1) {
            return volumeGroupIdForAudioAttributesInt;
        }
        if (!z) {
            return -1;
        }
        return getVolumeGroupIdForAudioAttributesInt(getDefaultAttributes());
    }

    private static java.util.List<android.media.audiopolicy.AudioProductStrategy> initializeAudioProductStrategies() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (native_list_audio_product_strategies(arrayList) != 0) {
            android.util.Log.w(TAG, ": initializeAudioProductStrategies failed");
        }
        return arrayList;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.media.audiopolicy.AudioProductStrategy audioProductStrategy = (android.media.audiopolicy.AudioProductStrategy) obj;
        if (this.mId == audioProductStrategy.mId && java.util.Objects.equals(this.mName, audioProductStrategy.mName) && java.util.Arrays.equals(this.mAudioAttributesGroups, audioProductStrategy.mAudioAttributesGroups)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mId), this.mName, java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mAudioAttributesGroups)));
    }

    private AudioProductStrategy(java.lang.String str, int i, android.media.audiopolicy.AudioProductStrategy.AudioAttributesGroup[] audioAttributesGroupArr) {
        java.util.Objects.requireNonNull(str, "name must not be null");
        java.util.Objects.requireNonNull(audioAttributesGroupArr, "AudioAttributesGroups must not be null");
        this.mName = str;
        this.mId = i;
        this.mAudioAttributesGroups = audioAttributesGroupArr;
    }

    @android.annotation.SystemApi
    public int getId() {
        return this.mId;
    }

    @android.annotation.SystemApi
    public java.lang.String getName() {
        return this.mName;
    }

    @android.annotation.SystemApi
    public android.media.AudioAttributes getAudioAttributes() {
        return this.mAudioAttributesGroups.length == 0 ? DEFAULT_ATTRIBUTES : this.mAudioAttributesGroups[0].getAudioAttributes();
    }

    public android.media.AudioAttributes getAudioAttributesForLegacyStreamType(int i) {
        for (android.media.audiopolicy.AudioProductStrategy.AudioAttributesGroup audioAttributesGroup : this.mAudioAttributesGroups) {
            if (audioAttributesGroup.supportsStreamType(i)) {
                return audioAttributesGroup.getAudioAttributes();
            }
        }
        return null;
    }

    public int getLegacyStreamTypeForAudioAttributes(android.media.AudioAttributes audioAttributes) {
        java.util.Objects.requireNonNull(audioAttributes, "AudioAttributes must not be null");
        for (android.media.audiopolicy.AudioProductStrategy.AudioAttributesGroup audioAttributesGroup : this.mAudioAttributesGroups) {
            if (audioAttributesGroup.supportsAttributes(audioAttributes)) {
                return audioAttributesGroup.getStreamType();
            }
        }
        return -1;
    }

    @android.annotation.SystemApi
    public boolean supportsAudioAttributes(android.media.AudioAttributes audioAttributes) {
        java.util.Objects.requireNonNull(audioAttributes, "AudioAttributes must not be null");
        for (android.media.audiopolicy.AudioProductStrategy.AudioAttributesGroup audioAttributesGroup : this.mAudioAttributesGroups) {
            if (audioAttributesGroup.supportsAttributes(audioAttributes)) {
                return true;
            }
        }
        return false;
    }

    public int getVolumeGroupIdForLegacyStreamType(int i) {
        for (android.media.audiopolicy.AudioProductStrategy.AudioAttributesGroup audioAttributesGroup : this.mAudioAttributesGroups) {
            if (audioAttributesGroup.supportsStreamType(i)) {
                return audioAttributesGroup.getVolumeGroupId();
            }
        }
        return -1;
    }

    public int getVolumeGroupIdForAudioAttributes(android.media.AudioAttributes audioAttributes) {
        java.util.Objects.requireNonNull(audioAttributes, "AudioAttributes must not be null");
        for (android.media.audiopolicy.AudioProductStrategy.AudioAttributesGroup audioAttributesGroup : this.mAudioAttributesGroups) {
            if (audioAttributesGroup.supportsAttributes(audioAttributes)) {
                return audioAttributesGroup.getVolumeGroupId();
            }
        }
        return -1;
    }

    private static int getVolumeGroupIdForAudioAttributesInt(android.media.AudioAttributes audioAttributes) {
        java.util.Objects.requireNonNull(audioAttributes, "attributes must not be null");
        java.util.Iterator<android.media.audiopolicy.AudioProductStrategy> it = getAudioProductStrategies().iterator();
        while (it.hasNext()) {
            int volumeGroupIdForAudioAttributes = it.next().getVolumeGroupIdForAudioAttributes(audioAttributes);
            if (volumeGroupIdForAudioAttributes != -1) {
                return volumeGroupIdForAudioAttributes;
            }
        }
        return -1;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mName);
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mAudioAttributesGroups.length);
        for (android.media.audiopolicy.AudioProductStrategy.AudioAttributesGroup audioAttributesGroup : this.mAudioAttributesGroups) {
            audioAttributesGroup.writeToParcel(parcel, i);
        }
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("\n Name: ");
        sb.append(this.mName);
        sb.append(" Id: ");
        sb.append(java.lang.Integer.toString(this.mId));
        for (android.media.audiopolicy.AudioProductStrategy.AudioAttributesGroup audioAttributesGroup : this.mAudioAttributesGroups) {
            sb.append(audioAttributesGroup.toString());
        }
        return sb.toString();
    }

    public static android.media.AudioAttributes getDefaultAttributes() {
        return DEFAULT_ATTRIBUTES;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean attributesMatches(android.media.AudioAttributes audioAttributes, android.media.AudioAttributes audioAttributes2) {
        java.util.Objects.requireNonNull(audioAttributes, "reference AudioAttributes must not be null");
        java.util.Objects.requireNonNull(audioAttributes2, "requester's AudioAttributes must not be null");
        java.lang.String join = android.text.TextUtils.join(android.inputmethodservice.navigationbar.NavigationBarInflaterView.GRAVITY_SEPARATOR, audioAttributes.getTags());
        java.lang.String join2 = android.text.TextUtils.join(android.inputmethodservice.navigationbar.NavigationBarInflaterView.GRAVITY_SEPARATOR, audioAttributes2.getTags());
        if (audioAttributes.equals(DEFAULT_ATTRIBUTES)) {
            return false;
        }
        if (audioAttributes.getSystemUsage() != 0 && audioAttributes2.getSystemUsage() != audioAttributes.getSystemUsage()) {
            return false;
        }
        if (audioAttributes.getContentType() != 0 && audioAttributes2.getContentType() != audioAttributes.getContentType()) {
            return false;
        }
        if ((audioAttributes.getAllFlags() & 13) == 0 || ((audioAttributes2.getAllFlags() & 13) != 0 && (audioAttributes2.getAllFlags() & audioAttributes.getAllFlags()) == audioAttributes.getAllFlags())) {
            return join.length() == 0 || join.equals(join2);
        }
        return false;
    }

    private static final class AudioAttributesGroup implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.media.audiopolicy.AudioProductStrategy.AudioAttributesGroup> CREATOR = new android.os.Parcelable.Creator<android.media.audiopolicy.AudioProductStrategy.AudioAttributesGroup>() { // from class: android.media.audiopolicy.AudioProductStrategy.AudioAttributesGroup.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.audiopolicy.AudioProductStrategy.AudioAttributesGroup createFromParcel(android.os.Parcel parcel) {
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                android.media.AudioAttributes[] audioAttributesArr = new android.media.AudioAttributes[readInt3];
                for (int i = 0; i < readInt3; i++) {
                    audioAttributesArr[i] = android.media.AudioAttributes.CREATOR.createFromParcel(parcel);
                }
                return new android.media.audiopolicy.AudioProductStrategy.AudioAttributesGroup(readInt, readInt2, audioAttributesArr);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.audiopolicy.AudioProductStrategy.AudioAttributesGroup[] newArray(int i) {
                return new android.media.audiopolicy.AudioProductStrategy.AudioAttributesGroup[i];
            }
        };
        private final android.media.AudioAttributes[] mAudioAttributes;
        private int mLegacyStreamType;
        private int mVolumeGroupId;

        AudioAttributesGroup(int i, int i2, android.media.AudioAttributes[] audioAttributesArr) {
            this.mVolumeGroupId = i;
            this.mLegacyStreamType = i2;
            this.mAudioAttributes = audioAttributesArr;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.media.audiopolicy.AudioProductStrategy.AudioAttributesGroup audioAttributesGroup = (android.media.audiopolicy.AudioProductStrategy.AudioAttributesGroup) obj;
            if (this.mVolumeGroupId == audioAttributesGroup.mVolumeGroupId && this.mLegacyStreamType == audioAttributesGroup.mLegacyStreamType && java.util.Arrays.equals(this.mAudioAttributes, audioAttributesGroup.mAudioAttributes)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.mVolumeGroupId), java.lang.Integer.valueOf(this.mLegacyStreamType), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mAudioAttributes)));
        }

        public int getStreamType() {
            return this.mLegacyStreamType;
        }

        public int getVolumeGroupId() {
            return this.mVolumeGroupId;
        }

        public android.media.AudioAttributes getAudioAttributes() {
            return this.mAudioAttributes.length == 0 ? android.media.audiopolicy.AudioProductStrategy.DEFAULT_ATTRIBUTES : this.mAudioAttributes[0];
        }

        public boolean supportsAttributes(android.media.AudioAttributes audioAttributes) {
            for (android.media.AudioAttributes audioAttributes2 : this.mAudioAttributes) {
                if (audioAttributes2.equals(audioAttributes) || android.media.audiopolicy.AudioProductStrategy.attributesMatches(audioAttributes2, audioAttributes)) {
                    return true;
                }
            }
            return false;
        }

        public boolean supportsStreamType(int i) {
            return this.mLegacyStreamType == i;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mVolumeGroupId);
            parcel.writeInt(this.mLegacyStreamType);
            parcel.writeInt(this.mAudioAttributes.length);
            for (android.media.AudioAttributes audioAttributes : this.mAudioAttributes) {
                audioAttributes.writeToParcel(parcel, i | 1);
            }
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("\n    Legacy Stream Type: ");
            sb.append(java.lang.Integer.toString(this.mLegacyStreamType));
            sb.append(" Volume Group Id: ");
            sb.append(java.lang.Integer.toString(this.mVolumeGroupId));
            for (android.media.AudioAttributes audioAttributes : this.mAudioAttributes) {
                sb.append("\n    -");
                sb.append(audioAttributes.toString());
            }
            return sb.toString();
        }
    }
}
