package com.godzynskyi.command.user;

import com.godzynskyi.annotation.RequestMapper;
import com.godzynskyi.controller.Command;
import com.godzynskyi.daoOld.car.filters.AutomatFilter;
import com.godzynskyi.daoOld.car.filters.CarFilter;
import com.godzynskyi.daoOld.car.filters.EngineFilter;
import com.godzynskyi.daoOld.car.filters.YearFilter;
import com.godzynskyi.entity.Car;
import com.godzynskyi.factory.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Java Developer on 04.12.2015.
 */
@RequestMapper("/catalog")
public class CommandCatalog implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<CarFilter> filterList = new LinkedList<>();

        //Add ENGINE FILTER
        String engineFrom = request.getParameter("enginefrom");
        if (engineFrom != null && !engineFrom.equals("")) {
            String engineTo = request.getParameter("engineto");
            double from = Double.parseDouble(engineFrom);
            double to = Double.parseDouble(engineTo);
            CarFilter filter = new EngineFilter(from, to);
            filterList.add(filter);
        }

        //Add YEAR FILTER
        String yearFrom = request.getParameter("yearfrom");
        if (yearFrom != null && !yearFrom.equals("")) {
            String yearTo = request.getParameter("yearto");
            int from = Integer.parseInt(yearFrom);
            int to = Integer.parseInt(yearTo);
            CarFilter filter = new YearFilter(from, to);
            filterList.add(filter);
        }

        //Add AUTOMAT FILTER
        String automat = request.getParameter("automat");
        if (automat != null && !automat.equals("")) {
            CarFilter filter = null;
            if (automat.equals("0")) filter = new AutomatFilter(Car.Transmission.MANUAL);
            if (automat.equals("1")) filter = new AutomatFilter(Car.Transmission.AUTOMAT);
            if (filter != null) filterList.add(filter);
        }

        List<Car> cars = DAOFactory.carDAO().findCars(filterList);
        request.setAttribute("cars", cars);

        return "user/catalog";
    }




}

