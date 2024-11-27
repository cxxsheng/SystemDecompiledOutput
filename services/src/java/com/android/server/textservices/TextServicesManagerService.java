package com.android.server.textservices;

/* loaded from: classes2.dex */
public class TextServicesManagerService extends com.android.internal.textservice.ITextServicesManager.Stub {
    private static final boolean DBG = false;
    private static final java.lang.String TAG = com.android.server.textservices.TextServicesManagerService.class.getSimpleName();
    private final android.content.Context mContext;

    @android.annotation.NonNull
    private final android.os.UserManager mUserManager;
    private final android.util.SparseArray<com.android.server.textservices.TextServicesManagerService.TextServicesData> mUserData = new android.util.SparseArray<>();
    private final java.lang.Object mLock = new java.lang.Object();
    private final com.android.server.textservices.TextServicesManagerService.TextServicesMonitor mMonitor = new com.android.server.textservices.TextServicesManagerService.TextServicesMonitor();

    private static class TextServicesData {
        private final android.content.Context mContext;
        private final android.content.ContentResolver mResolver;
        private final int mUserId;
        public int mUpdateCount = 0;
        private final java.util.HashMap<java.lang.String, android.view.textservice.SpellCheckerInfo> mSpellCheckerMap = new java.util.HashMap<>();
        private final java.util.ArrayList<android.view.textservice.SpellCheckerInfo> mSpellCheckerList = new java.util.ArrayList<>();
        private final java.util.HashMap<java.lang.String, com.android.server.textservices.TextServicesManagerService.SpellCheckerBindGroup> mSpellCheckerBindGroups = new java.util.HashMap<>();

        public TextServicesData(int i, @android.annotation.NonNull android.content.Context context) {
            this.mUserId = i;
            this.mContext = context;
            this.mResolver = context.getContentResolver();
        }

        private void putString(java.lang.String str, java.lang.String str2) {
            android.provider.Settings.Secure.putStringForUser(this.mResolver, str, str2, this.mUserId);
        }

        @android.annotation.Nullable
        private java.lang.String getString(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
            java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(this.mResolver, str, this.mUserId);
            return stringForUser != null ? stringForUser : str2;
        }

        private void putInt(java.lang.String str, int i) {
            android.provider.Settings.Secure.putIntForUser(this.mResolver, str, i, this.mUserId);
        }

        private int getInt(java.lang.String str, int i) {
            return android.provider.Settings.Secure.getIntForUser(this.mResolver, str, i, this.mUserId);
        }

        private boolean getBoolean(java.lang.String str, boolean z) {
            return getInt(str, z ? 1 : 0) == 1;
        }

        private void putSelectedSpellChecker(@android.annotation.Nullable java.lang.String str) {
            putString("selected_spell_checker", str);
        }

        private void putSelectedSpellCheckerSubtype(int i) {
            putInt("selected_spell_checker_subtype", i);
        }

        @android.annotation.NonNull
        private java.lang.String getSelectedSpellChecker() {
            return getString("selected_spell_checker", "");
        }

        public int getSelectedSpellCheckerSubtype(int i) {
            return getInt("selected_spell_checker_subtype", i);
        }

        public boolean isSpellCheckerEnabled() {
            return getBoolean("spell_checker_enabled", true);
        }

        @android.annotation.Nullable
        public android.view.textservice.SpellCheckerInfo getCurrentSpellChecker() {
            java.lang.String selectedSpellChecker = getSelectedSpellChecker();
            if (android.text.TextUtils.isEmpty(selectedSpellChecker)) {
                return null;
            }
            return this.mSpellCheckerMap.get(selectedSpellChecker);
        }

