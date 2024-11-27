package com.android.server.wm;

/* loaded from: classes3.dex */
public class PointerEventDispatcher extends android.view.InputEventReceiver {
    private final java.util.ArrayList<android.view.WindowManagerPolicyConstants.PointerEventListener> mListeners;
    private android.view.WindowManagerPolicyConstants.PointerEventListener[] mListenersArray;

    public PointerEventDispatcher(android.view.InputChannel inputChannel) {
        super(inputChannel, com.android.server.UiThread.getHandler().getLooper());
        this.mListeners = new java.util.ArrayList<>();
        this.mListenersArray = new android.view.WindowManagerPolicyConstants.PointerEventListener[0];
    }

    public void onInputEvent(android.view.InputEvent inputEvent) {
        android.view.WindowManagerPolicyConstants.PointerEventListener[] pointerEventListenerArr;
        try {
            if ((inputEvent instanceof android.view.MotionEvent) && (inputEvent.getSource() & 2) != 0) {
                android.view.MotionEvent motionEvent = (android.view.MotionEvent) inputEvent;
                synchronized (this.mListeners) {
                    try {
                        if (this.mListenersArray == null) {
                            this.mListenersArray = new android.view.WindowManagerPolicyConstants.PointerEventListener[this.mListeners.size()];
                            this.mListeners.toArray(this.mListenersArray);
                        }
                        pointerEventListenerArr = this.mListenersArray;
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                for (android.view.WindowManagerPolicyConstants.PointerEventListener pointerEventListener : pointerEventListenerArr) {
                    pointerEventListener.onPointerEvent(motionEvent);
                }
            }
        } finally {
            finishInputEvent(inputEvent, false);
        }
    }

    public void registerInputEventListener(android.view.WindowManagerPolicyConstants.PointerEventListener pointerEventListener) {
        synchronized (this.mListeners) {
            try {
                if (this.mListeners.contains(pointerEventListener)) {
                    throw new java.lang.IllegalStateException("registerInputEventListener: trying to register" + pointerEventListener + " twice.");
                }
                this.mListeners.add(pointerEventListener);
                this.mListenersArray = null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void unregisterInputEventListener(android.view.WindowManagerPolicyConstants.PointerEventListener pointerEventListener) {
        synchronized (this.mListeners) {
            try {
                if (!this.mListeners.contains(pointerEventListener)) {
                    throw new java.lang.IllegalStateException("registerInputEventListener: " + pointerEventListener + " not registered.");
                }
                this.mListeners.remove(pointerEventListener);
                this.mListenersArray = null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void dispose() {
        super.dispose();
        synchronized (this.mListeners) {
            this.mListeners.clear();
            this.mListenersArray = null;
        }
    }
}
