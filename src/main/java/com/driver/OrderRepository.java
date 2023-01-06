package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository {
    HashMap<String ,Order> orderHashMap = new HashMap<>();
    HashMap<String,DeliveryPartner> deliveryPartnerHashMap = new HashMap<>();
    HashMap<String, List<String>> orderPartnerPairHashMap = new HashMap<>();
    public void addOrderInDb(Order order){
        orderHashMap.put(order.getId(), order);
    }
    public void addDeliveryPartnerInDb(String partnerId){
        DeliveryPartner partner = new DeliveryPartner(partnerId);
        deliveryPartnerHashMap.put(partnerId,partner);
    }
    public void addOrderPartnerPairInDb(String orderId ,String partnerId){

        if(orderHashMap.containsKey(orderId) && deliveryPartnerHashMap.containsKey(partnerId)){
            if(orderPartnerPairHashMap.containsKey(partnerId)){
                orderPartnerPairHashMap.get(partnerId).add(orderId);
            }else {
                List<String> orderList = new ArrayList<>();
                orderList.add(orderId);
                orderPartnerPairHashMap.put(partnerId,orderList);
            }

            int numberOfOrder =deliveryPartnerHashMap.get(partnerId).getNumberOfOrders();
            deliveryPartnerHashMap.get(partnerId).setNumberOfOrders(numberOfOrder + 1);
        }
    }
    public Order getOrderByIdFromDb(String id){
        if(orderHashMap.containsKey(id)){
            return orderHashMap.get(id);
        }
        return null;
    }
    public  DeliveryPartner getPartnerByIdFromDb (String id){
        if(deliveryPartnerHashMap.containsKey(id)){
            return deliveryPartnerHashMap.get(id);
        }
        return null;
    }
    public int getOrderCountByPartnerId(String id){
        if(deliveryPartnerHashMap.containsKey(id)){
            return deliveryPartnerHashMap.get(id).getNumberOfOrders();
        }
        return 0;
    }
    public List<String> getOrdersByPartnerId(String id){
        if(orderPartnerPairHashMap.containsKey(id)){
            return orderPartnerPairHashMap.get(id);
        }return null;
    }
    public List<String> getAllOrder(){
        List<String> orderList = new ArrayList<>();
        for(Order order: orderHashMap.values()){
            orderList.add(order.getId());
        }
        return orderList;
    }
    public int getCountOfUnassignedOrders(){
        int assignedOrder = 0;
        for(DeliveryPartner deliveryPartner: deliveryPartnerHashMap.values()){
            assignedOrder+= deliveryPartner.getNumberOfOrders();
        }
        return orderHashMap.size() - assignedOrder;
    }
    public void deleteOrderById(String id){
        if(orderHashMap.containsKey(id)){

            for(Map.Entry<String, List<String>> m : orderPartnerPairHashMap.entrySet() ){
                if (m.getValue().contains(id)) {
                    int numberOfOrder =deliveryPartnerHashMap.get(m.getKey()).getNumberOfOrders();
                    deliveryPartnerHashMap.get(m.getKey()).setNumberOfOrders(numberOfOrder-1);
                    m.getValue().remove(id);
                }

            }

            orderHashMap.remove(id);
        }
    }
    public void deletePartnerById(String id){
        if(deliveryPartnerHashMap.containsKey(id)){
            if(orderPartnerPairHashMap.containsKey(id)){
                orderPartnerPairHashMap.remove(id);
            }
            deliveryPartnerHashMap.remove(id);
        }
    }
    public String getLastDeliveryTimeByPartnerId(String id){
       int maxTime = 0;
       if(deliveryPartnerHashMap.containsKey(id) && orderPartnerPairHashMap.containsKey(id)){
           List<String> orderIds = orderPartnerPairHashMap.get(id);
           for (String orderId : orderIds) {
               maxTime = Math.max(maxTime, orderHashMap.get(orderId).getDeliveryTime());
           }
       }

       int hh = maxTime/60;
       int mm = maxTime%60;
       String time = "";
       if(hh!=0) {
           if (hh < 10) {
               time += "0" + hh;
           } else {
               time += hh;
           }
       }else{
           time+="00";
       }
       time+=":";
        if(mm!=0) {
            if (mm < 10) {
                time += "0" + mm;
            } else {
                time += mm;
            }
        }else{
            time+="00";
        }
       return time;
    }
    public int getOrdersLeftAfterGivenTimeByPartnerId(String time, String id){
        int count =0;
        int givenTime =Integer.parseInt(time.substring(0,2))*60 + Integer.parseInt(time.substring(3,5));

        if(deliveryPartnerHashMap.containsKey(id) && orderPartnerPairHashMap.containsKey(id)){
            List<String> orderIds = orderPartnerPairHashMap.get(id);
            for (String orderId : orderIds) {
           if( orderHashMap.get(orderId).getDeliveryTime()>givenTime){
               count++;
           }
            }
        }
        return count;
    }
}
