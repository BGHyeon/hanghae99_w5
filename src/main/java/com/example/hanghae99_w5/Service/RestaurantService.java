package com.example.hanghae99_w5.Service;

import com.example.hanghae99_w5.DTO.MenuDTO;
import com.example.hanghae99_w5.Entity.Menu;
import com.example.hanghae99_w5.Entity.Restaurant;
import com.example.hanghae99_w5.Exceptions.IllegalDeliveryFeeException;
import com.example.hanghae99_w5.Exceptions.IllegalMenuException;
import com.example.hanghae99_w5.Exceptions.IllegalMinOrderPriceException;
import com.example.hanghae99_w5.Repository.MenuRepo;
import com.example.hanghae99_w5.Repository.RestaurantRepo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Getter
@RequiredArgsConstructor
public class RestaurantService {
    private final MenuRepo menuRepo;
    private final RestaurantRepo restaurantRepo;

    public Restaurant save(Restaurant restaurant) throws IllegalMenuException, IllegalDeliveryFeeException, IllegalMinOrderPriceException {
        restaurant.totalPolicyTest();
        return restaurantRepo.save(restaurant);
    }
    public Restaurant save(long restaurantId, List<Menu> menus) throws IllegalMenuException {
        Restaurant restaurant = restaurantRepo.findById(restaurantId).get();
        for(Menu m : menus){
            m.priceTest();
            restaurant.addMenu(m);
        }
        restaurant.menuPolicyTest();
        return restaurantRepo.save(restaurant);
    }
    public List<MenuDTO> getMenuDTO(long restaurantId){
        List<Menu> m = menuRepo.getMenuByRestaurantId(restaurantId);
        List<MenuDTO> dtos = new ArrayList<>();
        m.stream().forEach(e->dtos.add(new MenuDTO(e)));
        return dtos;
    }

}
