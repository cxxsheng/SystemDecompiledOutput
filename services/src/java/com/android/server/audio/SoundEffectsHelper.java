package com.android.server.audio;

/* loaded from: classes.dex */
class SoundEffectsHelper {
    private static final java.lang.String ASSET_FILE_VERSION = "1.0";
    private static final java.lang.String ATTR_ASSET_FILE = "file";
    private static final java.lang.String ATTR_ASSET_ID = "id";
    private static final java.lang.String ATTR_GROUP_NAME = "name";
    private static final java.lang.String ATTR_VERSION = "version";
    private static final int EFFECT_NOT_IN_SOUND_POOL = 0;
    private static final java.lang.String GROUP_TOUCH_SOUNDS = "touch_sounds";
    private static final int MSG_LOAD_EFFECTS = 0;
    private static final int MSG_LOAD_EFFECTS_TIMEOUT = 3;
    private static final int MSG_PLAY_EFFECT = 2;
    private static final int MSG_UNLOAD_EFFECTS = 1;
    private static final int NUM_SOUNDPOOL_CHANNELS = 4;
    private static final int SOUND_EFFECTS_LOAD_TIMEOUT_MS = 15000;
    private static final java.lang.String SOUND_EFFECTS_PATH = "/media/audio/ui/";
    private static final java.lang.String TAG = "AS.SfxHelper";
    private static final java.lang.String TAG_ASSET = "asset";
    private static final java.lang.String TAG_AUDIO_ASSETS = "audio_assets";
    private static final java.lang.String TAG_GROUP = "group";
    private final android.content.Context mContext;
    private final java.util.function.Consumer<android.media.PlayerBase> mPlayerAvailableCb;
    private final int mSfxAttenuationDb;
    private com.android.server.audio.SoundEffectsHelper.SfxHandler mSfxHandler;
    private com.android.server.audio.SoundEffectsHelper.SfxWorker mSfxWorker;
    private android.media.SoundPool mSoundPool;
    private com.android.server.audio.SoundEffectsHelper.SoundPoolLoader mSoundPoolLoader;
    private final com.android.server.utils.EventLogger mSfxLogger = new com.android.server.utils.EventLogger(26, "Sound Effects Loading");
    private final java.util.List<com.android.server.audio.SoundEffectsHelper.Resource> mResources = new java.util.ArrayList();
    private final int[] mEffects = new int[16];

    interface OnEffectsLoadCompleteHandler {
        void run(boolean z);
    }

    private static final class Resource {
        final java.lang.String mFileName;
        boolean mLoaded;
        int mSampleId = 0;

        Resource(java.lang.String str) {
            this.mFileName = str;
        }

        void unload() {
            this.mSampleId = 0;
            this.mLoaded = false;
        }
    }

    SoundEffectsHelper(android.content.Context context, java.util.function.Consumer<android.media.PlayerBase> consumer) {
        this.mContext = context;
        this.mSfxAttenuationDb = this.mContext.getResources().getInteger(android.R.integer.config_sideFpsToastTimeout);
        this.mPlayerAvailableCb = consumer;
        startWorker();
    }

    void loadSoundEffects(com.android.server.audio.SoundEffectsHelper.OnEffectsLoadCompleteHandler onEffectsLoadCompleteHandler) {
        sendMsg(0, 0, 0, onEffectsLoadCompleteHandler, 0);
    }

    void unloadSoundEffects() {
        sendMsg(1, 0, 0, null, 0);
    }

    void playSoundEffect(int i, int i2) {
        sendMsg(2, i, i2, null, 0);
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        if (this.mSfxHandler != null) {
            printWriter.println(str + "Message handler (watch for unhandled messages):");
            this.mSfxHandler.dump(new android.util.PrintWriterPrinter(printWriter), "  ");
        } else {
            printWriter.println(str + "Message handler is null");
        }
        printWriter.println(str + "Default attenuation (dB): " + this.mSfxAttenuationDb);
        this.mSfxLogger.dump(printWriter);
    }

    private void startWorker() {
        this.mSfxWorker = new com.android.server.audio.SoundEffectsHelper.SfxWorker();
        this.mSfxWorker.start();
        synchronized (this) {
            while (this.mSfxHandler == null) {
                try {
                    wait();
                } catch (java.lang.InterruptedException e) {
                    android.util.Log.w(TAG, "Interrupted while waiting " + this.mSfxWorker.getName() + " to start");
                }
            }
        }
    }

