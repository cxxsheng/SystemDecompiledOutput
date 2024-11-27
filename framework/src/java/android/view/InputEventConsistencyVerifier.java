package android.view;

/* loaded from: classes4.dex */
public final class InputEventConsistencyVerifier {
    private static final java.lang.String EVENT_TYPE_GENERIC_MOTION = "GenericMotionEvent";
    private static final java.lang.String EVENT_TYPE_KEY = "KeyEvent";
    private static final java.lang.String EVENT_TYPE_TOUCH = "TouchEvent";
    private static final java.lang.String EVENT_TYPE_TRACKBALL = "TrackballEvent";
    public static final int FLAG_RAW_DEVICE_INPUT = 1;
    private static final boolean IS_ENG_BUILD = android.os.Build.IS_ENG;
    private static final int RECENT_EVENTS_TO_LOG = 5;
    private int mButtonsPressed;
    private final java.lang.Object mCaller;
    private android.view.InputEvent mCurrentEvent;
    private java.lang.String mCurrentEventType;
    private final int mFlags;
    private boolean mHoverEntered;
    private android.view.InputEventConsistencyVerifier.KeyState mKeyStateList;
    private int mLastEventSeq;
    private java.lang.String mLastEventType;
    private int mLastNestingLevel;
    private final java.lang.String mLogTag;
    private int mMostRecentEventIndex;
    private android.view.InputEvent[] mRecentEvents;
    private boolean[] mRecentEventsUnhandled;
    private int mTouchEventStreamDeviceId;
    private boolean mTouchEventStreamIsTainted;
    private int mTouchEventStreamPointers;
    private int mTouchEventStreamSource;
    private boolean mTouchEventStreamUnhandled;
    private boolean mTrackballDown;
    private boolean mTrackballUnhandled;
    private java.lang.StringBuilder mViolationMessage;

    public InputEventConsistencyVerifier(java.lang.Object obj, int i) {
        this(obj, i, null);
    }

    public InputEventConsistencyVerifier(java.lang.Object obj, int i, java.lang.String str) {
        this.mTouchEventStreamDeviceId = -1;
        this.mCaller = obj;
        this.mFlags = i;
        this.mLogTag = str == null ? "InputEventConsistencyVerifier" : str;
    }

    public static boolean isInstrumentationEnabled() {
        return IS_ENG_BUILD;
    }

    public void reset() {
        this.mLastEventSeq = -1;
        this.mLastNestingLevel = 0;
        this.mTrackballDown = false;
        this.mTrackballUnhandled = false;
        this.mTouchEventStreamPointers = 0;
        this.mTouchEventStreamIsTainted = false;
        this.mTouchEventStreamUnhandled = false;
        this.mHoverEntered = false;
        this.mButtonsPressed = 0;
        while (this.mKeyStateList != null) {
            android.view.InputEventConsistencyVerifier.KeyState keyState = this.mKeyStateList;
            this.mKeyStateList = keyState.next;
            keyState.recycle();
        }
    }

    public void onInputEvent(android.view.InputEvent inputEvent, int i) {
        if (inputEvent instanceof android.view.KeyEvent) {
            onKeyEvent((android.view.KeyEvent) inputEvent, i);
            return;
        }
        android.view.MotionEvent motionEvent = (android.view.MotionEvent) inputEvent;
        if (motionEvent.isTouchEvent()) {
            onTouchEvent(motionEvent, i);
        } else if ((motionEvent.getSource() & 4) != 0) {
            onTrackballEvent(motionEvent, i);
        } else {
            onGenericMotionEvent(motionEvent, i);
        }
    }

