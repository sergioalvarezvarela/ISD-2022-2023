package es.udc.ws.app.client.service.thrift;

import es.udc.ws.app.client.service.ClientEventService;
import es.udc.ws.app.client.service.dto.ClientEventDto;
import es.udc.ws.app.client.service.dto.ClientResponseDto;
import es.udc.ws.app.client.service.exceptions.ClientAlreadyCanceledException;
import es.udc.ws.app.client.service.exceptions.ClientAlreadyResponseException;
import es.udc.ws.app.client.service.exceptions.ClientOutOfTimeException;
import es.udc.ws.app.thrift.*;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.time.LocalDate;
import java.util.List;

public class ThriftClientEventService implements ClientEventService {

    private final static String ENDPOINT_ADDRESS_PARAMETER =
            "ThriftClientEventService.endpointAddress";

    private final static String endpointAddress =
            ConfigurationParametersManager.getParameter(ENDPOINT_ADDRESS_PARAMETER);
    @Override
    public Long addEvent(ClientEventDto event) throws InputValidationException {
        return null;
    }

    @Override
    public ClientEventDto findEvent(Long eventId) throws InstanceNotFoundException {
        return null;
    }

    @Override
    public void cancelEvent(Long eventId) throws InstanceNotFoundException, ClientOutOfTimeException, ClientAlreadyCanceledException, InputValidationException {
        ThriftEventService.Client client = getClient();
        TTransport transport = client.getInputProtocol().getTransport();
        try{
            transport.open();
            client.CancelEvent(eventId);
        } catch (ThriftInputValidationException e) {
            throw new InputValidationException(e.getMessage());
        } catch (ThriftInstanceNotFoundException e) {
            throw new InstanceNotFoundException(e.getInstanceId(), e.getInstanceType());
        } catch (ThriftAlreadyCanceledException e) {
            throw new ClientAlreadyCanceledException(e.getEventId());
        } catch (ThriftOutOfTimeException e) {
            throw new ClientOutOfTimeException(e.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            transport.close();
        }
    }

    @Override
    public List<ClientEventDto> findEvents(LocalDate endDate, String keywords) throws InputValidationException {
        return null;
    }

    @Override
    public Long addResponse(ClientResponseDto response) throws InputValidationException, InstanceNotFoundException, ClientOutOfTimeException, ClientAlreadyCanceledException, ClientAlreadyResponseException {
        return null;
    }

    @Override
    public List<ClientResponseDto> findResponses(String userEmail, boolean asistence) throws InputValidationException {
        ThriftEventService.Client client = getClient();
        TTransport transport = client.getInputProtocol().getTransport();
        try{
            transport.open();
            return ClientResponseDtoToThriftDtoConversor.toClientResponseDtos(client.findResponsebyEmail(userEmail,asistence));
        } catch (ThriftInputValidationException e) {
            throw new InputValidationException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            transport.close();
        }
    }

    private ThriftEventService.Client getClient() {

        try {

            TTransport transport = new THttpClient(endpointAddress);
            TProtocol protocol = new TBinaryProtocol(transport);

            return new ThriftEventService.Client(protocol);

        } catch (TTransportException e) {
            throw new RuntimeException(e);
        }

    }
}
