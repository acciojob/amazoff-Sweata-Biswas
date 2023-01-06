package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    public void addOrder(Order order){
        orderRepository.addOrderInDb(order);
    }
    public void addDeliveryPartner(String partnerId ){
        orderRepository.addDeliveryPartnerInDb(partnerId);
    }
    public void addOrderPartnerPair(String orderId, String partnerId){
        orderRepository.addOrderPartnerPairInDb(orderId,partnerId);
    }
    public Order getOrderById(String id){
      return  orderRepository.getOrderByIdFromDb(id);
    }
    public  DeliveryPartner getPartnerById(String Id){
        return orderRepository.getPartnerByIdFromDb(Id);
    }
    public int getOrderCountByPartnerId(String id){
        return orderRepository.getOrderCountByPartnerId(id);
    }
    public List<String> getOrdersByPartnerId(String id){
        return orderRepository.getOrdersByPartnerId(id);
    }
    public List<String> getAllOrders(){
        return orderRepository.getAllOrder();
    }
    public int getCountOfUnassignedOrders(){
        return orderRepository.getCountOfUnassignedOrders();
    }
    public void deleteOrderById(String id){
         orderRepository.deleteOrderById(id);
    }
    public void deletePartnerById(String id){
        orderRepository.deletePartnerById(id);
    }
    public String getLastDeliveryTimeByPartnerId(String id){
      return orderRepository.getLastDeliveryTimeByPartnerId(id);
    }
    public int getOrdersLeftAfterGivenTimeByPartnerId(String time, String id){
        return orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(time,id);
    }
}
