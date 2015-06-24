package com.github.tfaga.lynx.utils;

import com.github.tfaga.lynx.beans.QueryFilter;
import com.github.tfaga.lynx.beans.QueryOrder;
import com.github.tfaga.lynx.beans.QueryParameters;
import com.github.tfaga.lynx.enums.FilterOperation;
import com.github.tfaga.lynx.enums.OrderDirection;
import com.github.tfaga.lynx.enums.QueryFormatError;
import com.github.tfaga.lynx.exceptions.QueryFormatException;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author Tilen Faganel
 * @version 1.0.0
 * @since 1.0.0
 */
public class QueryStringUtils {

    private static final Logger log = Logger.getLogger(QueryStringUtils.class.getSimpleName());

    public static final String LIMIT_DELIMITER = "limit";
    public static final String LIMIT_DELIMITER_ALT = "max";

    public static final String OFFSET_DELIMITER = "offset";
    public static final String OFFSET_DELIMITER_ALT = "skip";

    public static final String ORDER_DELIMITER = "order";
    public static final String ORDER_DELIMITER_ALT = "sort";

    public static final String FIELDS_DELIMITER = "fields";
    public static final String FIELDS_DELIMITER_ALT = "select";

    public static final String FILTER_DELIMITER = "filter";
    public static final String FILTER_DELIMITER_ALT = "where";

    public static QueryParameters parseUri(URI uri) {

        log.finest("Parsing uri object: " + uri);

        if (uri == null) return new QueryParameters();

        return parse(uri.getRawQuery());
    }

    public static QueryParameters parseUriEncoded(String uri) {

        return parseUri(decodeUrl(uri));
    }

    public static QueryParameters parseUri(String uri) {

        log.finest("Parsing uri string: " + uri);

        if (uri == null || uri.isEmpty()) return new QueryParameters();

        int idxQuery = uri.indexOf("?");
        int idxFragment = uri.indexOf("#");

        if (idxQuery == -1) return new QueryParameters();

        if (idxFragment == -1) return parse(uri.substring(idxQuery + 1));

        if (idxFragment < idxQuery) return new QueryParameters();

        return parse(uri.substring(idxQuery + 1, idxFragment));
    }

    public static QueryParameters parseEncoded(String queryString) {

        return parse(decodeUrl(queryString));
    }

    public static QueryParameters parse(String queryString) {

        log.finest("Parsing query string: " + queryString);

        QueryParameters params = new QueryParameters();

        if (queryString == null || queryString.isEmpty()) return params;

        for (String pair : queryString.split("&")) {

            int idxOfPair = pair.indexOf("=");

            if (idxOfPair == -1) {

                parsePair(params, pair, "");
                continue;
            }

            String key, value;

            key = pair.substring(0, idxOfPair);
            value = pair.substring(idxOfPair + 1);

            parsePair(params, key, value);
        }

        return params;
    }

    private static void parsePair(QueryParameters params, String key, String value) {

        log.finest("Parsing query string pair: " + key + " " + value);

        if (params == null) return;

        if (key == null || key.isEmpty()) return;

        if (value == null || value.isEmpty()) return;

        switch (key) {

            case LIMIT_DELIMITER:
            case LIMIT_DELIMITER_ALT:

                params.setLimit(parseLimit(key, value));

                break;

            case OFFSET_DELIMITER:
            case OFFSET_DELIMITER_ALT:

                params.setOffset(parseOffset(key, value));

                break;

            case ORDER_DELIMITER:
            case ORDER_DELIMITER_ALT:

                params.getOrder().clear();

                Arrays.stream(value.split(",")).map(o -> parseOrder(key, o))
                        .filter(o -> o != null).distinct()
                        .forEach(o -> params.getOrder().add(o));

                break;

            case FIELDS_DELIMITER:
            case FIELDS_DELIMITER_ALT:

                params.getFields().clear();

                params.getFields().addAll(parseFields(value));

                break;

            case FILTER_DELIMITER:
            case FILTER_DELIMITER_ALT:

                params.getFilters().clear();

                params.getFilters().addAll(parseFilter(key, value));

                break;
        }
    }

    public static Long parseOffset(String key, String value) {

        log.finest("Parsing offset string: " + value);

        return parseLimit(key, value);
    }

