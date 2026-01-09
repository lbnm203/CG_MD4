package com.codegym.book_borrowing_app.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    private int visitorCount = 0;

    // 1. Ghi log khi mượn sách (thay đổi trạng thái)
    @AfterReturning(
            pointcut = "execution(* com.codegym.book_borrowing_app.service.BookService.borrowBook(..))",
            returning = "borrowCode"
    )
    public void logAfterBorrowBook(JoinPoint joinPoint, String borrowCode) {
        Object[] args = joinPoint.getArgs();
        Long bookId = (Long) args[0];
        logger.info("📚 [MƯỢN SÁCH] - Book ID: {} - Mã mượn: {}", bookId, borrowCode);
    }

    // 2. Ghi log khi trả sách (thay đổi trạng thái)
    @AfterReturning(
            pointcut = "execution(* com.codegym.book_borrowing_app.service.BookService.returnBook(..))"
    )
    public void logAfterReturnBook(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String borrowCode = (String) args[0];
        logger.info("📖 [TRẢ SÁCH] - Mã mượn: {}", borrowCode);
    }

    // 3. Đếm số lượng người ghé thăm (tất cả request đến controller)
    @Before("execution(* com.codegym.book_borrowing_app.controller.BookController.*(..))")
    public void countVisitors(JoinPoint joinPoint) {
        visitorCount++;
        String methodName = joinPoint.getSignature().getName();
        logger.info("👥 [VISITOR COUNT: {}] - Phương thức: {}", visitorCount, methodName);
    }

    // 4. Ghi log khi có exception xảy ra
    @AfterThrowing(
            pointcut = "execution(* com.codegym.book_borrowing_app.service.BookService.*(..))",
            throwing = "exception"
    )
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        logger.error("❌ [LỖI] - Phương thức: {} - Exception: {}",
                methodName, exception.getMessage());
    }
}