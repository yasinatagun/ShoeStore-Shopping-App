package com.yasinatagun.shoestore.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavAction;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yasinatagun.shoestore.adapter.CartAdapter;
import com.yasinatagun.shoestore.databinding.FragmentCartBinding;
import com.yasinatagun.shoestore.model.Shoe;
import com.yasinatagun.shoestore.view.MainActivity;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    RecyclerView recyclerView;
    public CartAdapter adapter;

    public FragmentCartBinding binding;
    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        recyclerView = binding.cartRecyclerView;

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CartAdapter(getContext());
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.totalItemCountText.setText(String.valueOf(MainActivity.totalItemCount));
        binding.totalAmountText .setText(String.valueOf(MainActivity.totalAmount));
        binding.buttonBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyNowButtonClicked(v);
            }
        });
    }

    public void buyNowButtonClicked(View view){
        Navigation.findNavController(view).navigate((NavDirections) CartFragmentDirections.actionCartFragmentToInfotakingFragment());
    }

}