    private void sendMsg(int i, int i2, int i3, java.lang.Object obj, int i4) {
        this.mSfxHandler.sendMessageDelayed(this.mSfxHandler.obtainMessage(i, i2, i3, obj), i4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logEvent(java.lang.String str) {
        this.mSfxLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLoadSoundEffects(com.android.server.audio.SoundEffectsHelper.OnEffectsLoadCompleteHandler onEffectsLoadCompleteHandler) {
        if (this.mSoundPoolLoader != null) {
            this.mSoundPoolLoader.addHandler(onEffectsLoadCompleteHandler);
            return;
        }
        if (this.mSoundPool != null) {
            if (onEffectsLoadCompleteHandler != null) {
                onEffectsLoadCompleteHandler.run(true);
                return;
            }
            return;
        }
        logEvent("effects loading started");
        this.mSoundPool = new android.media.SoundPool.Builder().setMaxStreams(4).setAudioAttributes(new android.media.AudioAttributes.Builder().setUsage(13).setContentType(4).build()).build();
        this.mPlayerAvailableCb.accept(this.mSoundPool);
        loadSoundAssets();
        this.mSoundPoolLoader = new com.android.server.audio.SoundEffectsHelper.SoundPoolLoader();
        this.mSoundPoolLoader.addHandler(new com.android.server.audio.SoundEffectsHelper.OnEffectsLoadCompleteHandler() { // from class: com.android.server.audio.SoundEffectsHelper.1
            @Override // com.android.server.audio.SoundEffectsHelper.OnEffectsLoadCompleteHandler
            public void run(boolean z) {
                com.android.server.audio.SoundEffectsHelper.this.mSoundPoolLoader = null;
                if (!z) {
                    android.util.Log.w(com.android.server.audio.SoundEffectsHelper.TAG, "onLoadSoundEffects(), Error while loading samples");
                    com.android.server.audio.SoundEffectsHelper.this.onUnloadSoundEffects();
                }
            }
        });
        this.mSoundPoolLoader.addHandler(onEffectsLoadCompleteHandler);
        int i = 0;
        for (com.android.server.audio.SoundEffectsHelper.Resource resource : this.mResources) {
            java.lang.String resourceFilePath = getResourceFilePath(resource);
            int load = this.mSoundPool.load(resourceFilePath, 0);
            if (load > 0) {
                resource.mSampleId = load;
                resource.mLoaded = false;
                i++;
            } else {
                logEvent("effect " + resourceFilePath + " rejected by SoundPool");
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("SoundPool could not load file: ");
                sb.append(resourceFilePath);
                android.util.Log.w(TAG, sb.toString());
            }
        }
        if (i > 0) {
            sendMsg(3, 0, 0, null, 15000);
        } else {
            logEvent("effects loading completed, no effects to load");
            this.mSoundPoolLoader.onComplete(true);
        }
    }

    void onUnloadSoundEffects() {
        if (this.mSoundPool == null) {
            return;
        }
        if (this.mSoundPoolLoader != null) {
            this.mSoundPoolLoader.addHandler(new com.android.server.audio.SoundEffectsHelper.OnEffectsLoadCompleteHandler() { // from class: com.android.server.audio.SoundEffectsHelper.2
                @Override // com.android.server.audio.SoundEffectsHelper.OnEffectsLoadCompleteHandler
                public void run(boolean z) {
                    com.android.server.audio.SoundEffectsHelper.this.onUnloadSoundEffects();
                }
            });
        }
        logEvent("effects unloading started");
        for (com.android.server.audio.SoundEffectsHelper.Resource resource : this.mResources) {
            if (resource.mSampleId != 0) {
                this.mSoundPool.unload(resource.mSampleId);
                resource.unload();
            }
        }
        this.mSoundPool.release();
        this.mSoundPool = null;
        logEvent("effects unloading completed");
    }

    void onPlaySoundEffect(int i, int i2) {
        float f;
        if (i2 < 0) {
            f = (float) java.lang.Math.pow(10.0d, this.mSfxAttenuationDb / 20.0f);
        } else {
            f = i2 / 1000.0f;
        }
        com.android.server.audio.SoundEffectsHelper.Resource resource = this.mResources.get(this.mEffects[i]);
        if (this.mSoundPool != null && resource.mSampleId != 0 && resource.mLoaded) {
            this.mSoundPool.play(resource.mSampleId, f, f, 0, 0, 1.0f);
            return;
        }
        android.media.MediaPlayer mediaPlayer = new android.media.MediaPlayer();
        try {
            mediaPlayer.setDataSource(getResourceFilePath(resource));
            mediaPlayer.setAudioStreamType(1);
            mediaPlayer.prepare();
            mediaPlayer.setVolume(f);
            mediaPlayer.setOnCompletionListener(new android.media.MediaPlayer.OnCompletionListener() { // from class: com.android.server.audio.SoundEffectsHelper.3
                @Override // android.media.MediaPlayer.OnCompletionListener
                public void onCompletion(android.media.MediaPlayer mediaPlayer2) {
                    com.android.server.audio.SoundEffectsHelper.cleanupPlayer(mediaPlayer2);
                }
            });
            mediaPlayer.setOnErrorListener(new android.media.MediaPlayer.OnErrorListener() { // from class: com.android.server.audio.SoundEffectsHelper.4
                @Override // android.media.MediaPlayer.OnErrorListener
                public boolean onError(android.media.MediaPlayer mediaPlayer2, int i3, int i4) {
                    com.android.server.audio.SoundEffectsHelper.cleanupPlayer(mediaPlayer2);
                    return true;
                }
            });
            mediaPlayer.start();
        } catch (java.io.IOException e) {
            android.util.Log.w(TAG, "MediaPlayer IOException: " + e);
        } catch (java.lang.IllegalArgumentException e2) {
            android.util.Log.w(TAG, "MediaPlayer IllegalArgumentException: " + e2);
        } catch (java.lang.IllegalStateException e3) {
            android.util.Log.w(TAG, "MediaPlayer IllegalStateException: " + e3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void cleanupPlayer(android.media.MediaPlayer mediaPlayer) {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
                mediaPlayer.release();
            } catch (java.lang.IllegalStateException e) {
                android.util.Log.w(TAG, "MediaPlayer IllegalStateException: " + e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String getResourceFilePath(com.android.server.audio.SoundEffectsHelper.Resource resource) {
        java.lang.String str = android.os.Environment.getProductDirectory() + SOUND_EFFECTS_PATH + resource.mFileName;
        if (!new java.io.File(str).isFile()) {
            return android.os.Environment.getRootDirectory() + SOUND_EFFECTS_PATH + resource.mFileName;
        }
        return str;
    }

    private void loadSoundAssetDefaults() {
        int size = this.mResources.size();
        this.mResources.add(new com.android.server.audio.SoundEffectsHelper.Resource("Effect_Tick.ogg"));
        java.util.Arrays.fill(this.mEffects, size);
    }

    /* JADX WARN: Not initialized variable reg: 2, insn: 0x0071: MOVE (r1 I:??[OBJECT, ARRAY]) = (r2 I:??[OBJECT, ARRAY]), block:B:74:0x0071 */
    /* JADX WARN: Removed duplicated region for block: B:76:0x015e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void loadSoundAssets() {
        android.content.res.XmlResourceParser xmlResourceParser;
        org.xmlpull.v1.XmlPullParserException e;
        java.io.IOException e2;
        android.content.res.Resources.NotFoundException e3;
        android.content.res.XmlResourceParser xmlResourceParser2;
        if (this.mResources.isEmpty()) {
            loadSoundAssetDefaults();
            android.content.res.XmlResourceParser xmlResourceParser3 = null;
            try {
                try {
                    xmlResourceParser = this.mContext.getResources().getXml(android.R.xml.audio_assets);
                } catch (android.content.res.Resources.NotFoundException e4) {
                    xmlResourceParser = null;
                    e3 = e4;
                } catch (java.io.IOException e5) {
                    xmlResourceParser = null;
                    e2 = e5;
                } catch (org.xmlpull.v1.XmlPullParserException e6) {
                    xmlResourceParser = null;
                    e = e6;
                } catch (java.lang.Throwable th) {
                    th = th;
                    if (xmlResourceParser3 != null) {
                    }
                    throw th;
                }
                try {
                    com.android.internal.util.XmlUtils.beginDocument(xmlResourceParser, TAG_AUDIO_ASSETS);
                    java.lang.String attributeValue = xmlResourceParser.getAttributeValue(null, ATTR_VERSION);
                    java.util.HashMap hashMap = new java.util.HashMap();
                    if (ASSET_FILE_VERSION.equals(attributeValue)) {
                        while (true) {
                            com.android.internal.util.XmlUtils.nextElement(xmlResourceParser);
                            java.lang.String name = xmlResourceParser.getName();
                            if (name == null) {
                                break;
                            }
                            if (!name.equals(TAG_GROUP)) {
                                if (!name.equals(TAG_ASSET)) {
                                    break;
                                }
                                java.lang.String attributeValue2 = xmlResourceParser.getAttributeValue(null, ATTR_ASSET_ID);
                                java.lang.String attributeValue3 = xmlResourceParser.getAttributeValue(null, ATTR_ASSET_FILE);
                                try {
                                    int i = android.media.AudioManager.class.getField(attributeValue2).getInt(null);
                                    int intValue = hashMap.getOrDefault(java.lang.Integer.valueOf(i), 0).intValue() + 1;
                                    hashMap.put(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(intValue));
                                    if (intValue > 1) {
                                        android.util.Log.w(TAG, "Duplicate definition for sound ID: " + attributeValue2);
                                    }
                                    this.mEffects[i] = findOrAddResourceByFileName(attributeValue3);
                                } catch (java.lang.Exception e7) {
                                    android.util.Log.w(TAG, "Invalid sound ID: " + attributeValue2);
                                }
                            } else {
                                java.lang.String attributeValue4 = xmlResourceParser.getAttributeValue(null, "name");
                                if (!GROUP_TOUCH_SOUNDS.equals(attributeValue4)) {
                                    android.util.Log.w(TAG, "Unsupported group name: " + attributeValue4);
                                }
                            }
                        }
                        boolean allNavigationRepeatSoundsParsed = allNavigationRepeatSoundsParsed(hashMap);
                        boolean z = hashMap.getOrDefault(11, 0).intValue() > 0;
                        if (allNavigationRepeatSoundsParsed || z) {
                            android.media.AudioManager audioManager = (android.media.AudioManager) this.mContext.getSystemService(android.media.AudioManager.class);
                            if (audioManager != null && allNavigationRepeatSoundsParsed) {
                                audioManager.setNavigationRepeatSoundEffectsEnabled(true);
                            }
                            if (audioManager != null && z) {
                                audioManager.setHomeSoundEffectEnabled(true);
                            }
                        }
                    }
                } catch (android.content.res.Resources.NotFoundException e8) {
                    e3 = e8;
                    android.util.Log.w(TAG, "audio assets file not found", e3);
                    if (xmlResourceParser == null) {
                        return;
                    }
                    xmlResourceParser.close();
                } catch (java.io.IOException e9) {
                    e2 = e9;
                    android.util.Log.w(TAG, "I/O exception reading sound assets", e2);
                    if (xmlResourceParser != null) {
                        xmlResourceParser.close();
                    }
                    return;
                } catch (org.xmlpull.v1.XmlPullParserException e10) {
                    e = e10;
                    android.util.Log.w(TAG, "XML parser exception reading sound assets", e);
                    if (xmlResourceParser != null) {
                        xmlResourceParser.close();
                    }
                    return;
                }
                xmlResourceParser.close();
            } catch (java.lang.Throwable th2) {
                th = th2;
                xmlResourceParser3 = xmlResourceParser2;
                if (xmlResourceParser3 != null) {
                    xmlResourceParser3.close();
                }
                throw th;
            }
        }
    }

    private boolean allNavigationRepeatSoundsParsed(java.util.Map<java.lang.Integer, java.lang.Integer> map) {
        return ((map.getOrDefault(12, 0).intValue() + map.getOrDefault(13, 0).intValue()) + map.getOrDefault(14, 0).intValue()) + map.getOrDefault(15, 0).intValue() == 4;
    }

    private int findOrAddResourceByFileName(java.lang.String str) {
        for (int i = 0; i < this.mResources.size(); i++) {
            if (this.mResources.get(i).mFileName.equals(str)) {
                return i;
            }
        }
        int size = this.mResources.size();
        this.mResources.add(new com.android.server.audio.SoundEffectsHelper.Resource(str));
        return size;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.audio.SoundEffectsHelper.Resource findResourceBySampleId(int i) {
        for (com.android.server.audio.SoundEffectsHelper.Resource resource : this.mResources) {
            if (resource.mSampleId == i) {
                return resource;
            }
        }
        return null;
    }

    private class SfxWorker extends java.lang.Thread {
        SfxWorker() {
            super("AS.SfxWorker");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            android.os.Looper.prepare();
            synchronized (com.android.server.audio.SoundEffectsHelper.this) {
                com.android.server.audio.SoundEffectsHelper.this.mSfxHandler = new com.android.server.audio.SoundEffectsHelper.SfxHandler();
                com.android.server.audio.SoundEffectsHelper.this.notify();
            }
            android.os.Looper.loop();
        }
    }

    private class SfxHandler extends android.os.Handler {
        private SfxHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 0:
                    com.android.server.audio.SoundEffectsHelper.this.onLoadSoundEffects((com.android.server.audio.SoundEffectsHelper.OnEffectsLoadCompleteHandler) message.obj);
                    break;
                case 1:
                    com.android.server.audio.SoundEffectsHelper.this.onUnloadSoundEffects();
                    break;
                case 2:
                    final int i = message.arg1;
                    final int i2 = message.arg2;
                    com.android.server.audio.SoundEffectsHelper.this.onLoadSoundEffects(new com.android.server.audio.SoundEffectsHelper.OnEffectsLoadCompleteHandler() { // from class: com.android.server.audio.SoundEffectsHelper.SfxHandler.1
                        @Override // com.android.server.audio.SoundEffectsHelper.OnEffectsLoadCompleteHandler
                        public void run(boolean z) {
                            if (z) {
                                com.android.server.audio.SoundEffectsHelper.this.onPlaySoundEffect(i, i2);
                            }
                        }
                    });
                    break;
                case 3:
                    if (com.android.server.audio.SoundEffectsHelper.this.mSoundPoolLoader != null) {
                        com.android.server.audio.SoundEffectsHelper.this.mSoundPoolLoader.onTimeout();
                        break;
                    }
                    break;
            }
        }
    }

    private class SoundPoolLoader implements android.media.SoundPool.OnLoadCompleteListener {
        private java.util.List<com.android.server.audio.SoundEffectsHelper.OnEffectsLoadCompleteHandler> mLoadCompleteHandlers = new java.util.ArrayList();

        SoundPoolLoader() {
            com.android.server.audio.SoundEffectsHelper.this.mSoundPool.setOnLoadCompleteListener(this);
        }

        void addHandler(com.android.server.audio.SoundEffectsHelper.OnEffectsLoadCompleteHandler onEffectsLoadCompleteHandler) {
            if (onEffectsLoadCompleteHandler != null) {
                this.mLoadCompleteHandlers.add(onEffectsLoadCompleteHandler);
            }
        }

        @Override // android.media.SoundPool.OnLoadCompleteListener
        public void onLoadComplete(android.media.SoundPool soundPool, int i, int i2) {
            int i3 = 0;
            if (i2 == 0) {
                for (com.android.server.audio.SoundEffectsHelper.Resource resource : com.android.server.audio.SoundEffectsHelper.this.mResources) {
                    if (resource.mSampleId == i && !resource.mLoaded) {
                        com.android.server.audio.SoundEffectsHelper.this.logEvent("effect " + resource.mFileName + " loaded");
                        resource.mLoaded = true;
                    }
                    if (resource.mSampleId != 0 && !resource.mLoaded) {
                        i3++;
                    }
                }
                if (i3 == 0) {
                    onComplete(true);
                    return;
                }
                return;
            }
            com.android.server.audio.SoundEffectsHelper.Resource findResourceBySampleId = com.android.server.audio.SoundEffectsHelper.this.findResourceBySampleId(i);
            java.lang.String resourceFilePath = findResourceBySampleId != null ? com.android.server.audio.SoundEffectsHelper.this.getResourceFilePath(findResourceBySampleId) : "with unknown sample ID " + i;
            com.android.server.audio.SoundEffectsHelper.this.logEvent("effect " + resourceFilePath + " loading failed, status " + i2);
            android.util.Log.w(com.android.server.audio.SoundEffectsHelper.TAG, "onLoadSoundEffects(), Error " + i2 + " while loading sample " + resourceFilePath);
            onComplete(false);
        }

        void onTimeout() {
            onComplete(false);
        }

        void onComplete(boolean z) {
            if (com.android.server.audio.SoundEffectsHelper.this.mSoundPool != null) {
                com.android.server.audio.SoundEffectsHelper.this.mSoundPool.setOnLoadCompleteListener(null);
            }
            java.util.Iterator<com.android.server.audio.SoundEffectsHelper.OnEffectsLoadCompleteHandler> it = this.mLoadCompleteHandlers.iterator();
            while (it.hasNext()) {
                it.next().run(z);
            }
            com.android.server.audio.SoundEffectsHelper soundEffectsHelper = com.android.server.audio.SoundEffectsHelper.this;
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("effects loading ");
            sb.append(z ? "completed" : "failed");
            soundEffectsHelper.logEvent(sb.toString());
        }
    }
}
