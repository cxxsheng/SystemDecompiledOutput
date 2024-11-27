package com.android.server.companion.virtual.camera;

/* loaded from: classes.dex */
public final class VirtualCameraConversionUtil {
    @android.annotation.NonNull
    public static android.companion.virtualcamera.VirtualCameraConfiguration getServiceCameraConfiguration(@android.annotation.NonNull android.companion.virtual.camera.VirtualCameraConfig virtualCameraConfig) throws android.os.RemoteException {
        android.companion.virtualcamera.VirtualCameraConfiguration virtualCameraConfiguration = new android.companion.virtualcamera.VirtualCameraConfiguration();
        virtualCameraConfiguration.supportedStreamConfigs = (android.companion.virtualcamera.SupportedStreamConfiguration[]) virtualCameraConfig.getStreamConfigs().stream().map(new java.util.function.Function() { // from class: com.android.server.companion.virtual.camera.VirtualCameraConversionUtil$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.companion.virtualcamera.SupportedStreamConfiguration convertSupportedStreamConfiguration;
                convertSupportedStreamConfiguration = com.android.server.companion.virtual.camera.VirtualCameraConversionUtil.convertSupportedStreamConfiguration((android.companion.virtual.camera.VirtualCameraStreamConfig) obj);
                return convertSupportedStreamConfiguration;
            }
        }).toArray(new java.util.function.IntFunction() { // from class: com.android.server.companion.virtual.camera.VirtualCameraConversionUtil$$ExternalSyntheticLambda1
            @Override // java.util.function.IntFunction
            public final java.lang.Object apply(int i) {
                android.companion.virtualcamera.SupportedStreamConfiguration[] lambda$getServiceCameraConfiguration$0;
                lambda$getServiceCameraConfiguration$0 = com.android.server.companion.virtual.camera.VirtualCameraConversionUtil.lambda$getServiceCameraConfiguration$0(i);
                return lambda$getServiceCameraConfiguration$0;
            }
        });
        virtualCameraConfiguration.sensorOrientation = virtualCameraConfig.getSensorOrientation();
        virtualCameraConfiguration.lensFacing = virtualCameraConfig.getLensFacing();
        virtualCameraConfiguration.virtualCameraCallback = convertCallback(virtualCameraConfig.getCallback());
        return virtualCameraConfiguration;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.companion.virtualcamera.SupportedStreamConfiguration[] lambda$getServiceCameraConfiguration$0(int i) {
        return new android.companion.virtualcamera.SupportedStreamConfiguration[i];
    }

    @android.annotation.NonNull
    private static android.companion.virtualcamera.IVirtualCameraCallback convertCallback(@android.annotation.NonNull final android.companion.virtual.camera.IVirtualCameraCallback iVirtualCameraCallback) {
        return new android.companion.virtualcamera.IVirtualCameraCallback.Stub() { // from class: com.android.server.companion.virtual.camera.VirtualCameraConversionUtil.1
            @Override // android.companion.virtualcamera.IVirtualCameraCallback
            public void onStreamConfigured(int i, android.view.Surface surface, int i2, int i3, int i4) throws android.os.RemoteException {
                iVirtualCameraCallback.onStreamConfigured(i, surface, i2, i3, com.android.server.companion.virtual.camera.VirtualCameraConversionUtil.convertToJavaFormat(i4));
            }

            @Override // android.companion.virtualcamera.IVirtualCameraCallback
            public void onProcessCaptureRequest(int i, int i2) throws android.os.RemoteException {
                iVirtualCameraCallback.onProcessCaptureRequest(i, i2);
            }

            @Override // android.companion.virtualcamera.IVirtualCameraCallback
            public void onStreamClosed(int i) throws android.os.RemoteException {
                iVirtualCameraCallback.onStreamClosed(i);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public static android.companion.virtualcamera.SupportedStreamConfiguration convertSupportedStreamConfiguration(android.companion.virtual.camera.VirtualCameraStreamConfig virtualCameraStreamConfig) {
        android.companion.virtualcamera.SupportedStreamConfiguration supportedStreamConfiguration = new android.companion.virtualcamera.SupportedStreamConfiguration();
        supportedStreamConfiguration.height = virtualCameraStreamConfig.getHeight();
        supportedStreamConfiguration.width = virtualCameraStreamConfig.getWidth();
        supportedStreamConfiguration.pixelFormat = convertToHalFormat(virtualCameraStreamConfig.getFormat());
        supportedStreamConfiguration.maxFps = virtualCameraStreamConfig.getMaximumFramesPerSecond();
        return supportedStreamConfiguration;
    }

    private static int convertToHalFormat(int i) {
        switch (i) {
            case 1:
                return 1;
            case 35:
                return 35;
            default:
                return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int convertToJavaFormat(int i) {
        switch (i) {
            case 1:
                return 1;
            case 35:
                return 35;
            default:
                return 0;
        }
    }

    private VirtualCameraConversionUtil() {
    }
}
