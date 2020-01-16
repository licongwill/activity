
package com.me.activity.util.base64;

import java.io.IOException;

/**
 * @since 0.10.0
 */
public class CodecException extends IOException {

    public CodecException(String message) {
        super(message);
    }

    public CodecException(String message, Throwable cause) {
        super(message, cause);
    }
}
