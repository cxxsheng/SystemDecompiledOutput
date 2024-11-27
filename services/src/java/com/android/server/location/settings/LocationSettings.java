package com.android.server.location.settings;

/* loaded from: classes2.dex */
public class LocationSettings {
    private static final java.lang.String LOCATION_DIRNAME = "location";
    private static final java.lang.String LOCATION_SETTINGS_FILENAME = "settings";
    final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mUserSettings"})
    private final android.util.SparseArray<com.android.server.location.settings.LocationSettings.LocationUserSettingsStore> mUserSettings = new android.util.SparseArray<>(1);
    private final java.util.concurrent.CopyOnWriteArrayList<com.android.server.location.settings.LocationSettings.LocationUserSettingsListener> mUserSettingsListeners = new java.util.concurrent.CopyOnWriteArrayList<>();

    public interface LocationUserSettingsListener {
        void onLocationUserSettingsChanged(int i, com.android.server.location.settings.LocationUserSettings locationUserSettings, com.android.server.location.settings.LocationUserSettings locationUserSettings2);
    }

    public LocationSettings(android.content.Context context) {
        this.mContext = context;
    }

    public final void registerLocationUserSettingsListener(com.android.server.location.settings.LocationSettings.LocationUserSettingsListener locationUserSettingsListener) {
        this.mUserSettingsListeners.add(locationUserSettingsListener);
    }

    public final void unregisterLocationUserSettingsListener(com.android.server.location.settings.LocationSettings.LocationUserSettingsListener locationUserSettingsListener) {
        this.mUserSettingsListeners.remove(locationUserSettingsListener);
    }

    protected java.io.File getUserSettingsDir(int i) {
        return android.os.Environment.getDataSystemDeDirectory(i);
    }

    protected com.android.server.location.settings.LocationSettings.LocationUserSettingsStore createUserSettingsStore(int i, java.io.File file) {
        return new com.android.server.location.settings.LocationSettings.LocationUserSettingsStore(i, file);
    }

