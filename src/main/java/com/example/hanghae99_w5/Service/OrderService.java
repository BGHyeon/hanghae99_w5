package com.example.hanghae99_w5.Service;

import com.example.hanghae99_w5.DTO.OrderDTO;
import com.example.hanghae99_w5.Entity.Orders;
import com.example.hanghae99_w5.Exceptions.IllegalOrderCountException;
import com.example.hanghae99_w5.Exceptions.IllegalOrderPriceException;
import com.example.hanghae99_w5.Repository.MenuRepo;
import com.example.hanghae99_w5.Repository.OrderRepo;
import com.example.hanghae99_w5.Repository.RestaurantRepo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Getter
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;
    private final RestaurantRepo restaurantRepo;

    private final MenuRepo menuRepo;
    public Orders save(OrderDTO dto) throws IllegalOrderPriceException, IllegalOrderCountException {
        Orders order = dto.createOrder(restaurantRepo,menuRepo);
        order.totalPolicyTest();
        return orderRepo.save(order);
    }
}
