namespace java es.udc.ws.app.thrift

struct ThriftEventDto {
    1: i64 eventId
    2: string eventName
    3: string celebrationDate
    4: i32 runtime
    5: string eventDescription
    6: bool eventState
    7: i32 attendance
    8: i32 totalAttendance
}

struct ThriftResponseDto {
    1: i64 responseId
    2: i64 eventId
    3: string workerEmail
    4: bool attendance
}

exception ThriftInputValidationException {
    1: string message
}

exception ThriftInstanceNotFoundException {
    1: string instanceId
    2: string instanceType
}

exception ThriftAlreadyCanceledException {
    1: i64 eventId
}

exception ThriftAlreadyResponseException {
    1: i64 eventId
}

exception ThriftOutOfTimeException {
    1: i64 id
}

service ThriftEventService {

   void CancelEvent(1: i64 eventId) throws (1: ThriftInputValidationException e, 2: ThriftInstanceNotFoundException ee, 3: ThriftOutOfTimeException eee, 4: ThriftAlreadyCanceledException eeee)

   list<ThriftResponseDto> findResponsebyEmail(1: string email, 2: bool asist) throws (1: ThriftInputValidationException e)
}