package android.service.autofill.augmented;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class PresentationParams {
    abstract void dump(java.lang.String str, java.io.PrintWriter printWriter);

    PresentationParams() {
    }

    public android.service.autofill.augmented.PresentationParams.Area getSuggestionArea() {
        return null;
    }

    @android.annotation.SystemApi
    public static abstract class Area {
        private final android.graphics.Rect mBounds;
        public final android.service.autofill.augmented.AugmentedAutofillService.AutofillProxy proxy;

        private Area(android.service.autofill.augmented.AugmentedAutofillService.AutofillProxy autofillProxy, android.graphics.Rect rect) {
            this.proxy = autofillProxy;
            this.mBounds = rect;
        }

        public android.graphics.Rect getBounds() {
            return this.mBounds;
        }

        public java.lang.String toString() {
            return this.mBounds.toString();
        }
    }

    public static final class SystemPopupPresentationParams extends android.service.autofill.augmented.PresentationParams {
        private final android.service.autofill.augmented.PresentationParams.Area mSuggestionArea;

        public SystemPopupPresentationParams(android.service.autofill.augmented.AugmentedAutofillService.AutofillProxy autofillProxy, android.graphics.Rect rect) {
            this.mSuggestionArea = new android.service.autofill.augmented.PresentationParams.Area(autofillProxy, rect) { // from class: android.service.autofill.augmented.PresentationParams.SystemPopupPresentationParams.1
            };
        }

        @Override // android.service.autofill.augmented.PresentationParams
        public android.service.autofill.augmented.PresentationParams.Area getSuggestionArea() {
            return this.mSuggestionArea;
        }

        @Override // android.service.autofill.augmented.PresentationParams
        void dump(java.lang.String str, java.io.PrintWriter printWriter) {
            printWriter.print(str);
            printWriter.print("area: ");
            printWriter.println(this.mSuggestionArea);
        }
    }
}
