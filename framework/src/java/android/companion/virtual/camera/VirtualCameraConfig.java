package android.companion.virtual.camera;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class VirtualCameraConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.companion.virtual.camera.VirtualCameraConfig> CREATOR = new android.os.Parcelable.Creator<android.companion.virtual.camera.VirtualCameraConfig>() { // from class: android.companion.virtual.camera.VirtualCameraConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.companion.virtual.camera.VirtualCameraConfig createFromParcel(android.os.Parcel parcel) {
            return new android.companion.virtual.camera.VirtualCameraConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.companion.virtual.camera.VirtualCameraConfig[] newArray(int i) {
            return new android.companion.virtual.camera.VirtualCameraConfig[i];
        }
    };
    private static final int LENS_FACING_UNKNOWN = -1;
    public static final int SENSOR_ORIENTATION_0 = 0;
    public static final int SENSOR_ORIENTATION_180 = 180;
    public static final int SENSOR_ORIENTATION_270 = 270;
    public static final int SENSOR_ORIENTATION_90 = 90;
    private final android.companion.virtual.camera.IVirtualCameraCallback mCallback;
    private final int mLensFacing;
    private final java.lang.String mName;
    private final int mSensorOrientation;
    private final java.util.Set<android.companion.virtual.camera.VirtualCameraStreamConfig> mStreamConfigurations;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SensorOrientation {
    }

    private VirtualCameraConfig(java.lang.String str, java.util.Set<android.companion.virtual.camera.VirtualCameraStreamConfig> set, java.util.concurrent.Executor executor, android.companion.virtual.camera.VirtualCameraCallback virtualCameraCallback, int i, int i2) {
        this.mName = (java.lang.String) java.util.Objects.requireNonNull(str, "Missing name");
        if (i2 == -1) {
            throw new java.lang.IllegalArgumentException("Lens facing must be set");
        }
        this.mLensFacing = i2;
        this.mStreamConfigurations = java.util.Set.copyOf((java.util.Collection) java.util.Objects.requireNonNull(set, "Missing stream configurations"));
        if (this.mStreamConfigurations.isEmpty()) {
            throw new java.lang.IllegalArgumentException("At least one stream configuration is needed to create a virtual camera.");
        }
        this.mCallback = new android.companion.virtual.camera.VirtualCameraConfig.VirtualCameraCallbackInternal((android.companion.virtual.camera.VirtualCameraCallback) java.util.Objects.requireNonNull(virtualCameraCallback, "Missing callback"), (java.util.concurrent.Executor) java.util.Objects.requireNonNull(executor, "Missing callback executor"));
        this.mSensorOrientation = i;
    }

    private VirtualCameraConfig(android.os.Parcel parcel) {
        this.mName = parcel.readString8();
        this.mCallback = android.companion.virtual.camera.IVirtualCameraCallback.Stub.asInterface(parcel.readStrongBinder());
        this.mStreamConfigurations = java.util.Set.of(parcel.readParcelableArray(android.companion.virtual.camera.VirtualCameraStreamConfig.class.getClassLoader(), android.companion.virtual.camera.VirtualCameraStreamConfig.class));
        this.mSensorOrientation = parcel.readInt();
        this.mLensFacing = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString8(this.mName);
        parcel.writeStrongInterface(this.mCallback);
        parcel.writeParcelableArray((android.companion.virtual.camera.VirtualCameraStreamConfig[]) this.mStreamConfigurations.toArray(new android.companion.virtual.camera.VirtualCameraStreamConfig[0]), i);
        parcel.writeInt(this.mSensorOrientation);
        parcel.writeInt(this.mLensFacing);
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public java.util.Set<android.companion.virtual.camera.VirtualCameraStreamConfig> getStreamConfigs() {
        return this.mStreamConfigurations;
    }

    public android.companion.virtual.camera.IVirtualCameraCallback getCallback() {
        return this.mCallback;
    }

    public int getSensorOrientation() {
        return this.mSensorOrientation;
    }

    public int getLensFacing() {
        return this.mLensFacing;
    }

    public static final class Builder {
        private android.companion.virtual.camera.VirtualCameraCallback mCallback;
        private java.util.concurrent.Executor mCallbackExecutor;
        private final java.lang.String mName;
        private final android.util.ArraySet<android.companion.virtual.camera.VirtualCameraStreamConfig> mStreamConfigurations = new android.util.ArraySet<>();
        private int mSensorOrientation = 0;
        private int mLensFacing = -1;

        public Builder(java.lang.String str) {
            this.mName = (java.lang.String) java.util.Objects.requireNonNull(str, "Name cannot be null");
        }

        public android.companion.virtual.camera.VirtualCameraConfig.Builder addStreamConfig(int i, int i2, int i3, int i4) {
            if (i <= 0 || i > 2048) {
                throw new java.lang.IllegalArgumentException("Invalid width passed for stream config: " + i + ", must be between 1 and 2048");
            }
            if (i2 <= 0 || i2 > 2048) {
                throw new java.lang.IllegalArgumentException("Invalid height passed for stream config: " + i2 + ", must be between 1 and 2048");
            }
            if (!android.companion.virtual.camera.VirtualCameraConfig.isFormatSupported(i3)) {
                throw new java.lang.IllegalArgumentException("Invalid format passed for stream config: " + i3);
            }
            if (i4 <= 0 || i4 > 60) {
                throw new java.lang.IllegalArgumentException("Invalid maximumFramesPerSecond, must be greater than 0 and less than 60");
            }
            this.mStreamConfigurations.add(new android.companion.virtual.camera.VirtualCameraStreamConfig(i, i2, i3, i4));
            return this;
        }

        public android.companion.virtual.camera.VirtualCameraConfig.Builder setSensorOrientation(int i) {
            if (i != 0 && i != 90 && i != 180 && i != 270) {
                throw new java.lang.IllegalArgumentException("Invalid sensor orientation: " + i);
            }
            this.mSensorOrientation = i;
            return this;
        }

        public android.companion.virtual.camera.VirtualCameraConfig.Builder setLensFacing(int i) {
            if (i != 1 && i != 0) {
                throw new java.lang.IllegalArgumentException("Unsupported lens facing: " + i);
            }
            this.mLensFacing = i;
            return this;
        }

        public android.companion.virtual.camera.VirtualCameraConfig.Builder setVirtualCameraCallback(java.util.concurrent.Executor executor, android.companion.virtual.camera.VirtualCameraCallback virtualCameraCallback) {
            this.mCallbackExecutor = (java.util.concurrent.Executor) java.util.Objects.requireNonNull(executor);
            this.mCallback = (android.companion.virtual.camera.VirtualCameraCallback) java.util.Objects.requireNonNull(virtualCameraCallback);
            return this;
        }

        public android.companion.virtual.camera.VirtualCameraConfig build() {
            return new android.companion.virtual.camera.VirtualCameraConfig(this.mName, this.mStreamConfigurations, this.mCallbackExecutor, this.mCallback, this.mSensorOrientation, this.mLensFacing);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class VirtualCameraCallbackInternal extends android.companion.virtual.camera.IVirtualCameraCallback.Stub {
        private final android.companion.virtual.camera.VirtualCameraCallback mCallback;
        private final java.util.concurrent.Executor mExecutor;

        private VirtualCameraCallbackInternal(android.companion.virtual.camera.VirtualCameraCallback virtualCameraCallback, java.util.concurrent.Executor executor) {
            this.mCallback = virtualCameraCallback;
            this.mExecutor = executor;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStreamConfigured$0(int i, android.view.Surface surface, int i2, int i3, int i4) {
            this.mCallback.onStreamConfigured(i, surface, i2, i3, i4);
        }

        @Override // android.companion.virtual.camera.IVirtualCameraCallback
        public void onStreamConfigured(final int i, final android.view.Surface surface, final int i2, final int i3, final int i4) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.companion.virtual.camera.VirtualCameraConfig$VirtualCameraCallbackInternal$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.companion.virtual.camera.VirtualCameraConfig.VirtualCameraCallbackInternal.this.lambda$onStreamConfigured$0(i, surface, i2, i3, i4);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onProcessCaptureRequest$1(int i, long j) {
            this.mCallback.onProcessCaptureRequest(i, j);
        }

        @Override // android.companion.virtual.camera.IVirtualCameraCallback
        public void onProcessCaptureRequest(final int i, final long j) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.companion.virtual.camera.VirtualCameraConfig$VirtualCameraCallbackInternal$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.companion.virtual.camera.VirtualCameraConfig.VirtualCameraCallbackInternal.this.lambda$onProcessCaptureRequest$1(i, j);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStreamClosed$2(int i) {
            this.mCallback.onStreamClosed(i);
        }

        @Override // android.companion.virtual.camera.IVirtualCameraCallback
        public void onStreamClosed(final int i) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.companion.virtual.camera.VirtualCameraConfig$VirtualCameraCallbackInternal$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.companion.virtual.camera.VirtualCameraConfig.VirtualCameraCallbackInternal.this.lambda$onStreamClosed$2(i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isFormatSupported(int i) {
        switch (i) {
            case 1:
            case 35:
                return true;
            default:
                return false;
        }
    }
}
