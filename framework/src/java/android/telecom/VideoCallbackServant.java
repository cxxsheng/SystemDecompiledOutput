package android.telecom;

/* loaded from: classes3.dex */
final class VideoCallbackServant {
    private static final int MSG_CHANGE_CALL_DATA_USAGE = 4;
    private static final int MSG_CHANGE_CAMERA_CAPABILITIES = 5;
    private static final int MSG_CHANGE_PEER_DIMENSIONS = 3;
    private static final int MSG_CHANGE_VIDEO_QUALITY = 6;
    private static final int MSG_HANDLE_CALL_SESSION_EVENT = 2;
    private static final int MSG_RECEIVE_SESSION_MODIFY_REQUEST = 0;
    private static final int MSG_RECEIVE_SESSION_MODIFY_RESPONSE = 1;
    private final com.android.internal.telecom.IVideoCallback mDelegate;
    private final android.os.Handler mHandler = new android.os.Handler() { // from class: android.telecom.VideoCallbackServant.1
        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            try {
                internalHandleMessage(message);
            } catch (android.os.RemoteException e) {
            }
        }

        private void internalHandleMessage(android.os.Message message) throws android.os.RemoteException {
            com.android.internal.os.SomeArgs someArgs;
            switch (message.what) {
                case 0:
                    android.telecom.VideoCallbackServant.this.mDelegate.receiveSessionModifyRequest((android.telecom.VideoProfile) message.obj);
                    return;
                case 1:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.VideoCallbackServant.this.mDelegate.receiveSessionModifyResponse(someArgs.argi1, (android.telecom.VideoProfile) someArgs.arg1, (android.telecom.VideoProfile) someArgs.arg2);
                        return;
                    } finally {
                    }
                case 2:
                    try {
                        android.telecom.VideoCallbackServant.this.mDelegate.handleCallSessionEvent(((com.android.internal.os.SomeArgs) message.obj).argi1);
                        return;
                    } finally {
                    }
                case 3:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.VideoCallbackServant.this.mDelegate.changePeerDimensions(someArgs.argi1, someArgs.argi2);
                        return;
                    } finally {
                    }
                case 4:
                    try {
                        android.telecom.VideoCallbackServant.this.mDelegate.changeCallDataUsage(((java.lang.Long) ((com.android.internal.os.SomeArgs) message.obj).arg1).longValue());
                        return;
                    } finally {
                    }
                case 5:
                    android.telecom.VideoCallbackServant.this.mDelegate.changeCameraCapabilities((android.telecom.VideoProfile.CameraCapabilities) message.obj);
                    return;
                case 6:
                    android.telecom.VideoCallbackServant.this.mDelegate.changeVideoQuality(message.arg1);
                    return;
                default:
                    return;
            }
        }
    };
    private final com.android.internal.telecom.IVideoCallback mStub = new com.android.internal.telecom.IVideoCallback.Stub() { // from class: android.telecom.VideoCallbackServant.2
        @Override // com.android.internal.telecom.IVideoCallback
        public void receiveSessionModifyRequest(android.telecom.VideoProfile videoProfile) throws android.os.RemoteException {
            android.telecom.VideoCallbackServant.this.mHandler.obtainMessage(0, videoProfile).sendToTarget();
        }

        @Override // com.android.internal.telecom.IVideoCallback
        public void receiveSessionModifyResponse(int i, android.telecom.VideoProfile videoProfile, android.telecom.VideoProfile videoProfile2) throws android.os.RemoteException {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.argi1 = i;
            obtain.arg1 = videoProfile;
            obtain.arg2 = videoProfile2;
            android.telecom.VideoCallbackServant.this.mHandler.obtainMessage(1, obtain).sendToTarget();
        }

        @Override // com.android.internal.telecom.IVideoCallback
        public void handleCallSessionEvent(int i) throws android.os.RemoteException {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.argi1 = i;
            android.telecom.VideoCallbackServant.this.mHandler.obtainMessage(2, obtain).sendToTarget();
        }

        @Override // com.android.internal.telecom.IVideoCallback
        public void changePeerDimensions(int i, int i2) throws android.os.RemoteException {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.argi1 = i;
            obtain.argi2 = i2;
            android.telecom.VideoCallbackServant.this.mHandler.obtainMessage(3, obtain).sendToTarget();
        }

        @Override // com.android.internal.telecom.IVideoCallback
        public void changeCallDataUsage(long j) throws android.os.RemoteException {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = java.lang.Long.valueOf(j);
            android.telecom.VideoCallbackServant.this.mHandler.obtainMessage(4, obtain).sendToTarget();
        }

        @Override // com.android.internal.telecom.IVideoCallback
        public void changeCameraCapabilities(android.telecom.VideoProfile.CameraCapabilities cameraCapabilities) throws android.os.RemoteException {
            android.telecom.VideoCallbackServant.this.mHandler.obtainMessage(5, cameraCapabilities).sendToTarget();
        }

        @Override // com.android.internal.telecom.IVideoCallback
        public void changeVideoQuality(int i) throws android.os.RemoteException {
            android.telecom.VideoCallbackServant.this.mHandler.obtainMessage(6, i, 0).sendToTarget();
        }
    };

    public VideoCallbackServant(com.android.internal.telecom.IVideoCallback iVideoCallback) {
        this.mDelegate = iVideoCallback;
    }

    public com.android.internal.telecom.IVideoCallback getStub() {
        return this.mStub;
    }
}
