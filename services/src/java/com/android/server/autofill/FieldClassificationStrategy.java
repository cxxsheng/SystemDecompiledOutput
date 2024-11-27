package com.android.server.autofill;

/* loaded from: classes.dex */
final class FieldClassificationStrategy {
    private static final java.lang.String TAG = "FieldClassificationStrategy";
    private final android.content.Context mContext;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.ArrayList<com.android.server.autofill.FieldClassificationStrategy.Command> mQueuedCommands;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.service.autofill.IAutofillFieldClassificationService mRemoteService;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.content.ServiceConnection mServiceConnection;
    private final int mUserId;

    /* JADX INFO: Access modifiers changed from: private */
    interface Command {
        void run(android.service.autofill.IAutofillFieldClassificationService iAutofillFieldClassificationService) throws android.os.RemoteException;
    }

    /* JADX INFO: Access modifiers changed from: private */
    interface MetadataParser<T> {
        T get(android.content.res.Resources resources, int i);
    }

    public FieldClassificationStrategy(android.content.Context context, int i) {
        this.mContext = context;
        this.mUserId = i;
    }

    @android.annotation.Nullable
    android.content.pm.ServiceInfo getServiceInfo() {
        java.lang.String servicesSystemSharedLibraryPackageName = this.mContext.getPackageManager().getServicesSystemSharedLibraryPackageName();
        if (servicesSystemSharedLibraryPackageName == null) {
            android.util.Slog.w(TAG, "no external services package!");
            return null;
        }
        android.content.Intent intent = new android.content.Intent("android.service.autofill.AutofillFieldClassificationService");
        intent.setPackage(servicesSystemSharedLibraryPackageName);
        android.content.pm.ResolveInfo resolveService = this.mContext.getPackageManager().resolveService(intent, 132);
        if (resolveService == null || resolveService.serviceInfo == null) {
            android.util.Slog.w(TAG, "No valid components found.");
            return null;
        }
        return resolveService.serviceInfo;
    }

