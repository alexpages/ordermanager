Jenkinsfile (Declarative Pipeline)
/* Requires the Docker Pipeline plugin */
pipeline {
    agent { docker { image 'maven:3.9.6-eclipse-temurin-17-alpine' } }
    
    stages {

        stage('Build') { 
            steps {
            	echo '[INFO] - Build stage initialized....'
                sh 'mvn -B -DskipTests clean package' 
                echo '[INFO] - Build stage finalized successfully!'
            }
        }
    }
}