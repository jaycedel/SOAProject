<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Listing Customer Items</title>
            <link rel="stylesheet" type="text/css" href="/crud/faces/jsfcrud.css" />
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Listing Customer Items</h1>
            <h:form styleClass="jsfcrud_list_form">
                <h:outputText escape="false" value="(No Customer Items Found)<br />" rendered="#{customer.pagingInfo.itemCount == 0}" />
                <h:panelGroup rendered="#{customer.pagingInfo.itemCount > 0}">
                    <h:outputText value="Item #{customer.pagingInfo.firstItem + 1}..#{customer.pagingInfo.lastItem} of #{customer.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{customer.prev}" value="Previous #{customer.pagingInfo.batchSize}" rendered="#{customer.pagingInfo.firstItem >= customer.pagingInfo.batchSize}"/>&nbsp;
                    <h:commandLink action="#{customer.next}" value="Next #{customer.pagingInfo.batchSize}" rendered="#{customer.pagingInfo.lastItem + customer.pagingInfo.batchSize <= customer.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{customer.next}" value="Remaining #{customer.pagingInfo.itemCount - customer.pagingInfo.lastItem}"
                                   rendered="#{customer.pagingInfo.lastItem < customer.pagingInfo.itemCount && customer.pagingInfo.lastItem + customer.pagingInfo.batchSize > customer.pagingInfo.itemCount}"/>
                    <h:dataTable value="#{customer.customerItems}" var="item" border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px">
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Id"/>
                            </f:facet>
                            <h:outputText value=" #{item.id}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Name"/>
                            </f:facet>
                            <h:outputText value=" #{item.name}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Country"/>
                            </f:facet>
                            <h:outputText value=" #{item.country}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Balance"/>
                            </f:facet>
                            <h:outputText value=" #{item.balance}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText escape="false" value="&nbsp;"/>
                            </f:facet>
                            <h:commandLink value="Show" action="#{customer.detailSetup}">
                                <f:param name="jsfcrud.currentCustomer" value="#{jsfcrud_class['model.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][customer.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{customer.editSetup}">
                                <f:param name="jsfcrud.currentCustomer" value="#{jsfcrud_class['model.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][customer.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{customer.destroy}">
                                <f:param name="jsfcrud.currentCustomer" value="#{jsfcrud_class['model.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][customer.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                        </h:column>
                    </h:dataTable>
                </h:panelGroup>
                <br />
                <h:commandLink action="#{customer.createSetup}" value="New Customer"/>
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />
                
            </h:form>
        </body>
    </html>
</f:view>
