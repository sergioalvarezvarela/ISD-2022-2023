package Response;

import es.udc.ws.util.configuration.ConfigurationParametersManager;

public class SqlResponseDaoFactory {
    private final static String CLASS_NAME_PARAMETER = "SqlResponseDaoFactory.className";
    private static SqlResponseDao dao = null;
    private SqlResponseDaoFactory() {
    }
    @SuppressWarnings("rawtypes")
    private static SqlResponseDao getInstance() {
        try {
            String daoClassName = ConfigurationParametersManager
                    .getParameter(CLASS_NAME_PARAMETER);
            Class daoClass = Class.forName(daoClassName);
            return (SqlResponseDao) daoClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public synchronized static SqlResponseDao getDao() {

        if (dao == null) {
            dao = getInstance();
        }
        return dao;
    }
}
