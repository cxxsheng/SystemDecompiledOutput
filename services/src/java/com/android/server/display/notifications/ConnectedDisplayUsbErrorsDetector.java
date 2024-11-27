package com.android.server.display.notifications;

/* loaded from: classes.dex */
public class ConnectedDisplayUsbErrorsDetector implements android.hardware.usb.UsbManager.DisplayPortAltModeInfoListener {
    private static final java.lang.String TAG = "ConnectedDisplayUsbErrorsDetector";
    private final android.content.Context mContext;
    private final com.android.server.display.notifications.ConnectedDisplayUsbErrorsDetector.Injector mInjector;
    private final boolean mIsConnectedDisplayErrorHandlingEnabled;
    private com.android.server.display.notifications.ConnectedDisplayUsbErrorsDetector.Listener mListener;

    public interface Injector {
        android.hardware.usb.UsbManager getUsbManager();
    }

    public interface Listener {
        void onCableNotCapableDisplayPort();

        void onDisplayPortLinkTrainingFailure();
    }

    ConnectedDisplayUsbErrorsDetector(@android.annotation.NonNull com.android.server.display.feature.DisplayManagerFlags displayManagerFlags, @android.annotation.NonNull final android.content.Context context) {
        this(displayManagerFlags, context, new com.android.server.display.notifications.ConnectedDisplayUsbErrorsDetector.Injector() { // from class: com.android.server.display.notifications.ConnectedDisplayUsbErrorsDetector$$ExternalSyntheticLambda0
            @Override // com.android.server.display.notifications.ConnectedDisplayUsbErrorsDetector.Injector
            public final android.hardware.usb.UsbManager getUsbManager() {
                android.hardware.usb.UsbManager lambda$new$0;
                lambda$new$0 = com.android.server.display.notifications.ConnectedDisplayUsbErrorsDetector.lambda$new$0(context);
                return lambda$new$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.hardware.usb.UsbManager lambda$new$0(android.content.Context context) {
        return (android.hardware.usb.UsbManager) context.getSystemService(android.hardware.usb.UsbManager.class);
    }

    @com.android.internal.annotations.VisibleForTesting
    ConnectedDisplayUsbErrorsDetector(@android.annotation.NonNull com.android.server.display.feature.DisplayManagerFlags displayManagerFlags, @android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.display.notifications.ConnectedDisplayUsbErrorsDetector.Injector injector) {
        this.mContext = context;
        this.mInjector = injector;
        this.mIsConnectedDisplayErrorHandlingEnabled = displayManagerFlags.isConnectedDisplayErrorHandlingEnabled();
    }

    @android.annotation.SuppressLint({"AndroidFrameworkRequiresPermission"})
    void registerListener(com.android.server.display.notifications.ConnectedDisplayUsbErrorsDetector.Listener listener) {
        if (!this.mIsConnectedDisplayErrorHandlingEnabled) {
            return;
        }
        android.hardware.usb.UsbManager usbManager = this.mInjector.getUsbManager();
        if (usbManager == null) {
            android.util.Slog.e(TAG, "UsbManager is null");
            return;
        }
        this.mListener = listener;
        try {
            usbManager.registerDisplayPortAltModeInfoListener(this.mContext.getMainExecutor(), this);
        } catch (java.lang.IllegalStateException e) {
            android.util.Slog.e(TAG, "Failed to register listener", e);
        }
    }

    public void onDisplayPortAltModeInfoChanged(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.hardware.usb.DisplayPortAltModeInfo displayPortAltModeInfo) {
        if (this.mListener == null) {
            return;
        }
        if (2 == displayPortAltModeInfo.getPartnerSinkStatus() && 1 == displayPortAltModeInfo.getCableStatus()) {
            this.mListener.onCableNotCapableDisplayPort();
        } else if (2 == displayPortAltModeInfo.getLinkTrainingStatus()) {
            this.mListener.onDisplayPortLinkTrainingFailure();
        }
    }
}
