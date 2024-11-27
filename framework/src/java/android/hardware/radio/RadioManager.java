package android.hardware.radio;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class RadioManager {
    public static final int BAND_AM = 0;
    public static final int BAND_AM_HD = 3;
    public static final int BAND_FM = 1;
    public static final int BAND_FM_HD = 2;
    public static final int BAND_INVALID = -1;
    public static final int CLASS_AM_FM = 0;
    public static final int CLASS_DT = 2;
    public static final int CLASS_SAT = 1;
    public static final int CONFIG_DAB_DAB_LINKING = 6;
    public static final int CONFIG_DAB_DAB_SOFT_LINKING = 8;
    public static final int CONFIG_DAB_FM_LINKING = 7;
    public static final int CONFIG_DAB_FM_SOFT_LINKING = 9;
    public static final int CONFIG_FORCE_ANALOG = 2;
    public static final int CONFIG_FORCE_ANALOG_AM = 11;
    public static final int CONFIG_FORCE_ANALOG_FM = 10;
    public static final int CONFIG_FORCE_DIGITAL = 3;
    public static final int CONFIG_FORCE_MONO = 1;
    public static final int CONFIG_RDS_AF = 4;
    public static final int CONFIG_RDS_REG = 5;
    public static final int REGION_ITU_1 = 0;
    public static final int REGION_ITU_2 = 1;
    public static final int REGION_JAPAN = 3;
    public static final int REGION_KOREA = 4;
    public static final int REGION_OIRT = 2;
    public static final int STATUS_BAD_VALUE = -22;
    public static final int STATUS_DEAD_OBJECT = -32;
    public static final int STATUS_ERROR = Integer.MIN_VALUE;
    public static final int STATUS_INVALID_OPERATION = -38;
    public static final int STATUS_NO_INIT = -19;
    public static final int STATUS_OK = 0;
    public static final int STATUS_PERMISSION_DENIED = -1;
    public static final int STATUS_TIMED_OUT = -110;
    private static final java.lang.String TAG = "BroadcastRadio.manager";
    private final java.util.Map<android.hardware.radio.Announcement.OnListUpdatedListener, android.hardware.radio.ICloseHandle> mAnnouncementListeners;
    private final android.content.Context mContext;
    private final android.hardware.radio.IRadioService mService;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Band {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ConfigFlag {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RadioStatusType {
    }

    private native int nativeListModules(java.util.List<android.hardware.radio.RadioManager.ModuleProperties> list);

    public static class ModuleProperties implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.radio.RadioManager.ModuleProperties> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.RadioManager.ModuleProperties>() { // from class: android.hardware.radio.RadioManager.ModuleProperties.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.radio.RadioManager.ModuleProperties createFromParcel(android.os.Parcel parcel) {
                return new android.hardware.radio.RadioManager.ModuleProperties(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.radio.RadioManager.ModuleProperties[] newArray(int i) {
                return new android.hardware.radio.RadioManager.ModuleProperties[i];
            }
        };
        private final android.hardware.radio.RadioManager.BandDescriptor[] mBands;
        private final int mClassId;
        private final java.util.Map<java.lang.String, java.lang.Integer> mDabFrequencyTable;
        private final int mId;
        private final java.lang.String mImplementor;
        private final boolean mIsBgScanSupported;
        private final boolean mIsCaptureSupported;
        private final boolean mIsInitializationRequired;
        private final int mNumAudioSources;
        private final int mNumTuners;
        private final java.lang.String mProduct;
        private final java.lang.String mSerial;
        private final java.lang.String mServiceName;
        private final java.util.Set<java.lang.Integer> mSupportedIdentifierTypes;
        private final java.util.Set<java.lang.Integer> mSupportedProgramTypes;
        private final java.util.Map<java.lang.String, java.lang.String> mVendorInfo;
        private final java.lang.String mVersion;

        public ModuleProperties(int i, java.lang.String str, int i2, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, int i3, int i4, boolean z, boolean z2, android.hardware.radio.RadioManager.BandDescriptor[] bandDescriptorArr, boolean z3, int[] iArr, int[] iArr2, java.util.Map<java.lang.String, java.lang.Integer> map, java.util.Map<java.lang.String, java.lang.String> map2) {
            this.mId = i;
            this.mServiceName = android.text.TextUtils.isEmpty(str) ? "default" : str;
            this.mClassId = i2;
            this.mImplementor = str2;
            this.mProduct = str3;
            this.mVersion = str4;
            this.mSerial = str5;
            this.mNumTuners = i3;
            this.mNumAudioSources = i4;
            this.mIsInitializationRequired = z;
            this.mIsCaptureSupported = z2;
            this.mBands = bandDescriptorArr;
            this.mIsBgScanSupported = z3;
            this.mSupportedProgramTypes = arrayToSet(iArr);
            this.mSupportedIdentifierTypes = arrayToSet(iArr2);
            if (map != null) {
                for (java.util.Map.Entry<java.lang.String, java.lang.Integer> entry : map.entrySet()) {
                    java.util.Objects.requireNonNull(entry.getKey());
                    java.util.Objects.requireNonNull(entry.getValue());
                }
            }
            this.mDabFrequencyTable = (map == null || map.isEmpty()) ? null : map;
            this.mVendorInfo = map2 == null ? new java.util.HashMap<>() : map2;
        }

        private static java.util.Set<java.lang.Integer> arrayToSet(int[] iArr) {
            return (java.util.Set) java.util.Arrays.stream(iArr).boxed().collect(java.util.stream.Collectors.toSet());
        }

        private static int[] setToArray(java.util.Set<java.lang.Integer> set) {
            return set.stream().mapToInt(new android.app.admin.PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray();
        }

        public int getId() {
            return this.mId;
        }

        public java.lang.String getServiceName() {
            return this.mServiceName;
        }

        public int getClassId() {
            return this.mClassId;
        }

        public java.lang.String getImplementor() {
            return this.mImplementor;
        }

        public java.lang.String getProduct() {
            return this.mProduct;
        }

        public java.lang.String getVersion() {
            return this.mVersion;
        }

        public java.lang.String getSerial() {
            return this.mSerial;
        }

        public int getNumTuners() {
            return this.mNumTuners;
        }

        public int getNumAudioSources() {
            return this.mNumAudioSources;
        }

        public boolean isInitializationRequired() {
            return this.mIsInitializationRequired;
        }

        public boolean isCaptureSupported() {
            return this.mIsCaptureSupported;
        }

        public boolean isBackgroundScanningSupported() {
            return this.mIsBgScanSupported;
        }

        public boolean isProgramTypeSupported(int i) {
            return this.mSupportedProgramTypes.contains(java.lang.Integer.valueOf(i));
        }

        public boolean isProgramIdentifierSupported(int i) {
            return this.mSupportedIdentifierTypes.contains(java.lang.Integer.valueOf(i));
        }

        public java.util.Map<java.lang.String, java.lang.Integer> getDabFrequencyTable() {
            return this.mDabFrequencyTable;
        }

        public java.util.Map<java.lang.String, java.lang.String> getVendorInfo() {
            return this.mVendorInfo;
        }

        public android.hardware.radio.RadioManager.BandDescriptor[] getBands() {
            return this.mBands;
        }

        private ModuleProperties(android.os.Parcel parcel) {
            this.mId = parcel.readInt();
            java.lang.String readString = parcel.readString();
            this.mServiceName = android.text.TextUtils.isEmpty(readString) ? "default" : readString;
            this.mClassId = parcel.readInt();
            this.mImplementor = parcel.readString();
            this.mProduct = parcel.readString();
            this.mVersion = parcel.readString();
            this.mSerial = parcel.readString();
            this.mNumTuners = parcel.readInt();
            this.mNumAudioSources = parcel.readInt();
            this.mIsInitializationRequired = parcel.readInt() == 1;
            this.mIsCaptureSupported = parcel.readInt() == 1;
            android.os.Parcelable[] parcelableArr = (android.os.Parcelable[]) parcel.readParcelableArray(android.hardware.radio.RadioManager.BandDescriptor.class.getClassLoader(), android.hardware.radio.RadioManager.BandDescriptor.class);
            this.mBands = new android.hardware.radio.RadioManager.BandDescriptor[parcelableArr.length];
            for (int i = 0; i < parcelableArr.length; i++) {
                this.mBands[i] = (android.hardware.radio.RadioManager.BandDescriptor) parcelableArr[i];
            }
            this.mIsBgScanSupported = parcel.readInt() == 1;
            this.mSupportedProgramTypes = arrayToSet(parcel.createIntArray());
            this.mSupportedIdentifierTypes = arrayToSet(parcel.createIntArray());
            java.util.Map<java.lang.String, java.lang.Integer> readStringIntMap = android.hardware.radio.Utils.readStringIntMap(parcel);
            this.mDabFrequencyTable = readStringIntMap.isEmpty() ? null : readStringIntMap;
            this.mVendorInfo = android.hardware.radio.Utils.readStringMap(parcel);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mId);
            parcel.writeString(this.mServiceName);
            parcel.writeInt(this.mClassId);
            parcel.writeString(this.mImplementor);
            parcel.writeString(this.mProduct);
            parcel.writeString(this.mVersion);
            parcel.writeString(this.mSerial);
            parcel.writeInt(this.mNumTuners);
            parcel.writeInt(this.mNumAudioSources);
            parcel.writeInt(this.mIsInitializationRequired ? 1 : 0);
            parcel.writeInt(this.mIsCaptureSupported ? 1 : 0);
            parcel.writeParcelableArray(this.mBands, i);
            parcel.writeInt(this.mIsBgScanSupported ? 1 : 0);
            parcel.writeIntArray(setToArray(this.mSupportedProgramTypes));
            parcel.writeIntArray(setToArray(this.mSupportedIdentifierTypes));
            android.hardware.radio.Utils.writeStringIntMap(parcel, this.mDabFrequencyTable);
            android.hardware.radio.Utils.writeStringMap(parcel, this.mVendorInfo);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public java.lang.String toString() {
            return "ModuleProperties [mId=" + this.mId + ", mServiceName=" + this.mServiceName + ", mClassId=" + this.mClassId + ", mImplementor=" + this.mImplementor + ", mProduct=" + this.mProduct + ", mVersion=" + this.mVersion + ", mSerial=" + this.mSerial + ", mNumTuners=" + this.mNumTuners + ", mNumAudioSources=" + this.mNumAudioSources + ", mIsInitializationRequired=" + this.mIsInitializationRequired + ", mIsCaptureSupported=" + this.mIsCaptureSupported + ", mIsBgScanSupported=" + this.mIsBgScanSupported + ", mBands=" + java.util.Arrays.toString(this.mBands) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.mId), this.mServiceName, java.lang.Integer.valueOf(this.mClassId), this.mImplementor, this.mProduct, this.mVersion, this.mSerial, java.lang.Integer.valueOf(this.mNumTuners), java.lang.Integer.valueOf(this.mNumAudioSources), java.lang.Boolean.valueOf(this.mIsInitializationRequired), java.lang.Boolean.valueOf(this.mIsCaptureSupported), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mBands)), java.lang.Boolean.valueOf(this.mIsBgScanSupported), this.mDabFrequencyTable, this.mVendorInfo);
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof android.hardware.radio.RadioManager.ModuleProperties)) {
                return false;
            }
            android.hardware.radio.RadioManager.ModuleProperties moduleProperties = (android.hardware.radio.RadioManager.ModuleProperties) obj;
            return this.mId == moduleProperties.getId() && android.text.TextUtils.equals(this.mServiceName, moduleProperties.mServiceName) && this.mClassId == moduleProperties.mClassId && java.util.Objects.equals(this.mImplementor, moduleProperties.mImplementor) && java.util.Objects.equals(this.mProduct, moduleProperties.mProduct) && java.util.Objects.equals(this.mVersion, moduleProperties.mVersion) && java.util.Objects.equals(this.mSerial, moduleProperties.mSerial) && this.mNumTuners == moduleProperties.mNumTuners && this.mNumAudioSources == moduleProperties.mNumAudioSources && this.mIsInitializationRequired == moduleProperties.mIsInitializationRequired && this.mIsCaptureSupported == moduleProperties.mIsCaptureSupported && java.util.Arrays.equals(this.mBands, moduleProperties.mBands) && this.mIsBgScanSupported == moduleProperties.mIsBgScanSupported && java.util.Objects.equals(this.mDabFrequencyTable, moduleProperties.mDabFrequencyTable) && java.util.Objects.equals(this.mVendorInfo, moduleProperties.mVendorInfo);
        }
    }

    public static class BandDescriptor implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.radio.RadioManager.BandDescriptor> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.RadioManager.BandDescriptor>() { // from class: android.hardware.radio.RadioManager.BandDescriptor.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.radio.RadioManager.BandDescriptor createFromParcel(android.os.Parcel parcel) {
                int lookupTypeFromParcel = android.hardware.radio.RadioManager.BandDescriptor.lookupTypeFromParcel(parcel);
                byte b = 0;
                switch (lookupTypeFromParcel) {
                    case 0:
                    case 3:
                        return new android.hardware.radio.RadioManager.AmBandDescriptor(parcel);
                    case 1:
                    case 2:
                        return new android.hardware.radio.RadioManager.FmBandDescriptor(parcel);
                    default:
                        throw new java.lang.IllegalArgumentException("Unsupported band: " + lookupTypeFromParcel);
                }
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.radio.RadioManager.BandDescriptor[] newArray(int i) {
                return new android.hardware.radio.RadioManager.BandDescriptor[i];
            }
        };
        private final int mLowerLimit;
        private final int mRegion;
        private final int mSpacing;
        private final int mType;
        private final int mUpperLimit;

        BandDescriptor(int i, int i2, int i3, int i4, int i5) {
            if (i2 != 0 && i2 != 1 && i2 != 2 && i2 != 3) {
                throw new java.lang.IllegalArgumentException("Unsupported band: " + i2);
            }
            this.mRegion = i;
            this.mType = i2;
            this.mLowerLimit = i3;
            this.mUpperLimit = i4;
            this.mSpacing = i5;
        }

        public int getRegion() {
            return this.mRegion;
        }

        public int getType() {
            return this.mType;
        }

        public boolean isAmBand() {
            return this.mType == 0 || this.mType == 3;
        }

        public boolean isFmBand() {
            return this.mType == 1 || this.mType == 2;
        }

        public int getLowerLimit() {
            return this.mLowerLimit;
        }

        public int getUpperLimit() {
            return this.mUpperLimit;
        }

        public int getSpacing() {
            return this.mSpacing;
        }

        private BandDescriptor(android.os.Parcel parcel) {
            this.mRegion = parcel.readInt();
            this.mType = parcel.readInt();
            this.mLowerLimit = parcel.readInt();
            this.mUpperLimit = parcel.readInt();
            this.mSpacing = parcel.readInt();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static int lookupTypeFromParcel(android.os.Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            parcel.readInt();
            int readInt = parcel.readInt();
            parcel.setDataPosition(dataPosition);
            return readInt;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mRegion);
            parcel.writeInt(this.mType);
            parcel.writeInt(this.mLowerLimit);
            parcel.writeInt(this.mUpperLimit);
            parcel.writeInt(this.mSpacing);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public java.lang.String toString() {
            return "BandDescriptor [mRegion=" + this.mRegion + ", mType=" + this.mType + ", mLowerLimit=" + this.mLowerLimit + ", mUpperLimit=" + this.mUpperLimit + ", mSpacing=" + this.mSpacing + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }

        public int hashCode() {
            return ((((((((this.mRegion + 31) * 31) + this.mType) * 31) + this.mLowerLimit) * 31) + this.mUpperLimit) * 31) + this.mSpacing;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof android.hardware.radio.RadioManager.BandDescriptor)) {
                return false;
            }
            android.hardware.radio.RadioManager.BandDescriptor bandDescriptor = (android.hardware.radio.RadioManager.BandDescriptor) obj;
            return this.mRegion == bandDescriptor.getRegion() && this.mType == bandDescriptor.getType() && this.mLowerLimit == bandDescriptor.getLowerLimit() && this.mUpperLimit == bandDescriptor.getUpperLimit() && this.mSpacing == bandDescriptor.getSpacing();
        }
    }

    public static class FmBandDescriptor extends android.hardware.radio.RadioManager.BandDescriptor {
        public static final android.os.Parcelable.Creator<android.hardware.radio.RadioManager.FmBandDescriptor> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.RadioManager.FmBandDescriptor>() { // from class: android.hardware.radio.RadioManager.FmBandDescriptor.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.radio.RadioManager.FmBandDescriptor createFromParcel(android.os.Parcel parcel) {
                return new android.hardware.radio.RadioManager.FmBandDescriptor(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.radio.RadioManager.FmBandDescriptor[] newArray(int i) {
                return new android.hardware.radio.RadioManager.FmBandDescriptor[i];
            }
        };
        private final boolean mAf;
        private final boolean mEa;
        private final boolean mRds;
        private final boolean mStereo;
        private final boolean mTa;

        public FmBandDescriptor(int i, int i2, int i3, int i4, int i5, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
            super(i, i2, i3, i4, i5);
            this.mStereo = z;
            this.mRds = z2;
            this.mTa = z3;
            this.mAf = z4;
            this.mEa = z5;
        }

        public boolean isStereoSupported() {
            return this.mStereo;
        }

        public boolean isRdsSupported() {
            return this.mRds;
        }

        public boolean isTaSupported() {
            return this.mTa;
        }

        public boolean isAfSupported() {
            return this.mAf;
        }

        public boolean isEaSupported() {
            return this.mEa;
        }

        private FmBandDescriptor(android.os.Parcel parcel) {
            super(parcel);
            this.mStereo = parcel.readByte() == 1;
            this.mRds = parcel.readByte() == 1;
            this.mTa = parcel.readByte() == 1;
            this.mAf = parcel.readByte() == 1;
            this.mEa = parcel.readByte() == 1;
        }

        @Override // android.hardware.radio.RadioManager.BandDescriptor, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeByte(this.mStereo ? (byte) 1 : (byte) 0);
            parcel.writeByte(this.mRds ? (byte) 1 : (byte) 0);
            parcel.writeByte(this.mTa ? (byte) 1 : (byte) 0);
            parcel.writeByte(this.mAf ? (byte) 1 : (byte) 0);
            parcel.writeByte(this.mEa ? (byte) 1 : (byte) 0);
        }

        @Override // android.hardware.radio.RadioManager.BandDescriptor, android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.hardware.radio.RadioManager.BandDescriptor
        public java.lang.String toString() {
            return "FmBandDescriptor [ " + super.toString() + " mStereo=" + this.mStereo + ", mRds=" + this.mRds + ", mTa=" + this.mTa + ", mAf=" + this.mAf + ", mEa =" + this.mEa + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }

        @Override // android.hardware.radio.RadioManager.BandDescriptor
        public int hashCode() {
            return (((((((((super.hashCode() * 31) + (this.mStereo ? 1 : 0)) * 31) + (this.mRds ? 1 : 0)) * 31) + (this.mTa ? 1 : 0)) * 31) + (this.mAf ? 1 : 0)) * 31) + (this.mEa ? 1 : 0);
        }

        @Override // android.hardware.radio.RadioManager.BandDescriptor
        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!super.equals(obj) || !(obj instanceof android.hardware.radio.RadioManager.FmBandDescriptor)) {
                return false;
            }
            android.hardware.radio.RadioManager.FmBandDescriptor fmBandDescriptor = (android.hardware.radio.RadioManager.FmBandDescriptor) obj;
            return this.mStereo == fmBandDescriptor.isStereoSupported() && this.mRds == fmBandDescriptor.isRdsSupported() && this.mTa == fmBandDescriptor.isTaSupported() && this.mAf == fmBandDescriptor.isAfSupported() && this.mEa == fmBandDescriptor.isEaSupported();
        }
    }

    public static class AmBandDescriptor extends android.hardware.radio.RadioManager.BandDescriptor {
        public static final android.os.Parcelable.Creator<android.hardware.radio.RadioManager.AmBandDescriptor> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.RadioManager.AmBandDescriptor>() { // from class: android.hardware.radio.RadioManager.AmBandDescriptor.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.radio.RadioManager.AmBandDescriptor createFromParcel(android.os.Parcel parcel) {
                return new android.hardware.radio.RadioManager.AmBandDescriptor(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.radio.RadioManager.AmBandDescriptor[] newArray(int i) {
                return new android.hardware.radio.RadioManager.AmBandDescriptor[i];
            }
        };
        private final boolean mStereo;

        public AmBandDescriptor(int i, int i2, int i3, int i4, int i5, boolean z) {
            super(i, i2, i3, i4, i5);
            this.mStereo = z;
        }

        public boolean isStereoSupported() {
            return this.mStereo;
        }

        private AmBandDescriptor(android.os.Parcel parcel) {
            super(parcel);
            this.mStereo = parcel.readByte() == 1;
        }

        @Override // android.hardware.radio.RadioManager.BandDescriptor, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeByte(this.mStereo ? (byte) 1 : (byte) 0);
        }

        @Override // android.hardware.radio.RadioManager.BandDescriptor, android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.hardware.radio.RadioManager.BandDescriptor
        public java.lang.String toString() {
            return "AmBandDescriptor [ " + super.toString() + " mStereo=" + this.mStereo + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }

        @Override // android.hardware.radio.RadioManager.BandDescriptor
        public int hashCode() {
            return (super.hashCode() * 31) + (this.mStereo ? 1 : 0);
        }

        @Override // android.hardware.radio.RadioManager.BandDescriptor
        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            return super.equals(obj) && (obj instanceof android.hardware.radio.RadioManager.AmBandDescriptor) && this.mStereo == ((android.hardware.radio.RadioManager.AmBandDescriptor) obj).isStereoSupported();
        }
    }

    public static class BandConfig implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.radio.RadioManager.BandConfig> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.RadioManager.BandConfig>() { // from class: android.hardware.radio.RadioManager.BandConfig.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.radio.RadioManager.BandConfig createFromParcel(android.os.Parcel parcel) {
                int lookupTypeFromParcel = android.hardware.radio.RadioManager.BandDescriptor.lookupTypeFromParcel(parcel);
                byte b = 0;
                switch (lookupTypeFromParcel) {
                    case 0:
                    case 3:
                        return new android.hardware.radio.RadioManager.AmBandConfig(parcel);
                    case 1:
                    case 2:
                        return new android.hardware.radio.RadioManager.FmBandConfig(parcel);
                    default:
                        throw new java.lang.IllegalArgumentException("Unsupported band: " + lookupTypeFromParcel);
                }
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.radio.RadioManager.BandConfig[] newArray(int i) {
                return new android.hardware.radio.RadioManager.BandConfig[i];
            }
        };
        final android.hardware.radio.RadioManager.BandDescriptor mDescriptor;

        BandConfig(android.hardware.radio.RadioManager.BandDescriptor bandDescriptor) {
            java.util.Objects.requireNonNull(bandDescriptor, "Descriptor cannot be null");
            this.mDescriptor = new android.hardware.radio.RadioManager.BandDescriptor(bandDescriptor.getRegion(), bandDescriptor.getType(), bandDescriptor.getLowerLimit(), bandDescriptor.getUpperLimit(), bandDescriptor.getSpacing());
        }

        BandConfig(int i, int i2, int i3, int i4, int i5) {
            this.mDescriptor = new android.hardware.radio.RadioManager.BandDescriptor(i, i2, i3, i4, i5);
        }

        private BandConfig(android.os.Parcel parcel) {
            this.mDescriptor = new android.hardware.radio.RadioManager.BandDescriptor(parcel);
        }

        android.hardware.radio.RadioManager.BandDescriptor getDescriptor() {
            return this.mDescriptor;
        }

        public int getRegion() {
            return this.mDescriptor.getRegion();
        }

        public int getType() {
            return this.mDescriptor.getType();
        }

        public int getLowerLimit() {
            return this.mDescriptor.getLowerLimit();
        }

        public int getUpperLimit() {
            return this.mDescriptor.getUpperLimit();
        }

        public int getSpacing() {
            return this.mDescriptor.getSpacing();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            this.mDescriptor.writeToParcel(parcel, i);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public java.lang.String toString() {
            return "BandConfig [ " + this.mDescriptor.toString() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }

        public int hashCode() {
            return 31 + this.mDescriptor.hashCode();
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof android.hardware.radio.RadioManager.BandConfig)) {
                return false;
            }
            android.hardware.radio.RadioManager.BandDescriptor descriptor = ((android.hardware.radio.RadioManager.BandConfig) obj).getDescriptor();
            if ((this.mDescriptor == null) != (descriptor == null)) {
                return false;
            }
            return this.mDescriptor == null || this.mDescriptor.equals(descriptor);
        }
    }

    public static class FmBandConfig extends android.hardware.radio.RadioManager.BandConfig {
        public static final android.os.Parcelable.Creator<android.hardware.radio.RadioManager.FmBandConfig> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.RadioManager.FmBandConfig>() { // from class: android.hardware.radio.RadioManager.FmBandConfig.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.radio.RadioManager.FmBandConfig createFromParcel(android.os.Parcel parcel) {
                return new android.hardware.radio.RadioManager.FmBandConfig(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.radio.RadioManager.FmBandConfig[] newArray(int i) {
                return new android.hardware.radio.RadioManager.FmBandConfig[i];
            }
        };
        private final boolean mAf;
        private final boolean mEa;
        private final boolean mRds;
        private final boolean mStereo;
        private final boolean mTa;

        public FmBandConfig(android.hardware.radio.RadioManager.FmBandDescriptor fmBandDescriptor) {
            super(fmBandDescriptor);
            this.mStereo = fmBandDescriptor.isStereoSupported();
            this.mRds = fmBandDescriptor.isRdsSupported();
            this.mTa = fmBandDescriptor.isTaSupported();
            this.mAf = fmBandDescriptor.isAfSupported();
            this.mEa = fmBandDescriptor.isEaSupported();
        }

        FmBandConfig(int i, int i2, int i3, int i4, int i5, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
            super(i, i2, i3, i4, i5);
            this.mStereo = z;
            this.mRds = z2;
            this.mTa = z3;
            this.mAf = z4;
            this.mEa = z5;
        }

        public boolean getStereo() {
            return this.mStereo;
        }

        public boolean getRds() {
            return this.mRds;
        }

        public boolean getTa() {
            return this.mTa;
        }

        public boolean getAf() {
            return this.mAf;
        }

        public boolean getEa() {
            return this.mEa;
        }

        private FmBandConfig(android.os.Parcel parcel) {
            super(parcel);
            this.mStereo = parcel.readByte() == 1;
            this.mRds = parcel.readByte() == 1;
            this.mTa = parcel.readByte() == 1;
            this.mAf = parcel.readByte() == 1;
            this.mEa = parcel.readByte() == 1;
        }

        @Override // android.hardware.radio.RadioManager.BandConfig, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeByte(this.mStereo ? (byte) 1 : (byte) 0);
            parcel.writeByte(this.mRds ? (byte) 1 : (byte) 0);
            parcel.writeByte(this.mTa ? (byte) 1 : (byte) 0);
            parcel.writeByte(this.mAf ? (byte) 1 : (byte) 0);
            parcel.writeByte(this.mEa ? (byte) 1 : (byte) 0);
        }

        @Override // android.hardware.radio.RadioManager.BandConfig, android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.hardware.radio.RadioManager.BandConfig
        public java.lang.String toString() {
            return "FmBandConfig [" + super.toString() + ", mStereo=" + this.mStereo + ", mRds=" + this.mRds + ", mTa=" + this.mTa + ", mAf=" + this.mAf + ", mEa =" + this.mEa + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }

        @Override // android.hardware.radio.RadioManager.BandConfig
        public int hashCode() {
            return (((((((((super.hashCode() * 31) + (this.mStereo ? 1 : 0)) * 31) + (this.mRds ? 1 : 0)) * 31) + (this.mTa ? 1 : 0)) * 31) + (this.mAf ? 1 : 0)) * 31) + (this.mEa ? 1 : 0);
        }

        @Override // android.hardware.radio.RadioManager.BandConfig
        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!super.equals(obj) || !(obj instanceof android.hardware.radio.RadioManager.FmBandConfig)) {
                return false;
            }
            android.hardware.radio.RadioManager.FmBandConfig fmBandConfig = (android.hardware.radio.RadioManager.FmBandConfig) obj;
            return this.mStereo == fmBandConfig.mStereo && this.mRds == fmBandConfig.mRds && this.mTa == fmBandConfig.mTa && this.mAf == fmBandConfig.mAf && this.mEa == fmBandConfig.mEa;
        }

        public static class Builder {
            private boolean mAf;
            private final android.hardware.radio.RadioManager.BandDescriptor mDescriptor;
            private boolean mEa;
            private boolean mRds;
            private boolean mStereo;
            private boolean mTa;

            public Builder(android.hardware.radio.RadioManager.FmBandDescriptor fmBandDescriptor) {
                this.mDescriptor = new android.hardware.radio.RadioManager.BandDescriptor(fmBandDescriptor.getRegion(), fmBandDescriptor.getType(), fmBandDescriptor.getLowerLimit(), fmBandDescriptor.getUpperLimit(), fmBandDescriptor.getSpacing());
                this.mStereo = fmBandDescriptor.isStereoSupported();
                this.mRds = fmBandDescriptor.isRdsSupported();
                this.mTa = fmBandDescriptor.isTaSupported();
                this.mAf = fmBandDescriptor.isAfSupported();
                this.mEa = fmBandDescriptor.isEaSupported();
            }

            public Builder(android.hardware.radio.RadioManager.FmBandConfig fmBandConfig) {
                this.mDescriptor = new android.hardware.radio.RadioManager.BandDescriptor(fmBandConfig.getRegion(), fmBandConfig.getType(), fmBandConfig.getLowerLimit(), fmBandConfig.getUpperLimit(), fmBandConfig.getSpacing());
                this.mStereo = fmBandConfig.getStereo();
                this.mRds = fmBandConfig.getRds();
                this.mTa = fmBandConfig.getTa();
                this.mAf = fmBandConfig.getAf();
                this.mEa = fmBandConfig.getEa();
            }

            public android.hardware.radio.RadioManager.FmBandConfig build() {
                return new android.hardware.radio.RadioManager.FmBandConfig(this.mDescriptor.getRegion(), this.mDescriptor.getType(), this.mDescriptor.getLowerLimit(), this.mDescriptor.getUpperLimit(), this.mDescriptor.getSpacing(), this.mStereo, this.mRds, this.mTa, this.mAf, this.mEa);
            }

            public android.hardware.radio.RadioManager.FmBandConfig.Builder setStereo(boolean z) {
                this.mStereo = z;
                return this;
            }

            public android.hardware.radio.RadioManager.FmBandConfig.Builder setRds(boolean z) {
                this.mRds = z;
                return this;
            }

            public android.hardware.radio.RadioManager.FmBandConfig.Builder setTa(boolean z) {
                this.mTa = z;
                return this;
            }

            public android.hardware.radio.RadioManager.FmBandConfig.Builder setAf(boolean z) {
                this.mAf = z;
                return this;
            }

            public android.hardware.radio.RadioManager.FmBandConfig.Builder setEa(boolean z) {
                this.mEa = z;
                return this;
            }
        }
    }

    public static class AmBandConfig extends android.hardware.radio.RadioManager.BandConfig {
        public static final android.os.Parcelable.Creator<android.hardware.radio.RadioManager.AmBandConfig> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.RadioManager.AmBandConfig>() { // from class: android.hardware.radio.RadioManager.AmBandConfig.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.radio.RadioManager.AmBandConfig createFromParcel(android.os.Parcel parcel) {
                return new android.hardware.radio.RadioManager.AmBandConfig(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.radio.RadioManager.AmBandConfig[] newArray(int i) {
                return new android.hardware.radio.RadioManager.AmBandConfig[i];
            }
        };
        private final boolean mStereo;

        public AmBandConfig(android.hardware.radio.RadioManager.AmBandDescriptor amBandDescriptor) {
            super(amBandDescriptor);
            this.mStereo = amBandDescriptor.isStereoSupported();
        }

        AmBandConfig(int i, int i2, int i3, int i4, int i5, boolean z) {
            super(i, i2, i3, i4, i5);
            this.mStereo = z;
        }

        public boolean getStereo() {
            return this.mStereo;
        }

        private AmBandConfig(android.os.Parcel parcel) {
            super(parcel);
            this.mStereo = parcel.readByte() == 1;
        }

        @Override // android.hardware.radio.RadioManager.BandConfig, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeByte(this.mStereo ? (byte) 1 : (byte) 0);
        }

        @Override // android.hardware.radio.RadioManager.BandConfig, android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.hardware.radio.RadioManager.BandConfig
        public java.lang.String toString() {
            return "AmBandConfig [" + super.toString() + ", mStereo=" + this.mStereo + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }

        @Override // android.hardware.radio.RadioManager.BandConfig
        public int hashCode() {
            return (super.hashCode() * 31) + (this.mStereo ? 1 : 0);
        }

        @Override // android.hardware.radio.RadioManager.BandConfig
        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            return super.equals(obj) && (obj instanceof android.hardware.radio.RadioManager.AmBandConfig) && this.mStereo == ((android.hardware.radio.RadioManager.AmBandConfig) obj).getStereo();
        }

        public static class Builder {
            private final android.hardware.radio.RadioManager.BandDescriptor mDescriptor;
            private boolean mStereo;

            public Builder(android.hardware.radio.RadioManager.AmBandDescriptor amBandDescriptor) {
                this.mDescriptor = new android.hardware.radio.RadioManager.BandDescriptor(amBandDescriptor.getRegion(), amBandDescriptor.getType(), amBandDescriptor.getLowerLimit(), amBandDescriptor.getUpperLimit(), amBandDescriptor.getSpacing());
                this.mStereo = amBandDescriptor.isStereoSupported();
            }

            public Builder(android.hardware.radio.RadioManager.AmBandConfig amBandConfig) {
                this.mDescriptor = new android.hardware.radio.RadioManager.BandDescriptor(amBandConfig.getRegion(), amBandConfig.getType(), amBandConfig.getLowerLimit(), amBandConfig.getUpperLimit(), amBandConfig.getSpacing());
                this.mStereo = amBandConfig.getStereo();
            }

            public android.hardware.radio.RadioManager.AmBandConfig build() {
                return new android.hardware.radio.RadioManager.AmBandConfig(this.mDescriptor.getRegion(), this.mDescriptor.getType(), this.mDescriptor.getLowerLimit(), this.mDescriptor.getUpperLimit(), this.mDescriptor.getSpacing(), this.mStereo);
            }

            public android.hardware.radio.RadioManager.AmBandConfig.Builder setStereo(boolean z) {
                this.mStereo = z;
                return this;
            }
        }
    }

    public static class ProgramInfo implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.radio.RadioManager.ProgramInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.RadioManager.ProgramInfo>() { // from class: android.hardware.radio.RadioManager.ProgramInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.radio.RadioManager.ProgramInfo createFromParcel(android.os.Parcel parcel) {
                return new android.hardware.radio.RadioManager.ProgramInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.radio.RadioManager.ProgramInfo[] newArray(int i) {
                return new android.hardware.radio.RadioManager.ProgramInfo[i];
            }
        };
        private static final int FLAG_HD_AUDIO_ACQUIRED = 256;
        private static final int FLAG_HD_SIS_ACQUIRED = 128;
        private static final int FLAG_LIVE = 1;
        private static final int FLAG_MUTED = 2;
        private static final int FLAG_SIGNAL_ACQUIRED = 64;
        private static final int FLAG_STEREO = 32;
        private static final int FLAG_TRAFFIC_ANNOUNCEMENT = 8;
        private static final int FLAG_TRAFFIC_PROGRAM = 4;
        private static final int FLAG_TUNED = 16;
        private final int mInfoFlags;
        private final android.hardware.radio.ProgramSelector.Identifier mLogicallyTunedTo;
        private final android.hardware.radio.RadioMetadata mMetadata;
        private final android.hardware.radio.ProgramSelector.Identifier mPhysicallyTunedTo;
        private final java.util.Collection<android.hardware.radio.ProgramSelector.Identifier> mRelatedContent;
        private final android.hardware.radio.ProgramSelector mSelector;
        private final int mSignalQuality;
        private final java.util.Map<java.lang.String, java.lang.String> mVendorInfo;

        public ProgramInfo(android.hardware.radio.ProgramSelector programSelector, android.hardware.radio.ProgramSelector.Identifier identifier, android.hardware.radio.ProgramSelector.Identifier identifier2, java.util.Collection<android.hardware.radio.ProgramSelector.Identifier> collection, int i, int i2, android.hardware.radio.RadioMetadata radioMetadata, java.util.Map<java.lang.String, java.lang.String> map) {
            this.mSelector = (android.hardware.radio.ProgramSelector) java.util.Objects.requireNonNull(programSelector);
            this.mLogicallyTunedTo = identifier;
            this.mPhysicallyTunedTo = identifier2;
            if (collection == null) {
                this.mRelatedContent = java.util.Collections.emptyList();
            } else {
                com.android.internal.util.Preconditions.checkCollectionElementsNotNull(collection, "relatedContent");
                this.mRelatedContent = collection;
            }
            this.mInfoFlags = i;
            this.mSignalQuality = i2;
            this.mMetadata = radioMetadata;
            this.mVendorInfo = map == null ? new java.util.HashMap<>() : map;
        }

        public android.hardware.radio.ProgramSelector getSelector() {
            return this.mSelector;
        }

        public android.hardware.radio.ProgramSelector.Identifier getLogicallyTunedTo() {
            return this.mLogicallyTunedTo;
        }

        public android.hardware.radio.ProgramSelector.Identifier getPhysicallyTunedTo() {
            return this.mPhysicallyTunedTo;
        }

        public java.util.Collection<android.hardware.radio.ProgramSelector.Identifier> getRelatedContent() {
            return this.mRelatedContent;
        }

        @java.lang.Deprecated
        public int getChannel() {
            try {
                return (int) this.mSelector.getFirstId(1);
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Log.w(android.hardware.radio.RadioManager.TAG, "Not an AM/FM program");
                return 0;
            }
        }

        @java.lang.Deprecated
        public int getSubChannel() {
            try {
                return ((int) this.mSelector.getFirstId(4)) + 1;
            } catch (java.lang.IllegalArgumentException e) {
                return 0;
            }
        }

        public boolean isTuned() {
            return (this.mInfoFlags & 16) != 0;
        }

        public boolean isStereo() {
            return (this.mInfoFlags & 32) != 0;
        }

        @java.lang.Deprecated
        public boolean isDigital() {
            android.hardware.radio.ProgramSelector.Identifier identifier = this.mLogicallyTunedTo;
            if (identifier == null) {
                identifier = this.mSelector.getPrimaryId();
            }
            int type = identifier.getType();
            return (type == 1 || type == 2) ? false : true;
        }

        public boolean isLive() {
            return (this.mInfoFlags & 1) != 0;
        }

        public boolean isMuted() {
            return (this.mInfoFlags & 2) != 0;
        }

        public boolean isTrafficProgram() {
            return (this.mInfoFlags & 4) != 0;
        }

        public boolean isTrafficAnnouncementActive() {
            return (this.mInfoFlags & 8) != 0;
        }

        public boolean isSignalAcquired() {
            return (this.mInfoFlags & 64) != 0;
        }

        public boolean isHdSisAvailable() {
            return (this.mInfoFlags & 128) != 0;
        }

        public boolean isHdAudioAvailable() {
            return (this.mInfoFlags & 256) != 0;
        }

        public int getSignalStrength() {
            return this.mSignalQuality;
        }

        public android.hardware.radio.RadioMetadata getMetadata() {
            return this.mMetadata;
        }

        public java.util.Map<java.lang.String, java.lang.String> getVendorInfo() {
            return this.mVendorInfo;
        }

        private ProgramInfo(android.os.Parcel parcel) {
            this.mSelector = (android.hardware.radio.ProgramSelector) java.util.Objects.requireNonNull((android.hardware.radio.ProgramSelector) parcel.readTypedObject(android.hardware.radio.ProgramSelector.CREATOR));
            this.mLogicallyTunedTo = (android.hardware.radio.ProgramSelector.Identifier) parcel.readTypedObject(android.hardware.radio.ProgramSelector.Identifier.CREATOR);
            this.mPhysicallyTunedTo = (android.hardware.radio.ProgramSelector.Identifier) parcel.readTypedObject(android.hardware.radio.ProgramSelector.Identifier.CREATOR);
            this.mRelatedContent = parcel.createTypedArrayList(android.hardware.radio.ProgramSelector.Identifier.CREATOR);
            this.mInfoFlags = parcel.readInt();
            this.mSignalQuality = parcel.readInt();
            this.mMetadata = (android.hardware.radio.RadioMetadata) parcel.readTypedObject(android.hardware.radio.RadioMetadata.CREATOR);
            this.mVendorInfo = android.hardware.radio.Utils.readStringMap(parcel);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeTypedObject(this.mSelector, i);
            parcel.writeTypedObject(this.mLogicallyTunedTo, i);
            parcel.writeTypedObject(this.mPhysicallyTunedTo, i);
            android.hardware.radio.Utils.writeTypedCollection(parcel, this.mRelatedContent);
            parcel.writeInt(this.mInfoFlags);
            parcel.writeInt(this.mSignalQuality);
            parcel.writeTypedObject(this.mMetadata, i);
            android.hardware.radio.Utils.writeStringMap(parcel, this.mVendorInfo);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public java.lang.String toString() {
            return "ProgramInfo [selector=" + this.mSelector + ", logicallyTunedTo=" + java.util.Objects.toString(this.mLogicallyTunedTo) + ", physicallyTunedTo=" + java.util.Objects.toString(this.mPhysicallyTunedTo) + ", relatedContent=" + this.mRelatedContent.size() + ", infoFlags=" + this.mInfoFlags + ", mSignalQuality=" + this.mSignalQuality + ", mMetadata=" + java.util.Objects.toString(this.mMetadata) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mSelector, this.mLogicallyTunedTo, this.mPhysicallyTunedTo, this.mRelatedContent, java.lang.Integer.valueOf(this.mInfoFlags), java.lang.Integer.valueOf(this.mSignalQuality), this.mMetadata, this.mVendorInfo);
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof android.hardware.radio.RadioManager.ProgramInfo)) {
                return false;
            }
            android.hardware.radio.RadioManager.ProgramInfo programInfo = (android.hardware.radio.RadioManager.ProgramInfo) obj;
            return this.mSelector.strictEquals(programInfo.mSelector) && java.util.Objects.equals(this.mLogicallyTunedTo, programInfo.mLogicallyTunedTo) && java.util.Objects.equals(this.mPhysicallyTunedTo, programInfo.mPhysicallyTunedTo) && java.util.Objects.equals(this.mRelatedContent, programInfo.mRelatedContent) && this.mInfoFlags == programInfo.mInfoFlags && this.mSignalQuality == programInfo.mSignalQuality && java.util.Objects.equals(this.mMetadata, programInfo.mMetadata) && java.util.Objects.equals(this.mVendorInfo, programInfo.mVendorInfo);
        }
    }

    public int listModules(java.util.List<android.hardware.radio.RadioManager.ModuleProperties> list) {
        if (list == null) {
            android.util.Log.e(TAG, "the output list must not be empty");
            return -22;
        }
        android.util.Log.d(TAG, "Listing available tuners...");
        try {
            java.util.List<android.hardware.radio.RadioManager.ModuleProperties> listModules = this.mService.listModules();
            if (listModules == null) {
                android.util.Log.e(TAG, "Returned list was a null");
                return Integer.MIN_VALUE;
            }
            list.addAll(listModules);
            return 0;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed listing available tuners", e);
            return -32;
        }
    }

    public android.hardware.radio.RadioTuner openTuner(int i, android.hardware.radio.RadioManager.BandConfig bandConfig, boolean z, android.hardware.radio.RadioTuner.Callback callback, android.os.Handler handler) {
        if (callback == null) {
            throw new java.lang.IllegalArgumentException("callback must not be empty");
        }
        android.util.Log.d(TAG, "Opening tuner " + i + android.telecom.Logging.Session.TRUNCATE_STRING);
        android.hardware.radio.TunerCallbackAdapter tunerCallbackAdapter = new android.hardware.radio.TunerCallbackAdapter(callback, handler);
        try {
            android.hardware.radio.ITuner openTuner = this.mService.openTuner(i, bandConfig, z, tunerCallbackAdapter);
            if (openTuner == null) {
                android.util.Log.e(TAG, "Failed to open tuner");
                return null;
            }
            return new android.hardware.radio.TunerAdapter(openTuner, tunerCallbackAdapter, bandConfig != null ? bandConfig.getType() : -1);
        } catch (android.os.RemoteException | java.lang.IllegalArgumentException | java.lang.IllegalStateException e) {
            android.util.Log.e(TAG, "Failed to open tuner", e);
            return null;
        }
    }

    public void addAnnouncementListener(java.util.Set<java.lang.Integer> set, android.hardware.radio.Announcement.OnListUpdatedListener onListUpdatedListener) {
        addAnnouncementListener(new java.util.concurrent.Executor() { // from class: android.hardware.radio.RadioManager$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.Executor
            public final void execute(java.lang.Runnable runnable) {
                runnable.run();
            }
        }, set, onListUpdatedListener);
    }

    public void addAnnouncementListener(java.util.concurrent.Executor executor, java.util.Set<java.lang.Integer> set, android.hardware.radio.Announcement.OnListUpdatedListener onListUpdatedListener) {
        android.hardware.radio.ICloseHandle iCloseHandle;
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(onListUpdatedListener);
        int[] array = set.stream().mapToInt(new android.app.admin.PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray();
        android.hardware.radio.RadioManager.AnonymousClass1 anonymousClass1 = new android.hardware.radio.RadioManager.AnonymousClass1(executor, onListUpdatedListener);
        synchronized (this.mAnnouncementListeners) {
            try {
                iCloseHandle = this.mService.addAnnouncementListener(array, anonymousClass1);
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
                iCloseHandle = null;
            }
            java.util.Objects.requireNonNull(iCloseHandle);
            android.hardware.radio.ICloseHandle put = this.mAnnouncementListeners.put(onListUpdatedListener, iCloseHandle);
            if (put != null) {
                android.hardware.radio.Utils.close(put);
            }
        }
    }

    /* renamed from: android.hardware.radio.RadioManager$1, reason: invalid class name */
    class AnonymousClass1 extends android.hardware.radio.IAnnouncementListener.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ android.hardware.radio.Announcement.OnListUpdatedListener val$listener;

        AnonymousClass1(java.util.concurrent.Executor executor, android.hardware.radio.Announcement.OnListUpdatedListener onListUpdatedListener) {
            this.val$executor = executor;
            this.val$listener = onListUpdatedListener;
        }

        @Override // android.hardware.radio.IAnnouncementListener
        public void onListUpdated(final java.util.List<android.hardware.radio.Announcement> list) {
            java.util.concurrent.Executor executor = this.val$executor;
            final android.hardware.radio.Announcement.OnListUpdatedListener onListUpdatedListener = this.val$listener;
            executor.execute(new java.lang.Runnable() { // from class: android.hardware.radio.RadioManager$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.hardware.radio.Announcement.OnListUpdatedListener.this.onListUpdated(list);
                }
            });
        }
    }

    public void removeAnnouncementListener(android.hardware.radio.Announcement.OnListUpdatedListener onListUpdatedListener) {
        java.util.Objects.requireNonNull(onListUpdatedListener);
        synchronized (this.mAnnouncementListeners) {
            android.hardware.radio.ICloseHandle remove = this.mAnnouncementListeners.remove(onListUpdatedListener);
            if (remove != null) {
                android.hardware.radio.Utils.close(remove);
            }
        }
    }

    public RadioManager(android.content.Context context) throws android.os.ServiceManager.ServiceNotFoundException {
        this(context, android.hardware.radio.IRadioService.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.RADIO_SERVICE)));
    }

    public RadioManager(android.content.Context context, android.hardware.radio.IRadioService iRadioService) {
        this.mAnnouncementListeners = new java.util.HashMap();
        this.mContext = context;
        this.mService = iRadioService;
    }
}
