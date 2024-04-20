pipeline {
    agent any
    stages {
        stage('100-Checkout(SCM)') { 
            steps {
                echo "[INFO] > 100-Checkout(SCM) > Checking out Source Code Management..." 
                checkout scm
                echo "[INFO] > 100-Checkout(SCM) > Checkout completed!!" 
            }
        }
        
        stage('200-Build') { 
            steps {
                echo "[INFO] Building the image..." 
                bat 'mvn -B -DskipTests clean package' 
                echo "[INFO] > 200-Build > Build phase completed!!" 
            }
        }
        
        stage('201-Test') {
            steps {
                echo "[INFO] > 201-Test > Running tests..." 
                bat 'mvn clean test'
                echo "[INFO] > 201-Test > Test phase completed!!" 
            }
        }
    }
}