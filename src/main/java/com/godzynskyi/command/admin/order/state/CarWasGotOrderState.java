package com.godzynskyi.command.admin.order.state;

import com.godzynskyi.model.Order;
import com.godzynskyi.dao.DAOFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CarWasGotOrderState implements OrderState {

    @Override
    public String changeState(HttpServletRequest request, HttpServletResponse response) {

        Order order = (Order) request.getAttribute("order");
        List<String> defectList = DAOFactory.defectDAO().getDescriptionsOfNotRepairedDefectsOfCar(order.getCar().getId());

        request.setAttribute("defectList", defectList);

        return "admin/return_car";

    }
}
