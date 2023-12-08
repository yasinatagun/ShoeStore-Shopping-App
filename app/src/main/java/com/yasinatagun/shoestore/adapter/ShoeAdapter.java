package com.yasinatagun.shoestore.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.yasinatagun.shoestore.R;
import com.yasinatagun.shoestore.model.Shoe;
import com.yasinatagun.shoestore.view.MainActivity;
import com.yasinatagun.shoestore.view.fragment.ListFragmentDirections;

import java.util.ArrayList;
import java.util.Locale;

public class ShoeAdapter extends RecyclerView.Adapter<ShoeAdapter.ViewHolder> {
    private ArrayList<Shoe> shoeArrayList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    Context context;
    public ShoeAdapter(Context context, ArrayList<Shoe> data) {
        this.mInflater = LayoutInflater.from(context);
        this.shoeArrayList = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.single_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int positionn = position;
        holder.tvProductName.setText(shoeArrayList.get(position).getProductName().toUpperCase(Locale.ROOT));
        Picasso.get().load(shoeArrayList.get(position).getDownloadUrl()).into(holder.tvProductPicture);
        holder.tvProductPrice.setText(String.valueOf("$" + shoeArrayList.get(position).getProductPrice()));
        holder.tvProductType.setText(String.valueOf(shoeArrayList.get(position).getProductType().toString()));
        holder.imAddToCart.setTag(position);
        holder.imAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shoe shoe = shoeArrayList.get(positionn);
                String userEmail = context.getSharedPreferences("default", MODE_PRIVATE).getString("userEmail", "null");

                firebaseFirestore.collection("users").document(userEmail).collection("cart").add(shoe);

            }
        });
    }

    @Override
    public int getItemCount() {
        return shoeArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvProductName;
        ImageView tvProductPicture;
        TextView tvProductPrice;
        TextView tvProductType;
        ImageView imAddToCart;

        ViewHolder(View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.idText);
            tvProductPicture = itemView.findViewById(R.id.idImage);
            tvProductPrice = itemView.findViewById(R.id.idPrice);
            tvProductType = itemView.findViewById(R.id.idType);
            imAddToCart = itemView.findViewById(R.id.addToCartIcon);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
