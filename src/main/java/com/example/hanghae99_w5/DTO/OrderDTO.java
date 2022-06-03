package com.example.hanghae99_w5.DTO;

import com.example.hanghae99_w5.Entity.Menu;
import com.example.hanghae99_w5.Entity.Orders;
import com.example.hanghae99_w5.Repository.MenuRepo;
import com.example.hanghae99_w5.Repository.RestaurantRepo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderDTO {
    private long id;
    private long restaurantId;

    private String restaurantName;
    private List<OrderMenuDTO> foods = new ArrayList<>();

    private int deliveryFee;
    private int totalPrice;
    public OrderDTO(Orders order){
        this.id = order.getId();
        order.getOrderMenus().keySet().stream().forEach(e->{
            foods.add(new OrderMenuDTO(e,order.getOrderMenus().get(e)));
        });
        this.restaurantId =order.getRestaurant().getId();
        this.restaurantName = order.getRestaurant().getName();
        this.deliveryFee = order.getDeliveryFee();
        this.totalPrice = order.getTotalPrice();
    }
    public Orders createOrder(RestaurantRepo restaurantRepo, MenuRepo menuRepo){
        Orders order = new Orders();
        order.setRestaurant(restaurantRepo.findById(this.restaurantId).get());
        Map<Menu,Integer> map = new HashMap<>();
        List<Long> ids = new ArrayList<>();
        foods.stream().forEach(e->ids.add(e.getId()));
        List<Menu> menus = menuRepo.getMenusByIdList(ids);
        for(Menu menu: menus){
            map.put(menu, foods.stream().filter(e->e.getId() == menu.getId()).findFirst().get().getQuantity());
        }
        order.setOrderMenus(map);
        return order;
    }
    public void addOrderMenuDTO(OrderMenuDTO dto){
        this.foods.add(dto);
    }
}
