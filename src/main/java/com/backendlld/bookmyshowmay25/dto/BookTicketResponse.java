package com.backendlld.bookmyshowmay25.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookTicketResponse {
    private Long bookingId;
    private ResponseStatus status;
    private String message;
}
