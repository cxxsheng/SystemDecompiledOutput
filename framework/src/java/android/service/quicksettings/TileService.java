package android.service.quicksettings;

/* loaded from: classes3.dex */
public class TileService extends android.app.Service {
    public static final java.lang.String ACTION_QS_TILE = "android.service.quicksettings.action.QS_TILE";
    public static final java.lang.String ACTION_QS_TILE_PREFERENCES = "android.service.quicksettings.action.QS_TILE_PREFERENCES";
    private static final boolean DEBUG = false;
    public static final java.lang.String EXTRA_SERVICE = "service";
    public static final java.lang.String EXTRA_STATE = "state";
    public static final java.lang.String EXTRA_TOKEN = "token";
    public static final java.lang.String META_DATA_ACTIVE_TILE = "android.service.quicksettings.ACTIVE_TILE";
    public static final java.lang.String META_DATA_TOGGLEABLE_TILE = "android.service.quicksettings.TOGGLEABLE_TILE";
    public static final long START_ACTIVITY_NEEDS_PENDING_INTENT = 241766793;
    private static final java.lang.String TAG = "TileService";
    private final android.service.quicksettings.TileService.H mHandler = new android.service.quicksettings.TileService.H(android.os.Looper.getMainLooper());
    private boolean mListening = false;
    private android.service.quicksettings.IQSService mService;
    private android.service.quicksettings.Tile mTile;
    private android.os.IBinder mTileToken;
    private android.os.IBinder mToken;
    private java.lang.Runnable mUnlockRunnable;

    @Override // android.app.Service
    public void onDestroy() {
        if (this.mListening) {
            onStopListening();
            this.mListening = false;
        }
        super.onDestroy();
    }

    public void onTileAdded() {
    }

    public void onTileRemoved() {
    }

    public void onStartListening() {
    }

    public void onStopListening() {
    }

    public void onClick() {
    }