    public void onKeyEvent(android.view.KeyEvent keyEvent, int i) {
        if (!startEvent(keyEvent, i, EVENT_TYPE_KEY)) {
            return;
        }
        try {
            ensureMetaStateIsNormalized(keyEvent.getMetaState());
            int action = keyEvent.getAction();
            int deviceId = keyEvent.getDeviceId();
            int source = keyEvent.getSource();
            int keyCode = keyEvent.getKeyCode();
            switch (action) {
                case 0:
                    android.view.InputEventConsistencyVerifier.KeyState findKeyState = findKeyState(deviceId, source, keyCode, false);
                    if (findKeyState != null) {
                        if (!findKeyState.unhandled) {
                            if ((this.mFlags & 1) == 0 && keyEvent.getRepeatCount() == 0) {
                                problem("ACTION_DOWN but key is already down and this event is not a key repeat.");
                                break;
                            }
                        } else {
                            findKeyState.unhandled = false;
                            break;
                        }
                    } else {
                        addKeyState(deviceId, source, keyCode);
                        break;
                    }
                    break;
                case 1:
                    android.view.InputEventConsistencyVerifier.KeyState findKeyState2 = findKeyState(deviceId, source, keyCode, true);
                    if (findKeyState2 == null) {
                        problem("ACTION_UP but key was not down.");
                        break;
                    } else {
                        findKeyState2.recycle();
                        break;
                    }
                case 2:
                    break;
                default:
                    problem("Invalid action " + android.view.KeyEvent.actionToString(action) + " for key event.");
                    break;
            }
        } finally {
            finishEvent();
        }
    }

    public void onTrackballEvent(android.view.MotionEvent motionEvent, int i) {
        if (!startEvent(motionEvent, i, EVENT_TYPE_TRACKBALL)) {
            return;
        }
        try {
            ensureMetaStateIsNormalized(motionEvent.getMetaState());
            int action = motionEvent.getAction();
            if ((motionEvent.getSource() & 4) != 0) {
                switch (action) {
                    case 0:
                        if (this.mTrackballDown && !this.mTrackballUnhandled) {
                            problem("ACTION_DOWN but trackball is already down.");
                        } else {
                            this.mTrackballDown = true;
                            this.mTrackballUnhandled = false;
                        }
                        ensureHistorySizeIsZeroForThisAction(motionEvent);
                        ensurePointerCountIsOneForThisAction(motionEvent);
                        break;
                    case 1:
                        if (!this.mTrackballDown) {
                            problem("ACTION_UP but trackball is not down.");
                        } else {
                            this.mTrackballDown = false;
                            this.mTrackballUnhandled = false;
                        }
                        ensureHistorySizeIsZeroForThisAction(motionEvent);
                        ensurePointerCountIsOneForThisAction(motionEvent);
                        break;
                    case 2:
                        ensurePointerCountIsOneForThisAction(motionEvent);
                        break;
                    default:
                        problem("Invalid action " + android.view.MotionEvent.actionToString(action) + " for trackball event.");
                        break;
                }
                if (this.mTrackballDown && motionEvent.getPressure() <= 0.0f) {
                    problem("Trackball is down but pressure is not greater than 0.");
                } else if (!this.mTrackballDown && motionEvent.getPressure() != 0.0f) {
                    problem("Trackball is up but pressure is not equal to 0.");
                }
            } else {
                problem("Source was not SOURCE_CLASS_TRACKBALL.");
            }
        } finally {
            finishEvent();
        }
    }

