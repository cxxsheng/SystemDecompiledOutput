package com.android.server.inputmethod;

/* loaded from: classes2.dex */
final class SecureSettingsWrapper {
    private static final com.android.server.inputmethod.SecureSettingsWrapper.ReaderWriter NOOP;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"sUserMap"})
    private static final android.util.SparseArray<com.android.server.inputmethod.SecureSettingsWrapper.ReaderWriter> sUserMap;

    @android.annotation.Nullable
    private static volatile android.content.ContentResolver sContentResolver = null;
    private static final android.util.ArraySet<java.lang.String> CLONE_TO_MANAGED_PROFILE = new android.util.ArraySet<>();

    private interface ReaderWriter {
        int getInt(java.lang.String str, int i);

        @android.annotation.Nullable
        java.lang.String getString(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2);

        void putInt(java.lang.String str, int i);

        void putString(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2);
    }

    static {
        android.provider.Settings.Secure.getCloneToManagedProfileSettings(CLONE_TO_MANAGED_PROFILE);
        sUserMap = new android.util.SparseArray<>();
        NOOP = new com.android.server.inputmethod.SecureSettingsWrapper.ReaderWriter() { // from class: com.android.server.inputmethod.SecureSettingsWrapper.1
            @Override // com.android.server.inputmethod.SecureSettingsWrapper.ReaderWriter
            public void putString(java.lang.String str, java.lang.String str2) {
            }

            @Override // com.android.server.inputmethod.SecureSettingsWrapper.ReaderWriter
            public java.lang.String getString(java.lang.String str, java.lang.String str2) {
                return str2;
            }

            @Override // com.android.server.inputmethod.SecureSettingsWrapper.ReaderWriter
            public void putInt(java.lang.String str, int i) {
            }

            @Override // com.android.server.inputmethod.SecureSettingsWrapper.ReaderWriter
            public int getInt(java.lang.String str, int i) {
                return i;
            }
        };
    }

    private SecureSettingsWrapper() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getUserIdForClonedSettings(@android.annotation.NonNull java.lang.String str, int i) {
        if (!CLONE_TO_MANAGED_PROFILE.contains(str)) {
            return i;
        }
        return ((com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class)).getProfileParentId(i);
    }

    private static class UnlockedUserImpl implements com.android.server.inputmethod.SecureSettingsWrapper.ReaderWriter {
        private final android.content.ContentResolver mContentResolver;
        private final int mUserId;

        UnlockedUserImpl(int i, @android.annotation.NonNull android.content.ContentResolver contentResolver) {
            this.mUserId = i;
            this.mContentResolver = contentResolver;
        }

        @Override // com.android.server.inputmethod.SecureSettingsWrapper.ReaderWriter
        public void putString(java.lang.String str, java.lang.String str2) {
            android.provider.Settings.Secure.putStringForUser(this.mContentResolver, str, str2, com.android.server.inputmethod.SecureSettingsWrapper.getUserIdForClonedSettings(str, this.mUserId));
        }

        @Override // com.android.server.inputmethod.SecureSettingsWrapper.ReaderWriter
        @android.annotation.Nullable
        public java.lang.String getString(java.lang.String str, java.lang.String str2) {
            java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(this.mContentResolver, str, this.mUserId);
            return stringForUser != null ? stringForUser : str2;
        }

        @Override // com.android.server.inputmethod.SecureSettingsWrapper.ReaderWriter
        public void putInt(java.lang.String str, int i) {
            android.provider.Settings.Secure.putIntForUser(this.mContentResolver, str, i, com.android.server.inputmethod.SecureSettingsWrapper.getUserIdForClonedSettings(str, this.mUserId));
        }

        @Override // com.android.server.inputmethod.SecureSettingsWrapper.ReaderWriter
        public int getInt(java.lang.String str, int i) {
            return android.provider.Settings.Secure.getIntForUser(this.mContentResolver, str, i, this.mUserId);
        }
    }

    private static final class LockedUserImpl extends com.android.server.inputmethod.SecureSettingsWrapper.UnlockedUserImpl {

        @com.android.internal.annotations.GuardedBy({"mNonPersistentKeyValues"})
        private final android.util.ArrayMap<java.lang.String, java.lang.String> mNonPersistentKeyValues;

        LockedUserImpl(int i, @android.annotation.NonNull android.content.ContentResolver contentResolver) {
            super(i, contentResolver);
            this.mNonPersistentKeyValues = new android.util.ArrayMap<>();
        }

        @Override // com.android.server.inputmethod.SecureSettingsWrapper.UnlockedUserImpl, com.android.server.inputmethod.SecureSettingsWrapper.ReaderWriter
        public void putString(java.lang.String str, java.lang.String str2) {
            synchronized (this.mNonPersistentKeyValues) {
                this.mNonPersistentKeyValues.put(str, str2);
            }
        }

        @Override // com.android.server.inputmethod.SecureSettingsWrapper.UnlockedUserImpl, com.android.server.inputmethod.SecureSettingsWrapper.ReaderWriter
        @android.annotation.Nullable
        public java.lang.String getString(java.lang.String str, java.lang.String str2) {
            synchronized (this.mNonPersistentKeyValues) {
                try {
                    if (this.mNonPersistentKeyValues.containsKey(str)) {
                        java.lang.String str3 = this.mNonPersistentKeyValues.get(str);
                        if (str3 != null) {
                            str2 = str3;
                        }
                        return str2;
                    }
                    return super.getString(str, str2);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.inputmethod.SecureSettingsWrapper.UnlockedUserImpl, com.android.server.inputmethod.SecureSettingsWrapper.ReaderWriter
        public void putInt(java.lang.String str, int i) {
            synchronized (this.mNonPersistentKeyValues) {
                this.mNonPersistentKeyValues.put(str, java.lang.String.valueOf(i));
            }
        }

        @Override // com.android.server.inputmethod.SecureSettingsWrapper.UnlockedUserImpl, com.android.server.inputmethod.SecureSettingsWrapper.ReaderWriter
        public int getInt(java.lang.String str, int i) {
            synchronized (this.mNonPersistentKeyValues) {
                try {
                    if (this.mNonPersistentKeyValues.containsKey(str)) {
                        java.lang.String str2 = this.mNonPersistentKeyValues.get(str);
                        if (str2 != null) {
                            i = java.lang.Integer.parseInt(str2);
                        }
                        return i;
                    }
                    return super.getInt(str, i);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private static com.android.server.inputmethod.SecureSettingsWrapper.ReaderWriter createImpl(@android.annotation.NonNull com.android.server.pm.UserManagerInternal userManagerInternal, int i) {
        if (userManagerInternal.isUserUnlockingOrUnlocked(i)) {
            return new com.android.server.inputmethod.SecureSettingsWrapper.UnlockedUserImpl(i, sContentResolver);
        }
        return new com.android.server.inputmethod.SecureSettingsWrapper.LockedUserImpl(i, sContentResolver);
    }

    @android.annotation.NonNull
    private static com.android.server.inputmethod.SecureSettingsWrapper.ReaderWriter putOrGet(int i, @android.annotation.NonNull com.android.server.inputmethod.SecureSettingsWrapper.ReaderWriter readerWriter) {
        boolean z = readerWriter instanceof com.android.server.inputmethod.SecureSettingsWrapper.UnlockedUserImpl;
        synchronized (sUserMap) {
            try {
                com.android.server.inputmethod.SecureSettingsWrapper.ReaderWriter readerWriter2 = sUserMap.get(i);
                if (readerWriter2 == null) {
                    sUserMap.put(i, readerWriter);
                    return readerWriter;
                }
                if (!(readerWriter2 instanceof com.android.server.inputmethod.SecureSettingsWrapper.LockedUserImpl) || !z) {
                    return readerWriter2;
                }
                sUserMap.put(i, readerWriter);
                return readerWriter;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.NonNull
    private static com.android.server.inputmethod.SecureSettingsWrapper.ReaderWriter get(int i) {
        synchronized (sUserMap) {
            try {
                com.android.server.inputmethod.SecureSettingsWrapper.ReaderWriter readerWriter = sUserMap.get(i);
                if (readerWriter != null) {
                    return readerWriter;
                }
                com.android.server.pm.UserManagerInternal userManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
                if (!userManagerInternal.exists(i)) {
                    return NOOP;
                }
                return putOrGet(i, createImpl(userManagerInternal, i));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    static void onStart(@android.annotation.NonNull android.content.Context context) {
        sContentResolver = context.getContentResolver();
        final int currentUserId = ((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class)).getCurrentUserId();
        com.android.server.pm.UserManagerInternal userManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
        putOrGet(currentUserId, createImpl(userManagerInternal, currentUserId));
        userManagerInternal.addUserLifecycleListener(new com.android.server.pm.UserManagerInternal.UserLifecycleListener() { // from class: com.android.server.inputmethod.SecureSettingsWrapper.2
            @Override // com.android.server.pm.UserManagerInternal.UserLifecycleListener
            public void onUserRemoved(android.content.pm.UserInfo userInfo) {
                synchronized (com.android.server.inputmethod.SecureSettingsWrapper.sUserMap) {
                    com.android.server.inputmethod.SecureSettingsWrapper.sUserMap.remove(currentUserId);
                }
            }
        });
    }

    static void onUserStarting(int i) {
        putOrGet(i, createImpl((com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class), i));
    }

    static void onUserUnlocking(int i) {
        putOrGet(i, new com.android.server.inputmethod.SecureSettingsWrapper.UnlockedUserImpl(i, sContentResolver));
    }

    static void putString(java.lang.String str, java.lang.String str2, int i) {
        get(i).putString(str, str2);
    }

    @android.annotation.Nullable
    static java.lang.String getString(java.lang.String str, java.lang.String str2, int i) {
        return get(i).getString(str, str2);
    }

    static void putInt(java.lang.String str, int i, int i2) {
        get(i2).putInt(str, i);
    }

    static int getInt(java.lang.String str, int i, int i2) {
        return get(i2).getInt(str, i);
    }

    static void putBoolean(java.lang.String str, boolean z, int i) {
        get(i).putInt(str, z ? 1 : 0);
    }

    static boolean getBoolean(java.lang.String str, boolean z, int i) {
        return get(i).getInt(str, z ? 1 : 0) == 1;
    }
}
