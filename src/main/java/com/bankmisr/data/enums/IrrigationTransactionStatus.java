package com.bankmisr.data.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum IrrigationTransactionStatus {
    SCHEDULED,
    FAILED,
    SUCCEDED
}