    public void onTouchEvent(android.view.MotionEvent motionEvent, int i) {
        if (!startEvent(motionEvent, i, EVENT_TYPE_TOUCH)) {
            return;
        }
        int action = motionEvent.getAction();
        boolean z = action == 0 || action == 3 || action == 4;
        if (z && (this.mTouchEventStreamIsTainted || this.mTouchEventStreamUnhandled)) {
            this.mTouchEventStreamIsTainted = false;
            this.mTouchEventStreamUnhandled = false;
            this.mTouchEventStreamPointers = 0;
        }
        if (this.mTouchEventStreamIsTainted) {
            motionEvent.setTainted(true);
        }
        try {
            ensureMetaStateIsNormalized(motionEvent.getMetaState());
            int deviceId = motionEvent.getDeviceId();
            int source = motionEvent.getSource();
            if (!z && this.mTouchEventStreamDeviceId != -1 && (this.mTouchEventStreamDeviceId != deviceId || this.mTouchEventStreamSource != source)) {
                problem("Touch event stream contains events from multiple sources: previous device id " + this.mTouchEventStreamDeviceId + ", previous source " + java.lang.Integer.toHexString(this.mTouchEventStreamSource) + ", new device id " + deviceId + ", new source " + java.lang.Integer.toHexString(source));
            }
            this.mTouchEventStreamDeviceId = deviceId;
            this.mTouchEventStreamSource = source;
            int pointerCount = motionEvent.getPointerCount();
            if ((source & 2) != 0) {
                switch (action) {
                    case 0:
                        if (this.mTouchEventStreamPointers != 0) {
                            problem("ACTION_DOWN but pointers are already down.  Probably missing ACTION_UP from previous gesture.");
                        }
                        ensureHistorySizeIsZeroForThisAction(motionEvent);
                        ensurePointerCountIsOneForThisAction(motionEvent);
                        this.mTouchEventStreamPointers = 1 << motionEvent.getPointerId(0);
                        break;
                    case 1:
                        ensureHistorySizeIsZeroForThisAction(motionEvent);
                        ensurePointerCountIsOneForThisAction(motionEvent);
                        this.mTouchEventStreamPointers = 0;
                        this.mTouchEventStreamIsTainted = false;
                        break;
                    case 2:
                        int bitCount = java.lang.Integer.bitCount(this.mTouchEventStreamPointers);
                        if (pointerCount != bitCount) {
                            problem("ACTION_MOVE contained " + pointerCount + " pointers but there are currently " + bitCount + " pointers down.");
                            this.mTouchEventStreamIsTainted = true;
                            break;
                        }
                        break;
                    case 3:
                        this.mTouchEventStreamPointers = 0;
                        this.mTouchEventStreamIsTainted = false;
                        break;
                    case 4:
                        if (this.mTouchEventStreamPointers != 0) {
                            problem("ACTION_OUTSIDE but pointers are still down.");
                        }
                        ensureHistorySizeIsZeroForThisAction(motionEvent);
                        ensurePointerCountIsOneForThisAction(motionEvent);
                        this.mTouchEventStreamIsTainted = false;
                        break;
                    default:
                        int actionMasked = motionEvent.getActionMasked();
                        int actionIndex = motionEvent.getActionIndex();
                        if (actionMasked == 5) {
                            if (this.mTouchEventStreamPointers == 0) {
                                problem("ACTION_POINTER_DOWN but no other pointers were down.");
                                this.mTouchEventStreamIsTainted = true;
                            }
                            if (actionIndex >= 0 && actionIndex < pointerCount) {
                                int pointerId = motionEvent.getPointerId(actionIndex);
                                int i2 = 1 << pointerId;
                                if ((this.mTouchEventStreamPointers & i2) != 0) {
                                    problem("ACTION_POINTER_DOWN specified pointer id " + pointerId + " which is already down.");
                                    this.mTouchEventStreamIsTainted = true;
                                } else {
                                    this.mTouchEventStreamPointers |= i2;
                                }
                                ensureHistorySizeIsZeroForThisAction(motionEvent);
                                break;
                            }
                            problem("ACTION_POINTER_DOWN index is " + actionIndex + " but the pointer count is " + pointerCount + android.media.MediaMetrics.SEPARATOR);
                            this.mTouchEventStreamIsTainted = true;
                            ensureHistorySizeIsZeroForThisAction(motionEvent);
                        } else if (actionMasked == 6) {
                            if (actionIndex >= 0 && actionIndex < pointerCount) {
                                int pointerId2 = motionEvent.getPointerId(actionIndex);
                                int i3 = 1 << pointerId2;
                                if ((this.mTouchEventStreamPointers & i3) == 0) {
                                    problem("ACTION_POINTER_UP specified pointer id " + pointerId2 + " which is not currently down.");
                                    this.mTouchEventStreamIsTainted = true;
                                } else {
                                    this.mTouchEventStreamPointers &= ~i3;
                                }
                                ensureHistorySizeIsZeroForThisAction(motionEvent);
                                break;
                            }
                            problem("ACTION_POINTER_UP index is " + actionIndex + " but the pointer count is " + pointerCount + android.media.MediaMetrics.SEPARATOR);
                            this.mTouchEventStreamIsTainted = true;
                            ensureHistorySizeIsZeroForThisAction(motionEvent);
                        } else {
                            problem("Invalid action " + android.view.MotionEvent.actionToString(action) + " for touch event.");
                            break;
                        }
                }
            } else {
                problem("Source was not SOURCE_CLASS_POINTER.");
            }
        } finally {
            finishEvent();
        }
    }

