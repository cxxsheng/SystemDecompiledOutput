package android.view.textclassifier;

/* loaded from: classes4.dex */
public final class TextLinksParams {
    private static final java.util.function.Function<android.view.textclassifier.TextLinks.TextLink, android.view.textclassifier.TextLinks.TextLinkSpan> DEFAULT_SPAN_FACTORY = new java.util.function.Function() { // from class: android.view.textclassifier.TextLinksParams$$ExternalSyntheticLambda0
        @Override // java.util.function.Function
        public final java.lang.Object apply(java.lang.Object obj) {
            return android.view.textclassifier.TextLinksParams.lambda$static$0((android.view.textclassifier.TextLinks.TextLink) obj);
        }
    };
    private final int mApplyStrategy;
    private final android.view.textclassifier.TextClassifier.EntityConfig mEntityConfig;
    private final java.util.function.Function<android.view.textclassifier.TextLinks.TextLink, android.view.textclassifier.TextLinks.TextLinkSpan> mSpanFactory;

    static /* synthetic */ android.view.textclassifier.TextLinks.TextLinkSpan lambda$static$0(android.view.textclassifier.TextLinks.TextLink textLink) {
        return new android.view.textclassifier.TextLinks.TextLinkSpan(textLink);
    }

    private TextLinksParams(int i, java.util.function.Function<android.view.textclassifier.TextLinks.TextLink, android.view.textclassifier.TextLinks.TextLinkSpan> function) {
        this.mApplyStrategy = i;
        this.mSpanFactory = function;
        this.mEntityConfig = android.view.textclassifier.TextClassifier.EntityConfig.createWithHints(null);
    }

    public static android.view.textclassifier.TextLinksParams fromLinkMask(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if ((i & 1) != 0) {
            arrayList.add("url");
        }
        if ((i & 2) != 0) {
            arrayList.add("email");
        }
        if ((i & 4) != 0) {
            arrayList.add("phone");
        }
        if ((i & 8) != 0) {
            arrayList.add("address");
        }
        return new android.view.textclassifier.TextLinksParams.Builder().setEntityConfig(android.view.textclassifier.TextClassifier.EntityConfig.createWithExplicitEntityList(arrayList)).build();
    }

    public android.view.textclassifier.TextClassifier.EntityConfig getEntityConfig() {
        return this.mEntityConfig;
    }

    public int apply(android.text.Spannable spannable, android.view.textclassifier.TextLinks textLinks) {
        java.util.Objects.requireNonNull(spannable);
        java.util.Objects.requireNonNull(textLinks);
        java.lang.String obj = spannable.toString();
        if (android.text.util.Linkify.containsUnsupportedCharacters(obj)) {
            android.util.EventLog.writeEvent(1397638484, "116321860", -1, "");
            return 4;
        }
        if (!obj.startsWith(textLinks.getText().toString())) {
            return 3;
        }
        if (textLinks.getLinks().isEmpty()) {
            return 1;
        }
        int i = 0;
        for (android.view.textclassifier.TextLinks.TextLink textLink : textLinks.getLinks()) {
            android.view.textclassifier.TextLinks.TextLinkSpan apply = this.mSpanFactory.apply(textLink);
            if (apply != null) {
                android.text.style.ClickableSpan[] clickableSpanArr = (android.text.style.ClickableSpan[]) spannable.getSpans(textLink.getStart(), textLink.getEnd(), android.text.style.ClickableSpan.class);
                if (clickableSpanArr.length > 0) {
                    if (this.mApplyStrategy == 1) {
                        for (android.text.style.ClickableSpan clickableSpan : clickableSpanArr) {
                            spannable.removeSpan(clickableSpan);
                        }
                        spannable.setSpan(apply, textLink.getStart(), textLink.getEnd(), 33);
                        i++;
                    }
                } else {
                    spannable.setSpan(apply, textLink.getStart(), textLink.getEnd(), 33);
                    i++;
                }
            }
        }
        return i == 0 ? 2 : 0;
    }

    public static final class Builder {
        private int mApplyStrategy = 0;
        private java.util.function.Function<android.view.textclassifier.TextLinks.TextLink, android.view.textclassifier.TextLinks.TextLinkSpan> mSpanFactory = android.view.textclassifier.TextLinksParams.DEFAULT_SPAN_FACTORY;

        public android.view.textclassifier.TextLinksParams.Builder setApplyStrategy(int i) {
            this.mApplyStrategy = android.view.textclassifier.TextLinksParams.checkApplyStrategy(i);
            return this;
        }

        public android.view.textclassifier.TextLinksParams.Builder setSpanFactory(java.util.function.Function<android.view.textclassifier.TextLinks.TextLink, android.view.textclassifier.TextLinks.TextLinkSpan> function) {
            if (function == null) {
                function = android.view.textclassifier.TextLinksParams.DEFAULT_SPAN_FACTORY;
            }
            this.mSpanFactory = function;
            return this;
        }

        public android.view.textclassifier.TextLinksParams.Builder setEntityConfig(android.view.textclassifier.TextClassifier.EntityConfig entityConfig) {
            return this;
        }

        public android.view.textclassifier.TextLinksParams build() {
            return new android.view.textclassifier.TextLinksParams(this.mApplyStrategy, this.mSpanFactory);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int checkApplyStrategy(int i) {
        if (i != 0 && i != 1) {
            throw new java.lang.IllegalArgumentException("Invalid apply strategy. See TextLinksParams.ApplyStrategy for options.");
        }
        return i;
    }
}
