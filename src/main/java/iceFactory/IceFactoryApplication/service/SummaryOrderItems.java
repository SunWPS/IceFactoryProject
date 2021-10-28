package iceFactory.IceFactoryApplication.service;

import iceFactory.IceFactoryApplication.model.CustomerOrder;
import iceFactory.IceFactoryApplication.model.OrderItem;

import java.util.ArrayList;
import java.util.List;


public class SummaryOrderItems {

    public static List<OrderItem> summaryByTypeAndDate(List<CustomerOrder> orderList, String type, String date){
        List<CustomerOrder> filterOrder = new ArrayList<>();
        for(CustomerOrder order : orderList){
            if(order.getCustomer().getType().equals(type) && order.getOrderDate().substring(0, 10).equals(date)){
                filterOrder.add(order);
            }
        }

        List<OrderItem> filterItems = new ArrayList<>();
        for(CustomerOrder order : filterOrder){
            List<OrderItem> orderItems = order.getOrderItemList();
            for(OrderItem orderItem : orderItems){
                boolean check = true;
                for(OrderItem filterItem : filterItems){
                    if(orderItem.getPName().equals(filterItem.getPName())){
                        filterItem.setOrderQuantity(orderItem.getOrderQuantity() + filterItem.getOrderQuantity());
                        check = false;
                        break;
                    }
                }
                if(check) {
                    OrderItem newItem = new OrderItem();
                    newItem.setProduct(orderItem.getProduct());
                    newItem.setOrderQuantity(orderItem.getOrderQuantity());
                    newItem.increasePrice(orderItem.getPrice());
                    filterItems.add(newItem);
                }
            }
        }
        return filterItems;
    }

    public static List<OrderItem> summaryByDate(List<CustomerOrder> orderList, String date){
        List<CustomerOrder> filterOrder = new ArrayList<>();
        for(CustomerOrder order : orderList){
            if(order.getOrderDate().substring(0, 10).equals(date)){
                filterOrder.add(order);
            }
        }

        List<OrderItem> filterItems = new ArrayList<>();
        for(CustomerOrder order : filterOrder){
            List<OrderItem> orderItems = order.getOrderItemList();
            for(OrderItem orderItem : orderItems){
                boolean check = true;
                for(OrderItem filterItem : filterItems){
                    if(orderItem.getPName().equals(filterItem.getPName())){
                        filterItem.setOrderQuantity(orderItem.getOrderQuantity() + filterItem.getOrderQuantity());
                        filterItem.increasePrice(orderItem.getSumPrice());
                        check = false;
                        break;
                    }
                }
                if(check) {
                    OrderItem newItem = new OrderItem();
                    newItem.setProduct(orderItem.getProduct());
                    newItem.setOrderQuantity(orderItem.getOrderQuantity());
                    newItem.increasePrice(orderItem.getSumPrice());
                    filterItems.add(newItem);
                }
            }
        }
        return filterItems;
    }

    public static float totalPrice(List<OrderItem> orderItems){
        float total = 0;
        for(OrderItem item : orderItems)
            total += item.getSumPrice();
        return total;
    }

    public static float totalSummaryPrice(List<OrderItem> orderItems){
        float total = 0;
        for(OrderItem item: orderItems)
            total += item.getPrice();
        return total;
    }

}
