package android.view;

/* loaded from: classes4.dex */
public interface IRecentsAnimationRunner extends android.os.IInterface {
    void onAnimationCanceled(int[] iArr, android.window.TaskSnapshot[] taskSnapshotArr) throws android.os.RemoteException;

    void onAnimationStart(android.view.IRecentsAnimationController iRecentsAnimationController, android.view.RemoteAnimationTarget[] remoteAnimationTargetArr, android.view.RemoteAnimationTarget[] remoteAnimationTargetArr2, android.graphics.Rect rect, android.graphics.Rect rect2, android.os.Bundle bundle) throws android.os.RemoteException;

    void onTasksAppeared(android.view.RemoteAnimationTarget[] remoteAnimationTargetArr) throws android.os.RemoteException;

    public static class Default implements android.view.IRecentsAnimationRunner {
        @Override // android.view.IRecentsAnimationRunner
        public void onAnimationCanceled(int[] iArr, android.window.TaskSnapshot[] taskSnapshotArr) throws android.os.RemoteException {
        }

        @Override // android.view.IRecentsAnimationRunner
        public void onAnimationStart(android.view.IRecentsAnimationController iRecentsAnimationController, android.view.RemoteAnimationTarget[] remoteAnimationTargetArr, android.view.RemoteAnimationTarget[] remoteAnimationTargetArr2, android.graphics.Rect rect, android.graphics.Rect rect2, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.view.IRecentsAnimationRunner
        public void onTasksAppeared(android.view.RemoteAnimationTarget[] remoteAnimationTargetArr) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.IRecentsAnimationRunner {
        public static final java.lang.String DESCRIPTOR = "android.view.IRecentsAnimationRunner";
        static final int TRANSACTION_onAnimationCanceled = 2;
        static final int TRANSACTION_onAnimationStart = 3;
        static final int TRANSACTION_onTasksAppeared = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.view.IRecentsAnimationRunner asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.IRecentsAnimationRunner)) {
                return (android.view.IRecentsAnimationRunner) queryLocalInterface;
            }
            return new android.view.IRecentsAnimationRunner.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 2:
                    return "onAnimationCanceled";
                case 3:
                    return "onAnimationStart";
                case 4:
                    return "onTasksAppeared";
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
                case 2:
                    int[] createIntArray = parcel.createIntArray();
                    android.window.TaskSnapshot[] taskSnapshotArr = (android.window.TaskSnapshot[]) parcel.createTypedArray(android.window.TaskSnapshot.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAnimationCanceled(createIntArray, taskSnapshotArr);
                    return true;
                case 3:
                    android.view.IRecentsAnimationController asInterface = android.view.IRecentsAnimationController.Stub.asInterface(parcel.readStrongBinder());
                    android.view.RemoteAnimationTarget[] remoteAnimationTargetArr = (android.view.RemoteAnimationTarget[]) parcel.createTypedArray(android.view.RemoteAnimationTarget.CREATOR);
                    android.view.RemoteAnimationTarget[] remoteAnimationTargetArr2 = (android.view.RemoteAnimationTarget[]) parcel.createTypedArray(android.view.RemoteAnimationTarget.CREATOR);
                    android.graphics.Rect rect = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    android.graphics.Rect rect2 = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAnimationStart(asInterface, remoteAnimationTargetArr, remoteAnimationTargetArr2, rect, rect2, bundle);
                    return true;
                case 4:
                    android.view.RemoteAnimationTarget[] remoteAnimationTargetArr3 = (android.view.RemoteAnimationTarget[]) parcel.createTypedArray(android.view.RemoteAnimationTarget.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTasksAppeared(remoteAnimationTargetArr3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.IRecentsAnimationRunner {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.IRecentsAnimationRunner.Stub.DESCRIPTOR;
            }

            @Override // android.view.IRecentsAnimationRunner
            public void onAnimationCanceled(int[] iArr, android.window.TaskSnapshot[] taskSnapshotArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IRecentsAnimationRunner.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeTypedArray(taskSnapshotArr, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IRecentsAnimationRunner
            public void onAnimationStart(android.view.IRecentsAnimationController iRecentsAnimationController, android.view.RemoteAnimationTarget[] remoteAnimationTargetArr, android.view.RemoteAnimationTarget[] remoteAnimationTargetArr2, android.graphics.Rect rect, android.graphics.Rect rect2, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IRecentsAnimationRunner.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRecentsAnimationController);
                    obtain.writeTypedArray(remoteAnimationTargetArr, 0);
                    obtain.writeTypedArray(remoteAnimationTargetArr2, 0);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeTypedObject(rect2, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IRecentsAnimationRunner
            public void onTasksAppeared(android.view.RemoteAnimationTarget[] remoteAnimationTargetArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IRecentsAnimationRunner.Stub.DESCRIPTOR);
                    obtain.writeTypedArray(remoteAnimationTargetArr, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
