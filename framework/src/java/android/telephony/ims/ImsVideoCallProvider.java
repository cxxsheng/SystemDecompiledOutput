package android.telephony.ims;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class ImsVideoCallProvider {
    private static final int MSG_REQUEST_CALL_DATA_USAGE = 10;
    private static final int MSG_REQUEST_CAMERA_CAPABILITIES = 9;
    private static final int MSG_SEND_SESSION_MODIFY_REQUEST = 7;
    private static final int MSG_SEND_SESSION_MODIFY_RESPONSE = 8;
    private static final int MSG_SET_CALLBACK = 1;
    private static final int MSG_SET_CAMERA = 2;
    private static final int MSG_SET_DEVICE_ORIENTATION = 5;
    private static final int MSG_SET_DISPLAY_SURFACE = 4;
    private static final int MSG_SET_PAUSE_IMAGE = 11;
    private static final int MSG_SET_PREVIEW_SURFACE = 3;
    private static final int MSG_SET_ZOOM = 6;
    private com.android.ims.internal.IImsVideoCallCallback mCallback;
    private final android.os.Handler mProviderHandler = new android.os.Handler(android.os.Looper.getMainLooper()) { // from class: android.telephony.ims.ImsVideoCallProvider.1
        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            com.android.internal.os.SomeArgs someArgs;
            switch (message.what) {
                case 1:
                    android.telephony.ims.ImsVideoCallProvider.this.mCallback = (com.android.ims.internal.IImsVideoCallCallback) message.obj;
                    return;
                case 2:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telephony.ims.ImsVideoCallProvider.this.onSetCamera((java.lang.String) someArgs.arg1);
                        android.telephony.ims.ImsVideoCallProvider.this.onSetCamera((java.lang.String) someArgs.arg1, someArgs.argi1);
                        return;
                    } finally {
                    }
                case 3:
                    android.telephony.ims.ImsVideoCallProvider.this.onSetPreviewSurface((android.view.Surface) message.obj);
                    return;
                case 4:
                    android.telephony.ims.ImsVideoCallProvider.this.onSetDisplaySurface((android.view.Surface) message.obj);
                    return;
                case 5:
                    android.telephony.ims.ImsVideoCallProvider.this.onSetDeviceOrientation(message.arg1);
                    return;
                case 6:
                    android.telephony.ims.ImsVideoCallProvider.this.onSetZoom(((java.lang.Float) message.obj).floatValue());
                    return;
                case 7:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telephony.ims.ImsVideoCallProvider.this.onSendSessionModifyRequest((android.telecom.VideoProfile) someArgs.arg1, (android.telecom.VideoProfile) someArgs.arg2);
                        return;
                    } finally {
                    }
                case 8:
                    android.telephony.ims.ImsVideoCallProvider.this.onSendSessionModifyResponse((android.telecom.VideoProfile) message.obj);
                    return;
                case 9:
                    android.telephony.ims.ImsVideoCallProvider.this.onRequestCameraCapabilities();
                    return;
                case 10:
                    android.telephony.ims.ImsVideoCallProvider.this.onRequestCallDataUsage();
                    return;
                case 11:
                    android.telephony.ims.ImsVideoCallProvider.this.onSetPauseImage((android.net.Uri) message.obj);
                    return;
                default:
                    return;
            }
        }
    };
    private final android.telephony.ims.ImsVideoCallProvider.ImsVideoCallProviderBinder mBinder = new android.telephony.ims.ImsVideoCallProvider.ImsVideoCallProviderBinder();

    public abstract void onRequestCallDataUsage();

    public abstract void onRequestCameraCapabilities();

    public abstract void onSendSessionModifyRequest(android.telecom.VideoProfile videoProfile, android.telecom.VideoProfile videoProfile2);

    public abstract void onSendSessionModifyResponse(android.telecom.VideoProfile videoProfile);

    public abstract void onSetCamera(java.lang.String str);

    public abstract void onSetDeviceOrientation(int i);

    public abstract void onSetDisplaySurface(android.view.Surface surface);

    public abstract void onSetPauseImage(android.net.Uri uri);

    public abstract void onSetPreviewSurface(android.view.Surface surface);

    public abstract void onSetZoom(float f);

    private final class ImsVideoCallProviderBinder extends com.android.ims.internal.IImsVideoCallProvider.Stub {
        private ImsVideoCallProviderBinder() {
        }

        @Override // com.android.ims.internal.IImsVideoCallProvider
        public void setCallback(com.android.ims.internal.IImsVideoCallCallback iImsVideoCallCallback) {
            android.telephony.ims.ImsVideoCallProvider.this.mProviderHandler.obtainMessage(1, iImsVideoCallCallback).sendToTarget();
        }

        @Override // com.android.ims.internal.IImsVideoCallProvider
        public void setCamera(java.lang.String str, int i) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.argi1 = i;
            android.telephony.ims.ImsVideoCallProvider.this.mProviderHandler.obtainMessage(2, obtain).sendToTarget();
        }

        @Override // com.android.ims.internal.IImsVideoCallProvider
        public void setPreviewSurface(android.view.Surface surface) {
            android.telephony.ims.ImsVideoCallProvider.this.mProviderHandler.obtainMessage(3, surface).sendToTarget();
        }

        @Override // com.android.ims.internal.IImsVideoCallProvider
        public void setDisplaySurface(android.view.Surface surface) {
            android.telephony.ims.ImsVideoCallProvider.this.mProviderHandler.obtainMessage(4, surface).sendToTarget();
        }

        @Override // com.android.ims.internal.IImsVideoCallProvider
        public void setDeviceOrientation(int i) {
            android.telephony.ims.ImsVideoCallProvider.this.mProviderHandler.obtainMessage(5, i, 0).sendToTarget();
        }

        @Override // com.android.ims.internal.IImsVideoCallProvider
        public void setZoom(float f) {
            android.telephony.ims.ImsVideoCallProvider.this.mProviderHandler.obtainMessage(6, java.lang.Float.valueOf(f)).sendToTarget();
        }

        @Override // com.android.ims.internal.IImsVideoCallProvider
        public void sendSessionModifyRequest(android.telecom.VideoProfile videoProfile, android.telecom.VideoProfile videoProfile2) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = videoProfile;
            obtain.arg2 = videoProfile2;
            android.telephony.ims.ImsVideoCallProvider.this.mProviderHandler.obtainMessage(7, obtain).sendToTarget();
        }

        @Override // com.android.ims.internal.IImsVideoCallProvider
        public void sendSessionModifyResponse(android.telecom.VideoProfile videoProfile) {
            android.telephony.ims.ImsVideoCallProvider.this.mProviderHandler.obtainMessage(8, videoProfile).sendToTarget();
        }

        @Override // com.android.ims.internal.IImsVideoCallProvider
        public void requestCameraCapabilities() {
            android.telephony.ims.ImsVideoCallProvider.this.mProviderHandler.obtainMessage(9).sendToTarget();
        }

        @Override // com.android.ims.internal.IImsVideoCallProvider
        public void requestCallDataUsage() {
            android.telephony.ims.ImsVideoCallProvider.this.mProviderHandler.obtainMessage(10).sendToTarget();
        }

        @Override // com.android.ims.internal.IImsVideoCallProvider
        public void setPauseImage(android.net.Uri uri) {
            android.telephony.ims.ImsVideoCallProvider.this.mProviderHandler.obtainMessage(11, uri).sendToTarget();
        }
    }

    public final com.android.ims.internal.IImsVideoCallProvider getInterface() {
        return this.mBinder;
    }

    public void onSetCamera(java.lang.String str, int i) {
    }

    public void receiveSessionModifyRequest(android.telecom.VideoProfile videoProfile) {
        if (this.mCallback != null) {
            try {
                this.mCallback.receiveSessionModifyRequest(videoProfile);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void receiveSessionModifyResponse(int i, android.telecom.VideoProfile videoProfile, android.telecom.VideoProfile videoProfile2) {
        if (this.mCallback != null) {
            try {
                this.mCallback.receiveSessionModifyResponse(i, videoProfile, videoProfile2);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void handleCallSessionEvent(int i) {
        if (this.mCallback != null) {
            try {
                this.mCallback.handleCallSessionEvent(i);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void changePeerDimensions(int i, int i2) {
        if (this.mCallback != null) {
            try {
                this.mCallback.changePeerDimensions(i, i2);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void changeCallDataUsage(long j) {
        if (this.mCallback != null) {
            try {
                this.mCallback.changeCallDataUsage(j);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void changeCameraCapabilities(android.telecom.VideoProfile.CameraCapabilities cameraCapabilities) {
        if (this.mCallback != null) {
            try {
                this.mCallback.changeCameraCapabilities(cameraCapabilities);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void changeVideoQuality(int i) {
        if (this.mCallback != null) {
            try {
                this.mCallback.changeVideoQuality(i);
            } catch (android.os.RemoteException e) {
            }
        }
    }
}
