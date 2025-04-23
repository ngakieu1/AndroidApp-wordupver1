
package com.example.wordup.TwoPane;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wordup.Adapters.PackAdapter;
import com.example.wordup.Models.PackModel;
import com.example.wordup.R;
import com.example.wordup.RecyclerViewInterface;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    private ArrayList<PackModel> packModels;
    private int[] packImages = {
            R.drawable.whale, R.drawable.cloudy, R.drawable.languages,
            R.drawable.colour, R.drawable.clothes, R.drawable.numbers,
            R.drawable.car, R.drawable.technology
    };

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Setup RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        packModels = new ArrayList<>();
        setUpPackModels();

        PackAdapter adapter = new PackAdapter(
                requireContext(),
                packModels,
                (RecyclerViewInterface) requireActivity(),
                true // isTwoPane
        );

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Setup BottomNavigationView
        BottomNavigationView bottomNav = view.findViewById(R.id.bottomNav);
        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_account) {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).showAccount();
                }
                return true;
            } else if (item.getItemId() == R.id.nav_home) {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).goHome();
                }
                return true;
            }
            return false;
        });
    }



    private void setUpPackModels() {
        String[] packNames = getResources().getStringArray(R.array.category_vocab);
        for (int i = 0; i < packNames.length; i++) {
            packModels.add(new PackModel(packNames[i], packImages[i]));
        }
    }
}
