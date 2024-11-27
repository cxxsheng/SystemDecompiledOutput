package android.service.voice;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class HotwordAudioStream implements android.os.Parcelable {
    public static final java.lang.String KEY_AUDIO_STREAM_COPY_BUFFER_LENGTH_BYTES = "android.service.voice.key.AUDIO_STREAM_COPY_BUFFER_LENGTH_BYTES";
    private final android.media.AudioFormat mAudioFormat;
    private final android.os.ParcelFileDescriptor mAudioStreamParcelFileDescriptor;
    private final byte[] mInitialAudio;
    private final android.os.PersistableBundle mMetadata;
    private final android.media.AudioTimestamp mTimestamp;
    private static final byte[] DEFAULT_INITIAL_EMPTY_AUDIO = new byte[0];
    public static final android.os.Parcelable.Creator<android.service.voice.HotwordAudioStream> CREATOR = new android.os.Parcelable.Creator<android.service.voice.HotwordAudioStream>() { // from class: android.service.voice.HotwordAudioStream.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.voice.HotwordAudioStream[] newArray(int i) {
            return new android.service.voice.HotwordAudioStream[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.voice.HotwordAudioStream createFromParcel(android.os.Parcel parcel) {
            return new android.service.voice.HotwordAudioStream(parcel);
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public static android.media.AudioTimestamp defaultTimestamp() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.os.PersistableBundle defaultMetadata() {
        return new android.os.PersistableBundle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] defaultInitialAudio() {
        return DEFAULT_INITIAL_EMPTY_AUDIO;
    }

    private java.lang.String initialAudioToString() {
        return "length=" + this.mInitialAudio.length;
    }

    public android.service.voice.HotwordAudioStream.Builder buildUpon() {
        return new android.service.voice.HotwordAudioStream.Builder(this.mAudioFormat, this.mAudioStreamParcelFileDescriptor).setTimestamp(this.mTimestamp).setMetadata(this.mMetadata).setInitialAudio(this.mInitialAudio);
    }

    static abstract class BaseBuilder {
        BaseBuilder() {
        }

        public android.service.voice.HotwordAudioStream.Builder setInitialAudio(byte[] bArr) {
            java.util.Objects.requireNonNull(bArr, "value should not be null");
            android.service.voice.HotwordAudioStream.Builder builder = (android.service.voice.HotwordAudioStream.Builder) this;
            builder.mBuilderFieldsSet |= 16;
            builder.mInitialAudio = bArr;
            return builder;
        }
    }

    HotwordAudioStream(android.media.AudioFormat audioFormat, android.os.ParcelFileDescriptor parcelFileDescriptor, android.media.AudioTimestamp audioTimestamp, android.os.PersistableBundle persistableBundle, byte[] bArr) {
        this.mAudioFormat = audioFormat;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mAudioFormat);
        this.mAudioStreamParcelFileDescriptor = parcelFileDescriptor;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mAudioStreamParcelFileDescriptor);
        this.mTimestamp = audioTimestamp;
        this.mMetadata = persistableBundle;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mMetadata);
        this.mInitialAudio = bArr;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mInitialAudio);
    }

    public android.media.AudioFormat getAudioFormat() {
        return this.mAudioFormat;
    }

    public android.os.ParcelFileDescriptor getAudioStreamParcelFileDescriptor() {
        return this.mAudioStreamParcelFileDescriptor;
    }

    public android.media.AudioTimestamp getTimestamp() {
        return this.mTimestamp;
    }

    public android.os.PersistableBundle getMetadata() {
        return this.mMetadata;
    }

    public byte[] getInitialAudio() {
        return this.mInitialAudio;
    }

    public java.lang.String toString() {
        return "HotwordAudioStream { audioFormat = " + this.mAudioFormat + ", audioStreamParcelFileDescriptor = " + this.mAudioStreamParcelFileDescriptor + ", timestamp = " + this.mTimestamp + ", metadata = " + this.mMetadata + ", initialAudio = " + initialAudioToString() + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.service.voice.HotwordAudioStream hotwordAudioStream = (android.service.voice.HotwordAudioStream) obj;
        if (java.util.Objects.equals(this.mAudioFormat, hotwordAudioStream.mAudioFormat) && java.util.Objects.equals(this.mAudioStreamParcelFileDescriptor, hotwordAudioStream.mAudioStreamParcelFileDescriptor) && java.util.Objects.equals(this.mTimestamp, hotwordAudioStream.mTimestamp) && java.util.Objects.equals(this.mMetadata, hotwordAudioStream.mMetadata) && java.util.Arrays.equals(this.mInitialAudio, hotwordAudioStream.mInitialAudio)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((((java.util.Objects.hashCode(this.mAudioFormat) + 31) * 31) + java.util.Objects.hashCode(this.mAudioStreamParcelFileDescriptor)) * 31) + java.util.Objects.hashCode(this.mTimestamp)) * 31) + java.util.Objects.hashCode(this.mMetadata)) * 31) + java.util.Arrays.hashCode(this.mInitialAudio);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByte(this.mTimestamp != null ? (byte) 4 : (byte) 0);
        parcel.writeTypedObject(this.mAudioFormat, i);
        parcel.writeTypedObject(this.mAudioStreamParcelFileDescriptor, i);
        if (this.mTimestamp != null) {
            parcel.writeTypedObject(this.mTimestamp, i);
        }
        parcel.writeTypedObject(this.mMetadata, i);
        parcel.writeByteArray(this.mInitialAudio);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    HotwordAudioStream(android.os.Parcel parcel) {
        byte readByte = parcel.readByte();
        android.media.AudioFormat audioFormat = (android.media.AudioFormat) parcel.readTypedObject(android.media.AudioFormat.CREATOR);
        android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
        android.media.AudioTimestamp audioTimestamp = (readByte & 4) == 0 ? null : (android.media.AudioTimestamp) parcel.readTypedObject(android.media.AudioTimestamp.CREATOR);
        android.os.PersistableBundle persistableBundle = (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR);
        byte[] createByteArray = parcel.createByteArray();
        this.mAudioFormat = audioFormat;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mAudioFormat);
        this.mAudioStreamParcelFileDescriptor = parcelFileDescriptor;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mAudioStreamParcelFileDescriptor);
        this.mTimestamp = audioTimestamp;
        this.mMetadata = persistableBundle;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mMetadata);
        this.mInitialAudio = createByteArray;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mInitialAudio);
    }

    public static final class Builder extends android.service.voice.HotwordAudioStream.BaseBuilder {
        private android.media.AudioFormat mAudioFormat;
        private android.os.ParcelFileDescriptor mAudioStreamParcelFileDescriptor;
        private long mBuilderFieldsSet = 0;
        private byte[] mInitialAudio;
        private android.os.PersistableBundle mMetadata;
        private android.media.AudioTimestamp mTimestamp;

        @Override // android.service.voice.HotwordAudioStream.BaseBuilder
        public /* bridge */ /* synthetic */ android.service.voice.HotwordAudioStream.Builder setInitialAudio(byte[] bArr) {
            return super.setInitialAudio(bArr);
        }

        public Builder(android.media.AudioFormat audioFormat, android.os.ParcelFileDescriptor parcelFileDescriptor) {
            this.mAudioFormat = audioFormat;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mAudioFormat);
            this.mAudioStreamParcelFileDescriptor = parcelFileDescriptor;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mAudioStreamParcelFileDescriptor);
        }

        public android.service.voice.HotwordAudioStream.Builder setAudioFormat(android.media.AudioFormat audioFormat) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mAudioFormat = audioFormat;
            return this;
        }

        public android.service.voice.HotwordAudioStream.Builder setAudioStreamParcelFileDescriptor(android.os.ParcelFileDescriptor parcelFileDescriptor) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mAudioStreamParcelFileDescriptor = parcelFileDescriptor;
            return this;
        }

        public android.service.voice.HotwordAudioStream.Builder setTimestamp(android.media.AudioTimestamp audioTimestamp) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mTimestamp = audioTimestamp;
            return this;
        }

        public android.service.voice.HotwordAudioStream.Builder setMetadata(android.os.PersistableBundle persistableBundle) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mMetadata = persistableBundle;
            return this;
        }

        public android.service.voice.HotwordAudioStream build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 32;
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mTimestamp = android.service.voice.HotwordAudioStream.defaultTimestamp();
            }
            if ((this.mBuilderFieldsSet & 8) == 0) {
                this.mMetadata = android.service.voice.HotwordAudioStream.defaultMetadata();
            }
            if ((this.mBuilderFieldsSet & 16) == 0) {
                this.mInitialAudio = android.service.voice.HotwordAudioStream.defaultInitialAudio();
            }
            return new android.service.voice.HotwordAudioStream(this.mAudioFormat, this.mAudioStreamParcelFileDescriptor, this.mTimestamp, this.mMetadata, this.mInitialAudio);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 32) != 0) {
                throw new java.lang.IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
