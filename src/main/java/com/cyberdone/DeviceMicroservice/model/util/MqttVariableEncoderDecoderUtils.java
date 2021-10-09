package com.cyberdone.DeviceMicroservice.model.util;

import com.cyberdone.DeviceMicroservice.persistence.entity.ValueType;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

@Slf4j
@UtilityClass
public class MqttVariableEncoderDecoderUtils {
    private static final String ENCODE_NUMBER_SYMBOL = "#";
    private static final String ENCODE_STRING_SYMBOL = " ";
    private static final String DATE_PATTERN = "yyyy-MM-dd hh:mm:ss";

    public static String encode(LocalDateTime now) {
        return ENCODE_NUMBER_SYMBOL + now.getYear() +
                ENCODE_NUMBER_SYMBOL + now.getMonthValue() +
                ENCODE_NUMBER_SYMBOL + now.getDayOfMonth() +
                ENCODE_NUMBER_SYMBOL + now.getHour() +
                ENCODE_NUMBER_SYMBOL + now.getMinute() +
                ENCODE_NUMBER_SYMBOL + now.getSecond() +
                ENCODE_NUMBER_SYMBOL;
    }

    public static String encode(String variable) {
        if (variable.contains(ENCODE_STRING_SYMBOL)) {
            throw new IllegalStateException("Symbol ' ' is used for encoding and is restricted for usage");
        }
        return ENCODE_STRING_SYMBOL + variable + ENCODE_STRING_SYMBOL;
    }

    public static String encode(double variable) {
        return ENCODE_NUMBER_SYMBOL + String.format("%.11f", variable).replace(',', '.') + ENCODE_NUMBER_SYMBOL;
    }

    public static String encode(boolean variable) {
        return ENCODE_NUMBER_SYMBOL + (variable ? 1 : 0) + ENCODE_NUMBER_SYMBOL;
    }

    public static String encode(int variable) {
        return ENCODE_NUMBER_SYMBOL + variable + ENCODE_NUMBER_SYMBOL;
    }

    public static String encode(long variable) {
        return ENCODE_NUMBER_SYMBOL + variable + ENCODE_NUMBER_SYMBOL;
    }

    public static Date decode(String date) {
        try {
            return new SimpleDateFormat(DATE_PATTERN, Locale.ENGLISH).parse(date);
        } catch (ParseException e) {
            log.error("Current format is: '{}' but input date was: '{}'", DATE_PATTERN, date);
            throw new IllegalStateException("Parsing error. " + e);
        }
    }

    public static String encodeConsideringToValueType(String data, ValueType type) {
        return switch (type) {
            case NUMBER -> encode(Long.parseLong(data));
            case DOUBLE -> encode(Double.parseDouble(data));
            case STRING -> encode(data);
            case TIME -> encode(decode(data).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            case SWITCH, DIRECTION -> encode(Integer.parseInt(data));
            case NONE -> "";
        };
    }
}
