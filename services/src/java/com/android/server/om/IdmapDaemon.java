package com.android.server.om;

/* loaded from: classes2.dex */
class IdmapDaemon {
    private static final java.lang.String IDMAP_DAEMON = "idmap2d";
    private static final int SERVICE_CONNECT_INTERVAL_SLEEP_MS = 5;
    private static final int SERVICE_CONNECT_UPTIME_TIMEOUT_MS = 5000;
    private static final int SERVICE_CONNECT_WALLTIME_TIMEOUT_MS = 30000;
    private static final int SERVICE_TIMEOUT_MS = 10000;
    private static com.android.server.om.IdmapDaemon sInstance;
    private volatile android.os.IIdmap2 mService;
    private final java.util.concurrent.atomic.AtomicInteger mOpenedCount = new java.util.concurrent.atomic.AtomicInteger();
    private final java.lang.Object mIdmapToken = new java.lang.Object();

    IdmapDaemon() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    class Connection implements java.lang.AutoCloseable {

        @android.annotation.Nullable
        private final android.os.IIdmap2 mIdmap2;
        private boolean mOpened;

        private Connection(android.os.IIdmap2 iIdmap2) {
            this.mOpened = true;
            synchronized (com.android.server.om.IdmapDaemon.this.mIdmapToken) {
                com.android.server.om.IdmapDaemon.this.mOpenedCount.incrementAndGet();
                this.mIdmap2 = iIdmap2;
            }
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            synchronized (com.android.server.om.IdmapDaemon.this.mIdmapToken) {
                try {
                    if (this.mOpened) {
                        this.mOpened = false;
                        if (com.android.server.om.IdmapDaemon.this.mOpenedCount.decrementAndGet() != 0) {
                            return;
                        }
                        com.android.server.FgThread.getHandler().postDelayed(new java.lang.Runnable() { // from class: com.android.server.om.IdmapDaemon$Connection$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.om.IdmapDaemon.Connection.this.lambda$close$0();
                            }
                        }, com.android.server.om.IdmapDaemon.this.mIdmapToken, com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$close$0() {
            synchronized (com.android.server.om.IdmapDaemon.this.mIdmapToken) {
                try {
                    if (com.android.server.om.IdmapDaemon.this.mService == null || com.android.server.om.IdmapDaemon.this.mOpenedCount.get() != 0) {
                        return;
                    }
                    com.android.server.om.IdmapDaemon.stopIdmapService();
                    com.android.server.om.IdmapDaemon.this.mService = null;
                } finally {
                }
            }
        }

        @android.annotation.Nullable
        public android.os.IIdmap2 getIdmap2() {
            return this.mIdmap2;
        }
    }

    static com.android.server.om.IdmapDaemon getInstance() {
        if (sInstance == null) {
            sInstance = new com.android.server.om.IdmapDaemon();
        }
        return sInstance;
    }

    java.lang.String createIdmap(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.Nullable java.lang.String str3, int i, boolean z, int i2) throws java.util.concurrent.TimeoutException, android.os.RemoteException {
        com.android.server.om.IdmapDaemon.Connection connect = connect();
        try {
            android.os.IIdmap2 idmap2 = connect.getIdmap2();
            if (idmap2 == null) {
                android.util.Slog.w("OverlayManager", "idmap2d service is not ready for createIdmap(\"" + str + "\", \"" + str2 + "\", \"" + str3 + "\", " + i + ", " + z + ", " + i2 + ")");
                connect.close();
                return null;
            }
            java.lang.String createIdmap = idmap2.createIdmap(str, str2, android.text.TextUtils.emptyIfNull(str3), i, z, i2);
            connect.close();
            return createIdmap;
        } catch (java.lang.Throwable th) {
            if (connect == null) {
                throw th;
            }
            try {
                connect.close();
                throw th;
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
                throw th;
            }
        }
    }

    boolean removeIdmap(java.lang.String str, int i) throws java.util.concurrent.TimeoutException, android.os.RemoteException {
        com.android.server.om.IdmapDaemon.Connection connect = connect();
        try {
            android.os.IIdmap2 idmap2 = connect.getIdmap2();
            if (idmap2 == null) {
                android.util.Slog.w("OverlayManager", "idmap2d service is not ready for removeIdmap(\"" + str + "\", " + i + ")");
                connect.close();
                return false;
            }
            boolean removeIdmap = idmap2.removeIdmap(str, i);
            connect.close();
            return removeIdmap;
        } catch (java.lang.Throwable th) {
            if (connect != null) {
                try {
                    connect.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    boolean verifyIdmap(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.Nullable java.lang.String str3, int i, boolean z, int i2) throws java.lang.Exception {
        com.android.server.om.IdmapDaemon.Connection connect = connect();
        try {
            android.os.IIdmap2 idmap2 = connect.getIdmap2();
            if (idmap2 == null) {
                android.util.Slog.w("OverlayManager", "idmap2d service is not ready for verifyIdmap(\"" + str + "\", \"" + str2 + "\", \"" + str3 + "\", " + i + ", " + z + ", " + i2 + ")");
                connect.close();
                return false;
            }
            boolean verifyIdmap = idmap2.verifyIdmap(str, str2, android.text.TextUtils.emptyIfNull(str3), i, z, i2);
            connect.close();
            return verifyIdmap;
        } catch (java.lang.Throwable th) {
            if (connect == null) {
                throw th;
            }
            try {
                connect.close();
                throw th;
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
                throw th;
            }
        }
    }

    boolean idmapExists(java.lang.String str, int i) {
        try {
            com.android.server.om.IdmapDaemon.Connection connect = connect();
            try {
                android.os.IIdmap2 idmap2 = connect.getIdmap2();
                if (idmap2 != null) {
                    boolean isFile = new java.io.File(idmap2.getIdmapPath(str, i)).isFile();
                    connect.close();
                    return isFile;
                }
                android.util.Slog.w("OverlayManager", "idmap2d service is not ready for idmapExists(\"" + str + "\", " + i + ")");
                connect.close();
                return false;
            } finally {
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.wtf("OverlayManager", "failed to check if idmap exists for " + str, e);
            return false;
        }
    }

    android.os.FabricatedOverlayInfo createFabricatedOverlay(@android.annotation.NonNull android.os.FabricatedOverlayInternal fabricatedOverlayInternal) {
        try {
            com.android.server.om.IdmapDaemon.Connection connect = connect();
            try {
                android.os.IIdmap2 idmap2 = connect.getIdmap2();
                if (idmap2 == null) {
                    android.util.Slog.w("OverlayManager", "idmap2d service is not ready for createFabricatedOverlay()");
                    connect.close();
                    return null;
                }
                android.os.FabricatedOverlayInfo createFabricatedOverlay = idmap2.createFabricatedOverlay(fabricatedOverlayInternal);
                connect.close();
                return createFabricatedOverlay;
            } finally {
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.wtf("OverlayManager", "failed to fabricate overlay " + fabricatedOverlayInternal, e);
            return null;
        }
    }

    boolean deleteFabricatedOverlay(@android.annotation.NonNull java.lang.String str) {
        try {
            com.android.server.om.IdmapDaemon.Connection connect = connect();
            try {
                android.os.IIdmap2 idmap2 = connect.getIdmap2();
                if (idmap2 != null) {
                    boolean deleteFabricatedOverlay = idmap2.deleteFabricatedOverlay(str);
                    connect.close();
                    return deleteFabricatedOverlay;
                }
                android.util.Slog.w("OverlayManager", "idmap2d service is not ready for deleteFabricatedOverlay(\"" + str + "\")");
                connect.close();
                return false;
            } finally {
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.wtf("OverlayManager", "failed to delete fabricated overlay '" + str + "'", e);
            return false;
        }
    }

    synchronized java.util.List<android.os.FabricatedOverlayInfo> getFabricatedOverlayInfos() {
        int i;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        com.android.server.om.IdmapDaemon.Connection connection = null;
        try {
            try {
                connection = connect();
                android.os.IIdmap2 idmap2 = connection.getIdmap2();
                if (idmap2 == null) {
                    android.util.Slog.w("OverlayManager", "idmap2d service is not ready for getFabricatedOverlayInfos()");
                    java.util.List<android.os.FabricatedOverlayInfo> emptyList = java.util.Collections.emptyList();
                    try {
                        connection.getIdmap2();
                    } catch (android.os.RemoteException e) {
                    }
                    connection.close();
                    return emptyList;
                }
                i = idmap2.acquireFabricatedOverlayIterator();
                while (true) {
                    try {
                        java.util.List nextFabricatedOverlayInfos = idmap2.nextFabricatedOverlayInfos(i);
                        if (nextFabricatedOverlayInfos.isEmpty()) {
                            try {
                                break;
                            } catch (android.os.RemoteException e2) {
                            }
                        } else {
                            arrayList.addAll(nextFabricatedOverlayInfos);
                        }
                    } catch (java.lang.Exception e3) {
                        e = e3;
                        android.util.Slog.wtf("OverlayManager", "failed to get all fabricated overlays", e);
                        try {
                            if (connection.getIdmap2() != null && i != -1) {
                                connection.getIdmap2().releaseFabricatedOverlayIterator(i);
                            }
                        } catch (android.os.RemoteException e4) {
                        }
                        connection.close();
                        return arrayList;
                    }
                }
                if (connection.getIdmap2() != null && i != -1) {
                    connection.getIdmap2().releaseFabricatedOverlayIterator(i);
                }
                connection.close();
                return arrayList;
            } catch (java.lang.Exception e5) {
                e = e5;
                i = -1;
            } catch (java.lang.Throwable th) {
                th = th;
                try {
                    if (connection.getIdmap2() != null) {
                        connection.getIdmap2().releaseFabricatedOverlayIterator(-1);
                    }
                } catch (android.os.RemoteException e6) {
                }
                connection.close();
                throw th;
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
            if (connection.getIdmap2() != null && -1 != -1) {
                connection.getIdmap2().releaseFabricatedOverlayIterator(-1);
            }
            connection.close();
            throw th;
        }
    }

    java.lang.String dumpIdmap(@android.annotation.NonNull java.lang.String str) {
        try {
            com.android.server.om.IdmapDaemon.Connection connect = connect();
            try {
                android.os.IIdmap2 idmap2 = connect.getIdmap2();
                if (idmap2 == null) {
                    android.util.Slog.w("OverlayManager", "idmap2d service is not ready for dumpIdmap()");
                    connect.close();
                    return "idmap2d service is not ready for dumpIdmap()";
                }
                java.lang.String nullIfEmpty = android.text.TextUtils.nullIfEmpty(idmap2.dumpIdmap(str));
                connect.close();
                return nullIfEmpty;
            } finally {
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.wtf("OverlayManager", "failed to dump idmap", e);
            return null;
        }
    }

    @android.annotation.Nullable
    private android.os.IBinder getIdmapService() throws java.util.concurrent.TimeoutException, android.os.RemoteException {
        long uptimeMillis;
        try {
            android.os.SystemService.start(IDMAP_DAEMON);
        } catch (java.lang.RuntimeException e) {
            android.util.Slog.wtf("OverlayManager", "Failed to enable idmap2 daemon", e);
            if (e.getMessage().contains("failed to set system property")) {
                return null;
            }
        }
        long uptimeMillis2 = android.os.SystemClock.uptimeMillis() + 5000;
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        long j = elapsedRealtime + 30000;
        do {
            android.os.IBinder service = android.os.ServiceManager.getService("idmap");
            if (service != null) {
                service.linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.android.server.om.IdmapDaemon$$ExternalSyntheticLambda0
                    @Override // android.os.IBinder.DeathRecipient
                    public final void binderDied() {
                        com.android.server.om.IdmapDaemon.lambda$getIdmapService$0();
                    }
                }, 0);
                return service;
            }
            android.os.SystemClock.sleep(5L);
            uptimeMillis = android.os.SystemClock.uptimeMillis();
            if (uptimeMillis > uptimeMillis2) {
                break;
            }
            elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        } while (elapsedRealtime <= j);
        throw new java.util.concurrent.TimeoutException(android.text.TextUtils.formatSimple("Failed to connect to '%s' in %d/%d ms (spent %d/%d ms)", new java.lang.Object[]{"idmap", 5000, 30000, java.lang.Long.valueOf((uptimeMillis - uptimeMillis2) + 5000), java.lang.Long.valueOf((elapsedRealtime - j) + 30000)}));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getIdmapService$0() {
        android.util.Slog.w("OverlayManager", android.text.TextUtils.formatSimple("service '%s' died", new java.lang.Object[]{"idmap"}));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void stopIdmapService() {
        try {
            android.os.SystemService.stop(IDMAP_DAEMON);
        } catch (java.lang.RuntimeException e) {
            android.util.Slog.w("OverlayManager", "Failed to disable idmap2 daemon", e);
        }
    }

    @android.annotation.NonNull
    private com.android.server.om.IdmapDaemon.Connection connect() throws java.util.concurrent.TimeoutException, android.os.RemoteException {
        synchronized (this.mIdmapToken) {
            try {
                com.android.server.FgThread.getHandler().removeCallbacksAndMessages(this.mIdmapToken);
                com.android.server.om.IdmapDaemon.ConnectionIA connectionIA = null;
                if (this.mService != null) {
                    return new com.android.server.om.IdmapDaemon.Connection(this.mService);
                }
                android.os.IBinder idmapService = getIdmapService();
                if (idmapService == null) {
                    return new com.android.server.om.IdmapDaemon.Connection(connectionIA);
                }
                this.mService = android.os.IIdmap2.Stub.asInterface(idmapService);
                return new com.android.server.om.IdmapDaemon.Connection(this.mService);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
