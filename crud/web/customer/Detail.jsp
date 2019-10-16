<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Customer Detail</title>
            <link rel="stylesheet" type="text/css" href="/crud/faces/jsfcrud.css" />
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Customer Detail</h1>
            <h:form>
                <h:panelGrid columns="2">
                    <h:outputText value="Id:"/>
                    <h:outputText value="#{customer.customer.id}" title="Id" />
                    <h:outputText value="Name:"/>
                    <h:outputText value="#{customer.customer.name}" title="Name" />
                    <h:outputText value="Country:"/>
                    <h:outputText value="#{customer.customer.country}" title="Country" />
                    <h:outputText value="Balance:"/>
                    <h:outputText value="#{customer.customer.balance}" title="Balance" />
                </h:panelGrid>
                <br />
                <h:commandLink action="#{customer.destroy}" value="Destroy">
                    <f:param name="jsfcrud.currentCustomer" value="#{jsfcrud_class['model.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][customer.customer][customer.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <br />
                <h:commandLink action="#{customer.editSetup}" value="Edit">
                    <f:param name="jsfcrud.currentCustomer" value="#{jsfcrud_class['model.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][customer.customer][customer.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <h:commandLink action="#{customer.createSetup}" value="New Customer" />
                <br />
                <h:commandLink action="#{customer.listSetup}" value="Show All Customer Items"/>
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />
            </h:form>
        </body>
    </html>
</f:view>
