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
import android.widget.Toast;

import com.yasinatagun.shoestore.R;
import com.yasinatagun.shoestore.databinding.FragmentInfotakingBinding;
import com.yasinatagun.shoestore.model.CreditCard;

public class InfotakingFragment extends Fragment {
    private FragmentInfotakingBinding binding;
    String name;
    String phone;
    String zip;
    String city;
    String adress;

    public InfotakingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInfotakingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.goToBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBuyClicked(v);
                if (validateAdressInfo(name,adress,city,zip,phone)){
                    // Burada bilgiler doluysa bilgileri payment choice fragmentına gönderip, navigate ediyorum.
                    InfotakingFragmentDirections.ActionInfotakingFragmentToPaymentChoice action = InfotakingFragmentDirections.actionInfotakingFragmentToPaymentChoice(name, zip, adress, phone, city);
                    Navigation.findNavController(view).navigate((NavDirections) action);
                }else{
                    // Burada eğer bilgiler doğru değilse tüm edit text girdilerini silip, tekrar girilmesini istiyorum.
                    Toast.makeText(getContext(), "Wrong or Missing Information", Toast.LENGTH_LONG).show();
                    binding.etAdressCity.setText("");
                    binding.etAdressInfo.setText("");
                    binding.etAdressZIP.setText("");
                    binding.etAdressPhone.setText("");
                    binding.etAdressName.setText("");
                }
            }
        });


    }

    // Burada o anki edittext verilerini çekiyorum
    public void goToBuyClicked(View view) {
        name = binding.etAdressName.getText().toString();
        phone = binding.etAdressPhone.getText().toString();
        zip = binding.etAdressZIP.getText().toString();
        adress = binding.etAdressInfo.getText().toString();
        city = binding.etAdressCity.getText().toString();

    }

    // Burada verilerin girilip girilmediğini kontrol ediyorum
    public boolean validateAdressInfo(String a, String b, String c, String d, String e) {
        if (!a.equals("") && !b.equals("") &&!c.equals("") &&!d.equals("") && !e.equals("")) {
            return true;
        } else {
            return false;
        }
    }
}