    public void onGenericMotionEvent(android.view.MotionEvent motionEvent, int i) {
        if (!startEvent(motionEvent, i, EVENT_TYPE_GENERIC_MOTION)) {
            return;
        }
        try {
            ensureMetaStateIsNormalized(motionEvent.getMetaState());
            int action = motionEvent.getAction();
            int source = motionEvent.getSource();
            int buttonState = motionEvent.getButtonState();
            int actionButton = motionEvent.getActionButton();
            if ((source & 2) != 0) {
                switch (action) {
                    case 7:
                        ensurePointerCountIsOneForThisAction(motionEvent);
                        break;
                    case 8:
                        ensureHistorySizeIsZeroForThisAction(motionEvent);
                        ensurePointerCountIsOneForThisAction(motionEvent);
                        break;
                    case 9:
                        ensurePointerCountIsOneForThisAction(motionEvent);
                        this.mHoverEntered = true;
                        break;
                    case 10:
                        ensurePointerCountIsOneForThisAction(motionEvent);
                        if (!this.mHoverEntered) {
                            problem("ACTION_HOVER_EXIT without prior ACTION_HOVER_ENTER");
                        }
                        this.mHoverEntered = false;
                        break;
                    case 11:
                        ensureActionButtonIsNonZeroForThisAction(motionEvent);
                        if ((this.mButtonsPressed & actionButton) != 0) {
                            problem("Action button for ACTION_BUTTON_PRESS event is " + actionButton + ", but it has already been pressed and has yet to be released.");
                        }
                        this.mButtonsPressed |= actionButton;
                        if (actionButton == 32 && (buttonState & 2) != 0) {
                            this.mButtonsPressed |= 2;
                        } else if (actionButton == 64 && (buttonState & 4) != 0) {
                            this.mButtonsPressed |= 4;
                        }
                        if (this.mButtonsPressed != buttonState) {
                            problem(java.lang.String.format("Reported button state differs from expected button state based on press and release events. Is 0x%08x but expected 0x%08x.", java.lang.Integer.valueOf(buttonState), java.lang.Integer.valueOf(this.mButtonsPressed)));
                            break;
                        }
                        break;
                    case 12:
                        ensureActionButtonIsNonZeroForThisAction(motionEvent);
                        if ((this.mButtonsPressed & actionButton) != actionButton) {
                            problem("Action button for ACTION_BUTTON_RELEASE event is " + actionButton + ", but it was either never pressed or has already been released.");
                        }
                        this.mButtonsPressed &= ~actionButton;
                        if (actionButton == 32 && (buttonState & 2) == 0) {
                            this.mButtonsPressed &= -3;
                        } else if (actionButton == 64 && (buttonState & 4) == 0) {
                            this.mButtonsPressed &= -5;
                        }
                        if (this.mButtonsPressed != buttonState) {
                            problem(java.lang.String.format("Reported button state differs from expected button state based on press and release events. Is 0x%08x but expected 0x%08x.", java.lang.Integer.valueOf(buttonState), java.lang.Integer.valueOf(this.mButtonsPressed)));
                            break;
                        }
                        break;
                    default:
                        problem("Invalid action for generic pointer event.");
                        break;
                }
            } else if ((source & 16) != 0) {
                switch (action) {
                    case 2:
                        ensurePointerCountIsOneForThisAction(motionEvent);
                        break;
                    default:
                        problem("Invalid action for generic joystick event.");
                        break;
                }
            }
        } finally {
            finishEvent();
        }
    }

