// package com.seminarroom.edu;

// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;
// import com.seminarroom.edu.config.WebConfig;
// import org.springframework.context.annotation.Import;

// @SpringBootApplication
// @Import(WebConfig.class)
// public class EduJourneyBackendApplication {
//     public static void main(String[] args) {
//         SpringApplication.run(EduJourneyBackendApplication.class, args);
//     }
// }

package com.seminarroom.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EduJourneyBackendApplication {
    public static void main(String[] args) {
        System.out.println(">>> Application starting...");
        SpringApplication.run(EduJourneyBackendApplication.class, args);
    }
}
