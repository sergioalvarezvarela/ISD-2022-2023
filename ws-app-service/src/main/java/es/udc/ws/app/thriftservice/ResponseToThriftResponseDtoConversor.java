package es.udc.ws.app.thriftservice;

import Event.Event;
import Response.Response;
import es.udc.ws.app.thrift.ThriftEventDto;
import es.udc.ws.app.thrift.ThriftResponseDto;

import java.util.ArrayList;
import java.util.List;

public class ResponseToThriftResponseDtoConversor {
    public static Response toResponse(ThriftResponseDto response) {
        return new Response(response.getResponseId(),response.getEventId(), response.getWorkerEmail(), null, response.isAttendance());
    }

    public static List<ThriftResponseDto> toThriftResponseDtos(List<Response> responses) {

        List<ThriftResponseDto> dtos = new ArrayList<>(responses.size());

        for (Response response : responses) {
            dtos.add(toThriftResponseDto(response));
        }
        return dtos;

    }

    public static ThriftResponseDto toThriftResponseDto(Response response) {

        return new ThriftResponseDto(response.getResponseId(), response.getEventId(), response.getEmail(), response.getAsistencia());

    }

}
