package com.android.server.pm.pkg;

/* loaded from: classes2.dex */
public final class SuspendParams {
    private static final java.lang.String ATTR_QUARANTINED = "quarantined";
    private static final java.lang.String LOG_TAG = "FrameworkPackageUserState";
    private static final java.lang.String TAG_APP_EXTRAS = "app-extras";
    private static final java.lang.String TAG_DIALOG_INFO = "dialog-info";
    private static final java.lang.String TAG_LAUNCHER_EXTRAS = "launcher-extras";
    private final android.os.PersistableBundle mAppExtras;
    private final android.content.pm.SuspendDialogInfo mDialogInfo;
    private final android.os.PersistableBundle mLauncherExtras;
    private final boolean mQuarantined;

    public SuspendParams(android.content.pm.SuspendDialogInfo suspendDialogInfo, android.os.PersistableBundle persistableBundle, android.os.PersistableBundle persistableBundle2) {
        this(suspendDialogInfo, persistableBundle, persistableBundle2, false);
    }

    public SuspendParams(android.content.pm.SuspendDialogInfo suspendDialogInfo, android.os.PersistableBundle persistableBundle, android.os.PersistableBundle persistableBundle2, boolean z) {
        this.mDialogInfo = suspendDialogInfo;
        this.mAppExtras = persistableBundle;
        this.mLauncherExtras = persistableBundle2;
        this.mQuarantined = z;
    }

    public boolean equals(@android.annotation.Nullable java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof com.android.server.pm.pkg.SuspendParams)) {
            return false;
        }
        com.android.server.pm.pkg.SuspendParams suspendParams = (com.android.server.pm.pkg.SuspendParams) obj;
        return java.util.Objects.equals(this.mDialogInfo, suspendParams.mDialogInfo) && android.os.BaseBundle.kindofEquals(this.mAppExtras, suspendParams.mAppExtras) && android.os.BaseBundle.kindofEquals(this.mLauncherExtras, suspendParams.mLauncherExtras) && this.mQuarantined == suspendParams.mQuarantined;
    }

    public int hashCode() {
        return (((((java.util.Objects.hashCode(this.mDialogInfo) * 31) + (this.mAppExtras != null ? this.mAppExtras.size() : 0)) * 31) + (this.mLauncherExtras != null ? this.mLauncherExtras.size() : 0)) * 31) + java.lang.Boolean.hashCode(this.mQuarantined);
    }

    public void saveToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.attributeBoolean((java.lang.String) null, ATTR_QUARANTINED, this.mQuarantined);
        if (this.mDialogInfo != null) {
            typedXmlSerializer.startTag((java.lang.String) null, TAG_DIALOG_INFO);
            this.mDialogInfo.saveToXml(typedXmlSerializer);
            typedXmlSerializer.endTag((java.lang.String) null, TAG_DIALOG_INFO);
        }
        if (this.mAppExtras != null) {
            typedXmlSerializer.startTag((java.lang.String) null, TAG_APP_EXTRAS);
            try {
                this.mAppExtras.saveToXml(typedXmlSerializer);
            } catch (org.xmlpull.v1.XmlPullParserException e) {
                android.util.Slog.e(LOG_TAG, "Exception while trying to write appExtras. Will be lost on reboot", e);
            }
            typedXmlSerializer.endTag((java.lang.String) null, TAG_APP_EXTRAS);
        }
        if (this.mLauncherExtras != null) {
            typedXmlSerializer.startTag((java.lang.String) null, TAG_LAUNCHER_EXTRAS);
            try {
                this.mLauncherExtras.saveToXml(typedXmlSerializer);
            } catch (org.xmlpull.v1.XmlPullParserException e2) {
                android.util.Slog.e(LOG_TAG, "Exception while trying to write launcherExtras. Will be lost on reboot", e2);
            }
            typedXmlSerializer.endTag((java.lang.String) null, TAG_LAUNCHER_EXTRAS);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x002d, code lost:
    
        if (r7 != 4) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0030, code lost:
    
        r7 = r11.getName();
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0038, code lost:
    
        switch(r7.hashCode()) {
            case -538220657: goto L26;
            case -22768109: goto L23;
            case 1627485488: goto L20;
            default: goto L19;
        };
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0059, code lost:
    
        r8 = 65535;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x005a, code lost:
    
        switch(r8) {
            case 0: goto L48;
            case 1: goto L47;
            case 2: goto L46;
            default: goto L50;
        };
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x007b, code lost:
    
        r6 = android.os.PersistableBundle.restoreFromXml(r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0080, code lost:
    
        r5 = android.os.PersistableBundle.restoreFromXml(r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0085, code lost:
    
        r1 = android.content.pm.SuspendDialogInfo.restoreFromXml(r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x005d, code lost:
    
        android.util.Slog.w(com.android.server.pm.pkg.SuspendParams.LOG_TAG, "Unknown tag " + r11.getName() + " in SuspendParams. Ignoring");
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0042, code lost:
    
        if (r7.equals(com.android.server.pm.pkg.SuspendParams.TAG_LAUNCHER_EXTRAS) == false) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0044, code lost:
    
        r8 = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x004c, code lost:
    
        if (r7.equals(com.android.server.pm.pkg.SuspendParams.TAG_DIALOG_INFO) == false) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x004e, code lost:
    
        r8 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0056, code lost:
    
        if (r7.equals(com.android.server.pm.pkg.SuspendParams.TAG_APP_EXTRAS) == false) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static com.android.server.pm.pkg.SuspendParams restoreFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException {
        android.content.pm.SuspendDialogInfo suspendDialogInfo = null;
        boolean attributeBoolean = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_QUARANTINED, false);
        int depth = typedXmlPullParser.getDepth();
        android.os.PersistableBundle persistableBundle = null;
        android.os.PersistableBundle persistableBundle2 = null;
        while (true) {
            try {
                int next = typedXmlPullParser.next();
                char c = 1;
                if (next != 1) {
                    if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                    }
                }
            } catch (org.xmlpull.v1.XmlPullParserException e) {
                android.util.Slog.e(LOG_TAG, "Exception while trying to parse SuspendParams, some fields may default", e);
            }
        }
        return new com.android.server.pm.pkg.SuspendParams(suspendDialogInfo, persistableBundle, persistableBundle2, attributeBoolean);
    }

    public android.content.pm.SuspendDialogInfo getDialogInfo() {
        return this.mDialogInfo;
    }

    public android.os.PersistableBundle getAppExtras() {
        return this.mAppExtras;
    }

    public android.os.PersistableBundle getLauncherExtras() {
        return this.mLauncherExtras;
    }

    public boolean isQuarantined() {
        return this.mQuarantined;
    }
}
