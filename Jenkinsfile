pipeline {
    agent any
    
    stages 
    {
        stage('Maven Build') { 
            steps {
            	echo '[INFO] - Build stage initialized....'
            	sh 'mvn --version'
            	sh 'java -version'
                sh 'mvn clean package -DskipTests' 
                echo '[INFO] - Build stage finalized successfully!'
            }
        }
        stage('Test') { 
            steps {
                echo '[INFO] - Test stage initialized....'
                sh 'mvn test' 
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