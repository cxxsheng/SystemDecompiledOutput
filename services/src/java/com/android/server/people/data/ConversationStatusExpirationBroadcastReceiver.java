package com.android.server.people.data;

/* loaded from: classes2.dex */
public class ConversationStatusExpirationBroadcastReceiver extends android.content.BroadcastReceiver {
    static final java.lang.String ACTION = "ConversationStatusExpiration";
    static final java.lang.String EXTRA_USER_ID = "userId";
    static final int REQUEST_CODE = 10;
    static final java.lang.String SCHEME = "expStatus";

    void scheduleExpiration(android.content.Context context, int i, java.lang.String str, java.lang.String str2, android.app.people.ConversationStatus conversationStatus) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            ((android.app.AlarmManager) context.getSystemService(android.app.AlarmManager.class)).setExactAndAllowWhileIdle(0, conversationStatus.getEndTimeMillis(), android.app.PendingIntent.getBroadcast(context, 10, new android.content.Intent(ACTION).setPackage(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME).setData(new android.net.Uri.Builder().scheme(SCHEME).appendPath(getKey(i, str, str2, conversationStatus)).build()).addFlags(268435456).putExtra("userId", i), android.hardware.audio.common.V2_0.AudioFormat.DTS_HD));
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private static java.lang.String getKey(int i, java.lang.String str, java.lang.String str2, android.app.people.ConversationStatus conversationStatus) {
        return i + str + str2 + conversationStatus.getId();
    }

    static android.content.IntentFilter getFilter() {
        android.content.IntentFilter intentFilter = new android.content.IntentFilter(ACTION);
        intentFilter.addDataScheme(SCHEME);
        return intentFilter;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(android.content.Context context, final android.content.Intent intent) {
        java.lang.String action = intent.getAction();
        if (action != null && ACTION.equals(action)) {
            new java.lang.Thread(new java.lang.Runnable() { // from class: com.android.server.people.data.ConversationStatusExpirationBroadcastReceiver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.people.data.ConversationStatusExpirationBroadcastReceiver.lambda$onReceive$0(intent);
                }
            }).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onReceive$0(android.content.Intent intent) {
        ((com.android.server.people.PeopleServiceInternal) com.android.server.LocalServices.getService(com.android.server.people.PeopleServiceInternal.class)).pruneDataForUser(intent.getIntExtra("userId", android.app.ActivityManager.getCurrentUser()), new android.os.CancellationSignal());
    }
}
