package com.android.server.tv;

/* loaded from: classes2.dex */
final class TvRemoteServiceInput extends android.media.tv.ITvRemoteServiceInput.Stub {
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_KEYS = false;
    private static final java.lang.String TAG = "TvRemoteServiceInput";
    private final java.util.Map<android.os.IBinder, com.android.server.tv.UinputBridge> mBridgeMap = new android.util.ArrayMap();
    private final java.lang.Object mLock;
    private final android.media.tv.ITvRemoteProvider mProvider;

    TvRemoteServiceInput(java.lang.Object obj, android.media.tv.ITvRemoteProvider iTvRemoteProvider) {
        this.mLock = obj;
        this.mProvider = iTvRemoteProvider;
    }

    /* JADX WARN: Not initialized variable reg: 10, insn: 0x0077: INVOKE (r10 I:long) STATIC call: android.os.Binder.restoreCallingIdentity(long):void A[Catch: all -> 0x0042, MD:(long):void (c)] (LINE:78), block:B:33:0x0077 */
    public void openInputBridge(final android.os.IBinder iBinder, java.lang.String str, int i, int i2, int i3) {
        long restoreCallingIdentity;
        synchronized (this.mLock) {
            if (!this.mBridgeMap.containsKey(iBinder)) {
                try {
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        try {
                            this.mBridgeMap.put(iBinder, new com.android.server.tv.UinputBridge(iBinder, str, i, i2, i3));
                            iBinder.linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.android.server.tv.TvRemoteServiceInput.1
                                @Override // android.os.IBinder.DeathRecipient
                                public void binderDied() {
                                    com.android.server.tv.TvRemoteServiceInput.this.closeInputBridge(iBinder);
                                }
                            }, 0);
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        } catch (android.os.RemoteException e) {
                            android.util.Slog.e(TAG, "Token is already dead");
                            closeInputBridge(iBinder);
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            return;
                        }
                    } catch (java.io.IOException e2) {
                        android.util.Slog.e(TAG, "Cannot create device for " + str);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return;
                    }
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(restoreCallingIdentity);
                    throw th;
                }
            }
        }
        try {
            this.mProvider.onInputBridgeConnected(iBinder);
        } catch (android.os.RemoteException e3) {
            android.util.Slog.e(TAG, "Failed remote call to onInputBridgeConnected");
        }
    }

    public void openGamepadBridge(final android.os.IBinder iBinder, java.lang.String str) throws android.os.RemoteException {
        synchronized (this.mLock) {
            if (!this.mBridgeMap.containsKey(iBinder)) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    try {
                        try {
                            this.mBridgeMap.put(iBinder, com.android.server.tv.UinputBridge.openGamepad(iBinder, str));
                            iBinder.linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.android.server.tv.TvRemoteServiceInput.2
                                @Override // android.os.IBinder.DeathRecipient
                                public void binderDied() {
                                    com.android.server.tv.TvRemoteServiceInput.this.closeInputBridge(iBinder);
                                }
                            }, 0);
                        } catch (java.io.IOException e) {
                            android.util.Slog.e(TAG, "Cannot create device for " + str);
                            return;
                        }
                    } catch (android.os.RemoteException e2) {
                        android.util.Slog.e(TAG, "Token is already dead");
                        closeInputBridge(iBinder);
                        return;
                    }
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
        try {
            this.mProvider.onInputBridgeConnected(iBinder);
        } catch (android.os.RemoteException e3) {
            android.util.Slog.e(TAG, "Failed remote call to onInputBridgeConnected");
        }
    }

    public void closeInputBridge(android.os.IBinder iBinder) {
        synchronized (this.mLock) {
            try {
                com.android.server.tv.UinputBridge remove = this.mBridgeMap.remove(iBinder);
                if (remove == null) {
                    android.util.Slog.w(TAG, java.lang.String.format("Input bridge not found for token: %s", iBinder));
                    return;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    remove.close(iBinder);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void clearInputBridge(android.os.IBinder iBinder) {
        synchronized (this.mLock) {
            try {
                com.android.server.tv.UinputBridge uinputBridge = this.mBridgeMap.get(iBinder);
                if (uinputBridge == null) {
                    android.util.Slog.w(TAG, java.lang.String.format("Input bridge not found for token: %s", iBinder));
                    return;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    uinputBridge.clear(iBinder);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void sendTimestamp(android.os.IBinder iBinder, long j) {
    }

    public void sendKeyDown(android.os.IBinder iBinder, int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.tv.UinputBridge uinputBridge = this.mBridgeMap.get(iBinder);
                if (uinputBridge == null) {
                    android.util.Slog.w(TAG, java.lang.String.format("Input bridge not found for token: %s", iBinder));
                    return;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    uinputBridge.sendKeyDown(iBinder, i);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void sendKeyUp(android.os.IBinder iBinder, int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.tv.UinputBridge uinputBridge = this.mBridgeMap.get(iBinder);
                if (uinputBridge == null) {
                    android.util.Slog.w(TAG, java.lang.String.format("Input bridge not found for token: %s", iBinder));
                    return;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    uinputBridge.sendKeyUp(iBinder, i);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void sendPointerDown(android.os.IBinder iBinder, int i, int i2, int i3) {
        synchronized (this.mLock) {
            try {
                com.android.server.tv.UinputBridge uinputBridge = this.mBridgeMap.get(iBinder);
                if (uinputBridge == null) {
                    android.util.Slog.w(TAG, java.lang.String.format("Input bridge not found for token: %s", iBinder));
                    return;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    uinputBridge.sendPointerDown(iBinder, i, i2, i3);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void sendPointerUp(android.os.IBinder iBinder, int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.tv.UinputBridge uinputBridge = this.mBridgeMap.get(iBinder);
                if (uinputBridge == null) {
                    android.util.Slog.w(TAG, java.lang.String.format("Input bridge not found for token: %s", iBinder));
                    return;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    uinputBridge.sendPointerUp(iBinder, i);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void sendPointerSync(android.os.IBinder iBinder) {
        synchronized (this.mLock) {
            try {
                com.android.server.tv.UinputBridge uinputBridge = this.mBridgeMap.get(iBinder);
                if (uinputBridge == null) {
                    android.util.Slog.w(TAG, java.lang.String.format("Input bridge not found for token: %s", iBinder));
                    return;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    uinputBridge.sendPointerSync(iBinder);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void sendGamepadKeyUp(android.os.IBinder iBinder, int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.tv.UinputBridge uinputBridge = this.mBridgeMap.get(iBinder);
                if (uinputBridge == null) {
                    android.util.Slog.w(TAG, java.lang.String.format("Input bridge not found for token: %s", iBinder));
                    return;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    uinputBridge.sendGamepadKey(iBinder, i, false);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void sendGamepadKeyDown(android.os.IBinder iBinder, int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.tv.UinputBridge uinputBridge = this.mBridgeMap.get(iBinder);
                if (uinputBridge == null) {
                    android.util.Slog.w(TAG, java.lang.String.format("Input bridge not found for token: %s", iBinder));
                    return;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    uinputBridge.sendGamepadKey(iBinder, i, true);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void sendGamepadAxisValue(android.os.IBinder iBinder, int i, float f) {
        synchronized (this.mLock) {
            try {
                com.android.server.tv.UinputBridge uinputBridge = this.mBridgeMap.get(iBinder);
                if (uinputBridge == null) {
                    android.util.Slog.w(TAG, java.lang.String.format("Input bridge not found for token: %s", iBinder));
                    return;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    uinputBridge.sendGamepadAxisValue(iBinder, i, f);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
