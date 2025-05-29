    package com.seminarroom.edu.config;

    import com.amazonaws.auth.AWSStaticCredentialsProvider;
    import com.amazonaws.auth.BasicAWSCredentials;
    import com.amazonaws.services.s3.AmazonS3;
    import com.amazonaws.services.s3.AmazonS3ClientBuilder;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;

    @Configuration
    public class AwsS3Config {

        // Replace with your actual credentials (or use environment variables)
        private static final String ACCESS_KEY = "AKIA5ZM2EOKJXRVOUZPC";
        private static final String SECRET_KEY = "JD5rZpw6uCKlbO1+owf1pBKE/GBm54OU1Gh42Vbz";
        private static final String REGION = "us-east-1"; // Set your region
        private static final String BUCKET_NAME = "seminarroom-files";

        @Bean
        public AmazonS3 amazonS3() {
            BasicAWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
            return AmazonS3ClientBuilder.standard()
                    .withRegion(REGION)
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .build();
        }

        @Bean
        public String bucketName() {
            return BUCKET_NAME;
        }
    }
