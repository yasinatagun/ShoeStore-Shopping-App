package com.yasinatagun.shoestore.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yasinatagun.shoestore.databinding.FragmentPaymentChoiceBinding;
import com.yasinatagun.shoestore.model.enums.PaymentType;


public class PaymentChoiceFragment extends Fragment {
    String name;
    String phone;
    String zip;
    String city;
    String adress;
    private FragmentPaymentChoiceBinding binding;
    public PaymentChoiceFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaymentChoiceBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Burada argümanları fragmentlar arası geçirebilmek için, burada da aldım.
        if (getArguments() != null) {
            zip = PaymentChoiceFragmentArgs.fromBundle(getArguments()).getZip();
            name = PaymentChoiceFragmentArgs.fromBundle(getArguments()).getName();
            adress = PaymentChoiceFragmentArgs.fromBundle(getArguments()).getAdress();
            phone = PaymentChoiceFragmentArgs.fromBundle(getArguments()).getPhone();
            city = PaymentChoiceFragmentArgs.fromBundle(getArguments()).getCity();
        }


        binding.paywithCreditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creaditCardSelection(v);
            }
        });

        // BURADA NFC EKRANINA GİDİLECEK.
        // NFC YAPILMADI

//        binding.paywithNFC.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    public void creaditCardSelection(View view){
        // Burada info fragmentından buraya aldığım bilgileri credi kartı fragmentına da aktarıyorum
        PaymentChoiceFragmentDirections.ActionPaymentChoiceToPaymentFragment action = PaymentChoiceFragmentDirections.actionPaymentChoiceToPaymentFragment(name, zip, adress, phone, city);
        Navigation.findNavController(view).navigate((NavDirections) action);    }


    // NFC EKLENDİKTEN SONRA EKLENECEK
//    public void nfcSelection(View view){
//        PaymentChoiceFragmentDirections.ActionPaymentChoiceToPaymentFragment action = PaymentChoiceFragmentDirections.actionPaymentChoiceToPaymentFragment(name, zip, adress, phone, city, PaymentType.NFC);
//        Navigation.findNavController(view).navigate((NavDirections) action);    }
}