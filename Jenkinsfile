pipeline {
    agent any
    
    stages 
    {
        stage('Maven Build') { 
            steps {
            	echo '[INFO] - Build stage initialized....'
            	bat 'mvn -version'
            	bat 'java -version'
                bat 'mvn clean package -DskipTests' 
                echo '[INFO] - Build stage finalized successfully!'
            }
        }
        stage('Test') { 
            steps {
                echo '[INFO] - Test stage initialized....'
                bat 'mvn test' 
            	echo '[INFO] - Test stage finalized successfully!'
            }                
        }
        
        stage('Packaging and Pushing') { 
            steps {
                echo '[INFO] - Packaging stage initialized....'
            	echo '[INFO] - Packaging stage finalized successfully!'
            }                
        }
    }
}