        public void setCurrentSpellChecker(@android.annotation.Nullable android.view.textservice.SpellCheckerInfo spellCheckerInfo) {
            if (spellCheckerInfo != null) {
                putSelectedSpellChecker(spellCheckerInfo.getId());
            } else {
                putSelectedSpellChecker("");
            }
            putSelectedSpellCheckerSubtype(0);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void initializeTextServicesData() {
            this.mSpellCheckerList.clear();
            this.mSpellCheckerMap.clear();
            this.mUpdateCount++;
            java.util.List queryIntentServicesAsUser = this.mContext.getPackageManager().queryIntentServicesAsUser(new android.content.Intent("android.service.textservice.SpellCheckerService"), 128, this.mUserId);
            int size = queryIntentServicesAsUser.size();
            for (int i = 0; i < size; i++) {
                android.content.pm.ResolveInfo resolveInfo = (android.content.pm.ResolveInfo) queryIntentServicesAsUser.get(i);
                android.content.pm.ServiceInfo serviceInfo = resolveInfo.serviceInfo;
                android.content.ComponentName componentName = new android.content.ComponentName(serviceInfo.packageName, serviceInfo.name);
                if (!"android.permission.BIND_TEXT_SERVICE".equals(serviceInfo.permission)) {
                    android.util.Slog.w(com.android.server.textservices.TextServicesManagerService.TAG, "Skipping text service " + componentName + ": it does not require the permission android.permission.BIND_TEXT_SERVICE");
                } else {
                    try {
                        android.view.textservice.SpellCheckerInfo spellCheckerInfo = new android.view.textservice.SpellCheckerInfo(this.mContext, resolveInfo);
                        if (spellCheckerInfo.getSubtypeCount() <= 0) {
                            android.util.Slog.w(com.android.server.textservices.TextServicesManagerService.TAG, "Skipping text service " + componentName + ": it does not contain subtypes.");
                        } else {
                            this.mSpellCheckerList.add(spellCheckerInfo);
                            this.mSpellCheckerMap.put(spellCheckerInfo.getId(), spellCheckerInfo);
                        }
                    } catch (java.io.IOException e) {
                        android.util.Slog.w(com.android.server.textservices.TextServicesManagerService.TAG, "Unable to load the spell checker " + componentName, e);
                    } catch (org.xmlpull.v1.XmlPullParserException e2) {
                        android.util.Slog.w(com.android.server.textservices.TextServicesManagerService.TAG, "Unable to load the spell checker " + componentName, e2);
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dump(java.io.PrintWriter printWriter) {
            printWriter.println("  User #" + this.mUserId);
            printWriter.println("  Spell Checkers:");
            printWriter.println("  Spell Checkers: mUpdateCount=" + this.mUpdateCount);
            int i = 0;
            for (android.view.textservice.SpellCheckerInfo spellCheckerInfo : this.mSpellCheckerMap.values()) {
                printWriter.println("  Spell Checker #" + i);
                spellCheckerInfo.dump(printWriter, "    ");
                i++;
            }
            printWriter.println("");
            printWriter.println("  Spell Checker Bind Groups:");
            for (java.util.Map.Entry<java.lang.String, com.android.server.textservices.TextServicesManagerService.SpellCheckerBindGroup> entry : this.mSpellCheckerBindGroups.entrySet()) {
                com.android.server.textservices.TextServicesManagerService.SpellCheckerBindGroup value = entry.getValue();
                printWriter.println("    " + entry.getKey() + " " + value + ":");
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("      mInternalConnection=");
                sb.append(value.mInternalConnection);
                printWriter.println(sb.toString());
                printWriter.println("      mSpellChecker=" + value.mSpellChecker);
                printWriter.println("      mUnbindCalled=" + value.mUnbindCalled);
                printWriter.println("      mConnected=" + value.mConnected);
                int size = value.mPendingSessionRequests.size();
                for (int i2 = 0; i2 < size; i2++) {
                    com.android.server.textservices.TextServicesManagerService.SessionRequest sessionRequest = (com.android.server.textservices.TextServicesManagerService.SessionRequest) value.mPendingSessionRequests.get(i2);
                    printWriter.println("      Pending Request #" + i2 + ":");
                    java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
                    sb2.append("        mTsListener=");
                    sb2.append(sessionRequest.mTsListener);
                    printWriter.println(sb2.toString());
                    printWriter.println("        mScListener=" + sessionRequest.mScListener);
                    printWriter.println("        mScLocale=" + sessionRequest.mLocale + " mUid=" + sessionRequest.mUid);
                }
                int size2 = value.mOnGoingSessionRequests.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    com.android.server.textservices.TextServicesManagerService.SessionRequest sessionRequest2 = (com.android.server.textservices.TextServicesManagerService.SessionRequest) value.mOnGoingSessionRequests.get(i3);
                    printWriter.println("      On going Request #" + i3 + ":");
                    java.lang.StringBuilder sb3 = new java.lang.StringBuilder();
                    sb3.append("        mTsListener=");
                    sb3.append(sessionRequest2.mTsListener);
                    printWriter.println(sb3.toString());
                    printWriter.println("        mScListener=" + sessionRequest2.mScListener);
                    printWriter.println("        mScLocale=" + sessionRequest2.mLocale + " mUid=" + sessionRequest2.mUid);
                }
                int registeredCallbackCount = value.mListeners.getRegisteredCallbackCount();
                for (int i4 = 0; i4 < registeredCallbackCount; i4++) {
                    com.android.internal.textservice.ISpellCheckerSessionListener registeredCallbackItem = value.mListeners.getRegisteredCallbackItem(i4);
                    printWriter.println("      Listener #" + i4 + ":");
                    java.lang.StringBuilder sb4 = new java.lang.StringBuilder();
                    sb4.append("        mScListener=");
                    sb4.append(registeredCallbackItem);
                    printWriter.println(sb4.toString());
                    printWriter.println("        mGroup=" + value);
                }
            }
        }
    }

    public static final class Lifecycle extends com.android.server.SystemService {
        private com.android.server.textservices.TextServicesManagerService mService;

        public Lifecycle(android.content.Context context) {
            super(context);
            this.mService = new com.android.server.textservices.TextServicesManagerService(context);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            com.android.server.LocalServices.addService(com.android.server.textservices.TextServicesManagerInternal.class, new com.android.server.textservices.TextServicesManagerInternal() { // from class: com.android.server.textservices.TextServicesManagerService.Lifecycle.1
                @Override // com.android.server.textservices.TextServicesManagerInternal
                public android.view.textservice.SpellCheckerInfo getCurrentSpellCheckerForUser(int i) {
                    return com.android.server.textservices.TextServicesManagerService.Lifecycle.this.mService.getCurrentSpellCheckerForUser(i);
                }
            });
            publishBinderService("textservices", this.mService);
        }

        @Override // com.android.server.SystemService
        public void onUserStopping(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            this.mService.onStopUser(targetUser.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public void onUserUnlocking(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            this.mService.onUnlockUser(targetUser.getUserIdentifier());
        }
    }

    void onStopUser(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.textservices.TextServicesManagerService.TextServicesData textServicesData = this.mUserData.get(i);
                if (textServicesData == null) {
                    return;
                }
                unbindServiceLocked(textServicesData);
                this.mUserData.remove(i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void onUnlockUser(int i) {
        synchronized (this.mLock) {
            initializeInternalStateLocked(i);
        }
    }

    public TextServicesManagerService(android.content.Context context) {
        this.mContext = context;
        this.mUserManager = (android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class);
        this.mMonitor.register(context, (android.os.Looper) null, android.os.UserHandle.ALL, true);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void initializeInternalStateLocked(int i) {
        com.android.server.textservices.TextServicesManagerService.TextServicesData textServicesData = this.mUserData.get(i);
        if (textServicesData == null) {
            textServicesData = new com.android.server.textservices.TextServicesManagerService.TextServicesData(i, this.mContext);
            this.mUserData.put(i, textServicesData);
        }
        textServicesData.initializeTextServicesData();
        if (textServicesData.getCurrentSpellChecker() == null) {
            setCurrentSpellCheckerLocked(findAvailSystemSpellCheckerLocked(null, textServicesData), textServicesData);
        }
    }

    private final class TextServicesMonitor extends com.android.internal.content.PackageMonitor {
        private TextServicesMonitor() {
        }

        public void onSomePackagesChanged() {
            android.view.textservice.SpellCheckerInfo findAvailSystemSpellCheckerLocked;
            int changingUserId = getChangingUserId();
            synchronized (com.android.server.textservices.TextServicesManagerService.this.mLock) {
                try {
                    com.android.server.textservices.TextServicesManagerService.TextServicesData textServicesData = (com.android.server.textservices.TextServicesManagerService.TextServicesData) com.android.server.textservices.TextServicesManagerService.this.mUserData.get(changingUserId);
                    if (textServicesData == null) {
                        return;
                    }
                    android.view.textservice.SpellCheckerInfo currentSpellChecker = textServicesData.getCurrentSpellChecker();
                    textServicesData.initializeTextServicesData();
                    if (textServicesData.isSpellCheckerEnabled()) {
                        if (currentSpellChecker == null) {
                            com.android.server.textservices.TextServicesManagerService.this.setCurrentSpellCheckerLocked(com.android.server.textservices.TextServicesManagerService.this.findAvailSystemSpellCheckerLocked(null, textServicesData), textServicesData);
                        } else {
                            java.lang.String packageName = currentSpellChecker.getPackageName();
                            int isPackageDisappearing = isPackageDisappearing(packageName);
                            if ((isPackageDisappearing == 3 || isPackageDisappearing == 2) && ((findAvailSystemSpellCheckerLocked = com.android.server.textservices.TextServicesManagerService.this.findAvailSystemSpellCheckerLocked(packageName, textServicesData)) == null || !findAvailSystemSpellCheckerLocked.getId().equals(currentSpellChecker.getId()))) {
                                com.android.server.textservices.TextServicesManagerService.this.setCurrentSpellCheckerLocked(findAvailSystemSpellCheckerLocked, textServicesData);
                            }
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private boolean bindCurrentSpellCheckerService(android.content.Intent intent, android.content.ServiceConnection serviceConnection, int i, int i2) {
        if (intent == null || serviceConnection == null) {
            android.util.Slog.e(TAG, "--- bind failed: service = " + intent + ", conn = " + serviceConnection + ", userId =" + i2);
            return false;
        }
        return this.mContext.bindServiceAsUser(intent, serviceConnection, i, android.os.UserHandle.of(i2));
    }

    private void unbindServiceLocked(com.android.server.textservices.TextServicesManagerService.TextServicesData textServicesData) {
        java.util.HashMap hashMap = textServicesData.mSpellCheckerBindGroups;
        java.util.Iterator it = hashMap.values().iterator();
        while (it.hasNext()) {
            ((com.android.server.textservices.TextServicesManagerService.SpellCheckerBindGroup) it.next()).removeAllLocked();
        }
        hashMap.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.view.textservice.SpellCheckerInfo findAvailSystemSpellCheckerLocked(java.lang.String str, com.android.server.textservices.TextServicesManagerService.TextServicesData textServicesData) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator it = textServicesData.mSpellCheckerList.iterator();
        while (it.hasNext()) {
            android.view.textservice.SpellCheckerInfo spellCheckerInfo = (android.view.textservice.SpellCheckerInfo) it.next();
            if ((1 & spellCheckerInfo.getServiceInfo().applicationInfo.flags) != 0) {
                arrayList.add(spellCheckerInfo);
            }
        }
        int size = arrayList.size();
        if (size == 0) {
            android.util.Slog.w(TAG, "no available spell checker services found");
            return null;
        }
        if (str != null) {
            for (int i = 0; i < size; i++) {
                android.view.textservice.SpellCheckerInfo spellCheckerInfo2 = (android.view.textservice.SpellCheckerInfo) arrayList.get(i);
                if (str.equals(spellCheckerInfo2.getPackageName())) {
                    return spellCheckerInfo2;
                }
            }
        }
        java.util.ArrayList<java.util.Locale> suitableLocalesForSpellChecker = com.android.server.textservices.LocaleUtils.getSuitableLocalesForSpellChecker(this.mContext.getResources().getConfiguration().locale);
        int size2 = suitableLocalesForSpellChecker.size();
        for (int i2 = 0; i2 < size2; i2++) {
            java.util.Locale locale = suitableLocalesForSpellChecker.get(i2);
            for (int i3 = 0; i3 < size; i3++) {
                android.view.textservice.SpellCheckerInfo spellCheckerInfo3 = (android.view.textservice.SpellCheckerInfo) arrayList.get(i3);
                int subtypeCount = spellCheckerInfo3.getSubtypeCount();
                for (int i4 = 0; i4 < subtypeCount; i4++) {
                    if (locale.equals(com.android.internal.inputmethod.SubtypeLocaleUtils.constructLocaleFromString(spellCheckerInfo3.getSubtypeAt(i4).getLocale()))) {
                        return spellCheckerInfo3;
                    }
                }
            }
        }
        if (size > 1) {
            android.util.Slog.w(TAG, "more than one spell checker service found, picking first");
        }
        return (android.view.textservice.SpellCheckerInfo) arrayList.get(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public android.view.textservice.SpellCheckerInfo getCurrentSpellCheckerForUser(int i) {
        android.view.textservice.SpellCheckerInfo currentSpellChecker;
        synchronized (this.mLock) {
            try {
                com.android.server.textservices.TextServicesManagerService.TextServicesData textServicesData = this.mUserData.get(i);
                currentSpellChecker = textServicesData != null ? textServicesData.getCurrentSpellChecker() : null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return currentSpellChecker;
    }

    public android.view.textservice.SpellCheckerInfo getCurrentSpellChecker(int i, java.lang.String str) {
        verifyUser(i);
        synchronized (this.mLock) {
            try {
                com.android.server.textservices.TextServicesManagerService.TextServicesData dataFromCallingUserIdLocked = getDataFromCallingUserIdLocked(i);
                if (dataFromCallingUserIdLocked == null) {
                    return null;
                }
                return dataFromCallingUserIdLocked.getCurrentSpellChecker();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0096 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0099 A[LOOP:1: B:34:0x0099->B:44:0x00c1, LOOP_START, PHI: r2 r3
      0x0099: PHI (r2v1 android.view.textservice.SpellCheckerSubtype) = (r2v0 android.view.textservice.SpellCheckerSubtype), (r2v2 android.view.textservice.SpellCheckerSubtype) binds: [B:32:0x0094, B:44:0x00c1] A[DONT_GENERATE, DONT_INLINE]
      0x0099: PHI (r3v1 int) = (r3v0 int), (r3v2 int) binds: [B:32:0x0094, B:44:0x00c1] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public android.view.textservice.SpellCheckerSubtype getCurrentSpellCheckerSubtype(int i, boolean z) {
        android.view.inputmethod.InputMethodSubtype currentInputMethodSubtype;
        java.util.Locale locale;
        verifyUser(i);
        synchronized (this.mLock) {
            try {
                com.android.server.textservices.TextServicesManagerService.TextServicesData dataFromCallingUserIdLocked = getDataFromCallingUserIdLocked(i);
                android.view.textservice.SpellCheckerSubtype spellCheckerSubtype = null;
                if (dataFromCallingUserIdLocked == null) {
                    return null;
                }
                int i2 = 0;
                int selectedSpellCheckerSubtype = dataFromCallingUserIdLocked.getSelectedSpellCheckerSubtype(0);
                android.view.textservice.SpellCheckerInfo currentSpellChecker = dataFromCallingUserIdLocked.getCurrentSpellChecker();
                java.util.Locale locale2 = this.mContext.getResources().getConfiguration().locale;
                if (currentSpellChecker == null || currentSpellChecker.getSubtypeCount() == 0) {
                    return null;
                }
                if (selectedSpellCheckerSubtype == 0 && !z) {
                    return null;
                }
                int subtypeCount = currentSpellChecker.getSubtypeCount();
                if (selectedSpellCheckerSubtype != 0) {
                    while (i2 < subtypeCount) {
                        android.view.textservice.SpellCheckerSubtype subtypeAt = currentSpellChecker.getSubtypeAt(i2);
                        if (subtypeAt.hashCode() != selectedSpellCheckerSubtype) {
                            i2++;
                        } else {
                            return subtypeAt;
                        }
                    }
                    return null;
                }
                com.android.internal.view.IInputMethodManager asInterface = com.android.internal.view.IInputMethodManager.Stub.asInterface(android.os.ServiceManager.getService("input_method"));
                if (asInterface != null) {
                    try {
                        currentInputMethodSubtype = asInterface.getCurrentInputMethodSubtype(i);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(TAG, "Exception getting input method subtype for " + i, e);
                    }
                    if (currentInputMethodSubtype != null) {
                        java.lang.String locale3 = currentInputMethodSubtype.getLocale();
                        if (!android.text.TextUtils.isEmpty(locale3)) {
                            locale = com.android.internal.inputmethod.SubtypeLocaleUtils.constructLocaleFromString(locale3);
                            if (locale != null) {
                                locale2 = locale;
                            }
                            if (locale2 == null) {
                                return null;
                            }
                            while (i2 < currentSpellChecker.getSubtypeCount()) {
                                android.view.textservice.SpellCheckerSubtype subtypeAt2 = currentSpellChecker.getSubtypeAt(i2);
                                java.util.Locale localeObject = subtypeAt2.getLocaleObject();
                                if (java.util.Objects.equals(localeObject, locale2)) {
                                    return subtypeAt2;
                                }
                                if (spellCheckerSubtype == null && localeObject != null && android.text.TextUtils.equals(locale2.getLanguage(), localeObject.getLanguage())) {
                                    spellCheckerSubtype = subtypeAt2;
                                }
                                i2++;
                            }
                            return spellCheckerSubtype;
                        }
                    }
                    locale = null;
                    if (locale != null) {
                    }
                    if (locale2 == null) {
                    }
                }
                locale = null;
                if (locale != null) {
                }
                if (locale2 == null) {
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void getSpellCheckerService(int i, java.lang.String str, java.lang.String str2, com.android.internal.textservice.ITextServicesSessionListener iTextServicesSessionListener, com.android.internal.textservice.ISpellCheckerSessionListener iSpellCheckerSessionListener, android.os.Bundle bundle, int i2) {
        verifyUser(i);
        if (android.text.TextUtils.isEmpty(str) || iTextServicesSessionListener == null || iSpellCheckerSessionListener == null) {
            android.util.Slog.e(TAG, "getSpellCheckerService: Invalid input.");
            return;
        }
        synchronized (this.mLock) {
            try {
                com.android.server.textservices.TextServicesManagerService.TextServicesData dataFromCallingUserIdLocked = getDataFromCallingUserIdLocked(i);
                if (dataFromCallingUserIdLocked == null) {
                    return;
                }
                java.util.HashMap hashMap = dataFromCallingUserIdLocked.mSpellCheckerMap;
                if (hashMap.containsKey(str)) {
                    android.view.textservice.SpellCheckerInfo spellCheckerInfo = (android.view.textservice.SpellCheckerInfo) hashMap.get(str);
                    int callingUid = android.os.Binder.getCallingUid();
                    if (canCallerAccessSpellChecker(spellCheckerInfo, callingUid, i)) {
                        com.android.server.textservices.TextServicesManagerService.SpellCheckerBindGroup spellCheckerBindGroup = (com.android.server.textservices.TextServicesManagerService.SpellCheckerBindGroup) dataFromCallingUserIdLocked.mSpellCheckerBindGroups.get(str);
                        if (spellCheckerBindGroup == null) {
                            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                            try {
                                spellCheckerBindGroup = startSpellCheckerServiceInnerLocked(spellCheckerInfo, dataFromCallingUserIdLocked);
                                if (spellCheckerBindGroup == null) {
                                    return;
                                }
                            } finally {
                                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            }
                        }
                        spellCheckerBindGroup.getISpellCheckerSessionOrQueueLocked(new com.android.server.textservices.TextServicesManagerService.SessionRequest(callingUid, str2, iTextServicesSessionListener, iSpellCheckerSessionListener, bundle, i2));
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isSpellCheckerEnabled(int i) {
        verifyUser(i);
        synchronized (this.mLock) {
            try {
                com.android.server.textservices.TextServicesManagerService.TextServicesData dataFromCallingUserIdLocked = getDataFromCallingUserIdLocked(i);
                if (dataFromCallingUserIdLocked == null) {
                    return false;
                }
                return dataFromCallingUserIdLocked.isSpellCheckerEnabled();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    private com.android.server.textservices.TextServicesManagerService.SpellCheckerBindGroup startSpellCheckerServiceInnerLocked(android.view.textservice.SpellCheckerInfo spellCheckerInfo, com.android.server.textservices.TextServicesManagerService.TextServicesData textServicesData) {
        java.lang.String id = spellCheckerInfo.getId();
        com.android.server.textservices.TextServicesManagerService.InternalServiceConnection internalServiceConnection = new com.android.server.textservices.TextServicesManagerService.InternalServiceConnection(id, textServicesData.mSpellCheckerBindGroups);
        android.content.Intent intent = new android.content.Intent("android.service.textservice.SpellCheckerService");
        intent.setComponent(spellCheckerInfo.getComponent());
        if (!bindCurrentSpellCheckerService(intent, internalServiceConnection, 8388609, textServicesData.mUserId)) {
            android.util.Slog.e(TAG, "Failed to get a spell checker service.");
            return null;
        }
        com.android.server.textservices.TextServicesManagerService.SpellCheckerBindGroup spellCheckerBindGroup = new com.android.server.textservices.TextServicesManagerService.SpellCheckerBindGroup(internalServiceConnection);
        textServicesData.mSpellCheckerBindGroups.put(id, spellCheckerBindGroup);
        return spellCheckerBindGroup;
    }

    public android.view.textservice.SpellCheckerInfo[] getEnabledSpellCheckers(int i) {
        verifyUser(i);
        synchronized (this.mLock) {
            try {
                com.android.server.textservices.TextServicesManagerService.TextServicesData dataFromCallingUserIdLocked = getDataFromCallingUserIdLocked(i);
                if (dataFromCallingUserIdLocked == null) {
                    return null;
                }
                java.util.ArrayList arrayList = new java.util.ArrayList(dataFromCallingUserIdLocked.mSpellCheckerList);
                int size = arrayList.size();
                int callingUid = android.os.Binder.getCallingUid();
                for (int i2 = size - 1; i2 >= 0; i2--) {
                    if (!canCallerAccessSpellChecker((android.view.textservice.SpellCheckerInfo) arrayList.get(i2), callingUid, i)) {
                        arrayList.remove(i2);
                    }
                }
                if (arrayList.isEmpty()) {
                    return null;
                }
                return (android.view.textservice.SpellCheckerInfo[]) arrayList.toArray(new android.view.textservice.SpellCheckerInfo[arrayList.size()]);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void finishSpellCheckerService(int i, com.android.internal.textservice.ISpellCheckerSessionListener iSpellCheckerSessionListener) {
        verifyUser(i);
        synchronized (this.mLock) {
            try {
                com.android.server.textservices.TextServicesManagerService.TextServicesData dataFromCallingUserIdLocked = getDataFromCallingUserIdLocked(i);
                if (dataFromCallingUserIdLocked == null) {
                    return;
                }
                java.util.ArrayList arrayList = new java.util.ArrayList();
                for (com.android.server.textservices.TextServicesManagerService.SpellCheckerBindGroup spellCheckerBindGroup : dataFromCallingUserIdLocked.mSpellCheckerBindGroups.values()) {
                    if (spellCheckerBindGroup != null) {
                        arrayList.add(spellCheckerBindGroup);
                    }
                }
                int size = arrayList.size();
                for (int i2 = 0; i2 < size; i2++) {
                    ((com.android.server.textservices.TextServicesManagerService.SpellCheckerBindGroup) arrayList.get(i2)).removeListener(iSpellCheckerSessionListener);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void verifyUser(int i) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        if (i != callingUserId) {
            this.mContext.enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "Cross-user interaction requires INTERACT_ACROSS_USERS_FULL. userId=" + i + " callingUserId=" + callingUserId);
        }
    }

    private boolean canCallerAccessSpellChecker(@android.annotation.NonNull android.view.textservice.SpellCheckerInfo spellCheckerInfo, int i, int i2) {
        android.view.textservice.SpellCheckerInfo currentSpellCheckerForUser = getCurrentSpellCheckerForUser(i2);
        if (currentSpellCheckerForUser == null || !currentSpellCheckerForUser.getId().equals(spellCheckerInfo.getId())) {
            return !((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).filterAppAccess(spellCheckerInfo.getPackageName(), i, i2);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCurrentSpellCheckerLocked(@android.annotation.Nullable android.view.textservice.SpellCheckerInfo spellCheckerInfo, com.android.server.textservices.TextServicesManagerService.TextServicesData textServicesData) {
        if (spellCheckerInfo != null) {
            spellCheckerInfo.getId();
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            textServicesData.setCurrentSpellChecker(spellCheckerInfo);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            if (strArr.length == 0 || (strArr.length == 1 && strArr[0].equals("-a"))) {
                synchronized (this.mLock) {
                    try {
                        printWriter.println("Current Text Services Manager state:");
                        printWriter.println("  Users:");
                        int size = this.mUserData.size();
                        for (int i = 0; i < size; i++) {
                            this.mUserData.valueAt(i).dump(printWriter);
                        }
                    } finally {
                    }
                }
                return;
            }
            if (strArr.length != 2 || !strArr[0].equals("--user")) {
                printWriter.println("Invalid arguments to text services.");
                return;
            }
            int parseInt = java.lang.Integer.parseInt(strArr[1]);
            if (this.mUserManager.getUserInfo(parseInt) == null) {
                printWriter.println("Non-existent user.");
                return;
            }
            com.android.server.textservices.TextServicesManagerService.TextServicesData textServicesData = this.mUserData.get(parseInt);
            if (textServicesData == null) {
                printWriter.println("User needs to unlock first.");
                return;
            }
            synchronized (this.mLock) {
                printWriter.println("Current Text Services Manager state:");
                printWriter.println("  User " + parseInt + ":");
                textServicesData.dump(printWriter);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.textservices.TextServicesManagerService.TextServicesData getDataFromCallingUserIdLocked(int i) {
        return this.mUserData.get(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class SessionRequest {

        @android.annotation.Nullable
        public final android.os.Bundle mBundle;

        @android.annotation.Nullable
        public final java.lang.String mLocale;

        @android.annotation.NonNull
        public final com.android.internal.textservice.ISpellCheckerSessionListener mScListener;
        public final int mSupportedAttributes;

        @android.annotation.NonNull
        public final com.android.internal.textservice.ITextServicesSessionListener mTsListener;
        public final int mUid;

        SessionRequest(int i, @android.annotation.Nullable java.lang.String str, @android.annotation.NonNull com.android.internal.textservice.ITextServicesSessionListener iTextServicesSessionListener, @android.annotation.NonNull com.android.internal.textservice.ISpellCheckerSessionListener iSpellCheckerSessionListener, @android.annotation.Nullable android.os.Bundle bundle, int i2) {
            this.mUid = i;
            this.mLocale = str;
            this.mTsListener = iTextServicesSessionListener;
            this.mScListener = iSpellCheckerSessionListener;
            this.mBundle = bundle;
            this.mSupportedAttributes = i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class SpellCheckerBindGroup {
        private boolean mConnected;
        private final com.android.server.textservices.TextServicesManagerService.InternalServiceConnection mInternalConnection;
        private com.android.internal.textservice.ISpellCheckerService mSpellChecker;

        @android.annotation.NonNull
        java.util.HashMap<java.lang.String, com.android.server.textservices.TextServicesManagerService.SpellCheckerBindGroup> mSpellCheckerBindGroups;
        private boolean mUnbindCalled;
        private final java.lang.String TAG = com.android.server.textservices.TextServicesManagerService.SpellCheckerBindGroup.class.getSimpleName();
        private final java.util.ArrayList<com.android.server.textservices.TextServicesManagerService.SessionRequest> mPendingSessionRequests = new java.util.ArrayList<>();
        private final java.util.ArrayList<com.android.server.textservices.TextServicesManagerService.SessionRequest> mOnGoingSessionRequests = new java.util.ArrayList<>();
        private final com.android.server.textservices.TextServicesManagerService.InternalDeathRecipients mListeners = new com.android.server.textservices.TextServicesManagerService.InternalDeathRecipients(this);

        public SpellCheckerBindGroup(com.android.server.textservices.TextServicesManagerService.InternalServiceConnection internalServiceConnection) {
            this.mInternalConnection = internalServiceConnection;
            this.mSpellCheckerBindGroups = internalServiceConnection.mSpellCheckerBindGroups;
        }

        public void onServiceConnectedLocked(com.android.internal.textservice.ISpellCheckerService iSpellCheckerService) {
            if (this.mUnbindCalled) {
                return;
            }
            this.mSpellChecker = iSpellCheckerService;
            this.mConnected = true;
            try {
                int size = this.mPendingSessionRequests.size();
                for (int i = 0; i < size; i++) {
                    com.android.server.textservices.TextServicesManagerService.SessionRequest sessionRequest = this.mPendingSessionRequests.get(i);
                    this.mSpellChecker.getISpellCheckerSession(sessionRequest.mLocale, sessionRequest.mScListener, sessionRequest.mBundle, sessionRequest.mSupportedAttributes, new com.android.server.textservices.TextServicesManagerService.ISpellCheckerServiceCallbackBinder(this, sessionRequest));
                    this.mOnGoingSessionRequests.add(sessionRequest);
                }
                this.mPendingSessionRequests.clear();
            } catch (android.os.RemoteException e) {
                removeAllLocked();
            }
            cleanLocked();
        }

        public void onServiceDisconnectedLocked() {
            this.mSpellChecker = null;
            this.mConnected = false;
        }

        public void removeListener(com.android.internal.textservice.ISpellCheckerSessionListener iSpellCheckerSessionListener) {
            synchronized (com.android.server.textservices.TextServicesManagerService.this.mLock) {
                this.mListeners.unregister(iSpellCheckerSessionListener);
                final android.os.IBinder asBinder = iSpellCheckerSessionListener.asBinder();
                java.util.function.Predicate<? super com.android.server.textservices.TextServicesManagerService.SessionRequest> predicate = new java.util.function.Predicate() { // from class: com.android.server.textservices.TextServicesManagerService$SpellCheckerBindGroup$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$removeListener$0;
                        lambda$removeListener$0 = com.android.server.textservices.TextServicesManagerService.SpellCheckerBindGroup.lambda$removeListener$0(asBinder, (com.android.server.textservices.TextServicesManagerService.SessionRequest) obj);
                        return lambda$removeListener$0;
                    }
                };
                this.mPendingSessionRequests.removeIf(predicate);
                this.mOnGoingSessionRequests.removeIf(predicate);
                cleanLocked();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$removeListener$0(android.os.IBinder iBinder, com.android.server.textservices.TextServicesManagerService.SessionRequest sessionRequest) {
            return sessionRequest.mScListener.asBinder() == iBinder;
        }

        private void cleanLocked() {
            if (this.mUnbindCalled || this.mListeners.getRegisteredCallbackCount() > 0 || !this.mPendingSessionRequests.isEmpty() || !this.mOnGoingSessionRequests.isEmpty()) {
                return;
            }
            java.lang.String str = this.mInternalConnection.mSciId;
            if (this.mSpellCheckerBindGroups.get(str) == this) {
                this.mSpellCheckerBindGroups.remove(str);
            }
            com.android.server.textservices.TextServicesManagerService.this.mContext.unbindService(this.mInternalConnection);
            this.mUnbindCalled = true;
        }

        public void removeAllLocked() {
            android.util.Slog.e(this.TAG, "Remove the spell checker bind unexpectedly.");
            for (int registeredCallbackCount = this.mListeners.getRegisteredCallbackCount() - 1; registeredCallbackCount >= 0; registeredCallbackCount--) {
                this.mListeners.unregister(this.mListeners.getRegisteredCallbackItem(registeredCallbackCount));
            }
            this.mPendingSessionRequests.clear();
            this.mOnGoingSessionRequests.clear();
            cleanLocked();
        }

        public void getISpellCheckerSessionOrQueueLocked(@android.annotation.NonNull com.android.server.textservices.TextServicesManagerService.SessionRequest sessionRequest) {
            if (this.mUnbindCalled) {
                return;
            }
            this.mListeners.register(sessionRequest.mScListener);
            if (!this.mConnected) {
                this.mPendingSessionRequests.add(sessionRequest);
                return;
            }
            try {
                this.mSpellChecker.getISpellCheckerSession(sessionRequest.mLocale, sessionRequest.mScListener, sessionRequest.mBundle, sessionRequest.mSupportedAttributes, new com.android.server.textservices.TextServicesManagerService.ISpellCheckerServiceCallbackBinder(this, sessionRequest));
                this.mOnGoingSessionRequests.add(sessionRequest);
            } catch (android.os.RemoteException e) {
                removeAllLocked();
            }
            cleanLocked();
        }

        void onSessionCreated(@android.annotation.Nullable com.android.internal.textservice.ISpellCheckerSession iSpellCheckerSession, @android.annotation.NonNull com.android.server.textservices.TextServicesManagerService.SessionRequest sessionRequest) {
            synchronized (com.android.server.textservices.TextServicesManagerService.this.mLock) {
                try {
                    if (this.mUnbindCalled) {
                        return;
                    }
                    if (this.mOnGoingSessionRequests.remove(sessionRequest)) {
                        try {
                            sessionRequest.mTsListener.onServiceConnected(iSpellCheckerSession);
                        } catch (android.os.RemoteException e) {
                        }
                    }
                    cleanLocked();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private final class InternalServiceConnection implements android.content.ServiceConnection {
        private final java.lang.String mSciId;

        @android.annotation.NonNull
        private final java.util.HashMap<java.lang.String, com.android.server.textservices.TextServicesManagerService.SpellCheckerBindGroup> mSpellCheckerBindGroups;

        public InternalServiceConnection(java.lang.String str, @android.annotation.NonNull java.util.HashMap<java.lang.String, com.android.server.textservices.TextServicesManagerService.SpellCheckerBindGroup> hashMap) {
            this.mSciId = str;
            this.mSpellCheckerBindGroups = hashMap;
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            synchronized (com.android.server.textservices.TextServicesManagerService.this.mLock) {
                onServiceConnectedInnerLocked(componentName, iBinder);
            }
        }

        private void onServiceConnectedInnerLocked(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            com.android.internal.textservice.ISpellCheckerService asInterface = com.android.internal.textservice.ISpellCheckerService.Stub.asInterface(iBinder);
            com.android.server.textservices.TextServicesManagerService.SpellCheckerBindGroup spellCheckerBindGroup = this.mSpellCheckerBindGroups.get(this.mSciId);
            if (spellCheckerBindGroup != null && this == spellCheckerBindGroup.mInternalConnection) {
                spellCheckerBindGroup.onServiceConnectedLocked(asInterface);
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
            synchronized (com.android.server.textservices.TextServicesManagerService.this.mLock) {
                onServiceDisconnectedInnerLocked(componentName);
            }
        }

        private void onServiceDisconnectedInnerLocked(android.content.ComponentName componentName) {
            com.android.server.textservices.TextServicesManagerService.SpellCheckerBindGroup spellCheckerBindGroup = this.mSpellCheckerBindGroups.get(this.mSciId);
            if (spellCheckerBindGroup != null && this == spellCheckerBindGroup.mInternalConnection) {
                spellCheckerBindGroup.onServiceDisconnectedLocked();
            }
        }
    }

    private static final class InternalDeathRecipients extends android.os.RemoteCallbackList<com.android.internal.textservice.ISpellCheckerSessionListener> {
        private final com.android.server.textservices.TextServicesManagerService.SpellCheckerBindGroup mGroup;

        public InternalDeathRecipients(com.android.server.textservices.TextServicesManagerService.SpellCheckerBindGroup spellCheckerBindGroup) {
            this.mGroup = spellCheckerBindGroup;
        }

        @Override // android.os.RemoteCallbackList
        public void onCallbackDied(com.android.internal.textservice.ISpellCheckerSessionListener iSpellCheckerSessionListener) {
            this.mGroup.removeListener(iSpellCheckerSessionListener);
        }
    }

    private static final class ISpellCheckerServiceCallbackBinder extends com.android.internal.textservice.ISpellCheckerServiceCallback.Stub {

        @com.android.internal.annotations.GuardedBy({"mCallbackLock"})
        @android.annotation.Nullable
        private java.lang.ref.WeakReference<com.android.server.textservices.TextServicesManagerService.SpellCheckerBindGroup> mBindGroup;

        @android.annotation.NonNull
        private final java.lang.Object mCallbackLock = new java.lang.Object();

        @com.android.internal.annotations.GuardedBy({"mCallbackLock"})
        @android.annotation.Nullable
        private java.lang.ref.WeakReference<com.android.server.textservices.TextServicesManagerService.SessionRequest> mRequest;

        ISpellCheckerServiceCallbackBinder(@android.annotation.NonNull com.android.server.textservices.TextServicesManagerService.SpellCheckerBindGroup spellCheckerBindGroup, @android.annotation.NonNull com.android.server.textservices.TextServicesManagerService.SessionRequest sessionRequest) {
            synchronized (this.mCallbackLock) {
                this.mBindGroup = new java.lang.ref.WeakReference<>(spellCheckerBindGroup);
                this.mRequest = new java.lang.ref.WeakReference<>(sessionRequest);
            }
        }

        public void onSessionCreated(@android.annotation.Nullable com.android.internal.textservice.ISpellCheckerSession iSpellCheckerSession) {
            synchronized (this.mCallbackLock) {
                if (this.mBindGroup == null || this.mRequest == null) {
                    return;
                }
                com.android.server.textservices.TextServicesManagerService.SpellCheckerBindGroup spellCheckerBindGroup = this.mBindGroup.get();
                com.android.server.textservices.TextServicesManagerService.SessionRequest sessionRequest = this.mRequest.get();
                this.mBindGroup = null;
                this.mRequest = null;
                if (spellCheckerBindGroup != null && sessionRequest != null) {
                    spellCheckerBindGroup.onSessionCreated(iSpellCheckerSession, sessionRequest);
                }
            }
        }
    }
}