    private com.android.server.location.settings.LocationSettings.LocationUserSettingsStore getUserSettingsStore(int i) {
        com.android.server.location.settings.LocationSettings.LocationUserSettingsStore locationUserSettingsStore;
        synchronized (this.mUserSettings) {
            try {
                locationUserSettingsStore = this.mUserSettings.get(i);
                if (locationUserSettingsStore == null) {
                    locationUserSettingsStore = createUserSettingsStore(i, new java.io.File(new java.io.File(getUserSettingsDir(i), LOCATION_DIRNAME), LOCATION_SETTINGS_FILENAME));
                    this.mUserSettings.put(i, locationUserSettingsStore);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return locationUserSettingsStore;
    }

    public final com.android.server.location.settings.LocationUserSettings getUserSettings(int i) {
        return getUserSettingsStore(i).get();
    }

    public final void updateUserSettings(int i, java.util.function.Function<com.android.server.location.settings.LocationUserSettings, com.android.server.location.settings.LocationUserSettings> function) {
        getUserSettingsStore(i).update(function);
    }

    public final void dump(java.io.FileDescriptor fileDescriptor, android.util.IndentingPrintWriter indentingPrintWriter, java.lang.String[] strArr) {
        try {
            int[] runningUserIds = android.app.ActivityManager.getService().getRunningUserIds();
            if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
                indentingPrintWriter.print("ADAS Location Setting: ");
                indentingPrintWriter.increaseIndent();
                if (runningUserIds.length > 1) {
                    indentingPrintWriter.println();
                    for (int i : runningUserIds) {
                        indentingPrintWriter.print("[u");
                        indentingPrintWriter.print(i);
                        indentingPrintWriter.print("] ");
                        indentingPrintWriter.println(getUserSettings(i).isAdasGnssLocationEnabled());
                    }
                } else {
                    indentingPrintWriter.println(getUserSettings(runningUserIds[0]).isAdasGnssLocationEnabled());
                }
                indentingPrintWriter.decreaseIndent();
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    final void flushFiles() throws java.lang.InterruptedException {
        synchronized (this.mUserSettings) {
            try {
                int size = this.mUserSettings.size();
                for (int i = 0; i < size; i++) {
                    this.mUserSettings.valueAt(i).flushFile();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    final void deleteFiles() throws java.lang.InterruptedException {
        synchronized (this.mUserSettings) {
            try {
                int size = this.mUserSettings.size();
                for (int i = 0; i < size; i++) {
                    this.mUserSettings.valueAt(i).deleteFile();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    protected final void fireListeners(int i, com.android.server.location.settings.LocationUserSettings locationUserSettings, com.android.server.location.settings.LocationUserSettings locationUserSettings2) {
        java.util.Iterator<com.android.server.location.settings.LocationSettings.LocationUserSettingsListener> it = this.mUserSettingsListeners.iterator();
        while (it.hasNext()) {
            it.next().onLocationUserSettingsChanged(i, locationUserSettings, locationUserSettings2);
        }
    }

    class LocationUserSettingsStore extends com.android.server.location.settings.SettingsStore<com.android.server.location.settings.LocationUserSettings> {
        protected final int mUserId;

        LocationUserSettingsStore(int i, java.io.File file) {
            super(file);
            this.mUserId = i;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.android.server.location.settings.SettingsStore
        public com.android.server.location.settings.LocationUserSettings read(int i, java.io.DataInput dataInput) throws java.io.IOException {
            return filterSettings(com.android.server.location.settings.LocationUserSettings.read(com.android.server.location.settings.LocationSettings.this.mContext.getResources(), i, dataInput));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.location.settings.SettingsStore
        public void write(java.io.DataOutput dataOutput, com.android.server.location.settings.LocationUserSettings locationUserSettings) throws java.io.IOException {
            locationUserSettings.write(dataOutput);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ com.android.server.location.settings.LocationUserSettings lambda$update$0(java.util.function.Function function, com.android.server.location.settings.LocationUserSettings locationUserSettings) {
            return filterSettings((com.android.server.location.settings.LocationUserSettings) function.apply(locationUserSettings));
        }

        @Override // com.android.server.location.settings.SettingsStore
        public void update(final java.util.function.Function<com.android.server.location.settings.LocationUserSettings, com.android.server.location.settings.LocationUserSettings> function) {
            super.update(new java.util.function.Function() { // from class: com.android.server.location.settings.LocationSettings$LocationUserSettingsStore$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    com.android.server.location.settings.LocationUserSettings lambda$update$0;
                    lambda$update$0 = com.android.server.location.settings.LocationSettings.LocationUserSettingsStore.this.lambda$update$0(function, (com.android.server.location.settings.LocationUserSettings) obj);
                    return lambda$update$0;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onChange$1(com.android.server.location.settings.LocationUserSettings locationUserSettings, com.android.server.location.settings.LocationUserSettings locationUserSettings2) {
            com.android.server.location.settings.LocationSettings.this.fireListeners(this.mUserId, locationUserSettings, locationUserSettings2);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.location.settings.SettingsStore
        public void onChange(final com.android.server.location.settings.LocationUserSettings locationUserSettings, final com.android.server.location.settings.LocationUserSettings locationUserSettings2) {
            com.android.server.FgThread.getExecutor().execute(new java.lang.Runnable() { // from class: com.android.server.location.settings.LocationSettings$LocationUserSettingsStore$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.location.settings.LocationSettings.LocationUserSettingsStore.this.lambda$onChange$1(locationUserSettings, locationUserSettings2);
                }
            });
        }

        private com.android.server.location.settings.LocationUserSettings filterSettings(com.android.server.location.settings.LocationUserSettings locationUserSettings) {
            if (locationUserSettings.isAdasGnssLocationEnabled() && !com.android.server.location.settings.LocationSettings.this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
                return locationUserSettings.withAdasGnssLocationEnabled(false);
            }
            return locationUserSettings;
        }
    }
}
