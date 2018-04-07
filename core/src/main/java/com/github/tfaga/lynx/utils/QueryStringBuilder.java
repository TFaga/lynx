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
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Tilen Faganel
 * @version 1.0.0
 * @since 1.0.0
 */
public class QueryStringBuilder {

    private static final Logger log = Logger.getLogger(QueryStringBuilder.class.getSimpleName());

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

    private String query;

    private Boolean paginationEnabled = true;
    private Boolean filtersEnabled = true;
    private Boolean orderEnabled = true;
    private Boolean fieldsEnabled = true;

    private Long maxLimit;
    private Long defaultLimit;
    private Long defaultOffset;

    private Predicate<QueryFilter> filterPredicate;
    private Predicate<QueryOrder> orderPredicate;
    private Predicate<String> fieldPredicate;

    public QueryStringBuilder uri(URI uri) {

        log.finest("Setting uri object: " + uri);

        if (uri == null) throw new IllegalArgumentException("The passed URI cannot be null");

        query = uri.getRawQuery();

        return this;
    }

    public QueryStringBuilder uriEncoded(String uri) {

        return uri(decodeUrl(uri));
    }

    public QueryStringBuilder uri(String uri) {

        log.finest("Setting uri string: " + uri);

        if (uri == null || uri.isEmpty())
            throw new IllegalArgumentException("The passed URI string cannot be empty");

        int idxQuery = uri.indexOf("?");
        int idxFragment = uri.indexOf("#");

        if (idxQuery == -1) throw new IllegalArgumentException();

        if (idxFragment == -1) {

            query = uri.substring(idxQuery + 1);

            return this;
        }

        if (idxFragment < idxQuery) {

            query = "";

            return this;
        }

        query = uri.substring(idxQuery + 1, idxFragment);

        return this;
    }

    public QueryStringBuilder queryEncoded(String queryString) {

        return query(decodeUrl(queryString));
    }

    public QueryStringBuilder query(String queryString) {

        query = queryString;

        return this;
    }

    public QueryStringBuilder enablePagination(Boolean enable) {

        if (enable == null) throw new IllegalArgumentException("The enable boolean cannot be null");

        paginationEnabled = enable;

        return this;
    }

    public QueryStringBuilder enableFilters(Boolean enable) {

        if (enable == null) throw new IllegalArgumentException("The enable boolean cannot be null");

        filtersEnabled = enable;

        return this;
    }

    public QueryStringBuilder enableOrder(Boolean enable) {

        if (enable == null) throw new IllegalArgumentException("The enable boolean cannot be null");

        orderEnabled = enable;

        return this;
    }

    public QueryStringBuilder enableFields(Boolean enable) {

        if (enable == null) throw new IllegalArgumentException("The enable boolean cannot be null");

        fieldsEnabled = enable;

        return this;
    }

    public QueryStringBuilder maxLimit(int limit) {

        return maxLimit((long) limit);
    }

    public QueryStringBuilder maxLimit(Long limit) {

        log.finest("Setting max limit: " + limit);

        if (limit == null) throw new IllegalArgumentException("The passed limit cannot be null");

        if (limit < 0)
            throw new IllegalArgumentException("The passed limit must be a positive number");

        maxLimit = limit;

        return this;
    }

    public QueryStringBuilder defaultLimit(int limit) {

        return defaultLimit((long) limit);
    }

    public QueryStringBuilder defaultLimit(Long limit) {

        log.finest("Setting default limit: " + limit);

        if (limit == null) throw new IllegalArgumentException("The passed limit cannot be null");

        if (limit < 0)
            throw new IllegalArgumentException("The passed limit must be a positive number");

        defaultLimit = limit;

        return this;
    }

    public QueryStringBuilder defaultOffset(int offset) {

        return defaultOffset((long) offset);
    }

    public QueryStringBuilder defaultOffset(Long offset) {

        log.finest("Setting default offset: " + offset);

        if (offset == null) throw new IllegalArgumentException("The passed offset cannot be null");

        if (offset < 0)
            throw new IllegalArgumentException("The passed offset must be a positive number");

        defaultOffset = offset;

        return this;
    }

