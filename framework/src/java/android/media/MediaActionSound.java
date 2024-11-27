package android.media;

/* loaded from: classes2.dex */
public class MediaActionSound {
    public static final int FOCUS_COMPLETE = 1;
    private static final int NUM_MEDIA_SOUND_STREAMS = 1;
    public static final int SHUTTER_CLICK = 0;
    private static final java.lang.String[] SOUND_DIRS = {"/product/media/audio/ui/", "/system/media/audio/ui/"};
    private static final java.lang.String[] SOUND_FILES = {"camera_click.ogg", "camera_focus.ogg", "VideoRecord.ogg", "VideoStop.ogg"};
    public static final int START_VIDEO_RECORDING = 2;
    private static final int STATE_LOADED = 3;
    private static final int STATE_LOADING = 1;
    private static final int STATE_LOADING_PLAY_REQUESTED = 2;
    private static final int STATE_NOT_LOADED = 0;
    public static final int STOP_VIDEO_RECORDING = 3;
    private static final java.lang.String TAG = "MediaActionSound";
    private android.media.SoundPool.OnLoadCompleteListener mLoadCompleteListener = new android.media.SoundPool.OnLoadCompleteListener() { // from class: android.media.MediaActionSound.1
        @Override // android.media.SoundPool.OnLoadCompleteListener
        public void onLoadComplete(android.media.SoundPool soundPool, int i, int i2) {
            int i3;
            for (android.media.MediaActionSound.SoundState soundState : android.media.MediaActionSound.this.mSounds) {
                if (soundState.id == i) {
                    synchronized (soundState) {
                        if (i2 != 0) {
                            soundState.state = 0;
                            soundState.id = 0;
                            android.util.Log.e(android.media.MediaActionSound.TAG, "OnLoadCompleteListener() error: " + i2 + " loading sound: " + soundState.name);
                            return;
                        }
                        switch (soundState.state) {
                            case 1:
                                soundState.state = 3;
                                i3 = 0;
                                break;
                            case 2:
                                int i4 = soundState.id;
                                soundState.state = 3;
                                i3 = i4;
                                break;
                            default:
                                android.util.Log.e(android.media.MediaActionSound.TAG, "OnLoadCompleteListener() called in wrong state: " + soundState.state + " for sound: " + soundState.name);
                                i3 = 0;
                                break;
                        }
                        if (i3 != 0) {
                            soundPool.play(i3, 1.0f, 1.0f, 0, 0, 1.0f);
                            return;
                        }
                        return;
                    }
                }
            }
        }
    };
    private android.media.SoundPool mSoundPool = new android.media.SoundPool.Builder().setMaxStreams(1).setAudioAttributes(new android.media.AudioAttributes.Builder().setUsage(13).setFlags(1).setContentType(4).build()).build();
    private android.media.MediaActionSound.SoundState[] mSounds;

    public static boolean mustPlayShutterSound() {
        try {
            return android.media.IAudioService.Stub.asInterface(android.os.ServiceManager.getService("audio")).isCameraSoundForced();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "audio service is unavailable for queries, defaulting to false");
            return false;
        }
    }

    private class SoundState {
        public final int name;
        public int id = 0;
        public int state = 0;

        public SoundState(int i) {
            this.name = i;
        }
    }

    public MediaActionSound() {
        this.mSoundPool.setOnLoadCompleteListener(this.mLoadCompleteListener);
        this.mSounds = new android.media.MediaActionSound.SoundState[SOUND_FILES.length];
        for (int i = 0; i < this.mSounds.length; i++) {
            this.mSounds[i] = new android.media.MediaActionSound.SoundState(i);
        }
    }

    private int loadSound(android.media.MediaActionSound.SoundState soundState) {
        java.lang.String str = SOUND_FILES[soundState.name];
        for (java.lang.String str2 : SOUND_DIRS) {
            int load = this.mSoundPool.load(str2 + str, 1);
            if (load > 0) {
                soundState.state = 1;
                soundState.id = load;
                return load;
            }
        }
        return 0;
    }

    public void load(int i) {
        if (i < 0 || i >= SOUND_FILES.length) {
            throw new java.lang.RuntimeException("Unknown sound requested: " + i);
        }
        android.media.MediaActionSound.SoundState soundState = this.mSounds[i];
        synchronized (soundState) {
            switch (soundState.state) {
                case 0:
                    if (loadSound(soundState) <= 0) {
                        android.util.Log.e(TAG, "load() error loading sound: " + i);
                        break;
                    }
                    break;
                default:
                    android.util.Log.e(TAG, "load() called in wrong state: " + soundState + " for sound: " + i);
                    break;
            }
        }
    }

    public void play(int i) {
        if (i < 0 || i >= SOUND_FILES.length) {
            throw new java.lang.RuntimeException("Unknown sound requested: " + i);
        }
        android.media.MediaActionSound.SoundState soundState = this.mSounds[i];
        synchronized (soundState) {
            switch (soundState.state) {
                case 0:
                    loadSound(soundState);
                    if (loadSound(soundState) <= 0) {
                        android.util.Log.e(TAG, "play() error loading sound: " + i);
                        break;
                    }
                case 1:
                    soundState.state = 2;
                    break;
                case 2:
                default:
                    android.util.Log.e(TAG, "play() called in wrong state: " + soundState.state + " for sound: " + i);
                    break;
                case 3:
                    this.mSoundPool.play(soundState.id, 1.0f, 1.0f, 0, 0, 1.0f);
                    break;
            }
        }
    }

    public void release() {
        if (this.mSoundPool != null) {
            for (android.media.MediaActionSound.SoundState soundState : this.mSounds) {
                synchronized (soundState) {
                    soundState.state = 0;
                    soundState.id = 0;
                }
            }
            this.mSoundPool.release();
            this.mSoundPool = null;
        }
    }
}