    public void onUnhandledEvent(android.view.InputEvent inputEvent, int i) {
        if (i != this.mLastNestingLevel) {
            return;
        }
        if (this.mRecentEventsUnhandled != null) {
            this.mRecentEventsUnhandled[this.mMostRecentEventIndex] = true;
        }
        if (inputEvent instanceof android.view.KeyEvent) {
            android.view.KeyEvent keyEvent = (android.view.KeyEvent) inputEvent;
            android.view.InputEventConsistencyVerifier.KeyState findKeyState = findKeyState(keyEvent.getDeviceId(), keyEvent.getSource(), keyEvent.getKeyCode(), false);
            if (findKeyState != null) {
                findKeyState.unhandled = true;
                return;
            }
            return;
        }
        android.view.MotionEvent motionEvent = (android.view.MotionEvent) inputEvent;
        if (motionEvent.isTouchEvent()) {
            this.mTouchEventStreamUnhandled = true;
        } else if ((motionEvent.getSource() & 4) != 0 && this.mTrackballDown) {
            this.mTrackballUnhandled = true;
        }
    }

    private void ensureMetaStateIsNormalized(int i) {
        int normalizeMetaState = android.view.KeyEvent.normalizeMetaState(i);
        if (normalizeMetaState != i) {
            problem(java.lang.String.format("Metastate not normalized.  Was 0x%08x but expected 0x%08x.", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(normalizeMetaState)));
        }
    }

    private void ensurePointerCountIsOneForThisAction(android.view.MotionEvent motionEvent) {
        int pointerCount = motionEvent.getPointerCount();
        if (pointerCount != 1) {
            problem("Pointer count is " + pointerCount + " but it should always be 1 for " + android.view.MotionEvent.actionToString(motionEvent.getAction()));
        }
    }

    private void ensureActionButtonIsNonZeroForThisAction(android.view.MotionEvent motionEvent) {
        if (motionEvent.getActionButton() == 0) {
            problem("No action button set. Action button should always be non-zero for " + android.view.MotionEvent.actionToString(motionEvent.getAction()));
        }
    }

    private void ensureHistorySizeIsZeroForThisAction(android.view.MotionEvent motionEvent) {
        int historySize = motionEvent.getHistorySize();
        if (historySize != 0) {
            problem("History size is " + historySize + " but it should always be 0 for " + android.view.MotionEvent.actionToString(motionEvent.getAction()));
        }
    }

    private boolean startEvent(android.view.InputEvent inputEvent, int i, java.lang.String str) {
        int sequenceNumber = inputEvent.getSequenceNumber();
        if (sequenceNumber == this.mLastEventSeq && i < this.mLastNestingLevel && str == this.mLastEventType) {
            return false;
        }
        if (i > 0) {
            this.mLastEventSeq = sequenceNumber;
            this.mLastEventType = str;
            this.mLastNestingLevel = i;
        } else {
            this.mLastEventSeq = -1;
            this.mLastEventType = null;
            this.mLastNestingLevel = 0;
        }
        this.mCurrentEvent = inputEvent;
        this.mCurrentEventType = str;
        return true;
    }

