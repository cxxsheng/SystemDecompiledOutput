package android.media.metrics;

/* loaded from: classes2.dex */
public interface IMediaMetricsManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.metrics.IMediaMetricsManager";

    java.lang.String getBundleSessionId(int i) throws android.os.RemoteException;

    java.lang.String getEditingSessionId(int i) throws android.os.RemoteException;

    java.lang.String getPlaybackSessionId(int i) throws android.os.RemoteException;

    java.lang.String getRecordingSessionId(int i) throws android.os.RemoteException;

    java.lang.String getTranscodingSessionId(int i) throws android.os.RemoteException;

    void releaseSessionId(java.lang.String str, int i) throws android.os.RemoteException;

    void reportBundleMetrics(java.lang.String str, android.os.PersistableBundle persistableBundle, int i) throws android.os.RemoteException;

    void reportEditingEndedEvent(java.lang.String str, android.media.metrics.EditingEndedEvent editingEndedEvent, int i) throws android.os.RemoteException;

    void reportNetworkEvent(java.lang.String str, android.media.metrics.NetworkEvent networkEvent, int i) throws android.os.RemoteException;

    void reportPlaybackErrorEvent(java.lang.String str, android.media.metrics.PlaybackErrorEvent playbackErrorEvent, int i) throws android.os.RemoteException;

    void reportPlaybackMetrics(java.lang.String str, android.media.metrics.PlaybackMetrics playbackMetrics, int i) throws android.os.RemoteException;

    void reportPlaybackStateEvent(java.lang.String str, android.media.metrics.PlaybackStateEvent playbackStateEvent, int i) throws android.os.RemoteException;

    void reportTrackChangeEvent(java.lang.String str, android.media.metrics.TrackChangeEvent trackChangeEvent, int i) throws android.os.RemoteException;

    public static class Default implements android.media.metrics.IMediaMetricsManager {
        @Override // android.media.metrics.IMediaMetricsManager
        public void reportPlaybackMetrics(java.lang.String str, android.media.metrics.PlaybackMetrics playbackMetrics, int i) throws android.os.RemoteException {
        }

        @Override // android.media.metrics.IMediaMetricsManager
        public java.lang.String getPlaybackSessionId(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.metrics.IMediaMetricsManager
        public java.lang.String getRecordingSessionId(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.metrics.IMediaMetricsManager
        public void reportNetworkEvent(java.lang.String str, android.media.metrics.NetworkEvent networkEvent, int i) throws android.os.RemoteException {
        }

        @Override // android.media.metrics.IMediaMetricsManager
        public void reportPlaybackErrorEvent(java.lang.String str, android.media.metrics.PlaybackErrorEvent playbackErrorEvent, int i) throws android.os.RemoteException {
        }

        @Override // android.media.metrics.IMediaMetricsManager
        public void reportPlaybackStateEvent(java.lang.String str, android.media.metrics.PlaybackStateEvent playbackStateEvent, int i) throws android.os.RemoteException {
        }

        @Override // android.media.metrics.IMediaMetricsManager
        public void reportTrackChangeEvent(java.lang.String str, android.media.metrics.TrackChangeEvent trackChangeEvent, int i) throws android.os.RemoteException {
        }

        @Override // android.media.metrics.IMediaMetricsManager
        public void reportEditingEndedEvent(java.lang.String str, android.media.metrics.EditingEndedEvent editingEndedEvent, int i) throws android.os.RemoteException {
        }

        @Override // android.media.metrics.IMediaMetricsManager
        public java.lang.String getTranscodingSessionId(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.metrics.IMediaMetricsManager
        public java.lang.String getEditingSessionId(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.metrics.IMediaMetricsManager
        public java.lang.String getBundleSessionId(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.metrics.IMediaMetricsManager
        public void reportBundleMetrics(java.lang.String str, android.os.PersistableBundle persistableBundle, int i) throws android.os.RemoteException {
        }

        @Override // android.media.metrics.IMediaMetricsManager
        public void releaseSessionId(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.metrics.IMediaMetricsManager {
        static final int TRANSACTION_getBundleSessionId = 11;
        static final int TRANSACTION_getEditingSessionId = 10;
        static final int TRANSACTION_getPlaybackSessionId = 2;
        static final int TRANSACTION_getRecordingSessionId = 3;
        static final int TRANSACTION_getTranscodingSessionId = 9;
        static final int TRANSACTION_releaseSessionId = 13;
        static final int TRANSACTION_reportBundleMetrics = 12;
        static final int TRANSACTION_reportEditingEndedEvent = 8;
        static final int TRANSACTION_reportNetworkEvent = 4;
        static final int TRANSACTION_reportPlaybackErrorEvent = 5;
        static final int TRANSACTION_reportPlaybackMetrics = 1;
        static final int TRANSACTION_reportPlaybackStateEvent = 6;
        static final int TRANSACTION_reportTrackChangeEvent = 7;

        public Stub() {
            attachInterface(this, android.media.metrics.IMediaMetricsManager.DESCRIPTOR);
        }

        public static android.media.metrics.IMediaMetricsManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.metrics.IMediaMetricsManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.metrics.IMediaMetricsManager)) {
                return (android.media.metrics.IMediaMetricsManager) queryLocalInterface;
            }
            return new android.media.metrics.IMediaMetricsManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "reportPlaybackMetrics";
                case 2:
                    return "getPlaybackSessionId";
                case 3:
                    return "getRecordingSessionId";
                case 4:
                    return "reportNetworkEvent";
                case 5:
                    return "reportPlaybackErrorEvent";
                case 6:
                    return "reportPlaybackStateEvent";
                case 7:
                    return "reportTrackChangeEvent";
                case 8:
                    return "reportEditingEndedEvent";
                case 9:
                    return "getTranscodingSessionId";
                case 10:
                    return "getEditingSessionId";
                case 11:
                    return "getBundleSessionId";
                case 12:
                    return "reportBundleMetrics";
                case 13:
                    return "releaseSessionId";
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
                parcel.enforceInterface(android.media.metrics.IMediaMetricsManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.metrics.IMediaMetricsManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    android.media.metrics.PlaybackMetrics playbackMetrics = (android.media.metrics.PlaybackMetrics) parcel.readTypedObject(android.media.metrics.PlaybackMetrics.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportPlaybackMetrics(readString, playbackMetrics, readInt);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String playbackSessionId = getPlaybackSessionId(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeString(playbackSessionId);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String recordingSessionId = getRecordingSessionId(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeString(recordingSessionId);
                    return true;
                case 4:
                    java.lang.String readString2 = parcel.readString();
                    android.media.metrics.NetworkEvent networkEvent = (android.media.metrics.NetworkEvent) parcel.readTypedObject(android.media.metrics.NetworkEvent.CREATOR);
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportNetworkEvent(readString2, networkEvent, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    java.lang.String readString3 = parcel.readString();
                    android.media.metrics.PlaybackErrorEvent playbackErrorEvent = (android.media.metrics.PlaybackErrorEvent) parcel.readTypedObject(android.media.metrics.PlaybackErrorEvent.CREATOR);
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportPlaybackErrorEvent(readString3, playbackErrorEvent, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    java.lang.String readString4 = parcel.readString();
                    android.media.metrics.PlaybackStateEvent playbackStateEvent = (android.media.metrics.PlaybackStateEvent) parcel.readTypedObject(android.media.metrics.PlaybackStateEvent.CREATOR);
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportPlaybackStateEvent(readString4, playbackStateEvent, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    java.lang.String readString5 = parcel.readString();
                    android.media.metrics.TrackChangeEvent trackChangeEvent = (android.media.metrics.TrackChangeEvent) parcel.readTypedObject(android.media.metrics.TrackChangeEvent.CREATOR);
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportTrackChangeEvent(readString5, trackChangeEvent, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    java.lang.String readString6 = parcel.readString();
                    android.media.metrics.EditingEndedEvent editingEndedEvent = (android.media.metrics.EditingEndedEvent) parcel.readTypedObject(android.media.metrics.EditingEndedEvent.CREATOR);
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportEditingEndedEvent(readString6, editingEndedEvent, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String transcodingSessionId = getTranscodingSessionId(readInt9);
                    parcel2.writeNoException();
                    parcel2.writeString(transcodingSessionId);
                    return true;
                case 10:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String editingSessionId = getEditingSessionId(readInt10);
                    parcel2.writeNoException();
                    parcel2.writeString(editingSessionId);
                    return true;
                case 11:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String bundleSessionId = getBundleSessionId(readInt11);
                    parcel2.writeNoException();
                    parcel2.writeString(bundleSessionId);
                    return true;
                case 12:
                    java.lang.String readString7 = parcel.readString();
                    android.os.PersistableBundle persistableBundle = (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR);
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportBundleMetrics(readString7, persistableBundle, readInt12);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    java.lang.String readString8 = parcel.readString();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseSessionId(readString8, readInt13);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.metrics.IMediaMetricsManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.metrics.IMediaMetricsManager.DESCRIPTOR;
            }

            @Override // android.media.metrics.IMediaMetricsManager
            public void reportPlaybackMetrics(java.lang.String str, android.media.metrics.PlaybackMetrics playbackMetrics, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.metrics.IMediaMetricsManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(playbackMetrics, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.metrics.IMediaMetricsManager
            public java.lang.String getPlaybackSessionId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.metrics.IMediaMetricsManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.metrics.IMediaMetricsManager
            public java.lang.String getRecordingSessionId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.metrics.IMediaMetricsManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.metrics.IMediaMetricsManager
            public void reportNetworkEvent(java.lang.String str, android.media.metrics.NetworkEvent networkEvent, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.metrics.IMediaMetricsManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(networkEvent, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.metrics.IMediaMetricsManager
            public void reportPlaybackErrorEvent(java.lang.String str, android.media.metrics.PlaybackErrorEvent playbackErrorEvent, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.metrics.IMediaMetricsManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(playbackErrorEvent, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.metrics.IMediaMetricsManager
            public void reportPlaybackStateEvent(java.lang.String str, android.media.metrics.PlaybackStateEvent playbackStateEvent, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.metrics.IMediaMetricsManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(playbackStateEvent, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.metrics.IMediaMetricsManager
            public void reportTrackChangeEvent(java.lang.String str, android.media.metrics.TrackChangeEvent trackChangeEvent, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.metrics.IMediaMetricsManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(trackChangeEvent, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.metrics.IMediaMetricsManager
            public void reportEditingEndedEvent(java.lang.String str, android.media.metrics.EditingEndedEvent editingEndedEvent, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.metrics.IMediaMetricsManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(editingEndedEvent, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.metrics.IMediaMetricsManager
            public java.lang.String getTranscodingSessionId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.metrics.IMediaMetricsManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.metrics.IMediaMetricsManager
            public java.lang.String getEditingSessionId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.metrics.IMediaMetricsManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.metrics.IMediaMetricsManager
            public java.lang.String getBundleSessionId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.metrics.IMediaMetricsManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.metrics.IMediaMetricsManager
            public void reportBundleMetrics(java.lang.String str, android.os.PersistableBundle persistableBundle, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.metrics.IMediaMetricsManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(persistableBundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.metrics.IMediaMetricsManager
            public void releaseSessionId(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.metrics.IMediaMetricsManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 12;
        }
    }
}
