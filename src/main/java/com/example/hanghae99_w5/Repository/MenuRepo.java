package com.example.hanghae99_w5.Repository;

import com.example.hanghae99_w5.Entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepo extends JpaRepository<Menu,Long> {
    @Query("Select this_.menus from Restaurant this_ where this_.id=:id")
    public List<Menu> getMenuByRestaurantId(@Param("id") long id);

    @Query("Select this_ from Menu this_ where this_.id in (:ids)")
    public List<Menu> getMenusByIdList(@Param("ids")List<Long> ids);
}
