package com.android.server.input;

/* loaded from: classes2.dex */
final class KeyboardBacklightController implements com.android.server.input.InputManagerService.KeyboardBacklightControllerInterface, android.hardware.input.InputManager.InputDeviceListener {
    private static final int DEFAULT_NUM_BRIGHTNESS_CHANGE_STEPS = 10;
    private static final int MAX_BRIGHTNESS = 255;

    @com.android.internal.annotations.VisibleForTesting
    static final int MAX_BRIGHTNESS_CHANGE_STEPS = 10;
    private static final int MSG_DECREMENT_KEYBOARD_BACKLIGHT = 3;
    private static final int MSG_INCREMENT_KEYBOARD_BACKLIGHT = 2;
    private static final int MSG_INTERACTIVE_STATE_CHANGED = 6;
    private static final int MSG_NOTIFY_USER_ACTIVITY = 4;
    private static final int MSG_NOTIFY_USER_INACTIVITY = 5;
    private static final int MSG_UPDATE_EXISTING_DEVICES = 1;
    private static final java.lang.String UEVENT_KEYBOARD_BACKLIGHT_TAG = "kbd_backlight";
    private int mAmbientBacklightValue;
    private final com.android.server.input.AmbientKeyboardBacklightController mAmbientController;

    @android.annotation.Nullable
    private com.android.server.input.AmbientKeyboardBacklightController.AmbientKeyboardBacklightListener mAmbientListener;
    private final com.android.server.input.KeyboardBacklightController.AnimatorFactory mAnimatorFactory;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mDataStore"})
    private final com.android.server.input.PersistentDataStore mDataStore;
    private final android.os.Handler mHandler;
    private boolean mIsBacklightOn;
    private boolean mIsInteractive;

    @com.android.internal.annotations.GuardedBy({"mKeyboardBacklightListenerRecords"})
    private final android.util.SparseArray<com.android.server.input.KeyboardBacklightController.KeyboardBacklightListenerRecord> mKeyboardBacklightListenerRecords;
    private final android.util.SparseArray<com.android.server.input.KeyboardBacklightController.KeyboardBacklightState> mKeyboardBacklights;
    private final com.android.server.input.NativeInputManagerService mNative;
    private final com.android.server.input.UEventManager mUEventManager;
    private static final java.lang.String TAG = "KbdBacklightController";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private static final long TRANSITION_ANIMATION_DURATION_MILLIS = java.time.Duration.ofSeconds(1).toMillis();

    @com.android.internal.annotations.VisibleForTesting
    static final long USER_INACTIVITY_THRESHOLD_MILLIS = java.time.Duration.ofSeconds(30).toMillis();

    @com.android.internal.annotations.VisibleForTesting
    static final int[] DEFAULT_BRIGHTNESS_VALUE_FOR_LEVEL = new int[11];

    @com.android.internal.annotations.VisibleForTesting
    interface AnimatorFactory {
        android.animation.ValueAnimator makeIntAnimator(int i, int i2);
    }

    private enum Direction {
        DIRECTION_UP,
        DIRECTION_DOWN
    }

    static {
        for (int i = 0; i <= 10; i++) {
            DEFAULT_BRIGHTNESS_VALUE_FOR_LEVEL[i] = (int) java.lang.Math.floor((i * 255.0f) / 10.0f);
        }
    }