    public static Long parseLimit(String key, String value) {

        log.finest("Parsing limit string: " + value);

        Long limit;

        try {

            limit = Long.parseLong(value);
        } catch (NumberFormatException e) {

            String msg = "Value for '" + key + "' is not a number: '" + value + "'";

            log.finest(msg);

            throw new QueryFormatException(msg, key, QueryFormatError.NOT_A_NUMBER);
        }

        if (limit < 0) {

            String msg = "Value for '" + key + "' is negative: '" + value + "'";

            log.finest(msg);

            throw new QueryFormatException(msg, key, QueryFormatError.NEGATIVE);
        }

        return limit;
    }

    public static QueryOrder parseOrder(String key, String value) {

        log.finest("Parsing order string: " + value);

        if (value == null || value.isEmpty()) return null;

        QueryOrder o = new QueryOrder();

        String[] pair = value.split(" ");

        if (pair[0].isEmpty()) {

            String msg = "Value for '" + key + "' is malformed: '" + value + "'";

            log.finest(msg);

            throw new QueryFormatException(msg, key, QueryFormatError.MALFORMED);
        }

        o.setField(pair[0]);

        if (pair.length > 1) {

            try {

                o.setOrder(OrderDirection.valueOf(pair[1].toUpperCase()));
            } catch (IllegalArgumentException e) {

                String msg = "Constant in '" + key + "' does not exist: '" + value + "'";

                log.finest(msg);

                throw new QueryFormatException(msg, key, QueryFormatError.NO_SUCH_CONSTANT);
            }
        } else {

            o.setOrder(OrderDirection.ASC);
        }

        return o;
    }

    public static List<String> parseFields(String value) {

        log.finest("Parsing fields string: " + value);

        return Arrays.stream(value.split(",")).filter(f -> !f.isEmpty()).distinct()
                .collect(Collectors.toList());
    }

    public static List<QueryFilter> parseFilter(String key, String value) {

        log.finest("Parsing filter string: " + value);

        List<QueryFilter> filterList = new ArrayList<>();

        if (value == null || value.isEmpty()) return filterList;

        Arrays.stream(value.split("[ ]+(?=([^']*'[^']*')*[^']*$)"))
                .map(f -> f.split("[:]+(?=([^']*'[^']*')*[^']*$)"))
                .filter(f -> f.length == 3)
                .forEach(f -> {

                    QueryFilter qf = new QueryFilter();
                    qf.setField(f[0]);

                    try {

                        qf.setOperation(FilterOperation.valueOf(f[1].toUpperCase()));
                    } catch (IllegalArgumentException e) {

                        String msg = "Constant in '" + key + "' does not exist: '" + value + "'";

                        log.finest(msg);

                        throw new QueryFormatException(msg, key, QueryFormatError.NO_SUCH_CONSTANT);
                    }

                    if (f[2].matches("^\\[.*\\]$") && (qf.getOperation() == FilterOperation.IN ||
                            qf.getOperation() == FilterOperation.NIN)) {

                        String values = f[2].replaceAll("(^\\[)|(\\]$)", "");

                        Arrays.stream(values.split(",")).filter(e -> !e.isEmpty()).distinct()
                                .forEach(e -> qf.getValues().add(e));

                    } else if (f[2].matches("^dt'.*'$")) {

                        Date d = parseDate(f[2].replaceAll("(^dt')|('$)", ""));

                        if (d == null) {

                            String msg = "Value for '" + key + "' is malformed: '" + value + "'";

                            log.finest(msg);

                            throw new QueryFormatException(msg, key, QueryFormatError.MALFORMED);
                        }

                        qf.setDateValue(d);
                    } else {

                        qf.setValue(f[2].replaceAll("(^')|('$)", ""));
                    }

                    filterList.add(qf);
                });

        return filterList;
    }

    private static Date parseDate(String date) {

        try {
            return Date.from(ZonedDateTime.parse(date).toInstant());
        } catch (DateTimeParseException e) {

            return null;
        }
    }

    private static String decodeUrl(String url) {

        try {
            String a = URLEncoder.encode(url, StandardCharsets.UTF_8.displayName());

            if (!URLEncoder.encode(url, StandardCharsets.UTF_8.displayName()).equals(url)) {
                return URLDecoder.decode(url, StandardCharsets.UTF_8.displayName());
            } else {
                return url;
            }
        } catch (UnsupportedEncodingException e) {

            log.severe("UTF-8 encoding is not supported on this system");

            throw new AssertionError();
        }
    }
}