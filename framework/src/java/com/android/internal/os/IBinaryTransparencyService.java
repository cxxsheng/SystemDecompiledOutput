package com.android.internal.os;

/* loaded from: classes4.dex */
public interface IBinaryTransparencyService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.os.IBinaryTransparencyService";

    java.util.List<com.android.internal.os.IBinaryTransparencyService.ApexInfo> collectAllApexInfo(boolean z) throws android.os.RemoteException;

    java.util.List<com.android.internal.os.IBinaryTransparencyService.AppInfo> collectAllSilentInstalledMbaInfo(android.os.Bundle bundle) throws android.os.RemoteException;

    java.util.List<com.android.internal.os.IBinaryTransparencyService.AppInfo> collectAllUpdatedPreloadInfo(android.os.Bundle bundle) throws android.os.RemoteException;

    java.lang.String getSignedImageInfo() throws android.os.RemoteException;

    void recordMeasurementsForAllPackages() throws android.os.RemoteException;

    public static class Default implements com.android.internal.os.IBinaryTransparencyService {
        @Override // com.android.internal.os.IBinaryTransparencyService
        public java.lang.String getSignedImageInfo() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.os.IBinaryTransparencyService
        public void recordMeasurementsForAllPackages() throws android.os.RemoteException {
        }

        @Override // com.android.internal.os.IBinaryTransparencyService
        public java.util.List<com.android.internal.os.IBinaryTransparencyService.ApexInfo> collectAllApexInfo(boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.os.IBinaryTransparencyService
        public java.util.List<com.android.internal.os.IBinaryTransparencyService.AppInfo> collectAllUpdatedPreloadInfo(android.os.Bundle bundle) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.os.IBinaryTransparencyService
        public java.util.List<com.android.internal.os.IBinaryTransparencyService.AppInfo> collectAllSilentInstalledMbaInfo(android.os.Bundle bundle) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.os.IBinaryTransparencyService {
        static final int TRANSACTION_collectAllApexInfo = 3;
        static final int TRANSACTION_collectAllSilentInstalledMbaInfo = 5;
        static final int TRANSACTION_collectAllUpdatedPreloadInfo = 4;
        static final int TRANSACTION_getSignedImageInfo = 1;
        static final int TRANSACTION_recordMeasurementsForAllPackages = 2;

        public Stub() {
            attachInterface(this, com.android.internal.os.IBinaryTransparencyService.DESCRIPTOR);
        }

        public static com.android.internal.os.IBinaryTransparencyService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.os.IBinaryTransparencyService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.os.IBinaryTransparencyService)) {
                return (com.android.internal.os.IBinaryTransparencyService) queryLocalInterface;
            }
            return new com.android.internal.os.IBinaryTransparencyService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getSignedImageInfo";
                case 2:
                    return "recordMeasurementsForAllPackages";
                case 3:
                    return "collectAllApexInfo";
                case 4:
                    return "collectAllUpdatedPreloadInfo";
                case 5:
                    return "collectAllSilentInstalledMbaInfo";
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
                parcel.enforceInterface(com.android.internal.os.IBinaryTransparencyService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.os.IBinaryTransparencyService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String signedImageInfo = getSignedImageInfo();
                    parcel2.writeNoException();
                    parcel2.writeString(signedImageInfo);
                    return true;
                case 2:
                    recordMeasurementsForAllPackages();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    java.util.List<com.android.internal.os.IBinaryTransparencyService.ApexInfo> collectAllApexInfo = collectAllApexInfo(readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(collectAllApexInfo, 1);
                    return true;
                case 4:
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.util.List<com.android.internal.os.IBinaryTransparencyService.AppInfo> collectAllUpdatedPreloadInfo = collectAllUpdatedPreloadInfo(bundle);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(collectAllUpdatedPreloadInfo, 1);
                    return true;
                case 5:
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.util.List<com.android.internal.os.IBinaryTransparencyService.AppInfo> collectAllSilentInstalledMbaInfo = collectAllSilentInstalledMbaInfo(bundle2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(collectAllSilentInstalledMbaInfo, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.os.IBinaryTransparencyService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.os.IBinaryTransparencyService.DESCRIPTOR;
            }

            @Override // com.android.internal.os.IBinaryTransparencyService
            public java.lang.String getSignedImageInfo() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.os.IBinaryTransparencyService.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.os.IBinaryTransparencyService
            public void recordMeasurementsForAllPackages() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.os.IBinaryTransparencyService.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.os.IBinaryTransparencyService
            public java.util.List<com.android.internal.os.IBinaryTransparencyService.ApexInfo> collectAllApexInfo(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.os.IBinaryTransparencyService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(com.android.internal.os.IBinaryTransparencyService.ApexInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.os.IBinaryTransparencyService
            public java.util.List<com.android.internal.os.IBinaryTransparencyService.AppInfo> collectAllUpdatedPreloadInfo(android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.os.IBinaryTransparencyService.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(com.android.internal.os.IBinaryTransparencyService.AppInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.os.IBinaryTransparencyService
            public java.util.List<com.android.internal.os.IBinaryTransparencyService.AppInfo> collectAllSilentInstalledMbaInfo(android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.os.IBinaryTransparencyService.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(com.android.internal.os.IBinaryTransparencyService.AppInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }

    public static class ApexInfo implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<com.android.internal.os.IBinaryTransparencyService.ApexInfo> CREATOR = new android.os.Parcelable.Creator<com.android.internal.os.IBinaryTransparencyService.ApexInfo>() { // from class: com.android.internal.os.IBinaryTransparencyService.ApexInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public com.android.internal.os.IBinaryTransparencyService.ApexInfo createFromParcel(android.os.Parcel parcel) {
                com.android.internal.os.IBinaryTransparencyService.ApexInfo apexInfo = new com.android.internal.os.IBinaryTransparencyService.ApexInfo();
                apexInfo.readFromParcel(parcel);
                return apexInfo;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public com.android.internal.os.IBinaryTransparencyService.ApexInfo[] newArray(int i) {
                return new com.android.internal.os.IBinaryTransparencyService.ApexInfo[i];
            }
        };
        public byte[] digest;
        public java.lang.String moduleName;
        public java.lang.String packageName;
        public java.lang.String[] signerDigests;
        public long longVersion = 0;
        public int digestAlgorithm = 0;

        @Override // android.os.Parcelable
        public final void writeToParcel(android.os.Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeString(this.packageName);
            parcel.writeLong(this.longVersion);
            parcel.writeByteArray(this.digest);
            parcel.writeInt(this.digestAlgorithm);
            parcel.writeStringArray(this.signerDigests);
            parcel.writeString(this.moduleName);
            int dataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(dataPosition);
            parcel.writeInt(dataPosition2 - dataPosition);
            parcel.setDataPosition(dataPosition2);
        }

        public final void readFromParcel(android.os.Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new android.os.BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.packageName = parcel.readString();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.longVersion = parcel.readLong();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.digest = parcel.createByteArray();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.digestAlgorithm = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.signerDigests = parcel.createStringArray();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                } else {
                    this.moduleName = parcel.readString();
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                }
            } catch (java.lang.Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }

    public static class AppInfo implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<com.android.internal.os.IBinaryTransparencyService.AppInfo> CREATOR = new android.os.Parcelable.Creator<com.android.internal.os.IBinaryTransparencyService.AppInfo>() { // from class: com.android.internal.os.IBinaryTransparencyService.AppInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public com.android.internal.os.IBinaryTransparencyService.AppInfo createFromParcel(android.os.Parcel parcel) {
                com.android.internal.os.IBinaryTransparencyService.AppInfo appInfo = new com.android.internal.os.IBinaryTransparencyService.AppInfo();
                appInfo.readFromParcel(parcel);
                return appInfo;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public com.android.internal.os.IBinaryTransparencyService.AppInfo[] newArray(int i) {
                return new com.android.internal.os.IBinaryTransparencyService.AppInfo[i];
            }
        };
        public byte[] digest;
        public java.lang.String initiator;
        public java.lang.String[] initiatorSignerDigests;
        public java.lang.String installer;
        public java.lang.String originator;
        public java.lang.String packageName;
        public java.lang.String[] signerDigests;
        public java.lang.String splitName;
        public long longVersion = 0;
        public int digestAlgorithm = 0;
        public int mbaStatus = 0;

        @Override // android.os.Parcelable
        public final void writeToParcel(android.os.Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeString(this.packageName);
            parcel.writeLong(this.longVersion);
            parcel.writeString(this.splitName);
            parcel.writeByteArray(this.digest);
            parcel.writeInt(this.digestAlgorithm);
            parcel.writeStringArray(this.signerDigests);
            parcel.writeInt(this.mbaStatus);
            parcel.writeString(this.initiator);
            parcel.writeStringArray(this.initiatorSignerDigests);
            parcel.writeString(this.installer);
            parcel.writeString(this.originator);
            int dataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(dataPosition);
            parcel.writeInt(dataPosition2 - dataPosition);
            parcel.setDataPosition(dataPosition2);
        }

        public final void readFromParcel(android.os.Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new android.os.BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.packageName = parcel.readString();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.longVersion = parcel.readLong();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.splitName = parcel.readString();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.digest = parcel.createByteArray();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.digestAlgorithm = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.signerDigests = parcel.createStringArray();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.mbaStatus = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.initiator = parcel.readString();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.initiatorSignerDigests = parcel.createStringArray();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.installer = parcel.readString();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                } else {
                    this.originator = parcel.readString();
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                }
            } catch (java.lang.Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }
}
