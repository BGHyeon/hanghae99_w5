package com.example.hanghae99_w5.Entity;

import com.example.hanghae99_w5.Exceptions.IllegalOrderCountException;
import com.example.hanghae99_w5.Exceptions.IllegalOrderPriceException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@Entity
public class Orders extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = Restaurant.class,fetch = FetchType.LAZY)
    @JoinColumn
    private Restaurant restaurant;

    @ElementCollection
    @CollectionTable(name="order_menus",joinColumns = {@JoinColumn(name = "order_id",referencedColumnName = "id")})
    @MapKeyColumn(name="menu_id")
    @Column(name ="count")
    private Map<Menu,Integer> orderMenus = new HashMap<>();

    public void OrderMenu(Menu menu, int count){
        this.orderMenus.put(menu,orderMenus.getOrDefault(menu,0)+count);
    }
    public void totalPricePolicyTest() throws IllegalOrderPriceException {
        if(getTotalOrderPrice() < restaurant.getMinOrderPrice())
            throw new IllegalOrderPriceException("주문가격이 음식점 최소 주문가격을 넘지 않았습니다.");
    }
    public void orderMenuCountPolicyTest() throws IllegalOrderCountException {
        if(orderMenus.values().stream().filter(e-> e <= 0).count() > 0 ||orderMenus.keySet().stream().count() == 0)
            throw new IllegalOrderCountException("최소 1개 이상의 음식을 입력해 주세요.");
        if(orderMenus.values().stream().filter(e-> e> 100).count() > 0)
            throw new IllegalOrderCountException("최대 주문 개수는 메뉴당 100개입니다.");
    }
    public void totalPolicyTest() throws IllegalOrderPriceException, IllegalOrderCountException {
        totalPricePolicyTest();;
        orderMenuCountPolicyTest();
    }
    public int getDeliveryFee(){
        return this.restaurant.getDeliveryFee();
    }
    public int getTotalOrderPrice(){
        AtomicInteger price = new AtomicInteger(0);
        orderMenus.keySet().stream().forEach(e-> price.addAndGet((e.getPrice() * orderMenus.get(e))));
        return price.get();
    }
    public int getTotalPrice() {
        AtomicInteger price = new AtomicInteger(getDeliveryFee());
        orderMenus.keySet().stream().forEach(e-> price.addAndGet((e.getPrice() * orderMenus.get(e))));
        return price.get();
    }
}