    public QueryStringBuilder allowFilter(Predicate<QueryFilter> predicate) {

        filterPredicate = predicate;

        return this;
    }

    public QueryStringBuilder allowOrder(Predicate<QueryOrder> predicate) {

        orderPredicate = predicate;

        return this;
    }

    public QueryStringBuilder allowField(Predicate<String> predicate) {

        fieldPredicate = predicate;

        return this;
    }

    public QueryParameters build() {

        log.finest("Building query string: " + query);

        QueryParameters params = new QueryParameters();

        if (paginationEnabled && defaultLimit != null) params.setLimit(defaultLimit);
        if (paginationEnabled && defaultOffset != null) params.setOffset(defaultOffset);

        if (query == null || query.isEmpty()) return params;

        for (String pair : query.split("&+(?=([^']*'[^']*')*[^']*$)")) {

            int idxOfPair = pair.indexOf("=");

            if (idxOfPair == -1) {

                buildPair(params, pair, "");
                continue;
            }

            String key, value;

            key = pair.substring(0, idxOfPair);
            value = pair.substring(idxOfPair + 1);

            buildPair(params, key, value);
        }

        return params;
    }

    private void buildPair(QueryParameters params, String key, String value) {

        log.finest("Building query string pair: " + key + " " + value);

        if (params == null) return;

        if (key == null || key.isEmpty()) return;

        if (value == null || value.isEmpty()) return;

        switch (key) {

            case LIMIT_DELIMITER:
            case LIMIT_DELIMITER_ALT:

                if (paginationEnabled) {
                    params.setLimit(buildLimit(key, value));
                }

                break;

            case OFFSET_DELIMITER:
            case OFFSET_DELIMITER_ALT:

                if (paginationEnabled) {
                    params.setOffset(buildOffset(key, value));
                }

                break;

            case ORDER_DELIMITER:
            case ORDER_DELIMITER_ALT:

                if (orderEnabled) {
                    params.getOrder().clear();

                    Arrays.stream(value.split(",")).map(o -> buildOrder(key, o))
                            .filter(o -> o != null && (orderPredicate == null || orderPredicate.test(o)))
                            .distinct()
                            .forEach(o -> params.getOrder().add(o));
                }

                break;

            case FIELDS_DELIMITER:
            case FIELDS_DELIMITER_ALT:

                if (fieldsEnabled) {
                    params.getFields().clear();

                    params.getFields().addAll(buildFields(value));
                }

                break;

            case FILTER_DELIMITER:
            case FILTER_DELIMITER_ALT:

                if (filtersEnabled) {
                    params.getFilters().clear();

                    params.getFilters().addAll(buildFilter(key, value));

                }

                break;
        }
    }

    private Long buildOffset(String key, String value) {

        log.finest("Building offset string: " + value);

        Long offset = parseLong(key, value);

        if (offset < 0) {

            String msg = "Value for '" + key + "' is negative: '" + value + "'";

            log.finest(msg);

            throw new QueryFormatException(msg, key, QueryFormatError.NEGATIVE);
        }

        return offset;
    }

    private Long buildLimit(String key, String value) {

        log.finest("Building limit string: " + value);

        Long limit = parseLong(key, value);

        if (limit < 0) {

            String msg = "Value for '" + key + "' is negative: '" + value + "'";

            log.finest(msg);

            throw new QueryFormatException(msg, key, QueryFormatError.NEGATIVE);
        }

        if (maxLimit != null && limit > maxLimit) limit = maxLimit;

        return limit;
    }

