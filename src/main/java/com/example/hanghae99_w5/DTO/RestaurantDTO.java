package com.example.hanghae99_w5.DTO;

import com.example.hanghae99_w5.Entity.Restaurant;
import lombok.*;

@Getter
@Setter
public class RestaurantDTO {
    private long id;
    private String name;
    private int minOrderPrice;
    private int deliveryFee;
    public RestaurantDTO(){}
    public RestaurantDTO(Restaurant restaurant){
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.minOrderPrice = restaurant.getMinOrderPrice();
        this.deliveryFee = restaurant.getDeliveryFee();
    }
    public Restaurant createRestaurant(){
        Restaurant r = new Restaurant();
        r.setName(this.name);
        r.setMinOrderPrice(this.minOrderPrice);
        r.setDeliveryFee(this.deliveryFee);
        return  r;
    }
}
