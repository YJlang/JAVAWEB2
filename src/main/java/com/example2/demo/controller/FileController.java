// 컨트롤러가 속한 패키지 선언
package com.example2.demo.controller;

// 필요한 자바 I/O 및 파일 처리 관련 클래스들을 임포트
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

// 스프링 프레임워크 관련 클래스들을 임포트
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// 이 클래스가 스프링 MVC 컨트롤러임을 나타내는 어노테이션
@Controller
public class FileController {
    // application.properties에서 설정된 업로드 경로를 주입받음
    @Value("${spring.servlet.multipart.location}")
    private String uploadFolder;    

    // POST 요청을 처리하는 /upload-email 엔드포인트
    @PostMapping("/upload-email")
    public String uploadEmail(
        // HTTP 요청 파라미터들을 메소드 파라미터로 바인딩
        @RequestParam("email") String email,
        @RequestParam("subject") String subject,
        @RequestParam("message") String message,
        RedirectAttributes redirectAttributes) {
        try {   
            // 이메일 유효성 검증 추가
            if (!isValidEmail(email)) {
                redirectAttributes.addFlashAttribute("error", "유효하지 않은 이메일 형식입니다.");
                return "error_page";
            }
            
            // 업로드 폴더의 절대 경로를 가져옴
            Path uploadPath = Paths.get(uploadFolder).toAbsolutePath();
            // 업로드 폴더가 존재하지 않으면 생성
            if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
            }
            // 이메일 주소에서 특수문자를 제거하여 파일명으로 사용
            String sanitizedEmail = email.replaceAll("[^a-zA-Z0-9]", "_");
            // 최종 파일 경로 설정 (.txt 확장자 추가)
            Path filePath = uploadPath.resolve(sanitizedEmail + ".txt");
            // 디버깅을 위한 파일 경로 출력
            System.out.println("File path: " + filePath);
            
            // 파일에 내용을 쓰기 위한 BufferedWriter 생성
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
                // 메일 제목 작성
                writer.write("메일 제목: " + subject);
                writer.newLine();
                // 메시지 내용 작성
                writer.write("요청 메시지:");
                writer.newLine();
                writer.write(message);      
            }
            // 성공 메시지를 리다이렉트 시 전달하기 위해 설정
            redirectAttributes.addFlashAttribute("message", "메일 내용이 성공적으로 업로드되었습니다!");  
           
        } catch (IOException e) {
            // 예외 발생 시 스택 트레이스 출력
            e.printStackTrace();
            // 에러 메시지를 리다이렉트 시 전달
            redirectAttributes.addFlashAttribute("error", "파일 업로드 중 오류가 발생했습니다: " + e.getMessage());
            // 에러 페이지로 리다이렉트
            return "/error_page/article_error";
        }
        // 성공 시 upload_end 페이지로 리다이렉트
        return "upload_end";
    }

    // 이메일 유효성 검증 메서드
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}
