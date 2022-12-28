package es.udc.ws.app.thriftservice;

import Event.Event;
import es.udc.ws.app.thrift.ThriftEventDto;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class EventToThriftEventDtoConversor {

    public static Event toEvent(ThriftEventDto event) {
        return new Event(event.getEventId(), event.getEventName(), event.getEventDescription(), LocalDateTime.parse(event.getCelebrationDate()), null, event.getRuntime(),
                event.isEventState(), event.getAttendance(), event.getTotalAttendance() - event.getAttendance());
    }

    public static List<ThriftEventDto> toThriftEventDtos(List<Event> events) {

        List<ThriftEventDto> dtos = new ArrayList<>(events.size());

        for (Event event : events) {
            dtos.add(toThriftEventDto(event));
        }
        return dtos;

    }

    public static ThriftEventDto toThriftEventDto(Event event) {

        return new ThriftEventDto(event.getEventId(), event.getName(), event.getCelebrationDate().toString(), event.getRuntime(), event.getDescription(),
                event.getEventState(), event.getAttendance(), event.getAttendance() + event.getNot_Attendance());

    }

}
