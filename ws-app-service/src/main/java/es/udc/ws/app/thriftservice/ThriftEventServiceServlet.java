package es.udc.ws.app.thriftservice;

import es.udc.ws.util.servlet.ThriftHttpServletTemplate;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;

public class ThriftEventServiceServlet extends ThriftHttpServletTemplate {

    public ThriftEventServiceServlet() {
        super(createProcessor(), createProtocolFactory());
    }

    private static TProcessor createProcessor() {
return null;
        /*return new ThriftEventService.Processor<ThriftEventService.Iface>(
                new ThriftEventServiceImpl());*/

    }

    private static TProtocolFactory createProtocolFactory() {
        return new TBinaryProtocol.Factory();
    }
}
