package com.yasinatagun.shoestore.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.yasinatagun.shoestore.adapter.ShoeAdapter;
import com.yasinatagun.shoestore.databinding.FragmentListBinding;
import com.yasinatagun.shoestore.model.Shoe;
import com.yasinatagun.shoestore.model.enums.Gender;
import com.yasinatagun.shoestore.model.enums.ShoeType;
import com.yasinatagun.shoestore.view.MainActivity;

import java.util.ArrayList;
import java.util.Map;


public class ListFragment extends Fragment implements ShoeAdapter.ItemClickListener {
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    ShoeAdapter adapter;
    ArrayList<Shoe> shoeArrayList;
    RecyclerView recyclerView;
    private FragmentListBinding binding;
    ArrayList<Shoe> shoeFromDBlist;

    public ListFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shoeArrayList = new ArrayList<>();

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        shoeFromDBlist = new ArrayList<>();
        getDataFromFirestore();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        recyclerView = binding.shoeRecyclerView;
        int numberOfColumns = 2;

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        adapter = new ShoeAdapter(getContext(), shoeFromDBlist);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onItemClick(View view, int position) {
        Shoe shoe = shoeFromDBlist.get(position);
        Navigation.findNavController(view).navigate((NavDirections) ListFragmentDirections.actionListFragmentToShoeFragment(shoe));

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Burada gramer hatası oluşmamasını kontrol ediyorum
        if (shoeFromDBlist.size() > 1)
            binding.tvProductCount.setText("There are total " + shoeFromDBlist.size() + " product in our store");
        else
            binding.tvProductCount.setText("There is " + shoeFromDBlist.size() + " product int our store");
    }

    public void getDataFromFirestore() {
        CollectionReference collectionReference = firebaseFirestore.collection("Shoes");

        collectionReference.addSnapshotListener(new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(getContext(), error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }

                if (value != null) {
                    System.out.println(value.getDocuments().size());
                    for (DocumentChange snapshot : value.getDocumentChanges()) {
                        Map<String, Object> data = snapshot.getDocument().getData();

                        String productName = (String) data.get("productName");
                        String productDescription = (String) data.get("productDescription");
                        ShoeType productType = ShoeType.valueOf((String) data.get("productType"));
                        int productPrice = ((Long) data.get("productPrice")).intValue();
                        Gender productGender = Gender.valueOf((String) data.get("productGender"));
                        String productDownloadUrl = (String) data.get("downloadUrl");
                        int productStock = ((Long) data.get("productStock")).intValue();
                        int productDeliveryTime = ((Long) data.get("productDeliveryTime")).intValue();
                        String documentId = snapshot.getDocument().getId();

                        // Burada çektiğim data ile ayakkabı oluşturup arraylistte sıralıyorum
                        Shoe shoe = new Shoe(productName, productType, productPrice, productDescription, productStock, productDeliveryTime, productGender, productDownloadUrl, documentId);
                        System.out.println(shoe);
                        shoeFromDBlist.add(shoe);
                    }
                    binding.tvProductCount.setText("There are total " + shoeFromDBlist.size() + " product in our store");

                    // Burada adapterımı değişiklik için uyarıyorum
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

}