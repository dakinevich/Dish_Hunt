package ru.example.dishhunt.ui.ViewHolders;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.example.dishhunt.R;
import ru.example.dishhunt.data.data_sources.room.entites.ProductEntity;
import ru.example.dishhunt.ui.recipe_creator.ProductClickInterface;

public class SearchProductsViewHolder extends RecyclerView.ViewHolder {

    private TextView ProductViewName;
    private ProductClickInterface productClickInterface;


    private SearchProductsViewHolder(@NonNull View itemView, ProductClickInterface productClickInterface) {
        super(itemView);
        ProductViewName =itemView.findViewById(R.id.product_name);
        this.productClickInterface = productClickInterface;
    }


    @SuppressLint("SetTextI18n")
    public void bind(ProductEntity product) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productClickInterface != null){
                    productClickInterface.onClick(product);
                }
            }
        });
        ProductViewName.setText(product.getName());
    }

    public static SearchProductsViewHolder create(ViewGroup parent, ProductClickInterface productClickInterface) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product, parent, false);
        return new SearchProductsViewHolder(view, productClickInterface);
    }
}

