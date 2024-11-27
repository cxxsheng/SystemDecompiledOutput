package com.android.server.policy;

/* loaded from: classes2.dex */
public final class GlobalKeyIntent {
    private static final java.lang.String EXTRA_BEGAN_FROM_NON_INTERACTIVE = "EXTRA_BEGAN_FROM_NON_INTERACTIVE";
    private final boolean mBeganFromNonInteractive;
    private final android.content.ComponentName mComponentName;
    private final android.view.KeyEvent mKeyEvent;

    GlobalKeyIntent(@android.annotation.NonNull android.content.ComponentName componentName, @android.annotation.NonNull android.view.KeyEvent keyEvent, boolean z) {
        this.mComponentName = componentName;
        this.mKeyEvent = new android.view.KeyEvent(keyEvent);
        this.mBeganFromNonInteractive = z;
    }

    android.content.Intent getIntent() {
        return new android.content.Intent("android.intent.action.GLOBAL_BUTTON").setComponent(this.mComponentName).setFlags(268435456).putExtra("android.intent.extra.KEY_EVENT", this.mKeyEvent).putExtra(EXTRA_BEGAN_FROM_NON_INTERACTIVE, this.mBeganFromNonInteractive);
    }

    public android.view.KeyEvent getKeyEvent() {
        return this.mKeyEvent;
    }

    public boolean beganFromNonInteractive() {
        return this.mBeganFromNonInteractive;
    }

    public static com.android.server.policy.GlobalKeyIntent from(@android.annotation.NonNull android.content.Intent intent) {
        if (intent.getAction() != "android.intent.action.GLOBAL_BUTTON") {
            android.util.Log.wtf("GlobalKeyIntent", "Intent should be ACTION_GLOBAL_BUTTON");
            return null;
        }
        return new com.android.server.policy.GlobalKeyIntent(intent.getComponent(), (android.view.KeyEvent) intent.getParcelableExtra("android.intent.extra.KEY_EVENT", android.view.KeyEvent.class), intent.getBooleanExtra(EXTRA_BEGAN_FROM_NON_INTERACTIVE, false));
    }
}
