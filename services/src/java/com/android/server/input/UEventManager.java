package com.android.server.input;

/* loaded from: classes2.dex */
interface UEventManager {

    public static abstract class UEventListener {
        private final android.os.UEventObserver mObserver = new android.os.UEventObserver() { // from class: com.android.server.input.UEventManager.UEventListener.1
            public void onUEvent(android.os.UEventObserver.UEvent uEvent) {
                com.android.server.input.UEventManager.UEventListener.this.onUEvent(uEvent);
            }
        };

        public abstract void onUEvent(android.os.UEventObserver.UEvent uEvent);
    }

    default void addListener(com.android.server.input.UEventManager.UEventListener uEventListener, java.lang.String str) {
        uEventListener.mObserver.startObserving(str);
    }

    default void removeListener(com.android.server.input.UEventManager.UEventListener uEventListener) {
        uEventListener.mObserver.stopObserving();
    }
}
