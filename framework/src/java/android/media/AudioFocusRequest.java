package android.media;

/* loaded from: classes2.dex */
public final class AudioFocusRequest {
    private static final android.media.AudioAttributes FOCUS_DEFAULT_ATTR = new android.media.AudioAttributes.Builder().setUsage(1).build();
    public static final java.lang.String KEY_ACCESSIBILITY_FORCE_FOCUS_DUCKING = "a11y_force_ducking";
    private final android.media.AudioAttributes mAttr;
    private final int mFlags;
    private final int mFocusGain;
    private final android.media.AudioManager.OnAudioFocusChangeListener mFocusListener;
    private final android.os.Handler mListenerHandler;

    private AudioFocusRequest(android.media.AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener, android.os.Handler handler, android.media.AudioAttributes audioAttributes, int i, int i2) {
        this.mFocusListener = onAudioFocusChangeListener;
        this.mListenerHandler = handler;
        this.mFocusGain = i;
        this.mAttr = audioAttributes;
        this.mFlags = i2;
    }

    static final boolean isValidFocusGain(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
                return true;
            default:
                return false;
        }
    }

    public android.media.AudioManager.OnAudioFocusChangeListener getOnAudioFocusChangeListener() {
        return this.mFocusListener;
    }

    public android.os.Handler getOnAudioFocusChangeListenerHandler() {
        return this.mListenerHandler;
    }

    public android.media.AudioAttributes getAudioAttributes() {
        return this.mAttr;
    }

    public int getFocusGain() {
        return this.mFocusGain;
    }

    public boolean willPauseWhenDucked() {
        return (this.mFlags & 2) == 2;
    }

    public boolean acceptsDelayedFocusGain() {
        return (this.mFlags & 1) == 1;
    }

    @android.annotation.SystemApi
    public boolean locksFocus() {
        return (this.mFlags & 4) == 4;
    }

    int getFlags() {
        return this.mFlags;
    }

    public static final class Builder {
        private boolean mA11yForceDucking;
        private android.media.AudioAttributes mAttr;
        private boolean mDelayedFocus;
        private int mFocusGain;
        private android.media.AudioManager.OnAudioFocusChangeListener mFocusListener;
        private boolean mFocusLocked;
        private android.os.Handler mListenerHandler;
        private boolean mPausesOnDuck;

        public Builder(int i) {
            this.mAttr = android.media.AudioFocusRequest.FOCUS_DEFAULT_ATTR;
            this.mPausesOnDuck = false;
            this.mDelayedFocus = false;
            this.mFocusLocked = false;
            this.mA11yForceDucking = false;
            setFocusGain(i);
        }

        public Builder(android.media.AudioFocusRequest audioFocusRequest) {
            this.mAttr = android.media.AudioFocusRequest.FOCUS_DEFAULT_ATTR;
            this.mPausesOnDuck = false;
            this.mDelayedFocus = false;
            this.mFocusLocked = false;
            this.mA11yForceDucking = false;
            if (audioFocusRequest == null) {
                throw new java.lang.IllegalArgumentException("Illegal null AudioFocusRequest");
            }
            this.mAttr = audioFocusRequest.mAttr;
            this.mFocusListener = audioFocusRequest.mFocusListener;
            this.mListenerHandler = audioFocusRequest.mListenerHandler;
            this.mFocusGain = audioFocusRequest.mFocusGain;
            this.mPausesOnDuck = audioFocusRequest.willPauseWhenDucked();
            this.mDelayedFocus = audioFocusRequest.acceptsDelayedFocusGain();
        }

        public android.media.AudioFocusRequest.Builder setFocusGain(int i) {
            if (!android.media.AudioFocusRequest.isValidFocusGain(i)) {
                throw new java.lang.IllegalArgumentException("Illegal audio focus gain type " + i);
            }
            this.mFocusGain = i;
            return this;
        }

        public android.media.AudioFocusRequest.Builder setOnAudioFocusChangeListener(android.media.AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener) {
            if (onAudioFocusChangeListener == null) {
                throw new java.lang.NullPointerException("Illegal null focus listener");
            }
            this.mFocusListener = onAudioFocusChangeListener;
            this.mListenerHandler = null;
            return this;
        }

        android.media.AudioFocusRequest.Builder setOnAudioFocusChangeListenerInt(android.media.AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener, android.os.Handler handler) {
            this.mFocusListener = onAudioFocusChangeListener;
            this.mListenerHandler = handler;
            return this;
        }

        public android.media.AudioFocusRequest.Builder setOnAudioFocusChangeListener(android.media.AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener, android.os.Handler handler) {
            if (onAudioFocusChangeListener == null || handler == null) {
                throw new java.lang.NullPointerException("Illegal null focus listener or handler");
            }
            this.mFocusListener = onAudioFocusChangeListener;
            this.mListenerHandler = handler;
            return this;
        }

        public android.media.AudioFocusRequest.Builder setAudioAttributes(android.media.AudioAttributes audioAttributes) {
            if (audioAttributes == null) {
                throw new java.lang.NullPointerException("Illegal null AudioAttributes");
            }
            this.mAttr = audioAttributes;
            return this;
        }

        public android.media.AudioFocusRequest.Builder setWillPauseWhenDucked(boolean z) {
            this.mPausesOnDuck = z;
            return this;
        }

        public android.media.AudioFocusRequest.Builder setAcceptsDelayedFocusGain(boolean z) {
            this.mDelayedFocus = z;
            return this;
        }

        @android.annotation.SystemApi
        public android.media.AudioFocusRequest.Builder setLocksFocus(boolean z) {
            this.mFocusLocked = z;
            return this;
        }

        public android.media.AudioFocusRequest.Builder setForceDucking(boolean z) {
            this.mA11yForceDucking = z;
            return this;
        }

        public android.media.AudioFocusRequest build() {
            android.os.Bundle bundle;
            if ((this.mDelayedFocus || this.mPausesOnDuck) && this.mFocusListener == null) {
                throw new java.lang.IllegalStateException("Can't use delayed focus or pause on duck without a listener");
            }
            if (this.mA11yForceDucking) {
                if (this.mAttr.getBundle() == null) {
                    bundle = new android.os.Bundle();
                } else {
                    bundle = this.mAttr.getBundle();
                }
                bundle.putBoolean(android.media.AudioFocusRequest.KEY_ACCESSIBILITY_FORCE_FOCUS_DUCKING, true);
                this.mAttr = new android.media.AudioAttributes.Builder(this.mAttr).addBundle(bundle).build();
            }
            return new android.media.AudioFocusRequest(this.mFocusListener, this.mListenerHandler, this.mAttr, this.mFocusGain, (this.mDelayedFocus ? 1 : 0) | 0 | (this.mPausesOnDuck ? 2 : 0) | (this.mFocusLocked ? 4 : 0));
        }
    }
}
