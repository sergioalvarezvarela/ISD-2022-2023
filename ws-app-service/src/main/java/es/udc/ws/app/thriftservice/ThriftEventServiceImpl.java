package es.udc.ws.app.thriftservice;

import EventService.EventServiceFactory;
import EventService.Exception.AlreadyCanceledException;
import EventService.Exception.OutOfTimeException;
import Response.Response;
import es.udc.ws.app.thrift.*;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.util.List;

public class ThriftEventServiceImpl implements ThriftEventService.Iface {

    @Override
    public void CancelEvent(long eventId) throws ThriftInputValidationException, ThriftInstanceNotFoundException, ThriftOutOfTimeException, ThriftAlreadyCanceledException {
        try{
            EventServiceFactory.getService().CancelEvent(eventId);
        } catch (InstanceNotFoundException e) {
            throw new ThriftInstanceNotFoundException(e.getInstanceId().toString(),
                    e.getInstanceType().substring(e.getInstanceType().lastIndexOf('.') + 1));
        } catch (OutOfTimeException e) {
            throw new ThriftOutOfTimeException(e.getId());
        } catch (InputValidationException e) {
            throw new ThriftInputValidationException(e.getMessage());
        } catch (AlreadyCanceledException e) {
            throw new ThriftAlreadyCanceledException(e.getEventId());
        }

    }

    @Override
    public List<ThriftResponseDto> findResponsebyEmail(String email, boolean asist) throws ThriftInputValidationException {
        try {
            List<Response> responses = EventServiceFactory.getService().findResponsebyEmail(email, asist);
            return ResponseToThriftResponseDtoConversor.toThriftResponseDtos(responses);
        } catch (
                InputValidationException e) {
            throw new ThriftInputValidationException(e.getMessage());
        }
    }
}
