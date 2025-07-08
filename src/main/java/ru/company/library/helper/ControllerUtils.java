package ru.company.library.helper;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ControllerUtils {
    /**
     *
     * @param action Лямбда-выражение, содержащее операцию для выполнени
     * @param errorMessage Строка содержащая информациб об ошибке
     * @return Сообщение об ошибке
     */
    public static ResponseEntity<?> handleEntityOperation(
            Supplier<?> action,
            String errorMessage
    ) {
        try {
            Object result = action.get();

            // Для успешного удаления возвращаем сообщение
            if (result instanceof String) {
                Map<String, Object> response = new LinkedHashMap<>();
                response.put("status", "success");
                response.put("message", result);
                response.put("timestamp", Instant.now());
                return ResponseEntity.ok(response);
            }

            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException ex) {
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("status", "success");
            response.put("message", errorMessage);
            response.put("details", ex.getMessage());
            response.put("timestamp", Instant.now());
            return ResponseEntity.ok(response);
        }
    }
}