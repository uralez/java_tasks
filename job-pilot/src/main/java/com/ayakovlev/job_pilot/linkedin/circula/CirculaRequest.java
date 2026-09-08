package com.ayakovlev.job_pilot.linkedin.circula;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

@Slf4j
public class CirculaRequest {

    record Job(
            String id,
            String title
    ) {}

    record JobsResponse(
            java.util.List<Job> jobs
    ) {}

    private static final String JOB_TITLE           = "Senior Backend Engineer (m/f/d)";
    private static final String JOBS_URL            = "https://0x0ff1ce.circula.com/jobs";
//    private static final String TEST_APPLY_URL      = "https://httpbin.org/post";
    private static final String APPLY_URL_TEMPLATE  = "https://0x0ff1ce.circula.com/jobs/{jobId}/apply";
    private static final Properties properties;
    static {
        properties = getProperties();
    }
    public static void main(String[] args) {
        ClientHttpRequestInterceptor interceptor = (request, body, execution) ->{
            log.info("HTTP method:      {}", request.getMethod());
            log.info("URI:              {}", request.getURI());
            log.info("Content-Type:     {}", request.getHeaders().getContentType());
            log.info("Request body size:{}", body.length + " bytes");
            return execution.execute(request, body);
        };
        RestClient restClient = RestClient.builder()
                .requestInterceptor(interceptor)
                .build();

        // get
        String jobId = findJobId(restClient);
        log.info("jobId:            {}",   jobId);

        // post
        String postResponse = submitApplication(restClient, jobId);
        log.info("POST response:    {}", postResponse);
    }

    private static String submitApplication(RestClient restClient, String jobId){
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        String postResponse = "TEMP RESPONSE";
        try {
            parts.add("name",       getProperty("cv.name"));
            parts.add("email",      getProperty("cv.email"));
            parts.add("message",    getProperty("cv.message"));
            String home = System.getProperty("user.home");
            String pdfPath = home + getProperty("cv.path");
            Path cvPath = Path.of(pdfPath);
            FileSystemResource cv = new FileSystemResource(cvPath);
            boolean cvExists = cv.exists();
            log.info("CV exists: {}", cvExists);
            long cvSize = cv.contentLength();
            log.info("CV size: {}", cvSize + " bytes");
            if (!cvExists || cvSize < 10000) {
                return "ERROR: Troubles with CV. POST will not be sent.";
            }
            parts.add("cv", new FileSystemResource(pdfPath));
            log.info("parts:        {}", parts);
            log.info("cv file name: {}", cv.getFilename());
            log.info("cv size:      {}", cv.contentLength());

            String postUrl = APPLY_URL_TEMPLATE;
            log.info("postUrl:      {}", postUrl);
            long start = System.nanoTime();
            postResponse = restClient.post()
                    .uri(postUrl, jobId)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(parts)
                    .retrieve()
                    .body(String.class);
            long end = System.nanoTime();
            log.info("Spent time:   {}", (end - start) / 1_000_000 + " ms");
        }catch(PropertyNotSetException ex){
            log.error("Failed to init application", ex);
            return ex.getMessage();
        }catch(Exception ex){
            String res = "Failed to submit application";
            log.error(res, ex);
            return res;
        }
        return postResponse;
    }

    private static String findJobId(RestClient restClient){
        long start = System.nanoTime();
        JobsResponse jobsResponse = restClient.get()
                .uri(JOBS_URL)
                .retrieve()
                .body(JobsResponse.class);
        long end = System.nanoTime();
        log.info("Spent time:       {}", (end - start) / 1_000_000 + " ms");
        String jobId = jobsResponse.jobs().stream()
                .filter(job -> job.title().equals(JOB_TITLE))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "Job not found: " + JOB_TITLE))
                .id();
        return jobId;
    }

    private static Properties getProperties(){
        try (InputStream inputStream = Files.newInputStream(Path.of("src/main/resources/application-local.properties"))){
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        }catch(Exception ex){
            log.error("Failed to get properties.", ex);
            return null;
        }
    }
    private static String getProperty(String propertyName) throws PropertyNotSetException {
        if(properties == null) throw new PropertyNotSetException ("Properties are not set");
        String prop = properties.getProperty(propertyName);
        if(StringUtils.isBlank(prop)) throw new PropertyNotSetException(propertyName + " is not set");
        return prop;
    }

    static class PropertyNotSetException extends Exception {
        public PropertyNotSetException(String message){
            super(message);
        }
    }
}
