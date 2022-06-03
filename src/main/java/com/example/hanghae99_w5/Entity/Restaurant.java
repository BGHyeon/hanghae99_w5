package com.example.hanghae99_w5.Entity;

import com.example.hanghae99_w5.Exceptions.IllegalDeliveryFeeException;
import com.example.hanghae99_w5.Exceptions.IllegalMenuException;
import com.example.hanghae99_w5.Exceptions.IllegalMinOrderPriceException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private int minOrderPrice;

    @Column
    private int deliveryFee;

    @OneToMany(targetEntity = Menu.class,fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Menu> menus = new ArrayList<>();

    public void addMenu(Menu menu){
        this.menus.add(menu);
    }
    public void totalPolicyTest() throws IllegalDeliveryFeeException, IllegalMenuException, IllegalMinOrderPriceException {
        menuPolicyTest();
        minOrderPricePolicyTest();
        deliveryFeePolicyTest();
    }
    public void menuPolicyTest() throws IllegalMenuException {
        if(menus.stream().filter(disctinctByKey(e->e.getName())).count() != menus.size())
            throw new IllegalMenuException("중복된 이름의 메뉴를 추가할 수 없습니다.");
    }
    public void minOrderPricePolicyTest() throws IllegalMinOrderPriceException {
        if(minOrderPrice < 1000 || minOrderPrice > 100000)
            throw new IllegalMinOrderPriceException("최소 주문가격은 1000 ~ 100,000원 사이의 금액만 입력 가능합니다.");
        if(minOrderPrice % 100 != 0)
            throw new IllegalMinOrderPriceException("최소 주문가격은 100원 단위로 입력 가능합니다.");
    }
    public void deliveryFeePolicyTest() throws IllegalDeliveryFeeException {
        if(deliveryFee < 0 || deliveryFee > 10000)
            throw new IllegalDeliveryFeeException("기본 배달비는 0 ~ 10,000원 사이의 금액만 입력 가능합니다.");
        if(deliveryFee % 500 != 0)
            throw new IllegalDeliveryFeeException("기본 배달비는 500원 단위로 입력 가능합니다.");
    }
    public static <T> Predicate<T> disctinctByKey(Function<? super T,Object> keyExtractor){
        Map<Object,Boolean> map = new HashMap<>();
        return t->map.putIfAbsent(keyExtractor.apply(t),Boolean.TRUE)==null;
    }

}
