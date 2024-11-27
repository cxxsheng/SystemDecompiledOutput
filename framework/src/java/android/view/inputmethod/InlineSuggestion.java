package android.view.inputmethod;

/* loaded from: classes4.dex */
public final class InlineSuggestion implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.inputmethod.InlineSuggestion> CREATOR;
    private static final java.lang.String TAG = "InlineSuggestion";
    static com.android.internal.util.Parcelling<android.view.inputmethod.InlineSuggestion.InlineContentCallbackImpl> sParcellingForInlineContentCallback;
    static com.android.internal.util.Parcelling<com.android.internal.view.inline.InlineTooltipUi> sParcellingForInlineTooltipUi;
    private final com.android.internal.view.inline.IInlineContentProvider mContentProvider;
    private final android.view.inputmethod.InlineSuggestionInfo mInfo;
    private android.view.inputmethod.InlineSuggestion.InlineContentCallbackImpl mInlineContentCallback;
    private com.android.internal.view.inline.InlineTooltipUi mInlineTooltipUi;

    public static android.view.inputmethod.InlineSuggestion newInlineSuggestion(android.view.inputmethod.InlineSuggestionInfo inlineSuggestionInfo) {
        return new android.view.inputmethod.InlineSuggestion(inlineSuggestionInfo, null, null, null);
    }

    public InlineSuggestion(android.view.inputmethod.InlineSuggestionInfo inlineSuggestionInfo, com.android.internal.view.inline.IInlineContentProvider iInlineContentProvider) {
        this(inlineSuggestionInfo, iInlineContentProvider, null, null);
    }

    public void inflate(android.content.Context context, android.util.Size size, java.util.concurrent.Executor executor, final java.util.function.Consumer<android.widget.inline.InlineContentView> consumer) {
        android.util.Size minSize = this.mInfo.getInlinePresentationSpec().getMinSize();
        android.util.Size maxSize = this.mInfo.getInlinePresentationSpec().getMaxSize();
        if (!isValid(size.getWidth(), minSize.getWidth(), maxSize.getWidth()) || !isValid(size.getHeight(), minSize.getHeight(), maxSize.getHeight())) {
            throw new java.lang.IllegalArgumentException("size is neither between min:" + minSize + " and max:" + maxSize + ", nor wrap_content");
        }
        android.view.inputmethod.InlineSuggestion tooltip = this.mInfo.getTooltip();
        if (tooltip != null) {
            if (this.mInlineTooltipUi == null) {
                this.mInlineTooltipUi = new com.android.internal.view.inline.InlineTooltipUi(context);
            }
        } else {
            this.mInlineTooltipUi = null;
        }
        this.mInlineContentCallback = getInlineContentCallback(context, executor, consumer, this.mInlineTooltipUi);
        if (this.mContentProvider == null) {
            executor.execute(new java.lang.Runnable() { // from class: android.view.inputmethod.InlineSuggestion$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(null);
                }
            });
            this.mInlineTooltipUi = null;
            return;
        }
        try {
            this.mContentProvider.provideContent(size.getWidth(), size.getHeight(), new android.view.inputmethod.InlineSuggestion.InlineContentCallbackWrapper(this.mInlineContentCallback));
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Error creating suggestion content surface: " + e);
            executor.execute(new java.lang.Runnable() { // from class: android.view.inputmethod.InlineSuggestion$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(null);
                }
            });
        }
        if (tooltip == null) {
            return;
        }
        this.mInfo.getTooltip().inflate(context, new android.util.Size(-2, -2), executor, new java.util.function.Consumer() { // from class: android.view.inputmethod.InlineSuggestion$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.view.inputmethod.InlineSuggestion.this.lambda$inflate$3((android.widget.inline.InlineContentView) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$inflate$2(android.widget.inline.InlineContentView inlineContentView) {
        this.mInlineTooltipUi.setTooltipView(inlineContentView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$inflate$3(final android.widget.inline.InlineContentView inlineContentView) {
        android.os.Handler.getMain().post(new java.lang.Runnable() { // from class: android.view.inputmethod.InlineSuggestion$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                android.view.inputmethod.InlineSuggestion.this.lambda$inflate$2(inlineContentView);
            }
        });
    }

    private static boolean isValid(int i, int i2, int i3) {
        if (i == -2) {
            return true;
        }
        return i >= i2 && i <= i3;
    }

    private synchronized android.view.inputmethod.InlineSuggestion.InlineContentCallbackImpl getInlineContentCallback(android.content.Context context, java.util.concurrent.Executor executor, java.util.function.Consumer<android.widget.inline.InlineContentView> consumer, com.android.internal.view.inline.InlineTooltipUi inlineTooltipUi) {
        if (this.mInlineContentCallback != null) {
            throw new java.lang.IllegalStateException("Already called #inflate()");
        }
        return new android.view.inputmethod.InlineSuggestion.InlineContentCallbackImpl(context, this.mContentProvider, executor, consumer, inlineTooltipUi);
    }

    private static final class InlineContentCallbackWrapper extends com.android.internal.view.inline.IInlineContentCallback.Stub {
        private final java.lang.ref.WeakReference<android.view.inputmethod.InlineSuggestion.InlineContentCallbackImpl> mCallbackImpl;

        InlineContentCallbackWrapper(android.view.inputmethod.InlineSuggestion.InlineContentCallbackImpl inlineContentCallbackImpl) {
            this.mCallbackImpl = new java.lang.ref.WeakReference<>(inlineContentCallbackImpl);
        }

        @Override // com.android.internal.view.inline.IInlineContentCallback
        public void onContent(android.view.SurfaceControlViewHost.SurfacePackage surfacePackage, int i, int i2) {
            android.view.inputmethod.InlineSuggestion.InlineContentCallbackImpl inlineContentCallbackImpl = this.mCallbackImpl.get();
            if (inlineContentCallbackImpl != null) {
                inlineContentCallbackImpl.onContent(surfacePackage, i, i2);
            }
        }

        @Override // com.android.internal.view.inline.IInlineContentCallback
        public void onClick() {
            android.view.inputmethod.InlineSuggestion.InlineContentCallbackImpl inlineContentCallbackImpl = this.mCallbackImpl.get();
            if (inlineContentCallbackImpl != null) {
                inlineContentCallbackImpl.onClick();
            }
        }

        @Override // com.android.internal.view.inline.IInlineContentCallback
        public void onLongClick() {
            android.view.inputmethod.InlineSuggestion.InlineContentCallbackImpl inlineContentCallbackImpl = this.mCallbackImpl.get();
            if (inlineContentCallbackImpl != null) {
                inlineContentCallbackImpl.onLongClick();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class InlineContentCallbackImpl {
        private final java.util.function.Consumer<android.widget.inline.InlineContentView> mCallback;
        private final java.util.concurrent.Executor mCallbackExecutor;
        private final android.content.Context mContext;
        private final com.android.internal.view.inline.IInlineContentProvider mInlineContentProvider;
        private com.android.internal.view.inline.InlineTooltipUi mInlineTooltipUi;
        private android.view.SurfaceControlViewHost.SurfacePackage mSurfacePackage;
        private java.util.function.Consumer<android.view.SurfaceControlViewHost.SurfacePackage> mSurfacePackageConsumer;
        private android.widget.inline.InlineContentView mView;
        private final android.os.Handler mMainHandler = new android.os.Handler(android.os.Looper.getMainLooper());
        private boolean mFirstContentReceived = false;

        InlineContentCallbackImpl(android.content.Context context, com.android.internal.view.inline.IInlineContentProvider iInlineContentProvider, java.util.concurrent.Executor executor, java.util.function.Consumer<android.widget.inline.InlineContentView> consumer, com.android.internal.view.inline.InlineTooltipUi inlineTooltipUi) {
            this.mContext = context;
            this.mInlineContentProvider = iInlineContentProvider;
            this.mCallbackExecutor = executor;
            this.mCallback = consumer;
            this.mInlineTooltipUi = inlineTooltipUi;
        }

        public void onContent(final android.view.SurfaceControlViewHost.SurfacePackage surfacePackage, final int i, final int i2) {
            this.mMainHandler.post(new java.lang.Runnable() { // from class: android.view.inputmethod.InlineSuggestion$InlineContentCallbackImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.inputmethod.InlineSuggestion.InlineContentCallbackImpl.this.lambda$onContent$0(surfacePackage, i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: handleOnContent, reason: merged with bridge method [inline-methods] */
        public void lambda$onContent$0(android.view.SurfaceControlViewHost.SurfacePackage surfacePackage, int i, int i2) {
            if (!this.mFirstContentReceived) {
                handleOnFirstContentReceived(surfacePackage, i, i2);
                this.mFirstContentReceived = true;
            } else {
                handleOnSurfacePackage(surfacePackage);
            }
        }

        private void handleOnFirstContentReceived(android.view.SurfaceControlViewHost.SurfacePackage surfacePackage, int i, int i2) {
            this.mSurfacePackage = surfacePackage;
            if (this.mSurfacePackage == null) {
                this.mCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.view.inputmethod.InlineSuggestion$InlineContentCallbackImpl$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.inputmethod.InlineSuggestion.InlineContentCallbackImpl.this.lambda$handleOnFirstContentReceived$1();
                    }
                });
                return;
            }
            this.mView = new android.widget.inline.InlineContentView(this.mContext);
            if (this.mInlineTooltipUi != null) {
                this.mView.addOnLayoutChangeListener(new android.view.View.OnLayoutChangeListener() { // from class: android.view.inputmethod.InlineSuggestion.InlineContentCallbackImpl.1
                    @Override // android.view.View.OnLayoutChangeListener
                    public void onLayoutChange(android.view.View view, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
                        if (android.view.inputmethod.InlineSuggestion.InlineContentCallbackImpl.this.mInlineTooltipUi != null) {
                            android.view.inputmethod.InlineSuggestion.InlineContentCallbackImpl.this.mInlineTooltipUi.update(android.view.inputmethod.InlineSuggestion.InlineContentCallbackImpl.this.mView);
                        }
                    }
                });
            }
            this.mView.setLayoutParams(new android.view.ViewGroup.LayoutParams(i, i2));
            this.mView.setChildSurfacePackageUpdater(getSurfacePackageUpdater());
            this.mCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.view.inputmethod.InlineSuggestion$InlineContentCallbackImpl$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.inputmethod.InlineSuggestion.InlineContentCallbackImpl.this.lambda$handleOnFirstContentReceived$2();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleOnFirstContentReceived$1() {
            this.mCallback.accept(null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleOnFirstContentReceived$2() {
            this.mCallback.accept(this.mView);
        }

        private void handleOnSurfacePackage(android.view.SurfaceControlViewHost.SurfacePackage surfacePackage) {
            if (surfacePackage == null) {
                return;
            }
            if (this.mSurfacePackage != null || this.mSurfacePackageConsumer == null) {
                surfacePackage.release();
                try {
                    this.mInlineContentProvider.onSurfacePackageReleased();
                    return;
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(android.view.inputmethod.InlineSuggestion.TAG, "Error calling onSurfacePackageReleased(): " + e);
                    return;
                }
            }
            this.mSurfacePackage = surfacePackage;
            if (this.mSurfacePackage != null && this.mSurfacePackageConsumer != null) {
                this.mSurfacePackageConsumer.accept(this.mSurfacePackage);
                this.mSurfacePackageConsumer = null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleOnSurfacePackageReleased() {
            if (this.mSurfacePackage != null) {
                try {
                    this.mInlineContentProvider.onSurfacePackageReleased();
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(android.view.inputmethod.InlineSuggestion.TAG, "Error calling onSurfacePackageReleased(): " + e);
                }
                this.mSurfacePackage = null;
            }
            this.mSurfacePackageConsumer = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleGetSurfacePackage(java.util.function.Consumer<android.view.SurfaceControlViewHost.SurfacePackage> consumer) {
            if (this.mSurfacePackage != null) {
                consumer.accept(this.mSurfacePackage);
                return;
            }
            this.mSurfacePackageConsumer = consumer;
            try {
                this.mInlineContentProvider.requestSurfacePackage();
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(android.view.inputmethod.InlineSuggestion.TAG, "Error calling getSurfacePackage(): " + e);
                consumer.accept(null);
                this.mSurfacePackageConsumer = null;
            }
        }

        /* renamed from: android.view.inputmethod.InlineSuggestion$InlineContentCallbackImpl$2, reason: invalid class name */
        class AnonymousClass2 implements android.widget.inline.InlineContentView.SurfacePackageUpdater {
            AnonymousClass2() {
            }

            @Override // android.widget.inline.InlineContentView.SurfacePackageUpdater
            public void onSurfacePackageReleased() {
                android.view.inputmethod.InlineSuggestion.InlineContentCallbackImpl.this.mMainHandler.post(new java.lang.Runnable() { // from class: android.view.inputmethod.InlineSuggestion$InlineContentCallbackImpl$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.inputmethod.InlineSuggestion.InlineContentCallbackImpl.AnonymousClass2.this.lambda$onSurfacePackageReleased$0();
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onSurfacePackageReleased$0() {
                android.view.inputmethod.InlineSuggestion.InlineContentCallbackImpl.this.handleOnSurfacePackageReleased();
            }

            @Override // android.widget.inline.InlineContentView.SurfacePackageUpdater
            public void getSurfacePackage(final java.util.function.Consumer<android.view.SurfaceControlViewHost.SurfacePackage> consumer) {
                android.view.inputmethod.InlineSuggestion.InlineContentCallbackImpl.this.mMainHandler.post(new java.lang.Runnable() { // from class: android.view.inputmethod.InlineSuggestion$InlineContentCallbackImpl$2$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.inputmethod.InlineSuggestion.InlineContentCallbackImpl.AnonymousClass2.this.lambda$getSurfacePackage$1(consumer);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$getSurfacePackage$1(java.util.function.Consumer consumer) {
                android.view.inputmethod.InlineSuggestion.InlineContentCallbackImpl.this.handleGetSurfacePackage(consumer);
            }
        }

        private android.widget.inline.InlineContentView.SurfacePackageUpdater getSurfacePackageUpdater() {
            return new android.view.inputmethod.InlineSuggestion.InlineContentCallbackImpl.AnonymousClass2();
        }

        public void onClick() {
            this.mMainHandler.post(new java.lang.Runnable() { // from class: android.view.inputmethod.InlineSuggestion$InlineContentCallbackImpl$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.inputmethod.InlineSuggestion.InlineContentCallbackImpl.this.lambda$onClick$3();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClick$3() {
            if (this.mView != null && this.mView.hasOnClickListeners()) {
                this.mView.callOnClick();
            }
        }

        public void onLongClick() {
            this.mMainHandler.post(new java.lang.Runnable() { // from class: android.view.inputmethod.InlineSuggestion$InlineContentCallbackImpl$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.inputmethod.InlineSuggestion.InlineContentCallbackImpl.this.lambda$onLongClick$4();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onLongClick$4() {
            if (this.mView != null && this.mView.hasOnLongClickListeners()) {
                this.mView.performLongClick();
            }
        }
    }

    private static class InlineContentCallbackImplParceling implements com.android.internal.util.Parcelling<android.view.inputmethod.InlineSuggestion.InlineContentCallbackImpl> {
        private InlineContentCallbackImplParceling() {
        }

        @Override // com.android.internal.util.Parcelling
        public void parcel(android.view.inputmethod.InlineSuggestion.InlineContentCallbackImpl inlineContentCallbackImpl, android.os.Parcel parcel, int i) {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.android.internal.util.Parcelling
        public android.view.inputmethod.InlineSuggestion.InlineContentCallbackImpl unparcel(android.os.Parcel parcel) {
            return null;
        }
    }

    private static class InlineTooltipUiParceling implements com.android.internal.util.Parcelling<com.android.internal.view.inline.InlineTooltipUi> {
        private InlineTooltipUiParceling() {
        }

        @Override // com.android.internal.util.Parcelling
        public void parcel(com.android.internal.view.inline.InlineTooltipUi inlineTooltipUi, android.os.Parcel parcel, int i) {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.android.internal.util.Parcelling
        public com.android.internal.view.inline.InlineTooltipUi unparcel(android.os.Parcel parcel) {
            return null;
        }
    }

    public InlineSuggestion(android.view.inputmethod.InlineSuggestionInfo inlineSuggestionInfo, com.android.internal.view.inline.IInlineContentProvider iInlineContentProvider, android.view.inputmethod.InlineSuggestion.InlineContentCallbackImpl inlineContentCallbackImpl, com.android.internal.view.inline.InlineTooltipUi inlineTooltipUi) {
        this.mInfo = inlineSuggestionInfo;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mInfo);
        this.mContentProvider = iInlineContentProvider;
        this.mInlineContentCallback = inlineContentCallbackImpl;
        this.mInlineTooltipUi = inlineTooltipUi;
    }

    public android.view.inputmethod.InlineSuggestionInfo getInfo() {
        return this.mInfo;
    }

    public com.android.internal.view.inline.IInlineContentProvider getContentProvider() {
        return this.mContentProvider;
    }

    public android.view.inputmethod.InlineSuggestion.InlineContentCallbackImpl getInlineContentCallback() {
        return this.mInlineContentCallback;
    }

    public com.android.internal.view.inline.InlineTooltipUi getInlineTooltipUi() {
        return this.mInlineTooltipUi;
    }

    public java.lang.String toString() {
        return "InlineSuggestion { info = " + this.mInfo + ", contentProvider = " + this.mContentProvider + ", inlineContentCallback = " + this.mInlineContentCallback + ", inlineTooltipUi = " + this.mInlineTooltipUi + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.view.inputmethod.InlineSuggestion inlineSuggestion = (android.view.inputmethod.InlineSuggestion) obj;
        if (java.util.Objects.equals(this.mInfo, inlineSuggestion.mInfo) && java.util.Objects.equals(this.mContentProvider, inlineSuggestion.mContentProvider) && java.util.Objects.equals(this.mInlineContentCallback, inlineSuggestion.mInlineContentCallback) && java.util.Objects.equals(this.mInlineTooltipUi, inlineSuggestion.mInlineTooltipUi)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((java.util.Objects.hashCode(this.mInfo) + 31) * 31) + java.util.Objects.hashCode(this.mContentProvider)) * 31) + java.util.Objects.hashCode(this.mInlineContentCallback)) * 31) + java.util.Objects.hashCode(this.mInlineTooltipUi);
    }

    static {
        sParcellingForInlineContentCallback = com.android.internal.util.Parcelling.Cache.get(android.view.inputmethod.InlineSuggestion.InlineContentCallbackImplParceling.class);
        byte b = 0;
        if (sParcellingForInlineContentCallback == null) {
            sParcellingForInlineContentCallback = com.android.internal.util.Parcelling.Cache.put(new android.view.inputmethod.InlineSuggestion.InlineContentCallbackImplParceling());
        }
        sParcellingForInlineTooltipUi = com.android.internal.util.Parcelling.Cache.get(android.view.inputmethod.InlineSuggestion.InlineTooltipUiParceling.class);
        if (sParcellingForInlineTooltipUi == null) {
            sParcellingForInlineTooltipUi = com.android.internal.util.Parcelling.Cache.put(new android.view.inputmethod.InlineSuggestion.InlineTooltipUiParceling());
        }
        CREATOR = new android.os.Parcelable.Creator<android.view.inputmethod.InlineSuggestion>() { // from class: android.view.inputmethod.InlineSuggestion.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.inputmethod.InlineSuggestion[] newArray(int i) {
                return new android.view.inputmethod.InlineSuggestion[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.inputmethod.InlineSuggestion createFromParcel(android.os.Parcel parcel) {
                return new android.view.inputmethod.InlineSuggestion(parcel);
            }
        };
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        byte b = this.mContentProvider != null ? (byte) 2 : (byte) 0;
        if (this.mInlineContentCallback != null) {
            b = (byte) (b | 4);
        }
        if (this.mInlineTooltipUi != null) {
            b = (byte) (b | 8);
        }
        parcel.writeByte(b);
        parcel.writeTypedObject(this.mInfo, i);
        if (this.mContentProvider != null) {
            parcel.writeStrongInterface(this.mContentProvider);
        }
        sParcellingForInlineContentCallback.parcel(this.mInlineContentCallback, parcel, i);
        sParcellingForInlineTooltipUi.parcel(this.mInlineTooltipUi, parcel, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    InlineSuggestion(android.os.Parcel parcel) {
        byte readByte = parcel.readByte();
        android.view.inputmethod.InlineSuggestionInfo inlineSuggestionInfo = (android.view.inputmethod.InlineSuggestionInfo) parcel.readTypedObject(android.view.inputmethod.InlineSuggestionInfo.CREATOR);
        com.android.internal.view.inline.IInlineContentProvider asInterface = (readByte & 2) == 0 ? null : com.android.internal.view.inline.IInlineContentProvider.Stub.asInterface(parcel.readStrongBinder());
        android.view.inputmethod.InlineSuggestion.InlineContentCallbackImpl unparcel = sParcellingForInlineContentCallback.unparcel(parcel);
        com.android.internal.view.inline.InlineTooltipUi unparcel2 = sParcellingForInlineTooltipUi.unparcel(parcel);
        this.mInfo = inlineSuggestionInfo;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mInfo);
        this.mContentProvider = asInterface;
        this.mInlineContentCallback = unparcel;
        this.mInlineTooltipUi = unparcel2;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
