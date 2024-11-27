package android.view.accessibility;

/* loaded from: classes4.dex */
public interface IAccessibilityInteractionConnection extends android.os.IInterface {
    void attachAccessibilityOverlayToWindow(android.view.SurfaceControl surfaceControl, int i, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws android.os.RemoteException;

    void clearAccessibilityFocus() throws android.os.RemoteException;

    void findAccessibilityNodeInfoByAccessibilityId(long j, android.graphics.Region region, int i, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, android.view.MagnificationSpec magnificationSpec, float[] fArr, android.os.Bundle bundle) throws android.os.RemoteException;

    void findAccessibilityNodeInfosByText(long j, java.lang.String str, android.graphics.Region region, int i, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, android.view.MagnificationSpec magnificationSpec, float[] fArr) throws android.os.RemoteException;

    void findAccessibilityNodeInfosByViewId(long j, java.lang.String str, android.graphics.Region region, int i, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, android.view.MagnificationSpec magnificationSpec, float[] fArr) throws android.os.RemoteException;

    void findFocus(long j, int i, android.graphics.Region region, int i2, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2, android.view.MagnificationSpec magnificationSpec, float[] fArr) throws android.os.RemoteException;

    void focusSearch(long j, int i, android.graphics.Region region, int i2, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2, android.view.MagnificationSpec magnificationSpec, float[] fArr) throws android.os.RemoteException;

    void notifyOutsideTouch() throws android.os.RemoteException;

    void performAccessibilityAction(long j, int i, android.os.Bundle bundle, int i2, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2) throws android.os.RemoteException;

    void takeScreenshotOfWindow(int i, android.window.ScreenCapture.ScreenCaptureListener screenCaptureListener, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws android.os.RemoteException;

    public static class Default implements android.view.accessibility.IAccessibilityInteractionConnection {
        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void findAccessibilityNodeInfoByAccessibilityId(long j, android.graphics.Region region, int i, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, android.view.MagnificationSpec magnificationSpec, float[] fArr, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void findAccessibilityNodeInfosByViewId(long j, java.lang.String str, android.graphics.Region region, int i, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, android.view.MagnificationSpec magnificationSpec, float[] fArr) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void findAccessibilityNodeInfosByText(long j, java.lang.String str, android.graphics.Region region, int i, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, android.view.MagnificationSpec magnificationSpec, float[] fArr) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void findFocus(long j, int i, android.graphics.Region region, int i2, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2, android.view.MagnificationSpec magnificationSpec, float[] fArr) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void focusSearch(long j, int i, android.graphics.Region region, int i2, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2, android.view.MagnificationSpec magnificationSpec, float[] fArr) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void performAccessibilityAction(long j, int i, android.os.Bundle bundle, int i2, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void clearAccessibilityFocus() throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void notifyOutsideTouch() throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void takeScreenshotOfWindow(int i, android.window.ScreenCapture.ScreenCaptureListener screenCaptureListener, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void attachAccessibilityOverlayToWindow(android.view.SurfaceControl surfaceControl, int i, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.accessibility.IAccessibilityInteractionConnection {
        public static final java.lang.String DESCRIPTOR = "android.view.accessibility.IAccessibilityInteractionConnection";
        static final int TRANSACTION_attachAccessibilityOverlayToWindow = 10;
        static final int TRANSACTION_clearAccessibilityFocus = 7;
        static final int TRANSACTION_findAccessibilityNodeInfoByAccessibilityId = 1;
        static final int TRANSACTION_findAccessibilityNodeInfosByText = 3;
        static final int TRANSACTION_findAccessibilityNodeInfosByViewId = 2;
        static final int TRANSACTION_findFocus = 4;
        static final int TRANSACTION_focusSearch = 5;
        static final int TRANSACTION_notifyOutsideTouch = 8;
        static final int TRANSACTION_performAccessibilityAction = 6;
        static final int TRANSACTION_takeScreenshotOfWindow = 9;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.view.accessibility.IAccessibilityInteractionConnection asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.accessibility.IAccessibilityInteractionConnection)) {
                return (android.view.accessibility.IAccessibilityInteractionConnection) queryLocalInterface;
            }
            return new android.view.accessibility.IAccessibilityInteractionConnection.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "findAccessibilityNodeInfoByAccessibilityId";
                case 2:
                    return "findAccessibilityNodeInfosByViewId";
                case 3:
                    return "findAccessibilityNodeInfosByText";
                case 4:
                    return "findFocus";
                case 5:
                    return "focusSearch";
                case 6:
                    return "performAccessibilityAction";
                case 7:
                    return "clearAccessibilityFocus";
                case 8:
                    return "notifyOutsideTouch";
                case 9:
                    return "takeScreenshotOfWindow";
                case 10:
                    return "attachAccessibilityOverlayToWindow";
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
                    long readLong = parcel.readLong();
                    android.graphics.Region region = (android.graphics.Region) parcel.readTypedObject(android.graphics.Region.CREATOR);
                    int readInt = parcel.readInt();
                    android.view.accessibility.IAccessibilityInteractionConnectionCallback asInterface = android.view.accessibility.IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    long readLong2 = parcel.readLong();
                    android.view.MagnificationSpec magnificationSpec = (android.view.MagnificationSpec) parcel.readTypedObject(android.view.MagnificationSpec.CREATOR);
                    float[] createFloatArray = parcel.createFloatArray();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    findAccessibilityNodeInfoByAccessibilityId(readLong, region, readInt, asInterface, readInt2, readInt3, readLong2, magnificationSpec, createFloatArray, bundle);
                    return true;
                case 2:
                    long readLong3 = parcel.readLong();
                    java.lang.String readString = parcel.readString();
                    android.graphics.Region region2 = (android.graphics.Region) parcel.readTypedObject(android.graphics.Region.CREATOR);
                    int readInt4 = parcel.readInt();
                    android.view.accessibility.IAccessibilityInteractionConnectionCallback asInterface2 = android.view.accessibility.IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    long readLong4 = parcel.readLong();
                    android.view.MagnificationSpec magnificationSpec2 = (android.view.MagnificationSpec) parcel.readTypedObject(android.view.MagnificationSpec.CREATOR);
                    float[] createFloatArray2 = parcel.createFloatArray();
                    parcel.enforceNoDataAvail();
                    findAccessibilityNodeInfosByViewId(readLong3, readString, region2, readInt4, asInterface2, readInt5, readInt6, readLong4, magnificationSpec2, createFloatArray2);
                    return true;
                case 3:
                    long readLong5 = parcel.readLong();
                    java.lang.String readString2 = parcel.readString();
                    android.graphics.Region region3 = (android.graphics.Region) parcel.readTypedObject(android.graphics.Region.CREATOR);
                    int readInt7 = parcel.readInt();
                    android.view.accessibility.IAccessibilityInteractionConnectionCallback asInterface3 = android.view.accessibility.IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    long readLong6 = parcel.readLong();
                    android.view.MagnificationSpec magnificationSpec3 = (android.view.MagnificationSpec) parcel.readTypedObject(android.view.MagnificationSpec.CREATOR);
                    float[] createFloatArray3 = parcel.createFloatArray();
                    parcel.enforceNoDataAvail();
                    findAccessibilityNodeInfosByText(readLong5, readString2, region3, readInt7, asInterface3, readInt8, readInt9, readLong6, magnificationSpec3, createFloatArray3);
                    return true;
                case 4:
                    long readLong7 = parcel.readLong();
                    int readInt10 = parcel.readInt();
                    android.graphics.Region region4 = (android.graphics.Region) parcel.readTypedObject(android.graphics.Region.CREATOR);
                    int readInt11 = parcel.readInt();
                    android.view.accessibility.IAccessibilityInteractionConnectionCallback asInterface4 = android.view.accessibility.IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    long readLong8 = parcel.readLong();
                    android.view.MagnificationSpec magnificationSpec4 = (android.view.MagnificationSpec) parcel.readTypedObject(android.view.MagnificationSpec.CREATOR);
                    float[] createFloatArray4 = parcel.createFloatArray();
                    parcel.enforceNoDataAvail();
                    findFocus(readLong7, readInt10, region4, readInt11, asInterface4, readInt12, readInt13, readLong8, magnificationSpec4, createFloatArray4);
                    return true;
                case 5:
                    long readLong9 = parcel.readLong();
                    int readInt14 = parcel.readInt();
                    android.graphics.Region region5 = (android.graphics.Region) parcel.readTypedObject(android.graphics.Region.CREATOR);
                    int readInt15 = parcel.readInt();
                    android.view.accessibility.IAccessibilityInteractionConnectionCallback asInterface5 = android.view.accessibility.IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    long readLong10 = parcel.readLong();
                    android.view.MagnificationSpec magnificationSpec5 = (android.view.MagnificationSpec) parcel.readTypedObject(android.view.MagnificationSpec.CREATOR);
                    float[] createFloatArray5 = parcel.createFloatArray();
                    parcel.enforceNoDataAvail();
                    focusSearch(readLong9, readInt14, region5, readInt15, asInterface5, readInt16, readInt17, readLong10, magnificationSpec5, createFloatArray5);
                    return true;
                case 6:
                    long readLong11 = parcel.readLong();
                    int readInt18 = parcel.readInt();
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt19 = parcel.readInt();
                    android.view.accessibility.IAccessibilityInteractionConnectionCallback asInterface6 = android.view.accessibility.IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    long readLong12 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    performAccessibilityAction(readLong11, readInt18, bundle2, readInt19, asInterface6, readInt20, readInt21, readLong12);
                    return true;
                case 7:
                    clearAccessibilityFocus();
                    return true;
                case 8:
                    notifyOutsideTouch();
                    return true;
                case 9:
                    int readInt22 = parcel.readInt();
                    android.window.ScreenCapture.ScreenCaptureListener screenCaptureListener = (android.window.ScreenCapture.ScreenCaptureListener) parcel.readTypedObject(android.window.ScreenCapture.ScreenCaptureListener.CREATOR);
                    android.view.accessibility.IAccessibilityInteractionConnectionCallback asInterface7 = android.view.accessibility.IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    takeScreenshotOfWindow(readInt22, screenCaptureListener, asInterface7);
                    return true;
                case 10:
                    android.view.SurfaceControl surfaceControl = (android.view.SurfaceControl) parcel.readTypedObject(android.view.SurfaceControl.CREATOR);
                    int readInt23 = parcel.readInt();
                    android.view.accessibility.IAccessibilityInteractionConnectionCallback asInterface8 = android.view.accessibility.IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    attachAccessibilityOverlayToWindow(surfaceControl, readInt23, asInterface8);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.accessibility.IAccessibilityInteractionConnection {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.accessibility.IAccessibilityInteractionConnection.Stub.DESCRIPTOR;
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnection
            public void findAccessibilityNodeInfoByAccessibilityId(long j, android.graphics.Region region, int i, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, android.view.MagnificationSpec magnificationSpec, float[] fArr, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityInteractionConnection.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(region, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeLong(j2);
                    obtain.writeTypedObject(magnificationSpec, 0);
                    obtain.writeFloatArray(fArr);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnection
            public void findAccessibilityNodeInfosByViewId(long j, java.lang.String str, android.graphics.Region region, int i, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, android.view.MagnificationSpec magnificationSpec, float[] fArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityInteractionConnection.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeTypedObject(region, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeLong(j2);
                    obtain.writeTypedObject(magnificationSpec, 0);
                    obtain.writeFloatArray(fArr);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnection
            public void findAccessibilityNodeInfosByText(long j, java.lang.String str, android.graphics.Region region, int i, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, android.view.MagnificationSpec magnificationSpec, float[] fArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityInteractionConnection.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeTypedObject(region, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeLong(j2);
                    obtain.writeTypedObject(magnificationSpec, 0);
                    obtain.writeFloatArray(fArr);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnection
            public void findFocus(long j, int i, android.graphics.Region region, int i2, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2, android.view.MagnificationSpec magnificationSpec, float[] fArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityInteractionConnection.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(region, 0);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeLong(j2);
                    obtain.writeTypedObject(magnificationSpec, 0);
                    obtain.writeFloatArray(fArr);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnection
            public void focusSearch(long j, int i, android.graphics.Region region, int i2, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2, android.view.MagnificationSpec magnificationSpec, float[] fArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityInteractionConnection.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(region, 0);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeLong(j2);
                    obtain.writeTypedObject(magnificationSpec, 0);
                    obtain.writeFloatArray(fArr);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnection
            public void performAccessibilityAction(long j, int i, android.os.Bundle bundle, int i2, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityInteractionConnection.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeLong(j2);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnection
            public void clearAccessibilityFocus() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityInteractionConnection.Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnection
            public void notifyOutsideTouch() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityInteractionConnection.Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnection
            public void takeScreenshotOfWindow(int i, android.window.ScreenCapture.ScreenCaptureListener screenCaptureListener, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityInteractionConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(screenCaptureListener, 0);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnection
            public void attachAccessibilityOverlayToWindow(android.view.SurfaceControl surfaceControl, int i, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityInteractionConnection.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(surfaceControl, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 9;
        }
    }
}
