package android.view;

/* loaded from: classes4.dex */
public final class InputMonitor implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.InputMonitor> CREATOR = new android.os.Parcelable.Creator<android.view.InputMonitor>() { // from class: android.view.InputMonitor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.InputMonitor[] newArray(int i) {
            return new android.view.InputMonitor[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.InputMonitor createFromParcel(android.os.Parcel parcel) {
            return new android.view.InputMonitor(parcel);
        }
    };
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "InputMonitor";
    private final android.view.IInputMonitorHost mHost;
    private final android.view.InputChannel mInputChannel;
    private final android.view.SurfaceControl mSurface;

    public void pilferPointers() {
        try {
            this.mHost.pilferPointers();
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void dispose() {
        this.mInputChannel.dispose();
        this.mSurface.release();
        try {
            this.mHost.dispose();
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public InputMonitor(android.view.InputChannel inputChannel, android.view.IInputMonitorHost iInputMonitorHost, android.view.SurfaceControl surfaceControl) {
        this.mInputChannel = inputChannel;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mInputChannel);
        this.mHost = iInputMonitorHost;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mHost);
        this.mSurface = surfaceControl;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mSurface);
    }

    public android.view.InputChannel getInputChannel() {
        return this.mInputChannel;
    }

    public android.view.IInputMonitorHost getHost() {
        return this.mHost;
    }

    public android.view.SurfaceControl getSurface() {
        return this.mSurface;
    }

    public java.lang.String toString() {
        return "InputMonitor { inputChannel = " + this.mInputChannel + ", host = " + this.mHost + ", surface = " + this.mSurface + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.mInputChannel, i);
        parcel.writeStrongInterface(this.mHost);
        parcel.writeTypedObject(this.mSurface, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    InputMonitor(android.os.Parcel parcel) {
        android.view.InputChannel inputChannel = (android.view.InputChannel) parcel.readTypedObject(android.view.InputChannel.CREATOR);
        android.view.IInputMonitorHost asInterface = android.view.IInputMonitorHost.Stub.asInterface(parcel.readStrongBinder());
        android.view.SurfaceControl surfaceControl = (android.view.SurfaceControl) parcel.readTypedObject(android.view.SurfaceControl.CREATOR);
        this.mInputChannel = inputChannel;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mInputChannel);
        this.mHost = asInterface;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mHost);
        this.mSurface = surfaceControl;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mSurface);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
