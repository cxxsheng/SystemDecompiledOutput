package com.android.server.policy;

/* loaded from: classes2.dex */
public final class SingleKeyGestureDetector {
    private static final boolean DEBUG = false;
    private static final int MSG_KEY_DELAYED_PRESS = 2;
    private static final int MSG_KEY_LONG_PRESS = 0;
    private static final int MSG_KEY_UP = 3;
    private static final int MSG_KEY_VERY_LONG_PRESS = 1;
    static final long MULTI_PRESS_TIMEOUT = android.view.ViewConfiguration.getMultiPressTimeout();
    private static final java.lang.String TAG = "SingleKeyGesture";
    static long sDefaultLongPressTimeout;
    static long sDefaultVeryLongPressTimeout;
    private final android.os.Handler mHandler;
    private int mKeyPressCounter;
    private boolean mBeganFromNonInteractive = false;
    private boolean mBeganFromDefaultDisplayOn = false;
    private final java.util.ArrayList<com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule> mRules = new java.util.ArrayList<>();
    private com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule mActiveRule = null;
    private int mDownKeyCode = 0;
    private boolean mHandledByLongPress = false;
    private long mLastDownTime = 0;

    static abstract class SingleKeyRule {
        private final int mKeyCode;

        abstract void onPress(long j, int i);

        SingleKeyRule(int i) {
            this.mKeyCode = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean shouldInterceptKey(int i) {
            return i == this.mKeyCode;
        }

        boolean supportLongPress() {
            return false;
        }

        boolean supportVeryLongPress() {
            return false;
        }

        int getMaxMultiPressCount() {
            return 1;
        }

        void onMultiPress(long j, int i, int i2) {
        }

        long getLongPressTimeoutMs() {
            return com.android.server.policy.SingleKeyGestureDetector.sDefaultLongPressTimeout;
        }

        void onLongPress(long j) {
        }

        long getVeryLongPressTimeoutMs() {
            return com.android.server.policy.SingleKeyGestureDetector.sDefaultVeryLongPressTimeout;
        }

        void onVeryLongPress(long j) {
        }

        void onKeyUp(long j, int i, int i2) {
        }

        public java.lang.String toString() {
            return "KeyCode=" + android.view.KeyEvent.keyCodeToString(this.mKeyCode) + ", LongPress=" + supportLongPress() + ", VeryLongPress=" + supportVeryLongPress() + ", MaxMultiPressCount=" + getMaxMultiPressCount();
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule) && this.mKeyCode == ((com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule) obj).mKeyCode;
        }

        public int hashCode() {
            return this.mKeyCode;
        }
    }

    private static final class MessageObject extends java.lang.Record {
        private final com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule activeRule;
        private final int displayId;
        private final int keyCode;
        private final int pressCount;

        private MessageObject(com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule activeRule, int keyCode, int pressCount, int displayId) {
            this.activeRule = activeRule;
            this.keyCode = keyCode;
            this.pressCount = pressCount;
            this.displayId = displayId;
        }

        public com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule activeRule() {
            return this.activeRule;
        }

        public int displayId() {
            return this.displayId;
        }