    @android.annotation.SystemApi
    public final void setStatusIcon(android.graphics.drawable.Icon icon, java.lang.String str) {
        if (this.mService != null) {
            try {
                this.mService.updateStatusIcon(this.mTileToken, icon, str);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public final void showDialog(android.app.Dialog dialog) {
        dialog.getWindow().getAttributes().token = this.mToken;
        dialog.getWindow().setType(2035);
        dialog.getWindow().getDecorView().addOnAttachStateChangeListener(new android.view.View.OnAttachStateChangeListener() { // from class: android.service.quicksettings.TileService.1
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(android.view.View view) {
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(android.view.View view) {
                try {
                    android.service.quicksettings.TileService.this.mService.onDialogHidden(android.service.quicksettings.TileService.this.mTileToken);
                } catch (android.os.RemoteException e) {
                }
            }
        });
        dialog.show();
        try {
            this.mService.onShowDialog(this.mTileToken);
        } catch (android.os.RemoteException e) {
        }
    }

    public final void unlockAndRun(java.lang.Runnable runnable) {
        this.mUnlockRunnable = runnable;
        try {
            this.mService.startUnlockAndRun(this.mTileToken);
        } catch (android.os.RemoteException e) {
        }
    }

    public final boolean isSecure() {
        try {
            return this.mService.isSecure();
        } catch (android.os.RemoteException e) {
            return true;
        }
    }

    public final boolean isLocked() {
        try {
            return this.mService.isLocked();
        } catch (android.os.RemoteException e) {
            return true;
        }
    }

    @java.lang.Deprecated
    public final void startActivityAndCollapse(android.content.Intent intent) {
        if (android.app.compat.CompatChanges.isChangeEnabled(START_ACTIVITY_NEEDS_PENDING_INTENT)) {
            throw new java.lang.UnsupportedOperationException("startActivityAndCollapse: Starting activity from TileService using an Intent is not allowed.");
        }
        startActivity(intent);
        try {
            this.mService.onStartActivity(this.mTileToken);
        } catch (android.os.RemoteException e) {
        }
    }

    public final void startActivityAndCollapse(android.app.PendingIntent pendingIntent) {
        java.util.Objects.requireNonNull(pendingIntent);
        try {
            this.mService.startActivity(this.mTileToken, pendingIntent);
        } catch (android.os.RemoteException e) {
        }
    }

    public final android.service.quicksettings.Tile getQsTile() {
        return this.mTile;
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        this.mService = android.service.quicksettings.IQSService.Stub.asInterface(intent.getIBinderExtra("service"));
        this.mTileToken = intent.getIBinderExtra(EXTRA_TOKEN);
        try {
            this.mTile = this.mService.getTile(this.mTileToken);
            if (this.mTile != null) {
                this.mTile.setService(this.mService, this.mTileToken);
                this.mHandler.sendEmptyMessage(7);
            }
            return new android.service.quicksettings.IQSTileService.Stub() { // from class: android.service.quicksettings.TileService.2
                @Override // android.service.quicksettings.IQSTileService
                public void onTileRemoved() throws android.os.RemoteException {
                    android.service.quicksettings.TileService.this.mHandler.sendEmptyMessage(4);
                }

                @Override // android.service.quicksettings.IQSTileService
                public void onTileAdded() throws android.os.RemoteException {
                    android.service.quicksettings.TileService.this.mHandler.sendEmptyMessage(3);
                }

                @Override // android.service.quicksettings.IQSTileService
                public void onStopListening() throws android.os.RemoteException {
                    android.service.quicksettings.TileService.this.mHandler.sendEmptyMessage(2);
                }

                @Override // android.service.quicksettings.IQSTileService
                public void onStartListening() throws android.os.RemoteException {
                    android.service.quicksettings.TileService.this.mHandler.sendEmptyMessage(1);
                }

                @Override // android.service.quicksettings.IQSTileService
                public void onClick(android.os.IBinder iBinder) throws android.os.RemoteException {
                    android.service.quicksettings.TileService.this.mHandler.obtainMessage(5, iBinder).sendToTarget();
                }

                @Override // android.service.quicksettings.IQSTileService
                public void onUnlockComplete() throws android.os.RemoteException {
                    android.service.quicksettings.TileService.this.mHandler.sendEmptyMessage(6);
                }
            };
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, getClass().getSimpleName() + " - Couldn't get tile from IQSService.", e);
            return null;
        }
    }

    private class H extends android.os.Handler {
        private static final int MSG_START_LISTENING = 1;
        private static final int MSG_START_SUCCESS = 7;
        private static final int MSG_STOP_LISTENING = 2;
        private static final int MSG_TILE_ADDED = 3;
        private static final int MSG_TILE_CLICKED = 5;
        private static final int MSG_TILE_REMOVED = 4;
        private static final int MSG_UNLOCK_COMPLETE = 6;
        private final java.lang.String mTileServiceName;

        public H(android.os.Looper looper) {
            super(looper);
            this.mTileServiceName = android.service.quicksettings.TileService.this.getClass().getSimpleName();
        }

        private void logMessage(java.lang.String str) {
            android.util.Log.d(android.service.quicksettings.TileService.TAG, this.mTileServiceName + " Handler - " + str);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    if (!android.service.quicksettings.TileService.this.mListening) {
                        android.service.quicksettings.TileService.this.mListening = true;
                        android.service.quicksettings.TileService.this.onStartListening();
                        break;
                    }
                    break;
                case 2:
                    if (android.service.quicksettings.TileService.this.mListening) {
                        android.service.quicksettings.TileService.this.mListening = false;
                        android.service.quicksettings.TileService.this.onStopListening();
                        break;
                    }
                    break;
                case 3:
                    android.service.quicksettings.TileService.this.onTileAdded();
                    break;
                case 4:
                    if (android.service.quicksettings.TileService.this.mListening) {
                        android.service.quicksettings.TileService.this.mListening = false;
                        android.service.quicksettings.TileService.this.onStopListening();
                    }
                    android.service.quicksettings.TileService.this.onTileRemoved();
                    break;
                case 5:
                    android.service.quicksettings.TileService.this.mToken = (android.os.IBinder) message.obj;
                    android.service.quicksettings.TileService.this.onClick();
                    break;
                case 6:
                    if (android.service.quicksettings.TileService.this.mUnlockRunnable != null) {
                        android.service.quicksettings.TileService.this.mUnlockRunnable.run();
                        break;
                    }
                    break;
                case 7:
                    try {
                        android.service.quicksettings.TileService.this.mService.onStartSuccessful(android.service.quicksettings.TileService.this.mTileToken);
                        break;
                    } catch (android.os.RemoteException e) {
                        return;
                    }
            }
        }
    }

    public static boolean isQuickSettingsSupported() {
        return android.content.res.Resources.getSystem().getBoolean(com.android.internal.R.bool.config_quickSettingsSupported);
    }

    public static final void requestListeningState(android.content.Context context, android.content.ComponentName componentName) {
        android.app.StatusBarManager statusBarManager = (android.app.StatusBarManager) context.getSystemService(android.app.StatusBarManager.class);
        if (statusBarManager == null) {
            android.util.Log.e(TAG, "No StatusBarManager service found");
        } else {
            statusBarManager.requestTileServiceListeningState(componentName);
        }
    }
}
