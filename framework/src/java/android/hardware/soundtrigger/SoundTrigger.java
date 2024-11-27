package android.hardware.soundtrigger;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class SoundTrigger {
    public static final java.lang.String FAKE_HAL_ARCH = "injection";
    public static final int MODEL_PARAM_INVALID = -1;
    public static final int MODEL_PARAM_THRESHOLD_FACTOR = 0;
    public static final int RECOGNITION_MODE_GENERIC = 8;
    public static final int RECOGNITION_MODE_USER_AUTHENTICATION = 4;
    public static final int RECOGNITION_MODE_USER_IDENTIFICATION = 2;
    public static final int RECOGNITION_MODE_VOICE_TRIGGER = 1;
    public static final int RECOGNITION_STATUS_ABORT = 1;
    public static final int RECOGNITION_STATUS_FAILURE = 2;
    public static final int RECOGNITION_STATUS_GET_STATE_RESPONSE = 3;
    public static final int RECOGNITION_STATUS_SUCCESS = 0;
    public static final int STATUS_ERROR = Integer.MIN_VALUE;
    public static final int STATUS_OK = 0;
    private static final java.lang.String TAG = "SoundTrigger";
    public static final int STATUS_PERMISSION_DENIED = -android.system.OsConstants.EPERM;
    public static final int STATUS_NO_INIT = -android.system.OsConstants.ENODEV;
    public static final int STATUS_BAD_VALUE = -android.system.OsConstants.EINVAL;
    public static final int STATUS_DEAD_OBJECT = -android.system.OsConstants.EPIPE;
    public static final int STATUS_INVALID_OPERATION = -android.system.OsConstants.ENOSYS;
    public static final int STATUS_BUSY = -android.system.OsConstants.EBUSY;
    private static java.lang.Object mServiceLock = new java.lang.Object();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ModelParamTypes {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RecognitionModes {
    }

    public interface StatusListener {
        void onModelUnloaded(int i);

        void onRecognition(android.hardware.soundtrigger.SoundTrigger.RecognitionEvent recognitionEvent);

        void onResourcesAvailable();

        void onServiceDied();
    }

    private SoundTrigger() {
    }

    public static final class ModuleProperties implements android.os.Parcelable {
        public static final int AUDIO_CAPABILITY_ECHO_CANCELLATION = 1;
        public static final int AUDIO_CAPABILITY_NOISE_SUPPRESSION = 2;
        public static final android.os.Parcelable.Creator<android.hardware.soundtrigger.SoundTrigger.ModuleProperties> CREATOR = new android.os.Parcelable.Creator<android.hardware.soundtrigger.SoundTrigger.ModuleProperties>() { // from class: android.hardware.soundtrigger.SoundTrigger.ModuleProperties.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.soundtrigger.SoundTrigger.ModuleProperties createFromParcel(android.os.Parcel parcel) {
                return android.hardware.soundtrigger.SoundTrigger.ModuleProperties.fromParcel(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.soundtrigger.SoundTrigger.ModuleProperties[] newArray(int i) {
                return new android.hardware.soundtrigger.SoundTrigger.ModuleProperties[i];
            }
        };
        private final int mAudioCapabilities;
        private final java.lang.String mDescription;
        private final int mId;
        private final java.lang.String mImplementor;
        private final int mMaxBufferMillis;
        private final int mMaxKeyphrases;
        private final int mMaxSoundModels;
        private final int mMaxUsers;
        private final int mPowerConsumptionMw;
        private final int mRecognitionModes;
        private final boolean mReturnsTriggerInEvent;
        private final java.lang.String mSupportedModelArch;
        private final boolean mSupportsCaptureTransition;
        private final boolean mSupportsConcurrentCapture;
        private final java.util.UUID mUuid;
        private final int mVersion;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface AudioCapabilities {
        }

        public ModuleProperties(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, int i2, java.lang.String str4, int i3, int i4, int i5, int i6, boolean z, int i7, boolean z2, int i8, boolean z3, int i9) {
            this.mId = i;
            this.mImplementor = (java.lang.String) java.util.Objects.requireNonNull(str);
            this.mDescription = (java.lang.String) java.util.Objects.requireNonNull(str2);
            this.mUuid = java.util.UUID.fromString((java.lang.String) java.util.Objects.requireNonNull(str3));
            this.mVersion = i2;
            this.mSupportedModelArch = (java.lang.String) java.util.Objects.requireNonNull(str4);
            this.mMaxSoundModels = i3;
            this.mMaxKeyphrases = i4;
            this.mMaxUsers = i5;
            this.mRecognitionModes = i6;
            this.mSupportsCaptureTransition = z;
            this.mMaxBufferMillis = i7;
            this.mSupportsConcurrentCapture = z2;
            this.mPowerConsumptionMw = i8;
            this.mReturnsTriggerInEvent = z3;
            this.mAudioCapabilities = i9;
        }

        public int getId() {
            return this.mId;
        }

        public java.lang.String getImplementor() {
            return this.mImplementor;
        }

        public java.lang.String getDescription() {
            return this.mDescription;
        }

        public java.util.UUID getUuid() {
            return this.mUuid;
        }

        public int getVersion() {
            return this.mVersion;
        }

        public java.lang.String getSupportedModelArch() {
            return this.mSupportedModelArch;
        }

        public int getMaxSoundModels() {
            return this.mMaxSoundModels;
        }

        public int getMaxKeyphrases() {
            return this.mMaxKeyphrases;
        }

        public int getMaxUsers() {
            return this.mMaxUsers;
        }

        public int getRecognitionModes() {
            return this.mRecognitionModes;
        }

        public boolean isCaptureTransitionSupported() {
            return this.mSupportsCaptureTransition;
        }

        public int getMaxBufferMillis() {
            return this.mMaxBufferMillis;
        }

        public boolean isConcurrentCaptureSupported() {
            return this.mSupportsConcurrentCapture;
        }

        public int getPowerConsumptionMw() {
            return this.mPowerConsumptionMw;
        }

        public boolean isTriggerReturnedInEvent() {
            return this.mReturnsTriggerInEvent;
        }

        public int getAudioCapabilities() {
            return this.mAudioCapabilities;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static android.hardware.soundtrigger.SoundTrigger.ModuleProperties fromParcel(android.os.Parcel parcel) {
            return new android.hardware.soundtrigger.SoundTrigger.ModuleProperties(parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readByte() == 1, parcel.readInt(), parcel.readByte() == 1, parcel.readInt(), parcel.readByte() == 1, parcel.readInt());
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(getId());
            parcel.writeString(getImplementor());
            parcel.writeString(getDescription());
            parcel.writeString(getUuid().toString());
            parcel.writeInt(getVersion());
            parcel.writeString(getSupportedModelArch());
            parcel.writeInt(getMaxSoundModels());
            parcel.writeInt(getMaxKeyphrases());
            parcel.writeInt(getMaxUsers());
            parcel.writeInt(getRecognitionModes());
            parcel.writeByte(isCaptureTransitionSupported() ? (byte) 1 : (byte) 0);
            parcel.writeInt(getMaxBufferMillis());
            parcel.writeByte(isConcurrentCaptureSupported() ? (byte) 1 : (byte) 0);
            parcel.writeInt(getPowerConsumptionMw());
            parcel.writeByte(isTriggerReturnedInEvent() ? (byte) 1 : (byte) 0);
            parcel.writeInt(getAudioCapabilities());
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !(obj instanceof android.hardware.soundtrigger.SoundTrigger.ModuleProperties)) {
                return false;
            }
            android.hardware.soundtrigger.SoundTrigger.ModuleProperties moduleProperties = (android.hardware.soundtrigger.SoundTrigger.ModuleProperties) obj;
            if (this.mId == moduleProperties.mId && this.mImplementor.equals(moduleProperties.mImplementor) && this.mDescription.equals(moduleProperties.mDescription) && this.mUuid.equals(moduleProperties.mUuid) && this.mVersion == moduleProperties.mVersion && this.mSupportedModelArch.equals(moduleProperties.mSupportedModelArch) && this.mMaxSoundModels == moduleProperties.mMaxSoundModels && this.mMaxKeyphrases == moduleProperties.mMaxKeyphrases && this.mMaxUsers == moduleProperties.mMaxUsers && this.mRecognitionModes == moduleProperties.mRecognitionModes && this.mSupportsCaptureTransition == moduleProperties.mSupportsCaptureTransition && this.mMaxBufferMillis == moduleProperties.mMaxBufferMillis && this.mSupportsConcurrentCapture == moduleProperties.mSupportsConcurrentCapture && this.mPowerConsumptionMw == moduleProperties.mPowerConsumptionMw && this.mReturnsTriggerInEvent == moduleProperties.mReturnsTriggerInEvent && this.mAudioCapabilities == moduleProperties.mAudioCapabilities) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return ((((((((((((((((((((((((((((((this.mId + 31) * 31) + this.mImplementor.hashCode()) * 31) + this.mDescription.hashCode()) * 31) + this.mUuid.hashCode()) * 31) + this.mVersion) * 31) + this.mSupportedModelArch.hashCode()) * 31) + this.mMaxSoundModels) * 31) + this.mMaxKeyphrases) * 31) + this.mMaxUsers) * 31) + this.mRecognitionModes) * 31) + (this.mSupportsCaptureTransition ? 1 : 0)) * 31) + this.mMaxBufferMillis) * 31) + (this.mSupportsConcurrentCapture ? 1 : 0)) * 31) + this.mPowerConsumptionMw) * 31) + (this.mReturnsTriggerInEvent ? 1 : 0)) * 31) + this.mAudioCapabilities;
        }

        public java.lang.String toString() {
            return "ModuleProperties [id=" + getId() + ", implementor=" + getImplementor() + ", description=" + getDescription() + ", uuid=" + getUuid() + ", version=" + getVersion() + " , supportedModelArch=" + getSupportedModelArch() + ", maxSoundModels=" + getMaxSoundModels() + ", maxKeyphrases=" + getMaxKeyphrases() + ", maxUsers=" + getMaxUsers() + ", recognitionModes=" + getRecognitionModes() + ", supportsCaptureTransition=" + isCaptureTransitionSupported() + ", maxBufferMs=" + getMaxBufferMillis() + ", supportsConcurrentCapture=" + isConcurrentCaptureSupported() + ", powerConsumptionMw=" + getPowerConsumptionMw() + ", returnsTriggerInEvent=" + isTriggerReturnedInEvent() + ", audioCapabilities=" + getAudioCapabilities() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }
    }

    public static class SoundModel {
        public static final int TYPE_GENERIC_SOUND = 1;
        public static final int TYPE_KEYPHRASE = 0;
        public static final int TYPE_UNKNOWN = -1;
        private final byte[] mData;
        private final int mType;
        private final java.util.UUID mUuid;
        private final java.util.UUID mVendorUuid;
        private final int mVersion;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface SoundModelType {
        }

        public SoundModel(java.util.UUID uuid, java.util.UUID uuid2, int i, byte[] bArr, int i2) {
            this.mUuid = (java.util.UUID) java.util.Objects.requireNonNull(uuid);
            this.mVendorUuid = uuid2 == null ? new java.util.UUID(0L, 0L) : uuid2;
            this.mType = i;
            this.mVersion = i2;
            this.mData = bArr == null ? new byte[0] : bArr;
        }

        public java.util.UUID getUuid() {
            return this.mUuid;
        }

        public int getType() {
            return this.mType;
        }

        public java.util.UUID getVendorUuid() {
            return this.mVendorUuid;
        }

        public int getVersion() {
            return this.mVersion;
        }

        public byte[] getData() {
            return this.mData;
        }

        public int hashCode() {
            return ((((((((getVersion() + 31) * 31) + java.util.Arrays.hashCode(getData())) * 31) + getType()) * 31) + (getUuid() == null ? 0 : getUuid().hashCode())) * 31) + (getVendorUuid() != null ? getVendorUuid().hashCode() : 0);
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !(obj instanceof android.hardware.soundtrigger.SoundTrigger.SoundModel)) {
                return false;
            }
            android.hardware.soundtrigger.SoundTrigger.SoundModel soundModel = (android.hardware.soundtrigger.SoundTrigger.SoundModel) obj;
            if (getType() != soundModel.getType()) {
                return false;
            }
            if (getUuid() == null) {
                if (soundModel.getUuid() != null) {
                    return false;
                }
            } else if (!getUuid().equals(soundModel.getUuid())) {
                return false;
            }
            if (getVendorUuid() == null) {
                if (soundModel.getVendorUuid() != null) {
                    return false;
                }
            } else if (!getVendorUuid().equals(soundModel.getVendorUuid())) {
                return false;
            }
            if (java.util.Arrays.equals(getData(), soundModel.getData()) && getVersion() == soundModel.getVersion()) {
                return true;
            }
            return false;
        }
    }

    public static final class Keyphrase implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.soundtrigger.SoundTrigger.Keyphrase> CREATOR = new android.os.Parcelable.Creator<android.hardware.soundtrigger.SoundTrigger.Keyphrase>() { // from class: android.hardware.soundtrigger.SoundTrigger.Keyphrase.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.soundtrigger.SoundTrigger.Keyphrase createFromParcel(android.os.Parcel parcel) {
                return android.hardware.soundtrigger.SoundTrigger.Keyphrase.readFromParcel(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.soundtrigger.SoundTrigger.Keyphrase[] newArray(int i) {
                return new android.hardware.soundtrigger.SoundTrigger.Keyphrase[i];
            }
        };
        private final int mId;
        private final java.util.Locale mLocale;
        private final int mRecognitionModes;
        private final java.lang.String mText;
        private final int[] mUsers;

        public Keyphrase(int i, int i2, java.util.Locale locale, java.lang.String str, int[] iArr) {
            this.mId = i;
            this.mRecognitionModes = i2;
            this.mLocale = (java.util.Locale) java.util.Objects.requireNonNull(locale);
            this.mText = (java.lang.String) java.util.Objects.requireNonNull(str);
            this.mUsers = iArr == null ? new int[0] : iArr;
        }

        public int getId() {
            return this.mId;
        }

        public int getRecognitionModes() {
            return this.mRecognitionModes;
        }

        public java.util.Locale getLocale() {
            return this.mLocale;
        }

        public java.lang.String getText() {
            return this.mText;
        }

        public int[] getUsers() {
            return this.mUsers;
        }

        public static android.hardware.soundtrigger.SoundTrigger.Keyphrase readFromParcel(android.os.Parcel parcel) {
            int[] iArr;
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            java.util.Locale forLanguageTag = java.util.Locale.forLanguageTag(parcel.readString());
            java.lang.String readString = parcel.readString();
            int readInt3 = parcel.readInt();
            if (readInt3 < 0) {
                iArr = null;
            } else {
                int[] iArr2 = new int[readInt3];
                parcel.readIntArray(iArr2);
                iArr = iArr2;
            }
            return new android.hardware.soundtrigger.SoundTrigger.Keyphrase(readInt, readInt2, forLanguageTag, readString, iArr);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(getId());
            parcel.writeInt(getRecognitionModes());
            parcel.writeString(getLocale().toLanguageTag());
            parcel.writeString(getText());
            if (getUsers() != null) {
                parcel.writeInt(getUsers().length);
                parcel.writeIntArray(getUsers());
            } else {
                parcel.writeInt(-1);
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public int hashCode() {
            return (((((((((getText() == null ? 0 : getText().hashCode()) + 31) * 31) + getId()) * 31) + (getLocale() != null ? getLocale().hashCode() : 0)) * 31) + getRecognitionModes()) * 31) + java.util.Arrays.hashCode(getUsers());
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.hardware.soundtrigger.SoundTrigger.Keyphrase keyphrase = (android.hardware.soundtrigger.SoundTrigger.Keyphrase) obj;
            if (getText() == null) {
                if (keyphrase.getText() != null) {
                    return false;
                }
            } else if (!getText().equals(keyphrase.getText())) {
                return false;
            }
            if (getId() != keyphrase.getId()) {
                return false;
            }
            if (getLocale() == null) {
                if (keyphrase.getLocale() != null) {
                    return false;
                }
            } else if (!getLocale().equals(keyphrase.getLocale())) {
                return false;
            }
            if (getRecognitionModes() == keyphrase.getRecognitionModes() && java.util.Arrays.equals(getUsers(), keyphrase.getUsers())) {
                return true;
            }
            return false;
        }

        public java.lang.String toString() {
            return "Keyphrase [id=" + getId() + ", recognitionModes=" + getRecognitionModes() + ", locale=" + getLocale().toLanguageTag() + ", text=" + getText() + ", users=" + java.util.Arrays.toString(getUsers()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }
    }

    public static final class KeyphraseSoundModel extends android.hardware.soundtrigger.SoundTrigger.SoundModel implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel> CREATOR = new android.os.Parcelable.Creator<android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel>() { // from class: android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel createFromParcel(android.os.Parcel parcel) {
                return android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel.readFromParcel(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel[] newArray(int i) {
                return new android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel[i];
            }
        };
        private final android.hardware.soundtrigger.SoundTrigger.Keyphrase[] mKeyphrases;

        public KeyphraseSoundModel(java.util.UUID uuid, java.util.UUID uuid2, byte[] bArr, android.hardware.soundtrigger.SoundTrigger.Keyphrase[] keyphraseArr, int i) {
            super(uuid, uuid2, 0, bArr, i);
            this.mKeyphrases = keyphraseArr == null ? new android.hardware.soundtrigger.SoundTrigger.Keyphrase[0] : keyphraseArr;
        }

        public KeyphraseSoundModel(java.util.UUID uuid, java.util.UUID uuid2, byte[] bArr, android.hardware.soundtrigger.SoundTrigger.Keyphrase[] keyphraseArr) {
            this(uuid, uuid2, bArr, keyphraseArr, -1);
        }

        public android.hardware.soundtrigger.SoundTrigger.Keyphrase[] getKeyphrases() {
            return this.mKeyphrases;
        }

        public static android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel readFromParcel(android.os.Parcel parcel) {
            java.util.UUID uuid;
            java.util.UUID fromString = java.util.UUID.fromString(parcel.readString());
            if (parcel.readInt() < 0) {
                uuid = null;
            } else {
                uuid = java.util.UUID.fromString(parcel.readString());
            }
            return new android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel(fromString, uuid, parcel.readBlob(), (android.hardware.soundtrigger.SoundTrigger.Keyphrase[]) parcel.createTypedArray(android.hardware.soundtrigger.SoundTrigger.Keyphrase.CREATOR), parcel.readInt());
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeString(getUuid().toString());
            if (getVendorUuid() == null) {
                parcel.writeInt(-1);
            } else {
                parcel.writeInt(getVendorUuid().toString().length());
                parcel.writeString(getVendorUuid().toString());
            }
            parcel.writeInt(getVersion());
            parcel.writeBlob(getData());
            parcel.writeTypedArray(getKeyphrases(), i);
        }

        public java.lang.String toString() {
            return "KeyphraseSoundModel [keyphrases=" + java.util.Arrays.toString(getKeyphrases()) + ", uuid=" + getUuid() + ", vendorUuid=" + getVendorUuid() + ", type=" + getType() + ", data=" + (getData() == null ? 0 : getData().length) + ", version=" + getVersion() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }

        @Override // android.hardware.soundtrigger.SoundTrigger.SoundModel
        public int hashCode() {
            return (super.hashCode() * 31) + java.util.Arrays.hashCode(getKeyphrases());
        }

        @Override // android.hardware.soundtrigger.SoundTrigger.SoundModel
        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            return super.equals(obj) && (obj instanceof android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel) && java.util.Arrays.equals(getKeyphrases(), ((android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel) obj).getKeyphrases());
        }
    }

    public static class GenericSoundModel extends android.hardware.soundtrigger.SoundTrigger.SoundModel implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.soundtrigger.SoundTrigger.GenericSoundModel> CREATOR = new android.os.Parcelable.Creator<android.hardware.soundtrigger.SoundTrigger.GenericSoundModel>() { // from class: android.hardware.soundtrigger.SoundTrigger.GenericSoundModel.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.soundtrigger.SoundTrigger.GenericSoundModel createFromParcel(android.os.Parcel parcel) {
                return android.hardware.soundtrigger.SoundTrigger.GenericSoundModel.fromParcel(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.soundtrigger.SoundTrigger.GenericSoundModel[] newArray(int i) {
                return new android.hardware.soundtrigger.SoundTrigger.GenericSoundModel[i];
            }
        };

        public GenericSoundModel(java.util.UUID uuid, java.util.UUID uuid2, byte[] bArr, int i) {
            super(uuid, uuid2, 1, bArr, i);
        }

        public GenericSoundModel(java.util.UUID uuid, java.util.UUID uuid2, byte[] bArr) {
            this(uuid, uuid2, bArr, -1);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static android.hardware.soundtrigger.SoundTrigger.GenericSoundModel fromParcel(android.os.Parcel parcel) {
            java.util.UUID uuid;
            java.util.UUID fromString = java.util.UUID.fromString(parcel.readString());
            if (parcel.readInt() < 0) {
                uuid = null;
            } else {
                uuid = java.util.UUID.fromString(parcel.readString());
            }
            return new android.hardware.soundtrigger.SoundTrigger.GenericSoundModel(fromString, uuid, parcel.readBlob(), parcel.readInt());
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeString(getUuid().toString());
            if (getVendorUuid() == null) {
                parcel.writeInt(-1);
            } else {
                parcel.writeInt(getVendorUuid().toString().length());
                parcel.writeString(getVendorUuid().toString());
            }
            parcel.writeBlob(getData());
            parcel.writeInt(getVersion());
        }

        public java.lang.String toString() {
            return "GenericSoundModel [uuid=" + getUuid() + ", vendorUuid=" + getVendorUuid() + ", type=" + getType() + ", data=" + (getData() == null ? 0 : getData().length) + ", version=" + getVersion() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }
    }

    public static final class ModelParamRange implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.soundtrigger.SoundTrigger.ModelParamRange> CREATOR = new android.os.Parcelable.Creator<android.hardware.soundtrigger.SoundTrigger.ModelParamRange>() { // from class: android.hardware.soundtrigger.SoundTrigger.ModelParamRange.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.soundtrigger.SoundTrigger.ModelParamRange createFromParcel(android.os.Parcel parcel) {
                return new android.hardware.soundtrigger.SoundTrigger.ModelParamRange(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.soundtrigger.SoundTrigger.ModelParamRange[] newArray(int i) {
                return new android.hardware.soundtrigger.SoundTrigger.ModelParamRange[i];
            }
        };
        private final int mEnd;
        private final int mStart;

        public ModelParamRange(int i, int i2) {
            this.mStart = i;
            this.mEnd = i2;
        }

        private ModelParamRange(android.os.Parcel parcel) {
            this.mStart = parcel.readInt();
            this.mEnd = parcel.readInt();
        }

        public int getStart() {
            return this.mStart;
        }

        public int getEnd() {
            return this.mEnd;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public int hashCode() {
            return ((this.mStart + 31) * 31) + this.mEnd;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.hardware.soundtrigger.SoundTrigger.ModelParamRange modelParamRange = (android.hardware.soundtrigger.SoundTrigger.ModelParamRange) obj;
            if (this.mStart == modelParamRange.mStart && this.mEnd == modelParamRange.mEnd) {
                return true;
            }
            return false;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mStart);
            parcel.writeInt(this.mEnd);
        }

        public java.lang.String toString() {
            return "ModelParamRange [start=" + this.mStart + ", end=" + this.mEnd + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }
    }

    public static class RecognitionEvent {
        public static final android.os.Parcelable.Creator<android.hardware.soundtrigger.SoundTrigger.RecognitionEvent> CREATOR = new android.os.Parcelable.Creator<android.hardware.soundtrigger.SoundTrigger.RecognitionEvent>() { // from class: android.hardware.soundtrigger.SoundTrigger.RecognitionEvent.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.soundtrigger.SoundTrigger.RecognitionEvent createFromParcel(android.os.Parcel parcel) {
                return android.hardware.soundtrigger.SoundTrigger.RecognitionEvent.fromParcel(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.soundtrigger.SoundTrigger.RecognitionEvent[] newArray(int i) {
                return new android.hardware.soundtrigger.SoundTrigger.RecognitionEvent[i];
            }
        };
        public final boolean captureAvailable;
        public final int captureDelayMs;
        public final android.media.AudioFormat captureFormat;
        public final int capturePreambleMs;
        public final int captureSession;
        public final byte[] data;
        public final long halEventReceivedMillis;
        public final boolean recognitionStillActive;
        public final int soundModelHandle;
        public final int status;
        public final android.os.IBinder token;
        public final boolean triggerInData;

        public RecognitionEvent(int i, int i2, boolean z, int i3, int i4, int i5, boolean z2, android.media.AudioFormat audioFormat, byte[] bArr, long j) {
            this(i, i2, z, i3, i4, i5, z2, audioFormat, bArr, i == 3, j, null);
        }

        public RecognitionEvent(int i, int i2, boolean z, int i3, int i4, int i5, boolean z2, android.media.AudioFormat audioFormat, byte[] bArr, boolean z3, long j, android.os.IBinder iBinder) {
            this.status = i;
            this.soundModelHandle = i2;
            this.captureAvailable = z;
            this.captureSession = i3;
            this.captureDelayMs = i4;
            this.capturePreambleMs = i5;
            this.triggerInData = z2;
            this.captureFormat = (android.media.AudioFormat) java.util.Objects.requireNonNull(audioFormat);
            this.data = bArr == null ? new byte[0] : bArr;
            this.recognitionStillActive = z3;
            this.halEventReceivedMillis = j;
            this.token = iBinder;
        }

        public boolean isCaptureAvailable() {
            return this.captureAvailable;
        }

        public android.media.AudioFormat getCaptureFormat() {
            return this.captureFormat;
        }

        public int getCaptureSession() {
            return this.captureSession;
        }

        public byte[] getData() {
            return this.data;
        }

        public long getHalEventReceivedMillis() {
            return this.halEventReceivedMillis;
        }

        public android.os.IBinder getToken() {
            return this.token;
        }

        protected static android.hardware.soundtrigger.SoundTrigger.RecognitionEvent fromParcel(android.os.Parcel parcel) {
            android.media.AudioFormat audioFormat;
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            boolean z = parcel.readByte() == 1;
            int readInt3 = parcel.readInt();
            int readInt4 = parcel.readInt();
            int readInt5 = parcel.readInt();
            boolean z2 = parcel.readByte() == 1;
            if (parcel.readByte() != 1) {
                audioFormat = null;
            } else {
                audioFormat = new android.media.AudioFormat.Builder().setChannelMask(parcel.readInt()).setEncoding(parcel.readInt()).setSampleRate(parcel.readInt()).build();
            }
            return new android.hardware.soundtrigger.SoundTrigger.RecognitionEvent(readInt, readInt2, z, readInt3, readInt4, readInt5, z2, audioFormat, parcel.readBlob(), parcel.readBoolean(), parcel.readLong(), parcel.readStrongBinder());
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.status);
            parcel.writeInt(this.soundModelHandle);
            parcel.writeByte(this.captureAvailable ? (byte) 1 : (byte) 0);
            parcel.writeInt(this.captureSession);
            parcel.writeInt(this.captureDelayMs);
            parcel.writeInt(this.capturePreambleMs);
            parcel.writeByte(this.triggerInData ? (byte) 1 : (byte) 0);
            if (this.captureFormat != null) {
                parcel.writeByte((byte) 1);
                parcel.writeInt(this.captureFormat.getSampleRate());
                parcel.writeInt(this.captureFormat.getEncoding());
                parcel.writeInt(this.captureFormat.getChannelMask());
            } else {
                parcel.writeByte((byte) 0);
            }
            parcel.writeBlob(this.data);
            parcel.writeBoolean(this.recognitionStillActive);
            parcel.writeLong(this.halEventReceivedMillis);
            parcel.writeStrongBinder(this.token);
        }

        public int hashCode() {
            boolean z = this.captureAvailable;
            int i = com.android.internal.logging.nano.MetricsProto.MetricsEvent.AUTOFILL_SERVICE_DISABLED_APP;
            int i2 = ((((((((z ? 1231 : 1237) + 31) * 31) + this.captureDelayMs) * 31) + this.capturePreambleMs) * 31) + this.captureSession) * 31;
            if (!this.triggerInData) {
                i = 1237;
            }
            int i3 = i2 + i;
            if (this.captureFormat != null) {
                i3 = (((((i3 * 31) + this.captureFormat.getSampleRate()) * 31) + this.captureFormat.getEncoding()) * 31) + this.captureFormat.getChannelMask();
            }
            return (((((((((i3 * 31) + java.util.Arrays.hashCode(this.data)) * 31) + this.soundModelHandle) * 31) + this.status + (this.recognitionStillActive ? com.android.internal.logging.nano.MetricsProto.MetricsEvent.AUTOFILL_INVALID_PERMISSION : 1291)) * 31) + java.lang.Long.hashCode(this.halEventReceivedMillis)) * 31) + java.util.Objects.hashCode(this.token);
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.hardware.soundtrigger.SoundTrigger.RecognitionEvent recognitionEvent = (android.hardware.soundtrigger.SoundTrigger.RecognitionEvent) obj;
            if (this.captureAvailable != recognitionEvent.captureAvailable || this.captureDelayMs != recognitionEvent.captureDelayMs || this.capturePreambleMs != recognitionEvent.capturePreambleMs || this.captureSession != recognitionEvent.captureSession || !java.util.Arrays.equals(this.data, recognitionEvent.data) || this.recognitionStillActive != recognitionEvent.recognitionStillActive || this.soundModelHandle != recognitionEvent.soundModelHandle || this.halEventReceivedMillis != recognitionEvent.halEventReceivedMillis || !java.util.Objects.equals(this.token, recognitionEvent.token) || this.status != recognitionEvent.status || this.triggerInData != recognitionEvent.triggerInData) {
                return false;
            }
            if (this.captureFormat == null) {
                if (recognitionEvent.captureFormat != null) {
                    return false;
                }
            } else if (recognitionEvent.captureFormat == null || this.captureFormat.getSampleRate() != recognitionEvent.captureFormat.getSampleRate() || this.captureFormat.getEncoding() != recognitionEvent.captureFormat.getEncoding() || this.captureFormat.getChannelMask() != recognitionEvent.captureFormat.getChannelMask()) {
                return false;
            }
            return true;
        }

        public java.lang.String toString() {
            return "RecognitionEvent [status=" + this.status + ", soundModelHandle=" + this.soundModelHandle + ", captureAvailable=" + this.captureAvailable + ", captureSession=" + this.captureSession + ", captureDelayMs=" + this.captureDelayMs + ", capturePreambleMs=" + this.capturePreambleMs + ", triggerInData=" + this.triggerInData + (this.captureFormat == null ? "" : ", sampleRate=" + this.captureFormat.getSampleRate()) + (this.captureFormat == null ? "" : ", encoding=" + this.captureFormat.getEncoding()) + (this.captureFormat != null ? ", channelMask=" + this.captureFormat.getChannelMask() : "") + ", data=" + (this.data == null ? 0 : this.data.length) + ", recognitionStillActive=" + this.recognitionStillActive + ", halEventReceivedMillis=" + this.halEventReceivedMillis + ", token=" + this.token + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }
    }

    public static final class RecognitionConfig implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.soundtrigger.SoundTrigger.RecognitionConfig> CREATOR = new android.os.Parcelable.Creator<android.hardware.soundtrigger.SoundTrigger.RecognitionConfig>() { // from class: android.hardware.soundtrigger.SoundTrigger.RecognitionConfig.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.soundtrigger.SoundTrigger.RecognitionConfig createFromParcel(android.os.Parcel parcel) {
                return android.hardware.soundtrigger.SoundTrigger.RecognitionConfig.fromParcel(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.soundtrigger.SoundTrigger.RecognitionConfig[] newArray(int i) {
                return new android.hardware.soundtrigger.SoundTrigger.RecognitionConfig[i];
            }
        };
        public final boolean allowMultipleTriggers;
        public final int audioCapabilities;
        public final boolean captureRequested;
        public final byte[] data;
        public final android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra[] keyphrases;

        public RecognitionConfig(boolean z, boolean z2, android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra[] keyphraseRecognitionExtraArr, byte[] bArr, int i) {
            this.captureRequested = z;
            this.allowMultipleTriggers = z2;
            this.keyphrases = keyphraseRecognitionExtraArr == null ? new android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra[0] : keyphraseRecognitionExtraArr;
            this.data = bArr == null ? new byte[0] : bArr;
            this.audioCapabilities = i;
        }

        public RecognitionConfig(boolean z, boolean z2, android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra[] keyphraseRecognitionExtraArr, byte[] bArr) {
            this(z, z2, keyphraseRecognitionExtraArr, bArr, 0);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static android.hardware.soundtrigger.SoundTrigger.RecognitionConfig fromParcel(android.os.Parcel parcel) {
            return new android.hardware.soundtrigger.SoundTrigger.RecognitionConfig(parcel.readByte() == 1, parcel.readByte() == 1, (android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra[]) parcel.createTypedArray(android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra.CREATOR), parcel.readBlob(), parcel.readInt());
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeByte(this.captureRequested ? (byte) 1 : (byte) 0);
            parcel.writeByte(this.allowMultipleTriggers ? (byte) 1 : (byte) 0);
            parcel.writeTypedArray(this.keyphrases, i);
            parcel.writeBlob(this.data);
            parcel.writeInt(this.audioCapabilities);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public java.lang.String toString() {
            return "RecognitionConfig [captureRequested=" + this.captureRequested + ", allowMultipleTriggers=" + this.allowMultipleTriggers + ", keyphrases=" + java.util.Arrays.toString(this.keyphrases) + ", data=" + java.util.Arrays.toString(this.data) + ", audioCapabilities=" + java.lang.Integer.toHexString(this.audioCapabilities) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }

        public final boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !(obj instanceof android.hardware.soundtrigger.SoundTrigger.RecognitionConfig)) {
                return false;
            }
            android.hardware.soundtrigger.SoundTrigger.RecognitionConfig recognitionConfig = (android.hardware.soundtrigger.SoundTrigger.RecognitionConfig) obj;
            if (this.captureRequested == recognitionConfig.captureRequested && this.allowMultipleTriggers == recognitionConfig.allowMultipleTriggers && java.util.Arrays.equals(this.keyphrases, recognitionConfig.keyphrases) && java.util.Arrays.equals(this.data, recognitionConfig.data) && this.audioCapabilities == recognitionConfig.audioCapabilities) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return (((((((((this.captureRequested ? 1 : 0) + 31) * 31) + (this.allowMultipleTriggers ? 1 : 0)) * 31) + java.util.Arrays.hashCode(this.keyphrases)) * 31) + java.util.Arrays.hashCode(this.data)) * 31) + this.audioCapabilities;
        }
    }

    public static class ConfidenceLevel implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.soundtrigger.SoundTrigger.ConfidenceLevel> CREATOR = new android.os.Parcelable.Creator<android.hardware.soundtrigger.SoundTrigger.ConfidenceLevel>() { // from class: android.hardware.soundtrigger.SoundTrigger.ConfidenceLevel.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.soundtrigger.SoundTrigger.ConfidenceLevel createFromParcel(android.os.Parcel parcel) {
                return android.hardware.soundtrigger.SoundTrigger.ConfidenceLevel.fromParcel(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.soundtrigger.SoundTrigger.ConfidenceLevel[] newArray(int i) {
                return new android.hardware.soundtrigger.SoundTrigger.ConfidenceLevel[i];
            }
        };
        public final int confidenceLevel;
        public final int userId;

        public ConfidenceLevel(int i, int i2) {
            this.userId = i;
            this.confidenceLevel = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static android.hardware.soundtrigger.SoundTrigger.ConfidenceLevel fromParcel(android.os.Parcel parcel) {
            return new android.hardware.soundtrigger.SoundTrigger.ConfidenceLevel(parcel.readInt(), parcel.readInt());
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.userId);
            parcel.writeInt(this.confidenceLevel);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public int hashCode() {
            return ((this.confidenceLevel + 31) * 31) + this.userId;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.hardware.soundtrigger.SoundTrigger.ConfidenceLevel confidenceLevel = (android.hardware.soundtrigger.SoundTrigger.ConfidenceLevel) obj;
            if (this.confidenceLevel == confidenceLevel.confidenceLevel && this.userId == confidenceLevel.userId) {
                return true;
            }
            return false;
        }

        public java.lang.String toString() {
            return "ConfidenceLevel [userId=" + this.userId + ", confidenceLevel=" + this.confidenceLevel + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }
    }

    public static final class KeyphraseRecognitionExtra implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra> CREATOR = new android.os.Parcelable.Creator<android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra>() { // from class: android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra createFromParcel(android.os.Parcel parcel) {
                return android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra.fromParcel(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra[] newArray(int i) {
                return new android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra[i];
            }
        };
        public final int coarseConfidenceLevel;
        public final android.hardware.soundtrigger.SoundTrigger.ConfidenceLevel[] confidenceLevels;
        public final int id;
        public final int recognitionModes;

        public KeyphraseRecognitionExtra(int i, int i2, int i3) {
            this(i, i2, i3, new android.hardware.soundtrigger.SoundTrigger.ConfidenceLevel[0]);
        }

        public KeyphraseRecognitionExtra(int i, int i2, int i3, android.hardware.soundtrigger.SoundTrigger.ConfidenceLevel[] confidenceLevelArr) {
            this.id = i;
            this.recognitionModes = i2;
            this.coarseConfidenceLevel = i3;
            this.confidenceLevels = confidenceLevelArr == null ? new android.hardware.soundtrigger.SoundTrigger.ConfidenceLevel[0] : confidenceLevelArr;
        }

        public int getKeyphraseId() {
            return this.id;
        }

        public int getRecognitionModes() {
            return this.recognitionModes;
        }

        public int getCoarseConfidenceLevel() {
            return this.coarseConfidenceLevel;
        }

        public java.util.Collection<android.hardware.soundtrigger.SoundTrigger.ConfidenceLevel> getConfidenceLevels() {
            return java.util.Arrays.asList(this.confidenceLevels);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra fromParcel(android.os.Parcel parcel) {
            return new android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra(parcel.readInt(), parcel.readInt(), parcel.readInt(), (android.hardware.soundtrigger.SoundTrigger.ConfidenceLevel[]) parcel.createTypedArray(android.hardware.soundtrigger.SoundTrigger.ConfidenceLevel.CREATOR));
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.id);
            parcel.writeInt(this.recognitionModes);
            parcel.writeInt(this.coarseConfidenceLevel);
            parcel.writeTypedArray(this.confidenceLevels, i);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public int hashCode() {
            return ((((((java.util.Arrays.hashCode(this.confidenceLevels) + 31) * 31) + this.id) * 31) + this.recognitionModes) * 31) + this.coarseConfidenceLevel;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra keyphraseRecognitionExtra = (android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra) obj;
            if (java.util.Arrays.equals(this.confidenceLevels, keyphraseRecognitionExtra.confidenceLevels) && this.id == keyphraseRecognitionExtra.id && this.recognitionModes == keyphraseRecognitionExtra.recognitionModes && this.coarseConfidenceLevel == keyphraseRecognitionExtra.coarseConfidenceLevel) {
                return true;
            }
            return false;
        }

        public java.lang.String toString() {
            return "KeyphraseRecognitionExtra [id=" + this.id + ", recognitionModes=" + this.recognitionModes + ", coarseConfidenceLevel=" + this.coarseConfidenceLevel + ", confidenceLevels=" + java.util.Arrays.toString(this.confidenceLevels) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }
    }

    public static class KeyphraseRecognitionEvent extends android.hardware.soundtrigger.SoundTrigger.RecognitionEvent implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent> CREATOR = new android.os.Parcelable.Creator<android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent>() { // from class: android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent createFromParcel(android.os.Parcel parcel) {
                return android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent.fromParcelForKeyphrase(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent[] newArray(int i) {
                return new android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent[i];
            }
        };
        public final android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra[] keyphraseExtras;

        public KeyphraseRecognitionEvent(int i, int i2, boolean z, int i3, int i4, int i5, boolean z2, android.media.AudioFormat audioFormat, byte[] bArr, android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra[] keyphraseRecognitionExtraArr, long j, android.os.IBinder iBinder) {
            this(i, i2, z, i3, i4, i5, z2, audioFormat, bArr, keyphraseRecognitionExtraArr, i == 3, j, iBinder);
        }

        public KeyphraseRecognitionEvent(int i, int i2, boolean z, int i3, int i4, int i5, boolean z2, android.media.AudioFormat audioFormat, byte[] bArr, android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra[] keyphraseRecognitionExtraArr, boolean z3, long j, android.os.IBinder iBinder) {
            super(i, i2, z, i3, i4, i5, z2, audioFormat, bArr, z3, j, iBinder);
            this.keyphraseExtras = keyphraseRecognitionExtraArr != null ? keyphraseRecognitionExtraArr : new android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra[0];
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent fromParcelForKeyphrase(android.os.Parcel parcel) {
            android.media.AudioFormat audioFormat;
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            boolean z = parcel.readByte() == 1;
            int readInt3 = parcel.readInt();
            int readInt4 = parcel.readInt();
            int readInt5 = parcel.readInt();
            boolean z2 = parcel.readByte() == 1;
            if (parcel.readByte() != 1) {
                audioFormat = null;
            } else {
                audioFormat = new android.media.AudioFormat.Builder().setChannelMask(parcel.readInt()).setEncoding(parcel.readInt()).setSampleRate(parcel.readInt()).build();
            }
            return new android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent(readInt, readInt2, z, readInt3, readInt4, readInt5, z2, audioFormat, parcel.readBlob(), (android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra[]) parcel.createTypedArray(android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra.CREATOR), parcel.readBoolean(), parcel.readLong(), parcel.readStrongBinder());
        }

        @Override // android.hardware.soundtrigger.SoundTrigger.RecognitionEvent, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.status);
            parcel.writeInt(this.soundModelHandle);
            parcel.writeByte(this.captureAvailable ? (byte) 1 : (byte) 0);
            parcel.writeInt(this.captureSession);
            parcel.writeInt(this.captureDelayMs);
            parcel.writeInt(this.capturePreambleMs);
            parcel.writeByte(this.triggerInData ? (byte) 1 : (byte) 0);
            if (this.captureFormat != null) {
                parcel.writeByte((byte) 1);
                parcel.writeInt(this.captureFormat.getSampleRate());
                parcel.writeInt(this.captureFormat.getEncoding());
                parcel.writeInt(this.captureFormat.getChannelMask());
            } else {
                parcel.writeByte((byte) 0);
            }
            parcel.writeBlob(this.data);
            parcel.writeBoolean(this.recognitionStillActive);
            parcel.writeLong(this.halEventReceivedMillis);
            parcel.writeStrongBinder(this.token);
            parcel.writeTypedArray(this.keyphraseExtras, i);
        }

        @Override // android.hardware.soundtrigger.SoundTrigger.RecognitionEvent, android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.hardware.soundtrigger.SoundTrigger.RecognitionEvent
        public int hashCode() {
            return (super.hashCode() * 31) + java.util.Arrays.hashCode(this.keyphraseExtras);
        }

        @Override // android.hardware.soundtrigger.SoundTrigger.RecognitionEvent
        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            return super.equals(obj) && getClass() == obj.getClass() && java.util.Arrays.equals(this.keyphraseExtras, ((android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent) obj).keyphraseExtras);
        }

        @Override // android.hardware.soundtrigger.SoundTrigger.RecognitionEvent
        public java.lang.String toString() {
            return "KeyphraseRecognitionEvent [keyphraseExtras=" + java.util.Arrays.toString(this.keyphraseExtras) + ", status=" + this.status + ", soundModelHandle=" + this.soundModelHandle + ", captureAvailable=" + this.captureAvailable + ", captureSession=" + this.captureSession + ", captureDelayMs=" + this.captureDelayMs + ", capturePreambleMs=" + this.capturePreambleMs + ", triggerInData=" + this.triggerInData + (this.captureFormat == null ? "" : ", sampleRate=" + this.captureFormat.getSampleRate()) + (this.captureFormat == null ? "" : ", encoding=" + this.captureFormat.getEncoding()) + (this.captureFormat != null ? ", channelMask=" + this.captureFormat.getChannelMask() : "") + ", data=" + (this.data == null ? 0 : this.data.length) + ", recognitionStillActive=" + this.recognitionStillActive + ", halEventReceivedMillis=" + this.halEventReceivedMillis + ", token=" + this.token + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }
    }

    public static class GenericRecognitionEvent extends android.hardware.soundtrigger.SoundTrigger.RecognitionEvent implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent> CREATOR = new android.os.Parcelable.Creator<android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent>() { // from class: android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent createFromParcel(android.os.Parcel parcel) {
                return android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent.fromParcelForGeneric(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent[] newArray(int i) {
                return new android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent[i];
            }
        };

        public GenericRecognitionEvent(int i, int i2, boolean z, int i3, int i4, int i5, boolean z2, android.media.AudioFormat audioFormat, byte[] bArr, long j, android.os.IBinder iBinder) {
            this(i, i2, z, i3, i4, i5, z2, audioFormat, bArr, i == 3, j, iBinder);
        }

        public GenericRecognitionEvent(int i, int i2, boolean z, int i3, int i4, int i5, boolean z2, android.media.AudioFormat audioFormat, byte[] bArr, boolean z3, long j, android.os.IBinder iBinder) {
            super(i, i2, z, i3, i4, i5, z2, audioFormat, bArr, z3, j, iBinder);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent fromParcelForGeneric(android.os.Parcel parcel) {
            android.hardware.soundtrigger.SoundTrigger.RecognitionEvent fromParcel = android.hardware.soundtrigger.SoundTrigger.RecognitionEvent.fromParcel(parcel);
            return new android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent(fromParcel.status, fromParcel.soundModelHandle, fromParcel.captureAvailable, fromParcel.captureSession, fromParcel.captureDelayMs, fromParcel.capturePreambleMs, fromParcel.triggerInData, fromParcel.captureFormat, fromParcel.data, fromParcel.recognitionStillActive, fromParcel.halEventReceivedMillis, fromParcel.token);
        }

        @Override // android.hardware.soundtrigger.SoundTrigger.RecognitionEvent
        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return super.equals(obj);
        }

        @Override // android.hardware.soundtrigger.SoundTrigger.RecognitionEvent
        public java.lang.String toString() {
            return "GenericRecognitionEvent ::" + super.toString();
        }
    }

    public static int handleException(java.lang.Exception exc) {
        android.util.Log.w(TAG, "Exception caught", exc);
        if (exc instanceof android.os.RemoteException) {
            return STATUS_DEAD_OBJECT;
        }
        if (exc instanceof android.os.ServiceSpecificException) {
            switch (((android.os.ServiceSpecificException) exc).errorCode) {
                case 1:
                    return STATUS_BUSY;
                case 2:
                    return STATUS_INVALID_OPERATION;
                case 3:
                    return STATUS_PERMISSION_DENIED;
                case 4:
                    return STATUS_DEAD_OBJECT;
                case 5:
                    return Integer.MIN_VALUE;
                default:
                    return Integer.MIN_VALUE;
            }
        }
        if (exc instanceof java.lang.SecurityException) {
            return STATUS_PERMISSION_DENIED;
        }
        if (exc instanceof java.lang.IllegalStateException) {
            return STATUS_INVALID_OPERATION;
        }
        if ((exc instanceof java.lang.IllegalArgumentException) || (exc instanceof java.lang.NullPointerException)) {
            return STATUS_BAD_VALUE;
        }
        android.util.Log.e(TAG, "Escalating unexpected exception: ", exc);
        throw new java.lang.RuntimeException(exc);
    }

    public static int listModules(java.util.ArrayList<android.hardware.soundtrigger.SoundTrigger.ModuleProperties> arrayList) {
        return 0;
    }

    @java.lang.Deprecated
    public static int listModulesAsOriginator(java.util.ArrayList<android.hardware.soundtrigger.SoundTrigger.ModuleProperties> arrayList, android.media.permission.Identity identity) {
        try {
            convertDescriptorsToModuleProperties(getService().listModulesAsOriginator(identity), arrayList);
            return 0;
        } catch (java.lang.Exception e) {
            return handleException(e);
        }
    }

    @java.lang.Deprecated
    public static int listModulesAsMiddleman(java.util.ArrayList<android.hardware.soundtrigger.SoundTrigger.ModuleProperties> arrayList, android.media.permission.Identity identity, android.media.permission.Identity identity2) {
        try {
            convertDescriptorsToModuleProperties(getService().listModulesAsMiddleman(identity, identity2), arrayList);
            return 0;
        } catch (java.lang.Exception e) {
            return handleException(e);
        }
    }

    private static void convertDescriptorsToModuleProperties(android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor[] soundTriggerModuleDescriptorArr, java.util.ArrayList<android.hardware.soundtrigger.SoundTrigger.ModuleProperties> arrayList) {
        arrayList.clear();
        arrayList.ensureCapacity(soundTriggerModuleDescriptorArr.length);
        for (android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor soundTriggerModuleDescriptor : soundTriggerModuleDescriptorArr) {
            arrayList.add(android.hardware.soundtrigger.ConversionUtil.aidl2apiModuleDescriptor(soundTriggerModuleDescriptor));
        }
    }

    private static android.hardware.soundtrigger.SoundTriggerModule attachModule(int i, android.hardware.soundtrigger.SoundTrigger.StatusListener statusListener, android.os.Handler handler) {
        return null;
    }

    @java.lang.Deprecated
    public static android.hardware.soundtrigger.SoundTriggerModule attachModuleAsMiddleman(int i, android.hardware.soundtrigger.SoundTrigger.StatusListener statusListener, android.os.Handler handler, android.media.permission.Identity identity, android.media.permission.Identity identity2) {
        try {
            return new android.hardware.soundtrigger.SoundTriggerModule(getService(), i, statusListener, handler != null ? handler.getLooper() : android.os.Looper.getMainLooper(), identity, identity2, false);
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "", e);
            return null;
        }
    }

    public static android.hardware.soundtrigger.SoundTriggerModule attachModuleAsOriginator(int i, android.hardware.soundtrigger.SoundTrigger.StatusListener statusListener, android.os.Handler handler, android.media.permission.Identity identity) {
        try {
            return new android.hardware.soundtrigger.SoundTriggerModule(getService(), i, statusListener, handler != null ? handler.getLooper() : android.os.Looper.getMainLooper(), identity);
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "", e);
            return null;
        }
    }

    private static android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService getService() {
        android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService asInterface;
        synchronized (mServiceLock) {
            while (true) {
                try {
                    try {
                        asInterface = android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.SOUND_TRIGGER_MIDDLEWARE_SERVICE));
                    } catch (java.lang.Exception e) {
                        android.util.Log.e(TAG, "Failed to bind to soundtrigger service", e);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        return asInterface;
    }
}
