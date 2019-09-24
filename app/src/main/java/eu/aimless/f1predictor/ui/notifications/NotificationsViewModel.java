package eu.aimless.f1predictor.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import eu.aimless.f1predictor.repository.DataManager;

public class NotificationsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NotificationsViewModel() {
        mText = new MutableLiveData<>();

        String raceName = DataManager.getInstance().getLatestRaceName();
        mText.setValue(raceName);
    }

    public LiveData<String> getText() {
        return mText;
    }
}