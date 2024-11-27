package android.hardware.camera2.params;

/* loaded from: classes.dex */
public final class SessionConfiguration implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.camera2.params.SessionConfiguration> CREATOR = new android.os.Parcelable.Creator<android.hardware.camera2.params.SessionConfiguration>() { // from class: android.hardware.camera2.params.SessionConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.camera2.params.SessionConfiguration createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.camera2.params.SessionConfiguration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.camera2.params.SessionConfiguration[] newArray(int i) {
            return new android.hardware.camera2.params.SessionConfiguration[i];
        }
    };
    public static final int SESSION_HIGH_SPEED = 1;
    public static final int SESSION_REGULAR = 0;
    public static final int SESSION_VENDOR_START = 32768;
    private static final java.lang.String TAG = "SessionConfiguration";
    private int mColorSpace;
    private java.util.concurrent.Executor mExecutor;
    private android.hardware.camera2.params.InputConfiguration mInputConfig;
    private java.util.List<android.hardware.camera2.params.OutputConfiguration> mOutputConfigurations;
    private android.hardware.camera2.CaptureRequest mSessionParameters;
    private int mSessionType;
    private android.hardware.camera2.CameraCaptureSession.StateCallback mStateCallback;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SessionMode {
    }

    public SessionConfiguration(int i, java.util.List<android.hardware.camera2.params.OutputConfiguration> list, java.util.concurrent.Executor executor, android.hardware.camera2.CameraCaptureSession.StateCallback stateCallback) {
        this.mExecutor = null;
        this.mInputConfig = null;
        this.mSessionParameters = null;
        this.mSessionType = i;
        this.mOutputConfigurations = java.util.Collections.unmodifiableList(new java.util.ArrayList(list));
        this.mStateCallback = stateCallback;
        this.mExecutor = executor;
    }

    private SessionConfiguration(android.os.Parcel parcel) {
        this.mExecutor = null;
        this.mInputConfig = null;
        this.mSessionParameters = null;
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        int readInt3 = parcel.readInt();
        int readInt4 = parcel.readInt();
        boolean readBoolean = parcel.readBoolean();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readTypedList(arrayList, android.hardware.camera2.params.OutputConfiguration.CREATOR);
        if (com.android.internal.camera.flags.Flags.featureCombinationQuery() && parcel.readBoolean()) {
            new android.hardware.camera2.impl.CameraMetadataNative().readFromParcel(parcel);
        }
        if (readInt2 > 0 && readInt3 > 0 && readInt4 != -1) {
            this.mInputConfig = new android.hardware.camera2.params.InputConfiguration(readInt2, readInt3, readInt4, readBoolean);
        }
        this.mSessionType = readInt;
        this.mOutputConfigurations = arrayList;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (parcel == null) {
            throw new java.lang.IllegalArgumentException("dest must not be null");
        }
        parcel.writeInt(this.mSessionType);
        if (this.mInputConfig != null) {
            parcel.writeInt(this.mInputConfig.getWidth());
            parcel.writeInt(this.mInputConfig.getHeight());
            parcel.writeInt(this.mInputConfig.getFormat());
            parcel.writeBoolean(this.mInputConfig.isMultiResolution());
        } else {
            parcel.writeInt(0);
            parcel.writeInt(0);
            parcel.writeInt(-1);
            parcel.writeBoolean(false);
        }
        parcel.writeTypedList(this.mOutputConfigurations);
        if (com.android.internal.camera.flags.Flags.featureCombinationQuery()) {
            if (this.mSessionParameters != null) {
                parcel.writeBoolean(true);
                this.mSessionParameters.getNativeCopy().writeToParcel(parcel, 0);
            } else {
                parcel.writeBoolean(false);
            }
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.hardware.camera2.params.SessionConfiguration)) {
            return false;
        }
        android.hardware.camera2.params.SessionConfiguration sessionConfiguration = (android.hardware.camera2.params.SessionConfiguration) obj;
        if (this.mInputConfig != sessionConfiguration.mInputConfig || this.mSessionType != sessionConfiguration.mSessionType || this.mOutputConfigurations.size() != sessionConfiguration.mOutputConfigurations.size()) {
            return false;
        }
        for (int i = 0; i < this.mOutputConfigurations.size(); i++) {
            if (!this.mOutputConfigurations.get(i).equals(sessionConfiguration.mOutputConfigurations.get(i))) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        return android.hardware.camera2.utils.HashCodeHelpers.hashCode(this.mOutputConfigurations.hashCode(), this.mInputConfig.hashCode(), this.mSessionType);
    }

    public int getSessionType() {
        return this.mSessionType;
    }

    public java.util.List<android.hardware.camera2.params.OutputConfiguration> getOutputConfigurations() {
        return this.mOutputConfigurations;
    }

    public android.hardware.camera2.CameraCaptureSession.StateCallback getStateCallback() {
        return this.mStateCallback;
    }

    public java.util.concurrent.Executor getExecutor() {
        return this.mExecutor;
    }

    public void setInputConfiguration(android.hardware.camera2.params.InputConfiguration inputConfiguration) {
        if (this.mSessionType != 1) {
            this.mInputConfig = inputConfiguration;
            return;
        }
        throw new java.lang.UnsupportedOperationException("Method not supported for high speed session types");
    }

    public android.hardware.camera2.params.InputConfiguration getInputConfiguration() {
        return this.mInputConfig;
    }

    public void setSessionParameters(android.hardware.camera2.CaptureRequest captureRequest) {
        this.mSessionParameters = captureRequest;
    }

    public android.hardware.camera2.CaptureRequest getSessionParameters() {
        return this.mSessionParameters;
    }

    public void setColorSpace(android.graphics.ColorSpace.Named named) {
        this.mColorSpace = named.ordinal();
        java.util.Iterator<android.hardware.camera2.params.OutputConfiguration> it = this.mOutputConfigurations.iterator();
        while (it.hasNext()) {
            it.next().setColorSpace(named);
        }
    }

    public void clearColorSpace() {
        this.mColorSpace = -1;
        java.util.Iterator<android.hardware.camera2.params.OutputConfiguration> it = this.mOutputConfigurations.iterator();
        while (it.hasNext()) {
            it.next().clearColorSpace();
        }
    }

    public android.graphics.ColorSpace getColorSpace() {
        if (this.mColorSpace != -1) {
            return android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.values()[this.mColorSpace]);
        }
        return null;
    }
}
