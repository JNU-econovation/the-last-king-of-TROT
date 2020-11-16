package project.ggim.sample.ui.popular;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PopularViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PopularViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is popular fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}