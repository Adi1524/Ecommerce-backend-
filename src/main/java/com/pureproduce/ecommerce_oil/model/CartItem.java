package com.pureproduce.ecommerce_oil.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CartItem {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Cart cart;

    @ManyToOne
    private Product product;

    private String size;

    private Integer quantity;

    private Integer price;

    private Integer discountedPrice;

    private Long userId;

    public CartItem(Long id, Cart cart, Product product, String size, Integer quantity, Integer price, Long userId) {
        super();
        this.id = id;
        this.cart = cart;
        this.product = product;
        this.size = size;
        this.quantity = quantity;
        this.price = price;
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, product, size);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CartItem other = (CartItem) obj;
        return Objects.equals(id, other.id) && Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price)
                && Objects.equals(product, other.product) && Objects.equals(size, other.size);
    }

   
}
