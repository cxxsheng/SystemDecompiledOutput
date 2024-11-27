package com.android.server.search;

/* loaded from: classes2.dex */
public class SearchManagerService extends android.app.ISearchManager.Stub {
    private static final java.lang.String TAG = "SearchManagerService";
    private final android.content.Context mContext;
    final android.os.Handler mHandler;

    @com.android.internal.annotations.GuardedBy({"mSearchables"})
    private final android.util.SparseArray<com.android.server.search.Searchables> mSearchables = new android.util.SparseArray<>();

    public static class Lifecycle extends com.android.server.SystemService {
        private com.android.server.search.SearchManagerService mService;

        public Lifecycle(android.content.Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            this.mService = new com.android.server.search.SearchManagerService(getContext());
            publishBinderService("search", this.mService);
        }

        @Override // com.android.server.SystemService
        public void onUserStopped(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            this.mService.onCleanupUser(targetUser.getUserIdentifier());
        }
    }

    public SearchManagerService(android.content.Context context) {
        this.mContext = context;
        new com.android.server.search.SearchManagerService.MyPackageMonitor().register(context, (android.os.Looper) null, android.os.UserHandle.ALL, true);
        new com.android.server.search.SearchManagerService.GlobalSearchProviderObserver(context.getContentResolver());
        this.mHandler = com.android.internal.os.BackgroundThread.getHandler();
    }

