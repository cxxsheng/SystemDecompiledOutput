package android.media.projection;

/* loaded from: classes2.dex */
public interface IMediaProjectionManager extends android.os.IInterface {
    public static final java.lang.String EXTRA_PACKAGE_REUSING_GRANTED_CONSENT = "extra_media_projection_package_reusing_consent";
    public static final java.lang.String EXTRA_USER_REVIEW_GRANTED_CONSENT = "extra_media_projection_user_consent_required";

    android.media.projection.MediaProjectionInfo addCallback(android.media.projection.IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) throws android.os.RemoteException;

    android.media.projection.IMediaProjection createProjection(int i, java.lang.String str, int i2, boolean z) throws android.os.RemoteException;

    android.media.projection.MediaProjectionInfo getActiveProjectionInfo() throws android.os.RemoteException;

    android.media.projection.IMediaProjection getProjection(int i, java.lang.String str) throws android.os.RemoteException;

    boolean hasProjectionPermission(int i, java.lang.String str) throws android.os.RemoteException;

    boolean isCurrentProjection(android.media.projection.IMediaProjection iMediaProjection) throws android.os.RemoteException;

    void notifyActiveProjectionCapturedContentResized(int i, int i2) throws android.os.RemoteException;

    void notifyActiveProjectionCapturedContentVisibilityChanged(boolean z) throws android.os.RemoteException;

    void notifyAppSelectorDisplayed(int i) throws android.os.RemoteException;

    void notifyPermissionRequestCancelled(int i) throws android.os.RemoteException;

    void notifyPermissionRequestDisplayed(int i) throws android.os.RemoteException;

    void notifyPermissionRequestInitiated(int i, int i2) throws android.os.RemoteException;

    void notifyWindowingModeChanged(int i, int i2, int i3) throws android.os.RemoteException;

    void removeCallback(android.media.projection.IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) throws android.os.RemoteException;

    void requestConsentForInvalidProjection(android.media.projection.IMediaProjection iMediaProjection) throws android.os.RemoteException;

    boolean setContentRecordingSession(android.view.ContentRecordingSession contentRecordingSession, android.media.projection.IMediaProjection iMediaProjection) throws android.os.RemoteException;

    void setUserReviewGrantedConsentResult(int i, android.media.projection.IMediaProjection iMediaProjection) throws android.os.RemoteException;

    void stopActiveProjection() throws android.os.RemoteException;

