package android.media.projection;

/* loaded from: classes2.dex */
public interface IMediaProjection extends android.os.IInterface {
    int applyVirtualDisplayFlags(int i) throws android.os.RemoteException;

    boolean canProjectAudio() throws android.os.RemoteException;

    boolean canProjectSecureVideo() throws android.os.RemoteException;

    boolean canProjectVideo() throws android.os.RemoteException;

    android.app.ActivityOptions.LaunchCookie getLaunchCookie() throws android.os.RemoteException;

    boolean isValid() throws android.os.RemoteException;

    void notifyVirtualDisplayCreated(int i) throws android.os.RemoteException;

    void registerCallback(android.media.projection.IMediaProjectionCallback iMediaProjectionCallback) throws android.os.RemoteException;

    void setLaunchCookie(android.app.ActivityOptions.LaunchCookie launchCookie) throws android.os.RemoteException;

    void start(android.media.projection.IMediaProjectionCallback iMediaProjectionCallback) throws android.os.RemoteException;

    void stop() throws android.os.RemoteException;

    void unregisterCallback(android.media.projection.IMediaProjectionCallback iMediaProjectionCallback) throws android.os.RemoteException;

    public static class Default implements android.media.projection.IMediaProjection {
        @Override // android.media.projection.IMediaProjection
        public void start(android.media.projection.IMediaProjectionCallback iMediaProjectionCallback) throws android.os.RemoteException {
        }

        @Override // android.media.projection.IMediaProjection
        public void stop() throws android.os.RemoteException {
        }

        @Override // android.media.projection.IMediaProjection
        public boolean canProjectAudio() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.projection.IMediaProjection
        public boolean canProjectVideo() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.projection.IMediaProjection
        public boolean canProjectSecureVideo() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.projection.IMediaProjection
        public int applyVirtualDisplayFlags(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.projection.IMediaProjection
        public void registerCallback(android.media.projection.IMediaProjectionCallback iMediaProjectionCallback) throws android.os.RemoteException {
        }

        @Override // android.media.projection.IMediaProjection
        public void unregisterCallback(android.media.projection.IMediaProjectionCallback iMediaProjectionCallback) throws android.os.RemoteException {
        }

        @Override // android.media.projection.IMediaProjection
        public android.app.ActivityOptions.LaunchCookie getLaunchCookie() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.projection.IMediaProjection
        public void setLaunchCookie(android.app.ActivityOptions.LaunchCookie launchCookie) throws android.os.RemoteException {
        }

        @Override // android.media.projection.IMediaProjection
        public boolean isValid() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.projection.IMediaProjection
        public void notifyVirtualDisplayCreated(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.projection.IMediaProjection {
        public static final java.lang.String DESCRIPTOR = "android.media.projection.IMediaProjection";
        static final int TRANSACTION_applyVirtualDisplayFlags = 6;
        static final int TRANSACTION_canProjectAudio = 3;
        static final int TRANSACTION_canProjectSecureVideo = 5;
        static final int TRANSACTION_canProjectVideo = 4;
        static final int TRANSACTION_getLaunchCookie = 9;
        static final int TRANSACTION_isValid = 11;
        static final int TRANSACTION_notifyVirtualDisplayCreated = 12;
        static final int TRANSACTION_registerCallback = 7;
        static final int TRANSACTION_setLaunchCookie = 10;
        static final int TRANSACTION_start = 1;
        static final int TRANSACTION_stop = 2;
        static final int TRANSACTION_unregisterCallback = 8;
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

        public static android.media.projection.IMediaProjection asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.projection.IMediaProjection)) {
                return (android.media.projection.IMediaProjection) queryLocalInterface;
            }
            return new android.media.projection.IMediaProjection.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "start";
                case 2:
                    return "stop";
                case 3:
                    return "canProjectAudio";
                case 4:
                    return "canProjectVideo";
                case 5:
                    return "canProjectSecureVideo";
                case 6:
                    return "applyVirtualDisplayFlags";
                case 7:
                    return "registerCallback";
                case 8:
                    return "unregisterCallback";
                case 9:
                    return "getLaunchCookie";
                case 10:
                    return "setLaunchCookie";
                case 11:
                    return "isValid";
                case 12:
                    return "notifyVirtualDisplayCreated";
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
                    android.media.projection.IMediaProjectionCallback asInterface = android.media.projection.IMediaProjectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    start(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    stop();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    boolean canProjectAudio = canProjectAudio();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canProjectAudio);
                    return true;
                case 4:
                    boolean canProjectVideo = canProjectVideo();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canProjectVideo);
                    return true;
                case 5:
                    boolean canProjectSecureVideo = canProjectSecureVideo();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canProjectSecureVideo);
                    return true;
                case 6:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int applyVirtualDisplayFlags = applyVirtualDisplayFlags(readInt);
                    parcel2.writeNoException();
                    parcel2.writeInt(applyVirtualDisplayFlags);
                    return true;
                case 7:
                    android.media.projection.IMediaProjectionCallback asInterface2 = android.media.projection.IMediaProjectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCallback(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    android.media.projection.IMediaProjectionCallback asInterface3 = android.media.projection.IMediaProjectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterCallback(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    android.app.ActivityOptions.LaunchCookie launchCookie = getLaunchCookie();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(launchCookie, 1);
                    return true;
                case 10:
                    android.app.ActivityOptions.LaunchCookie launchCookie2 = (android.app.ActivityOptions.LaunchCookie) parcel.readTypedObject(android.app.ActivityOptions.LaunchCookie.CREATOR);
                    parcel.enforceNoDataAvail();
                    setLaunchCookie(launchCookie2);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    boolean isValid = isValid();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isValid);
                    return true;
                case 12:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyVirtualDisplayCreated(readInt2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.projection.IMediaProjection {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.projection.IMediaProjection.Stub.DESCRIPTOR;
            }

            @Override // android.media.projection.IMediaProjection
            public void start(android.media.projection.IMediaProjectionCallback iMediaProjectionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjection.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaProjectionCallback);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public void stop() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjection.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public boolean canProjectAudio() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjection.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public boolean canProjectVideo() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjection.Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public boolean canProjectSecureVideo() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjection.Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public int applyVirtualDisplayFlags(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public void registerCallback(android.media.projection.IMediaProjectionCallback iMediaProjectionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjection.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaProjectionCallback);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public void unregisterCallback(android.media.projection.IMediaProjectionCallback iMediaProjectionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjection.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaProjectionCallback);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public android.app.ActivityOptions.LaunchCookie getLaunchCookie() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjection.Stub.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.ActivityOptions.LaunchCookie) obtain2.readTypedObject(android.app.ActivityOptions.LaunchCookie.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public void setLaunchCookie(android.app.ActivityOptions.LaunchCookie launchCookie) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjection.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(launchCookie, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public boolean isValid() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjection.Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public void notifyVirtualDisplayCreated(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void applyVirtualDisplayFlags_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void getLaunchCookie_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void setLaunchCookie_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void isValid_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void notifyVirtualDisplayCreated_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }
    }
}
