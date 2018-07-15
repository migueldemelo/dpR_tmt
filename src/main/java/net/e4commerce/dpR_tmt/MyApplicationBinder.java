package net.e4commerce.dpR_tmt;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import net.e4commerce.dpR_tmt.dao.DepartmentDAO;
import net.e4commerce.dpR_tmt.dao.EmployeeDAO;
import net.e4commerce.dpR_tmt.dao.Store;

public class MyApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
    	bind(Store.class).to(Store.class).in(Singleton.class);;
    	bind(EmployeeDAO.class).to(EmployeeDAO.class);
    	bind(DepartmentDAO.class).to(DepartmentDAO.class);
    }
}
