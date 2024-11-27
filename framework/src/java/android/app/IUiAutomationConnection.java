package android.app;

/* loaded from: classes.dex */
public interface IUiAutomationConnection extends android.os.IInterface {
    void adoptShellPermissionIdentity(int i, java.lang.String[] strArr) throws android.os.RemoteException;

    void clearWindowAnimationFrameStats() throws android.os.RemoteException;

    boolean clearWindowContentFrameStats(int i) throws android.os.RemoteException;

    void connect(android.accessibilityservice.IAccessibilityServiceClient iAccessibilityServiceClient, int i) throws android.os.RemoteException;

    void disconnect() throws android.os.RemoteException;

    void dropShellPermissionIdentity() throws android.os.RemoteException;

    void executeShellCommand(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2) throws android.os.RemoteException;

    void executeShellCommandWithStderr(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2, android.os.ParcelFileDescriptor parcelFileDescriptor3) throws android.os.RemoteException;

    java.util.List<java.lang.String> getAdoptedShellPermissions() throws android.os.RemoteException;

    android.view.WindowAnimationFrameStats getWindowAnimationFrameStats() throws android.os.RemoteException;

    android.view.WindowContentFrameStats getWindowContentFrameStats(int i) throws android.os.RemoteException;

    void grantRuntimePermission(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    boolean injectInputEvent(android.view.InputEvent inputEvent, boolean z, boolean z2) throws android.os.RemoteException;

    void injectInputEventToInputFilter(android.view.InputEvent inputEvent) throws android.os.RemoteException;

    void revokeRuntimePermission(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    boolean setRotation(int i) throws android.os.RemoteException;

    void shutdown() throws android.os.RemoteException;

    void syncInputTransactions(boolean z) throws android.os.RemoteException;

    boolean takeScreenshot(android.graphics.Rect rect, android.window.ScreenCapture.ScreenCaptureListener screenCaptureListener) throws android.os.RemoteException;

    boolean takeSurfaceControlScreenshot(android.view.SurfaceControl surfaceControl, android.window.ScreenCapture.ScreenCaptureListener screenCaptureListener) throws android.os.RemoteException;

    public static class Default implements android.app.IUiAutomationConnection {
        @Override // android.app.IUiAutomationConnection
        public void connect(android.accessibilityservice.IAccessibilityServiceClient iAccessibilityServiceClient, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IUiAutomationConnection
        public void disconnect() throws android.os.RemoteException {
        }

        @Override // android.app.IUiAutomationConnection
        public boolean injectInputEvent(android.view.InputEvent inputEvent, boolean z, boolean z2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IUiAutomationConnection
        public void injectInputEventToInputFilter(android.view.InputEvent inputEvent) throws android.os.RemoteException {
        }

        @Override // android.app.IUiAutomationConnection
        public void syncInputTransactions(boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.IUiAutomationConnection
        public boolean setRotation(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IUiAutomationConnection
        public boolean takeScreenshot(android.graphics.Rect rect, android.window.ScreenCapture.ScreenCaptureListener screenCaptureListener) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IUiAutomationConnection
        public boolean takeSurfaceControlScreenshot(android.view.SurfaceControl surfaceControl, android.window.ScreenCapture.ScreenCaptureListener screenCaptureListener) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IUiAutomationConnection
        public boolean clearWindowContentFrameStats(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IUiAutomationConnection
        public android.view.WindowContentFrameStats getWindowContentFrameStats(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IUiAutomationConnection
        public void clearWindowAnimationFrameStats() throws android.os.RemoteException {
        }

        @Override // android.app.IUiAutomationConnection
        public android.view.WindowAnimationFrameStats getWindowAnimationFrameStats() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IUiAutomationConnection
        public void executeShellCommand(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2) throws android.os.RemoteException {
        }

        @Override // android.app.IUiAutomationConnection
        public void grantRuntimePermission(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IUiAutomationConnection
        public void revokeRuntimePermission(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IUiAutomationConnection
        public void adoptShellPermissionIdentity(int i, java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.app.IUiAutomationConnection
        public void dropShellPermissionIdentity() throws android.os.RemoteException {
        }

        @Override // android.app.IUiAutomationConnection
        public void shutdown() throws android.os.RemoteException {
        }

        @Override // android.app.IUiAutomationConnection
        public void executeShellCommandWithStderr(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2, android.os.ParcelFileDescriptor parcelFileDescriptor3) throws android.os.RemoteException {
        }

        @Override // android.app.IUiAutomationConnection
        public java.util.List<java.lang.String> getAdoptedShellPermissions() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.IUiAutomationConnection {
        public static final java.lang.String DESCRIPTOR = "android.app.IUiAutomationConnection";
        static final int TRANSACTION_adoptShellPermissionIdentity = 16;
        static final int TRANSACTION_clearWindowAnimationFrameStats = 11;
        static final int TRANSACTION_clearWindowContentFrameStats = 9;
        static final int TRANSACTION_connect = 1;
        static final int TRANSACTION_disconnect = 2;
        static final int TRANSACTION_dropShellPermissionIdentity = 17;
        static final int TRANSACTION_executeShellCommand = 13;
        static final int TRANSACTION_executeShellCommandWithStderr = 19;
        static final int TRANSACTION_getAdoptedShellPermissions = 20;
        static final int TRANSACTION_getWindowAnimationFrameStats = 12;
        static final int TRANSACTION_getWindowContentFrameStats = 10;
        static final int TRANSACTION_grantRuntimePermission = 14;
        static final int TRANSACTION_injectInputEvent = 3;
        static final int TRANSACTION_injectInputEventToInputFilter = 4;
        static final int TRANSACTION_revokeRuntimePermission = 15;
        static final int TRANSACTION_setRotation = 6;
        static final int TRANSACTION_shutdown = 18;
        static final int TRANSACTION_syncInputTransactions = 5;
        static final int TRANSACTION_takeScreenshot = 7;
        static final int TRANSACTION_takeSurfaceControlScreenshot = 8;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.app.IUiAutomationConnection asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.IUiAutomationConnection)) {
                return (android.app.IUiAutomationConnection) queryLocalInterface;
            }
            return new android.app.IUiAutomationConnection.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return android.media.MediaMetrics.Value.CONNECT;
                case 2:
                    return android.media.MediaMetrics.Value.DISCONNECT;
                case 3:
                    return "injectInputEvent";
                case 4:
                    return "injectInputEventToInputFilter";
                case 5:
                    return "syncInputTransactions";
                case 6:
                    return "setRotation";
                case 7:
                    return "takeScreenshot";
                case 8:
                    return "takeSurfaceControlScreenshot";
                case 9:
                    return "clearWindowContentFrameStats";
                case 10:
                    return "getWindowContentFrameStats";
                case 11:
                    return "clearWindowAnimationFrameStats";
                case 12:
                    return "getWindowAnimationFrameStats";
                case 13:
                    return "executeShellCommand";
                case 14:
                    return "grantRuntimePermission";
                case 15:
                    return "revokeRuntimePermission";
                case 16:
                    return "adoptShellPermissionIdentity";
                case 17:
                    return "dropShellPermissionIdentity";
                case 18:
                    return "shutdown";
                case 19:
                    return "executeShellCommandWithStderr";
                case 20:
                    return "getAdoptedShellPermissions";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.accessibilityservice.IAccessibilityServiceClient asInterface = android.accessibilityservice.IAccessibilityServiceClient.Stub.asInterface(parcel.readStrongBinder());
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    connect(asInterface, readInt);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    disconnect();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.view.InputEvent inputEvent = (android.view.InputEvent) parcel.readTypedObject(android.view.InputEvent.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean injectInputEvent = injectInputEvent(inputEvent, readBoolean, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(injectInputEvent);
                    return true;
                case 4:
                    android.view.InputEvent inputEvent2 = (android.view.InputEvent) parcel.readTypedObject(android.view.InputEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    injectInputEventToInputFilter(inputEvent2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    syncInputTransactions(readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean rotation = setRotation(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(rotation);
                    return true;
                case 7:
                    android.graphics.Rect rect = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    android.window.ScreenCapture.ScreenCaptureListener screenCaptureListener = (android.window.ScreenCapture.ScreenCaptureListener) parcel.readTypedObject(android.window.ScreenCapture.ScreenCaptureListener.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean takeScreenshot = takeScreenshot(rect, screenCaptureListener);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(takeScreenshot);
                    return true;
                case 8:
                    android.view.SurfaceControl surfaceControl = (android.view.SurfaceControl) parcel.readTypedObject(android.view.SurfaceControl.CREATOR);
                    android.window.ScreenCapture.ScreenCaptureListener screenCaptureListener2 = (android.window.ScreenCapture.ScreenCaptureListener) parcel.readTypedObject(android.window.ScreenCapture.ScreenCaptureListener.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean takeSurfaceControlScreenshot = takeSurfaceControlScreenshot(surfaceControl, screenCaptureListener2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(takeSurfaceControlScreenshot);
                    return true;
                case 9:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean clearWindowContentFrameStats = clearWindowContentFrameStats(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearWindowContentFrameStats);
                    return true;
                case 10:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.view.WindowContentFrameStats windowContentFrameStats = getWindowContentFrameStats(readInt4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(windowContentFrameStats, 1);
                    return true;
                case 11:
                    clearWindowAnimationFrameStats();
                    parcel2.writeNoException();
                    return true;
                case 12:
                    android.view.WindowAnimationFrameStats windowAnimationFrameStats = getWindowAnimationFrameStats();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(windowAnimationFrameStats, 1);
                    return true;
                case 13:
                    java.lang.String readString = parcel.readString();
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    android.os.ParcelFileDescriptor parcelFileDescriptor2 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    executeShellCommand(readString, parcelFileDescriptor, parcelFileDescriptor2);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    java.lang.String readString2 = parcel.readString();
                    java.lang.String readString3 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    grantRuntimePermission(readString2, readString3, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    java.lang.String readString4 = parcel.readString();
                    java.lang.String readString5 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    revokeRuntimePermission(readString4, readString5, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int readInt7 = parcel.readInt();
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    adoptShellPermissionIdentity(readInt7, createStringArray);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    dropShellPermissionIdentity();
                    parcel2.writeNoException();
                    return true;
                case 18:
                    shutdown();
                    return true;
                case 19:
                    java.lang.String readString6 = parcel.readString();
                    android.os.ParcelFileDescriptor parcelFileDescriptor3 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    android.os.ParcelFileDescriptor parcelFileDescriptor4 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    android.os.ParcelFileDescriptor parcelFileDescriptor5 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    executeShellCommandWithStderr(readString6, parcelFileDescriptor3, parcelFileDescriptor4, parcelFileDescriptor5);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    java.util.List<java.lang.String> adoptedShellPermissions = getAdoptedShellPermissions();
                    parcel2.writeNoException();
                    parcel2.writeStringList(adoptedShellPermissions);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.IUiAutomationConnection {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.IUiAutomationConnection.Stub.DESCRIPTOR;
            }

            @Override // android.app.IUiAutomationConnection
            public void connect(android.accessibilityservice.IAccessibilityServiceClient iAccessibilityServiceClient, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiAutomationConnection.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccessibilityServiceClient);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public void disconnect() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiAutomationConnection.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public boolean injectInputEvent(android.view.InputEvent inputEvent, boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiAutomationConnection.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(inputEvent, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public void injectInputEventToInputFilter(android.view.InputEvent inputEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiAutomationConnection.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(inputEvent, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public void syncInputTransactions(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiAutomationConnection.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public boolean setRotation(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiAutomationConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public boolean takeScreenshot(android.graphics.Rect rect, android.window.ScreenCapture.ScreenCaptureListener screenCaptureListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiAutomationConnection.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeTypedObject(screenCaptureListener, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public boolean takeSurfaceControlScreenshot(android.view.SurfaceControl surfaceControl, android.window.ScreenCapture.ScreenCaptureListener screenCaptureListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiAutomationConnection.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(surfaceControl, 0);
                    obtain.writeTypedObject(screenCaptureListener, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public boolean clearWindowContentFrameStats(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiAutomationConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public android.view.WindowContentFrameStats getWindowContentFrameStats(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiAutomationConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.view.WindowContentFrameStats) obtain2.readTypedObject(android.view.WindowContentFrameStats.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public void clearWindowAnimationFrameStats() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiAutomationConnection.Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public android.view.WindowAnimationFrameStats getWindowAnimationFrameStats() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiAutomationConnection.Stub.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.view.WindowAnimationFrameStats) obtain2.readTypedObject(android.view.WindowAnimationFrameStats.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public void executeShellCommand(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiAutomationConnection.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(parcelFileDescriptor2, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public void grantRuntimePermission(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiAutomationConnection.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public void revokeRuntimePermission(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiAutomationConnection.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public void adoptShellPermissionIdentity(int i, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiAutomationConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public void dropShellPermissionIdentity() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiAutomationConnection.Stub.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public void shutdown() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IUiAutomationConnection.Stub.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public void executeShellCommandWithStderr(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2, android.os.ParcelFileDescriptor parcelFileDescriptor3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiAutomationConnection.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(parcelFileDescriptor2, 0);
                    obtain.writeTypedObject(parcelFileDescriptor3, 0);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public java.util.List<java.lang.String> getAdoptedShellPermissions() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiAutomationConnection.Stub.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 19;
        }
    }
}
