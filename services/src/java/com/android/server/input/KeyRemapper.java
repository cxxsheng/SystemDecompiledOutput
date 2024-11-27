package com.android.server.input;

/* loaded from: classes2.dex */
final class KeyRemapper implements android.hardware.input.InputManager.InputDeviceListener {
    private static final int MSG_CLEAR_ALL_REMAPPING = 3;
    private static final int MSG_REMAP_KEY = 2;
    private static final int MSG_UPDATE_EXISTING_DEVICES = 1;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mDataStore"})
    private final com.android.server.input.PersistentDataStore mDataStore;
    private final android.os.Handler mHandler;
    private final com.android.server.input.NativeInputManagerService mNative;

    KeyRemapper(android.content.Context context, com.android.server.input.NativeInputManagerService nativeInputManagerService, com.android.server.input.PersistentDataStore persistentDataStore, android.os.Looper looper) {
        this.mContext = context;
        this.mNative = nativeInputManagerService;
        this.mDataStore = persistentDataStore;
        this.mHandler = new android.os.Handler(looper, new android.os.Handler.Callback() { // from class: com.android.server.input.KeyRemapper$$ExternalSyntheticLambda0
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(android.os.Message message) {
                boolean handleMessage;
                handleMessage = com.android.server.input.KeyRemapper.this.handleMessage(message);
                return handleMessage;
            }
        });
    }

    public void systemRunning() {
        android.hardware.input.InputManager inputManager = (android.hardware.input.InputManager) this.mContext.getSystemService(android.hardware.input.InputManager.class);
        java.util.Objects.requireNonNull(inputManager);
        inputManager.registerInputDeviceListener(this, this.mHandler);
        this.mHandler.sendMessage(android.os.Message.obtain(this.mHandler, 1, inputManager.getInputDeviceIds()));
    }

    public void remapKey(int i, int i2) {
        if (!supportRemapping()) {
            return;
        }
        this.mHandler.sendMessage(android.os.Message.obtain(this.mHandler, 2, i, i2));
    }

    public void clearAllKeyRemappings() {
        if (!supportRemapping()) {
            return;
        }
        this.mHandler.sendMessage(android.os.Message.obtain(this.mHandler, 3));
    }

    public java.util.Map<java.lang.Integer, java.lang.Integer> getKeyRemapping() {
        java.util.Map<java.lang.Integer, java.lang.Integer> keyRemapping;
        if (!supportRemapping()) {
            return new android.util.ArrayMap();
        }
        synchronized (this.mDataStore) {
            keyRemapping = this.mDataStore.getKeyRemapping();
        }
        return keyRemapping;
    }

    private void addKeyRemapping(int i, int i2) {
        android.hardware.input.InputManager inputManager = (android.hardware.input.InputManager) this.mContext.getSystemService(android.hardware.input.InputManager.class);
        java.util.Objects.requireNonNull(inputManager);
        for (int i3 : inputManager.getInputDeviceIds()) {
            android.view.InputDevice inputDevice = inputManager.getInputDevice(i3);
            if (inputDevice != null && !inputDevice.isVirtual() && inputDevice.isFullKeyboard()) {
                this.mNative.addKeyRemapping(i3, i, i2);
            }
        }
    }

    private void remapKeyInternal(int i, int i2) {
        addKeyRemapping(i, i2);
        synchronized (this.mDataStore) {
            try {
                try {
                    if (i == i2) {
                        this.mDataStore.clearMappedKey(i);
                    } else {
                        this.mDataStore.remapKey(i, i2);
                    }
                    this.mDataStore.saveIfNeeded();
                } catch (java.lang.Throwable th) {
                    this.mDataStore.saveIfNeeded();
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
    }

    private void clearAllRemappingsInternal() {
        synchronized (this.mDataStore) {
            try {
                try {
                    java.util.Iterator<java.lang.Integer> it = this.mDataStore.getKeyRemapping().keySet().iterator();
                    while (it.hasNext()) {
                        int intValue = it.next().intValue();
                        this.mDataStore.clearMappedKey(intValue);
                        addKeyRemapping(intValue, intValue);
                    }
                } finally {
                    this.mDataStore.saveIfNeeded();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceAdded(final int i) {
        if (!supportRemapping()) {
            return;
        }
        android.hardware.input.InputManager inputManager = (android.hardware.input.InputManager) this.mContext.getSystemService(android.hardware.input.InputManager.class);
        java.util.Objects.requireNonNull(inputManager);
        android.view.InputDevice inputDevice = inputManager.getInputDevice(i);
        if (inputDevice != null && !inputDevice.isVirtual() && inputDevice.isFullKeyboard()) {
            getKeyRemapping().forEach(new java.util.function.BiConsumer() { // from class: com.android.server.input.KeyRemapper$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    com.android.server.input.KeyRemapper.this.lambda$onInputDeviceAdded$0(i, (java.lang.Integer) obj, (java.lang.Integer) obj2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onInputDeviceAdded$0(int i, java.lang.Integer num, java.lang.Integer num2) {
        this.mNative.addKeyRemapping(i, num.intValue(), num2.intValue());
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceRemoved(int i) {
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceChanged(int i) {
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
                remapKeyInternal(message.arg1, message.arg2);
                return true;
            case 3:
                clearAllRemappingsInternal();
                return true;
            default:
                return false;
        }
    }

    private boolean supportRemapping() {
        return android.util.FeatureFlagUtils.isEnabled(this.mContext, "settings_new_keyboard_modifier_key");
    }
}
