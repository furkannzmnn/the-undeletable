package org.infa.model;

public final class FileResponse<T> {

        private final T data;
        private final String message;
        private final boolean error;

        public FileResponse(T data, boolean error, String message) {
            this.data = data;
            this.message = message;
            this.error = error;
        }

        public T data() {
            return data;
        }

        public String message() {
            return message;
        }

        public boolean hasError() {
            return error;
        }

}
