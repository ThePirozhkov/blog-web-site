package by.baby.blogwebsite.security.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Value("${spring.servlet.multipart.max-file-size}")
    private String MAX_SIZE;

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String exceptionHandle(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "Превышен размер файла" + " (MAX:" + MAX_SIZE + ")");
        return "redirect:/exception";
    }

}
