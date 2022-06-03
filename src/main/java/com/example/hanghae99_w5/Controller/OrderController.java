package com.example.hanghae99_w5.Controller;

import com.example.hanghae99_w5.DTO.OrderDTO;
import com.example.hanghae99_w5.Entity.Orders;
import com.example.hanghae99_w5.Exceptions.IllegalOrderCountException;
import com.example.hanghae99_w5.Exceptions.IllegalOrderPriceException;
import com.example.hanghae99_w5.Service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;
    @PostMapping(value = "/order/request")
    public ResponseEntity saveOrder(@RequestBody OrderDTO orderDTO){

        Gson gson = new Gson();
        JsonObject object = new JsonObject();
        try {
            ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
            Orders order = service.save(orderDTO);
            return ResponseEntity.ok(writer.writeValueAsString(new OrderDTO(order)));
        } catch (IllegalOrderPriceException | IllegalOrderCountException e) {
            e.printStackTrace();
            object.addProperty("msg",e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            object.addProperty("msg",e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(object));
    }

    @GetMapping(value = "/orders")
    public List<OrderDTO> getOrders(){
        List<OrderDTO> ret = new ArrayList<>();
        service.getOrderRepo().findAll().stream().forEach(e->ret.add(new OrderDTO(e)));
        return ret;
    }
}