        @Override // java.lang.Record
        public final boolean equals(java.lang.Object obj) {
            return (boolean) java.lang.runtime.ObjectMethods.bootstrap(java.lang.invoke.MethodHandles.lookup(), "equals", java.lang.invoke.MethodType.methodType(java.lang.Boolean.TYPE, com.android.server.policy.SingleKeyGestureDetector.MessageObject.class, java.lang.Object.class), com.android.server.policy.SingleKeyGestureDetector.MessageObject.class, "activeRule;keyCode;pressCount;displayId", "FIELD:Lcom/android/server/policy/SingleKeyGestureDetector$MessageObject;->activeRule:Lcom/android/server/policy/SingleKeyGestureDetector$SingleKeyRule;", "FIELD:Lcom/android/server/policy/SingleKeyGestureDetector$MessageObject;->keyCode:I", "FIELD:Lcom/android/server/policy/SingleKeyGestureDetector$MessageObject;->pressCount:I", "FIELD:Lcom/android/server/policy/SingleKeyGestureDetector$MessageObject;->displayId:I").dynamicInvoker().invoke(this, obj) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) java.lang.runtime.ObjectMethods.bootstrap(java.lang.invoke.MethodHandles.lookup(), "hashCode", java.lang.invoke.MethodType.methodType(java.lang.Integer.TYPE, com.android.server.policy.SingleKeyGestureDetector.MessageObject.class), com.android.server.policy.SingleKeyGestureDetector.MessageObject.class, "activeRule;keyCode;pressCount;displayId", "FIELD:Lcom/android/server/policy/SingleKeyGestureDetector$MessageObject;->activeRule:Lcom/android/server/policy/SingleKeyGestureDetector$SingleKeyRule;", "FIELD:Lcom/android/server/policy/SingleKeyGestureDetector$MessageObject;->keyCode:I", "FIELD:Lcom/android/server/policy/SingleKeyGestureDetector$MessageObject;->pressCount:I", "FIELD:Lcom/android/server/policy/SingleKeyGestureDetector$MessageObject;->displayId:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        public int keyCode() {
            return this.keyCode;
        }

        public int pressCount() {
            return this.pressCount;
        }

