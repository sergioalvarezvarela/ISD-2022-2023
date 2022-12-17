package es.udc.ws.app.client.service;

import es.udc.ws.util.configuration.ConfigurationParametersManager;

import java.lang.reflect.InvocationTargetException;

public class ClientEventServiceFactory {

    private final static String CLASS_NAME_PARAMETER
            = "ClientEventServiceFactory.className";
    private static Class<ClientEventService> serviceClass = null;

    private ClientEventServiceFactory() {
    }

    @SuppressWarnings("unchecked")
    private synchronized static Class<ClientEventService> getServiceClass() {

        if (serviceClass == null) {
            try {
                String serviceClassName = ConfigurationParametersManager
                        .getParameter(CLASS_NAME_PARAMETER);
                serviceClass = (Class<ClientEventService>) Class.forName(serviceClassName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return serviceClass;

    }

    public static ClientEventService getService() {

        try {
            return (ClientEventService) getServiceClass().getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }
}
