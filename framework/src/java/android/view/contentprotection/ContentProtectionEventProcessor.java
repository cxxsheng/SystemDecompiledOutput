package android.view.contentprotection;

/* loaded from: classes4.dex */
public class ContentProtectionEventProcessor {
    private static final int RESET_LOGIN_TOTAL_EVENTS_TO_PROCESS = 150;
    private static final java.lang.String TAG = "ContentProtectionEventProcessor";
    private boolean mAnyGroupFound = false;
    private final android.view.contentcapture.IContentCaptureManager mContentCaptureManager;
    private final com.android.internal.util.RingBuffer<android.view.contentcapture.ContentCaptureEvent> mEventBuffer;
    private final java.util.List<android.view.contentprotection.ContentProtectionEventProcessor.SearchGroup> mGroupsAll;
    private final java.util.List<android.view.contentprotection.ContentProtectionEventProcessor.SearchGroup> mGroupsOptional;
    private final java.util.List<android.view.contentprotection.ContentProtectionEventProcessor.SearchGroup> mGroupsRequired;
    private final android.os.Handler mHandler;
    public java.time.Instant mLastFlushTime;
    private final android.content.ContentCaptureOptions.ContentProtectionOptions mOptions;
    private final java.lang.String mPackageName;
    private int mResetLoginRemainingEventsToProcess;
    private static final java.time.Duration MIN_DURATION_BETWEEN_FLUSHING = java.time.Duration.ofSeconds(3);
    private static final java.util.Set<java.lang.Integer> EVENT_TYPES_TO_STORE = java.util.Set.of(1, 2, 3);

