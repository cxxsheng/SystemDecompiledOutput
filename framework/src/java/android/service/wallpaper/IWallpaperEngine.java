package android.service.wallpaper;

/* loaded from: classes3.dex */
public interface IWallpaperEngine extends android.os.IInterface {
    void addLocalColorsAreas(java.util.List<android.graphics.RectF> list) throws android.os.RemoteException;

    void applyDimming(float f) throws android.os.RemoteException;

    void destroy() throws android.os.RemoteException;

    void dispatchPointer(android.view.MotionEvent motionEvent) throws android.os.RemoteException;

    void dispatchWallpaperCommand(java.lang.String str, int i, int i2, int i3, android.os.Bundle bundle) throws android.os.RemoteException;

    android.view.SurfaceControl mirrorSurfaceControl() throws android.os.RemoteException;

    void onScreenTurnedOn() throws android.os.RemoteException;

    void onScreenTurningOn() throws android.os.RemoteException;

    void removeLocalColorsAreas(java.util.List<android.graphics.RectF> list) throws android.os.RemoteException;

    void requestWallpaperColors() throws android.os.RemoteException;

    void resizePreview(android.graphics.Rect rect) throws android.os.RemoteException;

    void setDesiredSize(int i, int i2) throws android.os.RemoteException;

    void setDisplayPadding(android.graphics.Rect rect) throws android.os.RemoteException;

    void setInAmbientMode(boolean z, long j) throws android.os.RemoteException;

    void setVisibility(boolean z) throws android.os.RemoteException;

    void setWallpaperFlags(int i) throws android.os.RemoteException;

    void setZoomOut(float f) throws android.os.RemoteException;

