package project.ggim.sample.ui.singer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SingerViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SingerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is singer fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}