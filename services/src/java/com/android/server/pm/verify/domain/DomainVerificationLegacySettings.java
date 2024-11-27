package com.android.server.pm.verify.domain;

/* loaded from: classes2.dex */
public class DomainVerificationLegacySettings {
    public static final java.lang.String ATTR_PACKAGE_NAME = "packageName";
    public static final java.lang.String ATTR_STATE = "state";
    public static final java.lang.String ATTR_USER_ID = "userId";
    public static final java.lang.String TAG_DOMAIN_VERIFICATIONS_LEGACY = "domain-verifications-legacy";
    public static final java.lang.String TAG_USER_STATE = "user-state";
    public static final java.lang.String TAG_USER_STATES = "user-states";

    @android.annotation.NonNull
    private final java.lang.Object mLock = new java.lang.Object();

    @android.annotation.NonNull
    private final android.util.ArrayMap<java.lang.String, com.android.server.pm.verify.domain.DomainVerificationLegacySettings.LegacyState> mStates = new android.util.ArrayMap<>();

    public void add(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.IntentFilterVerificationInfo intentFilterVerificationInfo) {
        synchronized (this.mLock) {
            getOrCreateStateLocked(str).setInfo(intentFilterVerificationInfo);
        }
    }

    public void add(@android.annotation.NonNull java.lang.String str, int i, int i2) {
        synchronized (this.mLock) {
            getOrCreateStateLocked(str).addUserState(i, i2);
        }
    }

    public int getUserState(@android.annotation.NonNull java.lang.String str, int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.pm.verify.domain.DomainVerificationLegacySettings.LegacyState legacyState = this.mStates.get(str);
                if (legacyState != null) {
                    return legacyState.getUserState(i);
                }
                return 0;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    public android.util.SparseIntArray getUserStates(@android.annotation.NonNull java.lang.String str) {
        synchronized (this.mLock) {
            try {
                com.android.server.pm.verify.domain.DomainVerificationLegacySettings.LegacyState legacyState = this.mStates.get(str);
                if (legacyState != null) {
                    return legacyState.getUserStates();
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    public android.content.pm.IntentFilterVerificationInfo remove(@android.annotation.NonNull java.lang.String str) {
        synchronized (this.mLock) {
            try {
                com.android.server.pm.verify.domain.DomainVerificationLegacySettings.LegacyState legacyState = this.mStates.get(str);
                if (legacyState != null && !legacyState.isAttached()) {
                    legacyState.markAttached();
                    return legacyState.getInfo();
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.pm.verify.domain.DomainVerificationLegacySettings.LegacyState getOrCreateStateLocked(@android.annotation.NonNull java.lang.String str) {
        com.android.server.pm.verify.domain.DomainVerificationLegacySettings.LegacyState legacyState = this.mStates.get(str);
        if (legacyState == null) {
            com.android.server.pm.verify.domain.DomainVerificationLegacySettings.LegacyState legacyState2 = new com.android.server.pm.verify.domain.DomainVerificationLegacySettings.LegacyState();
            this.mStates.put(str, legacyState2);
            return legacyState2;
        }
        return legacyState;
    }

    public void writeSettings(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        com.android.server.pm.SettingsXml.Serializer serializer = com.android.server.pm.SettingsXml.serializer(typedXmlSerializer);
        try {
            com.android.server.pm.SettingsXml.WriteSection startSection = serializer.startSection(TAG_DOMAIN_VERIFICATIONS_LEGACY);
            try {
                synchronized (this.mLock) {
                    int size = this.mStates.size();
                    for (int i = 0; i < size; i++) {
                        android.util.SparseIntArray userStates = this.mStates.valueAt(i).getUserStates();
                        if (userStates != null) {
                            com.android.server.pm.SettingsXml.WriteSection attribute = serializer.startSection(TAG_USER_STATES).attribute(ATTR_PACKAGE_NAME, this.mStates.keyAt(i));
                            try {
                                int size2 = userStates.size();
                                for (int i2 = 0; i2 < size2; i2++) {
                                    attribute.startSection("user-state").attribute("userId", userStates.keyAt(i2)).attribute("state", userStates.valueAt(i2)).finish();
                                }
                                if (attribute != null) {
                                    attribute.close();
                                }
                            } finally {
                            }
                        }
                    }
                }
                if (startSection != null) {
                    startSection.close();
                }
                serializer.close();
            } finally {
            }
        } catch (java.lang.Throwable th) {
            if (serializer != null) {
                try {
                    serializer.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public void readSettings(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        com.android.server.pm.SettingsXml.ChildSection children = com.android.server.pm.SettingsXml.parser(typedXmlPullParser).children();
        while (children.moveToNext()) {
            if (TAG_USER_STATES.equals(children.getName())) {
                readUserStates(children);
            }
        }
    }

    private void readUserStates(com.android.server.pm.SettingsXml.ReadSection readSection) {
        java.lang.String string = readSection.getString(ATTR_PACKAGE_NAME);
        synchronized (this.mLock) {
            try {
                com.android.server.pm.verify.domain.DomainVerificationLegacySettings.LegacyState orCreateStateLocked = getOrCreateStateLocked(string);
                com.android.server.pm.SettingsXml.ChildSection children = readSection.children();
                while (children.moveToNext()) {
                    if ("user-state".equals(children.getName())) {
                        readUserState(children, orCreateStateLocked);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void readUserState(com.android.server.pm.SettingsXml.ReadSection readSection, com.android.server.pm.verify.domain.DomainVerificationLegacySettings.LegacyState legacyState) {
        legacyState.addUserState(readSection.getInt("userId"), readSection.getInt("state"));
    }

    static class LegacyState {
        private boolean attached;

        @android.annotation.Nullable
        private android.content.pm.IntentFilterVerificationInfo mInfo;

        @android.annotation.Nullable
        private android.util.SparseIntArray mUserStates;

        LegacyState() {
        }

        @android.annotation.Nullable
        public android.content.pm.IntentFilterVerificationInfo getInfo() {
            return this.mInfo;
        }

        public int getUserState(int i) {
            return this.mUserStates.get(i, 0);
        }

        @android.annotation.Nullable
        public android.util.SparseIntArray getUserStates() {
            return this.mUserStates;
        }

        public void setInfo(@android.annotation.NonNull android.content.pm.IntentFilterVerificationInfo intentFilterVerificationInfo) {
            this.mInfo = intentFilterVerificationInfo;
        }

        public void addUserState(int i, int i2) {
            if (this.mUserStates == null) {
                this.mUserStates = new android.util.SparseIntArray(1);
            }
            this.mUserStates.put(i, i2);
        }

        public boolean isAttached() {
            return this.attached;
        }

        public void markAttached() {
            this.attached = true;
        }
    }
}
