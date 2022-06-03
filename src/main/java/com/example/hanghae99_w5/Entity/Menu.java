package com.example.hanghae99_w5.Entity;

import com.example.hanghae99_w5.Exceptions.IllegalMenuException;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Menu extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private int price;

    public void priceTest() throws IllegalMenuException {
        if(price < 100 || price > 1000000)
            throw new IllegalMenuException("메뉴 가격은 100 ~ 1,000,000원 사이로 입력 가능합니다.");
        if(price % 100 != 0)
            throw new IllegalMenuException("메뉴 가격은 100원 단위로 입력 가능합니다.");
    }

}
