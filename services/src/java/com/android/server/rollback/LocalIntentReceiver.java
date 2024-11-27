package com.android.server.rollback;

/* loaded from: classes2.dex */
class LocalIntentReceiver {
    final java.util.function.Consumer<android.content.Intent> mConsumer;
    private android.content.IIntentSender.Stub mLocalSender = new android.content.IIntentSender.Stub() { // from class: com.android.server.rollback.LocalIntentReceiver.1
        public void send(int i, android.content.Intent intent, java.lang.String str, android.os.IBinder iBinder, android.content.IIntentReceiver iIntentReceiver, java.lang.String str2, android.os.Bundle bundle) {
            com.android.server.rollback.LocalIntentReceiver.this.mConsumer.accept(intent);
        }
    };

    LocalIntentReceiver(java.util.function.Consumer<android.content.Intent> consumer) {
        this.mConsumer = consumer;
    }

    android.content.IntentSender getIntentSender() {
        return new android.content.IntentSender(this.mLocalSender);
    }
}
