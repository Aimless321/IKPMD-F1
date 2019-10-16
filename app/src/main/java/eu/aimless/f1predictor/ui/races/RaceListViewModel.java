package eu.aimless.f1predictor.ui.races;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RaceListViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RaceListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment, hello world");
    }

    public LiveData<String> getText() {
        return mText;
    }
}