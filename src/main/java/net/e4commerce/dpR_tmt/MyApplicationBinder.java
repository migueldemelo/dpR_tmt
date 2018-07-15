package net.e4commerce.dpR_tmt;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import net.e4commerce.dpR_tmt.dao.EmployeeDAO;
import net.e4commerce.dpR_tmt.dao.Store;

public class MyApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
    	bind(Store.class).to(Store.class);
    	bind(EmployeeDAO.class).to(EmployeeDAO.class);
    }
}