    private void finishEvent() {
        if (this.mViolationMessage != null && this.mViolationMessage.length() != 0) {
            if (!this.mCurrentEvent.isTainted()) {
                this.mViolationMessage.append("\n  in ").append(this.mCaller);
                this.mViolationMessage.append("\n  ");
                appendEvent(this.mViolationMessage, 0, this.mCurrentEvent, false);
                if (this.mRecentEvents != null) {
                    this.mViolationMessage.append("\n  -- recent events --");
                    int i = 0;
                    while (i < 5) {
                        int i2 = ((this.mMostRecentEventIndex + 5) - i) % 5;
                        android.view.InputEvent inputEvent = this.mRecentEvents[i2];
                        if (inputEvent == null) {
                            break;
                        }
                        this.mViolationMessage.append("\n  ");
                        i++;
                        appendEvent(this.mViolationMessage, i, inputEvent, this.mRecentEventsUnhandled[i2]);
                    }
                }
                android.util.Log.d(this.mLogTag, this.mViolationMessage.toString());
                this.mCurrentEvent.setTainted(true);
            }
            this.mViolationMessage.setLength(0);
        }
        if (this.mRecentEvents == null) {
            this.mRecentEvents = new android.view.InputEvent[5];
            this.mRecentEventsUnhandled = new boolean[5];
        }
        int i3 = (this.mMostRecentEventIndex + 1) % 5;
        this.mMostRecentEventIndex = i3;
        if (this.mRecentEvents[i3] != null) {
            this.mRecentEvents[i3].recycle();
        }
        this.mRecentEvents[i3] = this.mCurrentEvent.copy();
        this.mRecentEventsUnhandled[i3] = false;
        this.mCurrentEvent = null;
        this.mCurrentEventType = null;
    }

    private static void appendEvent(java.lang.StringBuilder sb, int i, android.view.InputEvent inputEvent, boolean z) {
        sb.append(i).append(": sent at ").append(inputEvent.getEventTimeNanos());
        sb.append(", ");
        if (z) {
            sb.append("(unhandled) ");
        }
        sb.append(inputEvent);
    }

    private void problem(java.lang.String str) {
        if (this.mViolationMessage == null) {
            this.mViolationMessage = new java.lang.StringBuilder();
        }
        if (this.mViolationMessage.length() == 0) {
            this.mViolationMessage.append(this.mCurrentEventType).append(": ");
        } else {
            this.mViolationMessage.append("\n  ");
        }
        this.mViolationMessage.append(str);
    }

    private android.view.InputEventConsistencyVerifier.KeyState findKeyState(int i, int i2, int i3, boolean z) {
        android.view.InputEventConsistencyVerifier.KeyState keyState = null;
        for (android.view.InputEventConsistencyVerifier.KeyState keyState2 = this.mKeyStateList; keyState2 != null; keyState2 = keyState2.next) {
            if (keyState2.deviceId == i && keyState2.source == i2 && keyState2.keyCode == i3) {
                if (z) {
                    if (keyState != null) {
                        keyState.next = keyState2.next;
                    } else {
                        this.mKeyStateList = keyState2.next;
                    }
                    keyState2.next = null;
                }
                return keyState2;
            }
            keyState = keyState2;
        }
        return null;
    }

    private void addKeyState(int i, int i2, int i3) {
        android.view.InputEventConsistencyVerifier.KeyState obtain = android.view.InputEventConsistencyVerifier.KeyState.obtain(i, i2, i3);
        obtain.next = this.mKeyStateList;
        this.mKeyStateList = obtain;
    }

    private static final class KeyState {
        private static android.view.InputEventConsistencyVerifier.KeyState mRecycledList;
        private static java.lang.Object mRecycledListLock = new java.lang.Object();
        public int deviceId;
        public int keyCode;
        public android.view.InputEventConsistencyVerifier.KeyState next;
        public int source;
        public boolean unhandled;

        private KeyState() {
        }

        public static android.view.InputEventConsistencyVerifier.KeyState obtain(int i, int i2, int i3) {
            android.view.InputEventConsistencyVerifier.KeyState keyState;
            synchronized (mRecycledListLock) {
                keyState = mRecycledList;
                if (keyState != null) {
                    mRecycledList = keyState.next;
                } else {
                    keyState = new android.view.InputEventConsistencyVerifier.KeyState();
                }
            }
            keyState.deviceId = i;
            keyState.source = i2;
            keyState.keyCode = i3;
            keyState.unhandled = false;
            return keyState;
        }

        public void recycle() {
            synchronized (mRecycledListLock) {
                this.next = mRecycledList;
                mRecycledList = this.next;
            }
        }
    }
}
