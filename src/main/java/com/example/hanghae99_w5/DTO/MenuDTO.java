package com.example.hanghae99_w5.DTO;

import com.example.hanghae99_w5.Entity.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class MenuDTO {
    private long id;
    private String name;
    private int price;
    public MenuDTO(){};
    public MenuDTO(Menu menu){
        this.id = menu.getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
    }
    public Menu createMenu(){
        Menu m = new Menu();
        m.setName(this.name);
        m.setPrice(this.price);
        return m;
    }
}
