package com.android.internal.protolog;

/* loaded from: classes5.dex */
public enum ProtoLogGroup implements com.android.internal.protolog.common.IProtoLogGroup {
    WM_ERROR(true, true, true, "WindowManager"),
    WM_DEBUG_ORIENTATION(true, true, false, "WindowManager"),
    WM_DEBUG_FOCUS_LIGHT(true, true, false, "WindowManager"),
    WM_DEBUG_BOOT(true, true, false, "WindowManager"),
    WM_DEBUG_RESIZE(true, true, false, "WindowManager"),
    WM_DEBUG_ADD_REMOVE(true, true, false, "WindowManager"),
    WM_DEBUG_CONFIGURATION(true, true, false, "WindowManager"),
    WM_DEBUG_SWITCH(true, true, false, "WindowManager"),
    WM_DEBUG_CONTAINERS(true, true, false, "WindowManager"),
    WM_DEBUG_FOCUS(true, true, false, "WindowManager"),
    WM_DEBUG_IMMERSIVE(true, true, false, "WindowManager"),
    WM_DEBUG_LOCKTASK(true, true, false, "WindowManager"),
    WM_DEBUG_STATES(true, true, false, "WindowManager"),
    WM_DEBUG_TASKS(true, true, false, "WindowManager"),
    WM_DEBUG_STARTING_WINDOW(true, true, false, "WindowManager"),
    WM_SHOW_TRANSACTIONS(true, true, false, "WindowManager"),
    WM_SHOW_SURFACE_ALLOC(true, true, false, "WindowManager"),
    WM_DEBUG_APP_TRANSITIONS(true, true, false, "WindowManager"),
    WM_DEBUG_ANIM(true, true, false, "WindowManager"),
    WM_DEBUG_APP_TRANSITIONS_ANIM(true, true, false, "WindowManager"),
    WM_DEBUG_RECENTS_ANIMATIONS(true, true, false, "WindowManager"),
    WM_DEBUG_DRAW(true, true, false, "WindowManager"),
    WM_DEBUG_REMOTE_ANIMATIONS(true, true, false, "WindowManager"),
    WM_DEBUG_SCREEN_ON(true, true, false, "WindowManager"),
    WM_DEBUG_KEEP_SCREEN_ON(true, true, false, "WindowManager"),
    WM_DEBUG_WINDOW_MOVEMENT(true, true, false, "WindowManager"),
    WM_DEBUG_IME(true, true, false, "WindowManager"),
    WM_DEBUG_WINDOW_ORGANIZER(true, true, false, "WindowManager"),
    WM_DEBUG_SYNC_ENGINE(true, true, false, "WindowManager"),
    WM_DEBUG_WINDOW_TRANSITIONS(true, true, false, "WindowManager"),
    WM_DEBUG_WINDOW_TRANSITIONS_MIN(true, true, true, "WindowManager"),
    WM_DEBUG_WINDOW_INSETS(true, true, false, "WindowManager"),
    WM_DEBUG_CONTENT_RECORDING(true, true, true, "WindowManager"),
    WM_DEBUG_WALLPAPER(true, true, false, "WindowManager"),
    WM_DEBUG_BACK_PREVIEW(true, true, true, "CoreBackPreview"),
    WM_DEBUG_DREAM(true, true, true, "WindowManager"),
    WM_DEBUG_DIMMER(true, true, false, "WindowManager"),
    WM_DEBUG_TPL(true, true, false, "WindowManager"),
    WM_DEBUG_EMBEDDED_WINDOWS(true, true, false, "WindowManager"),
    TEST_GROUP(true, true, false, "WindowManagerProtoLogTest");

    private final boolean mEnabled;
    private volatile boolean mLogToLogcat;
    private volatile boolean mLogToProto;
    private final java.lang.String mTag;

    ProtoLogGroup(boolean z, boolean z2, boolean z3, java.lang.String str) {
        this.mEnabled = z;
        this.mLogToProto = z2;
        this.mLogToLogcat = z3;
        this.mTag = str;
    }

    @Override // com.android.internal.protolog.common.IProtoLogGroup
    public boolean isEnabled() {
        return this.mEnabled;
    }

    @Override // com.android.internal.protolog.common.IProtoLogGroup
    public boolean isLogToProto() {
        return this.mLogToProto;
    }

    @Override // com.android.internal.protolog.common.IProtoLogGroup
    public boolean isLogToLogcat() {
        return this.mLogToLogcat;
    }

    @Override // com.android.internal.protolog.common.IProtoLogGroup
    public boolean isLogToAny() {
        return this.mLogToLogcat || this.mLogToProto;
    }

    @Override // com.android.internal.protolog.common.IProtoLogGroup
    public java.lang.String getTag() {
        return this.mTag;
    }

    @Override // com.android.internal.protolog.common.IProtoLogGroup
    public void setLogToProto(boolean z) {
        this.mLogToProto = z;
    }

    @Override // com.android.internal.protolog.common.IProtoLogGroup
    public void setLogToLogcat(boolean z) {
        this.mLogToLogcat = z;
    }

    private static class Consts {
        private static final boolean ENABLE_DEBUG = true;
        private static final boolean ENABLE_LOG_TO_PROTO_DEBUG = true;
        private static final java.lang.String TAG_WM = "WindowManager";

        private Consts() {
        }
    }
}
