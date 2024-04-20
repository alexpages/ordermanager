pipeline {
    agent any
    environment {
        REGISTRY = 'alexintelc/ordermanager'
        REGISTRY_CREDENTIAL = 'dockerhub'
        AWS_CREDENTIAL = '456bd977-adfc-47f9-9ccb-0430011f0767'
    }
    stages {
        stage('100-Checkout(SCM)') {
            steps {
                echo '[INFO] > 100-Checkout(SCM) > Checking out Source Code Management...'
                checkout scm
                echo '[INFO] > 100-Checkout(SCM) > Checkout completed!!'
            }
        }

        stage('200-Build') {
            steps {
                echo '[INFO] > 200-Build > Building the image...'
                bat 'mvn -B -DskipTests clean package'
                echo '[INFO] > 200-Build > Build phase completed!!'
            }
        }

        stage('201-Test') {
            steps {
                echo '[INFO] > 201-Test > Running tests...'
                bat 'mvn clean test'
                echo '[INFO] > 201-Test > Test phase completed!!'
            }
        }

        stage('300-Build Docker Image') {
            steps {
                echo '[INFO] > 300-Build Docker Image > Building Docker image...'
                bat 'docker build -t %REGISTRY%:latest .'
                echo '[INFO] > 300-Build Docker Image > Docker image build completed!!'
            }
        }

        stage('301-Publish Docker Image') {
            steps {
                echo '[INFO] > 301-Publish Docker Image > Publishing Docker image...'
                withCredentials([usernamePassword(credentialsId: REGISTRY_CREDENTIAL, passwordVariable: 'DOCKERHUB_PASSWORD', usernameVariable: 'DOCKERHUB_USERNAME')]) {
                    bat 'docker login -u %DOCKERHUB_USERNAME% -p %DOCKERHUB_PASSWORD%'
                    bat 'docker push %REGISTRY%:latest'
                }
                echo '[INFO] > 301-Publish Docker Image > Docker Image has been published!!'
            }
        }
        
        stage('302-Clean up Docker Image') {
            steps {
                echo "[INFO] > 302-Clean up Docker Image > Cleaning up Docker image after it has been pushed to Dockerhub..."
                bat "docker rmi %REGISTRY%:latest"
                echo "[INFO] > 302-Clean up Docker Image > Docker image has been cleaned up!!"
            }
        }
        
		stage('400-Deploy to Cloud') {
            steps {
                echo "[INFO] > 400-Deploy to Cloud > Deploying Docker image to AWS EC2 instance..."
                withCredentials([aws(credentialsId: AWS_CREDENTIAL, accessKeyVariable: 'AWS_ACCESS_KEY_ID', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY')]) {
                    bat 'docker pull alexintelc/ordermanager:latest'
                    
                 	bat 'docker-compose up -d'
                    
                    echo "[INFO] > 400-Deploy to Cloud > Docker image deployed successfully to AWS EC2 instance!"
                }
            }
        }
    }

    post {
        always {
            bat 'docker logout'
        }
    }
}
