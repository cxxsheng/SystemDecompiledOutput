package com.android.server.search;

/* loaded from: classes2.dex */
public class Searchables {
    private static final java.lang.String LOG_TAG = "Searchables";
    private static final java.lang.String MD_LABEL_DEFAULT_SEARCHABLE = "android.app.default_searchable";
    private static final java.lang.String MD_SEARCHABLE_SYSTEM_SEARCH = "*";
    private android.content.Context mContext;
    private java.util.List<android.content.pm.ResolveInfo> mGlobalSearchActivities;
    private int mUserId;
    public static java.lang.String GOOGLE_SEARCH_COMPONENT_NAME = "com.android.googlesearch/.GoogleSearch";
    public static java.lang.String ENHANCED_GOOGLE_SEARCH_COMPONENT_NAME = "com.google.android.providers.enhancedgooglesearch/.Launcher";
    private static final java.util.Comparator<android.content.pm.ResolveInfo> GLOBAL_SEARCH_RANKER = new java.util.Comparator<android.content.pm.ResolveInfo>() { // from class: com.android.server.search.Searchables.1
        @Override // java.util.Comparator
        public int compare(android.content.pm.ResolveInfo resolveInfo, android.content.pm.ResolveInfo resolveInfo2) {
            if (resolveInfo == resolveInfo2) {
                return 0;
            }
            boolean isSystemApp = com.android.server.search.Searchables.isSystemApp(resolveInfo);
            boolean isSystemApp2 = com.android.server.search.Searchables.isSystemApp(resolveInfo2);
            if (isSystemApp && !isSystemApp2) {
                return -1;
            }
            if (isSystemApp2 && !isSystemApp) {
                return 1;
            }
            return resolveInfo2.priority - resolveInfo.priority;
        }
    };
    private java.util.HashMap<android.content.ComponentName, android.app.SearchableInfo> mSearchablesMap = null;
    private java.util.ArrayList<android.app.SearchableInfo> mSearchablesList = null;
    private java.util.ArrayList<android.app.SearchableInfo> mSearchablesInGlobalSearchList = null;
    private android.content.ComponentName mCurrentGlobalSearchActivity = null;
    private android.content.ComponentName mWebSearchActivity = null;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mRebuildSearchables = true;

    @com.android.internal.annotations.GuardedBy({"this"})
    private android.util.ArraySet<java.lang.String> mKnownSearchablePackageNames = new android.util.ArraySet<>();
    private final android.content.pm.IPackageManager mPm = android.app.AppGlobals.getPackageManager();

    public Searchables(android.content.Context context, int i) {
        this.mContext = context;
        this.mUserId = i;
    }