        @Override // java.lang.Record
        public final java.lang.String toString() {
            return (java.lang.String) java.lang.runtime.ObjectMethods.bootstrap(java.lang.invoke.MethodHandles.lookup(), "toString", java.lang.invoke.MethodType.methodType(java.lang.String.class, com.android.server.policy.SingleKeyGestureDetector.MessageObject.class), com.android.server.policy.SingleKeyGestureDetector.MessageObject.class, "activeRule;keyCode;pressCount;displayId", "FIELD:Lcom/android/server/policy/SingleKeyGestureDetector$MessageObject;->activeRule:Lcom/android/server/policy/SingleKeyGestureDetector$SingleKeyRule;", "FIELD:Lcom/android/server/policy/SingleKeyGestureDetector$MessageObject;->keyCode:I", "FIELD:Lcom/android/server/policy/SingleKeyGestureDetector$MessageObject;->pressCount:I", "FIELD:Lcom/android/server/policy/SingleKeyGestureDetector$MessageObject;->displayId:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }
    }

    static com.android.server.policy.SingleKeyGestureDetector get(android.content.Context context, android.os.Looper looper) {
        com.android.server.policy.SingleKeyGestureDetector singleKeyGestureDetector = new com.android.server.policy.SingleKeyGestureDetector(looper);
        sDefaultLongPressTimeout = context.getResources().getInteger(android.R.integer.config_faceMaxTemplatesPerUser);
        sDefaultVeryLongPressTimeout = context.getResources().getInteger(android.R.integer.config_tooltipAnimTime);
        return singleKeyGestureDetector;
    }

    private SingleKeyGestureDetector(android.os.Looper looper) {
        this.mHandler = new com.android.server.policy.SingleKeyGestureDetector.KeyHandler(looper);
    }

    void addRule(com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule singleKeyRule) {
        if (this.mRules.contains(singleKeyRule)) {
            throw new java.lang.IllegalArgumentException("Rule : " + singleKeyRule + " already exists.");
        }
        this.mRules.add(singleKeyRule);
    }

    void removeRule(com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule singleKeyRule) {
        this.mRules.remove(singleKeyRule);
    }

    void interceptKey(android.view.KeyEvent keyEvent, boolean z, boolean z2) {
        if (keyEvent.getAction() == 0) {
            if (this.mDownKeyCode == 0 || this.mDownKeyCode != keyEvent.getKeyCode()) {
                this.mBeganFromNonInteractive = !z;
                this.mBeganFromDefaultDisplayOn = z2;
            }
            interceptKeyDown(keyEvent);
            return;
        }
        interceptKeyUp(keyEvent);
    }

    private void interceptKeyDown(android.view.KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (this.mDownKeyCode == keyCode) {
            if (this.mActiveRule != null && (keyEvent.getFlags() & 128) != 0 && this.mActiveRule.supportLongPress() && !this.mHandledByLongPress) {
                this.mHandledByLongPress = true;
                this.mHandler.removeMessages(0);
                this.mHandler.removeMessages(1);
                android.os.Message obtainMessage = this.mHandler.obtainMessage(0, new com.android.server.policy.SingleKeyGestureDetector.MessageObject(this.mActiveRule, keyCode, 1, keyEvent.getDisplayId()));
                obtainMessage.setAsynchronous(true);
                this.mHandler.sendMessage(obtainMessage);
                return;
            }
            return;
        }
        if (this.mDownKeyCode != 0 || (this.mActiveRule != null && !this.mActiveRule.shouldInterceptKey(keyCode))) {
            reset();
        }
        this.mDownKeyCode = keyCode;
        if (this.mActiveRule == null) {
            int size = this.mRules.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule singleKeyRule = this.mRules.get(i);
                if (!singleKeyRule.shouldInterceptKey(keyCode)) {
                    i++;
                } else {
                    this.mActiveRule = singleKeyRule;
                    break;
                }
            }
            this.mLastDownTime = 0L;
        }
        if (this.mActiveRule == null) {
            return;
        }
        long downTime = keyEvent.getDownTime() - this.mLastDownTime;
        this.mLastDownTime = keyEvent.getDownTime();
        if (downTime >= MULTI_PRESS_TIMEOUT) {
            this.mKeyPressCounter = 1;
        } else {
            this.mKeyPressCounter++;
        }
        if (this.mKeyPressCounter == 1) {
            if (this.mActiveRule.supportLongPress()) {
                android.os.Message obtainMessage2 = this.mHandler.obtainMessage(0, new com.android.server.policy.SingleKeyGestureDetector.MessageObject(this.mActiveRule, keyCode, this.mKeyPressCounter, keyEvent.getDisplayId()));
                obtainMessage2.setAsynchronous(true);
                this.mHandler.sendMessageDelayed(obtainMessage2, this.mActiveRule.getLongPressTimeoutMs());
            }
            if (this.mActiveRule.supportVeryLongPress()) {
                android.os.Message obtainMessage3 = this.mHandler.obtainMessage(1, new com.android.server.policy.SingleKeyGestureDetector.MessageObject(this.mActiveRule, keyCode, this.mKeyPressCounter, keyEvent.getDisplayId()));
                obtainMessage3.setAsynchronous(true);
                this.mHandler.sendMessageDelayed(obtainMessage3, this.mActiveRule.getVeryLongPressTimeoutMs());
                return;
            }
            return;
        }
        this.mHandler.removeMessages(0);
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(2);
        if (this.mActiveRule.getMaxMultiPressCount() > 1 && this.mKeyPressCounter == this.mActiveRule.getMaxMultiPressCount()) {
            android.os.Message obtainMessage4 = this.mHandler.obtainMessage(2, new com.android.server.policy.SingleKeyGestureDetector.MessageObject(this.mActiveRule, keyCode, this.mKeyPressCounter, keyEvent.getDisplayId()));
            obtainMessage4.setAsynchronous(true);
            this.mHandler.sendMessage(obtainMessage4);
        }
    }