    public ContentProtectionEventProcessor(com.android.internal.util.RingBuffer<android.view.contentcapture.ContentCaptureEvent> ringBuffer, android.os.Handler handler, android.view.contentcapture.IContentCaptureManager iContentCaptureManager, java.lang.String str, android.content.ContentCaptureOptions.ContentProtectionOptions contentProtectionOptions) {
        this.mEventBuffer = ringBuffer;
        this.mHandler = handler;
        this.mContentCaptureManager = iContentCaptureManager;
        this.mPackageName = str;
        this.mOptions = contentProtectionOptions;
        this.mGroupsRequired = contentProtectionOptions.requiredGroups.stream().map(new java.util.function.Function() { // from class: android.view.contentprotection.ContentProtectionEventProcessor$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return new android.view.contentprotection.ContentProtectionEventProcessor.SearchGroup((java.util.List) obj);
            }
        }).toList();
        this.mGroupsOptional = contentProtectionOptions.optionalGroups.stream().map(new java.util.function.Function() { // from class: android.view.contentprotection.ContentProtectionEventProcessor$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return new android.view.contentprotection.ContentProtectionEventProcessor.SearchGroup((java.util.List) obj);
            }
        }).toList();
        this.mGroupsAll = java.util.stream.Stream.of((java.lang.Object[]) new java.util.List[]{this.mGroupsRequired, this.mGroupsOptional}).flatMap(new java.util.function.Function() { // from class: android.view.contentprotection.ContentProtectionEventProcessor$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return ((java.util.List) obj).stream();
            }
        }).toList();
    }

    public void processEvent(android.view.contentcapture.ContentCaptureEvent contentCaptureEvent) {
        if (EVENT_TYPES_TO_STORE.contains(java.lang.Integer.valueOf(contentCaptureEvent.getType()))) {
            storeEvent(contentCaptureEvent);
        }
        if (contentCaptureEvent.getType() == 1) {
            processViewAppearedEvent(contentCaptureEvent);
        }
    }

    private void storeEvent(android.view.contentcapture.ContentCaptureEvent contentCaptureEvent) {
        android.view.contentcapture.ViewNode viewNode = contentCaptureEvent.getViewNode() != null ? contentCaptureEvent.getViewNode() : new android.view.contentcapture.ViewNode();
        viewNode.setTextIdEntry(this.mPackageName);
        contentCaptureEvent.setViewNode(viewNode);
        this.mEventBuffer.append(contentCaptureEvent);
    }

    private void processViewAppearedEvent(android.view.contentcapture.ContentCaptureEvent contentCaptureEvent) {
        android.view.contentcapture.ViewNode viewNode = contentCaptureEvent.getViewNode();
        final java.lang.String eventTextLower = android.view.contentprotection.ContentProtectionUtils.getEventTextLower(contentCaptureEvent);
        final java.lang.String viewNodeTextLower = android.view.contentprotection.ContentProtectionUtils.getViewNodeTextLower(viewNode);
        final java.lang.String hintTextLower = android.view.contentprotection.ContentProtectionUtils.getHintTextLower(viewNode);
        this.mGroupsAll.stream().filter(new java.util.function.Predicate() { // from class: android.view.contentprotection.ContentProtectionEventProcessor$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return android.view.contentprotection.ContentProtectionEventProcessor.lambda$processViewAppearedEvent$0((android.view.contentprotection.ContentProtectionEventProcessor.SearchGroup) obj);
            }
        }).filter(new java.util.function.Predicate() { // from class: android.view.contentprotection.ContentProtectionEventProcessor$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return android.view.contentprotection.ContentProtectionEventProcessor.lambda$processViewAppearedEvent$1(eventTextLower, viewNodeTextLower, hintTextLower, (android.view.contentprotection.ContentProtectionEventProcessor.SearchGroup) obj);
            }
        }).findFirst().ifPresent(new java.util.function.Consumer() { // from class: android.view.contentprotection.ContentProtectionEventProcessor$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.view.contentprotection.ContentProtectionEventProcessor.this.lambda$processViewAppearedEvent$2((android.view.contentprotection.ContentProtectionEventProcessor.SearchGroup) obj);
            }
        });
        if (this.mGroupsRequired.stream().allMatch(new java.util.function.Predicate() { // from class: android.view.contentprotection.ContentProtectionEventProcessor$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean z;
                z = ((android.view.contentprotection.ContentProtectionEventProcessor.SearchGroup) obj).mFound;
                return z;
            }
        }) && this.mGroupsOptional.stream().filter(new java.util.function.Predicate() { // from class: android.view.contentprotection.ContentProtectionEventProcessor$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean z;
                z = ((android.view.contentprotection.ContentProtectionEventProcessor.SearchGroup) obj).mFound;
                return z;
            }
        }).count() >= ((long) this.mOptions.optionalGroupsThreshold)) {
            loginDetected();
        } else {
            maybeResetLoginFlags();
        }
    }

    static /* synthetic */ boolean lambda$processViewAppearedEvent$0(android.view.contentprotection.ContentProtectionEventProcessor.SearchGroup searchGroup) {
        return !searchGroup.mFound;
    }

    static /* synthetic */ boolean lambda$processViewAppearedEvent$1(java.lang.String str, java.lang.String str2, java.lang.String str3, android.view.contentprotection.ContentProtectionEventProcessor.SearchGroup searchGroup) {
        return searchGroup.matches(str) || searchGroup.matches(str2) || searchGroup.matches(str3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processViewAppearedEvent$2(android.view.contentprotection.ContentProtectionEventProcessor.SearchGroup searchGroup) {
        searchGroup.mFound = true;
        this.mAnyGroupFound = true;
    }

    private void loginDetected() {
        if (this.mLastFlushTime == null || java.time.Instant.now().isAfter(this.mLastFlushTime.plus((java.time.temporal.TemporalAmount) MIN_DURATION_BETWEEN_FLUSHING))) {
            flush();
        }
        resetLoginFlags();
    }

    private void resetLoginFlags() {
        this.mGroupsAll.forEach(new java.util.function.Consumer() { // from class: android.view.contentprotection.ContentProtectionEventProcessor$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((android.view.contentprotection.ContentProtectionEventProcessor.SearchGroup) obj).mFound = false;
            }
        });
        this.mAnyGroupFound = false;
    }

    private void maybeResetLoginFlags() {
        if (this.mAnyGroupFound) {
            if (this.mResetLoginRemainingEventsToProcess <= 0) {
                this.mResetLoginRemainingEventsToProcess = 150;
                return;
            }
            this.mResetLoginRemainingEventsToProcess--;
            if (this.mResetLoginRemainingEventsToProcess <= 0) {
                resetLoginFlags();
            }
        }
    }

    private void flush() {
        this.mLastFlushTime = java.time.Instant.now();
        final android.content.pm.ParceledListSlice<android.view.contentcapture.ContentCaptureEvent> clearEvents = clearEvents();
        this.mHandler.post(new java.lang.Runnable() { // from class: android.view.contentprotection.ContentProtectionEventProcessor$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                android.view.contentprotection.ContentProtectionEventProcessor.this.lambda$flush$6(clearEvents);
            }
        });
    }

    private android.content.pm.ParceledListSlice<android.view.contentcapture.ContentCaptureEvent> clearEvents() {
        java.util.List asList = java.util.Arrays.asList(this.mEventBuffer.toArray());
        this.mEventBuffer.clear();
        return new android.content.pm.ParceledListSlice<>(asList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handlerOnLoginDetected, reason: merged with bridge method [inline-methods] */
    public void lambda$flush$6(android.content.pm.ParceledListSlice<android.view.contentcapture.ContentCaptureEvent> parceledListSlice) {
        try {
            this.mContentCaptureManager.onLoginDetected(parceledListSlice);
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Failed to flush events for: " + this.mPackageName, e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class SearchGroup {
        public boolean mFound = false;
        private final java.util.List<java.lang.String> mSearchStrings;

        SearchGroup(java.util.List<java.lang.String> list) {
            this.mSearchStrings = list;
        }

        public boolean matches(final java.lang.String str) {
            if (str == null) {
                return false;
            }
            java.util.stream.Stream<java.lang.String> stream = this.mSearchStrings.stream();
            java.util.Objects.requireNonNull(str);
            return stream.anyMatch(new java.util.function.Predicate() { // from class: android.view.contentprotection.ContentProtectionEventProcessor$SearchGroup$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    return str.contains((java.lang.String) obj);
                }
            });
        }
    }
}
