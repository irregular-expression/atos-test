package ru.irregularexpression.atostest.meetingrooms.utils;

import ru.irregularexpression.atostest.meetingrooms.R;

public class ErrorHandler {
    public static final int NO_ERRORS = Error.NO_ERRORS;

    public static class Error {
        private static final int NO_ERRORS = 0;

        //API errors
        public static final int UNKNOWN_ERROR = -1;
        public static final int NO_SUCH_USER = 101;
        public static final int INCORRECT_PASSWORD = 102;
        public static final int TIME_IS_ALREADY_RESERVED = 103;
        public static final int INCORRECT_EVENT_NAME = 104;

        //App errors
        public static final int NO_INTERNET_CONNECTION = 2000;
        public static final int NO_SERVER_CONNECTION = 2001;
        public static final int INCORRECT_SERVER_RESPONSE = 2002;
        public static final int SOCKET_TIMEOUT_EXCEPTION = 2204;
        public static final int DATE_FROM_PAST = 2300;

        //HTTP errors
        public static final int HTTP_BAD_REQUEST = 400;
        public static final int HTTP_UNAUTHORIZED = 401;
        public static final int HTTP_PAYMENT_REQUIRED = 402;
        public static final int HTTP_FORBIDDEN = 403;
        public static final int HTTP_NOT_FOUND = 404;
        public static final int HTTP_METHOD_NOT_ALLOWED = 405;
        public static final int HTTP_NOT_ACCEPTABLE = 406;
        public static final int HTTP_PROXY_AUTHENTICATION_REQUIRED = 407;
        public static final int HTTP_REQUEST_TIMEOUT = 408;
        public static final int HTTP_CONFLICT = 409;
        public static final int HTTP_GONE = 410;
        public static final int HTTP_LENGTH_REQUIRED = 411;
        public static final int HTTP_PRECONDITION_FAILED = 412;
        public static final int HTTP_PAYLOAD_TOO_LARGE = 413;
        public static final int HTTP_URI_TOO_LONG = 414;
        public static final int HTTP_UNSUPPORTED_MEDIA_TYPE = 415;
        public static final int HTTP_RANGE_NOT_SATISFIABLE = 416;
        public static final int HTTP_EXPECTATION_FAILED = 417;
        public static final int HTTP_IM_A_TEAPOT = 418;
        public static final int HTTP_MISDIRECTED_REQUEST = 421;
        public static final int HTTP_UNPROCESSABLE_ENTITY = 422;
        public static final int HTTP_LOCKED = 423;
        public static final int HTTP_FAILED_DEPENDENCY = 424;
        public static final int HTTP_UPGRADE_REQUIRED = 426;
        public static final int HTTP_PRECONDITION_REQUIRED = 428;
        public static final int HTTP_TOO_MANY_REQUESTS = 429;
        public static final int HTTP_REQUEST_HEADER_FIELDS_TOO_LARGE = 431;
        public static final int HTTP_RETRY_WITH = 449;
        public static final int HTTP_UNAVAILABLE_FOR_LEGAL_REASONS = 451;
        public static final int HTTP_INTERNAL_SERVER_ERROR = 500;
        public static final int HTTP_NOT_IMPLEMENTED = 501;
        public static final int HTTP_BAD_GATEWAY = 502;
        public static final int HTTP_SERVICE_UNAVAILABLE = 503;
        public static final int HTTP_GATEWAY_TIMEOUT = 504;
        public static final int HTTP_HTTP_VERSION_NOT_SUPPORTED = 505;
        public static final int HTTP_VARIANT_ALSO_NEGOTIATES = 506;
        public static final int HTTP_INSUFFICIENT_STORAGE = 507;
        public static final int HTTP_LOOP_DETECTED = 508;
        public static final int HTTP_BANDWIDTH_LIMIT_EXCEEDED = 509;
        public static final int HTTP_NOT_EXTENDED = 510;
        public static final int HTTP_NETWORK_AUTHENTICATION_REQUIRED = 511;
        public static final int HTTP_UNKNOWN_ERROR = 520;
        public static final int HTTP_WEB_SERVER_IS_DOWN = 521;
        public static final int HTTP_CONNECTION_TIMED_OUT = 522;
        public static final int HTTP_ORIGIN_IS_UNREACHABLE = 523;
        public static final int HTTP_A_TIMEOUT_OCCURRED = 524;
        public static final int HTTP_SSL_HANDSHAKE_FAILED = 525;
        public static final int HTTP_INVALID_SSL_CERTIFICATE = 526;
    }

    public static int getErrorTextResourse(int error) {
        switch (error) {
            case Error.INCORRECT_PASSWORD:
                return R.string.error_wrong_password;
            case Error.NO_SUCH_USER:
                return R.string.error_no_such_user;
            case Error.NO_SERVER_CONNECTION:
                return R.string.error_no_server_connection;
            case Error.NO_INTERNET_CONNECTION:
                return R.string.error_no_internet;
            case Error.HTTP_INTERNAL_SERVER_ERROR:
                return R.string.error_internal_server;
            case Error.DATE_FROM_PAST:
                return R.string.error_past_datetime;
            case Error.UNKNOWN_ERROR:
            default:
                return R.string.error_unknown;
        }
    }

}
