package com.android.server.am;

/* loaded from: classes.dex */
final class AppBindServiceEventsTracker extends com.android.server.am.BaseAppStateTimeSlotEventsTracker<com.android.server.am.AppBindServiceEventsTracker.AppBindServiceEventsPolicy, com.android.server.am.BaseAppStateTimeSlotEventsTracker.SimpleAppStateTimeslotEvents> implements android.app.ActivityManagerInternal.BindServiceEventListener {
    static final boolean DEBUG_APP_STATE_BIND_SERVICE_EVENT_TRACKER = false;
    static final java.lang.String TAG = "ActivityManager";

    AppBindServiceEventsTracker(android.content.Context context, com.android.server.am.AppRestrictionController appRestrictionController) {
        this(context, appRestrictionController, null, null);
    }

    AppBindServiceEventsTracker(android.content.Context context, com.android.server.am.AppRestrictionController appRestrictionController, java.lang.reflect.Constructor<? extends com.android.server.am.BaseAppStateTracker.Injector<com.android.server.am.AppBindServiceEventsTracker.AppBindServiceEventsPolicy>> constructor, java.lang.Object obj) {
        super(context, appRestrictionController, constructor, obj);
        this.mInjector.setPolicy(new com.android.server.am.AppBindServiceEventsTracker.AppBindServiceEventsPolicy(this.mInjector, this));
    }

    public void onBindingService(java.lang.String str, int i) {
        if (((com.android.server.am.AppBindServiceEventsTracker.AppBindServiceEventsPolicy) this.mInjector.getPolicy()).isEnabled()) {
            onNewEvent(str, i);
        }
    }

    @Override // com.android.server.am.BaseAppStateTracker
    @com.android.server.am.AppRestrictionController.TrackerType
    int getType() {
        return 7;
    }

    @Override // com.android.server.am.BaseAppStateTracker
    void onSystemReady() {
        super.onSystemReady();
        this.mInjector.getActivityManagerInternal().addBindServiceEventListener(this);
    }

    @Override // com.android.server.am.BaseAppStateEvents.Factory
    public com.android.server.am.BaseAppStateTimeSlotEventsTracker.SimpleAppStateTimeslotEvents createAppStateEvents(int i, java.lang.String str) {
        return new com.android.server.am.BaseAppStateTimeSlotEventsTracker.SimpleAppStateTimeslotEvents(i, str, ((com.android.server.am.AppBindServiceEventsTracker.AppBindServiceEventsPolicy) this.mInjector.getPolicy()).getTimeSlotSize(), TAG, (com.android.server.am.BaseAppStateEvents.MaxTrackingDurationConfig) this.mInjector.getPolicy());
    }

    @Override // com.android.server.am.BaseAppStateEvents.Factory
    public com.android.server.am.BaseAppStateTimeSlotEventsTracker.SimpleAppStateTimeslotEvents createAppStateEvents(com.android.server.am.BaseAppStateTimeSlotEventsTracker.SimpleAppStateTimeslotEvents simpleAppStateTimeslotEvents) {
        return new com.android.server.am.BaseAppStateTimeSlotEventsTracker.SimpleAppStateTimeslotEvents(simpleAppStateTimeslotEvents);
    }

    @Override // com.android.server.am.BaseAppStateTracker
    byte[] getTrackerInfoForStatsd(int i) {
        int totalEventsLocked = getTotalEventsLocked(i, android.os.SystemClock.elapsedRealtime());
        if (totalEventsLocked == 0) {
            return null;
        }
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
        protoOutputStream.write(1120986464257L, totalEventsLocked);
        protoOutputStream.flush();
        return protoOutputStream.getBytes();
    }

    @Override // com.android.server.am.BaseAppStateEventsTracker, com.android.server.am.BaseAppStateTracker
    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        printWriter.println("APP BIND SERVICE EVENT TRACKER:");
        super.dump(printWriter, "  " + str);
    }

    static final class AppBindServiceEventsPolicy extends com.android.server.am.BaseAppStateTimeSlotEventsTracker.BaseAppStateTimeSlotEventsPolicy<com.android.server.am.AppBindServiceEventsTracker> {
        static final boolean DEFAULT_BG_BIND_SVC_MONITOR_ENABLED = true;
        static final long DEFAULT_BG_BIND_SVC_WINDOW = 86400000;
        static final int DEFAULT_BG_EX_BIND_SVC_THRESHOLD = 10000;
        static final java.lang.String KEY_BG_BIND_SVC_MONITOR_ENABLED = "bg_bind_svc_monitor_enabled";
        static final java.lang.String KEY_BG_BIND_SVC_WINDOW = "bg_bind_svc_window";
        static final java.lang.String KEY_BG_EX_BIND_SVC_THRESHOLD = "bg_ex_bind_svc_threshold";

        AppBindServiceEventsPolicy(@android.annotation.NonNull com.android.server.am.BaseAppStateTracker.Injector injector, @android.annotation.NonNull com.android.server.am.AppBindServiceEventsTracker appBindServiceEventsTracker) {
            super(injector, appBindServiceEventsTracker, KEY_BG_BIND_SVC_MONITOR_ENABLED, true, KEY_BG_BIND_SVC_WINDOW, 86400000L, KEY_BG_EX_BIND_SVC_THRESHOLD, 10000);
        }

        @Override // com.android.server.am.BaseAppStateTimeSlotEventsTracker.BaseAppStateTimeSlotEventsPolicy
        java.lang.String getEventName() {
            return "bindservice";
        }

        @Override // com.android.server.am.BaseAppStateTimeSlotEventsTracker.BaseAppStateTimeSlotEventsPolicy, com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy, com.android.server.am.BaseAppStatePolicy
        void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            printWriter.print(str);
            printWriter.println("APP BIND SERVICE EVENT TRACKER POLICY SETTINGS:");
            super.dump(printWriter, "  " + str);
        }
    }
}
