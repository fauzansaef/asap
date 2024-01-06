package project.asap.exception;

import org.springframework.web.multipart.MaxUploadSizeExceededException;

public class FileUploadExceptionAdvice extends RuntimeException {

    public FileUploadExceptionAdvice(MaxUploadSizeExceededException exc) {
        super(exc);
    }

    public FileUploadExceptionAdvice(Exception ex) {
        super(ex);
    }

}
