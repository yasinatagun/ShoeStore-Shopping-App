package com.yasinatagun.shoestore.view.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.yasinatagun.shoestore.R;
import com.yasinatagun.shoestore.databinding.FragmentPaymentBinding;
import com.yasinatagun.shoestore.model.CreditCard;
import com.yasinatagun.shoestore.model.Shoe;
import com.yasinatagun.shoestore.model.enums.PaymentType;
import com.yasinatagun.shoestore.view.MainActivity;

import java.util.Random;

public class PaymentFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    // Burada tüm bilgileri almamın sebebi, kullanıcıya bir transaction bilgisi göstermek
    // Bunun yanında gerçek ödeme sistemlerinde de bu bilgilerin kullanılması gerektiğini düşündüm.
    FirebaseFirestore firebaseFirestore;
    CreditCard creditCard;
    String[] months = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    String[] years = {"2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031"};
    String cvv;
    String cardNumber;
    String cardName;
    String month;
    String year;
    String name;
    String phone;
    String zip;
    String city;
    String adress;
    String dateNow;
    private FragmentPaymentBinding binding;

    public PaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaymentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Spinner monthSpin = view.findViewById(R.id.monthspinner);
        Spinner yearSpin = view.findViewById(R.id.yearsspinner);
        firebaseFirestore = FirebaseFirestore.getInstance();
        monthSpin.setOnItemSelectedListener(this);
        yearSpin.setOnItemSelectedListener(this);
        ArrayAdapter monthAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, months);
        ArrayAdapter yearAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, years);

        // Spinnerlarımı oluşturduğum bölüm
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpin.setAdapter(monthAdapter);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpin.setAdapter(yearAdapter);

// Navigasyonda gelen fragment argümanları
        if (getArguments() != null) {
            zip = PaymentChoiceFragmentArgs.fromBundle(getArguments()).getZip();
            name = PaymentChoiceFragmentArgs.fromBundle(getArguments()).getName();
            adress = PaymentChoiceFragmentArgs.fromBundle(getArguments()).getAdress();
            phone = PaymentChoiceFragmentArgs.fromBundle(getArguments()).getPhone();
            city = PaymentChoiceFragmentArgs.fromBundle(getArguments()).getCity();
        }
        binding.buyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                buyClicked(v);
//                System.out.println(creditCard.getCardName().toString());
//                System.out.println(creditCard.getCardMonth().toString());
                if (validateCreditCard(creditCard)) {
                    String userEmail = getContext().getSharedPreferences("default", MODE_PRIVATE).getString("userEmail", "null");

                    dateNow = java.time.LocalDate.now().toString();
                    // DB DEN KOMPLE SİLME KULLANILACAK
                    for (Shoe shoe : MainActivity.cartAddedList) {
                        firebaseFirestore.collection("users").document(userEmail).collection("cart").document(shoe.getDocumentId()).delete();
                    }

                    // ********************************
                    PaymentFragmentDirections.ActionPaymentFragmentToFinishShoppingFragment action = PaymentFragmentDirections.actionPaymentFragmentToFinishShoppingFragment(cardName, cardNumber, zip, adress, dateNow, city, phone);
                    Navigation.findNavController(view).navigate((NavDirections) action);
                } else {
                    Toast.makeText(getContext(), "Wrong Credit Card Credentials", Toast.LENGTH_SHORT).show();
                    binding.paymentCreditCardNumber.setText("");
                    binding.paymentCreditCardName.setText("");
                    binding.paymentCreditCardCVV.setText("");
                }

            }
        });
    }

    public void buyClicked(View view) {
        cvv = binding.paymentCreditCardCVV.getText().toString();
        cardName = binding.paymentCreditCardName.getText().toString();
        cardNumber = binding.paymentCreditCardNumber.getText().toString();
        month = months[binding.monthspinner.getSelectedItemPosition()];
        year = years[binding.yearsspinner.getSelectedItemPosition()];
        creditCard = new CreditCard(cardNumber, cardName, month, year, cvv);
//        System.out.println(creditCard.getCardMonth());
//        System.out.println(creditCard.getCardName());
//        System.out.println(creditCard.getCardNumber());
//        System.out.println(creditCard.getCardYear());
    }

    // Burada kredi kartının 16 rakamlı numarası ve 3 rakamlı cvv'si olmasını kontrol ediyorum.
    public boolean validateCreditCard(CreditCard creditCard) {
        if (howMany(creditCard.getCardNumber()) == 16 && howMany(creditCard.getCardCVV()) == 3 && creditCard.getCardName() != null) {
            return true;
        } else {
            return false;
        }
    }

    // Kaç karakterin olduğunu hesaplayan method
    public int howMany(String data) {
        int totalCharacters = 0;
        char temp;
        for (int i = 0; i < data.length(); i++) {
            temp = data.charAt(i);
            if (temp != ' ')
                totalCharacters++;
        }
        return totalCharacters;
    }

    // SPINNER ile ilgili override methodalar
    @Override
    public void onItemSelected(AdapterView arg0, View arg1,
                               int position,
                               long id) {

    }

    @Override
    public void onNothingSelected(AdapterView arg0) {
        // Auto-generated method stub
    }
}