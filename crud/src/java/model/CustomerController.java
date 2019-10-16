/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import controller.CustomerJpaController;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import model.util.JsfUtil;
import controller.exceptions.NonexistentEntityException;
import model.util.PagingInfo;

/**
 *
 * @author Lei
 */
public class CustomerController {

    public CustomerController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (CustomerJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "customerJpa");
        pagingInfo = new PagingInfo();
        converter = new CustomerConverter();
    }
    private Customer customer = null;
    private List<Customer> customerItems = null;
    private CustomerJpaController jpaController = null;
    private CustomerConverter converter = null;
    private PagingInfo pagingInfo = null;

    public PagingInfo getPagingInfo() {
        if (pagingInfo.getItemCount() == -1) {
            pagingInfo.setItemCount(jpaController.getCustomerCount());
        }
        return pagingInfo;
    }

    public SelectItem[] getCustomerItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findCustomerEntities(), false);
    }

    public SelectItem[] getCustomerItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findCustomerEntities(), true);
    }

    public Customer getCustomer() {
        if (customer == null) {
            customer = (Customer) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentCustomer", converter, null);
        }
        if (customer == null) {
            customer = new Customer();
        }
        return customer;
    }

    public String listSetup() {
        reset(true);
        return "customer_list";
    }

    public String createSetup() {
        reset(false);
        customer = new Customer();
        return "customer_create";
    }

    public String create() {
        try {
            jpaController.create(customer);
            JsfUtil.addSuccessMessage("Customer was successfully created.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return listSetup();
    }

    public String detailSetup() {
        return scalarSetup("customer_detail");
    }

    public String editSetup() {
        return scalarSetup("customer_edit");
    }

    private String scalarSetup(String destination) {
        reset(false);
        customer = (Customer) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentCustomer", converter, null);
        if (customer == null) {
            String requestCustomerString = JsfUtil.getRequestParameter("jsfcrud.currentCustomer");
            JsfUtil.addErrorMessage("The customer with id " + requestCustomerString + " no longer exists.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
        String customerString = converter.getAsString(FacesContext.getCurrentInstance(), null, customer);
        String currentCustomerString = JsfUtil.getRequestParameter("jsfcrud.currentCustomer");
        if (customerString == null || customerString.length() == 0 || !customerString.equals(currentCustomerString)) {
            String outcome = editSetup();
            if ("customer_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Could not edit customer. Try again.");
            }
            return outcome;
        }
        try {
            jpaController.edit(customer);
            JsfUtil.addSuccessMessage("Customer was successfully updated.");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return listSetup();
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return detailSetup();
    }

    public String destroy() {
        String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentCustomer");
        Long id = new Long(idAsString);
        try {
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("Customer was successfully deleted.");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return relatedOrListOutcome();
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return relatedOrListOutcome();
    }

    private String relatedOrListOutcome() {
        String relatedControllerOutcome = relatedControllerOutcome();
        if (relatedControllerOutcome != null) {
            return relatedControllerOutcome;
        }
        return listSetup();
    }

    public List<Customer> getCustomerItems() {
        if (customerItems == null) {
            getPagingInfo();
            customerItems = jpaController.findCustomerEntities(pagingInfo.getBatchSize(), pagingInfo.getFirstItem());
        }
        return customerItems;
    }

    public String next() {
        reset(false);
        getPagingInfo().nextPage();
        return "customer_list";
    }

    public String prev() {
        reset(false);
        getPagingInfo().previousPage();
        return "customer_list";
    }

    private String relatedControllerOutcome() {
        String relatedControllerString = JsfUtil.getRequestParameter("jsfcrud.relatedController");
        String relatedControllerTypeString = JsfUtil.getRequestParameter("jsfcrud.relatedControllerType");
        if (relatedControllerString != null && relatedControllerTypeString != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            Object relatedController = context.getApplication().getELResolver().getValue(context.getELContext(), null, relatedControllerString);
            try {
                Class<?> relatedControllerType = Class.forName(relatedControllerTypeString);
                Method detailSetupMethod = relatedControllerType.getMethod("detailSetup");
                return (String) detailSetupMethod.invoke(relatedController);
            } catch (ClassNotFoundException e) {
                throw new FacesException(e);
            } catch (NoSuchMethodException e) {
                throw new FacesException(e);
            } catch (IllegalAccessException e) {
                throw new FacesException(e);
            } catch (InvocationTargetException e) {
                throw new FacesException(e);
            }
        }
        return null;
    }

    private void reset(boolean resetFirstItem) {
        customer = null;
        customerItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        Customer newCustomer = new Customer();
        String newCustomerString = converter.getAsString(FacesContext.getCurrentInstance(), null, newCustomer);
        String customerString = converter.getAsString(FacesContext.getCurrentInstance(), null, customer);
        if (!newCustomerString.equals(customerString)) {
            createSetup();
        }
    }

    public Converter getConverter() {
        return converter;
    }

}