    @android.annotation.Nullable
    private android.content.ComponentName getServiceComponentName() {
        android.content.pm.ServiceInfo serviceInfo = getServiceInfo();
        if (serviceInfo == null) {
            return null;
        }
        android.content.ComponentName componentName = new android.content.ComponentName(serviceInfo.packageName, serviceInfo.name);
        if (!"android.permission.BIND_AUTOFILL_FIELD_CLASSIFICATION_SERVICE".equals(serviceInfo.permission)) {
            android.util.Slog.w(TAG, componentName.flattenToShortString() + " does not require permission android.permission.BIND_AUTOFILL_FIELD_CLASSIFICATION_SERVICE");
            return null;
        }
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "getServiceComponentName(): " + componentName);
        }
        return componentName;
    }

    void reset() {
        synchronized (this.mLock) {
            if (this.mServiceConnection != null) {
                if (com.android.server.autofill.Helper.sDebug) {
                    android.util.Slog.d(TAG, "reset(): unbinding service.");
                }
                try {
                    this.mContext.unbindService(this.mServiceConnection);
                } catch (java.lang.IllegalArgumentException e) {
                    android.util.Slog.w(TAG, "reset(): " + e.getMessage());
                }
                this.mServiceConnection = null;
            } else if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(TAG, "reset(): service is not bound. Do nothing.");
            }
        }
    }

    private void connectAndRun(@android.annotation.NonNull com.android.server.autofill.FieldClassificationStrategy.Command command) {
        synchronized (this.mLock) {
            try {
                if (this.mRemoteService != null) {
                    try {
                        if (com.android.server.autofill.Helper.sVerbose) {
                            android.util.Slog.v(TAG, "running command right away");
                        }
                        command.run(this.mRemoteService);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.w(TAG, "exception calling service: " + e);
                    }
                    return;
                }
                if (com.android.server.autofill.Helper.sDebug) {
                    android.util.Slog.d(TAG, "service is null; queuing command");
                }
                if (this.mQueuedCommands == null) {
                    this.mQueuedCommands = new java.util.ArrayList<>(1);
                }
                this.mQueuedCommands.add(command);
                if (this.mServiceConnection != null) {
                    return;
                }
                if (com.android.server.autofill.Helper.sVerbose) {
                    android.util.Slog.v(TAG, "creating connection");
                }
                this.mServiceConnection = new android.content.ServiceConnection() { // from class: com.android.server.autofill.FieldClassificationStrategy.1
                    @Override // android.content.ServiceConnection
                    public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
                        if (com.android.server.autofill.Helper.sVerbose) {
                            android.util.Slog.v(com.android.server.autofill.FieldClassificationStrategy.TAG, "onServiceConnected(): " + componentName);
                        }
                        synchronized (com.android.server.autofill.FieldClassificationStrategy.this.mLock) {
                            try {
                                com.android.server.autofill.FieldClassificationStrategy.this.mRemoteService = android.service.autofill.IAutofillFieldClassificationService.Stub.asInterface(iBinder);
                            } catch (android.os.RemoteException e2) {
                                android.util.Slog.w(com.android.server.autofill.FieldClassificationStrategy.TAG, "exception calling " + componentName + ": " + e2);
                            } finally {
                            }
                            if (com.android.server.autofill.FieldClassificationStrategy.this.mQueuedCommands != null) {
                                int size = com.android.server.autofill.FieldClassificationStrategy.this.mQueuedCommands.size();
                                if (com.android.server.autofill.Helper.sDebug) {
                                    android.util.Slog.d(com.android.server.autofill.FieldClassificationStrategy.TAG, "running " + size + " queued commands");
                                }
                                for (int i = 0; i < size; i++) {
                                    com.android.server.autofill.FieldClassificationStrategy.Command command2 = (com.android.server.autofill.FieldClassificationStrategy.Command) com.android.server.autofill.FieldClassificationStrategy.this.mQueuedCommands.get(i);
                                    if (com.android.server.autofill.Helper.sVerbose) {
                                        android.util.Slog.v(com.android.server.autofill.FieldClassificationStrategy.TAG, "running queued command #" + i);
                                    }
                                    command2.run(com.android.server.autofill.FieldClassificationStrategy.this.mRemoteService);
                                }
                                com.android.server.autofill.FieldClassificationStrategy.this.mQueuedCommands = null;
                            } else if (com.android.server.autofill.Helper.sDebug) {
                                android.util.Slog.d(com.android.server.autofill.FieldClassificationStrategy.TAG, "no queued commands");
                            }
                        }
                    }

                    @Override // android.content.ServiceConnection
                    public void onServiceDisconnected(android.content.ComponentName componentName) {
                        if (com.android.server.autofill.Helper.sVerbose) {
                            android.util.Slog.v(com.android.server.autofill.FieldClassificationStrategy.TAG, "onServiceDisconnected(): " + componentName);
                        }
                        synchronized (com.android.server.autofill.FieldClassificationStrategy.this.mLock) {
                            com.android.server.autofill.FieldClassificationStrategy.this.mRemoteService = null;
                        }
                    }

                    @Override // android.content.ServiceConnection
                    public void onBindingDied(android.content.ComponentName componentName) {
                        if (com.android.server.autofill.Helper.sVerbose) {
                            android.util.Slog.v(com.android.server.autofill.FieldClassificationStrategy.TAG, "onBindingDied(): " + componentName);
                        }
                        synchronized (com.android.server.autofill.FieldClassificationStrategy.this.mLock) {
                            com.android.server.autofill.FieldClassificationStrategy.this.mRemoteService = null;
                        }
                    }

                    @Override // android.content.ServiceConnection
                    public void onNullBinding(android.content.ComponentName componentName) {
                        if (com.android.server.autofill.Helper.sVerbose) {
                            android.util.Slog.v(com.android.server.autofill.FieldClassificationStrategy.TAG, "onNullBinding(): " + componentName);
                        }
                        synchronized (com.android.server.autofill.FieldClassificationStrategy.this.mLock) {
                            com.android.server.autofill.FieldClassificationStrategy.this.mRemoteService = null;
                        }
                    }
                };
                android.content.ComponentName serviceComponentName = getServiceComponentName();
                if (com.android.server.autofill.Helper.sVerbose) {
                    android.util.Slog.v(TAG, "binding to: " + serviceComponentName);
                }
                if (serviceComponentName != null) {
                    android.content.Intent intent = new android.content.Intent();
                    intent.setComponent(serviceComponentName);
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        this.mContext.bindServiceAsUser(intent, this.mServiceConnection, 1, android.os.UserHandle.of(this.mUserId));
                        if (com.android.server.autofill.Helper.sVerbose) {
                            android.util.Slog.v(TAG, "bound");
                        }
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    } catch (java.lang.Throwable th) {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
                return;
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
            throw th2;
        }
    }

    @android.annotation.Nullable
    java.lang.String[] getAvailableAlgorithms() {
        return (java.lang.String[]) getMetadataValue("android.autofill.field_classification.available_algorithms", new com.android.server.autofill.FieldClassificationStrategy.MetadataParser() { // from class: com.android.server.autofill.FieldClassificationStrategy$$ExternalSyntheticLambda0
            @Override // com.android.server.autofill.FieldClassificationStrategy.MetadataParser
            public final java.lang.Object get(android.content.res.Resources resources, int i) {
                java.lang.String[] stringArray;
                stringArray = resources.getStringArray(i);
                return stringArray;
            }
        });
    }

    @android.annotation.Nullable
    java.lang.String getDefaultAlgorithm() {
        return (java.lang.String) getMetadataValue("android.autofill.field_classification.default_algorithm", new com.android.server.autofill.FieldClassificationStrategy.MetadataParser() { // from class: com.android.server.autofill.FieldClassificationStrategy$$ExternalSyntheticLambda1
            @Override // com.android.server.autofill.FieldClassificationStrategy.MetadataParser
            public final java.lang.Object get(android.content.res.Resources resources, int i) {
                java.lang.String string;
                string = resources.getString(i);
                return string;
            }
        });
    }

    @android.annotation.Nullable
    private <T> T getMetadataValue(java.lang.String str, com.android.server.autofill.FieldClassificationStrategy.MetadataParser<T> metadataParser) {
        android.content.pm.ServiceInfo serviceInfo = getServiceInfo();
        if (serviceInfo == null) {
            return null;
        }
        try {
            return metadataParser.get(this.mContext.getPackageManager().getResourcesForApplication(serviceInfo.applicationInfo), serviceInfo.metaData.getInt(str));
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.e(TAG, "Error getting application resources for " + serviceInfo, e);
            return null;
        }
    }

    void calculateScores(final android.os.RemoteCallback remoteCallback, @android.annotation.NonNull final java.util.List<android.view.autofill.AutofillValue> list, @android.annotation.NonNull final java.lang.String[] strArr, @android.annotation.NonNull final java.lang.String[] strArr2, @android.annotation.Nullable final java.lang.String str, @android.annotation.Nullable final android.os.Bundle bundle, @android.annotation.Nullable final android.util.ArrayMap<java.lang.String, java.lang.String> arrayMap, @android.annotation.Nullable final android.util.ArrayMap<java.lang.String, android.os.Bundle> arrayMap2) {
        connectAndRun(new com.android.server.autofill.FieldClassificationStrategy.Command() { // from class: com.android.server.autofill.FieldClassificationStrategy$$ExternalSyntheticLambda2
            @Override // com.android.server.autofill.FieldClassificationStrategy.Command
            public final void run(android.service.autofill.IAutofillFieldClassificationService iAutofillFieldClassificationService) {
                iAutofillFieldClassificationService.calculateScores(remoteCallback, list, strArr, strArr2, str, bundle, arrayMap, arrayMap2);
            }
        });
    }

    void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        android.content.ComponentName serviceComponentName = getServiceComponentName();
        printWriter.print(str);
        printWriter.print("User ID: ");
        printWriter.println(this.mUserId);
        printWriter.print(str);
        printWriter.print("Queued commands: ");
        if (this.mQueuedCommands == null) {
            printWriter.println("N/A");
        } else {
            printWriter.println(this.mQueuedCommands.size());
        }
        printWriter.print(str);
        printWriter.print("Implementation: ");
        if (serviceComponentName == null) {
            printWriter.println("N/A");
            return;
        }
        printWriter.println(serviceComponentName.flattenToShortString());
        try {
            printWriter.print(str);
            printWriter.print("Available algorithms: ");
            printWriter.println(java.util.Arrays.toString(getAvailableAlgorithms()));
            printWriter.print(str);
            printWriter.print("Default algorithm: ");
            printWriter.println(getDefaultAlgorithm());
        } catch (java.lang.Exception e) {
            printWriter.print("ERROR CALLING SERVICE: ");
            printWriter.println(e);
        }
    }
}