    private com.android.server.search.Searchables getSearchables(int i) {
        com.android.server.search.Searchables searchables;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.os.UserManager userManager = (android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class);
            if (userManager.getUserInfo(i) == null) {
                throw new java.lang.IllegalStateException("User " + i + " doesn't exist");
            }
            if (!userManager.isUserUnlockingOrUnlocked(i)) {
                throw new java.lang.IllegalStateException("User " + i + " isn't unlocked");
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            synchronized (this.mSearchables) {
                try {
                    searchables = this.mSearchables.get(i);
                    if (searchables == null) {
                        searchables = new com.android.server.search.Searchables(this.mContext, i);
                        this.mSearchables.put(i, searchables);
                    }
                    searchables.updateSearchableListIfNeeded();
                } finally {
                }
            }
            return searchables;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCleanupUser(int i) {
        synchronized (this.mSearchables) {
            this.mSearchables.remove(i);
        }
    }

    class MyPackageMonitor extends com.android.internal.content.PackageMonitor {
        private final java.util.ArrayList<java.lang.String> mChangedPackages = new java.util.ArrayList<>();
        private boolean mSearchablePackageAppeared = false;

        MyPackageMonitor() {
        }

        public void onBeginPackageChanges() {
            clearPackageChangeState();
        }

        public void onPackageAppeared(java.lang.String str, int i) {
            if (!this.mSearchablePackageAppeared) {
                this.mSearchablePackageAppeared = hasSearchableForPackage(str, getChangingUserId());
            }
            this.mChangedPackages.add(str);
        }

        public void onPackageDisappeared(java.lang.String str, int i) {
            this.mChangedPackages.add(str);
        }

        public void onPackageModified(java.lang.String str) {
            this.mChangedPackages.add(str);
        }

        public void onFinishPackageChanges() {
            onFinishPackageChangesInternal();
            clearPackageChangeState();
        }

        private void clearPackageChangeState() {
            this.mChangedPackages.clear();
            this.mSearchablePackageAppeared = false;
        }

        private boolean hasSearchableForPackage(java.lang.String str, int i) {
            if (com.android.server.search.SearchManagerService.querySearchableActivities(com.android.server.search.SearchManagerService.this.mContext, new android.content.Intent("android.intent.action.SEARCH").setPackage(str), i).isEmpty() && com.android.server.search.SearchManagerService.querySearchableActivities(com.android.server.search.SearchManagerService.this.mContext, new android.content.Intent("android.intent.action.WEB_SEARCH").setPackage(str), i).isEmpty()) {
                return !com.android.server.search.SearchManagerService.querySearchableActivities(com.android.server.search.SearchManagerService.this.mContext, new android.content.Intent("android.search.action.GLOBAL_SEARCH").setPackage(str), i).isEmpty();
            }
            return true;
        }

        private boolean shouldRebuildSearchableList(int i) {
            if (this.mSearchablePackageAppeared) {
                return true;
            }
            android.util.ArraySet<java.lang.String> arraySet = new android.util.ArraySet<>();
            synchronized (com.android.server.search.SearchManagerService.this.mSearchables) {
                try {
                    com.android.server.search.Searchables searchables = (com.android.server.search.Searchables) com.android.server.search.SearchManagerService.this.mSearchables.get(i);
                    if (searchables != null) {
                        arraySet = searchables.getKnownSearchablePackageNames();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            int size = this.mChangedPackages.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (arraySet.contains(this.mChangedPackages.get(i2))) {
                    return true;
                }
            }
            return false;
        }

        private void onFinishPackageChangesInternal() {
            int changingUserId = getChangingUserId();
            if (!shouldRebuildSearchableList(changingUserId)) {
                return;
            }
            synchronized (com.android.server.search.SearchManagerService.this.mSearchables) {
                try {
                    com.android.server.search.Searchables searchables = (com.android.server.search.Searchables) com.android.server.search.SearchManagerService.this.mSearchables.get(changingUserId);
                    if (searchables != null) {
                        searchables.invalidateSearchableList();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            android.content.Intent intent = new android.content.Intent("android.search.action.SEARCHABLES_CHANGED");
            intent.addFlags(603979776);
            com.android.server.search.SearchManagerService.this.mContext.sendBroadcastAsUser(intent, new android.os.UserHandle(changingUserId));
        }
    }

    @android.annotation.NonNull
    static java.util.List<android.content.pm.ResolveInfo> querySearchableActivities(android.content.Context context, android.content.Intent intent, int i) {
        return context.getPackageManager().queryIntentActivitiesAsUser(intent, 276824192, i);
    }

    class GlobalSearchProviderObserver extends android.database.ContentObserver {
        private final android.content.ContentResolver mResolver;

        public GlobalSearchProviderObserver(android.content.ContentResolver contentResolver) {
            super(null);
            this.mResolver = contentResolver;
            this.mResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("search_global_search_activity"), false, this);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            synchronized (com.android.server.search.SearchManagerService.this.mSearchables) {
                for (int i = 0; i < com.android.server.search.SearchManagerService.this.mSearchables.size(); i++) {
                    try {
                        ((com.android.server.search.Searchables) com.android.server.search.SearchManagerService.this.mSearchables.valueAt(i)).invalidateSearchableList();
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
            android.content.Intent intent = new android.content.Intent("android.search.action.GLOBAL_SEARCH_ACTIVITY_CHANGED");
            intent.addFlags(536870912);
            com.android.server.search.SearchManagerService.this.mContext.sendBroadcastAsUser(intent, android.os.UserHandle.ALL);
        }
    }

    public android.app.SearchableInfo getSearchableInfo(android.content.ComponentName componentName) {
        if (componentName == null) {
            android.util.Log.e(TAG, "getSearchableInfo(), activity == null");
            return null;
        }
        return getSearchables(android.os.UserHandle.getCallingUserId()).getSearchableInfo(componentName);
    }

    public java.util.List<android.app.SearchableInfo> getSearchablesInGlobalSearch() {
        return getSearchables(android.os.UserHandle.getCallingUserId()).getSearchablesInGlobalSearchList();
    }

    public java.util.List<android.content.pm.ResolveInfo> getGlobalSearchActivities() {
        return getSearchables(android.os.UserHandle.getCallingUserId()).getGlobalSearchActivities();
    }

    public android.content.ComponentName getGlobalSearchActivity() {
        return getSearchables(android.os.UserHandle.getCallingUserId()).getGlobalSearchActivity();
    }

    public android.content.ComponentName getWebSearchActivity() {
        return getSearchables(android.os.UserHandle.getCallingUserId()).getWebSearchActivity();
    }

    public void launchAssist(int i, android.os.Bundle bundle) {
        com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal = (com.android.server.statusbar.StatusBarManagerInternal) com.android.server.LocalServices.getService(com.android.server.statusbar.StatusBarManagerInternal.class);
        if (statusBarManagerInternal != null) {
            statusBarManagerInternal.startAssist(bundle);
        }
    }

    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            java.io.PrintWriter indentingPrintWriter = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ");
            synchronized (this.mSearchables) {
                for (int i = 0; i < this.mSearchables.size(); i++) {
                    try {
                        indentingPrintWriter.print("\nUser: ");
                        indentingPrintWriter.println(this.mSearchables.keyAt(i));
                        indentingPrintWriter.increaseIndent();
                        this.mSearchables.valueAt(i).dump(fileDescriptor, indentingPrintWriter, strArr);
                        indentingPrintWriter.decreaseIndent();
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        }
    }
}
