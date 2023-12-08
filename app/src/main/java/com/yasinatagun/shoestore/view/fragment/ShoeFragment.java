package com.yasinatagun.shoestore.view.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.yasinatagun.shoestore.databinding.FragmentShoeBinding;
import com.yasinatagun.shoestore.model.Shoe;
import com.yasinatagun.shoestore.view.MainActivity;


public class ShoeFragment extends Fragment {
    private FragmentShoeBinding binding;
    Shoe shoe;
    FirebaseFirestore firebaseFirestore;
    public ShoeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentShoeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            // Burada atamaları alıp viewBinding ile ekrana yerleştiriyorum
        if (getArguments() != null) {
            shoe = ShoeFragmentArgs.fromBundle(getArguments()).getShoe();
            binding.tvProductName.setText(shoe.getProductName());
            binding.tvProductDescription.setText(shoe.getProductDescription());
            binding.tvProductPrice.setText("$ " + shoe.getProductPrice());
            binding.tvProductType.setText(shoe.getProductType().toString());
            binding.tvProductGender.setText(shoe.getProductGender().toString());
            binding.tvProductDelivery.setText(String.valueOf(shoe.getProductDeliveryTime()));
            binding.tvProductStock.setText(String.valueOf(shoe.getProductStock()));
            Picasso.get().load(shoe.getDownloadUrl()).into(binding.productImage);

            firebaseFirestore = FirebaseFirestore.getInstance();

            binding.addCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String userEmail = getContext().getSharedPreferences("default", MODE_PRIVATE).getString("userEmail", "null");
                    firebaseFirestore.collection("users").document(userEmail).collection("cart").add(shoe);
                }
            });
        }

        binding.buttonBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = getContext().getSharedPreferences("default", MODE_PRIVATE).getString("userEmail", "null");
                firebaseFirestore.collection("users").document(userEmail).collection("cart").add(shoe);
                Navigation.findNavController(view).navigate((NavDirections) ShoeFragmentDirections.actionShoeFragmentToCartFragment());
            }
        });

    }
}