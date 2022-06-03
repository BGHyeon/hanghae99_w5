package com.example.hanghae99_w5.Controller;

import com.example.hanghae99_w5.DTO.MenuDTO;
import com.example.hanghae99_w5.Entity.Menu;
import com.example.hanghae99_w5.Entity.Restaurant;
import com.example.hanghae99_w5.Exceptions.IllegalDeliveryFeeException;
import com.example.hanghae99_w5.Exceptions.IllegalMenuException;
import com.example.hanghae99_w5.Exceptions.IllegalMinOrderPriceException;
import com.example.hanghae99_w5.DTO.RestaurantDTO;
import com.example.hanghae99_w5.Service.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService service;

    @GetMapping(value = "/restaurants")
    public List<RestaurantDTO> getAllRestuarant(){
        List<RestaurantDTO> ret = new ArrayList<>();
        service.getRestaurantRepo().findAll().stream().forEach(e->ret.add(new RestaurantDTO(e)));
        return ret;
    }

    @PostMapping(value = "/restaurant/register")
    public ResponseEntity saveRestaurant(@RequestBody RestaurantDTO restaurant) {
        Gson gson = new Gson();
        JsonObject object = new JsonObject();
        try {
            RestaurantDTO dto = new RestaurantDTO(service.save(restaurant.createRestaurant()));
            ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
            return ResponseEntity.ok(writer.writeValueAsString(dto));
        } catch (IllegalMenuException | IllegalDeliveryFeeException | IllegalMinOrderPriceException e ) {
            object.addProperty("msg",e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            object.addProperty("msg",e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(object));
    }
    @PostMapping(value = "/restaurant/{restaurantId}/food/register")
    public ResponseEntity setMenus(@RequestBody List<MenuDTO> menu, @PathVariable("restaurantId") String restaurantid){
        Gson gson = new Gson();
        JsonObject object = new JsonObject();
        System.out.println(menu.get(0).getName());
        try {
            List<Menu> m = new ArrayList<>();
            menu.stream().forEach(e->m.add(e.createMenu()));
            Restaurant res = service.save(Long.parseLong(restaurantid),m);
            List<MenuDTO> dtos = new ArrayList<>();
            res.getMenus().stream().forEach(e->dtos.add(new MenuDTO(e)));
            return ResponseEntity.ok(null);
        } catch (IllegalMenuException e) {
            object.addProperty("msg",e.getMessage());
        } catch(Exception e){
            e.printStackTrace();
            object.addProperty("msg",e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(object));
    }
    @GetMapping(value = "/restaurant/{restaurantId}/foods")
    public List<MenuDTO> getMenus(@PathVariable("restaurantId")String id){
        return service.getMenuDTO(Long.parseLong(id));
    }

}
