package ru.example.dishhunt.ui.recipe_creator.view_holders;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import ru.example.dishhunt.R;
import ru.example.dishhunt.data.data_sources.room.entites.ProductEntity;
import ru.example.dishhunt.ui.recipe_creator.ProductClickInterface;

public class SearchProductsViewHolder extends RecyclerView.ViewHolder {

    private TextView ProductViewName;
    private ProductClickInterface productClickInterface;
    private String mode;

    private SearchProductsViewHolder(@NonNull View itemView, ProductClickInterface productClickInterface, String m) {
        super(itemView);
        ProductViewName = itemView.findViewById(R.id.product_name);
        this.productClickInterface = productClickInterface;
        mode = m;
    }


    @SuppressLint("SetTextI18n")
    public void bind(ProductEntity product) {
        if ("add".equals(mode)){
            ProductViewName.setBackgroundResource(R.drawable.rounded_corner_positive_10);
        }
        else{
            ProductViewName.setBackgroundResource(R.drawable.rounded_corner_negative_10);
        }
        itemView.setOnClickListener(v -> {
            if (productClickInterface != null){
                productClickInterface.onProductClick(product);
            }
        });
        ProductViewName.setText(product.getName());
    }

    public static SearchProductsViewHolder create(ViewGroup parent, ProductClickInterface productClickInterface, String m) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product, parent, false);
        return new SearchProductsViewHolder(view, productClickInterface, m);
    }
}