    KeyboardBacklightController(android.content.Context context, com.android.server.input.NativeInputManagerService nativeInputManagerService, com.android.server.input.PersistentDataStore persistentDataStore, android.os.Looper looper, com.android.server.input.UEventManager uEventManager) {
        this(context, nativeInputManagerService, persistentDataStore, looper, new com.android.server.input.KeyboardBacklightController.AnimatorFactory() { // from class: com.android.server.input.KeyboardBacklightController$$ExternalSyntheticLambda0
            @Override // com.android.server.input.KeyboardBacklightController.AnimatorFactory
            public final android.animation.ValueAnimator makeIntAnimator(int i, int i2) {
                android.animation.ValueAnimator lambda$new$0;
                lambda$new$0 = com.android.server.input.KeyboardBacklightController.lambda$new$0(i, i2);
                return lambda$new$0;
            }
        }, uEventManager);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.animation.ValueAnimator lambda$new$0(int i, int i2) {
        return android.animation.ValueAnimator.ofInt(i, i2);
    }

    @com.android.internal.annotations.VisibleForTesting
    KeyboardBacklightController(android.content.Context context, com.android.server.input.NativeInputManagerService nativeInputManagerService, com.android.server.input.PersistentDataStore persistentDataStore, android.os.Looper looper, com.android.server.input.KeyboardBacklightController.AnimatorFactory animatorFactory, com.android.server.input.UEventManager uEventManager) {
        this.mKeyboardBacklights = new android.util.SparseArray<>(1);
        this.mIsBacklightOn = false;
        this.mIsInteractive = true;
        this.mKeyboardBacklightListenerRecords = new android.util.SparseArray<>();
        this.mAmbientBacklightValue = 0;
        this.mContext = context;
        this.mNative = nativeInputManagerService;
        this.mDataStore = persistentDataStore;
        this.mHandler = new android.os.Handler(looper, new android.os.Handler.Callback() { // from class: com.android.server.input.KeyboardBacklightController$$ExternalSyntheticLambda2
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(android.os.Message message) {
                boolean handleMessage;
                handleMessage = com.android.server.input.KeyboardBacklightController.this.handleMessage(message);
                return handleMessage;
            }
        });
        this.mAnimatorFactory = animatorFactory;
        this.mAmbientController = new com.android.server.input.AmbientKeyboardBacklightController(context, looper);
        this.mUEventManager = uEventManager;
    }

    @Override // com.android.server.input.InputManagerService.KeyboardBacklightControllerInterface
    public void systemRunning() {
        android.hardware.input.InputManager inputManager = (android.hardware.input.InputManager) this.mContext.getSystemService(android.hardware.input.InputManager.class);
        java.util.Objects.requireNonNull(inputManager);
        inputManager.registerInputDeviceListener(this, this.mHandler);
        this.mHandler.sendMessage(android.os.Message.obtain(this.mHandler, 1, inputManager.getInputDeviceIds()));
        this.mUEventManager.addListener(new com.android.server.input.UEventManager.UEventListener() { // from class: com.android.server.input.KeyboardBacklightController.1
            @Override // com.android.server.input.UEventManager.UEventListener
            public void onUEvent(android.os.UEventObserver.UEvent uEvent) {
                com.android.server.input.KeyboardBacklightController.this.onKeyboardBacklightUEvent(uEvent);
            }
        }, UEVENT_KEYBOARD_BACKLIGHT_TAG);
        if (com.android.server.input.InputFeatureFlagProvider.isAmbientKeyboardBacklightControlEnabled()) {
            this.mAmbientController.systemRunning();
        }
    }

    @Override // com.android.server.input.InputManagerService.KeyboardBacklightControllerInterface
    public void incrementKeyboardBacklight(int i) {
        this.mHandler.sendMessage(android.os.Message.obtain(this.mHandler, 2, java.lang.Integer.valueOf(i)));
    }

    @Override // com.android.server.input.InputManagerService.KeyboardBacklightControllerInterface
    public void decrementKeyboardBacklight(int i) {
        this.mHandler.sendMessage(android.os.Message.obtain(this.mHandler, 3, java.lang.Integer.valueOf(i)));
    }

    @Override // com.android.server.input.InputManagerService.KeyboardBacklightControllerInterface
    public void notifyUserActivity() {
        this.mHandler.sendMessage(android.os.Message.obtain(this.mHandler, 4));
    }

    @Override // com.android.server.input.InputManagerService.KeyboardBacklightControllerInterface
    public void onInteractiveChanged(boolean z) {
        this.mHandler.sendMessage(android.os.Message.obtain(this.mHandler, 6, java.lang.Boolean.valueOf(z)));
    }

    private void updateKeyboardBacklight(int i, com.android.server.input.KeyboardBacklightController.Direction direction) {
        int i2;
        int max;
        android.view.InputDevice inputDevice = getInputDevice(i);
        com.android.server.input.KeyboardBacklightController.KeyboardBacklightState keyboardBacklightState = this.mKeyboardBacklights.get(i);
        if (inputDevice == null || keyboardBacklightState == null) {
            return;
        }
        if (keyboardBacklightState.mUseAmbientController) {
            i2 = java.util.Arrays.binarySearch(keyboardBacklightState.mBrightnessValueForLevel, this.mAmbientBacklightValue);
            if (i2 < 0) {
                i2 = java.lang.Math.max(0, (-(i2 + 1)) - 1);
                if (direction != com.android.server.input.KeyboardBacklightController.Direction.DIRECTION_UP) {
                    i2++;
                }
            }
        } else {
            i2 = keyboardBacklightState.mBrightnessLevel;
        }
        if (direction == com.android.server.input.KeyboardBacklightController.Direction.DIRECTION_UP) {
            max = java.lang.Math.min(i2 + 1, keyboardBacklightState.getNumBrightnessChangeSteps());
        } else {
            max = java.lang.Math.max(i2 - 1, 0);
        }
        keyboardBacklightState.setBrightnessLevel(max);
        updateAmbientLightListener();
        maybeBackupBacklightBrightness(inputDevice, keyboardBacklightState.mLight, keyboardBacklightState.mBrightnessValueForLevel[max]);
        if (DEBUG) {
            android.util.Slog.d(TAG, "Changing state from " + keyboardBacklightState.mBrightnessLevel + " to " + max);
        }
        synchronized (this.mKeyboardBacklightListenerRecords) {
            for (int i3 = 0; i3 < this.mKeyboardBacklightListenerRecords.size(); i3++) {
                try {
                    android.hardware.input.IKeyboardBacklightState iKeyboardBacklightState = new android.hardware.input.IKeyboardBacklightState();
                    iKeyboardBacklightState.brightnessLevel = max;
                    iKeyboardBacklightState.maxBrightnessLevel = keyboardBacklightState.getNumBrightnessChangeSteps();
                    this.mKeyboardBacklightListenerRecords.valueAt(i3).notifyKeyboardBacklightChanged(i, iKeyboardBacklightState, true);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private void maybeBackupBacklightBrightness(android.view.InputDevice inputDevice, android.hardware.lights.Light light, int i) {
        if (com.android.server.input.InputFeatureFlagProvider.isAmbientKeyboardBacklightControlEnabled()) {
            return;
        }
        synchronized (this.mDataStore) {
            try {
                try {
                    this.mDataStore.setKeyboardBacklightBrightness(inputDevice.getDescriptor(), light.getId(), i);
                } finally {
                    this.mDataStore.saveIfNeeded();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void maybeRestoreBacklightBrightness(android.view.InputDevice inputDevice, android.hardware.lights.Light light) {
        java.util.OptionalInt keyboardBacklightBrightness;
        if (com.android.server.input.InputFeatureFlagProvider.isAmbientKeyboardBacklightControlEnabled()) {
            return;
        }
        com.android.server.input.KeyboardBacklightController.KeyboardBacklightState keyboardBacklightState = this.mKeyboardBacklights.get(inputDevice.getId());
        synchronized (this.mDataStore) {
            keyboardBacklightBrightness = this.mDataStore.getKeyboardBacklightBrightness(inputDevice.getDescriptor(), light.getId());
        }
        if (keyboardBacklightState != null && keyboardBacklightBrightness.isPresent()) {
            int binarySearch = java.util.Arrays.binarySearch(keyboardBacklightState.mBrightnessValueForLevel, java.lang.Math.max(0, java.lang.Math.min(255, keyboardBacklightBrightness.getAsInt())));
            if (binarySearch < 0) {
                binarySearch = java.lang.Math.min(keyboardBacklightState.getNumBrightnessChangeSteps(), -(binarySearch + 1));
            }
            keyboardBacklightState.setBrightnessLevel(binarySearch);
            if (DEBUG) {
                android.util.Slog.d(TAG, "Restoring brightness level " + keyboardBacklightBrightness.getAsInt());
            }
        }
    }

    private void handleUserActivity() {
        if (!this.mIsInteractive) {
            return;
        }
        this.mIsBacklightOn = true;
        for (int i = 0; i < this.mKeyboardBacklights.size(); i++) {
            this.mKeyboardBacklights.valueAt(i).onBacklightStateChanged();
        }
        this.mHandler.removeMessages(5);
        this.mHandler.sendEmptyMessageAtTime(5, android.os.SystemClock.uptimeMillis() + USER_INACTIVITY_THRESHOLD_MILLIS);
    }

    private void handleUserInactivity() {
        this.mIsBacklightOn = false;
        for (int i = 0; i < this.mKeyboardBacklights.size(); i++) {
            this.mKeyboardBacklights.valueAt(i).onBacklightStateChanged();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public void handleInteractiveStateChange(boolean z) {
        this.mIsInteractive = z;
        if (z) {
            handleUserActivity();
        } else {
            handleUserInactivity();
        }
        updateAmbientLightListener();
    }

    @com.android.internal.annotations.VisibleForTesting
    public void handleAmbientLightValueChanged(int i) {
        this.mAmbientBacklightValue = i;
        for (int i2 = 0; i2 < this.mKeyboardBacklights.size(); i2++) {
            this.mKeyboardBacklights.valueAt(i2).onAmbientBacklightValueChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean handleMessage(android.os.Message message) {
        switch (message.what) {
            case 1:
                for (int i : (int[]) message.obj) {
                    onInputDeviceAdded(i);
                }
                return true;
            case 2:
                updateKeyboardBacklight(((java.lang.Integer) message.obj).intValue(), com.android.server.input.KeyboardBacklightController.Direction.DIRECTION_UP);
                return true;
            case 3:
                updateKeyboardBacklight(((java.lang.Integer) message.obj).intValue(), com.android.server.input.KeyboardBacklightController.Direction.DIRECTION_DOWN);
                return true;
            case 4:
                handleUserActivity();
                return true;
            case 5:
                handleUserInactivity();
                return true;
            case 6:
                handleInteractiveStateChange(((java.lang.Boolean) message.obj).booleanValue());
                return true;
            default:
                return false;
        }
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    @com.android.internal.annotations.VisibleForTesting
    public void onInputDeviceAdded(int i) {
        onInputDeviceChanged(i);
        updateAmbientLightListener();
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    @com.android.internal.annotations.VisibleForTesting
    public void onInputDeviceRemoved(int i) {
        this.mKeyboardBacklights.remove(i);
        updateAmbientLightListener();
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    @com.android.internal.annotations.VisibleForTesting
    public void onInputDeviceChanged(int i) {
        android.view.InputDevice inputDevice = getInputDevice(i);
        if (inputDevice == null) {
            return;
        }
        android.hardware.lights.Light keyboardBacklight = getKeyboardBacklight(inputDevice);
        if (keyboardBacklight == null) {
            this.mKeyboardBacklights.remove(i);
            return;
        }
        com.android.server.input.KeyboardBacklightController.KeyboardBacklightState keyboardBacklightState = this.mKeyboardBacklights.get(i);
        if (keyboardBacklightState != null && keyboardBacklightState.mLight.getId() == keyboardBacklight.getId()) {
            return;
        }
        this.mKeyboardBacklights.put(i, new com.android.server.input.KeyboardBacklightController.KeyboardBacklightState(i, keyboardBacklight));
        maybeRestoreBacklightBrightness(inputDevice, keyboardBacklight);
    }

    private android.view.InputDevice getInputDevice(int i) {
        android.hardware.input.InputManager inputManager = (android.hardware.input.InputManager) this.mContext.getSystemService(android.hardware.input.InputManager.class);
        if (inputManager != null) {
            return inputManager.getInputDevice(i);
        }
        return null;
    }

    private android.hardware.lights.Light getKeyboardBacklight(android.view.InputDevice inputDevice) {
        for (android.hardware.lights.Light light : inputDevice.getLightsManager().getLights()) {
            if (light.getType() == 10003 && light.hasBrightnessControl()) {
                return light;
            }
        }
        return null;
    }

    @Override // com.android.server.input.InputManagerService.KeyboardBacklightControllerInterface
    public void registerKeyboardBacklightListener(android.hardware.input.IKeyboardBacklightListener iKeyboardBacklightListener, int i) {
        synchronized (this.mKeyboardBacklightListenerRecords) {
            try {
                if (this.mKeyboardBacklightListenerRecords.get(i) != null) {
                    throw new java.lang.IllegalStateException("The calling process has already registered a KeyboardBacklightListener.");
                }
                com.android.server.input.KeyboardBacklightController.KeyboardBacklightListenerRecord keyboardBacklightListenerRecord = new com.android.server.input.KeyboardBacklightController.KeyboardBacklightListenerRecord(i, iKeyboardBacklightListener);
                try {
                    iKeyboardBacklightListener.asBinder().linkToDeath(keyboardBacklightListenerRecord, 0);
                    this.mKeyboardBacklightListenerRecords.put(i, keyboardBacklightListenerRecord);
                } catch (android.os.RemoteException e) {
                    throw new java.lang.RuntimeException(e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.input.InputManagerService.KeyboardBacklightControllerInterface
    public void unregisterKeyboardBacklightListener(android.hardware.input.IKeyboardBacklightListener iKeyboardBacklightListener, int i) {
        synchronized (this.mKeyboardBacklightListenerRecords) {
            try {
                com.android.server.input.KeyboardBacklightController.KeyboardBacklightListenerRecord keyboardBacklightListenerRecord = this.mKeyboardBacklightListenerRecords.get(i);
                if (keyboardBacklightListenerRecord == null) {
                    throw new java.lang.IllegalStateException("The calling process has no registered KeyboardBacklightListener.");
                }
                if (keyboardBacklightListenerRecord.mListener.asBinder() != iKeyboardBacklightListener.asBinder()) {
                    throw new java.lang.IllegalStateException("The calling process has a different registered KeyboardBacklightListener.");
                }
                keyboardBacklightListenerRecord.mListener.asBinder().unlinkToDeath(keyboardBacklightListenerRecord, 0);
                this.mKeyboardBacklightListenerRecords.remove(i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onKeyboardBacklightListenerDied(int i) {
        synchronized (this.mKeyboardBacklightListenerRecords) {
            this.mKeyboardBacklightListenerRecords.remove(i);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public void onKeyboardBacklightUEvent(android.os.UEventObserver.UEvent uEvent) {
        if ("ADD".equalsIgnoreCase(uEvent.get("ACTION")) && "LEDS".equalsIgnoreCase(uEvent.get("SUBSYSTEM"))) {
            java.lang.String str = uEvent.get("DEVPATH");
            if (isValidBacklightNodePath(str)) {
                this.mNative.sysfsNodeChanged("/sys" + str);
            }
        }
    }

    private void updateAmbientLightListener() {
        if (!com.android.server.input.InputFeatureFlagProvider.isAmbientKeyboardBacklightControlEnabled()) {
            return;
        }
        boolean z = false;
        for (int i = 0; i < this.mKeyboardBacklights.size(); i++) {
            z |= this.mKeyboardBacklights.valueAt(i).mUseAmbientController;
        }
        boolean z2 = this.mIsInteractive & z;
        if (z2 && this.mAmbientListener == null) {
            this.mAmbientListener = new com.android.server.input.AmbientKeyboardBacklightController.AmbientKeyboardBacklightListener() { // from class: com.android.server.input.KeyboardBacklightController$$ExternalSyntheticLambda1
                @Override // com.android.server.input.AmbientKeyboardBacklightController.AmbientKeyboardBacklightListener
                public final void onKeyboardBacklightValueChanged(int i2) {
                    com.android.server.input.KeyboardBacklightController.this.handleAmbientLightValueChanged(i2);
                }
            };
            this.mAmbientController.registerAmbientBacklightListener(this.mAmbientListener);
        }
        if (!z2 && this.mAmbientListener != null) {
            this.mAmbientController.unregisterAmbientBacklightListener(this.mAmbientListener);
            this.mAmbientListener = null;
        }
    }

    private static boolean isValidBacklightNodePath(java.lang.String str) {
        int lastIndexOf;
        if (android.text.TextUtils.isEmpty(str) || (lastIndexOf = str.lastIndexOf(47)) < 0) {
            return false;
        }
        java.lang.String substring = str.substring(lastIndexOf + 1);
        java.lang.String substring2 = str.substring(0, lastIndexOf);
        return substring2.endsWith("leds") && substring.contains(UEVENT_KEYBOARD_BACKLIGHT_TAG) && substring2.lastIndexOf(47) >= 0;
    }

    @Override // com.android.server.input.InputManagerService.KeyboardBacklightControllerInterface
    public void dump(java.io.PrintWriter printWriter) {
        android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter);
        indentingPrintWriter.println("KbdBacklightController: " + this.mKeyboardBacklights.size() + " keyboard backlights");
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < this.mKeyboardBacklights.size(); i++) {
            indentingPrintWriter.println(i + ": " + this.mKeyboardBacklights.valueAt(i).toString());
        }
        indentingPrintWriter.decreaseIndent();
    }

    private class KeyboardBacklightListenerRecord implements android.os.IBinder.DeathRecipient {
        public final android.hardware.input.IKeyboardBacklightListener mListener;
        public final int mPid;

        KeyboardBacklightListenerRecord(int i, android.hardware.input.IKeyboardBacklightListener iKeyboardBacklightListener) {
            this.mPid = i;
            this.mListener = iKeyboardBacklightListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            if (com.android.server.input.KeyboardBacklightController.DEBUG) {
                android.util.Slog.d(com.android.server.input.KeyboardBacklightController.TAG, "Keyboard backlight listener for pid " + this.mPid + " died.");
            }
            com.android.server.input.KeyboardBacklightController.this.onKeyboardBacklightListenerDied(this.mPid);
        }

        public void notifyKeyboardBacklightChanged(int i, android.hardware.input.IKeyboardBacklightState iKeyboardBacklightState, boolean z) {
            try {
                this.mListener.onBrightnessChanged(i, iKeyboardBacklightState, z);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.input.KeyboardBacklightController.TAG, "Failed to notify process " + this.mPid + " that keyboard backlight changed, assuming it died.", e);
                binderDied();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class KeyboardBacklightState {
        private android.animation.ValueAnimator mAnimator;
        private int mBrightnessLevel;
        private final int mDeviceId;
        private final android.hardware.lights.Light mLight;
        private boolean mUseAmbientController = com.android.server.input.InputFeatureFlagProvider.isAmbientKeyboardBacklightControlEnabled();
        private final int[] mBrightnessValueForLevel = setupBrightnessLevels();

        KeyboardBacklightState(int i, android.hardware.lights.Light light) {
            this.mDeviceId = i;
            this.mLight = light;
        }

        private int[] setupBrightnessLevels() {
            if (!com.android.server.input.InputFeatureFlagProvider.isKeyboardBacklightCustomLevelsEnabled()) {
                return com.android.server.input.KeyboardBacklightController.DEFAULT_BRIGHTNESS_VALUE_FOR_LEVEL;
            }
            int[] preferredBrightnessLevels = this.mLight.getPreferredBrightnessLevels();
            if (preferredBrightnessLevels == null || preferredBrightnessLevels.length == 0) {
                return com.android.server.input.KeyboardBacklightController.DEFAULT_BRIGHTNESS_VALUE_FOR_LEVEL;
            }
            java.util.TreeSet treeSet = new java.util.TreeSet();
            int i = 0;
            treeSet.add(0);
            for (int i2 : preferredBrightnessLevels) {
                if (i2 > 0 && i2 < 255) {
                    treeSet.add(java.lang.Integer.valueOf(i2));
                }
            }
            treeSet.add(255);
            if (treeSet.size() - 1 > 10) {
                return com.android.server.input.KeyboardBacklightController.DEFAULT_BRIGHTNESS_VALUE_FOR_LEVEL;
            }
            int[] iArr = new int[treeSet.size()];
            java.util.Iterator it = treeSet.iterator();
            while (it.hasNext()) {
                iArr[i] = ((java.lang.Integer) it.next()).intValue();
                i++;
            }
            return iArr;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getNumBrightnessChangeSteps() {
            return this.mBrightnessValueForLevel.length - 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onBacklightStateChanged() {
            int i = this.mUseAmbientController ? com.android.server.input.KeyboardBacklightController.this.mAmbientBacklightValue : this.mBrightnessValueForLevel[this.mBrightnessLevel];
            if (!com.android.server.input.KeyboardBacklightController.this.mIsBacklightOn) {
                i = 0;
            }
            setBacklightValue(i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setBrightnessLevel(int i) {
            this.mUseAmbientController = false;
            if (com.android.server.input.KeyboardBacklightController.this.mIsBacklightOn) {
                setBacklightValue(this.mBrightnessValueForLevel[i]);
            }
            this.mBrightnessLevel = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onAmbientBacklightValueChanged() {
            if (com.android.server.input.KeyboardBacklightController.this.mIsBacklightOn && this.mUseAmbientController) {
                setBacklightValue(com.android.server.input.KeyboardBacklightController.this.mAmbientBacklightValue);
            }
        }

        private void cancelAnimation() {
            if (this.mAnimator != null && this.mAnimator.isRunning()) {
                this.mAnimator.cancel();
            }
        }

        private void setBacklightValue(int i) {
            int alpha = android.graphics.Color.alpha(com.android.server.input.KeyboardBacklightController.this.mNative.getLightColor(this.mDeviceId, this.mLight.getId()));
            if (alpha == i) {
                return;
            }
            if (com.android.server.input.InputFeatureFlagProvider.isKeyboardBacklightAnimationEnabled()) {
                startAnimation(alpha, i);
            } else {
                com.android.server.input.KeyboardBacklightController.this.mNative.setLightColor(this.mDeviceId, this.mLight.getId(), android.graphics.Color.argb(i, 0, 0, 0));
            }
        }

        private void startAnimation(int i, int i2) {
            cancelAnimation();
            this.mAnimator = com.android.server.input.KeyboardBacklightController.this.mAnimatorFactory.makeIntAnimator(i, i2);
            this.mAnimator.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() { // from class: com.android.server.input.KeyboardBacklightController$KeyboardBacklightState$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
                    com.android.server.input.KeyboardBacklightController.KeyboardBacklightState.this.lambda$startAnimation$0(valueAnimator);
                }
            });
            this.mAnimator.setDuration(com.android.server.input.KeyboardBacklightController.TRANSITION_ANIMATION_DURATION_MILLIS).start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$startAnimation$0(android.animation.ValueAnimator valueAnimator) {
            com.android.server.input.KeyboardBacklightController.this.mNative.setLightColor(this.mDeviceId, this.mLight.getId(), android.graphics.Color.argb(((java.lang.Integer) valueAnimator.getAnimatedValue()).intValue(), 0, 0, 0));
        }

        public java.lang.String toString() {
            return "KeyboardBacklightState{Light=" + this.mLight.getId() + ", BrightnessLevel=" + this.mBrightnessLevel + "}";
        }
    }
}
