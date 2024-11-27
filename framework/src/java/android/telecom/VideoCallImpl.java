package android.telecom;

/* loaded from: classes3.dex */
public class VideoCallImpl extends android.telecom.InCallService.VideoCall {
    private final android.telecom.VideoCallImpl.VideoCallListenerBinder mBinder;
    private android.telecom.InCallService.VideoCall.Callback mCallback;
    private final java.lang.String mCallingPackageName;
    private android.os.Handler mHandler;
    private int mTargetSdkVersion;
    private final com.android.internal.telecom.IVideoProvider mVideoProvider;
    private int mVideoQuality = 0;
    private int mVideoState = 0;
    private android.os.IBinder.DeathRecipient mDeathRecipient = new android.os.IBinder.DeathRecipient() { // from class: android.telecom.VideoCallImpl.1
        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            try {
                android.telecom.VideoCallImpl.this.mVideoProvider.asBinder().unlinkToDeath(this, 0);
            } catch (java.util.NoSuchElementException e) {
            }
        }
    };

    private final class VideoCallListenerBinder extends com.android.internal.telecom.IVideoCallback.Stub {
        private VideoCallListenerBinder() {
        }

        @Override // com.android.internal.telecom.IVideoCallback
        public void receiveSessionModifyRequest(android.telecom.VideoProfile videoProfile) {
            if (android.telecom.VideoCallImpl.this.mHandler == null) {
                return;
            }
            android.telecom.VideoCallImpl.this.mHandler.obtainMessage(1, videoProfile).sendToTarget();
        }

        @Override // com.android.internal.telecom.IVideoCallback
        public void receiveSessionModifyResponse(int i, android.telecom.VideoProfile videoProfile, android.telecom.VideoProfile videoProfile2) {
            if (android.telecom.VideoCallImpl.this.mHandler == null) {
                return;
            }
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = java.lang.Integer.valueOf(i);
            obtain.arg2 = videoProfile;
            obtain.arg3 = videoProfile2;
            android.telecom.VideoCallImpl.this.mHandler.obtainMessage(2, obtain).sendToTarget();
        }

        @Override // com.android.internal.telecom.IVideoCallback
        public void handleCallSessionEvent(int i) {
            if (android.telecom.VideoCallImpl.this.mHandler == null) {
                return;
            }
            android.telecom.VideoCallImpl.this.mHandler.obtainMessage(3, java.lang.Integer.valueOf(i)).sendToTarget();
        }

        @Override // com.android.internal.telecom.IVideoCallback
        public void changePeerDimensions(int i, int i2) {
            if (android.telecom.VideoCallImpl.this.mHandler == null) {
                return;
            }
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = java.lang.Integer.valueOf(i);
            obtain.arg2 = java.lang.Integer.valueOf(i2);
            android.telecom.VideoCallImpl.this.mHandler.obtainMessage(4, obtain).sendToTarget();
        }

        @Override // com.android.internal.telecom.IVideoCallback
        public void changeVideoQuality(int i) {
            if (android.telecom.VideoCallImpl.this.mHandler == null) {
                return;
            }
            android.telecom.VideoCallImpl.this.mHandler.obtainMessage(7, i, 0).sendToTarget();
        }

        @Override // com.android.internal.telecom.IVideoCallback
        public void changeCallDataUsage(long j) {
            if (android.telecom.VideoCallImpl.this.mHandler == null) {
                return;
            }
            android.telecom.VideoCallImpl.this.mHandler.obtainMessage(5, java.lang.Long.valueOf(j)).sendToTarget();
        }

        @Override // com.android.internal.telecom.IVideoCallback
        public void changeCameraCapabilities(android.telecom.VideoProfile.CameraCapabilities cameraCapabilities) {
            if (android.telecom.VideoCallImpl.this.mHandler == null) {
                return;
            }
            android.telecom.VideoCallImpl.this.mHandler.obtainMessage(6, cameraCapabilities).sendToTarget();
        }
    }

    private final class MessageHandler extends android.os.Handler {
        private static final int MSG_CHANGE_CALL_DATA_USAGE = 5;
        private static final int MSG_CHANGE_CAMERA_CAPABILITIES = 6;
        private static final int MSG_CHANGE_PEER_DIMENSIONS = 4;
        private static final int MSG_CHANGE_VIDEO_QUALITY = 7;
        private static final int MSG_HANDLE_CALL_SESSION_EVENT = 3;
        private static final int MSG_RECEIVE_SESSION_MODIFY_REQUEST = 1;
        private static final int MSG_RECEIVE_SESSION_MODIFY_RESPONSE = 2;

        public MessageHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            com.android.internal.os.SomeArgs someArgs;
            if (android.telecom.VideoCallImpl.this.mCallback == null) {
                return;
            }
            switch (message.what) {
                case 1:
                    android.telecom.VideoCallImpl.this.mCallback.onSessionModifyRequestReceived((android.telecom.VideoProfile) message.obj);
                    return;
                case 2:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.VideoCallImpl.this.mCallback.onSessionModifyResponseReceived(((java.lang.Integer) someArgs.arg1).intValue(), (android.telecom.VideoProfile) someArgs.arg2, (android.telecom.VideoProfile) someArgs.arg3);
                        return;
                    } finally {
                    }
                case 3:
                    android.telecom.VideoCallImpl.this.mCallback.onCallSessionEvent(((java.lang.Integer) message.obj).intValue());
                    return;
                case 4:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.VideoCallImpl.this.mCallback.onPeerDimensionsChanged(((java.lang.Integer) someArgs.arg1).intValue(), ((java.lang.Integer) someArgs.arg2).intValue());
                        return;
                    } finally {
                    }
                case 5:
                    android.telecom.VideoCallImpl.this.mCallback.onCallDataUsageChanged(((java.lang.Long) message.obj).longValue());
                    return;
                case 6:
                    android.telecom.VideoCallImpl.this.mCallback.onCameraCapabilitiesChanged((android.telecom.VideoProfile.CameraCapabilities) message.obj);
                    return;
                case 7:
                    android.telecom.VideoCallImpl.this.mVideoQuality = message.arg1;
                    android.telecom.VideoCallImpl.this.mCallback.onVideoQualityChanged(message.arg1);
                    return;
                default:
                    return;
            }
        }
    }

    VideoCallImpl(com.android.internal.telecom.IVideoProvider iVideoProvider, java.lang.String str, int i) throws android.os.RemoteException {
        this.mVideoProvider = iVideoProvider;
        this.mVideoProvider.asBinder().linkToDeath(this.mDeathRecipient, 0);
        this.mBinder = new android.telecom.VideoCallImpl.VideoCallListenerBinder();
        this.mVideoProvider.addVideoCallback(this.mBinder);
        this.mCallingPackageName = str;
        setTargetSdkVersion(i);
    }

    public void setTargetSdkVersion(int i) {
        this.mTargetSdkVersion = i;
    }

    @Override // android.telecom.InCallService.VideoCall
    public void destroy() {
        unregisterCallback(this.mCallback);
        try {
            this.mVideoProvider.asBinder().unlinkToDeath(this.mDeathRecipient, 0);
        } catch (java.util.NoSuchElementException e) {
        }
    }

    @Override // android.telecom.InCallService.VideoCall
    public void registerCallback(android.telecom.InCallService.VideoCall.Callback callback) {
        registerCallback(callback, null);
    }

    @Override // android.telecom.InCallService.VideoCall
    public void registerCallback(android.telecom.InCallService.VideoCall.Callback callback, android.os.Handler handler) {
        this.mCallback = callback;
        if (handler == null) {
            this.mHandler = new android.telecom.VideoCallImpl.MessageHandler(android.os.Looper.getMainLooper());
        } else {
            this.mHandler = new android.telecom.VideoCallImpl.MessageHandler(handler.getLooper());
        }
    }

    @Override // android.telecom.InCallService.VideoCall
    public void unregisterCallback(android.telecom.InCallService.VideoCall.Callback callback) {
        if (callback != this.mCallback) {
            return;
        }
        this.mCallback = null;
        try {
            this.mVideoProvider.removeVideoCallback(this.mBinder);
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // android.telecom.InCallService.VideoCall
    public void setCamera(java.lang.String str) {
        try {
            android.telecom.Log.w(this, "setCamera: cameraId=%s, calling=%s", str, this.mCallingPackageName);
            this.mVideoProvider.setCamera(str, this.mCallingPackageName, this.mTargetSdkVersion);
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // android.telecom.InCallService.VideoCall
    public void setPreviewSurface(android.view.Surface surface) {
        try {
            this.mVideoProvider.setPreviewSurface(surface);
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // android.telecom.InCallService.VideoCall
    public void setDisplaySurface(android.view.Surface surface) {
        try {
            this.mVideoProvider.setDisplaySurface(surface);
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // android.telecom.InCallService.VideoCall
    public void setDeviceOrientation(int i) {
        try {
            this.mVideoProvider.setDeviceOrientation(i);
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // android.telecom.InCallService.VideoCall
    public void setZoom(float f) {
        try {
            this.mVideoProvider.setZoom(f);
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // android.telecom.InCallService.VideoCall
    public void sendSessionModifyRequest(android.telecom.VideoProfile videoProfile) {
        try {
            this.mVideoProvider.sendSessionModifyRequest(new android.telecom.VideoProfile(this.mVideoState, this.mVideoQuality), videoProfile);
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // android.telecom.InCallService.VideoCall
    public void sendSessionModifyResponse(android.telecom.VideoProfile videoProfile) {
        try {
            this.mVideoProvider.sendSessionModifyResponse(videoProfile);
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // android.telecom.InCallService.VideoCall
    public void requestCameraCapabilities() {
        try {
            this.mVideoProvider.requestCameraCapabilities();
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // android.telecom.InCallService.VideoCall
    public void requestCallDataUsage() {
        try {
            this.mVideoProvider.requestCallDataUsage();
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // android.telecom.InCallService.VideoCall
    public void setPauseImage(android.net.Uri uri) {
        try {
            this.mVideoProvider.setPauseImage(uri);
        } catch (android.os.RemoteException e) {
        }
    }

    public void setVideoState(int i) {
        this.mVideoState = i;
    }

    public com.android.internal.telecom.IVideoProvider getVideoProvider() {
        return this.mVideoProvider;
    }
}
