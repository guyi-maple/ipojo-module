package tech.guyi.ipojo.module.coap.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CoapMethod {

    GET("GET"),POST("POST"),PUT("PUT"),DELETE("DELETE");

    private final String value;

}