    private QueryOrder buildOrder(String key, String value) {

        log.finest("Building order string: " + value);

        if (value == null || value.isEmpty()) return null;

        QueryOrder o = new QueryOrder();

        String[] pair = value.split("(\\s|\\+)");

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

    private List<String> buildFields(String value) {

        log.finest("Building fields string: " + value);

        return Arrays.stream(value.split(","))
                .filter(f -> !f.isEmpty() && (fieldPredicate == null || fieldPredicate.test(f)))
                .distinct()
                .collect(Collectors.toList());
    }

    private List<QueryFilter> buildFilter(String key, String value) {

        log.finest("Building filter string: " + value);

        List<QueryFilter> filterList = new ArrayList<>();

        if (value == null || value.isEmpty()) return filterList;

        Pattern filterPattern = Pattern.compile("[:]+(?=([^']*'[^']*')*[^']*$)") ;

        List<String[]> filters = Arrays.stream(value.split("[(\\s|+)]+(?=([^']*'[^']*')*[^']*$)"))
                .map(filterPattern::split)
                .collect(Collectors.toList());

        filters.stream().filter(f -> f.length == 2).forEach(f -> {

            QueryFilter qf = new QueryFilter();
            qf.setField(f[0]);
            qf.setOperation(parseFilterOperation(key, f[1].toUpperCase()));

            if ((qf.getOperation() == FilterOperation.ISNULL || qf.getOperation() == FilterOperation.ISNOTNULL)
                    && (filterPredicate == null || filterPredicate.test(qf))) {

                filterList.add(qf);
            }
        });

        Pattern valueListPattern = Pattern.compile("^\\[(.*)]$");
        Pattern valuesPattern = Pattern.compile("[,]+(?=([^']*'[^']*')*[^']*$)");

        Pattern valueDateTimePattern = Pattern.compile("^dt'(.*)'$");
        Pattern valuePattern = Pattern.compile("(^')|('$)");

        filters.stream()
                .filter(f -> f.length == 3)
                .forEach(f -> {

                    QueryFilter qf = new QueryFilter();
                    qf.setField(f[0]);
                    qf.setOperation(parseFilterOperation(key, f[1].toUpperCase()));

                    Matcher matcher;

                    if ((matcher = valueListPattern.matcher(f[2])).find() &&
                            (qf.getOperation() == FilterOperation.IN ||
                            qf.getOperation() == FilterOperation.NIN ||
                            qf.getOperation() == FilterOperation.NINIC ||
                            qf.getOperation() == FilterOperation.INIC)) {

                        String values = matcher.group(1);

                        Arrays.stream(valuesPattern.split(values))
                                .filter(e -> !e.isEmpty()).distinct()
                                .map(e -> valuePattern.matcher(e).replaceAll(""))
                                .forEach(e -> qf.getValues().add(e));

                    } else if ((matcher = valueDateTimePattern.matcher(f[2])).find()) {

                        Date d = parseDate(matcher.group(1));

                        if (d == null) {

                            String msg = "Value for '" + key + "' is malformed: '" + value + "'";

                            log.finest(msg);

                            throw new QueryFormatException(msg, key, QueryFormatError.MALFORMED);
                        }

                        qf.setDateValue(d);
                    } else {

                        qf.setValue(valuePattern.matcher(f[2]).replaceAll(""));
                    }

                    if (filterPredicate == null || filterPredicate.test(qf)) {

                        filterList.add(qf);
                    }
                });

        return filterList;
    }

    private Date parseDate(String date) {

        try {
            return Date.from(ZonedDateTime.parse(date).toInstant());
        } catch (DateTimeParseException e) {

            return null;
        }
    }

    private Long parseLong(String key, String value) {

        try {

            return Long.parseLong(value);
        } catch (NumberFormatException e) {

            String msg = "Value for '" + key + "' is not a number: '" + value + "'";

            log.finest(msg);

            throw new QueryFormatException(msg, key, QueryFormatError.NOT_A_NUMBER);
        }
    }

    private FilterOperation parseFilterOperation(String key, String value) {

        try {

            return FilterOperation.valueOf(value);
        } catch (IllegalArgumentException e) {

            String msg = "Constant in '" + key + "' does not exist: '" + value + "'";

            log.finest(msg);

            throw new QueryFormatException(msg, key, QueryFormatError.NO_SUCH_CONSTANT);
        }
    }

    private String decodeUrl(String url) {

        if (url == null) return null;

        try {
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