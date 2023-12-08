package com.yasinatagun.shoestore.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.yasinatagun.shoestore.R;
import com.yasinatagun.shoestore.model.Shoe;
import com.yasinatagun.shoestore.view.MainActivity;

import java.util.ArrayList;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private ShoeAdapter.ItemClickListener mClickListener;
    Context context;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public CartAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cart_single_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int positionn = position;
        holder.tvProductName.setText(MainActivity.cartAddedList.get(position).getProductName().toUpperCase(Locale.ROOT));
        Picasso.get().load(MainActivity.cartAddedList.get(position).getDownloadUrl()).into(holder.tvProductPicture);
        holder.tvProductPrice.setText("$" + MainActivity.cartAddedList.get(position).getProductPrice());
        holder.tvProductType.setText((MainActivity.cartAddedList.get(position).getProductType().toString()));
        holder.deleteFromCart.setTag(position);
        holder.deleteFromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shoe shoe = MainActivity.cartAddedList.get(positionn);
                String userEmail = context.getSharedPreferences("default", MODE_PRIVATE).getString("userEmail", "null");
                firebaseFirestore.collection("users").document(userEmail).collection("cart").document(shoe.getDocumentId()).delete();

            }
        });
    }

    @Override
    public int getItemCount() {
        return MainActivity.cartAddedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvProductName;
        ImageView tvProductPicture;
        TextView tvProductPrice;
        TextView tvProductType;
        ImageView deleteFromCart;

        ViewHolder(View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.cartProductName);
            tvProductPicture = itemView.findViewById(R.id.cartProductImage);
            tvProductPrice = itemView.findViewById(R.id.cartProductPrice);
            tvProductType = itemView.findViewById(R.id.cartProductType);
            deleteFromCart = itemView.findViewById(R.id.deleteFromCardImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public void setClickListener(ShoeAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
