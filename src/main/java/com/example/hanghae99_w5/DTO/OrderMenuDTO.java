package com.example.hanghae99_w5.DTO;

import com.example.hanghae99_w5.Entity.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class OrderMenuDTO {
    private long id;
    private int quantity;
    private String name;
    private int price;
    public OrderMenuDTO(Menu menu,int quantity){
        this.id = menu.getId();
        this.name = menu.getName();
        this.quantity =quantity;
        this.price = quantity * menu.getPrice();
    }

}
