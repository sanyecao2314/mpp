package com.citsamex.app.filter;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.citsamex.ws.Employee;

public class LogonSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        Employee emp = (Employee) event.getSession().getAttribute("passport");
        event.getSession().getServletContext().removeAttribute(emp.getId());
        //System.out.println("Remove the user:" + emp.getId());       
    }

}