    private boolean interceptKeyUp(android.view.KeyEvent keyEvent) {
        this.mDownKeyCode = 0;
        if (this.mActiveRule == null) {
            return false;
        }
        if (!this.mHandledByLongPress) {
            long eventTime = keyEvent.getEventTime();
            if (eventTime < this.mLastDownTime + this.mActiveRule.getLongPressTimeoutMs()) {
                this.mHandler.removeMessages(0);
            } else {
                this.mHandledByLongPress = this.mActiveRule.supportLongPress();
            }
            if (eventTime < this.mLastDownTime + this.mActiveRule.getVeryLongPressTimeoutMs()) {
                this.mHandler.removeMessages(1);
            } else {
                this.mHandledByLongPress |= this.mActiveRule.supportVeryLongPress();
            }
        }
        if (this.mHandledByLongPress) {
            this.mHandledByLongPress = false;
            this.mKeyPressCounter = 0;
            this.mActiveRule = null;
            return true;
        }
        if (keyEvent.getKeyCode() == this.mActiveRule.mKeyCode) {
            android.os.Message obtainMessage = this.mHandler.obtainMessage(3, new com.android.server.policy.SingleKeyGestureDetector.MessageObject(this.mActiveRule, this.mActiveRule.mKeyCode, this.mKeyPressCounter, keyEvent.getDisplayId()));
            obtainMessage.setAsynchronous(true);
            this.mHandler.sendMessage(obtainMessage);
            if (this.mActiveRule.getMaxMultiPressCount() == 1) {
                android.os.Message obtainMessage2 = this.mHandler.obtainMessage(2, new com.android.server.policy.SingleKeyGestureDetector.MessageObject(this.mActiveRule, this.mActiveRule.mKeyCode, 1, keyEvent.getDisplayId()));
                obtainMessage2.setAsynchronous(true);
                this.mHandler.sendMessage(obtainMessage2);
                this.mActiveRule = null;
                return true;
            }
            if (this.mKeyPressCounter < this.mActiveRule.getMaxMultiPressCount()) {
                android.os.Message obtainMessage3 = this.mHandler.obtainMessage(2, new com.android.server.policy.SingleKeyGestureDetector.MessageObject(this.mActiveRule, this.mActiveRule.mKeyCode, this.mKeyPressCounter, keyEvent.getDisplayId()));
                obtainMessage3.setAsynchronous(true);
                this.mHandler.sendMessageDelayed(obtainMessage3, MULTI_PRESS_TIMEOUT);
            }
            return true;
        }
        reset();
        return false;
    }

    int getKeyPressCounter(int i) {
        if (this.mActiveRule != null && this.mActiveRule.mKeyCode == i) {
            return this.mKeyPressCounter;
        }
        return 0;
    }

    void reset() {
        if (this.mActiveRule != null) {
            if (this.mDownKeyCode != 0) {
                this.mHandler.removeMessages(0);
                this.mHandler.removeMessages(1);
            }
            if (this.mKeyPressCounter > 0) {
                this.mHandler.removeMessages(2);
                this.mKeyPressCounter = 0;
            }
            this.mActiveRule = null;
        }
        this.mHandledByLongPress = false;
        this.mDownKeyCode = 0;
    }

    boolean isKeyIntercepted(int i) {
        return this.mActiveRule != null && this.mActiveRule.shouldInterceptKey(i);
    }

    boolean beganFromNonInteractive() {
        return this.mBeganFromNonInteractive;
    }

    boolean beganFromDefaultDisplayOn() {
        return this.mBeganFromDefaultDisplayOn;
    }

    void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.println(str + "SingleKey rules:");
        java.util.Iterator<com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule> it = this.mRules.iterator();
        while (it.hasNext()) {
            printWriter.println(str + "  " + it.next());
        }
    }

    private class KeyHandler extends android.os.Handler {
        KeyHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            com.android.server.policy.SingleKeyGestureDetector.MessageObject messageObject = (com.android.server.policy.SingleKeyGestureDetector.MessageObject) message.obj;
            com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule singleKeyRule = messageObject.activeRule;
            if (singleKeyRule == null) {
                android.util.Log.wtf(com.android.server.policy.SingleKeyGestureDetector.TAG, "No active rule.");
            }
            int unused = messageObject.keyCode;
            int i = messageObject.pressCount;
            int i2 = messageObject.displayId;
            switch (message.what) {
                case 0:
                    singleKeyRule.onLongPress(com.android.server.policy.SingleKeyGestureDetector.this.mLastDownTime);
                    break;
                case 1:
                    singleKeyRule.onVeryLongPress(com.android.server.policy.SingleKeyGestureDetector.this.mLastDownTime);
                    break;
                case 2:
                    if (i == 1) {
                        singleKeyRule.onPress(com.android.server.policy.SingleKeyGestureDetector.this.mLastDownTime, i2);
                        break;
                    } else {
                        singleKeyRule.onMultiPress(com.android.server.policy.SingleKeyGestureDetector.this.mLastDownTime, i, i2);
                        break;
                    }
                case 3:
                    singleKeyRule.onKeyUp(com.android.server.policy.SingleKeyGestureDetector.this.mLastDownTime, i, i2);
                    break;
            }
        }
    }
}
