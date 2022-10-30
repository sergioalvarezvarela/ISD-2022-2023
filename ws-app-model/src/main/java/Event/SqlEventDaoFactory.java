package Event;

import es.udc.ws.util.configuration.ConfigurationParametersManager;

public class SqlEventDaoFactory {
    private final static String CLASS_NAME_PARAMETER = "SqlEventDaoFactory.className";
    private static SqlEventDao dao = null;

    private SqlEventDaoFactory() {
    }

    @SuppressWarnings("rawtypes")
    private static SqlEventDao getInstance() {
        try {
            String daoClassName = ConfigurationParametersManager
                    .getParameter(CLASS_NAME_PARAMETER);
            Class daoClass = Class.forName(daoClassName);
            return (SqlEventDao) daoClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public synchronized static SqlEventDao getDao() {

        if (dao == null) {
            dao = getInstance();
        }
        return dao;

    }

}




