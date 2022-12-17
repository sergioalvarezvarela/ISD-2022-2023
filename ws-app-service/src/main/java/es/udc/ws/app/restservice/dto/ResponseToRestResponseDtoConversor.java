package es.udc.ws.app.restservice.dto;

import Event.Event;
import Response.Response;

import java.util.ArrayList;
import java.util.List;

public class ResponseToRestResponseDtoConversor {

    public static RestResponseDto toRestResponseDto(Response response) {
        return new RestResponseDto(response.getResponseId(), response.getEventId(), response.getEmail(), response.getAsistencia());
    }

    public static Response toResponse(RestResponseDto response) {
        return new Response(response.getResponseId(),response.getEventId(), response.getWorkerEmail(), null, response.getAttendance());
    }

    public static List<RestResponseDto> toRestResponseDtos(List<Response> responses) {
        List<RestResponseDto> responseDtos = new ArrayList<>(responses.size());
        for (int i = 0; i < responses.size(); i++) {
            Response response = responses.get(i);
            responseDtos.add(toRestResponseDto(response));
        }
        return responseDtos;
    }
}
