package com.forged.exceptions;

public class NodeNotFoundException extends BaseException {

    private NodeNotFoundException(final String errorMessage) {
        super(errorMessage);
    }

    public static NodeNotFoundException throwException() {
        return new NodeNotFoundException("ERROR: Node not found");
    }
}
