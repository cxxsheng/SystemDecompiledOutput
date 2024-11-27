package com.android.ims.internal.uce;

/* loaded from: classes4.dex */
public abstract class UceServiceBase {
    private com.android.ims.internal.uce.UceServiceBase.UceServiceBinder mBinder;

    private final class UceServiceBinder extends com.android.ims.internal.uce.uceservice.IUceService.Stub {
        private UceServiceBinder() {
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public boolean startService(com.android.ims.internal.uce.uceservice.IUceListener iUceListener) {
            return com.android.ims.internal.uce.UceServiceBase.this.onServiceStart(iUceListener);
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public boolean stopService() {
            return com.android.ims.internal.uce.UceServiceBase.this.onStopService();
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public boolean isServiceStarted() {
            return com.android.ims.internal.uce.UceServiceBase.this.onIsServiceStarted();
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public int createOptionsService(com.android.ims.internal.uce.options.IOptionsListener iOptionsListener, com.android.ims.internal.uce.common.UceLong uceLong) {
            return com.android.ims.internal.uce.UceServiceBase.this.onCreateOptionsService(iOptionsListener, uceLong);
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public int createOptionsServiceForSubscription(com.android.ims.internal.uce.options.IOptionsListener iOptionsListener, com.android.ims.internal.uce.common.UceLong uceLong, java.lang.String str) {
            return com.android.ims.internal.uce.UceServiceBase.this.onCreateOptionsService(iOptionsListener, uceLong, str);
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public void destroyOptionsService(int i) {
            com.android.ims.internal.uce.UceServiceBase.this.onDestroyOptionsService(i);
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public int createPresenceService(com.android.ims.internal.uce.presence.IPresenceListener iPresenceListener, com.android.ims.internal.uce.common.UceLong uceLong) {
            return com.android.ims.internal.uce.UceServiceBase.this.onCreatePresService(iPresenceListener, uceLong);
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public int createPresenceServiceForSubscription(com.android.ims.internal.uce.presence.IPresenceListener iPresenceListener, com.android.ims.internal.uce.common.UceLong uceLong, java.lang.String str) {
            return com.android.ims.internal.uce.UceServiceBase.this.onCreatePresService(iPresenceListener, uceLong, str);
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public void destroyPresenceService(int i) {
            com.android.ims.internal.uce.UceServiceBase.this.onDestroyPresService(i);
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public boolean getServiceStatus() {
            return com.android.ims.internal.uce.UceServiceBase.this.onGetServiceStatus();
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public com.android.ims.internal.uce.presence.IPresenceService getPresenceService() {
            return com.android.ims.internal.uce.UceServiceBase.this.onGetPresenceService();
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public com.android.ims.internal.uce.presence.IPresenceService getPresenceServiceForSubscription(java.lang.String str) {
            return com.android.ims.internal.uce.UceServiceBase.this.onGetPresenceService(str);
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public com.android.ims.internal.uce.options.IOptionsService getOptionsService() {
            return com.android.ims.internal.uce.UceServiceBase.this.onGetOptionsService();
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public com.android.ims.internal.uce.options.IOptionsService getOptionsServiceForSubscription(java.lang.String str) {
            return com.android.ims.internal.uce.UceServiceBase.this.onGetOptionsService(str);
        }
    }

    public com.android.ims.internal.uce.UceServiceBase.UceServiceBinder getBinder() {
        if (this.mBinder == null) {
            this.mBinder = new com.android.ims.internal.uce.UceServiceBase.UceServiceBinder();
        }
        return this.mBinder;
    }

    protected boolean onServiceStart(com.android.ims.internal.uce.uceservice.IUceListener iUceListener) {
        return false;
    }

    protected boolean onStopService() {
        return false;
    }

    protected boolean onIsServiceStarted() {
        return false;
    }

    protected int onCreateOptionsService(com.android.ims.internal.uce.options.IOptionsListener iOptionsListener, com.android.ims.internal.uce.common.UceLong uceLong) {
        return 0;
    }

    protected int onCreateOptionsService(com.android.ims.internal.uce.options.IOptionsListener iOptionsListener, com.android.ims.internal.uce.common.UceLong uceLong, java.lang.String str) {
        return 0;
    }

    protected void onDestroyOptionsService(int i) {
    }

    protected int onCreatePresService(com.android.ims.internal.uce.presence.IPresenceListener iPresenceListener, com.android.ims.internal.uce.common.UceLong uceLong) {
        return 0;
    }

    protected int onCreatePresService(com.android.ims.internal.uce.presence.IPresenceListener iPresenceListener, com.android.ims.internal.uce.common.UceLong uceLong, java.lang.String str) {
        return 0;
    }

    protected void onDestroyPresService(int i) {
    }

    protected boolean onGetServiceStatus() {
        return false;
    }

    protected com.android.ims.internal.uce.presence.IPresenceService onGetPresenceService() {
        return null;
    }

    protected com.android.ims.internal.uce.presence.IPresenceService onGetPresenceService(java.lang.String str) {
        return null;
    }

    protected com.android.ims.internal.uce.options.IOptionsService onGetOptionsService() {
        return null;
    }

    protected com.android.ims.internal.uce.options.IOptionsService onGetOptionsService(java.lang.String str) {
        return null;
    }
}
