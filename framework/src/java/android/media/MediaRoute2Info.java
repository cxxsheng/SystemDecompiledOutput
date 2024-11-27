package android.media;

/* loaded from: classes2.dex */
public final class MediaRoute2Info implements android.os.Parcelable {
    public static final int CONNECTION_STATE_CONNECTED = 2;
    public static final int CONNECTION_STATE_CONNECTING = 1;
    public static final int CONNECTION_STATE_DISCONNECTED = 0;
    public static final android.os.Parcelable.Creator<android.media.MediaRoute2Info> CREATOR = new android.os.Parcelable.Creator<android.media.MediaRoute2Info>() { // from class: android.media.MediaRoute2Info.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.MediaRoute2Info createFromParcel(android.os.Parcel parcel) {
            return new android.media.MediaRoute2Info(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.MediaRoute2Info[] newArray(int i) {
            return new android.media.MediaRoute2Info[i];
        }
    };
    public static final java.lang.String FEATURE_LIVE_AUDIO = "android.media.route.feature.LIVE_AUDIO";
    public static final java.lang.String FEATURE_LIVE_VIDEO = "android.media.route.feature.LIVE_VIDEO";
    public static final java.lang.String FEATURE_LOCAL_PLAYBACK = "android.media.route.feature.LOCAL_PLAYBACK";
    public static final java.lang.String FEATURE_REMOTE_AUDIO_PLAYBACK = "android.media.route.feature.REMOTE_AUDIO_PLAYBACK";
    public static final java.lang.String FEATURE_REMOTE_GROUP_PLAYBACK = "android.media.route.feature.REMOTE_GROUP_PLAYBACK";
    public static final java.lang.String FEATURE_REMOTE_PLAYBACK = "android.media.route.feature.REMOTE_PLAYBACK";
    public static final java.lang.String FEATURE_REMOTE_VIDEO_PLAYBACK = "android.media.route.feature.REMOTE_VIDEO_PLAYBACK";
    public static final int PLAYBACK_VOLUME_FIXED = 0;
    public static final int PLAYBACK_VOLUME_VARIABLE = 1;
    public static final java.lang.String ROUTE_ID_DEFAULT = "DEFAULT_ROUTE";
    public static final java.lang.String ROUTE_ID_DEVICE = "DEVICE_ROUTE";
    public static final int SUITABILITY_STATUS_NOT_SUITABLE_FOR_TRANSFER = 2;
    public static final int SUITABILITY_STATUS_SUITABLE_FOR_DEFAULT_TRANSFER = 0;
    public static final int SUITABILITY_STATUS_SUITABLE_FOR_MANUAL_TRANSFER = 1;
    public static final int TYPE_BLE_HEADSET = 26;
    public static final int TYPE_BLUETOOTH_A2DP = 8;
    public static final int TYPE_BUILTIN_SPEAKER = 2;
    public static final int TYPE_DOCK = 13;
    public static final int TYPE_GROUP = 2000;
    public static final int TYPE_HDMI = 9;
    public static final int TYPE_HDMI_ARC = 10;
    public static final int TYPE_HDMI_EARC = 29;
    public static final int TYPE_HEARING_AID = 23;
    public static final int TYPE_REMOTE_AUDIO_VIDEO_RECEIVER = 1003;
    public static final int TYPE_REMOTE_CAR = 1008;
    public static final int TYPE_REMOTE_COMPUTER = 1006;
    public static final int TYPE_REMOTE_GAME_CONSOLE = 1007;
    public static final int TYPE_REMOTE_SMARTPHONE = 1010;
    public static final int TYPE_REMOTE_SMARTWATCH = 1009;
    public static final int TYPE_REMOTE_SPEAKER = 1002;
    public static final int TYPE_REMOTE_TABLET = 1004;
    public static final int TYPE_REMOTE_TABLET_DOCKED = 1005;
    public static final int TYPE_REMOTE_TV = 1001;
    public static final int TYPE_UNKNOWN = 0;
    public static final int TYPE_USB_ACCESSORY = 12;
    public static final int TYPE_USB_DEVICE = 11;
    public static final int TYPE_USB_HEADSET = 22;
    public static final int TYPE_WIRED_HEADPHONES = 4;
    public static final int TYPE_WIRED_HEADSET = 3;
    private final java.lang.String mAddress;
    private final java.util.Set<java.lang.String> mAllowedPackages;
    private final java.lang.String mClientPackageName;
    private final int mConnectionState;
    private final java.util.Set<java.lang.String> mDeduplicationIds;
    private final java.lang.CharSequence mDescription;
    private final android.os.Bundle mExtras;
    private final java.util.List<java.lang.String> mFeatures;
    private final android.net.Uri mIconUri;
    private final java.lang.String mId;
    private final boolean mIsSystem;
    private final boolean mIsVisibilityRestricted;
    private final java.lang.CharSequence mName;
    private final java.lang.String mPackageName;
    private final java.lang.String mProviderId;
    private final int mSuitabilityStatus;
    private final int mType;
    private final int mVolume;
    private final int mVolumeHandling;
    private final int mVolumeMax;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ConnectionState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PlaybackVolume {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SuitabilityStatus {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Type {
    }

    MediaRoute2Info(android.media.MediaRoute2Info.Builder builder) {
        this.mId = builder.mId;
        this.mName = builder.mName;
        this.mFeatures = builder.mFeatures;
        this.mType = builder.mType;
        this.mIsSystem = builder.mIsSystem;
        this.mIconUri = builder.mIconUri;
        this.mDescription = builder.mDescription;
        this.mConnectionState = builder.mConnectionState;
        this.mClientPackageName = builder.mClientPackageName;
        this.mPackageName = builder.mPackageName;
        this.mVolumeHandling = builder.mVolumeHandling;
        this.mVolumeMax = builder.mVolumeMax;
        this.mVolume = builder.mVolume;
        this.mAddress = builder.mAddress;
        this.mDeduplicationIds = builder.mDeduplicationIds;
        this.mExtras = builder.mExtras;
        this.mProviderId = builder.mProviderId;
        this.mIsVisibilityRestricted = builder.mIsVisibilityRestricted;
        this.mAllowedPackages = builder.mAllowedPackages;
        this.mSuitabilityStatus = builder.mSuitabilityStatus;
    }

    MediaRoute2Info(android.os.Parcel parcel) {
        this.mId = parcel.readString();
        com.android.internal.util.Preconditions.checkArgument(!android.text.TextUtils.isEmpty(this.mId));
        this.mName = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mFeatures = parcel.createStringArrayList();
        this.mType = parcel.readInt();
        this.mIsSystem = parcel.readBoolean();
        this.mIconUri = (android.net.Uri) parcel.readParcelable(null, android.net.Uri.class);
        this.mDescription = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mConnectionState = parcel.readInt();
        this.mClientPackageName = parcel.readString();
        this.mPackageName = parcel.readString();
        this.mVolumeHandling = parcel.readInt();
        this.mVolumeMax = parcel.readInt();
        this.mVolume = parcel.readInt();
        this.mAddress = parcel.readString();
        this.mDeduplicationIds = java.util.Set.of((java.lang.Object[]) parcel.readStringArray());
        this.mExtras = parcel.readBundle();
        this.mProviderId = parcel.readString();
        this.mIsVisibilityRestricted = parcel.readBoolean();
        this.mAllowedPackages = java.util.Set.of((java.lang.Object[]) parcel.createString8Array());
        this.mSuitabilityStatus = parcel.readInt();
    }

    public java.lang.String getId() {
        if (!android.text.TextUtils.isEmpty(this.mProviderId)) {
            return android.media.MediaRouter2Utils.toUniqueId(this.mProviderId, this.mId);
        }
        return this.mId;
    }

    public java.lang.CharSequence getName() {
        return this.mName;
    }

    public java.util.List<java.lang.String> getFeatures() {
        return this.mFeatures;
    }

    public int getType() {
        return this.mType;
    }

    public boolean isSystemRoute() {
        return this.mIsSystem;
    }

    public android.net.Uri getIconUri() {
        return this.mIconUri;
    }

    public java.lang.CharSequence getDescription() {
        return this.mDescription;
    }

    public int getConnectionState() {
        return this.mConnectionState;
    }

    public java.lang.String getClientPackageName() {
        return this.mClientPackageName;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public int getVolumeHandling() {
        return this.mVolumeHandling;
    }

    public int getVolumeMax() {
        return this.mVolumeMax;
    }

    public int getVolume() {
        return this.mVolume;
    }

    public java.lang.String getAddress() {
        return this.mAddress;
    }

    public java.util.Set<java.lang.String> getDeduplicationIds() {
        return this.mDeduplicationIds;
    }

    public android.os.Bundle getExtras() {
        if (this.mExtras == null) {
            return null;
        }
        return new android.os.Bundle(this.mExtras);
    }

    public java.lang.String getOriginalId() {
        return this.mId;
    }

    public java.lang.String getProviderId() {
        return this.mProviderId;
    }

    public boolean hasAnyFeatures(java.util.Collection<java.lang.String> collection) {
        java.util.Objects.requireNonNull(collection, "features must not be null");
        java.util.Iterator<java.lang.String> it = collection.iterator();
        while (it.hasNext()) {
            if (getFeatures().contains(it.next())) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAllFeatures(java.util.Collection<java.lang.String> collection) {
        java.util.Objects.requireNonNull(collection, "features must not be null");
        java.util.Iterator<java.lang.String> it = collection.iterator();
        while (it.hasNext()) {
            if (!getFeatures().contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    public boolean isValid() {
        if (android.text.TextUtils.isEmpty(getId()) || android.text.TextUtils.isEmpty(getName()) || android.text.TextUtils.isEmpty(getProviderId())) {
            return false;
        }
        return true;
    }

    public boolean isVisibleTo(java.lang.String str) {
        return !this.mIsVisibilityRestricted || getPackageName().equals(str) || this.mAllowedPackages.contains(str);
    }

    public int getSuitabilityStatus() {
        return this.mSuitabilityStatus;
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println(str + "MediaRoute2Info");
        java.lang.String str2 = str + "  ";
        printWriter.println(str2 + "mId=" + this.mId);
        printWriter.println(str2 + "mName=" + ((java.lang.Object) this.mName));
        printWriter.println(str2 + "mFeatures=" + this.mFeatures);
        printWriter.println(str2 + "mType=" + getDeviceTypeString(this.mType));
        printWriter.println(str2 + "mIsSystem=" + this.mIsSystem);
        printWriter.println(str2 + "mIconUri=" + this.mIconUri);
        printWriter.println(str2 + "mDescription=" + ((java.lang.Object) this.mDescription));
        printWriter.println(str2 + "mConnectionState=" + this.mConnectionState);
        printWriter.println(str2 + "mClientPackageName=" + this.mClientPackageName);
        printWriter.println(str2 + "mPackageName=" + this.mPackageName);
        dumpVolume(printWriter, str2);
        printWriter.println(str2 + "mAddress=" + this.mAddress);
        printWriter.println(str2 + "mDeduplicationIds=" + this.mDeduplicationIds);
        printWriter.println(str2 + "mExtras=" + this.mExtras);
        printWriter.println(str2 + "mProviderId=" + this.mProviderId);
        printWriter.println(str2 + "mIsVisibilityRestricted=" + this.mIsVisibilityRestricted);
        printWriter.println(str2 + "mAllowedPackages=" + this.mAllowedPackages);
        printWriter.println(str2 + "mSuitabilityStatus=" + this.mSuitabilityStatus);
    }

    private void dumpVolume(java.io.PrintWriter printWriter, java.lang.String str) {
        java.lang.String str2;
        switch (this.mVolumeHandling) {
            case 0:
                str2 = "FIXED";
                break;
            case 1:
                str2 = "VARIABLE";
                break;
            default:
                str2 = "UNKNOWN";
                break;
        }
        printWriter.println(str + java.lang.String.format(java.util.Locale.US, "volume(current=%d, max=%d, handling=%s(%d))", java.lang.Integer.valueOf(this.mVolume), java.lang.Integer.valueOf(this.mVolumeMax), str2, java.lang.Integer.valueOf(this.mVolumeHandling)));
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.media.MediaRoute2Info)) {
            return false;
        }
        android.media.MediaRoute2Info mediaRoute2Info = (android.media.MediaRoute2Info) obj;
        return java.util.Objects.equals(this.mId, mediaRoute2Info.mId) && java.util.Objects.equals(this.mName, mediaRoute2Info.mName) && java.util.Objects.equals(this.mFeatures, mediaRoute2Info.mFeatures) && this.mType == mediaRoute2Info.mType && this.mIsSystem == mediaRoute2Info.mIsSystem && java.util.Objects.equals(this.mIconUri, mediaRoute2Info.mIconUri) && java.util.Objects.equals(this.mDescription, mediaRoute2Info.mDescription) && this.mConnectionState == mediaRoute2Info.mConnectionState && java.util.Objects.equals(this.mClientPackageName, mediaRoute2Info.mClientPackageName) && java.util.Objects.equals(this.mPackageName, mediaRoute2Info.mPackageName) && this.mVolumeHandling == mediaRoute2Info.mVolumeHandling && this.mVolumeMax == mediaRoute2Info.mVolumeMax && this.mVolume == mediaRoute2Info.mVolume && java.util.Objects.equals(this.mAddress, mediaRoute2Info.mAddress) && java.util.Objects.equals(this.mDeduplicationIds, mediaRoute2Info.mDeduplicationIds) && java.util.Objects.equals(this.mProviderId, mediaRoute2Info.mProviderId) && this.mIsVisibilityRestricted == mediaRoute2Info.mIsVisibilityRestricted && java.util.Objects.equals(this.mAllowedPackages, mediaRoute2Info.mAllowedPackages) && this.mSuitabilityStatus == mediaRoute2Info.mSuitabilityStatus;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mId, this.mName, this.mFeatures, java.lang.Integer.valueOf(this.mType), java.lang.Boolean.valueOf(this.mIsSystem), this.mIconUri, this.mDescription, java.lang.Integer.valueOf(this.mConnectionState), this.mClientPackageName, this.mPackageName, java.lang.Integer.valueOf(this.mVolumeHandling), java.lang.Integer.valueOf(this.mVolumeMax), java.lang.Integer.valueOf(this.mVolume), this.mAddress, this.mDeduplicationIds, this.mProviderId, java.lang.Boolean.valueOf(this.mIsVisibilityRestricted), this.mAllowedPackages, java.lang.Integer.valueOf(this.mSuitabilityStatus));
    }

    public java.lang.String toString() {
        return "MediaRoute2Info{ id=" + getId() + ", name=" + getName() + ", type=" + getDeviceTypeString(getType()) + ", isSystem=" + isSystemRoute() + ", features=" + getFeatures() + ", iconUri=" + getIconUri() + ", description=" + getDescription() + ", connectionState=" + getConnectionState() + ", clientPackageName=" + getClientPackageName() + ", volumeHandling=" + getVolumeHandling() + ", volumeMax=" + getVolumeMax() + ", volume=" + getVolume() + ", address=" + getAddress() + ", deduplicationIds=" + java.lang.String.join(",", getDeduplicationIds()) + ", providerId=" + getProviderId() + ", isVisibilityRestricted=" + this.mIsVisibilityRestricted + ", allowedPackages=" + java.lang.String.join(",", this.mAllowedPackages) + ", suitabilityStatus=" + this.mSuitabilityStatus + " }";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mId);
        android.text.TextUtils.writeToParcel(this.mName, parcel, i);
        parcel.writeStringList(this.mFeatures);
        parcel.writeInt(this.mType);
        parcel.writeBoolean(this.mIsSystem);
        parcel.writeParcelable(this.mIconUri, i);
        android.text.TextUtils.writeToParcel(this.mDescription, parcel, i);
        parcel.writeInt(this.mConnectionState);
        parcel.writeString(this.mClientPackageName);
        parcel.writeString(this.mPackageName);
        parcel.writeInt(this.mVolumeHandling);
        parcel.writeInt(this.mVolumeMax);
        parcel.writeInt(this.mVolume);
        parcel.writeString(this.mAddress);
        parcel.writeStringArray((java.lang.String[]) this.mDeduplicationIds.toArray(new java.lang.String[this.mDeduplicationIds.size()]));
        parcel.writeBundle(this.mExtras);
        parcel.writeString(this.mProviderId);
        parcel.writeBoolean(this.mIsVisibilityRestricted);
        parcel.writeString8Array((java.lang.String[]) this.mAllowedPackages.toArray(new java.lang.String[0]));
        parcel.writeInt(this.mSuitabilityStatus);
    }

    private static java.lang.String getDeviceTypeString(int i) {
        switch (i) {
            case 2:
                return "BUILTIN_SPEAKER";
            case 3:
                return "WIRED_HEADSET";
            case 4:
                return "WIRED_HEADPHONES";
            case 8:
                return "BLUETOOTH_A2DP";
            case 9:
                return "HDMI";
            case 10:
                return "HDMI_ARC";
            case 11:
                return "USB_DEVICE";
            case 12:
                return "USB_ACCESSORY";
            case 13:
                return "DOCK";
            case 22:
                return "USB_HEADSET";
            case 23:
                return "HEARING_AID";
            case 29:
                return "HDMI_EARC";
            case 1001:
                return "REMOTE_TV";
            case 1002:
                return "REMOTE_SPEAKER";
            case 1003:
                return "REMOTE_AUDIO_VIDEO_RECEIVER";
            case 1004:
                return "REMOTE_TABLET";
            case 1005:
                return "REMOTE_TABLET_DOCKED";
            case 1006:
                return "REMOTE_COMPUTER";
            case 1007:
                return "REMOTE_GAME_CONSOLE";
            case 1008:
                return "REMOTE_CAR";
            case 1009:
                return "REMOTE_SMARTWATCH";
            case 1010:
                return "REMOTE_SMARTPHONE";
            case 2000:
                return "GROUP";
            default:
                return android.text.TextUtils.formatSimple("UNKNOWN(%d)", java.lang.Integer.valueOf(i));
        }
    }

    public static final class Builder {
        private java.lang.String mAddress;
        private java.util.Set<java.lang.String> mAllowedPackages;
        private java.lang.String mClientPackageName;
        private int mConnectionState;
        private java.util.Set<java.lang.String> mDeduplicationIds;
        private java.lang.CharSequence mDescription;
        private android.os.Bundle mExtras;
        private final java.util.List<java.lang.String> mFeatures;
        private android.net.Uri mIconUri;
        private final java.lang.String mId;
        private boolean mIsSystem;
        private boolean mIsVisibilityRestricted;
        private final java.lang.CharSequence mName;
        private java.lang.String mPackageName;
        private java.lang.String mProviderId;
        private int mSuitabilityStatus;
        private int mType;
        private int mVolume;
        private int mVolumeHandling;
        private int mVolumeMax;

        public Builder(java.lang.String str, java.lang.CharSequence charSequence) {
            this.mType = 0;
            this.mVolumeHandling = 0;
            if (android.text.TextUtils.isEmpty(str)) {
                throw new java.lang.IllegalArgumentException("id must not be empty");
            }
            if (android.text.TextUtils.isEmpty(charSequence)) {
                throw new java.lang.IllegalArgumentException("name must not be empty");
            }
            this.mId = str;
            this.mName = charSequence;
            this.mFeatures = new java.util.ArrayList();
            this.mDeduplicationIds = java.util.Set.of();
            this.mAllowedPackages = java.util.Set.of();
            this.mSuitabilityStatus = 0;
        }

        public Builder(android.media.MediaRoute2Info mediaRoute2Info) {
            this(mediaRoute2Info.mId, mediaRoute2Info);
        }

        public Builder(java.lang.String str, android.media.MediaRoute2Info mediaRoute2Info) {
            this.mType = 0;
            this.mVolumeHandling = 0;
            if (android.text.TextUtils.isEmpty(str)) {
                throw new java.lang.IllegalArgumentException("id must not be empty");
            }
            java.util.Objects.requireNonNull(mediaRoute2Info, "routeInfo must not be null");
            this.mId = str;
            this.mName = mediaRoute2Info.mName;
            this.mFeatures = new java.util.ArrayList(mediaRoute2Info.mFeatures);
            this.mType = mediaRoute2Info.mType;
            this.mIsSystem = mediaRoute2Info.mIsSystem;
            this.mIconUri = mediaRoute2Info.mIconUri;
            this.mDescription = mediaRoute2Info.mDescription;
            this.mConnectionState = mediaRoute2Info.mConnectionState;
            this.mClientPackageName = mediaRoute2Info.mClientPackageName;
            this.mPackageName = mediaRoute2Info.mPackageName;
            this.mVolumeHandling = mediaRoute2Info.mVolumeHandling;
            this.mVolumeMax = mediaRoute2Info.mVolumeMax;
            this.mVolume = mediaRoute2Info.mVolume;
            this.mAddress = mediaRoute2Info.mAddress;
            this.mDeduplicationIds = java.util.Set.copyOf(mediaRoute2Info.mDeduplicationIds);
            if (mediaRoute2Info.mExtras != null) {
                this.mExtras = new android.os.Bundle(mediaRoute2Info.mExtras);
            }
            this.mProviderId = mediaRoute2Info.mProviderId;
            this.mIsVisibilityRestricted = mediaRoute2Info.mIsVisibilityRestricted;
            this.mAllowedPackages = mediaRoute2Info.mAllowedPackages;
            this.mSuitabilityStatus = mediaRoute2Info.mSuitabilityStatus;
        }

        public android.media.MediaRoute2Info.Builder addFeature(java.lang.String str) {
            if (android.text.TextUtils.isEmpty(str)) {
                throw new java.lang.IllegalArgumentException("feature must not be null or empty");
            }
            this.mFeatures.add(str);
            return this;
        }

        public android.media.MediaRoute2Info.Builder addFeatures(java.util.Collection<java.lang.String> collection) {
            java.util.Objects.requireNonNull(collection, "features must not be null");
            java.util.Iterator<java.lang.String> it = collection.iterator();
            while (it.hasNext()) {
                addFeature(it.next());
            }
            return this;
        }

        public android.media.MediaRoute2Info.Builder clearFeatures() {
            this.mFeatures.clear();
            return this;
        }

        public android.media.MediaRoute2Info.Builder setType(int i) {
            this.mType = i;
            return this;
        }

        public android.media.MediaRoute2Info.Builder setSystemRoute(boolean z) {
            this.mIsSystem = z;
            return this;
        }

        public android.media.MediaRoute2Info.Builder setIconUri(android.net.Uri uri) {
            this.mIconUri = uri;
            return this;
        }

        public android.media.MediaRoute2Info.Builder setDescription(java.lang.CharSequence charSequence) {
            this.mDescription = charSequence;
            return this;
        }

        public android.media.MediaRoute2Info.Builder setConnectionState(int i) {
            this.mConnectionState = i;
            return this;
        }

        public android.media.MediaRoute2Info.Builder setClientPackageName(java.lang.String str) {
            this.mClientPackageName = str;
            return this;
        }

        public android.media.MediaRoute2Info.Builder setPackageName(java.lang.String str) {
            this.mPackageName = str;
            return this;
        }

        public android.media.MediaRoute2Info.Builder setVolumeHandling(int i) {
            this.mVolumeHandling = i;
            return this;
        }

        public android.media.MediaRoute2Info.Builder setVolumeMax(int i) {
            this.mVolumeMax = i;
            return this;
        }

        public android.media.MediaRoute2Info.Builder setVolume(int i) {
            this.mVolume = i;
            return this;
        }

        public android.media.MediaRoute2Info.Builder setAddress(java.lang.String str) {
            this.mAddress = str;
            return this;
        }

        public android.media.MediaRoute2Info.Builder setDeduplicationIds(java.util.Set<java.lang.String> set) {
            this.mDeduplicationIds = java.util.Set.copyOf(set);
            return this;
        }

        public android.media.MediaRoute2Info.Builder setExtras(android.os.Bundle bundle) {
            if (bundle == null) {
                this.mExtras = null;
                return this;
            }
            this.mExtras = new android.os.Bundle(bundle);
            return this;
        }

        public android.media.MediaRoute2Info.Builder setProviderId(java.lang.String str) {
            if (android.text.TextUtils.isEmpty(str)) {
                throw new java.lang.IllegalArgumentException("providerId must not be null or empty");
            }
            this.mProviderId = str;
            return this;
        }

        public android.media.MediaRoute2Info.Builder setVisibilityPublic() {
            this.mIsVisibilityRestricted = false;
            this.mAllowedPackages = java.util.Set.of();
            return this;
        }

        public android.media.MediaRoute2Info.Builder setVisibilityRestricted(java.util.Set<java.lang.String> set) {
            this.mIsVisibilityRestricted = true;
            this.mAllowedPackages = java.util.Set.copyOf(set);
            return this;
        }

        public android.media.MediaRoute2Info.Builder setSuitabilityStatus(int i) {
            this.mSuitabilityStatus = i;
            return this;
        }

        public android.media.MediaRoute2Info build() {
            if (this.mFeatures.isEmpty()) {
                throw new java.lang.IllegalArgumentException("features must not be empty!");
            }
            return new android.media.MediaRoute2Info(this);
        }
    }
}