    public static class Default implements android.service.wallpaper.IWallpaperEngine {
        @Override // android.service.wallpaper.IWallpaperEngine
        public void setDesiredSize(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setDisplayPadding(android.graphics.Rect rect) throws android.os.RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setVisibility(boolean z) throws android.os.RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void onScreenTurningOn() throws android.os.RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void onScreenTurnedOn() throws android.os.RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setInAmbientMode(boolean z, long j) throws android.os.RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void dispatchPointer(android.view.MotionEvent motionEvent) throws android.os.RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void dispatchWallpaperCommand(java.lang.String str, int i, int i2, int i3, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void requestWallpaperColors() throws android.os.RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void destroy() throws android.os.RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setZoomOut(float f) throws android.os.RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void resizePreview(android.graphics.Rect rect) throws android.os.RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void removeLocalColorsAreas(java.util.List<android.graphics.RectF> list) throws android.os.RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void addLocalColorsAreas(java.util.List<android.graphics.RectF> list) throws android.os.RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public android.view.SurfaceControl mirrorSurfaceControl() throws android.os.RemoteException {
            return null;
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void applyDimming(float f) throws android.os.RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setWallpaperFlags(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.wallpaper.IWallpaperEngine {
        public static final java.lang.String DESCRIPTOR = "android.service.wallpaper.IWallpaperEngine";
        static final int TRANSACTION_addLocalColorsAreas = 14;
        static final int TRANSACTION_applyDimming = 16;
        static final int TRANSACTION_destroy = 10;
        static final int TRANSACTION_dispatchPointer = 7;
        static final int TRANSACTION_dispatchWallpaperCommand = 8;
        static final int TRANSACTION_mirrorSurfaceControl = 15;
        static final int TRANSACTION_onScreenTurnedOn = 5;
        static final int TRANSACTION_onScreenTurningOn = 4;
        static final int TRANSACTION_removeLocalColorsAreas = 13;
        static final int TRANSACTION_requestWallpaperColors = 9;
        static final int TRANSACTION_resizePreview = 12;
        static final int TRANSACTION_setDesiredSize = 1;
        static final int TRANSACTION_setDisplayPadding = 2;
        static final int TRANSACTION_setInAmbientMode = 6;
        static final int TRANSACTION_setVisibility = 3;
        static final int TRANSACTION_setWallpaperFlags = 17;
        static final int TRANSACTION_setZoomOut = 11;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.service.wallpaper.IWallpaperEngine asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.wallpaper.IWallpaperEngine)) {
                return (android.service.wallpaper.IWallpaperEngine) queryLocalInterface;
            }
            return new android.service.wallpaper.IWallpaperEngine.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setDesiredSize";
                case 2:
                    return "setDisplayPadding";
                case 3:
                    return "setVisibility";
                case 4:
                    return "onScreenTurningOn";
                case 5:
                    return "onScreenTurnedOn";
                case 6:
                    return "setInAmbientMode";
                case 7:
                    return "dispatchPointer";
                case 8:
                    return "dispatchWallpaperCommand";
                case 9:
                    return "requestWallpaperColors";
                case 10:
                    return "destroy";
                case 11:
                    return "setZoomOut";
                case 12:
                    return "resizePreview";
                case 13:
                    return "removeLocalColorsAreas";
                case 14:
                    return "addLocalColorsAreas";
                case 15:
                    return "mirrorSurfaceControl";
                case 16:
                    return "applyDimming";
                case 17:
                    return "setWallpaperFlags";
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
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDesiredSize(readInt, readInt2);
                    return true;
                case 2:
                    android.graphics.Rect rect = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDisplayPadding(rect);
                    return true;
                case 3:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setVisibility(readBoolean);
                    return true;
                case 4:
                    onScreenTurningOn();
                    return true;
                case 5:
                    onScreenTurnedOn();
                    return true;
                case 6:
                    boolean readBoolean2 = parcel.readBoolean();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setInAmbientMode(readBoolean2, readLong);
                    return true;
                case 7:
                    android.view.MotionEvent motionEvent = (android.view.MotionEvent) parcel.readTypedObject(android.view.MotionEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    dispatchPointer(motionEvent);
                    return true;
                case 8:
                    java.lang.String readString = parcel.readString();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    dispatchWallpaperCommand(readString, readInt3, readInt4, readInt5, bundle);
                    return true;
                case 9:
                    requestWallpaperColors();
                    return true;
                case 10:
                    destroy();
                    return true;
                case 11:
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setZoomOut(readFloat);
                    return true;
                case 12:
                    android.graphics.Rect rect2 = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    resizePreview(rect2);
                    return true;
                case 13:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.graphics.RectF.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeLocalColorsAreas(createTypedArrayList);
                    return true;
                case 14:
                    java.util.ArrayList createTypedArrayList2 = parcel.createTypedArrayList(android.graphics.RectF.CREATOR);
                    parcel.enforceNoDataAvail();
                    addLocalColorsAreas(createTypedArrayList2);
                    return true;
                case 15:
                    android.view.SurfaceControl mirrorSurfaceControl = mirrorSurfaceControl();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(mirrorSurfaceControl, 1);
                    return true;
                case 16:
                    float readFloat2 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    applyDimming(readFloat2);
                    return true;
                case 17:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setWallpaperFlags(readInt6);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.wallpaper.IWallpaperEngine {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.wallpaper.IWallpaperEngine.Stub.DESCRIPTOR;
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void setDesiredSize(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.wallpaper.IWallpaperEngine.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void setDisplayPadding(android.graphics.Rect rect) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.wallpaper.IWallpaperEngine.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void setVisibility(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.wallpaper.IWallpaperEngine.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void onScreenTurningOn() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.wallpaper.IWallpaperEngine.Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void onScreenTurnedOn() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.wallpaper.IWallpaperEngine.Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void setInAmbientMode(boolean z, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.wallpaper.IWallpaperEngine.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void dispatchPointer(android.view.MotionEvent motionEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.wallpaper.IWallpaperEngine.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(motionEvent, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void dispatchWallpaperCommand(java.lang.String str, int i, int i2, int i3, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.wallpaper.IWallpaperEngine.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void requestWallpaperColors() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.wallpaper.IWallpaperEngine.Stub.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void destroy() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.wallpaper.IWallpaperEngine.Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void setZoomOut(float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.wallpaper.IWallpaperEngine.Stub.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void resizePreview(android.graphics.Rect rect) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.wallpaper.IWallpaperEngine.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void removeLocalColorsAreas(java.util.List<android.graphics.RectF> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.wallpaper.IWallpaperEngine.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void addLocalColorsAreas(java.util.List<android.graphics.RectF> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.wallpaper.IWallpaperEngine.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public android.view.SurfaceControl mirrorSurfaceControl() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.wallpaper.IWallpaperEngine.Stub.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.view.SurfaceControl) obtain2.readTypedObject(android.view.SurfaceControl.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void applyDimming(float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.wallpaper.IWallpaperEngine.Stub.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void setWallpaperFlags(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.wallpaper.IWallpaperEngine.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16;
        }
    }
}