    public android.app.SearchableInfo getSearchableInfo(android.content.ComponentName componentName) {
        java.lang.String str;
        android.content.ComponentName componentName2;
        android.app.SearchableInfo searchableInfo;
        android.os.Bundle bundle;
        synchronized (this) {
            try {
                android.app.SearchableInfo searchableInfo2 = this.mSearchablesMap.get(componentName);
                if (searchableInfo2 != null) {
                    if (((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).canAccessComponent(android.os.Binder.getCallingUid(), searchableInfo2.getSearchActivity(), android.os.UserHandle.getCallingUserId())) {
                        return searchableInfo2;
                    }
                    return null;
                }
                try {
                    android.content.pm.ActivityInfo activityInfo = this.mPm.getActivityInfo(componentName, 128L, this.mUserId);
                    if (activityInfo == null) {
                        return null;
                    }
                    android.os.Bundle bundle2 = activityInfo.metaData;
                    if (bundle2 == null) {
                        str = null;
                    } else {
                        str = bundle2.getString(MD_LABEL_DEFAULT_SEARCHABLE);
                    }
                    if (str == null && (bundle = activityInfo.applicationInfo.metaData) != null) {
                        str = bundle.getString(MD_LABEL_DEFAULT_SEARCHABLE);
                    }
                    if (str == null || str.equals("*")) {
                        return null;
                    }
                    java.lang.String packageName = componentName.getPackageName();
                    if (str.charAt(0) == '.') {
                        componentName2 = new android.content.ComponentName(packageName, packageName + str);
                    } else {
                        componentName2 = new android.content.ComponentName(packageName, str);
                    }
                    synchronized (this) {
                        try {
                            searchableInfo = this.mSearchablesMap.get(componentName2);
                            if (searchableInfo != null) {
                                this.mSearchablesMap.put(componentName, searchableInfo);
                            }
                        } finally {
                        }
                    }
                    if (searchableInfo == null || !((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).canAccessComponent(android.os.Binder.getCallingUid(), searchableInfo.getSearchActivity(), android.os.UserHandle.getCallingUserId())) {
                        return null;
                    }
                    return searchableInfo;
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(LOG_TAG, "Error getting activity info " + e);
                    return null;
                }
            } finally {
            }
        }
    }

    public void updateSearchableListIfNeeded() {
        android.content.pm.ResolveInfo resolveInfo;
        android.app.SearchableInfo activityMetaData;
        synchronized (this) {
            try {
                if (this.mRebuildSearchables) {
                    java.util.HashMap<android.content.ComponentName, android.app.SearchableInfo> hashMap = new java.util.HashMap<>();
                    java.util.ArrayList<android.app.SearchableInfo> arrayList = new java.util.ArrayList<>();
                    java.util.ArrayList<android.app.SearchableInfo> arrayList2 = new java.util.ArrayList<>();
                    android.util.ArraySet<java.lang.String> arraySet = new android.util.ArraySet<>();
                    android.content.Intent intent = new android.content.Intent("android.intent.action.SEARCH");
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        java.util.List<android.content.pm.ResolveInfo> queryIntentActivities = queryIntentActivities(intent, 268435584);
                        java.util.List<android.content.pm.ResolveInfo> queryIntentActivities2 = queryIntentActivities(new android.content.Intent("android.intent.action.WEB_SEARCH"), 268435584);
                        if (queryIntentActivities != null || queryIntentActivities2 != null) {
                            int size = queryIntentActivities == null ? 0 : queryIntentActivities.size();
                            int size2 = (queryIntentActivities2 == null ? 0 : queryIntentActivities2.size()) + size;
                            for (int i = 0; i < size2; i++) {
                                if (i < size) {
                                    resolveInfo = queryIntentActivities.get(i);
                                } else {
                                    resolveInfo = queryIntentActivities2.get(i - size);
                                }
                                android.content.pm.ActivityInfo activityInfo = resolveInfo.activityInfo;
                                if (hashMap.get(new android.content.ComponentName(activityInfo.packageName, activityInfo.name)) == null && (activityMetaData = android.app.SearchableInfo.getActivityMetaData(this.mContext, activityInfo, this.mUserId)) != null) {
                                    arrayList.add(activityMetaData);
                                    arraySet.add(activityInfo.packageName);
                                    hashMap.put(activityMetaData.getSearchActivity(), activityMetaData);
                                    if (activityMetaData.shouldIncludeInGlobalSearch()) {
                                        arrayList2.add(activityMetaData);
                                    }
                                }
                            }
                        }
                        java.util.List<android.content.pm.ResolveInfo> findGlobalSearchActivities = findGlobalSearchActivities();
                        android.content.ComponentName findGlobalSearchActivity = findGlobalSearchActivity(findGlobalSearchActivities);
                        android.content.ComponentName findWebSearchActivity = findWebSearchActivity(findGlobalSearchActivity);
                        synchronized (this) {
                            try {
                                this.mSearchablesMap = hashMap;
                                this.mSearchablesList = arrayList;
                                this.mKnownSearchablePackageNames = arraySet;
                                this.mSearchablesInGlobalSearchList = arrayList2;
                                this.mGlobalSearchActivities = findGlobalSearchActivities;
                                this.mCurrentGlobalSearchActivity = findGlobalSearchActivity;
                                this.mWebSearchActivity = findWebSearchActivity;
                                java.util.Iterator<android.content.pm.ResolveInfo> it = this.mGlobalSearchActivities.iterator();
                                while (it.hasNext()) {
                                    this.mKnownSearchablePackageNames.add(it.next().getComponentInfo().packageName);
                                }
                                if (this.mCurrentGlobalSearchActivity != null) {
                                    this.mKnownSearchablePackageNames.add(this.mCurrentGlobalSearchActivity.getPackageName());
                                }
                                if (this.mWebSearchActivity != null) {
                                    this.mKnownSearchablePackageNames.add(this.mWebSearchActivity.getPackageName());
                                }
                                this.mRebuildSearchables = false;
                            } finally {
                            }
                        }
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    } catch (java.lang.Throwable th) {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
            } finally {
            }
        }
    }

    synchronized android.util.ArraySet<java.lang.String> getKnownSearchablePackageNames() {
        return this.mKnownSearchablePackageNames;
    }

    synchronized void invalidateSearchableList() {
        this.mRebuildSearchables = true;
    }

    private java.util.List<android.content.pm.ResolveInfo> findGlobalSearchActivities() {
        java.util.List<android.content.pm.ResolveInfo> queryIntentActivities = queryIntentActivities(new android.content.Intent("android.search.action.GLOBAL_SEARCH"), 268500992);
        if (queryIntentActivities != null && !queryIntentActivities.isEmpty()) {
            java.util.Collections.sort(queryIntentActivities, GLOBAL_SEARCH_RANKER);
        }
        return queryIntentActivities;
    }

    private android.content.ComponentName findGlobalSearchActivity(java.util.List<android.content.pm.ResolveInfo> list) {
        android.content.ComponentName unflattenFromString;
        java.lang.String globalSearchProviderSetting = getGlobalSearchProviderSetting();
        if (!android.text.TextUtils.isEmpty(globalSearchProviderSetting) && (unflattenFromString = android.content.ComponentName.unflattenFromString(globalSearchProviderSetting)) != null && isInstalled(unflattenFromString)) {
            return unflattenFromString;
        }
        return getDefaultGlobalSearchProvider(list);
    }

    private boolean isInstalled(android.content.ComponentName componentName) {
        android.content.Intent intent = new android.content.Intent("android.search.action.GLOBAL_SEARCH");
        intent.setComponent(componentName);
        java.util.List<android.content.pm.ResolveInfo> queryIntentActivities = queryIntentActivities(intent, 65536);
        if (queryIntentActivities != null && !queryIntentActivities.isEmpty()) {
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isSystemApp(android.content.pm.ResolveInfo resolveInfo) {
        return (resolveInfo.activityInfo.applicationInfo.flags & 1) != 0;
    }

    private android.content.ComponentName getDefaultGlobalSearchProvider(java.util.List<android.content.pm.ResolveInfo> list) {
        if (list != null && !list.isEmpty()) {
            android.content.pm.ActivityInfo activityInfo = list.get(0).activityInfo;
            return new android.content.ComponentName(activityInfo.packageName, activityInfo.name);
        }
        android.util.Log.w(LOG_TAG, "No global search activity found");
        return null;
    }

    private java.lang.String getGlobalSearchProviderSetting() {
        android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        return android.provider.Settings.Secure.getStringForUser(contentResolver, "search_global_search_activity", contentResolver.getUserId());
    }

    private android.content.ComponentName findWebSearchActivity(android.content.ComponentName componentName) {
        if (componentName == null) {
            return null;
        }
        android.content.Intent intent = new android.content.Intent("android.intent.action.WEB_SEARCH");
        intent.setPackage(componentName.getPackageName());
        java.util.List<android.content.pm.ResolveInfo> queryIntentActivities = queryIntentActivities(intent, 65536);
        if (queryIntentActivities != null && !queryIntentActivities.isEmpty()) {
            android.content.pm.ActivityInfo activityInfo = queryIntentActivities.get(0).activityInfo;
            return new android.content.ComponentName(activityInfo.packageName, activityInfo.name);
        }
        android.util.Log.w(LOG_TAG, "No web search activity found");
        return null;
    }

    private java.util.List<android.content.pm.ResolveInfo> queryIntentActivities(android.content.Intent intent, int i) {
        try {
            return this.mPm.queryIntentActivities(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), i | 8388608, this.mUserId).getList();
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    public synchronized java.util.ArrayList<android.app.SearchableInfo> getSearchablesList() {
        return createFilterdSearchableInfoList(this.mSearchablesList);
    }

    public synchronized java.util.ArrayList<android.app.SearchableInfo> getSearchablesInGlobalSearchList() {
        return createFilterdSearchableInfoList(this.mSearchablesInGlobalSearchList);
    }

    public synchronized java.util.ArrayList<android.content.pm.ResolveInfo> getGlobalSearchActivities() {
        return createFilterdResolveInfoList(this.mGlobalSearchActivities);
    }

    private java.util.ArrayList<android.app.SearchableInfo> createFilterdSearchableInfoList(java.util.List<android.app.SearchableInfo> list) {
        if (list == null) {
            return null;
        }
        java.util.ArrayList<android.app.SearchableInfo> arrayList = new java.util.ArrayList<>(list.size());
        android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        int callingUid = android.os.Binder.getCallingUid();
        int callingUserId = android.os.UserHandle.getCallingUserId();
        for (android.app.SearchableInfo searchableInfo : list) {
            if (packageManagerInternal.canAccessComponent(callingUid, searchableInfo.getSearchActivity(), callingUserId)) {
                arrayList.add(searchableInfo);
            }
        }
        return arrayList;
    }

    private java.util.ArrayList<android.content.pm.ResolveInfo> createFilterdResolveInfoList(java.util.List<android.content.pm.ResolveInfo> list) {
        if (list == null) {
            return null;
        }
        java.util.ArrayList<android.content.pm.ResolveInfo> arrayList = new java.util.ArrayList<>(list.size());
        android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        int callingUid = android.os.Binder.getCallingUid();
        int callingUserId = android.os.UserHandle.getCallingUserId();
        for (android.content.pm.ResolveInfo resolveInfo : list) {
            if (packageManagerInternal.canAccessComponent(callingUid, resolveInfo.activityInfo.getComponentName(), callingUserId)) {
                arrayList.add(resolveInfo);
            }
        }
        return arrayList;
    }

    public synchronized android.content.ComponentName getGlobalSearchActivity() {
        android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        int callingUid = android.os.Binder.getCallingUid();
        int callingUserId = android.os.UserHandle.getCallingUserId();
        if (this.mCurrentGlobalSearchActivity == null || !packageManagerInternal.canAccessComponent(callingUid, this.mCurrentGlobalSearchActivity, callingUserId)) {
            return null;
        }
        return this.mCurrentGlobalSearchActivity;
    }

    public synchronized android.content.ComponentName getWebSearchActivity() {
        android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        int callingUid = android.os.Binder.getCallingUid();
        int callingUserId = android.os.UserHandle.getCallingUserId();
        if (this.mWebSearchActivity == null || !packageManagerInternal.canAccessComponent(callingUid, this.mWebSearchActivity, callingUserId)) {
            return null;
        }
        return this.mWebSearchActivity;
    }

    void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        printWriter.println("Searchable authorities:");
        synchronized (this) {
            try {
                if (this.mSearchablesList != null) {
                    java.util.Iterator<android.app.SearchableInfo> it = this.mSearchablesList.iterator();
                    while (it.hasNext()) {
                        android.app.SearchableInfo next = it.next();
                        printWriter.print("  ");
                        printWriter.println(next.getSuggestAuthority());
                    }
                }
                printWriter.println("mRebuildSearchables = " + this.mRebuildSearchables);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
