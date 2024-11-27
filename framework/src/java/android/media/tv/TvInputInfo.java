package android.media.tv;

/* loaded from: classes2.dex */
public final class TvInputInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.tv.TvInputInfo> CREATOR = new android.os.Parcelable.Creator<android.media.tv.TvInputInfo>() { // from class: android.media.tv.TvInputInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.TvInputInfo createFromParcel(android.os.Parcel parcel) {
            return new android.media.tv.TvInputInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.TvInputInfo[] newArray(int i) {
            return new android.media.tv.TvInputInfo[i];
        }
    };
    private static final boolean DEBUG = false;
    public static final java.lang.String EXTRA_INPUT_ID = "android.media.tv.extra.INPUT_ID";
    private static final java.lang.String TAG = "TvInputInfo";
    public static final int TYPE_COMPONENT = 1004;
    public static final int TYPE_COMPOSITE = 1001;
    public static final int TYPE_DISPLAY_PORT = 1008;
    public static final int TYPE_DVI = 1006;
    public static final int TYPE_HDMI = 1007;
    public static final int TYPE_OTHER = 1000;
    public static final int TYPE_SCART = 1003;
    public static final int TYPE_SVIDEO = 1002;
    public static final int TYPE_TUNER = 0;
    public static final int TYPE_VGA = 1005;
    private final boolean mCanPauseRecording;
    private final boolean mCanRecord;
    private final android.os.Bundle mExtras;
    private final int mHdmiConnectionRelativePosition;
    private final android.hardware.hdmi.HdmiDeviceInfo mHdmiDeviceInfo;
    private final android.graphics.drawable.Icon mIcon;
    private final android.graphics.drawable.Icon mIconDisconnected;
    private final android.graphics.drawable.Icon mIconStandby;
    private android.net.Uri mIconUri;
    private final java.lang.String mId;
    private final boolean mIsConnectedToHdmiSwitch;
    private final boolean mIsHardwareInput;
    private final java.lang.CharSequence mLabel;
    private final int mLabelResId;
    private final java.lang.String mParentId;
    private final android.content.pm.ResolveInfo mService;
    private final java.lang.String mSetupActivity;
    private final int mTunerCount;
    private final int mType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Type {
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static android.media.tv.TvInputInfo createTvInputInfo(android.content.Context context, android.content.pm.ResolveInfo resolveInfo, android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo, java.lang.String str, java.lang.String str2, android.net.Uri uri) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.media.tv.TvInputInfo build = new android.media.tv.TvInputInfo.Builder(context, resolveInfo).setHdmiDeviceInfo(hdmiDeviceInfo).setParentId(str).setLabel(str2).build();
        build.mIconUri = uri;
        return build;
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static android.media.tv.TvInputInfo createTvInputInfo(android.content.Context context, android.content.pm.ResolveInfo resolveInfo, android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo, java.lang.String str, int i, android.graphics.drawable.Icon icon) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        return new android.media.tv.TvInputInfo.Builder(context, resolveInfo).setHdmiDeviceInfo(hdmiDeviceInfo).setParentId(str).setLabel(i).setIcon(icon).build();
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static android.media.tv.TvInputInfo createTvInputInfo(android.content.Context context, android.content.pm.ResolveInfo resolveInfo, android.media.tv.TvInputHardwareInfo tvInputHardwareInfo, java.lang.String str, android.net.Uri uri) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.media.tv.TvInputInfo build = new android.media.tv.TvInputInfo.Builder(context, resolveInfo).setTvInputHardwareInfo(tvInputHardwareInfo).setLabel(str).build();
        build.mIconUri = uri;
        return build;
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static android.media.tv.TvInputInfo createTvInputInfo(android.content.Context context, android.content.pm.ResolveInfo resolveInfo, android.media.tv.TvInputHardwareInfo tvInputHardwareInfo, int i, android.graphics.drawable.Icon icon) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        return new android.media.tv.TvInputInfo.Builder(context, resolveInfo).setTvInputHardwareInfo(tvInputHardwareInfo).setLabel(i).setIcon(icon).build();
    }

    private TvInputInfo(android.content.pm.ResolveInfo resolveInfo, java.lang.String str, int i, boolean z, java.lang.CharSequence charSequence, int i2, android.graphics.drawable.Icon icon, android.graphics.drawable.Icon icon2, android.graphics.drawable.Icon icon3, java.lang.String str2, boolean z2, boolean z3, int i3, android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo, boolean z4, int i4, java.lang.String str3, android.os.Bundle bundle) {
        this.mService = resolveInfo;
        this.mId = str;
        this.mType = i;
        this.mIsHardwareInput = z;
        this.mLabel = charSequence;
        this.mLabelResId = i2;
        this.mIcon = icon;
        this.mIconStandby = icon2;
        this.mIconDisconnected = icon3;
        this.mSetupActivity = str2;
        this.mCanRecord = z2;
        this.mCanPauseRecording = z3;
        this.mTunerCount = i3;
        this.mHdmiDeviceInfo = hdmiDeviceInfo;
        this.mIsConnectedToHdmiSwitch = z4;
        this.mHdmiConnectionRelativePosition = i4;
        this.mParentId = str3;
        this.mExtras = bundle;
    }

    public java.lang.String getId() {
        return this.mId;
    }

    public java.lang.String getParentId() {
        return this.mParentId;
    }

    public android.content.pm.ServiceInfo getServiceInfo() {
        return this.mService.serviceInfo;
    }

    public android.content.ComponentName getComponent() {
        return new android.content.ComponentName(this.mService.serviceInfo.packageName, this.mService.serviceInfo.name);
    }

    public android.content.Intent createSetupIntent() {
        if (!android.text.TextUtils.isEmpty(this.mSetupActivity)) {
            android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_MAIN);
            intent.setClassName(this.mService.serviceInfo.packageName, this.mSetupActivity);
            intent.putExtra(EXTRA_INPUT_ID, getId());
            return intent;
        }
        return null;
    }

    @java.lang.Deprecated
    public android.content.Intent createSettingsIntent() {
        return null;
    }

    public int getType() {
        return this.mType;
    }

    public int getTunerCount() {
        return this.mTunerCount;
    }

    public boolean canRecord() {
        return this.mCanRecord;
    }

    public boolean canPauseRecording() {
        return this.mCanPauseRecording;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    @android.annotation.SystemApi
    public android.hardware.hdmi.HdmiDeviceInfo getHdmiDeviceInfo() {
        if (this.mType == 1007) {
            return this.mHdmiDeviceInfo;
        }
        return null;
    }

    public boolean isPassthroughInput() {
        return this.mType != 0;
    }

    @android.annotation.SystemApi
    public boolean isHardwareInput() {
        return this.mIsHardwareInput;
    }

    @android.annotation.SystemApi
    public boolean isConnectedToHdmiSwitch() {
        return this.mIsConnectedToHdmiSwitch;
    }

    public int getHdmiConnectionRelativePosition() {
        return this.mHdmiConnectionRelativePosition;
    }

    public boolean isHidden(android.content.Context context) {
        return android.media.tv.TvInputInfo.TvInputSettings.isHidden(context, this.mId, android.os.UserHandle.myUserId());
    }

    public java.lang.CharSequence loadLabel(android.content.Context context) {
        if (this.mLabelResId != 0) {
            return context.getPackageManager().getText(this.mService.serviceInfo.packageName, this.mLabelResId, null);
        }
        if (!android.text.TextUtils.isEmpty(this.mLabel)) {
            return this.mLabel;
        }
        return this.mService.loadLabel(context.getPackageManager());
    }

    public java.lang.CharSequence loadCustomLabel(android.content.Context context) {
        return android.media.tv.TvInputInfo.TvInputSettings.getCustomLabel(context, this.mId, android.os.UserHandle.myUserId());
    }

    public android.graphics.drawable.Drawable loadIcon(android.content.Context context) {
        if (this.mIcon != null) {
            return this.mIcon.loadDrawable(context);
        }
        if (this.mIconUri != null) {
            try {
                java.io.InputStream openInputStream = context.getContentResolver().openInputStream(this.mIconUri);
                try {
                    android.graphics.drawable.Drawable createFromStream = android.graphics.drawable.Drawable.createFromStream(openInputStream, null);
                    if (createFromStream != null) {
                        if (openInputStream != null) {
                            openInputStream.close();
                        }
                        return createFromStream;
                    }
                    if (openInputStream != null) {
                        openInputStream.close();
                    }
                } finally {
                }
            } catch (java.io.IOException e) {
                android.util.Log.w(TAG, "Loading the default icon due to a failure on loading " + this.mIconUri, e);
            }
        }
        return loadServiceIcon(context);
    }

    @android.annotation.SystemApi
    public android.graphics.drawable.Drawable loadIcon(android.content.Context context, int i) {
        if (i == 0) {
            return loadIcon(context);
        }
        if (i == 1) {
            if (this.mIconStandby != null) {
                return this.mIconStandby.loadDrawable(context);
            }
            return null;
        }
        if (i == 2) {
            if (this.mIconDisconnected != null) {
                return this.mIconDisconnected.loadDrawable(context);
            }
            return null;
        }
        throw new java.lang.IllegalArgumentException("Unknown state: " + i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int hashCode() {
        return this.mId.hashCode();
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof android.media.tv.TvInputInfo)) {
            return false;
        }
        android.media.tv.TvInputInfo tvInputInfo = (android.media.tv.TvInputInfo) obj;
        return java.util.Objects.equals(this.mService, tvInputInfo.mService) && android.text.TextUtils.equals(this.mId, tvInputInfo.mId) && this.mType == tvInputInfo.mType && this.mIsHardwareInput == tvInputInfo.mIsHardwareInput && android.text.TextUtils.equals(this.mLabel, tvInputInfo.mLabel) && java.util.Objects.equals(this.mIconUri, tvInputInfo.mIconUri) && this.mLabelResId == tvInputInfo.mLabelResId && java.util.Objects.equals(this.mIcon, tvInputInfo.mIcon) && java.util.Objects.equals(this.mIconStandby, tvInputInfo.mIconStandby) && java.util.Objects.equals(this.mIconDisconnected, tvInputInfo.mIconDisconnected) && android.text.TextUtils.equals(this.mSetupActivity, tvInputInfo.mSetupActivity) && this.mCanRecord == tvInputInfo.mCanRecord && this.mCanPauseRecording == tvInputInfo.mCanPauseRecording && this.mTunerCount == tvInputInfo.mTunerCount && java.util.Objects.equals(this.mHdmiDeviceInfo, tvInputInfo.mHdmiDeviceInfo) && this.mIsConnectedToHdmiSwitch == tvInputInfo.mIsConnectedToHdmiSwitch && this.mHdmiConnectionRelativePosition == tvInputInfo.mHdmiConnectionRelativePosition && android.text.TextUtils.equals(this.mParentId, tvInputInfo.mParentId) && java.util.Objects.equals(this.mExtras, tvInputInfo.mExtras);
    }

    public java.lang.String toString() {
        return "TvInputInfo{id=" + this.mId + ", pkg=" + this.mService.serviceInfo.packageName + ", service=" + this.mService.serviceInfo.name + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        this.mService.writeToParcel(parcel, i);
        parcel.writeString(this.mId);
        parcel.writeInt(this.mType);
        parcel.writeByte(this.mIsHardwareInput ? (byte) 1 : (byte) 0);
        android.text.TextUtils.writeToParcel(this.mLabel, parcel, i);
        parcel.writeParcelable(this.mIconUri, i);
        parcel.writeInt(this.mLabelResId);
        parcel.writeParcelable(this.mIcon, i);
        parcel.writeParcelable(this.mIconStandby, i);
        parcel.writeParcelable(this.mIconDisconnected, i);
        parcel.writeString(this.mSetupActivity);
        parcel.writeByte(this.mCanRecord ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.mCanPauseRecording ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.mTunerCount);
        parcel.writeParcelable(this.mHdmiDeviceInfo, i);
        parcel.writeByte(this.mIsConnectedToHdmiSwitch ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.mHdmiConnectionRelativePosition);
        parcel.writeString(this.mParentId);
        parcel.writeBundle(this.mExtras);
    }

    private android.graphics.drawable.Drawable loadServiceIcon(android.content.Context context) {
        if (this.mService.serviceInfo.icon == 0 && this.mService.serviceInfo.applicationInfo.icon == 0) {
            return null;
        }
        return this.mService.serviceInfo.loadIcon(context.getPackageManager());
    }

    private TvInputInfo(android.os.Parcel parcel) {
        this.mService = android.content.pm.ResolveInfo.CREATOR.createFromParcel(parcel);
        this.mId = parcel.readString();
        this.mType = parcel.readInt();
        this.mIsHardwareInput = parcel.readByte() == 1;
        this.mLabel = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mIconUri = (android.net.Uri) parcel.readParcelable(null, android.net.Uri.class);
        this.mLabelResId = parcel.readInt();
        this.mIcon = (android.graphics.drawable.Icon) parcel.readParcelable(null, android.graphics.drawable.Icon.class);
        this.mIconStandby = (android.graphics.drawable.Icon) parcel.readParcelable(null, android.graphics.drawable.Icon.class);
        this.mIconDisconnected = (android.graphics.drawable.Icon) parcel.readParcelable(null, android.graphics.drawable.Icon.class);
        this.mSetupActivity = parcel.readString();
        this.mCanRecord = parcel.readByte() == 1;
        this.mCanPauseRecording = parcel.readByte() == 1;
        this.mTunerCount = parcel.readInt();
        this.mHdmiDeviceInfo = (android.hardware.hdmi.HdmiDeviceInfo) parcel.readParcelable(null, android.hardware.hdmi.HdmiDeviceInfo.class);
        this.mIsConnectedToHdmiSwitch = parcel.readByte() == 1;
        this.mHdmiConnectionRelativePosition = parcel.readInt();
        this.mParentId = parcel.readString();
        this.mExtras = parcel.readBundle();
    }

    public static final class Builder {
        private static final java.lang.String DELIMITER_INFO_IN_ID = "/";
        private static final int LENGTH_HDMI_DEVICE_ID = 2;
        private static final int LENGTH_HDMI_PHYSICAL_ADDRESS = 4;
        private static final java.lang.String PREFIX_HARDWARE_DEVICE = "HW";
        private static final java.lang.String PREFIX_HDMI_DEVICE = "HDMI";
        private static final java.lang.String XML_START_TAG_NAME = "tv-input";
        private static final android.util.SparseIntArray sHardwareTypeToTvInputType = new android.util.SparseIntArray();
        private java.lang.Boolean mCanPauseRecording;
        private java.lang.Boolean mCanRecord;
        private final android.content.Context mContext;
        private android.os.Bundle mExtras;
        private android.hardware.hdmi.HdmiDeviceInfo mHdmiDeviceInfo;
        private android.graphics.drawable.Icon mIcon;
        private android.graphics.drawable.Icon mIconDisconnected;
        private android.graphics.drawable.Icon mIconStandby;
        private java.lang.CharSequence mLabel;
        private int mLabelResId;
        private java.lang.String mParentId;
        private final android.content.pm.ResolveInfo mResolveInfo;
        private java.lang.String mSetupActivity;
        private java.lang.Integer mTunerCount;
        private android.media.tv.TvInputHardwareInfo mTvInputHardwareInfo;

        static {
            sHardwareTypeToTvInputType.put(1, 1000);
            sHardwareTypeToTvInputType.put(2, 0);
            sHardwareTypeToTvInputType.put(3, 1001);
            sHardwareTypeToTvInputType.put(4, 1002);
            sHardwareTypeToTvInputType.put(5, 1003);
            sHardwareTypeToTvInputType.put(6, 1004);
            sHardwareTypeToTvInputType.put(7, 1005);
            sHardwareTypeToTvInputType.put(8, 1006);
            sHardwareTypeToTvInputType.put(9, 1007);
            sHardwareTypeToTvInputType.put(10, 1008);
        }

        public Builder(android.content.Context context, android.content.ComponentName componentName) {
            if (context == null) {
                throw new java.lang.IllegalArgumentException("context cannot be null.");
            }
            this.mResolveInfo = context.getPackageManager().resolveService(new android.content.Intent(android.media.tv.TvInputService.SERVICE_INTERFACE).setComponent(componentName), 132);
            if (this.mResolveInfo == null) {
                throw new java.lang.IllegalArgumentException("Invalid component. Can't find the service.");
            }
            this.mContext = context;
        }

        public Builder(android.content.Context context, android.content.pm.ResolveInfo resolveInfo) {
            if (context == null) {
                throw new java.lang.IllegalArgumentException("context cannot be null");
            }
            if (resolveInfo == null) {
                throw new java.lang.IllegalArgumentException("resolveInfo cannot be null");
            }
            this.mContext = context;
            this.mResolveInfo = resolveInfo;
        }

        @android.annotation.SystemApi
        public android.media.tv.TvInputInfo.Builder setIcon(android.graphics.drawable.Icon icon) {
            this.mIcon = icon;
            return this;
        }

        @android.annotation.SystemApi
        public android.media.tv.TvInputInfo.Builder setIcon(android.graphics.drawable.Icon icon, int i) {
            if (i == 0) {
                this.mIcon = icon;
            } else if (i == 1) {
                this.mIconStandby = icon;
            } else if (i == 2) {
                this.mIconDisconnected = icon;
            } else {
                throw new java.lang.IllegalArgumentException("Unknown state: " + i);
            }
            return this;
        }

        @android.annotation.SystemApi
        public android.media.tv.TvInputInfo.Builder setLabel(java.lang.CharSequence charSequence) {
            if (this.mLabelResId != 0) {
                throw new java.lang.IllegalStateException("Resource ID for label is already set.");
            }
            this.mLabel = charSequence;
            return this;
        }

        @android.annotation.SystemApi
        public android.media.tv.TvInputInfo.Builder setLabel(int i) {
            if (this.mLabel != null) {
                throw new java.lang.IllegalStateException("Label text is already set.");
            }
            this.mLabelResId = i;
            return this;
        }

        @android.annotation.SystemApi
        public android.media.tv.TvInputInfo.Builder setHdmiDeviceInfo(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) {
            if (this.mTvInputHardwareInfo != null) {
                android.util.Log.w(android.media.tv.TvInputInfo.TAG, "TvInputHardwareInfo will not be used to build this TvInputInfo");
                this.mTvInputHardwareInfo = null;
            }
            this.mHdmiDeviceInfo = hdmiDeviceInfo;
            return this;
        }

        @android.annotation.SystemApi
        public android.media.tv.TvInputInfo.Builder setParentId(java.lang.String str) {
            this.mParentId = str;
            return this;
        }

        @android.annotation.SystemApi
        public android.media.tv.TvInputInfo.Builder setTvInputHardwareInfo(android.media.tv.TvInputHardwareInfo tvInputHardwareInfo) {
            if (this.mHdmiDeviceInfo != null) {
                android.util.Log.w(android.media.tv.TvInputInfo.TAG, "mHdmiDeviceInfo will not be used to build this TvInputInfo");
                this.mHdmiDeviceInfo = null;
            }
            this.mTvInputHardwareInfo = tvInputHardwareInfo;
            return this;
        }

        public android.media.tv.TvInputInfo.Builder setTunerCount(int i) {
            this.mTunerCount = java.lang.Integer.valueOf(i);
            return this;
        }

        public android.media.tv.TvInputInfo.Builder setCanRecord(boolean z) {
            this.mCanRecord = java.lang.Boolean.valueOf(z);
            return this;
        }

        public android.media.tv.TvInputInfo.Builder setCanPauseRecording(boolean z) {
            this.mCanPauseRecording = java.lang.Boolean.valueOf(z);
            return this;
        }

        public android.media.tv.TvInputInfo.Builder setExtras(android.os.Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public android.media.tv.TvInputInfo build() {
            java.lang.String generateInputId;
            int i;
            boolean z;
            boolean z2;
            int i2;
            android.content.ComponentName componentName = new android.content.ComponentName(this.mResolveInfo.serviceInfo.packageName, this.mResolveInfo.serviceInfo.name);
            if (this.mHdmiDeviceInfo != null) {
                java.lang.String generateInputId2 = generateInputId(componentName, this.mHdmiDeviceInfo);
                int relativePosition = getRelativePosition(this.mContext, this.mHdmiDeviceInfo);
                generateInputId = generateInputId2;
                i2 = relativePosition;
                z = true;
                z2 = relativePosition == 2;
                i = 1007;
            } else if (this.mTvInputHardwareInfo != null) {
                java.lang.String generateInputId3 = generateInputId(componentName, this.mTvInputHardwareInfo);
                int i3 = sHardwareTypeToTvInputType.get(this.mTvInputHardwareInfo.getType(), 0);
                if (this.mTvInputHardwareInfo.getType() == 9) {
                    this.mHdmiDeviceInfo = android.hardware.hdmi.HdmiDeviceInfo.hardwarePort(65535, this.mTvInputHardwareInfo.getHdmiPortId());
                }
                generateInputId = generateInputId3;
                z = true;
                z2 = false;
                i2 = 0;
                i = i3;
            } else {
                generateInputId = generateInputId(componentName);
                i = 0;
                z = false;
                z2 = false;
                i2 = 0;
            }
            parseServiceMetadata(i);
            return new android.media.tv.TvInputInfo(this.mResolveInfo, generateInputId, i, z, this.mLabel, this.mLabelResId, this.mIcon, this.mIconStandby, this.mIconDisconnected, this.mSetupActivity, this.mCanRecord == null ? false : this.mCanRecord.booleanValue(), this.mCanPauseRecording == null ? false : this.mCanPauseRecording.booleanValue(), this.mTunerCount != null ? this.mTunerCount.intValue() : 0, this.mHdmiDeviceInfo, z2, i2, this.mParentId, this.mExtras);
        }

        private static java.lang.String generateInputId(android.content.ComponentName componentName) {
            return componentName.flattenToShortString();
        }

        private static java.lang.String generateInputId(android.content.ComponentName componentName, android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) {
            return componentName.flattenToShortString() + java.lang.String.format(java.util.Locale.ENGLISH, "/HDMI%04X%02X", java.lang.Integer.valueOf(hdmiDeviceInfo.getPhysicalAddress()), java.lang.Integer.valueOf(hdmiDeviceInfo.getId()));
        }

        private static java.lang.String generateInputId(android.content.ComponentName componentName, android.media.tv.TvInputHardwareInfo tvInputHardwareInfo) {
            return componentName.flattenToShortString() + DELIMITER_INFO_IN_ID + PREFIX_HARDWARE_DEVICE + tvInputHardwareInfo.getDeviceId();
        }

        private static int getRelativePosition(android.content.Context context, android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) {
            android.hardware.hdmi.HdmiControlManager hdmiControlManager = (android.hardware.hdmi.HdmiControlManager) context.getSystemService(android.content.Context.HDMI_CONTROL_SERVICE);
            if (hdmiControlManager == null) {
                return 0;
            }
            return android.hardware.hdmi.HdmiUtils.getHdmiAddressRelativePosition(hdmiDeviceInfo.getPhysicalAddress(), hdmiControlManager.getPhysicalAddress());
        }

        private void parseServiceMetadata(int i) {
            int next;
            android.content.pm.ServiceInfo serviceInfo = this.mResolveInfo.serviceInfo;
            android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
            try {
                try {
                    android.content.res.XmlResourceParser loadXmlMetaData = serviceInfo.loadXmlMetaData(packageManager, android.media.tv.TvInputService.SERVICE_META_DATA);
                    try {
                        if (loadXmlMetaData == null) {
                            throw new java.lang.IllegalStateException("No android.media.tv.input meta-data found for " + serviceInfo.name);
                        }
                        android.content.res.Resources resourcesForApplication = packageManager.getResourcesForApplication(serviceInfo.applicationInfo);
                        android.util.AttributeSet asAttributeSet = android.util.Xml.asAttributeSet(loadXmlMetaData);
                        do {
                            next = loadXmlMetaData.next();
                            if (next == 1) {
                                break;
                            }
                        } while (next != 2);
                        if (!XML_START_TAG_NAME.equals(loadXmlMetaData.getName())) {
                            throw new java.lang.IllegalStateException("Meta-data does not start with tv-input tag for " + serviceInfo.name);
                        }
                        android.content.res.TypedArray obtainAttributes = resourcesForApplication.obtainAttributes(asAttributeSet, com.android.internal.R.styleable.TvInputService);
                        this.mSetupActivity = obtainAttributes.getString(1);
                        if (this.mCanRecord == null) {
                            this.mCanRecord = java.lang.Boolean.valueOf(obtainAttributes.getBoolean(2, false));
                        }
                        if (this.mTunerCount == null && i == 0) {
                            this.mTunerCount = java.lang.Integer.valueOf(obtainAttributes.getInt(3, 1));
                        }
                        if (this.mCanPauseRecording == null) {
                            this.mCanPauseRecording = java.lang.Boolean.valueOf(obtainAttributes.getBoolean(4, false));
                        }
                        obtainAttributes.recycle();
                        if (loadXmlMetaData != null) {
                            loadXmlMetaData.close();
                        }
                    } catch (java.lang.Throwable th) {
                        if (loadXmlMetaData != null) {
                            try {
                                loadXmlMetaData.close();
                            } catch (java.lang.Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    throw new java.lang.IllegalStateException("No resources found for " + serviceInfo.packageName, e);
                }
            } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e2) {
                throw new java.lang.IllegalStateException("Failed reading meta-data for " + serviceInfo.packageName, e2);
            }
        }
    }

    @android.annotation.SystemApi
    public static final class TvInputSettings {
        private static final java.lang.String CUSTOM_NAME_SEPARATOR = ",";
        private static final java.lang.String TV_INPUT_SEPARATOR = ":";

        private TvInputSettings() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean isHidden(android.content.Context context, java.lang.String str, int i) {
            return getHiddenTvInputIds(context, i).contains(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static java.lang.String getCustomLabel(android.content.Context context, java.lang.String str, int i) {
            return getCustomLabels(context, i).get(str);
        }

        @android.annotation.SystemApi
        public static java.util.Set<java.lang.String> getHiddenTvInputIds(android.content.Context context, int i) {
            java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(context.getContentResolver(), android.provider.Settings.Secure.TV_INPUT_HIDDEN_INPUTS, i);
            java.util.HashSet hashSet = new java.util.HashSet();
            if (android.text.TextUtils.isEmpty(stringForUser)) {
                return hashSet;
            }
            for (java.lang.String str : stringForUser.split(":")) {
                hashSet.add(android.net.Uri.decode(str));
            }
            return hashSet;
        }

        @android.annotation.SystemApi
        public static java.util.Map<java.lang.String, java.lang.String> getCustomLabels(android.content.Context context, int i) {
            java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(context.getContentResolver(), android.provider.Settings.Secure.TV_INPUT_CUSTOM_LABELS, i);
            java.util.HashMap hashMap = new java.util.HashMap();
            if (android.text.TextUtils.isEmpty(stringForUser)) {
                return hashMap;
            }
            for (java.lang.String str : stringForUser.split(":")) {
                java.lang.String[] split = str.split(",");
                hashMap.put(android.net.Uri.decode(split[0]), android.net.Uri.decode(split[1]));
            }
            return hashMap;
        }

        @android.annotation.SystemApi
        public static void putHiddenTvInputs(android.content.Context context, java.util.Set<java.lang.String> set, int i) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            boolean z = true;
            for (java.lang.String str : set) {
                ensureValidField(str);
                if (z) {
                    z = false;
                } else {
                    sb.append(":");
                }
                sb.append(android.net.Uri.encode(str));
            }
            android.provider.Settings.Secure.putStringForUser(context.getContentResolver(), android.provider.Settings.Secure.TV_INPUT_HIDDEN_INPUTS, sb.toString(), i);
            android.media.tv.TvInputManager tvInputManager = (android.media.tv.TvInputManager) context.getSystemService(android.content.Context.TV_INPUT_SERVICE);
            java.util.Iterator<java.lang.String> it = set.iterator();
            while (it.hasNext()) {
                android.media.tv.TvInputInfo tvInputInfo = tvInputManager.getTvInputInfo(it.next());
                if (tvInputInfo != null) {
                    tvInputManager.updateTvInputInfo(tvInputInfo);
                }
            }
        }

        @android.annotation.SystemApi
        public static void putCustomLabels(android.content.Context context, java.util.Map<java.lang.String, java.lang.String> map, int i) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            boolean z = true;
            for (java.util.Map.Entry<java.lang.String, java.lang.String> entry : map.entrySet()) {
                ensureValidField(entry.getKey());
                ensureValidField(entry.getValue());
                if (z) {
                    z = false;
                } else {
                    sb.append(":");
                }
                sb.append(android.net.Uri.encode(entry.getKey()));
                sb.append(",");
                sb.append(android.net.Uri.encode(entry.getValue()));
            }
            android.provider.Settings.Secure.putStringForUser(context.getContentResolver(), android.provider.Settings.Secure.TV_INPUT_CUSTOM_LABELS, sb.toString(), i);
            android.media.tv.TvInputManager tvInputManager = (android.media.tv.TvInputManager) context.getSystemService(android.content.Context.TV_INPUT_SERVICE);
            java.util.Iterator<java.lang.String> it = map.keySet().iterator();
            while (it.hasNext()) {
                android.media.tv.TvInputInfo tvInputInfo = tvInputManager.getTvInputInfo(it.next());
                if (tvInputInfo != null) {
                    tvInputManager.updateTvInputInfo(tvInputInfo);
                }
            }
        }

        private static void ensureValidField(java.lang.String str) {
            if (android.text.TextUtils.isEmpty(str)) {
                throw new java.lang.IllegalArgumentException(str + " should not empty ");
            }
        }
    }
}
