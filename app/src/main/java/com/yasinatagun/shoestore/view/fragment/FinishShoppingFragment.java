package com.yasinatagun.shoestore.view.fragment;

import android.os.Binder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yasinatagun.shoestore.R;
import com.yasinatagun.shoestore.databinding.FragmentFinishShoppingBinding;

public class FinishShoppingFragment extends Fragment {
    private FragmentFinishShoppingBinding binding;
String zip, adress, name, phone, city, date, cardNumber;
    public FinishShoppingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFinishShoppingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        if (getArguments() != null) {
            date = FinishShoppingFragmentArgs.fromBundle(getArguments()).getDate();
            zip = FinishShoppingFragmentArgs.fromBundle(getArguments()).getZip();
            adress = FinishShoppingFragmentArgs.fromBundle(getArguments()).getAdress();
            name = FinishShoppingFragmentArgs.fromBundle(getArguments()).getCardName();
            phone = FinishShoppingFragmentArgs.fromBundle(getArguments()).getPhone();
            cardNumber = FinishShoppingFragmentArgs.fromBundle(getArguments()).getCardNumber();
            city = FinishShoppingFragmentArgs.fromBundle(getArguments()).getCity();

            binding.doneAdress.setText(adress);
            binding.doneCardNumber.setText(cardNumber);
            binding.doneCity.setText(city);
            binding.doneDate.setText(date);
            binding.doneName.setText(name);
            binding.donePaymentType.setText("Credit Card");
            binding.donePhone.setText(phone);

            binding.buttonReturnHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    returnHomeClicked(v);
                }
            });


        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void returnHomeClicked(View view) {
        Navigation.findNavController(view).navigate((NavDirections) FinishShoppingFragmentDirections.actionFinishShoppingFragmentToListFragment());

    }
}