package com.yasinatagun.shoestore.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.yasinatagun.shoestore.BadgeDrawable;
import com.yasinatagun.shoestore.R;
import com.yasinatagun.shoestore.databinding.ActivityMainBinding;
import com.yasinatagun.shoestore.model.Shoe;
import com.yasinatagun.shoestore.model.enums.Gender;
import com.yasinatagun.shoestore.model.enums.ShoeType;
import com.yasinatagun.shoestore.view.fragment.CartFragment;
import com.yasinatagun.shoestore.view.fragment.InfotakingFragment;
import com.yasinatagun.shoestore.view.fragment.InfotakingFragmentDirections;
import com.yasinatagun.shoestore.view.fragment.ListFragment;
import com.yasinatagun.shoestore.view.fragment.ListFragmentDirections;
import com.yasinatagun.shoestore.view.fragment.PaymentChoiceFragment;
import com.yasinatagun.shoestore.view.fragment.PaymentChoiceFragmentDirections;
import com.yasinatagun.shoestore.view.fragment.PaymentFragment;
import com.yasinatagun.shoestore.view.fragment.ShoeFragment;
import com.yasinatagun.shoestore.view.fragment.ShoeFragmentDirections;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    private ActivityMainBinding binding;
    FirebaseFirestore firebaseFirestore;
    public static ArrayList<Shoe> cartAddedList = new ArrayList<Shoe>();
    public static int totalAmount = 0;
    public static int totalItemCount = 0;
    public static MenuItem menuItem;
    AppBarConfiguration appBarConfiguration;
    CollectionReference collectionReference;
    ListenerRegistration listenerRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        firebaseFirestore = FirebaseFirestore.getInstance();
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        NavController navController = Navigation.findNavController(getSupportFragmentManager().getFragments().get(0).getView());
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        getDataFromFirestore();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(getSupportFragmentManager().getFragments().get(0).getView());
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    // burada yukarı toolbarın menülerinde yapılacak işlemleri belirliyorum
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.cartItem) {
            List<Fragment> fragments = getSupportFragmentManager().getFragments().get(0).getChildFragmentManager().getFragments();
            Fragment activeFragment = null;
            for (Fragment f : fragments) {
                if ((f != null) && f.isVisible()) {
                    activeFragment = f;
                    break;
                }
            }
            // Burada SEPET iconuna tıklanınca sepete gidilmesini sağlıyorum
            if (activeFragment instanceof ListFragment) {
                Navigation.findNavController(activeFragment.getView()).navigate((NavDirections) ListFragmentDirections.actionListFragmentToCartFragment());
            } else if (activeFragment instanceof ShoeFragment) {
                Navigation.findNavController(activeFragment.getView()).navigate((NavDirections) ShoeFragmentDirections.actionShoeFragmentToCartFragment());
            } else if (activeFragment instanceof PaymentChoiceFragment) {
                Navigation.findNavController(activeFragment.getView()).navigate((NavDirections) PaymentChoiceFragmentDirections.actionPaymentChoiceToCartFragment());
            } else if (activeFragment instanceof PaymentFragment) {
                Navigation.findNavController(activeFragment.getView()).navigate((NavDirections) PaymentChoiceFragmentDirections.actionPaymentChoiceToCartFragment());
            } else if (activeFragment instanceof InfotakingFragment) {
                Navigation.findNavController(activeFragment.getView()).navigate((NavDirections) InfotakingFragmentDirections.actionInfotakingFragmentToCartFragment());
            }
            // Burada logout iconuna tıklandığında ne olacağını belirliyorum
        } else if (item.getItemId() == R.id.logoutItem) {
            firebaseAuth.signOut();
            Toast.makeText(MainActivity.this, "LOGOUT SUCCESSFULLY", Toast.LENGTH_LONG).show();
            Intent intentLogout = new Intent(MainActivity.this, LoginActivity.class);
            cartAddedList.clear();
            // Her seferinde db den veri yeniden çekileceği için burada total countları sıfırlıyorum.
            totalAmount = 0;
            totalItemCount = 0;
            listenerRegistration.remove();
            startActivity(intentLogout);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    // Burada firestore'dan verileri çekiyorum
    public void getDataFromFirestore() {
        String userEmail = getSharedPreferences("default", MODE_PRIVATE).getString("userEmail", "null");
        collectionReference = firebaseFirestore.collection("users").document(userEmail).collection("cart");
        listenerRegistration = collectionReference.addSnapshotListener(new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(MainActivity.this, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }

                if (value != null) {
                    System.out.println(value.getDocuments().size());
                    for (DocumentChange snapshot : value.getDocumentChanges()) {
                        if (snapshot.getType() == DocumentChange.Type.ADDED) {
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
                            Shoe shoe = new Shoe(productName, productType, productPrice, productDescription, productStock, productDeliveryTime, productGender, productDownloadUrl, documentId);
                            MainActivity.totalAmount += shoe.getProductPrice();
                            MainActivity.totalItemCount += 1;

                            //*******
                            LayerDrawable icon = (LayerDrawable) menuItem.getIcon();

                            BadgeDrawable badge;

                            // Reuse drawable if possible
                            Drawable reuse = icon.findDrawableByLayerId(R.id.ic_group_count);
                            if (reuse != null && reuse instanceof BadgeDrawable) {
                                badge = (BadgeDrawable) reuse;
                            } else {
                                badge = new BadgeDrawable(MainActivity.this);
                            }
                            badge.setCount(String.valueOf(totalItemCount));
                            icon.mutate();
                            icon.setDrawableByLayerId(R.id.ic_group_count, badge);
                            MainActivity.cartAddedList.add(shoe);
                            // Bu methodla aktif fragmentı buluyorum
                            List<Fragment> fragments = getSupportFragmentManager().getFragments().get(0).getChildFragmentManager().getFragments();
                            Fragment activeFragment = null;
                            for (Fragment f : fragments) {
                                if ((f != null) && f.isVisible()) {
                                    activeFragment = f;
                                    break;
                                }
                            }
                            // daha sonrasında işlemleri notify ederek listeyi yeniliyorum.
                            if (activeFragment instanceof CartFragment) {
                                ((CartFragment) activeFragment).adapter.notifyDataSetChanged();
                                ((CartFragment) activeFragment).binding.totalItemCountText.setText(String.valueOf(totalItemCount));
                                ((CartFragment) activeFragment).binding.totalAmountText.setText(String.valueOf(totalAmount));
                            }
                        } else if (snapshot.getType() == DocumentChange.Type.REMOVED) {
                            for (int i = 0; i < cartAddedList.size(); i++) {
                                if (cartAddedList.get(i).getDocumentId().equals(snapshot.getDocument().getId())) {
                                    MainActivity.totalAmount -= cartAddedList.get(i).getProductPrice();
                                    MainActivity.totalItemCount -= 1;
                                    //*******
                                    LayerDrawable icon = (LayerDrawable) menuItem.getIcon();
                                    BadgeDrawable badge;

                                    // Reuse drawable if possible
                                    Drawable reuse = icon.findDrawableByLayerId(R.id.ic_group_count);
                                    if (reuse != null && reuse instanceof BadgeDrawable) {
                                        badge = (BadgeDrawable) reuse;
                                    } else {
                                        badge = new BadgeDrawable(MainActivity.this);
                                    }

                                    badge.setCount(String.valueOf(totalItemCount));
                                    icon.mutate();
                                    icon.setDrawableByLayerId(R.id.ic_group_count, badge);
                                    cartAddedList.remove(i);
                                    // Bu methodla aktif fragmentı buluyorum
                                    List<Fragment> fragments = getSupportFragmentManager().getFragments().get(0).getChildFragmentManager().getFragments();
                                    Fragment activeFragment = null;
                                    for (Fragment f : fragments) {
                                        if ((f != null) && f.isVisible()) {
                                            activeFragment = f;
                                            break;
                                        }
                                    }
                                    // daha sonrasında işlemleri notify ederek listeyi yeniliyorum.
                                    if (activeFragment instanceof CartFragment) {
                                        ((CartFragment) activeFragment).adapter.notifyDataSetChanged();
                                        ((CartFragment) activeFragment).binding.totalItemCountText.setText(String.valueOf(totalItemCount));
                                        ((CartFragment) activeFragment).binding.totalAmountText.setText(String.valueOf(totalAmount));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MainActivity.menuItem = menu.findItem(R.id.cartItem);
        LayerDrawable icon = (LayerDrawable) menuItem.getIcon();

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_group_count);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(this);
        }

        badge.setCount(String.valueOf(totalItemCount));
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_group_count, badge);

        return super.onPrepareOptionsMenu(menu);
    }
}