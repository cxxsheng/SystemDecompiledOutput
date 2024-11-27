package com.android.internal.telephony.util;

/* loaded from: classes5.dex */
public class RemoteCallbackListExt<E extends android.os.IInterface> extends android.os.RemoteCallbackList<E> {
    public void broadcastAction(java.util.function.Consumer<E> consumer) {
        int beginBroadcast = beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                consumer.accept(getBroadcastItem(i));
            } finally {
                finishBroadcast();
            }
        }
    }
}
