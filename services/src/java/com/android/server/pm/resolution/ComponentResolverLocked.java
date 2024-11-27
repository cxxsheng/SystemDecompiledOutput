package com.android.server.pm.resolution;

/* loaded from: classes2.dex */
public abstract class ComponentResolverLocked extends com.android.server.pm.resolution.ComponentResolverBase {
    protected final com.android.server.pm.PackageManagerTracedLock mLock;

    protected ComponentResolverLocked(@android.annotation.NonNull com.android.server.pm.UserManagerService userManagerService) {
        super(userManagerService);
        this.mLock = new com.android.server.pm.PackageManagerTracedLock();
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase, com.android.server.pm.resolution.ComponentResolverApi
    public boolean componentExists(@android.annotation.NonNull android.content.ComponentName componentName) {
        boolean componentExists;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                componentExists = super.componentExists(componentName);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return componentExists;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase, com.android.server.pm.resolution.ComponentResolverApi
    @android.annotation.Nullable
    public com.android.internal.pm.pkg.component.ParsedActivity getActivity(@android.annotation.NonNull android.content.ComponentName componentName) {
        com.android.internal.pm.pkg.component.ParsedActivity activity;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                activity = super.getActivity(componentName);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return activity;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase, com.android.server.pm.resolution.ComponentResolverApi
    @android.annotation.Nullable
    public com.android.internal.pm.pkg.component.ParsedProvider getProvider(@android.annotation.NonNull android.content.ComponentName componentName) {
        com.android.internal.pm.pkg.component.ParsedProvider provider;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                provider = super.getProvider(componentName);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return provider;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase, com.android.server.pm.resolution.ComponentResolverApi
    @android.annotation.Nullable
    public com.android.internal.pm.pkg.component.ParsedActivity getReceiver(@android.annotation.NonNull android.content.ComponentName componentName) {
        com.android.internal.pm.pkg.component.ParsedActivity receiver;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                receiver = super.getReceiver(componentName);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return receiver;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase, com.android.server.pm.resolution.ComponentResolverApi
    @android.annotation.Nullable
    public com.android.internal.pm.pkg.component.ParsedService getService(@android.annotation.NonNull android.content.ComponentName componentName) {
        com.android.internal.pm.pkg.component.ParsedService service;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                service = super.getService(componentName);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return service;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase, com.android.server.pm.resolution.ComponentResolverApi
    public boolean isActivityDefined(@android.annotation.NonNull android.content.ComponentName componentName) {
        boolean isActivityDefined;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                isActivityDefined = super.isActivityDefined(componentName);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return isActivityDefined;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase, com.android.server.pm.resolution.ComponentResolverApi
    @android.annotation.Nullable
    public java.util.List<android.content.pm.ResolveInfo> queryActivities(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable java.lang.String str, long j, int i) {
        java.util.List<android.content.pm.ResolveInfo> queryActivities;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                queryActivities = super.queryActivities(computer, intent, str, j, i);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return queryActivities;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase, com.android.server.pm.resolution.ComponentResolverApi
    @android.annotation.Nullable
    public java.util.List<android.content.pm.ResolveInfo> queryActivities(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable java.lang.String str, long j, @android.annotation.NonNull java.util.List<com.android.internal.pm.pkg.component.ParsedActivity> list, int i) {
        java.util.List<android.content.pm.ResolveInfo> queryActivities;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                queryActivities = super.queryActivities(computer, intent, str, j, list, i);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return queryActivities;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase, com.android.server.pm.resolution.ComponentResolverApi
    @android.annotation.Nullable
    public android.content.pm.ProviderInfo queryProvider(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull java.lang.String str, long j, int i) {
        android.content.pm.ProviderInfo queryProvider;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                queryProvider = super.queryProvider(computer, str, j, i);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return queryProvider;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase, com.android.server.pm.resolution.ComponentResolverApi
    @android.annotation.Nullable
    public java.util.List<android.content.pm.ResolveInfo> queryProviders(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable java.lang.String str, long j, int i) {
        java.util.List<android.content.pm.ResolveInfo> queryProviders;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                queryProviders = super.queryProviders(computer, intent, str, j, i);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return queryProviders;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase, com.android.server.pm.resolution.ComponentResolverApi
    @android.annotation.Nullable
    public java.util.List<android.content.pm.ResolveInfo> queryProviders(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable java.lang.String str, long j, @android.annotation.NonNull java.util.List<com.android.internal.pm.pkg.component.ParsedProvider> list, int i) {
        java.util.List<android.content.pm.ResolveInfo> queryProviders;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                queryProviders = super.queryProviders(computer, intent, str, j, list, i);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return queryProviders;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase, com.android.server.pm.resolution.ComponentResolverApi
    @android.annotation.Nullable
    public java.util.List<android.content.pm.ProviderInfo> queryProviders(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i, long j, int i2) {
        java.util.List<android.content.pm.ProviderInfo> queryProviders;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                queryProviders = super.queryProviders(computer, str, str2, i, j, i2);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return queryProviders;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase, com.android.server.pm.resolution.ComponentResolverApi
    @android.annotation.Nullable
    public java.util.List<android.content.pm.ResolveInfo> queryReceivers(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable java.lang.String str, long j, int i) {
        java.util.List<android.content.pm.ResolveInfo> queryReceivers;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                queryReceivers = super.queryReceivers(computer, intent, str, j, i);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return queryReceivers;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase, com.android.server.pm.resolution.ComponentResolverApi
    @android.annotation.Nullable
    public java.util.List<android.content.pm.ResolveInfo> queryReceivers(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable java.lang.String str, long j, @android.annotation.NonNull java.util.List<com.android.internal.pm.pkg.component.ParsedActivity> list, int i) {
        java.util.List<android.content.pm.ResolveInfo> queryReceivers;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                queryReceivers = super.queryReceivers(computer, intent, str, j, list, i);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return queryReceivers;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase, com.android.server.pm.resolution.ComponentResolverApi
    @android.annotation.Nullable
    public java.util.List<android.content.pm.ResolveInfo> queryServices(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable java.lang.String str, long j, int i) {
        java.util.List<android.content.pm.ResolveInfo> queryServices;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                queryServices = super.queryServices(computer, intent, str, j, i);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return queryServices;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase, com.android.server.pm.resolution.ComponentResolverApi
    @android.annotation.Nullable
    public java.util.List<android.content.pm.ResolveInfo> queryServices(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable java.lang.String str, long j, @android.annotation.NonNull java.util.List<com.android.internal.pm.pkg.component.ParsedService> list, int i) {
        java.util.List<android.content.pm.ResolveInfo> queryServices;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                queryServices = super.queryServices(computer, intent, str, j, list, i);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return queryServices;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase, com.android.server.pm.resolution.ComponentResolverApi
    public void querySyncProviders(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull java.util.List<java.lang.String> list, @android.annotation.NonNull java.util.List<android.content.pm.ProviderInfo> list2, boolean z, int i) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                super.querySyncProviders(computer, list, list2, z, i);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase, com.android.server.pm.resolution.ComponentResolverApi
    public void dumpActivityResolvers(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull com.android.server.pm.DumpState dumpState, @android.annotation.NonNull java.lang.String str) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                super.dumpActivityResolvers(printWriter, dumpState, str);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase, com.android.server.pm.resolution.ComponentResolverApi
    public void dumpProviderResolvers(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull com.android.server.pm.DumpState dumpState, @android.annotation.NonNull java.lang.String str) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                super.dumpProviderResolvers(printWriter, dumpState, str);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase, com.android.server.pm.resolution.ComponentResolverApi
    public void dumpReceiverResolvers(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull com.android.server.pm.DumpState dumpState, @android.annotation.NonNull java.lang.String str) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                super.dumpReceiverResolvers(printWriter, dumpState, str);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase, com.android.server.pm.resolution.ComponentResolverApi
    public void dumpServiceResolvers(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull com.android.server.pm.DumpState dumpState, @android.annotation.NonNull java.lang.String str) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                super.dumpServiceResolvers(printWriter, dumpState, str);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase, com.android.server.pm.resolution.ComponentResolverApi
    public void dumpContentProviders(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull com.android.server.pm.DumpState dumpState, @android.annotation.NonNull java.lang.String str) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                super.dumpContentProviders(computer, printWriter, dumpState, str);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase, com.android.server.pm.resolution.ComponentResolverApi
    public void dumpServicePermissions(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull com.android.server.pm.DumpState dumpState) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                super.dumpServicePermissions(printWriter, dumpState);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }
}
