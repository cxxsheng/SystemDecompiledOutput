package android.service.settings.suggestions;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class SuggestionService extends android.app.Service {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "SuggestionService";

    public abstract java.util.List<android.service.settings.suggestions.Suggestion> onGetSuggestions();

    public abstract void onSuggestionDismissed(android.service.settings.suggestions.Suggestion suggestion);

    public abstract void onSuggestionLaunched(android.service.settings.suggestions.Suggestion suggestion);

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        return new android.service.settings.suggestions.ISuggestionService.Stub() { // from class: android.service.settings.suggestions.SuggestionService.1
            @Override // android.service.settings.suggestions.ISuggestionService
            public java.util.List<android.service.settings.suggestions.Suggestion> getSuggestions() {
                return android.service.settings.suggestions.SuggestionService.this.onGetSuggestions();
            }

            @Override // android.service.settings.suggestions.ISuggestionService
            public void dismissSuggestion(android.service.settings.suggestions.Suggestion suggestion) {
                android.service.settings.suggestions.SuggestionService.this.onSuggestionDismissed(suggestion);
            }

            @Override // android.service.settings.suggestions.ISuggestionService
            public void launchSuggestion(android.service.settings.suggestions.Suggestion suggestion) {
                android.service.settings.suggestions.SuggestionService.this.onSuggestionLaunched(suggestion);
            }
        };
    }
}
