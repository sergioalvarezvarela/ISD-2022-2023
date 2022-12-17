package es.udc.ws.app.restservice.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import EventService.Exception.AlreadyCanceledException;
import es.udc.ws.app.restservice.json.AppExceptionToJsonConversor;
import jakarta.servlet.Servlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import Response.Response;
import Event.Event;
import EventService.EventServiceFactory;
import EventService.Exception.AlreadyResponseException;
import EventService.Exception.OutOfTimeException;
import es.udc.ws.app.restservice.dto.EventToRestEventDtoConversor;
import es.udc.ws.app.restservice.dto.ResponseToRestResponseDtoConversor;
import es.udc.ws.app.restservice.dto.RestEventDto;
import es.udc.ws.app.restservice.dto.RestResponseDto;
import es.udc.ws.app.restservice.json.JsonToRestEventDtoConversor;
import es.udc.ws.app.restservice.json.JsonToRestResponseDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.servlet.RestHttpServletTemplate;
import es.udc.ws.util.servlet.ServletUtils;

import static es.udc.ws.util.servlet.ServletUtils.normalizePath;


@SuppressWarnings("serial")
public class ResponseServlet extends RestHttpServletTemplate {
    @Override
    protected void processPost(HttpServletRequest req, HttpServletResponse res) throws
            InputValidationException, InstanceNotFoundException, IOException {
        ServletUtils.checkEmptyPath(req);
        RestResponseDto responseDto = JsonToRestResponseDtoConversor.toRestResponseDto(req.getInputStream());
        Response response = ResponseToRestResponseDtoConversor.toResponse(responseDto);


        try {
            response = EventServiceFactory.getService().addResponse(response);
        } catch (OutOfTimeException e) {
            ServletUtils.writeServiceResponse(res, HttpServletResponse.SC_GONE, AppExceptionToJsonConversor.toOutOfTimeException(e), null);
        } catch (AlreadyCanceledException e) {
            ServletUtils.writeServiceResponse(res, HttpServletResponse.SC_FORBIDDEN, AppExceptionToJsonConversor.toAlreadyCanceledException(e), null);
        } catch (AlreadyResponseException e) {
            ServletUtils.writeServiceResponse(res, HttpServletResponse.SC_FORBIDDEN, AppExceptionToJsonConversor.toAlreadyResponseException(e), null);
        }

        responseDto = ResponseToRestResponseDtoConversor.toRestResponseDto(response);
        String responseURL = normalizePath(req.getRequestURL().toString()) + "/" + response.getResponseId();
        Map<String, String> headers = new HashMap<>(1);
        headers.put("Location", responseURL);
        ServletUtils.writeServiceResponse(res, HttpServletResponse.SC_CREATED,
                JsonToRestResponseDtoConversor.toObjectNode(responseDto), headers);

    }

    @Override
    protected void processGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,
            InputValidationException {
        ServletUtils.checkEmptyPath(req);
        String userEmail = ServletUtils.getMandatoryParameter(req,"userEmail");
        boolean asist = Boolean.parseBoolean(ServletUtils.getMandatoryParameter(req,"asistence"));
        List<Response> responses = EventServiceFactory.getService().findResponsebyEmail(userEmail, asist);
        List<RestResponseDto> responseDtos = ResponseToRestResponseDtoConversor.toRestResponseDtos(responses);
        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
                JsonToRestResponseDtoConversor.toArrayNode(responseDtos), null);

    }
}
