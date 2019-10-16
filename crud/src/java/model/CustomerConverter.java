/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import controller.CustomerJpaController;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author Lei
 */
public class CustomerConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        Long id = new Long(string);
        CustomerJpaController controller = (CustomerJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "customerJpa");
        return controller.findCustomer(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Customer) {
            Customer o = (Customer) object;
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: model.Customer");
        }
    }

}
