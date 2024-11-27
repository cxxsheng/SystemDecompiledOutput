package com.android.server.soundtrigger_middleware;

/* loaded from: classes2.dex */
public class SoundTriggerMiddlewareService extends android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService.Stub {
    private static final java.lang.String TAG = "SoundTriggerMiddlewareService";

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.NonNull
    private final com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal mDelegate;

    @android.annotation.NonNull
    private final com.android.server.soundtrigger_middleware.SoundTriggerInjection mInjection;

    private SoundTriggerMiddlewareService(@android.annotation.NonNull com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal iSoundTriggerMiddlewareInternal, @android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.soundtrigger_middleware.SoundTriggerInjection soundTriggerInjection) {
        java.util.Objects.requireNonNull(iSoundTriggerMiddlewareInternal);
        this.mDelegate = iSoundTriggerMiddlewareInternal;
        this.mContext = context;
        this.mInjection = soundTriggerInjection;
    }

    public android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor[] listModulesAsOriginator(android.media.permission.Identity identity) {
        android.media.permission.SafeCloseable establishIdentityDirect = establishIdentityDirect(identity);
        try {
            android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor[] listModules = this.mDelegate.listModules();
            if (establishIdentityDirect != null) {
                establishIdentityDirect.close();
            }
            return listModules;
        } catch (java.lang.Throwable th) {
            if (establishIdentityDirect != null) {
                try {
                    establishIdentityDirect.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor[] listModulesAsMiddleman(android.media.permission.Identity identity, android.media.permission.Identity identity2) {
        android.media.permission.SafeCloseable establishIdentityIndirect = establishIdentityIndirect(identity, identity2);
        try {
            android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor[] listModules = this.mDelegate.listModules();
            if (establishIdentityIndirect != null) {
                establishIdentityIndirect.close();
            }
            return listModules;
        } catch (java.lang.Throwable th) {
            if (establishIdentityIndirect != null) {
                try {
                    establishIdentityIndirect.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public android.media.soundtrigger_middleware.ISoundTriggerModule attachAsOriginator(int i, android.media.permission.Identity identity, android.media.soundtrigger_middleware.ISoundTriggerCallback iSoundTriggerCallback) {
        java.util.Objects.requireNonNull(identity);
        android.media.permission.SafeCloseable establishIdentityDirect = establishIdentityDirect(identity);
        try {
            com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareService.ModuleService moduleService = new com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareService.ModuleService(this.mDelegate.attach(i, iSoundTriggerCallback, false));
            if (establishIdentityDirect != null) {
                establishIdentityDirect.close();
            }
            return moduleService;
        } catch (java.lang.Throwable th) {
            if (establishIdentityDirect != null) {
                try {
                    establishIdentityDirect.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public android.media.soundtrigger_middleware.ISoundTriggerModule attachAsMiddleman(int i, android.media.permission.Identity identity, android.media.permission.Identity identity2, android.media.soundtrigger_middleware.ISoundTriggerCallback iSoundTriggerCallback, boolean z) {
        java.util.Objects.requireNonNull(identity);
        java.util.Objects.requireNonNull(identity2);
        android.media.permission.SafeCloseable establishIdentityIndirect = establishIdentityIndirect(identity, identity2);
        try {
            com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareService.ModuleService moduleService = new com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareService.ModuleService(this.mDelegate.attach(i, iSoundTriggerCallback, z));
            if (establishIdentityIndirect != null) {
                establishIdentityIndirect.close();
            }
            return moduleService;
        } catch (java.lang.Throwable th) {
            if (establishIdentityIndirect != null) {
                try {
                    establishIdentityIndirect.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @android.annotation.RequiresPermission("android.permission.MANAGE_SOUND_TRIGGER")
    public void attachFakeHalInjection(@android.annotation.NonNull android.media.soundtrigger_middleware.ISoundTriggerInjection iSoundTriggerInjection) {
        android.content.PermissionChecker.checkCallingOrSelfPermissionForPreflight(this.mContext, "android.permission.MANAGE_SOUND_TRIGGER");
        android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
        try {
            com.android.server.soundtrigger_middleware.SoundTriggerInjection soundTriggerInjection = this.mInjection;
            java.util.Objects.requireNonNull(iSoundTriggerInjection);
            soundTriggerInjection.registerClient(iSoundTriggerInjection);
            if (create != null) {
                create.close();
            }
        } catch (java.lang.Throwable th) {
            if (create != null) {
                try {
                    create.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (this.mDelegate instanceof com.android.server.soundtrigger_middleware.Dumpable) {
            ((com.android.server.soundtrigger_middleware.Dumpable) this.mDelegate).dump(printWriter);
        }
    }

    @android.annotation.NonNull
    private android.media.permission.SafeCloseable establishIdentityIndirect(android.media.permission.Identity identity, android.media.permission.Identity identity2) {
        return android.media.permission.PermissionUtil.establishIdentityIndirect(this.mContext, "android.permission.SOUNDTRIGGER_DELEGATE_IDENTITY", identity, identity2);
    }

    @android.annotation.NonNull
    private android.media.permission.SafeCloseable establishIdentityDirect(android.media.permission.Identity identity) {
        return android.media.permission.PermissionUtil.establishIdentityDirect(identity);
    }

    private static final class ModuleService extends android.media.soundtrigger_middleware.ISoundTriggerModule.Stub {
        private final android.media.soundtrigger_middleware.ISoundTriggerModule mDelegate;

        private ModuleService(android.media.soundtrigger_middleware.ISoundTriggerModule iSoundTriggerModule) {
            this.mDelegate = iSoundTriggerModule;
        }

        public int loadModel(android.media.soundtrigger.SoundModel soundModel) throws android.os.RemoteException {
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                int loadModel = this.mDelegate.loadModel(soundModel);
                if (create != null) {
                    create.close();
                }
                return loadModel;
            } catch (java.lang.Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public int loadPhraseModel(android.media.soundtrigger.PhraseSoundModel phraseSoundModel) throws android.os.RemoteException {
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                int loadPhraseModel = this.mDelegate.loadPhraseModel(phraseSoundModel);
                if (create != null) {
                    create.close();
                }
                return loadPhraseModel;
            } catch (java.lang.Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public void unloadModel(int i) throws android.os.RemoteException {
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                this.mDelegate.unloadModel(i);
                if (create != null) {
                    create.close();
                }
            } catch (java.lang.Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public android.os.IBinder startRecognition(int i, android.media.soundtrigger.RecognitionConfig recognitionConfig) throws android.os.RemoteException {
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                android.os.IBinder startRecognition = this.mDelegate.startRecognition(i, recognitionConfig);
                if (create != null) {
                    create.close();
                }
                return startRecognition;
            } catch (java.lang.Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public void stopRecognition(int i) throws android.os.RemoteException {
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                this.mDelegate.stopRecognition(i);
                if (create != null) {
                    create.close();
                }
            } catch (java.lang.Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public void forceRecognitionEvent(int i) throws android.os.RemoteException {
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                this.mDelegate.forceRecognitionEvent(i);
                if (create != null) {
                    create.close();
                }
            } catch (java.lang.Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public void setModelParameter(int i, int i2, int i3) throws android.os.RemoteException {
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                this.mDelegate.setModelParameter(i, i2, i3);
                if (create != null) {
                    create.close();
                }
            } catch (java.lang.Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public int getModelParameter(int i, int i2) throws android.os.RemoteException {
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                int modelParameter = this.mDelegate.getModelParameter(i, i2);
                if (create != null) {
                    create.close();
                }
                return modelParameter;
            } catch (java.lang.Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public android.media.soundtrigger.ModelParameterRange queryModelParameterSupport(int i, int i2) throws android.os.RemoteException {
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                android.media.soundtrigger.ModelParameterRange queryModelParameterSupport = this.mDelegate.queryModelParameterSupport(i, i2);
                if (create != null) {
                    create.close();
                }
                return queryModelParameterSupport;
            } catch (java.lang.Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public void detach() throws android.os.RemoteException {
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                this.mDelegate.detach();
                if (create != null) {
                    create.close();
                }
            } catch (java.lang.Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
    }

    public static final class Lifecycle extends com.android.server.SystemService {
        public Lifecycle(android.content.Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            com.android.server.soundtrigger_middleware.SoundTriggerInjection soundTriggerInjection = new com.android.server.soundtrigger_middleware.SoundTriggerInjection();
            publishBinderService("soundtrigger_middleware", new com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareService(new com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging(getContext(), new com.android.server.soundtrigger_middleware.SoundTriggerMiddlewarePermission(new com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation(new com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareImpl(new com.android.server.soundtrigger_middleware.HalFactory[]{new com.android.server.soundtrigger_middleware.DefaultHalFactory(), new com.android.server.soundtrigger_middleware.FakeHalFactory(soundTriggerInjection)}, new com.android.server.soundtrigger_middleware.AudioSessionProviderImpl())), getContext())), getContext(), soundTriggerInjection));
        }
    }
}
