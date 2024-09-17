package LogBeanieView.LogBeanieView.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LogViewController {

    @Value("${log.file.path:logs/application.log}")
    private String logFilePath;

    @GetMapping("/view-logs")
    public String viewLogs(Model model) {
        // Dynamically get the current project's root directory
        String projectRootDir = System.getProperty("user.dir");
        File logFile = new File(projectRootDir, logFilePath);

        List<String> logs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logs.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            logs.add("Error reading log file: " + e.getMessage());
        }

        model.addAttribute("logs", logs);
        return "logs";
    }
}