    public static class Default implements android.media.projection.IMediaProjectionManager {
        @Override // android.media.projection.IMediaProjectionManager
        public boolean hasProjectionPermission(int i, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.projection.IMediaProjectionManager
        public android.media.projection.IMediaProjection createProjection(int i, java.lang.String str, int i2, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.projection.IMediaProjectionManager
        public android.media.projection.IMediaProjection getProjection(int i, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.projection.IMediaProjectionManager
        public boolean isCurrentProjection(android.media.projection.IMediaProjection iMediaProjection) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.projection.IMediaProjectionManager
        public void requestConsentForInvalidProjection(android.media.projection.IMediaProjection iMediaProjection) throws android.os.RemoteException {
        }

        @Override // android.media.projection.IMediaProjectionManager
        public android.media.projection.MediaProjectionInfo getActiveProjectionInfo() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.projection.IMediaProjectionManager
        public void stopActiveProjection() throws android.os.RemoteException {
        }

        @Override // android.media.projection.IMediaProjectionManager
        public void notifyActiveProjectionCapturedContentResized(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.projection.IMediaProjectionManager
        public void notifyActiveProjectionCapturedContentVisibilityChanged(boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.projection.IMediaProjectionManager
        public android.media.projection.MediaProjectionInfo addCallback(android.media.projection.IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.projection.IMediaProjectionManager
        public void removeCallback(android.media.projection.IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) throws android.os.RemoteException {
        }

        @Override // android.media.projection.IMediaProjectionManager
        public boolean setContentRecordingSession(android.view.ContentRecordingSession contentRecordingSession, android.media.projection.IMediaProjection iMediaProjection) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.projection.IMediaProjectionManager
        public void setUserReviewGrantedConsentResult(int i, android.media.projection.IMediaProjection iMediaProjection) throws android.os.RemoteException {
        }

        @Override // android.media.projection.IMediaProjectionManager
        public void notifyPermissionRequestInitiated(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.projection.IMediaProjectionManager
        public void notifyPermissionRequestDisplayed(int i) throws android.os.RemoteException {
        }

        @Override // android.media.projection.IMediaProjectionManager
        public void notifyPermissionRequestCancelled(int i) throws android.os.RemoteException {
        }

        @Override // android.media.projection.IMediaProjectionManager
        public void notifyAppSelectorDisplayed(int i) throws android.os.RemoteException {
        }

        @Override // android.media.projection.IMediaProjectionManager
        public void notifyWindowingModeChanged(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.projection.IMediaProjectionManager {
        public static final java.lang.String DESCRIPTOR = "android.media.projection.IMediaProjectionManager";
        static final int TRANSACTION_addCallback = 10;
        static final int TRANSACTION_createProjection = 2;
        static final int TRANSACTION_getActiveProjectionInfo = 6;
        static final int TRANSACTION_getProjection = 3;
        static final int TRANSACTION_hasProjectionPermission = 1;
        static final int TRANSACTION_isCurrentProjection = 4;
        static final int TRANSACTION_notifyActiveProjectionCapturedContentResized = 8;
        static final int TRANSACTION_notifyActiveProjectionCapturedContentVisibilityChanged = 9;
        static final int TRANSACTION_notifyAppSelectorDisplayed = 17;
        static final int TRANSACTION_notifyPermissionRequestCancelled = 16;
        static final int TRANSACTION_notifyPermissionRequestDisplayed = 15;
        static final int TRANSACTION_notifyPermissionRequestInitiated = 14;
        static final int TRANSACTION_notifyWindowingModeChanged = 18;
        static final int TRANSACTION_removeCallback = 11;
        static final int TRANSACTION_requestConsentForInvalidProjection = 5;
        static final int TRANSACTION_setContentRecordingSession = 12;
        static final int TRANSACTION_setUserReviewGrantedConsentResult = 13;
        static final int TRANSACTION_stopActiveProjection = 7;
        private final android.os.PermissionEnforcer mEnforcer;

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static android.media.projection.IMediaProjectionManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.projection.IMediaProjectionManager)) {
                return (android.media.projection.IMediaProjectionManager) queryLocalInterface;
            }
            return new android.media.projection.IMediaProjectionManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "hasProjectionPermission";
                case 2:
                    return "createProjection";
                case 3:
                    return "getProjection";
                case 4:
                    return "isCurrentProjection";
                case 5:
                    return "requestConsentForInvalidProjection";
                case 6:
                    return "getActiveProjectionInfo";
                case 7:
                    return "stopActiveProjection";
                case 8:
                    return "notifyActiveProjectionCapturedContentResized";
                case 9:
                    return "notifyActiveProjectionCapturedContentVisibilityChanged";
                case 10:
                    return "addCallback";
                case 11:
                    return "removeCallback";
                case 12:
                    return "setContentRecordingSession";
                case 13:
                    return "setUserReviewGrantedConsentResult";
                case 14:
                    return "notifyPermissionRequestInitiated";
                case 15:
                    return "notifyPermissionRequestDisplayed";
                case 16:
                    return "notifyPermissionRequestCancelled";
                case 17:
                    return "notifyAppSelectorDisplayed";
                case 18:
                    return "notifyWindowingModeChanged";
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
                    int readInt = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasProjectionPermission = hasProjectionPermission(readInt, readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasProjectionPermission);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.media.projection.IMediaProjection createProjection = createProjection(readInt2, readString2, readInt3, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createProjection);
                    return true;
                case 3:
                    int readInt4 = parcel.readInt();
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.media.projection.IMediaProjection projection = getProjection(readInt4, readString3);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(projection);
                    return true;
                case 4:
                    android.media.projection.IMediaProjection asInterface = android.media.projection.IMediaProjection.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean isCurrentProjection = isCurrentProjection(asInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCurrentProjection);
                    return true;
                case 5:
                    android.media.projection.IMediaProjection asInterface2 = android.media.projection.IMediaProjection.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestConsentForInvalidProjection(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    android.media.projection.MediaProjectionInfo activeProjectionInfo = getActiveProjectionInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(activeProjectionInfo, 1);
                    return true;
                case 7:
                    stopActiveProjection();
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyActiveProjectionCapturedContentResized(readInt5, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyActiveProjectionCapturedContentVisibilityChanged(readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    android.media.projection.IMediaProjectionWatcherCallback asInterface3 = android.media.projection.IMediaProjectionWatcherCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    android.media.projection.MediaProjectionInfo addCallback = addCallback(asInterface3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(addCallback, 1);
                    return true;
                case 11:
                    android.media.projection.IMediaProjectionWatcherCallback asInterface4 = android.media.projection.IMediaProjectionWatcherCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeCallback(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    android.view.ContentRecordingSession contentRecordingSession = (android.view.ContentRecordingSession) parcel.readTypedObject(android.view.ContentRecordingSession.CREATOR);
                    android.media.projection.IMediaProjection asInterface5 = android.media.projection.IMediaProjection.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean contentRecordingSession2 = setContentRecordingSession(contentRecordingSession, asInterface5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(contentRecordingSession2);
                    return true;
                case 13:
                    int readInt7 = parcel.readInt();
                    android.media.projection.IMediaProjection asInterface6 = android.media.projection.IMediaProjection.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setUserReviewGrantedConsentResult(readInt7, asInterface6);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyPermissionRequestInitiated(readInt8, readInt9);
                    return true;
                case 15:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyPermissionRequestDisplayed(readInt10);
                    return true;
                case 16:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyPermissionRequestCancelled(readInt11);
                    return true;
                case 17:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyAppSelectorDisplayed(readInt12);
                    return true;
                case 18:
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyWindowingModeChanged(readInt13, readInt14, readInt15);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.projection.IMediaProjectionManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.projection.IMediaProjectionManager.Stub.DESCRIPTOR;
            }

            @Override // android.media.projection.IMediaProjectionManager
            public boolean hasProjectionPermission(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjectionManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public android.media.projection.IMediaProjection createProjection(int i, java.lang.String str, int i2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjectionManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.media.projection.IMediaProjection.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public android.media.projection.IMediaProjection getProjection(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjectionManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.media.projection.IMediaProjection.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public boolean isCurrentProjection(android.media.projection.IMediaProjection iMediaProjection) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjectionManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaProjection);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void requestConsentForInvalidProjection(android.media.projection.IMediaProjection iMediaProjection) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjectionManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaProjection);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public android.media.projection.MediaProjectionInfo getActiveProjectionInfo() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjectionManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.projection.MediaProjectionInfo) obtain2.readTypedObject(android.media.projection.MediaProjectionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void stopActiveProjection() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjectionManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void notifyActiveProjectionCapturedContentResized(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjectionManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void notifyActiveProjectionCapturedContentVisibilityChanged(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjectionManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public android.media.projection.MediaProjectionInfo addCallback(android.media.projection.IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjectionManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaProjectionWatcherCallback);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.projection.MediaProjectionInfo) obtain2.readTypedObject(android.media.projection.MediaProjectionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void removeCallback(android.media.projection.IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjectionManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaProjectionWatcherCallback);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public boolean setContentRecordingSession(android.view.ContentRecordingSession contentRecordingSession, android.media.projection.IMediaProjection iMediaProjection) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjectionManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(contentRecordingSession, 0);
                    obtain.writeStrongInterface(iMediaProjection);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void setUserReviewGrantedConsentResult(int i, android.media.projection.IMediaProjection iMediaProjection) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjectionManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iMediaProjection);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void notifyPermissionRequestInitiated(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjectionManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void notifyPermissionRequestDisplayed(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjectionManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void notifyPermissionRequestCancelled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjectionManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void notifyAppSelectorDisplayed(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjectionManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void notifyWindowingModeChanged(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjectionManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void getProjection_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void isCurrentProjection_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void requestConsentForInvalidProjection_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void stopActiveProjection_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void notifyActiveProjectionCapturedContentResized_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void notifyActiveProjectionCapturedContentVisibilityChanged_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void addCallback_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void setContentRecordingSession_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void setUserReviewGrantedConsentResult_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void notifyPermissionRequestInitiated_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void notifyPermissionRequestDisplayed_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void notifyPermissionRequestCancelled_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void notifyAppSelectorDisplayed_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void notifyWindowingModeChanged_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 17;
        }
    }
}
