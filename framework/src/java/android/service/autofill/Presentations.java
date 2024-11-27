package android.service.autofill;

/* loaded from: classes3.dex */
public final class Presentations {
    private android.widget.RemoteViews mDialogPresentation;
    private android.service.autofill.InlinePresentation mInlinePresentation;
    private android.service.autofill.InlinePresentation mInlineTooltipPresentation;
    private android.widget.RemoteViews mMenuPresentation;

    /* JADX INFO: Access modifiers changed from: private */
    public static android.widget.RemoteViews defaultMenuPresentation() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.service.autofill.InlinePresentation defaultInlinePresentation() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.widget.RemoteViews defaultDialogPresentation() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.service.autofill.InlinePresentation defaultInlineTooltipPresentation() {
        return null;
    }

    private void onConstructed() {
        if (this.mMenuPresentation == null && this.mInlinePresentation == null && this.mDialogPresentation == null) {
            throw new java.lang.IllegalStateException("All presentations are null.");
        }
        if (this.mInlineTooltipPresentation != null && this.mInlinePresentation == null) {
            throw new java.lang.IllegalStateException("The inline presentation is required for mInlineTooltipPresentation.");
        }
    }

    Presentations(android.widget.RemoteViews remoteViews, android.service.autofill.InlinePresentation inlinePresentation, android.widget.RemoteViews remoteViews2, android.service.autofill.InlinePresentation inlinePresentation2) {
        this.mMenuPresentation = remoteViews;
        this.mInlinePresentation = inlinePresentation;
        this.mDialogPresentation = remoteViews2;
        this.mInlineTooltipPresentation = inlinePresentation2;
        onConstructed();
    }

    public android.widget.RemoteViews getMenuPresentation() {
        return this.mMenuPresentation;
    }

    public android.service.autofill.InlinePresentation getInlinePresentation() {
        return this.mInlinePresentation;
    }

    public android.widget.RemoteViews getDialogPresentation() {
        return this.mDialogPresentation;
    }

    public android.service.autofill.InlinePresentation getInlineTooltipPresentation() {
        return this.mInlineTooltipPresentation;
    }

    public static final class Builder {
        private long mBuilderFieldsSet = 0;
        private android.widget.RemoteViews mDialogPresentation;
        private android.service.autofill.InlinePresentation mInlinePresentation;
        private android.service.autofill.InlinePresentation mInlineTooltipPresentation;
        private android.widget.RemoteViews mMenuPresentation;

        public android.service.autofill.Presentations.Builder setMenuPresentation(android.widget.RemoteViews remoteViews) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mMenuPresentation = remoteViews;
            return this;
        }

        public android.service.autofill.Presentations.Builder setInlinePresentation(android.service.autofill.InlinePresentation inlinePresentation) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mInlinePresentation = inlinePresentation;
            return this;
        }

        public android.service.autofill.Presentations.Builder setDialogPresentation(android.widget.RemoteViews remoteViews) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mDialogPresentation = remoteViews;
            return this;
        }

        public android.service.autofill.Presentations.Builder setInlineTooltipPresentation(android.service.autofill.InlinePresentation inlinePresentation) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mInlineTooltipPresentation = inlinePresentation;
            return this;
        }

        public android.service.autofill.Presentations build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            if ((this.mBuilderFieldsSet & 1) == 0) {
                this.mMenuPresentation = android.service.autofill.Presentations.defaultMenuPresentation();
            }
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mInlinePresentation = android.service.autofill.Presentations.defaultInlinePresentation();
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mDialogPresentation = android.service.autofill.Presentations.defaultDialogPresentation();
            }
            if ((this.mBuilderFieldsSet & 8) == 0) {
                this.mInlineTooltipPresentation = android.service.autofill.Presentations.defaultInlineTooltipPresentation();
            }
            return new android.service.autofill.Presentations(this.mMenuPresentation, this.mInlinePresentation, this.mDialogPresentation, this.mInlineTooltipPresentation);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 16) != 0) {
                throw new java.lang.IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
