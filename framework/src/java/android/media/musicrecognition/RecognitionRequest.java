package android.media.musicrecognition;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class RecognitionRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.musicrecognition.RecognitionRequest> CREATOR = new android.os.Parcelable.Creator<android.media.musicrecognition.RecognitionRequest>() { // from class: android.media.musicrecognition.RecognitionRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.musicrecognition.RecognitionRequest createFromParcel(android.os.Parcel parcel) {
            return new android.media.musicrecognition.RecognitionRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.musicrecognition.RecognitionRequest[] newArray(int i) {
            return new android.media.musicrecognition.RecognitionRequest[i];
        }
    };
    private final android.media.AudioAttributes mAudioAttributes;
    private final android.media.AudioFormat mAudioFormat;
    private final int mCaptureSession;
    private final int mIgnoreBeginningFrames;
    private final int mMaxAudioLengthSeconds;

    private RecognitionRequest(android.media.musicrecognition.RecognitionRequest.Builder builder) {
        this.mAudioAttributes = (android.media.AudioAttributes) java.util.Objects.requireNonNull(builder.mAudioAttributes);
        this.mAudioFormat = (android.media.AudioFormat) java.util.Objects.requireNonNull(builder.mAudioFormat);
        this.mCaptureSession = builder.mCaptureSession;
        this.mMaxAudioLengthSeconds = builder.mMaxAudioLengthSeconds;
        this.mIgnoreBeginningFrames = builder.mIgnoreBeginningFrames;
    }

    public android.media.AudioAttributes getAudioAttributes() {
        return this.mAudioAttributes;
    }

    public android.media.AudioFormat getAudioFormat() {
        return this.mAudioFormat;
    }

    public int getCaptureSession() {
        return this.mCaptureSession;
    }

    public int getMaxAudioLengthSeconds() {
        return this.mMaxAudioLengthSeconds;
    }

    public int getIgnoreBeginningFrames() {
        return this.mIgnoreBeginningFrames;
    }

    @android.annotation.SystemApi
    public static final class Builder {
        private android.media.AudioFormat mAudioFormat = new android.media.AudioFormat.Builder().setSampleRate(16000).setEncoding(2).build();
        private android.media.AudioAttributes mAudioAttributes = new android.media.AudioAttributes.Builder().setContentType(2).build();
        private int mCaptureSession = 1;
        private int mMaxAudioLengthSeconds = 24;
        private int mIgnoreBeginningFrames = 0;

        public android.media.musicrecognition.RecognitionRequest.Builder setAudioAttributes(android.media.AudioAttributes audioAttributes) {
            this.mAudioAttributes = audioAttributes;
            return this;
        }

        public android.media.musicrecognition.RecognitionRequest.Builder setAudioFormat(android.media.AudioFormat audioFormat) {
            this.mAudioFormat = audioFormat;
            return this;
        }

        public android.media.musicrecognition.RecognitionRequest.Builder setCaptureSession(int i) {
            this.mCaptureSession = i;
            return this;
        }

        public android.media.musicrecognition.RecognitionRequest.Builder setMaxAudioLengthSeconds(int i) {
            this.mMaxAudioLengthSeconds = i;
            return this;
        }

        public android.media.musicrecognition.RecognitionRequest.Builder setIgnoreBeginningFrames(int i) {
            this.mIgnoreBeginningFrames = i;
            return this;
        }

        public android.media.musicrecognition.RecognitionRequest build() {
            return new android.media.musicrecognition.RecognitionRequest(this);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mAudioFormat, i);
        parcel.writeParcelable(this.mAudioAttributes, i);
        parcel.writeInt(this.mCaptureSession);
        parcel.writeInt(this.mMaxAudioLengthSeconds);
        parcel.writeInt(this.mIgnoreBeginningFrames);
    }

    private RecognitionRequest(android.os.Parcel parcel) {
        this.mAudioFormat = (android.media.AudioFormat) parcel.readParcelable(android.media.AudioFormat.class.getClassLoader(), android.media.AudioFormat.class);
        this.mAudioAttributes = (android.media.AudioAttributes) parcel.readParcelable(android.media.AudioAttributes.class.getClassLoader(), android.media.AudioAttributes.class);
        this.mCaptureSession = parcel.readInt();
        this.mMaxAudioLengthSeconds = parcel.readInt();
        this.mIgnoreBeginningFrames = parcel.readInt();
    }
}
