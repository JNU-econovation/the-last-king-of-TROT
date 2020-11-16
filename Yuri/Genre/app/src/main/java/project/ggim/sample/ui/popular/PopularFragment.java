package project.ggim.sample.ui.popular;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import project.ggim.sample.R;

public class PopularFragment extends Fragment {

    private PopularViewModel popularViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        popularViewModel =
                new ViewModelProvider(this).get(PopularViewModel.class);
        View root = inflater.inflate(R.layout.fragment_popular, container, false);
        final TextView textView = root.findViewById(R.id.text_popular);
        popularViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}